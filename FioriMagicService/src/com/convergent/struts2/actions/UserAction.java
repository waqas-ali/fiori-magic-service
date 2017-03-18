package com.convergent.struts2.actions;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.convergent.odata.DatastoreUtil;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.opensymphony.xwork2.ActionSupport;
public class UserAction{
	private static final long serialVersionUID = 1L;

	
	public String login(){
		
		HttpServletRequest req = ServletActionContext.getRequest();
		boolean isAuth = false;
		String value = "";
		Cookie[] c = req.getCookies();
		
		if (c != null && c.length > 0) {
			int i = 0;
			for (i = 0; i < c.length; i++) {
				Cookie c1 = c[i];
				if (c1.getName().equalsIgnoreCase("domainname")) {
					value = c1.getValue();
					isAuth = true;
					break;
				}
			}
			if ( i == c.length) {
				isAuth = false;
			}
		}
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		HttpSession session = req.getSession();
		
		if ( user != null ) {
			return "success";
			//resp.getWriter().println("[<a href=\"" + userService.createLogoutURL("/html/index.html") + "\">sign out</a>]");
		}
		else {
			String domainIdCode = req.getParameter("domainIdCode");
			//domainIdCode = "40286f814276fdc2014276fdc2240000";
			if ( domainIdCode != null && !"".equals(domainIdCode) ) {
				//resp.sendRedirect(userService.createLogoutURL("/handledomaincode?code="+domainIdCode));
				return "testclient1";
			}
			else if ( isAuth ) {
				//resp.sendRedirect(userService.createLogoutURL("/handlecookie?domainName="+value));
				return "testclient2";
			}
			else {
				//resp.sendRedirect("/html/login.html");
				return "testclient3";
			}
		} 

		//return ActionSupport.SUCCESS;
	}
	public String index() {
		return ActionSupport.SUCCESS;
	}
	public String logout(){
		HttpSession session=ServletActionContext.getRequest().getSession();
		DatastoreUtil.setCurrentUser(session, null);
		DatastoreUtil.setRole(session,null);
		session.setAttribute("errorMessage", null);
		return ActionSupport.SUCCESS;
	}
	
}
