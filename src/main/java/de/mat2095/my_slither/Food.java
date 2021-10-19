package de.mat2095.my_slither;

import java.awt.Color;
import java.util.Random;


class Food {


    final int x, y;
    private final double size;
    private final double rsp;
    private final long spawnTime;

    private final Color colors[] = {new Color(0xFF0000), new Color(0xFFFF00), new Color(0x00FF00), new Color(0x0000FF)};
    private Color color;

    Food(int x, int y, double size, boolean fastSpawn) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.rsp = fastSpawn ? 4 : 1;
        spawnTime = System.currentTimeMillis();

        color = colors[new Random().nextInt(colors.length)];

    }

    double getSize() {
        return size;
    }

    double getRadius() {
        double fillRate = rsp * (System.currentTimeMillis() - spawnTime) / 1200;
        if (fillRate >= 1) {
            return size;
        } else {
            return (1 - Math.cos(Math.PI * fillRate)) / 2 * size;
        }
    }

    /**
     * returns the color of the food.
     * @return //the color of the food.
     */
    public Color getColor(){ return color;}
}
