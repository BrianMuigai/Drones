package com.brianmuigai.thedrone.CustomExceptions;

public class MedicationNotFoundException extends RuntimeException{

    public MedicationNotFoundException(String code) {
        super("Medication with code "+code+"not found");
    }
}
