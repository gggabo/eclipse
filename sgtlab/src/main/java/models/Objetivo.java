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
@Table(name = "TBL_OBJETIVO")
public class Objetivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_OBJETIVO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idObjetivo;
	
	@Column(name = "OBJETIVO", columnDefinition = "TEXT")
	private String objetivo;
	
	@ManyToOne
	@JoinColumn(name = "ID_PROYECTO")
	private Proyecto proyecto;
		
	public Objetivo() {
		// TODO Auto-generated constructor stub
	}
	
	public Objetivo(String objetivo) {
		super();
		this.objetivo = objetivo;
	}

	public long getIdObjetivo() {
		return idObjetivo;
	}

	public void setIdObjetivo(long idObjetivo) {
		this.idObjetivo = idObjetivo;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idObjetivo ^ (idObjetivo >>> 32));
		result = prime * result + ((objetivo == null) ? 0 : objetivo.hashCode());
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
		Objetivo other = (Objetivo) obj;
		if (idObjetivo != other.idObjetivo)
			return false;
		if (objetivo == null) {
			if (other.objetivo != null)
				return false;
		} else if (!objetivo.equals(other.objetivo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Objetivo [idObjetivo=" + idObjetivo + ", objetivo=" + objetivo + "]";
	}	
	
}
