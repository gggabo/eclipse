package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Componente;
import models.Equipo;
import models.Laboratorio;
import services.JPAService;

public class ComponenteController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Componente componente) {
		JPAService.runInTransaction(em ->{
			em.persist(componente);
			return null;
		});
	}
	
	public static void update (Componente componente) {
		JPAService.runInTransaction(em ->{
			em.merge(componente);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Componente> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select c from Componente c where c.estado=1").getResultList()
        );
	}
	
	static Componente comp;
	public static Componente getSpecificReactivoById(long idComponente) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select c from Componente c where c.idComponente = ?1");
			query.setParameter(1, idComponente);

			if(query.getResultList().size()>0) {
				comp = (Componente) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		return comp;
	}
	
	/*public static boolean DBcontainsCodComponente(String cod) {
		if(!JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select 1 from Equipo e where e.codigo = ?1 and (e.estado = 1 or e.estado = 0)");
			query.setParameter(1, cod);
			//System.out.println(query.getResultList());
			return query.getResultList();
		}).isEmpty()) 
			return true;
		else 
			return false;
	}*/
	
	static Equipo eq;
	public static List<Componente> getAllComponentByEquipo(long idEquipo) {		
		JPAService.runInTransaction(em ->{
			eq = em.find(Equipo.class, idEquipo);
			eq.getComponentes().size();
			
			return null;
			
		}); 
		
		return eq.getComponentes();
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Componente> searchComponenteByEquipo(Equipo eq) {		
		
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select c from Componente c where equipo = ?1 and estado = 1");
			query.setParameter(1, eq);
			
			return query.getResultList();
		});
	
	}
	
}
