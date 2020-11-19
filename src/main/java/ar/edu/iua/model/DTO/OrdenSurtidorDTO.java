package ar.edu.iua.model.DTO;

import java.io.Serializable;


public class OrdenSurtidorDTO implements Serializable {


    private String idOrden;
    private double temp;
    private double masaAcum;
    private String fecha;
    private String psw;
    private double densidad;

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }
  
    public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getMasaAcum() {
		return masaAcum;
	}

	public void setMasaAcum(double masaAcum) {
		this.masaAcum = masaAcum;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getDensidad() {
		return densidad;
	}

	public void setDensidad(double densidad) {
		this.densidad = densidad;
	}

	public OrdenSurtidorDTO(String idOrden, double temp, double masaAcum, String psw, double densidad) {
        this.idOrden = idOrden;
        this.temp = temp;
        this.masaAcum = masaAcum;
        this.psw = psw;
        this.densidad = densidad;
    }
}
