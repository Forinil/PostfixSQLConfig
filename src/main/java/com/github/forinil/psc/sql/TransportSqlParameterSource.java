package com.github.forinil.psc.sql;

import com.github.forinil.psc.entity.Transport;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class TransportSqlParameterSource extends AbstractEntitySqlParameterSource<String, Transport> {
    private final Map<String, String> params;

    public TransportSqlParameterSource(Transport transport) {
        super(transport);

        registerSqlType("domain", Types.VARCHAR);
        registerSqlType("transport", Types.VARCHAR);

        params = new HashMap<String, String>(2);
        params.put("domain", entity.getDomain());
        params.put("transport", entity.getTransport());
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
