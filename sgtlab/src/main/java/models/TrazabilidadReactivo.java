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
@Table(name = "TBL_TRAZABILIDADREACTIVO")
public class TrazabilidadReactivo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_TRAZABILIDADREACTIVO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idTrazabilidadReactivo;
	
	@ManyToOne
	@JoinColumn(name = "ID_TRAZABILIDAD")
	private Trazabilidad trazabilidad;
	
	@ManyToOne
	@JoinColumn(name = "ID_REACTIVO")
	private Reactivo reactivo;
	
	@Column(name = "GASTO")
	private float gasto;
	
	public TrazabilidadReactivo() {
		// TODO Auto-generated constructor stub
	}

	public TrazabilidadReactivo(float gasto) {
		super();
		this.gasto = gasto;
	}

	public long getIdTrazabilidadReactivo() {
		return idTrazabilidadReactivo;
	}

	public void setIdTrazabilidadReactivo(long idTrazabilidadReactivo) {
		this.idTrazabilidadReactivo = idTrazabilidadReactivo;
	}

	public Trazabilidad getTrazabilidad() {
		return trazabilidad;
	}

	public void setTrazabilidad(Trazabilidad trazabilidad) {
		this.trazabilidad = trazabilidad;
	}

	public Reactivo getReactivo() {
		return reactivo;
	}

	public void setReactivo(Reactivo reactivo) {
		this.reactivo = reactivo;
	}

	public float getGasto() {
		return gasto;
	}

	public void setGasto(float gasto) {
		this.gasto = gasto;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reactivo == null) ? 0 : reactivo.hashCode());
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
		TrazabilidadReactivo other = (TrazabilidadReactivo) obj;
		if (reactivo == null) {
			if (other.reactivo != null)
				return false;
		} else if (!reactivo.equals(other.reactivo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrazabilidadReactivo [idTrazabilidadReactivo=" + idTrazabilidadReactivo + ", trazabilidad="
				+ trazabilidad + ", reactivo=" + reactivo + ", gasto=" + gasto + "]";
	}
	
	

}
