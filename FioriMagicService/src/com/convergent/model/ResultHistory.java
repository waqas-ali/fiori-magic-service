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
	@Table(name = "ResultHistory")
	public class ResultHistory implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Basic(optional = false)
		private Long resultHistoryId;
		
		private Long userId;
		
		private Long resultId;
		private Long submissionId;
		
		private Date startTime;
		private Date endTime=null;
		
		private String processingDuration=null;
		
		private String vendor;
		
		private Double total;
		
		private Double tax;
		
		private String currency;
		
		private String category;

		public Long getResultHistoryId() {
			return resultHistoryId;
		}

		public void setResultHistoryId(Long resultHistoryId) {
			this.resultHistoryId = resultHistoryId;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public long getResultId() {
			return resultId;
		}

		public void setResultId(Long resultId) {
			this.resultId = resultId;
		}

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
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

		public String getProcessingDuration() {
			return processingDuration;
		}

		public void setProcessingDuration(String processingDuration) {
			this.processingDuration = processingDuration;
		}


		@Override
		public String toString() {
			return "ResultHistory [category=" + category + ", currency=" + currency + ", processingDuration=" + processingDuration + ", resultHistoryId=" + resultHistoryId
					+ ", resultId=" + resultId + ", startTime=" + startTime + ", tax=" + tax + ", total=" + total + ", userId=" + userId + ", vendor=" + vendor + "]";
		}

		public Long getSubmissionId() {
			return submissionId;
		}

		public void setSubmissionId(Long submissionId) {
			this.submissionId = submissionId;
		}

		public Date getEndTime() {
			return endTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
		
}
