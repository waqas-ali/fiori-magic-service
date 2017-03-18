package com.convergent.auth;

import static com.convergent.odata.DatastoreConstants.CURRENT_USER;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.convergent.model.User;
import com.convergent.odata.DatastoreConstants;
import com.convergent.odata.DatastoreUtil;

public class AuthenticationFilter implements Filter {
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		Map paramMap=null;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String requestedPage = getRequestedPage(httpRequest);
		String userName=null;
		String password=null;
		if(requestedPage.contains("User") && request.getParameter("$filter") !=null && request.getParameter("$filter").contains("userName")){
			String filter = request.getParameter("$filter").trim();
			String[] array = filter.split("and"); 	
			userName=array[0].split("eq")[1].split("\'")[1];
			password=array[1].split("eq")[1].split("\'")[1];
		}
		HttpSession session = httpRequest.getSession();
		String currentUser = (String) session.getAttribute(DatastoreConstants.CURRENT_USER);
		boolean authenticated = (currentUser != null);
		String userId=null;
		if (userName != null && password != null) {
				User userEntity=DatastoreUtil.findUser(userName, password);
				if(userEntity!=null)
					userId=userEntity.getUserId().toString();
				if (userId != null) {
					session.setAttribute(DatastoreConstants.CURRENT_USER, userId);
					if(requestedPage.contains(DatastoreConstants.USER_CONTAINER_NAME) && request.getParameter("$filter")!=null && request.getParameter("$filter").contains(DatastoreConstants.USER_LOGIN_PROPNAME)){
						String jsonResponse="{\"success\" :\"You Are Authorized.\",\"userId\" :"+userId+"}";
						response.setContentType(request.getContentType());
						response.getWriter().write(jsonResponse);
						return;
					}else{
						filterChain.doFilter(request, response);
						return;
					}
				}
				else {
					resp.sendRedirect("/"+DatastoreConstants.LOGIN_URL);
				}
		}else if(requestedPage.contains(DatastoreConstants.ODATA_EXTENSION)) { // odata cache it here
			 // now send that user is not authenticated via json
			if(authenticated && requestedPage.contains(DatastoreConstants.SUBMISSION_CONTAINER_NAME)){
				JPARequestWrapper requestwrapper=new JPARequestWrapper(httpRequest, paramMap);
				filterChain.doFilter(requestwrapper, response);
				return;
			}else if(authenticated){
				filterChain.doFilter(request, response);
				return;
			}else{
				String jsonResponse="{\"error\" :\"Not Authorized.\"}";
				response.setContentType(request.getContentType());
				response.getWriter().write(jsonResponse);
			}
		}
		else if(isExecludedPath(requestedPage)){ // others
			filterChain.doFilter(request, response);
			return;
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	
	private boolean isExecludedPath(String pagePath){
		for(String execludePath: DatastoreConstants.EXECLUDED_PATH_LIST){
			if(pagePath.contains(execludePath)){
				return true;
			}
		}
	return false;	
	}
	
	private String getRequestedPage(HttpServletRequest aHttpRequest) {
		String url = aHttpRequest.getRequestURI();
		int firstSlash = url.indexOf("/");
		String requestedPage = null;
		if (firstSlash != -1)
			requestedPage = url.substring(firstSlash + 1, url.length());
		return requestedPage;
	}
}
