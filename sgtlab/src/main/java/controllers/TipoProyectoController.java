package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.TipoProyecto;
import services.JPAService;

public class TipoProyectoController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (TipoProyecto tipoProyecto) {
		JPAService.runInTransaction(em ->{
			em.persist(tipoProyecto);
			return null;
		});
	}
	
	public static void update (TipoProyecto tipoProyecto) {
		JPAService.runInTransaction(em ->{
			em.merge(tipoProyecto);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<TipoProyecto> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select tp from TipoProyecto tp").getResultList()
        );
	}
	
	static	TipoProyecto tp;
	public static  TipoProyecto getSpecificMateriaById(long materia) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select tp from TipoProyecto tp where tp.idTipoProyecto = ?1");
			query.setParameter(1, materia);

			if(query.getResultList().size()>0) {
				tp = (TipoProyecto) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return tp;
		
	}
	
	@SuppressWarnings("unchecked")
	public static List<TipoProyecto> search(String searchField){
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select tp from TipoProyecto tp where tp.estado = 1 and"
					+ " nombre LIKE ?1) "
					//+ "order by concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos))"
					);
			query.setParameter(1, "%" + searchField + "%");
			
			return query.getResultList();
		});
	}
	
	
		
}
