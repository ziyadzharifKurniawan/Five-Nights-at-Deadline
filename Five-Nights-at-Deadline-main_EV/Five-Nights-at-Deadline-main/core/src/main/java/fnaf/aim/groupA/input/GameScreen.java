package fnaf.aim.groupA.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fnaf.aim.groupA.assets.AssetLoader;
import fnaf.aim.groupA.rendering.SceneRenderer;
import fnaf.aim.groupA.screens.RoomKeyPanController;
import fnaf.aim.groupA.screens.ClickExitProcessor;

// HUD imports
import fnaf.aim.groupA.hud.BatteryMeter;
import fnaf.aim.groupA.hud.SanityMeter;
import fnaf.aim.groupA.hud.LabReportMeter;

// Desk items
import fnaf.aim.groupA.items.Laptop;
import fnaf.aim.groupA.items.Phone;
import fnaf.aim.groupA.items.Ipad;

public class GameScreen implements Screen {
    private OrthographicCamera camera;      // room camera
    private OrthographicCamera hudCamera;   // HUD overlay camera
    private Viewport viewport;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private SceneRenderer scene;
    private RoomKeyPanController pan;

    private int screenW;
    private int screenH;
    private int roomW;
    private int roomH;

    // HUD meters
    private BatteryMeter battery;
    private SanityMeter sanity;
    private LabReportMeter labReport;

    // Desk items
    private Phone phone;
    private Laptop laptop;
    private Ipad ipad;

    private static final int HUD_PADDING = 30;

    public GameScreen(AssetLoader assets) {
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        roomW = (int)((float)screenW * 1.4);
        roomH = screenH;

        camera = new OrthographicCamera();
        viewport = new FitViewport(screenW, screenH, camera);
        viewport.apply();
        camera.position.set(roomW / 2f, roomH / 2f, 0);
        camera.update();

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, screenW, screenH);
        hudCamera.update();

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        scene = new SceneRenderer();

        pan = new RoomKeyPanController(camera, screenW, screenH, roomW);
        ClickExitProcessor exit = new ClickExitProcessor(camera);
        Gdx.input.setInputProcessor(new InputMultiplexer(exit));

        battery = new BatteryMeter(20 + HUD_PADDING, screenH - HUD_PADDING - 20, 200, 20);
        sanity = new SanityMeter(240 + (HUD_PADDING * 2), screenH - HUD_PADDING - 20, 200, 20);
        labReport = new LabReportMeter(460 + (HUD_PADDING * 3), screenH - HUD_PADDING - 20, 200, 20);

        phone  = new Phone(assets.phoneTexture(), 0, 0, 64, 64);
        laptop = new Laptop(assets.laptopTexture(), 0, 0, 128, 96);
        ipad   = new Ipad(assets.ipadTexture(), 0, 0, 96, 96);
    }

    @Override
    public void render(float delta) {
        pan.update();
        camera.update();

        viewport.apply();
        scene.draw(camera, roomW, roomH);

        // --- Desk items on top of map box ---
        float mapCenterX = scene.mapX + scene.mapW / 2f;
        float mapCenterY = scene.mapY + scene.mapH / 2f;
        float padding = scene.mapW * 0.1f;

        // Laptop centered horizontally, vertically in middle
        laptop.setPosition(mapCenterX - laptop.getWidth() / 2f,mapCenterY - laptop.getHeight() / 2f);

        // Phone left side with 10% padding
        phone.setPosition(scene.mapX + padding,mapCenterY - phone.getHeight() / 2f);

        // iPad right side with 10% padding
        ipad.setPosition(scene.mapX + scene.mapW - padding - ipad.getWidth(),mapCenterY - ipad.getHeight() / 2f);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        phone.render(batch);
        laptop.render(batch);
        ipad.render(batch);
        batch.end();

        // --- HUD meters + icons ---
        shapeRenderer.setProjectionMatrix(hudCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        battery.render(shapeRenderer);
        sanity.render(shapeRenderer);
        labReport.render(shapeRenderer);
        shapeRenderer.end();

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        battery.renderIcon(batch);
        sanity.renderIcon(batch);
        labReport.renderIcon(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(width / 2f, height / 2f, 0);
        camera.update();

        hudCamera.setToOrtho(false, width, height);
        hudCamera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        scene.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
/*
// Old Code
package fnaf.aim.groupA.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fnaf.aim.groupA.assets.AssetLoader;
import fnaf.aim.groupA.rendering.FlashlightRenderer;
import fnaf.aim.groupA.screens.MouseLookController;
import fnaf.aim.groupA.utils.Constants;

public class GameScreen implements Screen {

    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final AssetLoader assets;
    private final FlashlightRenderer flashlight;
    private final MouseLookController mouse;

    public GameScreen(AssetLoader assets) {
        this.assets = assets;

        camera = new OrthographicCamera(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        camera.zoom = Constants.CAMERA_ZOOM;
        camera.position.set(0, 0, 0);
        camera.update();

        batch = new SpriteBatch();
        flashlight = new FlashlightRenderer();

        mouse = new MouseLookController();
        Gdx.input.setInputProcessor(mouse);
    }

    @Override
    public void render(float delta) {
        camera.position.set(mouse.offsetX, mouse.offsetY, 0);
        camera.update();

        flashlight.beginMask(camera);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(
            assets.bg(),
            -Constants.WORLD_WIDTH / 2,
            -Constants.WORLD_HEIGHT / 2,
            Constants.WORLD_WIDTH,
            Constants.WORLD_HEIGHT
        );
        batch.end();

        flashlight.drawBlackOutside(camera);
        flashlight.drawCrosshair(camera);
    }

    @Override
    public void dispose() {
        batch.dispose();
        flashlight.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
*/
