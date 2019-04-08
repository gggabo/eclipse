package views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
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

import controllers.ComponenteController;
import models.Componente;
import models.Equipo;
import models.Material;
import models.Trazabilidad;
import models.TrazabilidadEquipo;
import models.TrazabilidadMedioCultivo;
import models.TrazabilidadReactivo;
import utils.dialogWindow;
import utils.message;
import viewComponents.ProcesoComponent;

public class VwTrazabilidad extends Panel {
	private static final long serialVersionUID = 1L;

	public VerticalLayout mainLayout = new VerticalLayout();
	public FormLayout mainFrm;
	public HorizontalLayout toolbar = new HorizontalLayout();
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
	 
	public void initTraza() {
		
		accordion.setCaption("Laboratorios utilizados");
		accordion.addTab(layoutProcesosQuimicos,"Laboratorio Procesos químicos",VaadinIcons.FLASK);
		accordion.addTab(layoutMicrobiologia,"Laboratorio Microbiología",VaadinIcons.CLUSTER);
		accordion.addTab(layoutAG,"Laboratorio Análisis de aguas",VaadinIcons.DROP);
		accordion.addTab(layoutOU,"Laboratorio Operaciones Unitarias",VaadinIcons.BULLSEYE);
		accordion.addTab(layoutEcotoxicologia,"Laboratorio Ecotoxicología",VaadinIcons.FILTER);
		accordion.setWidth("500px");
		accordion.setHeight("400px");
		
		descripcionProceso.setHeight("400px");
		vroot.addComponents(accordion,descripcionProceso);	 
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
			
		}).setCaption("Opciones"); 

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
		}).setCaption("Opciones");

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
		}).setCaption("Opciones");

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
			
		}).setCaption("Opciones");

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
		}).setCaption("Opciones");

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
		}).setCaption("Opciones");

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

		gridMedioCultivoMi.addColumn(Trazabilidad -> Trazabilidad.getGasto() + " " +Trazabilidad.getMedioCultivo().getNombre().toLowerCase())
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

		}).setCaption("Opciones");

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
			
		}).setCaption("Opciones");

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
		}).setCaption("Opciones");

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
		}).setCaption("Opciones");

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

		gridEquipoOU.addComponentColumn(Trazabilidad -> {
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
		}).setCaption("COMPONENTES").setExpandRatio(0);

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
		}).setCaption("Opciones");

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
		}).setCaption("Opciones");

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
			
		}).setCaption("Opciones");

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
		}).setCaption("Opciones");

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
		}).setCaption("Opciones");

		gridMaterialEC.setWidth("100%");
		gridMaterialEC.setHeight("150px");
		gridMaterialEC.setSelectionMode(SelectionMode.NONE);

		laboratorioMaterialesLayoutEC.addComponents(gridMaterialEC);
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
	
	public void buscarEnLab(int lab) {
		dialogWindow dialogReactivoWindow = new dialogWindow("Busqueda", VaadinIcons.ARROW_RIGHT);
		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("60%");
		dialogReactivoWindow.addComponentBody(new VwLaboratoriosBuscar(VwTrazabilidad.this, lab));
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}
	
	public void limpiarTraza() {
		
	}
	
	public void setCss() {
		toolbar.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
	}
	
	
	
	
	
	
}
