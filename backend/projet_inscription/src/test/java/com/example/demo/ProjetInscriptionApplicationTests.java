package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;

@SpringBootTest
class ProjetInscriptionApplicationTests {

	/*
	@Test
	void contextLoads() {
	}
	*/
	
	@Autowired
	UserRepository userRepository;
	@Test
	public void testCreateUser() {
		User u1=new User();
		userRepository.save(u1);
	}
	
	@Test
	public void testFindUser(){
		User u1=userRepository.findById(2L).get();
		System.out.println(u1);
	}
	@Test 
	public void deleteUser(){
		userRepository.deleteById(3L);
		
	}
	
	@Test 
	public void testUpdateUser() {
		User u1=userRepository.findById(4L).get();
		u1.setId(1L);
		userRepository.save(u1);
	}
	@Test 
	public void findAllUser(){
		List <User> list=userRepository.findAll();
		System.out.println(list);
	}
}
