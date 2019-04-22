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
@Table(name = "TBL_NOTIFICACION")
public class Notificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_NOTIFICACION")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idNotificacion;
	
	@Column(name = "DETALLE")
	private String detalle;	
	
	@Column(name = "ESTADO")
	private int estado;	
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;
		
	public Notificacion() {
		// TODO Auto-generated constructor stub
	}
	
	public Notificacion(String detalle, Usuario usuario, int estado) {
		super();
		this.detalle = detalle;
		this.usuario = usuario;
		this.estado = estado;
	}



	public long getIdNotificacion() {
		return idNotificacion;
	}

	public void setIdNotificacion(long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
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
		result = prime * result + (int) (idNotificacion ^ (idNotificacion >>> 32));
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
		Notificacion other = (Notificacion) obj;
		if (idNotificacion != other.idNotificacion)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Notificacion [idNotificacion=" + idNotificacion + ", detalle=" + detalle + ", usuario=" + usuario + "]";
	}
	
	
	
		
}
