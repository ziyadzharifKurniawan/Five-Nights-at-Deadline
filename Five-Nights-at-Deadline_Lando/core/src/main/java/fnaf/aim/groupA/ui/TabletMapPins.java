package fnaf.aim.groupA.ui;

import com.badlogic.gdx.math.Vector2;
import fnaf.aim.groupA.maps.MapIds;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class TabletMapPins {
    private TabletMapPins(){}

    private static final Map<String, Vector2> PINS;
    static {
        Map<String, Vector2> m = new HashMap<>();

        // These were derived from your sketch image you posted.
        // If you want to tweak them later: open iPad and click — it prints coords in console (see IpadOverlay.handleClick).

        // Rooms
        m.put(MapIds.R_S676,    new Vector2(0.2389f, 0.7033f));
        m.put(MapIds.R_CANTEEN, new Vector2(0.7385f, 0.8122f));
        m.put(MapIds.R_GK302,   new Vector2(0.8257f, 0.3173f));
        m.put(MapIds.R_TOILET,  new Vector2(0.3888f, 0.2650f));

        // Hallways
        m.put(MapIds.H_SCAN,    new Vector2(0.5352f, 0.7913f)); // S6Can
        m.put(MapIds.H_CANGK,   new Vector2(0.8035f, 0.6182f)); // CanGK
        m.put(MapIds.H_LEFT,    new Vector2(0.1916f, 0.4551f));
        m.put(MapIds.H_RIGHT,   new Vector2(0.5383f, 0.3511f));

        // Windows
        m.put(MapIds.W_LEFT,    new Vector2(0.1700f, 0.1791f));
        m.put(MapIds.W_RIGHT,   new Vector2(0.7957f, 0.1790f));

        PINS = Collections.unmodifiableMap(m);
    }

    public static Vector2 get(String nodeId) {
        return PINS.get(nodeId);
    }

    public static boolean has(String nodeId) {
        return PINS.containsKey(nodeId);
    }
}
