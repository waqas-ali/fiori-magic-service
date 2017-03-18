package user.oauth.app.servlets;

import java.io.IOException;

import javax.security.cert.X509Certificate;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class AutomaticallyDetermineDomainServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//Object obj = req.getAttribute("javax.servlet.request.X509Certificate");
		//X509Certificate certs[] = (X509Certificate[]) obj;
		
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
		resp.setContentType("text/html");
		if ( user != null ) {
			resp.getWriter().println("Welcome, You are a valid User, Your domain has been saved." );
			resp.getWriter().println("[<a href=\"" + userService.createLogoutURL("/html/index.html") + "\">sign out</a>]");
		}
		else {
			String domainIdCode = req.getParameter("domainIdCode");
			//domainIdCode = "40286f814276fdc2014276fdc2240000";
			if ( domainIdCode != null && !"".equals(domainIdCode) ) {
				resp.sendRedirect(userService.createLogoutURL("/handledomaincode?code="+domainIdCode));
			}
			else if ( isAuth ) {
				resp.sendRedirect(userService.createLogoutURL("/handlecookie?domainName="+value));
			}
			else {
				resp.sendRedirect("/html/login.html");
			}
		} 

	}
}
