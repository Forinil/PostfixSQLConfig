package com.github.forinil.psc.repository;

import com.github.forinil.psc.entity.Domain;
import com.github.forinil.psc.exception.NotUpdatableException;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainRepositoryIT {
    @Autowired
    private DomainRepository domainRepository;

    @Test
    public void testCreatingDomain() {
        val domain = Domain.of("localhost");
        val id = domainRepository.create(domain);

        Assert.assertEquals("localhost", id);
    }

    @Test
    public void testReadingDomain() {
        val domain = Domain.of("localhost2");
        val id = domainRepository.create(domain);

        Assert.assertEquals("localhost2", id);

        val readDomain = domainRepository.read(id);
        Assert.assertEquals(id, readDomain.getDomain());
    }

    @Test
    public void testReadAll() {
        val domains = domainRepository.readAll();

        Assert.assertEquals(2, domains.size());
    }

    @Test(expected = NotUpdatableException.class)
    public void testUpdate() throws NotUpdatableException {
        val domain = Domain.of("test");

        domainRepository.update(domain);
    }
}
