package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

public class FloatAnimation extends Easing {
    Easing mEasingCurve;
    float[] mSpec;
    private int mType = 1;
    private float mDuration = 1.0f;
    private float mWrap = Float.NaN;
    private float mInitialValue = Float.NaN;
    private float mTargetValue = Float.NaN;
    private float mScale = 1.0f;
    float mOffset = 0.0f;

    public String toString() {
        String str = "type " + this.mType;
        if (!Float.isNaN(this.mInitialValue)) {
            str = str + " " + this.mInitialValue;
        }
        if (!Float.isNaN(this.mTargetValue)) {
            str = str + " -> " + this.mTargetValue;
        }
        if (!Float.isNaN(this.mWrap)) {
            return str + "  % " + this.mWrap;
        }
        return str;
    }

    public FloatAnimation() {}

    public FloatAnimation(float... description) {
        setAnimationDescription(description);
    }

    public FloatAnimation(
            int type, float duration, float[] description, float initialValue, float wrap) {
        setAnimationDescription(packToFloatArray(duration, type, description, initialValue, wrap));
    }

    public static float[] packToFloatArray(float f, int i, float[] fArr, float f2, float f3) {
        int i2 = 0;
        if (!Float.isNaN(f2)) {
            i2 = 0 + 1;
        }
        if (fArr != null) {
            i2++;
        }
        if (fArr != null || i != 1) {
            i2 = i2 + 1 + (fArr == null ? 0 : fArr.length);
        }
        if (f != 1.0f || i2 > 0) {
            i2++;
        }
        if (!Float.isNaN(f2)) {
            i2++;
        }
        if (!Float.isNaN(f3)) {
            i2++;
        }
        float[] fArr2 = new float[i2];
        int i3 = 0;
        int length = fArr == null ? 0 : fArr.length;
        if (fArr2.length > 0) {
            fArr2[0] = f;
            i3 = 0 + 1;
        }
        if (fArr2.length > 1) {
            fArr2[i3] =
                    Float.intBitsToFloat(
                            (length << 16)
                                    | (((1 ^ (Float.isNaN(f3) ? 1 : 0)) | (Float.isNaN(f2) ? 0 : 2))
                                            << 8)
                                    | i);
            i3++;
        }
        if (length > 0) {
            System.arraycopy(fArr, 0, fArr2, i3, fArr.length);
            i3 += fArr.length;
        }
        if (!Float.isNaN(f2)) {
            fArr2[i3] = f2;
            i3++;
        }
        if (!Float.isNaN(f3)) {
            fArr2[i3] = f3;
        }
        return fArr2;
    }

    public void setAnimationDescription(float[] description) {
        this.mSpec = description;
        this.mDuration = this.mSpec.length == 0 ? 1.0f : this.mSpec[0];
        int len = 0;
        if (this.mSpec.length > 1) {
            int num_type = Float.floatToRawIntBits(this.mSpec[1]);
            this.mType = num_type & 255;
            boolean wrap = ((num_type >> 8) & 1) > 0;
            boolean init = ((num_type >> 8) & 2) > 0;
            len = (num_type >> 16) & 65535;
            int off = len + 2;
            if (init) {
                this.mInitialValue = this.mSpec[off];
                off++;
            }
            if (wrap) {
                this.mWrap = this.mSpec[off];
            }
        }
        create(this.mType, description, 2, len);
    }

    private void create(int type, float[] params, int offset, int len) {
        switch (type) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                this.mEasingCurve = new CubicEasing(type);
                break;
            case 11:
                this.mEasingCurve =
                        new CubicEasing(
                                params[offset + 0],
                                params[offset + 1],
                                params[offset + 2],
                                params[offset + 3]);
                break;
            case 12:
                this.mEasingCurve = new StepCurve(params, offset, len);
                break;
            case 13:
                this.mEasingCurve = new BounceCurve(type);
                break;
            case 14:
                this.mEasingCurve = new ElasticOutCurve();
                break;
        }
    }

    public float getDuration() {
        return this.mDuration;
    }

    public void setInitialValue(float value) {
        if (Float.isNaN(this.mWrap)) {
            this.mInitialValue = value;
        } else {
            this.mInitialValue = value % this.mWrap;
        }
        setScaleOffset();
    }

    public void setTargetValue(float value) {
        if (Float.isNaN(this.mWrap)) {
            this.mTargetValue = value;
        } else if (Math.abs(((value % this.mWrap) + this.mWrap) - this.mInitialValue)
                < Math.abs((value % this.mWrap) - this.mInitialValue)) {
            this.mTargetValue = (value % this.mWrap) + this.mWrap;
        } else {
            this.mTargetValue = value % this.mWrap;
        }
        setScaleOffset();
    }

    public float getTargetValue() {
        return this.mTargetValue;
    }

    private void setScaleOffset() {
        if (!Float.isNaN(this.mInitialValue) && !Float.isNaN(this.mTargetValue)) {
            this.mScale = this.mTargetValue - this.mInitialValue;
            this.mOffset = this.mInitialValue;
        } else {
            this.mScale = 1.0f;
            this.mOffset = 0.0f;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float get(float t) {
        return (this.mEasingCurve.get(t / this.mDuration)
                        * (this.mTargetValue - this.mInitialValue))
                + this.mInitialValue;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float getDiff(float t) {
        return this.mEasingCurve.getDiff(t / this.mDuration)
                * (this.mTargetValue - this.mInitialValue);
    }

    public float getInitialValue() {
        return this.mInitialValue;
    }
}
