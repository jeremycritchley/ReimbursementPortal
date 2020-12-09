package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
    	final String[] URI = request.getRequestURI().replace("/project-1/", "").split("/");
    	
		// try :
		/* get session
		 if (session == null) 
		 	// route to login page
		 else
		 	if (URI.length > 1)
		 		if (URI[1] == users)
		 			// route to users servlet
		 		else (if URI[1] == reims)
		 			// route to reims
		 	else
		 		route to portal home page
		*/
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			if (URI.length > 1)
			if (!URI[0].equals("login.html")) {
				try {
					//request.getRequestDispatcher("login.html").forward(request, response);
					response.sendRedirect("/project-1/login");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //catch (ServletException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
			}
		} else if (URI.length > 1) {
			if (URI[1].equals("users")) {
				// send to users 
				
			} else if (URI[1].equals("reims")) {
				// send to reims helper 
			} else {
				try {
					response.sendRedirect("portal");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (URI[0].equals("logout")) {
			try {
				response.sendRedirect("logout");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				response.sendRedirect("portal");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		switch(URI[0]) {
		case "login":
			break;
		case "logout":
			break;
		case "portal":
			if (URI.length > 1) {
				if (URI[1].equals("users")) {
					// dispatch to users servlet
				} else if(URI[1].equals("reims")) {
					// dispatch to reims servlet
				}
			} else {
				// show home page
			}
			break;
		}
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		processRequest(request, response);
	}

}
