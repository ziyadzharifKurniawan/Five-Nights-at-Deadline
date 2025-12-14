package fnaf.aim.groupA.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {
    public final AssetManager manager = new AssetManager();

    public void load() {
        manager.load("bg.png", Texture.class);
    }

    public void finish() {
        manager.finishLoading();
    }

    public Texture bg() {
        return manager.get("bg.png", Texture.class);
    }

    public void dispose() {
        manager.dispose();
    }
}
