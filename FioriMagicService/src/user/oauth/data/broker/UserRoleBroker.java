package user.oauth.data.broker;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import com.convergent.model.*;

import user.oauth.persistence.util.EMF;

public class UserRoleBroker {

	public void addUserRole(UserRole userRole) {

		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			em.persist(userRole);
			etxn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (etxn.isActive()) {
				etxn.rollback();
			}
			em.close();
		}
	}

	@SuppressWarnings({ "unchecked" })
	public List<UserRole> showAllUserRoles() {
		EntityManager em = EMF.createEntityManager();
		List<UserRole> list = null;
		List<UserRole> detachedList = null;
		try {
			Query query = em.createQuery("SELECT UR FROM UserRole UR ");
			list = (List<UserRole>) query.getResultList();
			detachedList = new ArrayList<UserRole>();
			for (UserRole userRole: list) {
				detachedList.add(userRole);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return detachedList;
	}
	

	@SuppressWarnings({ "unchecked" })
	public UserRole getUserRoleById(Long urid) {
		EntityManager em = EMF.createEntityManager();
		List<UserRole> list = null;
		UserRole uRole = null;
		try {
			Query query = em.createQuery("SELECT UR FROM UserRole UR where UR.userRoleId = :id ");
			query.setParameter("id", urid);
			list = (List<UserRole>) query.getResultList();
			uRole = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return uRole;
	}
			
	@SuppressWarnings({ "unchecked" })
	public UserRole getUserRoleByUserId(Long id) {
		EntityManager em = EMF.createEntityManager();
		List<UserRole> list = null;
		UserRole uRole = new UserRole();
		try {
			Query query = em.createQuery("SELECT UR FROM UserRole UR where UR.userId = :uid ");
			query.setParameter("uid", id);
			list = (List<UserRole>) query.getResultList();
			if ( list.size() > 0 ) {
				uRole = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return uRole;
	}
	
	@SuppressWarnings({ "unchecked" })
	public UserRole getUserRoleByRoleType(Long id) {
		EntityManager em = EMF.createEntityManager();
		List<UserRole> list = null;
		UserRole uRole = new UserRole();
		try {
			Query query = em.createQuery("SELECT UR FROM UserRole UR where UR.roleTypeId = :tid ");
			query.setParameter("tid", id);
			list = (List<UserRole>) query.getResultList();
			if ( list.size() > 0 ) {
				uRole = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return uRole;
	}
	
	public void updateUserRole(UserRole obj) {
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			UserRole uRole = (UserRole) em.find(UserRole.class, obj.getUserRoleId());
			uRole.setUserId(obj.getUserId());
			uRole.setRoleTypeId(obj.getRoleTypeId());
			etxn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (etxn.isActive()) {
				etxn.rollback();
			}
			em.close();
		}
	}

	public void deleteUserRole(Long roleId) {
		
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			UserRole uRole = (UserRole) em.find(UserRole.class, roleId);
			em.remove(uRole);
			etxn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (etxn.isActive()) {
				etxn.rollback();
			}
			em.close();
		}
	}
	
}

