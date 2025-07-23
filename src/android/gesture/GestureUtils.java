package android.gesture;

import android.util.Log;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class GestureUtils {
    private static final float NONUNIFORM_SCALE = (float) Math.sqrt(2.0d);
    private static final float SCALING_THRESHOLD = 0.26f;

    private GestureUtils() {
    }

    static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.e(GestureConstants.LOG_TAG, "Could not close stream", e);
            }
        }
    }

    public static float[] spatialSampling(Gesture gesture, int i) {
        return spatialSampling(gesture, i, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0023, code lost:
    
        if (r7 < r8) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0026, code lost:
    
        r7 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0027, code lost:
    
        r8 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0039, code lost:
    
        if (r7 < r8) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static float[] spatialSampling(android.gesture.Gesture r24, int r25, boolean r26) {
        /*
            Method dump skipped, instructions count: 380
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.gesture.GestureUtils.spatialSampling(android.gesture.Gesture, int, boolean):float[]");
    }

    private static void plot(float f, float f2, float[] fArr, int i) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        double d = f;
        int floor = (int) Math.floor(d);
        int ceil = (int) Math.ceil(d);
        double d2 = f2;
        int floor2 = (int) Math.floor(d2);
        int ceil2 = (int) Math.ceil(d2);
        if (f == floor && f2 == floor2) {
            int i2 = (ceil2 * i) + ceil;
            if (fArr[i2] < 1.0f) {
                fArr[i2] = 1.0f;
                return;
            }
            return;
        }
        double pow = Math.pow(r4 - f, 2.0d);
        double pow2 = Math.pow(floor2 - f2, 2.0d);
        double pow3 = Math.pow(ceil - f, 2.0d);
        double pow4 = Math.pow(ceil2 - f2, 2.0d);
        float sqrt = (float) Math.sqrt(pow + pow2);
        float sqrt2 = (float) Math.sqrt(pow2 + pow3);
        float sqrt3 = (float) Math.sqrt(pow + pow4);
        float sqrt4 = (float) Math.sqrt(pow3 + pow4);
        float f3 = sqrt + sqrt2 + sqrt3 + sqrt4;
        float f4 = sqrt / f3;
        int i3 = floor2 * i;
        int i4 = i3 + floor;
        if (f4 > fArr[i4]) {
            fArr[i4] = f4;
        }
        float f5 = sqrt2 / f3;
        int i5 = i3 + ceil;
        if (f5 > fArr[i5]) {
            fArr[i5] = f5;
        }
        float f6 = sqrt3 / f3;
        int i6 = ceil2 * i;
        int i7 = floor + i6;
        if (f6 > fArr[i7]) {
            fArr[i7] = f6;
        }
        float f7 = sqrt4 / f3;
        int i8 = i6 + ceil;
        if (f7 > fArr[i8]) {
            fArr[i8] = f7;
        }
    }

    public static float[] temporalSampling(GestureStroke gestureStroke, int i) {
        float f = gestureStroke.length / (i - 1);
        int i2 = 2;
        int i3 = i * 2;
        float[] fArr = new float[i3];
        float[] fArr2 = gestureStroke.points;
        int i4 = 0;
        float f2 = fArr2[0];
        int i5 = 1;
        float f3 = fArr2[1];
        fArr[0] = f2;
        fArr[1] = f3;
        int length = fArr2.length / 2;
        float f4 = Float.MIN_VALUE;
        float f5 = Float.MIN_VALUE;
        float f6 = Float.MIN_VALUE;
        float f7 = 0.0f;
        while (i4 < length) {
            if (f5 == f4) {
                i4++;
                if (i4 >= length) {
                    break;
                }
                int i6 = i4 * 2;
                float f8 = fArr2[i6];
                f6 = fArr2[i6 + i5];
                f5 = f8;
            }
            float f9 = f5 - f2;
            float f10 = f6 - f3;
            float[] fArr3 = fArr2;
            float f11 = f;
            float hypot = (float) Math.hypot(f9, f10);
            float f12 = f7 + hypot;
            if (f12 >= f11) {
                float f13 = (f11 - f7) / hypot;
                f2 += f9 * f13;
                f3 += f13 * f10;
                fArr[i2] = f2;
                fArr[i2 + 1] = f3;
                i2 += 2;
                f7 = 0.0f;
            } else {
                f7 = f12;
                f2 = f5;
                f3 = f6;
                f5 = Float.MIN_VALUE;
                f6 = Float.MIN_VALUE;
            }
            f = f11;
            fArr2 = fArr3;
            i5 = 1;
            f4 = Float.MIN_VALUE;
        }
        while (i2 < i3) {
            fArr[i2] = f2;
            fArr[i2 + 1] = f3;
            i2 += 2;
        }
        return fArr;
    }

    static float[] computeCentroid(float[] fArr) {
        int length = fArr.length;
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i = 0; i < length; i += 2) {
            f += fArr[i];
            f2 += fArr[i + 1];
        }
        float f3 = length;
        return new float[]{(f * 2.0f) / f3, (f2 * 2.0f) / f3};
    }

    private static float[][] computeCoVariance(float[] fArr) {
        float[][] fArr2 = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 2, 2);
        float[] fArr3 = fArr2[0];
        fArr3[0] = 0.0f;
        fArr3[1] = 0.0f;
        float[] fArr4 = fArr2[1];
        fArr4[0] = 0.0f;
        fArr4[1] = 0.0f;
        int length = fArr.length;
        for (int i = 0; i < length; i += 2) {
            float f = fArr[i];
            float f2 = fArr[i + 1];
            float[] fArr5 = fArr2[0];
            fArr5[0] = fArr5[0] + (f * f);
            float f3 = fArr5[1] + (f * f2);
            fArr5[1] = f3;
            float[] fArr6 = fArr2[1];
            fArr6[0] = f3;
            fArr6[1] = fArr6[1] + (f2 * f2);
        }
        float[] fArr7 = fArr2[0];
        float f4 = length / 2;
        fArr7[0] = fArr7[0] / f4;
        fArr7[1] = fArr7[1] / f4;
        float[] fArr8 = fArr2[1];
        fArr8[0] = fArr8[0] / f4;
        fArr8[1] = fArr8[1] / f4;
        return fArr2;
    }

    static float computeTotalLength(float[] fArr) {
        int length = fArr.length - 4;
        float f = 0.0f;
        for (int i = 0; i < length; i += 2) {
            f = (float) (f + Math.hypot(fArr[r3] - fArr[i], fArr[i + 3] - fArr[i + 1]));
        }
        return f;
    }

    static float computeStraightness(float[] fArr) {
        return ((float) Math.hypot(fArr[2] - fArr[0], fArr[3] - fArr[1])) / computeTotalLength(fArr);
    }

    static float computeStraightness(float[] fArr, float f) {
        return ((float) Math.hypot(fArr[2] - fArr[0], fArr[3] - fArr[1])) / f;
    }

    static float squaredEuclideanDistance(float[] fArr, float[] fArr2) {
        int length = fArr.length;
        float f = 0.0f;
        for (int i = 0; i < length; i++) {
            float f2 = fArr[i] - fArr2[i];
            f += f2 * f2;
        }
        return f / length;
    }

    static float cosineDistance(float[] fArr, float[] fArr2) {
        int length = fArr.length;
        float f = 0.0f;
        for (int i = 0; i < length; i++) {
            f += fArr[i] * fArr2[i];
        }
        return (float) Math.acos(f);
    }

    static float minimumCosineDistance(float[] fArr, float[] fArr2, int i) {
        double acos;
        int length = fArr.length;
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i2 = 0; i2 < length; i2 += 2) {
            float f3 = fArr[i2];
            float f4 = fArr2[i2];
            int i3 = i2 + 1;
            float f5 = fArr[i3];
            float f6 = fArr2[i3];
            f += (f3 * f4) + (f5 * f6);
            f2 += (f3 * f6) - (f5 * f4);
        }
        if (f == 0.0f) {
            return 1.5707964f;
        }
        double d = f2 / f;
        double atan = Math.atan(d);
        if (i > 2 && Math.abs(atan) >= 3.141592653589793d / i) {
            acos = Math.acos(f);
        } else {
            double cos = Math.cos(atan);
            acos = Math.acos((f * cos) + (f2 * d * cos));
        }
        return (float) acos;
    }

    public static OrientedBoundingBox computeOrientedBoundingBox(ArrayList<GesturePoint> arrayList) {
        int size = arrayList.size();
        float[] fArr = new float[size * 2];
        for (int i = 0; i < size; i++) {
            GesturePoint gesturePoint = arrayList.get(i);
            int i2 = i * 2;
            fArr[i2] = gesturePoint.x;
            fArr[i2 + 1] = gesturePoint.y;
        }
        return computeOrientedBoundingBox(fArr, computeCentroid(fArr));
    }

    public static OrientedBoundingBox computeOrientedBoundingBox(float[] fArr) {
        int length = fArr.length;
        float[] fArr2 = new float[length];
        for (int i = 0; i < length; i++) {
            fArr2[i] = fArr[i];
        }
        return computeOrientedBoundingBox(fArr2, computeCentroid(fArr2));
    }

    private static OrientedBoundingBox computeOrientedBoundingBox(float[] fArr, float[] fArr2) {
        float atan2;
        translate(fArr, -fArr2[0], -fArr2[1]);
        float[] computeOrientation = computeOrientation(computeCoVariance(fArr));
        float f = computeOrientation[0];
        if (f == 0.0f && computeOrientation[1] == 0.0f) {
            atan2 = -1.5707964f;
        } else {
            atan2 = (float) Math.atan2(computeOrientation[1], f);
            rotate(fArr, -atan2);
        }
        int length = fArr.length;
        float f2 = Float.MIN_VALUE;
        float f3 = Float.MAX_VALUE;
        float f4 = Float.MAX_VALUE;
        float f5 = Float.MIN_VALUE;
        for (int i = 0; i < length; i += 2) {
            float f6 = fArr[i];
            if (f6 < f3) {
                f3 = f6;
            }
            if (f6 > f2) {
                f2 = f6;
            }
            float f7 = fArr[i + 1];
            if (f7 < f4) {
                f4 = f7;
            }
            if (f7 > f5) {
                f5 = f7;
            }
        }
        return new OrientedBoundingBox((float) ((atan2 * 180.0f) / 3.141592653589793d), fArr2[0], fArr2[1], f2 - f3, f5 - f4);
    }

    private static float[] computeOrientation(float[][] fArr) {
        float[] fArr2 = new float[2];
        float[] fArr3 = fArr[0];
        if (fArr3[1] == 0.0f || fArr[1][0] == 0.0f) {
            fArr2[0] = 1.0f;
            fArr2[1] = 0.0f;
        }
        float f = fArr3[0];
        float f2 = ((-f) - fArr[1][1]) / 2.0f;
        float sqrt = (float) Math.sqrt(Math.pow(f2, 2.0d) - ((f * r9) - (fArr3[1] * r8[0])));
        float f3 = -f2;
        float f4 = f3 + sqrt;
        float f5 = f3 - sqrt;
        if (f4 == f5) {
            fArr2[0] = 0.0f;
            fArr2[1] = 0.0f;
            return fArr2;
        }
        if (f4 <= f5) {
            f4 = f5;
        }
        fArr2[0] = 1.0f;
        float[] fArr4 = fArr[0];
        fArr2[1] = (f4 - fArr4[0]) / fArr4[1];
        return fArr2;
    }

    static float[] rotate(float[] fArr, float f) {
        double d = f;
        float cos = (float) Math.cos(d);
        float sin = (float) Math.sin(d);
        int length = fArr.length;
        for (int i = 0; i < length; i += 2) {
            float f2 = fArr[i];
            int i2 = i + 1;
            float f3 = fArr[i2];
            fArr[i] = (f2 * cos) - (f3 * sin);
            fArr[i2] = (f2 * sin) + (f3 * cos);
        }
        return fArr;
    }

    static float[] translate(float[] fArr, float f, float f2) {
        int length = fArr.length;
        for (int i = 0; i < length; i += 2) {
            fArr[i] = fArr[i] + f;
            int i2 = i + 1;
            fArr[i2] = fArr[i2] + f2;
        }
        return fArr;
    }

    static float[] scale(float[] fArr, float f, float f2) {
        int length = fArr.length;
        for (int i = 0; i < length; i += 2) {
            fArr[i] = fArr[i] * f;
            int i2 = i + 1;
            fArr[i2] = fArr[i2] * f2;
        }
        return fArr;
    }
}
