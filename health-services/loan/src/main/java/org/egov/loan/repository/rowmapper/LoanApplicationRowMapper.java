package org.egov.loan.repository.rowmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.models.product.AdditionalFields;
import org.egov.loan.web.models.LoanApplication;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Component
public class LoanApplicationRowMapper implements RowMapper<LoanApplication> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public LoanApplication mapRow(ResultSet resultSet, int i) throws SQLException {

        try {
            String defaultedMonths = resultSet.getString("defaultedMonths");
            return LoanApplication.builder()
                    .id(resultSet.getString("id"))
                    .rowVersion(resultSet.getInt("rowversion"))
                    .isDeleted(resultSet.getBoolean("isdeleted"))
                    .hasConsented(resultSet.getBoolean("hasConsented"))
                    .hasDefaulted(resultSet.getBoolean("hasDefaulted"))
                    .tenantId(resultSet.getString("tenantid"))
                    .loanId(resultSet.getString("loanId"))
                    .applicantId(resultSet.getString("applicantId"))
                    .status(resultSet.getString("status"))
                    .rejectionReason(resultSet.getString("rejectionReason"))
                    .tenure(resultSet.getInt("tenure"))
                    .defaultedMonths(StringUtils.isEmpty(defaultedMonths) ? Arrays.asList() : Arrays.asList(defaultedMonths.split(",")))
                    .auditDetails(AuditDetails.builder()
                            .createdBy(resultSet.getString("createdby"))
                            .createdTime(resultSet.getLong("createdtime"))
                            .lastModifiedBy(resultSet.getString("lastmodifiedby"))
                            .lastModifiedTime(resultSet.getLong("lastmodifiedtime"))
                            .build())
                    .additionalFields(resultSet.getString("additionalDetails") == null ? null : objectMapper.readValue(resultSet.getString("additionalDetails"), AdditionalFields.class))
                    .build();
        } catch (JsonProcessingException e) {
            throw new SQLException(e);
        }
    }
}
