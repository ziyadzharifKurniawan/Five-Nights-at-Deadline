package fnaf.aim.groupA.hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

public class CountdownTimerHUD {
    private float duration;
    private float remaining;
    private float x, y;
    private BitmapFont font;

    public CountdownTimerHUD(float durationSeconds, float x, float y) {
        this.duration = durationSeconds;
        this.remaining = durationSeconds;
        this.x = x;
        this.y = y;
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2.5f);
    }

    public void reset() {
        remaining = duration;
    }

    public void update(float delta) {
        remaining = Math.max(0f, remaining - delta);
    }

    public void render(SpriteBatch batch) {
        int minutes = (int)(remaining / 60);
        int seconds = (int)(remaining % 60);
        String text = String.format("%02d:%02d", minutes, seconds);
        font.draw(batch, text, x, y);
    }

    public boolean isFinished() {
        return remaining <= 0f;
    }
}
