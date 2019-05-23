package views;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import controllers.LoginController;
import controllers.UsuarioController;
import models.Usuario;
import utils.dialogWindow;
import utils.message;
import viewComponents.editProfile;

public class VwMenu extends CssLayout implements Serializable {
	private static final long serialVersionUID = 4798824184709302432L;
	private static final String VALO_MENUITEMS = "valo-menuitems";
    private static final String VALO_MENU_TOGGLE = "valo-menu-toggle";
    private static final String VALO_MENU_VISIBLE = "valo-menu-visible";;
    MenuItem infItem;
    
    private Navigator navigator;
    private Map<String, Button> viewButtons = new HashMap<String, Button>();

    private String nombrePersona = (String) VaadinSession.getCurrent().getAttribute("NOMBRE_PERSONA");
    private long idUsuario = (long) VaadinSession.getCurrent().getAttribute("ID_USUARIO");
    
	CssLayout menuArea = new CssLayout();
	VerticalLayout menu = new VerticalLayout();
	VerticalLayout menuItemLayout = new VerticalLayout();
	VerticalLayout lvMenu = new VerticalLayout();
    
	
    private CssLayout menuItemsLayout;
    private CssLayout menuPart;
    
    
	//HorizontalLayout titulo = new HorizontalLayout();
	public Label lbTitulo = new Label("<div align='center'><h4> S. gestor de trazabilidad <br> <strong>Ingeniería Química</strong></h4></div>", ContentMode.HTML);
    
    private MenuItem cs;
	private MenuItem clave; 
	//private MenuItem rol; 
    
    public VwMenu(Navigator navigator) {
    	this.navigator = navigator;
        setPrimaryStyleName(ValoTheme.MENU_ROOT);
        menuPart = new CssLayout(); 
        menuPart.addStyleName(ValoTheme.MENU_PART);   
 
        // header of the menu
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName(ValoTheme.MENU_TITLE);
       // Label title = new Label("My CRUD");
        //lbTitulo.addStyleName(ValoTheme.LABEL_H3);
        lbTitulo.setSizeUndefined();
       // Image image = new Image(null, new ThemeResource("img/table-logo.png"));
        //image.setStyleName("logo");
     //   top.addComponent(image);
        top.addComponent(lbTitulo);
        menuPart.addComponent(top);

        // logout menu item
        
        MenuBar inf = new MenuBar();
        inf.addStyleName("user-menu");
        
        infItem = inf.addItem(nombrePersona, LoginController.getImgUser(idUsuario),null);
      //  rol = infItem.addItem("Cambiar rol",VaadinIcons.REFRESH ,comand);
        clave = infItem.addItem("Editar usuario",VaadinIcons.USER_CHECK ,comand);
        infItem.addSeparator();
        cs = infItem.addItem("Cerrar sesión", VaadinIcons.EXIT_O ,comand);
       // nameUser.addStyleName(ValoTheme.MENU_);
       //inf.addItem("Logout", VaadinIcons.SIGN_OUT, comand);
        
        menuPart.addComponents(inf);

        // button for toggling the visibility of the menu when on a small screen
        final Button showMenu = new Button("Menu", new ClickListener() {
			private static final long serialVersionUID = -3304985053249166783L;

			@Override
            public void buttonClick(final ClickEvent event) {
                if (menuPart.getStyleName().contains(VALO_MENU_VISIBLE)) {
                    menuPart.removeStyleName(VALO_MENU_VISIBLE);
                } else {
                    menuPart.addStyleName(VALO_MENU_VISIBLE);
                }
            }
        });
        showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
        showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
        showMenu.addStyleName(VALO_MENU_TOGGLE);
        showMenu.setIcon(VaadinIcons.MENU);
        menuPart.addComponent(showMenu);

        // container for the navigation buttons, which are added by addView()
        menuItemsLayout = new CssLayout();
        menuItemsLayout.setPrimaryStyleName(VALO_MENUITEMS);
        Label nameUser = new Label("Opciones");
        nameUser.addStyleName(ValoTheme.LABEL_H4);
        nameUser.addStyleName(ValoTheme.MENU_SUBTITLE);
        menuItemsLayout.addComponent(nameUser);
        menuPart.addComponent(menuItemsLayout);

        addComponent(menuPart);
	}
    
    /**
     * Register a pre-created view instance in the navigation menu and in the
     * {@link Navigator}.
     *
     * @see Navigator#addView(String, View)
     *
     * @param view
     *            view instance to register
     * @param name
     *            view name
     * @param caption
     *            view caption in the menu
     * @param icon
     *            view icon in the menu
     */
    public void addView(View view, final String name, String caption,
            Resource icon) {
        navigator.addView(name, view);
        createViewButton(name, caption, icon);
    }

    /**
     * Register a view in the navigation menu and in the {@link Navigator} based
     * on a view class.
     *
     * @see Navigator#addView(String, Class)
     *
     * @param viewClass
     *            class of the views to create
     * @param name
     *            view name
     * @param caption
     *            view caption in the menu
     * @param icon
     *            view icon in the menu
     */
    public void addView(Class<? extends View> viewClass, final String name,
            String caption, Resource icon) {
    	
        navigator.addView(name, viewClass);
        createViewButton(name, caption, icon);
    }

    private void createViewButton(final String name, String caption,
            Resource icon) {
        Button button = new Button(caption, new ClickListener() {

            /**
			 * 
			 */
			private static final long serialVersionUID = -1883760215859073317L;

			@Override
            public void buttonClick(ClickEvent event) {
                navigator.navigateTo(name);

            }
        });
        button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        button.setIcon(icon);
        menuItemsLayout.addComponent(button);
        viewButtons.put(name, button);
    }

    /**
     * Highlights a view navigation button as the currently active view in the
     * menu. This method does not perform the actual navigation.
     *
     * @param viewName
     *            the name of the view to show as active
     */
    public void setActiveView(String viewName) {
        for (Button button : viewButtons.values()) {
            button.removeStyleName("selected");
        }
        Button selected = viewButtons.get(viewName);
        if (selected != null) {
            selected.addStyleName("selected");
        }
        menuPart.removeStyleName(VALO_MENU_VISIBLE);
    }

    
    MenuBar.Command comand = new MenuBar.Command() {
		private static final long serialVersionUID = 765533891825052879L;

		@Override
		public void menuSelected(MenuItem selectedItem) {
			if(selectedItem == cs ){
				UI.getCurrent().getPage().setLocation(
						"/SGT/");
				//vaciarMenu();
				UI.getCurrent().getSession().close();
				//JPAUtil.shutdown();
			}
			if(selectedItem == clave) {
				editProfile();
			}
			
		}
	};
	
	public void editProfile() {
		
		editProfile perfil = new editProfile();
		Usuario user;
		user = UsuarioController.getSpecificUserById(idUsuario);
		
		perfil.cedula.setReadOnly(true);
		perfil.apellido_paterno.setReadOnly(true);
		perfil.apellido_materno.setReadOnly(true);
		perfil.nombre_uno.setReadOnly(true);
		perfil.nombre_dos.setReadOnly(true);
		perfil.nombre_usuario.setReadOnly(true);
		
		perfil.cedula.setValue(user.getCedula());
		perfil.apellido_paterno.setValue(user.getApellido_paterno());
		perfil.apellido_materno.setValue(user.getApellido_materno());
		perfil.nombre_uno.setValue(user.getNombre_uno());
		perfil.nombre_dos.setValue(user.getNombre_dos());
		perfil.correo.setValue(user.getCorreo());
		perfil.telefono.setValue(user.getTelefono());
		perfil.nombre_usuario.setValue(user.getNombre_usuario());
		perfil.uploadField.setValue(user.getImagen());
		
		dialogWindow dialogReactivoWindow = new dialogWindow("Editar perfil de usuario", VaadinIcons.USER);
		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.addComponentBody(perfil);
		
		dialogReactivoWindow.getOkButton().addClickListener(e ->{
			user.setCorreo(perfil.correo.getValue().trim());
			user.setTelefono(perfil.telefono.getValue().trim());
			user.setImagen(perfil.uploadField.getValue());
			if(!perfil.claveUsuario.getValue().isEmpty()) {
				user.setClave(DigestUtils.sha1Hex(perfil.claveUsuario.getValue().trim()));
			}
			
			UsuarioController.update(user);
			infItem.setIcon(LoginController.getImgUser(idUsuario));
			dialogReactivoWindow.close();
			message.normalMessage("Acción realizada con éxito");
		});
		dialogReactivoWindow.getCancelButton().addClickListener(e ->{
			dialogReactivoWindow.close();
		});
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}
    
}
