package user.oauth.app.servlets;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.oauth.app.helpers.Cryptographer;
import user.oauth.app.helpers.StringHelper;
import user.oauth.data.broker.LoginHistoryBroker;
import user.oauth.jpa.model.OAuthLoginHistory;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class LoginLoggingServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String name = user.getNickname();
		String domain = StringHelper.getDomain(user.getEmail());
		OAuthLoginHistory history = new OAuthLoginHistory(domain, name);
		LoginHistoryBroker broker = new LoginHistoryBroker();
		broker.saveHistory(history);
		Cryptographer cypt = new Cryptographer();
    	domain = cypt.encrypt(domain);
		Cookie c = new Cookie("domainName", domain);
		c.setPath("/");
		c.setMaxAge(90000);
		resp.addCookie(c);
		resp.sendRedirect("/determinedomain?name=" + name + "&domain=" + domain);
		
	}

}
