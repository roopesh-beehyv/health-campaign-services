package org.egov.loan.service;

import digit.models.coremodels.ProcessInstance;
import digit.models.coremodels.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.egov.loan.config.LoanConfiguration;
import org.egov.loan.repository.LoanApplicationRepository;
import org.egov.loan.web.models.LoanApplication;
import org.egov.loan.web.models.LoanApplicationRequest;
import org.egov.loan.web.models.LoanApplicationSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.egov.common.utils.CommonUtils.checkRowVersion;
import static org.egov.common.utils.CommonUtils.enrichForCreate;
import static org.egov.common.utils.CommonUtils.enrichForUpdate;
import static org.egov.common.utils.CommonUtils.getIdToObjMap;
import static org.egov.common.utils.CommonUtils.havingTenantId;
import static org.egov.common.utils.CommonUtils.identifyNullIds;
import static org.egov.common.utils.CommonUtils.includeDeleted;
import static org.egov.common.utils.CommonUtils.isSearchByIdOnly;
import static org.egov.common.utils.CommonUtils.lastChangedSince;
import static org.egov.common.utils.CommonUtils.uuidSupplier;
import static org.egov.common.utils.CommonUtils.validateEntities;

@Service
@Slf4j
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

    private final LoanConfiguration loanConfiguration;

    private final WorkflowService workflowService;

    @Autowired
    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository,
                                  LoanConfiguration loanConfiguration,
                                  WorkflowService workflowService) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanConfiguration = loanConfiguration;
        this.workflowService = workflowService;
    }

    public List<LoanApplication> create(LoanApplicationRequest loanApplicationRequest) throws Exception {
        log.info("Enrichment loans started");

        log.info("generating ids for loans");
        List<String> idList =  uuidSupplier().apply(loanApplicationRequest.getLoanApplications().size());

        log.info("enriching loans");
        enrichForCreate(loanApplicationRequest.getLoanApplications(), idList, loanApplicationRequest.getRequestInfo());

        // Initiate workflow for the new application
        log.info("initiate workflow");
        loanApplicationRequest.getLoanApplications().forEach(application -> {
            workflowService.updateWorkflowStatus(application, loanApplicationRequest.getRequestInfo());
        });

        log.info("saving loans");
        loanApplicationRepository.save(loanApplicationRequest.getLoanApplications(), loanConfiguration.getCreateLoanApplicationTopic());
        return loanApplicationRequest.getLoanApplications();
    }

    public List<LoanApplication> update(LoanApplicationRequest loanApplicationRequest) throws Exception {
        identifyNullIds(loanApplicationRequest.getLoanApplications());
        Map<String, LoanApplication> pMap = getIdToObjMap(loanApplicationRequest.getLoanApplications());

        log.info("checking if loan application already exists");
        List<String> loanApplicationIds = new ArrayList<>(pMap.keySet());
        List<LoanApplication> existingLoanApplications = loanApplicationRepository.findById(loanApplicationIds);

        log.info("validate entities for loans");
        validateEntities(pMap, existingLoanApplications);

        log.info("checking row version for loans");
        checkRowVersion(pMap, existingLoanApplications);

        log.info("updating lastModifiedTime and lastModifiedBy");
        enrichForUpdate(pMap, existingLoanApplications, loanApplicationRequest);

        loanApplicationRequest.getLoanApplications().forEach(loanApplication -> {
            Workflow workflow = loanApplication.getWorkflow();

            if (workflow != null) {
                workflowService.updateWorkflowStatus(loanApplication, loanApplicationRequest.getRequestInfo());
            } else {
                ProcessInstance processInstance = workflowService
                        .getCurrentWorkflow(loanApplicationRequest.getRequestInfo(),
                                loanApplication.getTenantId(),
                                loanApplication.getId());
                loanApplication.setWorkflow(Workflow.builder()
                        .comments(processInstance.getComment())
                        .build());
                loanApplication.setStatus(processInstance.getState().getState());
            }
        });

        log.info("saving updated loans");
        loanApplicationRepository.save(loanApplicationRequest.getLoanApplications(), loanConfiguration.getUpdateLoanApplicationTopic());
        return loanApplicationRequest.getLoanApplications();
    }

    public List<LoanApplication> search(LoanApplicationSearchRequest loanApplicationSearchRequest,
                                        Integer limit,
                                        Integer offset,
                                        String tenantId,
                                        Long lastChangedSince,
                                        Boolean includeDeleted) throws Exception {

        log.info("received request to search loan application");

        if (isSearchByIdOnly(loanApplicationSearchRequest.getLoanApplication())) {
            log.info("searching loan application by id");
            List<String> ids = loanApplicationSearchRequest.getLoanApplication().getId();
            log.info("fetching loan application with ids: {}", ids);
            return loanApplicationRepository.findById(ids, includeDeleted).stream()
                    .filter(lastChangedSince(lastChangedSince))
                    .filter(havingTenantId(tenantId))
                    .filter(includeDeleted(includeDeleted))
                    .collect(Collectors.toList());
        }
        log.info("searching loan application using criteria");
        List<LoanApplication> loanApplications =  loanApplicationRepository.find(loanApplicationSearchRequest.getLoanApplication(), limit,
                offset, tenantId, lastChangedSince, includeDeleted);

        // If no applications are found matching the given criteria, return an empty list
        if(CollectionUtils.isEmpty(loanApplications))
            return new ArrayList<>();

        // Enrich applicant object and workflow object
        loanApplications.forEach(application -> {
            ProcessInstance processInstance = workflowService
                    .getCurrentWorkflow(loanApplicationSearchRequest.getRequestInfo(),
                    application.getTenantId(),
                    application.getId());
//            enrichmentUtil.enrichApplicantOnSearch(application);
            application.setWorkflow(Workflow.builder()
                    .comments(processInstance.getComment())
                    .build());
            application.setStatus(processInstance.getState().getState());
        });
        return loanApplications;
    }
}
