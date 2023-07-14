package digit.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import digit.web.models.Address;
import digit.web.models.Applicant;
import digit.web.models.AuditDetails;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * A Object holds the basic data for a Scheme Application
 */
@ApiModel(description = "A Object holds the basic data for a Scheme Application")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-06-26T13:24:22.212+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchemeApplication   {
        @JsonProperty("id")
        private String id = null;

        @JsonProperty("tenantId")
        private String tenantId = null;

        @JsonProperty("applicationNumber")
        private String applicationNumber = null;

        @JsonProperty("name")
        private String name = null;

        @JsonProperty("land")
        private String land = null;

        @JsonProperty("applicant")
        private Applicant applicant = null;

        @JsonProperty("auditDetails")
        private AuditDetails auditDetails = null;

        @Valid
        @JsonProperty("workflow")
        private Workflow workflow = null;


}

