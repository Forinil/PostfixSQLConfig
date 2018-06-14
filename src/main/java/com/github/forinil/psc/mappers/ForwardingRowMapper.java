package com.github.forinil.psc.mappers;

import com.github.forinil.psc.entity.Forwarding;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ForwardingRowMapper implements RowMapper<Forwarding> {
    @Override
    public Forwarding mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Forwarding.of(rs.getString("source"), rs.getString("destination"));
    }
}
