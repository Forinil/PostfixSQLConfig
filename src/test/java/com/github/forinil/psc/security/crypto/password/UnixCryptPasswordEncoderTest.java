package com.github.forinil.psc.security.crypto.password;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;

public class UnixCryptPasswordEncoderTest {

    @Test
    public void testPasswordEncoding() {
        val passwordEncoder = new UnixCryptPasswordEncoder();
        val raw = "password";
        val encoded  = passwordEncoder.encode(raw);

        Assert.assertTrue(passwordEncoder.matches(raw, encoded));
    }
}
