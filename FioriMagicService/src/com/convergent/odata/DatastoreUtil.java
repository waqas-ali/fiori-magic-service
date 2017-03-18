package com.convergent.odata;

import static com.convergent.odata.DatastoreConstants.CURRENT_USER;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import user.oauth.data.broker.*;
import user.oauth.jpa.model.*;

import com.convergent.model.RoleType;
import com.convergent.model.User;
import com.convergent.model.UserRole;
import com.convergent.persistence.util.EMF;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

public class DatastoreUtil {
	public static BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	public static EntityManager em = EMF.createEntityManager();
	public static final ImagesService imageService = ImagesServiceFactory.getImagesService();

/*	public static Domain getDomainByName(String domainName) {
		Query query = em.createQuery("SELECT d FROM Domain d WHERE " + NAME_PROPNAME + " = :domainName ");
		query.setParameter("domainName", domainName);
		List<Domain> results = query.getResultList();
		Domain foundEntity = new Domain(); // just for created test purpose by waqas ali
		foundEntity.setName("test.com");
		foundEntity.setId(1000L);
		if (!results.isEmpty()) {
			foundEntity = (Domain) results.get(0);
		}
		return foundEntity;
	}*/

	public static User findUser(String e, String pswd) {
		String[] loginId = e.split("@");
		String domainName = "";
		if(loginId.length >= 2) {
			domainName = loginId[1];
			IDPBroker idpBroker = new IDPBroker();
			UserBroker userBroker = new UserBroker();
			IdentityProvider idp = idpBroker.getDomainByName(domainName);
			if (idp != null ) {
				User u = new User();
				u.setDomainId(idp.getId());
				u.setLoginId(loginId[0]);
				u.setPassword(pswd);
				User authUser = userBroker.authenticateUser(u);
				return authUser;
			}
		}
		return null;
	}

/*	public static RoleType findRoleType(String roleName) {
		Query query = em.createQuery("SELECT r FROM RoleType r WHERE " + ROLETYPE_ROLENAME_PROPNAME + " = :roleName ");
		query.setParameter("roleName", roleName);
		List roles = query.getResultList();
		return roles.size() > 0 ? (RoleType) roles.get(0) : null;
	}*/

	/*public static RoleType findRoleType(Long roleTypeId) {
		RoleType type = new RoleType();
		type.setRoleName("CLIENT");
		type.setRoleTypeId(roleTypeId);
		return type;
		//return EMF.createEntityManager().find(RoleType.class, KeyFactory.createKey(RoleType.class.getSimpleName(), roleTypeId));
	}*/

	public static RoleType findUserRole(String userId) {
		Long id = new Long(userId);
		UserRoleBroker urBroker = new UserRoleBroker();
		UserRole userRole = urBroker.getUserRoleByUserId(id);
		if ( userRole != null ) {
			RoleTypeBroker rtBroker = new RoleTypeBroker();
			return rtBroker.getRoleById(userRole.getRoleTypeId());
		}else {
			return null;
		}
		
	}

/*	public static Image findImageBySubmission(Long id) {
		Query query = em.createQuery("SELECT i FROM Image i WHERE " + SUBMISSION_ID_PROPNAME + " = :submissionId and " + IMAGE_IMAGEBLOBKEY_PROPNAME + " = :imageKey");
		query.setParameter("submissionId", id);
		query.setParameter("imageKey", new String());
		return (Image) query.getResultList().get(0);
	}
*/
	public static Map<String, BlobKey> getUploadedBlobs(HttpServletRequest req) {
		return blobstoreService.getUploadedBlobs(req);
	}

	public static String createUploadUrl(String callback) {
		return blobstoreService.createUploadUrl(callback);

	}

/*	public static Submission findSubmissionById(Long submissionId) {
		return em.find(Submission.class, KeyFactory.createKey(Submission.class.getSimpleName(), submissionId));

	}*/

/*	public static Result findResultById(Long resultId) {
		return EMF.createEntityManager().find(Result.class, KeyFactory.createKey(Result.class.getSimpleName(), resultId));

	}*/

/*	public static Image findImageId(Long imageId) {
		return em.find(Image.class, KeyFactory.createKey(Image.class.getSimpleName(), imageId));
	}*/

/*	public static void updateImage(Image image) {
		em.getTransaction().begin();
		em.persist(image);
		em.getTransaction().commit();
	}*/
	
/*	public static void updateResultHistory(ResultHistory image) {
		em.getTransaction().begin();
		em.persist(image);
		em.getTransaction().commit();
	}*/

/*	public static void updateSubmission(Submission submission) {
		em.getTransaction().begin();
		em.persist(submission);
		em.getTransaction().commit();
	}*/

/*	public static ResultHistory findResultHistoryById(Long resultHistoryId) {
		return em.find(ResultHistory.class, KeyFactory.createKey(ResultHistory.class.getSimpleName(), resultHistoryId));

	}*/
	
/*	public static List<ResultHistory> findLockedSubmissions() {
		Query query = em.createQuery("SELECT rh FROM ResultHistory rh WHERE endTime IS NULL");
		//query.setParameter("userId", new Long(userId));
		return query.getResultList();
	}*/

	public static BlobKey strToBlobKey(String strBlobKey) {
		return new BlobKey(strBlobKey);
	}

	public static String convertBlobKeyToUrl(BlobKey blobKey) {
		return imageService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey));
	}

	public static String getTimeDiff(Date dateOne, Date dateTwo) {
		String diff = "";
		long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
		diff=String.format("%d :: %d :: %d", timeDiff/(1000*60*60), (timeDiff%(1000*60*60))/(1000*60), ((timeDiff%(1000*60*60))%(1000*60))/1000);
		return diff;
	}

	public static String getCurrentUser(HttpSession aSession) {
		String currentUser = (String) aSession.getAttribute(CURRENT_USER);
		return currentUser;
	}

	public static void setCurrentUser(HttpSession aSession, String userId) {
		aSession.setAttribute(CURRENT_USER, userId);
	}

	public static String getRole(HttpSession aSession) {
		String role = (String) aSession.getAttribute(DatastoreConstants.ROLE);
		return role;
	}

	public static void setRole(HttpSession aSession, String role) {
		aSession.setAttribute(DatastoreConstants.ROLE, role);
	}
	
}
