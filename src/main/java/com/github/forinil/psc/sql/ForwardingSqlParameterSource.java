package com.github.forinil.psc.sql;

import com.github.forinil.psc.entity.Forwarding;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class ForwardingSqlParameterSource extends AbstractEntitySqlParameterSource<String, Forwarding> {
    private final Map<String, String> params;

    public ForwardingSqlParameterSource(Forwarding forwarding) {
        super(forwarding);

        registerSqlType("source", Types.VARCHAR);
        registerSqlType("destination", Types.VARCHAR);

        params = new HashMap<String, String>(2);
        params.put("source", entity.getSource());
        params.put("destination", entity.getDestination());
    }

    @Override
    public boolean hasValue(String paramName) {
        return params.containsKey(paramName);
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        if (hasValue(paramName)) {
            return params.get(paramName);
        }
        throw new IllegalArgumentException("Unknown parameter name");
    }
}
