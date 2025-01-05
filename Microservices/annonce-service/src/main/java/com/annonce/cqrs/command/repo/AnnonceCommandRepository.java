package com.annonce.cqrs.command.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.annonce.cqrs.domain.AnnonceCommand;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceCommandRepository extends JpaRepository<AnnonceCommand, Long> {

}
