package fnaf.aim.groupA.backend.model;

import java.time.Instant;
import java.util.UUID;

public class GameSession {

    private String id;
    private int sanity;
    private int jumpscares;
    private boolean alive;
    private Instant startedAt;
    private Instant endedAt;

    public GameSession() {
        this.id = UUID.randomUUID().toString();
        this.sanity = 100;
        this.jumpscares = 0;
        this.alive = true;
        this.startedAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public int getSanity() {
        return sanity;
    }

    public void decreaseSanity(int value) {
        sanity = Math.max(0, sanity - value);
        if (sanity == 0) alive = false;
    }

    public int getJumpscares() {
        return jumpscares;
    }

    public void addJumpscare() {
        jumpscares++;
    }

    public boolean isAlive() {
        return alive;
    }

    public void end() {
        this.endedAt = Instant.now();
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getEndedAt() {
        return endedAt;
    }
}
