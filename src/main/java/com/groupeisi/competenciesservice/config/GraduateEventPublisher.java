package com.groupeisi.competenciesservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GraduateEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public static final String TOPIC = "GRADUATE_UPDATED";

    public void publishGraduateUpdated(String userEmail) {
        String message = "{\"userEmail\":\"" + userEmail + "\",\"eventType\":\"COMPETENCE_CERTIFIEE\"}";
        kafkaTemplate.send(TOPIC, message);
        log.info("Événement Kafka publié : {} pour {}", TOPIC, userEmail);
    }
}