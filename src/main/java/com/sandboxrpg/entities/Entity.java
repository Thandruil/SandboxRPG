package com.sandboxrpg.entities;

import org.lwjgl.util.vector.Vector3f;

import java.util.UUID;

public abstract class Entity {

    public Vector3f position;
    public Vector3f rotation;

    public Entity() {
        this.position = new Vector3f(0,0,0);
        this.rotation = new Vector3f(0,0,0);
    }
}
