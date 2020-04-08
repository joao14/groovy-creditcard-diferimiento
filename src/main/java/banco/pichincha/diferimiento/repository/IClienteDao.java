package banco.pichincha.diferimiento.repository;

import banco.pichincha.diferimiento.dto.DifCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteDao extends JpaRepository<DifCliente,Integer> {

    @Query(value = "SELECT * FROM dif_cliente d WHERE d.clie_identificacion=:identificacion limit 1", nativeQuery = true)
    public abstract DifCliente findByCliente(@Param("identificacion") String identificacion);

    @Query(value = "SELECT * FROM dif_cliente d WHERE d.clie_id=:idcliente", nativeQuery = true)
    public abstract DifCliente findByIdCliente(@Param("idcliente") String idcliente);

    @Query(value = "SELECT * FROM dif_cliente d WHERE d.clie_hashiden=:hashiden", nativeQuery = true)
    public abstract DifCliente findByHashCliente(@Param("hashiden") String hashiden);

}
