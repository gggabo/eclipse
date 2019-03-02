package views;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
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
	public TextField entradaReactivo = new TextField("Entrada");
	public TextField gastoRectivo = new TextField("Gasto");
	public TextField saldoRectivo = new TextField("Saldo");
	public ComboBox<Unidad> cmbUnidad = new ComboBox<>();
	public List<Unidad> listUnidad = new ArrayList<>();
	public Button btnAddUnidad = new Button(VaadinIcons.PLUS_CIRCLE);
	public DateField fechaCaducidadRectivo = new DateField("Fecha caducidad");

	public TextField cedula = new TextField("Cedula/Pasaporte");
	public TextField nombre_usuario = new TextField("Usuario");

	public Panel pnlEquipos = new Panel();
	public HorizontalLayout toolbarEquipos = new HorizontalLayout();
	public VerticalLayout laboratorioEquipoLayout = new VerticalLayout();
	public MenuBar mainMenuEquipo = new MenuBar();
	public Grid<Usuario> gridEquipo = new Grid<>(Usuario.class);

	public Panel pnlMateriales = new Panel();
	public HorizontalLayout toolbarMateriales = new HorizontalLayout();
	public VerticalLayout laboratorioMaterialesLayout = new VerticalLayout();
	public MenuBar mainMenuMateriales = new MenuBar();
	public Grid<Usuario> gridMaterial = new Grid<>(Usuario.class);

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
			if (cmbLaboratorio.getValue() == null) {
				// tabSheet.setEnabled(false);
				return;
			}

			// tabSheet.setEnabled(true);
			listReactivos.addAll(
					ReactivoController.getAllReactiveByLaboratory(cmbLaboratorio.getValue().getIdLaboratorio()));
			gridReactivo.setItems(listReactivos);
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

		// gridReactivo.addColumn(Reactivo::getNombre).setCaption("NOMBRE").setExpandRatio(0);
		gridReactivo.addComponentColumn(Reactivo -> {
			Label lb = new Label();
			lb.setValue(Reactivo.getNombre());
			lb.setSizeFull();
			// lb.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
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

		/*
		 * gridReactivo.getColumn("CODIGO").setMaximumWidth(90);
		 * gridReactivo.getColumn("CODIGO").setResizable(false);
		 * 
		 * gridReactivo.getColumn("ENTRADA").setMaximumWidth(100);
		 * gridReactivo.getColumn("ENTRADA").setResizable(false);
		 * 
		 * gridReactivo.getColumn("GASTO").setMaximumWidth(100);
		 * gridReactivo.getColumn("GASTO").setResizable(false);
		 * 
		 * gridReactivo.getColumn("SALDO").setMaximumWidth(100);
		 * gridReactivo.getColumn("SALDO").setResizable(false);
		 * 
		 * gridReactivo.getColumn("FCADUCIDAD").setMaximumWidth(130);
		 * gridReactivo.getColumn("FCADUCIDAD").setResizable(false);
		 */

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
			// b.addStyleName(ValoTheme.BUTTON_TINY);
			b.setIcon(VaadinIcons.EDIT);

			Button b2 = new Button("Eliminar");
			b2.addClickListener(clickb2 -> {
				 listReactivos.remove(Reactivo); 
				 gridReactivo.setItems(listReactivos);
				 Reactivo.setEstado(0); 
				 ReactivoController.update(Reactivo);
				 message.warringMessage("Reactivo eliminado");
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			// b2.addStyleName(ValoTheme.BUTTON_TINY);
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

		mainMenuEquipo.addItem("Nuevo equipo", VaadinIcons.PLUS_CIRCLE, null);

		gridEquipo.setColumns("apellido_paterno");

		gridEquipo.setWidth("100%");
		gridEquipo.setSelectionMode(SelectionMode.NONE);

		pnlEquipos.setCaption("Gestión de equipos");
		pnlEquipos.setIcon(VaadinIcons.FLASK);
		pnlEquipos.setContent(laboratorioEquipoLayout);

		laboratorioEquipoLayout.addComponents(toolbarEquipos, gridEquipo);
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

				
				/*if (!entradaReactivo.isEmpty() && !gastoRectivo.isEmpty()) {
					saldoRectivo.setValue(String.valueOf(
							Float.parseFloat(entradaReactivo.getValue()) - Float.parseFloat(gastoRectivo.getValue())));
				} else {
					saldoRectivo.setValue("0.00");
				}*/
				
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

	public void userNewEdit(Usuario user) {

	}

	private void initComponents() {
		// tabSheet.setEnabled(false);

		// REACTIVO
		setEvents();
		fechaCaducidadRectivo.setDateFormat("dd/MM/yyyy");
		// saldoRectivo.setReadOnly(true);
		limpiarReactivo();
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

		// EQUIPO

		// MATERIAL

		cedula.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		nombre_usuario.setStyleName(ValoTheme.TEXTFIELD_SMALL);
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

		cedula.setMaxLength(20);
		nombre_usuario.setMaxLength(20);
		clave.setMaxLength(20);
	}


	Binder<Reactivo> validatorReactivo = new Binder<>();

	private void addValidation() {

		validatorReactivo.forField(codigoReactivo).asRequired("Campo requerido").bind(Reactivo::getCodigo,
				Reactivo::setCodigo);

		validatorReactivo.forField(nombreReactivo).asRequired("Campo requerido").bind(Reactivo::getNombre,
				Reactivo::setNombre);

		validatorReactivo.forField(entradaReactivo).asRequired("Campo requerido").withNullRepresentation("")
				.withConverter(new StringToFloatConverter("formato decimal 00.00"))
				.bind(Reactivo::getEntrada, Reactivo::setEntrada);

		validatorReactivo.forField(gastoRectivo).asRequired("Campo requerido").withNullRepresentation("")
				.withConverter(new StringToFloatConverter("formato decimal 00.00"))
				.bind(Reactivo::getGasto, Reactivo::setGasto);
		
		validatorReactivo.forField(cmbUnidad).asRequired("Campo requerido")
		.bind(Reactivo::getUnidad, Reactivo::setUnidad);

		validatorReactivo.forField(fechaCaducidadRectivo).asRequired("Campo requerido")
				.bind(Reactivo::getFechaCaducidad, Reactivo::setFechaCaducidad);

	}

	private void calcularSaldo() {
		
		entradaReactivo.addValueChangeListener(e -> {
			if (!entradaReactivo.isEmpty() && !gastoRectivo.isEmpty()) {
				saldoRectivo.setValue(String.valueOf(
						Float.parseFloat(entradaReactivo.getValue()) - Float.parseFloat(gastoRectivo.getValue())));
			} else {
				saldoRectivo.setValue("0.00");
			}
		});

		gastoRectivo.addValueChangeListener(e -> {
			if (!entradaReactivo.isEmpty() && !gastoRectivo.isEmpty()) {
				saldoRectivo.setValue(String.valueOf(
						Float.parseFloat(entradaReactivo.getValue()) - Float.parseFloat(gastoRectivo.getValue())));
			} else {
				saldoRectivo.setValue("0.00");
			}
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
