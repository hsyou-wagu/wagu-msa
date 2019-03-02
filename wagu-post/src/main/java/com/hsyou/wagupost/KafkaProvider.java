package com.hsyou.wagupost;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsyou.wagupost.model.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class KafkaProvider {

    @Autowired
    private ObjectMapper objectMapper;
    @Value("${kafka.topic.post}")
    private String postTopicName;

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    public void sendPostMessage(PostDTO postDTO){
        try {
            String value = objectMapper.writeValueAsString(postDTO);

            log.info("send "+value);
            kafkaProducer.send(new ProducerRecord<>(postTopicName, value));

        }catch(Exception ex){
            log.error(ex.toString());
        }
    }
}
