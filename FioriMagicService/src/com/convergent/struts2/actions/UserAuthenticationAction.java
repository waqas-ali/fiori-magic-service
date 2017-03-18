package com.convergent.struts2.actions;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;

import user.oauth.app.helpers.Cryptographer;
import user.oauth.app.helpers.StringHelper;
import user.oauth.data.broker.AliasDomainBroker;
import user.oauth.data.broker.IDPBroker;
import user.oauth.data.broker.LoginHistoryBroker;
import user.oauth.data.broker.UserBroker;
import user.oauth.jpa.model.AliasDomain;
import user.oauth.jpa.model.IdentityProvider;
import user.oauth.jpa.model.OAuthLoginHistory;

import com.convergent.model.User;
import com.convergent.odata.DatastoreConstants;
import com.convergent.odata.DatastoreUtil;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAuthenticationAction extends ActionSupport implements
		ModelDriven<User> {
	private static final long serialVersionUID = 1L;
	private User user = new User();
	private String domainName = "";
	private String url = "";

	public String login() throws IOException {
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
					Cryptographer cypt = new Cryptographer();
					this.domainName = cypt.decrypt(value);
					isAuth = true;
					break;
				}
			}
			if (i == c.length) {
				isAuth = false;
			}
		}

		UserService userService = UserServiceFactory.getUserService();
		com.google.appengine.api.users.User user = userService.getCurrentUser();
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		String id = (String) session.getAttribute(DatastoreConstants.CURRENT_USER);
		
		if (id != null) {
			return "success";
		} else {
			String domainIdCode = req.getParameter("domainIdCode");
			// domainIdCode = "40286f814276fdc2014276fdc2240000";
			if (domainIdCode != null && !"".equals(domainIdCode)) {
				IDPBroker broker = new IDPBroker();
				IdentityProvider idp = broker.getDomainByCode(domainIdCode);
				this.url = idp.getUrl();
				return "redirect";
			} else if (isAuth) {
				AliasDomainBroker broker = new AliasDomainBroker();
				AliasDomain alias = broker.getAliasByName(this.domainName);
				this.url = alias.getUrl();
				return "redirect";
			} else {
				return "notfound";
			}
		}

	}

	public String initiateProcess() {
		AliasDomainBroker broker = new AliasDomainBroker();
		AliasDomain alias = broker.getAliasByName(this.domainName);
		this.url = alias.getUrl();
		return "success";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String openIdAuthentication() throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		Set<String> attributes = new HashSet();
		String loginUrl = userService.createLoginURL("/Logging", null, url,
				attributes);
		ServletActionContext.getResponse().sendRedirect(loginUrl);
		return ActionSupport.NONE;
	}

	public String userAuth() {

		String[] loginId = user.getLoginId().split("@");
		String domainName = "";
		if (loginId.length >= 2) {
			domainName = loginId[1];
			IDPBroker idpBroker = new IDPBroker();
			UserBroker userBroker = new UserBroker();
			IdentityProvider idp = idpBroker.getDomainByName(domainName);
			if (idp != null) {
				User u = new User();
				u.setDomainId(idp.getId());
				u.setLoginId(loginId[0]);
				u.setPassword(user.getPassword());
				User authUser = userBroker.authenticateUser(u);
				if ( authUser != null ) {
					return "success";
				}
			}
		}
		//ServletActionContext.getRequest().getSession().setAttribute("errorMessage","Invalid login.. try again.");
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute(DatastoreConstants.CURRENT_USER, "1234");
		session.setAttribute(DatastoreConstants.ROLE, "client");
		return "success";

	}

	public String logging() {
		UserService userService = UserServiceFactory.getUserService();
		com.google.appengine.api.users.User user = userService.getCurrentUser();
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
		ServletActionContext.getResponse().addCookie(c);
		return "success";
	}

	public String logout() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute(DatastoreConstants.CURRENT_USER, null);
		session.setAttribute(DatastoreConstants.ROLE, null);
		session.setAttribute("errorMessage", null);
		session.invalidate();
		return "success";
	}

	public String homePage() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		String user = (String) session.getAttribute(DatastoreConstants.CURRENT_USER);
		if ( user != null )
			return "success";
		else
			return "redirect";
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public com.convergent.model.User getUser() {
		return user;
	}

	public void setUser(com.convergent.model.User user) {
		this.user = user;
	}

	@Override
	public com.convergent.model.User getModel() {
		return this.user;
	}

}
