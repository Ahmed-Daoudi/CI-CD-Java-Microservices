package com.annonce.cqrs.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annonce.cqrs.domain.AnnonceQuery;
import com.annonce.cqrs.query.repo.AnnonceQueryRepository;

import java.util.List;

@Service
public class AnnonceQueryServiceImpl implements AnnonceQueryService
{

    @Autowired
    private AnnonceQueryRepository annonceQueryRepository;

    @Override
    public AnnonceQuery findById(long id) {
        return this.annonceQueryRepository.findById(id);
    }

    @Override
    public List<AnnonceQuery> getProjects() {
        return annonceQueryRepository.findAll();    }

    @Override
    public  List<AnnonceQuery> findProjectByOwner(String username)

    {

        return this.annonceQueryRepository.findByCreatedBy(username);
    }

	@Override
	public List<AnnonceQuery> filter(String domaine, String jobType, String skills) {
		
		if(jobType.isEmpty() && skills.isEmpty() && !domaine.isEmpty() ) {
			return annonceQueryRepository.fetchAnnonce(domaine);
		}
		
		else if(!jobType.isEmpty() && skills.isEmpty() && domaine.isEmpty() ) {
			return annonceQueryRepository.fetchAnnonce1(jobType);
		}
		
		else if(!jobType.isEmpty() && skills.isEmpty() && !domaine.isEmpty() ) {
			return annonceQueryRepository.fetchAnnonce2(jobType,domaine);
		}
		else if(jobType.isEmpty() && !skills.isEmpty() && domaine.isEmpty() ) {
			return annonceQueryRepository.fetchAnnonce3(skills);
		}
		else if(jobType.isEmpty() && !skills.isEmpty() && !domaine.isEmpty() ) {
			return annonceQueryRepository.fetchAnnonce4(skills, domaine);
		}
		else if(!jobType.isEmpty() && !skills.isEmpty() && domaine.isEmpty() ) {
			return annonceQueryRepository.fetchAnnonce5(skills,jobType);
		}
		else if(!jobType.isEmpty() && !skills.isEmpty() && !domaine.isEmpty() ) {
			return annonceQueryRepository.fetchAnnonce6(skills,jobType,domaine);
		}
		else return  annonceQueryRepository.findAll();
		
		
	}
	
	@Override
	public List<AnnonceQuery> fetchAnnonceByRecruteur(long recruteur) {
		
		return annonceQueryRepository.fetchAnnonceByRecruteur(recruteur);
		
	}

    
    



}
