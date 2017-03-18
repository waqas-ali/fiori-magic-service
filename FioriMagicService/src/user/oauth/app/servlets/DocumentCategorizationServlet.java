package user.oauth.app.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.oauth.data.broker.WordsPointsBroker;

@SuppressWarnings("serial")
public class DocumentCategorizationServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.sendRedirect("/html/categorization_input.html");
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String sentence = req.getParameter("sentance");
		WordsPointsBroker broker = new WordsPointsBroker();
		String result = broker.pointsCalculation(sentence);
		resp.sendRedirect("/jsp/categorization_result.jsp?result="+result);
	}
}
