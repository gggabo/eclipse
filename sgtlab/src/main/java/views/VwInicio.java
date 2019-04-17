package views;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import models.Rol;

public class VwInicio extends VerticalLayout implements View, Serializable {
	private static final long serialVersionUID = 1L;
    
	private HorizontalLayout header = new HorizontalLayout();
	private Image imgHeader = new Image(null, new ThemeResource("images/logo_header_titulo.png"));
	private Label lbtRol = new Label("",ContentMode.HTML);
	@SuppressWarnings("unchecked")
	private List<Rol> roles = (List<Rol>) VaadinSession.getCurrent().getAttribute("TIPO_USUARIO");
	
	public VwInicio() {
		// TODO Auto-generated constructor stub
		
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
		
		addComponent(header);
	}
	
}
