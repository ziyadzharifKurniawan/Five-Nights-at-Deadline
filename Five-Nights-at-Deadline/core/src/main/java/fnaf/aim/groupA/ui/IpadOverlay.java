package fnaf.aim.groupA.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import fnaf.aim.groupA.ai.Enemy;
import fnaf.aim.groupA.ai.EnemyState;
import fnaf.aim.groupA.logic.EnemyLogic;
import fnaf.aim.groupA.logic.EnemyManager;

public class IpadOverlay {

    private final Texture frameTexture;
    private Texture mapTexture; // tablet_map.png

    private final OrthographicCamera camera;
    private final ShapeRenderer shape;

    private final float w, h;
    private boolean visible;

    // Optional: if you still want it around later
    @SuppressWarnings("unused")
    private final EnemyManager enemyManager;

    // AI enemies
    private Array<Enemy> aiEnemies;

    // Screen Coordinates
    private final Rectangle frameRect = new Rectangle();
    private final Rectangle screenRect = new Rectangle();
    private final Rectangle mapRect = new Rectangle();

    private float t = 0f;

    public IpadOverlay(Texture frameTexture, float w, float h, EnemyManager enemyManager) {
        this.frameTexture = frameTexture;
        this.w = w;
        this.h = h;
        this.enemyManager = enemyManager;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shape = new ShapeRenderer();
        visible = false;

        loadMapTextureIfExists();
        updateLayout();
    }

    private void loadMapTextureIfExists() {
        if (Gdx.files.internal("tablet_map.png").exists()) {
            mapTexture = new Texture(Gdx.files.internal("tablet_map.png"));
            mapTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            mapTexture = null; // won’t crash, just shows blank screen area
            Gdx.app.log("IpadOverlay", "tablet_map.png not found in assets");
        }
    }

    public void setAiEnemies(Array<Enemy> aiEnemies) {
        this.aiEnemies = aiEnemies;
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

    public void update(float delta) {
        if (!visible) return;
        t += delta;
        updateLayout();
    }

    private void updateLayout() {
        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();

        camera.setToOrtho(false, sw, sh);
        camera.update();

        frameRect.set((sw - w) / 2f, (sh - h) / 2f, w, h);

        // Screen Area
        float insetL = w * 0.13f;
        float insetR = w * 0.13f;
        float insetB = h * 0.18f;
        float insetT = h * 0.14f;

        screenRect.set(
            frameRect.x + insetL,
            frameRect.y + insetB,
            frameRect.width - insetL - insetR,
            frameRect.height - insetT - insetB
        );

        computeMapRect();
    }

    private void computeMapRect() {
        if (mapTexture == null) {
            mapRect.set(screenRect);
            return;
        }

        float tw = mapTexture.getWidth();
        float th = mapTexture.getHeight();

        float scale = Math.max(screenRect.width / tw, screenRect.height / th);

        float dw = tw * scale;
        float dh = th * scale;

        float dx = screenRect.x + (screenRect.width - dw) / 2f;
        float dy = screenRect.y + (screenRect.height - dh) / 2f;

        mapRect.set(dx, dy, dw, dh);
    }

    public void render(SpriteBatch batch) {
        if (!visible) return;

        updateLayout();

        // Draw the ipad frame + map
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(frameTexture, frameRect.x, frameRect.y, frameRect.width, frameRect.height);

        if (mapTexture != null) {
            batch.draw(mapTexture, mapRect.x, mapRect.y, mapRect.width, mapRect.height);
        }
        batch.end();

        // Enemy blips
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.MAGENTA);
        shape.circle(mapRect.x + mapRect.width / 2f, mapRect.y + mapRect.height / 2f, 10f);

        if (aiEnemies != null && aiEnemies.size > 0) {
            for (int i = 0; i < aiEnemies.size; i++) {
                Enemy e = aiEnemies.get(i);

                Vector2 pin = TabletMapPins.get(e.getCurrentNode());
                if (pin == null) continue;

                float ex = mapRect.x + pin.x * mapRect.width;
                float ey = mapRect.y + pin.y * mapRect.height;

                boolean danger = (e.getState() == EnemyState.AT_WINDOW || e.getState() == EnemyState.JUMPSCARE);
                float pulse = 1.5f + MathUtils.sin(t * 10f) * 1.2f;
                float r = danger ? (7f + pulse) : 6f;

                // Color when on window
                shape.setColor(danger ? Color.RED : new Color(0.6f, 0.95f, 1f, 1f));
                shape.circle(ex, ey, r);

                // Outline
                shape.setColor(0f, 0f, 0f, 0.35f);
                shape.circle(ex, ey, r + 2f);
            }
            if (enemyManager != null) {
                Color normal = new Color(0.6f, 0.95f, 1f, 1f);
                float pulse = 1.5f + MathUtils.sin(t * 10f) * 1.2f;

                for (EnemyLogic e : enemyManager.getEnemies()) {
                    float nx = (e.getX() - enemyManager.getLaneX()) / enemyManager.getLaneWidth();
                    float ny = (e.getY() - enemyManager.getLaneY()) / enemyManager.getLaneHeight();

                    nx = MathUtils.clamp(nx, 0f, 1f);
                    ny = MathUtils.clamp(ny, 0f, 1f);

                    float ex = mapRect.x + nx * mapRect.width;
                    float ey = mapRect.y + ny * mapRect.height;

                    float r = e.isRed() ? (7f + pulse) : 6f;

                    shape.setColor(e.isRed() ? Color.RED : normal);
                    shape.circle(ex, ey, r);

                    shape.setColor(0f, 0f, 0f, 0.35f);
                    shape.circle(ex, ey, r + 2f);
                }
            }
        }
        shape.end();
    }

    public void dispose () {
        shape.dispose();
        if (mapTexture != null) mapTexture.dispose();
    }

    public float getWidth () {
        return w;
    }

    public float getHeight () {
        return h;
    }

}

