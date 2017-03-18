package com.convergent.model;

import java.io.Serializable;

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
@Table(name = "Image")
public class Image implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Long imageId;
	
	private Long submissionId;
	
	private String imageBlobKey=new String();

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public Long getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}

	public void setImageBlobKey(String imageBlobKey) {
		this.imageBlobKey = imageBlobKey;
	}

	public String getImageBlobKey() {
		return imageBlobKey;
	}

	@Override
	public String toString() {
		return "Image [imageBlobKey=" + imageBlobKey + ", imageId=" + imageId + ", submissionId=" + submissionId + "]";
	}

	
}