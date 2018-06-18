package com.github.forinil.psc.service;

import com.github.forinil.psc.entity.User;
import com.github.forinil.psc.exception.database.DatabaseException;
import com.github.forinil.psc.exception.service.ServiceException;
import com.github.forinil.psc.model.create.UserCreateModel;
import com.github.forinil.psc.model.edit.UserEditModel;
import com.github.forinil.psc.model.view.UserViewModel;
import com.github.forinil.psc.repository.UserRepository;
import com.github.forinil.psc.service.impl.UserServiceImpl;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String ENCODED_PASSWORD = "encodedPassword";

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @Before
    public void setUp() {
        when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    public void create() throws DatabaseException, ServiceException {
        val email = "kbotor@gmail.com";
        val password = "password";
        val userCreateModel = UserCreateModel.of(email, password, password);
        when(userRepository.create(any(User.class))).thenReturn(email);
        when(userRepository.read(anyString())).thenReturn(null);

        val id = userService.create(userCreateModel);

        assertEquals(email, id);
        verify(passwordEncoder).encode(password);
        verify(userRepository).create(User.of(email, ENCODED_PASSWORD));
    }

    @Test
    public void read() throws DatabaseException, ServiceException {
        val email = "kbotor@example.com";
        val password = "password";
        val user = User.of(email, password);
        when(userRepository.read(anyString())).thenReturn(user);

        val user2 = userService.read(email);

        assertEquals(user2.getEmail(), email);
        assertEquals(user2.getPassword(), password);
        verify(userRepository).read(email);
    }

    @Test
    public void update() throws DatabaseException, ServiceException {
        val email = "kbotor@gmail.com";
        val password = "password";
        val password2 = "password2";
        val userEditModel = UserEditModel.of(email, password, password2, password2);

        userService.update(userEditModel);

        verify(passwordEncoder).encode(password2);
        verify(userRepository).create(User.of(email, ENCODED_PASSWORD));
    }

    @Test
    public void delete() throws DatabaseException, ServiceException {
        val email = "kbotor@gmail.com";
        val password = "password";
        val userViewModel = UserViewModel.of(email, password);

        userService.delete(userViewModel);

        verify(userRepository).deleteById(email);
    }

    @Test
    public void readAll() throws DatabaseException, ServiceException {
        val email = "kbotor@gmail.com";
        val password = "password";
        val user = User.of(email, password);
        val users = new ArrayList<User>();
        users.add(user);
        when(userRepository.readAll()).thenReturn(users);

        val userViewModelList = userService.readAll();

        assertEquals(users.size(), userViewModelList.size());
        assertEquals(users.get(0).getEmail(), userViewModelList.get(0).getEmail());
        assertEquals(users.get(0).getPassword(), userViewModelList.get(0).getPassword());
        verify(userRepository).readAll();
    }

    @Test
    public void deleteAll() throws DatabaseException, ServiceException {
        userService.deleteAll();

        verify(userRepository).deleteAll();
    }
}