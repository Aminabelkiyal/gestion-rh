package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Login;
import com.example.demo.model.Personne;
import com.example.demo.repository.PersonneRepository;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PersonneController {

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login loginRequest) {
        try {
            Personne user = personneRepository.findByEmail(loginRequest.getEmail());

            if (user == null) {
                // Return a JSON response for invalid credentials
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Invalid credentials\"}");
            }

            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                // Return the Personne object directly in a JSON response
                return ResponseEntity.ok(user);
            } else {
                // Return a JSON response for invalid credentials
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Invalid credentials\"}");
            }
        } catch (Exception e) {
            // Return a JSON response for login failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Login failed\"}");
        }
    }


   
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Personne newUser) {
        try {
            String hashedPassword = passwordEncoder.encode(newUser.getPassword());
            newUser.setPassword(hashedPassword);

            personneRepository.save(newUser);

            // Return a JSON response for successful registration
            return ResponseEntity.ok("{\"message\": \"User registered successfully\"}");
        } catch (Exception e) {
            // Return a JSON response for registration failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Registration failed\"}");
        }
    }
    
	@GetMapping("/personne")
	List<Personne> getAllUsers() {
		return personneRepository.findAll();
	}
	
	@PutMapping("/personne/{id}")
	public ResponseEntity<?> updatePersonne(@PathVariable Long id, @RequestBody Personne updatedPersonne) {
	    try {
	        Personne existingPersonne = personneRepository.findById(id).orElse(null);

	        if (existingPersonne != null) {
	            existingPersonne.setNom(updatedPersonne.getNom());
	            existingPersonne.setPrenom(updatedPersonne.getPrenom());
	            existingPersonne.setEmail(updatedPersonne.getEmail());
	            existingPersonne.setRole(updatedPersonne.getRole());
	            existingPersonne.setCongeDays(updatedPersonne.getCongeDays());
	            
	          // Check if the new password is provided
	            if (updatedPersonne.getPassword() != null && !updatedPersonne.getPassword().isEmpty()) {
	                String hashedPassword = passwordEncoder.encode(updatedPersonne.getPassword());
	                existingPersonne.setPassword(hashedPassword);     
	                
	            }
	            
	            personneRepository.save(existingPersonne);

	            // Return a JSON response for successful update
	            return ResponseEntity.ok("{\"message\": \"Personne updated successfully\"}");
	        } else {
	            // Return a JSON response for Personne not found
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Personne not found\"}");
	        }
	    } catch (Exception e) {
	        // Return a JSON response for update failure
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Update failed\"}");
	    }
	}


	@DeleteMapping("/personne/{id}")
	public ResponseEntity<?> deletePersonne(@PathVariable Long id) {
	    try {
	        if (personneRepository.existsById(id)) {
	            personneRepository.deleteById(id);
	            // Return a JSON response for successful deletion
	            return ResponseEntity.ok("{\"message\": \"Personne deleted successfully\"}");
	        } else {
	            // Return a JSON response for Personne not found
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Personne not found\"}");
	        }
	    } catch (Exception e) {
	        // Return a JSON response for deletion failure
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Deletion failed\"}");
	    }
	}

	@GetMapping("/total-count")
    public ResponseEntity<Long> getTotalCount() {
        long totalCount = personneRepository.countPersonne();
        return ResponseEntity.ok(totalCount);
    }
	

	@GetMapping("/total-count/admin")
    public ResponseEntity<Long> getAdminCount() {
        long totalCount = personneRepository.countPersonneAdmin();
        return ResponseEntity.ok(totalCount);
    }
	
	@GetMapping("/total-count/emp")
    public ResponseEntity<Long>getEmpCount() {
        long totalCount = personneRepository.countPersonneEmp();
        return ResponseEntity.ok(totalCount);
    }
}
