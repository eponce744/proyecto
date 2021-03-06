package ar.edu.iua.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chofer")

public class Chofer implements Serializable {

    private static final long serialVersionUID = 451621105748580924L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    private String apellido;

    @Column(unique = true)
    private long dni;
    
    private String cel;
        
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

	public String getCel() {
		return cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}


}
