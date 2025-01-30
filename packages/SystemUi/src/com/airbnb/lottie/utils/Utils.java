package com.airbnb.lottie.utils;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import java.io.Closeable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Utils {
    public static final C06051 threadLocalPathMeasure = new ThreadLocal() { // from class: com.airbnb.lottie.utils.Utils.1
        @Override // java.lang.ThreadLocal
        public final Object initialValue() {
            return new PathMeasure();
        }
    };
    public static final C06062 threadLocalTempPath = new ThreadLocal() { // from class: com.airbnb.lottie.utils.Utils.2
        @Override // java.lang.ThreadLocal
        public final Object initialValue() {
            return new Path();
        }
    };
    public static final C06073 threadLocalTempPath2 = new ThreadLocal() { // from class: com.airbnb.lottie.utils.Utils.3
        @Override // java.lang.ThreadLocal
        public final Object initialValue() {
            return new Path();
        }
    };
    public static final C06084 threadLocalPoints = new ThreadLocal() { // from class: com.airbnb.lottie.utils.Utils.4
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
            float min = Math.min(f4, f5) + f6;
            float max = Math.max(f4, f5) + f6;
            if (min >= length && max >= length) {
                min = MiscUtils.floorMod(min, length);
                max = MiscUtils.floorMod(max, length);
            }
            if (min < 0.0f) {
                min = MiscUtils.floorMod(min, length);
            }
            if (max < 0.0f) {
                max = MiscUtils.floorMod(max, length);
            }
            if (min == max) {
                path.reset();
                return;
            }
            if (min >= max) {
                min -= length;
            }
            path2.reset();
            pathMeasure.getSegment(min, max, path2, true);
            if (max > length) {
                path3.reset();
                pathMeasure.getSegment(0.0f, max % length, path3, true);
                path2.addPath(path3);
            } else if (min < 0.0f) {
                path3.reset();
                pathMeasure.getSegment(min + length, length, path3, true);
                path2.addPath(path3);
            }
            path.set(path2);
        }
    }

    public static void closeQuietly(Closeable closeable) {
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
}
