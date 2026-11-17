package interdisciplinar.com.interdisciplinar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Selecao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idSelecao;

    @Column(nullable = false, length = 40)
    private String nomeSelecao;
    
    @Column(nullable = false, length = 254)
    private String paisSelecao;

}
