package com.groupeisi.competenciesservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountDto {

    private Long id;
    private String email;
    private String role;
    // password non exposé dans le DTO
}