package org.zhjf.tiles.geom;

/**
 * Created by JerFer
 * Date: 2017/12/13.
 */
public class GeomEnvelop {
    private double minx;
    private double miny;
    private double maxx;
    private double maxy;

    public GeomEnvelop() {
    }

    public GeomEnvelop(double minx, double miny, double maxx, double maxy) {
        this.minx = minx;
        this.miny = miny;
        this.maxx = maxx;
        this.maxy = maxy;
    }

    public double getMinx() {
        return minx;
    }

    public void setMinx(double minx) {
        this.minx = minx;
    }

    public double getMiny() {
        return miny;
    }

    public void setMiny(double miny) {
        this.miny = miny;
    }

    public double getMaxx() {
        return maxx;
    }

    public void setMaxx(double maxx) {
        this.maxx = maxx;
    }

    public double getMaxy() {
        return maxy;
    }

    public void setMaxy(double maxy) {
        this.maxy = maxy;
    }
}
