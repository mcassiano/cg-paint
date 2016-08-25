package com.mcassiano.cgpaint;

public class Algorithms {

    public static void dda(double x1, double y1, double x2, double y2, Callbacks callbacks) {

        double dx = x2 - x1;
        double dy = y2 - y1;
        double x = x1;
        double y = y1;
        double steps;

        callbacks.drawPoint((int) x, (int) y);

        if (Math.abs(dx) > Math.abs(dy))
            steps = Math.abs(dx);
        else
            steps = Math.abs(dy);

        double xincr = dx/steps;
        double yincr = dy/steps;

        for (int i = 1; i <= steps; i++) {

            x += xincr;
            y += yincr;

            callbacks.drawPoint((int)Math.round(x), (int)Math.round(y));
        }

    }

    public static void bresehamLine(int x1, int y1, int x2, int y2, Callbacks callbacks) {

        int p, const1, const2;
        int xincr, yincr;

        int x = x1;
        int y = y1;

        callbacks.drawPoint(x1, y1);

        int rawDx = (x2 - x1);
        int rawDy = (y2 - y1);

        int dx = Math.abs(rawDx);
        int dy = Math.abs(rawDy);

        xincr = dx != 0 ? dx/rawDx : 0;
        yincr = dy != 0 ? dy/rawDy : 0;

        if (dx > dy) {
            p = 2 * dy - dx;
            const1 = 2 * dy;
            const2 = 2 * (dy - dx);

            for (int i = 0; i < dx; i++) {

                if (p < 0) p += const1;

                else {
                    p += const2;
                    y += yincr;
                }

                x += xincr;
                callbacks.drawPoint(x, y);

            }
        } else {
            p = 2 * dx - dy;

            const1 = 2 * dx;
            const2 = 2 * (dy - dx);

            for (int i = 0; i < dy; i++) {

                if (p < 0) p += const1;

                else {
                    p += const2;
                    x += xincr;
                }

                y += yincr;
                callbacks.drawPoint(x, y);
            }
        }

    }

    public static void bresehamCircle(int x1, int y1, int x2, int y2, Callbacks callbacks) {

        int radius = circleRadius(x1, y1, x2, y2);

        int x = 0;
        int y = radius;

        callbacks.drawSimmetric(x, y, x2, y2);

        int p = 3 - 2 * radius;

        while (x < y) {

            if (p < 0) p += 4 * x + 6;

            else {
                p += 4 * (x - y) + 10;
                y -= 1;
            }

            x += 1;

            callbacks.drawSimmetric(x, y, x2, y2);
        }


    }

    private static int circleRadius(int x1, int y1, int x2, int y2) {

        int dx = x2 - x1;
        int dy = y2 - y1;

        return (int) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    public interface Callbacks {
        void drawPoint(int x, int y);
        void drawSimmetric(int x1, int y1, int x2, int y2);
    }
}
