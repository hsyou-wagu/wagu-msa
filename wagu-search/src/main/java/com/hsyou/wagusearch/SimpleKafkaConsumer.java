package com.hsyou.wagusearch;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hsyou.wagusearch.model.PostDTO;
import com.hsyou.wagusearch.model.SearchEntity;
import com.hsyou.wagusearch.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Component
public class SimpleKafkaConsumer {

    @Autowired
    private SearchService searchService;

    @Autowired
    private KafkaConsumer<String, String> kafkaConsumer;

    @Autowired
    private ObjectMapper objectMapper;
    /**
     * This function will start a single worker thread per topic.
     * After creating the consumer object, we subscribed to a list of Kafka topics, in the constructor.
     * For this example, the list consists of only one topic. But you can give it a try with multiple topics.
     */
    public void runSingleWorker() {

        /*
         * We will start an infinite while loop, inside which we'll be listening to
         * new messages in each topic that we've subscribed to.
         */
        while(true) {

            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {

                /*
                Whenever there's a new message in the Kafka topic, we'll get the message in this loop, as
                the record object.
                 */

                /*
                Getting the message as a string from the record object.
                 */
                String message = record.value();

                /*
                Logging the received message to the console.
                 */
                log.info("Received message: " + message);

                /*
                If you remember, we sent 10 messages to this topic as plain strings.
                10 other messages were serialized JSON objects. Now we'll deserialize them here.
                But since we can't make out which message is a serialized JSON object and which isn't,
                we'll try to deserialize all of them.
                So, obviously, we'll get an exception for the first 10 messages we receive.
                We'll just log the errors and not worry about them.
                 */
                savePost(message);


                /*
                Once we finish processing a Kafka message, we have to commit the offset so that
                we don't end up consuming the same message endlessly. By default, the consumer object takes
                care of this. But to demonstrate how it can be done, we have turned this default behaviour off,
                instead, we're going to manually commit the offsets.
                The code for this is below. It's pretty much self explanatory.
                 */
                {
                    Map<TopicPartition, OffsetAndMetadata> commitMessage = new HashMap<>();

                    commitMessage.put(new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1));

                    kafkaConsumer.commitSync(commitMessage);

                    log.info("Offset committed to Kafka.");
                }
            }
        }
    }

    private void savePost(String msg){
        try {
            PostDTO postDTO = objectMapper.readValue(msg, PostDTO.class);
            searchService.save(postDTO);
        }catch(JsonParseException ex){
            ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
