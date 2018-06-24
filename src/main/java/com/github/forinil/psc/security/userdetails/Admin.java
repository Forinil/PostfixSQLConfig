package com.github.forinil.psc.security.userdetails;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Admin implements AdminDetails {
    @Value("${psc.admin.username}")
    private String username;

    @Value("${psc.admin.password}")
    private String password;

    @Value("${psc.admin.domain}")
    private String domain;

    private PasswordEncoder passwordEncoder;

    public Admin(@Autowired PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String getEncodedPassword() {
        return passwordEncoder.encode(password);
    }

    @Override
    public String getEmail() {
        return String.format("%s@%s", username, domain);
    }
}
