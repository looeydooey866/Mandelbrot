package com.example.mandelbrot;

public class Coord {
    private int x,y;
    private Coord(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static Coord of(int x, int y){
        return new Coord(x,y);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
}
