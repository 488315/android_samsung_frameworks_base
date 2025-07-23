package com.samsung.android.wallpaper.legibilitycolors.utils.image;

import android.graphics.Bitmap;
import android.hardware.scontext.SContextConstants;

/* loaded from: classes6.dex */
public class ImageConvolution extends BitmapImageProcessing {
    public double mFactor;
    public double mOffset;

    public ImageConvolution(Bitmap bitmap) {
        super(bitmap);
        this.mFactor = 1.0d;
        this.mOffset = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    public ImageConvolution(int[] iArr, int i, int i2, Bitmap.Config config) {
        super(iArr, i, i2, config);
        this.mFactor = 1.0d;
        this.mOffset = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    public void computeConvolution(double[][] dArr) {
        int[] iArr = new int[this.mPixels.length];
        computeConvolution(dArr, iArr);
        setPixels(iArr);
    }

    public void computeConvolution(double[][] dArr, int[] iArr) {
        int i;
        int i2 = this.mImageWidth;
        int i3 = i2 - 1;
        int i4 = this.mImageHeight - 1;
        int[] iArr2 = this.mPixels;
        int length = iArr2.length;
        int length2 = dArr.length;
        int i5 = length2 * length2;
        int i6 = length2 / 2;
        double d = this.mOffset;
        double d2 = 1.0d / this.mFactor;
        double[] dArr2 = new double[i5];
        int[] iArr3 = new int[i5];
        int[] iArr4 = new int[i5];
        for (int i7 = 0; i7 < i5; i7++) {
            int i8 = i7 / length2;
            int i9 = i7 % length2;
            dArr2[i7] = dArr[i8][i9];
            iArr3[i7] = i9 - i6;
            iArr4[i7] = i8 - i6;
        }
        int i10 = 0;
        while (i10 < length) {
            int i11 = i10 % i2;
            int i12 = i10 / i2;
            float f = 0.0f;
            int i13 = i10;
            int i14 = i2;
            int[] iArr5 = iArr2;
            float f2 = 0.0f;
            float f3 = 0.0f;
            int i15 = 0;
            while (i15 < i5) {
                int i16 = i11 + iArr3[i15];
                if (i16 < 0) {
                    i16 = 0;
                } else if (i16 > i3) {
                    i16 = i3;
                }
                int i17 = i15;
                int i18 = i12 + iArr4[i15];
                if (i18 < 0) {
                    i18 = 0;
                } else if (i18 > i4) {
                    i18 = i4;
                }
                int i19 = iArr5[i16 + (i18 * i14)];
                double d3 = dArr2[i17];
                f = (float) (f + (((i19 >> 16) & 255) * d3));
                f2 = (float) (f2 + (((i19 >> 8) & 255) * d3));
                f3 = (float) (f3 + ((255 & i19) * d3));
                i15 = i17 + 1;
                i3 = i3;
                i4 = i4;
            }
            int i20 = i4;
            int i21 = i3;
            int i22 = (int) ((f * d2) + d);
            int i23 = i22 < 0 ? 0 : i22 > 255 ? 255 : i22;
            int i24 = (int) ((f2 * d2) + d);
            if (i24 < 0) {
                i24 = 0;
                i = 255;
            } else {
                i = 255;
                if (i24 > 255) {
                    i24 = 255;
                }
            }
            int i25 = (int) ((f3 * d2) + d);
            if (i25 < 0) {
                i25 = 0;
            } else if (i25 > i) {
                i25 = i;
            }
            iArr[i13] = i25 | ((iArr5[i13] >>> 24) << 24) | (i23 << 16) | (i24 << 8);
            i10 = i13 + 1;
            i3 = i21;
            i2 = i14;
            iArr2 = iArr5;
            i4 = i20;
        }
    }
}
