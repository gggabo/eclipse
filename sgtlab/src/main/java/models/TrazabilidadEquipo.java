package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity 
@Table(name = "TBL_TRAZABILIDADEQUIPO")
public class TrazabilidadEquipo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_TRAZABILIDADEQUIPO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idTrazabilidadEquipo;
	
	@ManyToOne
	@JoinColumn(name = "ID_TRAZABILIDAD")
	private Trazabilidad trazabilidad;
	
	@ManyToOne
	@JoinColumn(name = "ID_EQUIPO")
	private Equipo equipo;
	
	@ManyToMany
	@JoinTable(name = "TBL_TRAZABILIDAD_EQUIPO_COMPONENTE", joinColumns= @JoinColumn(name = "ID_TRAZABILIDADEQUIPO"), 
	inverseJoinColumns = @JoinColumn(name ="ID_COMPONENTE"))
	private List<Componente> componentes = new ArrayList<>();
	
	public TrazabilidadEquipo() {
		// TODO Auto-generated constructor stub
	}


	public void addComponente(Componente componente) {
		if(!componentes.contains(componente)) {
			componentes.add(componente);
		}
	}
	
	
	public Trazabilidad getTrazabilidad() {
		return trazabilidad;
	}

	public void setTrazabilidad(Trazabilidad trazabilidad) {
		this.trazabilidad = trazabilidad;
	}


	public long getIdTrazabilidadEquipo() {
		return idTrazabilidadEquipo;
	}


	public void setIdTrazabilidadEquipo(long idTrazabilidadEquipo) {
		this.idTrazabilidadEquipo = idTrazabilidadEquipo;
	}


	public Equipo getEquipo() {
		return equipo;
	}


	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}


	public List<Componente> getComponentes() {
		return componentes;
	}


	public void setComponentes(List<Componente> componentes) {
		this.componentes = componentes;
	}


	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((equipo == null) ? 0 : equipo.hashCode());
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
		TrazabilidadEquipo other = (TrazabilidadEquipo) obj;
		if (equipo == null) {
			if (other.equipo != null)
				return false;
		} else if (!equipo.equals(other.equipo))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "TrazabilidadEquipo [idTrazabilidadEquipo=" + idTrazabilidadEquipo + ", trazabilidad=" + trazabilidad
				+ ", equipo=" + equipo + ", componentes=" + componentes + "]";
	}

	

	
	
	

}
