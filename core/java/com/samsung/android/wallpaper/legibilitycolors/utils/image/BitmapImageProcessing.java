package com.samsung.android.wallpaper.legibilitycolors.utils.image;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Spanned;
import com.android.internal.graphics.ColorUtils;
import com.samsung.android.wallpaper.legibilitycolors.utils.IUXColorUtils;

/* loaded from: classes6.dex */
public class BitmapImageProcessing {
    protected Bitmap.Config mConfig;
    protected final int mImageHeight;
    protected final int mImageWidth;
    protected int[] mPixels;

    public BitmapImageProcessing(Bitmap bitmap) {
        this.mImageWidth = bitmap.getWidth();
        this.mImageHeight = bitmap.getHeight();
        this.mConfig = bitmap.getConfig();
        this.mPixels = new int[this.mImageWidth * this.mImageHeight];
        bitmap.getPixels(this.mPixels, 0, this.mImageWidth, 0, 0, this.mImageWidth, this.mImageHeight);
    }

    public BitmapImageProcessing(int[] pixels, int width, int height, Bitmap.Config config) {
        this.mImageWidth = width;
        this.mImageHeight = height;
        this.mConfig = config;
        int[] copiedPixels = new int[pixels.length];
        System.arraycopy(pixels, 0, copiedPixels, 0, pixels.length);
        this.mPixels = copiedPixels;
    }

    public int getImageWidth() {
        return this.mImageWidth;
    }

    public int getImageHeight() {
        return this.mImageHeight;
    }

    public int[] getPixelsReference() {
        return this.mPixels;
    }

    public int[] getCopiedPixels() {
        int[] pixels = new int[this.mPixels.length];
        System.arraycopy(this.mPixels, 0, pixels, 0, this.mPixels.length);
        return pixels;
    }

    public Bitmap createBitmapFromCurrent() {
        Bitmap convertedBitmap = Bitmap.createBitmap(this.mImageWidth, this.mImageHeight, this.mConfig);
        convertedBitmap.setPixels(this.mPixels, 0, this.mImageWidth, 0, 0, this.mImageWidth, this.mImageHeight);
        return convertedBitmap;
    }

    public void setPixels(int[] pixelsSrc) {
        this.mPixels = pixelsSrc;
    }

    public void convertToLuminosity() {
        convertToLuminosity(this.mPixels);
    }

    public void convertToLuminosity(int[] pixelsDst) {
        int[] pixels = this.mPixels;
        int size = pixels.length;
        for (int i = 0; i < size; i++) {
            int grayValue = (int) (IUXColorUtils.caculateLuminosity(pixels[i]) * 255.0f);
            pixelsDst[i] = Color.rgb(grayValue, grayValue, grayValue);
        }
    }

    public void convertToGoogleLuminosity() {
        convertToGoogleLuminosity(this.mPixels);
    }

    public void convertToGoogleLuminosity(int[] pixelsDst) {
        int[] pixels = this.mPixels;
        int size = pixels.length;
        for (int i = 0; i < size; i++) {
            int grayValue = (int) (ColorUtils.calculateLuminance(pixels[i]) * 255.0d);
            pixelsDst[i] = Color.rgb(grayValue, grayValue, grayValue);
        }
    }

    public void convertToLuminosity2() {
        convertToLuminosity2(this.mPixels);
    }

    public void convertToLuminosity2(int[] pixelsDst) {
        int[] pixels = this.mPixels;
        int size = pixels.length;
        float[] hsl = new float[3];
        for (int i = 0; i < size; i++) {
            ColorUtils.colorToHSL(pixels[i], hsl);
            int grayValue = (int) (hsl[2] * 255.0f);
            pixelsDst[i] = Color.rgb(grayValue, grayValue, grayValue);
        }
    }

    public void convertToBrightness() {
        convertToBrightness(this.mPixels);
    }

    public void convertToBrightness(int[] pixelsDst) {
        int[] pixels = this.mPixels;
        int size = pixels.length;
        float[] hsv = new float[3];
        for (int i = 0; i < size; i++) {
            Color.colorToHSV(pixels[i], hsv);
            int grayValue = (int) (hsv[2] * 255.0f);
            pixelsDst[i] = Color.rgb(grayValue, grayValue, grayValue);
        }
    }

    public void convertToLuminosity3() {
        convertToLuminosity3(this.mPixels);
    }

    public void convertToLuminosity3(int[] pixelsDst) {
        int[] pixels = this.mPixels;
        int size = pixels.length;
        for (int i = 0; i < size; i++) {
            int grayValue = (int) (ColorUtils.calculateLuminance(pixels[i]) * 255.0d);
            pixelsDst[i] = Color.rgb(grayValue, grayValue, grayValue);
        }
    }

    public float getAverageValueFromRed(int[] pixels) {
        float avr = 0.0f;
        int size = pixels.length;
        for (int i = 0; i < size; i++) {
            avr = ((i * avr) + Color.red(pixels[i])) / (i + 1.0f);
        }
        return 0.003921569f * avr;
    }

    public float getAverageValueFromRed() {
        return getAverageValueFromRed(this.mPixels);
    }

    public float getDifferentialValueFromRed(int[] pixels, float referenceValue) {
        int size = pixels.length;
        int ref = (int) referenceValue;
        float sum = 0.0f;
        for (int pixel : pixels) {
            int red = (pixel >> 16) & 255;
            sum += red > ref ? red - ref : ref - red;
        }
        float avr = sum / size;
        return 0.003921569f * avr;
    }

    public float getDifferentialValueFromRed(float referenceValue) {
        return getDifferentialValueFromRed(this.mPixels, referenceValue);
    }

    public int getAverageColor(int[] pixels) {
        int i = 0;
        if (pixels == null) {
            return 0;
        }
        int A_MASK = -16777216;
        int R_MASK = Spanned.SPAN_PRIORITY;
        int pixelNum = pixels.length;
        long r = 0;
        long g = 0;
        long b = 0;
        int length = pixels.length;
        while (i < length) {
            int pixel = pixels[i];
            r += 16711680 & pixel;
            g += 65280 & pixel;
            b += pixel & 255;
            i++;
            A_MASK = A_MASK;
            R_MASK = R_MASK;
        }
        int avgR = ((int) (r / pixelNum)) & Spanned.SPAN_PRIORITY;
        int avgG = ((int) (g / pixelNum)) & 65280;
        int avgB = ((int) (b / pixelNum)) & 255;
        return (-16777216) | avgR | avgG | avgB;
    }

    public int getAverageColor() {
        return getAverageColor(this.mPixels);
    }
}
