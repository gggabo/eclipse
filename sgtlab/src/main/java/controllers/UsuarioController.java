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
        em.createQuery("select u from Usuario u where u.estado = 1 "
        		+ "order by concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos)").getResultList()
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
	
	static	Usuario us;
	public static  Usuario getSpecificUserDisable(String cedula) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select u from Usuario u where u.Cedula = ?1 and u.estado = 0");
			query.setParameter(1, cedula);

			if(query.getResultList().size()>0) {
				us = (Usuario) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return us;
	} 
		
	@SuppressWarnings("unchecked")
	public static List<Usuario> search(String searchField){
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("SELECT u from Usuario u where u.estado = 1 and"
					+ "(concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos) LIKE ?1 "
					+ "or cedula LIKE ?1) "
					//+ "order by concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos))"
					);
			query.setParameter(1, "%" + searchField + "%");
			
			return query.getResultList();
		});
	}
	
}
