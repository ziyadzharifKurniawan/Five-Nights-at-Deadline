package fnaf.aim.groupA.items;

import com.badlogic.gdx.graphics.Texture;

public class PhoneItem extends DeskItem {
    public PhoneItem(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    @Override
    public void onClick() {
        System.out.println("Phone clicked! Distraction lowers focus and drains energy.");
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
