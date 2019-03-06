package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	
	@OneToMany(mappedBy = "laboratorio", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reactivo>  reactivos = new ArrayList<>();
	
	@OneToMany(mappedBy = "laboratorio", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Equipo>  equipos = new ArrayList<>();
	
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
	
	public List<Reactivo> getReactivos() {
		return reactivos;
	}

	public void setReactivos(List<Reactivo> reactivos) {
		this.reactivos = reactivos;
	}
	
	@SuppressWarnings("unused")
	private void addReactivo(Reactivo reactivo) {
		if(!reactivos.contains(reactivo)) {
			reactivos.add(reactivo);
			reactivo.setLaboratorio(this);
		}
	}
	
	public void removeReactivo(Reactivo reactivo) {
		if(reactivos.contains(reactivo)) {
			reactivos.remove(reactivo);
			reactivo.setLaboratorio(null);
		}
	}
	
	public List<Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<Equipo> equipos) {
		this.equipos = equipos;
	}
	
	@SuppressWarnings("unused")
	private void addEquipo(Equipo equipo) {
		if(!equipos.contains(equipo)) {
			equipos.add(equipo);
			equipo.setLaboratorio(this);
		}
	}
	
	public void removeEquipo(Equipo equipo) {
		if(equipos.contains(equipo)) {
			equipos.remove(equipo);
			equipo.setLaboratorio(null);
		}
	}

}
