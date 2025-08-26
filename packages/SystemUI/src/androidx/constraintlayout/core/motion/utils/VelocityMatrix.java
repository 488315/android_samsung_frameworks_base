package androidx.constraintlayout.core.motion.utils;

/* loaded from: classes.dex */
public class VelocityMatrix {
    public float mDRotate;
    public float mDScaleX;
    public float mDScaleY;
    public float mDTranslateX;
    public float mDTranslateY;
    public float mRotate;

    public final void applyTransform(float f, float f2, int i, int i2, float[] fArr) {
        float f3 = fArr[0];
        float f4 = fArr[1];
        float f5 = (f2 - 0.5f) * 2.0f;
        float f6 = f3 + this.mDTranslateX;
        float f7 = f4 + this.mDTranslateY;
        float f8 = (this.mDScaleX * (f - 0.5f) * 2.0f) + f6;
        float f9 = (this.mDScaleY * f5) + f7;
        float radians = (float) Math.toRadians(this.mRotate);
        float radians2 = (float) Math.toRadians(this.mDRotate);
        double d = radians;
        double d2 = i2 * f5;
        float fSin = (((float) ((Math.sin(d) * ((-i) * r6)) - (Math.cos(d) * d2))) * radians2) + f8;
        float fCos = (radians2 * ((float) ((Math.cos(d) * (i * r6)) - (Math.sin(d) * d2)))) + f9;
        fArr[0] = fSin;
        fArr[1] = fCos;
    }
}
