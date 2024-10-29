package tech.saintbassanaga.sysges.config;

/**
 * Created by saintbassanaga {stpaul}
 * In the Project SysGes at Tue - 10/29/24
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class FilterChain {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF for development purposes
        http.csrf(AbstractHttpConfigurer::disable);

        // Permit all requests without authentication
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}

