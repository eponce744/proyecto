package ar.edu.iua.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "conciliacion")
public class Conciliacion implements Serializable {

    private static final long serialVersionUID = -1609250874892904470L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonBackReference
    private long id;

    private String codigoExterno;

    private double productoCargado;

    private double densidad;

    private double temp;

    private double caudal;

    private double pesajeInicial;

    private double pesajeFinal;

    private double netoBalanza;

    private double diferenciaBalanzaCaudal;

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

	public String getCodigoExterno() {
		return codigoExterno;
	}

	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getCaudal() {
        return caudal;
    }

    public void setCaudal(double caudal) {
        this.caudal = caudal;
    }

    public double getProductoCargado() {
        return productoCargado;
    }

    public void setProductoCargado(double productoCargado) {
        this.productoCargado = productoCargado;
    }

    public double getPesajeInicial() {
        return pesajeInicial;
    }

    public void setPesajeInicial(double pesajeInicial) {
        this.pesajeInicial = pesajeInicial;
    }

    public double getPesajeFinal() {
        return pesajeFinal;
    }

    public void setPesajeFinal(double pesajeFinal) {
        this.pesajeFinal = pesajeFinal;
    }

    public double getNetoBalanza() {
        return netoBalanza;
    }

    public void setNetoBalanza(double netoBalanza) {
        this.netoBalanza = netoBalanza;
    }
    
    public double getDiferenciaBalanzaCaudal() {
		return diferenciaBalanzaCaudal;
	}

	public void setDiferenciaBalanzaCaudal(double diferenciaBalanzaCaudal) {
		this.diferenciaBalanzaCaudal = diferenciaBalanzaCaudal;
	}

	public Conciliacion(double pesajeInicial, double pesajeFinal, double netoBalanza, double diferenciaBalanzaCaudal, double productoCargado, double densidad, double temp, double caudal) {
        this.pesajeInicial = pesajeInicial;
        this.pesajeFinal = pesajeFinal;
        this.netoBalanza = netoBalanza;
        this.diferenciaBalanzaCaudal = diferenciaBalanzaCaudal;
        this.productoCargado = productoCargado;
        this.densidad = densidad;
        this.temp = temp;
        this.caudal = caudal;
    }

    public Conciliacion() {
    }

}
