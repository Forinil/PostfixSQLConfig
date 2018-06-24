package com.github.forinil.psc.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.github.forinil.psc.security.service.UserDetailsServiceImpl.ADMIN;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${management.context-path}")
    private String actuatorContextPath;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.mvcMatcher("/**").authorizeRequests()
                .mvcMatchers("/").permitAll()
                .mvcMatchers("/css/**").permitAll()
                .mvcMatchers("/favicon.ico").permitAll()
                .mvcMatchers(HttpMethod.GET,"/login").permitAll()
                .mvcMatchers(HttpMethod.GET, actuatorContextPath + "/**").hasRole(ADMIN)
                .mvcMatchers(HttpMethod.POST, actuatorContextPath + "/loggers/**").hasRole(ADMIN)
                .mvcMatchers(HttpMethod.HEAD, actuatorContextPath + "/logfile", actuatorContextPath + "/logfile.json").hasRole(ADMIN)
                .mvcMatchers("/**").authenticated()
            .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
            .and()
                .logout()
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/")
            .and()
                .httpBasic();
    }
}
