package ar.edu.iua.model;

import javax.persistence.*;

import ar.edu.iua.model.DTO.OrdenSurtidorDTO;
import ar.edu.iua.model.DTO.PesajeDTO;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "orden")
@SqlResultSetMapping(
        name = "ordensurtidor",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "o.idOrden", type = String.class),
                                @ColumnResult(name = "o.temp", type = String.class),
                                @ColumnResult(name = "o.caudal", type = double.class),
                                @ColumnResult(name = "o.densidad", type = double.class)
                        },
                        targetClass = OrdenSurtidorDTO.class
                )
        }
)
@SqlResultSetMapping(
        name = "pesaje",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "p.idOrden", type = String.class),
                                @ColumnResult(name = "p.pesaje_inicial", type = String.class)
                        },
                        targetClass = PesajeDTO.class
                )
        }
)
public class Orden implements Serializable {

    private static final long serialVersionUID = -6842932053341232133L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String codigoExterno;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "camion_id")
    private Camion camion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @OneToOne(cascade = CascadeType.ALL)
    private Conciliacion conciliacion;

//    @OneToMany(targetEntity = OrdenDetalle.class, mappedBy = "orden", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<OrdenDetalle> ordenDetalleList;

    private double masaAcum;

    private double densidad;

    private double temperatura;

    private double caudal;

    private Date fechaUltimoAlmacenamiento;

    private double preset;

    private int estado;

    private Date fechaGeneracionOrden;

    private Date fechaPrevistaCarga;

    private String psw;

    private double pesajeInicial;

    private Date fechaPesajeInicial;

    private double pesajeFinal;

    private Date fechaPesajeFinal;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



	public String getCodigoExterno() {
		return codigoExterno;
	}

	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

	public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Conciliacion getConciliacion() {
        return conciliacion;
    }

    public void setConciliacion(Conciliacion conciliacion) {
        this.conciliacion = conciliacion;
    }

    public double getMasaAcum() {
		return masaAcum;
	}

	public void setMasaAcum(double masaAcum) {
		this.masaAcum = masaAcum;
	}

    public double getDensidad() {
        return densidad;
    }

	public void setDensidad(double densidad) {
        this.densidad = densidad;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getCaudal() {
        return caudal;
    }

    public void setCaudal(double caudal) {
        this.caudal = caudal;
    }

    public Date getFechaUltimoAlmacenamiento() {
        return fechaUltimoAlmacenamiento;
    }

    public void setFechaUltimoAlmacenamiento(Date fechaUltimoAlmacenamiento) {
        this.fechaUltimoAlmacenamiento = fechaUltimoAlmacenamiento;
    }

    public double getPreset() {
        return preset;
    }

    public void setPreset(double preset) {
        this.preset = preset;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFechaGeneracionOrden() {
        return fechaGeneracionOrden;
    }

    public void setFechaGeneracionOrden(Date fechaGeneracionOrden) {
        this.fechaGeneracionOrden = fechaGeneracionOrden;
    }

    public Date getFechaPrevistaCarga() {
        return fechaPrevistaCarga;
    }

    public void setFechaPrevistaCarga(Date fechaPrevistaCarga) {
        this.fechaPrevistaCarga = fechaPrevistaCarga;
    }

    public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public double getPesajeInicial() {
        return pesajeInicial;
    }

    public void setPesajeInicial(double pesajeInicial) {
        this.pesajeInicial = pesajeInicial;
    }

    public Date getFechaPesaje() {
        return fechaPesajeInicial;
    }

    public void setFechaPesaje(Date fechaPesajeInicial) {
        this.fechaPesajeInicial = fechaPesajeInicial;
    }

    public Date getFechaPesajeInicial() {
        return fechaPesajeInicial;
    }

    public void setFechaPesajeInicial(Date fechaPesajeInicial) {
        this.fechaPesajeInicial = fechaPesajeInicial;
    }

    public double getPesajeFinal() {
        return pesajeFinal;
    }

    public void setPesajeFinal(double pesajeFinal) {
        this.pesajeFinal = pesajeFinal;
    }

    public Date getFechaPesajeFinal() {
        return fechaPesajeFinal;
    }

    public void setFechaPesajeFinal(Date fechaPesajeFinal) {
        this.fechaPesajeFinal = fechaPesajeFinal;
    }
}
