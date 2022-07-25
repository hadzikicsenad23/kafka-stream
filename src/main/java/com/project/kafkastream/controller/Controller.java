package com.project.kafkastream.controller;

import com.project.kafkastream.domain.DecodedSample;
import com.project.kafkastream.services.SignalCalculationService;
import org.joda.time.DateTime;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;


@RequestMapping ("/api/kafka")
@RestController
public class Controller {

    private final SignalCalculationService signalCalculationService;
    final KafkaTemplate kafkaTemplate;
    private final String kafkaTopicName = "transaction-1";

    @Inject
    public Controller(SignalCalculationService signalCalculationService, KafkaTemplate kafkaTemplate) {
        this.signalCalculationService = signalCalculationService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public String sendMessage(@RequestBody DecodedSample decodedSample) {
        //This is just used for easier message publishing
        //Error handling is added on the app level so if the wrong message format is published, it will be skipped
        // (for this to happen new DecodedSample(...) needs to be changed when sending to the topic)
        this.kafkaTemplate.send(kafkaTopicName, new DecodedSample(decodedSample.getVehicleId(), decodedSample.getRecordedAt(), decodedSample.getSignalValues()));

        return "Message published to: " + kafkaTopicName;
    }

    //Getting the average speed of all vehicles
    @GetMapping("average-speed")
    public Double getAverageSpeed() {
        return signalCalculationService.calculateAverageSpeed(null);
    }

    //Getting the max speed out of all vehicles
    @GetMapping("max-speed")
    public Double getMaxSpeed() {
        return signalCalculationService.calculateMaxSpeed(null);
    }

    //Getting the last message out of all vehicles
    @GetMapping("time")
    public DateTime getTime() {
        return signalCalculationService.calculateLastMessageTimestamp(null);
    }

    //Getting number of charges of all vehicles
    @GetMapping("charges")
    public Integer getCharge() {
        return signalCalculationService.calculateNumberOfCharges(null);
    }

    //Getting the average speed of a single vehicle by ID
    @GetMapping("{vehicleId}/average-speed")
    public Double getAverageSpeedForVehicle(@PathVariable("vehicleId") String vehicleId) {
        return signalCalculationService.calculateAverageSpeed(vehicleId);
    }

    //Getting the max speed of a single vehicle by ID
    @GetMapping("{vehicleId}/max-speed")
    public Double getMaxSpeedForVehicle(@PathVariable("vehicleId") String vehicleId) {
        return signalCalculationService.calculateMaxSpeed(vehicleId);
    }

    //Getting the last message of a single vehicle by ID
    @GetMapping("{vehicleId}/time")
    public DateTime getTimeForVehicle(@PathVariable("vehicleId") String vehicleId) {
        return signalCalculationService.calculateLastMessageTimestamp(vehicleId);
    }

    //Getting number of charges a single vehicle by ID
    @GetMapping("{vehicleId}/charges")
    public Integer getChargeForVehicle(@PathVariable("vehicleId") String vehicleId) {
        return signalCalculationService.calculateNumberOfCharges(vehicleId);
    }

}
