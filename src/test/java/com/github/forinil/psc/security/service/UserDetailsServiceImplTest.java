package com.github.forinil.psc.security.service;

import com.github.forinil.psc.entity.User;
import com.github.forinil.psc.exception.database.DatabaseException;
import com.github.forinil.psc.repository.UserRepository;
import com.github.forinil.psc.service.impl.UserServiceImpl;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import static com.github.forinil.psc.security.service.UserDetailsServiceImpl.USER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserDetailsService userDetailsService;

    @Before
    @SuppressWarnings("deprecation")
    public void setUp() {
        val userService = new UserServiceImpl(userRepository, org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
        userDetailsService = new UserDetailsServiceImpl(userService);
    }

    @Test
    public void loadUserByUsername() throws DatabaseException {
        val email = "kbotor@gmail.com";
        val password = "password";
        val authority = new SimpleGrantedAuthority("ROLE_" + USER);
        when(userRepository.read(anyString())).thenReturn(User.of(email, password));

        val user = userDetailsService.loadUserByUsername(email);

        assertEquals(email, user.getUsername());
        assertEquals(password, user.getPassword());
        assertTrue(user.isEnabled());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertEquals(1, user.getAuthorities().size());
        assertTrue(user.getAuthorities().contains(authority));
    }
}