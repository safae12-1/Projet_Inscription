package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IDao;
import com.example.demo.entities.Role;
import com.example.demo.repository.RoleRepository;
@Service
public class RoleService implements IDao<Role> {

	
	@Autowired
	RoleRepository roleRepository;
	@Override
	public Role create(Role o) {
		return roleRepository.save(o);
	}

	@Override
	public boolean delete(Role o) {
		try {
			roleRepository.delete(o);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	@Override
	public Role update(Role o) {
		return roleRepository.save(o); //save joue deux roles si id n'existe pas il va le creer mais s'il est existe il va le modifier 
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(Long id) {
		return roleRepository.findById(id).orElse(null);
	}

}
