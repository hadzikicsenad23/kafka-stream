package com.project.kafkastream.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.util.Map;

public class DecodedSample {

    private String vehicleId;
    private DateTime recordedAt;
    private Map<String, Double> signalValues;

    public DecodedSample(
            @JsonProperty String vehicleId,
            @JsonProperty DateTime recordedAt,
            @JsonProperty Map<String, Double> signalValues) {
        this.vehicleId = vehicleId;
        this.recordedAt = recordedAt;
        this.signalValues = signalValues;
    }

    public DecodedSample() {
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public DateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(DateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    public Map<String, Double> getSignalValues() {
        return signalValues;
    }

    public void setSignalValues(Map<String, Double> signalValues) {
        this.signalValues = signalValues;
    }

    @Override
    public String toString() {
        return "DecodedSample{" +
                "vehicleId='" + vehicleId + '\'' +
                ", recordedAt='" + recordedAt + '\'' +
                ", signalValues='" + signalValues + '\'' +
                '}';
    }
}
