package com.example.mandelbrot;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class MandelbrotController {
    @FXML
    private Canvas canvas;
    private int h,w;
    private int iterations = 10;
    GraphicsContext gc;
    PixelWriter pw;

    private MandelbrotSet mset;

    public Image pixelTest(int width, int height) {
        WritableImage img = new WritableImage(width, height);
        PixelWriter pw = img.getPixelWriter();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pw.setColor(x, y, Color.rgb((int)(((double)x/width)*255),
                        (int)(((double)y/height)*255),
                        (int)(((double)(x+y)/(height+width))*255)));
            }
        }
        return img;
    }

    public void drawMandelbrotSet2(){
        for (int y=0;y<h;y++){
            for (int x=0;x<w;x++){
                int iter = mset.getIterationsTaken(y,x);
                if (iter < iterations){
                    Complex z = mset.getFinalZ(y,x);
                    double zx = z.getReal();
                    double zy = z.getImaginary();
                    double logZn = Math.log(zx * zx + zy * zy) / 2;
                    double nu = Math.log(logZn / Math.log(2)) / Math.log(2);
                    double smoothIter = iter + 1 - nu;
                    double adjustedC = Math.pow(smoothIter, 0.2);
                    double hue = adjustedC * 240;
                    pw.setColor(x, y, Color.hsb(hue, 1, 1));
                }
                else {
                    pw.setColor(x,y,Color.BLACK);
                }
            }
        }
    }

    public void drawMandelbrotSet(){
        int t = 0;
        int[] freq = new int[iterations + 1];
        for (int y=0;y<h;y++){
            for (int x=0;x<w;x++){
                int iters = mset.getIterationsTaken(y,x);
                freq[iters]++;
                t++;
            }
        }
        for (int y=0;y<h;y++){
            for (int x=0;x<w;x++){
                double c = 0;
                int iters = mset.getIterationsTaken(y,x);
                for (int i=0;i<=iters;i++){
                    c += freq[i];
                }
                c /= t;
                if (iters < iterations) {
                    pw.setColor(x, y, Color.rgb(y*255/h, (int) (c * 255), x*255/w));
                }
                else{
                    pw.setColor(x,y,Color.BLACK);
                }
            }
        }
    }

    @FXML
    public void initialize(){
        Platform.runLater(() -> {
            h = (int)canvas.getHeight();
            w = (int)canvas.getWidth();
            gc = canvas.getGraphicsContext2D();
            pw = gc.getPixelWriter();
            final double[] x = {-2.2, 0.8};
            final double[] y = {-1.12, 1.12};
            final Complex c = Complex.of(0,0);
            double threshold = 4.0;
            iterations = 1000;
            mset = new MandelbrotSet(w, h, x[0], x[1], y[0], y[1], threshold, iterations);
            drawMandelbrotSet();
            AnimationTimer a = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    mset.zoom(c, 0.8);
                    drawMandelbrotSet();
                }
            };
            canvas.setOnMouseClicked(e->{
                int mx = (int)e.getX();
                int my = (int)e.getY();
                double[] b = mset.getBounds();
                Complex g = Converter.real2argand(mx,my,w,h,b[0],b[1],b[2],b[3]);
                //g = Complex.of(-0.77399186284233290000, 0.12030317200727936000);
                System.err.println("Bing! You pressed on " + g + ".");
                //g = Complex.of(-1.5,0);
                c.setRe(g.getReal());
                c.setIm(g.getImaginary());
                a.stop();
                a.start();
            });
        });
    }
}