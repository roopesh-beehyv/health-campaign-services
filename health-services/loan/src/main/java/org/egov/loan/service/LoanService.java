package org.egov.loan.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.loan.config.LoanConfiguration;
import org.egov.loan.repository.LoanRepository;
import org.egov.loan.web.models.Loan;
import org.egov.loan.web.models.LoanRequest;
import org.egov.loan.web.models.LoanSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class LoanService {

    private final LoanRepository loanRepository;
    
    private final LoanConfiguration loanConfiguration;

    @Autowired
    public LoanService(LoanRepository loanRepository, LoanConfiguration loanConfiguration) {
        this.loanRepository = loanRepository;
        this.loanConfiguration = loanConfiguration;
    }

    public List<String> validateLoanId(List<String> loanIds) {
        return loanRepository.validateIds(loanIds, "id");
    }

    public List<Loan> create(LoanRequest loanRequest) throws Exception {
        log.info("Enrichment loans started");

        log.info("generating ids for loans");
        List<String> idList =  uuidSupplier().apply(loanRequest.getLoans().size());

        log.info("enriching loans");
        enrichForCreate(loanRequest.getLoans(), idList, loanRequest.getRequestInfo());

        log.info("saving loans");
        loanRepository.save(loanRequest.getLoans(), loanConfiguration.getCreateLoanTopic());
        return loanRequest.getLoans();
    }

    public List<Loan> update(LoanRequest loanRequest) throws Exception {
        identifyNullIds(loanRequest.getLoans());
        Map<String, Loan> pMap = getIdToObjMap(loanRequest.getLoans());

        log.info("checking if loan already exists");
        List<String> loanIds = new ArrayList<>(pMap.keySet());
        List<Loan> existingLoans = loanRepository.findById(loanIds);

        log.info("validate entities for loans");
        validateEntities(pMap, existingLoans);

        log.info("checking row version for loans");
        checkRowVersion(pMap, existingLoans);

        log.info("updating lastModifiedTime and lastModifiedBy");
        enrichForUpdate(pMap, existingLoans, loanRequest);

        log.info("saving updated loans");
        loanRepository.save(loanRequest.getLoans(), loanConfiguration.getUpdateLoanTopic());
        return loanRequest.getLoans();
    }

    public List<Loan> search(LoanSearchRequest loanSearchRequest,
                                Integer limit,
                                Integer offset,
                                String tenantId,
                                Long lastChangedSince,
                                Boolean includeDeleted) throws Exception {

        log.info("received request to search loan");

        if (isSearchByIdOnly(loanSearchRequest.getLoan())) {
            log.info("searching loan by id");
            List<String> ids = loanSearchRequest.getLoan().getId();
            log.info("fetching loan with ids: {}", ids);
            return loanRepository.findById(ids, includeDeleted).stream()
                    .filter(lastChangedSince(lastChangedSince))
                    .filter(havingTenantId(tenantId))
                    .filter(includeDeleted(includeDeleted))
                    .collect(Collectors.toList());
        }
        log.info("searching loan using criteria");
        return loanRepository.find(loanSearchRequest.getLoan(), limit,
                offset, tenantId, lastChangedSince, includeDeleted);
    }
}
