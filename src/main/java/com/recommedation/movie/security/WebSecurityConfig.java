package com.recommedation.movie.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl detailsServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/movies-recommendation/**").permitAll()
            .antMatchers(HttpMethod.GET, "/movies/rate/recommend").permitAll()
            .antMatchers(HttpMethod.GET, "/movies/user").permitAll()
            .antMatchers(HttpMethod.POST, "/movies/user").permitAll()
            .anyRequest().authenticated()
            .and().formLogin().loginPage("/movies-recommendation/login")
                .defaultSuccessUrl("/movies-recommendation/home").permitAll()
            .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/movies-recommendation/logout"))
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(detailsServiceImpl)
            .passwordEncoder(new BCryptPasswordEncoder())
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**");
    }
}
