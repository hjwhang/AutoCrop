package com.AutoCrop;

/**
 * Implementing this class will allow convertFrom() to convert to and from any subclassed colour space.
 */

abstract class ColourSpace {
    abstract double[] getValues();
    abstract XYZ toXYZ();
    abstract ColourSpace fromXYZ(XYZ xyz);


    public ColourSpace convertFrom(ColourSpace colourSpace) {
        return fromXYZ(colourSpace.toXYZ());
    };

}