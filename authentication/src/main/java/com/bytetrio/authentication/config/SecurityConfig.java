package com.bytetrio.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private TokenFilter filter;
    @Autowired
    private void setFilter(TokenFilter filter) {this.filter = filter;}

    @Bean
    public BCryptPasswordEncoder encoder() {return new BCryptPasswordEncoder(12);}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Throwable {
        return security.csrf(c -> c.disable())
            .authorizeHttpRequests(
                a -> a.requestMatchers("/user/sign-up", "/user/sign-in")
                .permitAll().requestMatchers("/user/sign-up", "/user/sign-in")
                .hasAnyRole("MEMBER", "USER", "DELIVERY").anyRequest().authenticated()
            ).httpBasic(Customizer.withDefaults())
            .sessionManagement(
                s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ).addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {return new CustomAuthenticationProvider(encoder());}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
