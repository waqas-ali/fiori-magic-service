package com.convergent.processor;

import static com.convergent.odata.DatastoreConstants.FORM_NAME;

import static com.convergent.odata.DatastoreConstants.SUBMISSION_ID_PROPNAME;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.oauth.data.broker.*;

import com.convergent.model.*;
import com.convergent.odata.DatastoreUtil;
import com.google.appengine.api.blobstore.BlobKey;

public class Notifications extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlobKey blobKey = DatastoreUtil.getUploadedBlobs(req).get(FORM_NAME);
		final String submissionId = req.getParameter(SUBMISSION_ID_PROPNAME);
		// get the image By submissionId
		ImageBroker imgBroker = new ImageBroker();
		SubmissionBroker subBroker = new SubmissionBroker();
		
		Image image = imgBroker.getImageBySubmissionId(Long.parseLong(submissionId));
		image.setImageBlobKey(blobKey.getKeyString());
		imgBroker.updateImage(image);
		
		Submission submission=subBroker.getSubmissionById(new Long(submissionId));
		submission.setStatus(Submission.Status.PROCESSING.toString());
		subBroker.updateSubmission(submission);
		
		String jsonResponse="{\"success\":\"Uploaded successfully.\"}";
		resp.setContentType(req.getContentType());
		resp.getWriter().write(jsonResponse);
		return;
	}
}