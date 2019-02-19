package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "TBL_USUARIO")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_USUARIO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "CEDULA")
	private String Cedula;

	@Column(name = "NOMBRE_UNO")
	private String nombre_uno;
	
	@Column(name = "NOMBRE_DOS")
	private String nombre_dos;
	
	@Column(name = "APELLIDO_PATERNO")
	private String apellido_paterno;
	
	@Column(name = "APELLIDO_MATERNO")
	private String apellido_materno;
	
	@Column(name = "CORREO")
	private String correo;
	
	@Column(name = "TELEFONO")
	private String telefono;
	
	@Lob
  //  @Column(name="IMAGEN", nullable=false, columnDefinition="mediumblob")  
	@Column(name="IMAGEN", columnDefinition="mediumblob")
    private byte[] imagen;
		
	@Column(name = "NOMBRE_USUARIO")
	private String nombre_usuario;
	
	@Column(name = "CLAVE")
	private String clave;
	
	@ManyToMany(fetch = FetchType.EAGER)
	//@OrderBy("apellido_paterno, apellido_materno")
	@JoinTable(name = "TBL_USUARIO_ROL", joinColumns= @JoinColumn(name = "ID_USUARIO"), 
	inverseJoinColumns = @JoinColumn(name ="ID_ROL"))
	private List<Rol> roles = new ArrayList<>();
	
	@Column(name = "ESTADO") 
	private int estado; 

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(String cedula ,String nombre_uno, String nombre_dos, String apellido_paterno, String apellido_materno,
			String correo, String telefono, byte[] imagen, String nombre_usuario, String clave, int estado) {
		super();
		this.Cedula = cedula;
		this.nombre_uno = nombre_uno;
		this.nombre_dos = nombre_dos;
		this.apellido_paterno = apellido_paterno;
		this.apellido_materno = apellido_materno;
		this.correo = correo;
		this.telefono = telefono;
		this.imagen = imagen;
		this.nombre_usuario = nombre_usuario;
		this.clave = clave;
		this.estado = estado;
	}

	public String getCedula() {
		return Cedula;
	}

	public void setCedula(String cedula) {
		Cedula = cedula;
	}

	public String getNombre_uno() {
		return nombre_uno;
	}

	public void setNombre_uno(String nombre_uno) {
		this.nombre_uno = nombre_uno;
	}

	public String getNombre_dos() {
		return nombre_dos;
	}

	public void setNombre_dos(String nombre_dos) {
		this.nombre_dos = nombre_dos;
	}

	public String getApellido_paterno() {
		return apellido_paterno;
	}

	public void setApellido_paterno(String apellido_paterno) {
		this.apellido_paterno = apellido_paterno;
	}

	public String getApellido_materno() {
		return apellido_materno;
	}

	public void setApellido_materno(String apellido_materno) {
		this.apellido_materno = apellido_materno;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	
	public void addUsuarioRol(Rol rol) {
		if(!roles.contains(rol)) {
			roles.add(rol);
		}
	}
	
	public void removeRolUsuario(Rol rol) {
		if(roles.contains(rol)) {
			roles.remove(rol);
		}
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre_uno=" + nombre_uno + ", nombre_dos=" + nombre_dos + ", apellido_paterno="
				+ apellido_paterno + ", apellido_materno=" + apellido_materno + ", correo=" + correo + ", telefono="
				+ telefono + ", imagen=" + Arrays.toString(imagen) + ", nombre_usuario=" + nombre_usuario + ", clave="
				+ clave + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido_materno == null) ? 0 : apellido_materno.hashCode());
		result = prime * result + ((apellido_paterno == null) ? 0 : apellido_paterno.hashCode());
		result = prime * result + ((clave == null) ? 0 : clave.hashCode());
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + Arrays.hashCode(imagen);
		result = prime * result + ((nombre_dos == null) ? 0 : nombre_dos.hashCode());
		result = prime * result + ((nombre_uno == null) ? 0 : nombre_uno.hashCode());
		result = prime * result + ((nombre_usuario == null) ? 0 : nombre_usuario.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
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
		Usuario other = (Usuario) obj;
		if (apellido_materno == null) {
			if (other.apellido_materno != null)
				return false;
		} else if (!apellido_materno.equals(other.apellido_materno))
			return false;
		if (apellido_paterno == null) {
			if (other.apellido_paterno != null)
				return false;
		} else if (!apellido_paterno.equals(other.apellido_paterno))
			return false;
		if (clave == null) {
			if (other.clave != null)
				return false;
		} else if (!clave.equals(other.clave))
			return false;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		if (id != other.id)
			return false;
		if (!Arrays.equals(imagen, other.imagen))
			return false;
		if (nombre_dos == null) {
			if (other.nombre_dos != null)
				return false;
		} else if (!nombre_dos.equals(other.nombre_dos))
			return false;
		if (nombre_uno == null) {
			if (other.nombre_uno != null)
				return false;
		} else if (!nombre_uno.equals(other.nombre_uno))
			return false;
		if (nombre_usuario == null) {
			if (other.nombre_usuario != null)
				return false;
		} else if (!nombre_usuario.equals(other.nombre_usuario))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

	
	
	
	
}
