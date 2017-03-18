package com.convergent.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.convergent.model.RoleType;
import com.convergent.odata.DatastoreUtil;

public class AuthorizationFilter implements Filter {
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		boolean authenticated = (DatastoreUtil.getCurrentUser(session) != null);
		if(authenticated){ // now looking for the authorization setting
			RoleType roleType=DatastoreUtil.findUserRole(DatastoreUtil.getCurrentUser(session));
			if(roleType!=null)
				DatastoreUtil.setRole(session,roleType.getRoleName());
		}
		filterChain.doFilter(request, response);
		return;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}
