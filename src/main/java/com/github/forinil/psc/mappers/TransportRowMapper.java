package com.github.forinil.psc.mappers;

import com.github.forinil.psc.entity.Transport;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TransportRowMapper implements RowMapper<Transport> {
    @Override
    public Transport mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Transport.of(rs.getString("domain"), rs.getString("transport"));
    }
}
