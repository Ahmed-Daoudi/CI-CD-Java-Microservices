package com.annonce.cqrs.query.hundler;

import com.annonce.cqrs.domain.*;
import com.annonce.cqrs.query.dto.AnnonceQueryDto;
import com.annonce.cqrs.query.repo.AnnonceQueryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnnonceEventHandlerImpl implements AnnonceQueryHandler
{
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    private AnnonceQueryRepository annonceQueryRepository;
   

    @Override
    public void createProject(AnnonceQueryDto annonceQueryDto) {

        
        AnnonceQuery annonceQuery = new AnnonceQuery();
        annonceQuery.setTitle(annonceQueryDto.getTitle());
        annonceQuery.setDomaine(annonceQueryDto.getDomaine());
        annonceQuery.setJobType(annonceQueryDto.getJobType());
        annonceQuery.setNbPostes(annonceQueryDto.getNbPostes());
        annonceQuery.setSkills(annonceQueryDto.getSkills());
        annonceQuery.setDescription(annonceQueryDto.getDescription());
        annonceQuery.setMission(annonceQueryDto.getMission());
        annonceQuery.setProfil(annonceQueryDto.getProfil());
        annonceQuery.setId(annonceQueryDto.getId());
        Date date = new Date(System.currentTimeMillis());
        annonceQuery.setDateAnnonce(date);
        annonceQuery.setAddrese(annonceQueryDto.getAddrese());
        

        this.annonceQueryRepository.save(annonceQuery);


    }



    @Override
    public void updateProject(AnnonceQueryDto annonceQueryDto) {
        AnnonceQuery annonceQuery= this.annonceQueryRepository.findById(annonceQueryDto.getId());
        annonceQuery.setTitle(annonceQueryDto.getTitle());
        annonceQuery.setDomaine(annonceQueryDto.getDomaine());
        annonceQuery.setJobType(annonceQueryDto.getJobType());
        annonceQuery.setNbPostes(annonceQueryDto.getNbPostes());
        annonceQuery.setSkills(annonceQueryDto.getSkills());
        annonceQuery.setDescription(annonceQueryDto.getDescription());
        annonceQuery.setMission(annonceQueryDto.getMission());
        annonceQuery.setProfil(annonceQueryDto.getProfil());
        annonceQuery.setDateAnnonce(annonceQueryDto.getDateAnnonce());
        annonceQuery.setAddrese(annonceQueryDto.getAddrese());
        this.annonceQueryRepository.save(annonceQuery);


    }
    @Override
    public void deleteProject(AnnonceQueryDto annonceQueryDto) {


        AnnonceQuery annonceQuery= this.annonceQueryRepository.findById(annonceQueryDto.getId());


        this.annonceQueryRepository.delete(annonceQuery);
    }


    @KafkaListener(topics = "annonce-event-create")
    public void consumeCreate(String userStr) {
        try {


            AnnonceQueryDto annonceQueryDto = OBJECT_MAPPER.readValue(userStr, AnnonceQueryDto.class);
            this.createProject(annonceQueryDto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @KafkaListener(topics = "annonce-event-update")
    public void consumeUpdate(String userStr) {
        try {

            AnnonceQueryDto annonceQueryDto = OBJECT_MAPPER.readValue(userStr, AnnonceQueryDto.class);
            this.updateProject(annonceQueryDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @KafkaListener(topics = "annonce-event-delete")
    public void consumeDelete(String userStr) {
        try {

            AnnonceQueryDto annonceQueryDto = OBJECT_MAPPER.readValue(userStr, AnnonceQueryDto.class);
            this.deleteProject(annonceQueryDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
