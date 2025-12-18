package fnaf.aim.groupA;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import fnaf.aim.groupA.assets.AssetLoader;
import fnaf.aim.groupA.input.GameScreenInput;


public class Main extends Game {
    private AssetLoader assets;

    @Override
    public void create() {
        assets = new AssetLoader();
        assets.load();
        assets.finish();
        Graphics.DisplayMode dm = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(dm);
        setScreen(new GameScreenInput(assets));

    }

    @Override
    public void dispose() {
        super.dispose();
        if (assets != null) assets.dispose();
    }
}
