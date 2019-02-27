package models;

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
@Table(name = "TBL_UNIDAD")
public class Unidad {
	@Id
	@Column(name = "ID_UNIDAD")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idUnidad;
	
	@Column(name = "NOMBRE")
	private String nombre;	
	
	@OneToMany(mappedBy = "unidad", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reactivo>  reactivos = new ArrayList<>();
	
	public Unidad() {
		// TODO Auto-generated constructor stub
	}
	
	public Unidad(String nombre) {
		super();
		this.nombre = nombre;
	}

	public long getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(long idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idUnidad ^ (idUnidad >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Unidad other = (Unidad) obj;
		if (idUnidad != other.idUnidad)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Unidad [idUnidad=" + idUnidad + ", nombre=" + nombre + "]";
	}
	
	
	
}
