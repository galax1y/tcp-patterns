package org.galax1y.repositories;

import org.galax1y.entities.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ISessionRepository {
    Optional<Session> getByAccountId(UUID accountId);
    List<Session> getAll();
    Session create(Session session);
    Session remove(Session session);
}
