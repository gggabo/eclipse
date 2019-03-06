package utils;

import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import controllers.RolController;
import models.Rol;
import models.Usuario;

public class readXlsUsers implements Serializable {
	private static final long serialVersionUID = 1L;
	private File file;
	private List<Usuario> listUsuariosImport = new ArrayList<>();
	
	public readXlsUsers() {
		
	}
	
	public readXlsUsers(File file) {
		super();
		this.file = file;
	}

	public List<Usuario> getXlsUsersResultList() {
		Usuario user;
		Row row;
		Cell cell;
		int i =0;
		List<Rol> roles = new ArrayList<>();
		roles.add(RolController.getSpecificRolById(3L));
		try {
			FileInputStream xls = new FileInputStream(file);

			HSSFWorkbook workbook = new HSSFWorkbook(xls);
		    
		    HSSFSheet sheet = workbook.getSheetAt(1);
		    
		    Iterator<Row> rowIterator = sheet.iterator();

		    endList:
		    while(rowIterator.hasNext()) {//recorrer fila
		    	row = rowIterator.next();
		    	
		    	user = new Usuario();
		    	i = 0;
		    	
		    	Iterator<Cell> cellIterator = row.cellIterator();
		    	
		    	while(cellIterator.hasNext()) {//recorrer celda
		    		cell = cellIterator.next();
		    		
		    		switch(cell.getCellType()){
		    			    			
		    		case FORMULA: 
		    			if(cell.getCachedFormulaResultType()==CellType.STRING) {
		    				 
		    				if(cell.getStringCellValue().equals("0000000000")) {
		    					rowIterator.remove();
			    				break endList;
			    			}else {
			    				
			    				//System.out.print(cell.getStringCellValue() + " | ");
			    				
			    				if(i==0) {
			    					user.setCedula(cell.getStringCellValue());
			    					user.setClave(DigestUtils.sha1Hex(cell.getStringCellValue()));
			    				}else if(i==1) {
			    					user.setApellido_paterno(cell.getStringCellValue());
			    				}else if(i==2) {
			    					user.setApellido_materno(cell.getStringCellValue());
			    				}else if(i==3) {
			    					user.setNombre_uno(cell.getStringCellValue());
			    				}else if(i==4) {
			    					user.setNombre_dos(cell.getStringCellValue());
			    				}else if(i==5) {
			    					user.setNombre_usuario(cell.getStringCellValue());
			    					user.setEstado(1);
			    					user.setCorreo("");
			    					user.setTelefono("");
			    					user.setRoles(roles);
			    				}
			    				
			    				i++;
			    			}	
		    			}
		    			break;
		    			
					default:
						break;
		    		}
		   			    		
		    	}//fin recorrer celda
		  
		    	//System.out.println();
		    	listUsuariosImport.add(user);
		    	
		    }//fin recorrer fila
		    //System.out.println(listUsuariosImport);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return listUsuariosImport;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
	
}
