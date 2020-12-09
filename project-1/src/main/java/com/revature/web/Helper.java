package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface Helper {
	
	
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, ServletException;
	
}
