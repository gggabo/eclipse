package views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import controllers.UsuarioController;
import models.Usuario;
import utils.UploadImage;
import utils.dialogWindow;
import utils.message;
public class VwUsuarios extends VerticalLayout implements View, Serializable{
	private static final long serialVersionUID = 1L;

	public VwUsuarios() {
		addComponent(buildUI()); 
		setCss();
		setPlaceHolder();
		setMaxLengthText();
		addValidation();
		cargarUsuarios();
	}
	
	public VerticalLayout mainLayout = new VerticalLayout();
	public Panel pnlPrincipal = new Panel();
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
	public PasswordField clave = new PasswordField("Clave");
	public Button btnGuardar = new Button("Guardar");
	public MenuBar mainMenu = new MenuBar();
	public UploadImage uploadField = new UploadImage();
	public String accion = "guardar"; 
	
	public Grid<Usuario> gridUsuario = new Grid<>();
	public List<Usuario> listUsuarios = new ArrayList<>();
		
	public Component buildUI() {
		
		
		toolbar.setWidth("100%");
		toolbar.setSpacing(true);
		toolbar.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbar.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbar.setResponsive(true);
		toolbar.addComponents(mainMenu);
		
		mainMenu.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenu.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenu.setResponsive(true);
		mainMenu.addItem("Nuevo usuario", VaadinIcons.USER_CHECK, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				userNewEdit(null);
				accion="guardar";
			}
		});		
		
		mainMenu.addItem("Imprimir", VaadinIcons.PRINT, null);				
		mainMenu.addItem("Importar usuarios", VaadinIcons.INSERT, null);
		
		listUsuarios.add(new Usuario("1313253575","GABRIEL", "GREGORIO", "SALVATIERRA", 
				"TUMBACO", "gsalvatierra3575@gmail.com", "0988171984", null, "gsalvatierra3575", "123456",1));
		gridUsuario.setItems(listUsuarios);
		gridUsuario.addColumn(Usuario::getCedula).setCaption("Cedula");
		gridUsuario.addColumn(Usuario -> Usuario.getNombre_uno() +" "+ Usuario.getNombre_dos()+" "+
				Usuario.getApellido_paterno() +" "+ Usuario.getApellido_materno()).setCaption("NOMBRES Y APELLIDOS");
		gridUsuario.addColumn(Usuario::getNombre_usuario).setCaption("USUARIO");
		gridUsuario.setWidth("100%");
		gridUsuario.setSelectionMode(SelectionMode.NONE);
		gridUsuario.addComponentColumn(Usuario -> {
			
			Button b = new Button("Editar");
			b.addClickListener(clickb ->{
				userNewEdit(Usuario);
				cedula.setValue(Usuario.getCedula());
				apellido_paterno.setValue(Usuario.getApellido_paterno());
				apellido_materno.setValue(Usuario.getApellido_materno());
				nombre_uno.setValue(Usuario.getNombre_uno());
				nombre_dos.setValue(Usuario.getNombre_dos());
				correo.setValue(Usuario.getCorreo()); 
				telefono.setValue(Usuario.getTelefono());
				nombre_usuario.setValue(Usuario.getNombre_usuario());
				clave.setValue(Usuario.getClave());
				uploadField.setValue(Usuario.getImagen());
				
				accion = "modificar";
				
			});
			b.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			b.setIcon(VaadinIcons.EDIT);
			
			Button b2 = new Button("Eliminar");
			b2.addClickListener(clickb2 ->{
				listUsuarios.remove(Usuario);
				gridUsuario.setItems(listUsuarios);
				message.warringMessage("Usuario eliminado");
			});
			b2.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			b2.setIcon(VaadinIcons.ERASER);
			
			HorizontalLayout hl = new HorizontalLayout();
			hl.addComponents(b,b2);
			return hl;			
		}).setCaption("Opciones");

		usuarioLayout.addComponents(toolbar,gridUsuario);
		usuarioLayout.setMargin(false);
		
		pnlPrincipal.setCaption("Gestión de usuarios");
		pnlPrincipal.setIcon(VaadinIcons.USERS);
		pnlPrincipal.setContent(usuarioLayout);
		
		mainLayout.addComponents(pnlPrincipal);
		return mainLayout;
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
		clave.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		
	}
	
	public void userNewEdit(Usuario user) {
		limpiar(); 
		mainFrm = new FormLayout();
		mainFrm.addComponents(cedula, apellido_paterno, apellido_materno,nombre_uno, nombre_dos, 
				correo, telefono, nombre_usuario, clave);
		mainFrm.setMargin(false);
		mainFrm.setSpacing(false);	
		
		HorizontalLayout layoutUpload = new HorizontalLayout();
		layoutUpload.addComponents(uploadField);
		layoutUpload.setCaption("Foto de perfil (Max 5Mb)");
		layoutUpload.setSpacing(false);
		layoutUpload.setMargin(false);
		
		HorizontalLayout layoutFormImg = new HorizontalLayout();
		layoutFormImg.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		layoutFormImg.addComponents(mainFrm,layoutUpload);
		
		dialogWindow dialogWindow = new dialogWindow("Ingreso de usuarios", VaadinIcons.USERS);
		dialogWindow.getOkButton().addClickListener(new ClickListener() {	
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
								
				if(accion.equals("guardar")) {
					
					if (!validator.isValid()) {
						validator.validate();
						
						message.warringMessage("Hay errores en los campos de texto");
						return;
					}

					Usuario us = new Usuario(cedula.getValue().toUpperCase().trim(),nombre_uno.getValue().toUpperCase().trim(), 
							nombre_dos.getValue().toUpperCase().trim(), apellido_paterno.getValue().toUpperCase().trim(), 
							apellido_materno.getValue().toUpperCase().trim(),correo.getValue().trim(), telefono.getValue().trim(), 
							uploadField.getValue(),nombre_usuario.getValue().toLowerCase().trim(), clave.getValue().trim(),1);
					us.setNombre_uno(nombre_uno.getValue().toUpperCase());
					UsuarioController.save(us);
				}else {
					user.setCedula(cedula.getValue().toUpperCase().trim());
					user.setApellido_paterno(apellido_paterno.getValue().toUpperCase().trim());
					user.setApellido_materno(apellido_materno.getValue().toUpperCase().trim());
					user.setNombre_uno(nombre_uno.getValue().toUpperCase().trim());
					user.setNombre_dos(nombre_dos.getValue().toUpperCase().trim());
					user.setCorreo(correo.getValue().trim());
					user.setTelefono(telefono.getValue().trim());
					user.setImagen(uploadField.getValue());
					user.setNombre_usuario(nombre_usuario.getValue().toLowerCase().trim());
					user.setClave(clave.getValue().trim());
					UsuarioController.update(user);
				}
				
				cargarUsuarios();
				dialogWindow.close();
			}
		});
		dialogWindow.setResponsive(true);
		dialogWindow.addComponentBody(layoutFormImg);
		UI.getCurrent().addWindow(dialogWindow);
	}
		
	private void cargarUsuarios() {
		listUsuarios = UsuarioController.findAll();
		gridUsuario.setItems(listUsuarios);
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
		clave.setPlaceholder("123456789");	
	}
	
	private void setMaxLengthText() {
		nombre_uno.setMaxLength(20);
		nombre_dos.setMaxLength(20);
		apellido_paterno.setMaxLength(20);
		apellido_materno.setMaxLength(20);
		correo.setMaxLength(50);
		telefono.setMaxLength(20);
		cedula.setMaxLength(20);
		nombre_usuario.setMaxLength(20);
		clave.setMaxLength(20);
	}
	
	private void limpiar() {
		nombre_uno.setValue("");
		nombre_dos.setValue("");
		apellido_paterno.setValue("");
		apellido_materno.setValue("");
		correo.setValue(""); 
		telefono.setValue("");
		cedula.setValue("");
		nombre_usuario.setValue("");
		clave.setValue("");
		uploadField.clear();
		
		validator.removeBean();
	}
	
	Binder<Usuario> validator = new Binder<>();
	
	private void addValidation() {
		
		validator.forField(cedula)
		.asRequired("Dato requerido")
		.bind(Usuario::getCedula, Usuario::setCedula);
		
		validator.forField(apellido_paterno)
		.asRequired("Dato requerido")
		.bind(Usuario::getApellido_paterno, Usuario::setApellido_paterno);
		
		validator.forField(nombre_uno)
		.asRequired("Dato requerido")
		.bind(Usuario::getNombre_uno, Usuario::setNombre_uno);
		
		validator.forField(correo).asRequired("Dato requerido")
		.withValidator(new EmailValidator("Error en formato de correo"))
		.bind(Usuario::getCorreo, Usuario::setCorreo);
				
	}

}
