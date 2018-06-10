package com.github.forinil.psc.repository;

import com.github.forinil.psc.exception.NotUpdatableException;

import java.util.List;

public interface Repository<ID, Entity> {
    ID create(Entity entity);
    Entity read(ID id);
    void update(Entity entity) throws NotUpdatableException;
    void deleteById(ID id);
    void delete(Entity entity);

    List<Entity> readAll();
    void deleteAll();
}
