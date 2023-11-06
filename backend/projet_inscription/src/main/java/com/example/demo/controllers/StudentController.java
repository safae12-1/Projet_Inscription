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

import com.example.demo.entities.Student;
import com.example.demo.services.StudentService;

@RestController
@RequestMapping("/spi/student")
public class StudentController {
@Autowired 
StudentService studentService;

@GetMapping
public List<Student> getAllStudent(){
	return studentService.findAll();
	}

/*@GetMapping("/{id}")
public Student getStudentById(@PathVariable Long id) {
	return studentService.findById(id);
}*/
@GetMapping("/{id}")
public ResponseEntity<Object> getStudentById(@PathVariable Long id) {
	
	Student student =studentService.findById(id);
	if(student==null) {
		return new ResponseEntity<Object>("etudiant avec id "+id+" n'existe pas",HttpStatus.BAD_REQUEST);
	}
	else {
		return ResponseEntity.ok(student);
	}
}
@PostMapping
public Student createStudent(@RequestBody Student student) {
	student.setId(0L);
	return studentService.create(student);
}

/*@PutMapping("/{id}")
public Student updateStudent(@PathVariable Long id,@RequestBody Student student) {
	student.setId(id);
	return studentService.update(student);
}*/

@PutMapping("/{id}")
public ResponseEntity<Object> updateStudent(@PathVariable Long id,@RequestBody Student student) {
	if(studentService.findById(id)==null) {
		return new ResponseEntity<Object>("etudiant avce id "+id+" n'existe pas",HttpStatus.BAD_REQUEST);
	}
	else {
		student.setId(id);
		studentService.update(student);
		return ResponseEntity.ok("mise a jour avec succes");
	}
}

/*@PostMapping("/{id}")
public void deleteStudent(@PathVariable Long id) {
	Student student =studentService.findById(id);
	studentService.delete(student);
}*/
@DeleteMapping("/{id}")
public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
	Student student =studentService.findById(id);
	if(student==null) {
		return new ResponseEntity<Object>("etudiant avec id "+id+" n'existe pas",HttpStatus.BAD_REQUEST);
	}
	else {
		studentService.delete(student);
		return ResponseEntity.ok("etudiant a été bien supprimé");
	}
	
}
}

