package views;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.vaadin.ui.NumberField;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.renderers.LocalDateRenderer;
import com.vaadin.ui.themes.ValoTheme;

import controllers.ComponenteController;
import controllers.EquipoController;
import controllers.MaterialController;
import controllers.ReactivoController;
import fi.jasoft.qrcode.QRCode;
import models.Componente;
import models.Equipo;
import models.Material;
import models.MedioCultivo;
import models.ProyectoParticipante;
import models.Reactivo;
import models.TipoProyecto;
import models.Trazabilidad;
import models.TrazabilidadMedioCultivo;
import models.TrazabilidadReactivo;
import models.Unidad;
import utils.dialogWindow;
import utils.message;
import viewComponents.ProcesoComponent;

public class VwTrazabilidad extends Panel {
	private static final long serialVersionUID = 1L;

	public VerticalLayout mainLayout = new VerticalLayout();
	//public Panel pnlPrincipal = new Panel();
	public FormLayout mainFrm;
	public HorizontalLayout toolbar = new HorizontalLayout();
	//public HorizontalLayout layoutProyectos = new HorizontalLayout();
	public VerticalLayout trazabilidadLayout = new VerticalLayout();
	public MenuBar mainMenu = new MenuBar();
	private VwProyectos vwproyectos;
	private Grid<Trazabilidad> gridTrazabilidad = new Grid<>();
	private List<Trazabilidad> listTrazabildiad = new ArrayList<>();
	private VerticalLayout trazas = new VerticalLayout();
	
	public VwTrazabilidad(VwProyectos vwproyectos) {
		this.vwproyectos = vwproyectos;
		setCaption("Gestión de trazabilidad de producto");
		setIcon(VaadinIcons.FILE_PROCESS);
		setHeight("600px"); 
		setCss(); 
		setContent(buildUI());
		cargarDatos();
		initTraza();
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
				newEditTraza();
			}
		});	
		
		mainMenu.addItem("Imprimir", VaadinIcons.PRINT, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				//buildUIProyect(); 
			}
		});	 

		trazabilidadLayout.addComponents(toolbar,trazas);//,gridTrazabilidad);//, buildUIProyect());
		trazabilidadLayout.setMargin(false);
		
		return trazabilidadLayout; 
	}

	public void cargarDatos() {
		/*listTrazabildiad.add(new Trazabilidad(LocalDate.now(), LocalTime.now(), "HHHH", null, 1));
		listTrazabildiad.add(new Trazabilidad(LocalDate.now(), LocalTime.now(), "HHHHsss", null, 1));
		listTrazabildiad.add(new Trazabilidad(LocalDate.now(), LocalTime.now(), "HHHHeeeeee", null, 1));
		
		gridTrazabilidad.setItems(listTrazabildiad);*/
		//trazas.setWidth("100%");
		trazas.addComponent(new ProcesoComponent());
		trazas.addComponent(new ProcesoComponent());
	
		trazas.addComponent(new ProcesoComponent());
		trazas.addComponent(new ProcesoComponent());
		trazas.addComponent(new ProcesoComponent());
		trazas.addComponent(new ProcesoComponent());
		
	}
	
	private Accordion accordion = new Accordion();
	/*LABORATORIO DE PROCESOS QUIMICOS*/
	//REACTIVOS
	public HorizontalLayout toolbarReactivoPQ = new HorizontalLayout();
	public VerticalLayout laboratorioReactivoLayoutPQ = new VerticalLayout();
	public MenuBar mainMenuRectivoPQ = new MenuBar();
	public Grid<TrazabilidadReactivo> gridReactivoPQ = new Grid<>();
	public List<TrazabilidadReactivo> listReactivosPQ = new ArrayList<>();
	private TabSheet tabProcesosQuimicosPQ = new TabSheet();
	//EQUIPOS
	public HorizontalLayout toolbarEquiposPQ = new HorizontalLayout();
	public VerticalLayout laboratorioEquipoLayoutPQ = new VerticalLayout();
	public MenuBar mainMenuEquipoPQ = new MenuBar();
	public Grid<Equipo> gridEquipoPQ = new Grid<>();
	public List<Equipo> listEquiposPQ = new ArrayList<>();
	//MATERIALES
	public HorizontalLayout toolbarMaterialPQ = new HorizontalLayout();
	public VerticalLayout laboratorioMaterialesLayoutPQ = new VerticalLayout();
	public MenuBar mainMenuMaterialPQ = new MenuBar();
	public Grid<Material> gridMaterialPQ = new Grid<>();
	public List<Material> listMaterialesPQ = new ArrayList<>();
	/*FIN LABORATORIO DE PROCESOS QUIMICOS*/
	
	/*LABORATORIO MICROBIOLOGIA*/
	//REACTIVOS
	public HorizontalLayout toolbarReactivoMi = new HorizontalLayout();
	public VerticalLayout laboratorioReactivoLayoutMi = new VerticalLayout();
	public MenuBar mainMenuRectivoMi = new MenuBar();
	public Grid<TrazabilidadReactivo> gridReactivoMi= new Grid<>();
	public List<TrazabilidadReactivo> listReactivosMi = new ArrayList<>();
	private TabSheet tabMicrobiologiaMi = new TabSheet();
	//EQUIPOS
	public HorizontalLayout toolbarEquiposMi = new HorizontalLayout();
	public VerticalLayout laboratorioEquipoLayoutMi = new VerticalLayout();
	public MenuBar mainMenuEquipoMi = new MenuBar();
	public Grid<Equipo> gridEquipoMi = new Grid<>();
	public List<Equipo> listEquiposMi = new ArrayList<>();
	//MATERIALES
	public HorizontalLayout toolbarMaterialMi = new HorizontalLayout();
	public VerticalLayout laboratorioMaterialesLayoutMi = new VerticalLayout();
	public MenuBar mainMenuMaterialMi = new MenuBar();
	public Grid<Material> gridMaterialMi = new Grid<>();
	public List<Material> listMaterialesMi = new ArrayList<>();
	//MEDIOS CULTIVO
	public HorizontalLayout toolbarMedioCultivoMi = new HorizontalLayout();
	public VerticalLayout laboratorioMedioCultivoLayoutMi = new VerticalLayout();
	public MenuBar mainMenuMedioCultivoMi = new MenuBar();
	public Grid<TrazabilidadMedioCultivo> gridMedioCultivoMi = new Grid<>();
	public List<TrazabilidadMedioCultivo> listMediosCultivosMi = new ArrayList<>();
	/*FIN LABORATORIO MICROBIOLOGIA*/
	
	/*LABORATORIO AGUAS*/
	//REACTIVOS
	public HorizontalLayout toolbarReactivoAG = new HorizontalLayout();
	public VerticalLayout laboratorioReactivoLayoutAG = new VerticalLayout();
	public MenuBar mainMenuRectivoAG = new MenuBar();
	public Grid<TrazabilidadReactivo> gridReactivoAG= new Grid<>();
	public List<TrazabilidadReactivo> listReactivosAG = new ArrayList<>();
	private TabSheet tabAguasAG = new TabSheet();
	//EQUIPOS
	public HorizontalLayout toolbarEquiposAG = new HorizontalLayout();
	public VerticalLayout laboratorioEquipoLayoutAG = new VerticalLayout();
	public MenuBar mainMenuEquipoAG = new MenuBar();
	public Grid<Equipo> gridEquipoAG = new Grid<>();
	public List<Equipo> listEquiposAG = new ArrayList<>();
	//MATERIALES
	public HorizontalLayout toolbarMaterialAG = new HorizontalLayout();
	public VerticalLayout laboratorioMaterialesLayoutAG = new VerticalLayout();
	public MenuBar mainMenuMaterialAG = new MenuBar();
	public Grid<Material> gridMaterialAG = new Grid<>();
	public List<Material> listMaterialesAG = new ArrayList<>();
	/*FIN LABORATORIO MICROBIOLOGIA*/
	
	/*LABORATORIO OPERACIONES UNITARIAS*/
	private TabSheet tabOperacionesUnitariasOU = new TabSheet();
	//EQUIPOS
	public HorizontalLayout toolbarEquiposOU = new HorizontalLayout();
	public VerticalLayout laboratorioEquipoLayoutOU = new VerticalLayout();
	public MenuBar mainMenuEquipoOU = new MenuBar();
	public Grid<Equipo> gridEquipoOU = new Grid<>();
	public List<Equipo> listEquiposOU = new ArrayList<>();
	//MATERIALES
	public HorizontalLayout toolbarMaterialOU = new HorizontalLayout();
	public VerticalLayout laboratorioMaterialesLayoutOU = new VerticalLayout();
	public MenuBar mainMenuMaterialOU = new MenuBar();
	public Grid<Material> gridMaterialOU = new Grid<>();
	public List<Material> listMaterialesOU = new ArrayList<>();
	/*FIN LABORATORIO DE PROCESOS QUIMICOS*/
	
	/*LABORATORIO DE ECOTOXICOLOGIA*/
	//REACTIVOS
	public HorizontalLayout toolbarReactivoEC = new HorizontalLayout();
	public VerticalLayout laboratorioReactivoLayoutEC = new VerticalLayout();
	public MenuBar mainMenuRectivoEC = new MenuBar();
	public Grid<TrazabilidadReactivo> gridReactivoEC = new Grid<>();
	public List<TrazabilidadReactivo> listReactivosEC = new ArrayList<>();
	private TabSheet tabEcotoxicologiaEC = new TabSheet();
	//EQUIPOS
	public HorizontalLayout toolbarEquiposEC = new HorizontalLayout();
	public VerticalLayout laboratorioEquipoLayoutEC = new VerticalLayout();
	public MenuBar mainMenuEquipoEC = new MenuBar();
	public Grid<Equipo> gridEquipoEC = new Grid<>();
	public List<Equipo> listEquiposEC = new ArrayList<>();
	//MATERIALES
	public HorizontalLayout toolbarMaterialEC = new HorizontalLayout();
	public VerticalLayout laboratorioMaterialesLayoutEC = new VerticalLayout();
	public MenuBar mainMenuMaterialEC = new MenuBar();
	public Grid<Material> gridMaterialEC = new Grid<>();
	public List<Material> listMaterialesEC = new ArrayList<>();
	/*FIN LABORATORIO DE ECOTOXICOLOGIA*/
	
	private RichTextArea descripcionProceso = new RichTextArea("Descripción de proceso");
	private HorizontalLayout vroot = new HorizontalLayout();
	 
	public void initTraza() {
		
		accordion.setCaption("Laboratorios utilizados");
		accordion.addTab(tabProcesosQuimicosPQ,"Laboratorio Procesos químicos",VaadinIcons.FLASK);
		accordion.addTab(tabMicrobiologiaMi,"Laboratorio Microbiología",VaadinIcons.CLUSTER);
		accordion.addTab(tabAguasAG,"Laboratorio Análisis de aguas",VaadinIcons.DROP);
		accordion.addTab(tabOperacionesUnitariasOU,"Laboratorio Operaciones Unitarias",VaadinIcons.BULLSEYE);
		accordion.addTab(tabEcotoxicologiaEC,"Laboratorio Ecotoxicología",VaadinIcons.FILTER);
		accordion.setWidth("500px");
		accordion.setHeight("400px");
		
		descripcionProceso.setHeight("400px");
		vroot.addComponents(accordion,descripcionProceso);	 
		vroot.setMargin(false); 
		
		/*LABORATORIO DE PROCESOS QUIMICOS*/
		tabProcesosQuimicosPQ.addTab(laboratorioReactivoLayoutPQ, "Reactivos", new ThemeResource("images/quimica.png"));
		tabProcesosQuimicosPQ.addTab(laboratorioEquipoLayoutPQ, "Equipos", new ThemeResource("images/microscopio.png"));
		tabProcesosQuimicosPQ.addTab(laboratorioMaterialesLayoutPQ, "Materiales", new ThemeResource("images/mortero.png"));
		
		// REACTIVO 
		toolbarReactivoPQ.setWidth("100%");
		toolbarReactivoPQ.setSpacing(true);
		toolbarReactivoPQ.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarReactivoPQ.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarReactivoPQ.setResponsive(true);
		toolbarReactivoPQ.addComponents(mainMenuRectivoPQ);

		mainMenuRectivoPQ.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuRectivoPQ.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuRectivoPQ.setResponsive(true);

		mainMenuRectivoPQ.addItem("Agregar reactivo a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				//BUSCAR REACTIVO
			}
		});
		
		gridReactivoPQ.setRowHeight(45.00);
		gridReactivoPQ.addComponentColumn(Trazabilidad -> {
			Label lb = new Label();
			lb.setValue(Trazabilidad.getReactivo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridReactivoPQ.addColumn(Trazabilidad -> Trazabilidad.getGasto() + " " +Trazabilidad.getReactivo().getNombre().toLowerCase())
		.setCaption("GASTO").setId("GASTO").setExpandRatio(0);
		gridReactivoPQ.addComponentColumn(Reactivo -> {
			
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listReactivosPQ.remove(Reactivo); 
				gridReactivoPQ.setItems(listReactivosPQ);
				message.normalMessage("Reactivo eliminado");
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

		gridReactivoPQ.setWidth("100%");
		gridReactivoPQ.setSelectionMode(SelectionMode.NONE);

		laboratorioReactivoLayoutPQ.addComponents(toolbarReactivoPQ,gridReactivoPQ); 
		laboratorioReactivoLayoutPQ.setMargin(false);
		
		//EQUIPOS
		toolbarEquiposPQ.setWidth("100%");
		toolbarEquiposPQ.setSpacing(true);
		toolbarEquiposPQ.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarEquiposPQ.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarEquiposPQ.setResponsive(true);
		toolbarEquiposPQ.addComponents(mainMenuEquipoPQ);

		mainMenuEquipoPQ.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuEquipoPQ.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuEquipoPQ.setResponsive(true);

		mainMenuEquipoPQ.addItem("Agregar equipo a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/*codigoEquipo.setReadOnly(false);
						newEditEquipo(null);
						pnlComponente.setVisible(false);
						equipoAction = "guardar";*/
			}
		});

		gridEquipoPQ.addComponentColumn(Equipo -> {
			Label lb = new Label("");
			lb.setValue(Equipo.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridEquipoPQ.addComponentColumn(Equipo -> {
			Label lb = new Label();
			lb.setContentMode(ContentMode.HTML);
			String comp = "";
			Iterator<Componente> iterator = ComponenteController.getAllComponentByEquipo(Equipo.getIdEquipo()).iterator();
			while(iterator.hasNext()) {
				Componente c = iterator.next();
				comp = comp + c.getNombre() + ", ";
			}
			lb.setValue("<font size='1'>"+comp+"</font>");

			lb.setSizeFull();
			return lb;
		}).setCaption("COMPONENTES").setExpandRatio(0);

		gridEquipoPQ.addComponentColumn(Equipo -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				/*listEquiposPQ.remove(Equipo); 
				gridEquipoPQ.setItems(listEquiposPQ);
				Equipo.setEstado(0); 
				EquipoController.update(Equipo);
				message.normalMessage("Reactivo eliminado");*/
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

		gridEquipoPQ.setWidth("100%");
		gridEquipoPQ.setSelectionMode(SelectionMode.NONE);


		laboratorioEquipoLayoutPQ.addComponents(toolbarEquiposPQ, gridEquipoPQ);
		laboratorioEquipoLayoutPQ.setMargin(false);
		
		// **MATERIAL**//
		toolbarMaterialPQ.setWidth("100%");
		toolbarMaterialPQ.setSpacing(true);
		toolbarMaterialPQ.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarMaterialPQ.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarMaterialPQ.setResponsive(true);
		toolbarMaterialPQ.addComponents(mainMenuMaterialPQ);

		mainMenuMaterialPQ.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuMaterialPQ.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuMaterialPQ.setResponsive(true);

		mainMenuMaterialPQ.addItem("Agregar material a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/*codigoMaterial.setReadOnly(false);
						newEditMaterial(null);
						materialAction = "guardar";*/
			}
		});

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
				/* listMaterialesPQ.remove(Material); 
						 gridMaterialPQ.setItems(listMaterialesPQ);
						 Material.setEstado(0); 
						 MaterialController.update(Material);

						 message.normalMessage("Material eliminado");*/
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

		gridMaterialPQ.setWidth("100%");
		gridMaterialPQ.setSelectionMode(SelectionMode.NONE);

		laboratorioMaterialesLayoutPQ.addComponents(toolbarMaterialPQ, gridMaterialPQ);
		laboratorioMaterialesLayoutPQ.setMargin(false);
		/*FIN LABORATORIO DE PROCESOS QUIMICOS*/
		
		/*LABORATORIO MICROBIOLOGIA*/
		tabMicrobiologiaMi.addTab(laboratorioReactivoLayoutMi, "Reactivos", new ThemeResource("images/quimica.png"));
		tabMicrobiologiaMi.addTab(laboratorioEquipoLayoutMi, "Equipos", new ThemeResource("images/microscopio.png"));
		tabMicrobiologiaMi.addTab(laboratorioMaterialesLayoutMi, "Materiales", new ThemeResource("images/mortero.png"));
		tabMicrobiologiaMi.addTab(laboratorioMedioCultivoLayoutMi, "Materiales", new ThemeResource("images/molecule.png"));
				
		// REACTIVO 
		toolbarReactivoMi.setWidth("100%");
		toolbarReactivoMi.setSpacing(true);
		toolbarReactivoMi.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarReactivoMi.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarReactivoMi.setResponsive(true);
		toolbarReactivoMi.addComponents(mainMenuRectivoMi);

		mainMenuRectivoMi.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuRectivoMi.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuRectivoMi.setResponsive(true);

		mainMenuRectivoMi.addItem("Agregar reactivo a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				//BUSCAR REACTIVO
			}
		});
		
		gridReactivoMi.setRowHeight(45.00);
		gridReactivoMi.addComponentColumn(Trazabilidad -> {
			Label lb = new Label();
			lb.setValue(Trazabilidad.getReactivo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridReactivoMi.addColumn(Trazabilidad -> Trazabilidad.getGasto() + " " +Trazabilidad.getReactivo().getNombre().toLowerCase())
		.setCaption("GASTO").setId("GASTO").setExpandRatio(0);
		gridReactivoMi.addComponentColumn(Reactivo -> {
			
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listReactivosMi.remove(Reactivo); 
				gridReactivoMi.setItems(listReactivosMi);
				message.normalMessage("Reactivo eliminado");
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

		gridReactivoMi.setWidth("100%");
		gridReactivoMi.setSelectionMode(SelectionMode.NONE);

		laboratorioReactivoLayoutMi.addComponents(toolbarReactivoMi,gridReactivoMi); 
		laboratorioReactivoLayoutMi.setMargin(false);
		
		//EQUIPOS
		toolbarEquiposMi.setWidth("100%");
		toolbarEquiposMi.setSpacing(true);
		toolbarEquiposMi.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarEquiposMi.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarEquiposMi.setResponsive(true);
		toolbarEquiposMi.addComponents(mainMenuEquipoMi);

		mainMenuEquipoMi.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuEquipoMi.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuEquipoMi.setResponsive(true);

		mainMenuEquipoMi.addItem("Agregar equipo a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/*codigoEquipo.setReadOnly(false);
						newEditEquipo(null);
						pnlComponente.setVisible(false);
						equipoAction = "guardar";*/
			}
		});

		gridEquipoMi.addComponentColumn(Equipo -> {
			Label lb = new Label("");
			lb.setValue(Equipo.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridEquipoMi.addComponentColumn(Equipo -> {
			Label lb = new Label();
			lb.setContentMode(ContentMode.HTML);
			String comp = "";
			Iterator<Componente> iterator = ComponenteController.getAllComponentByEquipo(Equipo.getIdEquipo()).iterator();
			while(iterator.hasNext()) {
				Componente c = iterator.next();
				comp = comp + c.getNombre() + ", ";
			}
			lb.setValue("<font size='1'>"+comp+"</font>");

			lb.setSizeFull();
			return lb;
		}).setCaption("COMPONENTES").setExpandRatio(0);

		gridEquipoMi.addComponentColumn(Equipo -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				/*listEquiposPQ.remove(Equipo); 
				gridEquipoPQ.setItems(listEquiposPQ);
				Equipo.setEstado(0); 
				EquipoController.update(Equipo);
				message.normalMessage("Reactivo eliminado");*/
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

		gridEquipoMi.setWidth("100%");
		gridEquipoMi.setSelectionMode(SelectionMode.NONE);


		laboratorioEquipoLayoutMi.addComponents(toolbarEquiposMi, gridEquipoMi);
		laboratorioEquipoLayoutMi.setMargin(false);
		
		// **MATERIAL**//
		toolbarMaterialMi.setWidth("100%");
		toolbarMaterialMi.setSpacing(true);
		toolbarMaterialMi.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarMaterialMi.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarMaterialMi.setResponsive(true);
		toolbarMaterialMi.addComponents(mainMenuMaterialMi);

		mainMenuMaterialMi.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuMaterialMi.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuMaterialMi.setResponsive(true);

		mainMenuMaterialMi.addItem("Agregar material a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/*codigoMaterial.setReadOnly(false);
						newEditMaterial(null);
						materialAction = "guardar";*/
			}
		});

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
				/* listMaterialesPQ.remove(Material); 
						 gridMaterialPQ.setItems(listMaterialesPQ);
						 Material.setEstado(0); 
						 MaterialController.update(Material);

						 message.normalMessage("Material eliminado");*/
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

		gridMaterialMi.setWidth("100%");
		gridMaterialMi.setSelectionMode(SelectionMode.NONE);

		laboratorioMaterialesLayoutMi.addComponents(toolbarMaterialMi, gridMaterialMi);
		laboratorioMaterialesLayoutMi.setMargin(false);
		
		// MEDIOS CULTIVO 
		toolbarMedioCultivoMi.setWidth("100%");
		toolbarMedioCultivoMi.setSpacing(true);
		toolbarMedioCultivoMi.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarMedioCultivoMi.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarMedioCultivoMi.setResponsive(true);
		toolbarMedioCultivoMi.addComponents(mainMenuMedioCultivoMi);

		mainMenuMedioCultivoMi.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuMedioCultivoMi.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuMedioCultivoMi.setResponsive(true);

		mainMenuMedioCultivoMi.addItem("Agregar medio de cultivo a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				//BUSCAR REACTIVO
			}
		});

		gridMedioCultivoMi.setRowHeight(45.00);
		gridMedioCultivoMi.addComponentColumn(Trazabilidad -> {
			Label lb = new Label();
			lb.setValue(Trazabilidad.getMedioCultivo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridMedioCultivoMi.addColumn(Trazabilidad -> Trazabilidad.getGasto() + " " +Trazabilidad.getMedioCultivo().getNombre().toLowerCase())
		.setCaption("GASTO").setId("GASTO").setExpandRatio(0);
		gridMedioCultivoMi.addComponentColumn(Trazabilidad -> {

			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listMediosCultivosMi.remove(Trazabilidad); 
				gridMedioCultivoMi.setItems(listMediosCultivosMi);
				//message.normalMessage("Medio de cultivo eliminado");
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

		gridMedioCultivoMi.setWidth("100%");
		gridMedioCultivoMi.setSelectionMode(SelectionMode.NONE);

		laboratorioMedioCultivoLayoutMi.addComponents(toolbarMedioCultivoMi,gridMedioCultivoMi); 
		laboratorioMedioCultivoLayoutMi.setMargin(false);
		/*FIN LABORATORIO MICROBIOLOGIA*/ 
		
		/*LABORATORIO DE AGUAS*/
		tabAguasAG.addTab(laboratorioReactivoLayoutAG, "Reactivos", new ThemeResource("images/quimica.png"));
		tabAguasAG.addTab(laboratorioEquipoLayoutAG, "Equipos", new ThemeResource("images/microscopio.png"));
		tabAguasAG.addTab(laboratorioMaterialesLayoutAG, "Materiales", new ThemeResource("images/mortero.png"));
		
		// REACTIVO 
		toolbarReactivoAG.setWidth("100%");
		toolbarReactivoAG.setSpacing(true);
		toolbarReactivoAG.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarReactivoAG.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarReactivoAG.setResponsive(true);
		toolbarReactivoAG.addComponents(mainMenuRectivoAG);

		mainMenuRectivoAG.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuRectivoAG.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuRectivoAG.setResponsive(true);

		mainMenuRectivoAG.addItem("Agregar reactivo a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				//BUSCAR REACTIVO
			}
		});
		
		gridReactivoAG.setRowHeight(45.00);
		gridReactivoAG.addComponentColumn(Trazabilidad -> {
			Label lb = new Label();
			lb.setValue(Trazabilidad.getReactivo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridReactivoAG.addColumn(Trazabilidad -> Trazabilidad.getGasto() + " " +Trazabilidad.getReactivo().getNombre().toLowerCase())
		.setCaption("GASTO").setId("GASTO").setExpandRatio(0);
		gridReactivoAG.addComponentColumn(Reactivo -> {
			
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listReactivosAG.remove(Reactivo); 
				gridReactivoAG.setItems(listReactivosAG);
				message.normalMessage("Reactivo eliminado");
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

		gridReactivoAG.setWidth("100%");
		gridReactivoAG.setSelectionMode(SelectionMode.NONE);

		laboratorioReactivoLayoutAG.addComponents(toolbarReactivoAG,gridReactivoAG); 
		laboratorioReactivoLayoutAG.setMargin(false);
		
		//EQUIPOS
		toolbarEquiposAG.setWidth("100%");
		toolbarEquiposAG.setSpacing(true);
		toolbarEquiposAG.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarEquiposAG.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarEquiposAG.setResponsive(true);
		toolbarEquiposAG.addComponents(mainMenuEquipoAG);

		mainMenuEquipoAG.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuEquipoAG.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuEquipoAG.setResponsive(true);

		mainMenuEquipoAG.addItem("Agregar equipo a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/*codigoEquipo.setReadOnly(false);
						newEditEquipo(null);
						pnlComponente.setVisible(false);
						equipoAction = "guardar";*/
			}
		});

		gridEquipoAG.addComponentColumn(Equipo -> {
			Label lb = new Label("");
			lb.setValue(Equipo.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridEquipoAG.addComponentColumn(Equipo -> {
			Label lb = new Label();
			lb.setContentMode(ContentMode.HTML);
			String comp = "";
			Iterator<Componente> iterator = ComponenteController.getAllComponentByEquipo(Equipo.getIdEquipo()).iterator();
			while(iterator.hasNext()) {
				Componente c = iterator.next();
				comp = comp + c.getNombre() + ", ";
			}
			lb.setValue("<font size='1'>"+comp+"</font>");

			lb.setSizeFull();
			return lb;
		}).setCaption("COMPONENTES").setExpandRatio(0);

		gridEquipoAG.addComponentColumn(Equipo -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				/*listEquiposPQ.remove(Equipo); 
				gridEquipoPQ.setItems(listEquiposPQ);
				Equipo.setEstado(0); 
				EquipoController.update(Equipo);
				message.normalMessage("Reactivo eliminado");*/
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

		gridEquipoAG.setWidth("100%");
		gridEquipoAG.setSelectionMode(SelectionMode.NONE);


		laboratorioEquipoLayoutAG.addComponents(toolbarEquiposAG, gridEquipoAG);
		laboratorioEquipoLayoutAG.setMargin(false);
		
		// **MATERIAL**//
		toolbarMaterialAG.setWidth("100%");
		toolbarMaterialAG.setSpacing(true);
		toolbarMaterialAG.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarMaterialAG.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarMaterialAG.setResponsive(true);
		toolbarMaterialAG.addComponents(mainMenuMaterialAG);

		mainMenuMaterialAG.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuMaterialAG.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuMaterialAG.setResponsive(true);

		mainMenuMaterialAG.addItem("Agregar material a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/*codigoMaterial.setReadOnly(false);
						newEditMaterial(null);
						materialAction = "guardar";*/
			}
		});

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
				/* listMaterialesPQ.remove(Material); 
						 gridMaterialPQ.setItems(listMaterialesPQ);
						 Material.setEstado(0); 
						 MaterialController.update(Material);

						 message.normalMessage("Material eliminado");*/
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

		gridMaterialAG.setWidth("100%");
		gridMaterialAG.setSelectionMode(SelectionMode.NONE);

		laboratorioMaterialesLayoutAG.addComponents(toolbarMaterialAG, gridMaterialAG);
		laboratorioMaterialesLayoutAG.setMargin(false);
		/*FIN LABORATORIO DE AGUAS*/
		
		/*LABORATORIO DE OPERACIONES UNITARIAS*/
		tabOperacionesUnitariasOU.addTab(laboratorioEquipoLayoutOU, "Equipos", new ThemeResource("images/microscopio.png"));
		tabOperacionesUnitariasOU.addTab(laboratorioMaterialesLayoutOU, "Materiales", new ThemeResource("images/mortero.png"));
		
		//EQUIPOS
		toolbarEquiposOU.setWidth("100%");
		toolbarEquiposOU.setSpacing(true);
		toolbarEquiposOU.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarEquiposOU.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarEquiposOU.setResponsive(true);
		toolbarEquiposOU.addComponents(mainMenuEquipoOU);

		mainMenuEquipoOU.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuEquipoOU.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuEquipoOU.setResponsive(true);

		mainMenuEquipoOU.addItem("Agregar equipo a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/*codigoEquipo.setReadOnly(false);
						newEditEquipo(null);
						pnlComponente.setVisible(false);
						equipoAction = "guardar";*/
			}
		});

		gridEquipoOU.addComponentColumn(Equipo -> {
			Label lb = new Label("");
			lb.setValue(Equipo.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridEquipoOU.addComponentColumn(Equipo -> {
			Label lb = new Label();
			lb.setContentMode(ContentMode.HTML);
			String comp = "";
			Iterator<Componente> iterator = ComponenteController.getAllComponentByEquipo(Equipo.getIdEquipo()).iterator();
			while(iterator.hasNext()) {
				Componente c = iterator.next();
				comp = comp + c.getNombre() + ", ";
			}
			lb.setValue("<font size='1'>"+comp+"</font>");

			lb.setSizeFull();
			return lb;
		}).setCaption("COMPONENTES").setExpandRatio(0);

		gridEquipoOU.addComponentColumn(Equipo -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				/*listEquiposPQ.remove(Equipo); 
				gridEquipoPQ.setItems(listEquiposPQ);
				Equipo.setEstado(0); 
				EquipoController.update(Equipo);
				message.normalMessage("Reactivo eliminado");*/
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

		gridEquipoOU.setWidth("100%");
		gridEquipoOU.setSelectionMode(SelectionMode.NONE);


		laboratorioEquipoLayoutOU.addComponents(toolbarEquiposOU, gridEquipoOU);
		laboratorioEquipoLayoutOU.setMargin(false);
		
		// **MATERIAL**//
		toolbarMaterialOU.setWidth("100%");
		toolbarMaterialOU.setSpacing(true);
		toolbarMaterialOU.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarMaterialOU.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarMaterialOU.setResponsive(true);
		toolbarMaterialOU.addComponents(mainMenuMaterialOU);

		mainMenuMaterialOU.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuMaterialOU.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuMaterialOU.setResponsive(true);

		mainMenuMaterialOU.addItem("Agregar material a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/*codigoMaterial.setReadOnly(false);
						newEditMaterial(null);
						materialAction = "guardar";*/
			}
		});

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
				/* listMaterialesPQ.remove(Material); 
						 gridMaterialPQ.setItems(listMaterialesPQ);
						 Material.setEstado(0); 
						 MaterialController.update(Material);

						 message.normalMessage("Material eliminado");*/
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

		gridMaterialOU.setWidth("100%");
		gridMaterialOU.setSelectionMode(SelectionMode.NONE);

		laboratorioMaterialesLayoutOU.addComponents(toolbarMaterialOU, gridMaterialOU);
		laboratorioMaterialesLayoutOU.setMargin(false);
		/*FIN LABORATORIO OPERACIONES UNITARIAS*/
		
		/*LABORATORIO DE ECOTOXICOLOGIA*/
		tabEcotoxicologiaEC.addTab(laboratorioReactivoLayoutEC, "Reactivos", new ThemeResource("images/quimica.png"));
		tabEcotoxicologiaEC.addTab(laboratorioEquipoLayoutEC, "Equipos", new ThemeResource("images/microscopio.png"));
		tabEcotoxicologiaEC.addTab(laboratorioMaterialesLayoutEC, "Materiales", new ThemeResource("images/mortero.png"));
		
		// REACTIVO 
		toolbarReactivoEC.setWidth("100%");
		toolbarReactivoEC.setSpacing(true);
		toolbarReactivoEC.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarReactivoEC.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarReactivoEC.setResponsive(true);
		toolbarReactivoEC.addComponents(mainMenuRectivoEC);

		mainMenuRectivoEC.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuRectivoEC.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuRectivoEC.setResponsive(true);

		mainMenuRectivoEC.addItem("Agregar reactivo a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				//BUSCAR REACTIVO
			}
		});
		
		gridReactivoEC.setRowHeight(45.00);
		gridReactivoEC.addComponentColumn(Trazabilidad -> {
			Label lb = new Label();
			lb.setValue(Trazabilidad.getReactivo().getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridReactivoEC.addColumn(Trazabilidad -> Trazabilidad.getGasto() + " " +Trazabilidad.getReactivo().getNombre().toLowerCase())
		.setCaption("GASTO").setId("GASTO").setExpandRatio(0);
		gridReactivoEC.addComponentColumn(Reactivo -> {
			
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listReactivosEC.remove(Reactivo); 
				gridReactivoEC.setItems(listReactivosEC);
				message.normalMessage("Reactivo eliminado");
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

		gridReactivoEC.setWidth("100%");
		gridReactivoEC.setSelectionMode(SelectionMode.NONE);

		laboratorioReactivoLayoutEC.addComponents(toolbarReactivoEC,gridReactivoEC); 
		laboratorioReactivoLayoutEC.setMargin(false);
		
		//EQUIPOS
		toolbarEquiposEC.setWidth("100%");
		toolbarEquiposEC.setSpacing(true);
		toolbarEquiposEC.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarEquiposEC.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarEquiposEC.setResponsive(true);
		toolbarEquiposEC.addComponents(mainMenuEquipoEC);

		mainMenuEquipoEC.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuEquipoEC.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuEquipoEC.setResponsive(true);

		mainMenuEquipoEC.addItem("Agregar equipo a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/*codigoEquipo.setReadOnly(false);
						newEditEquipo(null);
						pnlComponente.setVisible(false);
						equipoAction = "guardar";*/
			}
		});

		gridEquipoEC.addComponentColumn(Equipo -> {
			Label lb = new Label("");
			lb.setValue(Equipo.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridEquipoEC.addComponentColumn(Equipo -> {
			Label lb = new Label();
			lb.setContentMode(ContentMode.HTML);
			String comp = "";
			Iterator<Componente> iterator = ComponenteController.getAllComponentByEquipo(Equipo.getIdEquipo()).iterator();
			while(iterator.hasNext()) {
				Componente c = iterator.next();
				comp = comp + c.getNombre() + ", ";
			}
			lb.setValue("<font size='1'>"+comp+"</font>");

			lb.setSizeFull();
			return lb;
		}).setCaption("COMPONENTES").setExpandRatio(0);

		gridEquipoEC.addComponentColumn(Equipo -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				/*listEquiposPQ.remove(Equipo); 
				gridEquipoPQ.setItems(listEquiposPQ);
				Equipo.setEstado(0); 
				EquipoController.update(Equipo);
				message.normalMessage("Reactivo eliminado");*/
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

		gridEquipoEC.setWidth("100%");
		gridEquipoEC.setSelectionMode(SelectionMode.NONE);


		laboratorioEquipoLayoutEC.addComponents(toolbarEquiposEC, gridEquipoEC);
		laboratorioEquipoLayoutEC.setMargin(false);
		
		// **MATERIAL**//
		toolbarMaterialEC.setWidth("100%");
		toolbarMaterialEC.setSpacing(true);
		toolbarMaterialEC.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarMaterialEC.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarMaterialEC.setResponsive(true);
		toolbarMaterialEC.addComponents(mainMenuMaterialEC);

		mainMenuMaterialEC.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuMaterialEC.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuMaterialEC.setResponsive(true);

		mainMenuMaterialEC.addItem("Agregar material a proceso", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/*codigoMaterial.setReadOnly(false);
						newEditMaterial(null);
						materialAction = "guardar";*/
			}
		});

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
				/* listMaterialesPQ.remove(Material); 
						 gridMaterialPQ.setItems(listMaterialesPQ);
						 Material.setEstado(0); 
						 MaterialController.update(Material);

						 message.normalMessage("Material eliminado");*/
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

		gridMaterialEC.setWidth("100%");
		gridMaterialEC.setSelectionMode(SelectionMode.NONE);

		laboratorioMaterialesLayoutEC.addComponents(toolbarMaterialEC, gridMaterialEC);
		laboratorioMaterialesLayoutEC.setMargin(false);
		/*FIN LABORATORIO DE ECOTOXICOLOGIA*/
		
	}
	
	public void newEditTraza() {
		//limpiarReactivo();

		dialogWindow dialogReactivoWindow = new dialogWindow("Registro de procesos", VaadinIcons.ARROW_RIGHT);

		/*hl.setCaption("Unidad");
		hl.setSpacing(false);
		hl.setMargin(false);
		hl.addComponents(cmbUnidad, btnAddUnidad);
		 
		formLayoutReactivo.setSpacing(false);
		formLayoutReactivo.setMargin(false);
		formLayoutReactivo.addComponents(codigoReactivo, nombreReactivo, entradaReactivo, gastoRectivo, saldoRectivo,
				hl, fechaCaducidadRectivo);

		dialogReactivoWindow.getOkButton().addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				if (!validatorReactivo.isValid()) {
					validatorReactivo.validate();

					message.warringMessage("Hay errores en los campos de texto");
					return;
				}

				if (reactivoAction.equals("guardar")) {
					
					if (ReactivoController.DBcontainsCodReactivo(codigoReactivo.getValue())) {
						message.warringMessage("El codigo del reactivo ya se encuentra registrado");
						return;
					}
										
					Reactivo react = new Reactivo(codigoReactivo.getValue().toUpperCase().trim(),
							nombreReactivo.getValue().toUpperCase().trim(),
							Float.parseFloat(entradaReactivo.getValue()), fechaCaducidadRectivo.getValue(),
							Float.parseFloat(gastoRectivo.getValue()), Float.parseFloat(saldoRectivo.getValue()),
							cmbUnidad.getValue(), cmbLaboratorio.getValue(), 1);

					ReactivoController.save(react);
				} else {

					reactMod.setCodigo(codigoReactivo.getValue().toUpperCase().trim());
					reactMod.setNombre(nombreReactivo.getValue().toUpperCase().trim());
					reactMod.setEntrada(Float.parseFloat(entradaReactivo.getValue()));
					reactMod.setFechaCaducidad(fechaCaducidadRectivo.getValue());
					reactMod.setGasto(Float.parseFloat(gastoRectivo.getValue()));
					reactMod.setSaldo(Float.parseFloat(saldoRectivo.getValue()));
					reactMod.setUnidad(cmbUnidad.getValue());
					reactMod.setLaboratorio(cmbLaboratorio.getValue());

					ReactivoController.update(reactMod);
					codigoReactivo.setReadOnly(false);
				}

				message.normalMessage("Acción realizada con éxito");

				cargarDatosReactivo();
				dialogReactivoWindow.close();
			}
		});

		dialogReactivoWindow.getCancelButton().addClickListener(e -> {
			dialogReactivoWindow.close();
		});
*/
		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("85%");
		dialogReactivoWindow.addComponentBody(vroot);
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}
	
	public void limpiarTraza() {
		
	}
	
	public void setCss() {
		toolbar.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
	}
	
	
	
	
	
	
}
