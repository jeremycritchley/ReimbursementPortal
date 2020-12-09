package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;
import com.revature.services.ManagerServiceImpl;

public class ManagerHelper implements Helper {

	ObjectMapper om = new ObjectMapper();
	ManagerServiceImpl ms = new ManagerServiceImpl();
	String[] URI;
	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, ServletException {
		URI = request.getRequestURI().replace("/project-1/", "").split("/");
		HttpSession session = request.getSession(false);
		if (URI.length > 1) {
			if (URI[1].equals("reims")) {
				if (URI.length == 2) {
					reimsHelper(request, response);
				} else if (URI[2].equals("approve") || URI[2].equals("deny")) {
					ReimDTO rdto = om.readValue(request.getReader(), ReimDTO.class);
					if (URI[2].equals("approve")) {
						ms.approveReim(rdto, (String) session.getAttribute("user_id"));
					} else {
						ms.denyReim(rdto, (String) session.getAttribute("user_id"));
					}
				
				} else if (URI[2].equals("resolve")) {
					request.getRequestDispatcher("/resolve-reims.html").include(request, response);
				} else {
					
					request.getRequestDispatcher("portal.html").forward(request, response);
					
				}
			} else if (URI[1].equals("users")) {
				usersHelper(request, response);
			} else {
				// serve manage home page
				request.getRequestDispatcher("portal.html").forward(request, response);
			}
		} else {
			// serve manager home page
			request.getRequestDispatcher("portal.html").forward(request, response);
		}
		
	}

	private void usersHelper(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException, ServletException {
		HttpSession session = request.getSession(false);
		String param = request.getParameter("id");
		
		if (param != null ) {
			if (!param.equals("0")) {
				// get user by ID
				// if user is manager, don't include a response
				
				// else, get the users reims and include them in response
				UserDTO user = ms.viewByUser(param);
				if (user != null && param.equals(session.getAttribute("user_id"))) {
					response.getWriter().write(om.writeValueAsString(user));
				}
				
				if (user.getRole().equals("EMPLOYEE")) {
					response.getWriter().write(om.writeValueAsString(ms.viewReimsByEmployee(param)));
				}
			} else {
				response.getWriter().write(om.writeValueAsString(ms.viewAllEmployees()));
			}
			
		} else if ("GET".equals(request.getMethod())) {
			// get all employees
			if (URI.length == 3) {
				if (URI[2].equals("all")) {
					request.getRequestDispatcher("/view-employees.html").include(request, response);
				} else {
					try {
						
						response.getWriter().write(om.writeValueAsString(ms.viewByUser(URI[2])));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				request.getRequestDispatcher("/personal.html").forward(request, response);
			}
			
		} else if ("POST".equals(request.getMethod())) {
			if (URI.length == 3) {
				if (URI[2].equals(session.getAttribute("user_id"))) {
					UserDTO userUpdate = om.readValue(request.getReader(), UserDTO.class);
					if (ms.updateInfo(userUpdate)) {
						session.setAttribute("username", userUpdate.getUsername());
						response.setStatus(200);
					} else {
						response.setStatus(204);
					}
				}
			}
		}
		
	}

	private void reimsHelper(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException, ServletException {
		// TODO Auto-generated method stub
		String param = null;
		if ((param = request.getParameter("status")) != null) {
			// get reims by status
			if (param.equals("resolved")) {
				response.getWriter().write(om.writeValueAsString(ms.viewRiemsByStatus("0", true)));
			} else if (param.equals("pending")){
				response.getWriter().write(om.writeValueAsString(ms.viewRiemsByStatus("0", false)));
			} else if (param.equals("all")) {
				response.getWriter().write(om.writeValueAsString(ms.viewAllReims()));
			}
			
		} else if ((param = request.getParameter("id")) != null) {
			// get reim by ID
			response.getWriter().write(om.writeValueAsString(ms.viewReimsById(param)));
		} else if ("GET".equals(request.getMethod())) {
			// get all reims
			request.getRequestDispatcher("/reim-view.html").forward(request, response);
		}
		
	}
	
}
