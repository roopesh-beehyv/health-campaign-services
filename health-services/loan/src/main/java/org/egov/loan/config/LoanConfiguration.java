package org.egov.loan.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class LoanConfiguration {
    @Value("${loan.kafka.create.topic}")
    private String createLoanTopic;

    @Value("${loan.kafka.update.topic}")
    private String updateLoanTopic;

    @Value("${loan.application.kafka.create.topic}")
    private String createLoanApplicationTopic;

    @Value("${loan.application.kafka.update.topic}")
    private String updateLoanApplicationTopic;

    @Value("${egov.workflow.host}")
    private String wfHost;

    @Value("${egov.workflow.transition.path}")
    private String wfTransitionPath;

    @Value("${egov.workflow.businessservice.search.path}")
    private String wfBusinessServiceSearchPath;

    @Value("${egov.workflow.processinstance.search.path}")
    private String wfProcessInstanceSearchPath;

    @Value("${is.workflow.enabled}")
    private Boolean isWorkflowEnabled;

    @Value("${loan.business.codes}")
    private String loanBusinessCode;

    @Value("${loan.business}")
    private String loanBusiness;
}
