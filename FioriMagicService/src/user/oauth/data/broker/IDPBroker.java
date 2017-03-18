package user.oauth.data.broker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.datanucleus.store.valuegenerator.UUIDHexGenerator;

import user.oauth.app.helpers.PMFSingleton;
import user.oauth.jpa.model.AliasDomain;
import user.oauth.jpa.model.IdentityProvider;
import user.oauth.jpa.model.OAuthLoginHistory;
import user.oauth.persistence.util.EMF;

public class IDPBroker {

	public void saveDomain(IdentityProvider obj) {

		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		AliasDomain alias = new AliasDomain(obj.getUrl(),obj.getDomainName());
		alias.setPrimary(true);
		String code = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		obj.setDomainIdCode(code);
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
		AliasDomainBroker broker = new AliasDomainBroker();
		broker.saveAliasDomain(alias);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List retrieveDomainList() {

		EntityManager em = EMF.createEntityManager();
		List<IdentityProvider> list = null;
		List<IdentityProvider> detachedList = null;

		try {
			Query query = em.createQuery("SELECT I FROM IdentityProvider I");
			list = (List<IdentityProvider>) query.getResultList();
			detachedList = new ArrayList<IdentityProvider>();

			for (IdentityProvider idp: list) {
				LoginHistoryBroker broker = new LoginHistoryBroker();
				int actUsers = 0;
				actUsers = broker.calculateActiveUser(
						idp.getUserActiveDuration(), idp.getDomainName());
				idp.setActiveUsers(actUsers);
				detachedList.add(idp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return detachedList;
	}

	public void deleteDomain(String url) {
		
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			Query query = em.createQuery("DELETE I FROM IdentityProvider I WHERE I.url = :durl");
			query.setParameter("durl", url);
			int count = query.executeUpdate();
			etxn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (etxn.isActive()) {
				etxn.rollback();
			}
			em.close();
		}
		AliasDomainBroker broker = new AliasDomainBroker();
		broker.deleteAliasByURL(url);
	}
	
	public void deleteDomain(Long id) {
		
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		String url = "";
		try {
			etxn = em.getTransaction();
			etxn.begin();
			IdentityProvider idp = em.find(IdentityProvider.class, id);
			url = idp.getUrl();
			em.remove(idp);
			etxn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (etxn.isActive()) {
				etxn.rollback();
			}
			em.close();
		}
		AliasDomainBroker broker = new AliasDomainBroker();
		broker.deleteAliasByURL(url);
	}

	public void deleteAllDomain() {

		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			Query query = em.createQuery("DELETE I FROM IdentityProvider I");
			int count = query.executeUpdate();
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

	public void updateDomain(IdentityProvider obj) {

		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			IdentityProvider idp = em.find(IdentityProvider.class, obj.getId());
			idp.setDomainName(obj.getDomainName());
			idp.setUrl(obj.getUrl());
			idp.setLicenseExpireDate(obj.getLicenseExpireDate());
			idp.setNoOfLicense(obj.getNoOfLicense());
			idp.setNotes(obj.getNotes());
			idp.setUserActiveDuration(obj.getUserActiveDuration());
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

	@SuppressWarnings("unchecked")
	public IdentityProvider getDomainByName(String name) {
		EntityManager em = EMF.createEntityManager();
		IdentityProvider domain = null;
		List<IdentityProvider> list = null;
		try {
			Query query = em.createQuery("SELECT I FROM IdentityProvider I WHERE I.domainName = :dname");
			query.setParameter("dname", name);
			list = (List<IdentityProvider>) query.getResultList();
			domain = (IdentityProvider) list.get(0);
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			em.close();
		}
		return domain;
	}

	@SuppressWarnings("unchecked")
	public IdentityProvider getDomainByURL(String url) {
		EntityManager em = EMF.createEntityManager();
		IdentityProvider domain = null;
		List<IdentityProvider> list = null;
		try {
			Query query = em.createQuery("SELECT I FROM IdentityProvider I WHERE I.url = :durl");
			query.setParameter("durl", url);
			list = (List<IdentityProvider>) query.getResultList();
			domain = (IdentityProvider) list.get(0);
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			em.close();
		}
		return domain;
	}

	@SuppressWarnings("unchecked")
	public IdentityProvider getDomainById(Long id) {
		EntityManager em = EMF.createEntityManager();
		IdentityProvider domain = null;
		List<IdentityProvider> list = null;
		try {
			Query query = em.createQuery("SELECT I FROM IdentityProvider I WHERE I.id = :did");
			query.setParameter("did", id);
			list = (List<IdentityProvider>) query.getResultList();
			domain = (IdentityProvider) list.get(0);
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			em.close();
		}
		return domain;
	}
	
	@SuppressWarnings("unchecked")
	public IdentityProvider getDomainByCode(String code) {
		
		EntityManager em = EMF.createEntityManager();
		IdentityProvider domain = null;
		List<IdentityProvider> list = null;
		try {
			Query query = em.createQuery("SELECT I FROM IdentityProvider I WHERE I.domainIdCode = :dicode");
			query.setParameter("dicode", code);
			list = (List<IdentityProvider>) query.getResultList();
			domain = (IdentityProvider) list.get(0);
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			em.close();
		}
		return domain;
	}
/*
	public void addHistory(OAuthLoginHistory history) {
		String name = history.getDomainName();
		PersistenceManager pm = PMFSingleton.get().getPersistenceManager();
		Query query = pm.newQuery(IdentityProvider.class);
		query.setFilter("domainName == paramName");
		query.declareParameters("String paramName");
		List list = null;
		IdentityProvider domain = null;
		Transaction txn = null;
		try {
			txn = pm.currentTransaction();
			txn.begin();
			pm.getFetchPlan().addGroup("childerns");
			// domain = pm.getObjectById(IdentityProvider.class, name);
			list = (List) query.execute(name.toLowerCase());
			domain = (IdentityProvider) list.get(0);
			domain = pm.detachCopy(domain);
			domain.getHistory().add(history);
			pm.makePersistent(domain);
			txn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
			pm.close();
		}
	}

	public void addAliasDomain(AliasDomain domain) {
		String url = domain.getUrl();
		PersistenceManager pm = PMFSingleton.get().getPersistenceManager();
		IdentityProvider idp = null;
		Transaction txn = null;
		try {
			txn = pm.currentTransaction();
			txn.begin();
			pm.getFetchPlan().addGroup("childerns");
			idp = pm.getObjectById(IdentityProvider.class, url);
			idp = pm.detachCopy(idp);
			idp.getAliasName().add(domain);
			pm.makePersistent(idp);
			txn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
			pm.close();
		}
	}
	public void deleteAliasDomain(AliasDomain alias) {
		String url = alias.getUrl();
		String name = alias.getAliasName();
		PersistenceManager pm = PMFSingleton.get().getPersistenceManager();
		IdentityProvider idp = null;
		IdentityProvider detachedIDP = null;
		Transaction txn = null;
		try {
			txn = pm.currentTransaction();
			txn.begin();
			pm.getFetchPlan().addGroup("childerns");
			idp = pm.getObjectById(IdentityProvider.class, url);
			// detachedIDP = pm.detachCopy(idp);
			// pm.deletePersistent(idp);
			ArrayList<AliasDomain> list = idp.getAliasName();
			for (int i = 0; i < list.size(); i++) {
				AliasDomain d = list.get(i);
				if (d.getAliasName().equalsIgnoreCase(name)) {
					list.remove(i);
					break;
				}
			}
			// idp.setAliasName(list);
			// pm.makePersistent(idp);
			txn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
			pm.close();
		}
	}

	public void updateAliasDomain(AliasDomain alias, String newName) {
		String url = alias.getUrl();
		String name = alias.getAliasName();
		PersistenceManager pm = PMFSingleton.get().getPersistenceManager();
		IdentityProvider idp = null;
		IdentityProvider detachedIDP = null;
		Transaction txn = null;
		try {
			txn = pm.currentTransaction();
			txn.begin();
			pm.getFetchPlan().addGroup("childerns");
			idp = pm.getObjectById(IdentityProvider.class, url);
			// detachedIDP = pm.detachCopy(idp);
			// pm.deletePersistent(idp);
			ArrayList<AliasDomain> list = idp.getAliasName();
			for (int i = 0; i < list.size(); i++) {
				AliasDomain d = list.get(i);
				if (d.getAliasName().equalsIgnoreCase(name)) {
					d.setAliasName(newName);
					break;
				}
			}
			// idp.setAliasName(list);
			// pm.makePersistent(idp);
			txn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
			pm.close();
		}
	}
	*/
}
