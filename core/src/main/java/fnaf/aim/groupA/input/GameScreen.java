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
