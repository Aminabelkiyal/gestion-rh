package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class CongeGeneral {
    @Id
    @GeneratedValue
    private Long id;
    private String description; 
    private String type;
    private Number nbrJours;
    private Date dateDebut;

    public CongeGeneral() {
    }

    public CongeGeneral(Long employe, String type, Number nbrJours) {
        this.type = type;
        this.nbrJours = nbrJours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Number getNbrJours() {
        return nbrJours;
    }

    public void setNbrJours(Number nbrJours) {
        this.nbrJours = nbrJours;
    }


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
}
