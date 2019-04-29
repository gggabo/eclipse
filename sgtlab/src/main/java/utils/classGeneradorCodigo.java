
package utils;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import controllers.ProyectoController;


public class classGeneradorCodigo implements Serializable {
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
	
	public static String generarCodigoDocs(){
		String codDoc;
		Calendar fecha_sistema = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String date = dateFormat.format(fecha_sistema.getTime());
		codDoc=date; 
		return codDoc;
	} 
	
	public static String genCode() {
		SecureRandom random = new SecureRandom();
		String code = null;
		Boolean exist = true;
		while(exist) {
			code = new BigInteger(25, random).toString(32);	
			if(!ProyectoController.codeContainsDB(code)) {
				exist = false;
			}
		}
		
		return code;
	}

	
}
