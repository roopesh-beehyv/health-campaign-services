package digit.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import digit.service.SchemeRegistrationService;
import digit.util.ResponseInfoFactory;
import digit.web.models.*;
import io.swagger.annotations.ApiParam;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-06-26T13:24:22.212+05:30")

@Controller
@ToString
@Slf4j
@RequestMapping("/scheme-services")
public class V1ApiController{

        private final ObjectMapper objectMapper;

        private final HttpServletRequest request;

    private SchemeRegistrationService schemeRegistrationService;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    public V1ApiController(ObjectMapper objectMapper, HttpServletRequest request, SchemeRegistrationService schemeRegistrationService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.schemeRegistrationService = schemeRegistrationService;
    }

    @RequestMapping(value="/v1/registration/_create", method = RequestMethod.POST)
    public ResponseEntity<SchemeRegistrationResponse> v1RegistrationCreatePost(@ApiParam(value = "Details for the new Application(s) + RequestInfo meta data." ,required=true )  @Valid @RequestBody SchemeRequest schemeRequest) {
        log.info("coming here ---------------");
        List<SchemeApplication> applications = schemeRegistrationService.registerSchemeRequest(schemeRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(schemeRequest.getRequestInfo(), true);
        SchemeRegistrationResponse response = SchemeRegistrationResponse.builder().schemeApplications(applications).responseInfo(responseInfo).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/v1/registration/_update", method = RequestMethod.POST)
    public ResponseEntity<SchemeRegistrationResponse> v1RegistrationUpdatePost(@ApiParam(value = "Details for the new (s) + RequestInfo meta data." ,required=true )  @Valid @RequestBody SchemeRequest schemeRequest) {
        SchemeApplication application = schemeRegistrationService.updateSchApplication(schemeRequest);

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(schemeRequest.getRequestInfo(), true);
        SchemeRegistrationResponse response = SchemeRegistrationResponse.builder().schemeApplications(Collections.singletonList(application)).responseInfo(responseInfo).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/v1/scheme/_search", method = RequestMethod.POST)
    public ResponseEntity<SchemeRegistrationResponse> v1SchemeSearchPost(@RequestBody RequestInfoWrapper requestInfoWrapper, @Valid @ModelAttribute SchemeApplicationSearchCriteria schemeApplicationSearchCriteria)  {
        List<SchemeApplication> applications = schemeRegistrationService.searchSchemeApplications(requestInfoWrapper.getRequestInfo(), schemeApplicationSearchCriteria);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true);
        SchemeRegistrationResponse response = SchemeRegistrationResponse.builder().schemeApplications(applications).responseInfo(responseInfo).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
