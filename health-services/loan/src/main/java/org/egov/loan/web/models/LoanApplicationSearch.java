package org.egov.loan.web.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.data.query.annotations.Table;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
* ProductSearch
*/
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-02T16:45:24.641+05:30")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="loan_application")
public class LoanApplicationSearch {

    @JsonProperty("id")
    private List<String> id = null;

    @JsonProperty("loanId")
    private List<String> loanId = null;

    @JsonProperty("applicantId")
    private List<String> applicantId = null;
}

