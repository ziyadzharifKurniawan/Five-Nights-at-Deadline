package fnaf.aim.groupA.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import fnaf.aim.groupA.utils.Constants;

public class ClickExitProcessor extends InputAdapter {
    private final OrthographicCamera camera;
    private final Vector3 tmp = new Vector3();
    public ClickExitProcessor(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        tmp.set(screenX, screenY, 0);
        camera.unproject(tmp);

        float cx = camera.position.x + camera.viewportWidth / 2f
            - Constants.POWER_ICON_MARGIN - Constants.POWER_ICON_R;
        float cy = camera.position.y + camera.viewportHeight / 2f
            - Constants.POWER_ICON_MARGIN - Constants.POWER_ICON_R;

        float dx = tmp.x - cx;
        float dy = tmp.y - cy;
        float r = Constants.POWER_ICON_R + 6f;
        if (dx * dx + dy * dy <= r * r) {
            Gdx.app.exit();
            return true;
        }
        return false;
    }
}
