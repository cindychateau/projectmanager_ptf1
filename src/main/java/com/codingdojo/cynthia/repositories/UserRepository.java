package com.codingdojo.cynthia.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cynthia.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	//SELECT * FROM users WHERE email = <EMAIL QUE RECIBIMOS>
	User findByEmail(String email); 
}