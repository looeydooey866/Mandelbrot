package com.example.julia;

public class JuliaSet {
    private Complex[][] set;
    private int iterationLimit = 100;
    private int[][] iterationsTaken;
    private double minX, maxX, minY, maxY;
    private double escapeThreshold;
    private int screenWidth, screenHeight;
    private Complex c;

    public JuliaSet(int screenWidth, int screenHeight, double minX, double maxX, double minY, double maxY, double escapeThreshold, Complex c, int iterations){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;

        this.escapeThreshold = escapeThreshold;

        this.c = c;

        //Since no IO is involved, I decided to go for this configuration (don't forget)
        set = new Complex[screenWidth][screenHeight];
        iterationsTaken = new int[screenWidth][screenHeight];
        this.iterationLimit = iterations;
        getPoints();
        simulate();
    }

    public Complex get(int x, int y){
        return set[x][y];
    }

    public int getIterationsTaken(int x, int y){
        return iterationsTaken[x][y];
    }

    private void getPoints(){
        double xGraticule = (maxX-minX) / screenWidth;
        double yGraticule = (maxY-minY) / screenHeight;
        for (int x=0;x<screenWidth;x++){
            for (int y=0;y<screenHeight;y++){
                set[x][y] = Complex.of(minX + x*xGraticule,minY + y*yGraticule);
            }
        }
    }

    private void simulate(){
        for (int x=0;x<screenWidth;x++){
            for (int y=0;y<screenHeight;y++){
                for (int i=1;i<=iterationLimit;i++){
                    Complex z = set[x][y];
                    z.juliaTransform(c);
                    iterationsTaken[x][y] = i;
                    if (z.getMagnitude() > escapeThreshold){
                        break;
                    }
                }
            }
        }
    }
}
