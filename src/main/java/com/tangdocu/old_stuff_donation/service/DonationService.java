package com.tangdocu.old_stuff_donation.service;

import com.tangdocu.old_stuff_donation.model.DonationRequest;
import com.tangdocu.old_stuff_donation.model.DonationResponse;
import org.springframework.stereotype.Service;

@Service
public class DonationService {
    
    public DonationResponse processDonation(DonationRequest request) {
        // Here you would add your business logic, database operations, etc.
        return DonationResponse.builder()
                .message("Donation request processed successfully")
                .itemName(request.getItemName())
                .donorName(request.getDonorName())
                .status("ACCEPTED")
                .build();
    }
} 