package com.tangdocu.old_stuff_donation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DonationRequest {
    @JsonProperty("itemName")
    private String itemName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("donorName")
    private String donorName;

    @JsonProperty("contactInfo")
    private String contactInfo;
} 