package com.example.project_economic.config;

import com.example.project_economic.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/api/users/index","/webjars/**").permitAll()
                .requestMatchers("/api/users/login","/api/users/register","/webjars/**", "/css/**", "/js.js/**", "/images/**","/icomoon/**","/lib/**")
                .permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/api/users/login")
                .loginProcessingUrl("/api/users/home")
                .failureUrl("/api/users/fail")
                .defaultSuccessUrl("/api/users/homepage",true)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                .logoutSuccessUrl("/api/users/login")
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserInfoDetailService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
