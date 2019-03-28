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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity 
@Table(name = "TBL_MATERIAL")
public class Material implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_MATERIAL")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idMaterial;
	
	@Column(name = "CODIGO")
	private String codigo;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "MARCA")
	private String marca;
	
	@Column(name = "TIPO_MATERIAL")
	private String tipoMaterial;
	
	@Column(name = "CAPACIDAD")
	private String capacidad;
	
	@Column(name = "CANTIDAD")
	private int cantidad;
	
	@Column(name = "OBSERVACION")
	private String observacion;
	
	@ManyToOne
	@JoinColumn(name = "ID_LABORATORIO")
	private Laboratorio laboratorio;
			
	@Column(name = "ESTADO")
	private int estado;
	
	@ManyToMany(mappedBy = "materiales", cascade = {CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<Trazabilidad> trazabilidades = new ArrayList<>();
	
	public Material() {
		// TODO Auto-generated constructor stub
	}

	public Material(String codigo, String nombre, String marca, String tipoMaterial, String capacidad, int cantidad,
			String observacion, Laboratorio laboratorio, int estado) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.marca = marca;
		this.tipoMaterial = tipoMaterial;
		this.capacidad = capacidad;
		this.cantidad = cantidad;
		this.observacion = observacion;
		this.laboratorio = laboratorio;
		this.estado = estado;
	}

	public long getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(long idMaterial) {
		this.idMaterial = idMaterial;
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

	public String getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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
		result = prime * result + ((capacidad == null) ? 0 : capacidad.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + estado;
		result = prime * result + (int) (idMaterial ^ (idMaterial >>> 32));
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((observacion == null) ? 0 : observacion.hashCode());
		result = prime * result + ((tipoMaterial == null) ? 0 : tipoMaterial.hashCode());
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
		Material other = (Material) obj;
		if (cantidad != other.cantidad)
			return false;
		if (capacidad == null) {
			if (other.capacidad != null)
				return false;
		} else if (!capacidad.equals(other.capacidad))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (estado != other.estado)
			return false;
		if (idMaterial != other.idMaterial)
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
		if (observacion == null) {
			if (other.observacion != null)
				return false;
		} else if (!observacion.equals(other.observacion))
			return false;
		if (tipoMaterial == null) {
			if (other.tipoMaterial != null)
				return false;
		} else if (!tipoMaterial.equals(other.tipoMaterial))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Material [idMaterial=" + idMaterial + ", codigo=" + codigo + ", nombre=" + nombre + ", marca=" + marca
				+ ", tipoMaterial=" + tipoMaterial + ", capacidad=" + capacidad + ", cantidad=" + cantidad
				+ ", observacion=" + observacion + ", laboratorio=" + laboratorio + ", estado=" + estado + "]";
	}

	
	
	

	
	

}
