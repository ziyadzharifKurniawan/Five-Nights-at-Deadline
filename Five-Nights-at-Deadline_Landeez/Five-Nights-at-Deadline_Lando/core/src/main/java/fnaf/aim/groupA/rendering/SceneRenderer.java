package fnaf.aim.groupA.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import fnaf.aim.groupA.utils.Constants;

public class SceneRenderer {

    private final ShapeRenderer shape = new ShapeRenderer();

    public float mapX, mapY, mapW, mapH;

    private float leftWinX;
    private float leftWinY;
    private float rightWinX;
    private float rightWinY;

    public void draw(OrthographicCamera camera, int roomW, int roomH) {
        shape.setProjectionMatrix(camera.combined);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0.08f, 0.08f, 0.10f, 1f));
        shape.rect(0, 0, roomW, roomH);
        shape.end();

        float winY = roomH - Constants.WINDOW_MARGIN - Constants.WINDOW_SIZE_H;

        leftWinX = 200f;
        leftWinY = winY;

        rightWinX = roomW - 200f - Constants.WINDOW_SIZE_W;
        rightWinY = winY;

        drawWindow(leftWinX, leftWinY);
        drawWindow(rightWinX, rightWinY);

        float tableX = (roomW - Constants.TABLE_W) / 2f;
        float tableY = roomH * 0.60f;
        drawTable(tableX, tableY);
        drawMonitor(
            (roomW - Constants.MONITOR_W) / 2f,
            tableY + Constants.TABLE_H + 18f,
            tableX, tableY, roomW
        );

        mapX = (roomW - Constants.MAP_W) / 2f;
        mapY = tableY - Constants.MAP_H - 24f;
        mapW = Constants.MAP_W;
        mapH = Constants.MAP_H;

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.BLACK);
        shape.rect(mapX, mapY, mapW, mapH);
        shape.end();

        drawPowerIconHUD(camera);
    }


    private void drawWindow(float x, float y) {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0.15f, 0.18f, 0.25f, 1f));
        shape.rect(x, y, Constants.WINDOW_SIZE_W, Constants.WINDOW_SIZE_H);
        shape.end();

        float w = Constants.WINDOW_SIZE_W;
        float h = Constants.WINDOW_SIZE_H;
        float mx = x + w / 2f;
        float my = y + h / 2f;

        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(new Color(0.80f, 0.15f, 0.15f, 1f));
        shape.rect(x, y, w, h);
        shape.line(x, my, x + w, my);
        shape.line(mx, y, mx, y + h);
        shape.end();
    }

    private void drawTable(float x, float y) {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0.25f, 0.22f, 0.20f, 1f));
        shape.rect(x, y, Constants.TABLE_W, Constants.TABLE_H);
        shape.end();
    }

    private void drawMonitor(float monitorX, float monitorY, float tableX, float tableY, int roomW) {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0.10f, 0.10f, 0.12f, 1f));
        shape.rect(monitorX, monitorY, Constants.MONITOR_W, Constants.MONITOR_H);
        shape.setColor(new Color(0.06f, 0.12f, 0.18f, 1f));
        shape.rect(
            monitorX + 12f, monitorY + 12f,
            Constants.MONITOR_W - 24f, Constants.MONITOR_H - 24f
        );
        float baseX = (roomW - Constants.MONITOR_BASE_W) / 2f;
        float baseY = tableY + 4f;
        shape.setColor(new Color(0.12f, 0.12f, 0.14f, 1f));
        shape.rect(baseX, baseY, Constants.MONITOR_BASE_W, Constants.MONITOR_BASE_H);
        shape.end();
    }

    private void drawPowerIconHUD(OrthographicCamera camera) {
        float cx = camera.position.x + camera.viewportWidth / 2f
            - Constants.POWER_ICON_MARGIN - Constants.POWER_ICON_R;
        float cy = camera.position.y + camera.viewportHeight / 2f
            - Constants.POWER_ICON_MARGIN - Constants.POWER_ICON_R;

        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(new Color(0.95f, 0.95f, 0.95f, 1f));
        shape.circle(cx, cy, Constants.POWER_ICON_R);
        shape.end();

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0.95f, 0.95f, 0.95f, 1f));
        shape.rect(cx - 2f, cy + 6f, 4f, 16f);
        shape.end();
    }

    public void renderWindowFlash(SpriteBatch batch, boolean leftFlash, boolean rightFlash) {


        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Filled);

        if (leftFlash) {
            shape.setColor(1f, 1f, 1f, 0.85f);
            shape.rect(leftWinX, leftWinY, Constants.WINDOW_SIZE_W, Constants.WINDOW_SIZE_H);
        }

        if (rightFlash) {
            shape.setColor(1f, 1f, 1f, 0.85f);
            shape.rect(rightWinX, rightWinY, Constants.WINDOW_SIZE_W, Constants.WINDOW_SIZE_H);
        }

        shape.end();

    }

    public void dispose() {
        shape.dispose();
    }
}
