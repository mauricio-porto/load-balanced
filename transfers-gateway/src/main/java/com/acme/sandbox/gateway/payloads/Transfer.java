package com.acme.sandbox.gateway.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transfer {

    @NotBlank
    private String assetType;

    @NotBlank
    private String assetValue;

    private Long assetManufacturerId;

    private Long transferTypeId;

    private Long assetId;

    private Long transferInstructionId;
}
