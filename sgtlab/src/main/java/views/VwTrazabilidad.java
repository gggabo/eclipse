package views;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import fi.jasoft.qrcode.QRCode;
import models.ProyectoParticipante;
import models.TipoProyecto;

public class VwTrazabilidad extends Panel {
	private static final long serialVersionUID = 1L;

	public VerticalLayout mainLayout = new VerticalLayout();
	public Panel pnlPrincipal = new Panel();
	public FormLayout mainFrm;
	public HorizontalLayout toolbar = new HorizontalLayout();
	//public HorizontalLayout layoutProyectos = new HorizontalLayout();
	public VerticalLayout trazabilidadLayout = new VerticalLayout();
	public MenuBar mainMenu = new MenuBar();
	private VwProyectos vwproyectos;
	
	public VwTrazabilidad(VwProyectos vwproyectos) {
		this.vwproyectos = vwproyectos;
		setCaption("Gestión de trazabilidad de producto");
		setIcon(VaadinIcons.FILE_PROCESS);
		
		setCss();
		/*Button b = new Button("Trazabilidad");
		b.addClickListener(e ->{
			
		});
		VerticalLayout vl = new VerticalLayout();
		vl.addComponent(b);*/
		setContent(buildUI());
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
		
		mainMenu.addItem("Imprimir", VaadinIcons.PRINT, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				//buildUIProyect();
			}
		});	
			
		trazabilidadLayout.addComponents(toolbar);//, buildUIProyect());
		trazabilidadLayout.setMargin(false);
		
		/*pnlPrincipal.setCaption("Gestión de proyectos");
		pnlPrincipal.setIcon(VaadinIcons.USERS);
		pnlPrincipal.setContent(proyectoLayout);*/
		
		mainLayout.addComponents(pnlPrincipal);
		return trazabilidadLayout;
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
	
	public void setCss() {
		toolbar.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
	}
	
}
