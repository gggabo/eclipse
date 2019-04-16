package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity 
@Table(name = "TBL_TRAZABILIDAD")
@Where(clause = "ESTADO = 1")
public class Trazabilidad implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_TRAZABILIDAD")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idTrazabilidad;
	
	@Column(name = "FECHA")
	private LocalDate fecha;
	
	@Column(name = "HORA")
	private LocalTime hora;
	
	@Column(name = "DESCRIPCION", columnDefinition = "TEXT")
	private String descripcion;
	
	@Lob
	@Column(name="EVIDENCIA", columnDefinition="mediumblob")
	private byte[] evidencia;
	
	@ManyToOne
	@JoinColumn(name = "ID_PROYECTO")
	private Proyecto proyecto;
	
	@OneToMany(mappedBy = "trazabilidad", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TrazabilidadReactivo>  trazabilidadReactivos = new ArrayList<>();
	
	@OneToMany(mappedBy = "trazabilidad", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TrazabilidadEquipo>  trazabilidadEquipos = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "TBL_TRAZABILIDAD_MATERIAL", joinColumns= @JoinColumn(name = "ID_TRAZABILIDAD"), 
	inverseJoinColumns = @JoinColumn(name ="ID_MATERIAL"))
	private List<Material> materiales = new ArrayList<>();

	@OneToMany(mappedBy = "trazabilidad", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TrazabilidadMedioCultivo>  trazabilidadMediosCultivo = new ArrayList<>();
	
	@Column(name = "ESTADO_REVISION") 
	private String estadoRevision; 
	
	@Column(name = "ESTADO") 
	private int estado; 
	
	@ManyToMany
	@JoinTable(name = "TBL_TRAZABILIDAD_REVISOR", joinColumns= @JoinColumn(name = "ID_TRAZABILIDAD"), 
	inverseJoinColumns = @JoinColumn(name ="ID_USUARIO"))
	private List<Usuario> revisores = new ArrayList<>();
	
	/*@Column(name = "REVISOR")
	private String revisor;*/
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;
	
	public Trazabilidad() {
		// TODO Auto-generated constructor stub
	}

	public Trazabilidad(LocalDate fecha, LocalTime hora, String descripcion, byte[] evidencia, int estado) {
		super();
		this.fecha = fecha;
		this.hora = hora;
		this.descripcion = descripcion;
		this.evidencia = evidencia;
		this.estado = estado;
	}

	public long getIdTrazabilidad() {
		return idTrazabilidad;
	}

	public void setIdTrazabilidad(long idTrazabilidad) {
		this.idTrazabilidad = idTrazabilidad;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getEvidencia() {
		return evidencia;
	}

	public void setEvidencia(byte[] evidencia) {
		this.evidencia = evidencia;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public int getEstado() {
		return estado;
	}
	
	public String getEstadoRevision() {
		return estadoRevision;
	}

	public void setEstadoRevision(String estadoRevision) {
		this.estadoRevision = estadoRevision;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public List<TrazabilidadReactivo> getTrazabilidadReactivos() {
		return trazabilidadReactivos;
	}

	public void setTrazabilidadReactivos(List<TrazabilidadReactivo> trazabilidadReactivos) {
		this.trazabilidadReactivos = trazabilidadReactivos;
	}

	public List<TrazabilidadEquipo> getTrazabilidadEquipos() {
		return trazabilidadEquipos;
	}

	public void setTrazabilidadEquipos(List<TrazabilidadEquipo> trazabilidadEquipos) {
		this.trazabilidadEquipos = trazabilidadEquipos;
	}

	public List<Material> getMateriales() {
		return materiales;
	}

	public void setMateriales(List<Material> materiales) {
		this.materiales = materiales;
	}


	public List<TrazabilidadMedioCultivo> getTrazabilidadMediosCultivo() {
		return trazabilidadMediosCultivo;
	}

	public void setTrazabilidadMediosCultivo(List<TrazabilidadMedioCultivo> trazabilidadMediosCultivo) {
		this.trazabilidadMediosCultivo = trazabilidadMediosCultivo;
	}

	public List<Usuario> getRevisor() {
		return revisores;
	}

	public void setRevisor(List<Usuario> revisor) {
		this.revisores = revisor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + estado;
		result = prime * result + Arrays.hashCode(evidencia);
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + (int) (idTrazabilidad ^ (idTrazabilidad >>> 32));
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
		Trazabilidad other = (Trazabilidad) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (estado != other.estado)
			return false;
		if (!Arrays.equals(evidencia, other.evidencia))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (idTrazabilidad != other.idTrazabilidad)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trazabilidad [idTrazabilidad=" + idTrazabilidad + ", fecha=" + fecha + ", hora=" + hora
				+ ", descripcion=" + descripcion + ", evidencia=" + Arrays.toString(evidencia) + ", proyecto="
				+ proyecto + ", estado=" + estado + "]";
	}
	
	
}






