package com.brianmuigai.thedrone.CustomExceptions;

public class FileStorageException extends RuntimeException{
    public FileStorageException(String msg) {
        super(msg);
    }

    public FileStorageException(String msg, Throwable t) {
        super(msg+"\n"+t.getMessage(), t);
    }
}
