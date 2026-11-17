package interdisciplinar.com.interdisciplinar.entity;
 
import java.time.LocalDateTime;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPartida;
 
    @Column(nullable = false)
    private LocalDateTime dhPartida;
 
    @Column(nullable = false, length = 30)
    private String fasePartida;
 
    @Column(nullable = false)
    private Integer golsaPartida;
 
    @Column(nullable = false)
    private Integer golsbPartida;
 
    @ManyToOne
    @JoinColumn(name = "idEstadio_fk", nullable = false)
    private Estadio estadio;
 
    @ManyToOne
    @JoinColumn(name = "idSelecaoA_fk", nullable = false)
    private Selecao selecaoA;
 
    @ManyToOne
    @JoinColumn(name = "idSelecaoB_fk", nullable = false)
    private Selecao selecaoB;
}