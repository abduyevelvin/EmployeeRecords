package com.employee.record.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {

	private T data;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String message;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int status;
}