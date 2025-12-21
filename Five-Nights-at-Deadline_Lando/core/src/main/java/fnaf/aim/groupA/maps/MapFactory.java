package fnaf.aim.groupA.maps;

public final class MapFactory {
    private MapFactory(){}

    public static GameMapGraph buildDefaultMap() {
        GameMapGraph g = new GameMapGraph();

        // Room Nodes
        g.addNode(MapIds.R_S676, LocationType.ROOM);
        g.addNode(MapIds.R_CANTEEN, LocationType.ROOM);
        g.addNode(MapIds.R_GK302, LocationType.ROOM);
        g.addNode(MapIds.R_TOILET, LocationType.ROOM);

        // Hallway Nodes
        g.addNode(MapIds.H_SCAN, LocationType.HALLWAY);
        g.addNode(MapIds.H_CANGK, LocationType.HALLWAY);
        g.addNode(MapIds.H_LEFT, LocationType.HALLWAY);
        g.addNode(MapIds.H_RIGHT, LocationType.HALLWAY);

        // Window Nodes
        g.addNode(MapIds.W_LEFT, LocationType.WINDOW);
        g.addNode(MapIds.W_RIGHT, LocationType.WINDOW);

        //Movement Rules
        // S.676 -> S676Canteen Hallways
        g.connect(MapIds.R_S676, MapIds.H_SCAN, true);

        // Canteen -> S676Canteen Hallways
        g.connect(MapIds.R_CANTEEN, MapIds.H_SCAN, true);

        // GK302 -> CanteenGK302 Hallways
        g.connect(MapIds.R_GK302, MapIds.H_CANGK, true);

        // Toilet -> Left or Right Hallways
        g.connect(MapIds.R_TOILET, MapIds.H_LEFT, true);
        g.connect(MapIds.R_TOILET, MapIds.H_RIGHT, true);

        // S676Canteen Hallways -> CanteenGK302, Left, Right Hallways
        g.connect(MapIds.H_SCAN,  MapIds.H_CANGK, false);
        g.connect(MapIds.H_SCAN,  MapIds.H_LEFT,  false);
        g.connect(MapIds.H_SCAN,  MapIds.H_RIGHT, false);

        // CanteenGK302  -> S676Canteen, Left, Right Hallways
        g.connect(MapIds.H_CANGK, MapIds.H_SCAN, false);
        g.connect(MapIds.H_CANGK, MapIds.H_LEFT,  false);
        g.connect(MapIds.H_CANGK, MapIds.H_RIGHT, false);

        // Left Hallways -> left windows, and toilet
        g.connect(MapIds.H_LEFT, MapIds.W_LEFT, true);
        g.connect(MapIds.H_LEFT, MapIds.R_TOILET, true);

        // Right Hallways -> right windows, and toilet
        g.connect(MapIds.H_RIGHT, MapIds.W_RIGHT, true);
        g.connect(MapIds.H_RIGHT, MapIds.R_TOILET, true);

        return g;
    }
}

