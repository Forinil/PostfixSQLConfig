package com.github.forinil.psc.service;

import com.github.forinil.psc.entity.Forwarding;
import com.github.forinil.psc.exception.database.DatabaseException;
import com.github.forinil.psc.exception.service.ServiceException;
import com.github.forinil.psc.model.create.ForwardingCreateModel;
import com.github.forinil.psc.model.edit.ForwardingEditModel;
import com.github.forinil.psc.model.view.ForwardingViewModel;
import com.github.forinil.psc.repository.ForwardingRepository;
import com.github.forinil.psc.service.impl.ForwardingServiceImpl;
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
public class ForwardingServiceImplTest {

    @Mock
    private ForwardingRepository forwardingRepository;

    private ForwardingService forwardingService;

    @Before
    public void setUp() {
        forwardingService = new ForwardingServiceImpl(forwardingRepository);
    }

    @Test
    public void create() throws DatabaseException, ServiceException {
        val sourceString = "source";
        val destinationString = "destination";
        val forwardingCreateModel = ForwardingCreateModel.of(sourceString, destinationString);
        when(forwardingRepository.create(any(Forwarding.class))).thenReturn(sourceString);
        when(forwardingRepository.read(anyString())).thenReturn(null);

        val id = forwardingService.create(forwardingCreateModel);

        assertEquals(sourceString, id);
        verify(forwardingRepository).read(sourceString);
        verify(forwardingRepository).create(Forwarding.of(sourceString, destinationString));
    }

    @Test
    public void read() throws DatabaseException, ServiceException {
        val sourceString = "source";
        val destinationString = "destination";
        val forwarding = Forwarding.of(sourceString, destinationString);
        when(forwardingRepository.read(anyString())).thenReturn(forwarding);

        val forwardingViewModel = forwardingService.read(sourceString);

        assertEquals(sourceString, forwardingViewModel.getSource());
        assertEquals(destinationString, forwardingViewModel.getDestination());
        verify(forwardingRepository).read(sourceString);
    }

    @Test
    public void update() throws DatabaseException, ServiceException {
        val sourceString = "source";
        val destinationString = "destination";
        val forwardingEditModel = ForwardingEditModel.of(sourceString, destinationString);
        
        forwardingService.update(forwardingEditModel);

        verify(forwardingRepository).update(Forwarding.of(sourceString, destinationString));
    }

    @Test
    public void delete() throws DatabaseException, ServiceException {
        val sourceString = "source";
        val destinationString = "destination";
        val forwardingViewModel = ForwardingViewModel.of(sourceString, destinationString);
        
        forwardingService.delete(forwardingViewModel);

        verify(forwardingRepository).deleteById(sourceString);
    }

    @Test
    public void readAll() throws DatabaseException, ServiceException {
        val sourceString = "source";
        val destinationString = "destination";
        val forwarding = Forwarding.of(sourceString, destinationString);
        val forwardings = new ArrayList<Forwarding>(1);
        forwardings.add(forwarding);
        when(forwardingRepository.readAll()).thenReturn(forwardings);

        val forwardingViewModels = forwardingService.readAll();

        assertEquals(forwardings.size(), forwardingViewModels.size());
        assertEquals(forwardings.get(0).getSource(), forwardingViewModels.get(0).getSource());
        assertEquals(forwardings.get(0).getDestination(), forwardingViewModels.get(0).getDestination());
        verify(forwardingRepository).readAll();
    }

    @Test
    public void deleteAll() throws DatabaseException, ServiceException {
        forwardingService.deleteAll();

        verify(forwardingRepository).deleteAll();
    }
}