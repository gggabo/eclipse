package views;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.vaadin.ui.NumberField;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
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
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.LocalDateRenderer;
import com.vaadin.ui.themes.ValoTheme;

import controllers.EquipoController;
import controllers.LabotatorioController;
import controllers.MaterialController;
import controllers.MedioCultivoController;
import controllers.ReactivoController;
import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.MessageBox;
import models.Componente;
import models.Equipo;
import models.Laboratorio;
import models.Material;
import models.MedioCultivo;
import models.Reactivo;
import models.Rol;
import models.TrazabilidadEquipo;
import models.TrazabilidadMedioCultivo;
import models.TrazabilidadReactivo;
import models.Unidad;
import models.Usuario;
import utils.UploadImage;
import utils.message;
import utils.uploadXls;

public class VwLaboratoriosBuscarAll extends VerticalLayout implements Serializable {
	private static final long serialVersionUID = 1L;
	private VwTrazabilidadAll vwtrazabilidad;
	int laboratorio;

	public VwLaboratoriosBuscarAll(VwTrazabilidadAll vwtrazabilidad, int lab) {
		this.vwtrazabilidad = vwtrazabilidad;
		laboratorio = lab;
		addComponent(buildUI());
		setCss();
		setPlaceHolder();
		setMaxLengthText();
		cargarDatosPrincipales();
		initComponents();
		setMargin(false);

		if(laboratorio == 1) { //PROCESOS QUIMICOS
			cmbLaboratorio.setSelectedItem(listLab.get(0));
		}else if(laboratorio == 2) { // MICROBIOLOGIA
			cmbLaboratorio.setSelectedItem(listLab.get(1));
		}else if(laboratorio == 3) { // OPERACIONES UNITARIAS
			cmbLaboratorio.setSelectedItem(listLab.get(2));
		}else if(laboratorio == 4) { // ANALISIS DE AGUAS
			cmbLaboratorio.setSelectedItem(listLab.get(3));
		}else if(laboratorio == 5) { // ECOTOXICOLOGIA
			cmbLaboratorio.setSelectedItem(listLab.get(4));
		}
		
	}

	public VerticalLayout mainLayou = new VerticalLayout();
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
		toolbar.addComponents(cmbLaboratorio, btnAddLaboratorio);
		toolbar.setVisible(false);

		if(laboratorio == 1) { //PROCESOS QUIMICOS
			tabSheet.addTab(laboratorioReactivoLayout, "Reactivos", new ThemeResource("images/quimica.png"));
			tabSheet.addTab(laboratorioEquipoLayout, "Equipos", new ThemeResource("images/microscopio.png"));
			tabSheet.addTab(laboratorioMaterialesLayout, "Materiales", new ThemeResource("images/mortero.png"));
			//tabSheet.addTab(laboratorioMedioCultivoLayout, "M. Cultivo", new ThemeResource("images/molecule.png"));
		}else if(laboratorio == 2) { // MICROBIOLOGIA
			tabSheet.addTab(laboratorioReactivoLayout, "Reactivos", new ThemeResource("images/quimica.png"));
			tabSheet.addTab(laboratorioEquipoLayout, "Equipos", new ThemeResource("images/microscopio.png"));
			tabSheet.addTab(laboratorioMaterialesLayout, "Materiales", new ThemeResource("images/mortero.png"));
			tabSheet.addTab(laboratorioMedioCultivoLayout, "M. Cultivo", new ThemeResource("images/molecule.png"));
		}else if(laboratorio == 3) { // OPERACIONES UNITARIAS
			//tabSheet.addTab(laboratorioReactivoLayout, "Reactivos", new ThemeResource("images/quimica.png"));
			tabSheet.addTab(laboratorioEquipoLayout, "Equipos", new ThemeResource("images/microscopio.png"));
			tabSheet.addTab(laboratorioMaterialesLayout, "Materiales", new ThemeResource("images/mortero.png"));
			//tabSheet.addTab(laboratorioMedioCultivoLayout, "M. Cultivo", new ThemeResource("images/molecule.png"));
		}else if(laboratorio == 4) { // ANALISIS DE AGUAS
			tabSheet.addTab(laboratorioReactivoLayout, "Reactivos", new ThemeResource("images/quimica.png"));
			tabSheet.addTab(laboratorioEquipoLayout, "Equipos", new ThemeResource("images/microscopio.png"));
			tabSheet.addTab(laboratorioMaterialesLayout, "Materiales", new ThemeResource("images/mortero.png"));
			//tabSheet.addTab(laboratorioMedioCultivoLayout, "M. Cultivo", new ThemeResource("images/molecule.png"));
		}else if(laboratorio == 5) { // ECOTOXICOLOGIA
			tabSheet.addTab(laboratorioReactivoLayout, "Reactivos", new ThemeResource("images/quimica.png"));
			tabSheet.addTab(laboratorioEquipoLayout, "Equipos", new ThemeResource("images/microscopio.png"));
			tabSheet.addTab(laboratorioMaterialesLayout, "Materiales", new ThemeResource("images/mortero.png"));
			//tabSheet.addTab(laboratorioMedioCultivoLayout, "M. Cultivo", new ThemeResource("images/molecule.png"));
		}
		
		
		
		// **REACTIVO**// 
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

		gridReactivo.addColumn(Reactivo -> Reactivo.getSaldo() + " " + Reactivo.getUnidad().getNombre().toLowerCase())
				.setCaption("SALDO").setId("SALDO").setExpandRatio(0);
		gridReactivo.addColumn(Reactivo -> Reactivo.getFechaCaducidad(), new LocalDateRenderer("dd/MM/yyyy"))
				.setCaption("F. CADUCIDAD").setId("FCADUCIDAD").setExpandRatio(0);

		gridReactivo.addComponentColumn(Reactivo -> {

			Button b = new Button("Agregar");
			b.addClickListener(clickb -> {
				
				TrazabilidadReactivo tr = new TrazabilidadReactivo();
				tr.setReactivo(Reactivo);
				
				if(laboratorio == 1) {// PROCESOS QUIMICOS
					if(!vwtrazabilidad.listReactivosPQ.contains(tr)) {
						NumberField  txt = new NumberField("Cantidad de reactivo a usar");
						txt.setLocale(Locale.FRANCE);
						txt.setDecimalPrecision(2);
						txt.setDecimalSeparator('.');
						txt.setMinimumFractionDigits(2);
						
						MessageBox.createQuestion()
						.withCaption("Información")
						.withCancelButton(ButtonOption.caption("Cancelar"))
			    		.withMessage(txt)
			    		.withOkButton(() -> {
			    			float gasto, saldo;
			    			if(txt.isEmpty()) txt.setValue("0");
			    			gasto = Float.parseFloat(txt.getValue());
			    			saldo = Reactivo.getSaldo();
			    			if(gasto > saldo) {
			    				message.warringMessage("Gasto excede el saldo del reactivo");
			    			}else {
			    				tr.setGasto(Float.parseFloat(txt.getValue()));
				    			vwtrazabilidad.listReactivosPQ.add(tr);
								vwtrazabilidad.gridReactivoPQ.setItems(vwtrazabilidad.listReactivosPQ);	
			    			}
			    			
			    		},ButtonOption.caption("Aceptar"))
			    		.open();
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}else if(laboratorio == 2) {//MICROBIOLOGIA
					if(!vwtrazabilidad.listReactivosMi.contains(tr)) {
						NumberField  txt = new NumberField("Cantidad de reactivo a usar");
						txt.setLocale(Locale.FRANCE);
						txt.setDecimalPrecision(2);
						txt.setDecimalSeparator('.');
						txt.setMinimumFractionDigits(2);
						
						MessageBox.createQuestion()
						.withCaption("Información")
						.withCancelButton(ButtonOption.caption("Cancelar"))
			    		.withMessage(txt)
			    		.withOkButton(() -> {
			    			float gasto, saldo;
			    			if(txt.isEmpty()) txt.setValue("0");
			    			gasto = Float.parseFloat(txt.getValue());
			    			saldo = Reactivo.getSaldo();
			    			if(gasto > saldo) {
			    				message.warringMessage("Gasto excede el saldo del reactivo");
			    			}else {
			    				tr.setGasto(Float.parseFloat(txt.getValue()));
				    			vwtrazabilidad.listReactivosMi.add(tr);
								vwtrazabilidad.gridReactivoMi.setItems(vwtrazabilidad.listReactivosMi);	
			    			}
			    			
			    		},ButtonOption.caption("Aceptar"))
			    		.open();
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}else if(laboratorio == 4) {//ANALISIS DE AGUAS
					if(!vwtrazabilidad.listReactivosAG.contains(tr)) {
						NumberField  txt = new NumberField("Cantidad de reactivo a usar");
						txt.setLocale(Locale.FRANCE);
						txt.setDecimalPrecision(2);
						txt.setDecimalSeparator('.');
						txt.setMinimumFractionDigits(2);
						
						MessageBox.createQuestion()
						.withCaption("Información")
						.withCancelButton(ButtonOption.caption("Cancelar"))
			    		.withMessage(txt)
			    		.withOkButton(() -> {
			    			float gasto, saldo;
			    			if(txt.isEmpty()) txt.setValue("0");
			    			gasto = Float.parseFloat(txt.getValue());
			    			saldo = Reactivo.getSaldo();
			    			if(gasto > saldo) {
			    				message.warringMessage("Gasto excede el saldo del reactivo");
			    			}else {
			    				tr.setGasto(Float.parseFloat(txt.getValue()));
				    			vwtrazabilidad.listReactivosAG.add(tr);
								vwtrazabilidad.gridReactivoAG.setItems(vwtrazabilidad.listReactivosAG);	
			    			}
			    			
			    		},ButtonOption.caption("Aceptar"))
			    		.open();
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}else if(laboratorio == 5) {//ECOTOXICOLOGIA
					if(!vwtrazabilidad.listReactivosEC.contains(tr)) {
						NumberField  txt = new NumberField("Cantidad de reactivo a usar");
						txt.setLocale(Locale.FRANCE);
						txt.setDecimalPrecision(2);
						txt.setDecimalSeparator('.');
						txt.setMinimumFractionDigits(2);
						
						MessageBox.createQuestion()
						.withCaption("Información")
						.withCancelButton(ButtonOption.caption("Cancelar"))
			    		.withMessage(txt)
			    		.withOkButton(() -> {
			    			float gasto, saldo;
			    			if(txt.isEmpty()) txt.setValue("0");
			    			gasto = Float.parseFloat(txt.getValue());
			    			saldo = Reactivo.getSaldo();
			    			if(gasto > saldo) {
			    				message.warringMessage("Gasto excede el saldo del reactivo");
			    			}else {
			    				tr.setGasto(Float.parseFloat(txt.getValue()));
				    			vwtrazabilidad.listReactivosEC.add(tr);
								vwtrazabilidad.gridReactivoEC.setItems(vwtrazabilidad.listReactivosEC);	
			    			}
			    			
			    		},ButtonOption.caption("Aceptar"))
			    		.open();
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}
			

			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.PLUS);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b);
			return hl;
		}).setCaption("Opciones");

		gridReactivo.setWidth("100%");
		gridReactivo.setHeight("310px");
		gridReactivo.setSelectionMode(SelectionMode.NONE);

		laboratorioReactivoLayout.addComponents(filteringReactivo, gridReactivo);
		laboratorioReactivoLayout.setSpacing(true);
		laboratorioReactivoLayout.setMargin(true);
		// **FIN REACTIVO**//

		// **EQUIPO**//
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
	
		gridEquipo.addColumn(Equipo -> Equipo.getEstadoEquipo()).setCaption("ESTADO").setId("ESTADOEQUIPO").setExpandRatio(0);

		gridEquipo.addComponentColumn(Equipo -> {

			Button b = new Button("Agregar");
			b.addClickListener(clickb -> {

				TrazabilidadEquipo te = new TrazabilidadEquipo();
				te.setEquipo(Equipo);
				if(laboratorio == 1) { //PROCESOS QUIMICOS
					if(!vwtrazabilidad.listEquiposPQ.contains(te)) {
						vwtrazabilidad.listEquiposPQ.add(te);
						vwtrazabilidad.gridEquipoPQ.setItems(vwtrazabilidad.listEquiposPQ);	
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}else if(laboratorio == 2) { //MICROBIOLOGIA
					if(!vwtrazabilidad.listEquiposMi.contains(te)) {
						vwtrazabilidad.listEquiposMi.add(te);
						vwtrazabilidad.gridEquipoMi.setItems(vwtrazabilidad.listEquiposMi);	
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}else if(laboratorio == 3) {//OPERACIONES UNITARIAS
					//HorizontalLayout hl = new HorizontalLayout();
					if(!vwtrazabilidad.listEquiposOU.contains(te)) {
						/*hl.setCaption("Componentes del equipo a utilizar");
						Iterator<Componente> iterator = ComponenteController.getAllComponentByEquipo(Equipo.getIdEquipo()).iterator();
						List<Componente> listComp = new ArrayList<>();
						TwinColSelect<Componente> twComp = new TwinColSelect<>();
						while(iterator.hasNext()) {
							Componente c = iterator.next();
							listComp.add(c);
						}	
						twComp.setItems(listComp);
						twComp.setItemCaptionGenerator(Componente::getNombre);
						twComp.setRows(6);
						twComp.setWidth("530px");

						hl.addComponents(twComp);

						MessageBox.createQuestion()
						.withCaption("Información")
						.withCancelButton(ButtonOption.caption("Cancelar"))
						.withMessage(hl)
						.withOkButton(() -> {

							List<Componente> complist = new ArrayList<>(twComp.getSelectedItems());
							te.setComponentes(complist);*/
							vwtrazabilidad.listEquiposOU.add(te);
							vwtrazabilidad.gridEquipoOU.setItems(vwtrazabilidad.listEquiposOU);	

						/*},ButtonOption.caption("Aceptar"))
						.open();*/
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}else if(laboratorio == 4) { //ANALISIS DE AGUAS
					if(!vwtrazabilidad.listEquiposAG.contains(te)) {
						vwtrazabilidad.listEquiposAG.add(te);
						vwtrazabilidad.gridEquipoAG.setItems(vwtrazabilidad.listEquiposAG);	
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}else if(laboratorio == 5) { //ECOTOXICOLOGIA
					if(!vwtrazabilidad.listEquiposEC.contains(te)) {
						vwtrazabilidad.listEquiposEC.add(te);
						vwtrazabilidad.gridEquipoEC.setItems(vwtrazabilidad.listEquiposEC);	
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}


			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.PLUS);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b);
			return hl;
		}).setCaption("Opciones");

		gridEquipo.setWidth("100%");
		gridEquipo.setHeight("310px");
		gridEquipo.setSelectionMode(SelectionMode.NONE);

		laboratorioEquipoLayout.addComponents(filteringEquipo, gridEquipo);
		laboratorioEquipoLayout.setSpacing(true);
		laboratorioEquipoLayout.setMargin(true);
		// **FIN EQUIPO**//

		// **MATERIAL**//
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

		gridMaterial.addColumn(Material -> Material.getTipoMaterial()).setCaption("TIPO").setExpandRatio(0);
		gridMaterial.addColumn(Material -> Material.getCapacidad()).setCaption("CAPACIDAD").setExpandRatio(0);

		gridMaterial.addComponentColumn(Material -> {

			Button b = new Button("Agregar");
			b.addClickListener(clickb -> {
				if(laboratorio == 1) { //PROCESOS QUIMICOS
					if(!vwtrazabilidad.listMaterialesPQ.contains(Material)) {
						vwtrazabilidad.listMaterialesPQ.add(Material);
						vwtrazabilidad.gridMaterialPQ.setItems(vwtrazabilidad.listMaterialesPQ);
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}else if(laboratorio == 2) { //MICROBIOLOGIA
					if(!vwtrazabilidad.listMaterialesMi.contains(Material)) {
						vwtrazabilidad.listMaterialesMi.add(Material);
						vwtrazabilidad.gridMaterialMi.setItems(vwtrazabilidad.listMaterialesMi);
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}else if(laboratorio == 3) {//OPERACIONES UNITARIAS
					if(!vwtrazabilidad.listMaterialesOU.contains(Material)) {
						vwtrazabilidad.listMaterialesOU.add(Material);
						vwtrazabilidad.gridMaterialOU.setItems(vwtrazabilidad.listMaterialesOU);
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}else if(laboratorio == 4) { //ANALISIS DE AGUAS
					if(!vwtrazabilidad.listMaterialesAG.contains(Material)) {
						vwtrazabilidad.listMaterialesAG.add(Material);
						vwtrazabilidad.gridMaterialAG.setItems(vwtrazabilidad.listMaterialesAG);
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}else if(laboratorio == 5) { //ECOTOXICOLOGIA
					if(!vwtrazabilidad.listMaterialesEC.contains(Material)) {
						vwtrazabilidad.listMaterialesEC.add(Material);
						vwtrazabilidad.gridMaterialEC.setItems(vwtrazabilidad.listMaterialesEC);
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}
			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.PLUS);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b);
			return hl;
		}).setCaption("Opciones");

		gridMaterial.setWidth("100%");
		gridMaterial.setHeight("310px");
		gridMaterial.setSelectionMode(SelectionMode.NONE);

		laboratorioMaterialesLayout.addComponents(filteringMaterial, gridMaterial);
		laboratorioMaterialesLayout.setSpacing(true);
		laboratorioMaterialesLayout.setMargin(true);
		// **FIN MATERIAL**//

		// **MEDIOS DE CULTIVO**//
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

		gridMedioCultivo.addColumn(MedioCultivo -> MedioCultivo.getSaldo() + " " + MedioCultivo.getUnidad().getNombre().toLowerCase())
				.setCaption("SALDO").setId("SALDO").setExpandRatio(0);
		gridMedioCultivo.addColumn(MedioCultivo -> MedioCultivo.getFechaCaducidad(), new LocalDateRenderer("dd/MM/yyyy"))
				.setCaption("F. CADUCIDAD").setId("FCADUCIDAD").setExpandRatio(0);

		gridMedioCultivo.addComponentColumn(MedioCultivo -> {

			Button b = new Button("Agregar");
			b.addClickListener(clickb -> {
				
				TrazabilidadMedioCultivo mc = new TrazabilidadMedioCultivo();
				mc.setMedioCultivo(MedioCultivo);
				
				if(laboratorio == 2) {//MICROBIOLOGIA
					if(!vwtrazabilidad.listMediosCultivosMi.contains(mc)) {
						NumberField  txt = new NumberField("Cantidad de M. de cultivo a usar");
						txt.setLocale(Locale.FRANCE);
						txt.setDecimalPrecision(2);
						txt.setDecimalSeparator('.');
						txt.setMinimumFractionDigits(2);
						
						MessageBox.createQuestion()
						.withCaption("Información")
						.withCancelButton(ButtonOption.caption("Cancelar"))
			    		.withMessage(txt)
			    		.withOkButton(() -> {
			    			float gasto, saldo;
			    			if(txt.isEmpty()) txt.setValue("0");
			    			gasto = Float.parseFloat(txt.getValue());
			    			saldo = MedioCultivo.getSaldo();
			    			if(gasto > saldo) {
			    				message.warringMessage("Gasto excede el saldo del M. de cultivo");
			    			}else {
			    				mc.setGasto(Float.parseFloat(txt.getValue()));
				    			vwtrazabilidad.listMediosCultivosMi.add(mc);
								vwtrazabilidad.gridMedioCultivoMi.setItems(vwtrazabilidad.listMediosCultivosMi);	
			    			}
			    			
			    		},ButtonOption.caption("Aceptar"))
			    		.open();
					}else {
						message.warringMessage("El registro ya se encuentra agregado");
					}
				}
			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.PLUS);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b);
			return hl;
		}).setCaption("Opciones");
 
		gridMedioCultivo.setWidth("100%");
		gridMedioCultivo.setHeight("310px");
		gridMedioCultivo.setSelectionMode(SelectionMode.NONE);

		laboratorioMedioCultivoLayout.addComponents(filteringMedioCultivo, gridMedioCultivo);
		laboratorioMedioCultivoLayout.setSpacing(true);
		laboratorioMedioCultivoLayout.setMargin(true);
		// **FIN MEDIOS CULTIVO**//
		
		laboratorioLayout.addComponents(toolbar, tabSheet);
		laboratorioLayout.setMargin(false);
		
		return laboratorioLayout;
	}

	public void limpiarMainComponents() {

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
	

	public void limpiarComponente() {
		nombreComponente.setValue("");
		marcaComponente.setValue("");
		capacidadComponente.setValue("");
		cantidadComponente.setValue("");
	}
	

	public void limpiarMaterial() {
		codigoMaterial.setValue("");
		nombreMaterial.setValue("");
		marcaMaterial.setValue("");
		capacidadMaterial.setValue("");
		cantidadMaterial.setValue("");
		observacionMaterial.setValue("");
	}	
	

	
	private void initComponents() {

		//setEvents();
		
		rbEstadoEquipo.setItems("BUENO","REGULAR","MALO");
		equipoFechaAdquisicion.setDateFormat("dd/MM/yyyy");
		
		limpiarEquipo();
		limpiarComponente();
		
		fechaCaducidadMedioCultivo.setDateFormat("dd/MM/yyyy");
	}

	private void cargarDatosPrincipales() {
		listLab = LabotatorioController.findAll();
		createMenu(); 

	}

	/*private void cargarDatosReactivo() {
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
	*/

	public void setCss() {

		// LABORATORIO

		cmbLaboratorio.addStyleName(ValoTheme.COMBOBOX_SMALL);
		btnAddLaboratorio.addStyleName(ValoTheme.BUTTON_SMALL);
		btnAddLaboratorio.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

		// REACTIVO
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
		btnAddUnidadMedioCultivo.addStyleName(ValoTheme.BUTTON_SMALL);
		btnAddUnidadMedioCultivo.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
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

	
}
