package com.github.forinil.psc.repository.impl;

import com.github.forinil.psc.entity.Domain;
import com.github.forinil.psc.exception.NotUpdatableException;
import com.github.forinil.psc.mappers.DomainRowMapper;
import com.github.forinil.psc.repository.DomainRepository;
import com.github.forinil.psc.sql.DomainSqlParameterSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DomainRepositoryImpl extends AbstractRepository<String, Domain> implements DomainRepository {

    public DomainRepositoryImpl(@Autowired NamedParameterJdbcTemplate jdbcTemplate,
                                @Autowired DomainRowMapper domainRowMapper) {
        super(jdbcTemplate, domainRowMapper);
        insertSqlQuery = "INSERT INTO domains (domain) VALUES (:domain)";
        selectSqlQuery = "SELECT * FROM domains WHERE domain = :id";
        deleteSqlQuery = "DELETE FROM domains WHERE domain = :id";
        selectAllSqlQuery = "SELECT * FROM domains";
        deleteAllSqlQuery = "DELETE FROM domains";
    }

    @Override
    @Transactional
    public void update(Domain entity) throws NotUpdatableException {
        throw new NotUpdatableException("Domain entities are read-only");
    }

    @Override
    SqlParameterSource getParameterSourceFromEntity(Domain entity) {
        return new DomainSqlParameterSource(entity);
    }
}
