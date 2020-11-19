package ar.edu.iua.model.persistence;

import ar.edu.iua.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE orden o SET o.caudal = ?2, o.densidad = ?3, o.temp = ?4, o.masa_acum = ?5, o.fecha_ultimo_almacenamiento = ?6 WHERE o.id = ?1", nativeQuery = true)
    void updateOrdenSurtidorConFecha(long idOrden, double caudal , double densidad, double temp, double masaAcum, Date fechaUltimoAlmacenamiento);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE orden o SET o.caudal = ?2, o.densidad = ?3, o.temp = ?4, o.masa_acum = ?5 WHERE o.id = ?1", nativeQuery = true)
    void updateOrdenSurtidor(long idOrden, double caudal , double densidad, double temp, double masaAcum);

    @Query(value = "select MAX(id) from orden", nativeQuery = true)
    String getUltimoIdOrden();

    Orden findByCodigoExterno(String orden);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE orden p SET p.pesaje_inicial = ?2, p.fecha_pesaje_inicial = ?3, p.estado = ?4, p.psw = ?5 WHERE p.codigo_externo = ?1", nativeQuery = true)
    void updatePesajeInicial(String idOrden, double peso , Date fechaPesaje, int estado, String psw);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE orden o SET o.estado = 3 WHERE o.id = ?1", nativeQuery = true)
    void cerrarOrden(long idOrden);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE orden o SET o.estado = 3 WHERE o.codigo_externo = ?1", nativeQuery = true)
    void cerrarOrdenPorCodigoExterno(String codigoExterno);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE orden p SET p.pesaje_final = ?2, p.fecha_pesaje_final = ?3, p.estado = 4 WHERE p.codigo_externo = ?1", nativeQuery = true)
    void updatePesajeFinal(String idOrden, double peso , Date fechaPesaje);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE orden o SET o.conciliacion_id = ?2 WHERE o.id = ?1", nativeQuery = true)
    void updateConciliacion(long idOrden, long idConciliacion);
}
