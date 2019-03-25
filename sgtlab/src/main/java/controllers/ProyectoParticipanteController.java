package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Materia;
import models.Proyecto;
import models.ProyectoParticipante;
import models.Rol;
import models.TipoProyecto;
import models.Usuario;
import services.JPAService;

public class ProyectoParticipanteController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (ProyectoParticipante proyecto) {
		JPAService.runInTransaction(em ->{
			em.persist(proyecto);
			return null;
		});
	}
	
	public static void update (ProyectoParticipante proyecto) {
		JPAService.runInTransaction(em ->{
			em.merge(proyecto);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<ProyectoParticipante> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select p from ProyectoParticipante p").getResultList()
        );
	}
	
	static	ProyectoParticipante p;
	public static  ProyectoParticipante getSpecificMateriaById(long proyecto) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select p from ProyectoParticipante p where p.idProyectoParticipante = ?1");
			query.setParameter(1, proyecto);

			if(query.getResultList().size()>0) {
				p = (ProyectoParticipante) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return p;
		
	}
	
	/*@SuppressWarnings("unchecked")
	public static List<ProyectoParticipante> search(String searchField){
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select p from ProyectoParticipante p where p.estado = 1 and"
					+ " nombre LIKE ?1) "
					//+ "order by concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos))"
					);
			query.setParameter(1, "%" + searchField + "%");
			
			return query.getResultList();
		});
	}*/
	
	
		
}
