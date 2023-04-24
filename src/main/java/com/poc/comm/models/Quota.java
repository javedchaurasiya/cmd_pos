package com.poc.comm.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("quota")
public class Quota {

    @MongoId
    private String id;

    private int tenantId;
    private int dealerId;
    private int moduleId;
    private String type;
    private int limit;

}
