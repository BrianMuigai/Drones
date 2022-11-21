package com.brianmuigai.thedrone.CustomExceptions;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(Long orderId) {
        super("Order with id "+orderId+" not found!");
    }
}
