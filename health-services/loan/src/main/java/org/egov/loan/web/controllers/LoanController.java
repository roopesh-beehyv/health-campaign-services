package org.egov.loan.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.common.utils.ResponseInfoFactory;
import org.egov.loan.service.LoanApplicationService;
import org.egov.loan.service.LoanService;
import org.egov.loan.web.models.Loan;
import org.egov.loan.web.models.LoanApplication;
import org.egov.loan.web.models.LoanApplicationRequest;
import org.egov.loan.web.models.LoanApplicationResponse;
import org.egov.loan.web.models.LoanApplicationSearchRequest;
import org.egov.loan.web.models.LoanRequest;
import org.egov.loan.web.models.LoanResponse;
import org.egov.loan.web.models.LoanSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-02T16:45:24.641+05:30")

@Controller
@Validated
public class LoanController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final LoanService loanService;

    private final LoanApplicationService loanApplicationService;

    @Autowired
    public LoanController(ObjectMapper objectMapper, HttpServletRequest request,
                          LoanService loanService, LoanApplicationService loanApplicationService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.loanService = loanService;
        this.loanApplicationService = loanApplicationService;
    }


    @RequestMapping(value = "/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<LoanResponse> loanV1CreatePost(@ApiParam(value = "Capture details of Loan.",
            required = true) @Valid @RequestBody LoanRequest loanRequest) throws Exception {
        List<Loan> loans = loanService.create(loanRequest);
        LoanResponse response = LoanResponse.builder()
                .loans(loans)
                .responseInfo(ResponseInfoFactory
                        .createResponseInfo(loanRequest.getRequestInfo(), true))
                .build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @RequestMapping(value = "/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<LoanResponse> loanV1SearchPost(@ApiParam(value = "Capture details of Loan.", required = true) @Valid @RequestBody LoanSearchRequest loanSearchRequest,
                                                               @NotNull @Min(0) @Max(1000) @ApiParam(value = "Pagination - limit records in response", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit,
                                                               @NotNull @Min(0) @ApiParam(value = "Pagination - offset from which records should be returned in response", required = true) @Valid @RequestParam(value = "offset", required = true) Integer offset,
                                                               @NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @Size(min = 2, max = 1000) @RequestParam(value = "tenantId", required = true) String tenantId,
                                                               @ApiParam(value = "epoch of the time since when the changes on the object should be picked up. Search results from this parameter should include both newly created objects since this time as well as any modified objects since this time. This criterion is included to help polling clients to get the changes in system since a last time they synchronized with the platform. ") @Valid @RequestParam(value = "lastChangedSince", required = false) Long lastChangedSince,
                                                               @ApiParam(value = "Used in search APIs to specify if (soft) deleted records should be included in search results.", defaultValue = "false") @Valid @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted) throws Exception {

        List<Loan> loans = loanService.search(loanSearchRequest, limit, offset, tenantId,
                lastChangedSince, includeDeleted);
        LoanResponse loanResponse = LoanResponse.builder()
                .loans(loans)
                .responseInfo(ResponseInfoFactory.createResponseInfo(loanSearchRequest.getRequestInfo(), true))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(loanResponse);
    }

    @RequestMapping(value = "/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<LoanResponse> loanV1UpdatePost(@ApiParam(value = "Capture details of Loan.", required = true) @Valid @RequestBody LoanRequest loanRequest) throws Exception {
        List<Loan> loans = loanService.update(loanRequest);
        LoanResponse response = LoanResponse.builder()
                .loans(loans)
                .responseInfo(ResponseInfoFactory
                        .createResponseInfo(loanRequest.getRequestInfo(), true))
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @RequestMapping(value = "/application/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<LoanApplicationResponse> loanApplicationV1CreatePost(@ApiParam(value = "Capture details of Loan Application.",
            required = true) @Valid @RequestBody LoanApplicationRequest loanApplicationRequest) throws Exception {
        List<LoanApplication> loanApplications = loanApplicationService.create(loanApplicationRequest);
        LoanApplicationResponse response = LoanApplicationResponse.builder()
                .loanApplications(loanApplications)
                .responseInfo(ResponseInfoFactory
                        .createResponseInfo(loanApplicationRequest.getRequestInfo(), true))
                .build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @RequestMapping(value = "/application/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<LoanApplicationResponse> loanApplicationV1SearchPost(@ApiParam(value = "Capture details of Loan Application.", required = true) @Valid @RequestBody LoanApplicationSearchRequest loanApplicationSearchRequest,
                                                         @NotNull @Min(0) @Max(1000) @ApiParam(value = "Pagination - limit records in response", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit,
                                                         @NotNull @Min(0) @ApiParam(value = "Pagination - offset from which records should be returned in response", required = true) @Valid @RequestParam(value = "offset", required = true) Integer offset,
                                                         @NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @Size(min = 2, max = 1000) @RequestParam(value = "tenantId", required = true) String tenantId,
                                                         @ApiParam(value = "epoch of the time since when the changes on the object should be picked up. Search results from this parameter should include both newly created objects since this time as well as any modified objects since this time. This criterion is included to help polling clients to get the changes in system since a last time they synchronized with the platform. ") @Valid @RequestParam(value = "lastChangedSince", required = false) Long lastChangedSince,
                                                         @ApiParam(value = "Used in search APIs to specify if (soft) deleted records should be included in search results.", defaultValue = "false") @Valid @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted) throws Exception {

        List<LoanApplication> loanApplications = loanApplicationService.search(loanApplicationSearchRequest, limit, offset, tenantId,
                lastChangedSince, includeDeleted);
        LoanApplicationResponse loanResponse = LoanApplicationResponse.builder()
                .loanApplications(loanApplications)
                .responseInfo(ResponseInfoFactory.createResponseInfo(loanApplicationSearchRequest.getRequestInfo(), true))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(loanResponse);
    }

    @RequestMapping(value = "/application/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<LoanApplicationResponse> loanApplicationV1UpdatePost(@ApiParam(value = "Capture details of Loan Application.", required = true) @Valid @RequestBody LoanApplicationRequest loanApplicationRequest) throws Exception {
        List<LoanApplication> loanApplications = loanApplicationService.update(loanApplicationRequest);
        LoanApplicationResponse response = LoanApplicationResponse.builder()
                .loanApplications(loanApplications)
                .responseInfo(ResponseInfoFactory
                        .createResponseInfo(loanApplicationRequest.getRequestInfo(), true))
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

}
