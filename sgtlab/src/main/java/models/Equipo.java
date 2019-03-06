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
@Table(name = "TBL_EQUIPO")
public class Equipo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_EQUIPO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idEquipo;
	
	@Column(name = "CODIGO")
	private String codigo;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "MARCA")
	private String marca;
	
	@Column(name = "CANTIDAD")
	private int cantidad;
	
	@Column(name = "ESTADO_EQUIPO")
	private String estadoEquipo;
	
	@ManyToOne//(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_LABORATORIO")
	private Laboratorio laboratorio;
		
	@Column(name = "ESTADO")
	private int estado;
	
	public Equipo() {
		// TODO Auto-generated constructor stub
	}

	public Equipo(String codigo, String nombre, String marca, int cANTIDAD, String estadoEquipo,
			Laboratorio laboratorio, int estado) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.marca = marca;
		cantidad = cANTIDAD;
		this.estadoEquipo = estadoEquipo;
		this.laboratorio = laboratorio;
		this.estado = estado;
	}

	public long getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(long idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getEstadoEquipo() {
		return estadoEquipo;
	}

	public void setEstadoEquipo(String estadoEquipo) {
		this.estadoEquipo = estadoEquipo;
	}

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
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
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + estado;
		result = prime * result + ((estadoEquipo == null) ? 0 : estadoEquipo.hashCode());
		result = prime * result + (int) (idEquipo ^ (idEquipo >>> 32));
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
		Equipo other = (Equipo) obj;
		if (cantidad != other.cantidad)
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (estado != other.estado)
			return false;
		if (estadoEquipo == null) {
			if (other.estadoEquipo != null)
				return false;
		} else if (!estadoEquipo.equals(other.estadoEquipo))
			return false;
		if (idEquipo != other.idEquipo)
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
		return "Equipo [idEquipo=" + idEquipo + ", codigo=" + codigo + ", nombre=" + nombre + ", marca=" + marca
				+ ", cantidad=" + cantidad + ", estadoEquipo=" + estadoEquipo + ", laboratorio=" + laboratorio
				+ ", estado=" + estado + "]";
	}

	
	

}
