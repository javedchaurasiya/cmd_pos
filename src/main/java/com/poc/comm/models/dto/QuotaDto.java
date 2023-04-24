package com.poc.comm.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class QuotaDto {
    private int tenantId;
    private int dealerId;
    private int moduleId;
    private String type;
    private int limit;
}
