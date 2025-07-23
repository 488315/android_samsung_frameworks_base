package androidx.constraintlayout.motion.widget;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.LinkedHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MotionPaths implements Comparable {
    public static final String[] sNames = {SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_POSITION, "x", "y", "width", "height", "pathRotate"};
    public int mAnimateRelativeTo;
    public final LinkedHashMap mAttributes;
    public int mDrawPath;
    public float mHeight;
    public Easing mKeyFrameEasing;
    public final int mMode;
    public int mPathMotionArc;
    public float mPathRotate;
    public float mPosition;
    public float mRelativeAngle;
    public MotionController mRelativeToController;
    public double[] mTempDelta;
    public double[] mTempValue;
    public float mTime;
    public float mWidth;
    public float mX;
    public float mY;

    public MotionPaths() {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mPathMotionArc = -1;
        this.mAnimateRelativeTo = -1;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.mAttributes = new LinkedHashMap();
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
                f3 = f7;
            } else if (i2 == 2) {
                f5 = f7;
            } else if (i2 == 3) {
                f4 = f7;
            } else if (i2 == 4) {
                f6 = f7;
            }
        }
        float f8 = f3 - ((0.0f * f4) / 2.0f);
        float f9 = f5 - ((0.0f * f6) / 2.0f);
        fArr[0] = DrawerArrowDrawable$$ExternalSyntheticOutline0.m((f4 * 1.0f) + f8, f, (1.0f - f) * f8, 0.0f);
        fArr[1] = DrawerArrowDrawable$$ExternalSyntheticOutline0.m((f6 * 1.0f) + f9, f2, (1.0f - f2) * f9, 0.0f);
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
    public final int compareTo(Object obj) {
        return Float.compare(this.mPosition, ((MotionPaths) obj).mPosition);
    }

    public final void getCenter(double d, int[] iArr, double[] dArr, float[] fArr, int i) {
        float f = this.mX;
        float f2 = this.mY;
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

    public final void setBounds(float f, float f2, float f3, float f4) {
        this.mX = f;
        this.mY = f2;
        this.mWidth = f3;
        this.mHeight = f4;
    }

    public final void setupRelative(MotionController motionController, MotionPaths motionPaths) {
        double d = (((this.mWidth / 2.0f) + this.mX) - motionPaths.mX) - (motionPaths.mWidth / 2.0f);
        double d2 = (((this.mHeight / 2.0f) + this.mY) - motionPaths.mY) - (motionPaths.mHeight / 2.0f);
        this.mRelativeToController = motionController;
        this.mX = (float) Math.hypot(d2, d);
        if (Float.isNaN(this.mRelativeAngle)) {
            this.mY = (float) (Math.atan2(d2, d) + 1.5707963267948966d);
        } else {
            this.mY = (float) Math.toRadians(this.mRelativeAngle);
        }
    }

    public MotionPaths(int i, int i2, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float min;
        float f;
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mPathMotionArc = -1;
        this.mAnimateRelativeTo = -1;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.mAttributes = new LinkedHashMap();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
        if (motionPaths.mAnimateRelativeTo != -1) {
            float f2 = keyPosition.mFramePosition / 100.0f;
            this.mTime = f2;
            this.mDrawPath = keyPosition.mDrawPath;
            this.mMode = keyPosition.mPositionType;
            float f3 = Float.isNaN(keyPosition.mPercentWidth) ? f2 : keyPosition.mPercentWidth;
            float f4 = Float.isNaN(keyPosition.mPercentHeight) ? f2 : keyPosition.mPercentHeight;
            float f5 = motionPaths2.mWidth;
            float f6 = motionPaths.mWidth;
            float f7 = motionPaths2.mHeight;
            float f8 = motionPaths.mHeight;
            this.mPosition = this.mTime;
            this.mWidth = (int) (((f5 - f6) * f3) + f6);
            this.mHeight = (int) (((f7 - f8) * f4) + f8);
            if (keyPosition.mPositionType != 2) {
                float f9 = Float.isNaN(keyPosition.mPercentX) ? f2 : keyPosition.mPercentX;
                float f10 = motionPaths2.mX;
                float f11 = motionPaths.mX;
                this.mX = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f10, f11, f9, f11);
                f2 = Float.isNaN(keyPosition.mPercentY) ? f2 : keyPosition.mPercentY;
                float f12 = motionPaths2.mY;
                float f13 = motionPaths.mY;
                this.mY = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f12, f13, f2, f13);
            } else {
                if (Float.isNaN(keyPosition.mPercentX)) {
                    float f14 = motionPaths2.mX;
                    float f15 = motionPaths.mX;
                    min = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f14, f15, f2, f15);
                } else {
                    min = Math.min(f4, f3) * keyPosition.mPercentX;
                }
                this.mX = min;
                if (Float.isNaN(keyPosition.mPercentY)) {
                    float f16 = motionPaths2.mY;
                    float f17 = motionPaths.mY;
                    f = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f16, f17, f2, f17);
                } else {
                    f = keyPosition.mPercentY;
                }
                this.mY = f;
            }
            this.mAnimateRelativeTo = motionPaths.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        int i3 = keyPosition.mPositionType;
        if (i3 == 1) {
            float f18 = keyPosition.mFramePosition / 100.0f;
            this.mTime = f18;
            this.mDrawPath = keyPosition.mDrawPath;
            float f19 = Float.isNaN(keyPosition.mPercentWidth) ? f18 : keyPosition.mPercentWidth;
            float f20 = Float.isNaN(keyPosition.mPercentHeight) ? f18 : keyPosition.mPercentHeight;
            float f21 = motionPaths2.mWidth - motionPaths.mWidth;
            float f22 = motionPaths2.mHeight - motionPaths.mHeight;
            this.mPosition = this.mTime;
            f18 = Float.isNaN(keyPosition.mPercentX) ? f18 : keyPosition.mPercentX;
            float f23 = motionPaths.mX;
            float f24 = motionPaths.mWidth;
            float f25 = motionPaths.mY;
            float f26 = motionPaths.mHeight;
            float f27 = f18;
            float f28 = ((motionPaths2.mWidth / 2.0f) + motionPaths2.mX) - ((f24 / 2.0f) + f23);
            float f29 = ((motionPaths2.mHeight / 2.0f) + motionPaths2.mY) - ((f26 / 2.0f) + f25);
            float f30 = f28 * f27;
            float f31 = (f21 * f19) / 2.0f;
            this.mX = (int) ((f23 + f30) - f31);
            float f32 = f29 * f27;
            float f33 = (f22 * f20) / 2.0f;
            this.mY = (int) ((f25 + f32) - f33);
            this.mWidth = (int) (f24 + r8);
            this.mHeight = (int) (f26 + r9);
            float f34 = Float.isNaN(keyPosition.mPercentY) ? 0.0f : keyPosition.mPercentY;
            this.mMode = 1;
            float f35 = (int) ((motionPaths.mX + f30) - f31);
            float f36 = (int) ((motionPaths.mY + f32) - f33);
            this.mX = f35 + ((-f29) * f34);
            this.mY = f36 + (f28 * f34);
            this.mAnimateRelativeTo = this.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        if (i3 == 2) {
            float f37 = keyPosition.mFramePosition / 100.0f;
            this.mTime = f37;
            this.mDrawPath = keyPosition.mDrawPath;
            float f38 = Float.isNaN(keyPosition.mPercentWidth) ? f37 : keyPosition.mPercentWidth;
            float f39 = Float.isNaN(keyPosition.mPercentHeight) ? f37 : keyPosition.mPercentHeight;
            float f40 = motionPaths2.mWidth;
            float f41 = f40 - motionPaths.mWidth;
            float f42 = motionPaths2.mHeight;
            float f43 = f42 - motionPaths.mHeight;
            this.mPosition = this.mTime;
            float f44 = motionPaths.mX;
            float f45 = motionPaths.mY;
            float f46 = (f40 / 2.0f) + motionPaths2.mX;
            float f47 = (f42 / 2.0f) + motionPaths2.mY;
            float f48 = f41 * f38;
            this.mX = (int) ((((f46 - ((r9 / 2.0f) + f44)) * f37) + f44) - (f48 / 2.0f));
            float f49 = f43 * f39;
            this.mY = (int) ((((f47 - ((r12 / 2.0f) + f45)) * f37) + f45) - (f49 / 2.0f));
            this.mWidth = (int) (r9 + f48);
            this.mHeight = (int) (r12 + f49);
            this.mMode = 2;
            if (!Float.isNaN(keyPosition.mPercentX)) {
                this.mX = (int) (keyPosition.mPercentX * (i - ((int) this.mWidth)));
            }
            if (!Float.isNaN(keyPosition.mPercentY)) {
                this.mY = (int) (keyPosition.mPercentY * (i2 - ((int) this.mHeight)));
            }
            this.mAnimateRelativeTo = this.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        if (i3 != 3) {
            float f50 = keyPosition.mFramePosition / 100.0f;
            this.mTime = f50;
            this.mDrawPath = keyPosition.mDrawPath;
            float f51 = Float.isNaN(keyPosition.mPercentWidth) ? f50 : keyPosition.mPercentWidth;
            float f52 = Float.isNaN(keyPosition.mPercentHeight) ? f50 : keyPosition.mPercentHeight;
            float f53 = motionPaths2.mWidth;
            float f54 = motionPaths.mWidth;
            float f55 = f53 - f54;
            float f56 = motionPaths2.mHeight;
            float f57 = motionPaths.mHeight;
            float f58 = f56 - f57;
            this.mPosition = this.mTime;
            float f59 = motionPaths.mX;
            float f60 = motionPaths.mY;
            float f61 = ((f53 / 2.0f) + motionPaths2.mX) - ((f54 / 2.0f) + f59);
            float f62 = ((f56 / 2.0f) + motionPaths2.mY) - ((f57 / 2.0f) + f60);
            float f63 = (f55 * f51) / 2.0f;
            this.mX = (int) (((f61 * f50) + f59) - f63);
            float f64 = (f58 * f52) / 2.0f;
            this.mY = (int) (((f62 * f50) + f60) - f64);
            this.mWidth = (int) (f54 + r12);
            this.mHeight = (int) (f57 + r15);
            float f65 = Float.isNaN(keyPosition.mPercentX) ? f50 : keyPosition.mPercentX;
            float f66 = Float.isNaN(keyPosition.mAltPercentY) ? 0.0f : keyPosition.mAltPercentY;
            f50 = Float.isNaN(keyPosition.mPercentY) ? f50 : keyPosition.mPercentY;
            float f67 = Float.isNaN(keyPosition.mAltPercentX) ? 0.0f : keyPosition.mAltPercentX;
            this.mMode = 0;
            this.mX = (int) (((f67 * f62) + ((f65 * f61) + motionPaths.mX)) - f63);
            this.mY = (int) (((f62 * f50) + ((f61 * f66) + motionPaths.mY)) - f64);
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        float f68 = keyPosition.mFramePosition / 100.0f;
        this.mTime = f68;
        this.mDrawPath = keyPosition.mDrawPath;
        float f69 = Float.isNaN(keyPosition.mPercentWidth) ? f68 : keyPosition.mPercentWidth;
        float f70 = Float.isNaN(keyPosition.mPercentHeight) ? f68 : keyPosition.mPercentHeight;
        float f71 = motionPaths2.mWidth;
        float f72 = motionPaths.mWidth;
        float f73 = f71 - f72;
        float f74 = motionPaths2.mHeight;
        float f75 = motionPaths.mHeight;
        float f76 = f74 - f75;
        this.mPosition = this.mTime;
        float f77 = (f72 / 2.0f) + motionPaths.mX;
        float f78 = (f75 / 2.0f) + motionPaths.mY;
        float f79 = (f71 / 2.0f) + motionPaths2.mX;
        float f80 = (f74 / 2.0f) + motionPaths2.mY;
        if (f77 > f79) {
            f77 = f79;
            f79 = f77;
        }
        if (f78 <= f80) {
            f78 = f80;
            f80 = f78;
        }
        float f81 = f79 - f77;
        float f82 = f78 - f80;
        float f83 = (f73 * f69) / 2.0f;
        this.mX = (int) (((f81 * f68) + r13) - f83);
        float f84 = (f76 * f70) / 2.0f;
        this.mY = (int) (((f82 * f68) + r15) - f84);
        this.mWidth = (int) (f72 + r9);
        this.mHeight = (int) (f75 + r12);
        float f85 = Float.isNaN(keyPosition.mPercentX) ? f68 : keyPosition.mPercentX;
        float f86 = Float.isNaN(keyPosition.mAltPercentY) ? 0.0f : keyPosition.mAltPercentY;
        float f87 = Float.isNaN(keyPosition.mPercentY) ? f68 : keyPosition.mPercentY;
        float f88 = Float.isNaN(keyPosition.mAltPercentX) ? 0.0f : keyPosition.mAltPercentX;
        this.mMode = 0;
        this.mX = (int) (((f88 * f82) + ((f85 * f81) + motionPaths.mX)) - f83);
        this.mY = (int) (((f82 * f87) + ((f81 * f86) + motionPaths.mY)) - f84);
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }
}
