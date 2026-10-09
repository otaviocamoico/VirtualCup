package interdisciplinar.com.interdisciplinar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import interdisciplinar.com.interdisciplinar.enums.Role;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idUsuario;

    @Column(nullable = false, length = 40)
    private String nomeUsuario;

    @Column(nullable = false, length = 11)
    private String telUsuario;

    @Column(nullable = false, length = 254)
    private String emailUsuario;

    @Column(nullable = false, length = 10)
    private String senhaUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}

    