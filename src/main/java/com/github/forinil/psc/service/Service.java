package com.github.forinil.psc.service;

import com.github.forinil.psc.exception.service.ServiceException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface Service<ID, CreateModel, ViewModel, EditModel> {
    @NotNull ID create(@NotNull @Valid CreateModel entity) throws ServiceException;
    ViewModel read(@NotNull ID id) throws ServiceException;
    void update(@NotNull @Valid EditModel entity) throws ServiceException;
    void deleteById(@NotNull ID id) throws ServiceException;
    void delete(@NotNull @Valid ViewModel entity) throws ServiceException;

    @NotNull List<ViewModel> readAll() throws ServiceException;
    void deleteAll() throws ServiceException;
}
