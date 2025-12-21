package fnaf.aim.groupA.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import fnaf.aim.groupA.assets.AssetLoader;
import fnaf.aim.groupA.rendering.SceneRenderer;
import fnaf.aim.groupA.screens.RoomKeyPanController;
import fnaf.aim.groupA.screens.ClickExitProcessor;

import fnaf.aim.groupA.hud.*;
import fnaf.aim.groupA.items.*;
import fnaf.aim.groupA.ui.*;
import fnaf.aim.groupA.logic.*;
import fnaf.aim.groupA.utils.Constants;

public class GameScreenInput implements Screen {

    private OrthographicCamera camera, hudCamera;
    private Viewport viewport;
    private SpriteBatch batch;
    private ShapeRenderer shapes;

    private SceneRenderer scene;
    private RoomKeyPanController pan;
    private ClickExitProcessor exit;

    private BatteryMeterHUD battery;
    private SanityMeterHUD sanity;
    private LabReportMeterHUDHUD lab;
    private CountdownTimerHUD timerHud;
    private RendererHUD hud;

    private LaptopItem laptopItem;
    private PhoneItem phoneItem;
    private IpadItem ipad;

    private LaptopOverlay laptopOverlay;
    private PhoneOverlay phoneOverlay;
    private IpadOverlay ipadOverlay;

    private PlaySession session;
    private EnemyManager enemies;

    private ReadyDialogTable dialog;
    private boolean dialogOpen = true;

    private int sw, sh, rw, rh;

    private float leftX, leftY, rightX, rightY, winW, winH;

    private boolean jumpscareMinigameActive = false;
    private float jumpscareTimeLeft = 0f;
    private float jumpscareProgress = 1f;
    private float jumpscareDrainAcc = 0f;
    private float jumpscareTeleportAcc = 0f;
    private float jumpscareCircleX = 0f;
    private float jumpscareCircleY = 0f;
    private Texture jumpscareEnemyTexture;
    private final Texture[] jumpscareEnemyTextures = new Texture[6];
    private final Random jumpscareRng = new Random();

    int screenW = Gdx.graphics.getWidth();
    int screenH = Gdx.graphics.getHeight();
    // Screen-space click coords
    float sx = Gdx.input.getX();
    float sy = screenH - Gdx.input.getY();

    public GameScreenInput(AssetLoader assets) {

        sw = Gdx.graphics.getWidth();
        sh = Gdx.graphics.getHeight();
        rw = (int)(sw * 1.4f);
        rh = sh;

        camera = new OrthographicCamera();
        viewport = new FitViewport(sw, sh, camera);
        viewport.apply();
        camera.position.set(rw * 0.5f, rh * 0.5f, 0);
        camera.update();

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, sw, sh);

        batch = new SpriteBatch();
        shapes = new ShapeRenderer();

        scene = new SceneRenderer();
        pan = new RoomKeyPanController(camera, sw, sh, rw);
        exit = new ClickExitProcessor(camera);

        battery = new BatteryMeterHUD(50, sh - 50, 200, 20);
        sanity = new SanityMeterHUD(300, sh - 50, 200, 20);
        lab = new LabReportMeterHUDHUD(550, sh - 50, 200, 20);

        laptopItem = new LaptopItem(assets.laptopTexture(), 0, 0, 128, 96);
        phoneItem = new PhoneItem(assets.phoneTexture(), 0, 0, 64, 64);
        ipad = new IpadItem(assets.ipadTexture(), 0, 0, 96, 96);

        dialog = new ReadyDialogTable(sw, sh);

        float wy = rh - Constants.WINDOW_MARGIN - Constants.WINDOW_SIZE_H;
        leftX = 200f;
        leftY = wy;
        rightX = rw - 200f - Constants.WINDOW_SIZE_W;
        rightY = wy;
        winW = Constants.WINDOW_SIZE_W;
        winH = Constants.WINDOW_SIZE_H;

        enemies = new EnemyManager(
            0, 0, 1, 1,
            leftX + winW * 0.5f, leftY,
            rightX + winW * 0.5f, rightY
        );

        session = new PlaySession(300f, lab, battery, sanity, enemies);
        timerHud = new CountdownTimerHUD(300f, sw - 220, sh - 30);

        hud = new RendererHUD(
            battery, sanity, lab,
            assets.batteryIcon(), assets.sanityIcon(), assets.labReportIcon()
        );

        laptopOverlay = new LaptopOverlay(assets.laptopTexture(), 600, 400);
        phoneOverlay = new PhoneOverlay(assets.phoneTexture(), 400, 600);

        // Changed into percentage of screen, instead of fixed res
        float ipadW = Gdx.graphics.getWidth()  * 0.55f;
        float ipadH = Gdx.graphics.getHeight() * 0.90f;
        ipadOverlay = new IpadOverlay(assets.ipadTexture(), ipadW, ipadH, enemies);

        for (int i = 1; i <= 5; i++) {
            jumpscareEnemyTextures[i] = assets.enemyTexture(i);
        }
    }

    @Override
    public void render(float delta) {

        if (dialogOpen) {
            camera.position.set(rw * 0.5f, rh * 0.5f, 0);
            camera.update();
            scene.draw(camera, rw, rh);
            dialog.draw(shapes, batch);

            if (Gdx.input.justTouched()) {
                int x = Gdx.input.getX();
                int y = sh - Gdx.input.getY();

                if (dialog.checkYesClick(x, y)) {
                    dialogOpen = false;
                    session.start();
                    timerHud.reset();
                    Gdx.input.setInputProcessor(new InputMultiplexer(exit));
                }
                if (dialog.checkNoClick(x, y)) Gdx.app.exit();
            }
            return;
        }

        if (jumpscareMinigameActive) {
            updateJumpscareMinigame(delta);
            renderJumpscareMinigame();
            return;
        }

        session.setLaptopOpen(laptopOverlay.isVisible());
        session.setPhoneOpen(phoneOverlay.isVisible());
        session.update(delta);
        timerHud.update(delta);

        int jumpscareEnemyId = session.consumeJumpscareEnemyId();
        if (jumpscareEnemyId != 0) {
            startJumpscareMinigame(jumpscareEnemyId);
            return;
        }

        if (!session.isActive() || timerHud.isFinished()) {
            dialogOpen = true;
            return;
        }

        pan.update();
        camera.update();
        viewport.apply();
        scene.draw(camera, rw, rh);

        float mx = scene.mapX + scene.mapW * 0.5f;
        float my = scene.mapY + scene.mapH * 0.5f;
        float pad = scene.mapW * 0.1f;

        laptopItem.setPosition(mx - laptopItem.getWidth() * 0.5f, my - laptopItem.getHeight() * 0.5f);
        phoneItem.setPosition(scene.mapX + pad, my - phoneItem.getHeight() * 0.5f);
        ipad.setPosition(scene.mapX + scene.mapW - pad - ipad.getWidth(), my - ipad.getHeight() * 0.5f);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        phoneItem.render(batch);
        laptopItem.render(batch);
        ipad.render(batch);
        batch.end();

        if (Gdx.input.justTouched()) handleInput();
        scene.renderWindowFlash(batch, session.isLeftFlashing(), session.isRightFlashing());

        shapes.setProjectionMatrix(hudCamera.combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        hud.renderBars(shapes);
        shapes.end();

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        hud.renderIcons(batch);
        timerHud.render(batch);
        batch.end();

        laptopOverlay.update(delta);
        laptopOverlay.render(batch);

        phoneOverlay.update(delta);
        phoneOverlay.render(batch);

        ipadOverlay.update(delta);
        ipadOverlay.render(batch);
    }

    private void startJumpscareMinigame(int enemyId) {
        jumpscareMinigameActive = true;
        jumpscareTimeLeft = 30f;
        jumpscareProgress = 1f;
        jumpscareDrainAcc = 0f;
        jumpscareTeleportAcc = 0f;
        jumpscareEnemyTexture = enemyId >= 1 && enemyId <= 5 ? jumpscareEnemyTextures[enemyId] : null;
        teleportJumpscareCircle();

        laptopOverlay.hide();
        phoneOverlay.hide();
        ipadOverlay.hide();
    }

    private void updateJumpscareMinigame(float delta) {
        jumpscareTimeLeft = Math.max(0f, jumpscareTimeLeft - delta);
        jumpscareTeleportAcc += delta;
        while (jumpscareTeleportAcc >= 3f) {
            jumpscareTeleportAcc -= 3f;
            teleportJumpscareCircle();
        }

        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;
        float r = Math.min(w, h) * 0.12f;

        float mx = Gdx.input.getX();
        float my = h - Gdx.input.getY();
        float dx = mx - jumpscareCircleX;
        float dy = my - jumpscareCircleY;
        boolean inside = dx * dx + dy * dy <= r * r;

        if (!inside) {
            jumpscareDrainAcc += delta;
            while (jumpscareDrainAcc >= 1f) {
                jumpscareProgress -= 0.10f;
                jumpscareDrainAcc -= 1f;
            }
        } else {
            jumpscareDrainAcc = 0f;
        }

        if (jumpscareProgress <= 0f) {
            jumpscareProgress = 0f;
            jumpscareMinigameActive = false;
            dialogOpen = true;
            return;
        }

        if (jumpscareTimeLeft <= 0f) {
            jumpscareMinigameActive = false;
        }
    }

    private void teleportJumpscareCircle() {
        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;
        float r = Math.min(w, h) * 0.12f;

        float minX = r;
        float maxX = w - r;

        float minY = r + h * 0.18f;
        float maxY = h - r;

        if (maxX <= minX) {
            jumpscareCircleX = w * 0.5f;
        } else {
            jumpscareCircleX = minX + jumpscareRng.nextFloat() * (maxX - minX);
        }

        if (maxY <= minY) {
            jumpscareCircleY = h * 0.5f;
        } else {
            jumpscareCircleY = minY + jumpscareRng.nextFloat() * (maxY - minY);
        }
    }

    private void renderJumpscareMinigame() {
        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        if (jumpscareEnemyTexture != null) {
            batch.setProjectionMatrix(hudCamera.combined);
            batch.begin();
            batch.draw(jumpscareEnemyTexture, 0f, 0f, w, h);
            batch.end();
        }

        shapes.setProjectionMatrix(hudCamera.combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        if (jumpscareEnemyTexture == null) {
            shapes.setColor(1f, 0f, 0f, 1f);
            shapes.rect(0f, 0f, w, h);
        }

        float r = Math.min(w, h) * 0.12f;
        shapes.setColor(0f, 0.35f, 1f, 1f);
        shapes.circle(jumpscareCircleX, jumpscareCircleY, r);

        float barW = w * 0.8f;
        float barH = Math.max(18f, h * 0.03f);
        float barX = (w - barW) * 0.5f;
        float barY = h * 0.08f;

        shapes.setColor(0f, 0f, 0f, 0.55f);
        shapes.rect(barX, barY, barW, barH);

        shapes.setColor(0f, 1f, 0f, 1f);
        shapes.rect(barX, barY, barW * MathUtils.clamp(jumpscareProgress, 0f, 1f), barH);

        shapes.end();
    }

    private void handleInput() {

        Vector3 t = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(t);

        if (laptopOverlay.isVisible()) {
            float lx = (sw - 600) * 0.5f;
            float ly = (sh - 400) * 0.5f;
            if (!(t.x >= lx && t.x <= lx + 600 && t.y >= ly && t.y <= ly + 400)) {
                laptopOverlay.hide();
                return;
            }
        }

        if (phoneOverlay.isVisible()) {
            float px = (sw - 400) * 0.5f;
            float py = (sh - 600) * 0.5f;
            if (!(t.x >= px && t.x <= px + 400 && t.y >= py && t.y <= py + 600)) {
                phoneOverlay.hide();
                return;
            }
        }

        // CLOSE LOGIC — IPAD
        float ipadW = ipadOverlay.getWidth();
        float ipadH = ipadOverlay.getHeight();

        if (ipadOverlay.isVisible()) {
            float ix = (screenW - ipadW) * 0.5f;
            float iy = (screenW - ipadH) * 0.5f;

            if (!(sx >= ix && sx <= ix + ipadW && sy >= iy && sy <= iy + ipadH)) {
                ipadOverlay.hide();
            }
        }

        boolean hitLaptop =
            t.x >= laptopItem.getX() && t.x <= laptopItem.getX() + laptopItem.getWidth() &&
                t.y >= laptopItem.getY() && t.y <= laptopItem.getY() + laptopItem.getHeight();

        boolean hitPhone =
            t.x >= phoneItem.getX() && t.x <= phoneItem.getX() + phoneItem.getWidth() &&
                t.y >= phoneItem.getY() && t.y <= phoneItem.getY() + phoneItem.getHeight();

        boolean hitIpad =
            t.x >= ipad.getX() && t.x <= ipad.getX() + ipad.getWidth() &&
                t.y >= ipad.getY() && t.y <= ipad.getY() + ipad.getHeight();

        if (hitLaptop && !ipadOverlay.isVisible()) {
            laptopOverlay.show();
            return;
        }

        if (hitPhone && !ipadOverlay.isVisible()) {
            phoneOverlay.show();
            return;
        }

        if (hitIpad && !laptopOverlay.isVisible() && !phoneOverlay.isVisible()) {
            if (ipadOverlay.isVisible()) ipadOverlay.hide();
            else ipadOverlay.show();
            return;
        }

        if (!ipadOverlay.isVisible()) {
            if (t.x >= leftX && t.x <= leftX + winW &&
                t.y >= leftY && t.y <= leftY + winH) {
                session.flashLeftWindow();
            }

            if (t.x >= rightX && t.x <= rightX + winW &&
                t.y >= rightY && t.y <= rightY + winH) {
                session.flashRightWindow();
            }
        }
    }

    @Override
    public void resize(int w, int h) {
        viewport.update(w, h);
        camera.position.set(w * 0.5f, h * 0.5f, 0);
        camera.update();
        hudCamera.setToOrtho(false, w, h);
    }

    @Override public void dispose() {
        batch.dispose();
        shapes.dispose();
        scene.dispose();
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
