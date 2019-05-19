package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity 
@Table(name = "TBL_ROL")
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_ROL")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idRol;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@ManyToMany(mappedBy = "roles", cascade = {CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<Usuario> usuarios = new ArrayList<>();
	
	/*@ManyToMany()
	@JoinTable(name = "TBL_ROL_LABORATORIO", joinColumns= @JoinColumn(name = "ID_ROL"), 
	inverseJoinColumns = @JoinColumn(name ="ID_LABORATORIO"))
	private List<Laboratorio> laboratorios = new ArrayList<>();
	*/
	public Rol() {
		// TODO Auto-generated constructor stub
	}

	public Rol(String nombre, List<Usuario> usuarios) {
		super();
		this.nombre = nombre;
		this.usuarios = usuarios;
	}

	/*public List<Laboratorio> getLaboratorios() {
		return laboratorios;
	}

	public void setLaboratorios(List<Laboratorio> laboratorios) {
		this.laboratorios = laboratorios;
	}*/



	public long getIdRol() {
		return idRol;
	}

	public void setIdRol(long idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((usuarios == null) ? 0 : usuarios.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rol other = (Rol) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (usuarios == null) {
			if (other.usuarios != null)
				return false;
		} else if (!usuarios.equals(other.usuarios))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Rol [idRol=" + idRol + ", nombre=" + nombre + "]";
	}
	
	

}
