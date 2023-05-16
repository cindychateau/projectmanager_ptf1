package com.codingdojo.cynthia.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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
		
		//Lista de proyectos a los que pertenece mi usuario
		//model.addAttribute("myProjects", service.findMyJoinedProjects(userInMethod));
		model.addAttribute("myProjects", service.findMyJoinedProjects(userInMethod));
		
		//Lista de proyectos a los que NO pertenece mi usuario
		//model.addAttribute("otherProjects", service.findOtherProjects(userInMethod));
		model.addAttribute("otherProjects", service.findOtherProjects(userInMethod));
		
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
	
	@GetMapping("/join/{projectId}")
	public String join(@PathVariable("projectId") Long projectId,
					   HttpSession session) {
		/*====Revisa que mi usuario haya iniciado sesión====*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*====Revisa que mi usuario haya iniciado sesión====*/
		
		service.joinProject(userInMethod.getId(), projectId);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/leave/{projectId}")
	public String leave(@PathVariable("projectId") Long projectId,
			   			HttpSession session) {
		/*====Revisa que mi usuario haya iniciado sesión====*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*====Revisa que mi usuario haya iniciado sesión====*/
		
		service.leaveProject(userInMethod.getId(), projectId);
		
		return "redirect:/dashboard";
	}
	
	@GetMapping("/edit/{projectId}")
	public String edit(@PathVariable("projectId") Long id,
					   @ModelAttribute("project") Project project,
					   HttpSession session,
					   Model model) {
		/*====Revisa que mi usuario haya iniciado sesión====*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*====Revisa que mi usuario haya iniciado sesión====*/
		
		Project projectEdit = service.findProject(id);
		
		//Revisamos que el lead coincida con el usuario en sesion
		if(userInMethod.getId() != projectEdit.getLead().getId()) {
			return "redirect:/dashboard";
		}
		
		//model.addAttribute("project", projectEdit);
		model.addAttribute("project", projectEdit);
		return "edit.jsp";
		
	}
	
	@PutMapping("/update")
	public String update(@Valid @ModelAttribute("project") Project project,
						 BindingResult result,
						 HttpSession session) {
		/*====Revisa que mi usuario haya iniciado sesión====*/
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		/*====Revisa que mi usuario haya iniciado sesión====*/
		
		
		if(result.hasErrors()) {
			return "edit.jsp";
		} else {
			//Los usuarios que forman parte del proyecto los agregamos de nuevo
			Project thisProject = service.findProject(project.getId());
			List <User> usersJoinedInProject = thisProject.getUsersJoined(); //Usuarios que antes se habían unido
			project.setUsersJoined(usersJoinedInProject); //Agregando al project del formulario
			service.saveProject(project);
			return "redirect:/dashboard";
		}
	}
	
	
}
