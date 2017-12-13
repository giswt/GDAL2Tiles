package org.zhjf.tiles;

import org.zhjf.tiles.geom.GeomEnvelop;
import org.zhjf.tiles.geom.PointDouble;
import org.zhjf.tiles.geom.PointInteger;

/**
 * Created by JerFer
 * Date: 2017/12/13.
 */
public class GlobalGeodetic {
    private int tileSize;
    private double resFact;

    public GlobalGeodetic(int tileSize) {
        this.tileSize = tileSize;
        //1 tile @ level 0
        resFact = 360.0 / tileSize;
    }

    private PointDouble lonlatToPixels(double lon, double lat, int zoom) {
        double res = resFact / Math.pow(2, zoom);
        return new PointDouble((180 + lon) / res, (90 + lat) / res);
    }

    private PointInteger pixelsToTile(double px, double py) {
        int tx = (int) (Math.ceil(px / (float) (tileSize)) - 1);
        int ty = (int) (Math.ceil(py / (float) (tileSize)) - 1);
        return new PointInteger(tx, ty);
    }

    private PointInteger lonlatToTile(double lon, double lat, int zoom) {
        PointDouble pointDouble = lonlatToPixels(lon, lat, zoom);
        return pixelsToTile(pointDouble.getX(), pointDouble.getY());
    }

    private double resolution(int zoom) {
        return resFact / Math.pow(2, zoom);
    }

    private int zoomForPixelSize(double pixelSize) {
        for (int i = 0; i < ContentValue.MAXZOOMLEVEL; i++) {
            if (pixelSize > resolution(i)) {
                if (i != 0) return i - 1;
                else return 0;
            }
        }
        return 0;
    }

    private GeomEnvelop tileBounds(int tx, int ty, int zoom) {
        double res = resFact / Math.pow(2, zoom);
        return new GeomEnvelop(tx * tileSize * res - 180,
                ty * tileSize * res - 90,
                (tx + 1) * tileSize * res - 180,
                (ty + 1) * tileSize * res - 90);
    }

    private GeomEnvelop tileLatLonBounds(int tx, int ty, int zoom) {
        GeomEnvelop b = tileBounds(tx, ty, zoom);
        return new GeomEnvelop(b.getMiny(), b.getMinx(), b.getMaxy(), b.getMaxx());
    }
}
