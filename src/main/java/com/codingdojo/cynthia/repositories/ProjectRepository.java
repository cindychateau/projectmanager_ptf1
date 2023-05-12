package com.codingdojo.cynthia.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cynthia.models.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	//Lista de Projectos que incluyen a una persona
	
	//Lista de Projectos que NO incluyan a la persona
	
	
}
