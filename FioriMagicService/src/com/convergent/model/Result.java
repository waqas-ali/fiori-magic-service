package com.convergent.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author susanta
 */
@Entity
@Table(name = "Result")
public class Result implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Long resultId;

	
	private String resultBlobKey;

	private Long submissionId;

	

	
	private Date createdDate;


	
	private String vendor;
	
	
	private Double total;
	
	
	private Double tax;
	
	
	private String currency;
	
	
	private String category;

	public Long getResultId() {
		return resultId;
	}

	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}

	public String getResultBlobKey() {
		return resultBlobKey;
	}

	public void setResultBlobKey(String resultBlobKey) {
		this.resultBlobKey = resultBlobKey;
	}

	public long getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}


	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Result [category=" + category + ", createdDate=" + createdDate + ", currency=" + currency + ", resultBlobKey=" + resultBlobKey + ", resultId=" + resultId
				 + ", submissionId=" + submissionId + ", tax=" + tax + ", total=" + total + ", vendor=" + vendor + "]";
	}
}