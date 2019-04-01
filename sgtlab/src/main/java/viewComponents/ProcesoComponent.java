package viewComponents;

import java.io.Serializable;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import controllers.LoginController;

public class ProcesoComponent extends HorizontalLayout implements Serializable {
	private static final long serialVersionUID = 1L;
	private Button trazButton = new Button("Revisado", VaadinIcons.CHECK);
	private Button delButton = new Button("Eliminar", VaadinIcons.CLOSE_CIRCLE_O);
	private Button editButton = new Button("Ver", VaadinIcons.EYE);
	private VerticalLayout layout = new VerticalLayout();
	private HorizontalLayout footer = new HorizontalLayout();
	private Label footerText = new Label("Revisado por: Gabriel Salvatierra Tumbaco<br> Katherine Mendoza", ContentMode.HTML);
	private VerticalLayout layoutComponent = new VerticalLayout();
	private FormLayout form = new FormLayout();
	private RichTextArea nombreProyecto = new RichTextArea();
	private Label usuariosProyecto = new Label();
	private VerticalLayout layoutInformacion = new VerticalLayout();
	private Image imgUser = new Image();
	private Label nombreUsuario = new Label();
	private Label fechaPublicacion = new Label();
	private Label horaPublicacion = new Label();
	private Label estadoProyecto = new Label();

	public ProcesoComponent() {
		layoutInformacion.addStyleName(ValoTheme.LAYOUT_CARD);
		layoutInformacion.addStyleName(ValoTheme.PANEL_SCROLL_INDICATOR);
		addStyleName(ValoTheme.PANEL_SCROLL_INDICATOR);
		setWidth("100%");
		
		imgUser.setSource(LoginController.getImgUser(1L));
		imgUser.addStyleName("v-avatar");
		
		form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		form.setWidth("100%");
		form.setSpacing(true);
		
		trazButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		delButton.addStyleName(ValoTheme.BUTTON_DANGER);
		editButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		trazButton.addStyleName(ValoTheme.BUTTON_SMALL);
		delButton.addStyleName(ValoTheme.BUTTON_SMALL);
		editButton.addStyleName(ValoTheme.BUTTON_SMALL);
        
		footer.setSpacing(true);
		footer.addComponents(footerText, editButton,trazButton,delButton);
		footer.setWidth("100%");
		footer.setExpandRatio(footerText, 1); 
		
		footerText.setSizeUndefined(); 
	    footerText.setStyleName(ValoTheme.LABEL_TINY);
	    
	    estadoProyecto.addStyleName(ValoTheme.LABEL_SMALL);
	    estadoProyecto.setCaption("Estado proceso");
	    estadoProyecto.setValue("Esperando revisión");
	    estadoProyecto.addStyleName("v-label-esperando-revision");
	    		
		nombreProyecto.addStyleName(ValoTheme.LABEL_SMALL);
		nombreProyecto.setCaption("Proceso");
		nombreProyecto.setReadOnly(true);
		nombreProyecto.setWidth("100%");
		nombreProyecto.setValue("Integer legentibus erat a ante historiarum dapibus. Vivamus sagittis lacus vel augue laoreet rutrum faucibus. A communi observantia non est recedendum. Morbi fringilla convallis sapien, id pulvinar odio volutpat. Ab illo tempore, ab est sed immemorabili. Quam temere in vitiis, legem sancimus haerentia.\r\n" + 
				"\r\n" + 
				"Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh. Quam diu etiam furor iste tuus nos eludet? Cum sociis natoque penatibus et magnis dis parturient. Quam diu etiam furor iste tuus nos eludet? Tityre, tu patulae recubans sub tegmine fagi dolor.\r\n" ); 
				       
		form.addComponents(estadoProyecto,nombreProyecto);
				 
		layoutComponent.setSpacing(false);
		layoutComponent.setMargin(false);
		layoutComponent.addComponent(form); 
		
		layout.setSpacing(false);
		layout.setMargin(true);
		layout.addComponents(form,footer);
		layout.setSizeFull();
		
	    nombreUsuario.setSizeUndefined(); 
	    nombreUsuario.setStyleName(ValoTheme.LABEL_TINY);
	    nombreUsuario.setValue(LoginController.u.getApellido_paterno()+" "+LoginController.u.getNombre_uno());
		
	    fechaPublicacion.setSizeUndefined(); 
	    fechaPublicacion.setStyleName(ValoTheme.LABEL_TINY);
	    fechaPublicacion.setValue("21/03/2019");
	    
	    horaPublicacion.setSizeUndefined(); 
	    horaPublicacion.setStyleName(ValoTheme.LABEL_TINY);
	    horaPublicacion.setValue("12:45");
		
		VerticalLayout lvUser = new VerticalLayout();
		lvUser.addComponents(imgUser,nombreUsuario, fechaPublicacion,horaPublicacion);
		lvUser.setSpacing(false);
		lvUser.setSizeUndefined();
		lvUser.setComponentAlignment(nombreUsuario, Alignment.BOTTOM_CENTER);
		lvUser.setComponentAlignment(imgUser, Alignment.BOTTOM_CENTER);
		lvUser.setComponentAlignment(fechaPublicacion, Alignment.BOTTOM_CENTER);
		lvUser.setComponentAlignment(horaPublicacion, Alignment.BOTTOM_CENTER);
		
		
		layoutInformacion.addComponent(layout);
		
		addComponents(lvUser,layoutInformacion);
		setExpandRatio(layoutInformacion, 1);
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


	/*public Label getNombreProyecto() {
		return nombreProyecto;
	}


	public void setNombreProyecto(Label nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}*/


	public Label getUsuariosProyecto() {
		return usuariosProyecto;
	}


	public void setUsuariosProyecto(Label usuariosProyecto) {
		this.usuariosProyecto = usuariosProyecto;
	}


	/*public QRCode getQr() {
		return qr;
	}


	public void setQr(QRCode qr) {
		this.qr = qr;
	}
*/

	public Button getEditButton() {
		return editButton;
	}


	public void setEditButton(Button editButton) {
		this.editButton = editButton;
	}
	
	
	
}
