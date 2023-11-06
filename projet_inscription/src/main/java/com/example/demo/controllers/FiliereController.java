package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Filiere;
import com.example.demo.services.FiliereService;

@RestController
@RequestMapping("api/v1/filieres")//détermine le point de départ de l'URL pour toutes les méthodes du contrôleur,
public class FiliereController {

	@Autowired
    FiliereService filiereService;
	@GetMapping
	public List<Filiere> findAllFiliere(){
		return filiereService.findAll();
	}
	
	/*
	@GetMapping("/{id}")
	public Filiere findFiliereById(@PathVariable Long id) {
		return filiereService.findById(id);
	}
	*/
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findFiliereById(@PathVariable Long id) {
		Filiere filiere =filiereService.findById(id);
		if(filiere==null) {
			return new ResponseEntity<Object>("filiere avec id "+id+" n'existe pas ",HttpStatus.BAD_REQUEST);
		}
		else {
			return ResponseEntity.ok(filiere);
		}
	}
	
	@PostMapping
	public Filiere createFiliere(@RequestBody Filiere filiere) {
		filiere.setId(0L);//wakha mfhmtch 3lch hadi 
		return filiereService.create(filiere);
	}
	
	/*@PutMapping("/{id}")
	public Filiere updateFiliere(@PathVariable Long id,@RequestBody Filiere filiere) {
		filiere.setId(id);// hadak id liknkhdoo knfexiwh f contenu de filiere(hnt hna kndiro ghyr contenu mkndiroch id thowa ,id kndiroh f url) libghyn ndiro jdida bch fch n3yto 3la update onsfto liha filiere aymchy m3aha hta id lifixina fiha donc anmodifiw nif hadik li3ytna 3liha b id
		return filiereService.update(filiere);
	}
	*/
	@PutMapping("/{id}")//khs darori ndiro test sinon l3tina chy id g3ma kin obghyna nmodifiwh kicreyi lina w7d jdid
	public ResponseEntity<Object> updateFiliere(@PathVariable Long id,@RequestBody Filiere filiere) {
		if(filiereService.findById(id)==null) {
			return new ResponseEntity<Object>("filiere avec id "+id+" n'existe pas ",HttpStatus.BAD_REQUEST);
		}
		else {
			filiere.setId(id);// hadak id liknkhdoo knfexiwh f contenu de filiere(hnt hna kndiro ghyr contenu mkndiroch id thowa ,id kndiroh f url) libghyn ndiro jdida bch fch n3yto 3la update onsfto liha filiere aymchy m3aha hta id lifixina fiha donc anmodifiw nif hadik li3ytna 3liha b id
			filiereService.update(filiere);
			return ResponseEntity.ok("mise a jour avec succes");

		}
		
	}
	
	/*@PostMapping("/{id}")
	public void deleteFiliere(@PathVariable Long id) {
	
		Filiere filiere =filiereService.findById(id);
		filiereService.delete(filiere);
	}*/
	@PostMapping("/{id}")
	public ResponseEntity<Object> deleteFiliere(@PathVariable Long id) {
		Filiere filiere =filiereService.findById(id);
		if(filiere==null) {
			return new ResponseEntity<Object>("filiere avec id "+id+" n'existe pas",HttpStatus.BAD_REQUEST);
		}
		else {
			filiereService.delete(filiere);
			return ResponseEntity.ok("filiere a été bien supprimé");
		}
		
	}
}
