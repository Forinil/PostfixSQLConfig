package com.github.forinil.psc.sql;

import com.github.forinil.psc.entity.Domain;

import java.sql.Types;

public class DomainSqlParameterSource extends AbstractEntitySqlParameterSource<String, Domain> {

    public DomainSqlParameterSource(Domain domain) {
        super(domain);
        registerSqlType("domain", Types.VARCHAR);
    }

    @Override
    public boolean hasValue(String paramName) {
        return paramName != null && paramName.equals("domain");
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        if (hasValue(paramName)) {
            return entity.getDomain();
        }
        throw new IllegalArgumentException("Unknown parameter name");
    }
}
