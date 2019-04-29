package views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

import controllers.NotificacionController;
import controllers.ProyectoController;
import models.Notificacion;
import models.ProyectoParticipante;
import models.Rol;

public class VwInicio extends VerticalLayout implements View, Serializable {
	private static final long serialVersionUID = 1L;
	public long idUsuario = (long) VaadinSession.getCurrent().getAttribute("ID_USUARIO");
	private HorizontalLayout header = new HorizontalLayout();
	private Image imgHeader = new Image(null, new ThemeResource("images/logo_header_titulo.png"));
	private Label lbtRol = new Label("",ContentMode.HTML);
	@SuppressWarnings("unchecked")
	private List<Rol> roles = (List<Rol>) VaadinSession.getCurrent().getAttribute("TIPO_USUARIO");
	public VwProyectos proyectos;
	
	public VwInicio(VwProyectos proyectos) {
		// TODO Auto-generated constructor stub
		this.proyectos = proyectos;
		Iterator<Rol> iteratorRol = roles.iterator();
		Rol rol; 
		String strRol = "";
		while(iteratorRol.hasNext()) {
			rol = iteratorRol.next();
			strRol = strRol + rol.getNombre()+"&nbsp;&nbsp;";
		} 
		
		HorizontalLayout h = new HorizontalLayout();
		h.addComponent(imgHeader);
		h.setSizeFull();
		
		lbtRol.setValue(strRol);
		lbtRol.addStyleName(ValoTheme.LABEL_COLORED);
		lbtRol.addStyleName(ValoTheme.LABEL_H3);
		header.addComponents(h,lbtRol);
		header.setSizeFull();
		header.setExpandRatio(h, 1);
		
		addComponents(header, buildUI());
		
		llenarDatos();
		setEvents();
	}
	
	public HorizontalLayout mainLayout = new HorizontalLayout();
	public Panel pnlProyectos = new Panel();
	public HorizontalLayout toolbar = new HorizontalLayout();
	public HorizontalLayout layoutProyectos = new HorizontalLayout();
	public VerticalLayout proyectoLayout = new VerticalLayout();
	public MenuBar mainMenu = new MenuBar();
	public CssLayout filteringProject = new CssLayout();
	public Button clearFilterProject = new Button(VaadinIcons.CLOSE_CIRCLE);
	public TextField filtertxtProject = new TextField();
	
	public Panel pnlNotificacion = new Panel();
	
	public Grid<ProyectoParticipante> gridProyecto = new Grid<>();
	public List<ProyectoParticipante> listProyectos = new ArrayList<>();
	
	public Grid<Notificacion> gridNotificacion = new Grid<>();
	public List<Notificacion> listNotificaciones = new ArrayList<>();
	
	public Component buildUI() {
				
		filtertxtProject.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		filtertxtProject.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		filtertxtProject.setIcon(VaadinIcons.SEARCH);
		filtertxtProject.setPlaceholder("Buscar por codigo");
		filtertxtProject.setWidth("100%");
		filtertxtProject.setValueChangeMode(ValueChangeMode.LAZY);
		
		clearFilterProject.addStyleName(ValoTheme.BUTTON_SMALL);
		
		filteringProject.addComponents(filtertxtProject,clearFilterProject);
		filteringProject.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		
		gridProyecto.setRowHeight(35.00); 
		gridProyecto.addColumn(Proyecto -> Proyecto.getProyecto().getCodigo()).setCaption("CODIGO").setId("CODIGO");
		gridProyecto.addColumn(Proyecto -> Proyecto.getProyecto().getTema()).setCaption("TEMA").setId("TEMA");
		
		gridProyecto.setHeight("300px");
		gridProyecto.setSelectionMode(SelectionMode.NONE);
		gridProyecto.addComponentColumn(Proyecto -> {
	 
			Button b = new Button("Ir a proyecto");
			b.addClickListener(clickb ->{ 
				proyectos.filtertxtProject.setValue(Proyecto.getProyecto().getCodigo());
                getUI().getNavigator().navigateTo("proyectos");	
			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.ARROW_FORWARD);
						
			HorizontalLayout hl = new HorizontalLayout();
			hl.addComponents(b);
			return hl;			
		}).setCaption("Opciones");
		
		proyectoLayout.addComponents(filteringProject,gridProyecto);
		proyectoLayout.setMargin(true);
		
		pnlProyectos.setCaption("Mis proyectos");
		pnlProyectos.setIcon(VaadinIcons.SUITCASE);
		pnlProyectos.setContent(proyectoLayout);
		pnlProyectos.setHeight("400px");
		
		gridNotificacion.setRowHeight(70.00);
		gridNotificacion.setHeaderVisible(false);
		//gridNotificacion.addColumn(Notificacion -> Notificacion.getIdNotificacion()).setCaption("NOTIFICACION").setId("NOTIFICACION");
		gridNotificacion.addComponentColumn(Notificacion -> {
			Label lb = new Label();
			lb.addStyleName(ValoTheme.NOTIFICATION_SUCCESS);
			lb.setValue(Notificacion.getDetalle());
			lb.setSizeFull();
			return lb;
		}).setCaption("NOTIFICACION").setId("NOTIFICACION").setExpandRatio(1);
		gridNotificacion.setHeight("350px");
		
		pnlNotificacion.setCaption("Mis notificaciones");
		pnlNotificacion.setIcon(VaadinIcons.COMMENTS);
		pnlNotificacion.setContent(gridNotificacion);
		pnlNotificacion.setHeight("400px");
		
		
		mainLayout.addComponents(pnlProyectos, pnlNotificacion);
		mainLayout.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		mainLayout.setHeight("100%");
		return mainLayout;
		
	}
	
	public void llenarDatos() {
		listProyectos.addAll(ProyectoController.getProyectoByUser(idUsuario, ""));
		gridProyecto.setItems(listProyectos);
		
		listNotificaciones.addAll(NotificacionController.getNotificacionByIdUser(idUsuario));
		gridNotificacion.setItems(listNotificaciones);
	}
	
	public void setEvents() {
		filtertxtProject.addValueChangeListener(e ->{
			listProyectos.clear();
			listProyectos.addAll(ProyectoController.getProyectoByUser(idUsuario, e.getValue()));
			gridProyecto.setItems(listProyectos);
		});
	}
	
}
