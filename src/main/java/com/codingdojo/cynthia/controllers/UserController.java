package com.codingdojo.cynthia.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.cynthia.models.User;
import com.codingdojo.cynthia.services.AppService;

@Controller
public class UserController {
	
	@Autowired
	private AppService service;
	
	@GetMapping("/")
	public String index(@ModelAttribute("newUser") User newUser,
						Model model) {
		
		return "index.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser,
						   BindingResult result,
						   HttpSession session,
						   Model model) {
		
		//método en servicio
		service.register(newUser, result);
		
		if(result.hasErrors()) {
			
			return "index.jsp";
		} else {
			//Guardar sesión
			session.setAttribute("userInSession", newUser);
			return "redirect:/dashboard";
		}
		
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("email") String email,
						@RequestParam("password") String password,
						RedirectAttributes redirectAttributes,
						HttpSession session) {
		
		//enviar email y contraseña y que el servicio verifique si son correctos
		User userLogin = service.login(email, password);
		
		if(userLogin == null) {
			//Hay error
			//redirectAttributes.addFlashAttribute("error_login", "Email/Password incorrect");
			redirectAttributes.addFlashAttribute("error_login", "Email/Password incorrect");
			return "redirect:/";
		} else {
			//Guardamos en sesion
			session.setAttribute("userInSession", userLogin);
			return "redirect:/dashboard";
		}
		
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("userInSession");
		return "redirect:/";
	}
	
}