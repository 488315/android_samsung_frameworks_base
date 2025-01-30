package androidx.constraintlayout.motion.widget;

import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.LinkedHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MotionPaths implements Comparable {
    public static final String[] names = {"position", "x", "y", "width", "height", "pathRotate"};
    public final LinkedHashMap attributes;
    public float height;
    public int mAnimateRelativeTo;
    public int mDrawPath;
    public Easing mKeyFrameEasing;
    public int mMode;
    public int mPathMotionArc;
    public float mPathRotate;
    public float mRelativeAngle;
    public MotionController mRelativeToController;
    public double[] mTempDelta;
    public double[] mTempValue;
    public float position;
    public float time;
    public float width;

    /* renamed from: x */
    public float f27x;

    /* renamed from: y */
    public float f28y;

    public MotionPaths() {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mPathMotionArc = -1;
        this.mAnimateRelativeTo = -1;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.attributes = new LinkedHashMap();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
    }

    public static boolean diff(float f, float f2) {
        return (Float.isNaN(f) || Float.isNaN(f2)) ? Float.isNaN(f) != Float.isNaN(f2) : Math.abs(f - f2) > 1.0E-6f;
    }

    public static void setDpDt(float f, float f2, float[] fArr, int[] iArr, double[] dArr, double[] dArr2) {
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
        fArr[0] = MotionPaths$$ExternalSyntheticOutline0.m24m((f3 * 1.0f) + f8, f, (1.0f - f) * f8, 0.0f);
        fArr[1] = MotionPaths$$ExternalSyntheticOutline0.m24m((f5 * 1.0f) + f9, f2, (1.0f - f2) * f9, 0.0f);
    }

    public final void applyParameters(ConstraintSet.Constraint constraint) {
        this.mKeyFrameEasing = Easing.getInterpolator(constraint.motion.mTransitionEasing);
        ConstraintSet.Motion motion = constraint.motion;
        this.mPathMotionArc = motion.mPathMotionArc;
        this.mAnimateRelativeTo = motion.mAnimateRelativeTo;
        this.mPathRotate = motion.mPathRotate;
        this.mDrawPath = motion.mDrawPath;
        float f = constraint.propertySet.mProgress;
        this.mRelativeAngle = constraint.layout.circleAngle;
        for (String str : constraint.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = (ConstraintAttribute) constraint.mCustomConstraints.get(str);
            if (constraintAttribute != null) {
                int i = ConstraintAttribute.AbstractC01401.f30x66adad53[constraintAttribute.mType.ordinal()];
                if ((i == 1 || i == 2 || i == 3) ? false : true) {
                    this.attributes.put(str, constraintAttribute);
                }
            }
        }
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return Float.compare(this.position, ((MotionPaths) obj).position);
    }

    public final void getCenter(double d, int[] iArr, double[] dArr, float[] fArr, int i) {
        float f = this.f27x;
        float f2 = this.f28y;
        float f3 = this.width;
        float f4 = this.height;
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

    public final void setBounds(float f, float f2, float f3, float f4) {
        this.f27x = f;
        this.f28y = f2;
        this.width = f3;
        this.height = f4;
    }

    public final void setupRelative(MotionController motionController, MotionPaths motionPaths) {
        double d = (((this.width / 2.0f) + this.f27x) - motionPaths.f27x) - (motionPaths.width / 2.0f);
        double d2 = (((this.height / 2.0f) + this.f28y) - motionPaths.f28y) - (motionPaths.height / 2.0f);
        this.mRelativeToController = motionController;
        this.f27x = (float) Math.hypot(d2, d);
        if (Float.isNaN(this.mRelativeAngle)) {
            this.f28y = (float) (Math.atan2(d2, d) + 1.5707963267948966d);
        } else {
            this.f28y = (float) Math.toRadians(this.mRelativeAngle);
        }
    }

    public MotionPaths(int i, int i2, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f;
        int i3;
        float min;
        float f2;
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mPathMotionArc = -1;
        this.mAnimateRelativeTo = -1;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.attributes = new LinkedHashMap();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
        if (motionPaths.mAnimateRelativeTo != -1) {
            float f3 = keyPosition.mFramePosition / 100.0f;
            this.time = f3;
            this.mDrawPath = keyPosition.mDrawPath;
            this.mMode = keyPosition.mPositionType;
            float f4 = Float.isNaN(keyPosition.mPercentWidth) ? f3 : keyPosition.mPercentWidth;
            float f5 = Float.isNaN(keyPosition.mPercentHeight) ? f3 : keyPosition.mPercentHeight;
            float f6 = motionPaths2.width;
            float f7 = motionPaths.width;
            float f8 = motionPaths2.height;
            float f9 = motionPaths.height;
            this.position = this.time;
            this.width = (int) (((f6 - f7) * f4) + f7);
            this.height = (int) (((f8 - f9) * f5) + f9);
            int i4 = keyPosition.mPositionType;
            if (i4 == 1) {
                float f10 = Float.isNaN(keyPosition.mPercentX) ? f3 : keyPosition.mPercentX;
                float f11 = motionPaths2.f27x;
                float f12 = motionPaths.f27x;
                this.f27x = DependencyGraph$$ExternalSyntheticOutline0.m20m(f11, f12, f10, f12);
                f3 = Float.isNaN(keyPosition.mPercentY) ? f3 : keyPosition.mPercentY;
                float f13 = motionPaths2.f28y;
                float f14 = motionPaths.f28y;
                this.f28y = DependencyGraph$$ExternalSyntheticOutline0.m20m(f13, f14, f3, f14);
            } else if (i4 != 2) {
                float f15 = Float.isNaN(keyPosition.mPercentX) ? f3 : keyPosition.mPercentX;
                float f16 = motionPaths2.f27x;
                float f17 = motionPaths.f27x;
                this.f27x = DependencyGraph$$ExternalSyntheticOutline0.m20m(f16, f17, f15, f17);
                f3 = Float.isNaN(keyPosition.mPercentY) ? f3 : keyPosition.mPercentY;
                float f18 = motionPaths2.f28y;
                float f19 = motionPaths.f28y;
                this.f28y = DependencyGraph$$ExternalSyntheticOutline0.m20m(f18, f19, f3, f19);
            } else {
                if (Float.isNaN(keyPosition.mPercentX)) {
                    float f20 = motionPaths2.f27x;
                    float f21 = motionPaths.f27x;
                    min = DependencyGraph$$ExternalSyntheticOutline0.m20m(f20, f21, f3, f21);
                } else {
                    min = keyPosition.mPercentX * Math.min(f5, f4);
                }
                this.f27x = min;
                if (Float.isNaN(keyPosition.mPercentY)) {
                    float f22 = motionPaths2.f28y;
                    float f23 = motionPaths.f28y;
                    f2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(f22, f23, f3, f23);
                } else {
                    f2 = keyPosition.mPercentY;
                }
                this.f28y = f2;
            }
            this.mAnimateRelativeTo = motionPaths.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        int i5 = keyPosition.mPositionType;
        if (i5 == 1) {
            float f24 = keyPosition.mFramePosition / 100.0f;
            this.time = f24;
            this.mDrawPath = keyPosition.mDrawPath;
            float f25 = Float.isNaN(keyPosition.mPercentWidth) ? f24 : keyPosition.mPercentWidth;
            float f26 = Float.isNaN(keyPosition.mPercentHeight) ? f24 : keyPosition.mPercentHeight;
            float f27 = motionPaths2.width - motionPaths.width;
            float f28 = motionPaths2.height - motionPaths.height;
            this.position = this.time;
            f24 = Float.isNaN(keyPosition.mPercentX) ? f24 : keyPosition.mPercentX;
            float f29 = motionPaths.f27x;
            float f30 = motionPaths.width;
            float f31 = motionPaths.f28y;
            float f32 = motionPaths.height;
            float f33 = ((motionPaths2.width / 2.0f) + motionPaths2.f27x) - ((f30 / 2.0f) + f29);
            float f34 = ((motionPaths2.height / 2.0f) + motionPaths2.f28y) - ((f32 / 2.0f) + f31);
            float f35 = f33 * f24;
            float f36 = (f27 * f25) / 2.0f;
            this.f27x = (int) ((f29 + f35) - f36);
            float f37 = f24 * f34;
            float f38 = (f28 * f26) / 2.0f;
            this.f28y = (int) ((f31 + f37) - f38);
            this.width = (int) (f30 + r8);
            this.height = (int) (f32 + r9);
            float f39 = Float.isNaN(keyPosition.mPercentY) ? 0.0f : keyPosition.mPercentY;
            this.mMode = 1;
            float f40 = (int) ((motionPaths.f27x + f35) - f36);
            float f41 = (int) ((motionPaths.f28y + f37) - f38);
            this.f27x = f40 + ((-f34) * f39);
            this.f28y = f41 + (f33 * f39);
            this.mAnimateRelativeTo = this.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        if (i5 != 2) {
            float f42 = keyPosition.mFramePosition / 100.0f;
            this.time = f42;
            this.mDrawPath = keyPosition.mDrawPath;
            float f43 = Float.isNaN(keyPosition.mPercentWidth) ? f42 : keyPosition.mPercentWidth;
            float f44 = Float.isNaN(keyPosition.mPercentHeight) ? f42 : keyPosition.mPercentHeight;
            float f45 = motionPaths2.width;
            float f46 = motionPaths.width;
            float f47 = f45 - f46;
            float f48 = motionPaths2.height;
            float f49 = motionPaths.height;
            float f50 = f48 - f49;
            this.position = this.time;
            float f51 = motionPaths.f27x;
            float f52 = motionPaths.f28y;
            float f53 = ((f45 / 2.0f) + motionPaths2.f27x) - ((f46 / 2.0f) + f51);
            float f54 = ((f48 / 2.0f) + motionPaths2.f28y) - ((f49 / 2.0f) + f52);
            float f55 = (f47 * f43) / 2.0f;
            this.f27x = (int) (((f53 * f42) + f51) - f55);
            float f56 = (f54 * f42) + f52;
            float f57 = (f50 * f44) / 2.0f;
            this.f28y = (int) (f56 - f57);
            this.width = (int) (f46 + r10);
            this.height = (int) (f49 + r13);
            float f58 = Float.isNaN(keyPosition.mPercentX) ? f42 : keyPosition.mPercentX;
            float f59 = Float.isNaN(keyPosition.mAltPercentY) ? 0.0f : keyPosition.mAltPercentY;
            f42 = Float.isNaN(keyPosition.mPercentY) ? f42 : keyPosition.mPercentY;
            if (Float.isNaN(keyPosition.mAltPercentX)) {
                i3 = 0;
                f = 0.0f;
            } else {
                f = keyPosition.mAltPercentX;
                i3 = 0;
            }
            this.mMode = i3;
            this.f27x = (int) (((f * f54) + ((f58 * f53) + motionPaths.f27x)) - f55);
            this.f28y = (int) (((f54 * f42) + ((f53 * f59) + motionPaths.f28y)) - f57);
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        float f60 = keyPosition.mFramePosition / 100.0f;
        this.time = f60;
        this.mDrawPath = keyPosition.mDrawPath;
        float f61 = Float.isNaN(keyPosition.mPercentWidth) ? f60 : keyPosition.mPercentWidth;
        float f62 = Float.isNaN(keyPosition.mPercentHeight) ? f60 : keyPosition.mPercentHeight;
        float f63 = motionPaths2.width;
        float f64 = f63 - motionPaths.width;
        float f65 = motionPaths2.height;
        float f66 = f65 - motionPaths.height;
        this.position = this.time;
        float f67 = motionPaths.f27x;
        float f68 = motionPaths.f28y;
        float f69 = (f63 / 2.0f) + motionPaths2.f27x;
        float f70 = (f65 / 2.0f) + motionPaths2.f28y;
        float f71 = f64 * f61;
        this.f27x = (int) ((((f69 - ((r9 / 2.0f) + f67)) * f60) + f67) - (f71 / 2.0f));
        float f72 = f66 * f62;
        this.f28y = (int) ((((f70 - ((r12 / 2.0f) + f68)) * f60) + f68) - (f72 / 2.0f));
        this.width = (int) (r9 + f71);
        this.height = (int) (r12 + f72);
        this.mMode = 2;
        if (!Float.isNaN(keyPosition.mPercentX)) {
            this.f27x = (int) (keyPosition.mPercentX * ((int) (i - this.width)));
        }
        if (!Float.isNaN(keyPosition.mPercentY)) {
            this.f28y = (int) (keyPosition.mPercentY * ((int) (i2 - this.height)));
        }
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }
}
