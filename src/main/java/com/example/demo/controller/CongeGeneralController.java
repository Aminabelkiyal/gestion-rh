package com.example.demo.controller;

import com.example.demo.model.CongeGeneral;
import com.example.demo.repository.CongeGeneralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/conges/general")
public class CongeGeneralController {

    private final CongeGeneralRepository congeGeneralRepository;

    @Autowired
    public CongeGeneralController(CongeGeneralRepository congeGeneralRepository) {
        this.congeGeneralRepository = congeGeneralRepository;
    }

    @GetMapping
    public ResponseEntity<List<CongeGeneral>> getAllConges() {
        List<CongeGeneral> conges = congeGeneralRepository.findAll();
        return ResponseEntity.ok(conges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CongeGeneral> getCongeById(@PathVariable Long id) {
        Optional<CongeGeneral> conge = congeGeneralRepository.findById(id);
        return conge.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CongeGeneral> createConge(@RequestBody CongeGeneral congeGeneral) {
        CongeGeneral createdConge = congeGeneralRepository.save(congeGeneral);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdConge);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CongeGeneral> updateConge(@PathVariable Long id, @RequestBody CongeGeneral updatedConge) {
        if (!congeGeneralRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedConge.setId(id);
        CongeGeneral savedConge = congeGeneralRepository.save(updatedConge);
        return ResponseEntity.ok(savedConge);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConge(@PathVariable Long id) {
        if (!congeGeneralRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        congeGeneralRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
