package android.gesture;

import android.graphics.RectF;
import android.util.Log;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static float[] spatialSampling(Gesture gesture, int i, boolean z) {
        float f;
        float[] fArr;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6 = i - 1;
        float[] fArr2 = new float[i * i];
        float f7 = 0.0f;
        Arrays.fill(fArr2, 0.0f);
        RectF boundingBox = gesture.getBoundingBox();
        float fWidth = boundingBox.width();
        float fHeight = boundingBox.height();
        float f8 = f6 / fWidth;
        float f9 = f6 / fHeight;
        float f10 = 1.0f;
        if (z) {
            if (f8 >= f9) {
                f8 = f9;
            }
            f9 = f8;
        } else {
            float f11 = fWidth / fHeight;
            if (f11 > 1.0f) {
                f11 = 1.0f / f11;
            }
            if (f11 < SCALING_THRESHOLD) {
                if (f8 >= f9) {
                }
                f9 = f8;
            } else if (f8 > f9) {
                float f12 = NONUNIFORM_SCALE * f9;
                if (f12 < f8) {
                    f8 = f12;
                }
            } else {
                float f13 = NONUNIFORM_SCALE * f8;
                if (f13 < f9) {
                    f9 = f13;
                }
            }
        }
        float f14 = -boundingBox.centerX();
        float f15 = -boundingBox.centerY();
        float f16 = f6 / 2.0f;
        ArrayList<GestureStroke> strokes = gesture.getStrokes();
        int size = strokes.size();
        int i2 = 0;
        while (i2 < size) {
            float[] fArr3 = strokes.get(i2).points;
            int length = fArr3.length;
            float f17 = f7;
            float[] fArr4 = new float[length];
            float f18 = f10;
            for (int i3 = 0; i3 < length; i3 += 2) {
                fArr4[i3] = ((fArr3[i3] + f14) * f8) + f16;
                int i4 = i3 + 1;
                fArr4[i4] = ((fArr3[i4] + f15) * f9) + f16;
            }
            float f19 = -1.0f;
            float f20 = -1.0f;
            int i5 = 0;
            while (i5 < length) {
                float f21 = fArr4[i5];
                if (f21 < f17) {
                    f21 = f17;
                }
                float f22 = fArr4[i5 + 1];
                if (f22 < f17) {
                    f22 = f17;
                }
                if (f21 > f6) {
                    f = f6;
                } else {
                    f = f6;
                    f6 = f21;
                }
                if (f22 > f) {
                    fArr = fArr4;
                    f2 = f;
                } else {
                    fArr = fArr4;
                    f2 = f22;
                }
                plot(f6, f2, fArr2, i);
                if (f19 != -1.0f) {
                    if (f19 > f6) {
                        f4 = f15;
                        f5 = f14;
                        float fCeil = (float) Math.ceil(f6);
                        float f23 = (f20 - f2) / (f19 - f6);
                        while (fCeil < f19) {
                            plot(fCeil, ((fCeil - f6) * f23) + f2, fArr2, i);
                            fCeil += f18;
                            f6 = f6;
                        }
                        f3 = f6;
                    } else {
                        f3 = f6;
                        f4 = f15;
                        f5 = f14;
                        if (f19 < f3) {
                            float f24 = (f20 - f2) / (f19 - f3);
                            for (float fCeil2 = (float) Math.ceil(f19); fCeil2 < f3; fCeil2 += f18) {
                                plot(fCeil2, ((fCeil2 - f3) * f24) + f2, fArr2, i);
                            }
                        }
                    }
                    if (f20 > f2) {
                        float f25 = (f19 - f3) / (f20 - f2);
                        for (float fCeil3 = (float) Math.ceil(f2); fCeil3 < f20; fCeil3 += f18) {
                            plot(((fCeil3 - f2) * f25) + f3, fCeil3, fArr2, i);
                        }
                    } else if (f20 < f2) {
                        float f26 = (f19 - f3) / (f20 - f2);
                        for (float fCeil4 = (float) Math.ceil(f20); fCeil4 < f2; fCeil4 += f18) {
                            plot(((fCeil4 - f2) * f26) + f3, fCeil4, fArr2, i);
                        }
                    }
                } else {
                    f3 = f6;
                    f4 = f15;
                    f5 = f14;
                }
                i5 += 2;
                f20 = f2;
                fArr4 = fArr;
                f14 = f5;
                f6 = f;
                f15 = f4;
                f19 = f3;
            }
            i2++;
            f7 = f17;
            f10 = f18;
        }
        return fArr2;
    }

    private static void plot(float f, float f2, float[] fArr, int i) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        double d = f;
        int iFloor = (int) Math.floor(d);
        int iCeil = (int) Math.ceil(d);
        double d2 = f2;
        int iFloor2 = (int) Math.floor(d2);
        int iCeil2 = (int) Math.ceil(d2);
        if (f == iFloor && f2 == iFloor2) {
            int i2 = (iCeil2 * i) + iCeil;
            if (fArr[i2] < 1.0f) {
                fArr[i2] = 1.0f;
                return;
            }
            return;
        }
        double dPow = Math.pow(r4 - f, 2.0d);
        double dPow2 = Math.pow(iFloor2 - f2, 2.0d);
        double dPow3 = Math.pow(iCeil - f, 2.0d);
        double dPow4 = Math.pow(iCeil2 - f2, 2.0d);
        float fSqrt = (float) Math.sqrt(dPow + dPow2);
        float fSqrt2 = (float) Math.sqrt(dPow2 + dPow3);
        float fSqrt3 = (float) Math.sqrt(dPow + dPow4);
        float fSqrt4 = (float) Math.sqrt(dPow3 + dPow4);
        float f3 = fSqrt + fSqrt2 + fSqrt3 + fSqrt4;
        float f4 = fSqrt / f3;
        int i3 = iFloor2 * i;
        int i4 = i3 + iFloor;
        if (f4 > fArr[i4]) {
            fArr[i4] = f4;
        }
        float f5 = fSqrt2 / f3;
        int i5 = i3 + iCeil;
        if (f5 > fArr[i5]) {
            fArr[i5] = f5;
        }
        float f6 = fSqrt3 / f3;
        int i6 = iCeil2 * i;
        int i7 = iFloor + i6;
        if (f6 > fArr[i7]) {
            fArr[i7] = f6;
        }
        float f7 = fSqrt4 / f3;
        int i8 = i6 + iCeil;
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
            float fHypot = (float) Math.hypot(f9, f10);
            float f12 = f7 + fHypot;
            if (f12 >= f11) {
                float f13 = (f11 - f7) / fHypot;
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
        float fHypot = 0.0f;
        for (int i = 0; i < length; i += 2) {
            fHypot = (float) (fHypot + Math.hypot(fArr[r3] - fArr[i], fArr[i + 3] - fArr[i + 1]));
        }
        return fHypot;
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
        double dAcos;
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
        double dAtan = Math.atan(d);
        if (i > 2 && Math.abs(dAtan) >= 3.141592653589793d / i) {
            dAcos = Math.acos(f);
        } else {
            double dCos = Math.cos(dAtan);
            dAcos = Math.acos((f * dCos) + (f2 * d * dCos));
        }
        return (float) dAcos;
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
        float fAtan2;
        translate(fArr, -fArr2[0], -fArr2[1]);
        float[] fArrComputeOrientation = computeOrientation(computeCoVariance(fArr));
        float f = fArrComputeOrientation[0];
        if (f == 0.0f && fArrComputeOrientation[1] == 0.0f) {
            fAtan2 = -1.5707964f;
        } else {
            fAtan2 = (float) Math.atan2(fArrComputeOrientation[1], f);
            rotate(fArr, -fAtan2);
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
        return new OrientedBoundingBox((float) ((fAtan2 * 180.0f) / 3.141592653589793d), fArr2[0], fArr2[1], f2 - f3, f5 - f4);
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
        float fSqrt = (float) Math.sqrt(Math.pow(f2, 2.0d) - ((f * r9) - (fArr3[1] * r8[0])));
        float f3 = -f2;
        float f4 = f3 + fSqrt;
        float f5 = f3 - fSqrt;
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
        float fCos = (float) Math.cos(d);
        float fSin = (float) Math.sin(d);
        int length = fArr.length;
        for (int i = 0; i < length; i += 2) {
            float f2 = fArr[i];
            int i2 = i + 1;
            float f3 = fArr[i2];
            fArr[i] = (f2 * fCos) - (f3 * fSin);
            fArr[i2] = (f2 * fSin) + (f3 * fCos);
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
