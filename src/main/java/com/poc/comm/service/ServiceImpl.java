package com.poc.comm.service;


import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.poc.comm.beans.Usage;
import com.poc.comm.models.Quota;
import com.poc.comm.models.dto.QuotaDto;
import com.poc.comm.repository.QuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceImpl {

    private final InfluxDBClient influxDB;

    private final QuotaRepository quotaRepository;

    public String getPing() {
        if (influxDB.ping())
            return "Connected";
        else
            return "Not connected";
    }


    public String add(Usage usage) {
        Point point = Point
                .measurement("usage")
                .addTag("id", Integer.toString(usage.getId()))
                .addTag("tenantId", Integer.toString(usage.getTenantId()))
                .addTag("dealerId", Integer.toString(usage.getDealerId()))
                .addTag("moduleId", Integer.toString(usage.getModuleId()))
                .addTag("type", usage.getType())
                .addField("UNGA", "BUNGA")
                .time(Instant.now(), WritePrecision.MS);

        WriteApiBlocking writeApi = influxDB.getWriteApiBlocking();
        writeApi.writePoint("testTag", "galaxy", point);
        return "added";
    }

    public Long get24h(int tid, int did, int mid, String type) {
//        String query = "from(bucket: \"test\") " +
//                "|> range(start:-24h)" +
//                "|> filter(fn: (r) => r._measurement == \"usage\" and r._field == \"tenantId\"  and r._value == " + id + ")" +
//                "|> count()";
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Flux flux = Flux
                .from("testTag")
                .range(-24L, ChronoUnit.HOURS)
                .filter(
                        Restrictions.and(
                                Restrictions.measurement().equal("usage"),
                                Restrictions.tag("tenantId").equal(Integer.toString(tid)),
                                Restrictions.tag("dealerId").equal(Integer.toString(did)),
                                Restrictions.tag("moduleId").equal(Integer.toString(mid)),
                                Restrictions.tag("type").equal(type)
                        )
                )
                .count();

        List<FluxTable> tables = influxDB.getQueryApi().query(flux.toString());

        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
        if (tables.isEmpty())
            return 0L;

        return (Long) tables.get(0).getRecords().get(0).getValue();

    }

    public String addLimitConfig(QuotaDto quotaDto) {
        Quota quota = Quota.builder()
                .tenantId(quotaDto.getTenantId())
                .dealerId(quotaDto.getDealerId())
                .moduleId(quotaDto.getModuleId())
                .type(quotaDto.getType())
                .limit(quotaDto.getLimit())
                .build();

        quotaRepository.save(quota);

        return "added";
    }


    public String checkAndUpdateUsage(QuotaDto quotaDto) {
        Optional<Quota> optionalQuota = quotaRepository.findByTenantIdAndDealerIdAndModuleIdAndType(
                quotaDto.getTenantId(),
                quotaDto.getDealerId(),
                quotaDto.getModuleId(),
                quotaDto.getType());

        if (optionalQuota.isEmpty()) return "Quota not found";

        Quota quota = optionalQuota.get();

        Long usage = get24h(
                quotaDto.getTenantId(),
                quotaDto.getDealerId(),
                quotaDto.getModuleId(),
                quotaDto.getType());

        if (usage == quota.getLimit()) return "Limit Exceed";

        double percentUsage = (double) usage * 100 / (double) quota.getLimit();

        if (percentUsage > (double) 80) {
            //Send Notifications
            System.out.println("Sending Notification");

        }

        //update usage table
        add(
                Usage.builder()
                        .id(5)
                        .tenantId(quotaDto.getTenantId())
                        .dealerId(quotaDto.getDealerId())
                        .moduleId(quotaDto.getModuleId())
                        .type(quotaDto.getType())
                        .build());

        return "Updated Quota Successfully";
    }
}
