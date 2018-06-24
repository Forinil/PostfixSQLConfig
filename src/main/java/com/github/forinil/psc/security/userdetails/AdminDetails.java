package com.github.forinil.psc.security.userdetails;

public interface AdminDetails {
    String getUsername();
    String getPassword();
    String getDomain();

    String getEncodedPassword();
    String getEmail();
}
