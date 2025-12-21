package fnaf.aim.groupA.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class RoomKeyPanController {
    private final OrthographicCamera camera;
    private float panX;
    private final float speed = 12f; // pixels per frame

    private final float screenW;
    private final float screenH;
    private final float roomW;

    public RoomKeyPanController(OrthographicCamera camera, int screenW, int screenH, int roomW) {
        this.camera = camera;
        this.panX = camera.position.x;
        this.screenW = screenW;
        this.screenH = screenH;
        this.roomW = roomW;
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            panX += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            panX -= speed;
        }

        float halfWidth = screenW / 2f;
        float minX = halfWidth;
        float maxX = roomW - halfWidth;

        panX = MathUtils.clamp(panX, minX, maxX);
        camera.position.set(panX, screenH / 2f, 0);
        camera.update();
    }
}
