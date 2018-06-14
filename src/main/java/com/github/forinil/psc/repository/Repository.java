package com.github.forinil.psc.repository;

import com.github.forinil.psc.entity.Entity;
import com.github.forinil.psc.exception.database.DatabaseException;
import lombok.NonNull;

import java.util.List;

public interface Repository<ID, EntityType extends Entity<ID>> {
    ID create(@NonNull Entity<ID> entity) throws DatabaseException;
    EntityType read(@NonNull ID id) throws DatabaseException;
    void update(@NonNull EntityType entity) throws DatabaseException;
    void deleteById(@NonNull ID id) throws DatabaseException;
    void delete(@NonNull Entity<ID> entity) throws DatabaseException;

    List<EntityType> readAll() throws DatabaseException;
    void deleteAll() throws DatabaseException;
}
