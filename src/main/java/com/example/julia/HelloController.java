package com.example.julia;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class HelloController {
    @FXML
    private ImageView canvas;
    private int h,w;
    private int iterations = 10;

    private JuliaSet jset;

    public Image pixelTest(int width, int height) {
        //cool gradient :)
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

    public Image drawJuliaSet(){
        WritableImage img = new WritableImage(w, h);
        PixelWriter pw = img.getPixelWriter();
        for (int x=0;x<w;x++){
            for (int y=0;y<h;y++){
                int iters = jset.getIterationsTaken(x,y);
                /*
                Color c;
                if (iters == 0){
                    c = Color.BLACK;
                }
                else {
                    c = Color.hsb(Math.sin(iters/20.0)*360, 1, 1);
                }
                pw.setColor(x,y,c);

                 */
                double percent = (double)(iters)/iterations;
                //percent = 1/(1+Math.exp(-10*(percent-0.1)));
                //percent *= percent*percent;
                double multX = ((double)x/(double)w);
                double multY = ((double)y/(double)h);
                pw.setColor(x,y,Color.rgb((int)(percent*multX*255), (int)((percent)*multY*255), (int)(percent*200) + 55));
                //int val = (int)(((double)iters / iterations) * 255);
                //pw.setColor(x, y, Color.rgb(val, val, val));
            }
        }
        return img;
    }

    @FXML
    public void initialize(){
        Platform.runLater(() -> {
            h = (int)canvas.getFitHeight();
            w = (int)canvas.getFitWidth();
            double x = 1.5;
            double y = 1.5;
            double threshold = 4.0;
            iterations = 500;
            Complex c = Complex.of(-0.8, 0.156);
            System.out.println("X range: " + -x + " to " + x);
            System.out.println("Y range: " + -y + " to " + y);
            System.out.println("c: " + c);
            System.out.println("Threshold: " + threshold);
            System.out.println("Iterations: " + iterations);
            jset = new JuliaSet(w, h, -x, x, -y, y, threshold, c, iterations);
            canvas.setImage(drawJuliaSet());
        });
    }
}