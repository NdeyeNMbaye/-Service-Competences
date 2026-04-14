package com.groupeisi.competenciesservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiplomasClient {

    private final RestTemplate restTemplate;

    @Value("${diplomas-service.url}")
    private String diplomasServiceUrl;

    public boolean diplomaExistsByEmail(String email) {
        try {
            restTemplate.getForObject(
                    diplomasServiceUrl + "/api/diplomes/by-email?email=" + email,
                    Object.class
            );
            log.info("Diplômé trouvé pour email : {}", email);
            return true;
        } catch (Exception e) {
            log.error("Diplômé introuvable pour email : {}", email);
            return false;
        }
    }
}