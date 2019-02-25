package views;

import java.io.Serializable;

import com.example.sgtlab.MainUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

public class MainView extends HorizontalLayout implements Serializable {
	private static final long serialVersionUID = -6826161044686784696L;
	
	private VwMenu menu;
	
	public MainView(MainUI ui) {
		setSpacing(false);
		setStyleName("main-screen");
		
		CssLayout viewContainer = new CssLayout();
		viewContainer.addStyleName("valo-conten");
		viewContainer.setSizeFull();
		
		final Navigator navigator = new Navigator(ui, viewContainer);
		navigator.setErrorView(ErrorView.class); 
		
		menu = new VwMenu(navigator);
		menu.addView(new VwProyectos(), "inicio", "Inicio", VaadinIcons.HOME);
		menu.addView(new VwProyectos(), VwProyectos.VIEW_NAME, VwProyectos.VIEW_NAME, VaadinIcons.NOTEBOOK);
		menu.addView(new VwUsuarios(), "usuarios", "Usuarios", VaadinIcons.USERS);
		//menu.addView(new VwProyectos(), "categorias-cursos", "Categorias y cursos", VaadinIcons.ACADEMY_CAP);
		menu.addView(new VwLaboratorios(), "laboratorios", "Laboratorios", VaadinIcons.FLASK);
		
		navigator.addViewChangeListener(viewChangeListener);
		
		navigator.navigateTo("inicio");
		
		addComponent(menu);
		addComponent(viewContainer);
		setExpandRatio(viewContainer, 1);
		setSizeFull();
		
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
		};
	};
	

}
