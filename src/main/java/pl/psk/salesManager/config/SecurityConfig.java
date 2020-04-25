package pl.psk.salesManager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final String username;
    private final String password;

    public SecurityConfig(@Value("${security.username}") String username, @Value("${security.password}") String password) {
        this.username = username;
        this.password = password;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager
                = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withDefaultPasswordEncoder()
                .username(username)
                .password(password)
                .roles("ADMIN")
                .build());
        return manager;
    }
}