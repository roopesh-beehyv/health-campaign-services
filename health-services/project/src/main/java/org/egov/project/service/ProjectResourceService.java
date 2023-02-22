package org.egov.project.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.ds.Tuple;
import org.egov.common.models.ErrorDetails;
import org.egov.common.validator.Validator;
import org.egov.project.config.ProjectConfiguration;
import org.egov.project.repository.ProjectResourceRepository;
import org.egov.project.service.enrichment.ProjectResourceEnrichmentService;
import org.egov.project.validator.resource.PrProductVariantIdValidator;
import org.egov.project.validator.resource.PrProjectIdValidator;
import org.egov.project.web.models.ProjectResource;
import org.egov.project.web.models.ProjectResourceBulkRequest;
import org.egov.project.web.models.ProjectResourceRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.egov.common.utils.CommonUtils.handleErrors;
import static org.egov.common.utils.CommonUtils.populateErrorDetails;
import static org.egov.common.utils.CommonUtils.validate;
import static org.egov.project.Constants.GET_PROJECT_RESOURCE;
import static org.egov.project.Constants.SET_PROJECT_RESOURCE;
import static org.egov.project.Constants.VALIDATION_ERROR;

@Service
@Slf4j
public class ProjectResourceService {

    private final List<Validator<ProjectResourceBulkRequest, ProjectResource>> validators;

    private final ProjectResourceRepository projectResourceRepository;

    private final ProjectConfiguration projectConfiguration;

    private final ProjectResourceEnrichmentService enrichmentService;

    private final Predicate<Validator<ProjectResourceBulkRequest, ProjectResource>> isApplicableForCreate = validator ->
            validator.getClass().equals(PrProjectIdValidator.class)
                    || validator.getClass().equals(PrProductVariantIdValidator.class);

    public ProjectResourceService(List<Validator<ProjectResourceBulkRequest, ProjectResource>> validators, ProjectResourceRepository projectResourceRepository, ProjectConfiguration projectConfiguration, ProjectResourceEnrichmentService enrichmentService) {
        this.validators = validators;
        this.projectResourceRepository = projectResourceRepository;
        this.projectConfiguration = projectConfiguration;
        this.enrichmentService = enrichmentService;
    }

    public ProjectResource create(ProjectResourceRequest request) {
        ProjectResourceBulkRequest resourceBulkRequest = ProjectResourceBulkRequest.builder()
                .projectResource(Arrays.asList(request.getProjectResource())).requestInfo(request.getRequestInfo())
                .build();

        return create(resourceBulkRequest, false).get(0);
    }

    public List<ProjectResource> create(ProjectResourceBulkRequest request, boolean isBulk) {
        log.info("received request to create bulk project resource");
        Tuple<List<ProjectResource>, Map<ProjectResource, ErrorDetails>> tuple = validate(validators,
                isApplicableForCreate, request, SET_PROJECT_RESOURCE, GET_PROJECT_RESOURCE, VALIDATION_ERROR,
                isBulk);

        Map<ProjectResource, ErrorDetails> errorDetailsMap = tuple.getY();
        List<ProjectResource> validEntities = tuple.getX();
        try {
            if (!validEntities.isEmpty()) {
                log.info("processing {} valid entities", validEntities.size());
                enrichmentService.create(validEntities, request);
                projectResourceRepository.save(validEntities, projectConfiguration.getCreateProjectResourceTopic());
                log.info("successfully created project resource");
            }
        } catch (Exception exception) {
            log.error("error occurred while creating project resource: {}", exception.getMessage());
            populateErrorDetails(request, errorDetailsMap, validEntities, exception, SET_PROJECT_RESOURCE);
        }

        handleErrors(errorDetailsMap, isBulk, VALIDATION_ERROR);

        return validEntities;
    }

    public ProjectResource update(ProjectResourceRequest request) {
        ProjectResourceBulkRequest resourceBulkRequest = ProjectResourceBulkRequest.builder()
                .projectResource(Arrays.asList(request.getProjectResource())).requestInfo(request.getRequestInfo())
                .build();

        return update(resourceBulkRequest, false).get(0);
    }

    public List<ProjectResource> update(ProjectResourceBulkRequest request, boolean isBulk) {
        return null;
    }

    public ProjectResource delete(ProjectResourceRequest request) {
        ProjectResourceBulkRequest resourceBulkRequest = ProjectResourceBulkRequest.builder()
                .projectResource(Arrays.asList(request.getProjectResource())).requestInfo(request.getRequestInfo())
                .build();

        return delete(resourceBulkRequest, false).get(0);
    }

    public List<ProjectResource> delete(ProjectResourceBulkRequest request, boolean isBulk) {
        return null;
    }
}
