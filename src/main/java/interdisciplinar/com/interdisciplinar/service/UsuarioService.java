package interdisciplinar.com.interdisciplinar.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import interdisciplinar.com.interdisciplinar.entity.Usuario;
import interdisciplinar.com.interdisciplinar.enums.Role;
import interdisciplinar.com.interdisciplinar.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final String emailAdminInicial;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            @Value("${virtualcup.admin.email}") String emailAdminInicial) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;

        this.emailAdminInicial = emailAdminInicial
                .trim()
                .toLowerCase(Locale.ROOT);
    }

    // Salvar usuário
    public Usuario save(Usuario usuario) {

        // Validar nome
        if (usuario.getNomeUsuario() == null ||
                usuario.getNomeUsuario().isBlank()) {

            throw new IllegalArgumentException(
                "O nome do usuário é obrigatório."
            );
        }

        usuario.setNomeUsuario(usuario.getNomeUsuario().trim());

        // Validar e normalizar e-mail
        if (usuario.getEmailUsuario() == null ||
                usuario.getEmailUsuario().isBlank()) {

            throw new IllegalArgumentException(
                "O e-mail é obrigatório."
            );
        }

        String email = usuario.getEmailUsuario()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (email.length() > 254) {

            throw new IllegalArgumentException(
                "O e-mail deve possuir no máximo 254 caracteres."
            );
        }

        // Verificar e-mail duplicado
        if (usuarioRepository.existsByEmailUsuario(email)) {

            throw new IllegalArgumentException(
                "Este e-mail já está cadastrado."
            );
        }

        usuario.setEmailUsuario(email);

        // Validar senha
        if (usuario.getSenhaUsuario() == null ||
                usuario.getSenhaUsuario().length() < 8) {

            throw new IllegalArgumentException(
                "A senha deve possuir pelo menos 8 caracteres."
            );
        }

        // Definir perfil inicial
        usuario.setRole(Role.USER);

        // Criptografar senha
        usuario.setSenhaUsuario(
            passwordEncoder.encode(usuario.getSenhaUsuario())
        );

        return usuarioRepository.save(usuario);
    }

    // Listar usuários
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // Alterar perfil do usuário
    @Transactional
    public void alterarPerfil(
            Integer idUsuario,
            Role novoRole,
            String emailAdminAtual) {

        // Localizar usuário
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Usuário não encontrado."
                )
            );

        // Verificar se o perfil já é o solicitado
        if (usuario.getRole() == novoRole) {

            throw new IllegalArgumentException(
                "O usuário já possui este perfil."
            );
        }

        // Regras para rebaixamento
        if (novoRole == Role.USER) {

            // Proteger administrador inicial
            if (usuario.getEmailUsuario()
                    .equalsIgnoreCase(emailAdminInicial)) {

                throw new IllegalArgumentException(
                    "O administrador inicial não pode ser rebaixado."
                );
            }

            // Impedir que o administrador rebaixe a própria conta
            if (usuario.getEmailUsuario()
                    .equalsIgnoreCase(emailAdminAtual)) {

                throw new IllegalArgumentException(
                    "Você não pode rebaixar sua própria conta."
                );
            }

            // Impedir que o sistema fique sem administradores
            if (usuarioRepository.countByRole(Role.ADMIN) <= 1) {

                throw new IllegalArgumentException(
                    "Não é possível rebaixar o último administrador."
                );
            }
        }

        // Aplicar alteração
        usuario.setRole(novoRole);

        usuarioRepository.save(usuario);
    }
}
