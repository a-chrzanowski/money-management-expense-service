package pl.achrzanowski.moneymanagementexpenseservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.mvcMatcher("/api/**")
                .authorizeRequests()
                .mvcMatchers("/api/**")
                .access("hasAuthority('SCOPE_expense.read')")
                .and()
                .oauth2ResourceServer()
                .jwt();
        return httpSecurity.build();
    }

}
