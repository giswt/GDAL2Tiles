package org.zhjf.tiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by JerFer
 * Date: 2017/12/13.
 */
public class Zoomify {
    private int tilesize;
    private String tileFormat = "png";
    private List<double[]> tierSizeInTiles;
    private List<int[]> tierImageSize;
    private int numberOfTiers;
    private List<Double> tileCountUpToTier;

    public Zoomify(int width, int height, int tilesize, String tileFormat) {
        this.tilesize = tilesize;
        this.tileFormat = tileFormat;
        int[] imagesize = new int[]{width, height};
        double[] tiles = new double[]{Math.ceil(width / tilesize), Math.ceil(height / tilesize)};

        tierSizeInTiles = new LinkedList<>();
        tierSizeInTiles.add(tiles);

        tierImageSize = new LinkedList<>();
        tierImageSize.add(imagesize);

        while (imagesize[0] > tilesize || imagesize[1] > tilesize) {
            imagesize = new int[]{(int) Math.floor(imagesize[0] / 2), (int) Math.floor(imagesize[1] / 2)};
            tiles = new double[]{Math.ceil(imagesize[0] / tilesize), Math.ceil(imagesize[1] / tilesize)};

            tierSizeInTiles.add(tiles);
            tierImageSize.add(imagesize);
        }

//        tierSizeInTiles.reverse();
        Collections.reverse(tierSizeInTiles);
//        tierImageSize.reverse();
        Collections.reverse(tierImageSize);

//        numberOfTiers = len(tierSizeInTiles);
        numberOfTiers = tierSizeInTiles.size();

        tileCountUpToTier = new LinkedList<>();
        tileCountUpToTier.add(0.0);

        for (int i = 1; i < numberOfTiers + 1; i++) {
            tileCountUpToTier.add(tierSizeInTiles.get(i - 1)[0] * tierSizeInTiles.get(i - 1)[1] + tileCountUpToTier.get(i - 1));
        }
    }

    private String tileFilename(int x, int y, int z) {
        double tileIndex = x + y * tierSizeInTiles.get(z)[0] + tileCountUpToTier.get(z);
        return String.format("%s-%s-%s.%s", z, x, y, tileFormat);
    }
}
