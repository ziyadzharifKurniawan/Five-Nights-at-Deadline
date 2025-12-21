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

        // Rooms
        m.put(MapIds.R_S676,    new Vector2(0.310276f, 0.8082789f));
        m.put(MapIds.R_CANTEEN, new Vector2(0.6379812f, 0.85275966f));
        m.put(MapIds.R_GK302,   new Vector2(0.741467f, 0.46786496f));
        m.put(MapIds.R_TOILET,  new Vector2(0.29030505f, 0.27360204f));

        // Hallways
        m.put(MapIds.H_SCAN,    new Vector2(0.47004363f, 0.8100945f));
        m.put(MapIds.H_CANGK,   new Vector2(0.6706609f, 0.62763256f));
        m.put(MapIds.H_LEFT,    new Vector2(0.13326076f, 0.4288308f));
        m.put(MapIds.H_RIGHT,   new Vector2(0.46187368f, 0.39887437f));

        // Windows
        m.put(MapIds.W_LEFT,    new Vector2(0.12418305f, 0.21096587f));
        m.put(MapIds.W_RIGHT,   new Vector2(0.71332616f, 0.23729122f));

        PINS = Collections.unmodifiableMap(m);
    }

    public static Vector2 get(String nodeId) {
        return PINS.get(nodeId);
    }

    public static boolean has(String nodeId) {
        return PINS.containsKey(nodeId);
    }
}
