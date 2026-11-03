package interdisciplinar.com.interdisciplinar.entity;
 
import java.math.BigDecimal;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
 
@Entity
public class Ingresso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idIngresso;
 
    @Column(nullable = false, unique = true, length = 50)
    private String codigoIngresso;
 
    @Column(nullable = false, length = 30)
    private String setorIngresso;
 
    @Column(nullable = false, length = 20)
    private String assentoIngresso;
 
    @Column(nullable = false, length = 30)
    private String categoriaIngresso;
 
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoIngresso;
 
    @Column(nullable = false, length = 20)
    private String statusIngresso;
 
    @ManyToOne
    @JoinColumn(name = "idPartida_fk", nullable = false)
    private Partida partida;
 
    @ManyToOne
    @JoinColumn(name = "idCompra_fk", nullable = false)
    private Compra compra;
}