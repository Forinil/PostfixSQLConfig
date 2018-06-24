package com.github.forinil.psc.service.impl;

import com.github.forinil.psc.entity.Domain;
import com.github.forinil.psc.exception.database.DatabaseException;
import com.github.forinil.psc.exception.service.EntityAlreadyExistsException;
import com.github.forinil.psc.exception.service.ServiceException;
import com.github.forinil.psc.model.create.DomainCreateModel;
import com.github.forinil.psc.model.edit.DomainEditModel;
import com.github.forinil.psc.model.view.DomainViewModel;
import com.github.forinil.psc.repository.DomainRepository;
import com.github.forinil.psc.service.DomainService;
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
public class DomainServiceImpl implements DomainService {
    private DomainRepository domainRepository;
    
    public DomainServiceImpl(@Autowired DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }
    
    @NotNull
    @Override
    public String create(@NotNull DomainCreateModel domainCreateModel) throws ServiceException {
        try {
            val transport = domainRepository.read(domainCreateModel.getDomain());
            if (transport == null) {
                return domainRepository.create(Domain.of(domainCreateModel.getDomain()));
            } else {
                throw new EntityAlreadyExistsException(String.format("Domain %s already exists", domainCreateModel.getDomain()));
            }
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Cannot create domain %s", domainCreateModel), e);
        }
    }

    @Override
    public DomainViewModel read(@NotNull String id) throws ServiceException {
        try {
            return entityToViewModel(domainRepository.read(id));
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Error fetching domain with id '%s' from repository", id), e);
        }
    }

    @Override
    public void update(@NotNull DomainEditModel domainEditModel) throws ServiceException {
        try {
            domainRepository.update(Domain.of(domainEditModel.getDomain()));
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Cannot update domain %s", domainEditModel), e);
        }
    }

    @Override
    public void deleteById(@NotNull String id) throws ServiceException {
        try {
            domainRepository.deleteById(id);
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Cannot delete domain '%s'", id), e);
        }
    }

    @Override
    public void delete(@NotNull DomainViewModel domainViewModel) throws ServiceException {
        deleteById(domainViewModel.getDomain());
    }

    @NotNull
    @Override
    public List<DomainViewModel> readAll() throws ServiceException {
        try {
            return entityListToViewModelList(domainRepository.readAll());
        } catch (DatabaseException e) {
            throw new ServiceException("Error fetching all domains from repository", e);
        }
    }

    @Override
    public void deleteAll() throws ServiceException {
        try {
            domainRepository.deleteAll();
        } catch (DatabaseException e) {
            throw new ServiceException("Error deleting all domains", e);
        }
    }

    @NotNull
    private DomainViewModel entityToViewModel(@NotNull @Valid Domain domain) {
        return DomainViewModel.of(domain.getDomain());
    }

    @NotNull
    private List<DomainViewModel> entityListToViewModelList(@NotNull List<Domain> domains) {
        val domainViewModels = new ArrayList<DomainViewModel>(domains.size());

        for (Domain domain: domains) {
            domainViewModels.add(entityToViewModel(domain));
        }

        return domainViewModels;
    }
}
