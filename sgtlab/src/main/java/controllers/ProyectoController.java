package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Materia;
import models.Proyecto;
import models.ProyectoParticipante;
import services.JPAService;

public class ProyectoController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Proyecto proyecto) {
		JPAService.runInTransaction(em ->{
			em.persist(proyecto);
			return null;
		});
	}
	
	public static void update (Proyecto proyecto) {
		JPAService.runInTransaction(em ->{
			em.merge(proyecto);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Proyecto> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select p from Proyecto p").getResultList()
        );
	}
	
	static	Proyecto p;
	public static  Proyecto getSpecificMateriaById(long proyecto) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select p from Proyecto p where p.idProyecto = ?1");
			query.setParameter(1, proyecto);

			if(query.getResultList().size()>0) {
				p = (Proyecto) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return p;
		
	}
	
	static boolean t;
	public static boolean userIsResponsable(long idUsuario, Proyecto proyecto) {	
		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select 1 from ProyectoParticipante p where p.usuario.id = ?1 and p.proyecto = ?2 and p.responsable = true");
			query.setParameter(1, idUsuario);
			query.setParameter(2, proyecto);

			if(query.getResultList().size()>0) {
				t = true;
			}else {
				t = false;
			}
			return null;
		}
        ); 
		return t;
	}
	
	static boolean codDB;
	public static boolean codeContainsDB(String cod) {	
		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select 1 from Proyecto p where p.codigo = ?1");
			query.setParameter(1, cod);

			if(query.getResultList().size()>0) {
				codDB = true;
			}else {
				codDB = false;
			}
			return null;
		}
        ); 
		return codDB;
	}
	
	@SuppressWarnings("unchecked")
	public static List<ProyectoParticipante> getProyectoByUser(long idUser, String busc){
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select pp from ProyectoParticipante pp where pp.proyecto.codigo LIKE ?1 and pp.usuario.id =?2 and pp.proyecto.estado = 1 ORDER BY proyecto.idProyecto DESC");
			query.setParameter(1,"%" + busc + "%");
			query.setParameter(2,idUser);
			
			return query.getResultList();  
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<ProyectoParticipante> getProyectoById(long idProyecto){
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select pp from ProyectoParticipante pp where pp.proyecto.idProyecto =?1 ");
			query.setParameter(1,idProyecto);
			return query.getResultList();
		});
	}
	
	public static List<Materia> getAllMateriaByProject(long idProyecto) {		
		JPAService.runInTransaction(em ->{
			p = em.find(Proyecto.class, idProyecto);
			p.getMaterias().size();
			
			return null;
			
		}); 
		
		return p.getMaterias();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Proyecto> search(String searchField){
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select p from Proyecto p where p.estado = 1 and"
					+ " nombre LIKE ?1) "
					//+ "order by concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos))"
					);
			query.setParameter(1, "%" + searchField + "%");
			
			return query.getResultList();
		});
	}
	
	
		
}
