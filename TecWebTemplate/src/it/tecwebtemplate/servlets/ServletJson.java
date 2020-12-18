package it.tecwebtemplate.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import com.google.gson.Gson;

import it.tecwebtemplate.beans.*;

public class ServletJson extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Utilizzeremo gson qui
	private Gson gson;

	@Override
	public void init() {
		
		FeedDB FeedDB = (FeedDB) this.getServletContext().getAttribute("FeedDB");
		
		if (FeedDB == null) {
			
			// creiamo un novo FeedDB solo se non esiste ancora
			FeedDB = new FeedDB();
			this.getServletContext().setAttribute("FeedDB", FeedDB);
		}

		gson = new Gson();

	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		
		// recupero il tipo di categoria cercata dai parametri della richiesta
		String category = request.getParameter("category");

		// recupero i feed corrispondenti dal database (implementato come Java Bean)
		FeedDB FeedDB = (FeedDB) this.getServletContext().getAttribute("FeedDB");
		List<Feed> feeds = FeedDB.find(category);

		// li stampo su out
		response.getWriter().println(gson.toJson(feeds.toArray(new Feed[0])));


	}
}
