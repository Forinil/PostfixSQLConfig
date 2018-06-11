package com.github.forinil.psc.repository;

import com.github.forinil.psc.exception.DataAccessException;
import com.github.forinil.psc.exception.NotUpdatableException;

import java.util.List;

public interface Repository<ID, Entity> {
    ID create(Entity entity) throws DataAccessException;
    Entity read(ID id) throws DataAccessException;
    void update(Entity entity) throws NotUpdatableException;
    void deleteById(ID id) throws DataAccessException;
    void delete(Entity entity) throws DataAccessException;

    List<Entity> readAll() throws DataAccessException;
    void deleteAll() throws DataAccessException;
}
