package views;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
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

import controllers.LoginController;
import controllers.MateriaController;
import controllers.ProyectoController;
import controllers.ProyectoParticipanteController;
import controllers.TipoProyectoController;
import controllers.TrazabilidadController;
import controllers.UsuarioController;
import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.MessageBox;
import fi.jasoft.qrcode.QRCode;
import models.Materia;
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
	public long idUsuario = (long) VaadinSession.getCurrent().getAttribute("ID_USUARIO");
	
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
		 addValidation();
		 
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
		mainMenu.addItem("Nuevo proyecto", VaadinIcons.SUITCASE, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				buildNewEditProject(null);
				proyectoAction = "guardar";
			}
		});		
		
		mainMenu.addItem("Ver otros proyectos", VaadinIcons.SEARCH, null);		
		
		mainMenu.addItem("Actualizar", VaadinIcons.REFRESH, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				buildUIProyect();
			}
		});	
			
		proyectoLayout.addComponents(toolbar, buildUIProyect());
		proyectoLayout.setMargin(false);
		
		pnlPrincipal.setCaption("Gestión de proyectos");
		pnlPrincipal.setIcon(VaadinIcons.USERS);
		pnlPrincipal.setContent(proyectoLayout);
		pnlPrincipal.setHeight("600px");
		
		mainLayout.addComponents(pnlPrincipal);
		mainLayout.setHeight("100%");
		return mainLayout;
		
	}
	
	
	public Component buildUIProyect() {
		
		if(layoutProyectos.getComponentCount() >= 1) {
			layoutProyectos.removeAllComponents();
		}
		
		List<ProyectoParticipante> pp = ProyectoController.getProyectoByUser(idUsuario);
		Iterator<ProyectoParticipante> iteratorPp = pp.iterator();
		 
		
		List<ProyectoParticipante> ppU = null;
		Iterator<ProyectoParticipante> iteratorPpU;// = ppU.iterator();
		ProyectoParticipante propU;
		String usuarios = "";
		
		while(iteratorPp.hasNext()) {
			ProyectoParticipante prop = iteratorPp.next();	
			panelProyecto p = new panelProyecto();	
			p.setCaption(prop.getProyecto().getTipoProyecto().getNombre());
			p.getNombreProyecto().setValue(prop.getProyecto().getTema());
			
			ppU = ProyectoController.getProyectoById(prop.getProyecto().getIdProyecto());
			iteratorPpU = ppU.iterator();
			while(iteratorPpU.hasNext()) {
				propU = iteratorPpU.next();
			    usuarios = usuarios + propU.getUsuario().getApellido_paterno()
			    		+" "+propU.getUsuario().getApellido_materno()+" "+propU.getUsuario().getNombre_uno()+"<br>";
			}
			p.getUsuariosProyecto().setValue(usuarios);
			
			String qr = prop.getProyecto().getCodigo(); 
			p.getQr().setValue(qr);
			
			p.getOkButton().addClickListener(e ->{
				mainLayout.removeComponent(pnlPrincipal);
				VwTrazabilidad tr = new VwTrazabilidad(this, prop.getProyecto());
				mainLayout.addComponent(tr);
			});
			
			p.getPrintButton().addClickListener(e->{
				 
			});
			
			p.getEditButton().addClickListener(e ->{
				proyectoAction = "modificar";
				buildNewEditProject(prop);
				codigoProject.setValue(prop.getProyecto().getCodigo());
				this.qr.setValue(prop.getProyecto().getCodigo());
				fechaProyecto.setValue(prop.getProyecto().getFecha());
				cmbTipo.setValue(prop.getProyecto().getTipoProyecto());
				temaProject.setValue(prop.getProyecto().getTema());
				descripcionProject.setValue(prop.getProyecto().getDescripcion());

				List<ProyectoParticipante> ppUEdit = ProyectoController.getProyectoById(prop.getProyecto().getIdProyecto());
				Iterator<ProyectoParticipante> iteratorPpUEdit;
				iteratorPpUEdit = ppUEdit.iterator();
				ProyectoParticipante propUedit;
				while(iteratorPpUEdit.hasNext()) {
					propUedit = iteratorPpUEdit.next();
					listProyectoParticipante.add(propUedit);
				}
				gridParticipante.setItems(listProyectoParticipante);
				
				listMateria.addAll(ProyectoController.getAllMateriaByProject(prop.getProyecto().getIdProyecto()));
				gridMateria.setItems(listMateria);
				
			});
			
			p.getCancelButton().addClickListener(e ->{
								
				MessageBox.createQuestion()
				.withCaption("Confirmación de eliminación")
	    		.withMessage("Está seguro de eliminar este registro?")
	    		.withOkButton(() -> {
	    			Proyecto proyectoUp = prop.getProyecto();
					proyectoUp.setEstado(0);
					ProyectoController.update(proyectoUp);
					layoutProyectos.removeComponent(p);		
	    			message.normalMessage("Registro eliminado");
	    		},ButtonOption.caption("Si"))
	    		.withCancelButton(ButtonOption.caption("No"))
	    		.open();
				
			});
			
			layoutProyectos.addComponents(p);
			ppU.clear();
			usuarios = "";
		}
		
		
		layoutProyectos.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		layoutProyectos.setMargin(true);
		
		return layoutProyectos;
	} 
	
	//PARTICIPANTE
	public Panel pnlProyectoParticipante = new Panel();
	public HorizontalLayout toolbarParticipante = new HorizontalLayout();
	public VerticalLayout proyectoParticipanteLayout = new VerticalLayout();
	public MenuBar mainMenuParticipante = new MenuBar();
	public Grid<ProyectoParticipante> gridParticipante = new Grid<>();
	String proyectoAction = "guardar";
	//public List<ProyectoParticipante> listParticipante = new ArrayList<>();
	
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
		gridParticipante.addColumn(Estudiante -> Estudiante.getUsuario().getApellido_paterno()+" "+Estudiante.getUsuario().getApellido_materno()
		+" "+Estudiante.getUsuario().getNombre_uno()+" "+Estudiante.getUsuario().getNombre_dos()).setCaption("NOMBRE").setExpandRatio(0);
		gridParticipante.setWidth("100%");
		gridParticipante.setSelectionMode(SelectionMode.NONE);
		
		List<Rol> rolesAsignados = new ArrayList<>();
		
		gridParticipante.addComponentColumn(Participante ->{
			CheckBox chk = new CheckBox();
			rolesAsignados.clear();
			rolesAsignados.addAll(Participante.getUsuario().getRoles());
			Iterator<Rol> rol = rolesAsignados.iterator();
			Long idrol;
			Boolean yesNo = false;
			while(rol.hasNext()) {
				idrol = rol.next().getIdRol();
				
				if(idrol == 2 || idrol == 1) {//2: DOCENTE 1: ADMINISTRADOR
					yesNo = true;
				}
				
				if(yesNo) 
					chk.setVisible(true);
				else 
					chk.setVisible(false); 
			}
			
			chk.setValue(Participante.getResponsable());			
			
			chk.addValueChangeListener(e ->{
				Participante.setResponsable(e.getValue());
			});
			
			return chk;
		}).setCaption("Rev.").setStyleGenerator(Participante -> "v-align-center");
		
		gridParticipante.addComponentColumn(Participante -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listProyectoParticipante.remove(Participante);
				gridParticipante.setItems(listProyectoParticipante);
				
				
			/*	Iterator<ProyectoParticipante> ppIterator = listProyectoParticipante.iterator();
				ProyectoParticipante pp,pp2 = null;
				while(ppIterator.hasNext()) {
					pp = ppIterator.next();
					if(Participante.getUsuario().getId() == pp.getUsuario().getId()) {
						pp2 = pp;
					}
				}*/
				
				//listProyectoParticipante.remove(pp2);
				
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
	
	private void buildNewEditProject(ProyectoParticipante prop) {
		limpiarProyecto();
        cargarDatosProyecto();
		
		dialogWindow dialogReactivoWindow = new dialogWindow("Registro de proyectos", VaadinIcons.FLASK);
		
		formProject.addComponents(lb1,codigoProject,fechaProyecto ,cmbTipo,temaProject,descripcionProject);

		dialogReactivoWindow.getOkButton().addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (!validatorPrj.isValid()) {
					validatorPrj.validate();

					message.warringMessage("Hay errores en los campos de texto");
					return;
				}

				if(listProyectoParticipante.size()==0) {
					message.warringMessage("El proyecto debe tener al menos un participante");
					return;
				}
				
				if(proyectoAction.equals("guardar")) {
					Proyecto p = new Proyecto(codigoProject.getValue(), fechaProyecto.getValue(), LocalTime.now(),cmbTipo.getValue(), 
							temaProject.getValue().toUpperCase().trim(), descripcionProject.getValue().toUpperCase().trim(), "EJECUCIÓN",1);
					
					p.setMaterias(listMateria);
					ProyectoController.save(p); 
					
					Iterator<ProyectoParticipante> ppIterator = listProyectoParticipante.iterator();
					ProyectoParticipante pp;
					while(ppIterator.hasNext()) {
						pp = ppIterator.next();
						pp.setProyecto(p);
						ProyectoParticipanteController.save(pp);
					}
					
				}else {
					
					Proyecto p = prop.getProyecto();
					
					p.setFecha(fechaProyecto.getValue());
					p.setTipoProyecto(cmbTipo.getValue());
					p.setTema(temaProject.getValue().toUpperCase().trim());
					p.setDescripcion(descripcionProject.getValue().toUpperCase().trim());
					
					p.setMaterias(listMateria); 
					
					ProyectoParticipanteController.deleteUserProyecto(p.getIdProyecto());
					
					Iterator<ProyectoParticipante> ppIterator = listProyectoParticipante.iterator();
					ProyectoParticipante pp;
					while(ppIterator.hasNext()) {
						pp = ppIterator.next();
						pp.setProyecto(p);
					}
					
					p.setProyectoParticipantes(listProyectoParticipante);
					ProyectoController.update(p);
					
				}
				
				listProyectoParticipante.clear();
				message.normalMessage("Acción realizada con éxito");
				buildUIProyect();
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
		fechaProyecto.setEnabled(false);
		descripcionProject.clear();
		listProyectoParticipante.clear();
		gridParticipante.setItems(listProyectoParticipante);
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
				ProyectoParticipante pp = new ProyectoParticipante(Usuario, false);
				
				if(!listProyectoParticipante.contains(pp)) { 
				   listProyectoParticipante.add(pp);
				   gridParticipante.setItems(listProyectoParticipante); 
				}else {
					message.warringMessage("El registro ya está seleccionado");
				}

				
			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.EDIT);
			
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
		dialogReactivoWindow.getFooterText().setValue("Opciones");
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
				
				
			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.EDIT);
			
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
		dialogReactivoWindow.getFooterText().setValue("Opciones");
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
	
	 Binder<Proyecto> validatorPrj = new Binder<>();
	 private void addValidation() {
		 validatorPrj.forField(cmbTipo).asRequired("Campo requerido").bind(Proyecto::getTipoProyecto,
				 Proyecto::setTipoProyecto);
		 validatorPrj.forField(temaProject).asRequired("Campo requerido").bind(Proyecto::getTema,
				 Proyecto::setTema);
	 }

	
}








