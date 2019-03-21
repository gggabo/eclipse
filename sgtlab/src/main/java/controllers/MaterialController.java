package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Laboratorio;
import models.Material;
import services.JPAService;

public class MaterialController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Material material) {
		JPAService.runInTransaction(em ->{
			em.persist(material);
			return null;
		});
	}
	
	public static void update (Material material) {
		JPAService.runInTransaction(em ->{
			em.merge(material);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Material> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select m from Material m where m.estado=1").getResultList()
        );
	}
	
	static Material mat;
	public static  Material getSpecificReactivoById(long idMaterial) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select m from Material m where m.idMaterial = ?1");
			query.setParameter(1, idMaterial);

			if(query.getResultList().size()>0) {
				mat = (Material) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return mat;
		
	}
	
	public static boolean DBcontainsCodMaterial(String cod) {
		if(!JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select 1 from Material m where m.codigo = ?1 and (m.estado = 1 or m.estado = 0)");
			query.setParameter(1, cod);
			return query.getResultList();
		}).isEmpty()) 
			return true;
		else 
			return false;
	}
	
	static Laboratorio lab;
	public static List<Material> getAllMaterialByLaboratory(long idLab) {		
		JPAService.runInTransaction(em ->{
			lab = em.find(Laboratorio.class, idLab);
			lab.getMateriales().size();
			
			return null;
			
		}); 
		
		return lab.getMateriales();
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Material> searchMaterialByLaboratory(Laboratorio laboratorio,String searchField) {		
		
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select m from Material m where m.laboratorio = ?1 and m.estado = 1 "
					+ "and (m.nombre LIKE ?2 or m.codigo LIKE ?2)");
			query.setParameter(1, laboratorio);
			query.setParameter(2, "%" + searchField + "%");
			
			return query.getResultList();
		});
	
	}
	
}
