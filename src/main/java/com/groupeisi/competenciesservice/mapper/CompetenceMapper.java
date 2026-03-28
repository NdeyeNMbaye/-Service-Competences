package com.groupeisi.competenciesservice.mapper;

import com.groupeisi.competenciesservice.dto.CompetenceDto;
import com.groupeisi.competenciesservice.entities.CompetenceEntity;
import org.springframework.stereotype.Component;

@Component
public class CompetenceMapper {

    public CompetenceDto toDto(CompetenceEntity entity) {
        return CompetenceDto.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .niveau(entity.getNiveau())
                .userEmail(entity.getUserEmail()) // ✅ userId → userEmail
                .build();
    }

    public CompetenceEntity toEntity(CompetenceDto dto) {
        return CompetenceEntity.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .niveau(dto.getNiveau())
                .userEmail(dto.getUserEmail()) // ✅ userId → userEmail
                .build();
    }
}