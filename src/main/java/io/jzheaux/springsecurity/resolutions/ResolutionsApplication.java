package io.jzheaux.springsecurity.resolutions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@SpringBootApplication
public class ResolutionsApplication extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(UserRepository users) {
        return new UserRepositoryUserDetailsService(users);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authz -> authz
                .mvcMatchers(GET, "/resolutions", "/resolution/**").hasAnyAuthority("resolution:read")
                .anyRequest().hasAnyAuthority("resolution:write"))
            .httpBasic(basic -> {});

    }

    public static void main(String[] args) {
		SpringApplication.run(ResolutionsApplication.class, args);
	}

}
