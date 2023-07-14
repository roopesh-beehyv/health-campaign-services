package digit.repository.rowmapper;

import digit.web.models.AuditDetails;
import digit.web.models.SchemeApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class SchemeApplicationRowMapper implements ResultSetExtractor<List<SchemeApplication>> {
    public List<SchemeApplication> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String,SchemeApplication> birthRegistrationApplicationMap = new LinkedHashMap<>();

        while (rs.next()){
            String uuid = rs.getString("bapplicationnumber");
            SchemeApplication birthRegistrationApplication = birthRegistrationApplicationMap.get(uuid);

            if(birthRegistrationApplication == null) {

                Long lastModifiedTime = rs.getLong("blastModifiedTime");
                if (rs.wasNull()) {
                    lastModifiedTime = null;
                }


                AuditDetails auditdetails = AuditDetails.builder()
                        .createdBy(rs.getString("bcreatedBy"))
                        .createdTime(rs.getLong("bcreatedTime"))
                        .lastModifiedBy(rs.getString("blastModifiedBy"))
                        .lastModifiedTime(lastModifiedTime)
                        .build();

                birthRegistrationApplication = SchemeApplication.builder()
                        .applicationNumber(rs.getString("bapplicationnumber"))
                        .tenantId(rs.getString("btenantid"))
                        .id(rs.getString("bid"))
                        .name(rs.getString("name"))
                        .auditDetails(auditdetails)
                        .build();
            }
            birthRegistrationApplicationMap.put(uuid, birthRegistrationApplication);
        }
        return new ArrayList<>(birthRegistrationApplicationMap.values());
    }





}
