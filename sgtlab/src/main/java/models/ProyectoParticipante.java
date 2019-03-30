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
	private boolean responsable; 
	
	
	
	public ProyectoParticipante() {
		// TODO Auto-generated constructor stub
	}
	
	public ProyectoParticipante(Usuario usuario, boolean responsable) {
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

	public boolean getResponsable() {
		return responsable;
	}

	public void setResponsable(boolean responsable) {
		this.responsable = responsable;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		ProyectoParticipante other = (ProyectoParticipante) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProyectoParticipante [idProyectoParticipante=" + idProyectoParticipante + ", proyecto=" + proyecto
				+ ", usuario=" + usuario + ", responsable=" + responsable + "]";
	}
	
	
	

}
