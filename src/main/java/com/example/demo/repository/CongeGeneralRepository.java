package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Conge;
import com.example.demo.model.CongeGeneral;

public interface CongeGeneralRepository extends JpaRepository<CongeGeneral, Long>{

}
