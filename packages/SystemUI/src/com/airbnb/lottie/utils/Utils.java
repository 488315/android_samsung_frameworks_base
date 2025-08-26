package com.airbnb.lottie.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import java.io.Closeable;
import java.io.IOException;

/* loaded from: classes.dex */
public final class Utils {
    public static final AnonymousClass1 threadLocalPathMeasure = new ThreadLocal() { // from class: com.airbnb.lottie.utils.Utils.1
        @Override // java.lang.ThreadLocal
        public final Object initialValue() {
            return new PathMeasure();
        }
    };
    public static final AnonymousClass2 threadLocalTempPath = new ThreadLocal() { // from class: com.airbnb.lottie.utils.Utils.2
        @Override // java.lang.ThreadLocal
        public final Object initialValue() {
            return new Path();
        }
    };
    public static final AnonymousClass3 threadLocalTempPath2 = new ThreadLocal() { // from class: com.airbnb.lottie.utils.Utils.3
        @Override // java.lang.ThreadLocal
        public final Object initialValue() {
            return new Path();
        }
    };
    public static final AnonymousClass4 threadLocalPoints = new ThreadLocal() { // from class: com.airbnb.lottie.utils.Utils.4
        @Override // java.lang.ThreadLocal
        public final Object initialValue() {
            return new float[4];
        }
    };
    public static final float INV_SQRT_2 = (float) (Math.sqrt(2.0d) / 2.0d);

    private Utils() {
    }

    public static void applyTrimPathIfNeeded(Path path, float f, float f2, float f3) {
        PathMeasure pathMeasure = (PathMeasure) threadLocalPathMeasure.get();
        Path path2 = (Path) threadLocalTempPath.get();
        Path path3 = (Path) threadLocalTempPath2.get();
        pathMeasure.setPath(path, false);
        float length = pathMeasure.getLength();
        if (!(f == 1.0f && f2 == 0.0f) && length >= 1.0f && Math.abs((f2 - f) - 1.0f) >= 0.01d) {
            float f4 = f * length;
            float f5 = f2 * length;
            float f6 = f3 * length;
            float fMin = Math.min(f4, f5) + f6;
            float fMax = Math.max(f4, f5) + f6;
            if (fMin >= length && fMax >= length) {
                fMin = MiscUtils.floorMod(fMin, length);
                fMax = MiscUtils.floorMod(fMax, length);
            }
            if (fMin < 0.0f) {
                fMin = MiscUtils.floorMod(fMin, length);
            }
            if (fMax < 0.0f) {
                fMax = MiscUtils.floorMod(fMax, length);
            }
            if (fMin == fMax) {
                path.reset();
                return;
            }
            if (fMin >= fMax) {
                fMin -= length;
            }
            path2.reset();
            pathMeasure.getSegment(fMin, fMax, path2, true);
            if (fMax > length) {
                path3.reset();
                pathMeasure.getSegment(0.0f, fMax % length, path3, true);
                path2.addPath(path3);
            } else if (fMin < 0.0f) {
                path3.reset();
                pathMeasure.getSegment(fMin + length, length, path3, true);
                path2.addPath(path3);
            }
            path.set(path2);
        }
    }

    public static void closeQuietly(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }

    public static float dpScale() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    public static float getScale(Matrix matrix) {
        float[] fArr = (float[]) threadLocalPoints.get();
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        float f = INV_SQRT_2;
        fArr[2] = f;
        fArr[3] = f;
        matrix.mapPoints(fArr);
        return (float) Math.hypot(fArr[2] - fArr[0], fArr[3] - fArr[1]);
    }

    public static Bitmap resizeBitmapIfNeeded(Bitmap bitmap, int i, int i2) {
        if (bitmap.getWidth() == i && bitmap.getHeight() == i2) {
            return bitmap;
        }
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, i, i2, true);
        bitmap.recycle();
        return bitmapCreateScaledBitmap;
    }
}
