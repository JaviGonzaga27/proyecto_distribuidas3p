package microservicio_inventario.repository;


import microservicio_inventario.entity.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, String> {
    List<MovimientoInventario> findByCosechaId(String cosechaId);
    List<MovimientoInventario> findByInsumoId(String insumoId);
}