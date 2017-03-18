package com.convergent.odata;

import static com.convergent.odata.DatastoreConstants.CALLBACK_URL;
import static com.convergent.odata.DatastoreConstants.DOMAIN_CONTAINER_NAME;
import static com.convergent.odata.DatastoreConstants.DOMAIN_ID_PROPNAME;
import static com.convergent.odata.DatastoreConstants.DOMAIN_NAME_PROPNAME;
import static com.convergent.odata.DatastoreConstants.IMAGE_BLOBURL_PROPNAME;
import static com.convergent.odata.DatastoreConstants.IMAGE_CONTAINER_NAME;
import static com.convergent.odata.DatastoreConstants.IMAGE_ID_PROPNAME;
import static com.convergent.odata.DatastoreConstants.IMAGE_IMAGEBLOBKEY_PROPNAME;
import static com.convergent.odata.DatastoreConstants.RESULTHISTORY_CATEGORY_PROPNAME;
import static com.convergent.odata.DatastoreConstants.RESULTHISTORY_CONTAINER_NAME;
import static com.convergent.odata.DatastoreConstants.RESULTHISTORY_CURRENCY_PROPNAME;
import static com.convergent.odata.DatastoreConstants.RESULTHISTORY_ENDTIME_PROPNAME;
import static com.convergent.odata.DatastoreConstants.RESULTHISTORY_ID_NAME;
import static com.convergent.odata.DatastoreConstants.RESULTHISTORY_PROCESSING_DURATION;
import static com.convergent.odata.DatastoreConstants.RESULTHISTORY_STARTTIME_PROPNAME;
import static com.convergent.odata.DatastoreConstants.RESULTHISTORY_TAX_PROPNAME;
import static com.convergent.odata.DatastoreConstants.RESULTHISTORY_TOTAL_PROPNAME;
import static com.convergent.odata.DatastoreConstants.RESULTHISTORY_VENDOR_PROPNAME;
import static com.convergent.odata.DatastoreConstants.RESULT_CONTAINER_NAME;
import static com.convergent.odata.DatastoreConstants.RESULT_RESULTID_PROPNAME;
import static com.convergent.odata.DatastoreConstants.ROLETYPE_CONTAINER_NAME;
import static com.convergent.odata.DatastoreConstants.ROLETYPE_ROLENAME_PROPNAME;
import static com.convergent.odata.DatastoreConstants.ROLETYPE_ROLETYPEID_PROPNAME;
import static com.convergent.odata.DatastoreConstants.SUBMISSION_BLOBURLS_PROPNAME;
import static com.convergent.odata.DatastoreConstants.SUBMISSION_COMPLETEDDATE_PROPNAME;
import static com.convergent.odata.DatastoreConstants.SUBMISSION_CONTAINER_NAME;
import static com.convergent.odata.DatastoreConstants.SUBMISSION_COUNT_PROPNAME;
import static com.convergent.odata.DatastoreConstants.SUBMISSION_CREATEDDATE_PROPNAME;
import static com.convergent.odata.DatastoreConstants.SUBMISSION_ID_PROPNAME;
import static com.convergent.odata.DatastoreConstants.SUBMISSION_STATUS_PROPNAME;
import static com.convergent.odata.DatastoreConstants.SUBMISSION_TRIP_PROPNAME;
import static com.convergent.odata.DatastoreConstants.USER_CONTAINER_NAME;
import static com.convergent.odata.DatastoreConstants.USER_ID_PROPNAME;
import static com.convergent.odata.DatastoreConstants.USER_LOGIN_PROPNAME;
import static com.convergent.odata.DatastoreConstants.USER_PASSWORD_PROPNAME;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.odata4j.core.OEntities;
import org.odata4j.core.OEntity;
import org.odata4j.core.OEntityKey;
import org.odata4j.core.OLink;
import org.odata4j.core.OProperties;
import org.odata4j.core.OProperty;
import org.odata4j.edm.EdmEntitySet;
import org.odata4j.producer.EntitiesResponse;
import org.odata4j.producer.EntityResponse;
import org.odata4j.producer.QueryInfo;
import org.odata4j.producer.Responses;
import org.odata4j.producer.jpa.JPAEdmGenerator;
import org.odata4j.producer.jpa.JPAProducer;

import user.oauth.data.broker.IDPBroker;
import user.oauth.data.broker.ResultBroker;
import user.oauth.data.broker.ResultHistoryBroker;
import user.oauth.data.broker.RoleTypeBroker;
import user.oauth.data.broker.SubmissionBroker;
import user.oauth.jpa.model.IdentityProvider;

import com.convergent.model.Result;
import com.convergent.model.ResultHistory;
import com.convergent.model.RoleType;
import com.convergent.model.Submission;
import com.convergent.model.User;

public class JPAProducerWrapper extends JPAProducer {
	public JPAProducerWrapper(EntityManagerFactory emf, String namespace,
			int maxResults) throws Exception {
		super(emf, new JPAEdmGenerator(emf, namespace).generateEdm(null)
				.build(), maxResults, null, null);
	}

	@Override
	public EntitiesResponse getEntities(String entitySetName,
			QueryInfo queryInfo) {
		// TODO Auto-generated method stub
		EntitiesResponse response = null;
		response = super.getEntities(entitySetName, queryInfo);
		if (entitySetName.equals(IMAGE_CONTAINER_NAME)) {
			int responsesize = response.getEntities().size();
			for (int i = 0; i < responsesize; i++) {
				OEntity image = super.getEntities(entitySetName, queryInfo)
						.getEntities().get(i);
				final List<OProperty<?>> properties = new ArrayList<OProperty<?>>();
				properties.add(OProperties.int64(IMAGE_ID_PROPNAME, new Long(
						image.getProperty(IMAGE_ID_PROPNAME).getValue()
								.toString())));
				properties.add(OProperties.int64(SUBMISSION_ID_PROPNAME,
						new Long(image.getProperty(SUBMISSION_ID_PROPNAME)
								.getValue().toString())));
				String imageUrl = null;
				if (image.getProperty(IMAGE_IMAGEBLOBKEY_PROPNAME).getValue() != null
						&& image.getProperty(IMAGE_IMAGEBLOBKEY_PROPNAME)
								.getValue().toString().length() > 0) {
					imageUrl = DatastoreUtil.convertBlobKeyToUrl(DatastoreUtil
							.strToBlobKey(image
									.getProperty(IMAGE_IMAGEBLOBKEY_PROPNAME)
									.getValue().toString()));
				}
				properties.add(OProperties.string("IMAGE_URL", imageUrl));
				EdmEntitySet imageEntitySet = getMetadata().getEdmEntitySet(
						IMAGE_CONTAINER_NAME);
				OEntity newimage = OEntities.create(imageEntitySet,
						image.getEntityKey(), properties,
						new ArrayList<OLink>());
				response.getEntities().add(i, newimage);
			}
			return response;
		}
		return super.getEntities(entitySetName, queryInfo);
	}

	@Override
	public EntityResponse createEntity(String entitySetName, OEntity entity) {
		try {
			OEntity oEntity = null;
			EntityResponse response = null;
			String userName = null;
			List<String> urls = new ArrayList<String>();
			IDPBroker idpBroker = new IDPBroker();
			RoleTypeBroker rtBroker = new RoleTypeBroker();

			final List<OProperty<?>> properties = new ArrayList<OProperty<?>>();
			properties.addAll(entity.getProperties());
			User userEntity = null;
			RoleType roleTypeEntity = null;

			if (entitySetName.equals(USER_CONTAINER_NAME)) {
				userName = entity.getProperty(USER_LOGIN_PROPNAME).getValue()
						.toString();

				IdentityProvider domainEntity = idpBroker
						.getDomainByName(userName.split("@")[1]);
				if (domainEntity == null) {
					EdmEntitySet ees = getMetadata().getEdmEntitySet(
							DOMAIN_CONTAINER_NAME);
					final List<OProperty<?>> domainProperties = new ArrayList<OProperty<?>>();
					domainProperties.add(OProperties.string(
							DOMAIN_NAME_PROPNAME, userName.split("@")[1]));
					oEntity = OEntities.create(ees, dummyKey(),
							domainProperties, new ArrayList<OLink>());

					response = super.createEntity(DOMAIN_CONTAINER_NAME,
							oEntity);
					properties.add(OProperties.int64(
							DOMAIN_ID_PROPNAME,
							new Long(response.getEntity()
									.getProperty(DOMAIN_ID_PROPNAME).getValue()
									.toString())));
				} else {
					properties.add(OProperties.int64(DOMAIN_ID_PROPNAME,
							domainEntity.getId()));
				}
				properties.add(OProperties.string(USER_LOGIN_PROPNAME,
						userName.split("@")[0]));
				userEntity = DatastoreUtil.findUser(userName, entity
						.getProperty(USER_PASSWORD_PROPNAME).getValue()
						.toString());
			}
			if (entitySetName.equals(ROLETYPE_CONTAINER_NAME)) {
				roleTypeEntity = rtBroker.getRoleById(new Long(entity
						.getProperty(ROLETYPE_ROLENAME_PROPNAME).getValue()
						.toString()));
			}
			if (entitySetName.equals(SUBMISSION_CONTAINER_NAME)) {
				properties.add(OProperties.datetime(
						SUBMISSION_CREATEDDATE_PROPNAME, new Date()));
				for (int i = 0; i < Integer.parseInt(entity
						.getProperty("count").getValue().toString()); i++) {
					urls.add(DatastoreUtil.createUploadUrl("/" + CALLBACK_URL));
				}
				properties.add(OProperties.string(IMAGE_BLOBURL_PROPNAME,
						urls.toString()));
			}
			if (entitySetName.equals(RESULT_CONTAINER_NAME)) {
				properties.add(OProperties.datetime(
						SUBMISSION_CREATEDDATE_PROPNAME, new Date()));
			}
			if (entitySetName.equals(RESULTHISTORY_CONTAINER_NAME)) {
				ResultBroker rBroker = new ResultBroker();
				Result result = rBroker.getResultById(new Long(entity
						.getProperty("resultId").getValue().toString()));
				properties.add(OProperties.string(
						RESULTHISTORY_CURRENCY_PROPNAME, result.getCurrency()
								.toString()));
				properties.add(OProperties.string(
						RESULTHISTORY_VENDOR_PROPNAME, result.getVendor()));
				properties.add(OProperties.double_(
						RESULTHISTORY_TOTAL_PROPNAME, result.getTotal()));
				properties.add(OProperties.double_(RESULTHISTORY_TAX_PROPNAME,
						result.getTax()));
				properties.add(OProperties.string(
						RESULTHISTORY_CATEGORY_PROPNAME, result.getCategory()));
				properties.add(OProperties.datetime(
						RESULTHISTORY_STARTTIME_PROPNAME, new Date()));
			}
			EdmEntitySet ees = getMetadata().getEdmEntitySet(entitySetName);
			oEntity = OEntities.create(ees, dummyKey(), properties,
					new ArrayList<OLink>());
			if (entitySetName.equals(USER_CONTAINER_NAME)) {
				if (userEntity != null) {
					final List<OProperty<?>> userProperties = new ArrayList<OProperty<?>>();
					userProperties.add(OProperties.int64(USER_ID_PROPNAME,
							userEntity.getUserId()));
					userProperties.add(OProperties.string(USER_LOGIN_PROPNAME,
							userEntity.getLoginId()));
					EdmEntitySet userEntitySet = getMetadata().getEdmEntitySet(
							USER_CONTAINER_NAME);
					OEntity existingUser = OEntities
							.create(userEntitySet, OEntityKey.parse(userEntity
									.getUserId().toString()), userProperties,
									new ArrayList<OLink>());

					return Responses.entity(existingUser);
				} else {
					response = super.createEntity(entitySetName, oEntity);
				}
			} else if (entitySetName.equals(ROLETYPE_CONTAINER_NAME)) {
				if (roleTypeEntity != null) {
					final List<OProperty<?>> roleTypeProperties = new ArrayList<OProperty<?>>();
					roleTypeProperties.add(OProperties.int64(
							ROLETYPE_ROLETYPEID_PROPNAME,
							roleTypeEntity.getRoleTypeId()));
					roleTypeProperties.add(OProperties.string(
							ROLETYPE_ROLENAME_PROPNAME,
							roleTypeEntity.getRoleName()));
					EdmEntitySet roleTypeEntitySet = getMetadata()
							.getEdmEntitySet(ROLETYPE_CONTAINER_NAME);
					OEntity existingrole = OEntities.create(roleTypeEntitySet,
							OEntityKey.parse(roleTypeEntity.getRoleTypeId()
									.toString()), roleTypeProperties,
							new ArrayList<OLink>());

					return Responses.entity(existingrole);
				} else {
					response = super.createEntity(entitySetName, oEntity);
				}

			} else {
				response = super.createEntity(entitySetName, oEntity);
			}

			if (entitySetName.equals(SUBMISSION_CONTAINER_NAME)) {
				OEntity imageEntity = null;
				final List<OProperty<?>> imageproperties = new ArrayList<OProperty<?>>();
				imageproperties.add(OProperties.int64(SUBMISSION_ID_PROPNAME,
						new Long(response.getEntity().getEntityKey()
								.asSingleValue().toString())));
				EdmEntitySet imageees = getMetadata().getEdmEntitySet(
						IMAGE_CONTAINER_NAME);
				imageEntity = OEntities.create(imageees, dummyKey(),
						imageproperties, new ArrayList<OLink>());
				for (int i = 0; i < Integer.parseInt(entity
						.getProperty("count").getValue().toString()); i++) {
					super.createEntity(IMAGE_CONTAINER_NAME, imageEntity);
				}
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateEntity(String entitySetName, OEntity entity) {
		if (SUBMISSION_CONTAINER_NAME.equals(entitySetName)) {
			OEntity oEntity = submissionOEntity(entity);
			super.updateEntity(entitySetName, oEntity);
		} else if (RESULT_CONTAINER_NAME.equals(entitySetName)) {
			OEntity oEntity = resultOEntity(entity);
			super.updateEntity(entitySetName, oEntity);
		} else if (RESULTHISTORY_CONTAINER_NAME.equals(entitySetName)) {
			OEntity oEntity = resultHistoryOEntity(entity);
			super.updateEntity(entitySetName, oEntity);
		} else {
			super.updateEntity(entitySetName, entity);
		}
	}

	private OEntityKey dummyKey() {
		return OEntityKey.parse("1");
	}

	private OEntity submissionOEntity(OEntity entity) {
		SubmissionBroker subBroker = new SubmissionBroker();
		Submission submission = subBroker.getSubmissionById(new Long(entity
				.getEntityKey().asSingleValue().toString()));
		final List<OProperty<?>> submissionProperties = new ArrayList<OProperty<?>>();
		submissionProperties.addAll(entity.getProperties()); // status will be
																// taken
		submissionProperties.add(OProperties.int32(SUBMISSION_COUNT_PROPNAME,
				submission.getCount()));
		submissionProperties.add(OProperties.int64(SUBMISSION_ID_PROPNAME,
				submission.getSubmissionId()));
		submissionProperties.add(OProperties.string(SUBMISSION_TRIP_PROPNAME,
				submission.getTrip()));
		submissionProperties.add(OProperties.datetime(
				SUBMISSION_CREATEDDATE_PROPNAME, submission.getCreatedDate()));
		submissionProperties.add(OProperties.int64(USER_ID_PROPNAME,
				submission.getUserId()));
		submissionProperties.add(OProperties.string(
				SUBMISSION_BLOBURLS_PROPNAME, submission.getBlobUrls()));
		if (entity.getProperty(SUBMISSION_STATUS_PROPNAME).getValue()
				.toString()
				.equalsIgnoreCase(Submission.Status.COMPLETED.toString())) {
			submissionProperties.add(OProperties.datetime(
					SUBMISSION_COMPLETEDDATE_PROPNAME, new Date()));
		}
		EdmEntitySet submissionEntitySet = getMetadata().getEdmEntitySet(
				SUBMISSION_CONTAINER_NAME);
		return OEntities.create(submissionEntitySet, entity.getEntityKey(),
				submissionProperties, new ArrayList<OLink>());
	}

	private OEntity resultOEntity(OEntity entity) {
		ResultBroker rBroker = new ResultBroker();
		Result result = rBroker.getResultById(new Long(entity
				.getEntityKey().asSingleValue().toString()));
		final List<OProperty<?>> resultProperties = new ArrayList<OProperty<?>>();
		resultProperties.addAll(entity.getProperties());
		resultProperties.add(OProperties.int64(SUBMISSION_ID_PROPNAME,
				result.getSubmissionId()));
		resultProperties.add(OProperties.int64(RESULT_RESULTID_PROPNAME,
				result.getResultId()));
		// resultProperties.add(OProperties.string(RESULT_CURRENCY_PROPNAME,result.getCurrency()));
		// resultProperties.add(OProperties.datetime(SUBMISSION_CREATEDDATE_PROPNAME,result.getCreatedDate()));
		EdmEntitySet resultEntitySet = getMetadata().getEdmEntitySet(
				RESULT_CONTAINER_NAME);
		return OEntities.create(resultEntitySet, entity.getEntityKey(),
				resultProperties, new ArrayList<OLink>());
	}

	private OEntity resultHistoryOEntity(OEntity entity) {
		ResultHistoryBroker rhBroker = new ResultHistoryBroker();
		ResultHistory resultHistory = rhBroker.getResultHistoryById(new Long(
				entity.getEntityKey().asSingleValue().toString()));
		final List<OProperty<?>> resultHistoryProperties = new ArrayList<OProperty<?>>();
		resultHistoryProperties.addAll(entity.getProperties());
		resultHistoryProperties.add(OProperties.int64(RESULTHISTORY_ID_NAME,
				resultHistory.getResultHistoryId()));
		resultHistoryProperties.add(OProperties.int64(RESULT_RESULTID_PROPNAME,
				resultHistory.getResultId()));
		resultHistoryProperties.add(OProperties.int64(USER_ID_PROPNAME,
				resultHistory.getUserId()));
		resultHistoryProperties.add(OProperties.int64(SUBMISSION_ID_PROPNAME,
				resultHistory.getSubmissionId()));
		resultHistoryProperties.add(OProperties.string(
				RESULTHISTORY_VENDOR_PROPNAME, resultHistory.getVendor()));
		resultHistoryProperties.add(OProperties.string(
				RESULTHISTORY_CURRENCY_PROPNAME, resultHistory.getCurrency()));
		resultHistoryProperties.add(OProperties.double_(
				RESULTHISTORY_TOTAL_PROPNAME, resultHistory.getTotal()));
		resultHistoryProperties.add(OProperties.double_(
				RESULTHISTORY_TAX_PROPNAME, resultHistory.getTax()));
		resultHistoryProperties
				.add(OProperties.datetime(RESULTHISTORY_STARTTIME_PROPNAME,
						resultHistory.getStartTime()));
		resultHistoryProperties.add(OProperties.datetime(
				RESULTHISTORY_ENDTIME_PROPNAME, new Date()));
		resultHistoryProperties.add(OProperties.string(
				RESULTHISTORY_PROCESSING_DURATION,
				DatastoreUtil.getTimeDiff(new Date(),
						resultHistory.getStartTime())));
		resultHistoryProperties.add(OProperties.string(
				RESULTHISTORY_CATEGORY_PROPNAME, resultHistory.getCategory()));
		EdmEntitySet resultHistoryEntitySet = getMetadata().getEdmEntitySet(
				RESULTHISTORY_CONTAINER_NAME);
		return OEntities.create(resultHistoryEntitySet, entity.getEntityKey(),
				resultHistoryProperties, new ArrayList<OLink>());
	}

}
