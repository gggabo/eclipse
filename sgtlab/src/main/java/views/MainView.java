package views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.sgtlab.MainUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import models.Rol;

public class MainView extends HorizontalLayout implements Serializable {
	private static final long serialVersionUID = 1L;
	private VwMenu menu;
	private VwUsuarios usuario = new VwUsuarios();
	public VwProyectos proyectos = new VwProyectos();
	@SuppressWarnings("unchecked")
	private List<Rol> roles = (List<Rol>) VaadinSession.getCurrent().getAttribute("TIPO_USUARIO");
	public MainView(MainUI ui) {
		setSpacing(false);
		setStyleName("main-screen");
		 
		CssLayout viewContainer = new CssLayout();
		viewContainer.setSizeFull();
		viewContainer.addStyleName("v-scrollable");
		viewContainer.addStyleName("custom-margin-layout");
		viewContainer.addStyleName("login-screen");
		
		final Navigator navigator = new Navigator(ui, viewContainer);
		navigator.setErrorView(ErrorView.class); 
		
		menu = new VwMenu(navigator);
		List<String> menuAdd = new ArrayList<>();		
		
		Iterator<Rol> iteratorRol = roles.iterator();
		Rol rol;
		while(iteratorRol.hasNext()) {
			rol = iteratorRol.next(); 
			
			if(rol.getIdRol()==1) {//ADMINISTRADOR
				if(!menuAdd.contains("inicio")) {
					menu.addView(new VwInicio(proyectos), "inicio", "Inicio", VaadinIcons.HOME);
					menuAdd.add("inicio");
				}
				if(!menuAdd.contains("proyectos")) {
					menu.addView(proyectos, "proyectos", "Mis proyectos", VaadinIcons.NOTEBOOK);
					menuAdd.add("proyectos");
				}
				
				if(!menuAdd.contains("admin_proyectos")) {
					menu.addView(new VwProyectosAll(), "admin_proyectos", "Todos los proyectos", VaadinIcons.NOTEBOOK);
					menuAdd.add("admin_proyectos");
				}
				
				if(!menuAdd.contains("usuarios")) {
					menu.addView(usuario, "usuarios", "Usuarios", VaadinIcons.USERS);
					menuAdd.add("usuarios");
				}
				
				if(!menuAdd.contains("laboratorios")) {
					menu.addView(new VwLaboratorios(), "laboratorios", "Laboratorios", VaadinIcons.FLASK);
					menuAdd.add("laboratorios");
				}
				
				if(!menuAdd.contains("documentos")) {
					menu.addView(new VwDocumentos(), "documentos", "Documentos", VaadinIcons.COPY_O);
					menuAdd.add("documentos");
				}

			}else if(rol.getIdRol()==4) {//RESPONSABLE LABORATORIO
				if(!menuAdd.contains("inicio")) {
					menu.addView(new VwInicio(proyectos), "inicio", "Inicio", VaadinIcons.HOME);
					menuAdd.add("inicio");
				}
				
				if(!menuAdd.contains("proyectos")) {
					menu.addView(proyectos, "proyectos", "Proyectos", VaadinIcons.NOTEBOOK);
					menuAdd.add("proyectos");
				}
				
				if(!menuAdd.contains("laboratorios")) {
					menu.addView(new VwLaboratorios(), "laboratorios", "Laboratorios", VaadinIcons.FLASK);
					menuAdd.add("laboratorios");
				}
				
				if(!menuAdd.contains("documentos")) {
					menu.addView(new VwDocumentos(), "documentos", "Documentos", VaadinIcons.COPY_O);
					menuAdd.add("documentos");
				}
				
			}else if(rol.getIdRol()==2) {//DOCENTE
				if(!menuAdd.contains("inicio")) {
					menu.addView(new VwInicio(proyectos), "inicio", "Inicio", VaadinIcons.HOME);
					menuAdd.add("inicio");
				}
				if(!menuAdd.contains("proyectos")) {
					menu.addView(proyectos, "proyectos", "Proyectos", VaadinIcons.NOTEBOOK);
					menuAdd.add("proyectos");
				}
				
				/*if(!menuAdd.contains("laboratorios")) {
					menu.addView(new VwLaboratorios(), "laboratorios", "Laboratorios", VaadinIcons.FLASK);
					menuAdd.add("laboratorios");
				}*/
				
				if(!menuAdd.contains("documentos")) {
					menu.addView(new VwDocumentos(), "documentos", "Documentos", VaadinIcons.COPY_O);
					menuAdd.add("documentos");
				}
				
			}else if(rol.getIdRol()==3) {//ESTUDIANTE
				if(!menuAdd.contains("inicio")) {
					menu.addView(new VwInicio(proyectos), "inicio", "Inicio", VaadinIcons.HOME);
					menuAdd.add("inicio");
				}
				if(!menuAdd.contains("proyectos")) {
					menu.addView(proyectos, "proyectos", "Proyectos", VaadinIcons.NOTEBOOK);
					menuAdd.add("proyectos");
				}
				
				if(!menuAdd.contains("documentos")) {
					menu.addView(new VwDocumentos(), "documentos", "Documentos", VaadinIcons.COPY_O);
					menuAdd.add("documentos");
				}
			}
		} 
		
		navigator.addViewChangeListener(viewChangeListener);
		
		navigator.navigateTo("inicio");
		
		addComponent(menu);
		addComponent(viewContainer);
		setExpandRatio(viewContainer, 1);
		setSizeFull();
		addStyleName("custom-margin-layout");
		
	}
	
	ViewChangeListener viewChangeListener = new ViewChangeListener() {
		private static final long serialVersionUID = -8532897078668028709L;

		@Override
		public boolean beforeViewChange(ViewChangeEvent event) {
			// TODO Auto-generated method stub
			return true;
		}
		@Override
		public void afterViewChange(ViewChangeEvent event) {
			menu.setActiveView(event.getViewName());
			if(event.getViewName().equals("usuarios")) {
				usuario.cargarDatos();
			}
		};
	};
	

}
