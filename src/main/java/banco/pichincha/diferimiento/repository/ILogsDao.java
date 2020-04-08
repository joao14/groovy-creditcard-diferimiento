package banco.pichincha.diferimiento.repository;

import banco.pichincha.diferimiento.dto.DifCliente;
import banco.pichincha.diferimiento.dto.DifLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILogsDao extends JpaRepository<DifLogs,Integer> {

    @Query(value = "SELECT * FROM dif_logs l WHERE l.log_identificacion=:identificacion", nativeQuery = true)
    public abstract DifLogs findByNoCliente(@Param("identificacion") String identificacion);
}
