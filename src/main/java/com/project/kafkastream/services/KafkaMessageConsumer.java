package com.project.kafkastream.services;

import com.project.kafkastream.domain.DecodedSample;
import com.project.kafkastream.domain.SignalSample;
import com.project.kafkastream.domain.enums.SignalType;
import com.project.kafkastream.repository.SignalSampleRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class KafkaMessageConsumer {
    private final SignalSampleRepository repo;
    private static final List<String> enumValues = Stream.of(SignalType.values())
            .map(SignalType::getName).toList();

    @Inject
    public KafkaMessageConsumer(SignalSampleRepository repo) {
        this.repo = repo;
    }

    @KafkaListener(topics = "transaction-1")
    public void listener(@Payload DecodedSample decodedSample, ConsumerRecord<String, DecodedSample> cr) {
        System.out.println("Topic [transaction-1] Received message for " + decodedSample.getVehicleId() + " with time " + decodedSample.getRecordedAt() + " and signal values " + decodedSample.getSignalValues());
        System.out.println(cr.toString());
        List<SignalSample> signalSampleList = new ArrayList<>();
        decodedSample.getSignalValues().forEach( (key, value) -> {
            if (!enumValues.contains(key)){ //skipping if invalid signal is sent
                //log or throw an error, in this case just printing is done
                System.out.println("Invalid signal type: " + key);
                return;
            }
            signalSampleList.add(new SignalSample(decodedSample.getVehicleId(), decodedSample.getRecordedAt(), key, value));
        });
        repo.saveAll(signalSampleList);
    }
}
