package interdisciplinar.com.interdisciplinar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Estadio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idEstadio;

    @Column(nullable = false, length = 40)
    private String nomeEstadio;
    
    @Column(nullable = false, length = 100)
    private String paisEstadio;

    @Column(nullable = false, length = 100)
    private String cidadeEstadio;

    @Column(nullable = false, length = 100)
    private Integer capacidadeEstadio;

    

}

