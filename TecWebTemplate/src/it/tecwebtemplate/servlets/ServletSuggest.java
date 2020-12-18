package it.tecwebtemplate.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import it.tecwebtemplate.beans.FeedDB;

public class ServletSuggest extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() {
		FeedDB FeedDB = (FeedDB)this.getServletContext().getAttribute("FeedDB");
		if ( FeedDB == null ){
			
			// creiamo un novo FeedDB solo se non esiste ancora
			FeedDB = new FeedDB();
			this.getServletContext().setAttribute("FeedDB", FeedDB);
		}
	}
	
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

		String startsWith = request.getParameter("startsWith");
		if ( startsWith == null || startsWith.length() == 0 ){
			return;
		}
		
		FeedDB FeedDB = (FeedDB)this.getServletContext().getAttribute("FeedDB");
		List<String> categories = FeedDB.getCategories(startsWith);
		
		if ( categories == null || categories.size() == 0 ){
			return;
		}
		
		String category = categories.get(0);

		PrintWriter out = response.getWriter();
		out.write(category);

	}
}
