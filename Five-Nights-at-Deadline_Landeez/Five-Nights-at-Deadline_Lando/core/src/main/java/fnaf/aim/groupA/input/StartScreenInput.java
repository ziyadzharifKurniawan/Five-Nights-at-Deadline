package fnaf.aim.groupA.input;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import fnaf.aim.groupA.assets.AssetLoader;

public class StartScreenInput implements Screen {
    private final Game game;
    private Stage stage;
    private Skin skin;

    public StartScreenInput(Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json")); // default LibGDX skin
        Gdx.input.setInputProcessor(stage);

        showStartDialog();
    }

    private void showStartDialog() {
        Dialog dialog = new Dialog("Welcome", skin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    // ✅ User clicked YES → start the game
                    AssetLoader assets = new AssetLoader();
                    assets.load();
                    assets.finish();
                    game.setScreen(new GameScreenInput(assets));
                } else {
                    // ❌ User clicked NO → exit
                    Gdx.app.exit();
                }
            }
        };

        dialog.text("Are you ready to play?");
        dialog.button("Yes", true);
        dialog.button("No", false);
        dialog.show(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
