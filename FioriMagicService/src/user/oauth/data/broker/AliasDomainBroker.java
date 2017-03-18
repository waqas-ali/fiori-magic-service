package user.oauth.data.broker;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import user.oauth.app.helpers.PMFSingleton;
import user.oauth.jpa.model.AliasDomain;
import user.oauth.jpa.model.IdentityProvider;
import user.oauth.persistence.util.EMF;

public class AliasDomainBroker {

	public void saveAliasDomain(AliasDomain obj) {

		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			em.persist(obj);
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
	public List<AliasDomain> retrieveAliasDomains() {

		EntityManager em = EMF.createEntityManager();
		List<AliasDomain> list = null;
		List<AliasDomain> detachedList = null;

		try {
			
			Query query = em.createQuery("SELECT A FROM AliasDomain A " +
					"WHERE A.isPrimary = :value");
			query.setParameter("value", false);
			list = (List<AliasDomain>) query.getResultList();
			detachedList = new ArrayList<AliasDomain>();
			for (AliasDomain alias: list) {
				detachedList.add(alias);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return detachedList;

	}

	@SuppressWarnings({ "unchecked" })
	public List<AliasDomain> getAliasByURL(String domainURL) {

		EntityManager em = EMF.createEntityManager();
		List<AliasDomain> list = null;
		try {
			Query query = em.createQuery("SELECT A FROM AliasDomain A WHERE A.url = :aurl");
			query.setParameter("aurl", domainURL);
			list = (List<AliasDomain>) query.getResultList();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			em.close();
		}
		return list;
	}
	
	@SuppressWarnings({"unchecked"})
	public AliasDomain getAliasByName(String aliasName) {

		EntityManager em = EMF.createEntityManager();
		List<AliasDomain> list = null;
		AliasDomain aDomain = null;
		try {
			Query query = em.createQuery("SELECT A FROM AliasDomain A WHERE A.aliasName = :aname");
			query.setParameter("aname", aliasName);
			list = (List<AliasDomain>) query.getResultList();
			aDomain = list.get(0);
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			em.close();
		}
		return aDomain;
	}
	
	@SuppressWarnings({"unchecked"})
	public AliasDomain getAliasByKey(Long key) {

		EntityManager em = EMF.createEntityManager();
		List<AliasDomain> list = null;
		AliasDomain aDomain = null;
		try {
			Query query = em.createQuery("SELECT A FROM AliasDomain A WHERE A.key = :akey");
			query.setParameter("akey", key);
			list = (List<AliasDomain>) query.getResultList();
			aDomain = list.get(0);
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			em.close();
		}
		return aDomain;
	}
	
	public void updateAliasByKey(AliasDomain obj) {
		
		
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			AliasDomain alias = (AliasDomain) em.find(AliasDomain.class, obj.getKey());
			alias.setAliasName(obj.getAliasName());
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

	public void deleteAliasByKey(Long key) {
		
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			AliasDomain alias = (AliasDomain) em.find(AliasDomain.class, key);
			em.remove(alias);
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
	
	public void deleteAliasByURL(String url) {
		
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			List<AliasDomain> list = getAliasByURL(url);
			for ( AliasDomain alias : list) {
				deleteAliasByKey(alias.getKey());
			}
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

	/*
	@SuppressWarnings("rawtypes")
	public void deleteAll() {
		PersistenceManager pm = PMFSingleton.get().getPersistenceManager();
		Query query = pm.newQuery(AliasDomain.class);
		List list = null;
		Transaction txn = null;
		try {
			txn = pm.currentTransaction();
			txn.begin();
			list = (List) query.execute();
			pm.deletePersistentAll(list);
			txn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( txn.isActive()) {
				txn.rollback();
			}
			query.closeAll();
			pm.close();
		}
	}
	*/
	
}

