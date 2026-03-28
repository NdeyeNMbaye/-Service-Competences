package com.groupeisi.competenciesservice.repository;

import com.groupeisi.competenciesservice.entities.CompetenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetenceRepository extends JpaRepository<CompetenceEntity, Long> {
    List<CompetenceEntity> findByUserEmail(String userEmail);
}