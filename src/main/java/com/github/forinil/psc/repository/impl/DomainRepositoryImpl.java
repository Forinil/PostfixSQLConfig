package com.github.forinil.psc.repository.impl;

import com.github.forinil.psc.entity.Domain;
import com.github.forinil.psc.exception.NotUpdatableException;
import com.github.forinil.psc.mappers.DomainRowMapper;
import com.github.forinil.psc.repository.DomainRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class DomainRepositoryImpl implements DomainRepository {
    private JdbcTemplate jdbcTemplate;
    private DomainRowMapper domainRowMapper;

    public DomainRepositoryImpl(@Autowired JdbcTemplate jdbcTemplate,
                                @Autowired DomainRowMapper domainRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.domainRowMapper = domainRowMapper;
    }

    @Transactional
    @Override
    public String create(@NonNull Domain domain) throws com.github.forinil.psc.exception.DataAccessException {
        try {
            jdbcTemplate.update("INSERT INTO domains (domain) VALUES (?)", domain.getDomain());
        } catch (DataAccessException e) {
            logger.error("Error saving domain from database", e);
            throw new com.github.forinil.psc.exception.DataAccessException(e);
        }
        return domain.getDomain();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Domain read(@NonNull String domain) throws com.github.forinil.psc.exception.DataAccessException {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM domains WHERE domain = ?", new Object[] {domain}, domainRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            logger.error("Error reading domain from database", e);
            throw new com.github.forinil.psc.exception.DataAccessException(e);
        }
    }

    @Transactional
    @Override
    public void update(@NonNull Domain domain) throws NotUpdatableException {
        throw new NotUpdatableException("Domain entities are read-only");
    }

    @Transactional
    @Override
    public void deleteById(@NonNull String domain) throws com.github.forinil.psc.exception.DataAccessException {
        try {
            jdbcTemplate.update("DELETE FROM domains WHERE domain = ?", domain);
        } catch (DataAccessException e) {
            logger.error("Error deleting domain from database", e);
            throw new com.github.forinil.psc.exception.DataAccessException(e);
        }
    }

    @Transactional
    @Override
    public void delete(@NonNull Domain domain) throws com.github.forinil.psc.exception.DataAccessException {
        deleteById(domain.getDomain());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Domain> readAll() throws com.github.forinil.psc.exception.DataAccessException {
        List<Map<String, Object>> resultSet;

        try {
            resultSet = jdbcTemplate.queryForList("SELECT * FROM domains");
        } catch (DataAccessException e) {
            logger.error("Error reading domain from database", e);
            throw new com.github.forinil.psc.exception.DataAccessException(e);
        }

        val domains = new ArrayList<Domain>(resultSet.size());

        for (Map result: resultSet) {
            domains.add(Domain.of((String) result.get("domain")));
        }

        return domains;
    }

    @Transactional
    @Override
    public void deleteAll() throws com.github.forinil.psc.exception.DataAccessException {
        try {
            jdbcTemplate.update("DELETE FROM domains");
        } catch (DataAccessException e) {
            logger.error("Error reading domain from database", e);
            throw new com.github.forinil.psc.exception.DataAccessException(e);
        }
    }
}
