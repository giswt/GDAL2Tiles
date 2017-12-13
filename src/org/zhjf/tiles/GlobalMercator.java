package org.zhjf.tiles;

import org.zhjf.tiles.geom.GeomEnvelop;
import org.zhjf.tiles.geom.PointDouble;
import org.zhjf.tiles.geom.PointInteger;

/**
 * Created by JerFer
 * Date: 2017/12/13.
 */
public class GlobalMercator {
    private int tileSize = 256;
    private double initialResolution = 256;
    private double originShift = 256;

    public GlobalMercator(int tileSize) {
        this.tileSize = tileSize;
        //156543.03392804062 for tileSize 256 pixels
        this.initialResolution = 2 * Math.PI * 6378137 / tileSize;
        //20037508.342789244
        this.originShift = 2 * Math.PI * 6378137 / 2.0;
    }

    private PointDouble latLonToMeters(double lat, double lon) {
        double mx = lon * originShift / 180.0;
        double my = Math.log(Math.tan((90 + lat) * Math.PI / 360.0)) / (Math.PI / 180.0);
        my = my * originShift / 180.0;
        return new PointDouble(mx, my);
    }

    public PointDouble metersToLatLon(double mx, double my) {
        double lon = (mx / originShift) * 180.0;
        double lat = (my / originShift) * 180.0;

        lat = 180 / Math.PI * (2 * Math.atan(Math.exp(lat * Math.PI / 180.0)) - Math.PI / 2.0);
        return new PointDouble(lat, lon);
    }

    private PointDouble pixelsToMeters(int px, int py, int zoom) {
        double res = resolution(zoom);
        double mx = px * res - originShift;
        double my = py * res - originShift;
        return new PointDouble(mx, my);
    }

    private PointDouble metersToPixels(double mx, double my, int zoom) {
        double res = resolution(zoom);
        double px = (mx + originShift) / res;
        double py = (my + originShift) / res;
        return new PointDouble(px, py);
    }

    private PointInteger pixelsToTile(double px, double py) {
        int tx = (int) (Math.ceil(px / (float) (tileSize)) - 1);
        int ty = (int) (Math.ceil(py / (float) (tileSize)) - 1);
        return new PointInteger(tx, ty);
    }

    private PointDouble pixelsToRaster(double px, double py, int zoom) {
        double mapSize = tileSize << zoom;
        return new PointDouble(px, mapSize - py);
    }

    private PointInteger metersToTile(double mx, double my, int zoom) {
        PointDouble pointDouble = metersToPixels(mx, my, zoom);
        return pixelsToTile(pointDouble.getX(), pointDouble.getY());
    }

    private GeomEnvelop tileBounds(int tx, int ty, int zoom) {
        PointDouble minPoint = pixelsToMeters(tx * tileSize, ty * tileSize, zoom);
        PointDouble maxPoint = pixelsToMeters((tx + 1) * tileSize, (ty + 1) * tileSize, zoom);

        return new GeomEnvelop(minPoint.getX(), minPoint.getY(), maxPoint.getX(), maxPoint.getY());
    }

    private GeomEnvelop tileLatLonBounds(int tx, int ty, int zoom) {
        GeomEnvelop bounds = tileBounds(tx, ty, zoom);
        PointDouble minLatlon = metersToLatLon(bounds.getMinx(), bounds.getMiny());
        PointDouble maxLatlon = metersToLatLon(bounds.getMaxx(), bounds.getMaxy());
        return new GeomEnvelop(minLatlon.getX(), minLatlon.getY(), maxLatlon.getX(), maxLatlon.getY());
    }

    private double resolution(int zoom) {
        return initialResolution / (Math.pow(2, zoom));
    }

    private int zoomForPixelSize(int pixelSize) {
        for (int i = 0; i < ContentValue.MAXZOOMLEVEL; i++) {
            if (pixelSize > resolution(i)) {
                if (i != 0) {
                    return i - 1;
                } else return 0;
            }
        }
        return 0;
    }

    private PointInteger googleTile(int tx, int ty, int zoom) {
        return new PointInteger(tx, (int) (Math.pow(2, zoom) - 1 - ty));
    }

//    private String quadTree(int tx, int ty, int zoom) {
//        String quadKey = "";
//        ty = (int) (Math.pow(2, zoom) - 1 - ty);
//        for (int i = zoom; i > 0; i--) {
//            int digit = 0;
//            double mask = 1 << (i - 1);
//            if (tx & mask != 0) {
//                digit += 1;
//            }
//        }
//    }
}
