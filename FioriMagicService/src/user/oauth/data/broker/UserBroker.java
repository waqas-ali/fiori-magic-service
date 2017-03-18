package user.oauth.data.broker;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import user.oauth.jpa.model.AliasDomain;
import com.convergent.model.*;
import user.oauth.persistence.util.EMF;

public class UserBroker {

	public void addUser(User user) {

		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			em.persist(user);
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
	public List<User> getAllUsers() {
		EntityManager em = EMF.createEntityManager();
		List<User> list = null;
		List<User> detachedList = null;
		try {
			Query query = em.createQuery("SELECT U FROM User U ");
			list = (List<User>) query.getResultList();
			detachedList = new ArrayList<User>();
			for (User user: list) {
				detachedList.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return detachedList;
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<User> getUsersByDomain(Long id) {
		EntityManager em = EMF.createEntityManager();
		List<User> list = null;
		List<User> detachedList = null;
		try {
			Query query = em.createQuery("SELECT U FROM User U where U.domainId = :id ");
			query.setParameter("id", id);
			list = (List<User>) query.getResultList();
			detachedList = new ArrayList<User>();
			for (User user: list) {
				detachedList.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return detachedList;
	}
	
	@SuppressWarnings({ "unchecked" })
	public User getUserById(Long id) {
		EntityManager em = EMF.createEntityManager();
		List<User> list = null;
		User user = null;
		try {
			Query query = em.createQuery("SELECT U FROM User U where U.userId = :id ");
			query.setParameter("id", id);
			list = (List<User>) query.getResultList();
			user = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return user;
	}
	
	@SuppressWarnings({ "unchecked" })
	public User getUserByLoginId(String LoginId) {
		EntityManager em = EMF.createEntityManager();
		List<User> list = null;
		User user = null;
		try {
			Query query = em.createQuery("SELECT U FROM User U where U.loginId = :id ");
			query.setParameter("id", LoginId);
			list = (List<User>) query.getResultList();
			user = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return user;
	}

		
	public void updateUser(User obj) {
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			User user = (User) em.find(User.class, obj.getUserId());
			user.setDomainId(obj.getDomainId());
			user.setLoginId(obj.getLoginId());
			user.setPassword(obj.getPassword());
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

	public void deleteUser(Long userId) {
		
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			User user = (User) em.find(User.class, userId);
			em.remove(user);
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
	public User authenticateUser(User user) {
		EntityManager em = EMF.createEntityManager();
		List<User> list = null;
		try {
			String password = user.getPassword();
			Query query = em.createQuery("SELECT U FROM User U " +
					"where U.loginId = :uid " +
					"and U.domainId = :did " +
					"and U.password = :pswd");
			query.setParameter("uid", user.getLoginId());
			query.setParameter("did", user.getDomainId());
			query.setParameter("pswd", password);
			list = (List<User>) query.getResultList();
			if ( list != null && list.size() >0 )
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return null;
	}
	
	public void updateUserPassword(Long userId, String newPassword) {
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			User user = (User) em.find(User.class, userId);
			user.setPassword(newPassword);
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

