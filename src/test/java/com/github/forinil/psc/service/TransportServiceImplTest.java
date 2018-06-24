package com.github.forinil.psc.service;

import com.github.forinil.psc.entity.Transport;
import com.github.forinil.psc.exception.database.DatabaseException;
import com.github.forinil.psc.exception.service.ServiceException;
import com.github.forinil.psc.model.create.TransportCreateModel;
import com.github.forinil.psc.model.edit.TransportEditModel;
import com.github.forinil.psc.model.view.TransportViewModel;
import com.github.forinil.psc.repository.TransportRepository;
import com.github.forinil.psc.service.impl.TransportServiceImpl;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransportServiceImplTest {

    @Mock
    private TransportRepository transportRepository;

    private TransportService transportService;

    @Before
    public void setUp() {
        transportService = new TransportServiceImpl(transportRepository);
    }

    @Test
    public void create() throws DatabaseException, ServiceException {
        val domainString = "domain";
        val transportString = "transport";
        val transport = TransportCreateModel.of(domainString, transportString);
        when(transportRepository.create(any(Transport.class))).thenReturn(domainString);
        when(transportRepository.read(anyString())).thenReturn(null);

        val id = transportService.create(transport);

        assertEquals(domainString, id);
        verify(transportRepository).read(domainString);
        verify(transportRepository).create(Transport.of(domainString, transportString));
    }

    @Test
    public void read() throws DatabaseException, ServiceException {
        val domainString = "domain";
        val transportString = "transport";
        val transport = Transport.of(domainString, transportString);
        when(transportRepository.read(anyString())).thenReturn(transport);

        val transportViewModel = transportService.read(domainString);

        assertEquals(domainString, transportViewModel.getDomain());
        assertEquals(transportString, transportViewModel.getTransport());
        verify(transportRepository).read(domainString);
    }

    @Test
    public void update() throws DatabaseException, ServiceException {
        val domainString = "domain";
        val transportString = "transport";
        val transportEditModel = TransportEditModel.of(domainString, transportString);

        transportService.update(transportEditModel);

        verify(transportRepository).update(Transport.of(domainString, transportString));
    }

    @Test
    public void delete() throws DatabaseException, ServiceException {
        val domainString = "domain";
        val transportString = "transport";
        val transportViewModel = TransportViewModel.of(domainString, transportString);

        transportService.delete(transportViewModel);

        verify(transportRepository).deleteById(domainString);
    }

    @Test
    public void readAll() throws DatabaseException, ServiceException {
        val domainString = "domain";
        val transportString = "transport";
        val transport = Transport.of(domainString, transportString);
        val transports = new ArrayList<Transport>(1);
        transports.add(transport);
        when(transportRepository.readAll()).thenReturn(transports);

        val transportViewModels = transportService.readAll();

        assertEquals(transports.size(), transportViewModels.size());
        assertEquals(transports.get(0).getDomain(), transportViewModels.get(0).getDomain());
        assertEquals(transports.get(0).getTransport(), transportViewModels.get(0).getTransport());
        verify(transportRepository).readAll();
    }

    @Test
    public void deleteAll() throws DatabaseException, ServiceException {
        transportService.deleteAll();

        verify(transportRepository).deleteAll();
    }
}