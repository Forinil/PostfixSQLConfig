package com.github.forinil.psc.repository;

import com.github.forinil.psc.entity.Entity;
import com.github.forinil.psc.exception.database.DatabaseException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface Repository<ID, EntityType extends Entity<ID>> {
    @NotNull ID create(@NotNull @Valid Entity<ID> entity) throws DatabaseException;
    EntityType read(@NotNull ID id) throws DatabaseException;
    void update(@NotNull @Valid EntityType entity) throws DatabaseException;
    void deleteById(@NotNull ID id) throws DatabaseException;
    void delete(@NotNull @Valid Entity<ID> entity) throws DatabaseException;

    @NotNull List<EntityType> readAll() throws DatabaseException;
    void deleteAll() throws DatabaseException;
}
