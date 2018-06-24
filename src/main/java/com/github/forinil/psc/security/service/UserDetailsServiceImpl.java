package com.github.forinil.psc.security.service;

import com.github.forinil.psc.exception.service.ServiceException;
import com.github.forinil.psc.model.view.UserViewModel;
import com.github.forinil.psc.security.userdetails.AdminDetails;
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
    static final String ADMIN = "ADMIN";

    private UserService userService;

    private AdminDetails admin;

    public UserDetailsServiceImpl(@Autowired UserService userService,
                                  @Autowired AdminDetails admin) {
        this.userService = userService;
        this.admin = admin;
    }

    @NotNull
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val disabled = false;
        val accountExpired = false;
        val credentialsExpired = false;
        val accountLocked = false;

        if (isAdmin(username)) {
            return User.builder()
                    .username(admin.getEmail())
                    .password(admin.getEncodedPassword())
                    .disabled(disabled)
                    .accountExpired(accountExpired)
                    .credentialsExpired(credentialsExpired)
                    .accountLocked(accountLocked)
                    .roles(USER, ADMIN)
                    .build();
        }

        UserViewModel user;
        try {
            user = userService.read(username);
        } catch (ServiceException e) {
            throw new UsernameNotFoundException("Error fetching user from database", e);
        }

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with '%s' not found", username));
        }

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

    private boolean isAdmin(String username) {
        return username.equals(admin.getUsername()) || username.equals(admin.getEmail());
    }
}
