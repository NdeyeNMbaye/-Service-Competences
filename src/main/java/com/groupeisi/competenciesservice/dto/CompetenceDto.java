package com.groupeisi.competenciesservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetenceDto {
    private Long id;
    private String nom;
    private String niveau;
    private String userEmail;
}