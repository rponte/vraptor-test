package br.com.caelum.vraptor.test.controllers;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.test.models.Task;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class TestController {
	
	@Inject
	private Result result;
	@Inject
	private HttpSession session;
	@Inject
	private Validator validator;

	public void test(){
		result.include("name","vraptor");
	}
	
	public void test2(){
		result.forwardTo(this).test();
	}
	
	@Post
	public void test3(){
		result.redirectTo(RedirectedController.class).test();
	}
	
	@Post
	public void test4(Task task){
		result.include("task",task);
	}
	
	@Post
	public void test5(){
		Task task = new Task();
		task.setDescription("test");
		session.setAttribute("task", task);
	}	
	
	@Get
	public void test6() {
		result.include("taskInSession",session.getAttribute("task"));
	}
	
	@Post
	public void test7(@Valid Task task){
	}	
	
	@Post
	public void test8(){
	}
	
	@Post
	public void withValidatorError() {
		validator.add(new SimpleMessage("error", "error"));
		validator.onErrorRedirectTo(this).test();
	}
	
	@Post
	public void buggedMethod() {
		throw new RuntimeException();
	}	
}
