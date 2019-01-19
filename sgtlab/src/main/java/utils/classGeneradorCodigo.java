
package utils;

import java.io.Serializable;
/**
 * @author Gabriel Gregorio Salvatierra Tumbaco
 * @author Carlos Alfredo Silva Villafuerte
 * @version 1.0
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class classGeneradorCodigo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String generarCodigoImg(){ 
        String photoCode; 
        Calendar fecha_sistema = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = dateFormat.format(fecha_sistema.getTime());
        photoCode = "IMG-" +date+".jpg";   
        return photoCode;
     }
	
	public String generarCodigoReportes(){
		String codPdf;
		Calendar fecha_sistema = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String date = dateFormat.format(fecha_sistema.getTime());
		codPdf=date;
		return codPdf;
	}
}
