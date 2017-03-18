package user.oauth.data.broker;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import com.convergent.model.*;

import user.oauth.persistence.util.EMF;

public class ImageBroker {

	public void addImage(Image img) {

		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			em.persist(img);
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
	public List<Image> showAllImages() {
		EntityManager em = EMF.createEntityManager();
		List<Image> list = null;
		List<Image> detachedList = null;
		try {
			Query query = em.createQuery("SELECT I FROM Image I ");
			list = (List<Image>) query.getResultList();
			detachedList = new ArrayList<Image>();
			for (Image img: list) {
				detachedList.add(img);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return detachedList;
	}
	

	public Image getImageByImageId(Long imgId) {
		EntityManager em = EMF.createEntityManager();
		Image img = null;
		try {
			img = (Image) em.find(Image.class, imgId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return img;
	}
	
	@SuppressWarnings("unchecked")
	public Image getImageBySubmissionId(Long subId) {
		EntityManager em = EMF.createEntityManager();
		List<Image> list = null;
		Image img = null;
		try {
			Query query = em.createQuery("SELECT I FROM Image I where I.submissionId = :id ");
			query.setParameter("id", subId);
			list = (List<Image>) query.getResultList();
			img = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return img;
	}
	
	public void updateImage(Image obj) {
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			Image img = (Image) em.find(Image.class, obj.getImageId());
			img.setImageBlobKey(obj.getImageBlobKey());
			img.setSubmissionId(obj.getSubmissionId());
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

	public void deleteImage(Long imgId) {
		
		EntityManager em = EMF.createEntityManager();
		EntityTransaction etxn = null;
		try {
			etxn = em.getTransaction();
			etxn.begin();
			Image img = (Image) em.find(Image.class, imgId);
			em.remove(img);
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

