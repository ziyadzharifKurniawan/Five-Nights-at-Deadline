package fnaf.aim.groupA.logic;

import fnaf.aim.groupA.hud.BatteryMeterHUD;
import fnaf.aim.groupA.hud.LabReportMeterHUDHUD;
import fnaf.aim.groupA.hud.SanityMeterHUD;

public class PlaySession {

    // ===== SESSION CORE =====
    private float totalTime;
    private boolean active;

    private LabReportMeterHUDHUD lab;
    private BatteryMeterHUD battery;
    private SanityMeterHUD sanity;
    private EnemyManager enemyManager;

    // ===== PLAYER STATE =====
    private boolean laptopOpen;
    private boolean phoneOpen;

    // ===== FLASH =====
    private float leftFlashTimer = 0f;
    private float rightFlashTimer = 0f;

    private static final float FLASH_DURATION = 1f;
    private static final int FLASH_COST = 5;

    // ===== JUMPSCARE SYSTEM =====
    private float jumpscareTimer = 0f;
    private float nextJumpscareDelay = 12f;
    private float difficultyMultiplier = 1f;

    private boolean warningActive = false;
    private boolean warningLeft = true;

    // ===== CONSTRUCTOR (UNCHANGED SIGNATURE) =====
    public PlaySession(float totalTime,
                       LabReportMeterHUDHUD lab,
                       BatteryMeterHUD battery,
                       SanityMeterHUD sanity,
                       EnemyManager enemyManager) {

        this.totalTime = totalTime;
        this.lab = lab;
        this.battery = battery;
        this.sanity = sanity;
        this.enemyManager = enemyManager;
    }

    // ===== SESSION CONTROL =====
    public void start() {
        active = true;
    }

    public boolean isActive() {
        return active;
    }

    // ===== DEVICE STATES =====
    public void setLaptopOpen(boolean open) {
        laptopOpen = open;
    }

    public void setPhoneOpen(boolean open) {
        phoneOpen = open;
    }

    // ===== FLASH CONTROL =====
    public void flashLeftWindow() {
        if (battery.getValue() >= FLASH_COST) {
            battery.decrease(FLASH_COST);
            leftFlashTimer = FLASH_DURATION;
        }
    }

    public void flashRightWindow() {
        if (battery.getValue() >= FLASH_COST) {
            battery.decrease(FLASH_COST);
            rightFlashTimer = FLASH_DURATION;
        }
    }

    public boolean isLeftFlashing() {
        return leftFlashTimer > 0f;
    }

    public boolean isRightFlashing() {
        return rightFlashTimer > 0f;
    }

    // ===== UPDATE LOOP =====
    public void update(float delta) {
        if (!active) return;

        totalTime -= delta;
        if (totalTime <= 0f) {
            active = false;
            return;
        }

        if (leftFlashTimer > 0f) leftFlashTimer -= delta;
        if (rightFlashTimer > 0f) rightFlashTimer -= delta;

        enemyManager.update(
            delta,
            phoneOpen,
            isLeftFlashing(),
            isRightFlashing()
        );

        updateJumpscareSystem(delta);
    }

    // ===== JUMPSCARE LOGIC =====
    private void updateJumpscareSystem(float delta) {
        if (warningActive) return;

        jumpscareTimer += delta;

        if (jumpscareTimer >= nextJumpscareDelay) {
            jumpscareTimer = 0f;
            rollForWarning();
            scaleDifficulty();
        }
    }

    private void rollForWarning() {
        float chance = Math.min(0.2f * difficultyMultiplier, 0.85f);
        if (Math.random() < chance) {
            warningActive = true;
            warningLeft = Math.random() < 0.5;
        }
    }

    private void scaleDifficulty() {
        difficultyMultiplier += 0.05f;
        nextJumpscareDelay = Math.max(3f, 12f / difficultyMultiplier);
    }

    // ===== JUMPSCARE API =====
    public boolean isWarningActive() {
        return warningActive;
    }

    public boolean isWarningLeft() {
        return warningLeft;
    }

    public void clearWarning() {
        warningActive = false;
    }

    public void applyJumpscarePenalty() {
        int penalty = Math.max(1, sanity.getValue() / 10); // ~10%
        sanity.decrease(penalty);
    }
}
