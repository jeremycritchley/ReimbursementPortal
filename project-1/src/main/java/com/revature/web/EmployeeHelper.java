package com.revature.web;

import java.io.IOException;
import java.util.List;

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
import com.revature.services.EmployeeServiceImpl;

public class EmployeeHelper implements Helper {

	ObjectMapper om = new ObjectMapper();
	EmployeeServiceImpl es;
	
	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws JsonParseException, JsonMappingException, IOException, ServletException {
		HttpSession session = request.getSession(false);

		String[] URI = request.getRequestURI().replace("/project-1/", "").split("/");
		es = new EmployeeServiceImpl();
		
		if (URI.length > 1) {
			if (URI[1].equals("reims")) { // portal/reims/
				// portal/reims
				if ("POST".equals(request.getMethod())) { // portal/reims method=POST
					ReimDTO rdto = om.readValue(request.getReader(), ReimDTO.class);
					int reim_id = es.submitReim(rdto, (String) session.getAttribute("user_id"));

					if (reim_id != 0) {
						rdto.setReimId(Integer.toString(reim_id));
						response.setContentType("application/json");
						response.getWriter().println(om.writeValueAsString(rdto));
					} else {
						response.setStatus(204);
					}

				} else {
					reimsHelper(request, response);
				}
			} else if (URI[1].equals("users") && URI.length==3) { // portal/users
				
				if ("POST".equals(request.getMethod()) && URI[2].equals(session.getAttribute("user_id"))) {
					UserDTO userUpdate = om.readValue(request.getReader(), UserDTO.class);
					if (es.updateInfo(userUpdate)) {
						session.setAttribute("username", userUpdate.getUsername());
						response.setStatus(200);
					} else {
						response.setStatus(204);
					}
				} else if ("GET".equals(request.getMethod()) && URI[2].equals(session.getAttribute("user_id"))) {
					String user_id = URI[2];
					if (user_id != null) {
						UserDTO udto = es.viewByUser(user_id);
						if (udto != null) {
							response.getWriter().println(om.writeValueAsString(udto));
						} else {
							response.setStatus(400);
						}
					} else {
						request.getRequestDispatcher("portal.html").forward(request, response);
						
					}
				}
			} else if (URI[1].equals("users")){
				// serve employee home page
				request.getRequestDispatcher("/personal.html").forward(request, response);
			}
		} else {
			// serve employee home page
			
			request.getRequestDispatcher("portal.html").forward(request, response);
			
		}
	}
	
	private void reimsHelper(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException, ServletException {
		HttpSession session = request.getSession(false);
		String status = request.getParameter("status");
		if (status == null) {
			request.getRequestDispatcher("/reim-view.html").forward(request, response);
		} else if ("resolved".equals(status)) { // portal/reims/resolved
			List<ReimDTO> reims = es.viewRiemsByStatus((String) session.getAttribute("user_id"), true);
			
			if (reims != null) {
				response.setContentType("application/json");

				response.getWriter().write(om.writeValueAsString(reims));

			} else {
				response.setStatus(500);
			}
		} else if ("pending".equals(status)) { // portal/reims/pending
			List<ReimDTO> reims = es.viewRiemsByStatus((String) session.getAttribute("user_id"), false);
			if (reims != null) {
				response.setContentType("application/json");

				response.getWriter().write(om.writeValueAsString(reims));

			} else {
				response.setStatus(500);
			}
		} else if ("all".equals(status)) { // portal/reims/all
			List<ReimDTO> reims = es.viewReimsByEmployee((String) session.getAttribute("user_id"));
			if (reims != null) {
				response.setContentType("application/json");

				response.getWriter().write(om.writeValueAsString(reims));

			} else {
				response.setStatus(500);
			}
		} else if ("new".equals(status)) {
			request.getRequestDispatcher("/new-reim.html").forward(request, response);
		}
	}
}
