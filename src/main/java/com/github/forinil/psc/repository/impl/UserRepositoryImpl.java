package com.github.forinil.psc.repository.impl;

import com.github.forinil.psc.entity.User;
import com.github.forinil.psc.repository.UserRepository;
import com.github.forinil.psc.sql.UserSqlParameterSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Repository
public class UserRepositoryImpl extends AbstractRepository<String, User> implements UserRepository {

    public UserRepositoryImpl(@Autowired NamedParameterJdbcTemplate jdbcTemplate,
                              @Autowired RowMapper<User> rowMapper) {
        super(jdbcTemplate, rowMapper);
        insertSqlQuery = "INSERT INTO users (email, password) VALUES (:email, :password)";
        selectSqlQuery = "SELECT * FROM users WHERE email = :id";
        updateSqlQuery = "UPDATE users SET password = :password WHERE email = :email";
        deleteSqlQuery = "DELETE FROM users WHERE email = :id";
        selectAllSqlQuery = "SELECT * FROM users";
        deleteAllSqlQuery = "DELETE FROM users";
    }

    @NotNull
    @Override
    SqlParameterSource getParameterSourceFromEntity(@NotNull @Valid User entity) {
        return new UserSqlParameterSource(entity);
    }
}
