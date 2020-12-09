package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.UserDTO;
import com.revature.models.LoginHelper;
import com.revature.services.LoginService;
import com.revature.services.LoginServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private LoginService ls; 
	
    public LoginServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if (session != null ) {
			response.sendRedirect("portal");
		} else {
			request.getRequestDispatcher("index.html").forward(request, response);
		}
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		
		// if session is not null, show home page for that user
		if (session != null ) {
			request.getRequestDispatcher("portal.html").forward(request, response);
		} else {
			// if session is null, check login post info
			ls = new LoginServiceImpl();
			ObjectMapper om = new ObjectMapper();
			
			LoginHelper lh = om.readValue(request.getReader(), LoginHelper.class);
			
			String username = lh.getUsername();
			String password = lh.getPassword();
			//String username = request.getParameter("username");
			//String password = request.getParameter("password");
			
			UserDTO udto = ls.login(username, password);
			
			// if user exists, login user
			if (udto != null) {
				session = request.getSession();
				session.setAttribute("user_id", udto.getUserId());
				session.setAttribute("username", udto.getUsername());
				session.setAttribute("role", udto.getRole());
				
				response.sendRedirect("portal");
			} else {
				response.setStatus(204);
			}
			
		}
		
		
	}

}
