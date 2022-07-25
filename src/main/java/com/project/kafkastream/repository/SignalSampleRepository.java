package com.project.kafkastream.repository;

import com.project.kafkastream.domain.SignalSample;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SignalSampleRepository extends CassandraRepository<SignalSample, Integer> {

    @Query(value = "SELECT * FROM signal_sample " +
            "WHERE signal = :signalType ALLOW FILTERING")
    List<SignalSample> findBySignal(@Param("signalType") String signalType);

    @Query(value = "SELECT * FROM signal_sample " +
            "WHERE signal = :signalType and vehicle_id = :vehicleId ALLOW FILTERING")
    List<SignalSample> findBySignalAndVehicleId(@Param("signalType") String signalType, @Param("vehicleId") String vehicleId);

    @Query(value = "SELECT * FROM signal_sample;")
    List<SignalSample> findAllSignals();

    @Query(value = "SELECT * FROM signal_sample WHERE vehicle_id = :vehicleId ALLOW FILTERING")
    List<SignalSample> findAllSignalsByVehicleId(@Param("vehicleId") String vehicleId);
}