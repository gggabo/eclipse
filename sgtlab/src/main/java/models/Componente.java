package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity 
@Table(name = "TBL_COMPONENTE")
public class Componente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_COMPONENTE")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idComponente;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "MARCA")
	private String marca;
	
	@Column(name = "CAPACIDAD")
	private String capacidad;
	
	@Column(name = "CANTIDAD")
	private int cantidad;
	
	@ManyToOne
	@JoinColumn(name = "ID_EQUIPO")
	private Equipo equipo;
	
	@Column(name = "ESTADO")
	private int estado;
	
	public Componente() {
		// TODO Auto-generated constructor stub
	}
    
	
	
	public Componente(long idComponente, String nombre, String marca, String capacidad, int cantidad, Equipo equipo,
			int estado) {
		super();
		this.idComponente = idComponente;
		this.nombre = nombre;
		this.marca = marca;
		this.capacidad = capacidad;
		this.cantidad = cantidad;
		this.equipo = equipo;
		this.estado = estado;
	}

	public long getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(long idComponente) {
		this.idComponente = idComponente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
	
	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidad;
		result = prime * result + ((capacidad == null) ? 0 : capacidad.hashCode());
		result = prime * result + estado;
		result = prime * result + (int) (idComponente ^ (idComponente >>> 32));
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
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
		Componente other = (Componente) obj;
		if (cantidad != other.cantidad)
			return false;
		if (capacidad == null) {
			if (other.capacidad != null)
				return false;
		} else if (!capacidad.equals(other.capacidad))
			return false;
		if (estado != other.estado)
			return false;
		if (idComponente != other.idComponente)
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
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
		return "Componente [idComponente=" + idComponente + ", nombre=" + nombre + ", marca=" + marca + ", capacidad="
				+ capacidad + ", cantidad=" + cantidad + ", equipo=" + equipo + ", estado=" + estado + "]";
	}

	
	
	
	

	
}
