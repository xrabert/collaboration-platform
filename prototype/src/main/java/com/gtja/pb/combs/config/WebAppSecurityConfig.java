package com.gtja.pb.combs.config;

import com.gtja.pb.combs.filter.webapp.SpringSecurityBasedUserAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER-15)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.requestMatchers() .antMatchers("/app/**","/api/**").and().httpBasic()
    /*antMatcher("/app/**").httpBasic()*/
      .and().authorizeRequests()
      .anyRequest().authenticated()
      .and().csrf()
      .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      .and().addFilterAfter(new SpringSecurityBasedUserAuthenticationFilter(), FilterSecurityInterceptor.class);
  }

}
