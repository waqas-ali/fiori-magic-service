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
public class DomainCodeHandlerServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String code = req.getParameter("code");
		
		IDPBroker broker = new IDPBroker();
		IdentityProvider idp = broker.getDomainByCode(code);

		String url = (String) idp.getUrl();
		
		Cryptographer cypt = new Cryptographer();
		url = cypt.encrypt(url);
		resp.sendRedirect("/_ah/login_required?url=" + url);

	}

}
