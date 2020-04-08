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
@Table(name = "dif_solidife")
@XmlRootElement
public class DifSolidife implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sodi_id")
    private Integer sodiId;
    @Column(name = "sodi_hashiden")
    private String sodiHashiden;
    @Column(name = "sodi_otp")
    private String sodiOtp;
    @Basic(optional = false)
    @Column(name = "sodi_bacaid")
    private Integer sodiBacaid;
    @Basic(optional = false)
    @Column(name = "sodi_estado")
    private String sodiEstado;
    @Generated(GenerationTime.INSERT)
    @Basic(optional = false)
    @Column(name = "sodi_fechahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sodiFechahora;
    @JoinColumn(name = "clie_id", referencedColumnName = "clie_id")
    @ManyToOne
    private DifCliente clieId;

    public DifSolidife() {
    }

    public DifSolidife(String sodiHashiden, String sodiOtp, Integer sodiBacaid, Date sodiFechahora, DifCliente clieId) {
        this.sodiHashiden = sodiHashiden;
        this.sodiOtp = sodiOtp;
        this.sodiBacaid = sodiBacaid;
        this.sodiFechahora = sodiFechahora;
        this.clieId = clieId;
    }

    public Integer getSodiId() {
        return sodiId;
    }

    public void setSodiId(Integer sodiId) {
        this.sodiId = sodiId;
    }

    public String getSodiHashiden() {
        return sodiHashiden;
    }

    public void setSodiHashiden(String sodiHashiden) {
        this.sodiHashiden = sodiHashiden;
    }

    public String getSodiOtp() {
        return sodiOtp;
    }

    public void setSodiOtp(String sodiOtp) {
        this.sodiOtp = sodiOtp;
    }

    public Integer getSodiBacaid() {
        return sodiBacaid;
    }

    public void setSodiBacaid(Integer sodiBacaid) {
        this.sodiBacaid = sodiBacaid;
    }

    public String getSodiEstado() {
        return sodiEstado;
    }

    public void setSodiEstado(String sodiEstado) {
        this.sodiEstado = sodiEstado;
    }

    public Date getSodiFechahora() {
        return sodiFechahora;
    }

    public void setSodiFechahora(Date sodiFechahora) {
        this.sodiFechahora = sodiFechahora;
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
        hash += (sodiId != null ? sodiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DifSolidife)) {
            return false;
        }
        DifSolidife other = (DifSolidife) object;
        if ((this.sodiId == null && other.sodiId != null) || (this.sodiId != null && !this.sodiId.equals(other.sodiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DifSolidife[ soinId=" + sodiId + " ]";
    }

}
