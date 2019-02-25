package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "TBL_LABORATORIO")
public class Laboratorio implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_LABORATORIO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idLaboratorio;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "DESCRIPCIÓN")
	private String descripcion;
	
	public Laboratorio() {
		// TODO Auto-generated constructor stub
	}

	public Laboratorio(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public long getIdLaboratorio() {
		return idLaboratorio;
	}

	public void setIdLaboratorio(long idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
