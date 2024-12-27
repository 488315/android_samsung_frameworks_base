package com.android.systemui.edgelighting.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Slog;

public final class ExtractAppIconUtils {

    public final class ColorBucket {
        public int bestColor;
        public int bestMatchingColor;
    }

    public static int processDominantColorInImage(Drawable drawable) {
        int i;
        Bitmap bitmap;
        float f;
        int[] iArr;
        int i2 = 0;
        if (drawable == null) {
            Slog.w("ExtractAppIconUtils", "The bitmap provided to processDominantColorInImage() is null. using default color.");
            return 0;
        }
        Bitmap drawableToBitmap = DrawableUtils.drawableToBitmap(drawable);
        int i3 = 20;
        float f2 = 255.0f / 20;
        ColorBucket colorBucket = new ColorBucket();
        int width = drawableToBitmap.getWidth();
        int height = drawableToBitmap.getHeight();
        int i4 = (width * height) / 2;
        int[] iArr2 = new int[(int) Math.pow(20, 3.0d)];
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (true) {
            if (i5 >= width) {
                i = i3;
                break;
            }
            int i8 = i2;
            while (true) {
                if (i8 >= height) {
                    bitmap = drawableToBitmap;
                    f = f2;
                    iArr = iArr2;
                    break;
                }
                int pixel = drawableToBitmap.getPixel(i5, i8);
                if (Color.alpha(pixel) < 250) {
                    bitmap = drawableToBitmap;
                } else {
                    int red = Color.red(pixel);
                    int green = Color.green(pixel);
                    int blue = Color.blue(pixel);
                    int i9 = (int) f2;
                    int i10 = (i9 * blue) + (red / i9) + green;
                    bitmap = drawableToBitmap;
                    int i11 = iArr2[i10] + 1;
                    iArr2[i10] = i11;
                    if (i11 > i7) {
                        colorBucket.bestColor = Color.rgb(red, green, blue);
                        i7 = i11;
                    }
                    if (i11 > i6) {
                        f = f2;
                        if (Color.red(pixel) != Color.blue(pixel) || Color.blue(pixel) != Color.green(pixel)) {
                            int red2 = Color.red(pixel) - Color.green(pixel);
                            int red3 = Color.red(pixel) - Color.blue(pixel);
                            iArr = iArr2;
                            if (red2 > 5 || ((red2 < -5 && red3 > 5) || red3 < -5)) {
                                colorBucket.bestMatchingColor = Color.rgb(red, green, blue);
                                i6 = i11;
                                if (i11 > i4) {
                                    break;
                                }
                            }
                            i8 += 20;
                            f2 = f;
                            drawableToBitmap = bitmap;
                            iArr2 = iArr;
                        }
                        iArr = iArr2;
                        i8 += 20;
                        f2 = f;
                        drawableToBitmap = bitmap;
                        iArr2 = iArr;
                    }
                }
                f = f2;
                iArr = iArr2;
                i8 += 20;
                f2 = f;
                drawableToBitmap = bitmap;
                iArr2 = iArr;
            }
            if (i7 > i4) {
                i = 20;
                break;
            }
            i5 += 20;
            f2 = f;
            drawableToBitmap = bitmap;
            iArr2 = iArr;
            i2 = 0;
            i3 = 20;
        }
        return i6 > Math.round(((float) ((height / i) * (width / i))) * 0.005f) ? colorBucket.bestMatchingColor : colorBucket.bestColor;
    }
}
