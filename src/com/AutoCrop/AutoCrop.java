package com.AutoCrop;


import android.graphics.Bitmap;

public class AutoCrop {
    private int[] pixelArray;
    private int height, width;
    private RGB pixelRGB;
    private LAB whiteLAB,pixelLAB;
    private int tolerance = 5;
    private Bitmap originalImage;

    public AutoCrop(Bitmap bitmap) {
        originalImage = bitmap;
        height = bitmap.getHeight();
        width = bitmap.getWidth();
        this.pixelArray = new int[height*width];
        bitmap.getPixels(pixelArray, 0, width, 0, 0, width, height);
    }

    public AutoCrop(Bitmap bitmap, int tol) {
        if (tol >= 0) tolerance = tol;
        originalImage = bitmap;
        height = bitmap.getHeight();
        width = bitmap.getWidth();
        this.pixelArray = new int[height*width];
        bitmap.getPixels(pixelArray, 0, width, 0, 0, width, height);
    }

    public Bitmap croppedBitmap() {
        pixelRGB = new RGB();
        pixelLAB = new LAB();
        whiteLAB = (LAB) new LAB().convertFrom(new RGB(255, 255, 255));

        int top = topBorder();
        int bottom = bottomBorder();
        int left = leftBorder();
        int right = rightBorder();

        return Bitmap.createBitmap(originalImage, left, top, width-(left + right), height-(top + bottom));
    }

    private double diffFromWhite(int colour) {
        pixelRGB.setColour(colour);
        pixelLAB.convertFrom(pixelRGB);
        return pixelLAB.deltaE(whiteLAB);
    }

    private int topBorder() {
        for (int row = 0; row < height/3; row++) {
            double rowDiff = 0;
            for (int pixel = 0; pixel < width; pixel++) {
                rowDiff += diffFromWhite(pixelArray[(row * width) + pixel]);
            }
            if (rowDiff/width > tolerance) return row;
        }
        return 0;
    }

    private int bottomBorder() {
        for (int row = height - 1; row > 2*(height/3); row--) {
            double rowDiff = 0;
            for (int pixel = 0; pixel < width; pixel++) {
                rowDiff += diffFromWhite(pixelArray[(row * width) + pixel]);
            }
            if (rowDiff/width > tolerance) return height - row;
        }
        return 0;
    }

    private int rightBorder() {
        for (int column = width - 1; column > 2*(width/3); column--) {
            double columnDiff = 0;
            for (int pixel = 0; pixel < height; pixel++) {
                columnDiff += diffFromWhite(pixelArray[column + (pixel * width)]);
            }
            if (columnDiff/height > tolerance) return width - column;
        }
        return 0;
    }

    private int leftBorder() {
        for (int column = 0; column < width/2; column++) {
            double columnDiff = 0;
            for (int pixel = 0; pixel < height; pixel++) {
                columnDiff += diffFromWhite(pixelArray[column + (pixel * width)]);
            }
            if (columnDiff/height > tolerance) return column;
        }
        return 0;
    }
}