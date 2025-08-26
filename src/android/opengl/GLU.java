package android.opengl;

import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes3.dex */
public class GLU {
    private static final float[] sScratch = new float[32];

    public static String gluErrorString(int i) {
        if (i == 0) {
            return "no error";
        }
        switch (i) {
            case 1280:
                return "invalid enum";
            case 1281:
                return "invalid value";
            case 1282:
                return "invalid operation";
            case 1283:
                return "stack overflow";
            case 1284:
                return "stack underflow";
            case 1285:
                return "out of memory";
            default:
                return null;
        }
    }

    public static void gluLookAt(GL10 gl10, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        float[] fArr = sScratch;
        synchronized (fArr) {
            Matrix.setLookAtM(fArr, 0, f, f2, f3, f4, f5, f6, f7, f8, f9);
            gl10.glMultMatrixf(fArr, 0);
        }
    }

    public static void gluOrtho2D(GL10 gl10, float f, float f2, float f3, float f4) {
        gl10.glOrthof(f, f2, f3, f4, -1.0f, 1.0f);
    }

    public static void gluPerspective(GL10 gl10, float f, float f2, float f3, float f4) {
        float fTan = f3 * ((float) Math.tan(f * 0.008726646259971648d));
        float f5 = -fTan;
        gl10.glFrustumf(f5 * f2, fTan * f2, f5, fTan, f3, f4);
    }

    public static int gluProject(float f, float f2, float f3, float[] fArr, int i, float[] fArr2, int i2, int[] iArr, int i3, float[] fArr3, int i4) throws Throwable {
        float[] fArr4 = sScratch;
        synchronized (fArr4) {
            try {
                Matrix.multiplyMM(fArr4, 0, fArr2, i2, fArr, i);
                fArr4[16] = f;
                fArr4[17] = f2;
                fArr4[18] = f3;
                fArr4[19] = 1.0f;
                try {
                    Matrix.multiplyMV(fArr4, 20, fArr4, 0, fArr4, 16);
                    float f4 = fArr4[23];
                    if (f4 == 0.0f) {
                        return 0;
                    }
                    float f5 = 1.0f / f4;
                    fArr3[i4] = iArr[i3] + (iArr[i3 + 2] * ((fArr4[20] * f5) + 1.0f) * 0.5f);
                    fArr3[i4 + 1] = iArr[i3 + 1] + (iArr[i3 + 3] * ((fArr4[21] * f5) + 1.0f) * 0.5f);
                    fArr3[i4 + 2] = ((fArr4[22] * f5) + 1.0f) * 0.5f;
                    return 1;
                } catch (Throwable th) {
                    th = th;
                    fArr4 = fArr4;
                    Throwable th2 = th;
                    throw th2;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
    }

    public static int gluUnProject(float f, float f2, float f3, float[] fArr, int i, float[] fArr2, int i2, int[] iArr, int i3, float[] fArr3, int i4) throws Throwable {
        float[] fArr4 = sScratch;
        synchronized (fArr4) {
            try {
                Matrix.multiplyMM(fArr4, 0, fArr2, i2, fArr, i);
                if (!Matrix.invertM(fArr4, 16, fArr4, 0)) {
                    return 0;
                }
                fArr4[0] = (((f - iArr[i3]) * 2.0f) / iArr[i3 + 2]) - 1.0f;
                fArr4[1] = (((f2 - iArr[i3 + 1]) * 2.0f) / iArr[i3 + 3]) - 1.0f;
                fArr4[2] = (f3 * 2.0f) - 1.0f;
                fArr4[3] = 1.0f;
                try {
                    Matrix.multiplyMV(fArr3, i4, fArr4, 16, fArr4, 0);
                    return 1;
                } catch (Throwable th) {
                    th = th;
                    fArr4 = fArr4;
                    Throwable th2 = th;
                    throw th2;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
    }
}
