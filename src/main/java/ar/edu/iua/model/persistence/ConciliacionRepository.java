package ar.edu.iua.model.persistence;

import ar.edu.iua.model.Conciliacion;
import ar.edu.iua.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConciliacionRepository extends JpaRepository<Conciliacion, Long> {

    Conciliacion findByCodigoExterno(String orden);
}
