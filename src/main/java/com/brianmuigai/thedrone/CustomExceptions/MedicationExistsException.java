package com.brianmuigai.thedrone.CustomExceptions;

public class MedicationExistsException extends RuntimeException{
    public MedicationExistsException(String code) {
        super("Medication with code "+code+" already exists!");
    }
}
