package com.poc.comm;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CommApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommApplication.class, args);
	}

	@Bean
	InfluxDBClient getInfluxDB() {
		String token = "wgwaT1trYu8kuVVOzRikoq5N0tdetXKmGS0aEow1vUHQzT6Q-3WgQiGTtcK2MT0WTpiU7JrLtaP_Ph1VBaaAng==";
		String bucket = "testTag";
		String org = "galaxy";

		return InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray(), org, bucket);
	}



}
