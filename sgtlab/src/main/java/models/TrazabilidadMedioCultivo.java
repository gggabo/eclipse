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
@Table(name = "TBL_TRAZABILIDAD_MEDIO_CULTIVO")
public class TrazabilidadMedioCultivo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_TRAZABILIDAD_MEDIO_CULTIVO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idTrazabilidadReactivo;
	
	@ManyToOne
	@JoinColumn(name = "ID_TRAZABILIDAD")
	private Trazabilidad trazabilidad;
	
	@ManyToOne
	@JoinColumn(name = "ID_MEDIO_CULTIVO")
	private MedioCultivo medioCultivo;
	
	@Column(name = "GASTO")
	private float gasto;
	
	public TrazabilidadMedioCultivo() {
		// TODO Auto-generated constructor stub
	}

	public TrazabilidadMedioCultivo(float gasto) {
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

	public MedioCultivo getMedioCultivo() {
		return medioCultivo;
	}

	public void setMedioCultivo(MedioCultivo medioCultivo) {
		this.medioCultivo = medioCultivo;
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
		result = prime * result + Float.floatToIntBits(gasto);
		result = prime * result + (int) (idTrazabilidadReactivo ^ (idTrazabilidadReactivo >>> 32));
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
		TrazabilidadMedioCultivo other = (TrazabilidadMedioCultivo) obj;
		if (Float.floatToIntBits(gasto) != Float.floatToIntBits(other.gasto))
			return false;
		if (idTrazabilidadReactivo != other.idTrazabilidadReactivo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrazabilidadReactivo [idTrazabilidadReactivo=" + idTrazabilidadReactivo + ", trazabilidad="
				+ trazabilidad + ", reactivo=" + medioCultivo + ", gasto=" + gasto + "]";
	}
	
	

}
