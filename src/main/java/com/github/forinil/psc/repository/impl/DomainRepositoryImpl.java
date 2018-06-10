package com.github.forinil.psc.repository.impl;

import com.github.forinil.psc.entity.Domain;
import com.github.forinil.psc.exception.NotUpdatableException;
import com.github.forinil.psc.mappers.DomainRowMapper;
import com.github.forinil.psc.repository.DomainRepository;
import com.github.forinil.psc.repository.Repository;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
public class DomainRepositoryImpl implements DomainRepository {
    private JdbcTemplate jdbcTemplate;
    private DomainRowMapper domainRowMapper;

    public DomainRepositoryImpl(@Autowired JdbcTemplate jdbcTemplate,
                                @Autowired DomainRowMapper domainRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.domainRowMapper = domainRowMapper;
    }

    @Override
    public String create(@NonNull Domain domain) {
        jdbcTemplate.update("INSERT INTO domains (domain) VALUES (?)", domain.getDomain());
        return domain.getDomain();
    }

    @Override
    public Domain read(@NonNull String domain) {
        return jdbcTemplate.queryForObject("SELECT * FROM domains WHERE domain = ?", new Object[] {domain}, domainRowMapper);
    }

    @Override
    public void update(@NonNull Domain domain) throws NotUpdatableException {
        throw new NotUpdatableException("Domain entities are read-only");
    }

    @Override
    public void deleteById(@NonNull String domain) {
        jdbcTemplate.update("DELETE FROM domains WHERE domain = ?", domain);
    }

    @Override
    public void delete(@NonNull Domain domain) {
        deleteById(domain.getDomain());
    }

    @Override
    public List<Domain> readAll() {
        val resultSet = jdbcTemplate.queryForList("SELECT * FROM domains");
        val domains = new ArrayList<Domain>(resultSet.size());

        for (Map result: resultSet) {
            domains.add(Domain.of((String) result.get("domain")));
        }

        return domains;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM domains");
    }
}
