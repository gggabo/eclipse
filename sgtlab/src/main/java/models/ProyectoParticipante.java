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
@Table(name = "TBL_PROYECTOPARTICIPANTE")
public class ProyectoParticipante implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_PROYECTOPARTICIPANTE")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idProyectoParticipante;
	
	@ManyToOne//(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PROYECTO")
	private Proyecto proyecto;
	
	@ManyToOne//(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;
	
	@Column(name = "RESPONSABLE") 
	private int responsable; 
	
	public ProyectoParticipante() {
		// TODO Auto-generated constructor stub
	}
	
	public ProyectoParticipante(Usuario usuario, int responsable) {
		super();
		this.usuario = usuario;
		this.responsable = responsable;
	}

	public long getIdProyectoParticipante() {
		return idProyectoParticipante;
	}

	public void setIdProyectoParticipante(long idProyectoParticipante) {
		this.idProyectoParticipante = idProyectoParticipante;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getResponsable() {
		return responsable;
	}

	public void setResponsable(int responsable) {
		this.responsable = responsable;
	}
	
	
	

}
