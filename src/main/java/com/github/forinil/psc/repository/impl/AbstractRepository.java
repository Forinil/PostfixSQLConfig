package com.github.forinil.psc.repository.impl;

import com.github.forinil.psc.entity.Entity;
import com.github.forinil.psc.exception.DataAccessException;
import com.github.forinil.psc.exception.NotUpdatableException;
import com.github.forinil.psc.repository.Repository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
abstract class AbstractRepository<ID, EntityType extends Entity<ID>> implements Repository<ID, EntityType> {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private RowMapper<EntityType> rowMapper;

    String insertSqlQuery;
    String selectSqlQuery;
    String updateSqlQuery;
    String deleteSqlQuery;
    String selectAllSqlQuery;
    String deleteAllSqlQuery;

    AbstractRepository(NamedParameterJdbcTemplate jdbcTemplate,
                       RowMapper<EntityType> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public ID create(@NonNull Entity<ID> entity) throws DataAccessException {
        try {
            jdbcTemplate.update(insertSqlQuery, getParameterSourceFromEntity((EntityType) entity));
        } catch (org.springframework.dao.DataAccessException e) {
            logger.error("Error inserting entity to database", e);
            throw new com.github.forinil.psc.exception.DataAccessException(e);
        }
        return entity.id();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @SuppressWarnings("unchecked")
    public EntityType read(@NonNull ID id) throws DataAccessException {
        try {
            return jdbcTemplate.queryForObject(selectSqlQuery, getIDProvidingParameterSource(id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (org.springframework.dao.DataAccessException e) {
            logger.error("Error reading entity from database", e);
            throw new com.github.forinil.psc.exception.DataAccessException(e);
        }
    }

    @Override
    @Transactional
    public void update(@NonNull EntityType entity) throws NotUpdatableException, DataAccessException {
        try {
            jdbcTemplate.update(updateSqlQuery, getParameterSourceFromEntity(entity));
        } catch (org.springframework.dao.DataAccessException e) {
            logger.error("Error inserting entity to database", e);
            throw new com.github.forinil.psc.exception.DataAccessException(e);
        }
    }

    @Override
    @Transactional
    public void deleteById(ID id) throws DataAccessException {
        try {
            jdbcTemplate.update(deleteSqlQuery, getIDProvidingParameterSource(id));
        } catch (org.springframework.dao.DataAccessException e) {
            logger.error("Error deleting entity from database", e);
            throw new com.github.forinil.psc.exception.DataAccessException(e);
        }
    }

    @Override
    @Transactional
    public void delete(Entity<ID> entity) throws DataAccessException {
        deleteById(entity.id());
    }



    @Override
    @Transactional
    public void deleteAll() throws DataAccessException {
        try {
            jdbcTemplate.update(deleteAllSqlQuery, new EmptySqlParameterSource());
        } catch (org.springframework.dao.DataAccessException e) {
            logger.error("Error deleting all records from table", e);
            throw new com.github.forinil.psc.exception.DataAccessException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<EntityType> readAll() throws com.github.forinil.psc.exception.DataAccessException {
        try {
            return jdbcTemplate.query(selectAllSqlQuery, new EmptySqlParameterSource(), new RowMapperResultSetExtractor<EntityType>(rowMapper));
        } catch (org.springframework.dao.DataAccessException e) {
            logger.error("Error reading all records from database", e);
            throw new com.github.forinil.psc.exception.DataAccessException(e);
        }
    }

    abstract SqlParameterSource getParameterSourceFromEntity(@NonNull EntityType entity);

    private SqlParameterSource getIDProvidingParameterSource(@NonNull ID id) {
        return new MapSqlParameterSource("id", id);
    }
}
