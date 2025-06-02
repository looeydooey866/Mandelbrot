package com.example.mandelbrot;

import java.util.stream.IntStream;

public class MandelbrotSet {
    private final Complex[][] cset, zset;
    private int iterationLimit = 100;
    private final int[][] iterationsTaken;
    private double minX, maxX, minY, maxY;
    private final double escapeThreshold;
    private final int screenWidth, screenHeight;

    public MandelbrotSet(int screenWidth, int screenHeight, double minX, double maxX, double minY, double maxY, double escapeThreshold,int iterations){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;

        this.escapeThreshold = escapeThreshold;

        //adsasdpufvbasdfuvbasdf fine i'll just do it the old fashioned way hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
        cset = new Complex[screenHeight][screenWidth];
        zset = new Complex[screenHeight][screenWidth];
        iterationsTaken = new int[screenHeight][screenWidth];
        this.iterationLimit = iterations;
        getPoints();
        simulate();
    }

    public double[] getBounds(){
        return new double[]{minX, maxX, minY, maxY};
    }

    public void zoom(Complex p, double percent){
        double factor = 1.0 - percent;
        //so 0.9 percent per frame means that the new screen's length is 0.9x the previous one.
        double xrange = maxX - minX;
        double tlxd = p.getReal() - minX;
        minX += tlxd * factor;
        maxX = minX + xrange * percent;
        double yrange = maxY - minY;
        double tlyd = maxY - p.getImaginary();
        maxY -= tlyd * factor;
        minY = maxY - yrange * percent;
        getPoints();
        simulate();
    }

    public int getIterationsTaken(int y, int x){
        return iterationsTaken[y][x];
    }

    public Complex getFinalZ(int y, int x){
        return zset[y][x];
    }

    private void getPoints(){
        for (int x=0;x<screenWidth;x++){
            for (int y=0;y<screenHeight;y++){
                zset[y][x] = Complex.of(0,0);
                cset[y][x] = Converter.real2argand(x, y, screenWidth, screenHeight, minX, maxX, minY, maxY);
            }
        }
    }

    private void simulate(){
        IntStream.range(0, screenHeight).parallel().forEach(y -> {
            for (int x = 0; x < screenWidth; x++) {
                Complex c = cset[y][x];
                double zx = 0, zy = 0;
                double zx2 = 0, zy2 = 0;
                int i = 0;
                while (zx2+zy2 <= escapeThreshold && i < iterationLimit){
                    zy=2*zx*zy+c.getImaginary();
                    zx = zx2-zy2+c.getReal();
                    zx2=zx*zx;
                    zy2=zy*zy;
                    i++;
                }
                zset[y][x] = Complex.of(zx,zy);
                iterationsTaken[y][x] = i;
            }
        });
    }
}
