package fnaf.aim.groupA.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import fnaf.aim.groupA.logic.PlaySession;

public class JumpscareMinigameScreen implements Screen {

    private final Game game;
    private final PlaySession session;
    private final Screen returnScreen;

    private ShapeRenderer shape;
    private float timer = 4f;
    private int clicks = 0;

    public JumpscareMinigameScreen(Game game, PlaySession session, Screen returnScreen) {
        this.game = game;
        this.session = session;
        this.returnScreen = returnScreen;
    }

    @Override
    public void show() {
        shape = new ShapeRenderer();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                clicks++;
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        timer -= delta;

        if (clicks >= 15) {
            game.setScreen(returnScreen);
            dispose();
            return;
        }

        if (timer <= 0) {
            session.applyJumpscarePenalty();
            game.setScreen(returnScreen);
            dispose();
            return;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 5; i++) {
            shape.circle(
                (float) Math.random() * Gdx.graphics.getWidth(),
                (float) Math.random() * Gdx.graphics.getHeight(),
                10
            );
        }
        shape.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        shape.dispose();
    }
}
