package com.github.forinil.psc.repository;

import com.github.forinil.psc.PostfixSQLConfigApplication;
import com.github.forinil.psc.entity.Forwarding;
import com.github.forinil.psc.entity.Transport;
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
public class TransportRepositoryImplIT {
    private static final int CAPACITY = 2;

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUpDatabase() {
        val transports = new ArrayList<Object[]>(CAPACITY);
        transports.add(new String[]{"domain1", "transport"});
        transports.add(new String[]{"domain2", "transport"});

        jdbcTemplate.batchUpdate("INSERT INTO transport (domain, transport) VALUES (?, ?)", transports);
    }

    @After
    public void cleanUpDatabase() {
        jdbcTemplate.update("DELETE FROM transport");
    }

    @Test
    public void testCreate() throws DatabaseException {
        val domainString = "domain";
        val transportString = "transport";
        val transport = Transport.of(domainString, transportString);

        val id = transportRepository.create(transport);
        Assert.assertEquals(domainString, id);
    }

    @Test
    public void testRead() throws DatabaseException {
        val domainString = "domain";
        val transportString = "transport";
        val transport = Transport.of(domainString, transportString);

        val id = transportRepository.create(transport);
        Assert.assertEquals(domainString, id);

        val readForwarding = transportRepository.read(id);
        Assert.assertEquals(id, readForwarding.getDomain());
    }

    @Test
    public void testReadAll() throws DatabaseException {
        val transports = transportRepository.readAll();

        Assert.assertEquals(CAPACITY, transports.size());
    }

    @Test
    public void testUpdate() throws DatabaseException {
        val domainString = "domain1";
        val transportString = "transport2";
        val transport = Transport.of(domainString, transportString);
        transportRepository.update(transport);

        val updatedTransport = transportRepository.read(transport.id());
        Assert.assertEquals(transportString, updatedTransport.getTransport());
    }

    @Test
    public void testDelete() throws DatabaseException {
        val domainString = "domain1";
        val transportString = "transport2";
        val transport = Transport.of(domainString, transportString);

        transportRepository.delete(transport);

        val dbForwarding = transportRepository.read(transport.getDomain());

        Assert.assertNull(dbForwarding);
    }

    @Test
    public void testDeleteAll() throws DatabaseException {
        transportRepository.deleteAll();

        val transports = transportRepository.readAll();
        Assert.assertTrue(transports.isEmpty());
    }
}
