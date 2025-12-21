package fnaf.aim.groupA.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PhoneOverlay {

    private Texture phoneTexture;
    private OrthographicCamera camera;
    private float x, y, width, height;
    private boolean visible;

    // RGB scrolling
    private float hue = 0f;
    private float hueSpeed = 180f;   // ✅ much faster color cycling

    // Flashing
    private float flashTimer = 0f;
    private float flashInterval = 0.03f; // ✅ VERY fast flashing (30ms)
    private Color flashColor = Color.WHITE;

    public PhoneOverlay(Texture phoneTexture, float width, float height) {
        // ✅ Make phone wider for realism
        this.width = width * 1.4f;   // 40% wider
        this.height = height;

        this.phoneTexture = phoneTexture;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        x = (Gdx.graphics.getWidth() - this.width) / 2f;
        y = (Gdx.graphics.getHeight() - this.height) / 2f;

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

    public void update(float delta) {
        if (!visible) return;

        // ✅ Fast RGB cycling
        hue += hueSpeed * delta;
        if (hue > 360f) hue -= 360f;

        // ✅ Rapid flashing
        flashTimer -= delta;
        if (flashTimer <= 0f) {
            flashTimer = flashInterval;

            flashColor = new Color(
                (float)Math.random(),
                (float)Math.random(),
                (float)Math.random(),
                0.45f
            );
        }
    }

    public void render(SpriteBatch batch) {
        if (!visible) return;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Base phone
        batch.draw(phoneTexture, x, y - 100, width+250, height + 200);

        // RGB overlay
        Color rgb = hsvToRgb(hue, 1f, 0.45f);
        batch.setColor(rgb);
        batch.draw(phoneTexture, x, y - 100, width+ 250, height + 200);

        // Flash overlay
        batch.setColor(flashColor);
        batch.draw(phoneTexture, x, y - 100, width+ 250, height + 200);

        batch.setColor(Color.WHITE);
        batch.end();
    }

    // HSV → RGB
    private Color hsvToRgb(float h, float s, float v) {
        float c = v * s;
        float x = c * (1 - Math.abs((h / 60f) % 2 - 1));
        float m = v - c;

        float r = 0, g = 0, b = 0;

        if (h < 60)      { r = c; g = x; b = 0; }
        else if (h < 120){ r = x; g = c; b = 0; }
        else if (h < 180){ r = 0; g = c; b = x; }
        else if (h < 240){ r = 0; g = x; b = c; }
        else if (h < 300){ r = x; g = 0; b = c; }
        else             { r = c; g = 0; b = x; }

        return new Color(r + m, g + m, b + m, 1f);
    }
}
