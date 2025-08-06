package microservicio_central.repository;

import microservicio_central.entity.Agricultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AgricultorRepository extends JpaRepository<Agricultor, String> {
    Optional<Agricultor> findByEmail(String email);
    boolean existsByEmail(String email);
}
