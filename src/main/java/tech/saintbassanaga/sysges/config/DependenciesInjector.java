package tech.saintbassanaga.sysges.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by saintbassanaga {stpaul}
 * In the Project SysGes at Mon - 11/18/24
 */

@Configuration
@AllArgsConstructor
public class DependenciesInjector {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCryptPasswordEncoder for hashing passwords
    }

}

