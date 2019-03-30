package views;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import fi.jasoft.qrcode.QRCode;
import models.ProyectoParticipante;
import models.TipoProyecto;
import models.Trazabilidad;
import viewComponents.panelTrazabilidad;

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
	private Grid<Trazabilidad> gridTrazabilidad = new Grid<>();
	private List<Trazabilidad> listTrazabildiad = new ArrayList<>();
	
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
		cargarDatos();
		//addComponent(buildUI());
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
				//buildUIProyect();
			}
		});	
		
		mainMenu.addItem("Imprimir", VaadinIcons.PRINT, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				//buildUIProyect();
			}
		});	
			
		gridTrazabilidad.setBodyRowHeight(300);
		gridTrazabilidad.setSizeFull();
		gridTrazabilidad.setHeightMode(HeightMode.UNDEFINED);
		//gridTrazabilidad.addColumn(Trazabilidad -> Trazabilidad.getDescripcion()).setCaption("Descripción proceso").setExpandRatio(0);
		//panelTrazabilidad pn;
		gridTrazabilidad.addComponentColumn(Trazabilidad -> {
			panelTrazabilidad pn = new panelTrazabilidad();
			pn.setCaption("EJEMPLO DE PROYECTO");
			
			return pn;
		}).setCaption("Procesos");
		
		//gridTrazabilidad.setWidth("100%");
		gridTrazabilidad.setSelectionMode(SelectionMode.NONE);
		gridTrazabilidad.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		
		/*gridTrazabilidad.addComponentColumn(Materia -> {
			Button b2 = new Button("Quitar");
			b2.addClickListener(clickb2 -> {
				listMateria.remove(Materia);
				gridTrazabilidad.setItems(listMateria);
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);

			HorizontalLayout hl = new HorizontalLayout();
			hl.setSpacing(false);
			hl.setSizeFull();
			hl.addComponents(b2);
			return hl;
		}).setCaption("Opciones"); */
		
		trazabilidadLayout.addComponents(toolbar,gridTrazabilidad);//, buildUIProyect());
		trazabilidadLayout.setMargin(false);
		
		/*pnlPrincipal.setCaption("Gestión de proyectos");
		pnlPrincipal.setIcon(VaadinIcons.USERS);
		pnlPrincipal.setContent(proyectoLayout);*/
		
		mainLayout.addComponents(pnlPrincipal);
		return trazabilidadLayout;
	}

	public void cargarDatos() {
		listTrazabildiad.add(new Trazabilidad(LocalDate.now(), LocalTime.now(), "HHHH", null, 1));
		listTrazabildiad.add(new Trazabilidad(LocalDate.now(), LocalTime.now(), "HHHHsss", null, 1));
		listTrazabildiad.add(new Trazabilidad(LocalDate.now(), LocalTime.now(), "HHHHeeeeee", null, 1));
		
		gridTrazabilidad.setItems(listTrazabildiad);
	}
	
	public void setCss() {
		toolbar.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
	}
	
}
