package controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Query;
import models.Unidad;
import services.JPAService;

public class UnidadController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Unidad unidad) {
		JPAService.runInTransaction(em ->{
			em.persist(unidad);
			return null;
		});
	}
	
	public static void update (Unidad unidad) {
		JPAService.runInTransaction(em ->{
			em.merge(unidad);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Unidad> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select u from Unidad u").getResultList()
        );
	}
	
	static Unidad unid;
	public static Unidad getSpecificUnidadById(long unidad) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select u from Unidad u where u.idUnidad = ?1");
			query.setParameter(1, unidad);

			if(query.getResultList().size()>0) {
				unid = (Unidad) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return unid;
		
	}
		
}
