package interdisciplinar.com.interdisciplinar.service;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import interdisciplinar.com.interdisciplinar.entity.Compra;
import interdisciplinar.com.interdisciplinar.repository.CompraRepository;
 
@Service
public class CompraService {
 
    @Autowired
    private CompraRepository compraRepository;
 
    public Compra save(Compra compra){
    return compraRepository.save(compra);
    }
 
    public List<Compra> findAll(){
        return compraRepository.findAll();
    }
   
}