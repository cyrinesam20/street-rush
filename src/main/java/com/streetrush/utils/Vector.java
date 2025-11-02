package com.streetrush.utils;

public class Vector {
    public float x, y, z;

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector() {
        this(0, 0, 0);
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector copy() {
        return new Vector(x, y, z);
    }

    public void add(Vector v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
    }

    public float distance(Vector other) {
        float dx = x - other.x;
        float dy = y - other.y;
        float dz = z - other.z;
        return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
    }
}