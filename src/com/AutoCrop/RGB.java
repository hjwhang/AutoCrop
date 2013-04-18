package com.AutoCrop;

import android.graphics.Color;

public class RGB extends ColourSpace {
    private int red, green, blue;
    private XYZ xyz;

    public RGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        xyz = new XYZ();
    }

    public RGB() {
        xyz = new XYZ();
    }

    public void setColour(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void setColour(int colourInt) {
        this.red = Color.red(colourInt);
        this.green = Color.green(colourInt);
        this.blue = Color.blue(colourInt);
    }

    private double gamma(double comp) {
        return (comp > 0.0031308) ? 1.055 * (Math.pow(comp, 1/2.4)) - 0.055 : 12.92 * comp;
    }

    private double reverseGamma(double colour) {
        return (colour >  0.04045) ? Math.pow( (colour+0.055)/1.055, 2.4 ) *  100 : colour * 7.74;
    }

    public XYZ toXYZ() {
        double r, g, b, X, Y, Z;
        r = reverseGamma(red / 255.0);
        g = reverseGamma(green / 255.0);
        b = reverseGamma(blue / 255.0);

        //Matrix multiplication
        X = (r * 0.4124) + (g * 0.3576) + (b * 0.1805);
        Y = (r * 0.2126) + (g * 0.7152) + (b * 0.0722);
        Z = (r * 0.0193) + (g * 0.1192) + (b * 0.9505);
        xyz.set(X, Y, Z);

        return xyz;
    }

    protected RGB fromXYZ(XYZ xyz) {
        double[] XYZ = xyz.getValues();
        double X = XYZ[0] / 100.0;
        double Y = XYZ[1] / 100.0;
        double Z = XYZ[2] / 100.0;
        double R, G, B;

        //Matrix multiplication
        R = gamma(3.24060*X + -1.5372*Y + -0.4986*Z);
        G = gamma(-0.9689*X +  1.8758*Y +  0.0415*Z);
        B = gamma(0.05570*X +  -0.204*Y +  1.0570*Z);

        red =  (int) Math.floor(R * 255);
        green = (int) Math.floor(G * 255);
        blue = (int) Math.floor(B * 255);

        return this;
    }

    public double[] getValues() {
        double[] values = new double[3];
        values[0] = (double) red;
        values[1] = (double) green;
        values[2] = (double) blue;

        return values;
    }

}
