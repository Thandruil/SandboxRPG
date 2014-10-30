package com.sandboxrpg.world;

public class World {

    private Heightmap heightmap;
    private Tilemap tilemap;
    private int scale = 2;

    public World() {
        heightmap = new Heightmap("resources/heightmap256.bmp");
        tilemap = new Tilemap("resources/tilemap256.bmp");
    }

    public Heightmap getHeightmap() {
        return heightmap;
    }

    public Tilemap getTilemap() {
        return tilemap;
    }

    public int getScale() {
        return scale;
    }
}