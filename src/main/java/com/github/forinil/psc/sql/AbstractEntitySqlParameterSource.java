package com.github.forinil.psc.sql;

import com.github.forinil.psc.entity.Entity;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;

public abstract class AbstractEntitySqlParameterSource<ID, EntityType extends Entity<ID>> extends AbstractSqlParameterSource {
    protected EntityType entity;

    public AbstractEntitySqlParameterSource(EntityType entity) {
        this.entity = entity;
    }
}
