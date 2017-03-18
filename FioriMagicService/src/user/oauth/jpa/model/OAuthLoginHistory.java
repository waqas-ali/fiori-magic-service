package user.oauth.jpa.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class OAuthLoginHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private String domainName;
	private String userId;
	private Date loginDate;
	
	public OAuthLoginHistory(String domain, String userId) {
		this.setDomainName(domain);
		this.setUserId(userId);
		this.setLoginDate(new Date());
	}
	
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domain) {
		this.domainName = domain;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public Key getKey() {
		return key;
	}

}
