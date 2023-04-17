package br.com.ada.albuns.service.impl;

import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String CREATE_FIGURINHA = "CREATE_FIGURINHA";

    public void send(Map<String,Object> message){
        try{
            log.info("Mensagem enviada: {" + message + "}");
            ObjectMapper mapper = new ObjectMapper();

            String value = mapper.writeValueAsString(message);
            String topicName = "TOPIC_CREATE_FIGURINHA";

            kafkaTemplate.send(topicName, CREATE_FIGURINHA ,value);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
