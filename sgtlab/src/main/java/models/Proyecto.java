package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity 
@Table(name = "TBL_PROYECTO")
public class Proyecto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_PROYECTO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idProyecto;
	
	@Column(name = "CODIGO")
	private String codigo;
	
	@Column(name = "FECHA_INICIO")
	private LocalDate fechaInicio;
	
	@Column(name = "FECHA_FIN")
	private LocalDate fechaFin;
	
	@Column(name = "HORA")
	private LocalTime hora;
	
	@ManyToOne//(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TIPOPROYECTO")
	private TipoProyecto tipoProyecto;
	
	@Column(name = "TEMA")
	private String tema;
	
	@Column(name = "DESCRIPCION") 
	private String descripcion;
	
	@Column(name = "ESTADO")
	private int estado;
	
	@OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProyectoParticipante>  proyectoParticipantes = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "TBL_PROYECTO_MATERIA", joinColumns= @JoinColumn(name = "ID_PROYECTO"), 
	inverseJoinColumns = @JoinColumn(name ="ID_MATERIA"))
	private List<Materia> materias = new ArrayList<>();
	
	@OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Trazabilidad>  trazabilidad = new ArrayList<>();
	
	@Column(name = "ESTADO_PROYECTO")
	private String estadoProyecto;
	
	public Proyecto() {
		// TODO Auto-generated constructor stub
	}

	public Proyecto(String codigo, LocalDate fechaInicio,LocalTime hora , TipoProyecto tipoProyecto, String tema, String descripcion,String estadoProyecto,
			int estado) {
		super();
		this.codigo = codigo;
		this.fechaInicio = fechaInicio;
		this.hora = hora;
		this.tipoProyecto = tipoProyecto;
		this.tema = tema;
		this.descripcion = descripcion;
		this.estadoProyecto = estadoProyecto;
		this.estado = estado;
	}
		
	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public List<Trazabilidad> getTrazabilidad() {
		return trazabilidad;
	}

	public void setTrazabilidad(List<Trazabilidad> trazabilidad) {
		this.trazabilidad = trazabilidad;
	}

	public String getEstadoProyecto() {
		return estadoProyecto;
	}

	public void setEstadoProyecto(String estadoProyecto) {
		this.estadoProyecto = estadoProyecto;
	}

	public long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public TipoProyecto getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(TipoProyecto tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public List<ProyectoParticipante> getProyectoParticipantes() {
		return proyectoParticipantes;
	}

	public void setProyectoParticipantes(List<ProyectoParticipante> proyectoParticipantes) {
		this.proyectoParticipantes = proyectoParticipantes;
	}	

	public List<Materia> getMaterias() {
		return materias;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + estado;
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + (int) (idProyecto ^ (idProyecto >>> 32));
		result = prime * result + ((tema == null) ? 0 : tema.hashCode());
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
		Proyecto other = (Proyecto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (estado != other.estado)
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		if (idProyecto != other.idProyecto)
			return false;
		if (tema == null) {
			if (other.tema != null)
				return false;
		} else if (!tema.equals(other.tema))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Proyecto [idProyecto=" + idProyecto + ", codigo=" + codigo + ", fecha=" + fechaInicio + ", tipoProyecto="
				+ tipoProyecto + ", tema=" + tema + ", descripcion=" + descripcion + ", estado=" + estado + "]";
	}
	
	
	
	
	
	
	
	
	
}
