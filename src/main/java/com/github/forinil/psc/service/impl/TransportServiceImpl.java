package com.github.forinil.psc.service.impl;

import com.github.forinil.psc.entity.Transport;
import com.github.forinil.psc.exception.database.DatabaseException;
import com.github.forinil.psc.exception.service.EntityAlreadyExistsException;
import com.github.forinil.psc.exception.service.ServiceException;
import com.github.forinil.psc.model.create.TransportCreateModel;
import com.github.forinil.psc.model.edit.TransportEditModel;
import com.github.forinil.psc.model.view.TransportViewModel;
import com.github.forinil.psc.repository.TransportRepository;
import com.github.forinil.psc.service.TransportService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class TransportServiceImpl implements TransportService {
    private TransportRepository transportRepository;
    
    public TransportServiceImpl(@Autowired TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }
    
    @NotNull
    @Override
    public String create(@NotNull @Valid TransportCreateModel transportCreateModel) throws ServiceException {
        try {
            val transport = transportRepository.read(transportCreateModel.getDomain());
            if (transport == null) {
                return transportRepository.create(Transport.of(transportCreateModel.getDomain(), transportCreateModel.getTransport()));
            } else {
                throw new EntityAlreadyExistsException(String.format("Transport with domain %s already exists", transportCreateModel.getDomain()));
            }
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Cannot create transport %s", transportCreateModel), e);
        }
    }

    @Override
    public TransportViewModel read(@NotNull String id) throws ServiceException {
        try {
            return entityToViewModel(transportRepository.read(id));
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Error fetching transport with id '%s' from repository", id), e);
        }
    }

    @Override
    public void update(@NotNull @Valid TransportEditModel transportEditModel) throws ServiceException {
        try {
            transportRepository.update(Transport.of(transportEditModel.getDomain(), transportEditModel.getTransport()));
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Cannot update transport %s", transportEditModel), e);
        }
    }

    @Override
    public void deleteById(@NotNull String id) throws ServiceException {
        try {
            transportRepository.deleteById(id);
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Cannot delete transport with id '%s'", id), e);
        }
    }

    @Override
    public void delete(@NotNull @Valid TransportViewModel transportViewModel) throws ServiceException {
        deleteById(transportViewModel.getDomain());
    }

    @NotNull
    @Override
    public List<TransportViewModel> readAll() throws ServiceException {
        try {
            return entityListToViewModelList(transportRepository.readAll());
        } catch (DatabaseException e) {
            throw new ServiceException("Error fetching all transports from repository", e);
        }
    }

    @Override
    public void deleteAll() throws ServiceException {
        try {
            transportRepository.deleteAll();
        } catch (DatabaseException e) {
            throw new ServiceException("Error deleting all transports", e);
        }
    }

    @NotNull
    private TransportViewModel entityToViewModel(@NotNull @Valid Transport transport) {
        return TransportViewModel.of(transport.getDomain(), transport.getTransport());
    }

    @NotNull
    private List<TransportViewModel> entityListToViewModelList(@NotNull List<Transport> transports) {
        val transportViews = new ArrayList<TransportViewModel>(transports.size());

        for (Transport transport: transports) {
            transportViews.add(entityToViewModel(transport));
        }

        return transportViews;
    }
}
