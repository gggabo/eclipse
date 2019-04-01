package viewComponents;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

import models.TrazabilidadReactivo;

public class ReactivoGridComponent {
	public HorizontalLayout toolbarReactivoPQ = new HorizontalLayout();
	public VerticalLayout laboratorioReactivoLayoutPQ = new VerticalLayout();
	public MenuBar mainMenuRectivoPQ = new MenuBar();
	public Grid<TrazabilidadReactivo> gridReactivoPQ = new Grid<>();
	public List<TrazabilidadReactivo> listReactivosPQ = new ArrayList<>();	
	
	
}
