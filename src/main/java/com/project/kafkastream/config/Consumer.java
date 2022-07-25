package com.project.kafkastream.config;

import com.project.kafkastream.domain.DecodedSample;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@org.springframework.context.annotation.Configuration
public class Consumer {

    final KafkaProperties kafkaProperties;

    public Consumer(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }



    @Bean
    ConsumerFactory<String, DecodedSample> consumerFactory() {
        ErrorHandlingDeserializer<DecodedSample> errorHandlingDeserializer
                = new ErrorHandlingDeserializer<>(new JsonDeserializer<>(DecodedSample.class));
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(), new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, DecodedSample> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, DecodedSample> concurrentKafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

        return concurrentKafkaListenerContainerFactory;
    }

}
