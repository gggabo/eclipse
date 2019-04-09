package utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class UploadImageEvidencia extends HorizontalLayout implements Receiver {
	private static final long serialVersionUID = 1L;

	private byte[] data;
	private Upload upload = new Upload(null, this);
	private Image image = new Image(null,new ThemeResource("images/NO_IMAGE.png"));
	private float fileSizeLimit = 1.0f; // 1MB
	private String filename;
	private Button btnClear = new Button();
	
	private final String PNG = "image/png";
	private final String GIF = "image/gif";
	private final String JPG = "image/jpg";
	private final String JPEG = "image/jpeg";
	
	private ProgressBar progress = new ProgressBar();
	private ByteArrayOutputStream byteArrayOutputStream;
	private HorizontalLayout rootLayout;
	
	public UploadImageEvidencia() { 
		
		progress.setIndeterminate(true);
		progress.setVisible(false);
		
		image.setWidth("160px"); 
		image.setHeight("130px");
		image.addStyleName("v-image-upload");
		image.setSource(new ThemeResource("images/NO_IMAGE.png"));
		
		btnClear.setCaption("Borrar");
		btnClear.addStyleName(ValoTheme.BUTTON_DANGER);
		btnClear.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_TOP);
		btnClear.addStyleName(ValoTheme.BUTTON_TINY);
		btnClear.setHeight("27px"); 
		
		btnClear.addClickListener(e->{
			clear();
		});
		
		VerticalLayout hl = new VerticalLayout();
		//hl.setSpacing(false);
		hl.addComponents(upload,btnClear);
		
		rootLayout = new HorizontalLayout(image,hl,progress);
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
		});
		
	    upload.addProgressListener((long readBytes, long contentLength) -> {
            if((float) (contentLength/1024)/1024 >=fileSizeLimit){
            	upload.interruptUpload();
            	message.warringMessage("Tamaño de archivo superior a "+ fileSizeLimit +"MB");
		    }
        });
		
		upload.addSucceededListener(e->{
			data = byteArrayOutputStream.toByteArray();
			image.setSource(uploadUtils.byteToImg(data));
			message.normalMessage("Archivo subido con exito");
		});
		
		upload.addFinishedListener(e->{

		});
		
		upload.addFailedListener(e->{
			
		});
	}
	
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		byteArrayOutputStream = new ByteArrayOutputStream();
		this.filename = filename;
		return byteArrayOutputStream;
	}
	
	private boolean isAllowed(String MIMEType)
    {
        return (
                MIMEType.equalsIgnoreCase(this.JPEG) ||
                MIMEType.equalsIgnoreCase(this.JPG) ||
                MIMEType.equalsIgnoreCase(this.GIF) ||
                MIMEType.equalsIgnoreCase(this.PNG)
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
		data = null;
		image.setSource(new ThemeResource("images/NO_IMAGE.png"));
	    progress.setVisible(false);
	}
	
	public void setVisiblePreview(boolean visible) {
		image.setVisible(visible);
	}
	
	public void setValue(byte[] data) {
		if(data == null) {
			this.data = null;
			image.setSource(new ThemeResource("images/NO_IMAGE.png"));
		}else {
			this.data = data;
			image.setSource(uploadUtils.byteToImg(data));			
		}
		
	}
	
}
