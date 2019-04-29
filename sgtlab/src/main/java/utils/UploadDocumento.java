package utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class UploadDocumento extends HorizontalLayout implements Receiver {
	private static final long serialVersionUID = 1L;

	private byte[] data;
	private Upload upload = new Upload(null, this);
	private Image image = new Image();
	private float fileSizeLimit = 1.0f; // 1MB
	private String filename;
	private Label lbNombre = new Label();
	private String mimetype;
	private String tipoDoc;
	private Button btnClear = new Button();
	
	private final String PDF = "application/pdf";
	private final String DOC = "application/msword";
	private final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	private final String XLS = "application/vnd.ms-excel";
	private final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	private ProgressBar progress = new ProgressBar();
	private ByteArrayOutputStream byteArrayOutputStream;
	private VerticalLayout rootLayout;
	
	public UploadDocumento() { 
		
		progress.setIndeterminate(true);
		progress.setVisible(false);
		
		image.setWidth("90px"); 
		image.setHeight("80px");
		image.setVisible(false);
		//image.addStyleName("v-image-upload");
		//image.setSource(new ThemeResource("images/NO_IMAGE.png"));
		
		btnClear.setCaption("Borrar");
		btnClear.addStyleName(ValoTheme.BUTTON_DANGER);
		btnClear.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_TOP);
		btnClear.addStyleName(ValoTheme.BUTTON_TINY);
		btnClear.setHeight("27px"); 
		
		btnClear.addClickListener(e->{
			clear();
		});
		
		
		lbNombre.addStyleName(ValoTheme.LABEL_SMALL);
		lbNombre.addStyleName(ValoTheme.LABEL_COLORED);
		lbNombre.setValue(filename);
		lbNombre.setWidth("200px");
		
		HorizontalLayout hl = new HorizontalLayout();
		//hl.setSpacing(false);
		hl.addComponents(upload,btnClear);
		
		rootLayout = new VerticalLayout(image,lbNombre,hl,progress);
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
			if(mimetype.equals(DOC)){
				image.setSource(new ThemeResource("images/doc.png"));
				tipoDoc = "WORD DOC";
			}else if(mimetype.equals(DOCX)){
				image.setSource(new ThemeResource("images/doc.png"));
				tipoDoc = "WORD DOCX";
			}else if(mimetype.equals(XLS) || mimetype.equals(XLSX)){
				image.setSource(new ThemeResource("images/xls.png"));
				tipoDoc = "EXCEL XLS";
			}else if(mimetype.equals(XLSX)){
				image.setSource(new ThemeResource("images/xls.png"));
				tipoDoc = "EXCEL XLSX";
			}else if(mimetype.equals(PDF)){
				image.setSource(new ThemeResource("images/pdf.png"));
				tipoDoc = "PDF";
			}
			image.setVisible(true);
			lbNombre.setValue(filename);
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
		this.mimetype = mimeType;
		return byteArrayOutputStream;
	}
	
	private boolean isAllowed(String MIMEType)
    {
        return (
                MIMEType.equalsIgnoreCase(this.PDF) ||
                MIMEType.equalsIgnoreCase(this.DOC) ||
                MIMEType.equalsIgnoreCase(this.DOCX) ||
                MIMEType.equalsIgnoreCase(this.XLS)||
                MIMEType.equalsIgnoreCase(this.XLSX)
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
		image.setSource(null);
		image.setVisible(false);
		lbNombre.setValue("");
	    progress.setVisible(false);
	}
	
	public void setVisiblePreview(boolean visible) {
		image.setVisible(visible);
	}
	
	public void setValue(byte[] data) {
		if(data == null) {
			this.data = null;
			image.setSource(null);
		}else {
			this.data = data;
			image.setSource(null);			
		}
		
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	
	
	
}
