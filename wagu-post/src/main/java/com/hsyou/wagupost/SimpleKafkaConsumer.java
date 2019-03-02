package com.hsyou.wagupost;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class SimpleKafkaConsumer {

    @Value("${kafka.topic.post}")
    private String postTopicName;

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;


    @Value("${zookeeper.host}")
    private String zookeeperHost;

    @Value("${kafka.consumer.group.id}")
    private String kafkaGroupId;
//    @Autowired
//    private KafkaConsumer<String, String> kafkaConsumer;
    private KafkaConsumer<String, String> kafkaConsumer;

    public SimpleKafkaConsumer(String theTechCheckTopicName, Properties consumerProperties) {

        kafkaConsumer = new KafkaConsumer<>(consumerProperties);
        kafkaConsumer.subscribe(Arrays.asList(theTechCheckTopicName));
    }

    public SimpleKafkaConsumer() {

        Properties consumerProperties = new Properties();
        consumerProperties.put("bootstrap.servers", kafkaBootstrapServers);
        consumerProperties.put("group.id", kafkaGroupId);
        consumerProperties.put("zookeeper.session.timeout.ms", "6000");
        consumerProperties.put("zookeeper.sync.time.ms","2000");
        consumerProperties.put("auto.commit.enable", "false");
        consumerProperties.put("auto.commit.interval.ms", "1000");
        consumerProperties.put("consumer.timeout.ms", "-1");
        consumerProperties.put("max.poll.records", "1");
        consumerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer<String, String>(consumerProperties);

        kafkaConsumer.subscribe(Arrays.asList(postTopicName));
    }

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
                sendPostMessage(message);


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

    private void sendPostMessage(String msg){
        RestTemplate restTemplate = new RestTemplate();
        try {

            log.info("send post message -> "+msg);
//            URI uri = new URI("http://localhost:8081/post");
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//
//            HttpEntity<String> entity = new HttpEntity<String>(msg, headers);
//
//
//            restTemplate.postForObject(uri, entity, String.class);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}
