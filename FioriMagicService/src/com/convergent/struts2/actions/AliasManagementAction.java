package com.convergent.struts2.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import user.oauth.data.broker.AliasDomainBroker;
import user.oauth.data.broker.IDPBroker;
import user.oauth.jpa.model.AliasDomain;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AliasManagementAction extends ActionSupport implements ModelDriven<AliasDomain>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6452914372434408507L;
	private List<AliasDomain> aliasList = new ArrayList<AliasDomain>();
	private AliasDomain alias = new AliasDomain();
	private boolean readonly = false;
	private String label = "";
	private String action = "";
	
	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

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

	@SuppressWarnings("unchecked")
	public String showAlias() {
		AliasDomainBroker broker = new AliasDomainBroker();
		aliasList = broker.retrieveAliasDomains();
		return ActionSupport.SUCCESS;
	}
	
	public String updateAlias() {
		AliasDomainBroker broker = new AliasDomainBroker();
		broker.updateAliasByKey(alias);
		return ActionSupport.SUCCESS;
	}
	
	public String deleteAlias() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("akey");
		AliasDomainBroker broker = new AliasDomainBroker();
		Long l =new Long(name);
		broker.deleteAliasByKey(l);
		return ActionSupport.SUCCESS;
	}
	
	public String saveAlias() {
		AliasDomainBroker broker = new AliasDomainBroker();
		broker.saveAliasDomain(alias);
		return ActionSupport.SUCCESS;
	}
	public String loadAlias() {
		this.setLabel("Update");
		this.setAction("UpdateAlias");
		this.setReadonly(true);
		HttpServletRequest request = ServletActionContext.getRequest();
		String key = request.getParameter("akey");
		AliasDomainBroker broker = new AliasDomainBroker();
		alias = broker.getAliasByKey(new Long(key));
		return ActionSupport.SUCCESS;
	}

	public String addAliasForm() {
		this.setLabel("Add");
		this.setAction("SaveAlias");
		this.setReadonly(true);
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getParameter("aurl");
		alias.setUrl(url);
		return ActionSupport.SUCCESS;
	}
	public List<AliasDomain> getAliasList() {
		return aliasList;
	}

	public void setAliasList(List<AliasDomain> aliasList) {
		this.aliasList = aliasList;
	}

	public AliasDomain getAlias() {
		return alias;
	}

	public void setAlias(AliasDomain alias) {
		this.alias = alias;
	}

	@Override
	public AliasDomain getModel() {
		// TODO Auto-generated method stub
		return this.alias;
	}
	
}
