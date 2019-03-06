package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Equipo;
import models.Laboratorio;
import models.Reactivo;
import services.JPAService;

public class EquipoController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Equipo equipo) {
		JPAService.runInTransaction(em ->{
			em.persist(equipo);
			return null;
		});
	}
	
	public static void update (Equipo equipo) {
		JPAService.runInTransaction(em ->{
			em.merge(equipo);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Equipo> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select e from Equipo e where e.estado=1").getResultList()
        );
	}
	
	static Equipo eq;
	public static Equipo getSpecificReactivoById(long idEquipo) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select e from Equipo e where e.idEquipo = ?1");
			query.setParameter(1, idEquipo);

			if(query.getResultList().size()>0) {
				eq = (Equipo) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		return eq;
	}
	
	public static boolean DBcontainsCodEquipo(String cod) {
		if(!JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select 1 from Equipo e where e.codigo = ?1 and (e.estado = 1 or e.estado = 0)");
			query.setParameter(1, cod);
			//System.out.println(query.getResultList());
			return query.getResultList();
		}).isEmpty()) 
			return true;
		else 
			return false;
	}
	
	static Laboratorio lab;
	public static List<Equipo> getAllReactiveByLaboratory(long idLab) {		
		JPAService.runInTransaction(em ->{
			lab = em.find(Laboratorio.class, idLab);
			lab.getEquipos().size();
			
			return null;
			
		}); 
		
		return lab.getEquipos();
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Equipo> searchEquipoByLaboratory(Laboratorio laboratorio,String searchField) {		
		
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select e from Equipo e where laboratorio = ?1 and e.estado = 1 "
					+ "and (e.nombre LIKE ?2 or e.codigo LIKE ?2)");
				//	+ "concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos) LIKE ?1 "
				//	+ "or cedula LIKE ?1 "
				//	+ "order by concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos)");
			query.setParameter(1, laboratorio);
			query.setParameter(2, "%" + searchField + "%");
			
			return query.getResultList();
		});
	
	}
	
}
