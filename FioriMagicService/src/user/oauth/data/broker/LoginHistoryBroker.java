package user.oauth.data.broker;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import user.oauth.jpa.model.OAuthLoginHistory;
import user.oauth.persistence.util.EMF;

public class LoginHistoryBroker {

	public void saveHistory(OAuthLoginHistory obj) {

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

	@SuppressWarnings("unchecked")
	public List<OAuthLoginHistory> retrieveHistory() {
		
		EntityManager em = EMF.createEntityManager();
		List<OAuthLoginHistory> list = null;
		List<OAuthLoginHistory> detachedList = null;

		try {
			
			Query query = em.createQuery("SELECT O FROM OAuthLoginHistory O");
			list = (List<OAuthLoginHistory>) query.getResultList();
			detachedList = new ArrayList<OAuthLoginHistory>();
			for (OAuthLoginHistory history: list) {	
				detachedList.add(history);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return detachedList;

	}

	@SuppressWarnings("unchecked")
	public List<OAuthLoginHistory> getUserHistory(String name) {
		
		EntityManager em = EMF.createEntityManager();
		List<OAuthLoginHistory> list = null;
		try {
			Query query = em.createQuery("SELECT O FROM OAuthLoginHistory O WHERE O.userId = :id");
			query.setParameter("id", name);
			list = (List<OAuthLoginHistory>) query.getResultList();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			em.close();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public int calculateActiveUser(int days,String domain) {
		Date d = new Date();
		long n = days;
		Date dateBefore = new Date(d.getTime() - (n * 24 * 3600 * 1000) );
		
		EntityManager em = EMF.createEntityManager();
		Query query = em.createQuery("SELECT DISTINCT O.userId FROM OAuthLoginHistory O WHERE " +
				"O.loginDate >= :date and " +
				"O.domainName = :id ");
		query.setParameter("date", dateBefore);
		query.setParameter("id", domain);
		
		List<String> list = (List<String>) query.getResultList();
		ArrayList<String> uniqueList = new ArrayList<String>();
		/*
		if ( list != null && list.size() > 0) {
			uniqueList.add(list.get(0));
			for ( int i = 1; i< list.size(); i++) {
				String value = list.get(i);
				int j = 0;
				for ( j = 0; j< uniqueList.size(); j++) {
					if ( !value.equalsIgnoreCase(uniqueList.get(j)) ) {
						continue;
					}else {
						break;
					}
				}
				if ( j == uniqueList.size() ) {
					uniqueList.add(value);
				}
			}
		}
		*/
		return list.size();
	}
}
