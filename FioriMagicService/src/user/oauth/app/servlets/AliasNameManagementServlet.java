package user.oauth.app.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.oauth.data.broker.AliasDomainBroker;
import user.oauth.data.broker.IDPBroker;
import user.oauth.jpa.model.AliasDomain;
import user.oauth.jpa.model.IdentityProvider;

@SuppressWarnings("serial")
public class AliasNameManagementServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String type = req.getParameter("type");
		AliasDomainBroker broker = new AliasDomainBroker();
		String destination = "";
		if ( type != null && !"".equals(type) && !type.equalsIgnoreCase("put")) {
			if ( type.equalsIgnoreCase("delete") ) {
				this.doDelete(req, resp);
			}
			List<AliasDomain> list = broker.retrieveAliasDomains();
			req.setAttribute("alias_list", list);
			destination = "/jsp/alias_management.jsp";
			
		}
		else if ( type != null && !"".equals(type) && type.equalsIgnoreCase("put")) {
			String key = req.getParameter("key");
			Long l = new Long(key);
			AliasDomain alias = broker.getAliasByKey(l);
			req.setAttribute("alias", alias);
			req.getSession().setAttribute("alias_domain", alias);
			destination = "/jsp/add_alias.jsp";
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination);
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String dName = req.getParameter("aName").trim();
		String url = req.getParameter("dURL").trim();
		
		String type = req.getParameter("form_type");
		
		AliasDomainBroker broker = new AliasDomainBroker();
		
		AliasDomain alias;
		
		
		if (  !"update".equalsIgnoreCase(type) ) {
			alias = new AliasDomain(url, dName);			
			broker.saveAliasDomain(alias);
		}else {
			alias = (AliasDomain) req.getSession().getAttribute("alias_domain");
			req.getSession().removeAttribute("alias_domain");
			alias.setAliasName(dName);
			broker.updateAliasByKey(alias);
		}
		resp.sendRedirect("/aliasmanagement?type=get");
	}
	
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String name = req.getParameter("key");
		AliasDomainBroker broker = new AliasDomainBroker();
		Long l =new Long(name);
		broker.deleteAliasByKey(l);
	}
}
