package org.egov.loan.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.data.query.builder.SelectQueryBuilder;
import org.egov.common.data.repository.GenericRepository;
import org.egov.common.producer.Producer;
import org.egov.loan.repository.rowmapper.LoanRowMapper;
import org.egov.loan.web.models.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class LoanRepository extends GenericRepository<Loan> {

    @Autowired
    public LoanRepository(Producer producer, NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                          RedisTemplate<String, Object> redisTemplate,
                          SelectQueryBuilder selectQueryBuilder, LoanRowMapper loanRowMapper) {
        super(producer, namedParameterJdbcTemplate, redisTemplate, selectQueryBuilder,
                loanRowMapper, Optional.of("loan"));
    }

}

