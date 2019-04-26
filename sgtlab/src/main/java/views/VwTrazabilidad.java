package views;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import controllers.LoginController;
import controllers.MedioCultivoController;
import controllers.NotificacionController;
import controllers.ProyectoController;
import controllers.ProyectoParticipanteController;
import controllers.ReactivoController;
import controllers.TrazabilidadController;
import controllers.UsuarioController;
import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.MessageBox;
import models.Componente;
import models.Material;
import models.MedioCultivo;
import models.Notificacion;
import models.Proyecto;
import models.ProyectoParticipante;
import models.Reactivo;
import models.Rol;
import models.Trazabilidad;
import models.TrazabilidadEquipo;
import models.TrazabilidadMedioCultivo;
import models.TrazabilidadReactivo;
import models.Usuario;
import utils.UploadImageEvidencia;
import utils.dialogWindow;
import utils.message;
import utils.uploadUtils;
import viewComponents.ProcesoComponent;

public class VwTrazabilidad extends Panel {
	private static final long serialVersionUID = 1L;

	public VerticalLayout mainLayout = new VerticalLayout();
	public FormLayout mainFrm;
	public HorizontalLayout toolbar = new HorizontalLayout();
	public VerticalLayout trazabilidadLayout = new VerticalLayout();
	public MenuBar mainMenu = new MenuBar();
	private VwProyectos vwproyectos;
	private List<Trazabilidad> listTrazabilidad = new ArrayList<>();
	private VerticalLayout trazas = new VerticalLayout();
	private String trazabilidadAction = "guardar";
	Proyecto proyecto;
	private long idUsuario = (long) VaadinSession.getCurrent().getAttribute("ID_USUARIO");
	private String nombreUsuario = VaadinSession.getCurrent().getAttribute("NOMBRE_PERSONA").toString();
	@SuppressWarnings("unchecked")
	private List<Rol> roles = (List<Rol>) VaadinSession.getCurrent().getAttribute("TIPO_USUARIO");
	
	public VwTrazabilidad(VwProyectos vwproyectos, Proyecto proyecto) {
		this.proyecto = proyecto;
		this.vwproyectos = vwproyectos;
		setCaption("Gestión de trazabilidad de producto");
		setIcon(VaadinIcons.FILE_PROCESS);
		//setSizeFull();
		//addStyleName("v-scrollable");
		setHeight("600px"); 
		setCss(); 
		setContent(buildUI());
		cargarDatos();
		initTraza();
		addValidation();
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
		mainMenu.addItem("Ir a proyectos", VaadinIcons.ARROW_LEFT, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				vwproyectos.mainLayout.removeComponent(VwTrazabilidad.this);
				vwproyectos.mainLayout.addComponent(vwproyectos.pnlPrincipal);
			}
		});		
		
		mainMenu.addItem("Nuevo proceso", VaadinIcons.PLUS, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				trazabilidadAction = "guardar";
				newEditTraza(null);
			}
		});	
		
		mainMenu.addItem("Imprimir", VaadinIcons.PRINT, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				//buildUIProyect(); 
			}
		});	 
		
		mainMenu.addItem("Actualizar", VaadinIcons.REFRESH, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				cargarDatos();
			}
		});

		trazas.addStyleName("v-scrollable");
		//trazas.setSizeFull();
		trazabilidadLayout.addComponents(toolbar,trazas);//,gridTrazabilidad);//, buildUIProyect());
		trazabilidadLayout.setMargin(false);
		
		return trazabilidadLayout; 
	}

	//Trazabilidad traza;
	
	public void cargarDatos() {
		listTrazabilidad.clear();
		trazas.removeAllComponents();
		listTrazabilidad.addAll(TrazabilidadController.getAllTrazasByProyecto(proyecto.getIdProyecto()));
		
		Iterator<Trazabilidad> iteratorTrazabilidad = listTrazabilidad.iterator();
		
		List<Rol> rolesAsignados = new ArrayList<>();
		while(iteratorTrazabilidad.hasNext()) {
			Trazabilidad traza = new Trazabilidad();
			traza = iteratorTrazabilidad.next();
			ProcesoComponent pc = new ProcesoComponent();
			pc.getEstadoProceso().setValue(traza.getEstadoRevision());
			pc.getDescripcionProceso().setValue(traza.getDescripcion());	
			pc.getNombreUsuario().setValue(traza.getUsuario().getApellido_paterno()+" "+traza.getUsuario().getNombre_uno());
			pc.getFechaPublicacion().setValue(traza.getFecha().toString());
			pc.getHoraPublicacion().setValue(traza.getHora().toString());
			if(traza.getUsuario().getImagen()!=null) {
				pc.getImgUser().setSource(uploadUtils.byteToImgUser(traza.getUsuario().getImagen()));
			}else {
				pc.getImgUser().setSource(new ThemeResource("images/NO_USER.png"));
			} 
			
			if(idUsuario != traza.getUsuario().getId()) {
				pc.getEditButton().setVisible(false);
				pc.getDelButton().setVisible(false);
			}else {
				pc.getEditButton().setVisible(true);
				pc.getDelButton().setVisible(true);
			}
		    
			boolean isDocente = false;
			boolean isAdmin = false;
						
			rolesAsignados.clear();
			rolesAsignados.addAll(roles);
			Iterator<Rol> rol = rolesAsignados.iterator();
			Long idrol;
			while(rol.hasNext()) {
				idrol = rol.next().getIdRol();
				if(idrol == 2) {//2: DOCENTE 
					isDocente = true;
				}
				
				if(idrol == 1) {//1: ADMINISTRADOR 
					isAdmin = true;
				}
			}
						
			if(ProyectoController.userIsResponsable(idUsuario, proyecto) && isDocente) {
				pc.getRevisarButton().setVisible(true);
				pc.getEditButton().setVisible(true);
				pc.getDelButton().setVisible(true);
				pc.getCommentButton().setVisible(true);
			}else {
				pc.getRevisarButton().setVisible(false);
				pc.getCommentButton().setVisible(false);
			}
						
			if(isAdmin) {
				pc.getRevisarButton().setVisible(true);
				pc.getEditButton().setVisible(true);
				pc.getDelButton().setVisible(true);
				pc.getCommentButton().setVisible(true);
			}
			
			//CARGAR REVISORES DE PROCESO
			Iterator<Usuario> iteratorUsers = TrazabilidadController.getRevisoresByTraza(traza.getIdTrazabilidad()).iterator();
			Usuario revisor;
			boolean estadoRevision = false;
			String strRevisores = "";
			while(iteratorUsers.hasNext()) {
				revisor = iteratorUsers.next();		
				strRevisores = strRevisores + revisor.getApellido_paterno()+" "+revisor.getNombre_uno()+"<br>";
				estadoRevision = true;
			}
			pc.getRevisores().setValue(strRevisores);
			if(estadoRevision) {
				pc.getEstadoProceso().setValue("Revisado");
				pc.getEstadoProceso().addStyleName("v-label-revisado");
			}
			
			//EDITAR
			Trazabilidad trazaMod = traza;
			pc.getEditButton().addClickListener(e->{
				trazabilidadAction = "modificar";
				newEditTraza(trazaMod);
			});
			
			//VER
			Trazabilidad trazaView = traza;
			pc.getViewButton().addClickListener(e->{
				trazabilidadAction = "ver";
				newEditTraza(trazaView);
			});
			
			//REVISAR
			Trazabilidad trazaUp = traza;
			pc.getRevisarButton().addClickListener(e ->{	
				Usuario revisorCheck = UsuarioController.getSpecificUserById(idUsuario);	
				List<Usuario> usuariosRevisores = new ArrayList<>();
				usuariosRevisores.addAll(TrazabilidadController.getRevisoresByTraza(trazaUp.getIdTrazabilidad()));
				
				if(!usuariosRevisores.contains(revisorCheck)) {
					usuariosRevisores.add(revisorCheck);
				}else {
					message.warringMessage("El usuario ya realizó la revisión");
					return;
				}  
				
				trazaUp.setRevisor(usuariosRevisores);
				trazaUp.setEstadoRevision("Revisado"); 
				TrazabilidadController.update(trazaUp);
				
				Iterator<Usuario> iteratorUs = usuariosRevisores.iterator();
				Usuario rev;
				String srtRevisoresUp = "";
				while(iteratorUs.hasNext()) {
					rev = iteratorUs.next();
					srtRevisoresUp = srtRevisoresUp + rev.getApellido_paterno()+" "+rev.getNombre_uno()+"<br>";			
				}	
				pc.getRevisores().setValue(srtRevisoresUp);	
				pc.getEstadoProceso().setValue("Revisado");
				pc.getEstadoProceso().addStyleName("v-label-revisado");
				
				Iterator<ProyectoParticipante> ppIterator = ProyectoController.getProyectoById(proyecto.getIdProyecto()).iterator();
				ProyectoParticipante pp;
				while(ppIterator.hasNext()) {
					pp = ppIterator.next();
					if(pp.getUsuario().getId() != idUsuario) {
						Notificacion not = new Notificacion("El usuario "+nombreUsuario+" revisó una traza del proyecto "+pp.getProyecto().getTema(), pp.getUsuario(), 1);
						NotificacionController.save(not);
					}
					
				}
				
				message.normalMessage("Acción realizada con éxito");
			});
			
			//COMENTAR
			Trazabilidad trazaComment = traza;
			pc.getCommentButton().addClickListener(e->{				
				comentar(trazaComment);
			});
			//ELIMINAR
			Trazabilidad trazaDel = traza;
			pc.getDelButton().addClickListener(e ->{
				MessageBox.createQuestion()
				.withCaption("Confirmación de eliminación")
	    		.withMessage("Está seguro de eliminar este registro?")
	    		.withOkButton(() -> {
	    			trazaDel.setEstado(0);
	    			TrazabilidadController.update(trazaDel);
	    			trazas.removeComponent(pc);		
	    			message.normalMessage("Registro eliminado");
	    			
	    			Iterator<ProyectoParticipante> ppIterator = ProyectoController.getProyectoById(proyecto.getIdProyecto()).iterator();
					ProyectoParticipante pp;
					while(ppIterator.hasNext()) {
						pp = ppIterator.next();
						if(pp.getUsuario().getId() != idUsuario) {
							Notificacion not = new Notificacion("El usuario "+nombreUsuario+" eliminó una traza del proyecto "+pp.getProyecto().getTema(), pp.getUsuario(), 1);
							NotificacionController.save(not);
						}
						
					}
	    			
	    		},ButtonOption.caption("Si"))
	    		.withCancelButton(ButtonOption.caption("No"))
	    		.open();						
			});
						
			trazas.addComponent(pc);
		}   
				
	}
	
	private Accordion accordion = new Accordion();
	/*LABORATORIO DE PROCESOS QUIMICOS*/
	public VerticalLayout layoutProcesosQuimicos = new VerticalLayout();
	public HorizontalLayout toolbarProcesosQuimicos = new HorizontalLayout();
	public MenuBar mainMenuProcesosQuimicos = new MenuBar();
	//REACTIVOS
	public VerticalLayout laboratorioReactivoLayoutPQ = new VerticalLayout();
	public Grid<TrazabilidadReactivo> gridReactivoPQ = new Grid<>();
	public List<TrazabilidadReactivo> listReactivosPQ = new ArrayList<>();
	private TabSheet tabProcesosQuimicosPQ = new TabSheet();
	//EQUIPOS
	public VerticalLayout laboratorioEquipoLayoutPQ = new VerticalLayout();
	public Grid<TrazabilidadEquipo> gridEquipoPQ = new Grid<>();
	public List<TrazabilidadEquipo> listEquiposPQ = new ArrayList<>();
	public List<Componente> listComponenteEquipoPQ = new ArrayList<>();
	//MATERIALES
	public VerticalLayout laboratorioMaterialesLayoutPQ = new VerticalLayout();
	public Grid<Material> gridMaterialPQ = new Grid<>();
	public List<Material> listMaterialesPQ = new ArrayList<>();
	/*FIN LABORATORIO DE PROCESOS QUIMICOS*/
	
	/*LABORATORIO MICROBIOLOGIA*/
	public VerticalLayout layoutMicrobiologia = new VerticalLayout();
	public HorizontalLayout toolbarMicrobiologia = new HorizontalLayout();
	public MenuBar mainMenuMicrobiologia = new MenuBar();
	//REACTIVOS
	public VerticalLayout laboratorioReactivoLayoutMi = new VerticalLayout();
	public Grid<TrazabilidadReactivo> gridReactivoMi= new Grid<>();
	public List<TrazabilidadReactivo> listReactivosMi = new ArrayList<>();
	private TabSheet tabMicrobiologiaMi = new TabSheet();
	//EQUIPOS
	public VerticalLayout laboratorioEquipoLayoutMi = new VerticalLayout();
	public Grid<TrazabilidadEquipo> gridEquipoMi = new Grid<>();
	public List<TrazabilidadEquipo> listEquiposMi = new ArrayList<>();
	//MATERIALES
	public VerticalLayout laboratorioMaterialesLayoutMi = new VerticalLayout();
	public Grid<Material> gridMaterialMi = new Grid<>();
	public List<Material> listMaterialesMi = new ArrayList<>();
	//MEDIOS CULTIVO
	public VerticalLayout laboratorioMedioCultivoLayoutMi = new VerticalLayout();
	public Grid<TrazabilidadMedioCultivo> gridMedioCultivoMi = new Grid<>();
	public List<TrazabilidadMedioCultivo> listMediosCultivosMi = new ArrayList<>();
	/*FIN LABORATORIO MICROBIOLOGIA*/
	
	/*LABORATORIO AGUAS*/
	public VerticalLayout layoutAG = new VerticalLayout();
	public HorizontalLayout toolbarAG = new HorizontalLayout();
	public MenuBar mainMenuAG = new MenuBar();
	//REACTIVOS
	public VerticalLayout laboratorioReactivoLayoutAG = new VerticalLayout();
	public Grid<TrazabilidadReactivo> gridReactivoAG= new Grid<>();
	public List<TrazabilidadReactivo> listReactivosAG = new ArrayList<>();
	private TabSheet tabAguasAG = new TabSheet();
	//EQUIPOS
	public VerticalLayout laboratorioEquipoLayoutAG = new VerticalLayout();
	public Grid<TrazabilidadEquipo> gridEquipoAG = new Grid<>();
	public List<TrazabilidadEquipo> listEquiposAG = new ArrayList<>();
	//MATERIALES
	public VerticalLayout laboratorioMaterialesLayoutAG = new VerticalLayout();
	public Grid<Material> gridMaterialAG = new Grid<>();
	public List<Material> listMaterialesAG = new ArrayList<>();
	/*FIN LABORATORIO MICROBIOLOGIA*/
	
	/*LABORATORIO OPERACIONES UNITARIAS*/
	public VerticalLayout layoutOU = new VerticalLayout();
	public HorizontalLayout toolbarOU = new HorizontalLayout();
	public MenuBar mainMenuOU = new MenuBar();
	private TabSheet tabOperacionesUnitariasOU = new TabSheet();
	//EQUIPOS
	public VerticalLayout laboratorioEquipoLayoutOU = new VerticalLayout();
	public Grid<TrazabilidadEquipo> gridEquipoOU = new Grid<>();
	public List<TrazabilidadEquipo> listEquiposOU = new ArrayList<>();
	//MATERIALES
	public VerticalLayout laboratorioMaterialesLayoutOU = new VerticalLayout();
	public Grid<Material> gridMaterialOU = new Grid<>();
	public List<Material> listMaterialesOU = new ArrayList<>();
	/*FIN LABORATORIO DE PROCESOS QUIMICOS*/
	
	/*LABORATORIO DE ECOTOXICOLOGIA*/
	public VerticalLayout layoutEcotoxicologia= new VerticalLayout();
	public HorizontalLayout toolbarEcotoxicologia = new HorizontalLayout();
	public MenuBar mainMenuEcotoxicologia = new MenuBar();
	//REACTIVOS
	public VerticalLayout laboratorioReactivoLayoutEC = new VerticalLayout();
	public Grid<TrazabilidadReactivo> gridReactivoEC = new Grid<>();
	public List<TrazabilidadReactivo> listReactivosEC = new ArrayList<>();
	private TabSheet tabEcotoxicologiaEC = new TabSheet();
	//EQUIPOS
	public VerticalLayout laboratorioEquipoLayoutEC = new VerticalLayout();
	public Grid<TrazabilidadEquipo> gridEquipoEC = new Grid<>();
	public List<TrazabilidadEquipo> listEquiposEC = new ArrayList<>();
	//MATERIALES
	public VerticalLayout laboratorioMaterialesLayoutEC = new VerticalLayout();
	public Grid<Material> gridMaterialEC = new Grid<>();
	public List<Material> listMaterialesEC = new ArrayList<>();
	/*FIN LABORATORIO DE ECOTOXICOLOGIA*/
	
	private RichTextArea descripcionProceso = new RichTextArea("Descripción de proceso");
	private HorizontalLayout vroot = new HorizontalLayout();
	private VerticalLayout vlDescripcionProceso = new VerticalLayout();
	private UploadImageEvidencia evidencia = new UploadImageEvidencia();
	private RichTextArea txtComentario = new RichTextArea("Comentario docente"); 
	private VerticalLayout vlaccordionComment= new VerticalLayout();
	
	public void initTraza() {
		
		accordion.setCaption("Laboratorios utilizados");
		accordion.addTab(layoutProcesosQuimicos,"Laboratorio Procesos químicos",VaadinIcons.FLASK);
		accordion.addTab(layoutMicrobiologia,"Laboratorio Microbiología",VaadinIcons.CLUSTER);
		accordion.addTab(layoutAG,"Laboratorio Análisis de aguas",VaadinIcons.DROP);
		accordion.addTab(layoutOU,"Laboratorio Operaciones Unitarias",VaadinIcons.BULLSEYE);
		accordion.addTab(layoutEcotoxicologia,"Laboratorio Ecotoxicología",VaadinIcons.FILTER);
		accordion.setWidth("500px");
		accordion.setHeight("410px");
		
		//descripcionProceso.setHeight("400px");
		vlDescripcionProceso.setMargin(false);
		vlDescripcionProceso.addComponents(descripcionProceso, evidencia);
		vlaccordionComment.setMargin(false);
		vlaccordionComment.addComponents(accordion, txtComentario);
		txtComentario.setSizeFull();
		txtComentario.setReadOnly(true);
		vroot.addComponents(vlaccordionComment,vlDescripcionProceso);	 
		vroot.setMargin(false); 
		
		/*LABORATORIO DE PROCESOS QUIMICOS*/
		
		toolbarProcesosQuimicos.setWidth("100%");
		toolbarProcesosQuimicos.setSpacing(true);
		toolbarProcesosQuimicos.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarProcesosQuimicos.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarProcesosQuimicos.setResponsive(true);
		toolbarProcesosQuimicos.addComponents(mainMenuProcesosQuimicos);
		
		mainMenuProcesosQuimicos.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuProcesosQuimicos.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuProcesosQuimicos.setResponsive(true);

		mainMenuProcesosQuimicos.addItem("Agregar a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				buscarEnLab(1);
			}
		});
	
		layoutProcesosQuimicos.setMargin(false);
		layoutProcesosQuimicos.addComponents(toolbarProcesosQuimicos,tabProcesosQuimicosPQ);
		
		tabProcesosQuimicosPQ.addTab(laboratorioReactivoLayoutPQ, "Reactivos", new ThemeResource("images/quimica.png"));
		tabProcesosQuimicosPQ.addTab(laboratorioEquipoLayoutPQ, "Equipos", new ThemeResource("images/microscopio.png"));
		tabProcesosQuimicosPQ.addTab(laboratorioMaterialesLayoutPQ, "Materiales", new ThemeResource("images/mortero.png"));
		
		// REACTIVO 
		gridReactivoPQ.setRowHeight(45.00);
		gridReactivoPQ.addComponentColumn(Trazabilidad -> {
			Label lb = new Label();
			lb.setValue(Trazabilidad.getReactivo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridReactivoPQ.addColumn(Trazabilidad -> Trazabilidad.getGasto() + " " +Trazabilidad.getReactivo().getUnidad().getNombre().toLowerCase())
		.setCaption("GASTO").setId("GASTO").setExpandRatio(0);
		gridReactivoPQ.addComponentColumn(Reactivo -> {
			
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listReactivosPQ.remove(Reactivo); 
				gridReactivoPQ.setItems(listReactivosPQ);
				//message.normalMessage("Reactivo eliminado");
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
			
		}).setCaption("Opciones").setId("Opciones"); 

		gridReactivoPQ.setWidth("100%");
		gridReactivoPQ.setHeight("150px");
		gridReactivoPQ.setSelectionMode(SelectionMode.NONE);

		laboratorioReactivoLayoutPQ.addComponents(gridReactivoPQ); 
		laboratorioReactivoLayoutPQ.setMargin(false);
		
		//EQUIPOS
		gridEquipoPQ.setRowHeight(45.00);
		gridEquipoPQ.addComponentColumn(Trazabilidad -> {
			Label lb = new Label("");
			lb.setValue(Trazabilidad.getEquipo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridEquipoPQ.addComponentColumn(Equipo -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listEquiposPQ.remove(Equipo); 
				gridEquipoPQ.setItems(listEquiposPQ);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
		}).setCaption("Opciones").setId("Opciones"); 

		gridEquipoPQ.setWidth("100%");
		gridEquipoPQ.setHeight("150px");
		gridEquipoPQ.setSelectionMode(SelectionMode.NONE);


		laboratorioEquipoLayoutPQ.addComponents(gridEquipoPQ);
		laboratorioEquipoLayoutPQ.setMargin(false);
		
		// **MATERIAL**//
		gridMaterialPQ.setRowHeight(45.00);
		gridMaterialPQ.addComponentColumn(Material -> {
			Label lb = new Label();
			lb.setValue(Material.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridMaterialPQ.addColumn(Material -> Material.getCapacidad()).setCaption("CAPACIDAD").setExpandRatio(0);

		gridMaterialPQ.addComponentColumn(Material -> {

			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listMaterialesPQ.remove(Material); 
				gridMaterialPQ.setItems(listMaterialesPQ);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
		}).setCaption("Opciones").setId("Opciones"); 

		gridMaterialPQ.setWidth("100%");
		gridMaterialPQ.setHeight("150px");
		gridMaterialPQ.setSelectionMode(SelectionMode.NONE);

		laboratorioMaterialesLayoutPQ.addComponents(gridMaterialPQ);
		laboratorioMaterialesLayoutPQ.setMargin(false);
		/*FIN LABORATORIO DE PROCESOS QUIMICOS*/
		
		/*LABORATORIO MICROBIOLOGIA*/
		toolbarMicrobiologia.setWidth("100%");
		toolbarMicrobiologia.setSpacing(true);
		toolbarMicrobiologia.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarMicrobiologia.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarMicrobiologia.setResponsive(true);
		toolbarMicrobiologia.addComponents(mainMenuMicrobiologia);
		
		mainMenuMicrobiologia.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuMicrobiologia.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuMicrobiologia.setResponsive(true);

		mainMenuMicrobiologia.addItem("Agregar a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				buscarEnLab(2);
			}
		});
	
		layoutMicrobiologia.setMargin(false);
		layoutMicrobiologia.addComponents(toolbarMicrobiologia,tabMicrobiologiaMi);
		
		tabMicrobiologiaMi.addTab(laboratorioReactivoLayoutMi, "Reactivos", new ThemeResource("images/quimica.png"));
		tabMicrobiologiaMi.addTab(laboratorioEquipoLayoutMi, "Equipos", new ThemeResource("images/microscopio.png"));
		tabMicrobiologiaMi.addTab(laboratorioMaterialesLayoutMi, "Materiales", new ThemeResource("images/mortero.png"));
		tabMicrobiologiaMi.addTab(laboratorioMedioCultivoLayoutMi, "M. Cultivo", new ThemeResource("images/molecule.png"));
				
		// REACTIVO 
		gridReactivoMi.setRowHeight(45.00);
		gridReactivoMi.addComponentColumn(Trazabilidad -> {
			Label lb = new Label();
			lb.setValue(Trazabilidad.getReactivo().getNombre());
			lb.setSizeFull(); 
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridReactivoMi.addColumn(Trazabilidad -> Trazabilidad.getGasto() + " " +Trazabilidad.getReactivo().getUnidad().getNombre().toLowerCase())
		.setCaption("GASTO").setId("GASTO").setExpandRatio(0);
		gridReactivoMi.addComponentColumn(Reactivo -> {
			
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listReactivosMi.remove(Reactivo); 
				gridReactivoMi.setItems(listReactivosMi);
				//message.normalMessage("Reactivo eliminado");
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
			
		}).setCaption("Opciones").setId("Opciones"); 

		gridReactivoMi.setWidth("100%");
		gridReactivoMi.setHeight("150px");
		gridReactivoMi.setSelectionMode(SelectionMode.NONE);

		laboratorioReactivoLayoutMi.addComponents(gridReactivoMi); 
		laboratorioReactivoLayoutMi.setMargin(false);
		
		//EQUIPOS
		gridEquipoMi.setRowHeight(45.00);
		gridEquipoMi.addComponentColumn(Trazabilidad -> {
			Label lb = new Label("");
			lb.setValue(Trazabilidad.getEquipo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridEquipoMi.addComponentColumn(Equipo -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listEquiposMi.remove(Equipo); 
				gridEquipoMi.setItems(listEquiposMi);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
		}).setCaption("Opciones").setId("Opciones"); 

		gridEquipoMi.setWidth("100%");
		gridEquipoMi.setHeight("150px");
		gridEquipoMi.setSelectionMode(SelectionMode.NONE);


		laboratorioEquipoLayoutMi.addComponents(gridEquipoMi);
		laboratorioEquipoLayoutMi.setMargin(false);
		
		// **MATERIAL**//
		gridMaterialMi.setRowHeight(45.00);
		gridMaterialMi.addComponentColumn(Material -> {
			Label lb = new Label();
			lb.setValue(Material.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridMaterialMi.addColumn(Material -> Material.getCapacidad()).setCaption("CAPACIDAD").setExpandRatio(0);

		gridMaterialMi.addComponentColumn(Material -> {

			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listMaterialesMi.remove(Material); 
				gridMaterialMi.setItems(listMaterialesMi);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
		}).setCaption("Opciones").setId("Opciones"); 

		gridMaterialMi.setWidth("100%");
		gridMaterialMi.setHeight("150px");
		gridMaterialMi.setSelectionMode(SelectionMode.NONE);

		laboratorioMaterialesLayoutMi.addComponents(gridMaterialMi);
		laboratorioMaterialesLayoutMi.setMargin(false);
		
		// MEDIOS CULTIVO 
		gridMedioCultivoMi.setRowHeight(45.00);
		gridMedioCultivoMi.addComponentColumn(Trazabilidad -> {
			Label lb = new Label();
			lb.setValue(Trazabilidad.getMedioCultivo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridMedioCultivoMi.addColumn(Trazabilidad -> Trazabilidad.getGasto() + " " +Trazabilidad.getMedioCultivo().getUnidad().getNombre().toLowerCase())
		.setCaption("GASTO").setId("GASTO").setExpandRatio(0);
		gridMedioCultivoMi.addComponentColumn(Trazabilidad -> {

			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listMediosCultivosMi.remove(Trazabilidad); 
				gridMedioCultivoMi.setItems(listMediosCultivosMi);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;

		}).setCaption("Opciones").setId("Opciones"); 

		gridMedioCultivoMi.setWidth("100%");
		gridMedioCultivoMi.setHeight("150px");
		gridMedioCultivoMi.setSelectionMode(SelectionMode.NONE);

		laboratorioMedioCultivoLayoutMi.addComponents(gridMedioCultivoMi); 
		laboratorioMedioCultivoLayoutMi.setMargin(false);
		/*FIN LABORATORIO MICROBIOLOGIA*/ 
		
		/*LABORATORIO DE AGUAS*/
		toolbarAG.setWidth("100%");
		toolbarAG.setSpacing(true);
		toolbarAG.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarAG.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarAG.setResponsive(true);
		toolbarAG.addComponents(mainMenuAG);
		
		mainMenuAG.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuAG.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuAG.setResponsive(true);

		mainMenuAG.addItem("Agregar a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				buscarEnLab(4);
			}
		});
	
		layoutAG.setMargin(false);
		layoutAG.addComponents(toolbarAG,tabAguasAG);
		
		tabAguasAG.addTab(laboratorioReactivoLayoutAG, "Reactivos", new ThemeResource("images/quimica.png"));
		tabAguasAG.addTab(laboratorioEquipoLayoutAG, "Equipos", new ThemeResource("images/microscopio.png"));
		tabAguasAG.addTab(laboratorioMaterialesLayoutAG, "Materiales", new ThemeResource("images/mortero.png"));
		
		// REACTIVO 
		gridReactivoAG.setRowHeight(45.00);
		gridReactivoAG.addComponentColumn(Trazabilidad -> {
			Label lb = new Label();
			lb.setValue(Trazabilidad.getReactivo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridReactivoAG.addColumn(Trazabilidad -> Trazabilidad.getGasto() + " " +Trazabilidad.getReactivo().getUnidad().getNombre().toLowerCase())
		.setCaption("GASTO").setId("GASTO").setExpandRatio(0);
		gridReactivoAG.addComponentColumn(Reactivo -> {
			
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listReactivosAG.remove(Reactivo); 
				gridReactivoAG.setItems(listReactivosAG);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
			
		}).setCaption("Opciones").setId("Opciones"); 

		gridReactivoAG.setWidth("100%");
		gridReactivoAG.setHeight("150px");
		gridReactivoAG.setSelectionMode(SelectionMode.NONE);

		laboratorioReactivoLayoutAG.addComponents(gridReactivoAG); 
		laboratorioReactivoLayoutAG.setMargin(false);
		
		//EQUIPOS
		gridEquipoAG.setRowHeight(45.00);
		gridEquipoAG.addComponentColumn(Trazabilidad -> {
			Label lb = new Label("");
			lb.setValue(Trazabilidad.getEquipo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridEquipoAG.addComponentColumn(Equipo -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listEquiposAG.remove(Equipo); 
				gridEquipoAG.setItems(listEquiposAG);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
		}).setCaption("Opciones").setId("Opciones"); 

		gridEquipoAG.setWidth("100%");
		gridEquipoAG.setHeight("150px");
		gridEquipoAG.setSelectionMode(SelectionMode.NONE);


		laboratorioEquipoLayoutAG.addComponents(gridEquipoAG);
		laboratorioEquipoLayoutAG.setMargin(false);
		
		// **MATERIAL**//
		gridMaterialAG.setRowHeight(45.00);
		gridMaterialAG.addComponentColumn(Material -> {
			Label lb = new Label();
			lb.setValue(Material.getNombre());
			lb.setSizeFull();
			return lb; 
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridMaterialAG.addColumn(Material -> Material.getCapacidad()).setCaption("CAPACIDAD").setExpandRatio(0);

		gridMaterialAG.addComponentColumn(Material -> {

			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listMaterialesAG.remove(Material); 
				gridMaterialAG.setItems(listMaterialesAG);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
		}).setCaption("Opciones").setId("Opciones"); 

		gridMaterialAG.setWidth("100%");
		gridMaterialAG.setHeight("150px");
		gridMaterialAG.setSelectionMode(SelectionMode.NONE);

		laboratorioMaterialesLayoutAG.addComponents(gridMaterialAG);
		laboratorioMaterialesLayoutAG.setMargin(false);
		/*FIN LABORATORIO DE AGUAS*/
		
		/*LABORATORIO DE OPERACIONES UNITARIAS*/
		toolbarOU.setWidth("100%");
		toolbarOU.setSpacing(true);
		toolbarOU.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarOU.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarOU.setResponsive(true);
		toolbarOU.addComponents(mainMenuOU);
		
		mainMenuOU.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuOU.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuOU.setResponsive(true);

		mainMenuOU.addItem("Agregar a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				buscarEnLab(3);
			}
		});
	
		layoutOU.setMargin(false);
		layoutOU.addComponents(toolbarOU,tabOperacionesUnitariasOU);
				
		tabOperacionesUnitariasOU.addTab(laboratorioEquipoLayoutOU, "Equipos", new ThemeResource("images/microscopio.png"));
		tabOperacionesUnitariasOU.addTab(laboratorioMaterialesLayoutOU, "Materiales", new ThemeResource("images/mortero.png"));
		
		//EQUIPOS
		gridEquipoOU.setRowHeight(45.00);
		gridEquipoOU.addComponentColumn(Trazabilidad -> {
			Label lb = new Label("");
			lb.setValue(Trazabilidad.getEquipo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		/*gridEquipoOU.addComponentColumn(Trazabilidad -> {
			Label lb = new Label();
			lb.setContentMode(ContentMode.HTML);
			String comp = "";
			Iterator<Componente> iterator = Trazabilidad.getComponentes().iterator();
			while(iterator.hasNext()) {
				Componente c = iterator.next();
				comp = comp + c.getNombre() + ", ";
			}
			lb.setValue("<font size='1'>"+comp+"</font>");

			lb.setSizeFull();
			return lb;
		}).setCaption("COMPONENTES").setExpandRatio(0);*/

		gridEquipoOU.addComponentColumn(Trazabilidad -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listEquiposOU.remove(Trazabilidad); 
				gridEquipoOU.setItems(listEquiposOU);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
		}).setCaption("Opciones").setId("Opciones"); 

		gridEquipoOU.setWidth("100%");
		gridEquipoOU.setHeight("150px");
		gridEquipoOU.setSelectionMode(SelectionMode.NONE);


		laboratorioEquipoLayoutOU.addComponents(gridEquipoOU);
		laboratorioEquipoLayoutOU.setMargin(false);
		
		// **MATERIAL**//
		gridMaterialOU.setRowHeight(45.00);
		gridMaterialOU.addComponentColumn(Material -> {
			Label lb = new Label();
			lb.setValue(Material.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridMaterialOU.addColumn(Material -> Material.getCapacidad()).setCaption("CAPACIDAD").setExpandRatio(0);

		gridMaterialOU.addComponentColumn(Material -> {

			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listMaterialesOU.remove(Material); 
				gridMaterialOU.setItems(listMaterialesOU);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
		}).setCaption("Opciones").setId("Opciones"); 

		gridMaterialOU.setWidth("100%");
		gridMaterialOU.setHeight("150px");
		gridMaterialOU.setSelectionMode(SelectionMode.NONE);

		laboratorioMaterialesLayoutOU.addComponents(gridMaterialOU);
		laboratorioMaterialesLayoutOU.setMargin(false);
		/*FIN LABORATORIO OPERACIONES UNITARIAS*/
		
		/*LABORATORIO DE ECOTOXICOLOGIA*/
		toolbarEcotoxicologia.setWidth("100%");
		toolbarEcotoxicologia.setSpacing(true);
		toolbarEcotoxicologia.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarEcotoxicologia.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarEcotoxicologia.setResponsive(true);
		toolbarEcotoxicologia.addComponents(mainMenuEcotoxicologia);
		
		mainMenuEcotoxicologia.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuEcotoxicologia.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuEcotoxicologia.setResponsive(true);

		mainMenuEcotoxicologia.addItem("Agregar a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				buscarEnLab(5);
			}
		});
	
		layoutEcotoxicologia.setMargin(false);
		layoutEcotoxicologia.addComponents(toolbarEcotoxicologia,tabEcotoxicologiaEC);
		
		tabEcotoxicologiaEC.addTab(laboratorioReactivoLayoutEC, "Reactivos", new ThemeResource("images/quimica.png"));
		tabEcotoxicologiaEC.addTab(laboratorioEquipoLayoutEC, "Equipos", new ThemeResource("images/microscopio.png"));
		tabEcotoxicologiaEC.addTab(laboratorioMaterialesLayoutEC, "Materiales", new ThemeResource("images/mortero.png"));
		
		// REACTIVO 
		gridReactivoEC.setRowHeight(45.00);
		gridReactivoEC.addComponentColumn(Trazabilidad -> {
			Label lb = new Label();
			lb.setValue(Trazabilidad.getReactivo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridReactivoEC.addColumn(Trazabilidad -> Trazabilidad.getGasto() + " " +Trazabilidad.getReactivo().getUnidad().getNombre().toLowerCase())
		.setCaption("GASTO").setId("GASTO").setExpandRatio(0);
		gridReactivoEC.addComponentColumn(Reactivo -> {
			
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listReactivosEC.remove(Reactivo); 
				gridReactivoEC.setItems(listReactivosEC);
				//message.normalMessage("Reactivo eliminado");
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
			
		}).setCaption("Opciones").setId("Opciones"); 

		gridReactivoEC.setWidth("100%");
		gridReactivoEC.setHeight("150px");
		gridReactivoEC.setSelectionMode(SelectionMode.NONE);

		laboratorioReactivoLayoutEC.addComponents(gridReactivoEC); 
		laboratorioReactivoLayoutEC.setMargin(false);
		
		//EQUIPOS
		gridEquipoEC.setRowHeight(45.00);
		gridEquipoEC.addComponentColumn(Trazabilidad -> {
			Label lb = new Label("");
			lb.setValue(Trazabilidad.getEquipo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridEquipoEC.addComponentColumn(Equipo -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listEquiposEC.remove(Equipo); 
				gridEquipoEC.setItems(listEquiposEC);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
		}).setCaption("Opciones").setId("Opciones"); 

		gridEquipoEC.setWidth("100%");
		gridEquipoEC.setHeight("150px");
		gridEquipoEC.setSelectionMode(SelectionMode.NONE);


		laboratorioEquipoLayoutEC.addComponents(gridEquipoEC);
		laboratorioEquipoLayoutEC.setMargin(false);
		
		// **MATERIAL**//
		gridMaterialEC.setRowHeight(45.00);
		gridMaterialEC.addComponentColumn(Material -> {
			Label lb = new Label();
			lb.setValue(Material.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridMaterialEC.addColumn(Material -> Material.getCapacidad()).setCaption("CAPACIDAD").setExpandRatio(0);

		gridMaterialEC.addComponentColumn(Material -> {

			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listMaterialesEC.remove(Material); 
				gridMaterialEC.setItems(listMaterialesEC);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
		}).setCaption("Opciones").setId("Opciones");

		gridMaterialEC.setWidth("100%");
		gridMaterialEC.setHeight("150px");
		gridMaterialEC.setSelectionMode(SelectionMode.NONE);

		laboratorioMaterialesLayoutEC.addComponents(gridMaterialEC);
		laboratorioMaterialesLayoutEC.setMargin(false);
		/*FIN LABORATORIO DE ECOTOXICOLOGIA*/
		
	}
	
	public void newEditTraza(Trazabilidad trazMod) {
	
		dialogWindow dialogReactivoWindow = new dialogWindow("Registro de procesos", VaadinIcons.ARROW_RIGHT);
		
		limpiarTraza();

		if(trazabilidadAction.equals("modificar")) {
			
			listReactivosPQ.addAll(TrazabilidadController.getReactivoTrazaByLab(trazMod.getIdTrazabilidad(), 1));
			gridReactivoPQ.setItems(listReactivosPQ);
			
			listEquiposPQ.addAll(TrazabilidadController.getEquipoTrazaByLab(trazMod.getIdTrazabilidad(), 1));
			gridEquipoPQ.setItems(listEquiposPQ);
			
			listMaterialesPQ.addAll(TrazabilidadController.getMaterialTrazaByLab(trazMod.getIdTrazabilidad(), 1));
			gridMaterialPQ.setItems(listMaterialesPQ);
			
			
			listReactivosMi.addAll(TrazabilidadController.getReactivoTrazaByLab(trazMod.getIdTrazabilidad(), 2));
			gridReactivoMi.setItems(listReactivosMi);
			
			listEquiposMi.addAll(TrazabilidadController.getEquipoTrazaByLab(trazMod.getIdTrazabilidad(), 2));
			gridEquipoMi.setItems(listEquiposMi);
			
			listMaterialesMi.addAll(TrazabilidadController.getMaterialTrazaByLab(trazMod.getIdTrazabilidad(), 2));
			gridMaterialMi.setItems(listMaterialesMi);
						
			listMediosCultivosMi.addAll(TrazabilidadController.getMedioCultivoTrazaByLab(trazMod.getIdTrazabilidad(), 2));
			gridMedioCultivoMi.setItems(listMediosCultivosMi);
			
			
			listEquiposOU.addAll(TrazabilidadController.getEquipoTrazaByLab(trazMod.getIdTrazabilidad(), 3));
			gridEquipoOU.setItems(listEquiposOU); 
			
			listMaterialesOU.addAll(TrazabilidadController.getMaterialTrazaByLab(trazMod.getIdTrazabilidad(), 3));
			gridMaterialOU.setItems(listMaterialesOU);
			
			
			listReactivosAG.addAll(TrazabilidadController.getReactivoTrazaByLab(trazMod.getIdTrazabilidad(), 4));
			gridReactivoAG.setItems(listReactivosAG);
			
			listEquiposAG.addAll(TrazabilidadController.getEquipoTrazaByLab(trazMod.getIdTrazabilidad(), 4));
			gridEquipoAG.setItems(listEquiposAG);
			
			listMaterialesAG.addAll(TrazabilidadController.getMaterialTrazaByLab(trazMod.getIdTrazabilidad(), 4));
			gridMaterialAG.setItems(listMaterialesAG);
			
			
			listReactivosEC.addAll(TrazabilidadController.getReactivoTrazaByLab(trazMod.getIdTrazabilidad(), 5));
			gridReactivoEC.setItems(listReactivosEC);
			
			listEquiposEC.addAll(TrazabilidadController.getEquipoTrazaByLab(trazMod.getIdTrazabilidad(), 5));
			gridEquipoEC.setItems(listEquiposEC);
			
			listMaterialesEC.addAll(TrazabilidadController.getMaterialTrazaByLab(trazMod.getIdTrazabilidad(), 5));
			gridMaterialEC.setItems(listMaterialesEC);
			
			evidencia.setValue(trazMod.getEvidencia());
			
			descripcionProceso.setValue(trazMod.getDescripcion());
			
			toolbarProcesosQuimicos.setVisible(false);			
			toolbarMicrobiologia.setVisible(false);			
			toolbarOU.setVisible(false);			
			toolbarAG.setVisible(false);
			toolbarEcotoxicologia.setVisible(false);
			
			gridReactivoPQ.getColumn("Opciones").setHidden(true);
			gridEquipoPQ.getColumn("Opciones").setHidden(true);
			gridMaterialPQ.getColumn("Opciones").setHidden(true);
			
			gridReactivoMi.getColumn("Opciones").setHidden(true);
			gridEquipoMi.getColumn("Opciones").setHidden(true);
			gridMaterialMi.getColumn("Opciones").setHidden(true);
			gridMedioCultivoMi.getColumn("Opciones").setHidden(true);
			
			gridEquipoOU.getColumn("Opciones").setHidden(true);
			gridMaterialOU.getColumn("Opciones").setHidden(true);
			
			gridReactivoAG.getColumn("Opciones").setHidden(true);
			gridEquipoAG.getColumn("Opciones").setHidden(true);
			gridMaterialAG.getColumn("Opciones").setHidden(true);
			
			gridReactivoEC.getColumn("Opciones").setHidden(true);
			gridEquipoEC.getColumn("Opciones").setHidden(true);
			gridMaterialEC.getColumn("Opciones").setHidden(true);
			
		}else if(trazabilidadAction.equals("ver")){
			listReactivosPQ.addAll(TrazabilidadController.getReactivoTrazaByLab(trazMod.getIdTrazabilidad(), 1));
			gridReactivoPQ.setItems(listReactivosPQ);
			
			listEquiposPQ.addAll(TrazabilidadController.getEquipoTrazaByLab(trazMod.getIdTrazabilidad(), 1));
			gridEquipoPQ.setItems(listEquiposPQ);
			
			listMaterialesPQ.addAll(TrazabilidadController.getMaterialTrazaByLab(trazMod.getIdTrazabilidad(), 1));
			gridMaterialPQ.setItems(listMaterialesPQ);
			
			
			listReactivosMi.addAll(TrazabilidadController.getReactivoTrazaByLab(trazMod.getIdTrazabilidad(), 2));
			gridReactivoMi.setItems(listReactivosMi);
			
			listEquiposMi.addAll(TrazabilidadController.getEquipoTrazaByLab(trazMod.getIdTrazabilidad(), 2));
			gridEquipoMi.setItems(listEquiposMi);
			
			listMaterialesMi.addAll(TrazabilidadController.getMaterialTrazaByLab(trazMod.getIdTrazabilidad(), 2));
			gridMaterialMi.setItems(listMaterialesMi);
						
			listMediosCultivosMi.addAll(TrazabilidadController.getMedioCultivoTrazaByLab(trazMod.getIdTrazabilidad(), 2));
			gridMedioCultivoMi.setItems(listMediosCultivosMi);
			
			
			listEquiposOU.addAll(TrazabilidadController.getEquipoTrazaByLab(trazMod.getIdTrazabilidad(), 3));
			gridEquipoOU.setItems(listEquiposOU); 
			
			listMaterialesOU.addAll(TrazabilidadController.getMaterialTrazaByLab(trazMod.getIdTrazabilidad(), 3));
			gridMaterialOU.setItems(listMaterialesOU);
			
			
			listReactivosAG.addAll(TrazabilidadController.getReactivoTrazaByLab(trazMod.getIdTrazabilidad(), 4));
			gridReactivoAG.setItems(listReactivosAG);
			
			listEquiposAG.addAll(TrazabilidadController.getEquipoTrazaByLab(trazMod.getIdTrazabilidad(), 4));
			gridEquipoAG.setItems(listEquiposAG);
			
			listMaterialesAG.addAll(TrazabilidadController.getMaterialTrazaByLab(trazMod.getIdTrazabilidad(), 4));
			gridMaterialAG.setItems(listMaterialesAG);
			
			
			listReactivosEC.addAll(TrazabilidadController.getReactivoTrazaByLab(trazMod.getIdTrazabilidad(), 5));
			gridReactivoEC.setItems(listReactivosEC);
			
			listEquiposEC.addAll(TrazabilidadController.getEquipoTrazaByLab(trazMod.getIdTrazabilidad(), 5));
			gridEquipoEC.setItems(listEquiposEC);
			
			listMaterialesEC.addAll(TrazabilidadController.getMaterialTrazaByLab(trazMod.getIdTrazabilidad(), 5));
			gridMaterialEC.setItems(listMaterialesEC);
			
			evidencia.setValue(trazMod.getEvidencia());
			
			descripcionProceso.setValue(trazMod.getDescripcion());
			
			descripcionProceso.setReadOnly(true);
			
			if(trazMod.getComentario()==null) {
				txtComentario.setValue("");
				txtComentario.setVisible(false);
			}else {
				txtComentario.setValue(trazMod.getComentario());
				txtComentario.setVisible(true);
			}		
			
			toolbarProcesosQuimicos.setVisible(false);			
			toolbarMicrobiologia.setVisible(false);			
			toolbarOU.setVisible(false);			
			toolbarAG.setVisible(false);
			toolbarEcotoxicologia.setVisible(false);
			
			gridReactivoPQ.getColumn("Opciones").setHidden(true);
			gridEquipoPQ.getColumn("Opciones").setHidden(true);
			gridMaterialPQ.getColumn("Opciones").setHidden(true);
			
			gridReactivoMi.getColumn("Opciones").setHidden(true);
			gridEquipoMi.getColumn("Opciones").setHidden(true);
			gridMaterialMi.getColumn("Opciones").setHidden(true);
			gridMedioCultivoMi.getColumn("Opciones").setHidden(true);
			
			gridEquipoOU.getColumn("Opciones").setHidden(true);
			gridMaterialOU.getColumn("Opciones").setHidden(true);
			
			gridReactivoAG.getColumn("Opciones").setHidden(true);
			gridEquipoAG.getColumn("Opciones").setHidden(true);
			gridMaterialAG.getColumn("Opciones").setHidden(true);
			
			gridReactivoEC.getColumn("Opciones").setHidden(true);
			gridEquipoEC.getColumn("Opciones").setHidden(true);
			gridMaterialEC.getColumn("Opciones").setHidden(true);
			
			dialogReactivoWindow.getOkButton().setVisible(false);
			dialogReactivoWindow.getFooter().setVisible(false);
		}
		
		dialogReactivoWindow.getOkButton().addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				if (!validatorTrazabilidad.isValid()) {
					validatorTrazabilidad.validate();

					message.warringMessage("Hay errores en los campos de texto");
					return;
				} 

				if (trazabilidadAction.equals("guardar")) {
					
					Trazabilidad traz = new Trazabilidad(LocalDate.now(), LocalTime.now(), 
							descripcionProceso.getValue().trim(), evidencia.getValue(), 1);
				
					traz.setProyecto(proyecto);
					traz.setEstadoRevision("Esperando revisión");
					
					System.out.println("Proceso creado por: "+LoginController.u.getNombre_usuario());
					
					traz.setUsuario(UsuarioController.getSpecificUserById(idUsuario));
					
					//REACTIVOS
					List<TrazabilidadReactivo> listReactivos = new ArrayList<>();
					listReactivos.addAll(listReactivosPQ);
					listReactivos.addAll(listReactivosMi);
					listReactivos.addAll(listReactivosEC);
					listReactivos.addAll(listReactivosAG);
				
					Iterator<TrazabilidadReactivo> iteratorReactivo = listReactivos.iterator();
					TrazabilidadReactivo trazReactivo;
					Reactivo rUp; 
					float gasto = 0, saldoUp = 0, gastoUp = 0; 
					
					while(iteratorReactivo.hasNext()) {
						gasto = 0; 
						saldoUp = 0; 
						gastoUp = 0; 
						trazReactivo = iteratorReactivo.next();
						trazReactivo.setTrazabilidad(traz); 
						gasto = trazReactivo.getGasto();
						rUp = ReactivoController.getSpecificReactivoById(trazReactivo.getReactivo().getIdReactivo());
						saldoUp = rUp.getSaldo() - gasto;
						gastoUp = rUp.getGasto() + gasto;
						rUp.setGasto(gastoUp); 
						rUp.setSaldo(saldoUp);  
						System.out.println(rUp.getNombre()+" "+saldoUp);
						
						ReactivoController.update(rUp);
					}
					traz.setTrazabilidadReactivos(listReactivos);
					
					//EQUIPOS
					List<TrazabilidadEquipo> listEquipos = new ArrayList<>();
					listEquipos.addAll(listEquiposPQ);
					listEquipos.addAll(listEquiposMi);
					listEquipos.addAll(listEquiposEC);
					listEquipos.addAll(listEquiposAG);
					listEquipos.addAll(listEquiposOU);
				
					Iterator<TrazabilidadEquipo> iteratorEquipos = listEquipos.iterator();
					TrazabilidadEquipo trazEquipo;
					while(iteratorEquipos.hasNext()) {
						trazEquipo = iteratorEquipos.next();
						trazEquipo.setTrazabilidad(traz);
					}
					traz.setTrazabilidadEquipos(listEquipos);					
					
					//MATERIALES
					List<Material> listMateriales = new ArrayList<>();
					listMateriales.addAll(listMaterialesPQ);
					listMateriales.addAll(listMaterialesMi);
					listMateriales.addAll(listMaterialesEC);
					listMateriales.addAll(listMaterialesAG);
					listMateriales.addAll(listMaterialesOU);

					traz.setMateriales(listMateriales);
					
					//MEDIOS DE CULTIVO
					List<TrazabilidadMedioCultivo> listMediosCultivo = new ArrayList<>();
					listMediosCultivo.addAll(listMediosCultivosMi);
				
					Iterator<TrazabilidadMedioCultivo> iteratorMediosCultivo = listMediosCultivo.iterator();
					TrazabilidadMedioCultivo trazMediosCultivo;
					MedioCultivo mc;
					float gastoMc = 0, saldoMcUp = 0, gastoMcUp = 0; 
					
					while(iteratorMediosCultivo.hasNext()) {
						gastoMc = 0; 
						saldoMcUp = 0; 
						gastoMcUp = 0; 
						trazMediosCultivo = iteratorMediosCultivo.next();
						trazMediosCultivo.setTrazabilidad(traz);
						gastoMc = trazMediosCultivo.getGasto();
						mc = MedioCultivoController.getSpecificMedioCultivoById(trazMediosCultivo.getMedioCultivo().getIdMedioCultivo());
						saldoMcUp = mc.getSaldo() - gastoMc;
						gastoMcUp = mc.getGasto() + gastoMc;
						mc.setGasto(gastoMcUp);
						mc.setSaldo(saldoMcUp);  
						System.out.println(mc.getNombre()+" "+saldoMcUp);
						MedioCultivoController.update(mc);
					}
					traz.setTrazabilidadMediosCultivo(listMediosCultivo);	
					
				    TrazabilidadController.save(traz);
				    
				    Iterator<ProyectoParticipante> ppIterator = ProyectoController.getProyectoById(proyecto.getIdProyecto()).iterator();
					ProyectoParticipante pp;
					while(ppIterator.hasNext()) {
						pp = ppIterator.next();
						if(pp.getUsuario().getId() != idUsuario) {
							Notificacion not = new Notificacion("El usuario "+nombreUsuario+" agregó una nueva traza al proyecto "+pp.getProyecto().getTema(), pp.getUsuario(), 1);
							NotificacionController.save(not);
						}
						
					}
				    
				} else {

					trazMod.setProyecto(proyecto);
					trazMod.setDescripcion(descripcionProceso.getValue());
					trazMod.setEvidencia(evidencia.getValue());
					
					//REACTIVOS
					List<TrazabilidadReactivo> listReactivos = new ArrayList<>();
					listReactivos.addAll(listReactivosPQ);
					listReactivos.addAll(listReactivosMi);
					listReactivos.addAll(listReactivosEC);
					listReactivos.addAll(listReactivosAG);
				
					Iterator<TrazabilidadReactivo> iteratorReactivo = listReactivos.iterator();
					TrazabilidadReactivo trazReactivo;
					
					while(iteratorReactivo.hasNext()) {
						trazReactivo = iteratorReactivo.next();
						trazReactivo.setTrazabilidad(trazMod); 
					}
					trazMod.setTrazabilidadReactivos(listReactivos);
					
					//EQUIPOS
					List<TrazabilidadEquipo> listEquipos = new ArrayList<>();
					listEquipos.addAll(listEquiposPQ);
					listEquipos.addAll(listEquiposMi);
					listEquipos.addAll(listEquiposEC);
					listEquipos.addAll(listEquiposAG);
					listEquipos.addAll(listEquiposOU);
				
					Iterator<TrazabilidadEquipo> iteratorEquipos = listEquipos.iterator();
					TrazabilidadEquipo trazEquipo;
					while(iteratorEquipos.hasNext()) {
						trazEquipo = iteratorEquipos.next();
						trazEquipo.setTrazabilidad(trazMod);
					}
					trazMod.setTrazabilidadEquipos(listEquipos);					
					
					//MATERIALES
					List<Material> listMateriales = new ArrayList<>();
					listMateriales.addAll(listMaterialesPQ);
					listMateriales.addAll(listMaterialesMi);
					listMateriales.addAll(listMaterialesEC);
					listMateriales.addAll(listMaterialesAG);
					listMateriales.addAll(listMaterialesOU);

					trazMod.setMateriales(listMateriales);
					
					//MEDIOS DE CULTIVO
					List<TrazabilidadMedioCultivo> listMediosCultivo = new ArrayList<>();
					listMediosCultivo.addAll(listMediosCultivosMi);
				
					Iterator<TrazabilidadMedioCultivo> iteratorMediosCultivo = listMediosCultivo.iterator();
					TrazabilidadMedioCultivo trazMediosCultivo;
					
					while(iteratorMediosCultivo.hasNext()) {
						trazMediosCultivo = iteratorMediosCultivo.next();
						trazMediosCultivo.setTrazabilidad(trazMod);
					}
					trazMod.setTrazabilidadMediosCultivo(listMediosCultivo);	
					
				    TrazabilidadController.update(trazMod);
				    
				    Iterator<ProyectoParticipante> ppIterator = ProyectoController.getProyectoById(proyecto.getIdProyecto()).iterator();
					ProyectoParticipante pp;
					while(ppIterator.hasNext()) {
						pp = ppIterator.next();
						if(pp.getUsuario().getId() != idUsuario) {
							Notificacion not = new Notificacion("El usuario "+nombreUsuario+" modificó una traza del proyecto "+pp.getProyecto().getTema(), pp.getUsuario(), 1);
							NotificacionController.save(not);
						}
						
					}

				}

				message.normalMessage("Acción realizada con éxito");

				cargarDatos();
				dialogReactivoWindow.close();
			}
		});

		dialogReactivoWindow.getCancelButton().addClickListener(e -> {
			dialogReactivoWindow.close();
		});

		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("85%");
		dialogReactivoWindow.addComponentBody(vroot);
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}
	
	public void buscarEnLab(int lab) {
		dialogWindow dialogReactivoWindow = new dialogWindow("Busqueda", VaadinIcons.ARROW_RIGHT);
		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("60%");
		dialogReactivoWindow.addComponentBody(new VwLaboratoriosBuscar(VwTrazabilidad.this, lab));
		dialogReactivoWindow.getOkButton().setVisible(false);
		dialogReactivoWindow.getCancelButton().setCaption("Cerrar");
		dialogReactivoWindow.getCancelButton().addClickListener(e ->{
			dialogReactivoWindow.close();
		});
		dialogReactivoWindow.getFooterText().setValue("Opciones");
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}
	
	public RichTextArea comentario = new RichTextArea();
	
	public void comentar(Trazabilidad traza) {
		comentario.setSizeFull();
		dialogWindow dialogReactivoWindow = new dialogWindow("Comentario", VaadinIcons.COMMENT);
		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("60%");
		dialogReactivoWindow.addComponentBody(comentario);
		
		comentario.setValue(traza.getComentario()+"<br>");
		
		dialogReactivoWindow.getOkButton().addClickListener(e->{
			traza.setComentario(comentario.getValue()+"<br>"+nombreUsuario+"<br>Docente");
			
			if(comentario.isEmpty()) {
				message.warringMessage("No se puede guardar un comentario vacio");
				return;
			}
			
			TrazabilidadController.update(traza);
			
			Iterator<ProyectoParticipante> ppIterator = ProyectoController.getProyectoById(proyecto.getIdProyecto()).iterator();
			ProyectoParticipante pp;
			while(ppIterator.hasNext()) {
				pp = ppIterator.next();
				if(pp.getUsuario().getId() != idUsuario) {
					Notificacion not = new Notificacion("El usuario "+nombreUsuario+" comentó una traza del proyecto "+pp.getProyecto().getTema(), pp.getUsuario(), 1);
					NotificacionController.save(not);
				}
				
			}
			
			message.normalMessage("Acción realizada con éxito");
			dialogReactivoWindow.close();
		});
		
		dialogReactivoWindow.getCancelButton().addClickListener(e ->{
			dialogReactivoWindow.close();
		});
		
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}
	
	public void limpiarTraza() {
		listReactivosPQ.clear();
		gridReactivoPQ.setItems(listReactivosPQ);
		listEquiposPQ.clear();
		gridEquipoPQ.setItems(listEquiposPQ);
		listMaterialesPQ.clear();
		gridMaterialPQ.setItems(listMaterialesPQ);
		
		listReactivosMi.clear();
		gridReactivoMi.setItems(listReactivosMi);
		listEquiposMi.clear();
		gridEquipoMi.setItems(listEquiposMi);
		listMaterialesMi.clear();
		gridMaterialMi.setItems(listMaterialesMi);
		listMediosCultivosMi.clear();
		gridMedioCultivoMi.setItems(listMediosCultivosMi);
		
		listEquiposOU.clear();
		gridEquipoOU.setItems(listEquiposOU);
		listMaterialesOU.clear();
		gridMaterialOU.setItems(listMaterialesOU);
		
		listReactivosAG.clear();
		gridReactivoAG.setItems(listReactivosAG);
		listEquiposAG.clear();
		gridEquipoAG.setItems(listEquiposAG);
		listMaterialesAG.clear();
		gridMaterialAG.setItems(listMaterialesAG);
		
		listReactivosEC.clear();
		gridReactivoEC.setItems(listReactivosEC);
		listEquiposEC.clear();
		gridEquipoEC.setItems(listEquiposEC);
		listMaterialesEC.clear();
		gridMaterialEC.setItems(listMaterialesEC);
		
		descripcionProceso.clear();
		descripcionProceso.setReadOnly(false);
		
		txtComentario.clear();
		txtComentario.setVisible(false);
		
		evidencia.clear();
		
		accordion.setSelectedTab(0);
		
		toolbarProcesosQuimicos.setVisible(true);
		gridReactivoPQ.getColumn("Opciones").setHidden(false);
		gridEquipoPQ.getColumn("Opciones").setHidden(false);
		gridMaterialPQ.getColumn("Opciones").setHidden(false);
		
		toolbarMicrobiologia.setVisible(true);
		gridReactivoMi.getColumn("Opciones").setHidden(false);
		gridEquipoMi.getColumn("Opciones").setHidden(false);
		gridMaterialMi.getColumn("Opciones").setHidden(false);
		gridMedioCultivoMi.getColumn("Opciones").setHidden(false);
		
		toolbarOU.setVisible(true);
		gridEquipoOU.getColumn("Opciones").setHidden(false);
		gridMaterialOU.getColumn("Opciones").setHidden(false);
		
		toolbarAG.setVisible(true);
		gridReactivoAG.getColumn("Opciones").setHidden(false);
		gridEquipoAG.getColumn("Opciones").setHidden(false);
		gridMaterialAG.getColumn("Opciones").setHidden(false);
		
		toolbarEcotoxicologia.setVisible(true);
		gridReactivoEC.getColumn("Opciones").setHidden(false);
		gridEquipoEC.getColumn("Opciones").setHidden(false);
		gridMaterialEC.getColumn("Opciones").setHidden(false);
	}
	
	public void setCss() {
		toolbar.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
	}
	
	Binder<Trazabilidad> validatorTrazabilidad = new Binder<>();
	 private void addValidation() {
		 validatorTrazabilidad.forField(descripcionProceso).asRequired("Campo requerido").bind(Trazabilidad::getDescripcion,
				 Trazabilidad::setDescripcion);
	 }
	
	
	
	
	
}
