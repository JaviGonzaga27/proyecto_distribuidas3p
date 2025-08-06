package microservicio_facturacion.repository;

import microservicio_facturacion.entity.PrecioProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PrecioProductoRepository extends JpaRepository<PrecioProducto, String> {
    Optional<PrecioProducto> findByProductoAndActivo(String producto, Boolean activo);
}