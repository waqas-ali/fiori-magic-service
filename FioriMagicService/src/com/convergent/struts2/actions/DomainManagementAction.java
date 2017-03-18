package com.convergent.struts2.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import user.oauth.data.broker.IDPBroker;
import user.oauth.jpa.model.IdentityProvider;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class DomainManagementAction extends ActionSupport implements
		ModelDriven<IdentityProvider> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6217461489693576645L;

	private List<IdentityProvider> domains = new ArrayList<IdentityProvider>();
	private IdentityProvider idp = new IdentityProvider();
	private boolean readonly = false;
	private String label = "";
	private String action = "";

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<IdentityProvider> getDomains() {
		return domains;
	}

	public void setDomains(List<IdentityProvider> domains) {
		this.domains = domains;
	}

	public IdentityProvider getIdp() {
		return idp;
	}

	public void setIdp(IdentityProvider idp) {
		this.idp = idp;
	}

	@SuppressWarnings("unchecked")
	public String showDomains() {
		IDPBroker broker = new IDPBroker();
		domains = broker.retrieveDomainList();
		return "success";
	}

	public String updateDomain() {
		IDPBroker broker = new IDPBroker();
		broker.updateDomain(idp);
		return ActionSupport.SUCCESS;
	}

	public String deleteDomain() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Long id = Long.parseLong(request.getParameter("domainId"));
		IDPBroker broker = new IDPBroker();
		broker.deleteDomain(id);
		return ActionSupport.SUCCESS;
	}

	public String saveDomain() {
		IDPBroker broker = new IDPBroker();
		broker.saveDomain(idp);
		return ActionSupport.SUCCESS;
	}

	public String loadDomain() {
		this.setLabel("Update");
		this.setAction("EditDomain");
		this.setReadonly(true);
		HttpServletRequest request = ServletActionContext.getRequest();
		Long id = Long.parseLong(request.getParameter("domainId"));
		IDPBroker broker = new IDPBroker();
		idp = broker.getDomainById(id);
		return ActionSupport.SUCCESS;
	}

	public String addDomainForm() {
		this.setLabel("Add");
		this.setAction("AddDomain");
		this.setReadonly(false);
		return ActionSupport.SUCCESS;
	}

	@Override
	public IdentityProvider getModel() {
		return idp;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

}
