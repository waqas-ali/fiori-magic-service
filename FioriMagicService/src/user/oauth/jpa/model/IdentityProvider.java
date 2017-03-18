package user.oauth.jpa.model;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Domain")
public class IdentityProvider implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic(optional = false)
	private Long id;
	private String domainName;
	private int noOfLicense;
	private int userActiveDuration;
	private int activeUsers;
	private Date licenseExpireDate;
	private String notes;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String url;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String domainIdCode;
	
	public IdentityProvider(String name, String url, int nol, int time,Date d,String notes) {
		this.setDomainName(name);
		this.setUrl(url);
		this.setNoOfLicense(nol);
		this.setUserActiveDuration(time);
		this.setLicenseExpireDate(d);
		this.setNotes(notes);
	}
	
	public IdentityProvider() {
		
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getNoOfLicense() {
		return noOfLicense;
	}
	
	public void setNoOfLicense(int noOfLicense) {
		this.noOfLicense = noOfLicense;
	}
	
	public int getUserActiveDuration() {
		return userActiveDuration;
	}
	
	public void setUserActiveDuration(int expireTime) {
		this.userActiveDuration = expireTime;
	}
	
	public String getDomainIdCode() {
		return domainIdCode;
	}

	public int getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(int activeUsers) {
		this.activeUsers = activeUsers;
	}

	public Date getLicenseExpireDate() {
		return licenseExpireDate;
	}

	public void setLicenseExpireDate(Date licenseExpireDate) {
		this.licenseExpireDate = licenseExpireDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public void setDomainIdCode(String domainIdCode) {
		this.domainIdCode = domainIdCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
