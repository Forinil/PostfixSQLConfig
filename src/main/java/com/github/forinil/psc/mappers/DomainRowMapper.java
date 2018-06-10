package com.github.forinil.psc.mappers;

import com.github.forinil.psc.entity.Domain;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DomainRowMapper implements RowMapper<Domain> {
    @Override
    public Domain mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Domain.of(rs.getString("domain"));
    }
}
