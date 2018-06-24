package com.github.forinil.psc.service;

import com.github.forinil.psc.entity.Domain;
import com.github.forinil.psc.exception.database.DatabaseException;
import com.github.forinil.psc.exception.service.ServiceException;
import com.github.forinil.psc.model.create.DomainCreateModel;
import com.github.forinil.psc.model.edit.DomainEditModel;
import com.github.forinil.psc.model.view.DomainViewModel;
import com.github.forinil.psc.repository.DomainRepository;
import com.github.forinil.psc.service.impl.DomainServiceImpl;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DomainServiceImplTest {

    @Mock
    private DomainRepository domainRepository;

    private DomainService domainService;

    @Before
    public void setUp() {
        domainService = new DomainServiceImpl(domainRepository);
    }

    @Test
    public void create() throws DatabaseException, ServiceException {
        val domainString = "domain";
        val forwardingCreateModel = DomainCreateModel.of(domainString);
        when(domainRepository.create(any(Domain.class))).thenReturn(domainString);
        when(domainRepository.read(anyString())).thenReturn(null);

        val id = domainService.create(forwardingCreateModel);

        assertEquals(domainString, id);
        verify(domainRepository).read(domainString);
        verify(domainRepository).create(Domain.of(domainString));
    }

    @Test
    public void read() throws DatabaseException, ServiceException {
        val domainString = "domain";
        val domain = Domain.of(domainString);
        when(domainRepository.read(anyString())).thenReturn(domain);

        val domainViewModel = domainService.read(domainString);

        assertEquals(domainString, domainViewModel.getDomain());
        verify(domainRepository).read(domainString);
    }

    @Test
    public void update() throws DatabaseException, ServiceException {
        val domainString = "domain";
        val domainEditModel = DomainEditModel.of(domainString);

        domainService.update(domainEditModel);

        verify(domainRepository).update(Domain.of(domainString));
    }

    @Test
    public void delete() throws DatabaseException, ServiceException {
        val domainString = "domain";
        val domainViewModel = DomainViewModel.of(domainString);

        domainService.delete(domainViewModel);

        verify(domainRepository).deleteById(domainString);
    }

    @Test
    public void readAll() throws DatabaseException, ServiceException {
        val domainString = "domain";
        val domain = Domain.of(domainString);
        val domains = new ArrayList<Domain>(1);
        domains.add(domain);
        when(domainRepository.readAll()).thenReturn(domains);

        val domainViewModels = domainService.readAll();

        assertEquals(domains.size(), domainViewModels.size());
        verify(domainRepository).readAll();
    }

    @Test
    public void deleteAll() throws DatabaseException, ServiceException {
        domainService.deleteAll();

        verify(domainRepository).deleteAll();
    }
}