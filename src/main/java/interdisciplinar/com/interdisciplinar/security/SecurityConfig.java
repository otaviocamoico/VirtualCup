
package interdisciplinar.com.interdisciplinar.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                // Páginas públicas GET
                .requestMatchers(
                    HttpMethod.GET,
                    "/",
                    "/login",
                    "/usuarios/criar",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/copas/**",
                    "/estadios/**",
                    "/partidas/**",
                    "/virtualcup"
                ).permitAll()

                // Cadastro público POST
                .requestMatchers(
                    HttpMethod.POST,
                    "/usuarios/salvar"
                ).permitAll()

                // Listagem de usuários somente para administradores
                .requestMatchers("/usuarios/listar")
                .hasRole("ADMIN")

                // Área administrativa
                .requestMatchers(
                    "/admin/**",
                    "/virtualcup/admin/**"
                ).hasRole("ADMIN")

                // Área do usuário comum
                .requestMatchers("/virtualcup/cliente/**")
                .hasRole("USER")

                // Compras e perfil exigem autenticação
                .requestMatchers("/compras/**", "/perfil/**")
                .authenticated()

                // Demais requisições exigem autenticação
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("senha")

                // Redirecionamento conforme o papel
                .successHandler((request, response, authentication) -> {

                    boolean admin = authentication.getAuthorities()
                        .stream()
                        .anyMatch(authority ->
                            authority.getAuthority().equals("ROLE_ADMIN")
                        );

                    if (admin) {
                        response.sendRedirect(
                            request.getContextPath() +
                            "/virtualcup/admin/home"
                        );
                    } else {
                        response.sendRedirect(
                            request.getContextPath() +
                            "/virtualcup/cliente/home"
                        );
                    }
                })

                .failureUrl("/login?erro")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
