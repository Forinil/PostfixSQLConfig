package com.github.forinil.psc.service.impl;

import com.github.forinil.psc.entity.Forwarding;
import com.github.forinil.psc.exception.database.DatabaseException;
import com.github.forinil.psc.exception.service.EntityAlreadyExistsException;
import com.github.forinil.psc.exception.service.ServiceException;
import com.github.forinil.psc.model.create.ForwardingCreateModel;
import com.github.forinil.psc.model.edit.ForwardingEditModel;
import com.github.forinil.psc.model.view.ForwardingViewModel;
import com.github.forinil.psc.repository.ForwardingRepository;
import com.github.forinil.psc.service.ForwardingService;
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
public class ForwardingServiceImpl implements ForwardingService {
    private ForwardingRepository forwardingRepository;

    public ForwardingServiceImpl(@Autowired ForwardingRepository forwardingRepository) {
        this.forwardingRepository = forwardingRepository;
    }

    @NotNull
    @Override
    public String create(@NotNull ForwardingCreateModel forwardingCreateModel) throws ServiceException {
        try {
            val transport = forwardingRepository.read(forwardingCreateModel.getSource());
            if (transport == null) {
                return forwardingRepository.create(Forwarding.of(forwardingCreateModel.getSource(), forwardingCreateModel.getDestination()));
            } else {
                throw new EntityAlreadyExistsException(String.format("Forwarding from source %s already exists", forwardingCreateModel.getSource()));
            }
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Cannot create forwarding %s", forwardingCreateModel), e);
        }
    }

    @Override
    public ForwardingViewModel read(@NotNull String id) throws ServiceException {
        try {
            return entityToViewModel(forwardingRepository.read(id));
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Error fetching forwarding with id '%s' from repository", id), e);
        }
    }

    @Override
    public void update(@NotNull ForwardingEditModel forwardingEditModel) throws ServiceException {
        try {
            forwardingRepository.update(Forwarding.of(forwardingEditModel.getSource(), forwardingEditModel.getDestination()));
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Cannot update forwarding %s", forwardingEditModel), e);
        }
    }

    @Override
    public void deleteById(@NotNull String id) throws ServiceException {
        try {
            forwardingRepository.deleteById(id);
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Cannot delete forwarding with id '%s'", id), e);
        }
    }

    @Override
    public void delete(@NotNull ForwardingViewModel forwardingViewModel) throws ServiceException {
        deleteById(forwardingViewModel.getSource());
    }

    @NotNull
    @Override
    public List<ForwardingViewModel> readAll() throws ServiceException {
        try {
            return entityListToViewModelList(forwardingRepository.readAll());
        } catch (DatabaseException e) {
            throw new ServiceException("Error fetching all forwardings from repository", e);
        }
    }

    @Override
    public void deleteAll() throws ServiceException {
        try {
            forwardingRepository.deleteAll();
        } catch (DatabaseException e) {
            throw new ServiceException("Error deleting all forwardings", e);
        }
    }

    @NotNull
    private ForwardingViewModel entityToViewModel(@NotNull @Valid Forwarding forwarding) {
        return ForwardingViewModel.of(forwarding.getSource(), forwarding.getDestination());
    }

    @NotNull
    private List<ForwardingViewModel> entityListToViewModelList(@NotNull List<Forwarding> forwardings) {
        val forwardingViewModels = new ArrayList<ForwardingViewModel>(forwardings.size());

        for (Forwarding forwarding: forwardings) {
            forwardingViewModels.add(entityToViewModel(forwarding));
        }

        return forwardingViewModels;
    }
}
