package com.codingdojo.cynthia.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cynthia.models.Project;
import com.codingdojo.cynthia.models.User;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	//Lista de Projectos que incluyen a una persona
	List<Project> findByUsersJoinedContainsOrderByTitleDesc(User user);
	
	//Lista de Projectos que NO incluyan a la persona
	List <Project> findByUsersJoinedNotContains(User user);
	
}
