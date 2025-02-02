package androidx.constraintlayout.motion.widget;

import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.widgets.analyzer.RunGroup$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.LinkedHashMap;

/* loaded from: classes.dex */
final class MotionPaths implements Comparable<MotionPaths> {
    static String[] sNames = {"position", "x", "y", "width", "height", "pathRotate"};
    int mAnimateRelativeTo;
    LinkedHashMap<String, ConstraintAttribute> mAttributes;
    int mDrawPath;
    float mHeight;
    Easing mKeyFrameEasing;
    int mMode;
    int mPathMotionArc;
    float mPathRotate;
    float mPosition;
    float mRelativeAngle;
    MotionController mRelativeToController;
    double[] mTempDelta;
    double[] mTempValue;
    float mTime;
    float mWidth;

    /* renamed from: mX */
    float f11mX;

    /* renamed from: mY */
    float f12mY;

    MotionPaths() {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mPathMotionArc = -1;
        this.mAnimateRelativeTo = -1;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.mAttributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
    }

    private static boolean diff(float f, float f2) {
        return (Float.isNaN(f) || Float.isNaN(f2)) ? Float.isNaN(f) != Float.isNaN(f2) : Math.abs(f - f2) > 1.0E-6f;
    }

    static void setDpDt(float f, float f2, float[] fArr, int[] iArr, double[] dArr, double[] dArr2) {
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        for (int i = 0; i < iArr.length; i++) {
            float f7 = (float) dArr[i];
            double d = dArr2[i];
            int i2 = iArr[i];
            if (i2 == 1) {
                f4 = f7;
            } else if (i2 == 2) {
                f6 = f7;
            } else if (i2 == 3) {
                f3 = f7;
            } else if (i2 == 4) {
                f5 = f7;
            }
        }
        float f8 = f4 - ((0.0f * f3) / 2.0f);
        float f9 = f6 - ((0.0f * f5) / 2.0f);
        fArr[0] = (((f3 * 1.0f) + f8) * f) + ((1.0f - f) * f8) + 0.0f;
        fArr[1] = (((f5 * 1.0f) + f9) * f2) + ((1.0f - f2) * f9) + 0.0f;
    }

    public final void applyParameters(ConstraintSet.Constraint constraint) {
        this.mKeyFrameEasing = Easing.getInterpolator(constraint.motion.mTransitionEasing);
        ConstraintSet.Motion motion = constraint.motion;
        this.mPathMotionArc = motion.mPathMotionArc;
        this.mAnimateRelativeTo = motion.mAnimateRelativeTo;
        this.mPathRotate = motion.mPathRotate;
        this.mDrawPath = motion.mDrawPath;
        int i = motion.mAnimateCircleAngleTo;
        float f = constraint.propertySet.mProgress;
        this.mRelativeAngle = constraint.layout.circleAngle;
        for (String str : constraint.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = constraint.mCustomConstraints.get(str);
            if (constraintAttribute != null && constraintAttribute.isContinuous()) {
                this.mAttributes.put(str, constraintAttribute);
            }
        }
    }

    @Override // java.lang.Comparable
    public final int compareTo(MotionPaths motionPaths) {
        return Float.compare(this.mPosition, motionPaths.mPosition);
    }

    final void different(MotionPaths motionPaths, boolean[] zArr, boolean z) {
        boolean diff = diff(this.f11mX, motionPaths.f11mX);
        boolean diff2 = diff(this.f12mY, motionPaths.f12mY);
        zArr[0] = zArr[0] | diff(this.mPosition, motionPaths.mPosition);
        boolean z2 = z | diff | diff2;
        zArr[1] = zArr[1] | z2;
        zArr[2] = z2 | zArr[2];
        zArr[3] = zArr[3] | diff(this.mWidth, motionPaths.mWidth);
        zArr[4] = diff(this.mHeight, motionPaths.mHeight) | zArr[4];
    }

    final void getCenter(double d, int[] iArr, double[] dArr, float[] fArr, int i) {
        float f = this.f11mX;
        float f2 = this.f12mY;
        float f3 = this.mWidth;
        float f4 = this.mHeight;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f5 = (float) dArr[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                f = f5;
            } else if (i3 == 2) {
                f2 = f5;
            } else if (i3 == 3) {
                f3 = f5;
            } else if (i3 == 4) {
                f4 = f5;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr2 = new float[2];
            motionController.getCenter(d, fArr2, new float[2]);
            float f6 = fArr2[0];
            float f7 = fArr2[1];
            double d2 = f;
            double d3 = f2;
            double sin = Math.sin(d3) * d2;
            f2 = (float) ((f7 - (Math.cos(d3) * d2)) - (f4 / 2.0f));
            f = (float) ((sin + f6) - (f3 / 2.0f));
        }
        fArr[i] = (f3 / 2.0f) + f + 0.0f;
        fArr[i + 1] = (f4 / 2.0f) + f2 + 0.0f;
    }

    final void setBounds(float f, float f2, float f3, float f4) {
        this.f11mX = f;
        this.f12mY = f2;
        this.mWidth = f3;
        this.mHeight = f4;
    }

    public final void setupRelative(MotionController motionController, MotionPaths motionPaths) {
        double d = (((this.mWidth / 2.0f) + this.f11mX) - motionPaths.f11mX) - (motionPaths.mWidth / 2.0f);
        double d2 = (((this.mHeight / 2.0f) + this.f12mY) - motionPaths.f12mY) - (motionPaths.mHeight / 2.0f);
        this.mRelativeToController = motionController;
        this.f11mX = (float) Math.hypot(d2, d);
        if (Float.isNaN(this.mRelativeAngle)) {
            this.f12mY = (float) (Math.atan2(d2, d) + 1.5707963267948966d);
        } else {
            this.f12mY = (float) Math.toRadians(this.mRelativeAngle);
        }
    }

    MotionPaths(int i, int i2, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f;
        int i3;
        float f2;
        int i4;
        float min;
        float f3;
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mPathMotionArc = -1;
        this.mAnimateRelativeTo = -1;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.mAttributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
        if (motionPaths.mAnimateRelativeTo != -1) {
            float f4 = keyPosition.mFramePosition / 100.0f;
            this.mTime = f4;
            this.mDrawPath = keyPosition.mDrawPath;
            this.mMode = keyPosition.mPositionType;
            float f5 = Float.isNaN(keyPosition.mPercentWidth) ? f4 : keyPosition.mPercentWidth;
            float f6 = Float.isNaN(keyPosition.mPercentHeight) ? f4 : keyPosition.mPercentHeight;
            float f7 = motionPaths2.mWidth;
            float f8 = motionPaths.mWidth;
            float f9 = motionPaths2.mHeight;
            float f10 = motionPaths.mHeight;
            this.mPosition = this.mTime;
            this.mWidth = (int) (((f7 - f8) * f5) + f8);
            this.mHeight = (int) (((f9 - f10) * f6) + f10);
            if (keyPosition.mPositionType != 2) {
                float f11 = Float.isNaN(keyPosition.mPercentX) ? f4 : keyPosition.mPercentX;
                float f12 = motionPaths2.f11mX;
                float f13 = motionPaths.f11mX;
                this.f11mX = RunGroup$$ExternalSyntheticOutline0.m3m(f12, f13, f11, f13);
                f4 = Float.isNaN(keyPosition.mPercentY) ? f4 : keyPosition.mPercentY;
                float f14 = motionPaths2.f12mY;
                float f15 = motionPaths.f12mY;
                this.f12mY = RunGroup$$ExternalSyntheticOutline0.m3m(f14, f15, f4, f15);
            } else {
                if (Float.isNaN(keyPosition.mPercentX)) {
                    float f16 = motionPaths2.f11mX;
                    float f17 = motionPaths.f11mX;
                    min = RunGroup$$ExternalSyntheticOutline0.m3m(f16, f17, f4, f17);
                } else {
                    min = keyPosition.mPercentX * Math.min(f6, f5);
                }
                this.f11mX = min;
                if (Float.isNaN(keyPosition.mPercentY)) {
                    float f18 = motionPaths2.f12mY;
                    float f19 = motionPaths.f12mY;
                    f3 = RunGroup$$ExternalSyntheticOutline0.m3m(f18, f19, f4, f19);
                } else {
                    f3 = keyPosition.mPercentY;
                }
                this.f12mY = f3;
            }
            this.mAnimateRelativeTo = motionPaths.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        int i5 = keyPosition.mPositionType;
        if (i5 == 1) {
            float f20 = keyPosition.mFramePosition / 100.0f;
            this.mTime = f20;
            this.mDrawPath = keyPosition.mDrawPath;
            float f21 = Float.isNaN(keyPosition.mPercentWidth) ? f20 : keyPosition.mPercentWidth;
            float f22 = Float.isNaN(keyPosition.mPercentHeight) ? f20 : keyPosition.mPercentHeight;
            float f23 = motionPaths2.mWidth - motionPaths.mWidth;
            float f24 = motionPaths2.mHeight - motionPaths.mHeight;
            this.mPosition = this.mTime;
            f20 = Float.isNaN(keyPosition.mPercentX) ? f20 : keyPosition.mPercentX;
            float f25 = motionPaths.f11mX;
            float f26 = motionPaths.mWidth;
            float f27 = motionPaths.f12mY;
            float f28 = motionPaths.mHeight;
            float f29 = ((motionPaths2.mWidth / 2.0f) + motionPaths2.f11mX) - ((f26 / 2.0f) + f25);
            float f30 = ((motionPaths2.mHeight / 2.0f) + motionPaths2.f12mY) - ((f28 / 2.0f) + f27);
            float f31 = f29 * f20;
            float f32 = (f23 * f21) / 2.0f;
            this.f11mX = (int) ((f25 + f31) - f32);
            float f33 = f20 * f30;
            float f34 = (f24 * f22) / 2.0f;
            this.f12mY = (int) ((f27 + f33) - f34);
            this.mWidth = (int) (f26 + r6);
            this.mHeight = (int) (f28 + r7);
            float f35 = Float.isNaN(keyPosition.mPercentY) ? 0.0f : keyPosition.mPercentY;
            this.mMode = 1;
            float f36 = (int) ((motionPaths.f11mX + f31) - f32);
            float f37 = (int) ((motionPaths.f12mY + f33) - f34);
            this.f11mX = f36 + ((-f30) * f35);
            this.f12mY = f37 + (f29 * f35);
            this.mAnimateRelativeTo = this.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        if (i5 == 2) {
            float f38 = keyPosition.mFramePosition / 100.0f;
            this.mTime = f38;
            this.mDrawPath = keyPosition.mDrawPath;
            float f39 = Float.isNaN(keyPosition.mPercentWidth) ? f38 : keyPosition.mPercentWidth;
            float f40 = Float.isNaN(keyPosition.mPercentHeight) ? f38 : keyPosition.mPercentHeight;
            float f41 = motionPaths2.mWidth;
            float f42 = f41 - motionPaths.mWidth;
            float f43 = motionPaths2.mHeight;
            float f44 = f43 - motionPaths.mHeight;
            this.mPosition = this.mTime;
            float f45 = motionPaths.f11mX;
            float f46 = motionPaths.f12mY;
            float f47 = (f41 / 2.0f) + motionPaths2.f11mX;
            float f48 = (f43 / 2.0f) + motionPaths2.f12mY;
            float f49 = f42 * f39;
            this.f11mX = (int) ((((f47 - ((r7 / 2.0f) + f45)) * f38) + f45) - (f49 / 2.0f));
            float f50 = f44 * f40;
            this.f12mY = (int) ((((f48 - ((r12 / 2.0f) + f46)) * f38) + f46) - (f50 / 2.0f));
            this.mWidth = (int) (r7 + f49);
            this.mHeight = (int) (r12 + f50);
            this.mMode = 2;
            if (!Float.isNaN(keyPosition.mPercentX)) {
                this.f11mX = (int) (keyPosition.mPercentX * (i - ((int) this.mWidth)));
            }
            if (!Float.isNaN(keyPosition.mPercentY)) {
                this.f12mY = (int) (keyPosition.mPercentY * (i2 - ((int) this.mHeight)));
            }
            this.mAnimateRelativeTo = this.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        if (i5 != 3) {
            float f51 = keyPosition.mFramePosition / 100.0f;
            this.mTime = f51;
            this.mDrawPath = keyPosition.mDrawPath;
            float f52 = Float.isNaN(keyPosition.mPercentWidth) ? f51 : keyPosition.mPercentWidth;
            float f53 = Float.isNaN(keyPosition.mPercentHeight) ? f51 : keyPosition.mPercentHeight;
            float f54 = motionPaths2.mWidth;
            float f55 = motionPaths.mWidth;
            float f56 = f54 - f55;
            float f57 = motionPaths2.mHeight;
            float f58 = motionPaths.mHeight;
            float f59 = f57 - f58;
            this.mPosition = this.mTime;
            float f60 = motionPaths.f11mX;
            float f61 = motionPaths.f12mY;
            float f62 = ((f54 / 2.0f) + motionPaths2.f11mX) - ((f55 / 2.0f) + f60);
            float f63 = ((f57 / 2.0f) + motionPaths2.f12mY) - ((f58 / 2.0f) + f61);
            float f64 = (f56 * f52) / 2.0f;
            this.f11mX = (int) (((f62 * f51) + f60) - f64);
            float f65 = (f63 * f51) + f61;
            float f66 = (f59 * f53) / 2.0f;
            this.f12mY = (int) (f65 - f66);
            this.mWidth = (int) (f55 + r10);
            this.mHeight = (int) (f58 + r13);
            float f67 = Float.isNaN(keyPosition.mPercentX) ? f51 : keyPosition.mPercentX;
            float f68 = Float.isNaN(keyPosition.mAltPercentY) ? 0.0f : keyPosition.mAltPercentY;
            f51 = Float.isNaN(keyPosition.mPercentY) ? f51 : keyPosition.mPercentY;
            if (Float.isNaN(keyPosition.mAltPercentX)) {
                i4 = 0;
                f2 = 0.0f;
            } else {
                f2 = keyPosition.mAltPercentX;
                i4 = 0;
            }
            this.mMode = i4;
            this.f11mX = (int) (((f2 * f63) + ((f67 * f62) + motionPaths.f11mX)) - f64);
            this.f12mY = (int) (((f63 * f51) + ((f62 * f68) + motionPaths.f12mY)) - f66);
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        float f69 = keyPosition.mFramePosition / 100.0f;
        this.mTime = f69;
        this.mDrawPath = keyPosition.mDrawPath;
        float f70 = Float.isNaN(keyPosition.mPercentWidth) ? f69 : keyPosition.mPercentWidth;
        float f71 = Float.isNaN(keyPosition.mPercentHeight) ? f69 : keyPosition.mPercentHeight;
        float f72 = motionPaths2.mWidth;
        float f73 = motionPaths.mWidth;
        float f74 = f72 - f73;
        float f75 = motionPaths2.mHeight;
        float f76 = motionPaths.mHeight;
        float f77 = f75 - f76;
        this.mPosition = this.mTime;
        float f78 = (f73 / 2.0f) + motionPaths.f11mX;
        float f79 = motionPaths.f12mY;
        float f80 = (f76 / 2.0f) + f79;
        float f81 = (f72 / 2.0f) + motionPaths2.f11mX;
        float f82 = (f75 / 2.0f) + motionPaths2.f12mY;
        if (f78 <= f81) {
            f78 = f81;
            f81 = f78;
        }
        if (f80 <= f82) {
            f80 = f82;
            f82 = f80;
        }
        float f83 = f78 - f81;
        float f84 = (f74 * f70) / 2.0f;
        this.f11mX = (int) (((f83 * f69) + r14) - f84);
        float f85 = ((f80 - f82) * f69) + f79;
        float f86 = (f77 * f71) / 2.0f;
        this.f12mY = (int) (f85 - f86);
        this.mWidth = (int) (f73 + r9);
        this.mHeight = (int) (f76 + r13);
        float f87 = Float.isNaN(keyPosition.mPercentX) ? f69 : keyPosition.mPercentX;
        float f88 = Float.isNaN(keyPosition.mAltPercentY) ? 0.0f : keyPosition.mAltPercentY;
        f69 = Float.isNaN(keyPosition.mPercentY) ? f69 : keyPosition.mPercentY;
        if (Float.isNaN(keyPosition.mAltPercentX)) {
            i3 = 0;
            f = 0.0f;
        } else {
            f = keyPosition.mAltPercentX;
            i3 = 0;
        }
        this.mMode = i3;
        this.f11mX = (int) (((f * r16) + ((f87 * f83) + motionPaths.f11mX)) - f84);
        this.f12mY = (int) (((r16 * f69) + ((f83 * f88) + motionPaths.f12mY)) - f86);
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }
}
