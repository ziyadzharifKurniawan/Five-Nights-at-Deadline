package fnaf.aim.groupA.ai;

import java.util.*;

public class DefenseState {
    private final Set<String> lockedWindows = new HashSet<>();

    // Check if Window is locked or not
    public boolean isWindowLocked(String windowId) {
        return lockedWindows.contains(windowId);
    }

    // Locks window
    public void lockWindow(String windowId) {
        lockedWindows.add(windowId);
    }

    // Unlocks window
    public void unlockWindow(String windowId) {
        lockedWindows.remove(windowId);
    }
}

