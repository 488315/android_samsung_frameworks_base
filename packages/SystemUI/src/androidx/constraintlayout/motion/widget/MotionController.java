package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.motion.utils.ArcCurveFit;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.motion.utils.CustomSupport;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.motion.utils.ViewTimeCycle;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MotionController {
    public ArcCurveFit mArcSpline;
    public int[] mAttributeInterpolatorCount;
    public String[] mAttributeNames;
    public HashMap mAttributesMap;
    public HashMap mCycleMap;
    public final int mId;
    public double[] mInterpolateData;
    public int[] mInterpolateVariables;
    public double[] mInterpolateVelocity;
    public KeyTrigger[] mKeyTriggers;
    public CurveFit[] mSpline;
    public HashMap mTimeCycleAttributesMap;
    public final View mView;
    public final Rect mTempRect = new Rect();
    public boolean mForceMeasure = false;
    public int mCurveFitType = -1;
    public final MotionPaths mStartMotionPath = new MotionPaths();
    public final MotionPaths mEndMotionPath = new MotionPaths();
    public final MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
    public final MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
    public float mMotionStagger = Float.NaN;
    public float mStaggerOffset = 0.0f;
    public float mStaggerScale = 1.0f;
    public final float[] mValuesBuff = new float[4];
    public final ArrayList mMotionPaths = new ArrayList();
    public final float[] mVelocity = new float[1];
    public final ArrayList mKeyList = new ArrayList();
    public int mPathMotionArc = -1;
    public int mTransformPivotTarget = -1;
    public View mTransformPivotView = null;
    public int mQuantizeMotionSteps = -1;
    public float mQuantizeMotionPhase = Float.NaN;
    public Interpolator mQuantizeMotionInterpolator = null;
    public boolean mNoMovement = false;

    public MotionController(View view) {
        this.mView = view;
        this.mId = view.getId();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            ((ConstraintLayout.LayoutParams) layoutParams).getConstraintTag();
        }
    }

    public static void rotate(Rect rect, Rect rect2, int i, int i2, int i3) {
        if (i == 1) {
            int i4 = rect.left + rect.right;
            rect2.left = ((rect.top + rect.bottom) - rect.width()) / 2;
            rect2.top = i3 - ((rect.height() + i4) / 2);
            rect2.right = rect.width() + rect2.left;
            rect2.bottom = rect.height() + rect2.top;
            return;
        }
        if (i == 2) {
            int i5 = rect.left + rect.right;
            rect2.left = i2 - ((rect.width() + (rect.top + rect.bottom)) / 2);
            rect2.top = (i5 - rect.height()) / 2;
            rect2.right = rect.width() + rect2.left;
            rect2.bottom = rect.height() + rect2.top;
            return;
        }
        if (i == 3) {
            int i6 = rect.left + rect.right;
            rect2.left = ((rect.height() / 2) + rect.top) - (i6 / 2);
            rect2.top = i3 - ((rect.height() + i6) / 2);
            rect2.right = rect.width() + rect2.left;
            rect2.bottom = rect.height() + rect2.top;
            return;
        }
        if (i != 4) {
            return;
        }
        int i7 = rect.left + rect.right;
        rect2.left = i2 - ((rect.width() + (rect.bottom + rect.top)) / 2);
        rect2.top = (i7 - rect.height()) / 2;
        rect2.right = rect.width() + rect2.left;
        rect2.bottom = rect.height() + rect2.top;
    }

    public final float getAdjustedPosition(float f, float[] fArr) {
        float f2 = 0.0f;
        if (fArr != null) {
            fArr[0] = 1.0f;
        } else {
            float f3 = this.mStaggerScale;
            if (f3 != 1.0d) {
                float f4 = this.mStaggerOffset;
                if (f < f4) {
                    f = 0.0f;
                }
                if (f > f4 && f < 1.0d) {
                    f = Math.min((f - f4) * f3, 1.0f);
                }
            }
        }
        Easing easing = this.mStartMotionPath.mKeyFrameEasing;
        ArrayList arrayList = this.mMotionPaths;
        int size = arrayList.size();
        float f5 = Float.NaN;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            MotionPaths motionPaths = (MotionPaths) obj;
            Easing easing2 = motionPaths.mKeyFrameEasing;
            if (easing2 != null) {
                float f6 = motionPaths.mTime;
                if (f6 < f) {
                    easing = easing2;
                    f2 = f6;
                } else if (Float.isNaN(f5)) {
                    f5 = motionPaths.mTime;
                }
            }
        }
        if (easing == null) {
            return f;
        }
        float f7 = (Float.isNaN(f5) ? 1.0f : f5) - f2;
        double d = (f - f2) / f7;
        float f8 = (((float) easing.get(d)) * f7) + f2;
        if (fArr != null) {
            fArr[0] = (float) easing.getDiff(d);
        }
        return f8;
    }

    public final void getCenter(double d, float[] fArr, float[] fArr2) {
        float f;
        char c;
        double[] dArr = new double[4];
        double[] dArr2 = new double[4];
        this.mSpline[0].getPos(d, dArr);
        this.mSpline[0].getSlope(d, dArr2);
        float f2 = 0.0f;
        Arrays.fill(fArr2, 0.0f);
        int[] iArr = this.mInterpolateVariables;
        MotionPaths motionPaths = this.mStartMotionPath;
        float f3 = motionPaths.mX;
        float f4 = motionPaths.mY;
        float f5 = motionPaths.mWidth;
        float f6 = motionPaths.mHeight;
        float f7 = 0.0f;
        float f8 = 0.0f;
        float f9 = 0.0f;
        int i = 0;
        while (i < iArr.length) {
            double[] dArr3 = dArr;
            float f10 = (float) dArr3[i];
            float f11 = (float) dArr2[i];
            int i2 = iArr[i];
            if (i2 == 1) {
                c = 4;
                f3 = f10;
                f7 = f11;
            } else if (i2 == 2) {
                c = 4;
                f4 = f10;
                f2 = f11;
            } else if (i2 != 3) {
                c = 4;
                if (i2 == 4) {
                    f6 = f10;
                    f9 = f11;
                }
            } else {
                c = 4;
                f5 = f10;
                f8 = f11;
            }
            i++;
            dArr = dArr3;
        }
        float f12 = (f8 / 2.0f) + f7;
        float f13 = (f9 / 2.0f) + f2;
        MotionController motionController = motionPaths.mRelativeToController;
        if (motionController != null) {
            float[] fArr3 = new float[2];
            float[] fArr4 = new float[2];
            motionController.getCenter(d, fArr3, fArr4);
            float f14 = fArr3[0];
            float f15 = fArr3[1];
            float f16 = fArr4[0];
            float f17 = fArr4[1];
            double d2 = f3;
            double d3 = f4;
            float sin = (float) (((Math.sin(d3) * d2) + f14) - (f5 / 2.0f));
            float cos = (float) ((f15 - (Math.cos(d3) * d2)) - (f6 / 2.0f));
            double d4 = f7;
            f = 2.0f;
            double d5 = f2;
            float cos2 = (float) ((Math.cos(d3) * d5) + (Math.sin(d3) * d4) + f16);
            float sin2 = (float) ((Math.sin(d3) * d5) + (f17 - (Math.cos(d3) * d4)));
            f3 = sin;
            f4 = cos;
            f13 = sin2;
            f12 = cos2;
        } else {
            f = 2.0f;
        }
        fArr[0] = (f5 / f) + f3 + 0.0f;
        fArr[1] = (f6 / f) + f4 + 0.0f;
        fArr2[0] = f12;
        fArr2[1] = f13;
    }

    public final void getDpDt(float f, float f2, float f3, float[] fArr) {
        double[] dArr;
        float[] fArr2 = this.mVelocity;
        float adjustedPosition = getAdjustedPosition(f, fArr2);
        CurveFit[] curveFitArr = this.mSpline;
        int i = 0;
        if (curveFitArr == null) {
            MotionPaths motionPaths = this.mEndMotionPath;
            float f4 = motionPaths.mX;
            MotionPaths motionPaths2 = this.mStartMotionPath;
            float f5 = f4 - motionPaths2.mX;
            float f6 = motionPaths.mY - motionPaths2.mY;
            float f7 = motionPaths.mWidth - motionPaths2.mWidth;
            float f8 = (motionPaths.mHeight - motionPaths2.mHeight) + f6;
            fArr[0] = ((f7 + f5) * f2) + ((1.0f - f2) * f5);
            fArr[1] = (f8 * f3) + ((1.0f - f3) * f6);
            return;
        }
        double d = adjustedPosition;
        curveFitArr[0].getSlope(d, this.mInterpolateVelocity);
        this.mSpline[0].getPos(d, this.mInterpolateData);
        float f9 = fArr2[0];
        while (true) {
            dArr = this.mInterpolateVelocity;
            if (i >= dArr.length) {
                break;
            }
            dArr[i] = dArr[i] * f9;
            i++;
        }
        ArcCurveFit arcCurveFit = this.mArcSpline;
        if (arcCurveFit == null) {
            int[] iArr = this.mInterpolateVariables;
            double[] dArr2 = this.mInterpolateData;
            this.mStartMotionPath.getClass();
            MotionPaths.setDpDt(f2, f3, fArr, iArr, dArr, dArr2);
            return;
        }
        double[] dArr3 = this.mInterpolateData;
        if (dArr3.length > 0) {
            arcCurveFit.getPos(d, dArr3);
            this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
            int[] iArr2 = this.mInterpolateVariables;
            double[] dArr4 = this.mInterpolateVelocity;
            double[] dArr5 = this.mInterpolateData;
            this.mStartMotionPath.getClass();
            MotionPaths.setDpDt(f2, f3, fArr, iArr2, dArr4, dArr5);
        }
    }

    public final boolean interpolate(float f, long j, View view, KeyCache keyCache) {
        boolean z;
        View view2;
        boolean z2;
        float f2;
        ViewTimeCycle.PathRotate pathRotate;
        float f3;
        boolean z3;
        float f4;
        float f5;
        float f6;
        boolean z4;
        View view3 = view;
        ViewTimeCycle.PathRotate pathRotate2 = null;
        float adjustedPosition = getAdjustedPosition(f, null);
        int i = this.mQuantizeMotionSteps;
        if (i != -1) {
            float f7 = 1.0f / i;
            float floor = ((float) Math.floor(adjustedPosition / f7)) * f7;
            float f8 = (adjustedPosition % f7) / f7;
            if (!Float.isNaN(this.mQuantizeMotionPhase)) {
                f8 = (f8 + this.mQuantizeMotionPhase) % 1.0f;
            }
            Interpolator interpolator = this.mQuantizeMotionInterpolator;
            adjustedPosition = ((interpolator != null ? interpolator.getInterpolation(f8) : ((double) f8) > 0.5d ? 1.0f : 0.0f) * f7) + floor;
        }
        HashMap hashMap = this.mAttributesMap;
        if (hashMap != null) {
            Iterator it = hashMap.values().iterator();
            while (it.hasNext()) {
                ((ViewSpline) it.next()).setProperty(adjustedPosition, view3);
            }
        }
        HashMap hashMap2 = this.mTimeCycleAttributesMap;
        if (hashMap2 != null) {
            ViewTimeCycle.PathRotate pathRotate3 = null;
            z = false;
            for (ViewTimeCycle viewTimeCycle : hashMap2.values()) {
                if (viewTimeCycle instanceof ViewTimeCycle.PathRotate) {
                    pathRotate3 = (ViewTimeCycle.PathRotate) viewTimeCycle;
                } else {
                    z |= viewTimeCycle.setProperty(adjustedPosition, j, view3, keyCache);
                    view3 = view;
                }
            }
            pathRotate2 = pathRotate3;
        } else {
            z = false;
        }
        CurveFit[] curveFitArr = this.mSpline;
        MotionPaths motionPaths = this.mStartMotionPath;
        if (curveFitArr != null) {
            double d = adjustedPosition;
            curveFitArr[0].getPos(d, this.mInterpolateData);
            this.mSpline[0].getSlope(d, this.mInterpolateVelocity);
            ArcCurveFit arcCurveFit = this.mArcSpline;
            if (arcCurveFit != null) {
                double[] dArr = this.mInterpolateData;
                f2 = 0.0f;
                if (dArr.length > 0) {
                    arcCurveFit.getPos(d, dArr);
                    this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
                }
            } else {
                f2 = 0.0f;
            }
            if (this.mNoMovement) {
                view2 = view;
                pathRotate = pathRotate2;
                f3 = 1.0f;
                z3 = z;
                f4 = 2.0f;
            } else {
                int[] iArr = this.mInterpolateVariables;
                double[] dArr2 = this.mInterpolateData;
                f4 = 2.0f;
                double[] dArr3 = this.mInterpolateVelocity;
                f3 = 1.0f;
                boolean z5 = this.mForceMeasure;
                float f9 = motionPaths.mX;
                float f10 = motionPaths.mY;
                float f11 = motionPaths.mWidth;
                int i2 = 1;
                float f12 = motionPaths.mHeight;
                pathRotate = pathRotate2;
                if (iArr.length != 0) {
                    f5 = f11;
                    if (motionPaths.mTempValue.length <= iArr[iArr.length - 1]) {
                        int i3 = iArr[iArr.length - 1] + 1;
                        motionPaths.mTempValue = new double[i3];
                        motionPaths.mTempDelta = new double[i3];
                    }
                } else {
                    f5 = f11;
                }
                Arrays.fill(motionPaths.mTempValue, Double.NaN);
                for (int i4 = 0; i4 < iArr.length; i4++) {
                    double[] dArr4 = motionPaths.mTempValue;
                    int i5 = iArr[i4];
                    dArr4[i5] = dArr2[i4];
                    motionPaths.mTempDelta[i5] = dArr3[i4];
                }
                float f13 = Float.NaN;
                float f14 = f2;
                float f15 = f14;
                float f16 = f15;
                int i6 = 0;
                float f17 = f5;
                float f18 = f16;
                while (true) {
                    double[] dArr5 = motionPaths.mTempValue;
                    f6 = f12;
                    if (i6 >= dArr5.length) {
                        break;
                    }
                    if (Double.isNaN(dArr5[i6])) {
                        z4 = z;
                    } else {
                        float f19 = (float) (Double.isNaN(motionPaths.mTempValue[i6]) ? 0.0d : motionPaths.mTempValue[i6] + 0.0d);
                        z4 = z;
                        float f20 = (float) motionPaths.mTempDelta[i6];
                        if (i6 == i2) {
                            f9 = f19;
                            f14 = f20;
                        } else if (i6 == 2) {
                            f10 = f19;
                            f15 = f20;
                        } else if (i6 == 3) {
                            f17 = f19;
                            f18 = f20;
                        } else if (i6 == 4) {
                            f12 = f19;
                            f16 = f20;
                            i6++;
                            z = z4;
                            i2 = 1;
                        } else if (i6 == 5) {
                            f13 = f19;
                        }
                    }
                    f12 = f6;
                    i6++;
                    z = z4;
                    i2 = 1;
                }
                z3 = z;
                MotionController motionController = motionPaths.mRelativeToController;
                if (motionController != null) {
                    float[] fArr = new float[2];
                    float[] fArr2 = new float[2];
                    motionController.getCenter(d, fArr, fArr2);
                    float f21 = fArr[0];
                    float f22 = fArr[1];
                    float f23 = fArr2[0];
                    float f24 = fArr2[1];
                    double d2 = f9;
                    double d3 = f10;
                    f9 = (float) (((Math.sin(d3) * d2) + f21) - (f17 / 2.0f));
                    f10 = (float) ((f22 - (Math.cos(d3) * d2)) - (f6 / 2.0f));
                    double d4 = f14;
                    double d5 = f15;
                    float cos = (float) ((Math.cos(d3) * d2 * d5) + (Math.sin(d3) * d4) + f23);
                    float sin = (float) ((Math.sin(d3) * d2 * d5) + (f24 - (Math.cos(d3) * d4)));
                    if (dArr3.length >= 2) {
                        dArr3[0] = cos;
                        dArr3[1] = sin;
                    }
                    if (Float.isNaN(f13)) {
                        view2 = view;
                    } else {
                        view2 = view;
                        view2.setRotation((float) (Math.toDegrees(Math.atan2(sin, cos)) + f13));
                    }
                } else {
                    view2 = view;
                    if (!Float.isNaN(f13)) {
                        view2.setRotation(f13 + ((float) Math.toDegrees(Math.atan2((f16 / 2.0f) + f15, (f18 / 2.0f) + f14))) + f2);
                    }
                }
                float f25 = f9 + 0.5f;
                int i7 = (int) f25;
                float f26 = f10 + 0.5f;
                int i8 = (int) f26;
                int i9 = (int) (f25 + f17);
                int i10 = (int) (f26 + f6);
                int i11 = i9 - i7;
                int i12 = i10 - i8;
                if (i11 != view2.getMeasuredWidth() || i12 != view2.getMeasuredHeight() || z5) {
                    view2.measure(View.MeasureSpec.makeMeasureSpec(i11, 1073741824), View.MeasureSpec.makeMeasureSpec(i12, 1073741824));
                }
                view2.layout(i7, i8, i9, i10);
                this.mForceMeasure = false;
            }
            if (this.mTransformPivotTarget != -1) {
                if (this.mTransformPivotView == null) {
                    this.mTransformPivotView = ((View) view2.getParent()).findViewById(this.mTransformPivotTarget);
                }
                if (this.mTransformPivotView != null) {
                    float bottom = (this.mTransformPivotView.getBottom() + r1.getTop()) / f4;
                    float right = (this.mTransformPivotView.getRight() + this.mTransformPivotView.getLeft()) / f4;
                    if (view2.getRight() - view2.getLeft() > 0 && view2.getBottom() - view2.getTop() > 0) {
                        view2.setPivotX(right - view2.getLeft());
                        view2.setPivotY(bottom - view2.getTop());
                    }
                }
            }
            HashMap hashMap3 = this.mAttributesMap;
            if (hashMap3 != null) {
                for (SplineSet splineSet : hashMap3.values()) {
                    if (splineSet instanceof ViewSpline.PathRotate) {
                        double[] dArr6 = this.mInterpolateVelocity;
                        if (dArr6.length > 1) {
                            view2.setRotation(((ViewSpline.PathRotate) splineSet).get(adjustedPosition) + ((float) Math.toDegrees(Math.atan2(dArr6[1], dArr6[0]))));
                        }
                    }
                }
            }
            if (pathRotate != null) {
                double[] dArr7 = this.mInterpolateVelocity;
                double d6 = dArr7[0];
                double d7 = dArr7[1];
                ViewTimeCycle.PathRotate pathRotate4 = pathRotate;
                view2.setRotation(pathRotate4.get(adjustedPosition, j, view2, keyCache) + ((float) Math.toDegrees(Math.atan2(d7, d6))));
                z2 = z3 | pathRotate4.mContinue;
            } else {
                z2 = z3;
            }
            int i13 = 1;
            while (true) {
                CurveFit[] curveFitArr2 = this.mSpline;
                if (i13 >= curveFitArr2.length) {
                    break;
                }
                CurveFit curveFit = curveFitArr2[i13];
                float[] fArr3 = this.mValuesBuff;
                curveFit.getPos(d, fArr3);
                CustomSupport.setInterpolatedValue((ConstraintAttribute) motionPaths.mAttributes.get(this.mAttributeNames[i13 - 1]), view2, fArr3);
                i13++;
            }
            MotionConstrainedPoint motionConstrainedPoint = this.mStartPoint;
            if (motionConstrainedPoint.mVisibilityMode == 0) {
                if (adjustedPosition <= f2) {
                    view2.setVisibility(motionConstrainedPoint.mVisibility);
                } else {
                    MotionConstrainedPoint motionConstrainedPoint2 = this.mEndPoint;
                    if (adjustedPosition >= f3) {
                        view2.setVisibility(motionConstrainedPoint2.mVisibility);
                    } else if (motionConstrainedPoint2.mVisibility != motionConstrainedPoint.mVisibility) {
                        view2.setVisibility(0);
                    }
                }
            }
            if (this.mKeyTriggers != null) {
                int i14 = 0;
                while (true) {
                    KeyTrigger[] keyTriggerArr = this.mKeyTriggers;
                    if (i14 >= keyTriggerArr.length) {
                        break;
                    }
                    keyTriggerArr[i14].conditionallyFire(adjustedPosition, view2);
                    i14++;
                }
            }
        } else {
            view2 = view;
            boolean z6 = z;
            float f27 = motionPaths.mX;
            MotionPaths motionPaths2 = this.mEndMotionPath;
            float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(motionPaths2.mX, f27, adjustedPosition, f27);
            float f28 = motionPaths.mY;
            float m$12 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(motionPaths2.mY, f28, adjustedPosition, f28);
            float f29 = motionPaths.mWidth;
            float f30 = motionPaths2.mWidth;
            float m$13 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f30, f29, adjustedPosition, f29);
            float f31 = motionPaths.mHeight;
            float f32 = motionPaths2.mHeight;
            float f33 = m$1 + 0.5f;
            int i15 = (int) f33;
            float f34 = m$12 + 0.5f;
            int i16 = (int) f34;
            int i17 = (int) (f33 + m$13);
            int m$14 = (int) (f34 + DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f32, f31, adjustedPosition, f31));
            int i18 = i17 - i15;
            int i19 = m$14 - i16;
            if (f30 != f29 || f32 != f31 || this.mForceMeasure) {
                view2.measure(View.MeasureSpec.makeMeasureSpec(i18, 1073741824), View.MeasureSpec.makeMeasureSpec(i19, 1073741824));
                this.mForceMeasure = false;
            }
            view2.layout(i15, i16, i17, m$14);
            z2 = z6;
        }
        HashMap hashMap4 = this.mCycleMap;
        if (hashMap4 != null) {
            for (ViewOscillator viewOscillator : hashMap4.values()) {
                if (viewOscillator instanceof ViewOscillator.PathRotateSet) {
                    double[] dArr8 = this.mInterpolateVelocity;
                    view2.setRotation(((ViewOscillator.PathRotateSet) viewOscillator).get(adjustedPosition) + ((float) Math.toDegrees(Math.atan2(dArr8[1], dArr8[0]))));
                } else {
                    viewOscillator.setProperty(adjustedPosition, view2);
                }
            }
        }
        return z2;
    }

    public final void readView(MotionPaths motionPaths) {
        motionPaths.setBounds((int) this.mView.getX(), (int) this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:361:0x07ea. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:350:0x0851 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:354:0x0847 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setup(int r48, int r49, long r50) {
        /*
            Method dump skipped, instructions count: 5992
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionController.setup(int, int, long):void");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(" start: x: ");
        MotionPaths motionPaths = this.mStartMotionPath;
        sb.append(motionPaths.mX);
        sb.append(" y: ");
        sb.append(motionPaths.mY);
        sb.append(" end: x: ");
        MotionPaths motionPaths2 = this.mEndMotionPath;
        sb.append(motionPaths2.mX);
        sb.append(" y: ");
        sb.append(motionPaths2.mY);
        return sb.toString();
    }
}
