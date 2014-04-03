package com.sandboxrpg.entities;

import java.util.UUID;

public abstract class Entity {

    private static int nextEntityID;
    private int entityID;
    private UUID entityUniqueID;

    public float x;
    public float y;
    // public float z;

    public float dx;
    public float dy;
    // public float dz;

    public Entity() {
        this.entityID = nextEntityID++;
        this.entityUniqueID = UUID.randomUUID();
    }
}
