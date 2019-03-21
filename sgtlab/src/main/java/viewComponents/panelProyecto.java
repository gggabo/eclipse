package viewComponents;

import java.io.Serializable;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class panelProyecto extends Panel implements Serializable {
	private static final long serialVersionUID = 1L;
	private Button okButton = new Button("Editar", VaadinIcons.CHECK_CIRCLE_O);
	private Button cancelButton = new Button("Eliminar", VaadinIcons.CLOSE_CIRCLE_O);
	private VerticalLayout layout = new VerticalLayout();
	private HorizontalLayout footer = new HorizontalLayout();
	private Label footerText = new Label("Opciones       ");
	private VerticalLayout layoutComponent = new VerticalLayout();
	private FormLayout form = new FormLayout();
	private TextArea nombreProyecto = new TextArea("Tema");

	public panelProyecto() {
		setIcon(VaadinIcons.CLUSTER);
		setStyleName("v-panel-proyecto-integrador");
		
		okButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		cancelButton.addStyleName(ValoTheme.BUTTON_DANGER);
		okButton.addStyleName(ValoTheme.BUTTON_SMALL);
		cancelButton.addStyleName(ValoTheme.BUTTON_SMALL);
		
        footerText.setSizeUndefined(); 
        footerText.setStyleName(ValoTheme.LABEL_H3);
        footerText.setStyleName(ValoTheme.LABEL_COLORED);
        
		footer.setSpacing(true);
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.addComponents(footerText, okButton,cancelButton);
		footer.setWidth("310px");
		footer.setExpandRatio(footerText, 1);
		
		nombreProyecto.setValue("Desarrollo e implementación de un sistema de trazabilidad");
		
		form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		form.setWidth("310px");
		
		Label lb1 = new Label("Información del proyecto");
		lb1.setStyleName(ValoTheme.LABEL_H3);
		lb1.setStyleName(ValoTheme.LABEL_COLORED);
        form.addComponent(lb1);
        
		form.addComponent(nombreProyecto);
		
		layoutComponent.setSpacing(false);
		layoutComponent.setMargin(false);
		layoutComponent.addComponent(form);
		
		layout.setSpacing(false);
		layout.setMargin(true);
		layout.addComponents(form,footer);
		setContent(layout);
		//setSizeFull();
		//setResponsive(true);
	}
		
	
	public void addComponentBody(Component c) {
		layoutComponent.addComponent(c);
	}

	public Button getOkButton() {
		return okButton;
	}

	public void setOkButton(Button okButton) {
		this.okButton = okButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}
	
}
