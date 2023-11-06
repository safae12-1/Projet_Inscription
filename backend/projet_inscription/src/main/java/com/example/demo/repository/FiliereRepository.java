package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Filiere;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere,Long > {

}
