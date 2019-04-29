package views;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import controllers.DocumentoController;
import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.MessageBox;
import models.Documento;
import models.Rol;
import utils.UploadDocumento;
import utils.dialogWindow;
import utils.message;
import utils.uploadUtils;

public class VwDocumentos extends VerticalLayout implements View, Serializable {
	private static final long serialVersionUID = 1L;
	public long idUsuario = (long) VaadinSession.getCurrent().getAttribute("ID_USUARIO");
	@SuppressWarnings("unchecked")
	private List<Rol> roles = (List<Rol>) VaadinSession.getCurrent().getAttribute("TIPO_USUARIO");
	public VwProyectos proyectos;
	
	public VwDocumentos(){
		// TODO Auto-generated constructor stub
		Iterator<Rol> iteratorRol = roles.iterator();
		Rol rol;
		while(iteratorRol.hasNext()) {
			rol = iteratorRol.next();
			if(rol.getIdRol()==1) {
				toolbar.setVisible(true);
			}else {
				toolbar.setVisible(true);
			}
		} 
		
		addComponents(buildUI());
		
		llenarDatos();
		setEvents();
		
		addStyleName("custom-margin-layout");
	}
	
	public VerticalLayout mainLayout = new VerticalLayout();
	public Panel pnlDocumentos = new Panel();
	public HorizontalLayout toolbar = new HorizontalLayout();
	public HorizontalLayout layoutProyectos = new HorizontalLayout();
	public VerticalLayout documentosLayout = new VerticalLayout();
	public MenuBar mainMenu = new MenuBar();
	public CssLayout filteringDoc = new CssLayout();
	public Button clearFilterDoc = new Button(VaadinIcons.CLOSE_CIRCLE);
	public TextField filtertxtDoc = new TextField();
	
	public Panel pnlNotificacion = new Panel();
	
	public Grid<Documento> gridDocumentos = new Grid<>();
	public List<Documento> listDocumentos = new ArrayList<>();
	
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
		mainMenu.addItem("Nuevo documento", VaadinIcons.PLUS, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				newDocument();
			}
		});		
				
		filtertxtDoc.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		filtertxtDoc.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		filtertxtDoc.setIcon(VaadinIcons.SEARCH);
		filtertxtDoc.setPlaceholder("Buscar documento");
		filtertxtDoc.setWidth("100%");
		filtertxtDoc.setValueChangeMode(ValueChangeMode.LAZY);
		
		clearFilterDoc.addStyleName(ValoTheme.BUTTON_SMALL);
		
		filteringDoc.addComponents(filtertxtDoc,clearFilterDoc);
		filteringDoc.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filteringDoc.addStyleName("custom-margins");
		
		clearFilterDoc.addClickListener(e->{
			filtertxtDoc.clear();
		});
		
		gridDocumentos.setRowHeight(35.00); 
		gridDocumentos.addColumn(Documento -> Documento.getNombre()).setCaption("NOMBRE").setId("NOMBRE");
		gridDocumentos.addColumn(Documento -> Documento.getTipo()).setCaption("TIPO").setId("TIPO");
		
		gridDocumentos.setWidth("100%");
		gridDocumentos.setSelectionMode(SelectionMode.NONE);
		gridDocumentos.addComponentColumn(Documento -> {
	 
			Button b = new Button("Descargar");
			//b.addClickListener(clickb ->{ 
				File file = uploadUtils.bytesToFileDoc(Documento.getDocumento(), Documento.getTipo());
				Resource res = new FileResource(file);
				FileDownloader fd = new FileDownloader(res);
				fd.extend(b);				
			//});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.DOWNLOAD);
						
			Button b2 = new Button("Eliminar");
			b2.addClickListener(clickb ->{ 
				
				MessageBox.createQuestion()
				.withCaption("Confirmación de eliminación")
	    		.withMessage("Está seguro de eliminar este documento?")
	    		.withOkButton(() -> {
	    			Documento.setEstado(0);
					DocumentoController.update(Documento);
					listDocumentos.remove(Documento);
					gridDocumentos.setItems(listDocumentos);
	    		},ButtonOption.caption("Si"))
	    		.withCancelButton(ButtonOption.caption("No"))
	    		.open();
				
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.TRASH);
			
			HorizontalLayout hl = new HorizontalLayout();
			hl.addComponents(b,b2);
			return hl;			
		}).setCaption("Opciones");
		
		documentosLayout.addComponents(toolbar,filteringDoc,gridDocumentos);
		documentosLayout.setMargin(false);
		
		pnlDocumentos.setCaption("Documentos");
		pnlDocumentos.setIcon(VaadinIcons.COPY_O);
		pnlDocumentos.setContent(documentosLayout);
		//pnlDocumentos.setHeight("400px");	
		
		mainLayout.addComponents(pnlDocumentos);
		mainLayout.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		mainLayout.setHeight("100%");
		return mainLayout;
		
	}
	
	public void llenarDatos() {
		listDocumentos.clear();
		listDocumentos.addAll(DocumentoController.findAll());
		gridDocumentos.setItems(listDocumentos);
	}
	
	public void setEvents() {
		filtertxtDoc.addValueChangeListener(e ->{
			listDocumentos.clear();
			listDocumentos.addAll(DocumentoController.search(e.getValue()));
			gridDocumentos.setItems(listDocumentos);
		});
	}
	
	public FormLayout form = new FormLayout();
	public TextField nombreDoc = new TextField("Nombre documento");
	public UploadDocumento uploadDoc = new UploadDocumento();
	
	public void newDocument() {
		dialogWindow dialogReactivoWindow = new dialogWindow("Nuevo documento", VaadinIcons.COPY_O);
		dialogReactivoWindow.setResponsive(true);
		
		nombreDoc.setWidth("200px");
		nombreDoc.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		
		form.setMargin(false);
		uploadDoc.setCaption("Documento max 2mb");
		uploadDoc.setFileSizeLimit(2.0f);
		form.addComponents(nombreDoc,uploadDoc);
		dialogReactivoWindow.addComponentBody(form);
		dialogReactivoWindow.setWidth("420px");
		
		dialogReactivoWindow.getOkButton().addClickListener(e ->{
			
			if(nombreDoc.isEmpty()) {
				message.warringMessage("Necesita proporcionar un nomnbre");
				return;
			}
			
			if(uploadDoc.getValue()==null) {
				message.warringMessage("Necesita subir un archivo");
				return;
			}		
			
			Documento doc = new Documento(nombreDoc.getValue().trim(), uploadDoc.getTipoDoc(), uploadDoc.getValue(), 1);
			
			DocumentoController.save(doc);
			
			message.normalMessage("Acción realizada con exito");
			llenarDatos();
			dialogReactivoWindow.close();
		});
		
		dialogReactivoWindow.getCancelButton().addClickListener(e ->{
			dialogReactivoWindow.close();
		});
		
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}
	
	public void limpiarDocument() {
		nombreDoc.clear();
		uploadDoc.clear();
	}
	
}
