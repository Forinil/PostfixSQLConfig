package com.github.forinil.psc.security.service;

import com.github.forinil.psc.exception.service.ServiceException;
import com.github.forinil.psc.model.view.UserViewModel;
import com.github.forinil.psc.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Service
@Validated
public class UserDetailsServiceImpl implements UserDetailsService {

    static final String USER = "USER";

    private UserService userService;

    public UserDetailsServiceImpl(@Autowired UserService userService) {
        this.userService = userService;
    }

    @NotNull
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserViewModel user;
        try {
            user = userService.read(username);
        } catch (ServiceException e) {
            throw new UsernameNotFoundException("Error fetching user from database", e);
        }

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with '%s' not found", username));
        }

        val disabled = false;
        val accountExpired = false;
        val credentialsExpired = false;
        val accountLocked = false;

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .disabled(disabled)
                .accountExpired(accountExpired)
                .credentialsExpired(credentialsExpired)
                .accountLocked(accountLocked)
                .roles(USER)
                .build();
    }
}
