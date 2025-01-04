package com.tangdocu.old_stuff_donation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tangdocu.old_stuff_donation.model.DonationRequest;
import com.tangdocu.old_stuff_donation.model.DonationResponse;

@ExtendWith(MockitoExtension.class)
class DonationServiceTest {

    @InjectMocks
    private DonationService donationService;

    @Test
    void processDonation_ShouldReturnSuccessResponse() {
        // Arrange
        DonationRequest request = new DonationRequest();
        request.setItemName("Test Item");
        request.setDonorName("Test Donor");
        request.setDescription("Test Description");
        request.setContactInfo("test@email.com");

        // Act
        DonationResponse response = donationService.processDonation(request);

        // Assert
        assertNotNull(response);
        assertEquals("Donation request processed successfully", response.getMessage());
        assertEquals("Test Item", response.getItemName());
        assertEquals("Test Donor", response.getDonorName());
        assertEquals("ACCEPTED", response.getStatus());
    }
} 