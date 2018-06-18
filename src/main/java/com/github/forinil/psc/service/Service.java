package com.github.forinil.psc.service;

import com.github.forinil.psc.exception.service.ServiceException;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface Service<ID, CreateModel, ViewModel, EditModel> {
    @NotNull ID create(@NotNull CreateModel entity) throws ServiceException;
    ViewModel read(@NotNull ID id) throws ServiceException;
    void update(@NotNull EditModel entity) throws ServiceException;
    void deleteById(@NotNull ID id) throws ServiceException;
    void delete(@NotNull ViewModel entity) throws ServiceException;

    @NotNull List<ViewModel> readAll() throws ServiceException;
    void deleteAll() throws ServiceException;
}
