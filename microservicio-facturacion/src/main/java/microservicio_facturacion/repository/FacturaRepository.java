package microservicio_facturacion.repository;

import microservicio_facturacion.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, String> {
    Optional<Factura> findByCosechaId(String cosechaId);
    List<Factura> findByPagado(Boolean pagado);

    @Query("SELECT COUNT(f) FROM Factura f WHERE YEAR(f.fechaCreacion) = :year")
    long countByFechaCreacionYear(int year);
}