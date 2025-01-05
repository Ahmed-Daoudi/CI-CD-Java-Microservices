package com.annonce.cqrs.query.service;

import java.util.List;

import com.annonce.cqrs.domain.AnnonceQuery;

public interface AnnonceQueryService {
    AnnonceQuery findById(long id);

    List<AnnonceQuery> getProjects();
    List<AnnonceQuery> findProjectByOwner(String username);
    List<AnnonceQuery> filter(String domaine, String jobType,String skills);
     List<AnnonceQuery> fetchAnnonceByRecruteur(long recruteur);
}
