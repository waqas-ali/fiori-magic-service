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
 *
 */
@Entity
@Table(name = "Submission")
public class Submission implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public enum Status{READY,PROCESSING,EXTENDED_PROCESSING,COMPLETED};
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Long submissionId;
	private String status = Status.PROCESSING.toString();
	private Long userId;
	private Date createdDate;
	private int count;
	private String blobUrls;
	private Date completedDate=null;
	private String trip;

	
	
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Date getCompletedDate() {
		return completedDate;
	}


	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}



	public Long getSubmissionId() {
		return submissionId;
	}


	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}


	public String getTrip() {
		return trip;
	}


	public void setTrip(String trip) {
		this.trip = trip;
	}

	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	@Override
	public String toString() {
		return "Submission [completedDate=" + completedDate + ", createdDate=" + createdDate + ", status=" + status + ", submissionId=" + submissionId + ", trip=" + trip + ", userId=" + userId + "]";
	}


	public String getBlobUrls() {
		return blobUrls;
	}


	public void setBlobUrls(String blobUrls) {
		this.blobUrls = blobUrls;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

}