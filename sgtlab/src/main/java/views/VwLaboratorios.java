package views;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.vaadin.ui.NumberField;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToFloatConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
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
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.LocalDateRenderer;
import com.vaadin.ui.themes.ValoTheme;

import controllers.ComponenteController;
import controllers.EquipoController;
import controllers.LabotatorioController;
import controllers.MaterialController;
import controllers.MedioCultivoController;
import controllers.ReactivoController;
import controllers.UnidadController;
import models.Componente;
import models.Equipo;
import models.Laboratorio;
import models.Material;
import models.MedioCultivo;
import models.Reactivo;
import models.Rol;
import models.Unidad;
import models.Usuario;
import reports.Reportes;
import utils.UploadImage;
import utils.dialogWindow;
import utils.message;
import utils.uploadXls;

public class VwLaboratorios extends VerticalLayout implements View, Serializable {
	private static final long serialVersionUID = 1L;

	public VwLaboratorios() {
		addComponent(buildUI());
		setCss();
		setPlaceHolder();
		setMaxLengthText();
		addValidation();
		cargarDatosPrincipales();
		buildUser();
		initComponents();
		addStyleName("custom-margin-layout");
	}

	private Reportes reportes = new Reportes();
	
	public VerticalLayout mainLayout = new VerticalLayout();
	public Panel pnlPrincipal = new Panel();
	public FormLayout mainFrm;
	public HorizontalLayout toolbar = new HorizontalLayout();
	public ComboBox<Laboratorio> cmbLaboratorio = new ComboBox<>();
	public List<Laboratorio> listLab = new ArrayList<>();
	//public Button btnAddLaboratorio = new Button(VaadinIcons.PLUS_CIRCLE);

	public TabSheet tabSheet = new TabSheet();

	/*REACTIVO*/
	public Panel pnlReactivos = new Panel();
	public HorizontalLayout toolbarReactivo = new HorizontalLayout();
	public VerticalLayout laboratorioReactivoLayout = new VerticalLayout();
	public MenuBar mainMenuRectivo = new MenuBar();
	public CssLayout filteringReactivo = new CssLayout();
	public Button clearReactivoFilter = new Button(VaadinIcons.CLOSE_CIRCLE);
	public TextField filterReactivotxt = new TextField();
	public Grid<Reactivo> gridReactivo = new Grid<>();
	public List<Reactivo> listReactivos = new ArrayList<>();
	String reactivoAction = "guardar";
	public FormLayout formLayoutReactivo = new FormLayout();
	public TextField codigoReactivo = new TextField("Código");
	public TextField nombreReactivo = new TextField("Nombre");
	public NumberField entradaReactivo = new NumberField("Entrada");
	public NumberField gastoRectivo = new NumberField("Gasto");
	public TextField saldoRectivo = new TextField("Saldo");
	public ComboBox<Unidad> cmbUnidad = new ComboBox<>();
	public List<Unidad> listUnidad = new ArrayList<>();
	//public Button btnAddUnidad = new Button(VaadinIcons.PLUS_CIRCLE);
	public DateField fechaCaducidadRectivo = new DateField("Fecha caducidad");
	/*FIN REACTIVO*/

	/*EQUIPO*/
	public Panel pnlEquipos = new Panel();
	public HorizontalLayout toolbarEquipos = new HorizontalLayout();
	public VerticalLayout laboratorioEquipoLayout = new VerticalLayout();
	public MenuBar mainMenuEquipo = new MenuBar();
	public CssLayout filteringEquipo = new CssLayout();
	public Button clearEquipoFilter = new Button(VaadinIcons.CLOSE_CIRCLE);
	public TextField filterEquipotxt = new TextField();
	public Grid<Equipo> gridEquipo = new Grid<>();
	public List<Equipo> listEquipos = new ArrayList<>();
	String equipoAction = "guardar";
	public FormLayout formLayoutEquipo = new FormLayout();
	public TextField codigoEquipo= new TextField("Código");
	public TextField nombreEquipo = new TextField("Nombre");
	public TextField equipoMarca = new TextField("Marca");
	public NumberField cantidadEquipo = new NumberField("Cantidad");
	public TextArea equipoCaracteristicas = new TextArea("Características");
	public TextArea equipoObservacion = new TextArea("Observación");
	public DateField equipoFechaAdquisicion = new DateField("Fecha adquisición");
	public RadioButtonGroup<String> rbEstadoEquipo = new RadioButtonGroup<>("Estado");
	//EQUIPOS COMPUESTOS
	public Panel pnlComponente = new Panel();
	public HorizontalLayout toolbarComponente = new HorizontalLayout();
	public VerticalLayout laboratorioComponenteLayout = new VerticalLayout();
	public MenuBar mainMenuComponente = new MenuBar();
	public FormLayout formLayoutComponente = new FormLayout();
	public TextField nombreComponente = new TextField("Nombre");
	public TextField marcaComponente = new TextField("Marca");
	public TextField capacidadComponente = new TextField("Capacidad");
	public NumberField cantidadComponente = new NumberField("Cantidad");
	String componenteAction = "guardar";
	Equipo equipo;
	
	public Grid<Componente> gridComponente = new Grid<>();
	public List<Componente> listComponente = new ArrayList<>();
	
	/*FIN EQUIPO*/
	
	/*MATERIALES*/
	public Panel pnlMateriales = new Panel();
	public HorizontalLayout toolbarMaterial = new HorizontalLayout();
	public VerticalLayout laboratorioMaterialesLayout = new VerticalLayout();
	public MenuBar mainMenuMaterial = new MenuBar();
	public CssLayout filteringMaterial = new CssLayout();
	public Button clearMaterialFilter = new Button(VaadinIcons.CLOSE_CIRCLE);
	public TextField filterMaterialtxt = new TextField();
	public Grid<Material> gridMaterial = new Grid<>();
	public List<Material> listMateriales = new ArrayList<>();
	String materialAction = "guardar";
	public FormLayout formLayoutMaterial = new FormLayout();
	public TextField codigoMaterial= new TextField("Código");
	public TextField nombreMaterial = new TextField("Nombre");
	public TextField marcaMaterial = new TextField("Marca");
	public TextField capacidadMaterial = new TextField("Capacidad");
	public TextField tipoMaterial = new TextField("Tipo material");
	public NumberField cantidadMaterial = new NumberField("Cantidad");
	public TextArea observacionMaterial = new TextArea("Observación");	
	/*FIN MATERIALES*/
	
	/*MEDIO DE CULTIVO*/
	public Panel pnlMedioCultivo = new Panel();
	public HorizontalLayout toolbarMedioCultivo = new HorizontalLayout();
	public VerticalLayout laboratorioMedioCultivoLayout = new VerticalLayout();
	public MenuBar mainMenuMedioCultivo = new MenuBar();
	public CssLayout filteringMedioCultivo = new CssLayout();
	public Button clearMedioCultivoFilter = new Button(VaadinIcons.CLOSE_CIRCLE);
	public TextField filterMedioCultivotxt = new TextField();
	public Grid<MedioCultivo> gridMedioCultivo = new Grid<>();
	public List<MedioCultivo> listMediosCultivos = new ArrayList<>();
	String MedioCultivoAction = "guardar";
	public FormLayout formLayoutMedioCultivo = new FormLayout();
	public TextField codigoMedioCultivo = new TextField("Código");
	public TextField nombreMedioCultivo = new TextField("Nombre");
	public NumberField entradaMedioCultivo = new NumberField("Entrada");
	public NumberField gastoMedioCultivo = new NumberField("Gasto");
	public TextField saldoMedioCultivo = new TextField("Saldo");
	public ComboBox<Unidad> cmbUnidadMedioCultivo = new ComboBox<>();
	public List<Unidad> listUnidadMedioCultivo = new ArrayList<>();
	public Button btnAddUnidadMedioCultivo = new Button(VaadinIcons.PLUS_CIRCLE);
	public DateField fechaCaducidadMedioCultivo = new DateField("Fecha caducidad");
	/*FIN MEDIO CULTIVO*/
	

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
    long laboratorio;
	public Component buildUI() {

		cmbLaboratorio.setWidth("100%");
		cmbLaboratorio.setTextInputAllowed(false);
		cmbLaboratorio.setEmptySelectionAllowed(false);

		// cmbLaboratorio.setEmptySelectionCaption("Seleccionar laboratorio");

		cmbLaboratorio.addValueChangeListener(e -> {
			laboratorio = e.getValue().getIdLaboratorio();
			if(laboratorio == 1) { //PROCESOS QUIMICOS
			tabSheet.getTab(0).setVisible(true);
			tabSheet.getTab(1).setVisible(true);
			tabSheet.getTab(2).setVisible(true);
			tabSheet.getTab(3).setVisible(false);
		}else if(laboratorio == 2) {//MICROBIOLOGIA
			tabSheet.getTab(0).setVisible(true);
			tabSheet.getTab(1).setVisible(true);
			tabSheet.getTab(2).setVisible(true);
			tabSheet.getTab(3).setVisible(true);
		}else if(laboratorio == 3) { // OPERACIONES UNITARIAS
			tabSheet.getTab(0).setVisible(false);
			tabSheet.getTab(1).setVisible(true);
			tabSheet.getTab(2).setVisible(true);
			tabSheet.getTab(3).setVisible(false);
		}else if(laboratorio == 4) { // ANALISIS DE AGUAS
			tabSheet.getTab(0).setVisible(true);
			tabSheet.getTab(1).setVisible(true);
			tabSheet.getTab(2).setVisible(true);
			tabSheet.getTab(3).setVisible(false);
		}else if(laboratorio == 5) { // ECOTOXICOLOGIA
			tabSheet.getTab(0).setVisible(true);
			tabSheet.getTab(1).setVisible(true);
			tabSheet.getTab(2).setVisible(true);
			tabSheet.getTab(3).setVisible(false);
		}
			
			listReactivos.clear();
			gridReactivo.setItems(listReactivos);
			
			listEquipos.clear();
			gridEquipo.setItems(listEquipos);
			
			listMateriales.clear();
			gridMaterial.setItems(listMateriales); 
			
			listMediosCultivos.clear();
			gridMedioCultivo.setItems(listMediosCultivos);
			
			if (cmbLaboratorio.getValue() == null) {
				// tabSheet.setEnabled(false);
				return;
			}

			listReactivos.addAll(
					ReactivoController.searchReactiveByLaboratory(cmbLaboratorio.getValue(),filterReactivotxt.getValue()));
			gridReactivo.setItems(listReactivos);
			
			listEquipos.addAll(
					EquipoController.searchEquipoByLaboratory(cmbLaboratorio.getValue(), filterEquipotxt.getValue()));
			gridEquipo.setItems(listEquipos);
			
			listMateriales.addAll( 
					MaterialController.searchMaterialByLaboratory(cmbLaboratorio.getValue(), filterMaterialtxt.getValue()));
			gridMaterial.setItems(listMateriales);
			
			listMediosCultivos.addAll(
					MedioCultivoController.searchMedioCultivoByLaboratory(cmbLaboratorio.getValue(), filterMedioCultivotxt.getValue()));
			gridMedioCultivo.setItems(listMediosCultivos);
			
		});    
  
		toolbar.setWidth("100%");
		toolbar.setSpacing(true);
		toolbar.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbar.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbar.setResponsive(true);
		toolbar.addComponents(cmbLaboratorio);

		tabSheet.addTab(pnlReactivos, "Reactivos", new ThemeResource("images/quimica.png"));
		tabSheet.addTab(pnlEquipos, "Equipos", new ThemeResource("images/microscopio.png"));
		tabSheet.addTab(pnlMateriales, "Materiales", new ThemeResource("images/mortero.png"));
		tabSheet.addTab(pnlMedioCultivo, "M. Cultivo", new ThemeResource("images/molecule.png"));

		// **REACTIVO**// 
		toolbarReactivo.setWidth("100%");
		toolbarReactivo.setSpacing(true);
		toolbarReactivo.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarReactivo.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarReactivo.setResponsive(true);
		toolbarReactivo.addComponents(mainMenuRectivo);

		mainMenuRectivo.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuRectivo.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuRectivo.setResponsive(true);

		mainMenuRectivo.addItem("Nuevo reactivo", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				codigoReactivo.setReadOnly(false);
				newEditReactivo(null);
				reactivoAction = "guardar";
			}
		});
		
		mainMenuRectivo.addItem("Imprimir inventario", VaadinIcons.PRINT, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				reportes.generarInformeReactivos(cmbLaboratorio.getValue().getIdLaboratorio());
			}
		});

		filterReactivotxt.setPlaceholder("Buscar por codigo o nombre");
		filterReactivotxt.setValueChangeMode(ValueChangeMode.LAZY);
		filterReactivotxt.setSizeFull();
		filterReactivotxt.addValueChangeListener(e -> {
			listReactivos.clear();
			listReactivos.addAll(ReactivoController.searchReactiveByLaboratory(cmbLaboratorio.getValue(),
					filterReactivotxt.getValue()));
			gridReactivo.setItems(listReactivos);
		});

		clearReactivoFilter.addClickListener(e -> {
			filterReactivotxt.clear();
		});

		filteringReactivo.addComponents(filterReactivotxt, clearReactivoFilter);
		filteringReactivo.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filteringReactivo.addStyleName("custom-margins");

		gridReactivo.setRowHeight(45.00);
		gridReactivo.addColumn(Reactivo::getCodigo).setCaption("CÓDIGO").setId("CODIGO").setExpandRatio(0)
				.setStyleGenerator(Reactivo -> "v-align-middle-center");

		gridReactivo.addComponentColumn(Reactivo -> {
			Label lb = new Label();
			lb.setValue(Reactivo.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridReactivo.addColumn(Reactivo -> Reactivo.getEntrada() + " " + Reactivo.getUnidad().getNombre().toLowerCase())
				.setCaption("ENTRADA").setId("ENTRADA").setExpandRatio(0);
		gridReactivo.addColumn(Reactivo -> Reactivo.getGasto() + " " + Reactivo.getUnidad().getNombre().toLowerCase())
				.setCaption("GASTO").setId("GASTO").setExpandRatio(0);
		gridReactivo.addColumn(Reactivo -> Reactivo.getSaldo() + " " + Reactivo.getUnidad().getNombre().toLowerCase())
				.setCaption("SALDO").setId("SALDO").setExpandRatio(0);
		gridReactivo.addColumn(Reactivo -> Reactivo.getFechaCaducidad(), new LocalDateRenderer("dd/MM/yyyy"))
				.setCaption("F. CADUCIDAD").setId("FCADUCIDAD").setExpandRatio(0);

		gridReactivo.addComponentColumn(Reactivo -> {

			Button b = new Button("Editar");
			b.addClickListener(clickb -> {
				newEditReactivo(Reactivo);

				codigoReactivo.setValue(Reactivo.getCodigo());
				codigoReactivo.setReadOnly(true);
				nombreReactivo.setValue(Reactivo.getNombre());
				entradaReactivo.setValue(String.valueOf(Reactivo.getEntrada()));
				gastoRectivo.setValue(String.valueOf(Reactivo.getGasto()));
				saldoRectivo.setValue(String.valueOf(Reactivo.getSaldo()));
				cmbUnidad.setValue(Reactivo.getUnidad());
				fechaCaducidadRectivo.setValue(Reactivo.getFechaCaducidad());

				reactivoAction = "modificar";

			}); 
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.EDIT);

			Button b2 = new Button("Eliminar");
			b2.addClickListener(clickb2 -> {
				 listReactivos.remove(Reactivo); 
				 gridReactivo.setItems(listReactivos);
				 Reactivo.setEstado(0); 
				 ReactivoController.update(Reactivo);
				 
				 message.normalMessage("Reactivo eliminado");
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b, b2);
			return hl;
		}).setCaption("Opciones");

		gridReactivo.setWidth("100%");
		gridReactivo.setSelectionMode(SelectionMode.NONE);

		pnlReactivos.setCaption("Gestión de reactivos");
		pnlReactivos.setIcon(VaadinIcons.FLASK);
		pnlReactivos.setContent(laboratorioReactivoLayout);

		laboratorioReactivoLayout.addComponents(toolbarReactivo, filteringReactivo, gridReactivo);
		laboratorioReactivoLayout.setMargin(false);
		// **FIN REACTIVO**//

		// **EQUIPO**//
		toolbarEquipos.setWidth("100%");
		toolbarEquipos.setSpacing(true);
		toolbarEquipos.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarEquipos.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarEquipos.setResponsive(true);
		toolbarEquipos.addComponents(mainMenuEquipo);

		mainMenuEquipo.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuEquipo.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuEquipo.setResponsive(true);

		mainMenuEquipo.addItem("Nuevo equipo", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				codigoEquipo.setReadOnly(false);
				newEditEquipo(null);
				pnlComponente.setVisible(false);
				equipoAction = "guardar";
			}
		});
		
		mainMenuEquipo.addItem("Imprimir inventario", VaadinIcons.PRINT, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				if(cmbLaboratorio.getValue().getIdLaboratorio() != 3) {
					reportes.generarInformeEquipos(cmbLaboratorio.getValue().getIdLaboratorio());
				}else {
					reportes.generarInformeEquiposOU(cmbLaboratorio.getValue().getIdLaboratorio());
				}
			}
		});

		filterEquipotxt.setPlaceholder("Buscar por codigo o nombre");
		filterEquipotxt.setValueChangeMode(ValueChangeMode.LAZY);
		filterEquipotxt.setSizeFull();
		filterEquipotxt.addValueChangeListener(e -> {
			listEquipos.clear();
			listEquipos.addAll(EquipoController.searchEquipoByLaboratory(cmbLaboratorio.getValue(),
					filterEquipotxt.getValue()));
			gridEquipo.setItems(listEquipos);
		});

		clearEquipoFilter.addClickListener(e -> {
			filterEquipotxt.clear();
		});

		filteringEquipo.addComponents(filterEquipotxt, clearEquipoFilter);
		filteringEquipo.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filteringEquipo.addStyleName("custom-margins");

		gridEquipo.setRowHeight(45.00);
		gridEquipo.addColumn(Equipo::getCodigo).setCaption("CÓDIGO").setId("CODIGO").setExpandRatio(0)
				.setStyleGenerator(Equipo -> "v-align-middle-center");

		gridEquipo.addComponentColumn(Equipo -> {
			Label lb = new Label("");
			lb.setValue(Equipo.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);
		
		
		gridEquipo.addColumn(Equipo -> Equipo.getMarca()).setCaption("MARCA").setId("EQUIPOMARCA").setExpandRatio(0);
		
		gridEquipo.addComponentColumn(Equipo -> {
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
		
		gridEquipo.addColumn(Equipo -> Equipo.getCantidad()).setCaption("CANT").setId("CANTIDADEQUIPO").setExpandRatio(0);
		gridEquipo.addColumn(Equipo -> Equipo.getEstadoEquipo()).setCaption("ESTADO").setId("ESTADOEQUIPO").setExpandRatio(0);

		gridEquipo.addComponentColumn(Equipo -> {

			Button b = new Button("Editar");
			b.addClickListener(clickb -> {
				
				newEditEquipo(Equipo);
				pnlComponente.setVisible(true);
				codigoEquipo.setValue(Equipo.getCodigo());
				codigoEquipo.setReadOnly(true);
				nombreEquipo.setValue(Equipo.getNombre());
				equipoMarca.setValue(Equipo.getMarca());
				cantidadEquipo.setValue(String.valueOf(Equipo.getCantidad()));
				rbEstadoEquipo.setValue(Equipo.getEstadoEquipo());
				equipoObservacion.setValue(Equipo.getObservacion());
				equipoCaracteristicas.setValue(Equipo.getCaracteristicas());
				equipoFechaAdquisicion.setValue(Equipo.getFechaAdquisicion());
				equipoAction = "modificar";
				equipo = Equipo;
				
				cargarDatosComponente(Equipo);

			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.EDIT);

			Button b2 = new Button("Eliminar");
			b2.addClickListener(clickb2 -> {
				 listEquipos.remove(Equipo); 
				 gridEquipo.setItems(listEquipos);
				 Equipo.setEstado(0); 
				 EquipoController.update(Equipo);
				 message.normalMessage("Reactivo eliminado");
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b, b2);
			return hl;
		}).setCaption("Opciones");

		gridEquipo.setWidth("100%");
		gridEquipo.setSelectionMode(SelectionMode.NONE);

		pnlEquipos.setCaption("Gestión de equipos");
		pnlEquipos.setIcon(VaadinIcons.FLASK);
		pnlEquipos.setContent(laboratorioEquipoLayout);

		laboratorioEquipoLayout.addComponents(toolbarEquipos, filteringEquipo, gridEquipo);
		laboratorioEquipoLayout.setMargin(false);
		
		//COMPONENTES DE EQUIPO COMPUESTO
		toolbarComponente.setWidth("100%");
		toolbarComponente.setSpacing(true);
		toolbarComponente.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarComponente.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarComponente.setResponsive(true);
		toolbarComponente.addComponents(mainMenuComponente);

		mainMenuComponente.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuComponente.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuComponente.setResponsive(true);

		mainMenuComponente.addItem("Nuevo componente", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				newEditComponente(null,equipo);
				componenteAction = "guardar";
			}
		});
		
		gridComponente.setRowHeight(35.00); 
		gridComponente.setHeight("190px");
		gridComponente.addColumn(Componente -> Componente.getNombre()).setCaption("NOMBRE").setExpandRatio(0);
		gridComponente.addColumn(Componente -> Componente.getMarca()).setCaption("MARCA").setExpandRatio(0);
		gridComponente.addColumn(Componente -> Componente.getCapacidad()).setCaption("CAPACIDAD").setExpandRatio(0);
		gridComponente.addColumn(Componente -> Componente.getCantidad()).setCaption("CANTIDAD").setExpandRatio(0);
		gridComponente.setWidth("100%");
		gridComponente.setSelectionMode(SelectionMode.NONE);
		
		gridComponente.addComponentColumn(Componente -> {

			Button b = new Button("Editar");
			b.addClickListener(clickb -> {
				newEditComponente(Componente, equipo);
				nombreComponente.setValue(Componente.getNombre()); 
				marcaComponente.setValue(Componente.getMarca());  
				capacidadComponente.setValue(Componente.getCapacidad()); 
				cantidadComponente.setValue(String.valueOf(Componente.getCantidad())); 
				componenteAction = "modificar";

			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.EDIT);

			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				 Componente.setEstado(0); 
				 ComponenteController.update(Componente);
				 message.normalMessage("Componente eliminado");
				 cargarDatosComponente(equipo);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b, b2);
			return hl;
		}).setCaption("Opciones"); 
		
		laboratorioComponenteLayout.addComponents(toolbarComponente, gridComponente);
		laboratorioComponenteLayout.setMargin(false);
		
		pnlComponente.setCaption("Gestión de componentes");
		pnlComponente.setIcon(VaadinIcons.FLASK);
		pnlComponente.setContent(laboratorioComponenteLayout);
		
		// **FIN EQUIPO**//

		// **MATERIAL**//
		toolbarMaterial.setWidth("100%");
		toolbarMaterial.setSpacing(true);
		toolbarMaterial.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarMaterial.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarMaterial.setResponsive(true);
		toolbarMaterial.addComponents(mainMenuMaterial);

		mainMenuMaterial.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuMaterial.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuMaterial.setResponsive(true);

		mainMenuMaterial.addItem("Nuevo material", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				codigoMaterial.setReadOnly(false);
				newEditMaterial(null);
				materialAction = "guardar";
			}
		});

		mainMenuMaterial.addItem("Imprimir inventario", VaadinIcons.PRINT, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				reportes.generarInformeMateriales(cmbLaboratorio.getValue().getIdLaboratorio());
			}
		});
		
		filterMaterialtxt.setPlaceholder("Buscar por codigo o nombre");
		filterMaterialtxt.setValueChangeMode(ValueChangeMode.LAZY);
		filterMaterialtxt.setSizeFull();
		filterMaterialtxt.addValueChangeListener(e -> {
			listMateriales.clear();
			listMateriales.addAll(MaterialController.searchMaterialByLaboratory(cmbLaboratorio.getValue(),
					filterMaterialtxt.getValue()));
			gridMaterial.setItems(listMateriales);
		});

		clearMaterialFilter.addClickListener(e -> {
			filterMaterialtxt.clear();
		});

		filteringMaterial.addComponents(filterMaterialtxt, clearMaterialFilter);
		filteringMaterial.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filteringMaterial.addStyleName("custom-margins");

		gridMaterial.setRowHeight(45.00);
		gridMaterial.addColumn(Material::getCodigo).setCaption("CÓDIGO").setId("CODIGO").setExpandRatio(0)
				.setStyleGenerator(Material -> "v-align-middle-center");

		gridMaterial.addComponentColumn(Material -> {
			Label lb = new Label();
			lb.setValue(Material.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridMaterial.addColumn(Material -> Material.getMarca()).setCaption("MARCA").setExpandRatio(0);
		gridMaterial.addColumn(Material -> Material.getTipoMaterial()).setCaption("TIPO").setExpandRatio(0);
		gridMaterial.addColumn(Material -> Material.getCapacidad()).setCaption("CAPACIDAD").setExpandRatio(0);
		gridMaterial.addColumn(Material -> Material.getCantidad()).setCaption("CANTIDAD").setExpandRatio(0);

		gridMaterial.addComponentColumn(Material -> {

			Button b = new Button("Editar");
			b.addClickListener(clickb -> {
				newEditMaterial(Material);
				
				codigoMaterial.setReadOnly(true);
				codigoMaterial.setValue(Material.getCodigo());
				nombreMaterial.setValue(Material.getNombre());
				marcaMaterial.setValue(Material.getMarca());
				tipoMaterial.setValue(Material.getTipoMaterial());
				capacidadMaterial.setValue(Material.getCapacidad());
				cantidadMaterial.setValue(String.valueOf(Material.getCantidad()));
				observacionMaterial.setValue(Material.getObservacion());
				
				materialAction = "modificar";

			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.EDIT);

			Button b2 = new Button("Eliminar");
			b2.addClickListener(clickb2 -> {
				 listMateriales.remove(Material); 
				 gridMaterial.setItems(listMateriales);
				 Material.setEstado(0); 
				 MaterialController.update(Material);
				 
				 message.normalMessage("Material eliminado");
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b, b2);
			return hl;
		}).setCaption("Opciones");

		gridMaterial.setWidth("100%");
		gridMaterial.setSelectionMode(SelectionMode.NONE);

		pnlMateriales.setCaption("Gestión de materiales");
		pnlMateriales.setIcon(VaadinIcons.FLASK);
		pnlMateriales.setContent(laboratorioMaterialesLayout);

		laboratorioMaterialesLayout.addComponents(toolbarMaterial, filteringMaterial, gridMaterial);
		laboratorioMaterialesLayout.setMargin(false);
		// **FIN MATERIAL**//

		// **MEDIOS DE CULTIVO**//
		toolbarMedioCultivo.setWidth("100%");
		toolbarMedioCultivo.setSpacing(true);
		toolbarMedioCultivo.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarMedioCultivo.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarMedioCultivo.setResponsive(true);
		toolbarMedioCultivo.addComponents(mainMenuMedioCultivo);

		mainMenuMedioCultivo.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuMedioCultivo.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuMedioCultivo.setResponsive(true);

		mainMenuMedioCultivo.addItem("Nuevo medio cultivo", VaadinIcons.PLUS_CIRCLE, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				codigoMedioCultivo.setReadOnly(false);
				newEditMedioCultivo(null);
				MedioCultivoAction = "guardar";
			}
		});
		
		mainMenuMedioCultivo.addItem("Imprimir inventario", VaadinIcons.PRINT, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				reportes.generarInformeMediosCultivo(cmbLaboratorio.getValue().getIdLaboratorio());
			}
		});

		filterMedioCultivotxt.setPlaceholder("Buscar por codigo o nombre");
		filterMedioCultivotxt.setValueChangeMode(ValueChangeMode.LAZY);
		filterMedioCultivotxt.setSizeFull();
		filterMedioCultivotxt.addValueChangeListener(e -> {
			listMediosCultivos.clear();
			listMediosCultivos.addAll(MedioCultivoController.searchMedioCultivoByLaboratory(cmbLaboratorio.getValue(),
					filterMedioCultivotxt.getValue()));
			gridMedioCultivo.setItems(listMediosCultivos);
		});

		clearMedioCultivoFilter.addClickListener(e -> {
			filterMedioCultivotxt.clear();
		});

		filteringMedioCultivo.addComponents(filterMedioCultivotxt, clearMedioCultivoFilter);
		filteringMedioCultivo.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filteringMedioCultivo.addStyleName("custom-margins");

		gridMedioCultivo.setRowHeight(45.00);
		gridMedioCultivo.addColumn(MedioCultivo::getCodigo).setCaption("CÓDIGO").setId("CODIGO").setExpandRatio(0)
				.setStyleGenerator(MedioCultivo -> "v-align-middle-center");

		gridMedioCultivo.addComponentColumn(MedioCultivo -> {
			Label lb = new Label();
			lb.setValue(MedioCultivo.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridMedioCultivo.addColumn(MedioCultivo -> MedioCultivo.getEntrada() + " " + MedioCultivo.getUnidad().getNombre().toLowerCase())
				.setCaption("ENTRADA").setId("ENTRADA").setExpandRatio(0);
		gridMedioCultivo.addColumn(MedioCultivo -> MedioCultivo.getGasto() + " " + MedioCultivo.getUnidad().getNombre().toLowerCase())
				.setCaption("GASTO").setId("GASTO").setExpandRatio(0);
		gridMedioCultivo.addColumn(MedioCultivo -> MedioCultivo.getSaldo() + " " + MedioCultivo.getUnidad().getNombre().toLowerCase())
				.setCaption("SALDO").setId("SALDO").setExpandRatio(0);
		gridMedioCultivo.addColumn(MedioCultivo -> MedioCultivo.getFechaCaducidad(), new LocalDateRenderer("dd/MM/yyyy"))
				.setCaption("F. CADUCIDAD").setId("FCADUCIDAD").setExpandRatio(0);

		gridMedioCultivo.addComponentColumn(MedioCultivo -> {

			Button b = new Button("Editar");
			b.addClickListener(clickb -> {
				newEditMedioCultivo(MedioCultivo);

				codigoMedioCultivo.setValue(MedioCultivo.getCodigo());
				codigoMedioCultivo.setReadOnly(true);
				nombreMedioCultivo.setValue(MedioCultivo.getNombre());
				entradaMedioCultivo.setValue(String.valueOf(MedioCultivo.getEntrada()));
				gastoMedioCultivo.setValue(String.valueOf(MedioCultivo.getGasto()));
				saldoMedioCultivo.setValue(String.valueOf(MedioCultivo.getSaldo()));
				cmbUnidadMedioCultivo.setValue(MedioCultivo.getUnidad());
				fechaCaducidadMedioCultivo.setValue(MedioCultivo.getFechaCaducidad());

				MedioCultivoAction = "modificar";

			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.EDIT);

			Button b2 = new Button("Eliminar");
			b2.addClickListener(clickb2 -> {
				 listMediosCultivos.remove(MedioCultivo); 
				 gridMedioCultivo.setItems(listMediosCultivos);
				 MedioCultivo.setEstado(0); 
				 MedioCultivoController.update(MedioCultivo);
				 
				 message.normalMessage("Medio de cultivo eliminado");
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b, b2);
			return hl;
		}).setCaption("Opciones");

		gridMedioCultivo.setWidth("100%");
		gridMedioCultivo.setSelectionMode(SelectionMode.NONE);

		pnlMedioCultivo.setCaption("Gestión de medios de cultivos");
		pnlMedioCultivo.setIcon(VaadinIcons.FLASK);
		pnlMedioCultivo.setContent(laboratorioMedioCultivoLayout);

		laboratorioMedioCultivoLayout.addComponents(toolbarMedioCultivo, filteringMedioCultivo, gridMedioCultivo);
		laboratorioMedioCultivoLayout.setMargin(false);
		// **FIN MEDIOS CULTIVO**//
		
		laboratorioLayout.addComponents(toolbar, tabSheet);
		laboratorioLayout.setMargin(false);

		pnlPrincipal.setCaption("Gestión de laboratorios");
		pnlPrincipal.setIcon(VaadinIcons.FLASK);
		pnlPrincipal.setContent(laboratorioLayout);

		mainLayout.addComponents(pnlPrincipal);
		return mainLayout;
	}

	public void limpiarMainComponents() {

	}

	HorizontalLayout hl = new HorizontalLayout();
	public void newEditReactivo(Reactivo reactMod) {
		limpiarReactivo();

		dialogWindow dialogReactivoWindow = new dialogWindow("Gestión de reactivos", VaadinIcons.FLASK);

		hl.setCaption("Unidad");
		hl.setSpacing(false);
		hl.setMargin(false);
		hl.addComponents(cmbUnidad);
		 
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

		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("32%");
		dialogReactivoWindow.addComponentBody(formLayoutReactivo);
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}

	public void limpiarReactivo() {
		codigoReactivo.setValue("");
		nombreReactivo.setValue("");
		entradaReactivo.setValue("");
		gastoRectivo.setValue("");
		saldoRectivo.setValue("");
		fechaCaducidadRectivo.setValue(LocalDate.now());
	}
	
	public void newEditEquipo(Equipo equipo) {
		limpiarEquipo();

		dialogWindow dialogReactivoWindow = new dialogWindow("Gestión de equipos", VaadinIcons.FLASK);
		 
		formLayoutEquipo.setSpacing(false);
		formLayoutEquipo.setMargin(false);
		formLayoutEquipo.addComponents(codigoEquipo, nombreEquipo, equipoMarca, cantidadEquipo,equipoObservacion,equipoCaracteristicas,
				equipoFechaAdquisicion,rbEstadoEquipo);
 
		dialogReactivoWindow.getOkButton().addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				if (!validatorEquipo.isValid()) {
					validatorEquipo.validate();

					message.warringMessage("Hay errores en los campos de texto");
					return;
				}

				if (equipoAction.equals("guardar")) {
					
					if (EquipoController.DBcontainsCodEquipo(codigoEquipo.getValue())) {
						message.warringMessage("El codigo del equipo ya se encuentra registrado");
						return;
					}
										
					Equipo eq = new Equipo(codigoEquipo.getValue().toUpperCase().trim(),nombreEquipo.getValue().toUpperCase().trim(),
							equipoMarca.getValue().toUpperCase().trim(),Integer.parseInt(cantidadEquipo.getValue()),
							equipoObservacion.getValue().toUpperCase().trim(),equipoCaracteristicas.getValue().toUpperCase().trim(),
							equipoFechaAdquisicion.getValue(),rbEstadoEquipo.getValue(),cmbLaboratorio.getValue(),1);
					EquipoController.save(eq);
				} else {

					equipo.setCodigo(codigoEquipo.getValue().toUpperCase().trim());
					equipo.setNombre(nombreEquipo.getValue().toUpperCase().trim());
					equipo.setMarca(equipoMarca.getValue().toUpperCase().trim());
					equipo.setCantidad(Integer.parseInt(cantidadEquipo.getValue()));
					equipo.setEstadoEquipo(rbEstadoEquipo.getValue());					
					equipo.setLaboratorio(cmbLaboratorio.getValue());
					equipo.setObservacion(equipoObservacion.getValue().toUpperCase().trim());
					equipo.setCaracteristicas(equipoCaracteristicas.getValue().toUpperCase().trim());
					equipo.setFechaAdquisicion(equipoFechaAdquisicion.getValue());
					
					EquipoController.update(equipo);
					codigoEquipo.setReadOnly(false);
				}

				message.normalMessage("Acción realizada con éxito");

				cargarDatosEquipo();
				dialogReactivoWindow.close();
			}
		});

		dialogReactivoWindow.getCancelButton().addClickListener(e -> {
			dialogReactivoWindow.close();
		});


		
		VerticalLayout vroot = new VerticalLayout();
		vroot.addComponents(formLayoutEquipo,pnlComponente);
		vroot.setMargin(false);
		
		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("50%");
		dialogReactivoWindow.addComponentBody(vroot);
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}

	public void limpiarEquipo() {
		codigoEquipo.setValue("");
		nombreEquipo.setValue("");
		equipoMarca.setValue("");
		cantidadEquipo.setValue("");
		equipoObservacion.setValue("");
		equipoCaracteristicas.setValue("");
		equipoFechaAdquisicion.setValue(LocalDate.now());
		rbEstadoEquipo.setSelectedItem("BUENO");
	}	
	
	public void newEditComponente(Componente componente, Equipo eq) {
		limpiarComponente();
		
		dialogWindow dialogReactivoWindow = new dialogWindow("Gestión de componentes de equipos", VaadinIcons.FLASK);
		 
		formLayoutComponente.setSpacing(false);
		formLayoutComponente.setMargin(false);
		formLayoutComponente.addComponents(nombreComponente, marcaComponente, capacidadComponente, cantidadComponente);

		dialogReactivoWindow.getOkButton().addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				if (!validatorComponente.isValid()) {
					validatorComponente.validate();

					message.warringMessage("Hay errores en los campos de texto");
					return;
				}

				if (componenteAction.equals("guardar")) {	
					Componente comp = new Componente(nombreComponente.getValue().toUpperCase().trim(), marcaComponente.getValue().toUpperCase().trim(), 
							capacidadComponente.getValue().toUpperCase().trim(), Integer.parseInt(cantidadComponente.getValue()), eq, 1);
					
					ComponenteController.save(comp);
					
				} else {

					componente.setNombre(nombreComponente.getValue().toUpperCase().trim());
					componente.setMarca(marcaComponente.getValue().toUpperCase().trim());
					componente.setCapacidad(capacidadComponente.getValue().toUpperCase().trim());
					componente.setCantidad(Integer.parseInt(cantidadComponente.getValue()));
					componente.setEquipo(eq);
					
					ComponenteController.update(componente);
				}

				message.normalMessage("Acción realizada con éxito");
				cargarDatosComponente(eq);
				dialogReactivoWindow.close();
			}
		});

		dialogReactivoWindow.getCancelButton().addClickListener(e -> {
			dialogReactivoWindow.close();
		});

		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("32%");
		dialogReactivoWindow.addComponentBody(formLayoutComponente);
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}
	
	public void limpiarComponente() {
		nombreComponente.setValue("");
		marcaComponente.setValue("");
		capacidadComponente.setValue("");
		cantidadComponente.setValue("");
	}
	
	public void newEditMaterial(Material material) {
		limpiarMaterial();

		dialogWindow dialogReactivoWindow = new dialogWindow("Gestión de Materiales", VaadinIcons.FLASK);
		 
		formLayoutMaterial.setSpacing(false);
		formLayoutMaterial.setMargin(false);
		formLayoutMaterial.addComponents(codigoMaterial, nombreMaterial, marcaMaterial,tipoMaterial, capacidadMaterial, cantidadMaterial, observacionMaterial);
 
		dialogReactivoWindow.getOkButton().addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				if (!validatorMaterial.isValid()) {
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

				cargarDatosMaterial();;
				dialogReactivoWindow.close();
			}
		});

		dialogReactivoWindow.getCancelButton().addClickListener(e -> {
			dialogReactivoWindow.close();
		});


		
		VerticalLayout vroot = new VerticalLayout();
		vroot.addComponents(formLayoutMaterial);
		vroot.setMargin(false);
		
		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("35%");
		dialogReactivoWindow.addComponentBody(vroot);
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}

	public void limpiarMaterial() {
		codigoMaterial.setValue("");
		nombreMaterial.setValue("");
		marcaMaterial.setValue("");
		capacidadMaterial.setValue("");
		cantidadMaterial.setValue("");
		observacionMaterial.setValue("");
	}	
	
	HorizontalLayout hlmc = new HorizontalLayout();
	public void newEditMedioCultivo(MedioCultivo medioCultivo) {
		limpiarMedioCultivo();

		dialogWindow dialogMcWindow = new dialogWindow("Gestión de medios de cultivos", VaadinIcons.FLASK);

		hlmc.setCaption("Unidad");
		hlmc.setSpacing(false);
		hlmc.setMargin(false);
		hlmc.addComponents(cmbUnidadMedioCultivo, btnAddUnidadMedioCultivo);
		 
		formLayoutMedioCultivo.setSpacing(false);
		formLayoutMedioCultivo.setMargin(false);
		formLayoutMedioCultivo.addComponents(codigoMedioCultivo, nombreMedioCultivo, entradaMedioCultivo, gastoMedioCultivo, saldoMedioCultivo,
				hlmc, fechaCaducidadMedioCultivo);

		dialogMcWindow.getOkButton().addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				if (!validatorMc.isValid()) {
					validatorReactivo.validate();

					message.warringMessage("Hay errores en los campos de texto");
					return;
				}

				if (MedioCultivoAction.equals("guardar")) {
					
					if (ReactivoController.DBcontainsCodReactivo(codigoMedioCultivo.getValue())) {
						message.warringMessage("El codigo del medio de cultivo ya se encuentra registrado");
						return;
					}
										
					MedioCultivo mc = new MedioCultivo(codigoMedioCultivo.getValue().toUpperCase().trim(),
							nombreMedioCultivo.getValue().toUpperCase().trim(),
							Float.parseFloat(entradaMedioCultivo.getValue()), fechaCaducidadMedioCultivo.getValue(),
							Float.parseFloat(gastoMedioCultivo.getValue()), Float.parseFloat(saldoMedioCultivo.getValue()),
							cmbUnidadMedioCultivo.getValue(), cmbLaboratorio.getValue(), 1);

					MedioCultivoController.save(mc);
				} else {

					medioCultivo.setCodigo(codigoMedioCultivo.getValue().toUpperCase().trim());
					medioCultivo.setNombre(nombreMedioCultivo.getValue().toUpperCase().trim());
					medioCultivo.setEntrada(Float.parseFloat(entradaMedioCultivo.getValue()));
					medioCultivo.setFechaCaducidad(fechaCaducidadMedioCultivo.getValue());
					medioCultivo.setGasto(Float.parseFloat(gastoMedioCultivo.getValue()));
					medioCultivo.setSaldo(Float.parseFloat(saldoMedioCultivo.getValue()));
					medioCultivo.setUnidad(cmbUnidadMedioCultivo.getValue());
					medioCultivo.setLaboratorio(cmbLaboratorio.getValue());

					MedioCultivoController.update(medioCultivo);
					codigoReactivo.setReadOnly(false);
				}

				message.normalMessage("Acción realizada con éxito");

				cargarDatosMediosCultivo();
				dialogMcWindow.close();
			}
		});

		dialogMcWindow.getCancelButton().addClickListener(e -> {
			dialogMcWindow.close();
		});

		dialogMcWindow.setResponsive(true);
		dialogMcWindow.setWidth("32%");
		dialogMcWindow.addComponentBody(formLayoutMedioCultivo);
		UI.getCurrent().addWindow(dialogMcWindow);
	}

	public void limpiarMedioCultivo() {
		codigoMedioCultivo.setValue("");
		nombreMedioCultivo.setValue("");
		entradaMedioCultivo.setValue("");
		gastoMedioCultivo.setValue("");
		saldoMedioCultivo.setValue("");
		fechaCaducidadMedioCultivo.setValue(LocalDate.now());
	}
  
	
	private void initComponents() {

		setEvents();
		fechaCaducidadRectivo.setDateFormat("dd/MM/yyyy");
		limpiarReactivo();
		
		rbEstadoEquipo.setItems("BUENO","REGULAR","MALO");
		equipoFechaAdquisicion.setDateFormat("dd/MM/yyyy");
		
		limpiarEquipo();
		limpiarComponente();
		
		fechaCaducidadMedioCultivo.setDateFormat("dd/MM/yyyy");
		limpiarMedioCultivo();		
		
	}

	private void cargarDatosPrincipales() {
		listLab = LabotatorioController.findAll();
		createMenu(); 

		listUnidad = UnidadController.findAll();
		cmbUnidad.setItems(listUnidad);
		cmbUnidad.setItemCaptionGenerator(Unidad::getNombre);
		cmbUnidad.setEmptySelectionCaption("Seleccionar");
		
		cmbUnidadMedioCultivo.setItems(listUnidad);
		cmbUnidadMedioCultivo.setItemCaptionGenerator(Unidad::getNombre);
		cmbUnidadMedioCultivo.setEmptySelectionCaption("Seleccionar");
	}

	private void cargarDatosReactivo() {
		listReactivos.clear();
		listReactivos.addAll(
				ReactivoController.searchReactiveByLaboratory(cmbLaboratorio.getValue(), filterReactivotxt.getValue()));
		gridReactivo.setItems(listReactivos);
	}
	
	private void cargarDatosEquipo() {
		listEquipos.clear();
		listEquipos.addAll(
				EquipoController.searchEquipoByLaboratory(cmbLaboratorio.getValue(), filterEquipotxt.getValue()));
		gridEquipo.setItems(listEquipos);
	}

	private void cargarDatosComponente(Equipo eq) {
		listComponente.clear();
		listComponente.addAll(ComponenteController.searchComponenteByEquipo(eq));
		gridComponente.setItems(listComponente);
	}
	
	private void cargarDatosMaterial() {
		listMateriales.clear();
		listMateriales.addAll(
				MaterialController.searchMaterialByLaboratory(cmbLaboratorio.getValue(), filterEquipotxt.getValue()));
		gridMaterial.setItems(listMateriales);
	}
	
	private void cargarDatosMediosCultivo() {
		listMediosCultivos.clear();
		listMediosCultivos.addAll(
				MedioCultivoController.searchMedioCultivoByLaboratory(cmbLaboratorio.getValue(), filterMedioCultivotxt.getValue()));
		gridMedioCultivo.setItems(listMediosCultivos);
	}
	
	private void setEvents() {
		calcularSaldo();
	}

	public void setCss() {

		// LABORATORIO

		cmbLaboratorio.addStyleName(ValoTheme.COMBOBOX_SMALL);
		//btnAddLaboratorio.addStyleName(ValoTheme.BUTTON_SMALL);
		//btnAddLaboratorio.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

		// REACTIVO
		codigoReactivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		nombreReactivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		entradaReactivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		entradaReactivo.setDecimalPrecision(2);
		entradaReactivo.setDecimalSeparator('.');
		entradaReactivo.setGroupingSeparator(',');
		entradaReactivo.setMinimumFractionDigits(2);
		entradaReactivo.setDecimalSeparatorAlwaysShown(true);
		
		gastoRectivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		gastoRectivo.setDecimalPrecision(2);
		gastoRectivo.setDecimalSeparator('.');
		gastoRectivo.setGroupingSeparator(',');  
		gastoRectivo.setMinimumFractionDigits(2);
		gastoRectivo.setDecimalSeparatorAlwaysShown(true);
		saldoRectivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		saldoRectivo.setReadOnly(true);
		saldoRectivo.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
		cmbUnidad.addStyleName(ValoTheme.COMBOBOX_TINY);
		//btnAddUnidad.addStyleName(ValoTheme.BUTTON_SMALL);
		//btnAddUnidad.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		fechaCaducidadRectivo.setStyleName(ValoTheme.DATEFIELD_TINY);
		filterReactivotxt.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		filterReactivotxt.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		filterReactivotxt.setIcon(VaadinIcons.SEARCH);
		clearReactivoFilter.addStyleName(ValoTheme.BUTTON_SMALL);
		
		// EQUIPO
		codigoEquipo.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		nombreEquipo.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		equipoMarca.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		cantidadEquipo.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		equipoObservacion.addStyleName(ValoTheme.TEXTAREA_SMALL);
		equipoObservacion.setWidth("225px");
		equipoObservacion.setHeight("55px");
		equipoCaracteristicas.addStyleName(ValoTheme.TEXTAREA_SMALL);
		equipoCaracteristicas.setWidth("225px");
		equipoCaracteristicas.setHeight("55px");
		equipoFechaAdquisicion.addStyleName(ValoTheme.DATEFIELD_SMALL);
		rbEstadoEquipo.addStyleName(ValoTheme.OPTIONGROUP_SMALL);
		rbEstadoEquipo.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		
		filterEquipotxt.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		filterEquipotxt.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		filterEquipotxt.setIcon(VaadinIcons.SEARCH);
		clearEquipoFilter.addStyleName(ValoTheme.BUTTON_SMALL);
		
		nombreComponente.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		marcaComponente.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		capacidadComponente.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		cantidadComponente.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		
		// MATERIAL
		codigoMaterial.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		nombreMaterial.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		marcaMaterial.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		capacidadMaterial.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		marcaMaterial.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		tipoMaterial.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		cantidadMaterial.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		observacionMaterial.addStyleName(ValoTheme.TEXTAREA_SMALL);
		observacionMaterial.setWidth("225px");
		observacionMaterial.setHeight("55px");
		filterMaterialtxt.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		filterMaterialtxt.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		filterMaterialtxt.setIcon(VaadinIcons.SEARCH);
		clearMaterialFilter.addStyleName(ValoTheme.BUTTON_SMALL);
		
		//MEDIOS DE CULTIVO
		codigoMedioCultivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		nombreMedioCultivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		entradaMedioCultivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		entradaMedioCultivo.setDecimalPrecision(2);
		entradaMedioCultivo.setDecimalSeparator('.');
		entradaMedioCultivo.setGroupingSeparator(',');
		entradaMedioCultivo.setMinimumFractionDigits(2);
		entradaMedioCultivo.setDecimalSeparatorAlwaysShown(true);
		
		gastoMedioCultivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		gastoMedioCultivo.setDecimalPrecision(2);
		gastoMedioCultivo.setDecimalSeparator('.');
		gastoMedioCultivo.setGroupingSeparator(',');  
		gastoMedioCultivo.setMinimumFractionDigits(2);
		gastoMedioCultivo.setDecimalSeparatorAlwaysShown(true);
		saldoMedioCultivo.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		saldoMedioCultivo.setReadOnly(true);
		saldoMedioCultivo.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
		cmbUnidadMedioCultivo.addStyleName(ValoTheme.COMBOBOX_TINY);
		//btnAddUnidadMedioCultivo.addStyleName(ValoTheme.BUTTON_SMALL);
		//btnAddUnidadMedioCultivo.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		fechaCaducidadMedioCultivo.setStyleName(ValoTheme.DATEFIELD_TINY);
		filterMedioCultivotxt.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		filterMedioCultivotxt.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		filterMedioCultivotxt.setIcon(VaadinIcons.SEARCH);
		clearMedioCultivoFilter.addStyleName(ValoTheme.BUTTON_SMALL);
		
		clave.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		cmbRol.setStyleName(ValoTheme.COMBOBOX_TINY);
		/*
		 * btnAddRol.addStyleName(ValoTheme.BUTTON_SMALL);
		 * btnAddRol.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		 */
		gridRol.addStyleName(ValoTheme.TABLE_SMALL);
		gridRol.addStyleName(ValoTheme.TABLE_COMPACT);
		// cmbLaboratorio.addStyleName(ValoTheme.COMBOBOX_);
	}

	private void createMenu() {
		/*
		 * Iterator<Laboratorio> iteratorLab = listLab.iterator();
		 * 
		 * Laboratorio lab;
		 */

		// while(iteratorLab.hasNext()) {
		cmbLaboratorio.setItems(listLab);
		cmbLaboratorio.setItemCaptionGenerator(Laboratorio::getNombre);
		if (listLab.size() > 0) {
			cmbLaboratorio.setSelectedItem(listLab.get(0));
		}
		/*
		 * lab = iteratorLab.next(); subMenu.addItem(lab.getNombre(), VaadinIcons.FLASK,
		 * null);
		 */
		// }
	}


	private void setPlaceHolder() {
		codigoReactivo.setPlaceholder("LQR001");
		nombreReactivo.setPlaceholder("CLOR-ANTIMONIO");
		entradaReactivo.setPlaceholder("0.00");
		gastoRectivo.setPlaceholder("0.00");
		saldoRectivo.setPlaceholder("0.00");
		
		codigoEquipo.setPlaceholder("LQE001");
		nombreEquipo.setPlaceholder("AGITADOR MAGNETICO");
		equipoMarca.setPlaceholder("HANNA");
		cantidadEquipo.setPlaceholder("5");
		equipoObservacion.setPlaceholder("");
		equipoCaracteristicas.setPlaceholder("MODELO 25 X1-SERIE\r\n" + 
				"0024277-24LTS DE ACERO\r\n" + 
				"INOXIDABLE-30 LBS-CODIGO\r\n" + 
				"UTM 004-01-006-00009");
		
		codigoMaterial.setPlaceholder("LQM001");
		nombreMaterial.setPlaceholder("MATRAZ AFORADO");
		marcaMaterial.setPlaceholder("MARIENFELD");
		capacidadMaterial.setPlaceholder("100 ml");
		cantidadMaterial.setPlaceholder("5");
		observacionMaterial.setPlaceholder("EJEMPLO: 4 EN ESTADO BUENO Y 1 DAÑADO");
		
		nombreComponente.setPlaceholder("BOMBAS CENTRÍFUGAS");
		marcaComponente.setPlaceholder("PAOLO");
		capacidadComponente.setPlaceholder("0.5 HP");
		cantidadComponente.setPlaceholder("1");
		
		codigoMedioCultivo.setPlaceholder("LQMC001");
		nombreMedioCultivo.setPlaceholder("AGAR MANITOL SALADO");
		entradaMedioCultivo.setPlaceholder("0.00");
		gastoMedioCultivo.setPlaceholder("0.00");
		saldoMedioCultivo.setPlaceholder("0.00");

	}

	private void setMaxLengthText() {
		codigoReactivo.setMaxLength(20);
		nombreReactivo.setMaxLength(60);
		entradaReactivo.setMaxLength(15);
		gastoRectivo.setMaxLength(15);
		saldoRectivo.setMaxLength(15);
		
		nombreComponente.setMaxLength(50);
		marcaComponente.setMaxLength(30);
		capacidadComponente.setMaxLength(50);
		cantidadComponente.setMaxLength(5);
		
		codigoMedioCultivo.setMaxLength(20);
		nombreMedioCultivo.setMaxLength(60);
		entradaMedioCultivo.setMaxLength(15);
		gastoMedioCultivo.setMaxLength(15);
		saldoMedioCultivo.setMaxLength(15);
		
	}


	Binder<Reactivo> validatorReactivo = new Binder<>();
	Binder<Equipo> validatorEquipo = new Binder<>();
	Binder<Componente> validatorComponente = new Binder<>();
	Binder<Material> validatorMaterial = new Binder<>();
	Binder<MedioCultivo> validatorMc = new Binder<>();
	
	private void addValidation() {
		/*REACTIVO*/
		validatorReactivo.forField(codigoReactivo).asRequired("Campo requerido").bind(Reactivo::getCodigo,
				Reactivo::setCodigo);

		validatorReactivo.forField(nombreReactivo).asRequired("Campo requerido").bind(Reactivo::getNombre,
				Reactivo::setNombre);

		validatorReactivo.forField(entradaReactivo).asRequired("Campo requerido").withNullRepresentation("")
				.withConverter(new StringToFloatConverter("formato decimal 0.00"))
				.bind(Reactivo::getEntrada, Reactivo::setEntrada);

		validatorReactivo.forField(gastoRectivo).asRequired("Campo requerido").withNullRepresentation("")
				.withConverter(new StringToFloatConverter("formato decimal 0.00"))
				.bind(Reactivo::getGasto, Reactivo::setGasto);
		
		validatorReactivo.forField(cmbUnidad).asRequired("Campo requerido")
		.bind(Reactivo::getUnidad, Reactivo::setUnidad);

		/*validatorReactivo.forField(fechaCaducidadRectivo).asRequired("Campo requerido")
				.bind(Reactivo::getFechaCaducidad, Reactivo::setFechaCaducidad);*/
		/*FIN REACTIVO*/
		
		/*EQUIPO*/
		validatorEquipo.forField(codigoEquipo).asRequired("Campo requerido").bind(Equipo::getCodigo,
				Equipo::setCodigo);
		validatorEquipo.forField(nombreEquipo).asRequired("Campo requerido").bind(Equipo::getNombre,
				Equipo::setNombre);
		validatorEquipo.forField(rbEstadoEquipo).asRequired("Campo requerido").bind(Equipo::getEstadoEquipo,
				Equipo::setEstadoEquipo);
		
		validatorEquipo.forField(cantidadEquipo).asRequired("Campo requerido").withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("formato numerico 1"))
		.bind(Equipo::getCantidad, Equipo::setCantidad);

		//LAB OPERACIONES UNITARES - COMPONENTES		
		validatorComponente.forField(nombreComponente).asRequired("Campo requerido").bind(Componente::getNombre,
				Componente::setNombre);
		
		validatorComponente.forField(cantidadComponente).asRequired("Campo requerido").withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("formato numerico 1"))
		.bind(Componente::getCantidad, Componente::setCantidad);
		/*FIN EQUIPO*/
		
		/*MATERIAL*/
		validatorMaterial.forField(codigoMaterial).asRequired("Campo requerido").bind(Material::getCodigo,
				Material::setCodigo);
		validatorMaterial.forField(nombreMaterial).asRequired("Campo requerido").bind(Material::getNombre,
				Material::setNombre);
		validatorMaterial.forField(cantidadMaterial).asRequired("Campo requerido").withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("formato numerico 1"))
		.bind(Material::getCantidad, Material::setCantidad);
		/*FIN MATERIAL*/
		
		/*MEDIOS DE CULTIVO*/
		validatorMc.forField(codigoMedioCultivo).asRequired("Campo requerido").bind(MedioCultivo::getCodigo,
				MedioCultivo::setCodigo);

		validatorMc.forField(nombreMedioCultivo).asRequired("Campo requerido").bind(MedioCultivo::getNombre,
				MedioCultivo::setNombre);

		validatorMc.forField(entradaMedioCultivo).asRequired("Campo requerido").withNullRepresentation("")
				.withConverter(new StringToFloatConverter("formato decimal 0.00"))
				.bind(MedioCultivo::getEntrada, MedioCultivo::setEntrada);

		validatorMc.forField(gastoMedioCultivo).asRequired("Campo requerido").withNullRepresentation("")
				.withConverter(new StringToFloatConverter("formato decimal 0.00"))
				.bind(MedioCultivo::getGasto, MedioCultivo::setGasto);
		
		validatorMc.forField(cmbUnidadMedioCultivo).asRequired("Campo requerido")
		.bind(MedioCultivo::getUnidad, MedioCultivo::setUnidad);

		validatorMc.forField(fechaCaducidadMedioCultivo).asRequired("Campo requerido")
				.bind(MedioCultivo::getFechaCaducidad, MedioCultivo::setFechaCaducidad);
		/*FIN MEDIOS DE CULTIVO*/

	}

	private void calcularSaldo() {
		entradaReactivo.addValueChangeListener(e -> {
			float entrada, gasto, saldo;
			
			DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
			separadoresPersonalizados.setDecimalSeparator('.');
			
			DecimalFormat format = new DecimalFormat("#0.00;-#",separadoresPersonalizados);
			
			if(!entradaReactivo.isEmpty()) 
				entrada = Float.valueOf(entradaReactivo.getValue());
			else
				entrada = 0;
			
			if(!gastoRectivo.isEmpty()) 
				gasto = Float.valueOf(gastoRectivo.getValue());
			else
				gasto = 0;
			
			saldo = entrada - gasto;
			format.format(saldo);
			saldoRectivo.setValue(format.format(saldo));
			
		});

		gastoRectivo.addValueChangeListener(e -> {
			float entrada, gasto, saldo;
			
			DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
			separadoresPersonalizados.setDecimalSeparator('.');
						
			DecimalFormat format = new DecimalFormat("#0.00;-#",separadoresPersonalizados);
			
			if(!entradaReactivo.isEmpty()) 
				entrada = Float.valueOf(entradaReactivo.getValue());
			else
				entrada = 0;
			
			if(!gastoRectivo.isEmpty()) 
				gasto = Float.valueOf(gastoRectivo.getValue());
			else
				gasto = 0;
			 
			saldo = entrada - gasto;
			
			saldoRectivo.setValue(format.format(saldo));
		});
		
		//MEDIOS DE CULTIVO
		entradaMedioCultivo.addValueChangeListener(e -> {
			float entrada, gasto, saldo;
			
			DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
			separadoresPersonalizados.setDecimalSeparator('.');
			
			DecimalFormat format = new DecimalFormat("#0.00;-#",separadoresPersonalizados);
			
			if(!entradaMedioCultivo.isEmpty()) 
				entrada = Float.valueOf(entradaMedioCultivo.getValue());
			else
				entrada = 0;
			
			if(!gastoMedioCultivo.isEmpty()) 
				gasto = Float.valueOf(gastoMedioCultivo.getValue());
			else
				gasto = 0;
			
			saldo = entrada - gasto;
			format.format(saldo);
			saldoMedioCultivo.setValue(format.format(saldo));
			
		});

		gastoMedioCultivo.addValueChangeListener(e -> {
			float entrada, gasto, saldo;
			
			DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
			separadoresPersonalizados.setDecimalSeparator('.');
						
			DecimalFormat format = new DecimalFormat("#0.00;-#",separadoresPersonalizados);
			
			if(!entradaMedioCultivo.isEmpty()) 
				entrada = Float.valueOf(entradaMedioCultivo.getValue());
			else
				entrada = 0;
			
			if(!gastoMedioCultivo.isEmpty()) 
				gasto = Float.valueOf(gastoMedioCultivo.getValue());
			else
				gasto = 0;
			 
			saldo = entrada - gasto;
			
			saldoMedioCultivo.setValue(format.format(saldo));
		});

	}

	private void buildUser() {
		/*
		 * cedula.addValueChangeListener(e ->{ clave.setValue(e.getValue());
		 * 
		 * if(!nombre_uno.getValue().isEmpty() && nombre_uno.getValue().trim().length()
		 * >=1) { varNombreUno = nombre_uno.getValue().substring(0,1); }else {
		 * varNombreUno = ""; }
		 * 
		 * if(!cedula.getValue().isEmpty() && cedula.getValue().length() >=4) {
		 * varCedula = cedula.getValue().substring(cedula.getValue().trim().length() - 4
		 * , cedula.getValue().trim().length());//4 ULTIMOS DÍGITOS DE CEDULA }else {
		 * varCedula = ""; }
		 * 
		 * varApellido = apellido_paterno.getValue().trim().replace(" ", "");
		 * 
		 * nombre_usuario.setValue(varNombreUno+varApellido+varCedula); });
		 * 
		 * apellido_paterno.addValueChangeListener(e->{
		 * if(!nombre_uno.getValue().isEmpty() && nombre_uno.getValue().trim().length()
		 * >=1) { varNombreUno = nombre_uno.getValue().substring(0,1); }else {
		 * varNombreUno = ""; }
		 * 
		 * if(!cedula.getValue().isEmpty() && cedula.getValue().length() >=4) {
		 * varCedula = cedula.getValue().substring(cedula.getValue().trim().length() - 4
		 * , cedula.getValue().trim().length());//4 ULTIMOS DÍGITOS DE CEDULA }else {
		 * varCedula = ""; }
		 * 
		 * varApellido = apellido_paterno.getValue().trim().replace(" ", "");;
		 * 
		 * nombre_usuario.setValue(varNombreUno+varApellido+varCedula); });
		 * 
		 * nombre_uno.addValueChangeListener(e->{ if(!nombre_uno.getValue().isEmpty() &&
		 * nombre_uno.getValue().trim().length() >=1) { varNombreUno =
		 * nombre_uno.getValue().substring(0,1); }else { varNombreUno = ""; }
		 * 
		 * if(!cedula.getValue().isEmpty() && cedula.getValue().length() >=4) {
		 * varCedula = cedula.getValue().substring(cedula.getValue().trim().length() - 4
		 * , cedula.getValue().trim().length());//4 ULTIMOS DÍGITOS DE CEDULA }else {
		 * varCedula = ""; }
		 * 
		 * varApellido = apellido_paterno.getValue().trim().replace(" ", "");;
		 * 
		 * nombre_usuario.setValue(varNombreUno+varApellido+varCedula); });
		 */
	}

}
