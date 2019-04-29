package utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;

import com.vaadin.server.ConnectorResource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;

public class uploadUtils implements Serializable{

	private classGeneradorCodigo genCod = new classGeneradorCodigo();
	private static String path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/VAADIN/themes/dashboard/";
	
	private static final long serialVersionUID = 1L;


	public void redimensionar(File file) {
		/*try {
			Thumbnails.of(file).size(1000, 1000).toFile(new File(file.getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void redimensionar2(File file) {
		/*try {
			Thumbnails.of(file).size(3000, 3000).toFile(new File(file.getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public static byte[] imgToByte(File file) {
		byte[] fileByte = null;
		try {
			if(file==null)
				return null;
			fileByte = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileByte;
	}
	
	public File bytesToFile(byte[] data) {
		File f = new File(rutas.RUTA_UPLOAD+genCod.generarCodigoImg());
		try {
			FileUtils.writeByteArrayToFile(f, data);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return f;
	}
	
	//OBTENER DOCUMENTOS DESDE LA BASE DE DATOS
	public static File bytesToFileDoc(byte[] data, String tipo) {
		String extension="";
		if(tipo.equals("WORD DOC")) {
			extension = ".doc";
		}else if(tipo.equals("WORD DOCX")) {
			extension = ".docx";
		}else if(tipo.equals("EXCEL XLS")) {
			extension = ".xls";
		}else if(tipo.equals("EXCEL XLSX")) {
			extension = ".xlsx";
		}else if(tipo.equals("PDF")) {
			extension = ".pdf";
		}
			
		File f = new File(rutas.RUTA_DOWNLOAD+classGeneradorCodigo.generarCodigoDocs()+extension);
		try {
			FileUtils.writeByteArrayToFile(f, data);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return f;
	}
	
	// FUNCION PARA OBTENER IMAGENES DESDE LA BASE DE DATOS
	
	static byte[] imagenResult= null;
	
	public static StreamResource byteToImg(byte[] imagen) {
		
		if(imagen == null) {
			 /*File directory = new File("./");
			    System.out.println(directory.getAbsolutePath());*/
			imagenResult = imgToByte(new File(path+"/images/NO_USER.png"));
		}else {
			imagenResult = imagen;
		}
		
		StreamResource.StreamSource imageSource = null;
		imageSource = new StreamResource.StreamSource() {
			private static final long serialVersionUID = 1L;

			@Override
			public InputStream getStream() {
				return new ByteArrayInputStream(imagenResult);
			}
		};
		StreamResource imageResource = new StreamResource(imageSource, generarCodigoImg());
		imageResource.setCacheTime(0);
		return imageResource;
	}
	
	static byte[] imagenResultEvidencia= null;
	public static StreamResource byteToImgEvidencia(byte[] imagen) {
		
		if(imagen == null) {
			imagenResultEvidencia = imgToByte(new File(path+"/images/NO_IMAGE.png"));
		}else {
			imagenResultEvidencia = imagen;
		}
		
		StreamResource.StreamSource imageSource = null;
		imageSource = new StreamResource.StreamSource() {
			private static final long serialVersionUID = 1L;

			@Override
			public InputStream getStream() {
				return new ByteArrayInputStream(imagenResultEvidencia);
			}
		};
		StreamResource imageResource = new StreamResource(imageSource, generarCodigoImg());
		imageResource.setCacheTime(0);
		return imageResource;
	}
	
	
	public static StreamResource byteToImgUser(byte[] imagen) {
		StreamResource.StreamSource imageSource = null;
		imageSource = new StreamResource.StreamSource() {
			private static final long serialVersionUID = 1L;

			@Override
			public InputStream getStream() {
				return new ByteArrayInputStream(imagen);
			}
		};
		StreamResource imageResource = new StreamResource(imageSource, generarCodigoImg());
		imageResource.setCacheTime(0);
		return imageResource;
	}
	
	//OBTENER LA URL DE UNA IMG
	public String getImageUrl(Image image) {
	
		URI location = UI.getCurrent().getPage().getLocation();
		String scheme = location.getScheme();
		String authority = location.getAuthority();
		String contextPath = VaadinServlet.getCurrent().getServletContext().getContextPath();
		Integer uiId = image.getUI().getUIId();
		String cid = image.getConnectorId();
		String filename = ((ConnectorResource) image.getSource()).getFilename();
		
		return scheme + "://" + authority + contextPath + "/APP/connector/" + uiId + "/" + cid + "/source/" + filename;
	}
	
	public static String generarCodigoImg(){ 
        String photoCode; 
        Calendar fecha_sistema = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = dateFormat.format(fecha_sistema.getTime());
        photoCode = "IMG-" +date+".jpg";   
        return photoCode;
     }
	
	public static String generarCodigoXls(){ 
        String photoCode; 
        Calendar fecha_sistema = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = dateFormat.format(fecha_sistema.getTime());
        photoCode = "datos-" +date+".xls";   
        return photoCode;
     }

	public String base64Encoder(File file) {

		byte[] fileContent;
		String encodedString = null;
		try {
			fileContent = FileUtils.readFileToByteArray(file);
			encodedString = Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return "data:image/jpeg;base64," + encodedString;
	}

}
