package com.annonce.cqrs.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


import java.io.Serializable;
import java.util.Date;
@Setter
@Getter
@Entity
@Table(name = "annonces")
public class AnnonceCommand implements Serializable {
	
	private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String title;
    private String domaine;
    
    @Lob
    private String mission;
    
    @Lob
    private String profil;
    private Date dateAnnonce;
    private String addrese;
   
	public String getMission() {
		return mission;
	}


	private int nbPostes;
    private String skills;
    
    @Lob
    private String description;
    

    @Enumerated(EnumType.STRING)
    private AnnonceType jobType;


   
    private long recruteur;
 



	@Override
	public String toString() {
		return "AnnonceCommand [id=" + id + ", title=" + title + ", domaine=" + domaine + ", nbPostes=" + nbPostes
				+ ", skills=" + skills + ", description=" + description + ", jobType=" + jobType + "]";
	}
	
	



}