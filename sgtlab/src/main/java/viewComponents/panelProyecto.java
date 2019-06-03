package viewComponents;

import java.io.Serializable;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
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
	private Button trazButton = new Button("", VaadinIcons.FILE_TREE_SMALL);
	private Button delButton = new Button("", VaadinIcons.TRASH);
	private Button editButton = new Button("", VaadinIcons.EDIT);
	private Button printButton = new Button("", VaadinIcons.PRINT);
	private Button endButton = new Button("", VaadinIcons.FLAG_O);
	private VerticalLayout layout = new VerticalLayout();
	private HorizontalLayout footer = new HorizontalLayout();
	private Label footerText = new Label("Opciones");
	private VerticalLayout layoutComponent = new VerticalLayout();
	private FormLayout form = new FormLayout();
	private Label nombreProyecto = new Label();
	private Label usuariosProyecto = new Label();
	private QRCode qr = new QRCode("Código");
	private Button btnImpQr = new Button();
	private Button btnImpQr2 = new Button();
	private Label estadoProyecto = new Label();

	public panelProyecto() {
		setIcon(VaadinIcons.CLUSTER);
		setStyleName("v-panel-proyecto-integrador");
		addStyleName(ValoTheme.PANEL_SCROLL_INDICATOR);
		
		trazButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		printButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		endButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		delButton.addStyleName(ValoTheme.BUTTON_DANGER);
		editButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		trazButton.addStyleName(ValoTheme.BUTTON_SMALL);
		delButton.addStyleName(ValoTheme.BUTTON_SMALL);
		editButton.addStyleName(ValoTheme.BUTTON_SMALL);
		printButton.addStyleName(ValoTheme.BUTTON_SMALL);
		endButton.addStyleName(ValoTheme.BUTTON_SMALL);
		
		trazButton.setDescription("Trazas del proyecto");
		printButton.setDescription("Imprimir proyecto");
		endButton.setDescription("Finalizar proyecto");
		delButton.setDescription("Eliminar proyecto");
		editButton.setDescription("Editar proyecto");
		
        footerText.setSizeUndefined(); 
        footerText.setStyleName(ValoTheme.LABEL_H3);
        footerText.setStyleName(ValoTheme.LABEL_COLORED);
        
		footer.setSpacing(true);
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.addComponents(footerText, editButton,trazButton,printButton,endButton,delButton);
		footer.setWidth("445px");
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

		form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		form.setWidth("310px");
		
		Label lb1 = new Label("Información del proyecto");
		lb1.setStyleName(ValoTheme.LABEL_H3);
		lb1.setStyleName(ValoTheme.LABEL_COLORED);
        form.addComponent(lb1);
        
        btnImpQr.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        btnImpQr.addStyleName(ValoTheme.BUTTON_SMALL);
        btnImpQr.setIcon(VaadinIcons.PRINT);
        btnImpQr2.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        btnImpQr2.addStyleName(ValoTheme.BUTTON_SMALL);
        btnImpQr2.setIcon(VaadinIcons.QRCODE);
        
        HorizontalLayout hlQr = new HorizontalLayout();
        hlQr.setSpacing(false); 
        hlQr.addComponents(btnImpQr,btnImpQr2);
        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(false);
        vl.addComponents(qr,hlQr);
        vl.setComponentAlignment(hlQr,Alignment.TOP_CENTER);
        
        qr.setCaption("Codigo");
        qr.setWidth("120px");
        qr.setHeight("120px");

        btnImpQr.addStyleName("v-button-qr");
        btnImpQr2.addStyleName("v-button-qr");
        
        estadoProyecto.addStyleName("v-label-esperando-revision");
		estadoProyecto.setCaption("Estado");
		estadoProyecto.addStyleName(ValoTheme.LABEL_SMALL);
        
        HorizontalLayout hl = new HorizontalLayout();
        
		form.addComponents(nombreProyecto,usuariosProyecto, estadoProyecto);
		form.setSpacing(true);
		
		//form2.addComponent(qr);
		
		hl.addComponents(form,vl);
		
		layoutComponent.setSpacing(false);
		layoutComponent.setMargin(false);
		layoutComponent.addComponent(hl); 
		
		layout.setSpacing(false);
		layout.setMargin(true);
		layout.addComponents(hl,footer);
		layout.setSizeFull();
		setContent(layout);
		
	}
		
	
	public Button getBtnImpQr2() {
		return btnImpQr2;
	}


	public void setBtnImpQr2(Button btnImpQr2) {
		this.btnImpQr2 = btnImpQr2;
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


	
	public Button getBtnImpQr() {
		return btnImpQr;
	}


	public void setBtnImpQr(Button btnImpQr) {
		this.btnImpQr = btnImpQr;
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

	

	public Button getPrintButton() {
		return printButton;
	}


	public void setPrintButton(Button printButton) {
		this.printButton = printButton;
	}


	public QRCode getQr() {
		return qr;
	}


	public void setQr(QRCode qr) {
		this.qr = qr;
	}


	public Button getEditButton() {
		return editButton;
	}


	public void setEditButton(Button editButton) {
		this.editButton = editButton;
	}


	public Label getEstadoProyecto() {
		return estadoProyecto;
	}


	public void setEstadoProyecto(Label estadoProyecto) {
		this.estadoProyecto = estadoProyecto;
	}


	public Button getEndButton() {
		return endButton;
	}


	public void setEndButton(Button endButton) {
		this.endButton = endButton;
	}
	
	
	
	
}
