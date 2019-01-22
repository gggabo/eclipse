package controllers;

import java.io.Serializable;
import java.util.List;

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
        em.createQuery("select u from Usuario u").getResultList()
        );
	}
	
		
	
}
