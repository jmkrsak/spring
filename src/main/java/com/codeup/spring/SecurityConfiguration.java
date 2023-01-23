package com.codeup.spring;

import com.codeup.spring.services.UserDetailsLoader;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailsLoader usersLoader;
    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    // This method allows us to configure settings for different URL paths and who can access them.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /* Login configuration */
//                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/posts/show") // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
                /* Logout configuration */
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout") // append a query string value
                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/login", "/users/sign-up", "/posts/show") // anyone can see the home and the ads pages
                .permitAll()
                /* Pages that require authentication */
                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/posts/**"// only authenticated users can edit ads
                )
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        ;
        return http.build();
    }
}
