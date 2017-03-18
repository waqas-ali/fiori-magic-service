package user.oauth.data.broker;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import com.convergent.model.*;

import user.oauth.persistence.util.EMF;

public class SubmissionBroker {

	public void addSubmission(Submission sub) {

		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			em.persist(sub);
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
	public List<Submission> showAllSubmission() {
		EntityManager em = EMF.createEntityManager();
		List<Submission> list = null;
		List<Submission> detachedList = null;
		try {
			Query query = em.createQuery("SELECT S FROM Submission S ");
			list = (List<Submission>) query.getResultList();
			detachedList = new ArrayList<Submission>();
			for (Submission sub: list) {
				detachedList.add(sub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return detachedList;
	}
	

	@SuppressWarnings({ "unchecked" })
	public Submission getSubmissionById(Long sid) {
		EntityManager em = EMF.createEntityManager();
		List<Submission> list = null;
		Submission sub = null;
		try {
			Query query = em.createQuery("SELECT S FROM Submission S where S.submissionId = :id ");
			query.setParameter("id", sid);
			list = (List<Submission>) query.getResultList();
			sub = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return sub;
	}	
	public void updateSubmission(Submission obj) {
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			Submission sub = (Submission) em.find(Submission.class, obj.getSubmissionId());
			sub.setBlobUrls(obj.getBlobUrls());
			sub.setCompletedDate(obj.getCompletedDate());
			sub.setCount(obj.getCount());
			sub.setStatus(obj.getStatus());
			sub.setTrip(obj.getTrip());
			sub.setUserId(obj.getUserId());
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

	public void deleteSubmission(Long subId) {
		
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			Submission sub = (Submission) em.find(Submission.class, subId);
			em.remove(sub);
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

