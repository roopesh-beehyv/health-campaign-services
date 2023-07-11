package org.egov.loan.web.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.Workflow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.models.product.AdditionalFields;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
* Product
*/
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-02T16:45:24.641+05:30")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanApplication {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min=2,max=1000)
    private String tenantId = null;

    @JsonProperty("loanId")
    @NotNull
    @Size(min = 2, max = 64)
    private String loanId = null;

    @JsonProperty("applicantId")
    @NotNull
    @Size(min = 2, max = 64)
    private String applicantId = null;

    @JsonProperty("tenure")
    @NotNull
    private Integer tenure = null;

    @JsonProperty("hasConsented")
    private Boolean hasConsented = false;

    @JsonProperty("defaultedMonths")
    private String defaultedMonths = null;

    @JsonProperty("hasDefaulted")
    private Boolean hasDefaulted = false;

    @JsonProperty("status")
    @Size(max=1000)
    private String status = null;

    @JsonProperty("rejectionReason")
    @Size(max=1000)
    private String rejectionReason = null;

    @JsonProperty("additionalFields")
    @Valid
    private AdditionalFields additionalFields = null;

    @JsonProperty("isDeleted")
    private Boolean isDeleted = null;

    @JsonProperty("rowVersion")
    private Integer rowVersion = null;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails = null;

    @Valid
    @JsonProperty("workflow")
    private Workflow workflow = null;
}

