package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Documento;
import services.JPAService;

public class DocumentoController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Documento documento) {
		JPAService.runInTransaction(em ->{
			em.persist(documento);
			return null;
		});
	}
	
	public static void update (Documento documento) {
		JPAService.runInTransaction(em ->{
			em.merge(documento);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Documento> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select d from Documento d where d.estado = 1").getResultList()
        );
	}
	
	static	Documento doc;
	public static  Documento getSpecificRolById(long documento) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select d from Documento d where d.idDocumento = ?1 and d.estado = 1");
			query.setParameter(1, documento);

			if(query.getResultList().size()>0) {
				doc = (Documento) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return doc;
		
	}
	
	@SuppressWarnings("unchecked")
	public static List<Documento> search(String searchField){
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select d from Documento d where d.estado = 1 and"
					+ " d.nombre LIKE ?1) "
					);
			query.setParameter(1, "%" + searchField + "%");
			
			return query.getResultList();
		});
	}
		
}
