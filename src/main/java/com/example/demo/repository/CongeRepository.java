package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Conge;
import com.example.demo.model.Personne;

public interface CongeRepository extends JpaRepository<Conge, Long>{
	
	@Query("SELECT c FROM Conge c WHERE c.employe.id = :employeeId")
	List<Conge> findByEmploye(@Param("employeeId") Long employeeId);

    List<Conge> findByEmployeAndState(String empName, String state);
    List<Conge> findByState(String state);
	long countByState(String upperCase);
	}
