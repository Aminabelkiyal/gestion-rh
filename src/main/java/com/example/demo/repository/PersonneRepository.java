package com.example.demo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Personne;

public interface PersonneRepository extends JpaRepository<Personne, Long> {
	// Personne findByNom(String nom);
	List<Personne> findByNom(String nom);

	// pour afficher que les etudiant ou bien les professeurs ou bien les surveillants
	@Query(value = "SELECT * FROM personne WHERE dtype = :type", nativeQuery = true)
	List<Personne> findUsersByClasse(@Param("type") String type);
	
	 Personne findByEmail(String email);
	    Optional<Personne> findOneByEmailAndPassword(String email, String password);
	    @Query("SELECT COUNT(p) FROM Personne p")
	    long countPersonne();
	    
	    @Query("SELECT COUNT(p) FROM Personne p WHERE p.role = 'admin'")
	    long countPersonneAdmin();
	    
	    @Query("SELECT COUNT(p) FROM Personne p WHERE p.role = 'employe'")
	    long countPersonneEmp();
}