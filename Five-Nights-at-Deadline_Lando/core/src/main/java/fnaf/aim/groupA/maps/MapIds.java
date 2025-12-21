package fnaf.aim.groupA.maps;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class MapIds {
    private MapIds(){}

    // Rooms
    public static final String R_S676    = "ROOM_S676";
    public static final String R_CANTEEN = "ROOM_CANTEEN";
    public static final String R_GK302   = "ROOM_GK302";
    public static final String R_TOILET  = "ROOM_TOILET";

    // Hallways
    public static final String H_SCAN    = "HALLWAY_SCAN";
    public static final String H_CANGK   = "HALLWAY_CANGK";
    public static final String H_LEFT    = "HALLWAY_LEFT";
    public static final String H_RIGHT   = "HALLWAY_RIGHT";

    // Windows
    public static final String W_LEFT  = "WINDOW_LEFT";
    public static final String W_RIGHT = "WINDOW_RIGHT";


    // Enemy targetings
    public static final Set<String> LEFT_WINDOWS  = Collections.singleton(W_LEFT);
    public static final Set<String> RIGHT_WINDOWS = Collections.singleton(W_RIGHT);
    public static final Set<String> BOTH_WINDOWS;

    static {
        HashSet<String> s = new HashSet<>();
        s.add(W_LEFT);
        s.add(W_RIGHT);
        BOTH_WINDOWS = Collections.unmodifiableSet(s);
    }
}

