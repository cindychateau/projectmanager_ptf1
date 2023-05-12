package com.codingdojo.cynthia.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="First name is obligatory")
	private String firstName;
	
	@NotEmpty(message="Last name is obligatory")
	private String lastName;
	
	@NotEmpty(message="Email is obligatory")
	@Email(message="Please enter a valid email")
	private String email;
	
	@NotEmpty(message="Password is obligatory")
	@Size(min=6, message="Password must be at least 6 characters")
	private String password;
	
	@Transient //No guarda el dato DB
	@NotEmpty(message="Confirm is obligatory")
	@Size(min=6, message="Confirm must be at least 6 characters")
	private String confirm;
	
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;
	
	@OneToMany(mappedBy="lead", fetch=FetchType.LAZY)
	private List<Project> myProjects; //Los proyectos que cree
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="projects_has_users",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="project_id")
			)
	private List<Project> projectsJoined; //Los proyectos a los que me uní

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
	
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

	public List<Project> getMyProjects() {
		return myProjects;
	}

	public void setMyProjects(List<Project> myProjects) {
		this.myProjects = myProjects;
	}

	public List<Project> getProjectsJoined() {
		return projectsJoined;
	}

	public void setProjectsJoined(List<Project> projectsJoined) {
		this.projectsJoined = projectsJoined;
	}
    
    
	
}
