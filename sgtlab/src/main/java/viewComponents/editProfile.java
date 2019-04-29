package viewComponents;

import java.io.Serializable;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import utils.UploadImage;

public class editProfile extends VerticalLayout implements Serializable {
	private static final long serialVersionUID = 1L;

	public editProfile() {
		setMargin(false);
		setPlaceHolder();
		setCss();
		addComponent(buildUI());
	}
	
	public FormLayout mainFrm;
	public HorizontalLayout toolbar = new HorizontalLayout();
	public VerticalLayout usuarioLayout = new VerticalLayout();
	public TextField nombre_uno = new TextField("Primer nombre");
	public TextField nombre_dos = new TextField("Segundo nombre");
	public TextField apellido_paterno = new TextField("Apellido paterno");
	public TextField apellido_materno = new TextField("Apellido materno");
	public TextField correo = new TextField("Correo electrónico");
	public TextField telefono = new TextField("Teléfono");
	public TextField cedula = new TextField("Cedula/Pasaporte");
	public TextField nombre_usuario = new TextField("Usuario");
	public PasswordField claveUsuario = new PasswordField("Clave");
	public UploadImage uploadField = new UploadImage();
	
	public Component buildUI() {
		mainFrm = new FormLayout();
		mainFrm.addComponents(cedula, apellido_paterno, apellido_materno,nombre_uno, nombre_dos, 
				correo, telefono, nombre_usuario, claveUsuario);
		mainFrm.setMargin(false);
		mainFrm.setSpacing(false);		
		
		uploadField.setFileSizeLimit(1.0F);
		
		HorizontalLayout layoutFormImg = new HorizontalLayout();
		layoutFormImg.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		layoutFormImg.addComponents(mainFrm,uploadField);
		
		return layoutFormImg;
	}
	
	public void setCss() {
		apellido_paterno.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		apellido_materno.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		nombre_uno.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		nombre_dos.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		correo.setStyleName(ValoTheme.TEXTFIELD_SMALL); 
		telefono.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		cedula.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		nombre_usuario.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		claveUsuario.setStyleName(ValoTheme.TEXTFIELD_SMALL);
	} 
	
	private void setPlaceHolder() {
		nombre_uno.setPlaceholder("Freddy");
		nombre_dos.setPlaceholder("Rodrigo");
		apellido_paterno.setPlaceholder("Sánchez");
		apellido_materno.setPlaceholder("Moreira");
		correo.setPlaceholder("usuario@mail.com"); 
		telefono.setPlaceholder("09999999999");
		cedula.setPlaceholder("1313253658");
		nombre_usuario.setPlaceholder("fsanchez1234");
		claveUsuario.setPlaceholder("123456789");	
	}
	
}
