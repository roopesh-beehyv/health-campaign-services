package org.egov.loan.web.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
* Boundary
*/
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-02T16:45:24.641+05:30")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Boundary   {
        @JsonProperty("code")
      @NotNull



    private String code = null;

        @JsonProperty("name")
      @NotNull



    private String name = null;

        @JsonProperty("label")
    


    private String label = null;

        @JsonProperty("latitude")
    


    private String latitude = null;

        @JsonProperty("longitude")
    


    private String longitude = null;

        @JsonProperty("children")
    
  @Valid


    private List<Boundary> children = null;

        @JsonProperty("materializedPath")
    


    private String materializedPath = null;


        public Boundary addChildrenItem(Boundary childrenItem) {
            if (this.children == null) {
            this.children = new ArrayList<>();
            }
        this.children.add(childrenItem);
        return this;
        }

}

