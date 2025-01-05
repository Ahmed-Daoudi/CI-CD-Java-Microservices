package com.annonce.cqrs.command.dto;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;


import com.annonce.cqrs.domain.AnnonceType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class AnnonceCommandDto {
    private Long id;
   
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
		return "AnnonceCommandDto [id=" + id + ", title=" + title + ", domaine=" + domaine + ", nbPostes=" + nbPostes
				+ ", skills=" + skills + ", description=" + description + ", jobType=" + jobType + "]";
	}
	
	

   


}