package interdisciplinar.com.interdisciplinar.entity;
 
import java.math.BigDecimal;
import java.time.LocalDateTime;
 
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
public class Compra {
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idCompra;
 
    @Column(nullable = false)
    private LocalDateTime dhCompra;
 
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal vtCompra;
 
    @Column(nullable = false, length = 20)
    private String statusCompra;
 
    @Column(nullable = false, length = 30)
    private String metodoCompra;
}