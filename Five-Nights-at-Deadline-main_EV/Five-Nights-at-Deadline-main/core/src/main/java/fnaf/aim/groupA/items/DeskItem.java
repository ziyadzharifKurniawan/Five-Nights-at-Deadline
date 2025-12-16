package fnaf.aim.groupA.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public abstract class DeskItem {
    protected TextureRegion region;
    protected float x, y, width, height;
    protected float rotation;
    public DeskItem(Texture texture, float x, float y, float width, float height) {
        this.region = new TextureRegion(texture);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = MathUtils.random(-45f, 45f);
    }
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch) {
        batch.draw(region,
            x, y,
            width / 2f, height / 2f,
            width, height,
            1f, 1f,
            rotation);
    }
    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public abstract void onClick();
}
