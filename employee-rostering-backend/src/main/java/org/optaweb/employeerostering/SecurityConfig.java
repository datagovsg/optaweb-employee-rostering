package org.optaweb.employeerostering;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure (HttpSecurity httpSecurity) throws Exception {
        // TODO: This is necessary only for H2 database console in development
        httpSecurity
                .headers().frameOptions().disable();
        httpSecurity
                .csrf().disable();
        httpSecurity
                .authorizeRequests()
                .antMatchers("/console/**")
                .permitAll();

        // TODO: discern which routes need to be protected in the application
        httpSecurity.authorizeRequests()
                .antMatchers("/**/*").permitAll()
                .and().formLogin();
    }
}
