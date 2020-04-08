package banco.pichincha.diferimiento.dto;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author gamerino
 */
@Entity
@Table(name = "dif_logsotp")
@XmlRootElement
public class DifLogsOtp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "loot_id")
    private Integer lootId;
    @Column(name = "loot_operacion")
    private String lootOperacion;
    @Column(name = "loot_ip")
    private String lootIp;
    @Basic(optional = false)
    @Column(name = "loot_errorotp")
    private String lootErrorotp;
    @Basic(optional = false)
    @Column(name = "loot_intentos")
    private String lootIntentos;
    @Generated(GenerationTime.INSERT)
    @Basic(optional = false)
    @Column(name = "loot_fechahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sodiFechahora;
    @JoinColumn(name = "clie_id", referencedColumnName = "clie_id")
    @ManyToOne
    private DifCliente clieId;

    public DifLogsOtp() {
    }

    public DifLogsOtp(String lootOperacion, String lootErrorotp, String lootIntentos, Date sodiFechahora, DifCliente clieId) {
        this.lootOperacion = lootOperacion;
        this.lootErrorotp = lootErrorotp;
        this.lootIntentos = lootIntentos;
        this.sodiFechahora = sodiFechahora;
        this.clieId = clieId;
    }

    public String getLootOperacion() {
        return lootOperacion;
    }

    public void setLootOperacion(String lootOperacion) {
        this.lootOperacion = lootOperacion;
    }

    public String getLootIp() {
        return lootIp;
    }

    public void setLootIp(String lootIp) {
        this.lootIp = lootIp;
    }

    public String getLootErrorotp() {
        return lootErrorotp;
    }

    public void setLootErrorotp(String lootErrorotp) {
        this.lootErrorotp = lootErrorotp;
    }

    public String getLootIntentos() {
        return lootIntentos;
    }

    public void setLootIntentos(String lootIntentos) {
        this.lootIntentos = lootIntentos;
    }

    public DifCliente getClieId() {
        return clieId;
    }

    public void setClieId(DifCliente clieId) {
        this.clieId = clieId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lootId != null ? lootId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DifLogsOtp)) {
            return false;
        }
        DifLogsOtp other = (DifLogsOtp) object;
        if ((this.lootId == null && other.lootId != null) || (this.lootId != null && !this.lootId.equals(other.lootId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DifLogsOtp[ lootId=" + lootId + " ]";
    }

}
