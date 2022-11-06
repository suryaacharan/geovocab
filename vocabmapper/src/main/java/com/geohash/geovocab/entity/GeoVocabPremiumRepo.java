package com.geohash.geovocab.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoVocabPremiumRepo extends JpaRepository<GeoVocabPremium, String> {
    public GeoVocabPremium findByGeoHash(String geoHash);
    
    public GeoVocabPremium findByMagicwords(String magicWords);
  
}
