package com.lfr.app.boot.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/home", "/").permitAll()
				.antMatchers("/home/**", "/").permitAll()
				.antMatchers("/resources/**").permitAll() 
                .antMatchers("/css/**").permitAll() 
                .antMatchers("/js/**").permitAll()
                .antMatchers("/img/**").permitAll() 
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
		
	    // add this line to use H2 web console
//	    http.headers().frameOptions().disable();
	    
	    http.csrf().disable();
	    http.headers().frameOptions().disable();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("artur")
				.password("1234")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
}