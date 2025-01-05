package com.annonce.cqrs.query.hundler;

import com.annonce.cqrs.query.dto.AnnonceQueryDto;

public interface AnnonceQueryHandler
{
    public  void createProject(AnnonceQueryDto annonceQueryDto);
    public void updateProject(AnnonceQueryDto annonceQueryDto);
    public  void deleteProject(AnnonceQueryDto annonceQueryDto);

}
