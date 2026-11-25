package interdisciplinar.com.interdisciplinar.security;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import interdisciplinar.com.interdisciplinar.entity.Usuario;
import interdisciplinar.com.interdisciplinar.enums.Role;
import interdisciplinar.com.interdisciplinar.repository.UsuarioRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    private final String nome;
    private final String email;
    private final String senha;

    public AdminInitializer(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            @Value("${virtualcup.admin.nome}") String nome,
            @Value("${virtualcup.admin.email}") String email,
            @Value("${virtualcup.admin.senha}") String senha) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;

        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public void run(String... args) {

        String emailNormalizado = email.trim()
                .toLowerCase(Locale.ROOT);

        // Verifica se o administrador já existe
        usuarioRepository.findByEmailUsuario(emailNormalizado)
            .ifPresentOrElse(usuario -> {

                // Promove a conta configurada, caso ainda não seja ADMIN
                if (usuario.getRole() != Role.ADMIN) {

                    usuario.setRole(Role.ADMIN);

                    usuarioRepository.save(usuario);

                    System.out.println(
                        "Conta administrativa promovida com sucesso!"
                    );

                } else {

                    System.out.println(
                        "Administrador inicial já está cadastrado."
                    );

                }

            }, () -> {

                // Criar novo administrador
                Usuario admin = new Usuario();

                admin.setNomeUsuario(nome);
                admin.setEmailUsuario(emailNormalizado);

                admin.setSenhaUsuario(
                    passwordEncoder.encode(senha)
                );

                admin.setRole(Role.ADMIN);

                usuarioRepository.save(admin);

                System.out.println(
                    "Administrador inicial criado com sucesso!"
                );

            });
    }
}
