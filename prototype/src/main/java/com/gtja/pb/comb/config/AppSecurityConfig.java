package com.gtja.pb.comb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

  @Configuration
  @Order(SecurityProperties.BASIC_AUTH_ORDER)
  public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers("/*.bundle.*");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.httpBasic().and().authorizeRequests().antMatchers("/index.html", "/", "/home", "/login")
          .permitAll().anyRequest().authenticated().and().csrf()
          .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Autowired
    public void initialize(AuthenticationManagerBuilder auth) throws Exception {
      //TODO: using properties file for ldap information
      auth.ldapAuthentication().userDnPatterns("uid={0},ou=staffs").groupSearchBase("ou=it").contextSource()
          .url("ldap://localhost:8389/dc=pb,dc=gtja,dc=com")/*.and().passwordCompare()
          .passwordEncoder(new LdapShaPasswordEncoder()).passwordAttri+bute("userPassword")*/;
    }

  }
