package com.tangdocu.old_stuff_donation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DonationResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("itemName")
    private String itemName;

    @JsonProperty("donorName")
    private String donorName;

    @JsonProperty("status")
    private String status;
} 