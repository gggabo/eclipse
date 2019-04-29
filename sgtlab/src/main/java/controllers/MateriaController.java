package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Materia;
import services.JPAService;

public class MateriaController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Materia materia) {
		JPAService.runInTransaction(em ->{
			em.persist(materia);
			return null;
		});
	}
	
	public static void update (Materia materia) {
		JPAService.runInTransaction(em ->{
			em.merge(materia);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Materia> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select m from Materia m").getResultList()
        );
	}
	
	static	Materia mat;
	public static  Materia getSpecificMateriaById(long materia) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select m from Materia m where m.idMateria = ?1");
			query.setParameter(1, materia);

			if(query.getResultList().size()>0) {
				mat = (Materia) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return mat;
		
	}
	
	@SuppressWarnings("unchecked")
	public static List<Materia> search(String searchField){
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("SELECT m from Materia m where m.estado = 1 and"
					+ " nombre LIKE ?1) "
					//+ "order by concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos))"
					);
			query.setParameter(1, "%" + searchField + "%");
			
			return query.getResultList();
		});
	}
	
	
		
}
