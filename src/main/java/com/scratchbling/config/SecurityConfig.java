package com.scratchbling.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        User.UserBuilder builder = User.withDefaultPasswordEncoder();

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(builder.username("admin").password("bling182").roles("ADMIN").build());

        return manager;
    }

    @Configuration
    @Order(1)
    public static class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private JwtAuthorizationFilter authorizationFilter;


        protected void configure(HttpSecurity http) throws Exception{

            http.
                antMatcher("/api/**").
                    sessionManagement().
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                    and().
                        cors().
                            disable().
                        csrf().
                            disable().
                        formLogin().
                            disable().
                        logout().
                            disable().
                        httpBasic().
                            disable().
                    authorizeRequests().
                        antMatchers(HttpMethod.GET).permitAll().
                        antMatchers(HttpMethod.POST).hasRole("ADMIN").
                        antMatchers(HttpMethod.PUT).hasRole("ADMIN").
                        antMatchers(HttpMethod.PATCH).hasRole("ADMIN").
                        antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                    .and().
                        addFilterAfter(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Configuration
    @Order(2)
    public static class ApiAuthConfig extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {

            http.
                antMatcher("/auth").
                    authorizeRequests().
                    anyRequest().
                    permitAll().
                and().
                    sessionManagement().
                    sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
                    cors().
                        disable().
                    csrf().
                        disable().
                    formLogin().
                        disable().
                    logout().
                        disable().
                    httpBasic().
                        disable();
        }
    }

    @Configuration
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.
                cors(withDefaults()).
                csrf(withDefaults()).
                headers(withDefaults()).
                authorizeRequests().
                    antMatchers("/web/admin").hasRole("ADMIN").
                and().
                    formLogin().
                and().
                    logout().
                and().
                authorizeRequests().
                    antMatchers("/web").permitAll();
        }
    }

}
