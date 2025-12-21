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
        manager.load("Enemy1.png", Texture.class);
        manager.load("Enemy2.png", Texture.class);
        manager.load("Enemy3.png", Texture.class);
        manager.load("Enemy4.png", Texture.class);
        manager.load("Enemy5.png", Texture.class);
        manager.load("battery_icon.png", Texture.class);
        manager.load("sanity_icon.png", Texture.class);
        manager.load("battery_icon.png", Texture.class);
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

    public Texture batteryIcon() {
        return manager.get("battery_icon.png", Texture.class);
    }

    public Texture sanityIcon() {
        return manager.get("sanity_icon.png", Texture.class);
    }

    public Texture labReportIcon() {
        return manager.get("battery_icon.png", Texture.class);
    }

    public Texture enemyTexture(int enemyId) {
        return switch (enemyId) {
            case 1 -> manager.get("Enemy1.png", Texture.class);
            case 2 -> manager.get("Enemy2.png", Texture.class);
            case 3 -> manager.get("Enemy3.png", Texture.class);
            case 4 -> manager.get("Enemy4.png", Texture.class);
            case 5 -> manager.get("Enemy5.png", Texture.class);
            default -> null;
        };
    }

    public void dispose() {
        manager.dispose();
    }
}
