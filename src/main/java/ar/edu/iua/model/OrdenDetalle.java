package ar.edu.iua.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ordenDetalle")

public class OrdenDetalle implements Serializable {

    private static final long serialVersionUID = -2222985674134742453L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double masaAcum;

    private double densidad;

    private double temp;

    private double caudal;

    private Date fecha;

    private long idOrden;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getDensidad() {
        return densidad;
    }

    public void setDensidad(double densidad) {
        this.densidad = densidad;
    }


    public double getCaudal() {
        return caudal;
    }

    public void setCaudal(double caudal) {
        this.caudal = caudal;
    }

    public long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(long idOrden) {
        this.idOrden = idOrden;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

	public double getMasaAcum() {
		return masaAcum;
	}

	public void setMasaAcum(double masaAcum) {
		this.masaAcum = masaAcum;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}
	
	 public OrdenDetalle(double masaAcum, double densidad, double temp, double caudal, long idOrden, Date fecha){
	        this.masaAcum = masaAcum;
	        this.densidad = densidad;
	        this.temp = temp;
	        this.caudal = caudal;
	        this.idOrden = idOrden;
	        this.fecha = fecha;
	 }

	 public OrdenDetalle(){

	 }
    
}
