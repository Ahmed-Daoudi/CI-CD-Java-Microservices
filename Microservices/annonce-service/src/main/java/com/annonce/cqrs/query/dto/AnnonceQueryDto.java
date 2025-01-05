package com.annonce.cqrs.query.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.annonce.cqrs.domain.AnnonceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnonceQueryDto {
    private long id;

    private String title;
    private String domaine;
    private int nbPostes;
    private String skills;
    private String description;
    
    private String mission;
    private String profil;
    private Date dateAnnonce;
    private String addrese;
   




    @Enumerated(EnumType.STRING)
    private AnnonceType jobType;
    
 private long recruteur;
    






	@Override
	public String toString() {
		return "AnnonceCommand [id=" + id + ", title=" + title + ", domaine=" + domaine + ", nbPostes=" + nbPostes
				+ ", skills=" + skills + ", description=" + description + ", jobType=" + jobType + "]";
	}
	
}