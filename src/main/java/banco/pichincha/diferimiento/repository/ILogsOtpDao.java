package banco.pichincha.diferimiento.repository;

import banco.pichincha.diferimiento.dto.DifCliente;
import banco.pichincha.diferimiento.dto.DifLogsOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILogsOtpDao extends JpaRepository<DifLogsOtp,Integer> {

    @Query(value = "SELECT * FROM dif_logsotp l WHERE l.clie_id=:idcliente", nativeQuery = true)
    public abstract DifLogsOtp findByLogsCliente(@Param("idcliente") Integer idcliente);
}
