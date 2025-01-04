package com.tangdocu.old_stuff_donation.function;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class ReverseStringFunction implements Function<String, String> {

    @Override
    public String apply(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}
