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
public class DomainManagementServlet extends HttpServlet {
	
	@SuppressWarnings("rawtypes")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String type = req.getParameter("type");
		IDPBroker broker = new IDPBroker();
		String destination = "";
		if ( type != null && !"".equals(type) && !type.equalsIgnoreCase("put")) {
			if ( type.equalsIgnoreCase("delete") ) {
				this.doDelete(req, resp);
			}
			List list = broker.retrieveDomainList();
			req.setAttribute("domain_list", list);
			destination = "/jsp/domain_management.jsp";
			
		}
		else if ( type != null && !"".equals(type) && type.equalsIgnoreCase("put")) {
			String name = req.getParameter("name");
			IdentityProvider idp = broker.getDomainByURL(name);
			req.setAttribute("domain", idp);
			req.getSession().setAttribute("domain", idp);
			destination = "/jsp/add_domain.jsp";
			//resp.sendRedirect("add_domain.jsp");
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination);
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String dName = req.getParameter("dname").trim();
		String url = req.getParameter("daddress").trim();
		int nol = Integer.parseInt(req.getParameter("license").trim());
		int days = Integer.parseInt(req.getParameter("days").trim());
		String note = req.getParameter("note").trim();
		String s = req.getParameter("expireDate").trim();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try {
			d = format.parse(s);
		} catch (ParseException e) {
			d = null;
		}
		String type = req.getParameter("form_type");
		
		IDPBroker broker = new IDPBroker();
		IdentityProvider idp;
		if ( !"update".equalsIgnoreCase(type) ) {
			idp = new IdentityProvider(dName,url,nol,days,d,note);
			broker.saveDomain(idp);
		}else {
			idp = (IdentityProvider) req.getSession().getAttribute("domain");
			idp.setUserActiveDuration(days);
			idp.setNoOfLicense(nol);
			idp.setLicenseExpireDate(d);
			idp.setNotes(note);
			req.getSession().removeAttribute("domain");
			broker.updateDomain(idp);
		}
		resp.sendRedirect("/domainmanagement?type=get");
	}
	
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String name = req.getParameter("name");
		IDPBroker broker = new IDPBroker();
		broker.deleteDomain(name);
		//broker.deleteAllDomain();
	}

	
}
