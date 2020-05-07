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
@Table(name = "dif_logs")
@XmlRootElement
public class DifLogs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "log_id")
    private Integer logId;
    @Basic(optional = false)
    @Column(name = "log_identificacion")
    private String logIdentificacion;
    @Basic(optional = false)
    @Column(name = "log_nombres")
    private String logNombres;
    @Basic(optional = false)
    @Column(name = "log_apellidos")
    private String logApellidos;
    @Basic(optional = false)
    @Column(name = "log_telefono")
    private String logTelefono;
    @Basic(optional = false)
    @Column(name = "log_email")
    private String logEmail;
    @Basic(optional = false)
    @Column(name = "log_ip")
    private String logIp;
    @Basic(optional = false)
    @Column(name = "log_tipo")
    private String logTipo;
    @Generated(GenerationTime.INSERT)
    @Basic(optional = false)
    @Column(name = "log_fechahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logFechahora;

    public DifLogs() {
    }

    public DifLogs(Integer logId) {
        this.logId = logId;
    }

    public DifLogs(String logIdentificacion, String logNombres, String logApellidos, String logTelefono, String logEmail, String logIp) {
        this.logIdentificacion = logIdentificacion;
        this.logNombres = logNombres;
        this.logApellidos = logApellidos;
        this.logTelefono = logTelefono;
        this.logEmail = logEmail;
        this.logIp = logIp;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getLogIdentificacion() {
        return logIdentificacion;
    }

    public void setLogIdentificacion(String logIdentificacion) {
        this.logIdentificacion = logIdentificacion;
    }

    public String getLogNombres() {
        return logNombres;
    }

    public void setLogNombres(String logNombres) {
        this.logNombres = logNombres;
    }

    public String getLogApellidos() {
        return logApellidos;
    }

    public void setLogApellidos(String logApellidos) {
        this.logApellidos = logApellidos;
    }

    public String getLogTelefono() {
        return logTelefono;
    }

    public void setLogTelefono(String logTelefono) {
        this.logTelefono = logTelefono;
    }

    public String getLogEmail() {
        return logEmail;
    }

    public void setLogEmail(String logEmail) {
        this.logEmail = logEmail;
    }

    public String getLogIp() {
        return logIp;
    }

    public void setLogIp(String logIp) {
        this.logIp = logIp;
    }

    public Date getLogFechahora() {
        return logFechahora;
    }

    public void setLogFechahora(Date logFechahora) {
        this.logFechahora = logFechahora;
    }

    public String getLogTipo() {return logTipo;}

    public void setLogTipo(String logTipo) {
        this.logTipo = logTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logId != null ? logId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DifLogs)) {
            return false;
        }
        DifLogs other = (DifLogs) object;
        if ((this.logId == null && other.logId != null) || (this.logId != null && !this.logId.equals(other.logId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.InLogs[ logId=" + logId + " ]";
    }

}