package com.convergent.processor;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.oauth.data.broker.ResultHistoryBroker;

import com.convergent.model.ResultHistory;
import com.convergent.odata.DatastoreUtil;

public class UnLockSubmissions extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final long LAST_HOUR=1000*60*60;
	private static final long DEFAULT_USER=1L;// system
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			ResultHistoryBroker rhBroker = new ResultHistoryBroker();
			List<ResultHistory> resultHistories= rhBroker.getLockedSubmissions();
			Date date=new Date();
			if(resultHistories!=null && resultHistories.size()>0){
				for (ResultHistory resultHistory : resultHistories) {
						if((date.getTime()- resultHistory.getStartTime().getTime())>LAST_HOUR){
							resultHistory.setUserId(DEFAULT_USER);
							resultHistory.setEndTime(date);
							resultHistory.setProcessingDuration(DatastoreUtil.getTimeDiff(date, resultHistory.getStartTime()));
							rhBroker.updateResultHistory(resultHistory);
						}
				}
			}
			
	}
	
}
