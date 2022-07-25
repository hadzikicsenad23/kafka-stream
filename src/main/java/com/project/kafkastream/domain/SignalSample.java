package com.project.kafkastream.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;


@Table("signal_sample") //represents that it will map to ‘sample_data’ table in Cassandra DB.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignalSample {

    @PrimaryKeyColumn(name = "vehicle_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String vehicleId;

    @PrimaryKeyColumn(name = "recorded_at", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private DateTime recordedAt;

    @PrimaryKeyColumn(name = "signal", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String signal;

    @Column("value")
    private Double value;
}