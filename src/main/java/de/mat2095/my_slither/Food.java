package de.mat2095.my_slither;

import java.awt.Color;
import java.util.Random;


class Food {

    private static final Color FOOD_COLORS[] = new Color[]{new Color(0xCC7832), new Color(0xAA0012), new Color(0x106A0)};

    final int x, y;
    private final double size;
    private final double rsp;
    private final long spawnTime;
    private Color col;

    Food(int x, int y, double size, boolean fastSpawn) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.rsp = fastSpawn ? 4 : 1;
        spawnTime = System.currentTimeMillis();
        this.col = FOOD_COLORS[new Random().nextInt(FOOD_COLORS.length)];

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
    Color getCol() { return col; }
}
