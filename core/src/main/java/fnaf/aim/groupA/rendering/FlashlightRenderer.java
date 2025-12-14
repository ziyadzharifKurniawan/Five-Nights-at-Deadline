package fnaf.aim.groupA.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import fnaf.aim.groupA.utils.Constants;

public class FlashlightRenderer {

    private final ShapeRenderer shape = new ShapeRenderer();

    public void beginMask(OrthographicCamera camera) {
        Gdx.gl.glEnable(GL20.GL_STENCIL_TEST);
        Gdx.gl.glClear(GL20.GL_STENCIL_BUFFER_BIT);

        Gdx.gl.glStencilFunc(GL20.GL_ALWAYS, 1, 0xFF);
        Gdx.gl.glStencilOp(GL20.GL_KEEP, GL20.GL_KEEP, GL20.GL_REPLACE);
        Gdx.gl.glStencilMask(0xFF);

        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.circle(0, 0, Constants.FLASHLIGHT_RADIUS);
        shape.end();

        Gdx.gl.glStencilMask(0x00);
        Gdx.gl.glStencilFunc(GL20.GL_EQUAL, 1, 0xFF);
    }

    public void drawBlackOutside(OrthographicCamera camera) {
        Gdx.gl.glStencilFunc(GL20.GL_NOTEQUAL, 1, 0xFF);
        Gdx.gl.glStencilMask(0x00);

        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(0, 0, 0, 1);
        shape.rect(-4000, -4000, 8000, 8000);
        shape.end();

        Gdx.gl.glDisable(GL20.GL_STENCIL_TEST);
    }

    public void drawCrosshair(OrthographicCamera camera) {
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(1, 1, 1, 1);
        shape.circle(0, 0, 2);
        shape.end();
    }

    public void dispose() {
        shape.dispose();
    }
}
