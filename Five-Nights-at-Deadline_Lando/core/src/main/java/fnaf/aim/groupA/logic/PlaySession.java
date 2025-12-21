package fnaf.aim.groupA.logic;

import fnaf.aim.groupA.hud.BatteryMeterHUD;
import fnaf.aim.groupA.hud.SanityMeterHUD;
import fnaf.aim.groupA.hud.LabReportMeterHUDHUD;

public class PlaySession {

    private float playTimer = 0f;
    private final float duration;
    private boolean active = false;

    private LabReportMeterHUDHUD lab;
    private BatteryMeterHUD battery;
    private SanityMeterHUD sanity;

    private float batteryAcc = 0f;
    private float sanityAcc = 0f;
    private float labPassiveAcc = 0f;
    private float labBoostAcc = 0f;

    private static final float LAB_PASSIVE = 0.33f;
    private static final float LAB_LAPTOP_BOOST = 0.22f;
    private static final float LAPTOP_BATTERY = 0.55f;
    private static final float LAPTOP_SANITY = 0.42f;

    private static final float PHONE_SANITY_RECOVERY = 0.60f;
    private static final float PHONE_BATTERY_DRAIN = 0.25f;

    private static final float SANITY_LOCK = 20f;

    private boolean laptopOpen = false;
    private boolean phoneOpen = false;

    private float leftFlashTimer = 0f;
    private float rightFlashTimer = 0f;

    private float flashDuration = 1f;
    private int flashBatteryCost = 5;

    private EnemyManager enemyManager;

    public PlaySession(float durationSeconds,
                       LabReportMeterHUDHUD lab,
                       BatteryMeterHUD battery,
                       SanityMeterHUD sanity,
                       EnemyManager enemyManager) {

        this.duration = durationSeconds;
        this.lab = lab;
        this.battery = battery;
        this.sanity = sanity;
        this.enemyManager = enemyManager;
    }

    public void start() {
        playTimer = 0f;
        active = true;
        lab.reset();
    }

    public boolean isActive() {
        return active;
    }

    public void setLaptopOpen(boolean open) {
        this.laptopOpen = open;
    }

    public void setPhoneOpen(boolean open) {
        this.phoneOpen = open;
    }

    public boolean canOpenLaptop() {
        return sanity.getValue() > SANITY_LOCK;
    }

    public void flashLeftWindow() {
        if (battery.getValue() > flashBatteryCost) {
            leftFlashTimer = flashDuration;
            battery.decrease(flashBatteryCost);
        }
    }

    public void flashRightWindow() {
        if (battery.getValue() > flashBatteryCost) {
            rightFlashTimer = flashDuration;
            battery.decrease(flashBatteryCost);
        }
    }

    public boolean isLeftFlashing() {
        return leftFlashTimer > 0f;
    }

    public boolean isRightFlashing() {
        return rightFlashTimer > 0f;
    }

    public void update(float delta) {
        if (!active) return;

        playTimer += delta;
        if (playTimer >= duration) {
            active = false;
            return;
        }

        if (leftFlashTimer > 0f) leftFlashTimer -= delta;
        if (rightFlashTimer > 0f) rightFlashTimer -= delta;

        labPassiveAcc += LAB_PASSIVE * delta;
        while (labPassiveAcc >= 1f) {
            lab.increase(1);
            labPassiveAcc -= 1f;
        }

        if (laptopOpen && canOpenLaptop()) {
            labBoostAcc += LAB_LAPTOP_BOOST * delta;
            while (labBoostAcc >= 1f) {
                lab.increase(1);
                labBoostAcc -= 1f;
            }

            batteryAcc += LAPTOP_BATTERY * delta;
            while (batteryAcc >= 1f) {
                battery.decrease(1);
                batteryAcc -= 1f;
            }

            sanityAcc += LAPTOP_SANITY * delta;
            while (sanityAcc >= 1f) {
                sanity.decrease(1);
                sanityAcc -= 1f;
            }
        }

        if (phoneOpen) {
            sanityAcc += PHONE_SANITY_RECOVERY * delta;
            while (sanityAcc >= 1f) {
                sanity.increase(1);
                sanityAcc -= 1f;
            }

            batteryAcc += PHONE_BATTERY_DRAIN * delta;
            while (batteryAcc >= 1f) {
                battery.decrease(1);
                batteryAcc -= 1f;
            }
        }

        enemyManager.update(delta, phoneOpen, isLeftFlashing(), isRightFlashing());
    }
}
