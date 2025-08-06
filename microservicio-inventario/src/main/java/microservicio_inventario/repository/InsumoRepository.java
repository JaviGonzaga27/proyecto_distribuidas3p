package microservicio_inventario.repository;


import microservicio_inventario.entity.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, String> {
    Optional<Insumo> findByNombreInsumo(String nombreInsumo);

    @Modifying
    @Transactional
    @Query("UPDATE Insumo i SET i.stock = i.stock - :cantidad WHERE i.nombreInsumo = :nombreInsumo AND i.stock >= :cantidad")
    int descontarStock(String nombreInsumo, Double cantidad);

    @Query("SELECT i FROM Insumo i WHERE i.stock < 10")
    List<Insumo> findInsumosConStockBajo();
}

