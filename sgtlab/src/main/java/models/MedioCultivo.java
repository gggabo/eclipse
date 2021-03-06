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
@Table(name = "TBL_MEDIO_CULTIVO")
public class MedioCultivo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_MEDIO_CULTIVO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idMedioCultivo;
	
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
	
	@OneToMany(mappedBy = "medioCultivo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TrazabilidadMedioCultivo>  trazabilidadMediosCultivo = new ArrayList<>();
	
	public MedioCultivo() {
		// TODO Auto-generated constructor stub
	}

	public MedioCultivo(String codigo, String nombre, float entrada, LocalDate fechaCaducidad,float gasto,
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

	public long getIdMedioCultivo() {
		return idMedioCultivo;
	}

	public void setIdMedioCultivo(long idMedioCultivo) {
		this.idMedioCultivo = idMedioCultivo;
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
		result = prime * result + (int) (idMedioCultivo ^ (idMedioCultivo >>> 32));
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
		MedioCultivo other = (MedioCultivo) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (idMedioCultivo != other.idMedioCultivo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reactivo [idReactivo=" + idMedioCultivo + ", codigo=" + codigo + ", nombre=" + nombre + ", entrada="
				+ entrada + ", fechaCaducidad=" + fechaCaducidad + ", imagen=" + ", gasto="
				+ gasto + ", saldo=" + saldo + ", unidad=" + unidad + ", laboratorio=" + laboratorio + "]";
	}
	
	

}
