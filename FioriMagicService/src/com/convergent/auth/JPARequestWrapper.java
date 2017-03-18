package com.convergent.auth;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import com.convergent.odata.DatastoreConstants;
import com.convergent.odata.DatastoreUtil;

public class JPARequestWrapper extends HttpServletRequestWrapper {

	public Map paramMap=null;
	String user=null;
	String role=null;
	public JPARequestWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
		paramMap=new HashMap(super.getParameterMap());
	}
	
	public JPARequestWrapper(HttpServletRequest request, Map paramMap) {
		super(request);
		HttpSession session = request.getSession();
		role=DatastoreUtil.getRole(session);
		user=DatastoreUtil.getCurrentUser(session);
		this.paramMap=new HashMap(super.getParameterMap());
		this.paramMap.put(DatastoreConstants.USER_ID_PROPNAME, new Long(user));
	}
	@Override
	public Map getParameterMap() {		
		return paramMap;
	
	}
	@Override
	public String getQueryString() {
		if(DatastoreConstants.CUSTOMER_ROLE.equals(role) && super.getQueryString()==null){
			return "$filter="+DatastoreConstants.USER_ID_PROPNAME+" eq "+ user;
		}
		
	return super.getQueryString();
	}
	@Override
	public Enumeration getParameterNames() {
		// TODO Auto-generated method stub
		
		Hashtable<String,String> hashTable=new Hashtable<String,String>();
		
		Iterator i=paramMap.keySet().iterator();
		while(i.hasNext()){
			String name=(String)i.next();			
			hashTable.put(name,name);
		}
		
		return hashTable.elements();
		
		
	}
	public String[] getParameterValues(String name) {
   	 if(paramMap.get(name) instanceof String){	 
   		 return new String[]{(String)paramMap.get(name)};
   	 }
   	 else if (paramMap.get(name) instanceof String[]){
   		 return (String [])paramMap.get(name);
   	 }else{
   		 return null;
   	 }
   	 		 
    }

}
