package com.hsyou.wagusearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hsyou.wagusearch.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Properties;

@Slf4j
@EnableEurekaClient
@SpringBootApplication
public class WaguSearchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WaguSearchApplication.class, args);
    }

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;


    @Value("${zookeeper.host}")
    private String zookeeperHost;

    @Value("${kafka.consumer.group.id}")
    private String kafkaGroupId;

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
    @Autowired
    private SimpleKafkaConsumer simpleKafkaConsumer;
    @Override
    public void run(String... args) throws Exception {

        Thread kafkaConsumerThread = new Thread(() -> {
            log.info("Starting Kafka consumer thread.");

            simpleKafkaConsumer.runSingleWorker();
        });

        /*
         * Starting the first thread.
         */
        kafkaConsumerThread.start();
    }

}

