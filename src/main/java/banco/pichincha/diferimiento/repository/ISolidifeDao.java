package banco.pichincha.diferimiento.repository;

import banco.pichincha.diferimiento.dto.DifCliente;
import banco.pichincha.diferimiento.dto.DifSolidife;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ISolidifeDao extends JpaRepository<DifSolidife,Integer> {

    @Query(value = "SELECT * FROM dif_solidife d WHERE d.clie_id=:clieId", nativeQuery = true)
    public abstract DifSolidife findSoliDifeByClient(@Param("clieId") Integer clieId);

    @Query(value = "SELECT * FROM dif_solidife d WHERE d.clie_id=:clieId", nativeQuery = true)
    public abstract DifSolidife findSoliDifeByClientTarjetas(@Param("clieId") Integer clieId);

    @Modifying
    @Transactional
    @Query(value="UPDATE dif_solidife d set d.sodi_estado = 1  WHERE d.sodi_hashiden=:hash", nativeQuery = true)
    public void changeStateGestionCmbOut(@Param("hash") String hash);

    @Modifying
    @Transactional
    @Query(value="UPDATE dif_solidife d set d.sodi_estado = 0, d.sodi_otp=:sodiOtp WHERE d.clie_id=:clieId", nativeQuery = true)
    public void changeStateGestionOtp(@Param("sodiOtp") String sodiOtp,@Param("clieId") Integer clieId);

}
