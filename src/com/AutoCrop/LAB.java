package com.AutoCrop;

public class LAB extends ColourSpace {
    public double L, A, B;
    private XYZ xyz;

    public LAB() {
        xyz = new XYZ();
    }

    private double CIELAB(double t) {
        return (t > 0.008856) ? Math.pow(t, 1/3.0) : (7.787*t) + (16/116);
    }

    private double reverseCIELAB(double t) {
        return (Math.pow(t,3)> 0.008856) ? Math.pow(t,3) : (t - 16/116) / 7.787;
    }

    public LAB fromXYZ(XYZ xyz) {
        double X = CIELAB(xyz.getValues()[0] / 95.047);
        double Y = CIELAB(xyz.getValues()[1] / 100.0);
        double Z = CIELAB(xyz.getValues()[2] / 108.883);

        L = (116.0 * Y) - 16;
        A = 500.0 * (X - Y);
        B = 200.0 * (Y - Z);
        return this;
    }

    public XYZ toXYZ() {
        double Y = (L + 16) / 116;
        double X = (A / 500) + Y;
        double Z = Y - (B / 200);

        X = reverseCIELAB(X) * 95.047;
        Y = reverseCIELAB(Y) * 100.0;
        Z = reverseCIELAB(Z) * 108.883;
        xyz.set(X, Y, Z);

        return xyz;
    }

    public double deltaE(LAB lab) {
        return Math.sqrt(Math.pow(this.L - lab.L, 2) + Math.pow(this.A - lab.A, 2) + Math.pow(this.B - lab.B, 2));
    }

    public double[] getValues() {
        double [] values = new double[3];
        values[0] = L;
        values[1] = A;
        values[2] = B;

        return values;
    }
}
