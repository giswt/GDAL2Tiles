package org.zhjf.tiles.geom;

/**
 * Created by JerFer
 * Date: 2017/12/13.
 */
public class PointDouble {
    private double x;
    private double y;

    public PointDouble(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public PointDouble() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
