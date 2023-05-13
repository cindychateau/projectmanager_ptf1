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

import com.codingdojo.cynthia.models.Project;
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
		
		//Pendiente: Lista de proyectos a los que pertenece mi usuario
		
		//Pendiente: Lista de proyectos a los que NO pertenece mi usuario
		
		return "dashboard.jsp";
		
	}
	
	@GetMapping("/new")
	public String newProject(HttpSession session,
							@ModelAttribute("project") Project project) {
		/*====Revisa que mi usuario haya iniciado sesión====*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*====Revisa que mi usuario haya iniciado sesión====*/
		
		return "new.jsp";
		
	}
	
	@PostMapping("/create")
	public String createProject(@Valid @ModelAttribute("project") Project project,
								BindingResult result,
								HttpSession session) {
		/*====Revisa que mi usuario haya iniciado sesión====*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*====Revisa que mi usuario haya iniciado sesión====*/
		
		//revisamos errores
		if(result.hasErrors()) {
			return "new.jsp";
		} else {
			//Guardamos el proyecto
			Project newProject = service.saveProject(project);
			
			//Agregar el nuevo proyecto a la lista de proyectos que me uní
			User myUser = service.findUser(userInMethod.getId()); //Obtenemos el objeto de usuario
			//Agregando a la lista de proyetos unidos el nuevo proyecto
			myUser.getProjectsJoined().add(newProject); 
			service.saveUser(myUser);
			
			return "redirect:/dashboard";
		}
		
		
	}
	
	
}
