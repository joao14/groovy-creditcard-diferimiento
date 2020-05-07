package banco.pichincha.diferimiento.repository;

import banco.pichincha.diferimiento.dto.DifCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IClienteDao extends JpaRepository<DifCliente, Integer> {

    @Query(value = "SELECT * FROM dif_cliente d WHERE d.clie_identificacion=:identificacion", nativeQuery = true)
    public abstract List<DifCliente> findByCliente(@Param("identificacion") String identificacion);

    @Query(value = "select * from dif_cliente where clie_id !=:clieId and (clie_montcapitotal < 18000 or clie_montcapivencer < 18000 ) and clie_identificacion=:identificacion", nativeQuery = true)
    public abstract List<DifCliente> findByCreditosClienteMenor18000(@Param("identificacion") String identificacion, @Param("clieId") Integer clieId);

    @Query(value = "select * from dif_cliente where clie_id !=:clieId and (clie_montcapitotal > 18000 or clie_montcapivencer > 18000 ) and clie_identificacion=:identificacion", nativeQuery = true)
    public abstract List<DifCliente> findByCreditosClienteMayores18000(@Param("identificacion") String identificacion, @Param("clieId") Integer clieId);

    @Query(value = "SELECT * FROM dif_cliente d WHERE d.clie_identificacion=:identificacion AND d.baca_id=:bacaId limit 1", nativeQuery = true)
    public abstract DifCliente findByTarjetaCliente(@Param("identificacion") String identificacion, @Param("bacaId") Integer bacaId);

    @Query(value = "SELECT * FROM dif_cliente d WHERE d.clie_id=:idcliente", nativeQuery = true)
    public abstract DifCliente findByIdCliente(@Param("idcliente") String idcliente);

    @Query(value = "SELECT * FROM dif_cliente d WHERE d.clie_hashiden=:hashiden", nativeQuery = true)
    public abstract DifCliente findByHashCliente(@Param("hashiden") String hashiden);

    @Modifying
    @Transactional
    @Query(value = "UPDATE dif_cliente d set d.clie_celularform=:celularform , d.clie_emailform=:emailform  WHERE d.clie_identificacion=:identificacion", nativeQuery = true)
    public void changeValuesEmailPhoneOfClient(@Param("celularform") String celularform, @Param("emailform") String emailform, @Param("identificacion") String identificacion);

    /**
     * PROCEDIMIENTOS ALMACENADOS
     */
    @Procedure(name = "bp_gestion_operaciones_habitar_preciso_autoseguro", outputParameterName = "tipo")
    public abstract String getTypeRelacionPrecisoHabitarAutoseguro(@Param("identificacion") String identificacion);


}
