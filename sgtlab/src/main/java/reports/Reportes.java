package reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import services.conexionDB;
import utils.classGeneradorCodigo;
 
public class Reportes {
	
	//public void generarInformeProyecto(long idProyecto) {	
		private classGeneradorCodigo generador=new classGeneradorCodigo();
		private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	    private String rutaReporte = basepath + "/REPORTES/";
	    //private CallableStatement st; 
		//private ResultSet rs ;
	     @SuppressWarnings("deprecation") 
		public void generarInformeProyecto(long idProyecto, String cod){
		    	final Map<String, Object> map = new HashMap<String, Object>();                
		    	map.put("IN_IDPROYECTO",idProyecto);
		    	map.put("IN_CODIGO",cod);
			try {   JasperPrint print = JasperFillManager.fillReport(rutaReporte+"rpt_project.jasper", map, conexionDB.getConnection());
		    		JRPdfExporter exporter = new JRPdfExporter();
		    	    final ByteArrayOutputStream output=new ByteArrayOutputStream();
		    	  
		    	    exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		    	    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
		    	    exporter.exportReport();
		    	    output.flush();
		    	    output.close(); 
		    	    StreamResource.StreamSource source = new StreamResource.StreamSource() {
						private static final long serialVersionUID = 1L;

										public InputStream getStream() { 
		    	                           byte[] b = null;
		    	                            b=output.toByteArray();
		    	                        return new ByteArrayInputStream(b);
		    	                        }
		    	                };
		    	    	StreamResource resource = new StreamResource(source, "Informe Proyecto-"+generador.generarCodigoReportes()+".pdf");
		    	    		resource.setMIMEType("application/pdf");
		    	    	Embedded e = new Embedded();
		    	    		e.setMimeType("application/pdf");
		    	    		e.setType(Embedded.TYPE_BROWSER);
		    	    		e.setSizeFull();
		    	    		e.setSource(resource); 
		    	    	Window w = new Window("Informe trazabilidad");
		    	    		w.setSizeFull();
		    	    		w.setWidth(w.getWidth()-8,Unit.PERCENTAGE);
		                    w.setHeight(w.getHeight()-8,Unit.PERCENTAGE);
		    	    		w.center();
		    	    		w.setContent(e);
		    	    		w.setResizable(false);
		    	    		UI.getCurrent().addWindow(w);   
		    	} catch (JRException | IOException ex) {
		            ex.printStackTrace();
		         }   
		    }
	
}
