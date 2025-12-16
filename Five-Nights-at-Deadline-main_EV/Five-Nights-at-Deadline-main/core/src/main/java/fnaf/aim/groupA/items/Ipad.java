package fnaf.aim.groupA.items;

import com.badlogic.gdx.graphics.Texture;

public class Ipad extends DeskItem {
    public Ipad(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    @Override
    public void onClick() {
        System.out.println("iPad clicked! Quick progress but heavy battery drain.");
    }
}
