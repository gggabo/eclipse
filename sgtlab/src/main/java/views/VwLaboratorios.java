package views;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.LocalDateRenderer;
import com.vaadin.ui.themes.ValoTheme;

import controllers.LabotatorioController;
import controllers.ReactivoController;
import controllers.UnidadController;
import models.Laboratorio;
import models.Reactivo;
import models.Rol;
import models.Unidad;
import models.Usuario;
import utils.UploadImage;
import utils.dialogWindow;
import utils.uploadXls;
public class VwLaboratorios extends VerticalLayout implements View, Serializable{
	private static final long serialVersionUID = 1L;

	public VwLaboratorios() {
		addComponent(buildUI()); 
		setCss();
		setPlaceHolder();
		setMaxLengthText();
		addValidation();
		cargarDatos();
		buildUser();
		initComponents();
		addStyleName("custom-margin-layout");
	}
	
	public VerticalLayout mainLayout = new VerticalLayout();
	public Panel pnlPrincipal = new Panel();
	public FormLayout mainFrm;
	public HorizontalLayout toolbar = new HorizontalLayout();
	public ComboBox<Laboratorio> cmbLaboratorio = new ComboBox<>();
	public List<Laboratorio> listLab = new ArrayList<>();
	public Button btnAddLaboratorio = new Button(VaadinIcons.PLUS_CIRCLE);
	
	public TabSheet tabSheet = new TabSheet();
	
	public Panel pnlReactivos = new Panel();
	public HorizontalLayout toolbarReactivo = new HorizontalLayout();
	public VerticalLayout laboratorioReactivoLayout = new VerticalLayout();
	public MenuBar mainMenuRectivo = new MenuBar();
	public CssLayout filteringReactivo = new CssLayout();
	public Button clearReactivoFilter = new Button(VaadinIcons.CLOSE_CIRCLE);
	public TextField filterReactivotxt = new TextField();
	public Grid<Reactivo> gridReactivo = new Grid<>();
	public List<Reactivo> listReactivos = new ArrayList<>();
	public dialogWindow dialogReactivoWindow = new dialogWindow("Gestión de reactivos", VaadinIcons.FLASK);
	String reactivoOptions = "guardar";
	public FormLayout formLayoutReactivo = new FormLayout();
	public TextField codigoReactivo = new TextField("Código");
	public TextField nombreReactivo = new TextField("Nombre");
	public TextField entradaReactivo = new TextField("Entrada");
	public TextField gastoRectivo = new TextField("Gasto");
	public TextField saldoRectivo = new TextField("Saldo");
	public ComboBox<Unidad> cmbUnidad = new ComboBox<>();
	public List<Unidad> listUnidad  = new ArrayList<>();
	public Button btnAddUnidad = new Button(VaadinIcons.PLUS_CIRCLE);
	public DateField fechaCaducidadRectivo = new DateField("Fecha caducidad");
	
	
	public TextField cedula = new TextField("Cedula/Pasaporte");
	public TextField nombre_usuario = new TextField("Usuario");
			
	public Panel pnlEquipos = new Panel();
	public HorizontalLayout toolbarEquipos = new HorizontalLayout();
	public VerticalLayout laboratorioEquipoLayout = new VerticalLayout();
	public MenuBar mainMenuEquipo = new MenuBar();
	public Grid<Usuario> gridEquipo= new Grid<>(Usuario.class);
	
	public Panel pnlMateriales = new Panel();
	public HorizontalLayout toolbarMateriales = new HorizontalLayout();
	public VerticalLayout laboratorioMaterialesLayout = new VerticalLayout();
	public MenuBar mainMenuMateriales = new MenuBar();
	public Grid<Usuario> gridMaterial= new Grid<>(Usuario.class);
	
	public VerticalLayout laboratorioLayout = new VerticalLayout();
	
	public PasswordField clave = new PasswordField("Clave");
	public Button btnGuardar = new Button("Guardar");
	public MenuBar mainMenu = new MenuBar();
	public MenuItem subMenu;
	public UploadImage uploadField = new UploadImage();
	public uploadXls uploadXls = new uploadXls();
	public String accion = "guardar"; 
		
	public ComboBox<Rol> cmbRol = new ComboBox<>();
	
	public Grid<Rol> gridRol = new Grid<>();
	public List<Rol> listGridRol = new ArrayList<>();
		
	public Grid<Usuario> gridUsuarioImport = new Grid<>();
	public List<Usuario> listUsuariosImport = new ArrayList<>();
	
	public Component buildUI() {
						
		cmbLaboratorio.setWidth("35%");
		cmbLaboratorio.setEmptySelectionCaption("Seleccionar laboratorio");
		
		cmbLaboratorio.addValueChangeListener(e ->{
			listReactivos.clear();
			if(cmbLaboratorio.getValue()==null) {
				
				return;
			}
			
			listReactivos.addAll(ReactivoController.getAllReactiveByLaboratory(cmbLaboratorio.getValue().getIdLaboratorio()));
			gridReactivo.setItems(listReactivos);
		});
		
		toolbar.setWidth("100%");
		toolbar.setSpacing(true);
		toolbar.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbar.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbar.setResponsive(true);
		toolbar.addComponents(cmbLaboratorio,btnAddLaboratorio);
		 
		tabSheet.addTab(pnlReactivos,"Reactivos",new ThemeResource("images/quimica.png"));
		tabSheet.addTab(pnlEquipos,"Equipos",new ThemeResource("images/microscopio.png"));
		tabSheet.addTab(pnlMateriales,"Materiales",new ThemeResource("images/mortero.png"));
		 
		//**REACTIVO**//
		toolbarReactivo.setWidth("100%");
		toolbarReactivo.setSpacing(true);
		toolbarReactivo.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarReactivo.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarReactivo.setResponsive(true);
		toolbarReactivo.addComponents(mainMenuRectivo);
		
		mainMenuRectivo.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuRectivo.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuRectivo.setResponsive(true); 
		
		mainMenuRectivo.addItem("Nuevo reactivo" , VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
			    newEditReactivo(null);
				reactivoOptions = "guardar";
			}
		});	
		
		filterReactivotxt.setPlaceholder("Buscar por codigo o nombre");
		filterReactivotxt.setValueChangeMode(ValueChangeMode.LAZY);
		filterReactivotxt.setSizeFull();
		filterReactivotxt.addValueChangeListener(e ->{
			/*listUsuarios.clear();
			listUsuarios.addAll(UsuarioController.search(filtertxt.getValue()));
			gridUsuario.setItems(listUsuarios);*/
			
			listReactivos.clear();
			listReactivos.addAll(ReactivoController.searchReactiveByLaboratory(cmbLaboratorio.getValue(),filterReactivotxt.getValue() ));
			//System.out.println(listReactivos);
			gridReactivo.setItems(listReactivos);
		}); 
		
		clearReactivoFilter.addClickListener(e->{
			filterReactivotxt.clear();
		});
		
		filteringReactivo.addComponents(filterReactivotxt,clearReactivoFilter);
		filteringReactivo.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filteringReactivo.addStyleName("custom-margins");
		
		gridReactivo.addColumn(Reactivo::getCodigo).setCaption("CÓDIGO").setId("CODIGO");
		gridReactivo.addColumn(Reactivo::getNombre).setCaption("NOMBRE");
		gridReactivo.addColumn(Reactivo-> Reactivo.getEntrada()+" "+Reactivo.getUnidad().getNombre().toLowerCase()).setCaption("ENTRADA").setId("ENTRADA");
		gridReactivo.addColumn(Reactivo-> Reactivo.getGasto()+" "+Reactivo.getUnidad().getNombre().toLowerCase()).setCaption("GASTO").setId("GASTO");
		gridReactivo.addColumn(Reactivo-> Reactivo.getSaldo()+" "+Reactivo.getUnidad().getNombre().toLowerCase()).setCaption("SALDO").setId("SALDO");
		gridReactivo.addColumn(Reactivo-> Reactivo.getFechaCaducidad(), new LocalDateRenderer("dd/MM/yyyy")).setCaption("F. CADUCIDAD").setId("FCADUCIDAD");
		 
		gridReactivo.getColumn("CODIGO").setMaximumWidth(90);
		gridReactivo.getColumn("CODIGO").setResizable(false);
		
		gridReactivo.getColumn("ENTRADA").setMaximumWidth(100);
		gridReactivo.getColumn("ENTRADA").setResizable(false);
		
		gridReactivo.getColumn("GASTO").setMaximumWidth(100);
		gridReactivo.getColumn("GASTO").setResizable(false);
		
		gridReactivo.getColumn("SALDO").setMaximumWidth(100); 
		gridReactivo.getColumn("SALDO").setResizable(false);
		
		gridReactivo.getColumn("FCADUCIDAD").setMaximumWidth(130);
		gridReactivo.getColumn("FCADUCIDAD").setResizable(false);
		
		gridReactivo.addComponentColumn(Reactivo -> {
			 
			Button b = new Button("Editar"); 
			b.addClickListener(clickb ->{ 
				newEditReactivo(Reactivo);
				
				codigoReactivo.setValue(Reactivo.getCodigo());
				nombreReactivo.setValue(Reactivo.getNombre());
				entradaReactivo.setValue(Reactivo.getNombre());
				gastoRectivo.setValue(Reactivo.getNombre());
				saldoRectivo.setValue(Reactivo.getNombre());
				cmbUnidad.setValue(Reactivo.getUnidad());
				fechaCaducidadRectivo.setValue( LocalDate.now());
				
				/*cedula.setValue(Usuario.getCedula());
				apellido_paterno.setValue(Usuario.getApellido_paterno());
				apellido_materno.setValue(Usuario.getApellido_materno());
				nombre_uno.setValue(Usuario.getNombre_uno());
				nombre_dos.setValue(Usuario.getNombre_dos());
				correo.setValue(Usuario.getCorreo()); 
				telefono.setValue(Usuario.getTelefono());
				nombre_usuario.setValue(Usuario.getNombre_usuario());
				clave.setValue("");
				uploadField.setValue(Usuario.getImagen());	
				
				listGridRol.addAll(Usuario.getRoles());
				
				gridRol.setItems(listGridRol);
	
				accion = "modificar";*/
				
			});
			b.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			b.setIcon(VaadinIcons.EDIT);
			
			Button b2 = new Button("Eliminar");
			b2.addClickListener(clickb2 ->{
				/*listUsuarios.remove(Usuario);
				gridReactivo.setItems(listUsuarios);
				Usuario.setEstado(0);
				UsuarioController.update(Usuario);
				message.warringMessage("Usuario eliminado");*/
			});
			b2.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			b2.setIcon(VaadinIcons.ERASER);
			
			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.addComponents(b,b2);
			return hl;			
		}).setCaption("Opciones");
		
		gridReactivo.setWidth("100%");
		gridReactivo.setSelectionMode(SelectionMode.NONE);
		
		pnlReactivos.setCaption("Gestión de reactivos");
		pnlReactivos.setIcon(VaadinIcons.FLASK);
		pnlReactivos.setContent(laboratorioReactivoLayout); 
		
		laboratorioReactivoLayout.addComponents(toolbarReactivo, filteringReactivo,gridReactivo);
		laboratorioReactivoLayout.setMargin(false);
		//**FIN REACTIVO**//
		
		//**EQUIPO**//
		toolbarEquipos.setWidth("100%");
		toolbarEquipos.setSpacing(true);
		toolbarEquipos.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarEquipos.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarEquipos.setResponsive(true);
		toolbarEquipos.addComponents(mainMenuEquipo);

		mainMenuEquipo.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuEquipo.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuEquipo.setResponsive(true); 

		mainMenuEquipo.addItem("Nuevo equipo" , VaadinIcons.PLUS_CIRCLE, null);	

		gridEquipo.setColumns("apellido_paterno");

		gridEquipo.setWidth("100%");
		gridEquipo.setSelectionMode(SelectionMode.NONE);

		pnlEquipos.setCaption("Gestión de equipos");
		pnlEquipos.setIcon(VaadinIcons.FLASK);
		pnlEquipos.setContent(laboratorioEquipoLayout); 

		laboratorioEquipoLayout.addComponents(toolbarEquipos,gridEquipo);
		laboratorioEquipoLayout.setMargin(false);
		//**FIN EQUIPO**//

		//**MATERIAL**//
		toolbarMateriales.setWidth("100%");
		toolbarMateriales.setSpacing(true);
		toolbarMateriales.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarMateriales.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarMateriales.setResponsive(true);
		toolbarMateriales.addComponents(mainMenuMateriales);

		mainMenuMateriales.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuMateriales.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuMateriales.setResponsive(true); 

		mainMenuMateriales.addItem("Nuevo material" , VaadinIcons.PLUS_CIRCLE, null);	

		gridMaterial.setColumns("apellido_paterno");

		gridMaterial.setWidth("100%");
		gridMaterial.setSelectionMode(SelectionMode.NONE);

		pnlMateriales.setCaption("Gestión de materiales");
		pnlMateriales.setIcon(VaadinIcons.FLASK);
		pnlMateriales.setContent(laboratorioMaterialesLayout); 

		laboratorioMaterialesLayout.addComponents(toolbarMateriales,gridMaterial);
		laboratorioMaterialesLayout.setMargin(false);
		//**FIN MATERIAL**// 
		
		
		pnlPrincipal.setCaption("Gestión de laboratorios");
		pnlPrincipal.setIcon(VaadinIcons.FLASK);
		pnlPrincipal.setContent(laboratorioLayout);
		
		laboratorioLayout.addComponents(toolbar, tabSheet);
		laboratorioLayout.setMargin(false);
		
		mainLayout.addComponents(pnlPrincipal);
		return mainLayout;
	}
	
	HorizontalLayout hl = new HorizontalLayout();
	public void newEditReactivo(Reactivo react) {
		limpiarReactivo();
		
		hl.setCaption("Unidad");
		hl.setSpacing(false);
		hl.setMargin(false);
		hl.addComponents(cmbUnidad,btnAddUnidad);
		
		formLayoutReactivo.setSpacing(false);
		formLayoutReactivo.setMargin(false);
		formLayoutReactivo.addComponents(codigoReactivo,nombreReactivo,entradaReactivo,gastoRectivo, saldoRectivo, hl,fechaCaducidadRectivo);
		
		dialogReactivoWindow.getOkButton().addClickListener(new ClickListener() {	
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
								
				if(accion.equals("guardar")) {
					 
					/*if (!validator.isValid()) {
						validator.validate();
						
						message.warringMessage("Hay errores en los campos de texto");
						return;
					}

					if(UsuarioController.DBcontainsUser(cedula.getValue())) {
						message.warringMessage("El usuario ya se encuentra registrado");
						return;
					}
					
					Usuario us = new Usuario(cedula.getValue().toUpperCase().trim(),nombre_uno.getValue().toUpperCase().trim(), 
							nombre_dos.getValue().toUpperCase().trim(), apellido_paterno.getValue().toUpperCase().trim(), 
							apellido_materno.getValue().toUpperCase().trim(),correo.getValue().trim(), telefono.getValue().trim(), 
							uploadField.getValue(),nombre_usuario.getValue().toLowerCase().trim(), DigestUtils.sha1Hex(clave.getValue().trim()),1);
					
					us.setRoles(listGridRol);
					
					UsuarioController.save(us); */
				}else {
				/*	user.setCedula(cedula.getValue().toUpperCase().trim());
					user.setApellido_paterno(apellido_paterno.getValue().toUpperCase().trim());
					user.setApellido_materno(apellido_materno.getValue().toUpperCase().trim());
					user.setNombre_uno(nombre_uno.getValue().toUpperCase().trim());
					user.setNombre_dos(nombre_dos.getValue().toUpperCase().trim());
					user.setCorreo(correo.getValue().trim());
					user.setTelefono(telefono.getValue().trim());
					user.setImagen(uploadField.getValue());
					user.setNombre_usuario(nombre_usuario.getValue().toLowerCase().trim());
										
					if(!clave.getValue().isEmpty()) {
						user.setClave(DigestUtils.sha1Hex(clave.getValue().trim()));
					}
										
					user.setRoles(listGridRol);
					
					UsuarioController.update(user);*/
				}
				 
				//cargarDatos();
				dialogReactivoWindow.close();
			}
		});
		
		dialogReactivoWindow.getCancelButton().addClickListener(e->{
			dialogReactivoWindow.close();
		});
		
		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("32%");
		dialogReactivoWindow.addComponentBody(formLayoutReactivo); 
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}
	
	public void limpiarReactivo() {
		
	}
	
	public void userNewEdit(Usuario user) {
	/*	limpiar(); 
		
		btnAddRol.setHeight("24px");
		btnAddRol.addClickListener(e->{
			if(cmbRol.getValue()==null) {
				message.warringMessage("Debe seleccionar un rol de la lista");
				return;
			}
			addRol();
			
		});
		
		
		cmbRol.setWidth("130px");
		
		layoutRol.addComponents(cmbRol,btnAddRol);
		layoutRol.setMargin(false);
		layoutRol.setSpacing(false);
		
		layoutRolGrid.setCaption("Rol de usuario");
		layoutRolGrid.setMargin(false);
		layoutRolGrid.setSpacing(false);
		layoutRolGrid.addComponents(layoutRol);
		
		mainFrm = new FormLayout();
		mainFrm.addComponents(cedula, apellido_paterno, apellido_materno,nombre_uno, nombre_dos, 
				correo, telefono, nombre_usuario, clave, layoutRolGrid);
		mainFrm.setMargin(false);
		mainFrm.setSpacing(false);	
		
		VerticalLayout layoutUpload = new VerticalLayout();
		layoutUpload.addComponents(uploadField, gridRol);
		layoutUpload.setCaption("Foto de perfil (Max 3Mb)");
		layoutUpload.setSpacing(true);
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

					if(UsuarioController.DBcontainsUser(cedula.getValue())) {
						message.warringMessage("El usuario ya se encuentra registrado");
						return;
					}
					
					Usuario us = new Usuario(cedula.getValue().toUpperCase().trim(),nombre_uno.getValue().toUpperCase().trim(), 
							nombre_dos.getValue().toUpperCase().trim(), apellido_paterno.getValue().toUpperCase().trim(), 
							apellido_materno.getValue().toUpperCase().trim(),correo.getValue().trim(), telefono.getValue().trim(), 
							uploadField.getValue(),nombre_usuario.getValue().toLowerCase().trim(), DigestUtils.sha1Hex(clave.getValue().trim()),1);
					
					us.setRoles(listGridRol);
					
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
										
					if(!clave.getValue().isEmpty()) {
						user.setClave(DigestUtils.sha1Hex(clave.getValue().trim()));
					}
										
					user.setRoles(listGridRol);
					
					UsuarioController.update(user);
				}
				
				cargarDatos();
				dialogWindow.close();
			}
		});
		
		dialogWindow.getCancelButton().addClickListener(e->{
			dialogWindow.close();
		});
		
		dialogWindow.setResponsive(true);
		dialogWindow.addComponentBody(layoutFormImg); 
		UI.getCurrent().addWindow(dialogWindow);*/
	}
	
    private void initComponents() {
    	
	}
	
	private void cargarDatos() {		
		listLab = LabotatorioController.findAll(); 
		createMenu();
		
		listUnidad = UnidadController.findAll();
		cmbUnidad.setItems(listUnidad);
		cmbUnidad.setItemCaptionGenerator(Unidad::getNombre);
		cmbUnidad.setEmptySelectionCaption("Seleccionar");
	} 
	
	public void setCss() {
		
		//LABORATORIO

		cmbLaboratorio.addStyleName(ValoTheme.COMBOBOX_SMALL);
		btnAddLaboratorio.addStyleName(ValoTheme.BUTTON_SMALL);
		btnAddLaboratorio.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		
		//REACTIVO
		codigoReactivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		nombreReactivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		entradaReactivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		gastoRectivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		saldoRectivo.setStyleName(ValoTheme.TEXTFIELD_SMALL); 
		cmbUnidad.addStyleName(ValoTheme.COMBOBOX_TINY);
		btnAddUnidad.addStyleName(ValoTheme.BUTTON_SMALL);
		btnAddUnidad.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		fechaCaducidadRectivo.setStyleName(ValoTheme.DATEFIELD_TINY);
		filterReactivotxt.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		filterReactivotxt.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		filterReactivotxt.setIcon(VaadinIcons.SEARCH);
		clearReactivoFilter.addStyleName(ValoTheme.BUTTON_SMALL);
		
		//EQUIPO
		
		//MATERIAL
		
		cedula.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		nombre_usuario.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		clave.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		cmbRol.setStyleName(ValoTheme.COMBOBOX_TINY);
		/*btnAddRol.addStyleName(ValoTheme.BUTTON_SMALL);
		btnAddRol.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);*/
		gridRol.addStyleName(ValoTheme.TABLE_SMALL);
		gridRol.addStyleName(ValoTheme.TABLE_COMPACT); 
		//cmbLaboratorio.addStyleName(ValoTheme.COMBOBOX_);
	}
	
	private void createMenu() {
        Iterator<Laboratorio> iteratorLab = listLab.iterator();
		
		Laboratorio lab;
		
		//while(iteratorLab.hasNext()) {
			cmbLaboratorio.setItems(listLab);
			cmbLaboratorio.setItemCaptionGenerator(Laboratorio::getNombre);
			/*lab = iteratorLab.next();
			subMenu.addItem(lab.getNombre(), VaadinIcons.FLASK, null);*/
		//}
	}
	
	private void addRol() {
				
		Rol rol = cmbRol.getValue();
		boolean c = false;
		
		for(Rol r:listGridRol) {
			if(r.getIdRol() == rol.getIdRol()) {
				c=true;
				break;
			}
		}
		if(!c) {
			listGridRol.add(rol);
			gridRol.setItems(listGridRol);
		}
		
	}
	
	private void setPlaceHolder() {
		codigoReactivo.setPlaceholder("LQ001");
		nombreReactivo.setPlaceholder("CLOR-ANTIMONIO");
		entradaReactivo.setPlaceholder("Sánchez");
		gastoRectivo.setPlaceholder("Moreira");
		saldoRectivo.setPlaceholder("usuario@mail.com"); 
		
		cedula.setPlaceholder("1313253658");
		nombre_usuario.setPlaceholder("fsanchez1234");
		clave.setPlaceholder("123456789");	
	}
	
	private void setMaxLengthText() {
		/*nombre_uno.setMaxLength(20);
		nombre_dos.setMaxLength(20);
		apellido_paterno.setMaxLength(20);
		apellido_materno.setMaxLength(20);
		correo.setMaxLength(50);
		telefono.setMaxLength(20);*/
		cedula.setMaxLength(20);
		nombre_usuario.setMaxLength(20);
		clave.setMaxLength(20);
	}
	
	private void limpiar() {
		/*nombre_uno.setValue("");
		nombre_dos.setValue("");
		apellido_paterno.setValue("");
		apellido_materno.setValue("");
		correo.setValue(""); 
		telefono.setValue("");*/
		cedula.setValue("");
		nombre_usuario.setValue("");
		clave.setValue("");
		uploadField.clear();
		cmbRol.setValue(null);
		listGridRol.clear();
		//System.out.println(listGridRol);
		gridRol.setItems(listGridRol);
		
	}
	
	Binder<Usuario> validator = new Binder<>();
	
	private void addValidation() {
		
		validator.forField(cedula)
		.asRequired("Dato requerido")
		.bind(Usuario::getCedula, Usuario::setCedula);
		
		/*validator.forField(apellido_paterno)
		.asRequired("Dato requerido")
		.bind(Usuario::getApellido_paterno, Usuario::setApellido_paterno);
		
		validator.forField(nombre_uno)
		.asRequired("Dato requerido")
		.bind(Usuario::getNombre_uno, Usuario::setNombre_uno);*/
		
		/*validator.forField(correo).asRequired("Dato requerido")
		.withValidator(new EmailValidator("Error en formato de correo"))
		.bind(Usuario::getCorreo, Usuario::setCorreo);*/
				
	}
	
	private void buildUser() {
		/*cedula.addValueChangeListener(e ->{
			clave.setValue(e.getValue());
			
			if(!nombre_uno.getValue().isEmpty() && nombre_uno.getValue().trim().length() >=1) {
				varNombreUno =  nombre_uno.getValue().substring(0,1);
			}else {
				varNombreUno = "";
			}
			
			if(!cedula.getValue().isEmpty() && cedula.getValue().length() >=4) {
				varCedula = cedula.getValue().substring(cedula.getValue().trim().length() - 4 , cedula.getValue().trim().length());//4 ULTIMOS DÍGITOS DE CEDULA
			}else {
				varCedula = "";
			}
			
			varApellido = apellido_paterno.getValue().trim().replace(" ", "");
					
			nombre_usuario.setValue(varNombreUno+varApellido+varCedula);
		});
		
		apellido_paterno.addValueChangeListener(e->{
			if(!nombre_uno.getValue().isEmpty() && nombre_uno.getValue().trim().length() >=1) {
				varNombreUno =  nombre_uno.getValue().substring(0,1);
			}else {
				varNombreUno = "";
			}
			
			if(!cedula.getValue().isEmpty() && cedula.getValue().length() >=4) {
				varCedula = cedula.getValue().substring(cedula.getValue().trim().length() - 4 , cedula.getValue().trim().length());//4 ULTIMOS DÍGITOS DE CEDULA
			}else {
				varCedula = "";
			}
			
			varApellido = apellido_paterno.getValue().trim().replace(" ", "");;
					
			nombre_usuario.setValue(varNombreUno+varApellido+varCedula);
		});
		
		nombre_uno.addValueChangeListener(e->{
			if(!nombre_uno.getValue().isEmpty() && nombre_uno.getValue().trim().length() >=1) {
				varNombreUno =  nombre_uno.getValue().substring(0,1);
			}else {
				varNombreUno = "";
			}
			
			if(!cedula.getValue().isEmpty() && cedula.getValue().length() >=4) {
				varCedula = cedula.getValue().substring(cedula.getValue().trim().length() - 4 , cedula.getValue().trim().length());//4 ULTIMOS DÍGITOS DE CEDULA
			}else {
				varCedula = "";
			}
			
			varApellido = apellido_paterno.getValue().trim().replace(" ", "");;
					
			nombre_usuario.setValue(varNombreUno+varApellido+varCedula);
		});*/
	}

}
