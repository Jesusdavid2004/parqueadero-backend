package com.parqueadero.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.parqueadero.model.Usuario;
import com.parqueadero.repository.UsuarioRepository;

@Configuration
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository;

    public SecurityConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            return new User(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
            );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        String frontendUrl = System.getenv("FRONTEND_URL");

        configuration.setAllowedOrigins(List.of(
                "http://localhost:4200",
                "http://localhost:3000",
                "http://localhost:5173",
                "http://localhost:5174",
                "http://127.0.0.1:4200",
                "http://127.0.0.1:3000",
                "http://127.0.0.1:5173",
                "https://parqueadero-frontend-pi.vercel.app",
                "https://parqueadero-frontend-hfzoxq1ly-jesusdavid2004s-projects.vercel.app",
                frontendUrl != null ? frontendUrl : "http://localhost:4200"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                .requestMatchers("/api/debug/**").permitAll()
                .requestMatchers("/api/auth/me").authenticated()
                .requestMatchers("/api/clientes/**").hasRole("ADMIN")
                .requestMatchers("/api/pagos/**").hasRole("ADMIN")
                .requestMatchers("/api/facturas/**").hasRole("ADMIN")
                .requestMatchers("/api/menus/**").hasRole("ADMIN")
                .requestMatchers("/api/sedes/**").hasRole("ADMIN")
                .requestMatchers("/api/zonas/**").hasRole("ADMIN")
                .requestMatchers("/api/tarifas/**").hasRole("ADMIN")
                .requestMatchers("/api/empleados/**").hasRole("ADMIN")
                .requestMatchers("/api/cliente/**").hasAnyRole("CLIENTE", "ADMIN")
                .requestMatchers("/api/vehiculos/**").authenticated()
                .requestMatchers("/api/tickets/**").authenticated()
                .requestMatchers("/api/reservas/**").authenticated()
                .requestMatchers("/api/espacios/**").authenticated()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}