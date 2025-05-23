package org.galax1y.errors;

public class ConflictException extends RuntimeException {
    public ConflictException(String entity, String field) {
        super(String.format("Conflict has occurred on field '%s' on entity '%s'.", field, entity));
    }
}
