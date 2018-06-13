package com.github.forinil.psc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class PostfixSQLConfigWebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PostfixSQLConfigWebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PostfixSQLConfigWebApplication.class);
    }
}
