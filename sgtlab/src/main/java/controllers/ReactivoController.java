package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Laboratorio;
import models.Reactivo;
import services.JPAService;

public class ReactivoController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Reactivo reactivo) {
		JPAService.runInTransaction(em ->{
			em.persist(reactivo);
			return null;
		});
	}
	
	public static void update (Reactivo reactivo) {
		JPAService.runInTransaction(em ->{
			em.merge(reactivo);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Laboratorio> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select r from Reactivo r").getResultList()
        );
	}
	
	static Reactivo react;
	public static  Reactivo getSpecificReactivoById(long idReactivo) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select r from Reactivo r where r.idReactivo = ?1");
			query.setParameter(1, idReactivo);

			if(query.getResultList().size()>0) {
				react = (Reactivo) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return react;
		
	}
	
	static Laboratorio lab;
	public static List<Reactivo> getAllReactiveByLaboratory(long idLab) {		
		JPAService.runInTransaction(em ->{
			lab = em.find(Laboratorio.class, idLab);
			lab.getReactivos().size();
		    /*em.createQuery("select r from Reactivo r where r.idLaboratorio = ?1")
			.setParameter(1, idLaborario)
			.getResultList()*/
			
			return null;
			
		}); 
		
		return lab.getReactivos();
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Reactivo> searchReactiveByLaboratory(Laboratorio laboratorio,String searchField) {		
	/*	return JPAService.runInTransaction(em ->
			lab = em.find(Laboratorio.class, idLab);
			lab.getReactivos().size();
		    em.createQuery("select r from Reactivo r where Laboratorio = ?1")
			.setParameter(1, laboratorio)
			.getResultList()
		); */ 
		
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select r from Reactivo r where laboratorio = ?1 "
					+ "and r.nombre LIKE ?2");
				//	+ "concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos) LIKE ?1 "
				//	+ "or cedula LIKE ?1 "
				//	+ "order by concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos)");
			query.setParameter(1, laboratorio);
			query.setParameter(2, "%" + searchField + "%");
			
			return query.getResultList();
		});
	
		
		//return lab.getReactivos();
	}
	
	
		
}
