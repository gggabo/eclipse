package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Laboratorio;
import models.MedioCultivo;
import services.JPAService;

public class MedioCultivoController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (MedioCultivo medioCultivo) {
		JPAService.runInTransaction(em ->{
			em.persist(medioCultivo);
			return null;
		});
	}
	
	public static void update (MedioCultivo medioCultivo) {
		JPAService.runInTransaction(em ->{
			em.merge(medioCultivo);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Laboratorio> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select mc from MedioCultivo mc where mc.estado=1").getResultList()
        );
	}
	
	static MedioCultivo mc;
	public static  MedioCultivo getSpecificReactivoById(long idMedioCultivo) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select mc from MedioCultivo mc where mc.idMedioCultivo = ?1");
			query.setParameter(1, idMedioCultivo);

			if(query.getResultList().size()>0) {
				mc = (MedioCultivo) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return mc;
		
	}
	
	public static boolean DBcontainsCodMedioCultivo(String cod) {
		if(!JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select 1 from MedioCultivo mc where mc.codigo = ?1 and (mc.estado = 1 or mc.estado = 0)");
			query.setParameter(1, cod);
			//System.out.println(query.getResultList());
			return query.getResultList();
		}).isEmpty()) 
			return true;
		else 
			return false;
	}
	
	static Laboratorio lab;
	public static List<MedioCultivo> getAllReactiveByLaboratory(long idLab) {		
		JPAService.runInTransaction(em ->{
			lab = em.find(Laboratorio.class, idLab);
			lab.getMediosCultivos().size();
			
			return null;
			
		}); 
		
		return lab.getMediosCultivos();
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<MedioCultivo> searchMedioCultivoByLaboratory(Laboratorio laboratorio,String searchField) {		
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select mc from MedioCultivo mc where laboratorio = ?1 and mc.estado = 1 "
					+ "and (mc.nombre LIKE ?2 or mc.codigo LIKE ?2)");
				//	+ "concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos) LIKE ?1 "
				//	+ "or cedula LIKE ?1 "
				//	+ "order by concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos)");
			query.setParameter(1, laboratorio);
			query.setParameter(2, "%" + searchField + "%");
			
			return query.getResultList();
		});
	
	}
	
}
