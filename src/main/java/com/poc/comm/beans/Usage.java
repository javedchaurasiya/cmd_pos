package com.poc.comm.beans;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usage {
    private int id;
    private int tenantId;
    private int dealerId;
    private int moduleId;
    private String type;
}
