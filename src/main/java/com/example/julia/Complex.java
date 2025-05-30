package com.example.julia;

public class Complex {
    private double re, im;

    public double getReal(){
        return re;
    }

    public double getImaginary(){
        return im;
    }

    private Complex(double re, double im){
        this.re = re;
        this.im = im;
    }

    public static Complex of(double re, double im){
        return new Complex(re, im);
    }

    public static Complex ofPolar(double mag, double theta){
        return new Complex(mag * Math.cos(theta), mag * Math.sin(theta));
    }

    public void square(){
        // (x + yi) * (x + yi)
        // x^2 - y^2 + xyi
        double newRe = re*re - im*im;
        double newIm = 2*re*im;
        this.re = newRe;
        this.im = newIm;
    }

    public void add(Complex other){
        this.re += other.re;
        this.im += other.im;
    }

    public void juliaTransform(Complex c){
        square(); add(c);
    }

    @Override
    public String toString(){
        return String.format("[%.2f, %.2f]", re, im);
    }

    public double getMagnitude(){
        return Math.sqrt(re*re+im*im);
    }

    public static void main(String[] args){
        Complex c = Complex.of(3,4);
        System.out.println(c.getMagnitude());
    }
}
