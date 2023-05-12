package com.codingdojo.cynthia.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.codingdojo.cynthia.models.User;
import com.codingdojo.cynthia.services.AppService;

@Controller
public class ProjectController {
	
	@Autowired
	private AppService service;
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session,
							Model model) {
		/*====Revisa que mi usuario haya iniciado sesión====*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*====Revisa que mi usuario haya iniciado sesión====*/
		
		return "dashboard.jsp";
		
	}
	
}
