package androidx.constraintlayout.motion.widget;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.LinkedHashMap;

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
        float fCos = this.mY;
        float f2 = this.mWidth;
        float f3 = this.mHeight;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f4 = (float) dArr[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                f = f4;
            } else if (i3 == 2) {
                fCos = f4;
            } else if (i3 == 3) {
                f2 = f4;
            } else if (i3 == 4) {
                f3 = f4;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr2 = new float[2];
            motionController.getCenter(d, fArr2, new float[2]);
            float f5 = fArr2[0];
            float f6 = fArr2[1];
            double d2 = f;
            double d3 = fCos;
            double dSin = Math.sin(d3) * d2;
            fCos = (float) ((f6 - (Math.cos(d3) * d2)) - (f3 / 2.0f));
            f = (float) ((dSin + f5) - (f2 / 2.0f));
        }
        fArr[i] = (f2 / 2.0f) + f + 0.0f;
        fArr[i + 1] = (f3 / 2.0f) + fCos + 0.0f;
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
        float fMin;
        float fM$1;
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
            float f = keyPosition.mFramePosition / 100.0f;
            this.mTime = f;
            this.mDrawPath = keyPosition.mDrawPath;
            this.mMode = keyPosition.mPositionType;
            float f2 = Float.isNaN(keyPosition.mPercentWidth) ? f : keyPosition.mPercentWidth;
            float f3 = Float.isNaN(keyPosition.mPercentHeight) ? f : keyPosition.mPercentHeight;
            float f4 = motionPaths2.mWidth;
            float f5 = motionPaths.mWidth;
            float f6 = motionPaths2.mHeight;
            float f7 = motionPaths.mHeight;
            this.mPosition = this.mTime;
            this.mWidth = (int) (((f4 - f5) * f2) + f5);
            this.mHeight = (int) (((f6 - f7) * f3) + f7);
            if (keyPosition.mPositionType != 2) {
                float f8 = Float.isNaN(keyPosition.mPercentX) ? f : keyPosition.mPercentX;
                float f9 = motionPaths2.mX;
                float f10 = motionPaths.mX;
                this.mX = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f9, f10, f8, f10);
                f = Float.isNaN(keyPosition.mPercentY) ? f : keyPosition.mPercentY;
                float f11 = motionPaths2.mY;
                float f12 = motionPaths.mY;
                this.mY = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f11, f12, f, f12);
            } else {
                if (Float.isNaN(keyPosition.mPercentX)) {
                    float f13 = motionPaths2.mX;
                    float f14 = motionPaths.mX;
                    fMin = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f13, f14, f, f14);
                } else {
                    fMin = Math.min(f3, f2) * keyPosition.mPercentX;
                }
                this.mX = fMin;
                if (Float.isNaN(keyPosition.mPercentY)) {
                    float f15 = motionPaths2.mY;
                    float f16 = motionPaths.mY;
                    fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f15, f16, f, f16);
                } else {
                    fM$1 = keyPosition.mPercentY;
                }
                this.mY = fM$1;
            }
            this.mAnimateRelativeTo = motionPaths.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        int i3 = keyPosition.mPositionType;
        if (i3 == 1) {
            float f17 = keyPosition.mFramePosition / 100.0f;
            this.mTime = f17;
            this.mDrawPath = keyPosition.mDrawPath;
            float f18 = Float.isNaN(keyPosition.mPercentWidth) ? f17 : keyPosition.mPercentWidth;
            float f19 = Float.isNaN(keyPosition.mPercentHeight) ? f17 : keyPosition.mPercentHeight;
            float f20 = motionPaths2.mWidth - motionPaths.mWidth;
            float f21 = motionPaths2.mHeight - motionPaths.mHeight;
            this.mPosition = this.mTime;
            f17 = Float.isNaN(keyPosition.mPercentX) ? f17 : keyPosition.mPercentX;
            float f22 = motionPaths.mX;
            float f23 = motionPaths.mWidth;
            float f24 = motionPaths.mY;
            float f25 = motionPaths.mHeight;
            float f26 = f17;
            float f27 = ((motionPaths2.mWidth / 2.0f) + motionPaths2.mX) - ((f23 / 2.0f) + f22);
            float f28 = ((motionPaths2.mHeight / 2.0f) + motionPaths2.mY) - ((f25 / 2.0f) + f24);
            float f29 = f27 * f26;
            float f30 = (f20 * f18) / 2.0f;
            this.mX = (int) ((f22 + f29) - f30);
            float f31 = f28 * f26;
            float f32 = (f21 * f19) / 2.0f;
            this.mY = (int) ((f24 + f31) - f32);
            this.mWidth = (int) (f23 + r8);
            this.mHeight = (int) (f25 + r9);
            float f33 = Float.isNaN(keyPosition.mPercentY) ? 0.0f : keyPosition.mPercentY;
            this.mMode = 1;
            float f34 = (int) ((motionPaths.mX + f29) - f30);
            float f35 = (int) ((motionPaths.mY + f31) - f32);
            this.mX = f34 + ((-f28) * f33);
            this.mY = f35 + (f27 * f33);
            this.mAnimateRelativeTo = this.mAnimateRelativeTo;
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        if (i3 == 2) {
            float f36 = keyPosition.mFramePosition / 100.0f;
            this.mTime = f36;
            this.mDrawPath = keyPosition.mDrawPath;
            float f37 = Float.isNaN(keyPosition.mPercentWidth) ? f36 : keyPosition.mPercentWidth;
            float f38 = Float.isNaN(keyPosition.mPercentHeight) ? f36 : keyPosition.mPercentHeight;
            float f39 = motionPaths2.mWidth;
            float f40 = f39 - motionPaths.mWidth;
            float f41 = motionPaths2.mHeight;
            float f42 = f41 - motionPaths.mHeight;
            this.mPosition = this.mTime;
            float f43 = motionPaths.mX;
            float f44 = motionPaths.mY;
            float f45 = (f39 / 2.0f) + motionPaths2.mX;
            float f46 = (f41 / 2.0f) + motionPaths2.mY;
            float f47 = f40 * f37;
            this.mX = (int) ((((f45 - ((r9 / 2.0f) + f43)) * f36) + f43) - (f47 / 2.0f));
            float f48 = f42 * f38;
            this.mY = (int) ((((f46 - ((r12 / 2.0f) + f44)) * f36) + f44) - (f48 / 2.0f));
            this.mWidth = (int) (r9 + f47);
            this.mHeight = (int) (r12 + f48);
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
            float f49 = keyPosition.mFramePosition / 100.0f;
            this.mTime = f49;
            this.mDrawPath = keyPosition.mDrawPath;
            float f50 = Float.isNaN(keyPosition.mPercentWidth) ? f49 : keyPosition.mPercentWidth;
            float f51 = Float.isNaN(keyPosition.mPercentHeight) ? f49 : keyPosition.mPercentHeight;
            float f52 = motionPaths2.mWidth;
            float f53 = motionPaths.mWidth;
            float f54 = f52 - f53;
            float f55 = motionPaths2.mHeight;
            float f56 = motionPaths.mHeight;
            float f57 = f55 - f56;
            this.mPosition = this.mTime;
            float f58 = motionPaths.mX;
            float f59 = motionPaths.mY;
            float f60 = ((f52 / 2.0f) + motionPaths2.mX) - ((f53 / 2.0f) + f58);
            float f61 = ((f55 / 2.0f) + motionPaths2.mY) - ((f56 / 2.0f) + f59);
            float f62 = (f54 * f50) / 2.0f;
            this.mX = (int) (((f60 * f49) + f58) - f62);
            float f63 = (f57 * f51) / 2.0f;
            this.mY = (int) (((f61 * f49) + f59) - f63);
            this.mWidth = (int) (f53 + r12);
            this.mHeight = (int) (f56 + r15);
            float f64 = Float.isNaN(keyPosition.mPercentX) ? f49 : keyPosition.mPercentX;
            float f65 = Float.isNaN(keyPosition.mAltPercentY) ? 0.0f : keyPosition.mAltPercentY;
            f49 = Float.isNaN(keyPosition.mPercentY) ? f49 : keyPosition.mPercentY;
            float f66 = Float.isNaN(keyPosition.mAltPercentX) ? 0.0f : keyPosition.mAltPercentX;
            this.mMode = 0;
            this.mX = (int) (((f66 * f61) + ((f64 * f60) + motionPaths.mX)) - f62);
            this.mY = (int) (((f61 * f49) + ((f60 * f65) + motionPaths.mY)) - f63);
            this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
            this.mPathMotionArc = keyPosition.mPathMotionArc;
            return;
        }
        float f67 = keyPosition.mFramePosition / 100.0f;
        this.mTime = f67;
        this.mDrawPath = keyPosition.mDrawPath;
        float f68 = Float.isNaN(keyPosition.mPercentWidth) ? f67 : keyPosition.mPercentWidth;
        float f69 = Float.isNaN(keyPosition.mPercentHeight) ? f67 : keyPosition.mPercentHeight;
        float f70 = motionPaths2.mWidth;
        float f71 = motionPaths.mWidth;
        float f72 = f70 - f71;
        float f73 = motionPaths2.mHeight;
        float f74 = motionPaths.mHeight;
        float f75 = f73 - f74;
        this.mPosition = this.mTime;
        float f76 = (f71 / 2.0f) + motionPaths.mX;
        float f77 = (f74 / 2.0f) + motionPaths.mY;
        float f78 = (f70 / 2.0f) + motionPaths2.mX;
        float f79 = (f73 / 2.0f) + motionPaths2.mY;
        if (f76 > f78) {
            f76 = f78;
            f78 = f76;
        }
        if (f77 <= f79) {
            f77 = f79;
            f79 = f77;
        }
        float f80 = f78 - f76;
        float f81 = f77 - f79;
        float f82 = (f72 * f68) / 2.0f;
        this.mX = (int) (((f80 * f67) + r13) - f82);
        float f83 = (f75 * f69) / 2.0f;
        this.mY = (int) (((f81 * f67) + r15) - f83);
        this.mWidth = (int) (f71 + r9);
        this.mHeight = (int) (f74 + r12);
        float f84 = Float.isNaN(keyPosition.mPercentX) ? f67 : keyPosition.mPercentX;
        float f85 = Float.isNaN(keyPosition.mAltPercentY) ? 0.0f : keyPosition.mAltPercentY;
        float f86 = Float.isNaN(keyPosition.mPercentY) ? f67 : keyPosition.mPercentY;
        float f87 = Float.isNaN(keyPosition.mAltPercentX) ? 0.0f : keyPosition.mAltPercentX;
        this.mMode = 0;
        this.mX = (int) (((f87 * f81) + ((f84 * f80) + motionPaths.mX)) - f82);
        this.mY = (int) (((f81 * f86) + ((f80 * f85) + motionPaths.mY)) - f83);
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }
}
