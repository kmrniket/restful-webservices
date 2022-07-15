package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
//import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	
	@Autowired
	private UserDaoService service;
	
	//retriveAllUsers   /GET/users
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	//retrieveUser(int id)  /GET/users/{id}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUSer(@PathVariable int id) {
		User user =  service.findOne(id);
		if (user==null)
			throw new UserNotFoundException("id-"+id);
		
		EntityModel<User> model = EntityModel.of(user);
		
		WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		model.add(linkToUsers.withRel("all-users"));
		
		return model;
	}

	//POST request
	//input - details of user
	//output - CREATED & Return the created URI 
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user ) {
		User savedUser = service.save(user);
		
		//status of CREATED
		//URI of the created resource (say /user/4)
		// /user/{id}    savedUser.getId 
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		//return new ResponseEntity<>(user, HttpStatus.CREATED);
		return ResponseEntity.created(location).build();
	}
	
	//DELETE request
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user =  service.deleteById(id);
		if (user==null)
			throw new UserNotFoundException("id-"+id);
		
	}
}
