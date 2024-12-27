package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

/* loaded from: classes5.dex */
class CubicEasing extends Easing {
    private static final float D_ERROR = 1.0E-4f;
    private static final float ERROR = 0.01f;
    float mType;
    float mX1;
    float mX2;
    float mY1;
    float mY2;
    private static final float[] STANDARD = {0.4f, 0.0f, 0.2f, 1.0f};
    private static final float[] ACCELERATE = {0.4f, 0.05f, 0.8f, 0.7f};
    private static final float[] DECELERATE = {0.0f, 0.0f, 0.2f, 0.95f};
    private static final float[] LINEAR = {1.0f, 1.0f, 0.0f, 0.0f};
    private static final float[] ANTICIPATE = {0.36f, 0.0f, 0.66f, -0.56f};
    private static final float[] OVERSHOOT = {0.34f, 1.56f, 0.64f, 1.0f};

    CubicEasing(int type) {
        this.mType = 0.0f;
        this.mX1 = 0.0f;
        this.mY1 = 0.0f;
        this.mX2 = 0.0f;
        this.mY2 = 0.0f;
        this.mType = type;
        config(type);
    }

    CubicEasing(float x1, float y1, float x2, float y2) {
        this.mType = 0.0f;
        this.mX1 = 0.0f;
        this.mY1 = 0.0f;
        this.mX2 = 0.0f;
        this.mY2 = 0.0f;
        setup(x1, y1, x2, y2);
    }

    public void config(int type) {
        switch (type) {
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
        this.mType = type;
    }

    void setup(float[] values) {
        setup(values[0], values[1], values[2], values[3]);
    }

    void setup(float x1, float y1, float x2, float y2) {
        this.mX1 = x1;
        this.mY1 = y1;
        this.mX2 = x2;
        this.mY2 = y2;
    }

    private float getX(float t) {
        float t1 = 1.0f - t;
        float f1 = t1 * 3.0f * t1 * t;
        float f2 = 3.0f * t1 * t * t;
        float f3 = t * t * t;
        return (this.mX1 * f1) + (this.mX2 * f2) + f3;
    }

    private float getY(float t) {
        float t1 = 1.0f - t;
        float f1 = t1 * 3.0f * t1 * t;
        float f2 = 3.0f * t1 * t * t;
        float f3 = t * t * t;
        return (this.mY1 * f1) + (this.mY2 * f2) + f3;
    }

    private float getDiffX(float t) {
        float t1 = 1.0f - t;
        return (t1 * 3.0f * t1 * this.mX1)
                + (6.0f * t1 * t * (this.mX2 - this.mX1))
                + (3.0f * t * t * (1.0f - this.mX2));
    }

    private float getDiffY(float t) {
        float t1 = 1.0f - t;
        return (t1 * 3.0f * t1 * this.mY1)
                + (6.0f * t1 * t * (this.mY2 - this.mY1))
                + (3.0f * t * t * (1.0f - this.mY2));
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float getDiff(float x) {
        float t = 0.5f;
        float range = 0.5f;
        while (range > 1.0E-4f) {
            float tx = getX(t);
            range = (float) (range * 0.5d);
            if (tx < x) {
                t += range;
            } else {
                t -= range;
            }
        }
        float x1 = getX(t - range);
        float x2 = getX(t + range);
        float y1 = getY(t - range);
        float y2 = getY(t + range);
        return (y2 - y1) / (x2 - x1);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float get(float x) {
        if (x <= 0.0f) {
            return 0.0f;
        }
        if (x >= 1.0f) {
            return 1.0f;
        }
        float t = 0.5f;
        float range = 0.5f;
        while (range > 0.01f) {
            float tx = getX(t);
            range *= 0.5f;
            if (tx < x) {
                t += range;
            } else {
                t -= range;
            }
        }
        float x1 = getX(t - range);
        float x2 = getX(t + range);
        float y1 = getY(t - range);
        float y2 = getY(t + range);
        return (((y2 - y1) * (x - x1)) / (x2 - x1)) + y1;
    }
}
