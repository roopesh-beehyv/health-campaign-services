package org.egov.loan.repository.rowmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import org.egov.common.models.product.AdditionalFields;
import org.egov.common.models.product.Product;
import org.egov.loan.web.models.Loan;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LoanRowMapper implements RowMapper<Loan> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Loan mapRow(ResultSet resultSet, int i) throws SQLException {

        try {
            return Loan.builder()
                    .id(resultSet.getString("id"))
                    .rowVersion(resultSet.getInt("rowversion"))
                    .isDeleted(resultSet.getBoolean("isdeleted"))
                    .tenantId(resultSet.getString("tenantid"))
                    .name(resultSet.getString("name"))
                    .interestRate(resultSet.getDouble("interestRate"))
                    .durationPeriod(resultSet.getString("durationPeriod"))
                    .provider(resultSet.getString("provider"))
                    .contact(resultSet.getString("contact"))
                    .type(resultSet.getString("type"))
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
