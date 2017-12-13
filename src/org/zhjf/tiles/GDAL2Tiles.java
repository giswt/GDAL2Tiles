package org.zhjf.tiles;

import org.zhjf.tiles.geom.GeomEnvelop;
import org.zhjf.tiles.geom.PointDouble;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by JerFer
 * Date: 2017/12/13.
 */
public class GDAL2Tiles {
    private boolean stopped = false;
    private FileInputStream input = null;
    private String output = null;

    private int tilesize = 256;
    private String tiledriver = "PNG";
    private String tileext = "png";
    private boolean scaledquery = true;

    private int querysize = 4 * tilesize;
    private boolean overviewquery = false;
    private String resampling = "";
    private int tminz, tmaxz;
    private String kml;
    private double ominx, ominy, omaxx, omaxy;
    private String profile;
    private GeomEnvelop swne;

    //-l -p raster -z 0-5 -w none <image> <tilesdir>
    public GDAL2Tiles(FileInputStream input, String output, String resampling, String profile, int tminz, int tmaxz) {
        this.input = input;
        this.output = output;
        this.profile = profile;

        this.resampling = resampling;
        this.resampling = "average";

        //采样方法
        if (resampling == "average") {
            resampling = "average";
        } else if (resampling == "antialias") {

        } else if (resampling == "near") {

        } else if (resampling == "bilinear") {

        } else if (resampling == "cubic") {

        } else if (resampling == "cubicspline") {

        } else if (resampling == "lanczos") {

        }
        this.tminz = tminz;
        this.tmaxz = tmaxz;

    }

    public void process() {
        // Opening and preprocessing of the input file
        open_input();

        // Generation of main metadata files and HTML viewers
        generate_metadata();

        // Generation of the lowest tiles
        generate_base_tiles();

        // Generation of the overview tiles (higher in the pyramid)
        generate_overview_tiles();
    }

    private void generate_overview_tiles() {

    }

    private void generate_base_tiles() {

    }

    private void generate_metadata() {
        if (!new File(output).exists()) {
            new File(output).mkdirs();
        }
        if (profile == "mercator") {
            GlobalMercator mercator = new GlobalMercator(tilesize);
            PointDouble southWest = mercator.metersToLatLon(ominx, ominy);
            PointDouble northEast = mercator.metersToLatLon(omaxx, omaxy);
            southWest = new PointDouble(max(-85.05112878, southWest.getX()), max(-180.0, southWest.getY()));
            northEast = new PointDouble(min(85.05112878, northEast.getX()), min(180.0, northEast.getY()));
            this.swne = new GeomEnvelop(southWest.getX(), southWest.getY(), northEast.getX(), northEast.getY());
        }

    }

    private double max(double x, double y) {
        return x > y ? x : y;
    }

    private double min(double x, double y) {
        return x < y ? x : y;
    }

    private void open_input() {

    }
}
