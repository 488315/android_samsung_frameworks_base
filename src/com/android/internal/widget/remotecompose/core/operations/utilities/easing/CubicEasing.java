package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

/* loaded from: classes6.dex */
class CubicEasing extends Easing {
    private static final float D_ERROR = 1.0E-4f;
    private static final float ERROR = 0.01f;
    private static final float[] STANDARD = {0.4f, 0.0f, 0.2f, 1.0f};
    private static final float[] ACCELERATE = {0.4f, 0.05f, 0.8f, 0.7f};
    private static final float[] DECELERATE = {0.0f, 0.0f, 0.2f, 0.95f};
    private static final float[] LINEAR = {1.0f, 1.0f, 0.0f, 0.0f};
    private static final float[] ANTICIPATE = {0.36f, 0.0f, 0.66f, -0.56f};
    private static final float[] OVERSHOOT = {0.34f, 1.56f, 0.64f, 1.0f};
    float mX1 = 0.0f;
    float mY1 = 0.0f;
    float mX2 = 0.0f;
    float mY2 = 0.0f;

    CubicEasing(int i) {
        this.mType = i;
        config(i);
    }

    CubicEasing(float f, float f2, float f3, float f4) {
        setup(f, f2, f3, f4);
    }

    public void config(int i) {
        switch (i) {
            case 1:
                setup(STANDARD);
                break;
            case 2:
                setup(ACCELERATE);
                break;
            case 3:
                setup(DECELERATE);
                break;
            case 4:
                setup(LINEAR);
                break;
            case 5:
                setup(ANTICIPATE);
                break;
            case 6:
                setup(OVERSHOOT);
                break;
        }
        this.mType = i;
    }

    void setup(float[] fArr) {
        setup(fArr[0], fArr[1], fArr[2], fArr[3]);
    }

    void setup(float f, float f2, float f3, float f4) {
        this.mX1 = f;
        this.mY1 = f2;
        this.mX2 = f3;
        this.mY2 = f4;
    }

    private float getX(float f) {
        float f2 = 1.0f - f;
        float f3 = 3.0f * f2;
        return (this.mX1 * f2 * f3 * f) + (this.mX2 * f3 * f * f) + (f * f * f);
    }

    private float getY(float f) {
        float f2 = 1.0f - f;
        float f3 = 3.0f * f2;
        return (this.mY1 * f2 * f3 * f) + (this.mY2 * f3 * f * f) + (f * f * f);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float getDiff(float f) {
        float f2 = 0.5f;
        float f3 = 0.5f;
        while (f2 > 1.0E-4f) {
            f2 *= 0.5f;
            f3 = getX(f3) < f ? f3 + f2 : f3 - f2;
        }
        float f4 = f3 - f2;
        float f5 = f3 + f2;
        return (getY(f5) - getY(f4)) / (getX(f5) - getX(f4));
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float get(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        float f2 = 0.5f;
        float f3 = 0.5f;
        while (f2 > 0.01f) {
            f2 *= 0.5f;
            f3 = getX(f3) < f ? f3 + f2 : f3 - f2;
        }
        float f4 = f3 - f2;
        float x = getX(f4);
        float f5 = f3 + f2;
        float x2 = getX(f5);
        float y = getY(f4);
        return (((getY(f5) - y) * (f - x)) / (x2 - x)) + y;
    }
}
