package com.geohash.geovocab.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoHashRepo extends JpaRepository<GeoHashEntity, String> {
    public GeoHashEntity findByHashVal(String hashVal);
    
    @Query("select p from GeoHashEntity p where p.hashVal in :ids" )
    public List<GeoHashEntity> findByIds(@Param("ids") List<String> hashValList);
    
    @Query("select p from GeoHashEntity p where p.word in :words" )
    public List<GeoHashEntity> findByWords(@Param("words") List<String> wordList);
}
