package org.galax1y.entities;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * Session is a value object
 * A value object is an immutable object that is defined by its attributes rather than a unique identity.
 */

public class Session {
    private final UUID accountId;
    private final Date createdAt;
    private final Date expiresAt;

    public Session(UUID accountId) {
        Instant now = Instant.now();
        Instant tomorrow = now.plus(Duration.ofDays(1));

        this.accountId = accountId;
        this.createdAt = Date.from(now);
        this.expiresAt = Date.from(tomorrow);
    }

    public static Session create(UUID accountId) {
        return new Session(accountId);
    }

    public UUID getAccountId() {
        return accountId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public boolean isActive() {
        return Instant.now().isBefore(expiresAt.toInstant());
    }
}
