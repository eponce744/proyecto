package ar.edu.iua.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "camion")
public class Camion implements Serializable {

    private static final long serialVersionUID = -8074209302652797995L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String descripcion;
    
    private String patente;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "camion_cisterna_detalle",
            joinColumns = @JoinColumn(name = "camion_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cisterna_id", referencedColumnName = "id"))
    private List<Cisterna> cisternaList;

    @OneToOne(cascade = CascadeType.ALL)
    private Chofer chofer;

    @OneToMany(targetEntity = Orden.class, mappedBy = "camion", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Orden> ordenList;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public List<Cisterna> getCisternaList() {
        return cisternaList;
    }

    public void setCisternaList(List<Cisterna> cisternaList) {
        this.cisternaList = cisternaList;
    }

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    public List<Orden> getOrdenList() {
        return ordenList;
    }

    public void setOrdenList(List<Orden> ordenList) {
        this.ordenList = ordenList;
    }
}
