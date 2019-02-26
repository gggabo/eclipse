package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;

import com.vaadin.ui.themes.ValoTheme;

import models.Usuario;

public class uploadXls extends HorizontalLayout implements Receiver {
	private static final long serialVersionUID = 1L;

	private byte[] data;
	private Upload upload = new Upload(null, this);
	private Image image = new Image(null,new ThemeResource("images/xlsCancel.png"));
	private float fileSizeLimit = 1.0f; // 1MB
	private String filename;
	public File file;
	private Button btnClear = new Button();
	private Grid<Usuario> gridUsuarioImport = new Grid<>();
	private List<Usuario> listUsuariosImport = new ArrayList<>();
	private readXlsUsers readXlsUsers = new readXlsUsers();
	
	private final String XLS = "application/vnd.ms-excel";
	/*private final String GIF = "image/gif";
	private final String JPG = "image/jpg";
	private final String JPEG = "image/jpeg";*/
	
	private ProgressBar progress = new ProgressBar();
	//private ByteArrayOutputStream byteArrayOutputStream;
	private HorizontalLayout rootLayout;
	
	public uploadXls() { 
		
		progress.setIndeterminate(true);
		progress.setVisible(false);
		
		image.setWidth("50px"); 
		image.setHeight("60px");
		image.addStyleName("v-image-upload"); 
		image.setSource(new ThemeResource("images/xlsCancel.png"));
		
		btnClear.setCaption("Borrar");
		btnClear.addStyleName(ValoTheme.BUTTON_DANGER);
		btnClear.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_TOP);
		btnClear.addStyleName(ValoTheme.BUTTON_TINY);
		btnClear.setHeight("27px"); 
		
		btnClear.addClickListener(e->{
			clear();
		});
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(false);
		hl.addComponents(upload,btnClear);
		
		rootLayout = new HorizontalLayout(image,progress, hl);
		rootLayout.setSpacing(false);
		rootLayout.setMargin(false);
		rootLayout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		rootLayout.setComponentAlignment(progress, Alignment.MIDDLE_CENTER);
		rootLayout.setComponentAlignment(hl, Alignment.MIDDLE_CENTER);
		
		addComponent(rootLayout);
		upload.setButtonStyleName("v-button v-widget friendly v-button-friendly small v-button-small");
		upload.setButtonCaption("Subir");
		
		upload.addStartedListener(e -> {
			if(!isAllowed(e.getMIMEType())) {
				upload.interruptUpload();
				message.warringMessage("Tipo de archivo no admitido");
			}
		    listUsuariosImport.clear();
		    gridUsuarioImport.setItems(listUsuariosImport);
		});
		
	    upload.addProgressListener((long readBytes, long contentLength) -> {
            if((float) (contentLength/1024)/1024 >=fileSizeLimit){
            	upload.interruptUpload();
            	message.warringMessage("Tamaño de archivo superior a "+ fileSizeLimit +"MB");
		    }
        });
		
	    
		upload.addSucceededListener(e->{
			/*data = byteArrayOutputStream.toByteArray();*/
			image.setSource(new ThemeResource("images/xlsOk.png"));
			message.normalMessage("Archivo subido con exito");
			
			readXlsUsers.setFile(file);
			listUsuariosImport=readXlsUsers.getXlsUsersResultList();
			
			//listUsuariosImport.add(new Usuario("1313253575", "GABRIEL", "GABRIEL", "GABRIEL", "GABRIEL", "GABRIEL", "GABRIEL", null, "gsalvatierra", "123456", 1));
			
			gridUsuarioImport.setItems(listUsuariosImport);
			
		});
		 
		upload.addFinishedListener(e->{ 

		});
		
		upload.addFailedListener(e->{
			
		});
	}
	
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		/*byteArrayOutputStream = new ByteArrayOutputStream();
		this.filename = filename;
		return byteArrayOutputStream;*/
		FileOutputStream fos = null; 
		try {
			setFilename(uploadUtils.generarCodigoXls());
			//ABRIR EL ARCHIVO PARA GUARDARLO
			file = new File(rutas.RUTA_UPLOAD+getFilename()); 
			fos = new FileOutputStream(file);
		} catch (final IOException e) {
			new Notification("No se pudo abrir el archivo",e.getMessage(),Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
			return null;
		}
		return fos;
		
	}
	
	private boolean isAllowed(String MIMEType)
    {
        return (
               /* MIMEType.equalsIgnoreCase(this.JPEG) ||
                MIMEType.equalsIgnoreCase(this.JPG) ||
                MIMEType.equalsIgnoreCase(this.GIF) ||*/
                MIMEType.equalsIgnoreCase(this.XLS)
                );
    }
	
	public byte[] getValue() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public float getFileSizeLimit() {
		return fileSizeLimit;
	}

	public void setFileSizeLimit(float fileSizeLimit) {
		this.fileSizeLimit = fileSizeLimit;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public void setCaptionButton(String caption) {
		upload.setButtonCaption(caption);
	}
	
	public void clear() {
		file = null;
		image.setSource(new ThemeResource("images/xlsCancel.png"));
	    progress.setVisible(false);
	    listUsuariosImport.clear();
	    gridUsuarioImport.setItems(listUsuariosImport);
	}
	
	public void setVisiblePreview(boolean visible) {
		image.setVisible(visible);
	}
	
	public void setValue(File data) {
		if(data == null) {
			this.data = null;
			image.setSource(new ThemeResource("images/xlsCancel.png"));
		}else {
			this.file = data;
			image.setSource(new ThemeResource("images/xlsOk.png"));			
		}
		
	}
	
	public Grid<Usuario> getGridUsuarioImport() {
		return gridUsuarioImport;
	}

	public void setGridUsuarioImport(Grid<Usuario> gridUsuarioImport) {
		this.gridUsuarioImport = gridUsuarioImport;
	}

	public List<Usuario> getListUsuariosImport() {
		return listUsuariosImport;
	}

	public void setListUsuariosImport(List<Usuario> listUsuariosImport) {
		this.listUsuariosImport = listUsuariosImport;
	}
	
}
