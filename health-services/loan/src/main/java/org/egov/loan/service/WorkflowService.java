package org.egov.loan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.BusinessService;
import digit.models.coremodels.BusinessServiceResponse;
import digit.models.coremodels.ProcessInstance;
import digit.models.coremodels.ProcessInstanceRequest;
import digit.models.coremodels.ProcessInstanceResponse;
import digit.models.coremodels.RequestInfoWrapper;
import digit.models.coremodels.State;
import digit.models.coremodels.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.loan.config.LoanConfiguration;
import org.egov.loan.repository.ServiceRequestRepository;
import org.egov.loan.web.models.LoanApplication;
import org.egov.loan.web.models.LoanApplicationRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class WorkflowService {

    @Qualifier("objectMapper")
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private LoanConfiguration config;

    public void updateWorkflowStatus(LoanApplication loanApplication, RequestInfo requestInfo) {
        ProcessInstance processInstance = getProcessInstanceForLoanApplication(loanApplication,
                requestInfo);
        ProcessInstanceRequest workflowRequest = new ProcessInstanceRequest(
                requestInfo, Collections.singletonList(processInstance));
        State state = callWorkFlow(workflowRequest);
        loanApplication.setStatus(state.getState());
    }

    public State callWorkFlow(ProcessInstanceRequest workflowReq) {

        ProcessInstanceResponse response = null;
        StringBuilder url = new StringBuilder(config.getWfHost().concat(config.getWfTransitionPath()));
        Object optional = repository.fetchResult(url, workflowReq);
        response = mapper.convertValue(optional, ProcessInstanceResponse.class);
        return response.getProcessInstances().get(0).getState();
    }

    private ProcessInstance getProcessInstanceForLoanApplication(LoanApplication application,
                                                                 RequestInfo requestInfo) {
        Workflow workflow = application.getWorkflow();

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setBusinessId(application.getId());
        processInstance.setAction(workflow.getAction());
        processInstance.setModuleName(config.getLoanBusiness());
        processInstance.setTenantId(application.getTenantId());
        processInstance.setBusinessService(config.getLoanBusinessCode());
        processInstance.setDocuments(workflow.getVerificationDocuments());
        processInstance.setComment(workflow.getComments());

        if (!CollectionUtils.isEmpty(workflow.getAssignes())) {
            List<User> users = new ArrayList<>();

            workflow.getAssignes().forEach(uuid -> {
                User user = User.builder().uuid(uuid).build();
                users.add(user);
            });

            processInstance.setAssignes(users);
        }

        return processInstance;

    }

    public ProcessInstance getCurrentWorkflow(RequestInfo requestInfo, String tenantId, String businessId) {

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        StringBuilder url = getWorkflowSearchURLWithParams(tenantId, businessId);

        Object res = repository.fetchResult(url, requestInfoWrapper);
        ProcessInstanceResponse response = null;

        try {
            response = mapper.convertValue(res, ProcessInstanceResponse.class);
        } catch (Exception e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse workflow search response");
        }

        if (response != null && !CollectionUtils.isEmpty(response.getProcessInstances())
                && response.getProcessInstances().get(0) != null)
            return response.getProcessInstances().get(0);

        return null;
    }

    private BusinessService getBusinessService(LoanApplication application, RequestInfo requestInfo) {
        String tenantId = application.getTenantId();
        StringBuilder url = getSearchURLWithParams(tenantId, "DTR");
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        Object result = repository.fetchResult(url, requestInfoWrapper);
        BusinessServiceResponse response = null;
        try {
            response = mapper.convertValue(result, BusinessServiceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse response of workflow business service search");
        }

        if (CollectionUtils.isEmpty(response.getBusinessServices()))
            throw new CustomException("BUSINESSSERVICE_NOT_FOUND", "The businessService " + "DTR" + " is not found");

        return response.getBusinessServices().get(0);
    }

    private StringBuilder getSearchURLWithParams(String tenantId, String businessService) {

        StringBuilder url = new StringBuilder(config.getWfHost());
        url.append(config.getWfBusinessServiceSearchPath());
        url.append("?tenantId=");
        url.append(tenantId);
        url.append("&businessServices=");
        url.append(businessService);
        return url;
    }

    private StringBuilder getWorkflowSearchURLWithParams(String tenantId, String businessId) {

        StringBuilder url = new StringBuilder(config.getWfHost());
        url.append(config.getWfProcessInstanceSearchPath());
        url.append("?tenantId=");
        url.append(tenantId);
        url.append("&businessIds=");
        url.append(businessId);
        return url;
    }

    public ProcessInstanceRequest getProcessInstanceForBirthRegistrationPayment(LoanApplicationRequest updateRequest) {

        LoanApplication application = updateRequest.getLoanApplications().get(0);

        ProcessInstance process = ProcessInstance.builder()
                .businessService(config.getLoanBusinessCode())
                .businessId(application.getId())
                .comment("Payment for loan application processed")
                .moduleName(config.getLoanBusiness())
                .tenantId(application.getTenantId())
                .action("PAY")
                .build();

        return ProcessInstanceRequest.builder()
                .requestInfo(updateRequest.getRequestInfo())
                .processInstances(Arrays.asList(process))
                .build();

    }
}