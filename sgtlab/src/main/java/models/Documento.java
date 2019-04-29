package models;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity 
@Table(name = "TBL_DOCUMENTO")
public class Documento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_DOCUMENTO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idDocumento;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@Lob
	@Column(name="DOCUMENTO", columnDefinition="mediumblob")
	private byte[] documento;
	
	@Column(name = "ESTADO")
	private int estado;
	
	public Documento() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Documento(String nombre, String tipo, byte[] documento, int estado) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.documento = documento;
		this.estado = estado;
	}

    

	public int getEstado() {
		return estado;
	}


	public void setEstado(int estado) {
		this.estado = estado;
	}


	public long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(documento);
		result = prime * result + (int) (idDocumento ^ (idDocumento >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Documento other = (Documento) obj;
		if (!Arrays.equals(documento, other.documento))
			return false;
		if (idDocumento != other.idDocumento)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Documento [idDocumento=" + idDocumento + ", nombre=" + nombre + ", tipo=" + tipo + "]";
	}
	
	

}
