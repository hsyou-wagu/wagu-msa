package com.hsyou.wagusearch.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Properties;

@Configuration
public class KafkaConfig {
    @Value("${kafka.topic.post}")
    private String postTopicName;

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;


    @Value("${zookeeper.host}")
    private String zookeeperHost;

    @Value("${kafka.consumer.group.id}")
    private String kafkaGroupId;

    @Bean
    public KafkaConsumer<String, String> kafkaConsumer(){
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

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(consumerProperties);
        kafkaConsumer.subscribe(Arrays.asList(postTopicName));

        return kafkaConsumer;
     }

}
