package fnaf.aim.groupA.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;

import java.util.Random;

public class LaptopOverlay {

    private Texture laptopTexture;
    private OrthographicCamera camera;
    private float x, y, width, height;
    private boolean visible;

    private BitmapFont font;
    private String typingText;
    private Random random;

    public LaptopOverlay(Texture laptopTexture, float width, float height) {
        this.laptopTexture = laptopTexture;
        this.width = width;
        this.height = height;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        x = (Gdx.graphics.getWidth() - width) / 2f;
        y = (Gdx.graphics.getHeight() - height) / 2f;

        visible = false;

        font = new BitmapFont();
        typingText = "";
        random = new Random();
    }

    public void show() {
        visible = true;
        typingText = "";
    }

    public void hide() {
        visible = false;
        typingText = "";
    }

    public boolean isVisible() {
        return visible;
    }

    public void update(float delta) {
        if (!visible) return;

        // Random typing effect
        if (random.nextFloat() < 0.1f) {
            char c = (char)(random.nextInt(26) + 'A');
            typingText += c;

            if (typingText.length() > 32) {
                typingText = typingText.substring(typingText.length() - 32);
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (!visible) return;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(laptopTexture, x, y - 160, width, height + 80);
        font.draw(batch, typingText, x + 130, y + height - 230);

        batch.end();
    }
}
