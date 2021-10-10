package common.data;

import java.io.Serializable;

public class Location implements Serializable {
    private Double X;
    private float Y;
    private long Z;
    private String name;
    Location(){}
    public Location(Double X, float Y, long Z, String name){
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.name = name;
    }
    public Double getX() {
        return X;
    }

    public float getY() {
        return Y;
    }

    public long getZ() {
        return Z;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{" +
                "X=" + X +
                ", Y=" + Y +
                ", Z=" + Z +
                ", название: " + name +
                '}';
    }
}
