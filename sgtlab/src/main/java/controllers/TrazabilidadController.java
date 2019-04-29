package controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import models.Material;
import models.Proyecto;
import models.Trazabilidad;
import models.TrazabilidadEquipo;
import models.TrazabilidadMedioCultivo;
import models.TrazabilidadReactivo;
import models.Usuario;
import services.JPAService;

public class TrazabilidadController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Trazabilidad trazabilidad) {
		JPAService.runInTransaction(em ->{
			em.persist(trazabilidad);
			return null;
		});
	}
	
	public static void update (Trazabilidad trazabilidad) {
		JPAService.runInTransaction(em ->{
			em.merge(trazabilidad);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Trazabilidad> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select t from Trazabilidad t where t.estado=1").getResultList()
        );
	}
	
	static Trazabilidad trazabilidad;
	public static  Trazabilidad getSpecificTrazaById(long idTrazabilidad) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select t from Trazabilidad t where t.idTrazabilidad = ?1");
			query.setParameter(1, idTrazabilidad);

			if(query.getResultList().size()>0) {
				trazabilidad = (Trazabilidad) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return trazabilidad;
		
	}
	
	static Proyecto pr;
	public static List<Trazabilidad> getAllTrazasByProyecto(long idProyecto) {		
		JPAService.runInTransaction(em ->{
			pr = em.find(Proyecto.class, idProyecto);
			pr.getTrazabilidad().size();		
			return null;	
		}); 	
		return pr.getTrazabilidad();
	}
	
	public static List<TrazabilidadReactivo> getAllReactivoTraza(long idProyecto) {		
		JPAService.runInTransaction(em ->{
			trazabilidad = em.find(Trazabilidad.class, idProyecto);
			trazabilidad.getTrazabilidadReactivos().size();		
			return null;	
		}); 	
		return trazabilidad.getTrazabilidadReactivos();
	}
	
@SuppressWarnings("unchecked")
public static List<TrazabilidadReactivo> getReactivoTrazaByLab(long idTrazabilidad, long laboratorio) {		
		
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select tr from TrazabilidadReactivo tr where tr.trazabilidad.idTrazabilidad = ?1"
					+ " and tr.reactivo.laboratorio.idLaboratorio = ?2"
					);
			query.setParameter(1, idTrazabilidad);
			query.setParameter(2, laboratorio);
			
			return query.getResultList();
		});
	
	}

@SuppressWarnings("unchecked")
public static List<TrazabilidadEquipo> getEquipoTrazaByLab(long idTrazabilidad, long laboratorio) {		
		
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select te from TrazabilidadEquipo te where te.trazabilidad.idTrazabilidad = ?1"
					+ " and te.equipo.laboratorio.idLaboratorio = ?2"
					);
			query.setParameter(1, idTrazabilidad);
			query.setParameter(2, laboratorio);
			
			List<TrazabilidadEquipo> tEquipo = new ArrayList<>();
			tEquipo.addAll(query.getResultList());
			
			Iterator<TrazabilidadEquipo> iteratorTEquipo = tEquipo.iterator();
			TrazabilidadEquipo te;
			
			while(iteratorTEquipo.hasNext()) {
				te = iteratorTEquipo.next();
				te.getComponentes().size();
			}
			
			return tEquipo;
		});
	
}


public static List<Material> getMaterialTrazaByLab(long idTrazabilidad, long laboratorio) {		
	List<Material> listReturn = new ArrayList<>();
	JPAService.runInTransaction(em ->{
		trazabilidad = em.find(Trazabilidad.class, idTrazabilidad);
		trazabilidad.getMateriales().size();
		
		List<Material> listMat = new ArrayList<>();
		listMat.addAll(trazabilidad.getMateriales());
		Iterator<Material> iteratorMat = listMat.iterator();
		Material m;
		
		while(iteratorMat.hasNext()) {
			m = iteratorMat.next();
			if(m.getLaboratorio().getIdLaboratorio() == laboratorio) {
				listReturn.add(m);
			}
		}
		
		return null;	
	}); 	
	return listReturn;
	
}

@SuppressWarnings("unchecked")
public static List<TrazabilidadMedioCultivo> getMedioCultivoTrazaByLab(long idTrazabilidad, long laboratorio) {		
		
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select tmc from TrazabilidadMedioCultivo tmc where tmc.trazabilidad.idTrazabilidad = ?1"
					+ " and tmc.medioCultivo.laboratorio.idLaboratorio = ?2"
					);
			query.setParameter(1, idTrazabilidad);
			query.setParameter(2, laboratorio);
			
			return query.getResultList();
		});
	
	}

	public static List<Usuario> getRevisoresByTraza(long idTraza) {		
		JPAService.runInTransaction(em ->{
			trazabilidad = em.find(Trazabilidad.class, idTraza);
			trazabilidad.getRevisor().size();		
			return null;	
		}); 	
		return trazabilidad.getRevisor();
	}
	
	
	
/*	public static boolean DBcontainsCodReactivo(String cod) {
		if(!JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select 1 from Reactivo r where r.codigo = ?1 and (r.estado = 1 or r.estado = 0)");
			query.setParameter(1, cod);
			//System.out.println(query.getResultList());
			return query.getResultList();
		}).isEmpty()) 
			return true;
		else 
			return false;
	}
	
	static Laboratorio lab;
	public static List<Reactivo> getAllReactiveByLaboratory(long idLab) {		
		JPAService.runInTransaction(em ->{
			lab = em.find(Laboratorio.class, idLab);
			lab.getReactivos().size();
			
			return null;
			
		}); 
		
		return lab.getReactivos();
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Reactivo> searchReactiveByLaboratory(Laboratorio laboratorio,String searchField) {		
		
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select r from Reactivo r where laboratorio = ?1 and r.estado = 1 "
					+ "and (r.nombre LIKE ?2 or r.codigo LIKE ?2)");
				//	+ "concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos) LIKE ?1 "
				//	+ "or cedula LIKE ?1 "
				//	+ "order by concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos)");
			query.setParameter(1, laboratorio);
			query.setParameter(2, "%" + searchField + "%");
			
			return query.getResultList();
		});
	
	}*/
	
}
