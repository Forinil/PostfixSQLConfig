package com.github.forinil.psc.repository.impl;

import com.github.forinil.psc.entity.Entity;
import com.github.forinil.psc.exception.database.DatabaseException;
import com.github.forinil.psc.repository.Repository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
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
    public ID create(@NonNull Entity<ID> entity) throws DatabaseException {
            try {
            jdbcTemplate.update(insertSqlQuery, getParameterSourceFromEntity((EntityType) entity));
        } catch (DataAccessException e) {
            logger.error("Error inserting entity to database", e);
            throw new DatabaseException(e);
        }
        return entity.id();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @SuppressWarnings("unchecked")
    public EntityType read(@NonNull ID id) throws DatabaseException {
        try {
            return jdbcTemplate.queryForObject(selectSqlQuery, getIDProvidingParameterSource(id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            logger.error("Error reading entity from database", e);
            throw new DatabaseException(e);
        }
    }

    @Override
    @Transactional
    public void update(@NonNull EntityType entity) throws DatabaseException {
        try {
            jdbcTemplate.update(updateSqlQuery, getParameterSourceFromEntity(entity));
        } catch (DataAccessException e) {
            logger.error("Error inserting entity to database", e);
            throw new DatabaseException(e);
        }
    }

    @Override
    @Transactional
    public void deleteById(ID id) throws DatabaseException {
        try {
            jdbcTemplate.update(deleteSqlQuery, getIDProvidingParameterSource(id));
        } catch (DataAccessException e) {
            logger.error("Error deleting entity from database", e);
            throw new DatabaseException(e);
        }
    }

    @Override
    @Transactional
    public void delete(Entity<ID> entity) throws DatabaseException {
        deleteById(entity.id());
    }



    @Override
    @Transactional
    public void deleteAll() throws DatabaseException {
        try {
            jdbcTemplate.update(deleteAllSqlQuery, new EmptySqlParameterSource());
        } catch (DataAccessException e) {
            logger.error("Error deleting all records from table", e);
            throw new DatabaseException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<EntityType> readAll() throws DatabaseException {
        try {
            return jdbcTemplate.query(selectAllSqlQuery, new EmptySqlParameterSource(), new RowMapperResultSetExtractor<EntityType>(rowMapper));
        } catch (DataAccessException e) {
            logger.error("Error reading all records from database", e);
            throw new DatabaseException(e);
        }
    }

    abstract SqlParameterSource getParameterSourceFromEntity(@NonNull EntityType entity);

    private SqlParameterSource getIDProvidingParameterSource(@NonNull ID id) {
        return new MapSqlParameterSource("id", id);
    }
}
