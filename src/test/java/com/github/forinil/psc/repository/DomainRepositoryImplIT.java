package com.github.forinil.psc.repository;

import com.github.forinil.psc.entity.Domain;
import com.github.forinil.psc.exception.DataAccessException;
import com.github.forinil.psc.exception.NotUpdatableException;
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
@SpringBootTest
public class DomainRepositoryImplIT {
    private static final int CAPACITY = 2;

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUpDatabase() {
        val domains = new ArrayList<Object[]>(CAPACITY);
        domains.add(new String[]{"localhost"});
        domains.add(new String[]{"pckbotor"});

        jdbcTemplate.batchUpdate("INSERT INTO domains (domain) VALUES (?)", domains);
    }

    @After
    public void cleanUpDatabase() {
        jdbcTemplate.update("DELETE FROM domains");
    }

    @Test
    public void testCreatingDomain() throws DataAccessException {
        val domainString = "localhost.localdomain";
        val domain = Domain.of(domainString);

        val id = domainRepository.create(domain);
        Assert.assertEquals(domainString, id);
    }

    @Test
    public void testReadingDomain() throws DataAccessException {
        val domainString = "localhost.localdomain";
        val domain = Domain.of(domainString);

        val id = domainRepository.create(domain);
        Assert.assertEquals(domainString, id);

        val readDomain = domainRepository.read(id);
        Assert.assertEquals(id, readDomain.getDomain());
    }

    @Test
    public void testReadAll() throws DataAccessException {
        val domains = domainRepository.readAll();

        Assert.assertEquals(CAPACITY, domains.size());
    }

    @Test(expected = NotUpdatableException.class)
    public void testUpdate() throws NotUpdatableException {
        val domain = Domain.of("test");

        domainRepository.update(domain);
    }

    @Test
    public void testDelete() throws DataAccessException {
        val domain = Domain.of("localhost");

        domainRepository.delete(domain);

        val dbDomain = domainRepository.read(domain.getDomain());

        Assert.assertNull(dbDomain);
    }

    @Test
    public void testDeleteAll() throws DataAccessException {
        domainRepository.deleteAll();

        val domains = domainRepository.readAll();
        Assert.assertTrue(domains.isEmpty());
    }
}
