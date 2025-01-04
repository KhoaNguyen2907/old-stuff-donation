package com.tangdocu.old_stuff_donation.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tangdocu.old_stuff_donation.model.DonationRequest;
import com.tangdocu.old_stuff_donation.model.DonationResponse;
import com.tangdocu.old_stuff_donation.service.DonationService;

@ExtendWith(MockitoExtension.class)
class DonationFunctionTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private DonationService donationService;

    @InjectMocks
    private DonationFunction donationFunction;

    private DonationRequest donationRequest;
    private DonationResponse donationResponse;
    private String requestJson;

    @BeforeEach
    void setUp() throws Exception {
        // Setup test data
        donationRequest = new DonationRequest();
        donationRequest.setItemName("Test Item");
        donationRequest.setDonorName("Test Donor");

        donationResponse = DonationResponse.builder()
                .message("Donation request processed successfully")
                .itemName("Test Item")
                .donorName("Test Donor")
                .status("ACCEPTED")
                .build();

        requestJson = "{\"itemName\":\"Test Item\",\"donorName\":\"Test Donor\"}";
    }

    @Test
    void apply_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        request.setBody(requestJson);

        when(objectMapper.readValue(requestJson, DonationRequest.class)).thenReturn(donationRequest);
        when(donationService.processDonation(any(DonationRequest.class))).thenReturn(donationResponse);
        when(objectMapper.writeValueAsString(donationResponse)).thenReturn("{\"message\":\"success\"}");

        // Act
        APIGatewayProxyResponseEvent response = donationFunction.apply(request);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertEquals("{\"message\":\"success\"}", response.getBody());
        assertNotNull(response.getHeaders());
        assertEquals("application/json", response.getHeaders().get("Content-Type"));
    }

    @Test
    void apply_ShouldHandleError() throws Exception {
        // Arrange
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        request.setBody("invalid json");

        when(objectMapper.readValue("invalid json", DonationRequest.class))
                .thenThrow(new JsonParseException (null,"Invalid JSON"));

        // Act
        APIGatewayProxyResponseEvent response = donationFunction.apply(request);

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getHeaders());
        assertEquals("application/json", response.getHeaders().get("Content-Type"));
    }
} 