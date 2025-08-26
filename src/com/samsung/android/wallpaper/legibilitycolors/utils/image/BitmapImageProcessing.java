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
        int width = bitmap.getWidth();
        this.mImageWidth = width;
        int height = bitmap.getHeight();
        this.mImageHeight = height;
        this.mConfig = bitmap.getConfig();
        int[] iArr = new int[width * height];
        this.mPixels = iArr;
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
    }

    public BitmapImageProcessing(int[] iArr, int i, int i2, Bitmap.Config config) {
        this.mImageWidth = i;
        this.mImageHeight = i2;
        this.mConfig = config;
        int[] iArr2 = new int[iArr.length];
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        this.mPixels = iArr2;
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
        int[] iArr = this.mPixels;
        int[] iArr2 = new int[iArr.length];
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        return iArr2;
    }

    public Bitmap createBitmapFromCurrent() {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mImageWidth, this.mImageHeight, this.mConfig);
        int[] iArr = this.mPixels;
        int i = this.mImageWidth;
        bitmapCreateBitmap.setPixels(iArr, 0, i, 0, 0, i, this.mImageHeight);
        return bitmapCreateBitmap;
    }

    public void setPixels(int[] iArr) {
        this.mPixels = iArr;
    }

    public void convertToLuminosity() {
        convertToLuminosity(this.mPixels);
    }

    public void convertToLuminosity(int[] iArr) {
        int[] iArr2 = this.mPixels;
        int length = iArr2.length;
        for (int i = 0; i < length; i++) {
            int iCaculateLuminosity = (int) (IUXColorUtils.caculateLuminosity(iArr2[i]) * 255.0f);
            iArr[i] = Color.rgb(iCaculateLuminosity, iCaculateLuminosity, iCaculateLuminosity);
        }
    }

    public void convertToGoogleLuminosity() {
        convertToGoogleLuminosity(this.mPixels);
    }

    public void convertToGoogleLuminosity(int[] iArr) {
        int[] iArr2 = this.mPixels;
        int length = iArr2.length;
        for (int i = 0; i < length; i++) {
            int iCalculateLuminance = (int) (ColorUtils.calculateLuminance(iArr2[i]) * 255.0d);
            iArr[i] = Color.rgb(iCalculateLuminance, iCalculateLuminance, iCalculateLuminance);
        }
    }

    public void convertToLuminosity2() {
        convertToLuminosity2(this.mPixels);
    }

    public void convertToLuminosity2(int[] iArr) {
        int[] iArr2 = this.mPixels;
        int length = iArr2.length;
        float[] fArr = new float[3];
        for (int i = 0; i < length; i++) {
            ColorUtils.colorToHSL(iArr2[i], fArr);
            int i2 = (int) (fArr[2] * 255.0f);
            iArr[i] = Color.rgb(i2, i2, i2);
        }
    }

    public void convertToBrightness() {
        convertToBrightness(this.mPixels);
    }

    public void convertToBrightness(int[] iArr) {
        int[] iArr2 = this.mPixels;
        int length = iArr2.length;
        float[] fArr = new float[3];
        for (int i = 0; i < length; i++) {
            Color.colorToHSV(iArr2[i], fArr);
            int i2 = (int) (fArr[2] * 255.0f);
            iArr[i] = Color.rgb(i2, i2, i2);
        }
    }

    public void convertToLuminosity3() {
        convertToLuminosity3(this.mPixels);
    }

    public void convertToLuminosity3(int[] iArr) {
        int[] iArr2 = this.mPixels;
        int length = iArr2.length;
        for (int i = 0; i < length; i++) {
            int iCalculateLuminance = (int) (ColorUtils.calculateLuminance(iArr2[i]) * 255.0d);
            iArr[i] = Color.rgb(iCalculateLuminance, iCalculateLuminance, iCalculateLuminance);
        }
    }

    public float getAverageValueFromRed(int[] iArr) {
        float fRed = 0.0f;
        for (int i = 0; i < iArr.length; i++) {
            float f = i;
            fRed = ((fRed * f) + Color.red(iArr[i])) / (f + 1.0f);
        }
        return fRed * 0.003921569f;
    }

    public float getAverageValueFromRed() {
        return getAverageValueFromRed(this.mPixels);
    }

    public float getDifferentialValueFromRed(int[] iArr, float f) {
        int length = iArr.length;
        int i = (int) f;
        float f2 = 0.0f;
        for (int i2 : iArr) {
            f2 += ((i2 >> 16) & 255) > i ? r3 - i : i - r3;
        }
        return (f2 / length) * 0.003921569f;
    }

    public float getDifferentialValueFromRed(float f) {
        return getDifferentialValueFromRed(this.mPixels, f);
    }

    public int getAverageColor(int[] iArr) {
        if (iArr == null) {
            return 0;
        }
        int length = iArr.length;
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        for (int i : iArr) {
            j += 16711680 & i;
            j2 += 65280 & i;
            j3 += i & 255;
        }
        long j4 = length;
        return (((int) (j3 / j4)) & 255) | (-16777216) | (((int) (j / j4)) & Spanned.SPAN_PRIORITY) | (((int) (j2 / j4)) & 65280);
    }

    public int getAverageColor() {
        return getAverageColor(this.mPixels);
    }
}
