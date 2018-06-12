package com.github.forinil.psc.repository;

import com.github.forinil.psc.entity.Entity;
import com.github.forinil.psc.exception.DataAccessException;
import com.github.forinil.psc.exception.NotUpdatableException;
import lombok.NonNull;

import java.util.List;

public interface Repository<ID, EntityType extends Entity<ID>> {
    ID create(@NonNull Entity<ID> entity) throws DataAccessException;
    EntityType read(@NonNull ID id) throws DataAccessException;
    void update(@NonNull EntityType entity) throws NotUpdatableException, DataAccessException;
    void deleteById(@NonNull ID id) throws DataAccessException;
    void delete(@NonNull Entity<ID> entity) throws DataAccessException;

    List<EntityType> readAll() throws DataAccessException;
    void deleteAll() throws DataAccessException;
}
