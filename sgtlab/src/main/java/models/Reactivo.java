package models;

import java.io.Serializable;
import java.time.LocalDate;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity 
@Table(name = "TBL_REACTIVO")
public class Reactivo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_REACTIVO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idReactivo;
	
	@Column(name = "CODIGO")
	private String codigo;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "ENTRADA")
	private float entrada;
	
	@Column(name = "FECHA_CADUCIDAD")
	private LocalDate fechaCaducidad;
			
	/*@Lob
	@Column(name="IMAGEN", columnDefinition="mediumblob")
	private byte[] imagen;*/
		
	@Column(name = "GASTO")
	private float gasto;
	
	@Column(name = "SALDO")
	private float saldo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_UNIDAD")
	private Unidad unidad;
	
	@ManyToOne
	@JoinColumn(name = "ID_LABORATORIO")
	private Laboratorio laboratorio;
		
	@Column(name = "ESTADO")
	private int estado;
	
	@OneToMany(mappedBy = "reactivo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TrazabilidadReactivo>  trazabilidadReactivos = new ArrayList<>();
		
	public Reactivo() {
		// TODO Auto-generated constructor stub
	}

	public Reactivo(String codigo, String nombre, float entrada, LocalDate fechaCaducidad,float gasto,
			float saldo, Unidad unidad, Laboratorio laboratorio, int estado) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.entrada = entrada;
		this.fechaCaducidad = fechaCaducidad;
		//this.imagen = imagen;
		this.gasto = gasto;
		this.saldo = saldo;
		this.unidad = unidad;
		this.laboratorio = laboratorio;
		this.estado = estado;
	}



	public long getIdReactivo() {
		return idReactivo;
	}

	public void setIdReactivo(long idReactivo) {
		this.idReactivo = idReactivo;
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

	public float getEntrada() {
		return entrada;
	}

	public void setEntrada(float entrada) {
		this.entrada = entrada;
	}

	public LocalDate getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(LocalDate fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	/*public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}*/

	public float getGasto() {
		return gasto;
	}

	public void setGasto(float gasto) {
		this.gasto = gasto;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
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
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + Float.floatToIntBits(entrada);
		result = prime * result + ((fechaCaducidad == null) ? 0 : fechaCaducidad.hashCode());
		result = prime * result + Float.floatToIntBits(gasto);
		result = prime * result + (int) (idReactivo ^ (idReactivo >>> 32));
		//result = prime * result + Arrays.hashCode(imagen);
		result = prime * result + ((laboratorio == null) ? 0 : laboratorio.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + Float.floatToIntBits(saldo);
		result = prime * result + ((unidad == null) ? 0 : unidad.hashCode());
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
		Reactivo other = (Reactivo) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (Float.floatToIntBits(entrada) != Float.floatToIntBits(other.entrada))
			return false;
		if (fechaCaducidad == null) {
			if (other.fechaCaducidad != null)
				return false;
		} else if (!fechaCaducidad.equals(other.fechaCaducidad))
			return false;
		if (Float.floatToIntBits(gasto) != Float.floatToIntBits(other.gasto))
			return false;
		if (idReactivo != other.idReactivo)
			return false;
		/*if (!Arrays.equals(imagen, other.imagen))
			return false;*/
		if (laboratorio == null) {
			if (other.laboratorio != null)
				return false;
		} else if (!laboratorio.equals(other.laboratorio))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (Float.floatToIntBits(saldo) != Float.floatToIntBits(other.saldo))
			return false;
		if (unidad == null) {
			if (other.unidad != null)
				return false;
		} else if (!unidad.equals(other.unidad))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Reactivo [idReactivo=" + idReactivo + ", codigo=" + codigo + ", nombre=" + nombre + ", entrada="
				+ entrada + ", fechaCaducidad=" + fechaCaducidad + ", imagen=" + ", gasto="
				+ gasto + ", saldo=" + saldo + ", unidad=" + unidad + ", laboratorio=" + laboratorio + "]";
	}
	
	

}
