package viewComponents;

import java.io.Serializable;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import fi.jasoft.qrcode.QRCode;

public class panelProyecto extends Panel implements Serializable {
	private static final long serialVersionUID = 1L;
	private Button trazButton = new Button("", VaadinIcons.BOOK);
	private Button delButton = new Button("", VaadinIcons.CLOSE_CIRCLE_O);
	private Button editButton = new Button("", VaadinIcons.EDIT);
	private VerticalLayout layout = new VerticalLayout();
	private HorizontalLayout footer = new HorizontalLayout();
	private Label footerText = new Label("Opciones");
	private VerticalLayout layoutComponent = new VerticalLayout();
	private FormLayout form = new FormLayout();
	private Label nombreProyecto = new Label();
	private Label usuariosProyecto = new Label();
	private QRCode qr = new QRCode();

	public panelProyecto() {
		setIcon(VaadinIcons.CLUSTER);
		setStyleName("v-panel-proyecto-integrador");
		addStyleName(ValoTheme.PANEL_SCROLL_INDICATOR);
		
		trazButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		delButton.addStyleName(ValoTheme.BUTTON_DANGER);
		editButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		trazButton.addStyleName(ValoTheme.BUTTON_SMALL);
		delButton.addStyleName(ValoTheme.BUTTON_SMALL);
		editButton.addStyleName(ValoTheme.BUTTON_SMALL);
		/*trazButton.setWidth("40px");
		delButton.setWidth("40px");
		editButton.setWidth("40px");*/
		
        footerText.setSizeUndefined(); 
        footerText.setStyleName(ValoTheme.LABEL_H3);
        footerText.setStyleName(ValoTheme.LABEL_COLORED);
        
		footer.setSpacing(true);
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.addComponents(footerText, editButton,trazButton,delButton);
		footer.setWidth("310px");
		footer.setExpandRatio(footerText, 1);
		
		nombreProyecto.addStyleName(ValoTheme.LABEL_SMALL);
		nombreProyecto.setCaption("Tema");
		nombreProyecto.setSizeFull();
		nombreProyecto.setContentMode(ContentMode.HTML);
		nombreProyecto.setValue("Desarrollo e implementación de un sistema de trazabilidad");
		
		usuariosProyecto.addStyleName(ValoTheme.LABEL_SMALL);
		usuariosProyecto.setCaption("Usuarios");
		usuariosProyecto.setSizeFull();
		usuariosProyecto.setContentMode(ContentMode.HTML);
		/*usuariosProyecto.setValue("Salvatierra Tumbaco Gabriel<br>"
				+ "Bravo Kevin Alex<br>"
				+ "Sanchez Moreira Freddy<br>");
		*/
		form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		form.setWidth("310px");
		
		Label lb1 = new Label("Información del proyecto");
		lb1.setStyleName(ValoTheme.LABEL_H3);
		lb1.setStyleName(ValoTheme.LABEL_COLORED);
        form.addComponent(lb1);
        
        qr.setCaption("Codigo");
        //qr.setValue("Ejemplo de QR - Gabriel");
        qr.setWidth("120px");
        qr.setHeight("120px");
        
		form.addComponents(nombreProyecto,usuariosProyecto,qr);
		form.setSpacing(true);
		
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
		return trazButton;
	}

	public void setOkButton(Button okButton) {
		this.trazButton = okButton;
	}

	public Button getCancelButton() {
		return delButton;
	}

	public void setCancelButton(Button cancelButton) {
		this.delButton = cancelButton;
	}


	public Label getNombreProyecto() {
		return nombreProyecto;
	}


	public void setNombreProyecto(Label nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}


	public Label getUsuariosProyecto() {
		return usuariosProyecto;
	}


	public void setUsuariosProyecto(Label usuariosProyecto) {
		this.usuariosProyecto = usuariosProyecto;
	}


	public QRCode getQr() {
		return qr;
	}


	public void setQr(QRCode qr) {
		this.qr = qr;
	}
	
	
	
}
