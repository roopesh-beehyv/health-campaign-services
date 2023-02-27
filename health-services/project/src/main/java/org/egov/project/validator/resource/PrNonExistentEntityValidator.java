package org.egov.project.validator.resource;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.models.Error;
import org.egov.common.validator.Validator;
import org.egov.project.repository.ProjectResourceRepository;
import org.egov.project.web.models.ProjectResource;
import org.egov.project.web.models.ProjectResourceBulkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.egov.common.utils.CommonUtils.*;
import static org.egov.common.utils.ValidatorUtils.getErrorForNonExistentEntity;
import static org.egov.project.Constants.GET_ID;

@Component
@Order(value = 4)
@Slf4j
public class PrNonExistentEntityValidator implements Validator<ProjectResourceBulkRequest, ProjectResource> {

    private final ProjectResourceRepository projectResourceRepository;

    @Autowired
    public PrNonExistentEntityValidator(ProjectResourceRepository projectResourceRepository) {
        this.projectResourceRepository = projectResourceRepository;
    }

    @Override
    public Map<ProjectResource, List<Error>> validate(ProjectResourceBulkRequest request) {

        Map<ProjectResource, List<Error>> errorDetailsMap = new HashMap<>();
        List<ProjectResource> entities = request.getProjectResource();
        Class<?> objClass = getObjClass(entities);
        Method idMethod = getMethod(GET_ID, objClass);
        System.out.println(idMethod.getName() + "id methods");
        Map<String, ProjectResource> eMap = getIdToObjMap(entities
                .stream().filter(notHavingErrors()).collect(Collectors.toList()), idMethod);
        if (!eMap.isEmpty()) {
            List<String> entityIds = new ArrayList<>(eMap.keySet());
            List<ProjectResource> existingEntities = projectResourceRepository
                    .findById(entityIds, false, getIdFieldName(idMethod));
            List<ProjectResource> nonExistentEntities = checkNonExistentEntities(eMap,
                    existingEntities, idMethod);
            nonExistentEntities.forEach(projectResource -> {
                Error error = getErrorForNonExistentEntity();
                populateErrorDetails(projectResource, error, errorDetailsMap);
            });
        }

        return errorDetailsMap;
    }
}
