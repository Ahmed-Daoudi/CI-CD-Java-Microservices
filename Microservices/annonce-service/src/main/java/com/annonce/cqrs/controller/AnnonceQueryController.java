package com.annonce.cqrs.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import com.annonce.cqrs.domain.AnnonceQuery;
import com.annonce.cqrs.query.repo.AnnonceQueryRepository;
import com.annonce.cqrs.query.service.AnnonceQueryService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/annonce-query")
@CrossOrigin("*")
public class AnnonceQueryController {
    @Autowired
    private AnnonceQueryService annonceQueryService;
    @Autowired
    KafkaTemplate<Long,String> kafkaTemplate;
    @Autowired
    AnnonceQueryRepository repo;



    @GetMapping("/all")
    public List<AnnonceQuery> getProjects(){

        return this.annonceQueryService.getProjects();
    }


    @GetMapping("/filter")
    public List<AnnonceQuery> filter(@RequestParam String domaine,@RequestParam String jobType,@RequestParam String skills)
    {

        return  this.annonceQueryService.filter(domaine, jobType, skills);
    }

    @GetMapping("/annoncebyid/{id}")
    public AnnonceQuery getProjectById(@PathVariable long id){
        return this.annonceQueryService.findById(id);
    }
    
    
    @GetMapping("/annonceByRecruteur/{idRecruteur}")
	public List<AnnonceQuery> fetchAnnonceByRecruteur(@PathVariable long idRecruteur) {
		
		return this.annonceQueryService.fetchAnnonceByRecruteur(idRecruteur);
		
	}
    
    

}
