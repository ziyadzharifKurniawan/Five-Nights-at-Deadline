package fnaf.aim.groupA.items;

import com.badlogic.gdx.graphics.Texture;

public class Laptop extends DeskItem {
    public Laptop(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    @Override
    public void onClick() {
        System.out.println("Laptop clicked! Work progresses but drains energy.");
    }
}
