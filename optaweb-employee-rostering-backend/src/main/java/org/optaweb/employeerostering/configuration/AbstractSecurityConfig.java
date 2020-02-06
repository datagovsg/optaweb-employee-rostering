package org.optaweb.employeerostering.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


abstract public class AbstractSecurityConfig extends WebSecurityConfigurerAdapter {}


@EnableWebSecurity
@Profile({Profiles.DEVELOPMENT, Profiles.TEST})
class MockSecurityConfig extends AbstractSecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(Profiles.class);

    @Override
    protected void configure (HttpSecurity httpSecurity) throws Exception {
        logger.info("Configuring security for DEVELOPMENT.");

        httpSecurity
                .headers().frameOptions().disable();
        httpSecurity
                .csrf().disable();

        // Necessary only for H2 database console in development
        httpSecurity
                .authorizeRequests()
                .antMatchers("/h2-console/**", "/login/**")
                .permitAll();

        // TODO: discern which routes need to be protected in the application
        httpSecurity.authorizeRequests()
                .antMatchers("/**").permitAll()
                .and().formLogin();
    }
}

@EnableWebSecurity
@Profile({Profiles.STAGING, Profiles.PRODUCTION})
class ProductionSecurityConfig extends AbstractSecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(Profiles.class);

    @Override
    protected void configure (HttpSecurity httpSecurity) throws Exception {
        logger.info("Configuring security for PRODUCTION.");
        httpSecurity
            .headers().frameOptions().disable();
        httpSecurity
            .csrf().disable();
        // TODO: discern which routes need to be protected in the application
        httpSecurity.authorizeRequests()
                .antMatchers("/**").permitAll()
                .and().formLogin();
    }
}
