package banco.pichincha.diferimiento.dto;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

@Entity
@Table(name = "dif_cliente")
@XmlRootElement
public class DifCliente {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clie_id")
    private Integer clieId;
    @Basic(optional = false)
    @Column(name = "clie_identificacion")
    private String clieIdentificacion;
    @Basic(optional = false)
    @Column(name = "clie_idenenma")
    private String clieIdenenma;
    @Column(name = "clie_hashiden")
    private String clieHashiden;
    @Column(name = "clie_primnomb")
    private String cliePrimnomb;
    @Column(name = "clie_segunomb")
    private String clieSegunomb;
    @Column(name = "clie_primapell")
    private String cliePrimapell;
    @Column(name = "clie_seguapell")
    private String clieSeguapell;
    @Column(name = "clie_numepres")
    private String clieNumepres;
    @Column(name = "clie_familia")
    private String clieFamilia;
    @Column(name = "clie_codiprod")
    private String clieCodiprod;
    @Column(name = "clie_diasmora")
    private Character clieDiasmora;
    @Column(name = "clie_nombsist")
    private String clieNombsist;
    @Column(name = "clie_diapago")
    private String clieDiapago;
    @Column(name = "clie_nombtipoconc")
    private String clieNombtipoconc;
    @Column(name = "clie_montcapivencer")
    private String clieMontcapivencer;
    @Column(name = "clie_montcapinodeve")
    private String clieMontcapinodeve;
    @Column(name = "clie_montcapivencido")
    private String clieMontcapivencido;
    @Column(name = "clie_montcapitotal")
    private String clieMontcapitotal;
    @Column(name = "clie_presestaactu")
    private String cliePresestaactu;
    @Column(name = "clie_numeopera")
    private String clieNumeopera;
    @Column(name = "clie_activo")
    private String clieActivo;
    @Column(name = "clie_asesor")
    private String clieAsesor;
    @Column(name = "clie_agencia")
    private String clieAgencia;
    @Column(name = "clie_nombagen")
    private String clieNombagen;
    @Column(name = "clie_zona")
    private String clieZona;
    @Column(name = "clie_region")
    private String clieRegion;
    @Column(name = "clie_segmento")
    private String clieSegmento;
    @Column(name = "clie_subsegmento")
    private String clieSubsegmento;
    @Column(name = "clie_pais")
    private String cliePais;
    @Column(name = "clie_provincia")
    private String clieProvincia;
    @Column(name = "clie_tipo")
    private String clieTipo;
    @Column(name = "clie_canal")
    private String clieCanal;
    @Column(name = "clie_celular")
    private String clieCelular;
    @Column(name = "clie_email")
    private String clieEmail;
    @Column(name = "clie_estado")
    private String clieEstado;
    @Column(name = "clie_base")
    private String clieBase;
    @JoinColumn(name = "baca_id", referencedColumnName = "baca_id")
    @ManyToOne
    private DifBasecampa bacaId;
    @OneToMany(mappedBy = "clieId")
    private Collection<DifSolidife> difSolidifeCollection;

    public DifCliente() {
    }

    public DifCliente(String clieIdentificacion, String clieIdenenma, String clieHashiden, String cliePrimnomb,String clieSegunomb, String cliePrimapell, String clieSeguapell, String clieNumepres, String clieFamilia, String clieCodiprod, Character clieDiasmora, String clieNombsist, String clieDiapago, String clieNombtipoconc, String clieMontcapivencer, String clieMontcapinodeve, String clieMontcapivencido, String clieMontcapitotal, String cliePresestaactu, String clieNumeopera, String clieActivo, String clieAsesor, String clieAgencia, String clieNombagen, String clieZona, String clieRegion, String clieSegmento, String clieSubsegmento, String cliePais, String clieProvincia, String clieTipo, String clieCanal, String clieCelular, String clieEmail, String clieEstado, DifBasecampa bacaId, String clieBase) {
        this.clieIdentificacion = clieIdentificacion;
        this.clieIdenenma = clieIdenenma;
        this.clieHashiden = clieHashiden;
        this.cliePrimnomb= cliePrimnomb;
        this.clieSegunomb= clieSegunomb;
        this.cliePrimapell= cliePrimapell;
        this.clieSeguapell= clieSeguapell;
        this.clieNumepres = clieNumepres;
        this.clieFamilia = clieFamilia;
        this.clieCodiprod = clieCodiprod;
        this.clieDiasmora = clieDiasmora;
        this.clieNombsist = clieNombsist;
        this.clieDiapago = clieDiapago;
        this.clieNombtipoconc = clieNombtipoconc;
        this.clieMontcapivencer = clieMontcapivencer;
        this.clieMontcapinodeve = clieMontcapinodeve;
        this.clieMontcapivencido = clieMontcapivencido;
        this.clieMontcapitotal = clieMontcapitotal;
        this.cliePresestaactu = cliePresestaactu;
        this.clieNumeopera = clieNumeopera;
        this.clieActivo = clieActivo;
        this.clieAsesor = clieAsesor;
        this.clieAgencia = clieAgencia;
        this.clieNombagen = clieNombagen;
        this.clieZona = clieZona;
        this.clieRegion = clieRegion;
        this.clieSegmento = clieSegmento;
        this.clieSubsegmento = clieSubsegmento;
        this.cliePais = cliePais;
        this.clieProvincia = clieProvincia;
        this.clieTipo = clieTipo;
        this.clieCanal = clieCanal;
        this.clieCelular = clieCelular;
        this.clieEmail = clieEmail;
        this.clieEstado = clieEstado;
        this.clieBase = clieBase;
        this.bacaId = bacaId;
    }

    public Integer getClieId() {
        return clieId;
    }

    public void setClieId(Integer clieId) {
        this.clieId = clieId;
    }

    public String getClieIdentificacion() {
        return clieIdentificacion;
    }

    public void setClieIdentificacion(String clieIdentificacion) {
        this.clieIdentificacion = clieIdentificacion;
    }

    public String getClieIdenenma() {
        return clieIdenenma;
    }

    public void setClieIdenenma(String clieIdenenma) {
        this.clieIdenenma = clieIdenenma;
    }

    public String getClieHashiden() {
        return clieHashiden;
    }

    public void setClieHashiden(String clieHashiden) {
        this.clieHashiden = clieHashiden;
    }

    public String getCliePrimnomb() {
        return cliePrimnomb;
    }

    public void setCliePrimnomb(String cliePrimnomb) {
        this.cliePrimnomb = cliePrimnomb;
    }

    public String getClieSegunomb() {
        return clieSegunomb;
    }

    public void setClieSegunomb(String clieSegunomb) {
        this.clieSegunomb = clieSegunomb;
    }

    public String getCliePrimapell() {
        return cliePrimapell;
    }

    public void setCliePrimapell(String cliePrimapell) {
        this.cliePrimapell = cliePrimapell;
    }

    public String getClieSeguapell() {
        return clieSeguapell;
    }

    public void setClieSeguapell(String clieSeguapell) {
        this.clieSeguapell = clieSeguapell;
    }

    public String getClieNumepres() {
        return clieNumepres;
    }

    public void setClieNumepres(String clieNumepres) {
        this.clieNumepres = clieNumepres;
    }

    public String getClieFamilia() {
        return clieFamilia;
    }

    public void setClieFamilia(String clieFamilia) {
        this.clieFamilia = clieFamilia;
    }

    public String getClieCodiprod() {
        return clieCodiprod;
    }

    public void setClieCodiprod(String clieCodiprod) {
        this.clieCodiprod = clieCodiprod;
    }

    public Character getClieDiasmora() {
        return clieDiasmora;
    }

    public void setClieDiasmora(Character clieDiasmora) {
        this.clieDiasmora = clieDiasmora;
    }

    public String getClieNombsist() {
        return clieNombsist;
    }

    public void setClieNombsist(String clieNombsist) {
        this.clieNombsist = clieNombsist;
    }

    public String getClieDiapago() {
        return clieDiapago;
    }

    public void setClieDiapago(String clieDiapago) {
        this.clieDiapago = clieDiapago;
    }

    public String getClieNombtipoconc() {
        return clieNombtipoconc;
    }

    public void setClieNombtipoconc(String clieNombtipoconc) {
        this.clieNombtipoconc = clieNombtipoconc;
    }

    public String getClieMontcapivencer() {
        return clieMontcapivencer;
    }

    public void setClieMontcapivencer(String clieMontcapivencer) {
        this.clieMontcapivencer = clieMontcapivencer;
    }

    public String getClieMontcapinodeve() {
        return clieMontcapinodeve;
    }

    public void setClieMontcapinodeve(String clieMontcapinodeve) {
        this.clieMontcapinodeve = clieMontcapinodeve;
    }

    public String getClieMontcapivencido() {
        return clieMontcapivencido;
    }

    public void setClieMontcapivencido(String clieMontcapivencido) {
        this.clieMontcapivencido = clieMontcapivencido;
    }

    public String getClieMontcapitotal() {
        return clieMontcapitotal;
    }

    public void setClieMontcapitotal(String clieMontcapitotal) {
        this.clieMontcapitotal = clieMontcapitotal;
    }

    public String getCliePresestaactu() {
        return cliePresestaactu;
    }

    public void setCliePresestaactu(String cliePresestaactu) {
        this.cliePresestaactu = cliePresestaactu;
    }

    public String getClieNumeopera() {
        return clieNumeopera;
    }

    public void setClieNumeopera(String clieNumeopera) {
        this.clieNumeopera = clieNumeopera;
    }

    public String getClieActivo() {
        return clieActivo;
    }

    public void setClieActivo(String clieActivo) {
        this.clieActivo = clieActivo;
    }

    public String getClieAsesor() {
        return clieAsesor;
    }

    public void setClieAsesor(String clieAsesor) {
        this.clieAsesor = clieAsesor;
    }

    public String getClieAgencia() {
        return clieAgencia;
    }

    public void setClieAgencia(String clieAgencia) {
        this.clieAgencia = clieAgencia;
    }

    public String getClieNombagen() {
        return clieNombagen;
    }

    public void setClieNombagen(String clieNombagen) {
        this.clieNombagen = clieNombagen;
    }

    public String getClieZona() {
        return clieZona;
    }

    public void setClieZona(String clieZona) {
        this.clieZona = clieZona;
    }

    public String getClieRegion() {
        return clieRegion;
    }

    public void setClieRegion(String clieRegion) {
        this.clieRegion = clieRegion;
    }

    public String getClieSegmento() {
        return clieSegmento;
    }

    public void setClieSegmento(String clieSegmento) {
        this.clieSegmento = clieSegmento;
    }

    public String getClieSubsegmento() {
        return clieSubsegmento;
    }

    public void setClieSubsegmento(String clieSubsegmento) {
        this.clieSubsegmento = clieSubsegmento;
    }

    public String getCliePais() {
        return cliePais;
    }

    public void setCliePais(String cliePais) {
        this.cliePais = cliePais;
    }

    public String getClieProvincia() {
        return clieProvincia;
    }

    public void setClieProvincia(String clieProvincia) {
        this.clieProvincia = clieProvincia;
    }

    public String getClieTipo() {
        return clieTipo;
    }

    public void setClieTipo(String clieTipo) {
        this.clieTipo = clieTipo;
    }

    public String getClieCanal() {
        return clieCanal;
    }

    public void setClieCanal(String clieCanal) {
        this.clieCanal = clieCanal;
    }

    public String getClieCelular() {
        return clieCelular;
    }

    public void setClieCelular(String clieCelular) {
        this.clieCelular = clieCelular;
    }

    public String getClieEmail() {
        return clieEmail;
    }

    public void setClieEmail(String clieEmail) {
        this.clieEmail = clieEmail;
    }

    public String getClieEstado() {
        return clieEstado;
    }

    public void setClieEstado(String clieEstado) {
        this.clieEstado = clieEstado;
    }

    public String getClieBase() {
        return clieBase;
    }

    public void setClieBase(String clieBase) {
        this.clieBase = clieBase;
    }

    public DifBasecampa getBacaId() {
        return bacaId;
    }

    public void setBacaId(DifBasecampa bacaId) {
        this.bacaId = bacaId;
    }

    @XmlTransient
    public Collection<DifSolidife> getDifSolidifeCollection() {
        return difSolidifeCollection;
    }
    public void setDifSolidifeCollection(Collection<DifSolidife> difSolidifeCollection) {
        this.difSolidifeCollection = difSolidifeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clieId != null ? clieId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DifCliente)) {
            return false;
        }
        DifCliente other = (DifCliente) object;
        if ((this.clieId == null && other.clieId != null) || (this.clieId != null && !this.clieId.equals(other.clieId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DifCliente[ clieId=" + clieId + " ]";
    }


}
