package user.oauth.data.broker;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import com.convergent.model.*;

import user.oauth.persistence.util.EMF;

public class ResultHistoryBroker {

	public void addResultHistory(ResultHistory history) {

		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			em.persist(history);
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
	public List<ResultHistory> showAllResultHistories() {
		EntityManager em = EMF.createEntityManager();
		List<ResultHistory> list = null;
		List<ResultHistory> detachedList = null;
		try {
			Query query = em.createQuery("SELECT RH FROM ResultHistory RH ");
			list = (List<ResultHistory>) query.getResultList();
			detachedList = new ArrayList<ResultHistory>();
			for (ResultHistory history: list) {
				detachedList.add(history);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return detachedList;
	}
	

	public ResultHistory getResultHistoryById(Long id) {
		EntityManager em = EMF.createEntityManager();
		ResultHistory history = null;
		try {
			history = (ResultHistory) em.find(ResultHistory.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return history;
	}
	
	@SuppressWarnings("unchecked")
	public List<ResultHistory> getLockedSubmissions() {
		EntityManager em = EMF.createEntityManager();
		List<ResultHistory> list = null;
		List<ResultHistory> detachedList = null;
		try {
			Query query = em.createQuery("SELECT RH FROM ResultHistory RH WHERE RH.endTime IS NULL");
			list = (List<ResultHistory>) query.getResultList();
			detachedList = new ArrayList<ResultHistory>();
			for (ResultHistory history: list) {
				detachedList.add(history);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return detachedList;
	}
	
	public void updateResultHistory(ResultHistory obj) {
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			ResultHistory history = (ResultHistory) em.find(ResultHistory.class, obj.getResultHistoryId());
			history.setEndTime(obj.getEndTime());
			history.setCurrency(obj.getCurrency());
			history.setCategory(obj.getCategory());
			history.setProcessingDuration(obj.getProcessingDuration());
			history.setTax(obj.getTax());
			history.setTotal(obj.getTotal());
			history.setVendor(obj.getVendor());
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

