package fnaf.aim.groupA.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {
    public final AssetManager manager = new AssetManager();

    public void load() {
        manager.load("bg.png", Texture.class);
        manager.load("phone.png", Texture.class);
        manager.load("laptop.png", Texture.class);
        manager.load("ipad.png", Texture.class);
    }

    public void finish() {
        manager.finishLoading();
    }

    public Texture bg() {
        return manager.get("bg.png", Texture.class);
    }

    public Texture phoneTexture() {
        return manager.get("phone.png", Texture.class);
    }

    public Texture laptopTexture() {
        return manager.get("laptop.png", Texture.class);
    }

    public Texture ipadTexture() {
        return manager.get("ipad.png", Texture.class);
    }

    public void dispose() {
        manager.dispose();
    }
}
