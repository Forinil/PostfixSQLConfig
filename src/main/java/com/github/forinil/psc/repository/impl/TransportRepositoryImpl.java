package com.github.forinil.psc.repository.impl;

import com.github.forinil.psc.entity.Transport;
import com.github.forinil.psc.repository.TransportRepository;
import com.github.forinil.psc.sql.TransportSqlParameterSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class TransportRepositoryImpl extends AbstractRepository<String, Transport> implements TransportRepository {

    TransportRepositoryImpl(@Autowired NamedParameterJdbcTemplate jdbcTemplate,
                            @Autowired RowMapper<Transport> rowMapper) {
        super(jdbcTemplate, rowMapper);
        insertSqlQuery = "INSERT INTO transport (domain, transport) VALUES (:domain, :transport)";
        selectSqlQuery = "SELECT * FROM transport WHERE domain = :id";
        updateSqlQuery = "UPDATE transport SET transport = :transport WHERE domain = :domain";
        deleteSqlQuery = "DELETE FROM transport WHERE domain = :id";
        selectAllSqlQuery = "SELECT * FROM transport";
        deleteAllSqlQuery = "DELETE FROM transport";
    }

    @Override
    SqlParameterSource getParameterSourceFromEntity(Transport entity) {
        return new TransportSqlParameterSource(entity);
    }
}
