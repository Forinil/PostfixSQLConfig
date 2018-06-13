package com.github.forinil.psc.sql;

import com.github.forinil.psc.entity.User;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class UserSqlParameterSource extends AbstractEntitySqlParameterSource<String, User> {
    private final Map<String, String> params;

    public UserSqlParameterSource(User user) {
        super(user);

        registerSqlType("email", Types.VARCHAR);
        registerSqlType("password", Types.VARCHAR);

        params = new HashMap<String, String>(2);
        params.put("email", entity.getEmail());
        params.put("password", entity.getPassword());
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
