package com.khushal.kafka_producer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaMessagePublisher {

    private final KafkaTemplate<String, Object> template;

    public void publishMessage(String message){
        CompletableFuture<SendResult<String, Object>> future = template.send("producer", message);
        future.whenComplete((result, err) -> {
            if(err == null){
                System.out.println("Sent message = [" + message +
                        "] with offset = [" + result.getRecordMetadata().offset() + "]");
            }else{
                System.out.println("Unable to send message = [" + message +
                        "] due to : " + err.getMessage());
            }
        });

    }
}
