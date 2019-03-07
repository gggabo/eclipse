package views;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.vaadin.ui.NumberField;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToFloatConverter;
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
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.LocalDateRenderer;
import com.vaadin.ui.themes.ValoTheme;

import controllers.EquipoController;
import controllers.LabotatorioController;
import controllers.ReactivoController;
import controllers.UnidadController;
import models.Equipo;
import models.Laboratorio;
import models.Reactivo;
import models.Rol;
import models.Unidad;
import models.Usuario;
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

	public VerticalLayout mainLayout = new VerticalLayout();
	public Panel pnlPrincipal = new Panel();
	public FormLayout mainFrm;
	public HorizontalLayout toolbar = new HorizontalLayout();
	public ComboBox<Laboratorio> cmbLaboratorio = new ComboBox<>();
	public List<Laboratorio> listLab = new ArrayList<>();
	public Button btnAddLaboratorio = new Button(VaadinIcons.PLUS_CIRCLE);

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
	public Button btnAddUnidad = new Button(VaadinIcons.PLUS_CIRCLE);
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
	public RadioButtonGroup<String> rbEstadoEquipo = new RadioButtonGroup<>("Estado");
	/*FIN EQUIPO*/

	public Panel pnlMateriales = new Panel();
	public HorizontalLayout toolbarMateriales = new HorizontalLayout();
	public VerticalLayout laboratorioMaterialesLayout = new VerticalLayout();
	public MenuBar mainMenuMateriales = new MenuBar();
	public Grid<Usuario> gridMaterial = new Grid<>(Usuario.class);
	
	public Panel pnlCultivo = new Panel();
	public HorizontalLayout toolbarCultivo = new HorizontalLayout();
	public VerticalLayout laboratorioCultivoLayout = new VerticalLayout();
	public MenuBar mainMenuCultivo = new MenuBar();
	public Grid<Usuario> gridCultivo = new Grid<>(Usuario.class);

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

		cmbLaboratorio.setWidth("100%");
		cmbLaboratorio.setTextInputAllowed(false);
		cmbLaboratorio.setEmptySelectionAllowed(false);

		// cmbLaboratorio.setEmptySelectionCaption("Seleccionar laboratorio");

		cmbLaboratorio.addValueChangeListener(e -> {
			listReactivos.clear();
			gridReactivo.setItems(listReactivos);
			
			listEquipos.clear();
			gridEquipo.setItems(listEquipos);
			
			if (cmbLaboratorio.getValue() == null) {
				// tabSheet.setEnabled(false);
				return;
			}

			// tabSheet.setEnabled(true);
			listReactivos.addAll(
					ReactivoController.getAllReactiveByLaboratory(cmbLaboratorio.getValue().getIdLaboratorio()));
			gridReactivo.setItems(listReactivos);
			
			listEquipos.addAll(
					EquipoController.searchEquipoByLaboratory(cmbLaboratorio.getValue(), filterEquipotxt.getValue()));
			gridEquipo.setItems(listEquipos);
			
		});

		toolbar.setWidth("100%");
		toolbar.setSpacing(true);
		toolbar.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbar.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbar.setResponsive(true);
		toolbar.addComponents(cmbLaboratorio, btnAddLaboratorio);

		tabSheet.addTab(pnlReactivos, "Reactivos", new ThemeResource("images/quimica.png"));
		tabSheet.addTab(pnlEquipos, "Equipos", new ThemeResource("images/microscopio.png"));
		tabSheet.addTab(pnlMateriales, "Materiales", new ThemeResource("images/mortero.png"));
		tabSheet.addTab(pnlCultivo, "M. Cultivo", new ThemeResource("images/molecule.png"));

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
				newEditReactivo(null);
				reactivoAction = "guardar";
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
				newEditEquipo(null);
				equipoAction = "guardar";
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
			Label lb = new Label();
			lb.setValue(Equipo.getNombre());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOMBRE").setExpandRatio(0);

		gridEquipo.addColumn(Equipo -> Equipo.getMarca().toLowerCase())
				.setCaption("MARCA").setId("EQUIPOMARCA").setExpandRatio(0);
		gridEquipo.addColumn(Equipo -> Equipo.getCantidad()).setCaption("CANTIDAD").setId("CANTIDADEQUIPO").setExpandRatio(0);
		gridEquipo.addColumn(Equipo -> Equipo.getEstadoEquipo()).setCaption("ESTADO").setId("ESTADOEQUIPO").setExpandRatio(0);

		gridEquipo.addComponentColumn(Equipo -> {

			Button b = new Button("Editar");
			b.addClickListener(clickb -> {
				newEditEquipo(Equipo);

				codigoEquipo.setValue(Equipo.getCodigo());
				codigoEquipo.setReadOnly(true);
				nombreEquipo.setValue(Equipo.getNombre());
				equipoMarca.setValue(Equipo.getMarca());
				cantidadEquipo.setValue(String.valueOf(Equipo.getCantidad()));
				rbEstadoEquipo.setValue(Equipo.getEstadoEquipo());

				equipoAction = "modificar";

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
		// **FIN EQUIPO**//

		// **MATERIAL**//
		toolbarMateriales.setWidth("100%");
		toolbarMateriales.setSpacing(true);
		toolbarMateriales.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarMateriales.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarMateriales.setResponsive(true);
		toolbarMateriales.addComponents(mainMenuMateriales);

		mainMenuMateriales.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuMateriales.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuMateriales.setResponsive(true);

		mainMenuMateriales.addItem("Nuevo material", VaadinIcons.PLUS_CIRCLE, null);

		gridMaterial.setColumns("apellido_paterno");

		gridMaterial.setWidth("100%");
		gridMaterial.setSelectionMode(SelectionMode.NONE);

		pnlMateriales.setCaption("Gestión de materiales");
		pnlMateriales.setIcon(VaadinIcons.FLASK);
		pnlMateriales.setContent(laboratorioMaterialesLayout);

		laboratorioMaterialesLayout.addComponents(toolbarMateriales, gridMaterial);
		laboratorioMaterialesLayout.setMargin(false);
		// **FIN MATERIAL**//

		// **MEDIOS DE CULTIVO**//
		toolbarCultivo.setWidth("100%");
		toolbarCultivo.setSpacing(true);
		toolbarCultivo.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarCultivo.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarCultivo.setResponsive(true);
		toolbarCultivo.addComponents(mainMenuCultivo);

		mainMenuCultivo.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuCultivo.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuCultivo.setResponsive(true);

		mainMenuCultivo.addItem("Nuevo medio cultivo", VaadinIcons.PLUS_CIRCLE, null);

		gridCultivo.setColumns("apellido_paterno");

		gridCultivo.setWidth("100%");
		gridCultivo.setSelectionMode(SelectionMode.NONE);

		pnlCultivo.setCaption("Gestión de medios de cuultivo");
		pnlCultivo.setIcon(VaadinIcons.FLASK);
		pnlCultivo.setContent(laboratorioCultivoLayout);

		laboratorioCultivoLayout.addComponents(toolbarCultivo, gridCultivo);
		laboratorioCultivoLayout.setMargin(false);
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
		limpiarReactivo();

		dialogWindow dialogReactivoWindow = new dialogWindow("Gestión de equipos", VaadinIcons.FLASK);
		 
		formLayoutEquipo.setSpacing(false);
		formLayoutEquipo.setMargin(false);
		formLayoutEquipo.addComponents(codigoEquipo, nombreEquipo, equipoMarca, cantidadEquipo, rbEstadoEquipo);

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
							equipoMarca.getValue().toUpperCase().trim(),Integer.parseInt(cantidadEquipo.getValue()),rbEstadoEquipo.getValue(),
							cmbLaboratorio.getValue(),1);

					EquipoController.save(eq);
				} else {

					equipo.setCodigo(codigoEquipo.getValue().toUpperCase().trim());
					equipo.setNombre(nombreEquipo.getValue().toUpperCase().trim());
					equipo.setMarca(equipoMarca.getValue().toUpperCase().trim());
					equipo.setCantidad(Integer.parseInt(cantidadEquipo.getValue()));
					equipo.setEstadoEquipo(rbEstadoEquipo.getValue());					
					equipo.setLaboratorio(cmbLaboratorio.getValue());

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

		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("32%");
		dialogReactivoWindow.addComponentBody(formLayoutEquipo);
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}

	public void limpiarEquipo() {
		codigoEquipo.setValue("");
		nombreEquipo.setValue("");
		equipoMarca.setValue("");
		cantidadEquipo.setValue("");
		rbEstadoEquipo.setSelectedItem("BUENO");
	}
	
	private void initComponents() {

		setEvents();
		fechaCaducidadRectivo.setDateFormat("dd/MM/yyyy");

		limpiarReactivo();
		
		rbEstadoEquipo.setItems("BUENO","REGULAR","MALO");
		limpiarEquipo();
	}

	private void cargarDatosPrincipales() {
		listLab = LabotatorioController.findAll();
		createMenu(); 

		listUnidad = UnidadController.findAll();
		cmbUnidad.setItems(listUnidad);
		cmbUnidad.setItemCaptionGenerator(Unidad::getNombre);
		cmbUnidad.setEmptySelectionCaption("Seleccionar");
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

	private void setEvents() {
		calcularSaldo();
	}

	public void setCss() {

		// LABORATORIO

		cmbLaboratorio.addStyleName(ValoTheme.COMBOBOX_SMALL);
		btnAddLaboratorio.addStyleName(ValoTheme.BUTTON_SMALL);
		btnAddLaboratorio.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

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
		btnAddUnidad.addStyleName(ValoTheme.BUTTON_SMALL);
		btnAddUnidad.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
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
		rbEstadoEquipo.addStyleName(ValoTheme.OPTIONGROUP_SMALL);
		rbEstadoEquipo.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		
		filterEquipotxt.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		filterEquipotxt.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		filterEquipotxt.setIcon(VaadinIcons.SEARCH);
		clearEquipoFilter.addStyleName(ValoTheme.BUTTON_SMALL);
		
		// MATERIAL

		
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
		codigoReactivo.setPlaceholder("LQ001");
		nombreReactivo.setPlaceholder("CLOR-ANTIMONIO");
		entradaReactivo.setPlaceholder("0.00");
		gastoRectivo.setPlaceholder("0.00");
		saldoRectivo.setPlaceholder("0.00");

	}

	private void setMaxLengthText() {
		codigoReactivo.setMaxLength(20);
		nombreReactivo.setMaxLength(60);
		entradaReactivo.setMaxLength(15);
		gastoRectivo.setMaxLength(15);
		saldoRectivo.setMaxLength(15);
		
	}


	Binder<Reactivo> validatorReactivo = new Binder<>();
	Binder<Equipo> validatorEquipo = new Binder<>();

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

		validatorReactivo.forField(fechaCaducidadRectivo).asRequired("Campo requerido")
				.bind(Reactivo::getFechaCaducidad, Reactivo::setFechaCaducidad);
		/*FIN REACTIVO*/
		
		/*EQUIPO*/
		validatorEquipo.forField(codigoEquipo).asRequired("Campo requerido").bind(Equipo::getCodigo,
				Equipo::setCodigo);
		validatorEquipo.forField(nombreEquipo).asRequired("Campo requerido").bind(Equipo::getNombre,
				Equipo::setNombre);
		validatorEquipo.forField(rbEstadoEquipo).asRequired("Campo requerido").bind(Equipo::getEstadoEquipo,
				Equipo::setEstadoEquipo);
		/*FIN EQUIPO*/
		

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
