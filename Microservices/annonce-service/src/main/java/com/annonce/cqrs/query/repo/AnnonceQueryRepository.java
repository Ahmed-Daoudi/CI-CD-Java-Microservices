package com.annonce.cqrs.query.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.annonce.cqrs.domain.AnnonceQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AnnonceQueryRepository extends MongoRepository<AnnonceQuery, Long>
{
    @Query("{ 'id': ?0 }")
    AnnonceQuery findById(long id);
    
    
    @Query("{ 'createdBy': ?0 }")
    List<AnnonceQuery> findByCreatedBy(String user);

    @Query("{ 'lastModifiedBy': ?0 }")
    List<AnnonceQuery> findByLastModifiedBy(String user);
    
    @Query("{'recruteur' : ?0 }")
   	List<AnnonceQuery> fetchAnnonceByRecruteur(long recruteur);
  
    
    @Query("{'domaine' : { $regex: ?0 } }")
   	List<AnnonceQuery> fetchAnnonce(String domaine);
    
    @Query("{'jobType' : { $regex: ?0 }}")
   	List<AnnonceQuery> fetchAnnonce1(String type);
    
    @Query("{'jobType' : { $regex: ?0 }, 'domaine' : { $regex: ?1 }}")
  	List<AnnonceQuery> fetchAnnonce2(String type,String domaine);
    
    @Query("{'skills' : { $regex: ?0 }}")
   	List<AnnonceQuery> fetchAnnonce3(String skills);
    
    @Query("{'skills' : { $regex: ?0 }, 'domaine' : { $regex: ?1 }}")
   	List<AnnonceQuery> fetchAnnonce4(String skills,String domaine);
    
    @Query("{'skills' : { $regex: ?0 }, 'jobType' : { $regex: ?1 }}")
   	List<AnnonceQuery> fetchAnnonce5(String skills,String type);
    
    @Query("{'skills' : { $regex: ?0 }, 'jobType' : { $regex: ?1 },  'domaine' : { $regex: ?2 }}")
   	List<AnnonceQuery> fetchAnnonce6(String skills,String type, String domaine);
    
    
    
    
    
    
    
    

    
}
