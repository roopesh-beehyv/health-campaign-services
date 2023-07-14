package digit.repository;


import digit.repository.querybuilder.SchemeApplicationQueryBuilder;

import digit.repository.rowmapper.SchemeApplicationRowMapper;
import digit.web.models.SchemeApplication;
import digit.web.models.SchemeApplicationSearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Repository
public class SchemeRegistrationRepository {

    @Autowired
    private SchemeApplicationQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SchemeApplicationRowMapper rowMapper;

    public List<SchemeApplication>getApplications(SchemeApplicationSearchCriteria searchCriteria){
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getApplicationSearchQuery(searchCriteria, preparedStmtList);
        log.info("Final query: " + query);
        return jdbcTemplate.query(query, preparedStmtList.toArray(), rowMapper);
    }
}