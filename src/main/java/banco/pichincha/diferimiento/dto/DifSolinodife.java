package banco.pichincha.diferimiento.dto;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gamerino
 */
@Entity
@Table(name = "dif_solinodife")
@XmlRootElement
public class DifSolinodife implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sond_id")
    private Integer sondId;
    @Column(name = "sond_hash")
    private String sondHash;
    @Basic(optional = false)
    @Column(name = "sond_bacaid")
    private Integer sondBacaid;
    @Generated(GenerationTime.INSERT)
    @Basic(optional = false)
    @Column(name = "sond_fechahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sondFechahora;
    @JoinColumn(name = "clie_id", referencedColumnName = "clie_id")
    @ManyToOne
    private DifCliente clieId;

    public DifSolinodife() {
    }

    public DifSolinodife(String sondHash, Integer sondBacaid, DifCliente clieId) {
        this.sondHash = sondHash;
        this.sondBacaid = sondBacaid;
        this.clieId = clieId;
    }

    public String getSondHash() {
        return sondHash;
    }

    public void setSondHash(String sondHash) {
        this.sondHash = sondHash;
    }

    public Integer getSondBacaid() {
        return sondBacaid;
    }

    public void setSondBacaid(Integer sondBacaid) {
        this.sondBacaid = sondBacaid;
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
        hash += (sondId != null ? sondId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DifSolinodife)) {
            return false;
        }
        DifSolinodife other = (DifSolinodife) object;
        if ((this.sondId == null && other.sondId != null) || (this.sondId != null && !this.sondId.equals(other.sondId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DifSolinodife[ sondId=" + sondId + " ]";
    }

}
