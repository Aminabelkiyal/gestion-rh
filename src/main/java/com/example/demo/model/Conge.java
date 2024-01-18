package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
public class Conge {
    @Id
    @GeneratedValue
    private Long id;
     @ManyToOne
    private Personne employe;
    private String description; 
    private String comment; 
    private String type;
    private int nbrJours;
    private String state="pending";
    private Date dateDebut;

    public Conge() {
    }

    public Conge(Personne employe, String type, int nbrJours) {
        this.employe = employe;
        this.type = type;
        this.nbrJours = nbrJours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Personne getEmploye() {
        return employe;
    }

    public void setEmploye(Personne employe) {
        this.employe = employe;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getNbrJours() {
        return nbrJours;
    }

    public void setNbrJours(int nbrJours) {
        this.nbrJours = nbrJours;
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
