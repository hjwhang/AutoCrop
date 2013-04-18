package com.AutoCrop;

public class XYZ extends ColourSpace{
    private double X, Y, Z;

    public XYZ(double X, double Y, double Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public XYZ() {}

    public void set(double X, double Y, double Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }
    protected XYZ toXYZ() {
        return this;
    }

    protected ColourSpace fromXYZ(XYZ xyz) {
        return xyz;
    }

    public double[] getValues() {
        double[] values = new double[3];
        values[0] = X;
        values[1] = Y;
        values[2] = Z;
        return values;
    }
}
