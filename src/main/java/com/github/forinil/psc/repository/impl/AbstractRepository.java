package com.github.forinil.psc.repository.impl;

import com.github.forinil.psc.entity.Entity;
import com.github.forinil.psc.exception.database.DatabaseException;
import com.github.forinil.psc.exception.database.DatabaseValidationException;
import com.github.forinil.psc.exception.database.NoRowsUpdatedException;
import com.github.forinil.psc.repository.Repository;
import com.github.forinil.psc.util.ConstraintViolationInformation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Validated
abstract class AbstractRepository<ID, EntityType extends Entity<ID>> implements Repository<ID, EntityType> {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private RowMapper<EntityType> rowMapper;
    private Validator validator;

    String insertSqlQuery;
    String selectSqlQuery;
    String updateSqlQuery;
    String deleteSqlQuery;
    String selectAllSqlQuery;
    String deleteAllSqlQuery;

    AbstractRepository(NamedParameterJdbcTemplate jdbcTemplate,
                       RowMapper<EntityType> rowMapper,
                       Validator validator) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.validator = validator;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public @NotNull ID create(@NotNull Entity<ID> entity) throws DatabaseException {
        try {
            Set<ConstraintViolation<Entity<ID>>> validationResult = validator.validate(entity);
            if (!validationResult.isEmpty()) {
                throw new DatabaseValidationException("", getViolationInformation(validationResult));
            }
            jdbcTemplate.update(insertSqlQuery, getParameterSourceFromEntityInternal((EntityType) entity));
        } catch (DataAccessException e) {
            logger.error("Error inserting entity to database", e);
            throw new DatabaseException(e);
        }
        return entity.id();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @SuppressWarnings("unchecked")
    public EntityType read(@NotNull ID id) throws DatabaseException {
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
    public void update(@NotNull EntityType entity) throws DatabaseException {
        try {
            Set<ConstraintViolation<EntityType>> validationResult = validator.validate(entity);
            if (!validationResult.isEmpty()) {
                throw new DatabaseValidationException("", getViolationInformation(validationResult));
            }
            val rowsUpdated = jdbcTemplate.update(updateSqlQuery, getParameterSourceFromEntityInternal(entity));
            if (rowsUpdated == 0) {
                throw new NoRowsUpdatedException(String.format("No rows updated. Please check if entity with id '%s' exists.", entity.id()));
            }
        } catch (DataAccessException e) {
            logger.error("Error inserting entity to database", e);
            throw new DatabaseException(e);
        }
    }

    @Override
    @Transactional
    public void deleteById(@NotNull ID id) throws DatabaseException {
        try {
            jdbcTemplate.update(deleteSqlQuery, getIDProvidingParameterSource(id));
        } catch (DataAccessException e) {
            logger.error("Error deleting entity from database", e);
            throw new DatabaseException(e);
        }
    }

    @Override
    @Transactional
    public void delete(@NotNull Entity<ID> entity) throws DatabaseException {
        validator.validate(entity);
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
    public @NotNull List<EntityType> readAll() throws DatabaseException {
        try {
            return jdbcTemplate.query(selectAllSqlQuery, new EmptySqlParameterSource(), new RowMapperResultSetExtractor<EntityType>(rowMapper));
        } catch (DataAccessException e) {
            logger.error("Error reading all records from database", e);
            throw new DatabaseException(e);
        }
    }

    abstract @NotNull SqlParameterSource getParameterSourceFromEntity(@NotNull EntityType entity);

    private @NotNull SqlParameterSource getParameterSourceFromEntityInternal(@NotNull EntityType entity) {
        validator.validate(entity);
        return getParameterSourceFromEntity(entity);
    }

    private @NotNull SqlParameterSource getIDProvidingParameterSource(@NotNull ID id) {
        return new MapSqlParameterSource("id", id);
    }

    private @NotNull <T> Set<ConstraintViolationInformation> getViolationInformation(@NotNull Set<ConstraintViolation<T>> validationResult) {
        Set<ConstraintViolationInformation> constraintViolationInformationSet = new HashSet<ConstraintViolationInformation>(validationResult.size());

        for (ConstraintViolation<T> constraintViolation: validationResult) {
            val constraintViolationInformation = ConstraintViolationInformation.builder().message(constraintViolation.getMessage()).object(constraintViolation.getRootBean()).build();
            constraintViolationInformationSet.add(constraintViolationInformation);
        }

        return constraintViolationInformationSet;
    }
}
