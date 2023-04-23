package com.poc.comm.service;


import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.poc.comm.beans.Usage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ServiceImpl {

    @Autowired
    InfluxDBClient influxDB;
    public String getPing() {
        if (influxDB.ping())
            return "Connected";
        else
            return "Not connected";
    }



    public String add(Usage usage) {
        Point point = Point
                .measurement("usage")
                .addField("id", usage.getId())
                .addField("tenantId", usage.getTenantId())
                .addField("type", usage.getType())
                .time(Instant.now(), WritePrecision.MS);

        WriteApiBlocking writeApi = influxDB.getWriteApiBlocking();
        writeApi.writePoint("comm", "tekion", point);
        return "added";
    }

    public String get24h(int id) {
        String query = "from(bucket: \"comm\") " +
                "|> range(start:-24h)" +
                "|> filter(fn: (r) => r._measurement == \"usage\" and r._field == \"tenantId\"  and r._value == " + id + ")" +
                "|> count()";

        List<FluxTable> tables = influxDB.getQueryApi().query(query);

        if (tables.isEmpty())
            return "No records";

        return Long.toString((Long) tables.get(0).getRecords().get(0).getValue());

    }
}
