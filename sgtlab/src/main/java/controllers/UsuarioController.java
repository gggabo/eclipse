package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Usuario;
import services.JPAService;

public class UsuarioController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Usuario usuario) {
		JPAService.runInTransaction(em ->{
			em.persist(usuario);
			return null;
		});
	}
	
	public static void update (Usuario usuario) {
		JPAService.runInTransaction(em ->{
			em.merge(usuario);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Usuario> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select u from Usuario u where u.estado = 1").getResultList()
        );
	}
	
	public static boolean DBcontainsUser(String cedula) {
		if(!JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select 1 from Usuario u where u.Cedula = ?1 and (u.estado = 1 or u.estado = 0)");
			query.setParameter(1, cedula);
			//System.out.println(query.getResultList());
			return query.getResultList();
		}).isEmpty()) 
			return true;
		else 
			return false;
	}
	
		
	
}
