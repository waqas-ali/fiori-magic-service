package com.convergent.struts2.interceptors;

import org.apache.struts2.ServletActionContext;

import com.convergent.odata.DatastoreConstants;
import com.convergent.odata.DatastoreUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorizationInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 1L;
	public String intercept(ActionInvocation invocation) throws Exception {
//		String role=DatastoreUtil.getRole(ServletActionContext.getRequest().getSession());
//		if(role==null){
//			return DatastoreConstants.RESULT_FORBIDDEN;
//		}else if(DatastoreConstants.CUSTOMER_ROLE.equals(role)){
//			return "testclient";
//		}
		return "success";
		//return invocation.invoke();	
	}
}
