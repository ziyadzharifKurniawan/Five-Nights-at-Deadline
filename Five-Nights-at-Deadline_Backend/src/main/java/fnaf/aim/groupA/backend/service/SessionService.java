package fnaf.aim.groupA.backend.service;

import fnaf.aim.groupA.backend.model.GameSession;
import fnaf.aim.groupA.backend.repository.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository repository;

    public SessionService(SessionRepository repository) {
        this.repository = repository;
    }

    public GameSession startSession() {
        GameSession session = new GameSession();
        return repository.save(session);
    }

    public GameSession applyJumpscare(String sessionId) {
        GameSession session = repository.find(sessionId);
        session.addJumpscare();
        session.decreaseSanity(10);
        return session;
    }

    public GameSession endSession(String sessionId) {
        GameSession session = repository.find(sessionId);
        session.end();
        return session;
    }
}
