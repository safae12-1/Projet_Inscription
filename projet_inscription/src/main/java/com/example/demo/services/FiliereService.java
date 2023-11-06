package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IDao;
import com.example.demo.entities.Filiere;
import com.example.demo.repository.FiliereRepository;

@Service
public class FiliereService  implements IDao<Filiere>{

	@Autowired
	FiliereRepository filiereRepository;
	@Override
	public Filiere create(Filiere o) {
		return filiereRepository.save(o);
	
	}

	@Override
	public boolean delete(Filiere o) {
		try {
			filiereRepository.delete(o);
			return true;
		}
		catch(Exception e) {
			return false;
		}
		
	}

	@Override
	public Filiere update(Filiere o) {
		return filiereRepository.save(o);
	}

	@Override
	public List<Filiere> findAll() {
		return filiereRepository.findAll();
		
	}

	@Override
	public Filiere findById(Long id) {
		return filiereRepository.findById(id).orElse(null); //return de findById()==>Optional<Filiere> (cad objet peut etre present ou absent) ==>cest pour cela on doit ajouter .orElse(null) cest a dire si l'objet n'exesite pas il nous envoie valeur null		
	}
	

}
