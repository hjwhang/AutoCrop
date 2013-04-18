package com.AutoCrop;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

public class AutoCropImageView extends ImageView {
    private int tolerance;
    private Boolean cropDisabled;

    public AutoCropImageView(android.content.Context context) {
        super(context);
    }

    public AutoCropImageView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes =
                context.obtainStyledAttributes(attrs, com.AutoCrop.R.styleable.AutoCropImageView);
        //Default tolerance is 5
        tolerance = attributes.getInt(com.AutoCrop.R.styleable.AutoCropImageView_tolerance, 5);
        cropDisabled = attributes.getBoolean(com.AutoCrop.R.styleable.AutoCropImageView_disable_crop, false);
        attributes.recycle();
    }

    public AutoCropImageView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray attributes = context.obtainStyledAttributes(attrs, com.AutoCrop.R.styleable.AutoCropImageView);
        //Default tolerance is 5
        tolerance = attributes.getInt(com.AutoCrop.R.styleable.AutoCropImageView_tolerance, 5);
        cropDisabled = attributes.getBoolean(com.AutoCrop.R.styleable.AutoCropImageView_disable_crop, false);
        attributes.recycle();
    }


    public void setImage(android.graphics.drawable.Drawable drawable) {
        if (cropDisabled) {
            super.setImageDrawable(drawable);
        }
        else { //display cropped image
            AutoCrop autoCrop;
            if (drawable instanceof BitmapDrawable) {
                autoCrop = new AutoCrop(((BitmapDrawable) drawable).getBitmap(), tolerance);
            }
            else {
                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                autoCrop = new AutoCrop(bitmap, tolerance);
            }
            super.setImageBitmap(autoCrop.croppedBitmap());
        }
    }

    public void setImage(android.graphics.Bitmap bm) {
        if (cropDisabled) {
            super.setImageBitmap(bm);
        }
        else {
            super.setImageBitmap(new AutoCrop(bm, tolerance).croppedBitmap());
        }
    }

    public void setImage(int resID) {
        if (cropDisabled) {
            super.setImageResource(resID);
        }
        else {
            Bitmap bm = BitmapFactory.decodeResource(getContext().getResources(),
                    resID);
            super.setImageBitmap(new AutoCrop(bm, tolerance).croppedBitmap());
        }
    }
}
