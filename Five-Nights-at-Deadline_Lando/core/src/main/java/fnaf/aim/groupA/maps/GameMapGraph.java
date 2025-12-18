package fnaf.aim.groupA.maps;

import java.util.*;

public class GameMapGraph {
    private final Map<String, MapNode> nodes = new HashMap<>();

    // Add nodes to the map
    public MapNode addNode(String id, LocationType type) {
        if (nodes.containsKey(id)) return nodes.get(id);
        MapNode n = new MapNode(id, type);
        nodes.put(id, n);
        return n;
    }

    // Connect node a and b
    public void connect(String a, String b, boolean bidirectional) {
        requireNode(a);
        requireNode(b);
        nodes.get(a).addNeighbor(b);
        if (bidirectional) nodes.get(b).addNeighbor(a);
    }

    public MapNode getNode(String id) {
        return nodes.get(id);
    }

    public Collection<MapNode> getAllNodes() {
        return nodes.values();
    }

    // Pathfinding
    public List<String> shortestPathNextStep(String start, Set<String> goals) {
        if (start == null || goals == null || goals.isEmpty()) return Collections.emptyList();
        if (goals.contains(start)) return Arrays.asList(start);

        Queue<String> q = new ArrayDeque<>();
        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();

        q.add(start);
        visited.add(start);

        String foundGoal = null;

        while (!q.isEmpty()) {
            String cur = q.poll();
            for (String nxt : nodes.get(cur).getNeighbors()) {
                if (visited.contains(nxt)) continue;
                visited.add(nxt);
                parent.put(nxt, cur);

                if (goals.contains(nxt)) {
                    foundGoal = nxt;
                    q.clear();
                    break;
                }
                q.add(nxt);
            }
        }

        if (foundGoal == null) return Collections.emptyList();

        List<String> path = new ArrayList<>();
        String cur = foundGoal;
        while (cur != null) {
            path.add(cur);
            cur = parent.get(cur);
        }
        Collections.reverse(path);
        path.add(0, start); // ensure start included if not already
        // If start duplicated, remove dup
        if (path.size() >= 2 && path.get(0).equals(path.get(1))) path.remove(0);

        return path;
    }

    private void requireNode(String id) {
        if (!nodes.containsKey(id)) {
            throw new IllegalStateException("Node not found: " + id);
        }
    }
}

