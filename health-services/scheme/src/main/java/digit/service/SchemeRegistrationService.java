package digit.service;



import digit.config.SchemeConfiguration;
import digit.enrichment.SchemeApplicationEnrichment;
import digit.kafka.Producer;
import digit.repository.SchemeRegistrationRepository;
import digit.validators.SchemeApplicationValidator;
import digit.web.models.*;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class SchemeRegistrationService {

    @Autowired
    private SchemeApplicationValidator validator;

    @Autowired
    private SchemeApplicationEnrichment enrichmentUtil;

    @Autowired
    private UserService userService;


    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private SchemeRegistrationRepository schemeRegistrationRepository;

    @Autowired
    private Producer producer;

    @Autowired
    private SchemeConfiguration configuration;

    public List<SchemeApplication> registerSchemeRequest(SchemeRequest schemeRequest) {
        // Validate applications
        validator.validateBirthApplication(schemeRequest);

        // Enrich applications
        enrichmentUtil.enrichSchemeApplication(schemeRequest);

        // Enrich/Upsert user in upon birth registration
        //userService.callUserService(birthRegistrationRequest);

        // Initiate workflow for the new application
        workflowService.updateWorkflowStatus(schemeRequest);

        // Push the application to the topic for persister to listen and persist
        producer.push(configuration.getCreateTopic(), schemeRequest);

        // Return the response back to user
        return schemeRequest.getSchemeApplications();
    }

    public List<SchemeApplication> searchSchemeApplications(RequestInfo requestInfo, SchemeApplicationSearchCriteria schemeApplicationSearchCriteria) {
        // Fetch applications from database according to the given search criteria
        log.info("Final query: test");
        List<SchemeApplication> applications = schemeRegistrationRepository.getApplications(schemeApplicationSearchCriteria);

        // If no applications are found matching the given criteria, return an empty list
        if(CollectionUtils.isEmpty(applications))
            return new ArrayList<>();


        //WORKFLOW INTEGRATION
        applications.forEach(application -> {
            application.setWorkflow(Workflow.builder().
                    status(workflowService.getCurrentWorkflow(requestInfo, application.getTenantId(),
                            application.getApplicationNumber()).getState().getState()).build());
        });


        // Otherwise return the found applications
        return applications;
    }

    public SchemeApplication updateSchApplication(SchemeRequest schemeRequest) {
        // Validate whether the application that is being requested for update indeed exists
        SchemeApplication existingApplication = validator.validateApplicationExistence(schemeRequest.getSchemeApplications().get(0));
        //existingApplication.setWorkflow(schemeRequest.getBirthRegistrationApplications().get(0).getWorkflow());
        //log.info(existingApplication.toString());
        //birthRegistrationRequest.setBirthRegistrationApplications(Collections.singletonList(existingApplication));

        // Enrich application upon update


        workflowService.updateWorkflowStatus(schemeRequest);

        // Just like create request, update request will be handled asynchronously by the persister
        producer.push(configuration.getUpdateTopic(), schemeRequest);

        return schemeRequest.getSchemeApplications().get(0);
    }
}
