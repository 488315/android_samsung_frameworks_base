package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;

/* loaded from: classes6.dex */
public class FloatAnimation extends Easing implements Serializable {
    Easing mEasingCurve;
    float[] mSpec;
    private float mDuration = 1.0f;
    private float mWrap = Float.NaN;
    private float mInitialValue = Float.NaN;
    private float mTargetValue = Float.NaN;
    private int mDirectionalSnap = 0;
    float mOffset = 0.0f;
    private boolean mPropagate = false;

    private static float wrap(float f, float f2) {
        float f3 = f2 % f;
        return f3 < 0.0f ? f3 + f : f3;
    }

    float wrapDistance(float f, float f2, float f3) {
        float f4 = (f3 - f2) % 360.0f;
        return f4 < (-f) / 2.0f ? f4 + f : f4 > f / 2.0f ? f4 - f : f4;
    }

    public String toString() {
        String str = "type " + this.mType;
        if (!Float.isNaN(this.mInitialValue)) {
            str = str + " " + this.mInitialValue;
        }
        if (!Float.isNaN(this.mTargetValue)) {
            str = str + " -> " + this.mTargetValue;
        }
        if (Float.isNaN(this.mWrap)) {
            return str;
        }
        return str + "  % " + this.mWrap;
    }

    public FloatAnimation(float... fArr) {
        this.mType = 1;
        setAnimationDescription(fArr);
    }

    public FloatAnimation(int i, float f, float[] fArr, float f2, float f3) {
        this.mType = 1;
        setAnimationDescription(packToFloatArray(f, i, fArr, f2, f3));
    }

    public static float[] packToFloatArray(float f, int i, float[] fArr, float f2, float f3) {
        int length;
        int length2 = !Float.isNaN(f2) ? 1 : 0;
        if (fArr != null) {
            length2++;
        }
        if (fArr != null || i != 1) {
            length2 = length2 + 1 + (fArr == null ? 0 : fArr.length);
        }
        if (!Float.isNaN(f2)) {
            length2++;
        }
        if (!Float.isNaN(f3)) {
            length2++;
        }
        if (f != 1.0f || length2 > 0) {
            length2++;
        }
        if (!Float.isNaN(f3) || !Float.isNaN(f2)) {
            length2++;
        }
        float[] fArr2 = new float[length2];
        int length3 = fArr == null ? 0 : fArr.length;
        if (length2 > 0) {
            fArr2[0] = f;
            length = 1;
        } else {
            length = 0;
        }
        if (length2 > 1) {
            fArr2[length] = Float.intBitsToFloat(i | (((!Float.isNaN(f3) ? 1 : 0) | (Float.isNaN(f2) ? 0 : 2)) << 8) | (length3 << 16));
            length++;
        }
        if (length3 > 0) {
            System.arraycopy(fArr, 0, fArr2, length, fArr.length);
            length += fArr.length;
        }
        if (!Float.isNaN(f2)) {
            fArr2[length] = f2;
            length++;
        }
        if (!Float.isNaN(f3)) {
            fArr2[length] = f3;
        }
        return fArr2;
    }

    public static String unpackAnimationToString(float[] fArr) {
        int i;
        int i2;
        float f;
        float f2;
        int i3;
        String str;
        float f3 = fArr.length == 0 ? 1.0f : fArr[0];
        if (fArr.length > 1) {
            int iFloatToRawIntBits = Float.floatToRawIntBits(fArr[1]);
            int i4 = iFloatToRawIntBits & 255;
            int i5 = iFloatToRawIntBits >> 8;
            boolean z = (i5 & 1) > 0;
            boolean z2 = (i5 & 2) > 0;
            i2 = (iFloatToRawIntBits >> 10) & 3;
            i = ((iFloatToRawIntBits >> 12) & 1) > 0 ? 1 : 0;
            i = (iFloatToRawIntBits >> 16) & 65535;
            int i6 = i + 2;
            if (z2) {
                f2 = fArr[i6];
                i6 = i + 3;
            } else {
                f2 = Float.NaN;
            }
            f = z ? fArr[i6] : Float.NaN;
            i3 = i;
            i = i4;
        } else {
            i = 0;
            i2 = 0;
            f = Float.NaN;
            f2 = Float.NaN;
            i3 = 0;
        }
        switch (i) {
            case 1:
                str = "CUBIC_STANDARD";
                break;
            case 2:
                str = "CUBIC_ACCELERATE";
                break;
            case 3:
                str = "CUBIC_DECELERATE";
                break;
            case 4:
                str = "CUBIC_LINEAR";
                break;
            case 5:
                str = "CUBIC_ANTICIPATE";
                break;
            case 6:
                str = "CUBIC_OVERSHOOT";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            default:
                str = "";
                break;
            case 11:
                str = ((("CUBIC_CUSTOM (" + fArr[2] + " ") + fArr[3] + " ") + fArr[4] + " ") + fArr[5] + " )";
                break;
            case 12:
                String str2 = "SPLINE_CUSTOM (";
                for (int i7 = 2; i7 < 2 + i; i7++) {
                    str2 = str2 + fArr[i7] + " ";
                }
                str = str2 + NavigationBarInflaterView.KEY_CODE_END;
                break;
            case 13:
                str = "EASE_OUT_BOUNCE";
                break;
            case 14:
                str = "EASE_OUT_ELASTIC";
                break;
        }
        String str3 = f3 + " " + str;
        if (!Float.isNaN(f2)) {
            str3 = str3 + " init =" + f2;
        }
        if (!Float.isNaN(f)) {
            str3 = str3 + " wrap =" + f;
        }
        if (i2 != 0) {
            str3 = str3 + " directionalSnap=" + i2;
        }
        if (i3 == 0) {
            return str3;
        }
        return str3 + " propagate";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAnimationDescription(float[] fArr) {
        this.mSpec = fArr;
        int i = 0;
        this.mDuration = fArr.length == 0 ? 1.0f : fArr[0];
        if (fArr.length > 1) {
            int iFloatToRawIntBits = Float.floatToRawIntBits(fArr[1]);
            this.mType = iFloatToRawIntBits & 255;
            int i2 = iFloatToRawIntBits >> 8;
            Object[] objArr = (i2 & 1) > 0;
            Object[] objArr2 = (i2 & 2) > 0;
            int i3 = (iFloatToRawIntBits >> 10) & 3;
            boolean z = ((iFloatToRawIntBits >> 12) & 1) > 0;
            int i4 = (iFloatToRawIntBits >> 16) & 65535;
            int i5 = i4 + 2;
            if (objArr2 != false) {
                this.mInitialValue = this.mSpec[i5];
                i5 = i4 + 3;
            }
            if (objArr != false) {
                this.mWrap = this.mSpec[i5];
            }
            this.mDirectionalSnap = i3;
            this.mPropagate = z;
            i = i4;
        }
        create(this.mType, fArr, 2, i);
    }

    private void create(int i, float[] fArr, int i2, int i3) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                this.mEasingCurve = new CubicEasing(i);
                break;
            case 11:
                this.mEasingCurve = new CubicEasing(fArr[i2], fArr[i2 + 1], fArr[i2 + 2], fArr[i2 + 3]);
                break;
            case 12:
                this.mEasingCurve = new StepCurve(fArr, i2, i3);
                break;
            case 13:
                this.mEasingCurve = new BounceCurve(i);
                break;
            case 14:
                this.mEasingCurve = new ElasticOutCurve();
                break;
        }
    }

    public float getDuration() {
        return this.mDuration;
    }

    public void setInitialValue(float f) {
        if (Float.isNaN(this.mWrap)) {
            this.mInitialValue = f;
        } else {
            this.mInitialValue = f % this.mWrap;
        }
        setScaleOffset();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setTargetValue(float f) {
        int i;
        this.mTargetValue = f;
        if (!Float.isNaN(this.mWrap)) {
            this.mInitialValue = wrap(this.mWrap, this.mInitialValue);
            this.mTargetValue = wrap(this.mWrap, this.mTargetValue);
            if (Float.isNaN(this.mInitialValue)) {
                this.mInitialValue = this.mTargetValue;
            }
            float fWrapDistance = wrapDistance(this.mWrap, this.mInitialValue, this.mTargetValue);
            if (fWrapDistance > 0.0f) {
                float f2 = this.mTargetValue;
                if (f2 < this.mInitialValue) {
                    this.mTargetValue = f2 + this.mWrap;
                } else if (fWrapDistance < 0.0f && (i = this.mDirectionalSnap) != 0) {
                    if (i == 1) {
                        float f3 = this.mTargetValue;
                        if (f3 > this.mInitialValue) {
                            this.mInitialValue = f3;
                        }
                    }
                    if (i == 2) {
                        float f4 = this.mTargetValue;
                        if (f4 < this.mInitialValue) {
                            this.mInitialValue = f4;
                        }
                    }
                    this.mTargetValue -= this.mWrap;
                }
            }
        }
        setScaleOffset();
    }

    public float getTargetValue() {
        return this.mTargetValue;
    }

    private void setScaleOffset() {
        if (!Float.isNaN(this.mInitialValue) && !Float.isNaN(this.mTargetValue)) {
            this.mOffset = this.mInitialValue;
        } else {
            this.mOffset = 0.0f;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float get(float f) {
        int i = this.mDirectionalSnap;
        if (i == 1) {
            float f2 = this.mTargetValue;
            if (f2 < this.mInitialValue) {
                this.mInitialValue = f2;
                return f2;
            }
        }
        if (i == 2) {
            float f3 = this.mTargetValue;
            if (f3 > this.mInitialValue) {
                this.mInitialValue = f3;
                return f3;
            }
        }
        float f4 = this.mEasingCurve.get(f / this.mDuration);
        float f5 = this.mTargetValue;
        float f6 = this.mInitialValue;
        return (f4 * (f5 - f6)) + f6;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float getDiff(float f) {
        return this.mEasingCurve.getDiff(f / this.mDuration) * (this.mTargetValue - this.mInitialValue);
    }

    public boolean isPropagate() {
        return this.mPropagate;
    }

    public float getInitialValue() {
        return this.mInitialValue;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType("FloatAnimation").add("initialValue", Float.valueOf(this.mInitialValue)).add("targetValue", Float.valueOf(this.mTargetValue)).add("duration", Float.valueOf(this.mDuration)).add("easing", Easing.getString(this.mEasingCurve.getType()));
    }
}
