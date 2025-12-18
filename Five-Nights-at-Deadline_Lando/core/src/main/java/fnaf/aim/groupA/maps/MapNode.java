package fnaf.aim.groupA.maps;

import java.util.*;

public class MapNode {
    private final String id;
    private final LocationType type;
    private final Set<String> neighbors = new HashSet<>();

    public MapNode(String id, LocationType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() { return id; }
    public LocationType getType() { return type; }

    public Set<String> getNeighbors() {
        return Collections.unmodifiableSet(neighbors);
    }

    void addNeighbor(String otherId) {
        neighbors.add(otherId);
    }

    @Override
    public String toString() {
        return "MapNode{" + id + ", " + type + ", neighbors=" + neighbors + "}";
    }
}

