package digit.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.egov.common.contract.request.RequestInfo;
import digit.web.models.SchemeApplication;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * Contract class to receive request. Array of  items are used in case of create, whereas single  item is used for update
 */
@ApiModel(description = "Contract class to receive request. Array of  items are used in case of create, whereas single  item is used for update")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-06-26T13:24:22.212+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchemeRequest   {
        @JsonProperty("RequestInfo")
        private RequestInfo requestInfo = null;

        @JsonProperty("SchemeApplications")
        @Valid
        private List<SchemeApplication> schemeApplications = null;


        public SchemeRequest addSchemeApplicationsItem(SchemeApplication schemeApplicationsItem) {
            if (this.schemeApplications == null) {
            this.schemeApplications = new ArrayList<>();
            }
        this.schemeApplications.add(schemeApplicationsItem);
        return this;
        }

}

