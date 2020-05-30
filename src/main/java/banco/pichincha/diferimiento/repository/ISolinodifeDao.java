package banco.pichincha.diferimiento.repository;


import banco.pichincha.diferimiento.dto.DifSolinodife;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ISolinodifeDao extends JpaRepository<DifSolinodife, Integer> {

    @Query(value = "SELECT * FROM dif_solinodife d WHERE d.clie_id=:clieId AND d.sond_bacaid=:bacaId", nativeQuery = true)
    public abstract DifSolinodife findSolinoDifeByClient(@Param("clieId") Integer clieId, @Param("bacaId") Integer bacaId);


}
