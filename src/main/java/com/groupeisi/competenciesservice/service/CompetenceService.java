package com.groupeisi.competenciesservice.service;

import com.groupeisi.competenciesservice.config.GraduateEventPublisher;
import com.groupeisi.competenciesservice.dto.CompetenceDto;
import com.groupeisi.competenciesservice.entities.CompetenceEntity;
import com.groupeisi.competenciesservice.repository.CompetenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompetenceService {

    private final CompetenceRepository repository;
    private final RestTemplate restTemplate;
    private final GraduateEventPublisher eventPublisher;
    @Value("${diplomas-service.url}")
    private String diplomasServiceUrl;

    // CREATE
    public CompetenceDto create(CompetenceDto dto) {
        log.info("Vérification diplômé pour email : {}", dto.getUserEmail());

        // Communication synchrone REST → diplomas-service
        try {
            restTemplate.getForObject(
                    diplomasServiceUrl + "/api/diplomes/by-email?email=" + dto.getUserEmail(),
                    Object.class
            );
            log.info("Diplômé trouvé pour email : {}", dto.getUserEmail());
        } catch (Exception e) {
            log.error("Diplômé introuvable pour email : {}", dto.getUserEmail());
            throw new RuntimeException("Diplômé introuvable dans diplomas-service pour email : " + dto.getUserEmail());
        }

        CompetenceEntity entity = CompetenceEntity.builder()
                .nom(dto.getNom())
                .niveau(dto.getNiveau())
                .userEmail(dto.getUserEmail())
                .build();

        entity = repository.save(entity);
        eventPublisher.publishGraduateUpdated(dto.getUserEmail());
        log.info("Compétence créée avec id : {}", entity.getId());
        dto.setId(entity.getId());
        return dto;
    }

    // READ - par email
    public List<CompetenceDto> getByUserEmail(String userEmail) {
        log.info("Récupération compétences pour email : {}", userEmail);
        return repository.findByUserEmail(userEmail)
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    // READ - toutes
    public List<CompetenceDto> getAll() {
        log.info("Récupération de toutes les compétences");
        return repository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    // UPDATE
    public CompetenceDto update(Long id, CompetenceDto dto) {
        log.info("Mise à jour compétence id : {}", id);
        CompetenceEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compétence non trouvée avec id : " + id));
        entity.setNom(dto.getNom());
        entity.setNiveau(dto.getNiveau());
        entity.setUserEmail(dto.getUserEmail());
        entity = repository.save(entity);
        log.info("Compétence mise à jour : {}", id);
        return entityToDto(entity);
    }

    // DELETE
    public void delete(Long id) {
        log.info("Suppression compétence id : {}", id);
        CompetenceEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compétence non trouvée avec id : " + id));
        repository.delete(entity);
        log.info("Compétence supprimée : {}", id);
    }

    private CompetenceDto entityToDto(CompetenceEntity e) {
        return CompetenceDto.builder()
                .id(e.getId())
                .nom(e.getNom())
                .niveau(e.getNiveau())
                .userEmail(e.getUserEmail())
                .build();
    }
}