package fnaf.aim.groupA.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import fnaf.aim.groupA.logic.EnemyLogic;
import fnaf.aim.groupA.logic.EnemyManager;

public class IpadOverlay {

    private Texture texture;
    private OrthographicCamera camera;
    private ShapeRenderer shape;

    private float x;
    private float y;
    private float w;
    private float h;

    private boolean visible;

    private EnemyManager enemyManager;

    private float laneX;
    private float laneY;
    private float laneW;
    private float laneH;

    private float windowW = 60f;
    private float windowH = 80f;

    private float leftWinX;
    private float leftWinY;

    private float rightWinX;
    private float rightWinY;

    public IpadOverlay(Texture texture,
                       float w,
                       float h,
                       EnemyManager enemyManager) {

        this.texture = texture;
        this.w = w;
        this.h = h;
        this.enemyManager = enemyManager;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        x = (Gdx.graphics.getWidth() - w) / 2f;
        y = (Gdx.graphics.getHeight() - h) / 2f;

        shape = new ShapeRenderer();
        visible = false;
    }

    public void show() {
        visible = true;
    }

    public void hide() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    private void updateLane() {
        laneX = x + w * 0.20f;
        laneW = w * 0.60f;

        laneY = y + h * 0.15f;
        laneH = h * 0.70f;

        leftWinX = laneX + laneW * 0.15f - windowW / 2f;
        rightWinX = laneX + laneW * 0.85f - windowW / 2f;

        leftWinY = laneY - windowH * 0.25f;
        rightWinY = laneY - windowH * 0.25f;
    }

    public void update(float delta) {
        if (!visible) return;
        updateLane();
    }

    public void render(SpriteBatch batch) {
        if (!visible) return;

        updateLane();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(texture, x, y, w, h);
        batch.end();

        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        shape.setColor(0.6f, 0f, 0f, 1f);
        shape.rect(laneX, laneY, laneW, laneH);

        shape.setColor(0.3f, 0.3f, 0.3f, 1f);
        shape.rect(leftWinX, leftWinY, windowW, windowH);
        shape.rect(rightWinX, rightWinY, windowW, windowH);

        shape.setColor(1f, 1f, 0.8f, 0.25f);
        shape.triangle(
            leftWinX + windowW / 2f, leftWinY + windowH,
            leftWinX + windowW / 2f - 30, leftWinY + windowH + 60,
            leftWinX + windowW / 2f + 30, leftWinY + windowH + 60
        );

        shape.triangle(
            rightWinX + windowW / 2f, rightWinY + windowH,
            rightWinX + windowW / 2f - 30, rightWinY + windowH + 60,
            rightWinX + windowW / 2f + 30, rightWinY + windowH + 60
        );

        for (EnemyLogic e : enemyManager.getEnemies()) {
            float nx = (e.getX() - enemyManager.getLaneX()) / enemyManager.getLaneWidth();
            float ny = (e.getY() - enemyManager.getLaneY()) / enemyManager.getLaneHeight();

            float ex = laneX + nx * laneW;
            float ey = laneY + ny * laneH;

            shape.setColor(e.isRed() ? Color.RED : Color.WHITE);
            shape.circle(ex, ey, 6);
        }

        shape.end();
    }
}
