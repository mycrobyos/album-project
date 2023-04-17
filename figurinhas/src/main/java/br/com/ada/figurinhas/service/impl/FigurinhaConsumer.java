package br.com.ada.figurinhas.service.impl;

import br.com.ada.figurinhas.model.dto.CreateFigurinhasMessage;
import br.com.ada.figurinhas.service.FigurinhaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FigurinhaConsumer {
    private final String topicName = "CREATE_FIGURINHA_TOPIC";

    @Autowired
    private FigurinhaService stickerService;

    @KafkaListener(topics = "CREATE_FIGURINHA_TOPIC", groupId = "group_id" )
    public void consume(ConsumerRecord<String, String> payload){
        log.info("Tópico: {}", topicName);
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
        log.info("Order: {}", payload.value());

        stickerService.createFigurinhasForAlbum(convertToModel(payload));

    }

    private CreateFigurinhasMessage convertToModel(ConsumerRecord<String, String> payload) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(payload.value(), CreateFigurinhasMessage.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Error to convert data to model");
    }
}