package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Rol;
import services.JPAService;

public class RolController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Rol rol) {
		JPAService.runInTransaction(em ->{
			em.persist(rol);
			return null;
		});
	}
	
	public static void update (Rol rol) {
		JPAService.runInTransaction(em ->{
			em.merge(rol);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Rol> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select r from Rol r").getResultList()
        );
	}
	
	static	Rol us;
	public static  Rol getSpecificRolById(long rol) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select r from Rol r where r.idRol = ?1");
			query.setParameter(1, rol);

			if(query.getResultList().size()>0) {
				us = (Rol) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return us;
		
	}
	
	/*static Rol rol;
	public static List<Laboratorio> getAllLabsByRol(long idLab) {		
		JPAService.runInTransaction(em ->{
			rol = em.find(Rol.class, idLab);
			rol.getLaboratorios().size();
			
			return null;
			
		}); 
		
		return rol.getLaboratorios();
	}
		*/
}
