package com.in28minutes.rest.webservices.restfulwebservices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.rest.webservices.restfulwebservices.helloworld.HelloWorldBean;

//Tell spring this is a controller

@RestController
public class HelloWorldController {
	
	//GET Method
	//URI - /hello-world
	//method - return "Hello World"

	@RequestMapping(method = RequestMethod.GET, path = "hello-world")
	
	//@GETMapping(path = "hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	
	//returning a bean back which is automatically getting converted into a json
	@GetMapping(path = "hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	//returning a bean back with a path variable
	//hello-world/path-variable/in28minutes
		@GetMapping(path = "/hello-world/path-variable/{name}")
		public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
			return new HelloWorldBean(String.format("Hello World,%s", name));
		}
}
