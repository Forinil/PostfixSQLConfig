package com.github.forinil.psc.repository;

import com.github.forinil.psc.PostfixSQLConfigApplication;
import com.github.forinil.psc.entity.Forwarding;
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
public class ForwardingRepositoryImplIT {
    private static final int CAPACITY = 2;

    @Autowired
    private ForwardingRepository forwardingRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUpDatabase() {
        val users = new ArrayList<Object[]>(CAPACITY);
        users.add(new String[]{"source1", "destination"});
        users.add(new String[]{"source2", "destination"});

        jdbcTemplate.batchUpdate("INSERT INTO forwardings (source, destination) VALUES (?, ?)", users);
    }

    @After
    public void cleanUpDatabase() {
        jdbcTemplate.update("DELETE FROM forwardings");
    }

    @Test
    public void testCreate() throws DatabaseException {
        val sourceString = "source";
        val destinationString = "destination";
        val forwarding = Forwarding.of(sourceString, destinationString);

        val id = forwardingRepository.create(forwarding);
        Assert.assertEquals(sourceString, id);
    }

    @Test
    public void testRead() throws DatabaseException {
        val sourceString = "source";
        val destinationString = "destination";
        val forwarding = Forwarding.of(sourceString, destinationString);

        val id = forwardingRepository.create(forwarding);
        Assert.assertEquals(sourceString, id);

        val readForwarding = forwardingRepository.read(id);
        Assert.assertEquals(id, readForwarding.getSource());
    }

    @Test
    public void testReadAll() throws DatabaseException {
        val forwardings = forwardingRepository.readAll();

        Assert.assertEquals(CAPACITY, forwardings.size());
    }

    @Test
    public void testUpdate() throws DatabaseException {
        val sourceString = "source1";
        val destinationString = "destination2";
        val forwarding = Forwarding.of(sourceString, destinationString);
        forwardingRepository.update(forwarding);

        val updatedUser = forwardingRepository.read(forwarding.id());
        Assert.assertEquals(destinationString, updatedUser.getDestination());
    }

    @Test
    public void testDelete() throws DatabaseException {
        val sourceString = "source1";
        val destinationString = "destination2";
        val forwarding = Forwarding.of(sourceString, destinationString);

        forwardingRepository.delete(forwarding);

        val dbForwarding = forwardingRepository.read(forwarding.getSource());

        Assert.assertNull(dbForwarding);
    }

    @Test
    public void testDeleteAll() throws DatabaseException {
        forwardingRepository.deleteAll();

        val forwardings = forwardingRepository.readAll();
        Assert.assertTrue(forwardings.isEmpty());
    }
}
