package fnaf.aim.groupA.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;

public class MouseLookController extends InputAdapter {

    public float offsetX = 0;
    public float offsetY = 0;
    private static final float SENSITIVITY = 0.5f;
    private static final float MAX_OFFSET = 200f;

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        offsetX += Gdx.input.getDeltaX() * SENSITIVITY;
        offsetY -= Gdx.input.getDeltaY() * SENSITIVITY;

        offsetX = MathUtils.clamp(offsetX, -MAX_OFFSET, MAX_OFFSET);
        offsetY = MathUtils.clamp(offsetY, -MAX_OFFSET, MAX_OFFSET);
        return true;
    }
}
