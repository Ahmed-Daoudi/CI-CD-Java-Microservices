package com.annonce.cqrs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.annonce.cqrs.command.dto.AnnonceCommandDto;
import com.annonce.cqrs.command.service.AnnonceCommandServiceImpl;
import javax.validation.Valid;


@RestController
@RequestMapping("/project-command")
@CrossOrigin(origins = "*")

public class AnnonceCommandController {
    @Autowired
    AnnonceCommandServiceImpl projectCommandService;
    
    
    


    @PostMapping("/create")
    public ResponseEntity<AnnonceCommandDto> createUser( @Valid @RequestBody AnnonceCommandDto dto){

        if(this.projectCommandService.createProject(dto)==1){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AnnonceCommandDto> deleteProject(@PathVariable long id){

        try{
            this.projectCommandService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.CONFLICT);

        }


    }


    @PutMapping("/update")
    public ResponseEntity<AnnonceCommandDto> update(@RequestBody AnnonceCommandDto dto){

        if(this.projectCommandService.updateProject(dto)==1){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return  new ResponseEntity<>(HttpStatus.CONFLICT);

    }
}
