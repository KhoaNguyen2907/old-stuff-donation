package com.tangdocu.old_stuff_donation.function;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tangdocu.old_stuff_donation.model.DonationRequest;
import com.tangdocu.old_stuff_donation.model.DonationResponse;
import com.tangdocu.old_stuff_donation.service.DonationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DonationFunction implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    
    private final ObjectMapper objectMapper;
    private final DonationService donationService;

    @Override
    public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        setDefaultHeaders(response);

        try {
            // Parse request
            DonationRequest donationRequest = objectMapper.readValue(input.getBody(), DonationRequest.class);
            
            // Process using service
            DonationResponse result = donationService.processDonation(donationRequest);
            
            // Set response
            response.setStatusCode(200);
            response.setBody(objectMapper.writeValueAsString(result));
            
        } catch (JsonProcessingException e) {
            handleError(response, e);
        }
        
        return response;
    }

    private void setDefaultHeaders(APIGatewayProxyResponseEvent response) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        response.setHeaders(headers);
    }

    private void handleError(APIGatewayProxyResponseEvent response, Exception e) {
        response.setStatusCode(400);
        log.error("Error processing donation: {}", e.getMessage());
        response.setBody("{\"error\":\"Internal Server Error\"}");
    }
} 