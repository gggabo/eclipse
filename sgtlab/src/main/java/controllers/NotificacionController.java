package controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Query;

import models.Notificacion;
import services.JPAService;

public class NotificacionController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Notificacion notificacion) {
		JPAService.runInTransaction(em ->{
			em.persist(notificacion);
			return null;
		});
	}
	
	public static void update (Notificacion notificacion) {
		JPAService.runInTransaction(em ->{
			em.merge(notificacion);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Notificacion> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select n from Notificacion n").getResultList()
        );
	}
	
	@SuppressWarnings("unchecked")
	public static List<Notificacion> getNotificacionByIdUser(long idUser){
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("select n from Notificacion n where n.usuario.id =?1 and n.estado = 1 order by n.idNotificacion DESC");
			query.setParameter(1,idUser);
			return query.getResultList();
		});
	}
		
}
