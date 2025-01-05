package com.annonce.cqrs.command.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import com.annonce.cqrs.command.dto.AnnonceCommandDto;
import com.annonce.cqrs.command.repo.AnnonceCommandRepository;
import com.annonce.cqrs.domain.AnnonceCommand;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AnnonceCommandServiceImpl implements  AnnonceCommandService{


    @Value("${manager.api.url}")
    private  String url;
    @Autowired
    AnnonceCommandRepository annonceCommandRepository;

    @Autowired
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    private final KafkaTemplate<Long, String> kafkaTemplate;
    @Autowired
    public AnnonceCommandServiceImpl(KafkaTemplate<Long, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public int createProject(AnnonceCommandDto annonceCommandDto) {

        AnnonceCommand annonceCommand = new AnnonceCommand();
        annonceCommand.setTitle(annonceCommandDto.getTitle());
        annonceCommand.setDomaine(annonceCommandDto.getDomaine());
        annonceCommand.setJobType(annonceCommandDto.getJobType());
        annonceCommand.setNbPostes(annonceCommandDto.getNbPostes());
        annonceCommand.setSkills(annonceCommandDto.getSkills());
        annonceCommand.setDescription(annonceCommandDto.getDescription());
        annonceCommand.setMission(annonceCommandDto.getMission());
        annonceCommand.setProfil(annonceCommandDto.getProfil());
        
        Date date = new Date(System.currentTimeMillis());
        annonceCommand.setDateAnnonce(date);
        annonceCommand.setAddrese(annonceCommandDto.getAddrese());


        this.annonceCommandRepository.save(annonceCommand);


        this.raiseEventToQueryProject(annonceCommand,"annonce-event-create");



        return 1;


    }

    @Override
    public int updateProject(AnnonceCommandDto annonceCommandDto) {
        this.annonceCommandRepository.findById(annonceCommandDto.getId()).ifPresent(annonceCommand -> {
        	 annonceCommand.setId(annonceCommandDto.getId());
        	 annonceCommand.setTitle(annonceCommandDto.getTitle());
             annonceCommand.setDomaine(annonceCommandDto.getDomaine());
             annonceCommand.setJobType(annonceCommandDto.getJobType());
             annonceCommand.setNbPostes(annonceCommandDto.getNbPostes());
             annonceCommand.setSkills(annonceCommandDto.getSkills());
             annonceCommand.setDescription(annonceCommandDto.getDescription());
             annonceCommand.setMission(annonceCommandDto.getMission());
             annonceCommand.setProfil(annonceCommandDto.getProfil());
             Date date = new Date(System.currentTimeMillis());
             annonceCommand.setDateAnnonce(date);
             annonceCommand.setAddrese(annonceCommandDto.getAddrese());
             
             this.annonceCommandRepository.save(annonceCommand);
            this.raiseEventToQueryProject(annonceCommand,"annonce-event-update");

        });
        return 1;



    }
    
    
    @Override
    public void assignAnnonceToRecruteurProfile(int recruteurId, int annonceId) {
    	AnnonceCommand myAnnonce =  annonceCommandRepository.findById((long)annonceId).orElse(null);
    	myAnnonce.setRecruteur(recruteurId);
    	
    	this.annonceCommandRepository.save(myAnnonce);
        this.raiseEventToQueryProject(myAnnonce,"annonce-event-update");
    	}
    
    
    
    

    @Override
    public void deleteProject(Long id) {
        Optional<AnnonceCommand> annonceCommand = this.annonceCommandRepository.findById(id);
        if(!annonceCommand.isEmpty()){
           
            this.annonceCommandRepository.deleteById(id);
            annonceCommand.get().setId(id);
            this.raiseEventToQueryProject(annonceCommand.get(),"annonce-event-delete");

        }else {
            AnnonceCommand projectCommand1= new AnnonceCommand();
            projectCommand1.setId(id);
            this.raiseEventToQueryProject(projectCommand1,"annonce-event-delete");
            


        }

    }


    private void raiseEventToQueryProject(AnnonceCommand dto, String topic){
        try{
            String value = OBJECT_MAPPER.writeValueAsString(dto);
            this.kafkaTemplate.send(topic,value);
            System.out.println("sended");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void createNodeRedProject(Map<String,String> body){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Map> entity = new HttpEntity<>(body,headers);
        Object res=  restTemplate.postForEntity( url+"/instance", entity , Map.class ).getBody();
    }

    void deleteNodeRedProject(String username, String appname){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        Map<String,Object> res=  restTemplate.getForEntity( url+"/myinstances/"+username, Map.class ).getBody();
        ObjectMapper oMapper = new ObjectMapper();
        List<Map<String,String>> instances = oMapper.convertValue(res.get("instances"), List.class);


        for(int i = 0; i<instances.size(); i++){
            System.out.println(instances.get(i).get("appname"));
            if(instances.get(i).get("appname").equals(appname)){
                Map<String,String> body = new HashMap<>();
                body.put("appname",appname);
                body.put("command","stop");
                HttpEntity<Map> entity = new HttpEntity<>(body,headers);
                Object res1Stop=  restTemplate.postForEntity( url+"/instance/"+instances.get(i).get("container_id"), entity , Map.class ).getBody();
                body.put("command","remove");
                Object res1Remove=  restTemplate.postForEntity( url+"/instance/"+instances.get(i).get("container_id"), entity , Map.class ).getBody();
            }
        }



    }


}
