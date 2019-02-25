package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Laboratorio;
import models.Rol;
import services.JPAService;

public class LabotatorioController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Laboratorio laboratorio) {
		JPAService.runInTransaction(em ->{
			em.persist(laboratorio);
			return null;
		});
	}
	
	public static void update (Laboratorio laboratorio) {
		JPAService.runInTransaction(em ->{
			em.merge(laboratorio);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Laboratorio> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select l from Laboratorio l").getResultList()
        );
	}
	
	static Laboratorio lab;
	public static  Laboratorio getSpecificRolById(long laboratorio) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select l from Laboratorio l where l.idLaboratorio = ?1");
			query.setParameter(1, laboratorio);

			if(query.getResultList().size()>0) {
				lab = (Laboratorio) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return lab;
		
	}
		
}
