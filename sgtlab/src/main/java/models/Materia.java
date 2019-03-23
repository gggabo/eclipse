package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "TBL_MATERIA")
public class Materia implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_MATERIA")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idMateria;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "SEMESTRE")
	private String semetres;
	
	public Materia() {
		// TODO Auto-generated constructor stub
	}

		
	public Materia(String nombre, String semetres) {
		super();
		this.nombre = nombre;
		this.semetres = semetres;
	}

	public long getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(long idMateria) {
		this.idMateria = idMateria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSemetres() {
		return semetres;
	}

	public void setSemetres(String semetres) {
		this.semetres = semetres;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idMateria ^ (idMateria >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((semetres == null) ? 0 : semetres.hashCode());
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
		Materia other = (Materia) obj;
		if (idMateria != other.idMateria)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (semetres == null) {
			if (other.semetres != null)
				return false;
		} else if (!semetres.equals(other.semetres))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Materia [idMateria=" + idMateria + ", nombre=" + nombre + ", semetres=" + semetres + "]";
	}
	
	
	
	
	
}
