package com.github.forinil.psc.repository.impl;

import com.github.forinil.psc.entity.Forwarding;
import com.github.forinil.psc.mappers.ForwardingRowMapper;
import com.github.forinil.psc.repository.ForwardingRepository;
import com.github.forinil.psc.sql.ForwardingSqlParameterSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Repository
public class ForwardingRepositoryImpl extends AbstractRepository<String, Forwarding> implements ForwardingRepository {
    ForwardingRepositoryImpl(@Autowired NamedParameterJdbcTemplate jdbcTemplate,
                             @Autowired ForwardingRowMapper rowMapper) {
        super(jdbcTemplate, rowMapper);
        insertSqlQuery = "INSERT INTO forwardings (source, destination) VALUES (:source, :destination)";
        selectSqlQuery = "SELECT * FROM forwardings WHERE source = :id";
        updateSqlQuery = "UPDATE forwardings SET destination = :destination where source = :source";
        deleteSqlQuery = "DELETE FROM forwardings WHERE source = :id";
        selectAllSqlQuery = "SELECT * FROM forwardings";
        deleteAllSqlQuery = "DELETE FROM forwardings";
    }

    @NotNull
    @Override
    SqlParameterSource getParameterSourceFromEntity(@NotNull @Valid Forwarding entity) {
        return new ForwardingSqlParameterSource(entity);
    }
}
