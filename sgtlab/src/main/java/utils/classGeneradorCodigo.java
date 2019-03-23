
package utils;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;


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
	
	public static String genCode() {
		SecureRandom random = new SecureRandom();
		String code = new BigInteger(80, random).toString(32);		
		return code;
	}

	
}
