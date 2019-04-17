package utils;

import java.io.Serializable;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class dialogWindow extends Window implements Serializable {
	private static final long serialVersionUID = 3180695715553484812L;

	private VerticalLayout v = new VerticalLayout();
	private HorizontalLayout footer = new HorizontalLayout();
	private Button okButton = new Button("Aceptar", VaadinIcons.CHECK_CIRCLE_O);
	private Button cancelButton = new Button("Cancelar", VaadinIcons.CLOSE_CIRCLE_O);
	private Label footerText = new Label("Guardar información");
	private VerticalLayout layoutComponent = new VerticalLayout();

	public dialogWindow(String title, VaadinIcons icon) {
		setCaption(title);
		setIcon(icon);
		center();
		setResizable(false);
		setModal(true);
		setSizeUndefined();
		//setWidth("700px");
		
		okButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		cancelButton.addStyleName(ValoTheme.BUTTON_DANGER);
		
		footerText.setSizeUndefined(); 
		
		footer.setWidth("100%");
		footer.setSpacing(true);
		footer.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.addComponents(footerText,okButton,cancelButton);
		footer.setExpandRatio(footerText, 1);
		
		layoutComponent.setSpacing(false);
		v.setSpacing(false);
		v.addComponents(layoutComponent,footer);
		setContent(v);
		
	}
	
	
	
	/*public void setTitle(String title) {
		this.title = title;
	}*/
	
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
	public VerticalLayout getLayoutComponent() {
		return layoutComponent;
	}

	public void setLayoutComponent(VerticalLayout layoutComponent) {
		this.layoutComponent = layoutComponent;
	}

    

	public HorizontalLayout getFooter() {
		return footer;
	}



	public void setFooter(HorizontalLayout footer) {
		this.footer = footer;
	}



	public Label getFooterText() {
		return footerText;
	}



	public void setFooterText(Label footerText) {
		this.footerText = footerText;
	}

	

	
	
	
	
	
}
