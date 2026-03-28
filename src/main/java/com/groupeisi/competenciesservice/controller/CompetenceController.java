package com.groupeisi.competenciesservice.controller;

import com.groupeisi.competenciesservice.dto.CompetenceDto;
import com.groupeisi.competenciesservice.service.CompetenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competences")
@RequiredArgsConstructor
public class CompetenceController {

    private final CompetenceService competenceService;

    @PostMapping
    public ResponseEntity<CompetenceDto> create(@RequestBody CompetenceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(competenceService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<CompetenceDto>> getAll() {
        return ResponseEntity.ok(competenceService.getAll());
    }

    @GetMapping("/by-email")
    public ResponseEntity<List<CompetenceDto>> getByEmail(@RequestParam String userEmail) {
        return ResponseEntity.ok(competenceService.getByUserEmail(userEmail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompetenceDto> update(@PathVariable Long id,
                                                @RequestBody CompetenceDto dto) {
        return ResponseEntity.ok(competenceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        competenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}