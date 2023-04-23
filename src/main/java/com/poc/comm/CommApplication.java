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
		String token = "gz1tKtgJiiDHN4-c1EQoUEvjHc4krVkXukNpAki0fzbe_05vEb812cckgOCNcMSU2Ed7U5gW1DPEmEolmt-flw==";
		String bucket = "comm";
		String org = "tekion";

		return InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray(), org, bucket);
	}



}
