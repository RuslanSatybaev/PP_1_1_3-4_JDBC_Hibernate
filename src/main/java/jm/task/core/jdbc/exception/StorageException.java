package jm.task.core.jdbc.exception;

public class StorageException extends RuntimeException{
    public StorageException(Exception message) {
        super(message);
    }
}
