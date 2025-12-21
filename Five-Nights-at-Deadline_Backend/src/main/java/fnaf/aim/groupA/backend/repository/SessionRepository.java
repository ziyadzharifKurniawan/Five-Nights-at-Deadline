package fnaf.aim.groupA.backend.repository;

import fnaf.aim.groupA.backend.model.GameSession;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SessionRepository {

    private final Map<String, GameSession> sessions = new ConcurrentHashMap<>();

    public GameSession save(GameSession session) {
        sessions.put(session.getId(), session);
        return session;
    }

    public GameSession find(String id) {
        return sessions.get(id);
    }
}
