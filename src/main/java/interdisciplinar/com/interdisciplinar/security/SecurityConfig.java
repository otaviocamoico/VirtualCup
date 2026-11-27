
package interdisciplinar.com.interdisciplinar.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth

            // Páginas e recursos públicos
            .requestMatchers(
                HttpMethod.GET,
                "/",
                "/login",
                "/usuarios/criar",
                "/css/**",
                "/js/**",
                "/img/**",
                "/copas/**",
                "/partidas/**",
                "/virtualcup"
            ).permitAll()

            // Cadastro público de usuários
            .requestMatchers(
                HttpMethod.POST,
                "/usuarios/salvar"
            ).permitAll()

            // Gerenciamento de usuários: somente ADMIN
            .requestMatchers(
                "/usuarios/listar",
                "/usuarios/*/promover",
                "/usuarios/*/rebaixar"
            ).hasRole("ADMIN")

            // Gerenciamento de estádios: somente ADMIN
            .requestMatchers(
                "/estadios/**"
            ).hasRole("ADMIN")

            // Demais áreas administrativas
            .requestMatchers(
                "/admin/**",
                "/virtualcup/admin/**"
            ).hasRole("ADMIN")

            // Área do usuário comum
            .requestMatchers(
                "/virtualcup/cliente/**"
            ).hasRole("USER")

            // Funcionalidades que exigem autenticação
            .requestMatchers(
                "/compras/**",
                "/perfil/**"
            ).authenticated()

            // Demais rotas exigem autenticação
            .anyRequest().authenticated()
        )

        // Configuração de login
        .formLogin(form -> form

            .loginPage("/login")
            .usernameParameter("email")
            .passwordParameter("senha")

            .successHandler((request, response, authentication) -> {

                boolean admin = authentication.getAuthorities().stream()
                    .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN")
                    );

                if (admin) {

                    response.sendRedirect(
                        request.getContextPath() + "/virtualcup/admin/home"
                    );

                } else {

                    response.sendRedirect(
                        request.getContextPath() + "/virtualcup/cliente/home"
                    );

                }
            })

            .failureUrl("/login?erro")
            .permitAll()
        )

        // Configuração de logout
        .logout(logout -> logout
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        );

        return http.build();
    }

    // Criptografia das senhas
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
