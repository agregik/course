package ru.example.dormitorysystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.example.dormitorysystem.model.User;
import ru.example.dormitorysystem.repository.UserRepository;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/login", "/register", "/css/**").permitAll()
                            .requestMatchers("/student/**").hasAnyRole("STUDENT")
                            .requestMatchers("/accommodations/**").hasAnyRole("ADMIN", "COMMANDANT")
                            .requestMatchers("/rooms/**").hasAnyRole("ADMIN", "COMMANDANT")
                            .requestMatchers("/students/**").hasAnyRole("ADMIN")
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form
                            .loginPage("/login")
                            .successHandler(authenticationSuccessHandler())
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout")
                            .permitAll()
                    );
            return http.build();
        }

        @Bean
        public AuthenticationSuccessHandler authenticationSuccessHandler() {
            return (request, response, authentication) -> {
                String role = authentication.getAuthorities().iterator().next().getAuthority();
                if ("ROLE_STUDENT".equals(role)) {
                    response.sendRedirect("/student/dashboard");
                } else {
                    response.sendRedirect("/rooms");
                }
            };
        }

        @Bean
        public UserDetailsService userDetailsService(UserRepository userRepository) {
            return username -> {
                User user = userRepository.findByUsername(username);
                if (user == null) throw new UsernameNotFoundException("User not found: " + username);
                return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                );
            };
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

}