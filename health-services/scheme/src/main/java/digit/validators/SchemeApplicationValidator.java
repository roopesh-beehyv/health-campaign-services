package digit.validators;


import digit.repository.SchemeRegistrationRepository;
import digit.web.models.*;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class SchemeApplicationValidator {

    @Autowired
    private SchemeRegistrationRepository repository;

    public void validateBirthApplication(SchemeRequest schemeApplication) {
        schemeApplication.getSchemeApplications().forEach(application -> {
            if(ObjectUtils.isEmpty(application.getTenantId()))
                throw new CustomException("EG_BT_APP_ERR", "tenantId is mandatory for creating scheme applications");
        });
    }

    public SchemeApplication validateApplicationExistence(SchemeApplication schemeApplication) {
        return repository.getApplications(SchemeApplicationSearchCriteria.builder().applicationNumber(schemeApplication.getApplicationNumber()).build()).get(0);
    }
}