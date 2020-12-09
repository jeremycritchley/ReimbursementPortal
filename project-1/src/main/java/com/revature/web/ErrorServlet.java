package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErrorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
		HttpSession session = request.getSession();
		try {
			if (session.getAttribute("username") != null) {
				request.getRequestDispatcher("portal.html").forward(request, response);
			} else {
				session.invalidate();
				response.sendRedirect("login");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		doGet(request, response);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		doGet(request, response);
	}
	
}
