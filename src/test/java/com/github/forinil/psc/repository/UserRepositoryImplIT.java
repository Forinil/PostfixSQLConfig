package com.github.forinil.psc.repository;

import com.github.forinil.psc.PostfixSQLConfigApplication;
import com.github.forinil.psc.entity.User;
import com.github.forinil.psc.exception.database.DatabaseException;
import lombok.val;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PostfixSQLConfigApplication.class)
public class UserRepositoryImplIT {
    private static final int CAPACITY = 2;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUpDatabase() {
        val users = new ArrayList<Object[]>(CAPACITY);
        users.add(new String[]{"user1@example.com", "password"});
        users.add(new String[]{"user2@example.com", "password"});

        jdbcTemplate.batchUpdate("INSERT INTO users (email, password) VALUES (?, ?)", users);
    }

    @After
    public void cleanUpDatabase() {
        jdbcTemplate.update("DELETE FROM users");
    }

    @Test
    public void testCreate() throws DatabaseException {
        val email = "kbotor@example.com";
        val user = User.of(email, email);

        val id = userRepository.create(user);
        Assert.assertEquals(email, id);
    }

    @Test
    public void testRead() throws DatabaseException {
        val email = "kbotor@example.com";
        val user = User.of(email, email);

        val id = userRepository.create(user);
        Assert.assertEquals(email, id);

        val readUser = userRepository.read(id);
        Assert.assertEquals(id, readUser.getEmail());
    }

    @Test
    public void testReadAll() throws DatabaseException {
        val users = userRepository.readAll();

        Assert.assertEquals(CAPACITY, users.size());
    }

    @Test
    public void testUpdate() throws DatabaseException {
        val user = User.of("user1@example.com", "new_password");
        userRepository.update(user);

        val updatedUser = userRepository.read(user.id());
        Assert.assertEquals("new_password", updatedUser.getPassword());
    }

    @Test
    public void testDelete() throws DatabaseException {
        val userString = "user@example.com";
        val user = User.of(userString, userString);

        userRepository.delete(user);

        val dbDomain = userRepository.read(user.getEmail());

        Assert.assertNull(dbDomain);
    }

    @Test
    public void testDeleteAll() throws DatabaseException {
        userRepository.deleteAll();

        val domains = userRepository.readAll();
        Assert.assertTrue(domains.isEmpty());
    }
}
