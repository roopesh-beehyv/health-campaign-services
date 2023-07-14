package digit.enrichment;

//import digit.service.UserService;
import digit.util.IdgenUtil;
import digit.util.UserUtil;
import digit.web.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class SchemeApplicationEnrichment {

    @Autowired
    private IdgenUtil idgenUtil;

    //@Autowired
    //private UserService userService;

    @Autowired
    private UserUtil userUtils;

    public void enrichSchemeApplication(SchemeRequest schemeRequest) {
        //List<String> birthRegistrationIdList = idgenUtil.getIdList(birthRegistrationRequest.getRequestInfo(), birthRegistrationRequest.getBirthRegistrationApplications().get(0).getTenantId(), "identifier.id", "", birthRegistrationRequest.getBirthRegistrationApplications().size());
        Integer index = 0;
        for(SchemeApplication application : schemeRequest.getSchemeApplications()){
            // Enrich audit details
            AuditDetails auditDetails = AuditDetails.builder().createdBy(schemeRequest.getRequestInfo().getUserInfo().getUuid()).createdTime(System.currentTimeMillis()).lastModifiedBy(schemeRequest.getRequestInfo().getUserInfo().getUuid()).lastModifiedTime(System.currentTimeMillis()).build();
            application.setAuditDetails(auditDetails);

            // Enrich UUID
            application.setId(UUID.randomUUID().toString());
            application.setApplicationNumber(UUID.randomUUID().toString());

            //Enrich application number from IDgen
            //application.setApplicationNumber(birthRegistrationIdList.get(index++));

        }
    }

    public void enrichSchemeApplicationUponUpdate(SchemeRequest schemeRequest) {
        // Enrich lastModifiedTime and lastModifiedBy in case of update
        for(SchemeApplication application : schemeRequest.getSchemeApplications()) {
            application.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
            application.getAuditDetails().setLastModifiedBy(schemeRequest.getRequestInfo().getUserInfo().getUuid());
        }
    }
}