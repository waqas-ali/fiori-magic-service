package user.oauth.data.broker;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import com.convergent.model.*;

import user.oauth.persistence.util.EMF;

public class ResultBroker {

	public void addResult(Result res) {

		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			em.persist(res);
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
	public List<Result> showAllResults() {
		EntityManager em = EMF.createEntityManager();
		List<Result> list = null;
		List<Result> detachedList = null;
		try {
			Query query = em.createQuery("SELECT RH FROM ResultHistory RH ");
			list = (List<Result>) query.getResultList();
			detachedList = new ArrayList<Result>();
			for (Result res: list) {
				detachedList.add(res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return detachedList;
	}
	

	public Result getResultById(Long id) {
		EntityManager em = EMF.createEntityManager();
		Result res = null;
		try {
			res = (Result) em.find(Result.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return res;
	}
	
	public void updateResult(Result obj) {
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			Result res = (Result) em.find(Result.class, obj.getResultId());
			res.setCategory(obj.getCategory());
			res.setCurrency(obj.getCurrency());
			res.setResultBlobKey(obj.getResultBlobKey());
			res.setSubmissionId(obj.getSubmissionId());
			res.setTax(obj.getTax());
			res.setTotal(obj.getTotal());
			res.setVendor(obj.getVendor());
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

