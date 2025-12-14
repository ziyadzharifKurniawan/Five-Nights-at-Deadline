package fnaf.aim.groupA;

import com.badlogic.gdx.Game;
import fnaf.aim.groupA.assets.AssetLoader;
import fnaf.aim.groupA.input.GameScreen;

public class Main extends Game {

    private AssetLoader assets;

    @Override
    public void create() {
        assets = new AssetLoader();
        assets.load();
        assets.finish();
        setScreen(new GameScreen(assets));
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}
