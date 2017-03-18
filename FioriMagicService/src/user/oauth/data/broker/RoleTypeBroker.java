package user.oauth.data.broker;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import user.oauth.jpa.model.AliasDomain;
import com.convergent.model.*;
import user.oauth.persistence.util.EMF;

public class RoleTypeBroker {

	public void addRole(RoleType role) {

		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			em.persist(role);
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
	public List<RoleType> showAllRoles() {
		EntityManager em = EMF.createEntityManager();
		List<RoleType> list = null;
		List<RoleType> detachedList = null;
		try {
			Query query = em.createQuery("SELECT R FROM RoleType R ");
			list = (List<RoleType>) query.getResultList();
			detachedList = new ArrayList<RoleType>();
			for (RoleType role: list) {
				detachedList.add(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return detachedList;
	}
	

	@SuppressWarnings({ "unchecked" })
	public RoleType getRoleById(Long id) {
		EntityManager em = EMF.createEntityManager();
		List<RoleType> list = null;
		RoleType role = null;
		try {
			Query query = em.createQuery("SELECT R FROM RoleType R where R.roleTypeId = :id ");
			query.setParameter("id", id);
			list = (List<RoleType>) query.getResultList();
			role = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return role;
	}
			
	@SuppressWarnings({ "unchecked" })
	public RoleType getRoleByName(String name) {
		EntityManager em = EMF.createEntityManager();
		List<RoleType> list = null;
		RoleType role = new RoleType();
		try {
			Query query = em.createQuery("SELECT R FROM RoleType R where R.roleName = :rname ");
			query.setParameter("rname", name);
			list = (List<RoleType>) query.getResultList();
			if ( list.size() > 0 ) {
				role = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return role;
	}
	
	public void updateRole(RoleType obj) {
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			RoleType role = (RoleType) em.find(RoleType.class, obj.getRoleTypeId());
			role.setRoleName(obj.getRoleName());
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

	public void deleteRole(Long roleId) {
		
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			RoleType role = (RoleType) em.find(RoleType.class, roleId);
			em.remove(role);
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

