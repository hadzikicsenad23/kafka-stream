package com.project.kafkastream.services;

import com.project.kafkastream.domain.SignalSample;
import com.project.kafkastream.repository.SignalSampleRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Component
public class SignalCalculationService {
    private final SignalSampleRepository repo;
    private final Double isCharging = 1.0;

    @Inject
    public SignalCalculationService(SignalSampleRepository repo) {
        this.repo = repo;
    }

    public Double calculateAverageSpeed(String vehicleId) {
        List<SignalSample> currentSpeeds;
        if (vehicleId == null) {
            currentSpeeds = repo.findBySignal("currentSpeed");
        } else {
            currentSpeeds = repo.findBySignalAndVehicleId("currentSpeed", vehicleId);
        }
        Double speedSum = 0D;
        if (currentSpeeds.isEmpty()) {
            return speedSum;
        }

        for (SignalSample currentSpeed: currentSpeeds) {
            speedSum += currentSpeed.getValue();
        }

        return speedSum/currentSpeeds.size();
    }

    public Double calculateMaxSpeed(String vehicleId) {
        List<SignalSample> currentSpeeds;
        if (vehicleId == null) {
            currentSpeeds = repo.findBySignal("currentSpeed");
        } else {
            currentSpeeds = repo.findBySignalAndVehicleId("currentSpeed", vehicleId);
        }
        SignalSample maxSpeedSignal = currentSpeeds.stream().max(Comparator.comparing(SignalSample::getValue)).orElseThrow(NoSuchElementException::new);
        return maxSpeedSignal.getValue();
    }

    public DateTime calculateLastMessageTimestamp(String vehicleId) {
        List<SignalSample> allSignals;
        if (vehicleId == null) {
            allSignals = repo.findAllSignals();
        } else {
            allSignals = repo.findAllSignalsByVehicleId(vehicleId);
        }
        SignalSample lastSignal = allSignals.stream().max(Comparator.comparing(SignalSample::getRecordedAt)).orElseThrow(NoSuchElementException::new);
        return lastSignal.getRecordedAt();
    }

    public Integer calculateNumberOfCharges(String vehicleId) {
        List<SignalSample> charges;
        if (vehicleId == null) {
            charges = repo.findBySignal("isCharging");
        } else {
            charges = repo.findBySignalAndVehicleId("isCharging", vehicleId);
        }
        Integer numOfCharges = 0;
        if (charges.isEmpty()) {
            return numOfCharges;
        }

        for (SignalSample charge: charges) {
            if (Objects.equals(charge.getValue(), isCharging)) {
                numOfCharges ++;
            }

        }

        return numOfCharges;
    }
}
