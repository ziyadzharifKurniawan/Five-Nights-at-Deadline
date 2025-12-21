package fnaf.aim.groupA.backend.controller;

import fnaf.aim.groupA.backend.model.GameSession;
import fnaf.aim.groupA.backend.service.SessionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @PostMapping("/start")
    public GameSession start() {
        return service.startSession();
    }

    @PostMapping("/jumpscare/{id}")
    public GameSession jumpscare(@PathVariable String id) {
        return service.applyJumpscare(id);
    }

    @PostMapping("/end/{id}")
    public GameSession end(@PathVariable String id) {
        return service.endSession(id);
    }
}
