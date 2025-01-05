package com.annonce.cqrs.command.service;


import com.annonce.cqrs.command.dto.AnnonceCommandDto;


public interface AnnonceCommandService {
    int createProject(AnnonceCommandDto annonceCommandDto);
    int updateProject(AnnonceCommandDto annonceCommandDto);
    void deleteProject(Long id );

    
    public void assignAnnonceToRecruteurProfile(int recruteurId, int annonceId);
    
}


