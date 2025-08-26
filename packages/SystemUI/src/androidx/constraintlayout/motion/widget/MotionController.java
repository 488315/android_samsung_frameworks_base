package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.motion.utils.ArcCurveFit;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet;
import androidx.constraintlayout.motion.utils.CustomSupport;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.motion.utils.ViewTimeCycle;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

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
            float fSin = (float) (((Math.sin(d3) * d2) + f14) - (f5 / 2.0f));
            float fCos = (float) ((f15 - (Math.cos(d3) * d2)) - (f6 / 2.0f));
            double d4 = f7;
            f = 2.0f;
            double d5 = f2;
            float fCos2 = (float) ((Math.cos(d3) * d5) + (Math.sin(d3) * d4) + f16);
            float fSin2 = (float) ((Math.sin(d3) * d5) + (f17 - (Math.cos(d3) * d4)));
            f3 = fSin;
            f4 = fCos;
            f13 = fSin2;
            f12 = fCos2;
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
        boolean property;
        View view2;
        boolean z;
        float f2;
        ViewTimeCycle.PathRotate pathRotate;
        float f3;
        boolean z2;
        float f4;
        float f5;
        float f6;
        boolean z3;
        View view3 = view;
        ViewTimeCycle.PathRotate pathRotate2 = null;
        float adjustedPosition = getAdjustedPosition(f, null);
        int i = this.mQuantizeMotionSteps;
        if (i != -1) {
            float f7 = 1.0f / i;
            float fFloor = ((float) Math.floor(adjustedPosition / f7)) * f7;
            float f8 = (adjustedPosition % f7) / f7;
            if (!Float.isNaN(this.mQuantizeMotionPhase)) {
                f8 = (f8 + this.mQuantizeMotionPhase) % 1.0f;
            }
            Interpolator interpolator = this.mQuantizeMotionInterpolator;
            adjustedPosition = ((interpolator != null ? interpolator.getInterpolation(f8) : ((double) f8) > 0.5d ? 1.0f : 0.0f) * f7) + fFloor;
        }
        HashMap map = this.mAttributesMap;
        if (map != null) {
            Iterator it = map.values().iterator();
            while (it.hasNext()) {
                ((ViewSpline) it.next()).setProperty(adjustedPosition, view3);
            }
        }
        HashMap map2 = this.mTimeCycleAttributesMap;
        if (map2 != null) {
            ViewTimeCycle.PathRotate pathRotate3 = null;
            property = false;
            for (ViewTimeCycle viewTimeCycle : map2.values()) {
                if (viewTimeCycle instanceof ViewTimeCycle.PathRotate) {
                    pathRotate3 = (ViewTimeCycle.PathRotate) viewTimeCycle;
                } else {
                    property |= viewTimeCycle.setProperty(adjustedPosition, j, view3, keyCache);
                    view3 = view;
                }
            }
            pathRotate2 = pathRotate3;
        } else {
            property = false;
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
                z2 = property;
                f4 = 2.0f;
            } else {
                int[] iArr = this.mInterpolateVariables;
                double[] dArr2 = this.mInterpolateData;
                f4 = 2.0f;
                double[] dArr3 = this.mInterpolateVelocity;
                f3 = 1.0f;
                boolean z4 = this.mForceMeasure;
                float fSin = motionPaths.mX;
                float fCos = motionPaths.mY;
                float f9 = motionPaths.mWidth;
                int i2 = 1;
                float f10 = motionPaths.mHeight;
                pathRotate = pathRotate2;
                if (iArr.length != 0) {
                    f5 = f9;
                    if (motionPaths.mTempValue.length <= iArr[iArr.length - 1]) {
                        int i3 = iArr[iArr.length - 1] + 1;
                        motionPaths.mTempValue = new double[i3];
                        motionPaths.mTempDelta = new double[i3];
                    }
                } else {
                    f5 = f9;
                }
                Arrays.fill(motionPaths.mTempValue, Double.NaN);
                for (int i4 = 0; i4 < iArr.length; i4++) {
                    double[] dArr4 = motionPaths.mTempValue;
                    int i5 = iArr[i4];
                    dArr4[i5] = dArr2[i4];
                    motionPaths.mTempDelta[i5] = dArr3[i4];
                }
                float f11 = Float.NaN;
                float f12 = f2;
                float f13 = f12;
                float f14 = f13;
                int i6 = 0;
                float f15 = f5;
                float f16 = f14;
                while (true) {
                    double[] dArr5 = motionPaths.mTempValue;
                    f6 = f10;
                    if (i6 >= dArr5.length) {
                        break;
                    }
                    if (Double.isNaN(dArr5[i6])) {
                        z3 = property;
                    } else {
                        float f17 = (float) (Double.isNaN(motionPaths.mTempValue[i6]) ? 0.0d : motionPaths.mTempValue[i6] + 0.0d);
                        z3 = property;
                        float f18 = (float) motionPaths.mTempDelta[i6];
                        if (i6 == i2) {
                            fSin = f17;
                            f12 = f18;
                        } else if (i6 == 2) {
                            fCos = f17;
                            f13 = f18;
                        } else if (i6 == 3) {
                            f15 = f17;
                            f16 = f18;
                        } else if (i6 == 4) {
                            f10 = f17;
                            f14 = f18;
                            i6++;
                            property = z3;
                            i2 = 1;
                        } else if (i6 == 5) {
                            f11 = f17;
                        }
                    }
                    f10 = f6;
                    i6++;
                    property = z3;
                    i2 = 1;
                }
                z2 = property;
                MotionController motionController = motionPaths.mRelativeToController;
                if (motionController != null) {
                    float[] fArr = new float[2];
                    float[] fArr2 = new float[2];
                    motionController.getCenter(d, fArr, fArr2);
                    float f19 = fArr[0];
                    float f20 = fArr[1];
                    float f21 = fArr2[0];
                    float f22 = fArr2[1];
                    double d2 = fSin;
                    double d3 = fCos;
                    fSin = (float) (((Math.sin(d3) * d2) + f19) - (f15 / 2.0f));
                    fCos = (float) ((f20 - (Math.cos(d3) * d2)) - (f6 / 2.0f));
                    double d4 = f12;
                    double d5 = f13;
                    float fCos2 = (float) ((Math.cos(d3) * d2 * d5) + (Math.sin(d3) * d4) + f21);
                    float fSin2 = (float) ((Math.sin(d3) * d2 * d5) + (f22 - (Math.cos(d3) * d4)));
                    if (dArr3.length >= 2) {
                        dArr3[0] = fCos2;
                        dArr3[1] = fSin2;
                    }
                    if (Float.isNaN(f11)) {
                        view2 = view;
                    } else {
                        view2 = view;
                        view2.setRotation((float) (Math.toDegrees(Math.atan2(fSin2, fCos2)) + f11));
                    }
                } else {
                    view2 = view;
                    if (!Float.isNaN(f11)) {
                        view2.setRotation(f11 + ((float) Math.toDegrees(Math.atan2((f14 / 2.0f) + f13, (f16 / 2.0f) + f12))) + f2);
                    }
                }
                float f23 = fSin + 0.5f;
                int i7 = (int) f23;
                float f24 = fCos + 0.5f;
                int i8 = (int) f24;
                int i9 = (int) (f23 + f15);
                int i10 = (int) (f24 + f6);
                int i11 = i9 - i7;
                int i12 = i10 - i8;
                if (i11 != view2.getMeasuredWidth() || i12 != view2.getMeasuredHeight() || z4) {
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
            HashMap map3 = this.mAttributesMap;
            if (map3 != null) {
                for (SplineSet splineSet : map3.values()) {
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
                z = z2 | pathRotate4.mContinue;
            } else {
                z = z2;
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
            boolean z5 = property;
            float f25 = motionPaths.mX;
            MotionPaths motionPaths2 = this.mEndMotionPath;
            float fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(motionPaths2.mX, f25, adjustedPosition, f25);
            float f26 = motionPaths.mY;
            float fM$12 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(motionPaths2.mY, f26, adjustedPosition, f26);
            float f27 = motionPaths.mWidth;
            float f28 = motionPaths2.mWidth;
            float fM$13 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f28, f27, adjustedPosition, f27);
            float f29 = motionPaths.mHeight;
            float f30 = motionPaths2.mHeight;
            float f31 = fM$1 + 0.5f;
            int i15 = (int) f31;
            float f32 = fM$12 + 0.5f;
            int i16 = (int) f32;
            int i17 = (int) (f31 + fM$13);
            int iM$1 = (int) (f32 + DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f30, f29, adjustedPosition, f29));
            int i18 = i17 - i15;
            int i19 = iM$1 - i16;
            if (f28 != f27 || f30 != f29 || this.mForceMeasure) {
                view2.measure(View.MeasureSpec.makeMeasureSpec(i18, 1073741824), View.MeasureSpec.makeMeasureSpec(i19, 1073741824));
                this.mForceMeasure = false;
            }
            view2.layout(i15, i16, i17, iM$1);
            z = z5;
        }
        HashMap map4 = this.mCycleMap;
        if (map4 != null) {
            for (ViewOscillator viewOscillator : map4.values()) {
                if (viewOscillator instanceof ViewOscillator.PathRotateSet) {
                    double[] dArr8 = this.mInterpolateVelocity;
                    view2.setRotation(((ViewOscillator.PathRotateSet) viewOscillator).get(adjustedPosition) + ((float) Math.toDegrees(Math.atan2(dArr8[1], dArr8[0]))));
                } else {
                    viewOscillator.setProperty(adjustedPosition, view2);
                }
            }
        }
        return z;
    }

    public final void readView(MotionPaths motionPaths) {
        motionPaths.setBounds((int) this.mView.getX(), (int) this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:415:0x07ea. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:221:0x04bb  */
    /* JADX WARN: Removed duplicated region for block: B:455:0x0920 A[PHI: r2 r3
      0x0920: PHI (r2v153 java.lang.String) = (r2v131 java.lang.String), (r2v132 java.lang.String), (r2v133 java.lang.String), (r2v154 java.lang.String) binds: [B:503:0x09b3, B:499:0x09a5, B:496:0x0996, B:454:0x091c] A[DONT_GENERATE, DONT_INLINE]
      0x0920: PHI (r3v151 java.lang.String) = (r3v140 java.lang.String), (r3v141 java.lang.String), (r3v144 java.lang.String), (r3v152 java.lang.String) binds: [B:503:0x09b3, B:499:0x09a5, B:496:0x0996, B:454:0x091c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:672:0x0fcc A[PHI: r1 r2 r6 r7 r9 r10 r15 r50
      0x0fcc: PHI (r1v93 java.lang.Object) = (r1v80 java.lang.Object), (r1v81 java.lang.Object), (r1v82 java.lang.Object), (r1v94 java.lang.Object) binds: [B:730:0x1100, B:726:0x10e5, B:722:0x10ca, B:671:0x0fc8] A[DONT_GENERATE, DONT_INLINE]
      0x0fcc: PHI (r2v80 java.lang.String) = (r2v72 java.lang.String), (r2v73 java.lang.String), (r2v74 java.lang.String), (r2v81 java.lang.String) binds: [B:730:0x1100, B:726:0x10e5, B:722:0x10ca, B:671:0x0fc8] A[DONT_GENERATE, DONT_INLINE]
      0x0fcc: PHI (r6v34 java.lang.String) = (r6v5 java.lang.String), (r6v6 java.lang.String), (r6v7 java.lang.String), (r6v35 java.lang.String) binds: [B:730:0x1100, B:726:0x10e5, B:722:0x10ca, B:671:0x0fc8] A[DONT_GENERATE, DONT_INLINE]
      0x0fcc: PHI (r7v19 java.lang.String) = (r7v3 java.lang.String), (r7v4 java.lang.String), (r7v5 java.lang.String), (r7v20 java.lang.String) binds: [B:730:0x1100, B:726:0x10e5, B:722:0x10ca, B:671:0x0fc8] A[DONT_GENERATE, DONT_INLINE]
      0x0fcc: PHI (r9v50 java.lang.String) = (r9v33 java.lang.String), (r9v34 java.lang.String), (r9v35 java.lang.String), (r9v51 java.lang.String) binds: [B:730:0x1100, B:726:0x10e5, B:722:0x10ca, B:671:0x0fc8] A[DONT_GENERATE, DONT_INLINE]
      0x0fcc: PHI (r10v21 java.lang.String) = (r10v7 java.lang.String), (r10v8 java.lang.String), (r10v9 java.lang.String), (r10v22 java.lang.String) binds: [B:730:0x1100, B:726:0x10e5, B:722:0x10ca, B:671:0x0fc8] A[DONT_GENERATE, DONT_INLINE]
      0x0fcc: PHI (r15v42 java.lang.String) = (r15v33 java.lang.String), (r15v34 java.lang.String), (r15v35 java.lang.String), (r15v43 java.lang.String) binds: [B:730:0x1100, B:726:0x10e5, B:722:0x10ca, B:671:0x0fc8] A[DONT_GENERATE, DONT_INLINE]
      0x0fcc: PHI (r50v18 java.util.Iterator) = (r50v3 java.util.Iterator), (r50v4 java.util.Iterator), (r50v5 java.util.Iterator), (r50v19 java.util.Iterator) binds: [B:730:0x1100, B:726:0x10e5, B:722:0x10ca, B:671:0x0fc8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:927:0x0851 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:928:0x0847 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setup(int i, int i2, long j) {
        Object obj;
        Object obj2;
        MotionConstrainedPoint motionConstrainedPoint;
        ArrayList arrayList;
        HashSet hashSet;
        String str;
        String str2;
        HashSet hashSet2;
        HashMap map;
        String str3;
        Object obj3;
        String str4;
        String str5;
        MotionController motionController;
        String str6;
        String str7;
        Object obj4;
        ViewOscillator viewOscillator;
        Object obj5;
        String str8;
        String str9;
        String str10;
        char c;
        char c2;
        float f;
        ViewOscillator viewOscillator2;
        Iterator it;
        String str11;
        Object obj6;
        String str12;
        String str13;
        String str14;
        String str15;
        String str16;
        char c3;
        char c4;
        KeyCycleOscillator rotationXset;
        float f2;
        KeyCycleOscillator customSet;
        String str17;
        String str18;
        String str19;
        float fHypot;
        double d;
        String str20;
        String str21;
        char c5;
        String str22;
        String str23;
        String str24;
        String str25;
        ConstraintAttribute constraintAttribute;
        HashSet hashSet3;
        Iterator it2;
        int iIntValue;
        int i3;
        String str26;
        String str27;
        char c6;
        char c7;
        Iterator it3;
        String str28;
        char c8;
        char c9;
        TimeCycleSplineSet rotationXset2;
        String str29;
        String str30;
        ConstraintAttribute constraintAttribute2;
        Iterator it4;
        int iIntValue2;
        Integer num;
        int i4;
        char c10;
        HashSet hashSet4;
        String str31;
        String str32;
        HashMap map2;
        String str33;
        Object obj7;
        String str34;
        String str35;
        char c11;
        char c12;
        SplineSet rotationXset3;
        ConstraintAttribute constraintAttribute3;
        String str36;
        String str37;
        int i5;
        HashSet hashSet5;
        HashSet hashSet6;
        HashMap map3;
        String str38 = "translationY";
        String str39 = "translationX";
        String str40 = "scaleY";
        String str41 = "scaleX";
        String str42 = "rotationY";
        String str43 = "progress";
        new HashSet();
        int i6 = 1;
        HashSet hashSet7 = new HashSet();
        HashSet hashSet8 = new HashSet();
        HashSet hashSet9 = new HashSet();
        HashSet hashSet10 = hashSet7;
        HashMap map4 = new HashMap();
        int i7 = this.mPathMotionArc;
        HashSet hashSet11 = hashSet9;
        MotionPaths motionPaths = this.mStartMotionPath;
        if (i7 != -1) {
            motionPaths.mPathMotionArc = i7;
        }
        MotionConstrainedPoint motionConstrainedPoint2 = this.mStartPoint;
        float f3 = motionConstrainedPoint2.mAlpha;
        MotionPaths motionPaths2 = motionPaths;
        MotionConstrainedPoint motionConstrainedPoint3 = this.mEndPoint;
        if (MotionConstrainedPoint.diff(f3, motionConstrainedPoint3.mAlpha)) {
            hashSet8.add("alpha");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint2.mElevation, motionConstrainedPoint3.mElevation)) {
            hashSet8.add("elevation");
        }
        int i8 = motionConstrainedPoint2.mVisibility;
        int i9 = motionConstrainedPoint3.mVisibility;
        if (i8 != i9 && motionConstrainedPoint2.mVisibilityMode == 0 && (i8 == 0 || i9 == 0)) {
            hashSet8.add("alpha");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint2.mRotation, motionConstrainedPoint3.mRotation)) {
            hashSet8.add("rotation");
        }
        if (!Float.isNaN(motionConstrainedPoint2.mPathRotate) || !Float.isNaN(motionConstrainedPoint3.mPathRotate)) {
            hashSet8.add("transitionPathRotate");
        }
        if (!Float.isNaN(motionConstrainedPoint2.mProgress) || !Float.isNaN(motionConstrainedPoint3.mProgress)) {
            hashSet8.add("progress");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint2.mRotationX, motionConstrainedPoint3.mRotationX)) {
            hashSet8.add("rotationX");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint2.rotationY, motionConstrainedPoint3.rotationY)) {
            hashSet8.add("rotationY");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint2.mPivotX, motionConstrainedPoint3.mPivotX)) {
            hashSet8.add("transformPivotX");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint2.mPivotY, motionConstrainedPoint3.mPivotY)) {
            hashSet8.add("transformPivotY");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint2.mScaleX, motionConstrainedPoint3.mScaleX)) {
            hashSet8.add("scaleX");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint2.mScaleY, motionConstrainedPoint3.mScaleY)) {
            hashSet8.add("scaleY");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint2.mTranslationX, motionConstrainedPoint3.mTranslationX)) {
            hashSet8.add("translationX");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint2.mTranslationY, motionConstrainedPoint3.mTranslationY)) {
            hashSet8.add("translationY");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint2.mTranslationZ, motionConstrainedPoint3.mTranslationZ)) {
            obj = "translationZ";
            hashSet8.add(obj);
        } else {
            obj = "translationZ";
        }
        MotionController motionController2 = this;
        ArrayList arrayList2 = motionController2.mKeyList;
        if (arrayList2 != null) {
            motionConstrainedPoint = motionConstrainedPoint2;
            int size = arrayList2.size();
            obj2 = "rotationX";
            ArrayList arrayList3 = null;
            int i10 = 0;
            while (i10 < size) {
                Object obj8 = arrayList2.get(i10);
                int i11 = i10 + 1;
                ArrayList arrayList4 = arrayList2;
                Key key = (Key) obj8;
                if (key instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) key;
                    MotionPaths motionPaths3 = new MotionPaths(i, i2, keyPosition, motionController2.mStartMotionPath, motionController2.mEndMotionPath);
                    i5 = size;
                    if (Collections.binarySearch(motionController2.mMotionPaths, motionPaths3) == 0) {
                        str37 = str42;
                        str36 = str39;
                        Log.e("MotionController", " KeyPath position \"" + motionPaths3.mPosition + "\" outside of range");
                    } else {
                        str36 = str39;
                        str37 = str42;
                    }
                    motionController2.mMotionPaths.add((-r15) - 1, motionPaths3);
                    int i12 = keyPosition.mCurveFit;
                    if (i12 != -1) {
                        motionController2.mCurveFitType = i12;
                    }
                    hashSet6 = hashSet10;
                    map3 = map4;
                    hashSet5 = hashSet11;
                } else {
                    str36 = str39;
                    str37 = str42;
                    i5 = size;
                    if (key instanceof KeyCycle) {
                        hashSet5 = hashSet11;
                        key.getAttributeNames(hashSet5);
                        hashSet6 = hashSet10;
                    } else {
                        hashSet5 = hashSet11;
                        if (key instanceof KeyTimeCycle) {
                            hashSet6 = hashSet10;
                            key.getAttributeNames(hashSet6);
                        } else {
                            hashSet6 = hashSet10;
                            if (key instanceof KeyTrigger) {
                                if (arrayList3 == null) {
                                    arrayList3 = new ArrayList();
                                }
                                ArrayList arrayList5 = arrayList3;
                                arrayList5.add((KeyTrigger) key);
                                arrayList3 = arrayList5;
                            } else {
                                map3 = map4;
                                key.setInterpolation(map3);
                                key.getAttributeNames(hashSet8);
                            }
                        }
                    }
                    map3 = map4;
                }
                hashSet11 = hashSet5;
                hashSet10 = hashSet6;
                map4 = map3;
                i10 = i11;
                arrayList2 = arrayList4;
                size = i5;
                str42 = str37;
                str39 = str36;
            }
            arrayList = arrayList3;
        } else {
            obj2 = "rotationX";
            motionConstrainedPoint = motionConstrainedPoint2;
            arrayList = null;
        }
        String str44 = str39;
        String str45 = str42;
        HashSet hashSet12 = hashSet10;
        HashMap map5 = map4;
        HashSet hashSet13 = hashSet11;
        if (arrayList != null) {
            motionController2.mKeyTriggers = (KeyTrigger[]) arrayList.toArray(new KeyTrigger[0]);
        }
        String str46 = ",";
        String str47 = "CUSTOM";
        String str48 = "CUSTOM,";
        if (hashSet8.isEmpty()) {
            hashSet = hashSet8;
            str = "CUSTOM,";
            str2 = "CUSTOM";
            hashSet2 = hashSet12;
            map = map5;
            str3 = ",";
            obj3 = obj2;
            str4 = str45;
            str5 = str44;
        } else {
            hashSet2 = hashSet12;
            motionController2.mAttributesMap = new HashMap();
            Iterator it5 = hashSet8.iterator();
            while (it5.hasNext()) {
                Iterator it6 = it5;
                String str49 = (String) it5.next();
                if (!str49.startsWith(str48)) {
                    hashSet4 = hashSet8;
                    str31 = str48;
                    str32 = str47;
                    map2 = map5;
                    str33 = str46;
                    switch (str49.hashCode()) {
                        case -1249320806:
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            if (str49.equals(obj7)) {
                                c11 = 0;
                                break;
                            } else {
                                c11 = 65535;
                                break;
                            }
                        case -1249320805:
                            str34 = str45;
                            str35 = str44;
                            if (str49.equals(str34)) {
                                c11 = 1;
                                obj7 = obj2;
                                break;
                            } else {
                                obj7 = obj2;
                                c11 = 65535;
                                break;
                            }
                        case -1225497657:
                            str35 = str44;
                            obj7 = obj2;
                            if (str49.equals(str35)) {
                                str34 = str45;
                                c11 = 2;
                                break;
                            } else {
                                str34 = str45;
                                c11 = 65535;
                                break;
                            }
                        case -1225497656:
                            if (str49.equals("translationY")) {
                                c11 = 3;
                                obj7 = obj2;
                                str34 = str45;
                                str35 = str44;
                                break;
                            }
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                        case -1225497655:
                            if (str49.equals(obj)) {
                                c11 = 4;
                                obj7 = obj2;
                                str34 = str45;
                                str35 = str44;
                                break;
                            }
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                        case -1001078227:
                            if (str49.equals("progress")) {
                                c11 = 5;
                                obj7 = obj2;
                                str34 = str45;
                                str35 = str44;
                                break;
                            }
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                        case -908189618:
                            if (str49.equals("scaleX")) {
                                obj7 = obj2;
                                str34 = str45;
                                str35 = str44;
                                c11 = 6;
                                break;
                            }
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                        case -908189617:
                            if (str49.equals("scaleY")) {
                                obj7 = obj2;
                                str34 = str45;
                                str35 = str44;
                                c11 = 7;
                                break;
                            }
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                        case -797520672:
                            if (str49.equals("waveVariesBy")) {
                                obj7 = obj2;
                                str34 = str45;
                                str35 = str44;
                                c11 = '\b';
                                break;
                            }
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                        case -760884510:
                            if (str49.equals("transformPivotX")) {
                                c12 = '\t';
                                c11 = c12;
                                obj7 = obj2;
                                str34 = str45;
                                str35 = str44;
                                break;
                            }
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                        case -760884509:
                            if (str49.equals("transformPivotY")) {
                                c12 = '\n';
                                c11 = c12;
                                obj7 = obj2;
                                str34 = str45;
                                str35 = str44;
                                break;
                            }
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                        case -40300674:
                            if (str49.equals("rotation")) {
                                c12 = 11;
                                c11 = c12;
                                obj7 = obj2;
                                str34 = str45;
                                str35 = str44;
                                break;
                            }
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                        case -4379043:
                            if (str49.equals("elevation")) {
                                c12 = '\f';
                                c11 = c12;
                                obj7 = obj2;
                                str34 = str45;
                                str35 = str44;
                                break;
                            }
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                        case 37232917:
                            if (str49.equals("transitionPathRotate")) {
                                c12 = '\r';
                                c11 = c12;
                                obj7 = obj2;
                                str34 = str45;
                                str35 = str44;
                                break;
                            }
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                        case 92909918:
                            if (str49.equals("alpha")) {
                                c12 = 14;
                                c11 = c12;
                                obj7 = obj2;
                                str34 = str45;
                                str35 = str44;
                                break;
                            }
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                        case 156108012:
                            if (str49.equals("waveOffset")) {
                                c12 = 15;
                                c11 = c12;
                                obj7 = obj2;
                                str34 = str45;
                                str35 = str44;
                                break;
                            }
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                        default:
                            obj7 = obj2;
                            str34 = str45;
                            str35 = str44;
                            c11 = 65535;
                            break;
                    }
                    switch (c11) {
                        case 0:
                            rotationXset3 = new ViewSpline.RotationXset();
                            break;
                        case 1:
                            rotationXset3 = new ViewSpline.RotationYset();
                            break;
                        case 2:
                            rotationXset3 = new ViewSpline.TranslationXset();
                            break;
                        case 3:
                            rotationXset3 = new ViewSpline.TranslationYset();
                            break;
                        case 4:
                            rotationXset3 = new ViewSpline.TranslationZset();
                            break;
                        case 5:
                            rotationXset3 = new ViewSpline.ProgressSet();
                            break;
                        case 6:
                            rotationXset3 = new ViewSpline.ScaleXset();
                            break;
                        case 7:
                            rotationXset3 = new ViewSpline.ScaleYset();
                            break;
                        case '\b':
                            rotationXset3 = new ViewSpline.AlphaSet();
                            break;
                        case '\t':
                            rotationXset3 = new ViewSpline.PivotXset();
                            break;
                        case '\n':
                            rotationXset3 = new ViewSpline.PivotYset();
                            break;
                        case 11:
                            rotationXset3 = new ViewSpline.RotationSet();
                            break;
                        case '\f':
                            rotationXset3 = new ViewSpline.ElevationSet();
                            break;
                        case '\r':
                            rotationXset3 = new ViewSpline.PathRotate();
                            break;
                        case 14:
                            rotationXset3 = new ViewSpline.AlphaSet();
                            break;
                        case 15:
                            rotationXset3 = new ViewSpline.AlphaSet();
                            break;
                        default:
                            rotationXset3 = null;
                            break;
                    }
                } else {
                    hashSet4 = hashSet8;
                    SparseArray sparseArray = new SparseArray();
                    str33 = str46;
                    String str50 = str49.split(str46)[1];
                    str31 = str48;
                    ArrayList arrayList6 = motionController2.mKeyList;
                    map2 = map5;
                    int size2 = arrayList6.size();
                    str32 = str47;
                    int i13 = 0;
                    while (i13 < size2) {
                        Object obj9 = arrayList6.get(i13);
                        int i14 = i13 + 1;
                        ArrayList arrayList7 = arrayList6;
                        Key key2 = (Key) obj9;
                        HashMap map6 = key2.mCustomConstraints;
                        if (map6 != null && (constraintAttribute3 = (ConstraintAttribute) map6.get(str50)) != null) {
                            sparseArray.append(key2.mFramePosition, constraintAttribute3);
                        }
                        i13 = i14;
                        arrayList6 = arrayList7;
                    }
                    rotationXset3 = new ViewSpline.CustomSet(str49, sparseArray);
                    obj7 = obj2;
                    str34 = str45;
                    str35 = str44;
                }
                if (rotationXset3 != null) {
                    rotationXset3.mType = str49;
                    motionController2.mAttributesMap.put(str49, rotationXset3);
                }
                it5 = it6;
                str44 = str35;
                str45 = str34;
                obj2 = obj7;
                hashSet8 = hashSet4;
                str48 = str31;
                str46 = str33;
                map5 = map2;
                str47 = str32;
            }
            hashSet = hashSet8;
            str = str48;
            str2 = str47;
            map = map5;
            str3 = str46;
            obj3 = obj2;
            str4 = str45;
            str5 = str44;
            ArrayList arrayList8 = motionController2.mKeyList;
            if (arrayList8 != null) {
                int size3 = arrayList8.size();
                for (int i15 = 0; i15 < size3; i15 = i4) {
                    Object obj10 = arrayList8.get(i15);
                    i4 = i15 + 1;
                    ArrayList arrayList9 = arrayList8;
                    Key key3 = (Key) obj10;
                    int i16 = size3;
                    if (key3 instanceof KeyAttributes) {
                        HashMap map7 = motionController2.mAttributesMap;
                        KeyAttributes keyAttributes = (KeyAttributes) key3;
                        keyAttributes.getClass();
                        for (String str51 : map7.keySet()) {
                            int i17 = i4;
                            HashMap map8 = map7;
                            SplineSet splineSet = (SplineSet) map7.get(str51);
                            if (splineSet != null) {
                                String str52 = str2;
                                if (!str51.startsWith(str52)) {
                                    str2 = str52;
                                    switch (str51.hashCode()) {
                                        case -1249320806:
                                            if (str51.equals(obj3)) {
                                                c10 = 0;
                                                break;
                                            } else {
                                                c10 = 65535;
                                                break;
                                            }
                                        case -1249320805:
                                            if (str51.equals(str4)) {
                                                c10 = 1;
                                                break;
                                            }
                                            break;
                                        case -1225497657:
                                            if (str51.equals(str5)) {
                                                c10 = 2;
                                                break;
                                            }
                                            break;
                                        case -1225497656:
                                            if (str51.equals("translationY")) {
                                                c10 = 3;
                                                break;
                                            }
                                            break;
                                        case -1225497655:
                                            if (str51.equals(obj)) {
                                                c10 = 4;
                                                break;
                                            }
                                            break;
                                        case -1001078227:
                                            if (str51.equals("progress")) {
                                                c10 = 5;
                                                break;
                                            }
                                            break;
                                        case -908189618:
                                            if (str51.equals("scaleX")) {
                                                c10 = 6;
                                                break;
                                            }
                                            break;
                                        case -908189617:
                                            if (str51.equals("scaleY")) {
                                                c10 = 7;
                                                break;
                                            }
                                            break;
                                        case -760884510:
                                            if (str51.equals("transformPivotX")) {
                                                c10 = '\b';
                                                break;
                                            }
                                            break;
                                        case -760884509:
                                            if (str51.equals("transformPivotY")) {
                                                c10 = '\t';
                                                break;
                                            }
                                            break;
                                        case -40300674:
                                            if (str51.equals("rotation")) {
                                                c10 = '\n';
                                                break;
                                            }
                                            break;
                                        case -4379043:
                                            if (str51.equals("elevation")) {
                                                c10 = 11;
                                                break;
                                            }
                                            break;
                                        case 37232917:
                                            if (str51.equals("transitionPathRotate")) {
                                                c10 = '\f';
                                                break;
                                            }
                                            break;
                                        case 92909918:
                                            if (str51.equals("alpha")) {
                                                c10 = '\r';
                                                break;
                                            }
                                            break;
                                    }
                                    switch (c10) {
                                        case 0:
                                            if (!Float.isNaN(keyAttributes.mRotationX)) {
                                                splineSet.setPoint(keyAttributes.mRotationX, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                        case 1:
                                            if (!Float.isNaN(keyAttributes.mRotationY)) {
                                                splineSet.setPoint(keyAttributes.mRotationY, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                        case 2:
                                            if (!Float.isNaN(keyAttributes.mTranslationX)) {
                                                splineSet.setPoint(keyAttributes.mTranslationX, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                        case 3:
                                            if (!Float.isNaN(keyAttributes.mTranslationY)) {
                                                splineSet.setPoint(keyAttributes.mTranslationY, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                        case 4:
                                            if (!Float.isNaN(keyAttributes.mTranslationZ)) {
                                                splineSet.setPoint(keyAttributes.mTranslationZ, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                        case 5:
                                            if (!Float.isNaN(keyAttributes.mProgress)) {
                                                splineSet.setPoint(keyAttributes.mProgress, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                        case 6:
                                            if (!Float.isNaN(keyAttributes.mScaleX)) {
                                                splineSet.setPoint(keyAttributes.mScaleX, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                        case 7:
                                            if (!Float.isNaN(keyAttributes.mScaleY)) {
                                                splineSet.setPoint(keyAttributes.mScaleY, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                        case '\b':
                                            if (!Float.isNaN(keyAttributes.mRotationX)) {
                                                splineSet.setPoint(keyAttributes.mPivotX, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                        case '\t':
                                            if (!Float.isNaN(keyAttributes.mRotationY)) {
                                                splineSet.setPoint(keyAttributes.mPivotY, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                        case '\n':
                                            if (!Float.isNaN(keyAttributes.mRotation)) {
                                                splineSet.setPoint(keyAttributes.mRotation, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                        case 11:
                                            if (!Float.isNaN(keyAttributes.mElevation)) {
                                                splineSet.setPoint(keyAttributes.mElevation, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                        case '\f':
                                            if (!Float.isNaN(keyAttributes.mTransitionPathRotate)) {
                                                splineSet.setPoint(keyAttributes.mTransitionPathRotate, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                        case '\r':
                                            if (!Float.isNaN(keyAttributes.mAlpha)) {
                                                splineSet.setPoint(keyAttributes.mAlpha, keyAttributes.mFramePosition);
                                                break;
                                            }
                                            break;
                                    }
                                } else {
                                    str2 = str52;
                                    ConstraintAttribute constraintAttribute4 = (ConstraintAttribute) keyAttributes.mCustomConstraints.get(str51.substring(7));
                                    if (constraintAttribute4 != null) {
                                        ((ViewSpline.CustomSet) splineSet).mConstraintAttributeList.append(keyAttributes.mFramePosition, constraintAttribute4);
                                    }
                                }
                            }
                            i4 = i17;
                            map7 = map8;
                        }
                    }
                    motionController2 = this;
                    size3 = i16;
                    arrayList8 = arrayList9;
                }
            }
            motionController2 = this;
            motionConstrainedPoint.addValues(0, motionController2.mAttributesMap);
            motionConstrainedPoint3.addValues(100, motionController2.mAttributesMap);
            Iterator it7 = motionController2.mAttributesMap.keySet().iterator();
            while (it7.hasNext()) {
                String str53 = (String) it7.next();
                HashMap map9 = map;
                if (!map9.containsKey(str53) || (num = (Integer) map9.get(str53)) == null) {
                    it4 = it7;
                    iIntValue2 = 0;
                } else {
                    it4 = it7;
                    iIntValue2 = num.intValue();
                }
                map = map9;
                SplineSet splineSet2 = (SplineSet) motionController2.mAttributesMap.get(str53);
                if (splineSet2 != null) {
                    splineSet2.setup(iIntValue2);
                }
                it7 = it4;
            }
        }
        if (hashSet2.isEmpty()) {
            motionController = motionController2;
            str6 = str4;
            str7 = str5;
        } else {
            if (motionController2.mTimeCycleAttributesMap == null) {
                motionController2.mTimeCycleAttributesMap = new HashMap();
            }
            Iterator it8 = hashSet2.iterator();
            while (it8.hasNext()) {
                String str54 = (String) it8.next();
                if (!motionController2.mTimeCycleAttributesMap.containsKey(str54)) {
                    String str55 = str;
                    if (str54.startsWith(str55)) {
                        it3 = it8;
                        SparseArray sparseArray2 = new SparseArray();
                        str = str55;
                        String str56 = str54.split(str3)[1];
                        Object obj11 = obj3;
                        ArrayList arrayList10 = motionController2.mKeyList;
                        int size4 = arrayList10.size();
                        str30 = str4;
                        int i18 = 0;
                        while (i18 < size4) {
                            Object obj12 = arrayList10.get(i18);
                            int i19 = i18 + 1;
                            Key key4 = (Key) obj12;
                            int i20 = size4;
                            HashMap map10 = key4.mCustomConstraints;
                            if (map10 != null && (constraintAttribute2 = (ConstraintAttribute) map10.get(str56)) != null) {
                                sparseArray2.append(key4.mFramePosition, constraintAttribute2);
                            }
                            size4 = i20;
                            i18 = i19;
                        }
                        str29 = str5;
                        rotationXset2 = new ViewTimeCycle.CustomSet(str54, sparseArray2);
                        obj3 = obj11;
                    } else {
                        String str57 = str4;
                        Object obj13 = obj3;
                        it3 = it8;
                        str = str55;
                        switch (str54.hashCode()) {
                            case -1249320806:
                                obj3 = obj13;
                                str28 = str57;
                                if (str54.equals(obj3)) {
                                    c8 = 0;
                                    break;
                                } else {
                                    c8 = 65535;
                                    break;
                                }
                            case -1249320805:
                                str28 = str57;
                                if (str54.equals(str28)) {
                                    c8 = 1;
                                    obj3 = obj13;
                                    break;
                                } else {
                                    obj3 = obj13;
                                    c8 = 65535;
                                    break;
                                }
                            case -1225497657:
                                if (str54.equals(str5)) {
                                    obj3 = obj13;
                                    str28 = str57;
                                    c8 = 2;
                                    break;
                                }
                                obj3 = obj13;
                                str28 = str57;
                                c8 = 65535;
                                break;
                            case -1225497656:
                                if (str54.equals("translationY")) {
                                    c8 = 3;
                                    obj3 = obj13;
                                    str28 = str57;
                                    break;
                                }
                                obj3 = obj13;
                                str28 = str57;
                                c8 = 65535;
                                break;
                            case -1225497655:
                                if (str54.equals(obj)) {
                                    c8 = 4;
                                    obj3 = obj13;
                                    str28 = str57;
                                    break;
                                }
                                obj3 = obj13;
                                str28 = str57;
                                c8 = 65535;
                                break;
                            case -1001078227:
                                if (str54.equals("progress")) {
                                    c8 = 5;
                                    obj3 = obj13;
                                    str28 = str57;
                                    break;
                                }
                                obj3 = obj13;
                                str28 = str57;
                                c8 = 65535;
                                break;
                            case -908189618:
                                if (str54.equals("scaleX")) {
                                    obj3 = obj13;
                                    str28 = str57;
                                    c8 = 6;
                                    break;
                                }
                                obj3 = obj13;
                                str28 = str57;
                                c8 = 65535;
                                break;
                            case -908189617:
                                if (str54.equals("scaleY")) {
                                    obj3 = obj13;
                                    str28 = str57;
                                    c8 = 7;
                                    break;
                                }
                                obj3 = obj13;
                                str28 = str57;
                                c8 = 65535;
                                break;
                            case -40300674:
                                if (str54.equals("rotation")) {
                                    obj3 = obj13;
                                    str28 = str57;
                                    c8 = '\b';
                                    break;
                                }
                                obj3 = obj13;
                                str28 = str57;
                                c8 = 65535;
                                break;
                            case -4379043:
                                if (str54.equals("elevation")) {
                                    c9 = '\t';
                                    c8 = c9;
                                    obj3 = obj13;
                                    str28 = str57;
                                    break;
                                }
                                obj3 = obj13;
                                str28 = str57;
                                c8 = 65535;
                                break;
                            case 37232917:
                                if (str54.equals("transitionPathRotate")) {
                                    c9 = '\n';
                                    c8 = c9;
                                    obj3 = obj13;
                                    str28 = str57;
                                    break;
                                }
                                obj3 = obj13;
                                str28 = str57;
                                c8 = 65535;
                                break;
                            case 92909918:
                                if (str54.equals("alpha")) {
                                    c9 = 11;
                                    c8 = c9;
                                    obj3 = obj13;
                                    str28 = str57;
                                    break;
                                }
                                obj3 = obj13;
                                str28 = str57;
                                c8 = 65535;
                                break;
                            default:
                                obj3 = obj13;
                                str28 = str57;
                                c8 = 65535;
                                break;
                        }
                        switch (c8) {
                            case 0:
                                rotationXset2 = new ViewTimeCycle.RotationXset();
                                str29 = str5;
                                str30 = str28;
                                rotationXset2.mLastTime = j;
                                break;
                            case 1:
                                rotationXset2 = new ViewTimeCycle.RotationYset();
                                str29 = str5;
                                str30 = str28;
                                rotationXset2.mLastTime = j;
                                break;
                            case 2:
                                rotationXset2 = new ViewTimeCycle.TranslationXset();
                                str29 = str5;
                                str30 = str28;
                                rotationXset2.mLastTime = j;
                                break;
                            case 3:
                                rotationXset2 = new ViewTimeCycle.TranslationYset();
                                str29 = str5;
                                str30 = str28;
                                rotationXset2.mLastTime = j;
                                break;
                            case 4:
                                rotationXset2 = new ViewTimeCycle.TranslationZset();
                                str29 = str5;
                                str30 = str28;
                                rotationXset2.mLastTime = j;
                                break;
                            case 5:
                                rotationXset2 = new ViewTimeCycle.ProgressSet();
                                str29 = str5;
                                str30 = str28;
                                rotationXset2.mLastTime = j;
                                break;
                            case 6:
                                rotationXset2 = new ViewTimeCycle.ScaleXset();
                                str29 = str5;
                                str30 = str28;
                                rotationXset2.mLastTime = j;
                                break;
                            case 7:
                                rotationXset2 = new ViewTimeCycle.ScaleYset();
                                str29 = str5;
                                str30 = str28;
                                rotationXset2.mLastTime = j;
                                break;
                            case '\b':
                                rotationXset2 = new ViewTimeCycle.RotationSet();
                                str29 = str5;
                                str30 = str28;
                                rotationXset2.mLastTime = j;
                                break;
                            case '\t':
                                rotationXset2 = new ViewTimeCycle.ElevationSet();
                                str29 = str5;
                                str30 = str28;
                                rotationXset2.mLastTime = j;
                                break;
                            case '\n':
                                rotationXset2 = new ViewTimeCycle.PathRotate();
                                str29 = str5;
                                str30 = str28;
                                rotationXset2.mLastTime = j;
                                break;
                            case 11:
                                rotationXset2 = new ViewTimeCycle.AlphaSet();
                                str29 = str5;
                                str30 = str28;
                                rotationXset2.mLastTime = j;
                                break;
                            default:
                                str29 = str5;
                                str30 = str28;
                                rotationXset2 = null;
                                break;
                        }
                        if (rotationXset2 != null) {
                            motionController2 = this;
                            it8 = it3;
                            str4 = str30;
                            str5 = str29;
                        } else {
                            rotationXset2.mType = str54;
                            this.mTimeCycleAttributesMap.put(str54, rotationXset2);
                            motionController2 = this;
                            str4 = str30;
                            str5 = str29;
                            it8 = it3;
                        }
                    }
                    if (rotationXset2 != null) {
                    }
                }
            }
            String str58 = str5;
            String str59 = str4;
            MotionController motionController3 = motionController2;
            ArrayList arrayList11 = motionController3.mKeyList;
            if (arrayList11 != null) {
                int size5 = arrayList11.size();
                for (int i21 = 0; i21 < size5; i21 = i3) {
                    Object obj14 = arrayList11.get(i21);
                    i3 = i21 + 1;
                    Key key5 = (Key) obj14;
                    if (key5 instanceof KeyTimeCycle) {
                        KeyTimeCycle keyTimeCycle = (KeyTimeCycle) key5;
                        HashMap map11 = motionController3.mTimeCycleAttributesMap;
                        keyTimeCycle.getClass();
                        for (String str60 : map11.keySet()) {
                            ArrayList arrayList12 = arrayList11;
                            ViewTimeCycle viewTimeCycle = (ViewTimeCycle) map11.get(str60);
                            if (viewTimeCycle != null) {
                                int i22 = size5;
                                String str61 = str2;
                                if (str60.startsWith(str61)) {
                                    int i23 = i3;
                                    ConstraintAttribute constraintAttribute5 = (ConstraintAttribute) keyTimeCycle.mCustomConstraints.get(str60.substring(7));
                                    if (constraintAttribute5 != null) {
                                        ViewTimeCycle.CustomSet customSet2 = (ViewTimeCycle.CustomSet) viewTimeCycle;
                                        HashMap map12 = map11;
                                        int i24 = keyTimeCycle.mFramePosition;
                                        str2 = str61;
                                        float f4 = keyTimeCycle.mWavePeriod;
                                        int i25 = keyTimeCycle.mWaveShape;
                                        float f5 = keyTimeCycle.mWaveOffset;
                                        customSet2.mConstraintAttributeList.append(i24, constraintAttribute5);
                                        customSet2.mWaveProperties.append(i24, new float[]{f4, f5});
                                        customSet2.mWaveShape = Math.max(customSet2.mWaveShape, i25);
                                        size5 = i22;
                                        i3 = i23;
                                        keyTimeCycle = keyTimeCycle;
                                        map11 = map12;
                                    } else {
                                        i3 = i23;
                                        str2 = str61;
                                        arrayList11 = arrayList12;
                                        size5 = i22;
                                    }
                                } else {
                                    str2 = str61;
                                    int i26 = i3;
                                    KeyTimeCycle keyTimeCycle2 = keyTimeCycle;
                                    HashMap map13 = map11;
                                    switch (str60.hashCode()) {
                                        case -1249320806:
                                            str26 = str59;
                                            str27 = str58;
                                            if (str60.equals(obj3)) {
                                                c6 = 0;
                                                break;
                                            } else {
                                                c6 = 65535;
                                                break;
                                            }
                                        case -1249320805:
                                            str26 = str59;
                                            str27 = str58;
                                            if (str60.equals(str26)) {
                                                c6 = 1;
                                                break;
                                            }
                                            break;
                                        case -1225497657:
                                            str27 = str58;
                                            if (str60.equals(str27)) {
                                                str26 = str59;
                                                c6 = 2;
                                                break;
                                            } else {
                                                str26 = str59;
                                                c6 = 65535;
                                                break;
                                            }
                                        case -1225497656:
                                            if (str60.equals("translationY")) {
                                                c6 = 3;
                                                str26 = str59;
                                                str27 = str58;
                                                break;
                                            }
                                            str26 = str59;
                                            str27 = str58;
                                            c6 = 65535;
                                            break;
                                        case -1225497655:
                                            if (str60.equals(obj)) {
                                                c6 = 4;
                                                str26 = str59;
                                                str27 = str58;
                                                break;
                                            }
                                            str26 = str59;
                                            str27 = str58;
                                            c6 = 65535;
                                            break;
                                        case -1001078227:
                                            if (str60.equals("progress")) {
                                                c6 = 5;
                                                str26 = str59;
                                                str27 = str58;
                                                break;
                                            }
                                            str26 = str59;
                                            str27 = str58;
                                            c6 = 65535;
                                            break;
                                        case -908189618:
                                            if (str60.equals("scaleX")) {
                                                str26 = str59;
                                                str27 = str58;
                                                c6 = 6;
                                                break;
                                            }
                                            str26 = str59;
                                            str27 = str58;
                                            c6 = 65535;
                                            break;
                                        case -908189617:
                                            if (str60.equals("scaleY")) {
                                                str26 = str59;
                                                str27 = str58;
                                                c6 = 7;
                                                break;
                                            }
                                            str26 = str59;
                                            str27 = str58;
                                            c6 = 65535;
                                            break;
                                        case -40300674:
                                            if (str60.equals("rotation")) {
                                                str26 = str59;
                                                str27 = str58;
                                                c6 = '\b';
                                                break;
                                            }
                                            str26 = str59;
                                            str27 = str58;
                                            c6 = 65535;
                                            break;
                                        case -4379043:
                                            if (str60.equals("elevation")) {
                                                c7 = '\t';
                                                c6 = c7;
                                                str26 = str59;
                                                str27 = str58;
                                                break;
                                            }
                                            str26 = str59;
                                            str27 = str58;
                                            c6 = 65535;
                                            break;
                                        case 37232917:
                                            if (str60.equals("transitionPathRotate")) {
                                                c7 = '\n';
                                                c6 = c7;
                                                str26 = str59;
                                                str27 = str58;
                                                break;
                                            }
                                            str26 = str59;
                                            str27 = str58;
                                            c6 = 65535;
                                            break;
                                        case 92909918:
                                            if (str60.equals("alpha")) {
                                                c7 = 11;
                                                c6 = c7;
                                                str26 = str59;
                                                str27 = str58;
                                                break;
                                            }
                                            str26 = str59;
                                            str27 = str58;
                                            c6 = 65535;
                                            break;
                                        default:
                                            str26 = str59;
                                            str27 = str58;
                                            c6 = 65535;
                                            break;
                                    }
                                    switch (c6) {
                                        case 0:
                                            keyTimeCycle = keyTimeCycle2;
                                            if (!Float.isNaN(keyTimeCycle.mRotationX)) {
                                                viewTimeCycle.setPoint(keyTimeCycle.mRotationX, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                                break;
                                            }
                                            break;
                                        case 1:
                                            keyTimeCycle = keyTimeCycle2;
                                            if (!Float.isNaN(keyTimeCycle.mRotationY)) {
                                                viewTimeCycle.setPoint(keyTimeCycle.mRotationY, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                                break;
                                            }
                                            break;
                                        case 2:
                                            keyTimeCycle = keyTimeCycle2;
                                            if (!Float.isNaN(keyTimeCycle.mTranslationX)) {
                                                viewTimeCycle.setPoint(keyTimeCycle.mTranslationX, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                                break;
                                            }
                                            break;
                                        case 3:
                                            keyTimeCycle = keyTimeCycle2;
                                            if (!Float.isNaN(keyTimeCycle.mTranslationY)) {
                                                viewTimeCycle.setPoint(keyTimeCycle.mTranslationY, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                                break;
                                            }
                                            break;
                                        case 4:
                                            keyTimeCycle = keyTimeCycle2;
                                            if (!Float.isNaN(keyTimeCycle.mTranslationZ)) {
                                                viewTimeCycle.setPoint(keyTimeCycle.mTranslationZ, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                                break;
                                            }
                                            break;
                                        case 5:
                                            keyTimeCycle = keyTimeCycle2;
                                            if (!Float.isNaN(keyTimeCycle.mProgress)) {
                                                viewTimeCycle.setPoint(keyTimeCycle.mProgress, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                                break;
                                            }
                                            break;
                                        case 6:
                                            keyTimeCycle = keyTimeCycle2;
                                            if (!Float.isNaN(keyTimeCycle.mScaleX)) {
                                                viewTimeCycle.setPoint(keyTimeCycle.mScaleX, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                                break;
                                            }
                                            break;
                                        case 7:
                                            keyTimeCycle = keyTimeCycle2;
                                            if (!Float.isNaN(keyTimeCycle.mScaleY)) {
                                                viewTimeCycle.setPoint(keyTimeCycle.mScaleY, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                                break;
                                            }
                                            break;
                                        case '\b':
                                            keyTimeCycle = keyTimeCycle2;
                                            if (!Float.isNaN(keyTimeCycle.mRotation)) {
                                                viewTimeCycle.setPoint(keyTimeCycle.mRotation, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                                break;
                                            }
                                            break;
                                        case '\t':
                                            keyTimeCycle = keyTimeCycle2;
                                            if (!Float.isNaN(keyTimeCycle.mElevation)) {
                                                viewTimeCycle.setPoint(keyTimeCycle.mElevation, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                                break;
                                            }
                                            break;
                                        case '\n':
                                            keyTimeCycle = keyTimeCycle2;
                                            if (!Float.isNaN(keyTimeCycle.mTransitionPathRotate)) {
                                                viewTimeCycle.setPoint(keyTimeCycle.mTransitionPathRotate, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                                break;
                                            }
                                            break;
                                        case 11:
                                            keyTimeCycle = keyTimeCycle2;
                                            if (!Float.isNaN(keyTimeCycle.mAlpha)) {
                                                viewTimeCycle.setPoint(keyTimeCycle.mAlpha, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                                break;
                                            }
                                            break;
                                        default:
                                            Log.e("KeyTimeCycles", "UNKNOWN addValues \"" + str60 + "\"");
                                            keyTimeCycle = keyTimeCycle2;
                                            break;
                                    }
                                    str58 = str27;
                                    str59 = str26;
                                    map11 = map13;
                                    arrayList11 = arrayList12;
                                    size5 = i22;
                                    i3 = i26;
                                }
                            }
                            arrayList11 = arrayList12;
                        }
                    }
                    motionController3 = this;
                    str58 = str58;
                    str59 = str59;
                    arrayList11 = arrayList11;
                    size5 = size5;
                }
            }
            str6 = str59;
            str7 = str58;
            motionController = this;
            Iterator it9 = motionController.mTimeCycleAttributesMap.keySet().iterator();
            while (it9.hasNext()) {
                String str62 = (String) it9.next();
                HashMap map14 = map;
                if (map14.containsKey(str62)) {
                    it2 = it9;
                    iIntValue = ((Integer) map14.get(str62)).intValue();
                } else {
                    it2 = it9;
                    iIntValue = 0;
                }
                map = map14;
                ((ViewTimeCycle) motionController.mTimeCycleAttributesMap.get(str62)).setup(iIntValue);
                it9 = it2;
            }
        }
        int size6 = motionController.mMotionPaths.size();
        int i27 = size6 + 2;
        MotionPaths[] motionPathsArr = new MotionPaths[i27];
        motionPathsArr[0] = motionPaths2;
        int i28 = size6 + 1;
        MotionPaths motionPaths4 = motionController.mEndMotionPath;
        motionPathsArr[i28] = motionPaths4;
        if (motionController.mMotionPaths.size() > 0) {
            obj4 = obj3;
            if (motionController.mCurveFitType == -1) {
                motionController.mCurveFitType = 0;
            }
        } else {
            obj4 = obj3;
        }
        ArrayList arrayList13 = motionController.mMotionPaths;
        int size7 = arrayList13.size();
        String str63 = str6;
        int i29 = 1;
        int i30 = 0;
        while (i30 < size7) {
            Object obj15 = arrayList13.get(i30);
            i30++;
            motionPathsArr[i29] = (MotionPaths) obj15;
            i29++;
        }
        HashSet hashSet14 = new HashSet();
        Iterator it10 = motionPaths4.mAttributes.keySet().iterator();
        while (it10.hasNext()) {
            String str64 = (String) it10.next();
            Iterator it11 = it10;
            MotionPaths motionPaths5 = motionPaths2;
            if (motionPaths5.mAttributes.containsKey(str64)) {
                motionPaths2 = motionPaths5;
                hashSet3 = hashSet;
                if (!hashSet3.contains(str + str64)) {
                    hashSet14.add(str64);
                }
            } else {
                motionPaths2 = motionPaths5;
                hashSet3 = hashSet;
            }
            it10 = it11;
            hashSet = hashSet3;
        }
        String[] strArr = (String[]) hashSet14.toArray(new String[0]);
        motionController.mAttributeNames = strArr;
        motionController.mAttributeInterpolatorCount = new int[strArr.length];
        int i31 = 0;
        while (true) {
            String[] strArr2 = motionController.mAttributeNames;
            if (i31 < strArr2.length) {
                String str65 = strArr2[i31];
                motionController.mAttributeInterpolatorCount[i31] = 0;
                int i32 = 0;
                while (true) {
                    if (i32 >= i27) {
                        break;
                    }
                    if (!motionPathsArr[i32].mAttributes.containsKey(str65) || (constraintAttribute = (ConstraintAttribute) motionPathsArr[i32].mAttributes.get(str65)) == null) {
                        i32++;
                    } else {
                        int[] iArr = motionController.mAttributeInterpolatorCount;
                        iArr[i31] = constraintAttribute.numberOfInterpolatedValues() + iArr[i31];
                    }
                }
                i31++;
            } else {
                boolean z = motionPathsArr[0].mPathMotionArc != -1;
                int length = 18 + strArr2.length;
                boolean[] zArr = new boolean[length];
                int i33 = 1;
                while (i33 < i27) {
                    boolean z2 = z;
                    MotionPaths motionPaths6 = motionPathsArr[i33];
                    boolean[] zArr2 = zArr;
                    MotionPaths motionPaths7 = motionPathsArr[i33 - 1];
                    int i34 = i33;
                    String str66 = str7;
                    boolean zDiff = MotionPaths.diff(motionPaths6.mX, motionPaths7.mX);
                    boolean zDiff2 = MotionPaths.diff(motionPaths6.mY, motionPaths7.mY);
                    zArr2[0] = zArr2[0] | MotionPaths.diff(motionPaths6.mPosition, motionPaths7.mPosition);
                    boolean z3 = zDiff | zDiff2 | z2;
                    zArr2[1] = zArr2[1] | z3;
                    zArr2[2] = zArr2[2] | z3;
                    zArr2[3] = zArr2[3] | MotionPaths.diff(motionPaths6.mWidth, motionPaths7.mWidth);
                    zArr2[4] = MotionPaths.diff(motionPaths6.mHeight, motionPaths7.mHeight) | zArr2[4];
                    i33 = i34 + 1;
                    z = z2;
                    zArr = zArr2;
                    str7 = str66;
                }
                String str67 = str7;
                boolean[] zArr3 = zArr;
                int i35 = 0;
                for (int i36 = 1; i36 < length; i36++) {
                    if (zArr3[i36]) {
                        i35++;
                    }
                }
                motionController.mInterpolateVariables = new int[i35];
                int iMax = Math.max(2, i35);
                motionController.mInterpolateData = new double[iMax];
                motionController.mInterpolateVelocity = new double[iMax];
                int i37 = 0;
                for (int i38 = 1; i38 < length; i38++) {
                    if (zArr3[i38]) {
                        motionController.mInterpolateVariables[i37] = i38;
                        i37++;
                    }
                }
                double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i27, motionController.mInterpolateVariables.length);
                double[] dArr2 = new double[i27];
                int i39 = 0;
                while (i39 < i27) {
                    MotionPaths motionPaths8 = motionPathsArr[i39];
                    double[] dArr3 = dArr[i39];
                    int i40 = i39;
                    int[] iArr2 = motionController.mInterpolateVariables;
                    float[] fArr = {motionPaths8.mPosition, motionPaths8.mX, motionPaths8.mY, motionPaths8.mWidth, motionPaths8.mHeight, motionPaths8.mPathRotate};
                    int i41 = 0;
                    int i42 = 0;
                    while (i41 < iArr2.length) {
                        int[] iArr3 = iArr2;
                        if (iArr2[i41] < 6) {
                            str25 = str38;
                            dArr3[i42] = fArr[r9];
                            i42++;
                        } else {
                            str25 = str38;
                        }
                        i41++;
                        iArr2 = iArr3;
                        str38 = str25;
                    }
                    dArr2[i40] = motionPathsArr[i40].mTime;
                    i39 = i40 + 1;
                    str38 = str38;
                }
                String str68 = str38;
                int i43 = 0;
                while (true) {
                    int[] iArr4 = motionController.mInterpolateVariables;
                    if (i43 < iArr4.length) {
                        if (iArr4[i43] < 6) {
                            String strM = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), MotionPaths.sNames[motionController.mInterpolateVariables[i43]], " [");
                            int i44 = 0;
                            while (i44 < i27) {
                                StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
                                sbM.append(dArr[i44][i43]);
                                strM = sbM.toString();
                                i44++;
                                str43 = str43;
                            }
                        }
                        i43++;
                        str43 = str43;
                    } else {
                        String str69 = str43;
                        motionController.mSpline = new CurveFit[motionController.mAttributeNames.length + 1];
                        int i45 = 0;
                        while (true) {
                            String[] strArr3 = motionController.mAttributeNames;
                            if (i45 >= strArr3.length) {
                                String str70 = str40;
                                String str71 = str41;
                                motionController.mSpline[0] = CurveFit.get(motionController.mCurveFitType, dArr2, dArr);
                                if (motionPathsArr[0].mPathMotionArc != -1) {
                                    int[] iArr5 = new int[i27];
                                    double[] dArr4 = new double[i27];
                                    double[][] dArr5 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i27, 2);
                                    for (int i46 = 0; i46 < i27; i46++) {
                                        iArr5[i46] = motionPathsArr[i46].mPathMotionArc;
                                        dArr4[i46] = r5.mTime;
                                        double[] dArr6 = dArr5[i46];
                                        dArr6[0] = r5.mX;
                                        dArr6[1] = r5.mY;
                                    }
                                    motionController.mArcSpline = new ArcCurveFit(iArr5, dArr4, dArr5);
                                }
                                motionController.mCycleMap = new HashMap();
                                if (motionController.mKeyList != null) {
                                    Iterator it12 = hashSet13.iterator();
                                    float f6 = Float.NaN;
                                    while (it12.hasNext()) {
                                        String str72 = (String) it12.next();
                                        String str73 = str2;
                                        if (!str72.startsWith(str73)) {
                                            switch (str72.hashCode()) {
                                                case -1249320806:
                                                    it = it12;
                                                    str11 = str69;
                                                    obj6 = obj4;
                                                    str12 = str70;
                                                    str13 = str68;
                                                    str14 = str71;
                                                    str15 = str63;
                                                    str16 = str67;
                                                    if (str72.equals(obj6)) {
                                                        c3 = 0;
                                                        break;
                                                    } else {
                                                        c3 = 65535;
                                                        break;
                                                    }
                                                case -1249320805:
                                                    str11 = str69;
                                                    str12 = str70;
                                                    str13 = str68;
                                                    str14 = str71;
                                                    str15 = str63;
                                                    str16 = str67;
                                                    it = it12;
                                                    obj6 = obj4;
                                                    if (str72.equals(str15)) {
                                                        c3 = 1;
                                                        break;
                                                    }
                                                    break;
                                                case -1225497657:
                                                    str11 = str69;
                                                    str12 = str70;
                                                    str13 = str68;
                                                    str14 = str71;
                                                    str16 = str67;
                                                    it = it12;
                                                    obj6 = obj4;
                                                    str15 = str63;
                                                    if (str72.equals(str16)) {
                                                        c3 = 2;
                                                        break;
                                                    }
                                                    break;
                                                case -1225497656:
                                                    str11 = str69;
                                                    str12 = str70;
                                                    str13 = str68;
                                                    str14 = str71;
                                                    it = it12;
                                                    if (str72.equals(str13)) {
                                                        c3 = 3;
                                                        obj6 = obj4;
                                                        str15 = str63;
                                                        str16 = str67;
                                                        break;
                                                    } else {
                                                        obj6 = obj4;
                                                        str15 = str63;
                                                        str16 = str67;
                                                        c3 = 65535;
                                                        break;
                                                    }
                                                case -1225497655:
                                                    str11 = str69;
                                                    str12 = str70;
                                                    str14 = str71;
                                                    if (str72.equals(obj)) {
                                                        it = it12;
                                                        c3 = 4;
                                                        obj6 = obj4;
                                                        str13 = str68;
                                                        str15 = str63;
                                                        str16 = str67;
                                                        break;
                                                    }
                                                    it = it12;
                                                    obj6 = obj4;
                                                    str13 = str68;
                                                    str15 = str63;
                                                    str16 = str67;
                                                    c3 = 65535;
                                                    break;
                                                case -1001078227:
                                                    str11 = str69;
                                                    str12 = str70;
                                                    str14 = str71;
                                                    if (str72.equals(str11)) {
                                                        it = it12;
                                                        c3 = 5;
                                                        obj6 = obj4;
                                                        str13 = str68;
                                                        str15 = str63;
                                                        str16 = str67;
                                                        break;
                                                    }
                                                    it = it12;
                                                    obj6 = obj4;
                                                    str13 = str68;
                                                    str15 = str63;
                                                    str16 = str67;
                                                    c3 = 65535;
                                                    break;
                                                case -908189618:
                                                    str12 = str70;
                                                    str14 = str71;
                                                    it = it12;
                                                    if (str72.equals(str14)) {
                                                        str11 = str69;
                                                        obj6 = obj4;
                                                        str13 = str68;
                                                        str15 = str63;
                                                        str16 = str67;
                                                        c3 = 6;
                                                        break;
                                                    } else {
                                                        str11 = str69;
                                                        obj6 = obj4;
                                                        str13 = str68;
                                                        str15 = str63;
                                                        str16 = str67;
                                                        c3 = 65535;
                                                        break;
                                                    }
                                                case -908189617:
                                                    str12 = str70;
                                                    it = it12;
                                                    str11 = str69;
                                                    obj6 = obj4;
                                                    str13 = str68;
                                                    if (str72.equals(str12)) {
                                                        str14 = str71;
                                                        str15 = str63;
                                                        str16 = str67;
                                                        c3 = 7;
                                                        break;
                                                    }
                                                    str14 = str71;
                                                    str15 = str63;
                                                    str16 = str67;
                                                    c3 = 65535;
                                                    break;
                                                case -797520672:
                                                    if (str72.equals("waveVariesBy")) {
                                                        it = it12;
                                                        str11 = str69;
                                                        obj6 = obj4;
                                                        str12 = str70;
                                                        str13 = str68;
                                                        str14 = str71;
                                                        str15 = str63;
                                                        str16 = str67;
                                                        c3 = '\b';
                                                        break;
                                                    }
                                                    it = it12;
                                                    str11 = str69;
                                                    obj6 = obj4;
                                                    str12 = str70;
                                                    str13 = str68;
                                                    str14 = str71;
                                                    str15 = str63;
                                                    str16 = str67;
                                                    c3 = 65535;
                                                    break;
                                                case -40300674:
                                                    if (str72.equals("rotation")) {
                                                        c4 = '\t';
                                                        it = it12;
                                                        c3 = c4;
                                                        str11 = str69;
                                                        obj6 = obj4;
                                                        str12 = str70;
                                                        str13 = str68;
                                                        str14 = str71;
                                                        str15 = str63;
                                                        str16 = str67;
                                                        break;
                                                    }
                                                    it = it12;
                                                    str11 = str69;
                                                    obj6 = obj4;
                                                    str12 = str70;
                                                    str13 = str68;
                                                    str14 = str71;
                                                    str15 = str63;
                                                    str16 = str67;
                                                    c3 = 65535;
                                                    break;
                                                case -4379043:
                                                    if (str72.equals("elevation")) {
                                                        c4 = '\n';
                                                        it = it12;
                                                        c3 = c4;
                                                        str11 = str69;
                                                        obj6 = obj4;
                                                        str12 = str70;
                                                        str13 = str68;
                                                        str14 = str71;
                                                        str15 = str63;
                                                        str16 = str67;
                                                        break;
                                                    }
                                                    it = it12;
                                                    str11 = str69;
                                                    obj6 = obj4;
                                                    str12 = str70;
                                                    str13 = str68;
                                                    str14 = str71;
                                                    str15 = str63;
                                                    str16 = str67;
                                                    c3 = 65535;
                                                    break;
                                                case 37232917:
                                                    if (str72.equals("transitionPathRotate")) {
                                                        c4 = 11;
                                                        it = it12;
                                                        c3 = c4;
                                                        str11 = str69;
                                                        obj6 = obj4;
                                                        str12 = str70;
                                                        str13 = str68;
                                                        str14 = str71;
                                                        str15 = str63;
                                                        str16 = str67;
                                                        break;
                                                    }
                                                    it = it12;
                                                    str11 = str69;
                                                    obj6 = obj4;
                                                    str12 = str70;
                                                    str13 = str68;
                                                    str14 = str71;
                                                    str15 = str63;
                                                    str16 = str67;
                                                    c3 = 65535;
                                                    break;
                                                case 92909918:
                                                    if (str72.equals("alpha")) {
                                                        c4 = '\f';
                                                        it = it12;
                                                        c3 = c4;
                                                        str11 = str69;
                                                        obj6 = obj4;
                                                        str12 = str70;
                                                        str13 = str68;
                                                        str14 = str71;
                                                        str15 = str63;
                                                        str16 = str67;
                                                        break;
                                                    }
                                                    it = it12;
                                                    str11 = str69;
                                                    obj6 = obj4;
                                                    str12 = str70;
                                                    str13 = str68;
                                                    str14 = str71;
                                                    str15 = str63;
                                                    str16 = str67;
                                                    c3 = 65535;
                                                    break;
                                                case 156108012:
                                                    if (str72.equals("waveOffset")) {
                                                        c4 = '\r';
                                                        it = it12;
                                                        c3 = c4;
                                                        str11 = str69;
                                                        obj6 = obj4;
                                                        str12 = str70;
                                                        str13 = str68;
                                                        str14 = str71;
                                                        str15 = str63;
                                                        str16 = str67;
                                                        break;
                                                    }
                                                    it = it12;
                                                    str11 = str69;
                                                    obj6 = obj4;
                                                    str12 = str70;
                                                    str13 = str68;
                                                    str14 = str71;
                                                    str15 = str63;
                                                    str16 = str67;
                                                    c3 = 65535;
                                                    break;
                                                default:
                                                    it = it12;
                                                    str11 = str69;
                                                    obj6 = obj4;
                                                    str12 = str70;
                                                    str13 = str68;
                                                    str14 = str71;
                                                    str15 = str63;
                                                    str16 = str67;
                                                    c3 = 65535;
                                                    break;
                                            }
                                            switch (c3) {
                                                case 0:
                                                    rotationXset = new ViewOscillator.RotationXset();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                case 1:
                                                    rotationXset = new ViewOscillator.RotationYset();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                case 2:
                                                    rotationXset = new ViewOscillator.TranslationXset();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                case 3:
                                                    rotationXset = new ViewOscillator.TranslationYset();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                case 4:
                                                    rotationXset = new ViewOscillator.TranslationZset();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                case 5:
                                                    rotationXset = new ViewOscillator.ProgressSet();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                case 6:
                                                    rotationXset = new ViewOscillator.ScaleXset();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                case 7:
                                                    rotationXset = new ViewOscillator.ScaleYset();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                case '\b':
                                                    rotationXset = new ViewOscillator.AlphaSet();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                case '\t':
                                                    rotationXset = new ViewOscillator.RotationSet();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                case '\n':
                                                    rotationXset = new ViewOscillator.ElevationSet();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                case 11:
                                                    rotationXset = new ViewOscillator.PathRotateSet();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                case '\f':
                                                    rotationXset = new ViewOscillator.AlphaSet();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                case '\r':
                                                    rotationXset = new ViewOscillator.AlphaSet();
                                                    f2 = f6;
                                                    customSet = rotationXset;
                                                    break;
                                                default:
                                                    f2 = f6;
                                                    customSet = null;
                                                    break;
                                            }
                                        } else {
                                            it = it12;
                                            f2 = f6;
                                            customSet = new ViewOscillator.CustomSet();
                                            str11 = str69;
                                            obj6 = obj4;
                                            str12 = str70;
                                            str13 = str68;
                                            str14 = str71;
                                            str15 = str63;
                                            str16 = str67;
                                        }
                                        if (customSet == null) {
                                            f6 = f2;
                                            obj4 = obj6;
                                            str63 = str15;
                                            str2 = str73;
                                            str70 = str12;
                                            str71 = str14;
                                            str69 = str11;
                                            str68 = str13;
                                            str67 = str16;
                                            it12 = it;
                                        } else {
                                            str2 = str73;
                                            obj4 = obj6;
                                            if (customSet.mVariesBy == 1 && Float.isNaN(f2)) {
                                                float[] fArr2 = new float[2];
                                                float f7 = 1.0f / 99;
                                                double d2 = 0.0d;
                                                fHypot = 0.0f;
                                                double d3 = 0.0d;
                                                int i47 = 0;
                                                while (i47 < 100) {
                                                    float f8 = i47 * f7;
                                                    int i48 = i47;
                                                    String str74 = str15;
                                                    double d4 = f8;
                                                    Easing easing = motionPaths2.mKeyFrameEasing;
                                                    ArrayList arrayList14 = motionController.mMotionPaths;
                                                    int size8 = arrayList14.size();
                                                    String str75 = str16;
                                                    float f9 = 0.0f;
                                                    Easing easing2 = easing;
                                                    int i49 = 0;
                                                    float f10 = Float.NaN;
                                                    while (i49 < size8) {
                                                        Object obj16 = arrayList14.get(i49);
                                                        i49++;
                                                        ArrayList arrayList15 = arrayList14;
                                                        MotionPaths motionPaths9 = (MotionPaths) obj16;
                                                        int i50 = size8;
                                                        Easing easing3 = motionPaths9.mKeyFrameEasing;
                                                        if (easing3 != null) {
                                                            float f11 = motionPaths9.mTime;
                                                            if (f11 < f8) {
                                                                f9 = f11;
                                                                easing2 = easing3;
                                                            } else if (Float.isNaN(f10)) {
                                                                f10 = motionPaths9.mTime;
                                                            }
                                                        }
                                                        size8 = i50;
                                                        arrayList14 = arrayList15;
                                                    }
                                                    if (easing2 != null) {
                                                        if (Float.isNaN(f10)) {
                                                            f10 = 1.0f;
                                                        }
                                                        d = (((float) easing2.get((f8 - f9) / r37)) * (f10 - f9)) + f9;
                                                    } else {
                                                        d = d4;
                                                    }
                                                    motionController.mSpline[0].getPos(d, motionController.mInterpolateData);
                                                    motionController.mStartMotionPath.getCenter(d, motionController.mInterpolateVariables, motionController.mInterpolateData, fArr2, 0);
                                                    if (i48 > 0) {
                                                        c5 = 0;
                                                        str20 = str11;
                                                        str21 = str13;
                                                        fHypot += (float) Math.hypot(d3 - fArr2[1], d2 - fArr2[0]);
                                                    } else {
                                                        str20 = str11;
                                                        str21 = str13;
                                                        c5 = 0;
                                                    }
                                                    double d5 = fArr2[c5];
                                                    str13 = str21;
                                                    d3 = fArr2[1];
                                                    d2 = d5;
                                                    i47 = i48 + 1;
                                                    str11 = str20;
                                                    str15 = str74;
                                                    str16 = str75;
                                                }
                                                str17 = str15;
                                                str68 = str13;
                                                str18 = str16;
                                                str19 = str11;
                                            } else {
                                                str17 = str15;
                                                str68 = str13;
                                                str18 = str16;
                                                str19 = str11;
                                                fHypot = f2;
                                            }
                                            customSet.mType = str72;
                                            motionController.mCycleMap.put(str72, customSet);
                                            it12 = it;
                                            str70 = str12;
                                            str71 = str14;
                                            str69 = str19;
                                            f6 = fHypot;
                                            str63 = str17;
                                            str67 = str18;
                                        }
                                    }
                                    String str76 = str69;
                                    String str77 = str70;
                                    String str78 = str71;
                                    String str79 = str63;
                                    String str80 = str67;
                                    ArrayList arrayList16 = motionController.mKeyList;
                                    int size9 = arrayList16.size();
                                    int i51 = 0;
                                    while (i51 < size9) {
                                        Object obj17 = arrayList16.get(i51);
                                        int i52 = i51 + 1;
                                        Key key6 = (Key) obj17;
                                        if (key6 instanceof KeyCycle) {
                                            KeyCycle keyCycle = (KeyCycle) key6;
                                            HashMap map15 = motionController.mCycleMap;
                                            keyCycle.getClass();
                                            Iterator it13 = map15.keySet().iterator();
                                            while (it13.hasNext()) {
                                                String str81 = (String) it13.next();
                                                ArrayList arrayList17 = arrayList16;
                                                if (str81.startsWith("CUSTOM")) {
                                                    int i53 = size9;
                                                    ConstraintAttribute constraintAttribute6 = (ConstraintAttribute) keyCycle.mCustomConstraints.get(str81.substring(7));
                                                    if (constraintAttribute6 != null) {
                                                        int i54 = i52;
                                                        if (constraintAttribute6.getType() == ConstraintAttribute.AttributeType.FLOAT_TYPE && (viewOscillator = (ViewOscillator) map15.get(str81)) != null) {
                                                            int i55 = keyCycle.mFramePosition;
                                                            int i56 = keyCycle.mWaveShape;
                                                            String str82 = keyCycle.mCustomWaveShape;
                                                            Iterator it14 = it13;
                                                            int i57 = keyCycle.mWaveVariesBy;
                                                            String str83 = str76;
                                                            viewOscillator.mWavePoints.add(new KeyCycleOscillator.WavePoint(i55, keyCycle.mWavePeriod, keyCycle.mWaveOffset, keyCycle.mWavePhase, constraintAttribute6.getValueToInterpolate()));
                                                            if (i57 != -1) {
                                                                viewOscillator.mVariesBy = i57;
                                                            }
                                                            viewOscillator.mWaveShape = i56;
                                                            viewOscillator.setCustom(constraintAttribute6);
                                                            viewOscillator.mWaveString = str82;
                                                            size9 = i53;
                                                            i52 = i54;
                                                            it13 = it14;
                                                            arrayList16 = arrayList17;
                                                            str76 = str83;
                                                        } else {
                                                            size9 = i53;
                                                            i52 = i54;
                                                        }
                                                    } else {
                                                        size9 = i53;
                                                    }
                                                    arrayList16 = arrayList17;
                                                } else {
                                                    int i58 = size9;
                                                    int i59 = i52;
                                                    Iterator it15 = it13;
                                                    String str84 = str76;
                                                    switch (str81.hashCode()) {
                                                        case -1249320806:
                                                            str76 = str84;
                                                            obj5 = obj4;
                                                            str8 = str68;
                                                            str9 = str79;
                                                            str10 = str80;
                                                            if (str81.equals(obj5)) {
                                                                c = 0;
                                                                break;
                                                            } else {
                                                                c = 65535;
                                                                break;
                                                            }
                                                        case -1249320805:
                                                            str76 = str84;
                                                            str8 = str68;
                                                            str9 = str79;
                                                            str10 = str80;
                                                            if (str81.equals(str9)) {
                                                                c = 1;
                                                                obj5 = obj4;
                                                                break;
                                                            } else {
                                                                obj5 = obj4;
                                                                c = 65535;
                                                                break;
                                                            }
                                                        case -1225497657:
                                                            str76 = str84;
                                                            str8 = str68;
                                                            str10 = str80;
                                                            obj5 = obj4;
                                                            if (str81.equals(str10)) {
                                                                str9 = str79;
                                                                c = 2;
                                                                break;
                                                            } else {
                                                                str9 = str79;
                                                                c = 65535;
                                                                break;
                                                            }
                                                        case -1225497656:
                                                            str76 = str84;
                                                            str8 = str68;
                                                            if (str81.equals(str8)) {
                                                                c = 3;
                                                                obj5 = obj4;
                                                                str9 = str79;
                                                                str10 = str80;
                                                                break;
                                                            } else {
                                                                obj5 = obj4;
                                                                str9 = str79;
                                                                str10 = str80;
                                                                c = 65535;
                                                                break;
                                                            }
                                                        case -1225497655:
                                                            str76 = str84;
                                                            if (str81.equals(obj)) {
                                                                c = 4;
                                                                obj5 = obj4;
                                                                str8 = str68;
                                                                str9 = str79;
                                                                str10 = str80;
                                                                break;
                                                            }
                                                            obj5 = obj4;
                                                            str8 = str68;
                                                            str9 = str79;
                                                            str10 = str80;
                                                            c = 65535;
                                                            break;
                                                        case -1001078227:
                                                            str76 = str84;
                                                            if (str81.equals(str76)) {
                                                                c = 5;
                                                                obj5 = obj4;
                                                                str8 = str68;
                                                                str9 = str79;
                                                                str10 = str80;
                                                                break;
                                                            }
                                                            obj5 = obj4;
                                                            str8 = str68;
                                                            str9 = str79;
                                                            str10 = str80;
                                                            c = 65535;
                                                            break;
                                                        case -908189618:
                                                            if (str81.equals(str78)) {
                                                                str76 = str84;
                                                                obj5 = obj4;
                                                                str8 = str68;
                                                                str9 = str79;
                                                                str10 = str80;
                                                                c = 6;
                                                                break;
                                                            }
                                                            str76 = str84;
                                                            obj5 = obj4;
                                                            str8 = str68;
                                                            str9 = str79;
                                                            str10 = str80;
                                                            c = 65535;
                                                            break;
                                                        case -908189617:
                                                            if (str81.equals(str77)) {
                                                                str76 = str84;
                                                                obj5 = obj4;
                                                                str8 = str68;
                                                                str9 = str79;
                                                                str10 = str80;
                                                                c = 7;
                                                                break;
                                                            }
                                                            str76 = str84;
                                                            obj5 = obj4;
                                                            str8 = str68;
                                                            str9 = str79;
                                                            str10 = str80;
                                                            c = 65535;
                                                            break;
                                                        case -40300674:
                                                            if (str81.equals("rotation")) {
                                                                str76 = str84;
                                                                obj5 = obj4;
                                                                str8 = str68;
                                                                str9 = str79;
                                                                str10 = str80;
                                                                c = '\b';
                                                                break;
                                                            }
                                                            str76 = str84;
                                                            obj5 = obj4;
                                                            str8 = str68;
                                                            str9 = str79;
                                                            str10 = str80;
                                                            c = 65535;
                                                            break;
                                                        case -4379043:
                                                            if (str81.equals("elevation")) {
                                                                c2 = '\t';
                                                                str76 = str84;
                                                                obj5 = obj4;
                                                                str9 = str79;
                                                                str10 = str80;
                                                                c = c2;
                                                                str8 = str68;
                                                                break;
                                                            }
                                                            str76 = str84;
                                                            obj5 = obj4;
                                                            str8 = str68;
                                                            str9 = str79;
                                                            str10 = str80;
                                                            c = 65535;
                                                            break;
                                                        case 37232917:
                                                            if (str81.equals("transitionPathRotate")) {
                                                                c2 = '\n';
                                                                str76 = str84;
                                                                obj5 = obj4;
                                                                str9 = str79;
                                                                str10 = str80;
                                                                c = c2;
                                                                str8 = str68;
                                                                break;
                                                            }
                                                            str76 = str84;
                                                            obj5 = obj4;
                                                            str8 = str68;
                                                            str9 = str79;
                                                            str10 = str80;
                                                            c = 65535;
                                                            break;
                                                        case 92909918:
                                                            if (str81.equals("alpha")) {
                                                                c2 = 11;
                                                                str76 = str84;
                                                                obj5 = obj4;
                                                                str9 = str79;
                                                                str10 = str80;
                                                                c = c2;
                                                                str8 = str68;
                                                                break;
                                                            }
                                                            str76 = str84;
                                                            obj5 = obj4;
                                                            str8 = str68;
                                                            str9 = str79;
                                                            str10 = str80;
                                                            c = 65535;
                                                            break;
                                                        case 156108012:
                                                            if (str81.equals("waveOffset")) {
                                                                c2 = '\f';
                                                                str76 = str84;
                                                                obj5 = obj4;
                                                                str9 = str79;
                                                                str10 = str80;
                                                                c = c2;
                                                                str8 = str68;
                                                                break;
                                                            }
                                                            str76 = str84;
                                                            obj5 = obj4;
                                                            str8 = str68;
                                                            str9 = str79;
                                                            str10 = str80;
                                                            c = 65535;
                                                            break;
                                                        case 1530034690:
                                                            if (str81.equals("wavePhase")) {
                                                                c2 = '\r';
                                                                str76 = str84;
                                                                obj5 = obj4;
                                                                str9 = str79;
                                                                str10 = str80;
                                                                c = c2;
                                                                str8 = str68;
                                                                break;
                                                            }
                                                            str76 = str84;
                                                            obj5 = obj4;
                                                            str8 = str68;
                                                            str9 = str79;
                                                            str10 = str80;
                                                            c = 65535;
                                                            break;
                                                        default:
                                                            str76 = str84;
                                                            obj5 = obj4;
                                                            str8 = str68;
                                                            str9 = str79;
                                                            str10 = str80;
                                                            c = 65535;
                                                            break;
                                                    }
                                                    switch (c) {
                                                        case 0:
                                                            f = keyCycle.mRotationX;
                                                            break;
                                                        case 1:
                                                            f = keyCycle.mRotationY;
                                                            break;
                                                        case 2:
                                                            f = keyCycle.mTranslationX;
                                                            break;
                                                        case 3:
                                                            f = keyCycle.mTranslationY;
                                                            break;
                                                        case 4:
                                                            f = keyCycle.mTranslationZ;
                                                            break;
                                                        case 5:
                                                            f = keyCycle.mProgress;
                                                            break;
                                                        case 6:
                                                            f = keyCycle.mScaleX;
                                                            break;
                                                        case 7:
                                                            f = keyCycle.mScaleY;
                                                            break;
                                                        case '\b':
                                                            f = keyCycle.mRotation;
                                                            break;
                                                        case '\t':
                                                            f = keyCycle.mElevation;
                                                            break;
                                                        case '\n':
                                                            f = keyCycle.mTransitionPathRotate;
                                                            break;
                                                        case 11:
                                                            f = keyCycle.mAlpha;
                                                            break;
                                                        case '\f':
                                                            f = keyCycle.mWaveOffset;
                                                            break;
                                                        case '\r':
                                                            f = keyCycle.mWavePhase;
                                                            break;
                                                        default:
                                                            str81.startsWith("CUSTOM");
                                                            f = Float.NaN;
                                                            break;
                                                    }
                                                    float f12 = f;
                                                    if (Float.isNaN(f12) || (viewOscillator2 = (ViewOscillator) map15.get(str81)) == null) {
                                                        str68 = str8;
                                                        str80 = str10;
                                                        str79 = str9;
                                                        obj4 = obj5;
                                                        arrayList16 = arrayList17;
                                                        size9 = i58;
                                                        i52 = i59;
                                                    } else {
                                                        int i60 = keyCycle.mFramePosition;
                                                        Object obj18 = obj;
                                                        int i61 = keyCycle.mWaveShape;
                                                        String str85 = str8;
                                                        String str86 = keyCycle.mCustomWaveShape;
                                                        String str87 = str10;
                                                        int i62 = keyCycle.mWaveVariesBy;
                                                        HashMap map16 = map15;
                                                        KeyCycle keyCycle2 = keyCycle;
                                                        viewOscillator2.mWavePoints.add(new KeyCycleOscillator.WavePoint(i60, keyCycle.mWavePeriod, keyCycle.mWaveOffset, keyCycle.mWavePhase, f12));
                                                        if (i62 != -1) {
                                                            viewOscillator2.mVariesBy = i62;
                                                        }
                                                        viewOscillator2.mWaveShape = i61;
                                                        viewOscillator2.mWaveString = str86;
                                                        size9 = i58;
                                                        i52 = i59;
                                                        keyCycle = keyCycle2;
                                                        str79 = str9;
                                                        obj4 = obj5;
                                                        arrayList16 = arrayList17;
                                                        str68 = str85;
                                                        map15 = map16;
                                                        obj = obj18;
                                                        str80 = str87;
                                                    }
                                                    it13 = it15;
                                                }
                                            }
                                        }
                                        size9 = size9;
                                        i51 = i52;
                                        str79 = str79;
                                        obj4 = obj4;
                                        arrayList16 = arrayList16;
                                        str68 = str68;
                                        obj = obj;
                                        str80 = str80;
                                        motionController = this;
                                    }
                                    Iterator it16 = motionController.mCycleMap.values().iterator();
                                    while (it16.hasNext()) {
                                        ((ViewOscillator) it16.next()).setup();
                                    }
                                    return;
                                }
                                return;
                            }
                            String str88 = strArr3[i45];
                            int i63 = i45;
                            double[][] dArr7 = null;
                            double[] dArr8 = null;
                            int i64 = 0;
                            int i65 = 0;
                            while (i64 < i27) {
                                int i66 = i64;
                                if (motionPathsArr[i66].mAttributes.containsKey(str88)) {
                                    if (dArr7 == null) {
                                        dArr8 = new double[i27];
                                        ConstraintAttribute constraintAttribute7 = (ConstraintAttribute) motionPathsArr[i66].mAttributes.get(str88);
                                        int iNumberOfInterpolatedValues = constraintAttribute7 == null ? 0 : constraintAttribute7.numberOfInterpolatedValues();
                                        int[] iArr6 = new int[2];
                                        iArr6[i6] = iNumberOfInterpolatedValues;
                                        iArr6[0] = i27;
                                        dArr7 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, iArr6);
                                    }
                                    MotionPaths motionPaths10 = motionPathsArr[i66];
                                    double[][] dArr9 = dArr7;
                                    str23 = str40;
                                    str24 = str41;
                                    dArr8[i65] = motionPaths10.mTime;
                                    double[] dArr10 = dArr9[i65];
                                    ConstraintAttribute constraintAttribute8 = (ConstraintAttribute) motionPaths10.mAttributes.get(str88);
                                    if (constraintAttribute8 != null) {
                                        int i67 = i6;
                                        if (constraintAttribute8.numberOfInterpolatedValues() == i67) {
                                            dArr10[0] = constraintAttribute8.getValueToInterpolate();
                                            str22 = str88;
                                            i6 = i67;
                                            i65++;
                                            dArr7 = dArr9;
                                        } else {
                                            int iNumberOfInterpolatedValues2 = constraintAttribute8.numberOfInterpolatedValues();
                                            i6 = i67;
                                            constraintAttribute8.getValuesToInterpolate(new float[iNumberOfInterpolatedValues2]);
                                            int i68 = 0;
                                            int i69 = 0;
                                            while (i68 < iNumberOfInterpolatedValues2) {
                                                double[] dArr11 = dArr10;
                                                dArr11[i69] = r7[i68];
                                                i68++;
                                                dArr10 = dArr11;
                                                str88 = str88;
                                                i69++;
                                            }
                                            str22 = str88;
                                            i65++;
                                            dArr7 = dArr9;
                                        }
                                    } else {
                                        str22 = str88;
                                        i65++;
                                        dArr7 = dArr9;
                                    }
                                } else {
                                    str22 = str88;
                                    str23 = str40;
                                    str24 = str41;
                                }
                                i64 = i66 + 1;
                                str88 = str22;
                                str40 = str23;
                                str41 = str24;
                            }
                            String str89 = str40;
                            int i70 = i63 + 1;
                            motionController.mSpline[i70] = CurveFit.get(motionController.mCurveFitType, Arrays.copyOf(dArr8, i65), (double[][]) Arrays.copyOf(dArr7, i65));
                            i45 = i70;
                            str40 = str89;
                            str41 = str41;
                            i6 = 1;
                        }
                    }
                }
            }
        }
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
