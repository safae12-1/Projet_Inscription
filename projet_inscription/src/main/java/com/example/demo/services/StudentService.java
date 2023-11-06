package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IDao;
import com.example.demo.entities.Student;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService implements IDao<Student> {

	@Autowired
	StudentRepository studentRepository;
	@Override
	public Student create(Student o) {
		return studentRepository.save(o) ;
	}

	@Override
	public boolean delete(Student o) {
		try {
			studentRepository.delete(o);
			return true;
		}
		catch(Exception e) {
			return false;
		}	
	}

	@Override
	public Student update(Student o) {
		return studentRepository.save(o);
	}

	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	@Override
	public Student findById(Long id) {
		studentRepository.findById(id).orElse(null);
		return null;
	}
	

}
