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
	private String semetre;
	
	@Column(name = "ESTADO")
	private int estado;
	
	@ManyToMany(mappedBy = "materias", cascade = {CascadeType.ALL })//, fetch = FetchType.EAGER)
	private List<Proyecto> proyectos = new ArrayList<>();
		
	public Materia() {
		// TODO Auto-generated constructor stub
	}

	public Materia(String nombre, String semetres, int estado) {
		super();
		this.nombre = nombre;
		this.semetre = semetres;
		this.estado = estado;
	}


	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
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

	public String getSemetre() {
		return semetre;
	}

	public void setSemetre(String semetre) {
		this.semetre = semetre;
	}

	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + estado;
		result = prime * result + (int) (idMateria ^ (idMateria >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((semetre == null) ? 0 : semetre.hashCode());
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
		if (estado != other.estado)
			return false;
		if (idMateria != other.idMateria)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (semetre == null) {
			if (other.semetre != null)
				return false;
		} else if (!semetre.equals(other.semetre))
			return false;
		return true;
	}




	@Override
	public String toString() {
		return "Materia [idMateria=" + idMateria + ", nombre=" + nombre + ", semetres=" + semetre + ", estado="
				+ estado + "]";
	}



	
	
	
	
}
