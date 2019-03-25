package views;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.vaadin.ui.NumberField;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import controllers.ComponenteController;
import controllers.MateriaController;
import controllers.MaterialController;
import controllers.ProyectoController;
import controllers.ProyectoParticipanteController;
import controllers.TipoProyectoController;
import controllers.UsuarioController;
import fi.jasoft.qrcode.QRCode;
import models.Componente;
import models.Equipo;
import models.Laboratorio;
import models.Materia;
import models.Material;
import models.Proyecto;
import models.ProyectoParticipante;
import models.Rol;
import models.TipoProyecto;
import models.Usuario;
import utils.classGeneradorCodigo;
import utils.dialogWindow;
import utils.message;
import viewComponents.panelProyecto;

public class VwProyectos extends VerticalLayout implements View, Serializable {
	private static final long serialVersionUID = -5759789185491799696L;
	
	public static final String VIEW_NAME = "proyectos";
	
	public VerticalLayout mainLayout = new VerticalLayout();
	public Panel pnlPrincipal = new Panel();
	public FormLayout mainFrm;
	public HorizontalLayout toolbar = new HorizontalLayout();
	public HorizontalLayout layoutProyectos = new HorizontalLayout();
	public VerticalLayout proyectoLayout = new VerticalLayout();
	public MenuBar mainMenu = new MenuBar();
	
	 public VwProyectos() {
		 addComponent(buildUI());
		 addStyleName("custom-margin-layout");
		 initUI();
		 setCss();
		 
		 initBuscarUsuario();
		 initBuscarMateria();
		
	}
	 
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
		mainMenu.addItem("Nuevo proyecto", VaadinIcons.USER_CHECK, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				/*userNewEdit(null);
				accion="guardar";*/
				buildNewProject();
			}
		});		
		
		mainMenu.addItem("Imprimir", VaadinIcons.PRINT, null);		
		
		proyectoLayout.addComponents(toolbar, buildUIProyect());
		proyectoLayout.setMargin(false);
		
		pnlPrincipal.setCaption("Gestión de proyectos");
		pnlPrincipal.setIcon(VaadinIcons.USERS);
		pnlPrincipal.setContent(proyectoLayout);
		
		mainLayout.addComponents(pnlPrincipal);
		return mainLayout;
		
	}
	
	public Component buildUIProyect() {
		panelProyecto p1 = new panelProyecto();
		//p1.addComponentBody(panelContent());
		p1.setCaption("Proyecto integrador");
		
		panelProyecto p2 = new panelProyecto();
		//p2.addComponentBody(panelContent());
		p2.setCaption("Proyecto de clases");
		 
		panelProyecto p3 = new panelProyecto();
		//p3.addComponentBody(panelContent());
		p3.setCaption("Practica clases");
		
		panelProyecto p4 = new panelProyecto();
		//p4.addComponentBody(panelContent());
		p4.setCaption("Trabajo titulación");
		
		layoutProyectos.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		layoutProyectos.addComponents(p1,p2,p3,p4);
		layoutProyectos.setMargin(true);
		
		return layoutProyectos;
	} 
	
	//PARTICIPANTE
	public Panel pnlProyectoParticipante = new Panel();
	public HorizontalLayout toolbarParticipante = new HorizontalLayout();
	public VerticalLayout proyectoParticipanteLayout = new VerticalLayout();
	public MenuBar mainMenuParticipante = new MenuBar();
	public Grid<Usuario> gridParticipante = new Grid<>();
	public List<Usuario> listParticipante = new ArrayList<>();
	
	//MATERIAS
	public Panel pnlProyectoMateria = new Panel();
	public HorizontalLayout toolbarMateria = new HorizontalLayout();
	public VerticalLayout proyectoMateriaLayout = new VerticalLayout();
	public MenuBar mainMenuMateria = new MenuBar();
	public Grid<Materia> gridMateria = new Grid<>();
	public List<Materia> listMateria = new ArrayList<>();

	private void initUI() {
		/*PARTICIPANTES*/
		toolbarParticipante.setWidth("100%");
		toolbarParticipante.setSpacing(true);
		toolbarParticipante.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarParticipante.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarParticipante.setResponsive(true);
		toolbarParticipante.addComponents(mainMenuParticipante);

		mainMenuParticipante.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuParticipante.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuParticipante.setResponsive(true);

		mainMenuParticipante.addItem("Agregar participante", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				buscarUsuario();
			}
		});
		
		gridParticipante.setRowHeight(35.00); 
		gridParticipante.setHeight("150px");
		gridParticipante.addColumn(Estudiante -> Estudiante.getNombre_uno()+" "+Estudiante.getNombre_dos()
		+" "+Estudiante.getApellido_paterno()+" "+Estudiante.getApellido_materno()).setCaption("NOMBRE").setExpandRatio(0);
		gridParticipante.setWidth("100%");
		gridParticipante.setSelectionMode(SelectionMode.NONE);
		
		List<Rol> rolesAsignados = new ArrayList<>();
		
		gridParticipante.addComponentColumn(Participante ->{
			CheckBox chk = new CheckBox();
			rolesAsignados.clear();
			rolesAsignados.addAll(Participante.getRoles());
			Iterator<Rol> rol = rolesAsignados.iterator();
			Long idrol;
			Boolean yesNo = false;
			while(rol.hasNext()) {
				idrol = rol.next().getIdRol();
				
				if(idrol == 2) {//2: DOCENTE
					yesNo = true;
				}
				
				if(yesNo) 
					chk.setVisible(true);
				else 
					chk.setVisible(false);
			}
			
			chk.addValueChangeListener(e ->{
				//if(e.getValue()) {
					Iterator<ProyectoParticipante> ppIterator = listProyectoParticipante.iterator();
					ProyectoParticipante pp;
					while(ppIterator.hasNext()) {
						pp = ppIterator.next();
						
						if(Participante.getId() == pp.getUsuario().getId()) {
							if(e.getValue()) {
								pp.setResponsable(1);
								System.out.println(pp.getUsuario().getNombre_usuario() +"1");		
							}else {						
							System.out.println(pp.getUsuario().getNombre_usuario()+"0");
							pp.setResponsable(0);
							}
						}
					}
				//}
			});
			
			return chk;
		}).setCaption("Resp.").setStyleGenerator(Participante -> "v-align-center");
		
		gridParticipante.addComponentColumn(Participante -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listParticipante.remove(Participante);
				gridParticipante.setItems(listParticipante);
				
				Iterator<ProyectoParticipante> ppIterator = listProyectoParticipante.iterator();
				ProyectoParticipante pp,pp2 = null;
				while(ppIterator.hasNext()) {
					pp = ppIterator.next();
					if(Participante.getId() == pp.getUsuario().getId()) {
						pp2 = pp;
					}
				}
				
				listProyectoParticipante.remove(pp2);
				
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
		}).setCaption("Opciones"); 
		
		proyectoParticipanteLayout.addComponents(toolbarParticipante, gridParticipante);
		proyectoParticipanteLayout.setMargin(false);
		
		pnlProyectoParticipante.setCaption("Participantes del proyecto");
		pnlProyectoParticipante.setIcon(VaadinIcons.GROUP);
		pnlProyectoParticipante.setContent(proyectoParticipanteLayout);
		/*FIN PARTICIPANTE*/
		
		/*MATERIAS*/
		toolbarMateria.setWidth("100%");
		toolbarMateria.setSpacing(true);
		toolbarMateria.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarMateria.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarMateria.setResponsive(true);
		toolbarMateria.addComponents(mainMenuMateria);

		mainMenuMateria.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuMateria.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuMateria.setResponsive(true);

		mainMenuMateria.addItem("Agregar materia", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				buscarMateria();
			}
		});
		
		gridMateria.setRowHeight(35.00); 
		gridMateria.setHeight("150px");
		gridMateria.addColumn(Materia -> Materia.getNombre()).setCaption("NOMBRE").setExpandRatio(0);
		gridMateria.setWidth("100%");
		gridMateria.setSelectionMode(SelectionMode.NONE);
		
		gridMateria.addComponentColumn(Materia -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listMateria.remove(Materia);
				gridMateria.setItems(listMateria);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
		}).setCaption("Opciones"); 
		
		proyectoMateriaLayout.addComponents(toolbarMateria, gridMateria);
		proyectoMateriaLayout.setMargin(false);
		
		pnlProyectoMateria.setCaption("Materias del proyecto involucradas");
		pnlProyectoMateria.setIcon(VaadinIcons.BOOK);
		pnlProyectoMateria.setContent(proyectoMateriaLayout);
		/*FIN MATERIAS*/
		
	}
	
	private FormLayout formProject = new FormLayout();
	private TextField codigoProject = new TextField("codigo");
	private List<TipoProyecto> listTipo = new ArrayList<>();
	private ComboBox<TipoProyecto> cmbTipo = new ComboBox<>("Tipo");
	private DateField fechaProyecto = new DateField("Fecha");
	private TextArea temaProject = new TextArea("Tema");
	private TextArea descripcionProject = new TextArea("Descripción");
	private Label lb1 = new Label("Información del proyecto");
	private QRCode qr = new QRCode();
	private TabSheet tabProyecto = new  TabSheet();	
	private List<ProyectoParticipante> listProyectoParticipante = new ArrayList<>();
	
	private void buildNewProject() {
		limpiarProyecto();
        cargarDatosProyecto();
		
		dialogWindow dialogReactivoWindow = new dialogWindow("Registro de proyectos", VaadinIcons.FLASK);
		
		/*listTipo.add("Proyecto integrador");
		cmbTipo.setItems(listTipo);
		cmbTipo.setTextInputAllowed(false);*/
						
		formProject.addComponents(lb1,codigoProject,fechaProyecto ,cmbTipo,temaProject,descripcionProject);
		
		/*formLayoutMaterial.setSpacing(false);
		formLayoutMaterial.setMargin(false);
		formLayoutMaterial.addComponents(codigoMaterial, nombreMaterial, marcaMaterial,tipoMaterial, capacidadMaterial, cantidadMaterial, observacionMaterial);
 */
		dialogReactivoWindow.getOkButton().addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				Proyecto p = new Proyecto(codigoProject.getValue(), fechaProyecto.getValue(), cmbTipo.getValue(), 
						temaProject.getValue(), descripcionProject.getValue(), 1);
				
				ProyectoController.save(p);
				
				Iterator<ProyectoParticipante> ppIterator = listProyectoParticipante.iterator();
				ProyectoParticipante pp;
				while(ppIterator.hasNext()) {
					pp = ppIterator.next();
					pp.setProyecto(p);
					ProyectoParticipanteController.save(pp);
				}
				
				listProyectoParticipante.clear();
				
			/*	if (!validatorMaterial.isValid()) {
					validatorMaterial.validate();

					message.warringMessage("Hay errores en los campos de texto");
					return;
				}

				if (materialAction.equals("guardar")) {
					
					if (MaterialController.DBcontainsCodMaterial(codigoMaterial.getValue())) {
						message.warringMessage("El codigo del material ya se encuentra registrado");
						return;
					} 
										
					Material mat = new Material(codigoMaterial.getValue().toUpperCase().trim(),nombreMaterial.getValue().toUpperCase().trim(),
							marcaMaterial.getValue().toUpperCase().trim(), tipoMaterial.getValue().toUpperCase().trim(), 
							capacidadMaterial.getValue().toUpperCase().trim(), Integer.parseInt(cantidadMaterial.getValue()), 
							observacionMaterial.getValue().toUpperCase().trim(), cmbLaboratorio.getValue(), 1);
					MaterialController.save(mat);
				} else {

					material.setCodigo(codigoMaterial.getValue().toUpperCase().trim());
					material.setNombre(nombreMaterial.getValue().toUpperCase().trim());
					material.setMarca(marcaMaterial.getValue().toUpperCase().trim());
					material.setTipoMaterial(tipoMaterial.getValue().toUpperCase().trim());
					material.setCapacidad(capacidadMaterial.getValue().toUpperCase().trim());
					material.setCantidad(Integer.parseInt(cantidadMaterial.getValue()));
					material.setObservacion(observacionMaterial.getValue().toUpperCase().trim());
					
					MaterialController.update(material);
					codigoMaterial.setReadOnly(false);
				}

				message.normalMessage("Acción realizada con éxito");

				cargarDatosMaterial();;*/
				dialogReactivoWindow.close();
			}
		});

		dialogReactivoWindow.getCancelButton().addClickListener(e -> {
			dialogReactivoWindow.close();
		});


		HorizontalLayout infProyecto = new HorizontalLayout();
		infProyecto.setMargin(false);
		infProyecto.setSpacing(false);
		infProyecto.addComponents(formProject,qr);
		infProyecto.setExpandRatio(formProject, 1);
		infProyecto.setSizeFull();
		
		tabProyecto.addTab(pnlProyectoParticipante,"Participantes",new ThemeResource("images/team.png"));
		tabProyecto.addTab(pnlProyectoMateria,"Materias",new ThemeResource("images/books.png"));
		
		VerticalLayout vroot = new VerticalLayout();
		vroot.addComponents(infProyecto,tabProyecto);
		vroot.setMargin(false);
		vroot.setSpacing(false);
		
		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("45%");
		dialogReactivoWindow.addComponentBody(vroot);
		dialogReactivoWindow.getCancelButton().setCaption("Cerrar");
		dialogReactivoWindow.getLayoutComponent().setMargin(false);
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}
	
	private void cargarDatosProyecto() { 
		listTipo.clear();
		listTipo.addAll(TipoProyectoController.findAll());
		cmbTipo.setItems(listTipo);
		cmbTipo.setItemCaptionGenerator(TipoProyecto::getNombre);
	}
	
	private void limpiarProyecto() {
		String cod = classGeneradorCodigo.genCode();
		codigoProject.setValue(cod.toUpperCase());
		codigoProject.setReadOnly(true);
		qr.setValue(cod.toUpperCase());
		temaProject.clear();
		fechaProyecto.setValue(LocalDate.now());
		descripcionProject.clear();
		listParticipante.clear();
		gridParticipante.setItems(listParticipante);
		listMateria.clear();
		gridMateria.setItems(listMateria);
	}
	
	private void setCss() {
		temaProject.setRows(2);
		descripcionProject.setRows(2);
		cmbTipo.setTextInputAllowed(false);
		cmbTipo.setEmptySelectionCaption("Seleccionar el tipo de proyecto");
		
		formProject.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		lb1.setStyleName(ValoTheme.LABEL_H3);
		lb1.setStyleName(ValoTheme.LABEL_COLORED);
		toolbarParticipante.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		fechaProyecto.setDateFormat("dd/MM/yyyy");
		
		qr.setWidth("140px");	
		qr.setHeight("140px");
	}
	
	public VerticalLayout usuarioLayout = new VerticalLayout();
	public CssLayout filtering = new CssLayout();
	public Button clearFilter = new Button(VaadinIcons.CLOSE_CIRCLE);
	public TextField filtertxt = new TextField();
	public Grid<Usuario> gridUsuario = new Grid<>();
	public List<Usuario> listUsuarios = new ArrayList<>();
	
	private void initBuscarUsuario() {
		filtertxt.setPlaceholder("Buscar por nombres o cedula");
		filtertxt.setValueChangeMode(ValueChangeMode.LAZY);
		filtertxt.setSizeFull();
		filtertxt.addValueChangeListener(e ->{
			listUsuarios.clear();
			listUsuarios.addAll(UsuarioController.search(filtertxt.getValue()));
			gridUsuario.setItems(listUsuarios);
		}); 
		
		clearFilter.addClickListener(e->{
			filtertxt.clear();
		});
		
		filtering.addComponents(filtertxt,clearFilter);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filtering.addStyleName("custom-margins");
				   
		gridUsuario.addColumn(Usuario::getCedula).setCaption("CÉDULA/DNI");
		gridUsuario.addColumn(Usuario -> Usuario.getApellido_paterno() +" "+ Usuario.getApellido_materno() +" "+
		Usuario.getNombre_uno() +" "+ Usuario.getNombre_dos()
				).setCaption("NOMBRES Y APELLIDOS").setId("NOMBRES");
		gridUsuario.addColumn(Usuario::getNombre_usuario).setCaption("USUARIO");
		
		gridUsuario.setWidth("100%");
		gridUsuario.setSelectionMode(SelectionMode.NONE);
		gridUsuario.addComponentColumn(Usuario -> {
	 
			Button b = new Button("Seleccionar");
			b.addClickListener(clickb ->{ 
				
				if(!listParticipante.contains(Usuario)) {
				   listParticipante.add(Usuario);
				   gridParticipante.setItems(listParticipante);
				   
				   ProyectoParticipante pp = new ProyectoParticipante(Usuario,0);
				   
				   listProyectoParticipante.add(pp);
				   
				}else {
					message.warringMessage("El registro ya está seleccionado");
				}
				
				
				/*userNewEdit(Usuario);
				cedula.setValue(Usuario.getCedula());
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
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.EDIT);
			
			/*Button b2 = new Button("Eliminar");
			b2.addClickListener(clickb2 ->{
				listUsuarios.remove(Usuario);
				gridUsuario.setItems(listUsuarios);
				Usuario.setEstado(0);
				UsuarioController.update(Usuario);
				message.warringMessage("Usuario eliminado");
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);*/
			
			HorizontalLayout hl = new HorizontalLayout();
			hl.addComponents(b);
			return hl;			
		}).setCaption("Opciones");

		usuarioLayout.addComponents(filtering,gridUsuario);
		usuarioLayout.setMargin(false);
	}
	
	private void buscarUsuario() {
		cargarDatosUsuario();
		dialogWindow dialogReactivoWindow = new dialogWindow("Búsqueda de participantes", VaadinIcons.FLASK);
		
		dialogReactivoWindow.getCancelButton().addClickListener(e -> {
			dialogReactivoWindow.close();
		});
		
		VerticalLayout vroot = new VerticalLayout();
		vroot.addComponents(usuarioLayout);
		vroot.setMargin(false);
		vroot.setSpacing(false);
		
		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("65%");
		dialogReactivoWindow.addComponentBody(vroot);
		dialogReactivoWindow.getOkButton().setVisible(false);
		dialogReactivoWindow.getCancelButton().setCaption("Cerrar");
		dialogReactivoWindow.getLayoutComponent().setMargin(false);
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}
	
	private void cargarDatosUsuario() {
		listUsuarios = UsuarioController.findAll();
		gridUsuario.setItems(listUsuarios);
	}
	
	
	public VerticalLayout materiaLayout = new VerticalLayout();
	public CssLayout filteringMateria = new CssLayout();
	public Button clearFilterMateria = new Button(VaadinIcons.CLOSE_CIRCLE);
	public TextField filtertxtMateria = new TextField();
	public Grid<Materia> gridMateriabus = new Grid<>();
	public List<Materia> listMateriabus = new ArrayList<>();
	
	private void initBuscarMateria() {
		filtertxtMateria.setPlaceholder("Buscar por nombres");
		filtertxtMateria.setValueChangeMode(ValueChangeMode.LAZY);
		filtertxtMateria.setSizeFull();
		filtertxtMateria.addValueChangeListener(e ->{
			listMateriabus.clear();
			listMateriabus.addAll(MateriaController.search(filtertxtMateria.getValue()));
			gridMateriabus.setItems(listMateriabus);
		}); 
		
		clearFilterMateria.addClickListener(e->{
			filtertxtMateria.clear();
		});
		
		filteringMateria.addComponents(filtertxtMateria,clearFilterMateria);
		filteringMateria.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filteringMateria.addStyleName("custom-margins");
				   
		gridMateriabus.addColumn(Materia::getNombre).setCaption("NOMBRE");
		gridMateriabus.addColumn(Materia::getSemetre).setCaption("SEMESTRE");
		
		gridMateriabus.setWidth("100%");
		gridMateriabus.setSelectionMode(SelectionMode.NONE);
		gridMateriabus.addComponentColumn(Materia -> {
	 
			Button b = new Button("Seleccionar");
			b.addClickListener(clickb ->{ 
				
				if(!listMateria.contains(Materia)) {
				   listMateria.add(Materia);
				   gridMateria.setItems(listMateria);
				}else {
					message.warringMessage("El registro ya está seleccionado");
				}
				
				
				/*userNewEdit(Usuario);
				cedula.setValue(Usuario.getCedula());
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
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.EDIT);
			
			/*Button b2 = new Button("Eliminar");
			b2.addClickListener(clickb2 ->{
				listUsuarios.remove(Usuario);
				gridUsuario.setItems(listUsuarios);
				Usuario.setEstado(0);
				UsuarioController.update(Usuario);
				message.warringMessage("Usuario eliminado");
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);*/
			
			HorizontalLayout hl = new HorizontalLayout();
			hl.addComponents(b);
			return hl;			
		}).setCaption("Opciones");

		materiaLayout.addComponents(filteringMateria,gridMateriabus);
		materiaLayout.setMargin(false);
	}
	
	private void buscarMateria() {
		cargarDatosMateria();
		dialogWindow dialogReactivoWindow = new dialogWindow("Busqueda de materias", VaadinIcons.FLASK);
		
		dialogReactivoWindow.getCancelButton().addClickListener(e -> {
			dialogReactivoWindow.close(); 
		});
		
		VerticalLayout vroot = new VerticalLayout();
		vroot.addComponents(materiaLayout);
		vroot.setMargin(false);
		vroot.setSpacing(false);
		
		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("55%");
		dialogReactivoWindow.addComponentBody(vroot);
		dialogReactivoWindow.getOkButton().setVisible(false);
		dialogReactivoWindow.getCancelButton().setCaption("Cerrar");
		dialogReactivoWindow.getLayoutComponent().setMargin(false);
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}
	
	private void cargarDatosMateria() {
		listMateriabus = MateriaController.findAll();
		gridMateriabus.setItems(listMateriabus);
	}
	
	 Component panelContent() {
	        VerticalLayout layout = new VerticalLayout();
	        layout.setSizeFull();
	        Label content = new Label(
	                "Suspendisse dictum feugiat nisl ut dapibus. Mauris iaculis porttitor posuere. Praesent id metus massa, ut blandit odio.");
	        content.setWidth("10em");
	       // content.setSizeFull();
	      //  content.setSizeUndefined();
	        layout.addComponent(content);
	        Button button = new Button("Button");
	        button.setSizeFull();
	        layout.addComponent(button);
	        return layout;
	    }
	

	
}
