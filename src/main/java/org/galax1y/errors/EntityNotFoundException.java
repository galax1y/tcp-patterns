package org.galax1y.errors;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, UUID id) {
        super(String.format("Entity type '%s' with ID '%s' not found.", entityName, id));
    }
}
