package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SessionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			response.setContentType("application/json");
			response.getWriter().write("{\"username\":\""+session.getAttribute("username")+"\", \"role\":\""+session.getAttribute("role")+"\", \"user_id\":\""+session.getAttribute("user_id")+"\"}");
		} else {
			response.setContentType("application/json");
			response.getWriter().write("{\"username\":null, \"role\":null}");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
