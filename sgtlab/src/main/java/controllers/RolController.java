package controllers;

import java.io.Serializable;
import java.util.List;

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
	
		
}
