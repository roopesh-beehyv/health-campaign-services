package org.egov.loan;

import org.egov.tracer.config.TracerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableCaching
@Import({TracerConfiguration.class})
public class LoanApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(LoanApp.class, args);
    }
}
