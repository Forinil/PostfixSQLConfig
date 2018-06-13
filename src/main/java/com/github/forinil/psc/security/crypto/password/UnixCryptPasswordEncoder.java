package com.github.forinil.psc.security.crypto.password;

import lombok.val;
import org.apache.commons.codec.digest.UnixCrypt;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class UnixCryptPasswordEncoder implements PasswordEncoder {
    private final StringKeyGenerator saltGenerator;

    public UnixCryptPasswordEncoder() {
        this.saltGenerator = KeyGenerators.string();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        val salt = this.saltGenerator.generateKey();
        return encode((String) rawPassword, salt);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        val salt = encodedPassword.substring(0, 2);
        val encodedRawPassword = encode((String) rawPassword, salt);
        return encodedPassword.equals(encodedRawPassword);
    }

    private String encode(final String rawPassword, final String salt) {
        return UnixCrypt.crypt(rawPassword, salt);
    }
}
