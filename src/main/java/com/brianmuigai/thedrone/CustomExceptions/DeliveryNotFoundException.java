package com.brianmuigai.thedrone.CustomExceptions;

import com.brianmuigai.thedrone.entities.Delivery;

public class DeliveryNotFoundException extends RuntimeException {

    public DeliveryNotFoundException(Long id) {
        super("Delivery with id "+id+" not found!");
    }
}
