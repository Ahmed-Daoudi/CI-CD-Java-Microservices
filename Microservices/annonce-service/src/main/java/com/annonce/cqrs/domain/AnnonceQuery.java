package com.annonce.cqrs.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.util.Date;
@Setter
@Getter
@Document
public class AnnonceQuery {
    @Id
    private long id;

    private String title;
    private String domaine;
    private int nbPostes;
    private String skills;
    private String description;
    
    private long recruteur;
    
    private String mission;
    private String profil;
    private Date dateAnnonce;
    private String addrese;


    @Enumerated(EnumType.STRING)
    private AnnonceType jobType;


	@Override
	public String toString() {
		return "AnnonceCommand [id=" + id + ", title=" + title + ", domaine=" + domaine + ", nbPostes=" + nbPostes
				+ ", skills=" + skills + ", description=" + description + ", jobType=" + jobType + "]";
	}
	
}