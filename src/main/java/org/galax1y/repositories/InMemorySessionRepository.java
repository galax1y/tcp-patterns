package org.galax1y.repositories;

import org.galax1y.entities.Session;

import java.util.*;

public class InMemorySessionRepository implements ISessionRepository {
    private final Map<UUID, Session> items = new HashMap<>();

    @Override
    public Optional<Session> getByAccountId(UUID accountId) {
        return Optional.ofNullable(items.get(accountId));
    }

    @Override
    public List<Session> getAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public Session create(Session session) {
        items.put(session.getAccountId(), session);
        return session;
    }

    @Override
    public Session remove(Session session) {
        return items.remove(session.getAccountId());
    }
}
