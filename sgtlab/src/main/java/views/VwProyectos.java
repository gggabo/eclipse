package views;

import java.io.Serializable;
import java.util.Iterator;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import viewComponents.panelProyecto;

public class VwProyectos extends VerticalLayout implements View, Serializable {
	private static final long serialVersionUID = -5759789185491799696L;
	
	public static final String VIEW_NAME = "proyectos";
	
	public VerticalLayout mainLayout = new VerticalLayout();
	public Panel pnlPrincipal = new Panel();
	public FormLayout mainFrm;
	public HorizontalLayout toolbar = new HorizontalLayout();
	public HorizontalLayout layoutProyectos = new HorizontalLayout();
	public VerticalLayout proyectoLayout = new VerticalLayout();
	public MenuBar mainMenu = new MenuBar();
	
	 private CssLayout dashboardPanels = new CssLayout();
	 public VwProyectos() {
		 addComponent(buildUI());
		 addStyleName("custom-margin-layout");
		 
		 
		  
		 // TODO Auto-generated constructor stub
		
		/*TextArea notes = new TextArea("Notes");
        notes.setValue("Remember to:\n· Zoom in and out in the Sales view\n· Filter the transactions and drag a set of them to the Reports tab\n· Create a new report\n· Change the schedule of the movie theater");
        notes.setSizeFull();
        notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        Component panel = createContentWrapper(notes);
        panel.addStyleName("notes");
		
        dashboardPanels.addComponent(panel);
		addComponent(dashboardPanels);*/
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
		mainMenu.addItem("Nuevo proyecto", VaadinIcons.USER_CHECK, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				/*userNewEdit(null);
				accion="guardar";*/
			}
		});		
		
		mainMenu.addItem("Imprimir", VaadinIcons.PRINT, null);		
		
		layoutProyectos.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		panelProyecto p1 = new panelProyecto();
		//p1.addComponentBody(panelContent());
		p1.setCaption("Proyecto integrador");
		
		panelProyecto p2 = new panelProyecto();
		//p2.addComponentBody(panelContent());
		p2.setCaption("Proyecto de clases");
		 
		panelProyecto p3 = new panelProyecto();
		//p3.addComponentBody(panelContent());
		p3.setCaption("Practica clases");
		
		panelProyecto p4 = new panelProyecto();
		//p4.addComponentBody(panelContent());
		p4.setCaption("Trabajo titulación");
		
		layoutProyectos.addComponents(p1,p2,p3,p4);
		layoutProyectos.setMargin(true);
		
		proyectoLayout.addComponents(toolbar, layoutProyectos);
		proyectoLayout.setMargin(false);
		
		pnlPrincipal.setCaption("Gestión de proyectos");
		pnlPrincipal.setIcon(VaadinIcons.USERS);
		pnlPrincipal.setContent(proyectoLayout);
		
		mainLayout.addComponents(pnlPrincipal);
		return mainLayout;
		
	}
	
	
	public Component buildUIProyect() {
		
		panelProyecto pnl = new panelProyecto();
	    pnl.addComponentBody(panelContent());
		pnl.setCaption("Proyecto integrador");
		
		return pnl;
	} 
	
	 Component panelContent() {
	        VerticalLayout layout = new VerticalLayout();
	        layout.setSizeFull();
	        Label content = new Label(
	                "Suspendisse dictum feugiat nisl ut dapibus. Mauris iaculis porttitor posuere. Praesent id metus massa, ut blandit odio.");
	        content.setWidth("10em");
	       // content.setSizeFull();
	      //  content.setSizeUndefined();
	        layout.addComponent(content);
	        Button button = new Button("Button");
	        button.setSizeFull();
	        layout.addComponent(button);
	        return layout;
	    }
	
	@SuppressWarnings("unused")
	private Component createContentWrapper(final Component content) {
        final CssLayout slot = new CssLayout();
        slot.setWidth("100%");
        slot.addStyleName("dashboard-panel-slot");

        CssLayout card = new CssLayout();
        card.setWidth("100%");
        card.addStyleName(ValoTheme.LAYOUT_CARD);

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("dashboard-panel-toolbar");
        toolbar.setWidth("100%");
        toolbar.setSpacing(false);

        Label caption = new Label(content.getCaption());
        caption.addStyleName(ValoTheme.LABEL_H4);
        caption.addStyleName(ValoTheme.LABEL_COLORED);
        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        content.setCaption(null);

        MenuBar tools = new MenuBar();
        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        MenuItem max = tools.addItem("", VaadinIcons.EXPAND, new Command() {

            /**
			 * 
			 */
			private static final long serialVersionUID = -3567305036949101199L;

			@Override
            public void menuSelected(final MenuItem selectedItem) {
                if (!slot.getStyleName().contains("max")) {
                    selectedItem.setIcon(VaadinIcons.COMPRESS);
                    toggleMaximized(slot, true);
                } else {
                    slot.removeStyleName("max");
                    selectedItem.setIcon(VaadinIcons.EXPAND);
                    toggleMaximized(slot, false);
                }
            }
        });
        max.setStyleName("icon-only");
        MenuItem root = tools.addItem("", VaadinIcons.COG, null);
        root.addItem("Configure", new Command() {
            /**
			 * 
			 */
			private static final long serialVersionUID = -150057833968707994L;

			@Override
            public void menuSelected(final MenuItem selectedItem) {
                Notification.show("Not implemented in this demo");
            }
        });
        root.addSeparator();
        root.addItem("Close", new Command() {
            /**
			 * 
			 */
			private static final long serialVersionUID = -7579904478147049889L;

			@Override
            public void menuSelected(final MenuItem selectedItem) {
                Notification.show("Not implemented in this demo");
            }
        });

        toolbar.addComponents(caption, tools);
        toolbar.setExpandRatio(caption, 1);
        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

        card.addComponents(toolbar, content);
        slot.addComponent(card);
        return slot;
    }
	
	private void toggleMaximized(final Component panel, final boolean maximized) {
        for (Iterator<Component> it = this.iterator(); it.hasNext();) {
            it.next().setVisible(!maximized);
        }
        dashboardPanels.setVisible(true);

        for (Iterator<Component> it = dashboardPanels.iterator(); it.hasNext();) {
            Component c = it.next();
            c.setVisible(!maximized);
        }

        if (maximized) {
            panel.setVisible(true);
            panel.addStyleName("max");
        } else {
            panel.removeStyleName("max");
        }
    }
	
}
