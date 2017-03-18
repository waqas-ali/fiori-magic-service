//package com.convergent.odata;
/*
 * this file used for the future pursoe to
 *
 * /
//import java.util.Collection;
//import java.util.List;
//
//import org.odata4j.consumer.ODataConsumer;
//import org.odata4j.core.OEntity;
//import org.odata4j.core.OQueryRequest;
//import org.odata4j.jersey.consumer.ODataJerseyConsumer;
//import static com.convergent.odata.DatastoreConstants.*;
//
//public class ODataConsumers {
//	private final static ODataConsumer c = ODataJerseyConsumer.create(AppEngineUtil.getHostName() + "/odata.svc/");
//
//	/** Check whether string s is NOT empty. */
//	public static boolean isNotEmpty(String s) {
//		return (s != null) && s.length() > 0;
//	}
//
//	/** Check whether collection c is NOT empty. */
//	public static <E> boolean isNotEmpty(Collection<E> c) {
//		return (c != null) && !c.isEmpty();
//	}
//	
//	public static List<OEntity> getEntities(String entity, String filter, String select) {
//		OQueryRequest<OEntity> request = c.getEntities(entity);
//		if (isNotEmpty(filter)) {
//			request.filter(filter);
//		}
//		if (isNotEmpty(select)) {
//			request.select(select);
//		}
//		System.out.println(request.execute());
//		return request.execute().toList();
//	}
//
//	public static String asSingleEntityKey(List<OEntity> entities) {
//		if (isNotEmpty(entities)) {
//			System.out.println(entities.get(0).getEntityKey().toKeyStringWithoutParentheses());
//			return entities.get(0).getEntityKey().toKeyStringWithoutParentheses();
//		}
//		return null;
//	}
//
//	public static String getDomainId(String domainName) {
//		List<OEntity> entities = getEntities(DOMAIN_CONTAINER_NAME, DOMAIN_NAME_PROPNAME + " eq '" + domainName + "'", ID_PROPNAME);
//		return asSingleEntityKey(entities);
//	}
//
//	public static String getUserId(String emailId, String password) {
//		String[] email = emailId.split("@");
//		List<OEntity> entities = null;
//		String domainId = getDomainId(email[1]);
//		if (isNotEmpty(domainId)) {
//			entities = getEntities(USER_CONTAINER_NAME, USER_NAME_PROPNAME + " eq '" + email[0] + "' and " + USER_PASSWORD_PROPNAME + " eq '" + password + "' and " + DOMAIN_ID_PROPNAME + " eq "
//					+ domainId, USER_ID_PROPNAME);
//		}
//		return asSingleEntityKey(entities);
//	}
//
//}
