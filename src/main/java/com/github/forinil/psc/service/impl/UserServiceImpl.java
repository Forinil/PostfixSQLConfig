package com.github.forinil.psc.service.impl;

import com.github.forinil.psc.entity.User;
import com.github.forinil.psc.exception.database.DatabaseException;
import com.github.forinil.psc.exception.service.EntityAlreadyExistsException;
import com.github.forinil.psc.exception.service.ServiceException;
import com.github.forinil.psc.model.create.UserCreateModel;
import com.github.forinil.psc.model.edit.UserEditModel;
import com.github.forinil.psc.model.view.UserViewModel;
import com.github.forinil.psc.repository.UserRepository;
import com.github.forinil.psc.service.UserService;
import lombok.val;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(@Autowired UserRepository userRepository,
                           @Autowired PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @NotNull
    @NotEmpty
    @Override
    public String create(@NotNull @Valid UserCreateModel userCreateModel) throws ServiceException {
        try {
            val user = userRepository.read(userCreateModel.getEmail());
            if (user == null) {
                val encodedPassword = passwordEncoder.encode(userCreateModel.getPassword());
                return userRepository.create(User.of(userCreateModel.getEmail(), encodedPassword));
            } else {
                throw new EntityAlreadyExistsException(String.format("User with email %s already exists", userCreateModel.getEmail()));
            }
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Cannot create user %s", userCreateModel), e);
        }
    }

    @Override
    public UserViewModel read(@NotNull String id) throws ServiceException {
        try {
            return entityToViewModel(userRepository.read(id));
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Error fetching user with id '%s' from repository", id), e);
        }
    }

    @Override
    public void update(@NotNull @Valid UserEditModel userEditModel) throws ServiceException {
        val encodedPassword = passwordEncoder.encode(userEditModel.getNewPassword());
        try {
            userRepository.create(User.of(userEditModel.getEmail(), encodedPassword));
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Cannot update user %s", userEditModel), e);
        }
    }

    @Override
    public void deleteById(@NotNull String id) throws ServiceException {
        try {
            userRepository.deleteById(id);
        } catch (DatabaseException e) {
            throw new ServiceException(String.format("Cannot delete user with id '%s'", id), e);
        }
    }

    @Override
    public void delete(@NotNull @Valid UserViewModel entity) throws ServiceException {
        deleteById(entity.getEmail());
    }

    @NotNull
    @Override
    public List<UserViewModel> readAll() throws ServiceException {
        try {
            return entityListToViewModelList(userRepository.readAll());
        } catch (DatabaseException e) {
            throw new ServiceException("Error fetching all users from repository", e);
        }
    }

    @Override
    public void deleteAll() throws ServiceException {
        try {
            userRepository.deleteAll();
        } catch (DatabaseException e) {
            throw new ServiceException("Error deleting all users", e);
        }
    }

    @NotNull
    private UserViewModel entityToViewModel(@NotNull @Valid User user) {
        return UserViewModel.of(user.getEmail(), user.getPassword());
    }

    @NotNull
    private List<UserViewModel> entityListToViewModelList(@NotNull List<User> users) {
        val userViews = new ArrayList<UserViewModel>(users.size());

        for (User user: users) {
            userViews.add(entityToViewModel(user));
        }

        return userViews;
    }
}
