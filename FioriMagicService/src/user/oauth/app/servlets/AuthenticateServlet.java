package user.oauth.app.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.oauth.app.helpers.Cryptographer;
import user.oauth.data.broker.AliasDomainBroker;
import user.oauth.data.broker.IDPBroker;

import user.oauth.jpa.model.AliasDomain;
import user.oauth.jpa.model.IdentityProvider;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class AuthenticateServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String url = req.getParameter("url");
		Cryptographer cypt = new Cryptographer();
		url = cypt.decrypt(url);

		UserService userService = UserServiceFactory.getUserService();
		Set<String> attributes = new HashSet();
		String loginUrl = userService.createLoginURL("/logging", null, url,
				attributes);
		resp.sendRedirect(loginUrl);

	}

}
