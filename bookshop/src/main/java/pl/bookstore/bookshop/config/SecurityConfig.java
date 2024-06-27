package pl.bookstore.bookshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static pl.bookstore.bookshop.config.AuthorityList.adminAuthority;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .userDetailsService(userDetailsService)
                .csrf(CsrfConfigurer::disable)
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(new CustomAccessDeniedHandler()))
                .authorizeHttpRequests(customizer -> customizer

                        .requestMatchers(HttpMethod.GET, "/book").permitAll()
                        .requestMatchers(HttpMethod.POST, "/books").hasAnyAuthority(adminAuthority)
                        .requestMatchers(HttpMethod.GET , "/books/{bookId}").permitAll()
                        .requestMatchers(HttpMethod.PUT , "/books/{bookId}").hasAnyAuthority(adminAuthority)
                        .requestMatchers(HttpMethod.DELETE , "/books/{bookId}").hasAnyAuthority(adminAuthority)
                        .requestMatchers(HttpMethod.GET , "/books/filter").permitAll()
                        .requestMatchers(HttpMethod.GET , "/orders").authenticated()
                        .requestMatchers(HttpMethod.POST , "/orders").authenticated()
                        .requestMatchers(HttpMethod.POST, "/order-report").hasAnyAuthority(adminAuthority)
                        .requestMatchers(HttpMethod.GET, "/order-report/print").hasAnyAuthority(adminAuthority)
                )

                .formLogin(customizer -> customizer
                        .loginProcessingUrl("/login")
                        .permitAll())

                .logout(customizer -> customizer
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true))
                .build();
    }
}
