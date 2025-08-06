package microservicio_central.repository;

import microservicio_central.entity.Cosecha;
import microservicio_central.entity.EstadoCosecha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CosechaRepository extends JpaRepository<Cosecha, String> {
    List<Cosecha> findByAgricultorId(String agricultorId);
    List<Cosecha> findByEstado(EstadoCosecha estado);

    @Query("SELECT c FROM Cosecha c WHERE c.agricultorId = ?1 ORDER BY c.fechaRegistro DESC")
    List<Cosecha> findByAgricultorIdOrderByFechaDesc(String agricultorId);
}
