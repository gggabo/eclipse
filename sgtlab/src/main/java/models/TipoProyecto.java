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
@Table(name = "TBL_TIPOPROYECTO")
public class TipoProyecto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_TIPOPROYECTO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idTipoProyecto;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "ESTADO")
	private int estado;
	
	@OneToMany(mappedBy = "tipoProyecto", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Proyecto>  proyectos = new ArrayList<>();
		
	public TipoProyecto() {
		// TODO Auto-generated constructor stub
	}

	public TipoProyecto(String nombre, String semetres, int estado) {
		super();
		this.nombre = nombre;
		this.estado = estado;
	}


	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
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
		result = prime * result + estado;
		result = prime * result + (int) (idTipoProyecto ^ (idTipoProyecto >>> 32));
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
		TipoProyecto other = (TipoProyecto) obj;
		if (estado != other.estado)
			return false;
		if (idTipoProyecto != other.idTipoProyecto)
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
		return "TipoProyecto [idTipoProyecto=" + idTipoProyecto + ", nombre=" + nombre + ", estado=" + estado + "]";
	}


	



	
	
	
	
}
