package android.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class ArcMotion extends PathMotion {
    private static final float DEFAULT_MAX_ANGLE_DEGREES = 70.0f;
    private static final float DEFAULT_MAX_TANGENT = (float) Math.tan(Math.toRadians(35.0d));
    private static final float DEFAULT_MIN_ANGLE_DEGREES = 0.0f;
    private float mMaximumAngle;
    private float mMaximumTangent;
    private float mMinimumHorizontalAngle;
    private float mMinimumHorizontalTangent;
    private float mMinimumVerticalAngle;
    private float mMinimumVerticalTangent;

    public ArcMotion() {
        this.mMinimumHorizontalAngle = 0.0f;
        this.mMinimumVerticalAngle = 0.0f;
        this.mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
        this.mMinimumHorizontalTangent = 0.0f;
        this.mMinimumVerticalTangent = 0.0f;
        this.mMaximumTangent = DEFAULT_MAX_TANGENT;
    }

    public ArcMotion(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMinimumHorizontalAngle = 0.0f;
        this.mMinimumVerticalAngle = 0.0f;
        this.mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
        this.mMinimumHorizontalTangent = 0.0f;
        this.mMinimumVerticalTangent = 0.0f;
        this.mMaximumTangent = DEFAULT_MAX_TANGENT;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ArcMotion);
        setMinimumVerticalAngle(typedArrayObtainStyledAttributes.getFloat(1, 0.0f));
        setMinimumHorizontalAngle(typedArrayObtainStyledAttributes.getFloat(0, 0.0f));
        setMaximumAngle(typedArrayObtainStyledAttributes.getFloat(2, DEFAULT_MAX_ANGLE_DEGREES));
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setMinimumHorizontalAngle(float f) {
        this.mMinimumHorizontalAngle = f;
        this.mMinimumHorizontalTangent = toTangent(f);
    }

    public float getMinimumHorizontalAngle() {
        return this.mMinimumHorizontalAngle;
    }

    public void setMinimumVerticalAngle(float f) {
        this.mMinimumVerticalAngle = f;
        this.mMinimumVerticalTangent = toTangent(f);
    }

    public float getMinimumVerticalAngle() {
        return this.mMinimumVerticalAngle;
    }

    public void setMaximumAngle(float f) {
        this.mMaximumAngle = f;
        this.mMaximumTangent = toTangent(f);
    }

    public float getMaximumAngle() {
        return this.mMaximumAngle;
    }

    private static float toTangent(float f) {
        if (f < 0.0f || f > 90.0f) {
            throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
        }
        return (float) Math.tan(Math.toRadians(f / 2.0f));
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0098  */
    @Override // android.transition.PathMotion
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Path getPath(float f, float f2, float f3, float f4) {
        float fAbs;
        float fAbs2;
        float f5;
        float f6;
        float f7;
        float f8;
        Path path = new Path();
        path.moveTo(f, f2);
        float f9 = f3 - f;
        float f10 = f4 - f2;
        float f11 = (f9 * f9) + (f10 * f10);
        float f12 = (f + f3) / 2.0f;
        float f13 = (f2 + f4) / 2.0f;
        float f14 = 0.25f * f11;
        boolean z = f2 > f4;
        if (f10 == 0.0f) {
            fAbs2 = (Math.abs(f9) * 0.5f * this.mMinimumHorizontalTangent) + f13;
            fAbs = f12;
        } else if (f9 == 0.0f) {
            fAbs = (Math.abs(f10) * 0.5f * this.mMinimumVerticalTangent) + f12;
            fAbs2 = f13;
        } else {
            if (Math.abs(f9) < Math.abs(f10)) {
                float fAbs3 = Math.abs(f11 / (f10 * 2.0f));
                if (z) {
                    fAbs2 = f4 + fAbs3;
                    fAbs = f3;
                } else {
                    fAbs2 = fAbs3 + f2;
                    fAbs = f;
                }
                f5 = this.mMinimumVerticalTangent;
            } else {
                float f15 = f11 / (f9 * 2.0f);
                if (z) {
                    fAbs = f + f15;
                    fAbs2 = f2;
                } else {
                    fAbs = f3 - f15;
                    fAbs2 = f4;
                }
                f5 = this.mMinimumHorizontalTangent;
            }
            f6 = f14 * f5 * f5;
            float f16 = f12 - fAbs;
            float f17 = f13 - fAbs2;
            f7 = (f16 * f16) + (f17 * f17);
            float f18 = this.mMaximumTangent;
            f8 = f14 * f18 * f18;
            if (f7 != 0.0f || f7 >= f6) {
                f6 = f7 <= f8 ? f8 : 0.0f;
            }
            if (f6 != 0.0f) {
                float fSqrt = (float) Math.sqrt(f6 / f7);
                fAbs = ((fAbs - f12) * fSqrt) + f12;
                fAbs2 = f13 + (fSqrt * (fAbs2 - f13));
            }
            path.cubicTo((f + fAbs) / 2.0f, (f2 + fAbs2) / 2.0f, (fAbs + f3) / 2.0f, (fAbs2 + f4) / 2.0f, f3, f4);
            return path;
        }
        f6 = 0.0f;
        float f162 = f12 - fAbs;
        float f172 = f13 - fAbs2;
        f7 = (f162 * f162) + (f172 * f172);
        float f182 = this.mMaximumTangent;
        f8 = f14 * f182 * f182;
        if (f7 != 0.0f) {
            if (f7 <= f8) {
            }
        }
        if (f6 != 0.0f) {
        }
        path.cubicTo((f + fAbs) / 2.0f, (f2 + fAbs2) / 2.0f, (fAbs + f3) / 2.0f, (fAbs2 + f4) / 2.0f, f3, f4);
        return path;
    }
}
