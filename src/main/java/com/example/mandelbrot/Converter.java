package com.example.mandelbrot;

public class Converter {
    public static Complex real2argand(int x, int y, int screenWidth, int screenHeight, double xmin, double xmax, double ymin, double ymax){
        //must get the center of the pixels in order to get the complex corresponding to it
        double xrange = xmax - xmin;
        double yrange = ymax - ymin;
        double xpixel = xrange / screenWidth;
        double ypixel = yrange / screenHeight;
        double xshift = xpixel / 2;
        double yshift = ypixel / 2;
        double xres = xmin + x * xpixel + xshift;
        double yres = ymax - (y * ypixel + yshift);
        return Complex.of(xres, yres);
    }

    public static Coord argand2real(Complex c, int screenWidth, int screenHeight, double xmin, double xmax, double ymin, double ymax){
        //i guess if it's out of bounds we can return NaN for both c values
        //so we can just get the percentage that the c coordinate is from the top left, and then multiply by width and height and floor?
        double xrange = xmax - xmin;
        double yrange = ymax - ymin;
        double xcoverage = (c.getReal() - xmin) / xrange;
        double ycoverage = (ymax - c.getImaginary()) / yrange;
        //ya ya fine humans read from top left to bototm right asipudfvapisduvfj basidjkufvb sadipjufvsadf
        double xform = xcoverage * screenWidth;
        double yform = ycoverage * screenHeight;
        return Coord.of((int)Math.floor(xform),(int)Math.floor(yform));
    }
}
