package com.android.systemui.edgelighting.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Slog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ExtractAppIconUtils {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ColorBucket {
        public int bestColor;
        public int bestMatchingColor;
    }

    public static int processDominantColorInImage(Drawable drawable) {
        int i;
        Bitmap bitmap;
        float f;
        char c;
        int i2 = 0;
        if (drawable == null) {
            Slog.w("ExtractAppIconUtils", "The bitmap provided to processDominantColorInImage() is null. using default color.");
            return 0;
        }
        Bitmap drawableToBitmap = DrawableUtils.drawableToBitmap(drawable);
        char c2 = 20;
        float f2 = 255.0f / 20;
        ColorBucket colorBucket = new ColorBucket();
        int width = drawableToBitmap.getWidth();
        int height = drawableToBitmap.getHeight();
        int i3 = (width * height) / 2;
        int[] iArr = new int[(int) Math.pow(20, 3.0d)];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            if (i4 >= width) {
                i = width;
                break;
            }
            int i7 = i2;
            while (true) {
                if (i7 >= height) {
                    bitmap = drawableToBitmap;
                    f = f2;
                    c = c2;
                    i = width;
                    break;
                }
                int pixel = drawableToBitmap.getPixel(i4, i7);
                if (Color.alpha(pixel) < 250) {
                    bitmap = drawableToBitmap;
                    f = f2;
                    c = c2;
                } else {
                    int red = Color.red(pixel);
                    int green = Color.green(pixel);
                    int blue = Color.blue(pixel);
                    c = c2;
                    int i8 = (int) f2;
                    int i9 = (i8 * blue) + (red / i8) + green;
                    bitmap = drawableToBitmap;
                    int i10 = iArr[i9] + 1;
                    iArr[i9] = i10;
                    if (i10 > i6) {
                        colorBucket.bestColor = Color.rgb(red, green, blue);
                        i6 = i10;
                    }
                    if (i10 > i5) {
                        f = f2;
                        if (Color.red(pixel) != Color.blue(pixel) || Color.blue(pixel) != Color.green(pixel)) {
                            int red2 = Color.red(pixel) - Color.green(pixel);
                            int red3 = Color.red(pixel) - Color.blue(pixel);
                            i = width;
                            if (red2 > 5 || ((red2 < -5 && red3 > 5) || red3 < -5)) {
                                colorBucket.bestMatchingColor = Color.rgb(red, green, blue);
                                i5 = i10;
                                if (i10 > i3) {
                                    break;
                                }
                            }
                            i7 += 20;
                            c2 = c;
                            f2 = f;
                            drawableToBitmap = bitmap;
                            width = i;
                        }
                    } else {
                        f = f2;
                    }
                }
                i = width;
                i7 += 20;
                c2 = c;
                f2 = f;
                drawableToBitmap = bitmap;
                width = i;
            }
            if (i6 > i3) {
                break;
            }
            i4 += 20;
            c2 = c;
            f2 = f;
            drawableToBitmap = bitmap;
            width = i;
            i2 = 0;
        }
        return i5 > Math.round(((float) ((height / 20) * (i / 20))) * 0.005f) ? colorBucket.bestMatchingColor : colorBucket.bestColor;
    }
}
