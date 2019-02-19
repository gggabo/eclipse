package utils;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class message {

	private static Notification msj;
	
	public static void warringMessage(String mensaje){
		msj = new Notification("SGTLab System","<br/>"+mensaje+"",Type.WARNING_MESSAGE, true);
		msj.setDelayMsec(2000);
		msj.show(Page.getCurrent());
		msj.setPosition(Position.BOTTOM_RIGHT);
	}
	
	public static void normalMessage(String mensaje){
		msj = new Notification("SGTLab System","<br/>"+mensaje+"",Type.HUMANIZED_MESSAGE, true);
		msj.setDelayMsec(1000);
		msj.show(Page.getCurrent());
		msj.setPosition(Position.BOTTOM_RIGHT);
	}
	
	
	
}
