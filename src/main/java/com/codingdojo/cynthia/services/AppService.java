package com.codingdojo.cynthia.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.cynthia.models.Project;
import com.codingdojo.cynthia.models.User;
import com.codingdojo.cynthia.repositories.ProjectRepository;
import com.codingdojo.cynthia.repositories.UserRepository;

@Service
public class AppService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	public User register(User newUser, BindingResult result) {
		
		//Revisamos que el correo que recibimos no exista en nuestra tabla de usuarios
		String email = newUser.getEmail();
		User isUser = userRepo.findByEmail(email); //NULL o Objeto Usuario
		if(isUser != null) {
			//El correo ya está registrado
			//result.rejectValue("email", "Unique", "The email is already in use");
			result.rejectValue("email", "Unique", "The email is already in use");
		}
		
		//Comparamos contraseñas 
		String password = newUser.getPassword();
		String confirm = newUser.getConfirm();
		if(!password.equals(confirm)) { //! -> Lo contrario
			//result.rejectValue("confirm", "Matches", "The passwords don't match");
			result.rejectValue("confirm", "Matches", "The passwords don't match");
		}
		
		//Si NO existe error, guardamos!
		if(result.hasErrors()) {
			return null;
		} else {
			//Encriptamos contraseña
			//String contra_encriptada = BCrypt.hashpw(contrasena, BCrypt.gensalt());
			String pass_encript = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
			newUser.setPassword(pass_encript);
			return userRepo.save(newUser);
		}	
		
	}
	
	public User login(String email, String password) {
		
		//Buscar que el correo recibido esté en BD
		User userExists = userRepo.findByEmail(email); //NULL o Objeto de User
		if(userExists == null) {
			return null;
		}
		
		//Comparamos contraseñas
		//BCrypt.checkpw(Contraseña NO encriptada, Contraseña encriptada) -> True o False
		if(BCrypt.checkpw(password, userExists.getPassword())) {
			return userExists;
		} else {
			return null;
		}
		
	}
	
	/*Guardamos proyecto*/
	public Project saveProject(Project project) {
		return projectRepo.save(project);
	}
	
	/*Encontrar un usuario en base a su id*/
	public User findUser(Long id) {
		return userRepo.findById(id).orElse(null);
	}
	
	/*Guarda cambios en usuario*/
	public User saveUser(User user) {
		return userRepo.save(user);
	}
	
	/*Regresa lista de Proyectos en los que un usuario está*/
	public List<Project> findMyJoinedProjects(User userInSession) {
		return projectRepo.findByUsersJoinedContainsOrderByTitleDesc(userInSession);
	}
	
	/*Regresa lista de Proyectos en los que un usuario NO está */
	public List<Project> findOtherProjects(User userInSession) {
		return projectRepo.findByUsersJoinedNotContains(userInSession);
	}
	
	/*Regrese un objeto de proyecto en base a su id*/
	public Project findProject(Long id) {
		return projectRepo.findById(id).orElse(null);
	}
	
	/*Usuario se une a proyecto*/
	public void joinProject(Long userId, Long projectId) {
		User myUser = findUser(userId); //Obtiene el objeto de usuario
		Project myProject = findProject(projectId); //Obtiene el objeto de proyecto
		
		myProject.getUsersJoined().add(myUser);
		projectRepo.save(myProject);
	}
	
	/*Usuario sale de proyecto*/
	public void leaveProject(Long userId, Long projectId) {
		User myUser = findUser(userId); //Obtiene el objeto de usuario
		Project myProject = findProject(projectId); //Obtiene el objeto de proyecto
		
		myProject.getUsersJoined().remove(myUser);
		projectRepo.save(myProject);
	}
	
	
}
