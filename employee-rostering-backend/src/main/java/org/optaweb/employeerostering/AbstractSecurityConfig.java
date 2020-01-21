package org.optaweb.employeerostering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

class Profiles {
    public static final String DEVELOPMENT = "dev";
    public static final String PRODUCTION = "prod";
}

abstract public class AbstractSecurityConfig extends WebSecurityConfigurerAdapter {}


@EnableWebSecurity
@Profile(Profiles.DEVELOPMENT)
class DevelopmentSecurityConfig extends AbstractSecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(Profiles.class);

    @Override
    protected void configure (HttpSecurity httpSecurity) throws Exception {
        logger.info("Configuring security for DEVELOPMENT.");

        // Necessary only for H2 database console in development
        httpSecurity
                .headers().frameOptions().disable();
        httpSecurity
                .csrf().disable();
        httpSecurity
                .authorizeRequests()
                .antMatchers("/h2-console/**")
                .permitAll();

        // TODO: discern which routes need to be protected in the application
        httpSecurity.authorizeRequests()
                .antMatchers("/").permitAll()
                .and().formLogin();
    }
}

@EnableWebSecurity
@Profile(Profiles.PRODUCTION)
class ProductionSecurityConfig extends AbstractSecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(Profiles.class);

    @Override
    protected void configure (HttpSecurity httpSecurity) throws Exception {
        logger.info("Configuring security for PRODUCTION.");

        // TODO: discern which routes need to be protected in the application
        httpSecurity.authorizeRequests()
                .antMatchers("/").permitAll()
                .and().formLogin();
    }
}
