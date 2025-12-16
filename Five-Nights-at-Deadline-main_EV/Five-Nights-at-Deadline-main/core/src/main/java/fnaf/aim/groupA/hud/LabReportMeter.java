package fnaf.aim.groupA.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class LabReportMeter extends Meter {

    private BitmapFont font;

    public LabReportMeter(float x, float y, float width, float height) {
        super("LabReport", 100, x, y, width, height);
        this.value = 0;
        font = new BitmapFont();
    }

    @Override
    public void render(ShapeRenderer shape) {
        shape.setColor(Color.GREEN);
        float filledWidth = width * ((float) value / maxValue);
        shape.rect(x, y, filledWidth, height);
        shape.setColor(Color.WHITE);
        shape.end();
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.rect(x, y, width, height);
        shape.end();
        shape.begin(ShapeRenderer.ShapeType.Filled);

    }
    public void renderIcon(SpriteBatch batch) {
        font.setColor(Color.GREEN);
        font.draw(batch, "📑", x - 25, y + height - 2);
    }
}
