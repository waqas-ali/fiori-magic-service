package com.convergent.odata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DatastoreConstants {
	public static final String DOMAIN_CONTAINER_NAME = "Domain";
	public static final String DOMAIN_ID_PROPNAME ="domainId";
	public static final String DOMAIN_NAME_PROPNAME = "domainName";
	public static final String ROLETYPE_CONTAINER_NAME = "RoleType";
	public static final String ROLETYPE_ROLENAME_PROPNAME = "roleName";
	public static final String ROLETYPE_ROLETYPEID_PROPNAME = "roleTypeId";
	public static final String ID_PROPNAME = "id";
	public static final String NAME_PROPNAME = "name";
	public static final String USER_PASSWORD_PROPNAME="password";
	public static final String USER_CONTAINER_NAME = "User";
	public static final String USER_ID_PROPNAME = "userId";
	public static final String USER_LOGIN_PROPNAME = "loginId";
	public static final String SUBMISSION_CONTAINER_NAME = "Submission";
	public static final String SUBMISSION_ID_PROPNAME = "submissionId";
	public static final String SUBMISSION_STATUS_PROPNAME = "status";
	public static final String SUBMISSION_CREATEDDATE_PROPNAME = "createdDate";
	public static final String SUBMISSION_COMPLETEDDATE_PROPNAME = "completedDate";
	public static final String SUBMISSION_TRIP_PROPNAME = "trip";
	public static final String SUBMISSION_COUNT_PROPNAME = "count";
	public static final String SUBMISSION_BLOBURLS_PROPNAME = "blobUrls";
	public static final String IMAGE_CONTAINER_NAME = "Image";
	public static final String IMAGE_ID_PROPNAME = "imageId";
	public static final String IMAGE_IMAGEBLOBKEY_PROPNAME = "imageBlobKey";
	public static final String IMAGE_IMAGEURL_PROPNAME = "ImageURL";
	public static final String IMAGE_URLCOUNT_PROPNAME = "count";
	public static final String IMAGE_BLOBURL_PROPNAME = "blobUrls";
	public static final String RESULT_CONTAINER_NAME = "Result";
	public static final String RESULT_CURRENCY_PROPNAME = "currency";
	public static final String RESULT_RESULTID_PROPNAME = "resultId";
	public static final String RESULTHISTORY_ID_NAME = "resultHistoryId";
	public static final String RESULTHISTORY_CONTAINER_NAME = "ResultHistory";
	public static final String RESULTHISTORY_STARTTIME_PROPNAME = "startTime";
	public static final String RESULTHISTORY_VENDOR_PROPNAME = "vendor";
	public static final String RESULTHISTORY_TOTAL_PROPNAME = "total";
	public static final String RESULTHISTORY_TAX_PROPNAME = "tax";
	public static final String RESULTHISTORY_CURRENCY_PROPNAME = "currency";
	public static final String RESULTHISTORY_CATEGORY_PROPNAME = "category";
	public static final String RESULTHISTORY_ENDTIME_PROPNAME = "endTime";
	public static final String RESULTHISTORY_PROCESSING_DURATION = "processingDuration";
	public static final String CUSTOM_ERROR_MSG = "ErrorMessage";
	public static final String DOMAIN_NOT_FOUND = "Domain not found.";
	public static final String UPLOAD_IMAGE_URL = "/odata.svc/Image";
	public static final String UPDATE_SUBMISSION_URL ="/odata.svc/Submission";
	public static final String RETURN_SUCCESS ="success";
	public static final String RETURN_ERROR ="error";
	public static final String FORM_NAME ="form[file]";
	public static final String CALLBACK_URL ="notifications";
    public static final String EMPTY_URL = ""; 
    public static final String LOGIN_URL = "Login"; 
    public static final String CURRENT_USER = "CURRENT_USER"; 
    public static final String CUSTOMER_ROLE="CLIENT";
    public static final String EXECLUDED_PATH="images,_ah,odata.svc,assets,prototype,notifications,unLockSubmissions";
    public static final List <String> EXECLUDED_PATH_LIST = new ArrayList<String>(Arrays.asList(EXECLUDED_PATH.split(",")));
    public static final String ROLE="ROLE";
    public static final String ODATA_EXTENSION=".svc";
    public static final String RESULT_FORBIDDEN="forbidden";
    
    
}