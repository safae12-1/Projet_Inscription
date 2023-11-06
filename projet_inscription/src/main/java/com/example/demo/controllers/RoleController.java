package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.entities.Role;
import com.example.demo.services.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
	@Autowired
	RoleService roleService;
	@GetMapping
	public List<Role> findAllRole(){
		return roleService.findAll();	
	}
	
	/*@GetMapping("/{id}")
	public Role findRoleById(@PathVariable Long id) {
		return roleService.findById(id);
	}*/
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findRoleById(@PathVariable Long id) {
		Role role =roleService.findById(id);
		if(role==null) {
			return new ResponseEntity<Object>("role avec id "+id+" n'existe pas", HttpStatus.BAD_REQUEST);
		}
		else {
			return ResponseEntity.ok(role);
		}
	}
	@PostMapping
	public Role createRole(@RequestBody Role role) {
		role.setId(0L);
		return roleService.create(role);
	}
	
	/*@PutMapping("/{id}")
	public Role updateRole(@PathVariable Long id,@RequestBody Role role) {
		role.setId(id);
		return roleService.update(role);
	}*/
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateRole(@PathVariable Long id,@RequestBody Role role) {
		if(roleService.findById(id)==null){
			return new ResponseEntity<Object>("role avec id "+id+" n'existe pas",HttpStatus.BAD_REQUEST);
		}
		else {
			role.setId(id);
			roleService.update(role);
			return ResponseEntity.ok("mise a jour avec succes");
			}	
	}
	
	/*@DeleteMapping("/{id}")
	public void deleteRole(@PathVariable Long id) {
		Role role=roleService.findById(id);
		roleService.delete(role);
	}*/
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteRole(@PathVariable Long id) {
		Role role=roleService.findById(id);
		if(role==null) {
			return new ResponseEntity<Object>("role avce id "+id+" n'existe pas",HttpStatus.BAD_REQUEST);
		}
		else {
			roleService.delete(role);
			return ResponseEntity.ok("role a été bien supprimé");
		}
		
	}
}
