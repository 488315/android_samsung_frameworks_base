package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.motion.utils.ArcCurveFit;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.utils.CustomSupport;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.motion.utils.ViewTimeCycle;
import androidx.constraintlayout.utils.widget.MotionLabel;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MotionController {
    public ArcCurveFit mArcSpline;
    public int[] mAttributeInterpolatorCount;
    public String[] mAttributeNames;
    public HashMap mAttributesMap;
    public HashMap mCycleMap;
    public int mId;
    public double[] mInterpolateData;
    public int[] mInterpolateVariables;
    public double[] mInterpolateVelocity;
    public KeyTrigger[] mKeyTriggers;
    public CurveFit[] mSpline;
    public HashMap mTimeCycleAttributesMap;
    public View mView;
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
            ((ConstraintLayout.LayoutParams) layoutParams).getClass();
        }
    }

    public static void rotate(int i, int i2, int i3, Rect rect, Rect rect2) {
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

    public final void addKey(Key key) {
        this.mKeyList.add(key);
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
        Iterator it = this.mMotionPaths.iterator();
        float f5 = Float.NaN;
        while (it.hasNext()) {
            MotionPaths motionPaths = (MotionPaths) it.next();
            Easing easing2 = motionPaths.mKeyFrameEasing;
            if (easing2 != null) {
                float f6 = motionPaths.time;
                if (f6 < f) {
                    easing = easing2;
                    f2 = f6;
                } else if (Float.isNaN(f5)) {
                    f5 = motionPaths.time;
                }
            }
        }
        if (easing == null) {
            return f;
        }
        float f7 = (Float.isNaN(f5) ? 1.0f : f5) - f2;
        double d = (f - f2) / f7;
        float f8 = f2 + (((float) easing.get(d)) * f7);
        if (fArr != null) {
            fArr[0] = (float) easing.getDiff(d);
        }
        return f8;
    }

    public final void getCenter(double d, float[] fArr, float[] fArr2) {
        double[] dArr = new double[4];
        double[] dArr2 = new double[4];
        this.mSpline[0].getPos(d, dArr);
        this.mSpline[0].getSlope(d, dArr2);
        float f = 0.0f;
        Arrays.fill(fArr2, 0.0f);
        int[] iArr = this.mInterpolateVariables;
        MotionPaths motionPaths = this.mStartMotionPath;
        float f2 = motionPaths.f27x;
        float f3 = motionPaths.f28y;
        float f4 = motionPaths.width;
        float f5 = motionPaths.height;
        float f6 = 0.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        for (int i = 0; i < iArr.length; i++) {
            float f9 = (float) dArr[i];
            float f10 = (float) dArr2[i];
            int i2 = iArr[i];
            if (i2 == 1) {
                f2 = f9;
                f = f10;
            } else if (i2 == 2) {
                f3 = f9;
                f8 = f10;
            } else if (i2 == 3) {
                f4 = f9;
                f6 = f10;
            } else if (i2 == 4) {
                f5 = f9;
                f7 = f10;
            }
        }
        float f11 = (f6 / 2.0f) + f;
        float f12 = (f7 / 2.0f) + f8;
        MotionController motionController = motionPaths.mRelativeToController;
        if (motionController != null) {
            float[] fArr3 = new float[2];
            float[] fArr4 = new float[2];
            motionController.getCenter(d, fArr3, fArr4);
            float f13 = fArr3[0];
            float f14 = fArr3[1];
            float f15 = fArr4[0];
            float f16 = fArr4[1];
            double d2 = f2;
            double d3 = f3;
            float sin = (float) (((Math.sin(d3) * d2) + f13) - (f4 / 2.0f));
            f3 = (float) ((f14 - (Math.cos(d3) * d2)) - (f5 / 2.0f));
            double d4 = f15;
            double d5 = f;
            double d6 = f8;
            f11 = (float) ((Math.cos(d3) * d6) + (Math.sin(d3) * d5) + d4);
            f2 = sin;
            f12 = (float) ((Math.sin(d3) * d6) + (f16 - (Math.cos(d3) * d5)));
        }
        fArr[0] = (f4 / 2.0f) + f2 + 0.0f;
        fArr[1] = (f5 / 2.0f) + f3 + 0.0f;
        fArr2[0] = f11;
        fArr2[1] = f12;
    }

    public final void getDpDt(float f, float f2, float f3, float[] fArr) {
        double[] dArr;
        float[] fArr2 = this.mVelocity;
        float adjustedPosition = getAdjustedPosition(f, fArr2);
        CurveFit[] curveFitArr = this.mSpline;
        MotionPaths motionPaths = this.mStartMotionPath;
        int i = 0;
        if (curveFitArr == null) {
            MotionPaths motionPaths2 = this.mEndMotionPath;
            float f4 = motionPaths2.f27x - motionPaths.f27x;
            float f5 = motionPaths2.f28y - motionPaths.f28y;
            float f6 = motionPaths2.width - motionPaths.width;
            float f7 = (motionPaths2.height - motionPaths.height) + f5;
            fArr[0] = ((f6 + f4) * f2) + ((1.0f - f2) * f4);
            fArr[1] = (f7 * f3) + ((1.0f - f3) * f5);
            return;
        }
        double d = adjustedPosition;
        curveFitArr[0].getSlope(d, this.mInterpolateVelocity);
        this.mSpline[0].getPos(d, this.mInterpolateData);
        float f8 = fArr2[0];
        while (true) {
            dArr = this.mInterpolateVelocity;
            if (i >= dArr.length) {
                break;
            }
            dArr[i] = dArr[i] * f8;
            i++;
        }
        ArcCurveFit arcCurveFit = this.mArcSpline;
        if (arcCurveFit == null) {
            int[] iArr = this.mInterpolateVariables;
            double[] dArr2 = this.mInterpolateData;
            motionPaths.getClass();
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
            motionPaths.getClass();
            MotionPaths.setDpDt(f2, f3, fArr, iArr2, dArr4, dArr5);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean interpolate(float f, long j, View view, KeyCache keyCache) {
        ViewTimeCycle.PathRotate pathRotate;
        boolean z;
        float f2;
        MotionController motionController;
        boolean z2;
        float f3;
        MotionPaths motionPaths;
        ViewTimeCycle.PathRotate pathRotate2;
        boolean z3;
        double d;
        float f4;
        float f5;
        boolean z4;
        float f6;
        float adjustedPosition = getAdjustedPosition(f, null);
        int i = this.mQuantizeMotionSteps;
        float f7 = 1.0f;
        if (i != -1) {
            float f8 = 1.0f / i;
            float floor = ((float) Math.floor(adjustedPosition / f8)) * f8;
            float f9 = (adjustedPosition % f8) / f8;
            if (!Float.isNaN(this.mQuantizeMotionPhase)) {
                f9 = (f9 + this.mQuantizeMotionPhase) % 1.0f;
            }
            Interpolator interpolator = this.mQuantizeMotionInterpolator;
            if (interpolator != null) {
                f7 = interpolator.getInterpolation(f9);
            } else if (f9 <= 0.5d) {
                f7 = 0.0f;
            }
            adjustedPosition = (f7 * f8) + floor;
        }
        float f10 = adjustedPosition;
        HashMap hashMap = this.mAttributesMap;
        if (hashMap != null) {
            Iterator it = hashMap.values().iterator();
            while (it.hasNext()) {
                ((ViewSpline) it.next()).setProperty(view, f10);
            }
        }
        HashMap hashMap2 = this.mTimeCycleAttributesMap;
        if (hashMap2 != null) {
            pathRotate = null;
            z = false;
            for (ViewTimeCycle viewTimeCycle : hashMap2.values()) {
                if (viewTimeCycle instanceof ViewTimeCycle.PathRotate) {
                    pathRotate = (ViewTimeCycle.PathRotate) viewTimeCycle;
                } else {
                    z |= viewTimeCycle.setProperty(f10, j, view, keyCache);
                }
            }
        } else {
            pathRotate = null;
            z = false;
        }
        CurveFit[] curveFitArr = this.mSpline;
        MotionPaths motionPaths2 = this.mStartMotionPath;
        if (curveFitArr != null) {
            double d2 = f10;
            curveFitArr[0].getPos(d2, this.mInterpolateData);
            this.mSpline[0].getSlope(d2, this.mInterpolateVelocity);
            ArcCurveFit arcCurveFit = this.mArcSpline;
            if (arcCurveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    arcCurveFit.getPos(d2, dArr);
                    this.mArcSpline.getSlope(d2, this.mInterpolateVelocity);
                }
            }
            if (this.mNoMovement) {
                f3 = f10;
                motionPaths = motionPaths2;
                pathRotate2 = pathRotate;
                z3 = z;
                d = d2;
                motionController = this;
            } else {
                int[] iArr = this.mInterpolateVariables;
                double[] dArr2 = this.mInterpolateData;
                double[] dArr3 = this.mInterpolateVelocity;
                boolean z5 = this.mForceMeasure;
                float f11 = motionPaths2.f27x;
                float f12 = motionPaths2.f28y;
                float f13 = motionPaths2.width;
                float f14 = motionPaths2.height;
                if (iArr.length != 0) {
                    f5 = f12;
                    if (motionPaths2.mTempValue.length <= iArr[iArr.length - 1]) {
                        int i2 = iArr[iArr.length - 1] + 1;
                        motionPaths2.mTempValue = new double[i2];
                        motionPaths2.mTempDelta = new double[i2];
                    }
                } else {
                    f5 = f12;
                }
                pathRotate2 = pathRotate;
                z3 = z;
                Arrays.fill(motionPaths2.mTempValue, Double.NaN);
                for (int i3 = 0; i3 < iArr.length; i3++) {
                    double[] dArr4 = motionPaths2.mTempValue;
                    int i4 = iArr[i3];
                    dArr4[i4] = dArr2[i3];
                    motionPaths2.mTempDelta[i4] = dArr3[i3];
                }
                float f15 = Float.NaN;
                float f16 = 0.0f;
                int i5 = 0;
                float f17 = f14;
                float f18 = 0.0f;
                float f19 = 0.0f;
                float f20 = f11;
                float f21 = f5;
                f3 = f10;
                float f22 = 0.0f;
                float f23 = f13;
                float f24 = f21;
                while (true) {
                    double[] dArr5 = motionPaths2.mTempValue;
                    z4 = z5;
                    if (i5 >= dArr5.length) {
                        break;
                    }
                    if (Double.isNaN(dArr5[i5])) {
                        f6 = f15;
                    } else {
                        f6 = f15;
                        float f25 = (float) (Double.isNaN(motionPaths2.mTempValue[i5]) ? 0.0d : motionPaths2.mTempValue[i5] + 0.0d);
                        float f26 = (float) motionPaths2.mTempDelta[i5];
                        if (i5 == 1) {
                            f15 = f6;
                            f16 = f26;
                            f20 = f25;
                        } else if (i5 == 2) {
                            f22 = f26;
                            f24 = f25;
                        } else if (i5 == 3) {
                            f19 = f26;
                            f23 = f25;
                        } else if (i5 == 4) {
                            f18 = f26;
                            f17 = f25;
                        } else if (i5 == 5) {
                            f15 = f25;
                        }
                        i5++;
                        z5 = z4;
                    }
                    f15 = f6;
                    i5++;
                    z5 = z4;
                }
                float f27 = f15;
                MotionController motionController2 = motionPaths2.mRelativeToController;
                if (motionController2 != null) {
                    float[] fArr = new float[2];
                    float[] fArr2 = new float[2];
                    motionController2.getCenter(d2, fArr, fArr2);
                    float f28 = fArr[0];
                    float f29 = fArr[1];
                    float f30 = fArr2[0];
                    float f31 = fArr2[1];
                    motionPaths = motionPaths2;
                    double d3 = f28;
                    double d4 = f20;
                    d = d2;
                    double d5 = f24;
                    float sin = (float) (((Math.sin(d5) * d4) + d3) - (f23 / 2.0f));
                    float cos = (float) ((f29 - (Math.cos(d5) * d4)) - (f17 / 2.0f));
                    double d6 = f16;
                    double d7 = f22;
                    float cos2 = (float) ((Math.cos(d5) * d4 * d7) + (Math.sin(d5) * d6) + f30);
                    float sin2 = (float) ((Math.sin(d5) * d4 * d7) + (f31 - (Math.cos(d5) * d6)));
                    if (dArr3.length >= 2) {
                        dArr3[0] = cos2;
                        dArr3[1] = sin2;
                    }
                    if (!Float.isNaN(f27)) {
                        view.setRotation((float) (Math.toDegrees(Math.atan2(sin2, cos2)) + f27));
                    }
                    f20 = sin;
                    f24 = cos;
                } else {
                    motionPaths = motionPaths2;
                    d = d2;
                    if (!Float.isNaN(f27)) {
                        view.setRotation((float) (Math.toDegrees(Math.atan2((f18 / 2.0f) + f22, (f19 / 2.0f) + f16)) + f27 + 0.0f));
                    }
                }
                if (view instanceof FloatLayout) {
                    ((MotionLabel) ((FloatLayout) view)).layout(f20, f24, f23 + f20, f24 + f17);
                } else {
                    float f32 = f20 + 0.5f;
                    int i6 = (int) f32;
                    float f33 = f24 + 0.5f;
                    int i7 = (int) f33;
                    int i8 = (int) (f32 + f23);
                    int i9 = (int) (f33 + f17);
                    int i10 = i8 - i6;
                    int i11 = i9 - i7;
                    if (((i10 == view.getMeasuredWidth() && i11 == view.getMeasuredHeight()) ? false : true) || z4) {
                        view.measure(View.MeasureSpec.makeMeasureSpec(i10, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(i11, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                    }
                    view.layout(i6, i7, i8, i9);
                }
                motionController = this;
                motionController.mForceMeasure = false;
            }
            if (motionController.mTransformPivotTarget != -1) {
                if (motionController.mTransformPivotView == null) {
                    motionController.mTransformPivotView = ((View) view.getParent()).findViewById(motionController.mTransformPivotTarget);
                }
                if (motionController.mTransformPivotView != null) {
                    float bottom = (motionController.mTransformPivotView.getBottom() + r0.getTop()) / 2.0f;
                    float right = (motionController.mTransformPivotView.getRight() + motionController.mTransformPivotView.getLeft()) / 2.0f;
                    if (view.getRight() - view.getLeft() > 0 && view.getBottom() - view.getTop() > 0) {
                        view.setPivotX(right - view.getLeft());
                        view.setPivotY(bottom - view.getTop());
                    }
                }
            }
            HashMap hashMap3 = motionController.mAttributesMap;
            if (hashMap3 != null) {
                for (SplineSet splineSet : hashMap3.values()) {
                    if (splineSet instanceof ViewSpline.PathRotate) {
                        double[] dArr6 = motionController.mInterpolateVelocity;
                        if (dArr6.length > 1) {
                            f4 = f3;
                            view.setRotation(((ViewSpline.PathRotate) splineSet).get(f4) + ((float) Math.toDegrees(Math.atan2(dArr6[1], dArr6[0]))));
                            f3 = f4;
                        }
                    }
                    f4 = f3;
                    f3 = f4;
                }
            }
            f2 = f3;
            if (pathRotate2 != null) {
                double[] dArr7 = motionController.mInterpolateVelocity;
                view.setRotation(pathRotate2.get(f2, j, view, keyCache) + ((float) Math.toDegrees(Math.atan2(dArr7[1], dArr7[0]))));
                z2 = z3 | pathRotate2.mContinue;
            } else {
                z2 = z3;
            }
            int i12 = 1;
            while (true) {
                CurveFit[] curveFitArr2 = motionController.mSpline;
                if (i12 >= curveFitArr2.length) {
                    break;
                }
                CurveFit curveFit = curveFitArr2[i12];
                float[] fArr3 = motionController.mValuesBuff;
                curveFit.getPos(d, fArr3);
                CustomSupport.setInterpolatedValue((ConstraintAttribute) motionPaths.attributes.get(motionController.mAttributeNames[i12 - 1]), view, fArr3);
                i12++;
            }
            MotionConstrainedPoint motionConstrainedPoint = motionController.mStartPoint;
            if (motionConstrainedPoint.mVisibilityMode == 0) {
                if (f2 <= 0.0f) {
                    view.setVisibility(motionConstrainedPoint.visibility);
                } else {
                    MotionConstrainedPoint motionConstrainedPoint2 = motionController.mEndPoint;
                    if (f2 >= 1.0f) {
                        view.setVisibility(motionConstrainedPoint2.visibility);
                    } else if (motionConstrainedPoint2.visibility != motionConstrainedPoint.visibility) {
                        view.setVisibility(0);
                    }
                }
            }
            if (motionController.mKeyTriggers != null) {
                int i13 = 0;
                while (true) {
                    KeyTrigger[] keyTriggerArr = motionController.mKeyTriggers;
                    if (i13 >= keyTriggerArr.length) {
                        break;
                    }
                    keyTriggerArr[i13].conditionallyFire(view, f2);
                    i13++;
                }
            }
        } else {
            f2 = f10;
            boolean z6 = z;
            motionController = this;
            float f34 = motionPaths2.f27x;
            MotionPaths motionPaths3 = motionController.mEndMotionPath;
            float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(motionPaths3.f27x, f34, f2, f34);
            float f35 = motionPaths2.f28y;
            float m20m2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(motionPaths3.f28y, f35, f2, f35);
            float f36 = motionPaths2.width;
            float f37 = motionPaths3.width;
            float m20m3 = DependencyGraph$$ExternalSyntheticOutline0.m20m(f37, f36, f2, f36);
            float f38 = motionPaths2.height;
            float f39 = motionPaths3.height;
            float f40 = m20m + 0.5f;
            int i14 = (int) f40;
            float f41 = m20m2 + 0.5f;
            int i15 = (int) f41;
            int i16 = (int) (f40 + m20m3);
            int m20m4 = (int) (f41 + DependencyGraph$$ExternalSyntheticOutline0.m20m(f39, f38, f2, f38));
            int i17 = i16 - i14;
            int i18 = m20m4 - i15;
            if (f37 != f36 || f39 != f38 || motionController.mForceMeasure) {
                view.measure(View.MeasureSpec.makeMeasureSpec(i17, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(i18, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                motionController.mForceMeasure = false;
            }
            view.layout(i14, i15, i16, m20m4);
            z2 = z6;
        }
        HashMap hashMap4 = motionController.mCycleMap;
        if (hashMap4 != null) {
            for (ViewOscillator viewOscillator : hashMap4.values()) {
                if (viewOscillator instanceof ViewOscillator.PathRotateSet) {
                    double[] dArr8 = motionController.mInterpolateVelocity;
                    view.setRotation(((ViewOscillator.PathRotateSet) viewOscillator).get(f2) + ((float) Math.toDegrees(Math.atan2(dArr8[1], dArr8[0]))));
                } else {
                    viewOscillator.setProperty(view, f2);
                }
            }
        }
        return z2;
    }

    public final void readView(MotionPaths motionPaths) {
        motionPaths.setBounds((int) this.mView.getX(), (int) this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void setup(int i, int i2, long j) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        ArrayList arrayList;
        HashSet hashSet;
        HashSet hashSet2;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        Object obj;
        Object obj2;
        Object obj3;
        String str11;
        ArrayList arrayList2;
        ArrayList arrayList3;
        String str12;
        String str13;
        String str14;
        Object obj4;
        Object obj5;
        Object obj6;
        MotionController motionController;
        String str15;
        Iterator it;
        HashMap hashMap;
        String str16;
        Iterator it2;
        String str17;
        String str18;
        Object obj7;
        String str19;
        Object obj8;
        String str20;
        Object obj9;
        String str21;
        String str22;
        String str23;
        KeyCycle keyCycle;
        String str24;
        String str25;
        String str26;
        ViewOscillator viewOscillator;
        String str27;
        String str28;
        String str29;
        String str30;
        String str31;
        char c;
        char c2;
        char c3;
        float f;
        float f2;
        HashMap hashMap2;
        Iterator it3;
        Object obj10;
        Object obj11;
        String str32;
        String str33;
        String str34;
        String str35;
        String str36;
        String str37;
        String str38;
        String str39;
        String str40;
        Object obj12;
        char c4;
        char c5;
        char c6;
        KeyCycleOscillator rotationXset;
        KeyCycleOscillator keyCycleOscillator;
        String str41;
        String str42;
        String str43;
        String str44;
        MotionPaths motionPaths;
        String str45;
        String str46;
        double d;
        String str47;
        String str48;
        double[] dArr;
        double[][] dArr2;
        ConstraintAttribute constraintAttribute;
        HashSet hashSet3;
        ArrayList arrayList4;
        HashMap hashMap3;
        String str49;
        Iterator it4;
        String str50;
        Object obj13;
        KeyTimeCycle keyTimeCycle;
        Object obj14;
        Object obj15;
        Object obj16;
        char c7;
        char c8;
        char c9;
        Iterator it5;
        HashMap hashMap4;
        String str51;
        String str52;
        Object obj17;
        Object obj18;
        Object obj19;
        char c10;
        char c11;
        TimeCycleSplineSet rotationXset2;
        Object obj20;
        TimeCycleSplineSet timeCycleSplineSet;
        String str53;
        ConstraintAttribute constraintAttribute2;
        Integer num;
        HashSet hashSet4;
        HashSet hashSet5;
        Iterator it6;
        String str54;
        String str55;
        String str56;
        String str57;
        String str58;
        Object obj21;
        String str59;
        ArrayList arrayList5;
        Object obj22;
        Object obj23;
        char c12;
        char c13;
        SplineSet rotationXset3;
        Object obj24;
        SplineSet splineSet;
        String str60;
        ConstraintAttribute constraintAttribute3;
        String str61;
        String str62;
        String str63;
        MotionController motionController2 = this;
        new HashSet();
        HashSet hashSet6 = new HashSet();
        HashSet hashSet7 = new HashSet();
        HashSet hashSet8 = new HashSet();
        HashMap hashMap5 = new HashMap();
        int i3 = motionController2.mPathMotionArc;
        MotionPaths motionPaths2 = motionController2.mStartMotionPath;
        if (i3 != -1) {
            motionPaths2.mPathMotionArc = i3;
        }
        MotionConstrainedPoint motionConstrainedPoint = motionController2.mStartPoint;
        float f3 = motionConstrainedPoint.alpha;
        MotionConstrainedPoint motionConstrainedPoint2 = motionController2.mEndPoint;
        String str64 = "alpha";
        if (MotionConstrainedPoint.diff(f3, motionConstrainedPoint2.alpha)) {
            hashSet7.add("alpha");
        }
        String str65 = "elevation";
        if (MotionConstrainedPoint.diff(motionConstrainedPoint.elevation, motionConstrainedPoint2.elevation)) {
            hashSet7.add("elevation");
        }
        int i4 = motionConstrainedPoint.visibility;
        int i5 = motionConstrainedPoint2.visibility;
        if (i4 != i5 && motionConstrainedPoint.mVisibilityMode == 0 && (i4 == 0 || i5 == 0)) {
            hashSet7.add("alpha");
        }
        String str66 = "rotation";
        if (MotionConstrainedPoint.diff(motionConstrainedPoint.rotation, motionConstrainedPoint2.rotation)) {
            hashSet7.add("rotation");
        }
        if (!Float.isNaN(motionConstrainedPoint.mPathRotate) || !Float.isNaN(motionConstrainedPoint2.mPathRotate)) {
            hashSet7.add("transitionPathRotate");
        }
        String str67 = "progress";
        if (!Float.isNaN(motionConstrainedPoint.mProgress) || !Float.isNaN(motionConstrainedPoint2.mProgress)) {
            hashSet7.add("progress");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint.rotationX, motionConstrainedPoint2.rotationX)) {
            hashSet7.add("rotationX");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint.rotationY, motionConstrainedPoint2.rotationY)) {
            hashSet7.add("rotationY");
        }
        MotionPaths motionPaths3 = motionPaths2;
        if (MotionConstrainedPoint.diff(motionConstrainedPoint.mPivotX, motionConstrainedPoint2.mPivotX)) {
            hashSet7.add("transformPivotX");
        }
        if (MotionConstrainedPoint.diff(motionConstrainedPoint.mPivotY, motionConstrainedPoint2.mPivotY)) {
            hashSet7.add("transformPivotY");
        }
        String str68 = "scaleX";
        if (MotionConstrainedPoint.diff(motionConstrainedPoint.scaleX, motionConstrainedPoint2.scaleX)) {
            hashSet7.add("scaleX");
        }
        Object obj25 = "rotationX";
        String str69 = "scaleY";
        if (MotionConstrainedPoint.diff(motionConstrainedPoint.scaleY, motionConstrainedPoint2.scaleY)) {
            hashSet7.add("scaleY");
        }
        Object obj26 = "rotationY";
        if (MotionConstrainedPoint.diff(motionConstrainedPoint.translationX, motionConstrainedPoint2.translationX)) {
            hashSet7.add("translationX");
        }
        Object obj27 = "translationX";
        String str70 = "translationY";
        if (MotionConstrainedPoint.diff(motionConstrainedPoint.translationY, motionConstrainedPoint2.translationY)) {
            hashSet7.add("translationY");
        }
        String str71 = "translationZ";
        if (MotionConstrainedPoint.diff(motionConstrainedPoint.translationZ, motionConstrainedPoint2.translationZ)) {
            hashSet7.add("translationZ");
        }
        ArrayList arrayList6 = motionController2.mKeyList;
        ArrayList arrayList7 = motionController2.mMotionPaths;
        if (arrayList6 != null) {
            Iterator it7 = arrayList6.iterator();
            ArrayList arrayList8 = null;
            while (it7.hasNext()) {
                String str72 = str70;
                Key key = (Key) it7.next();
                String str73 = str71;
                if (key instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) key;
                    str62 = str67;
                    str61 = str68;
                    MotionPaths motionPaths4 = new MotionPaths(i, i2, keyPosition, motionController2.mStartMotionPath, motionController2.mEndMotionPath);
                    if (Collections.binarySearch(arrayList7, motionPaths4) == 0) {
                        str63 = str69;
                        Log.e("MotionController", " KeyPath position \"" + motionPaths4.position + "\" outside of range");
                    } else {
                        str63 = str69;
                    }
                    arrayList7.add((-r7) - 1, motionPaths4);
                    int i6 = keyPosition.mCurveFit;
                    if (i6 != -1) {
                        motionController2.mCurveFitType = i6;
                    }
                } else {
                    str61 = str68;
                    str62 = str67;
                    str63 = str69;
                    if (key instanceof KeyCycle) {
                        key.getAttributeNames(hashSet8);
                    } else if (key instanceof KeyTimeCycle) {
                        key.getAttributeNames(hashSet6);
                    } else if (key instanceof KeyTrigger) {
                        if (arrayList8 == null) {
                            arrayList8 = new ArrayList();
                        }
                        ArrayList arrayList9 = arrayList8;
                        arrayList9.add((KeyTrigger) key);
                        arrayList8 = arrayList9;
                    } else {
                        key.setInterpolation(hashMap5);
                        key.getAttributeNames(hashSet7);
                    }
                }
                str71 = str73;
                str70 = str72;
                str69 = str63;
                str67 = str62;
                str68 = str61;
            }
            str = str71;
            str2 = str68;
            str3 = str67;
            str4 = str69;
            str5 = str70;
            arrayList = arrayList8;
        } else {
            str = "translationZ";
            str2 = "scaleX";
            str3 = "progress";
            str4 = "scaleY";
            str5 = "translationY";
            arrayList = null;
        }
        if (arrayList != null) {
            motionController2.mKeyTriggers = (KeyTrigger[]) arrayList.toArray(new KeyTrigger[0]);
        }
        String str74 = "waveOffset";
        String str75 = "CUSTOM,";
        if (hashSet7.isEmpty()) {
            hashSet = hashSet7;
            hashSet2 = hashSet8;
            str6 = str;
            str7 = str4;
            str8 = str3;
            str9 = str2;
            str10 = "waveOffset";
            obj = obj27;
            obj2 = obj26;
            obj3 = obj25;
            str11 = str5;
            arrayList2 = arrayList7;
        } else {
            motionController2.mAttributesMap = new HashMap();
            Iterator it8 = hashSet7.iterator();
            while (it8.hasNext()) {
                String str76 = (String) it8.next();
                if (str76.startsWith("CUSTOM,")) {
                    SparseArray sparseArray = new SparseArray();
                    it6 = it8;
                    String str77 = str76.split(",")[1];
                    Iterator it9 = arrayList6.iterator();
                    while (it9.hasNext()) {
                        HashSet hashSet9 = hashSet8;
                        Key key2 = (Key) it9.next();
                        HashSet hashSet10 = hashSet7;
                        HashMap hashMap6 = key2.mCustomConstraints;
                        if (hashMap6 != null && (constraintAttribute3 = (ConstraintAttribute) hashMap6.get(str77)) != null) {
                            sparseArray.append(key2.mFramePosition, constraintAttribute3);
                        }
                        hashSet7 = hashSet10;
                        hashSet8 = hashSet9;
                    }
                    hashSet4 = hashSet7;
                    hashSet5 = hashSet8;
                    ViewSpline.CustomSet customSet = new ViewSpline.CustomSet(str76, sparseArray);
                    str54 = str;
                    str56 = str3;
                    str57 = str2;
                    str58 = str74;
                    str59 = str5;
                    arrayList5 = arrayList7;
                    splineSet = customSet;
                    str55 = str4;
                    obj21 = obj27;
                    obj23 = obj26;
                    obj24 = obj25;
                } else {
                    hashSet4 = hashSet7;
                    hashSet5 = hashSet8;
                    it6 = it8;
                    switch (str76.hashCode()) {
                        case -1249320806:
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            str58 = str74;
                            obj21 = obj27;
                            str59 = str5;
                            arrayList5 = arrayList7;
                            obj22 = obj25;
                            obj23 = obj26;
                            if (str76.equals(obj22)) {
                                c12 = 0;
                                break;
                            }
                            c12 = 65535;
                            break;
                        case -1249320805:
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            str58 = str74;
                            obj21 = obj27;
                            str59 = str5;
                            arrayList5 = arrayList7;
                            Object obj28 = obj26;
                            if (str76.equals(obj28)) {
                                obj23 = obj28;
                                obj22 = obj25;
                                c12 = 1;
                                break;
                            } else {
                                obj23 = obj28;
                                obj22 = obj25;
                                c12 = 65535;
                                break;
                            }
                        case -1225497657:
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            str58 = str74;
                            str59 = str5;
                            arrayList5 = arrayList7;
                            Object obj29 = obj27;
                            if (str76.equals(obj29)) {
                                obj21 = obj29;
                                obj22 = obj25;
                                c12 = 2;
                                obj23 = obj26;
                                break;
                            } else {
                                obj21 = obj29;
                                obj22 = obj25;
                                obj23 = obj26;
                                c12 = 65535;
                                break;
                            }
                        case -1225497656:
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            str58 = str74;
                            str59 = str5;
                            if (str76.equals(str59)) {
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                obj21 = obj27;
                                c12 = 3;
                                obj23 = obj26;
                                break;
                            } else {
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                obj21 = obj27;
                                obj23 = obj26;
                                c12 = 65535;
                                break;
                            }
                        case -1225497655:
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            if (str76.equals(str54)) {
                                str58 = str74;
                                obj21 = obj27;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                obj23 = obj26;
                                c12 = 4;
                                break;
                            }
                            str58 = str74;
                            obj21 = obj27;
                            str59 = str5;
                            arrayList5 = arrayList7;
                            obj22 = obj25;
                            obj23 = obj26;
                            c12 = 65535;
                            break;
                        case -1001078227:
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            obj21 = obj27;
                            if (str76.equals(str56)) {
                                str54 = str;
                                str58 = str74;
                                obj23 = obj26;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = 5;
                                break;
                            } else {
                                str54 = str;
                                str58 = str74;
                                obj23 = obj26;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = 65535;
                                break;
                            }
                        case -908189618:
                            str55 = str4;
                            str57 = str2;
                            if (str76.equals(str57)) {
                                obj21 = obj27;
                                str54 = str;
                                str58 = str74;
                                obj23 = obj26;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = 6;
                                str56 = str3;
                                break;
                            } else {
                                obj21 = obj27;
                                str54 = str;
                                str56 = str3;
                                str58 = str74;
                                obj23 = obj26;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = 65535;
                                break;
                            }
                        case -908189617:
                            str55 = str4;
                            if (str76.equals(str55)) {
                                obj21 = obj27;
                                str54 = str;
                                str56 = str3;
                                str58 = str74;
                                obj23 = obj26;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = 7;
                                str57 = str2;
                                break;
                            } else {
                                obj21 = obj27;
                                str54 = str;
                                str56 = str3;
                                str57 = str2;
                                str58 = str74;
                                obj23 = obj26;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = 65535;
                                break;
                            }
                        case -797520672:
                            if (str76.equals("waveVariesBy")) {
                                c13 = '\b';
                                str54 = str;
                                str56 = str3;
                                str57 = str2;
                                str58 = str74;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = c13;
                                str55 = str4;
                                obj21 = obj27;
                                obj23 = obj26;
                                break;
                            }
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            str58 = str74;
                            obj21 = obj27;
                            str59 = str5;
                            arrayList5 = arrayList7;
                            obj22 = obj25;
                            obj23 = obj26;
                            c12 = 65535;
                            break;
                        case -760884510:
                            if (str76.equals("transformPivotX")) {
                                c13 = '\t';
                                str54 = str;
                                str56 = str3;
                                str57 = str2;
                                str58 = str74;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = c13;
                                str55 = str4;
                                obj21 = obj27;
                                obj23 = obj26;
                                break;
                            }
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            str58 = str74;
                            obj21 = obj27;
                            str59 = str5;
                            arrayList5 = arrayList7;
                            obj22 = obj25;
                            obj23 = obj26;
                            c12 = 65535;
                            break;
                        case -760884509:
                            if (str76.equals("transformPivotY")) {
                                c13 = '\n';
                                str54 = str;
                                str56 = str3;
                                str57 = str2;
                                str58 = str74;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = c13;
                                str55 = str4;
                                obj21 = obj27;
                                obj23 = obj26;
                                break;
                            }
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            str58 = str74;
                            obj21 = obj27;
                            str59 = str5;
                            arrayList5 = arrayList7;
                            obj22 = obj25;
                            obj23 = obj26;
                            c12 = 65535;
                            break;
                        case -40300674:
                            if (str76.equals("rotation")) {
                                c13 = 11;
                                str54 = str;
                                str56 = str3;
                                str57 = str2;
                                str58 = str74;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = c13;
                                str55 = str4;
                                obj21 = obj27;
                                obj23 = obj26;
                                break;
                            }
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            str58 = str74;
                            obj21 = obj27;
                            str59 = str5;
                            arrayList5 = arrayList7;
                            obj22 = obj25;
                            obj23 = obj26;
                            c12 = 65535;
                            break;
                        case -4379043:
                            if (str76.equals("elevation")) {
                                c13 = '\f';
                                str54 = str;
                                str56 = str3;
                                str57 = str2;
                                str58 = str74;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = c13;
                                str55 = str4;
                                obj21 = obj27;
                                obj23 = obj26;
                                break;
                            }
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            str58 = str74;
                            obj21 = obj27;
                            str59 = str5;
                            arrayList5 = arrayList7;
                            obj22 = obj25;
                            obj23 = obj26;
                            c12 = 65535;
                            break;
                        case 37232917:
                            if (str76.equals("transitionPathRotate")) {
                                c13 = '\r';
                                str54 = str;
                                str56 = str3;
                                str57 = str2;
                                str58 = str74;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = c13;
                                str55 = str4;
                                obj21 = obj27;
                                obj23 = obj26;
                                break;
                            }
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            str58 = str74;
                            obj21 = obj27;
                            str59 = str5;
                            arrayList5 = arrayList7;
                            obj22 = obj25;
                            obj23 = obj26;
                            c12 = 65535;
                            break;
                        case 92909918:
                            if (str76.equals("alpha")) {
                                c13 = 14;
                                str54 = str;
                                str56 = str3;
                                str57 = str2;
                                str58 = str74;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = c13;
                                str55 = str4;
                                obj21 = obj27;
                                obj23 = obj26;
                                break;
                            }
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            str58 = str74;
                            obj21 = obj27;
                            str59 = str5;
                            arrayList5 = arrayList7;
                            obj22 = obj25;
                            obj23 = obj26;
                            c12 = 65535;
                            break;
                        case 156108012:
                            if (str76.equals(str74)) {
                                c13 = 15;
                                str54 = str;
                                str56 = str3;
                                str57 = str2;
                                str58 = str74;
                                str59 = str5;
                                arrayList5 = arrayList7;
                                obj22 = obj25;
                                c12 = c13;
                                str55 = str4;
                                obj21 = obj27;
                                obj23 = obj26;
                                break;
                            }
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            str58 = str74;
                            obj21 = obj27;
                            str59 = str5;
                            arrayList5 = arrayList7;
                            obj22 = obj25;
                            obj23 = obj26;
                            c12 = 65535;
                            break;
                        default:
                            str54 = str;
                            str55 = str4;
                            str56 = str3;
                            str57 = str2;
                            str58 = str74;
                            obj21 = obj27;
                            str59 = str5;
                            arrayList5 = arrayList7;
                            obj22 = obj25;
                            obj23 = obj26;
                            c12 = 65535;
                            break;
                    }
                    switch (c12) {
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
                    obj24 = obj22;
                    splineSet = rotationXset3;
                }
                if (splineSet == null) {
                    str60 = str59;
                } else {
                    splineSet.mType = str76;
                    str60 = str59;
                    motionController2.mAttributesMap.put(str76, splineSet);
                }
                str2 = str57;
                str3 = str56;
                str74 = str58;
                arrayList7 = arrayList5;
                it8 = it6;
                hashSet8 = hashSet5;
                str = str54;
                str5 = str60;
                obj25 = obj24;
                obj26 = obj23;
                obj27 = obj21;
                str4 = str55;
                hashSet7 = hashSet4;
            }
            hashSet = hashSet7;
            hashSet2 = hashSet8;
            str6 = str;
            str7 = str4;
            str8 = str3;
            str9 = str2;
            str10 = str74;
            obj = obj27;
            obj2 = obj26;
            obj3 = obj25;
            str11 = str5;
            arrayList2 = arrayList7;
            if (arrayList6 != null) {
                Iterator it10 = arrayList6.iterator();
                while (it10.hasNext()) {
                    Key key3 = (Key) it10.next();
                    if (key3 instanceof KeyAttributes) {
                        key3.addValues(motionController2.mAttributesMap);
                    }
                }
            }
            motionController2.mStartPoint.addValues(0, motionController2.mAttributesMap);
            motionController2.mEndPoint.addValues(100, motionController2.mAttributesMap);
            Iterator it11 = motionController2.mAttributesMap.keySet().iterator();
            while (it11.hasNext()) {
                String str78 = (String) it11.next();
                int intValue = (!hashMap5.containsKey(str78) || (num = (Integer) hashMap5.get(str78)) == null) ? 0 : num.intValue();
                Iterator it12 = it11;
                SplineSet splineSet2 = (SplineSet) motionController2.mAttributesMap.get(str78);
                if (splineSet2 != null) {
                    splineSet2.setup(intValue);
                }
                it11 = it12;
            }
        }
        String str79 = "CUSTOM";
        if (hashSet6.isEmpty()) {
            arrayList3 = arrayList6;
            str12 = "CUSTOM";
            str13 = "CUSTOM,";
            str14 = str11;
            obj4 = obj3;
            obj5 = obj2;
            obj6 = obj;
            String str80 = str9;
            motionController = motionController2;
            str15 = str80;
        } else {
            if (motionController2.mTimeCycleAttributesMap == null) {
                motionController2.mTimeCycleAttributesMap = new HashMap();
            }
            Iterator it13 = hashSet6.iterator();
            while (it13.hasNext()) {
                String str81 = (String) it13.next();
                if (!motionController2.mTimeCycleAttributesMap.containsKey(str81)) {
                    if (!str81.startsWith(str75)) {
                        it5 = it13;
                        hashMap4 = hashMap5;
                        str51 = str75;
                        switch (str81.hashCode()) {
                            case -1249320806:
                                str52 = str11;
                                obj17 = obj3;
                                obj18 = obj2;
                                obj19 = obj;
                                if (str81.equals(obj17)) {
                                    c10 = 0;
                                    break;
                                }
                                c10 = 65535;
                                break;
                            case -1249320805:
                                str52 = str11;
                                obj18 = obj2;
                                obj19 = obj;
                                if (str81.equals(obj18)) {
                                    c10 = 1;
                                    obj17 = obj3;
                                    break;
                                } else {
                                    obj17 = obj3;
                                    c10 = 65535;
                                    break;
                                }
                            case -1225497657:
                                str52 = str11;
                                obj19 = obj;
                                if (str81.equals(obj19)) {
                                    c10 = 2;
                                    obj17 = obj3;
                                    obj18 = obj2;
                                    break;
                                } else {
                                    obj17 = obj3;
                                    obj18 = obj2;
                                    c10 = 65535;
                                    break;
                                }
                            case -1225497656:
                                str52 = str11;
                                obj17 = obj3;
                                obj18 = obj2;
                                if (str81.equals(str52)) {
                                    obj19 = obj;
                                    c10 = 3;
                                    break;
                                }
                                obj19 = obj;
                                c10 = 65535;
                                break;
                            case -1225497655:
                                if (str81.equals(str6)) {
                                    str52 = str11;
                                    obj17 = obj3;
                                    obj18 = obj2;
                                    obj19 = obj;
                                    c10 = 4;
                                    break;
                                }
                                str52 = str11;
                                obj17 = obj3;
                                obj18 = obj2;
                                obj19 = obj;
                                c10 = 65535;
                                break;
                            case -1001078227:
                                if (str81.equals(str8)) {
                                    c11 = 5;
                                    obj17 = obj3;
                                    obj18 = obj2;
                                    obj19 = obj;
                                    String str82 = str11;
                                    c10 = c11;
                                    str52 = str82;
                                    break;
                                }
                                str52 = str11;
                                obj17 = obj3;
                                obj18 = obj2;
                                obj19 = obj;
                                c10 = 65535;
                                break;
                            case -908189618:
                                if (str81.equals(str9)) {
                                    c11 = 6;
                                    obj17 = obj3;
                                    obj18 = obj2;
                                    obj19 = obj;
                                    String str822 = str11;
                                    c10 = c11;
                                    str52 = str822;
                                    break;
                                }
                                str52 = str11;
                                obj17 = obj3;
                                obj18 = obj2;
                                obj19 = obj;
                                c10 = 65535;
                                break;
                            case -908189617:
                                if (str81.equals(str7)) {
                                    c11 = 7;
                                    obj17 = obj3;
                                    obj18 = obj2;
                                    obj19 = obj;
                                    String str8222 = str11;
                                    c10 = c11;
                                    str52 = str8222;
                                    break;
                                }
                                str52 = str11;
                                obj17 = obj3;
                                obj18 = obj2;
                                obj19 = obj;
                                c10 = 65535;
                                break;
                            case -40300674:
                                if (str81.equals("rotation")) {
                                    c11 = '\b';
                                    obj17 = obj3;
                                    obj18 = obj2;
                                    obj19 = obj;
                                    String str82222 = str11;
                                    c10 = c11;
                                    str52 = str82222;
                                    break;
                                }
                                str52 = str11;
                                obj17 = obj3;
                                obj18 = obj2;
                                obj19 = obj;
                                c10 = 65535;
                                break;
                            case -4379043:
                                if (str81.equals("elevation")) {
                                    c11 = '\t';
                                    obj17 = obj3;
                                    obj18 = obj2;
                                    obj19 = obj;
                                    String str822222 = str11;
                                    c10 = c11;
                                    str52 = str822222;
                                    break;
                                }
                                str52 = str11;
                                obj17 = obj3;
                                obj18 = obj2;
                                obj19 = obj;
                                c10 = 65535;
                                break;
                            case 37232917:
                                if (str81.equals("transitionPathRotate")) {
                                    c11 = '\n';
                                    obj17 = obj3;
                                    obj18 = obj2;
                                    obj19 = obj;
                                    String str8222222 = str11;
                                    c10 = c11;
                                    str52 = str8222222;
                                    break;
                                }
                                str52 = str11;
                                obj17 = obj3;
                                obj18 = obj2;
                                obj19 = obj;
                                c10 = 65535;
                                break;
                            case 92909918:
                                if (str81.equals("alpha")) {
                                    c11 = 11;
                                    obj17 = obj3;
                                    obj18 = obj2;
                                    obj19 = obj;
                                    String str82222222 = str11;
                                    c10 = c11;
                                    str52 = str82222222;
                                    break;
                                }
                                str52 = str11;
                                obj17 = obj3;
                                obj18 = obj2;
                                obj19 = obj;
                                c10 = 65535;
                                break;
                            default:
                                str52 = str11;
                                obj17 = obj3;
                                obj18 = obj2;
                                obj19 = obj;
                                c10 = 65535;
                                break;
                        }
                        switch (c10) {
                            case 0:
                                rotationXset2 = new ViewTimeCycle.RotationXset();
                                obj20 = obj19;
                                obj3 = obj17;
                                timeCycleSplineSet = rotationXset2;
                                str53 = str9;
                                timeCycleSplineSet.last_time = j;
                                break;
                            case 1:
                                rotationXset2 = new ViewTimeCycle.RotationYset();
                                obj20 = obj19;
                                obj3 = obj17;
                                timeCycleSplineSet = rotationXset2;
                                str53 = str9;
                                timeCycleSplineSet.last_time = j;
                                break;
                            case 2:
                                rotationXset2 = new ViewTimeCycle.TranslationXset();
                                obj20 = obj19;
                                obj3 = obj17;
                                timeCycleSplineSet = rotationXset2;
                                str53 = str9;
                                timeCycleSplineSet.last_time = j;
                                break;
                            case 3:
                                rotationXset2 = new ViewTimeCycle.TranslationYset();
                                obj20 = obj19;
                                obj3 = obj17;
                                timeCycleSplineSet = rotationXset2;
                                str53 = str9;
                                timeCycleSplineSet.last_time = j;
                                break;
                            case 4:
                                rotationXset2 = new ViewTimeCycle.TranslationZset();
                                obj20 = obj19;
                                obj3 = obj17;
                                timeCycleSplineSet = rotationXset2;
                                str53 = str9;
                                timeCycleSplineSet.last_time = j;
                                break;
                            case 5:
                                rotationXset2 = new ViewTimeCycle.ProgressSet();
                                obj20 = obj19;
                                obj3 = obj17;
                                timeCycleSplineSet = rotationXset2;
                                str53 = str9;
                                timeCycleSplineSet.last_time = j;
                                break;
                            case 6:
                                rotationXset2 = new ViewTimeCycle.ScaleXset();
                                obj20 = obj19;
                                obj3 = obj17;
                                timeCycleSplineSet = rotationXset2;
                                str53 = str9;
                                timeCycleSplineSet.last_time = j;
                                break;
                            case 7:
                                rotationXset2 = new ViewTimeCycle.ScaleYset();
                                obj20 = obj19;
                                obj3 = obj17;
                                timeCycleSplineSet = rotationXset2;
                                str53 = str9;
                                timeCycleSplineSet.last_time = j;
                                break;
                            case '\b':
                                rotationXset2 = new ViewTimeCycle.RotationSet();
                                obj20 = obj19;
                                obj3 = obj17;
                                timeCycleSplineSet = rotationXset2;
                                str53 = str9;
                                timeCycleSplineSet.last_time = j;
                                break;
                            case '\t':
                                rotationXset2 = new ViewTimeCycle.ElevationSet();
                                obj20 = obj19;
                                obj3 = obj17;
                                timeCycleSplineSet = rotationXset2;
                                str53 = str9;
                                timeCycleSplineSet.last_time = j;
                                break;
                            case '\n':
                                rotationXset2 = new ViewTimeCycle.PathRotate();
                                obj20 = obj19;
                                obj3 = obj17;
                                timeCycleSplineSet = rotationXset2;
                                str53 = str9;
                                timeCycleSplineSet.last_time = j;
                                break;
                            case 11:
                                rotationXset2 = new ViewTimeCycle.AlphaSet();
                                obj20 = obj19;
                                obj3 = obj17;
                                timeCycleSplineSet = rotationXset2;
                                str53 = str9;
                                timeCycleSplineSet.last_time = j;
                                break;
                            default:
                                str53 = str9;
                                obj20 = obj19;
                                obj3 = obj17;
                                timeCycleSplineSet = null;
                                break;
                        }
                    } else {
                        SparseArray sparseArray2 = new SparseArray();
                        it5 = it13;
                        String str83 = str81.split(",")[1];
                        Iterator it14 = arrayList6.iterator();
                        while (it14.hasNext()) {
                            String str84 = str75;
                            Key key4 = (Key) it14.next();
                            HashMap hashMap7 = hashMap5;
                            HashMap hashMap8 = key4.mCustomConstraints;
                            if (hashMap8 != null && (constraintAttribute2 = (ConstraintAttribute) hashMap8.get(str83)) != null) {
                                sparseArray2.append(key4.mFramePosition, constraintAttribute2);
                            }
                            hashMap5 = hashMap7;
                            str75 = str84;
                        }
                        hashMap4 = hashMap5;
                        str51 = str75;
                        timeCycleSplineSet = new ViewTimeCycle.CustomSet(str81, sparseArray2);
                        str52 = str11;
                        obj18 = obj2;
                        obj20 = obj;
                        str53 = str9;
                    }
                    if (timeCycleSplineSet != null) {
                        timeCycleSplineSet.mType = str81;
                        motionController2.mTimeCycleAttributesMap.put(str81, timeCycleSplineSet);
                    }
                    str9 = str53;
                    obj = obj20;
                    hashMap5 = hashMap4;
                    str11 = str52;
                    obj2 = obj18;
                    it13 = it5;
                    str75 = str51;
                }
            }
            HashMap hashMap9 = hashMap5;
            str13 = str75;
            str14 = str11;
            Object obj30 = obj2;
            Object obj31 = obj;
            String str85 = str9;
            if (arrayList6 != null) {
                Iterator it15 = arrayList6.iterator();
                while (it15.hasNext()) {
                    Key key5 = (Key) it15.next();
                    if (key5 instanceof KeyTimeCycle) {
                        KeyTimeCycle keyTimeCycle2 = (KeyTimeCycle) key5;
                        HashMap hashMap10 = motionController2.mTimeCycleAttributesMap;
                        keyTimeCycle2.getClass();
                        Iterator it16 = hashMap10.keySet().iterator();
                        while (it16.hasNext()) {
                            Iterator it17 = it15;
                            String str86 = (String) it16.next();
                            ViewTimeCycle viewTimeCycle = (ViewTimeCycle) hashMap10.get(str86);
                            if (viewTimeCycle == null) {
                                arrayList4 = arrayList6;
                                hashMap3 = hashMap10;
                                str49 = str79;
                                it4 = it16;
                                str50 = str85;
                                obj13 = obj3;
                                keyTimeCycle = keyTimeCycle2;
                                obj14 = obj30;
                                obj15 = obj31;
                            } else if (!str86.startsWith(str79)) {
                                KeyTimeCycle keyTimeCycle3 = keyTimeCycle2;
                                arrayList4 = arrayList6;
                                hashMap3 = hashMap10;
                                str49 = str79;
                                it4 = it16;
                                switch (str86.hashCode()) {
                                    case -1249320806:
                                        str50 = str85;
                                        obj16 = obj3;
                                        obj15 = obj31;
                                        if (str86.equals(obj16)) {
                                            c7 = 0;
                                            break;
                                        }
                                        c7 = 65535;
                                        break;
                                    case -1249320805:
                                        str50 = str85;
                                        obj15 = obj31;
                                        if (str86.equals(obj30)) {
                                            c8 = 1;
                                            c7 = c8;
                                            obj16 = obj3;
                                            break;
                                        }
                                        obj16 = obj3;
                                        c7 = 65535;
                                        break;
                                    case -1225497657:
                                        str50 = str85;
                                        obj15 = obj31;
                                        if (str86.equals(obj15)) {
                                            c8 = 2;
                                            c7 = c8;
                                            obj16 = obj3;
                                            break;
                                        }
                                        obj16 = obj3;
                                        c7 = 65535;
                                        break;
                                    case -1225497656:
                                        str50 = str85;
                                        if (str86.equals(str14)) {
                                            c9 = 3;
                                            c7 = c9;
                                            obj16 = obj3;
                                            obj15 = obj31;
                                            break;
                                        }
                                        obj16 = obj3;
                                        obj15 = obj31;
                                        c7 = 65535;
                                        break;
                                    case -1225497655:
                                        str50 = str85;
                                        if (str86.equals(str6)) {
                                            c9 = 4;
                                            c7 = c9;
                                            obj16 = obj3;
                                            obj15 = obj31;
                                            break;
                                        }
                                        obj16 = obj3;
                                        obj15 = obj31;
                                        c7 = 65535;
                                        break;
                                    case -1001078227:
                                        str50 = str85;
                                        if (str86.equals(str8)) {
                                            c9 = 5;
                                            c7 = c9;
                                            obj16 = obj3;
                                            obj15 = obj31;
                                            break;
                                        }
                                        obj16 = obj3;
                                        obj15 = obj31;
                                        c7 = 65535;
                                        break;
                                    case -908189618:
                                        str50 = str85;
                                        if (str86.equals(str50)) {
                                            c9 = 6;
                                            c7 = c9;
                                            obj16 = obj3;
                                            obj15 = obj31;
                                            break;
                                        }
                                        obj16 = obj3;
                                        obj15 = obj31;
                                        c7 = 65535;
                                        break;
                                    case -908189617:
                                        if (str86.equals(str7)) {
                                            c7 = 7;
                                            str50 = str85;
                                            obj16 = obj3;
                                            obj15 = obj31;
                                            break;
                                        }
                                        str50 = str85;
                                        obj16 = obj3;
                                        obj15 = obj31;
                                        c7 = 65535;
                                        break;
                                    case -40300674:
                                        if (str86.equals("rotation")) {
                                            str50 = str85;
                                            c9 = '\b';
                                            c7 = c9;
                                            obj16 = obj3;
                                            obj15 = obj31;
                                            break;
                                        }
                                        str50 = str85;
                                        obj16 = obj3;
                                        obj15 = obj31;
                                        c7 = 65535;
                                        break;
                                    case -4379043:
                                        if (str86.equals("elevation")) {
                                            str50 = str85;
                                            c9 = '\t';
                                            c7 = c9;
                                            obj16 = obj3;
                                            obj15 = obj31;
                                            break;
                                        }
                                        str50 = str85;
                                        obj16 = obj3;
                                        obj15 = obj31;
                                        c7 = 65535;
                                        break;
                                    case 37232917:
                                        if (str86.equals("transitionPathRotate")) {
                                            str50 = str85;
                                            c9 = '\n';
                                            c7 = c9;
                                            obj16 = obj3;
                                            obj15 = obj31;
                                            break;
                                        }
                                        str50 = str85;
                                        obj16 = obj3;
                                        obj15 = obj31;
                                        c7 = 65535;
                                        break;
                                    case 92909918:
                                        if (str86.equals("alpha")) {
                                            str50 = str85;
                                            c9 = 11;
                                            c7 = c9;
                                            obj16 = obj3;
                                            obj15 = obj31;
                                            break;
                                        }
                                        str50 = str85;
                                        obj16 = obj3;
                                        obj15 = obj31;
                                        c7 = 65535;
                                        break;
                                    default:
                                        str50 = str85;
                                        obj16 = obj3;
                                        obj15 = obj31;
                                        c7 = 65535;
                                        break;
                                }
                                switch (c7) {
                                    case 0:
                                        obj13 = obj16;
                                        obj14 = obj30;
                                        keyTimeCycle = keyTimeCycle3;
                                        if (!Float.isNaN(keyTimeCycle.mRotationX)) {
                                            viewTimeCycle.setPoint(keyTimeCycle.mRotationX, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                            break;
                                        }
                                        break;
                                    case 1:
                                        obj13 = obj16;
                                        obj14 = obj30;
                                        keyTimeCycle = keyTimeCycle3;
                                        if (!Float.isNaN(keyTimeCycle.mRotationY)) {
                                            viewTimeCycle.setPoint(keyTimeCycle.mRotationY, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                            break;
                                        }
                                        break;
                                    case 2:
                                        obj13 = obj16;
                                        obj14 = obj30;
                                        keyTimeCycle = keyTimeCycle3;
                                        if (!Float.isNaN(keyTimeCycle.mTranslationX)) {
                                            viewTimeCycle.setPoint(keyTimeCycle.mTranslationX, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                            break;
                                        }
                                        break;
                                    case 3:
                                        obj13 = obj16;
                                        obj14 = obj30;
                                        keyTimeCycle = keyTimeCycle3;
                                        if (!Float.isNaN(keyTimeCycle.mTranslationY)) {
                                            viewTimeCycle.setPoint(keyTimeCycle.mTranslationY, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                            break;
                                        }
                                        break;
                                    case 4:
                                        obj13 = obj16;
                                        obj14 = obj30;
                                        keyTimeCycle = keyTimeCycle3;
                                        if (!Float.isNaN(keyTimeCycle.mTranslationZ)) {
                                            viewTimeCycle.setPoint(keyTimeCycle.mTranslationZ, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                            break;
                                        }
                                        break;
                                    case 5:
                                        obj13 = obj16;
                                        obj14 = obj30;
                                        keyTimeCycle = keyTimeCycle3;
                                        if (!Float.isNaN(keyTimeCycle.mProgress)) {
                                            viewTimeCycle.setPoint(keyTimeCycle.mProgress, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                            break;
                                        }
                                        break;
                                    case 6:
                                        obj13 = obj16;
                                        obj14 = obj30;
                                        keyTimeCycle = keyTimeCycle3;
                                        if (!Float.isNaN(keyTimeCycle.mScaleX)) {
                                            viewTimeCycle.setPoint(keyTimeCycle.mScaleX, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                            break;
                                        }
                                        break;
                                    case 7:
                                        obj13 = obj16;
                                        obj14 = obj30;
                                        keyTimeCycle = keyTimeCycle3;
                                        if (!Float.isNaN(keyTimeCycle.mScaleY)) {
                                            viewTimeCycle.setPoint(keyTimeCycle.mScaleY, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                            break;
                                        }
                                        break;
                                    case '\b':
                                        obj13 = obj16;
                                        obj14 = obj30;
                                        keyTimeCycle = keyTimeCycle3;
                                        if (!Float.isNaN(keyTimeCycle.mRotation)) {
                                            viewTimeCycle.setPoint(keyTimeCycle.mRotation, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                            break;
                                        }
                                        break;
                                    case '\t':
                                        obj13 = obj16;
                                        obj14 = obj30;
                                        keyTimeCycle = keyTimeCycle3;
                                        if (!Float.isNaN(keyTimeCycle.mElevation)) {
                                            viewTimeCycle.setPoint(keyTimeCycle.mElevation, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                            break;
                                        }
                                        break;
                                    case '\n':
                                        obj13 = obj16;
                                        obj14 = obj30;
                                        keyTimeCycle = keyTimeCycle3;
                                        if (!Float.isNaN(keyTimeCycle.mTransitionPathRotate)) {
                                            viewTimeCycle.setPoint(keyTimeCycle.mTransitionPathRotate, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                            break;
                                        }
                                        break;
                                    case 11:
                                        keyTimeCycle = keyTimeCycle3;
                                        if (Float.isNaN(keyTimeCycle.mAlpha)) {
                                            obj13 = obj16;
                                            obj14 = obj30;
                                            break;
                                        } else {
                                            obj13 = obj16;
                                            obj14 = obj30;
                                            viewTimeCycle.setPoint(keyTimeCycle.mAlpha, keyTimeCycle.mWavePeriod, keyTimeCycle.mWaveOffset, keyTimeCycle.mFramePosition, keyTimeCycle.mWaveShape);
                                            break;
                                        }
                                    default:
                                        obj13 = obj16;
                                        obj14 = obj30;
                                        keyTimeCycle = keyTimeCycle3;
                                        Log.e("KeyTimeCycles", "UNKNOWN addValues \"" + str86 + "\"");
                                        break;
                                }
                            } else {
                                HashMap hashMap11 = hashMap10;
                                ConstraintAttribute constraintAttribute4 = (ConstraintAttribute) keyTimeCycle2.mCustomConstraints.get(str86.substring(7));
                                if (constraintAttribute4 != null) {
                                    ViewTimeCycle.CustomSet customSet2 = (ViewTimeCycle.CustomSet) viewTimeCycle;
                                    Iterator it18 = it16;
                                    int i7 = keyTimeCycle2.mFramePosition;
                                    String str87 = str79;
                                    float f4 = keyTimeCycle2.mWavePeriod;
                                    ArrayList arrayList10 = arrayList6;
                                    int i8 = keyTimeCycle2.mWaveShape;
                                    float f5 = keyTimeCycle2.mWaveOffset;
                                    customSet2.mConstraintAttributeList.append(i7, constraintAttribute4);
                                    customSet2.mWaveProperties.append(i7, new float[]{f4, f5});
                                    customSet2.mWaveShape = Math.max(customSet2.mWaveShape, i8);
                                    it15 = it17;
                                    it16 = it18;
                                    hashMap10 = hashMap11;
                                    str79 = str87;
                                    arrayList6 = arrayList10;
                                    keyTimeCycle2 = keyTimeCycle2;
                                } else {
                                    it15 = it17;
                                    hashMap10 = hashMap11;
                                }
                            }
                            it15 = it17;
                            it16 = it4;
                            obj31 = obj15;
                            keyTimeCycle2 = keyTimeCycle;
                            obj30 = obj14;
                            hashMap10 = hashMap3;
                            str79 = str49;
                            arrayList6 = arrayList4;
                            obj3 = obj13;
                            str85 = str50;
                        }
                    }
                    it15 = it15;
                    obj31 = obj31;
                    obj30 = obj30;
                    str79 = str79;
                    arrayList6 = arrayList6;
                    obj3 = obj3;
                    str85 = str85;
                    motionController2 = this;
                }
            }
            arrayList3 = arrayList6;
            str12 = str79;
            str15 = str85;
            obj4 = obj3;
            obj6 = obj31;
            obj5 = obj30;
            motionController = this;
            for (String str88 : motionController.mTimeCycleAttributesMap.keySet()) {
                HashMap hashMap12 = hashMap9;
                ((ViewTimeCycle) motionController.mTimeCycleAttributesMap.get(str88)).setup(hashMap12.containsKey(str88) ? ((Integer) hashMap12.get(str88)).intValue() : 0);
                hashMap9 = hashMap12;
            }
        }
        int size = arrayList2.size() + 2;
        MotionPaths[] motionPathsArr = new MotionPaths[size];
        motionPathsArr[0] = motionPaths3;
        motionPathsArr[size - 1] = motionController.mEndMotionPath;
        if (arrayList2.size() > 0 && motionController.mCurveFitType == -1) {
            motionController.mCurveFitType = 0;
        }
        Iterator it19 = arrayList2.iterator();
        int i9 = 1;
        while (it19.hasNext()) {
            motionPathsArr[i9] = (MotionPaths) it19.next();
            i9++;
        }
        HashSet hashSet11 = new HashSet();
        Iterator it20 = motionController.mEndMotionPath.attributes.keySet().iterator();
        while (it20.hasNext()) {
            String str89 = (String) it20.next();
            Object obj32 = obj6;
            Iterator it21 = it20;
            MotionPaths motionPaths5 = motionPaths3;
            if (motionPaths5.attributes.containsKey(str89)) {
                motionPaths3 = motionPaths5;
                hashSet3 = hashSet;
                if (!hashSet3.contains(str13 + str89)) {
                    hashSet11.add(str89);
                }
            } else {
                motionPaths3 = motionPaths5;
                hashSet3 = hashSet;
            }
            hashSet = hashSet3;
            obj6 = obj32;
            it20 = it21;
        }
        Object obj33 = obj6;
        String[] strArr = (String[]) hashSet11.toArray(new String[0]);
        motionController.mAttributeNames = strArr;
        motionController.mAttributeInterpolatorCount = new int[strArr.length];
        int i10 = 0;
        while (true) {
            String[] strArr2 = motionController.mAttributeNames;
            if (i10 < strArr2.length) {
                String str90 = strArr2[i10];
                motionController.mAttributeInterpolatorCount[i10] = 0;
                int i11 = 0;
                while (true) {
                    if (i11 >= size) {
                        break;
                    }
                    if (!motionPathsArr[i11].attributes.containsKey(str90) || (constraintAttribute = (ConstraintAttribute) motionPathsArr[i11].attributes.get(str90)) == null) {
                        i11++;
                    } else {
                        int[] iArr = motionController.mAttributeInterpolatorCount;
                        iArr[i10] = constraintAttribute.numberOfInterpolatedValues() + iArr[i10];
                    }
                }
                i10++;
            } else {
                boolean z = motionPathsArr[0].mPathMotionArc != -1;
                int length = strArr2.length + 18;
                boolean[] zArr = new boolean[length];
                int i12 = 1;
                while (i12 < size) {
                    String str91 = str14;
                    MotionPaths motionPaths6 = motionPathsArr[i12];
                    String str92 = str6;
                    MotionPaths motionPaths7 = motionPathsArr[i12 - 1];
                    String str93 = str15;
                    boolean diff = MotionPaths.diff(motionPaths6.f27x, motionPaths7.f27x);
                    String str94 = str7;
                    boolean diff2 = MotionPaths.diff(motionPaths6.f28y, motionPaths7.f28y);
                    zArr[0] = zArr[0] | MotionPaths.diff(motionPaths6.position, motionPaths7.position);
                    boolean z2 = diff | diff2 | z;
                    zArr[1] = zArr[1] | z2;
                    zArr[2] = z2 | zArr[2];
                    zArr[3] = zArr[3] | MotionPaths.diff(motionPaths6.width, motionPaths7.width);
                    zArr[4] = zArr[4] | MotionPaths.diff(motionPaths6.height, motionPaths7.height);
                    i12++;
                    str14 = str91;
                    str8 = str8;
                    str6 = str92;
                    str15 = str93;
                    str7 = str94;
                    str66 = str66;
                    str65 = str65;
                }
                String str95 = str15;
                String str96 = str14;
                String str97 = str7;
                String str98 = str8;
                String str99 = str65;
                String str100 = str66;
                String str101 = str6;
                int i13 = 0;
                for (int i14 = 1; i14 < length; i14++) {
                    if (zArr[i14]) {
                        i13++;
                    }
                }
                motionController.mInterpolateVariables = new int[i13];
                int max = Math.max(2, i13);
                motionController.mInterpolateData = new double[max];
                motionController.mInterpolateVelocity = new double[max];
                int i15 = 0;
                for (int i16 = 1; i16 < length; i16++) {
                    if (zArr[i16]) {
                        motionController.mInterpolateVariables[i15] = i16;
                        i15++;
                    }
                }
                double[][] dArr3 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, motionController.mInterpolateVariables.length);
                double[] dArr4 = new double[size];
                for (int i17 = 0; i17 < size; i17++) {
                    MotionPaths motionPaths8 = motionPathsArr[i17];
                    double[] dArr5 = dArr3[i17];
                    int[] iArr2 = motionController.mInterpolateVariables;
                    float[] fArr = {motionPaths8.position, motionPaths8.f27x, motionPaths8.f28y, motionPaths8.width, motionPaths8.height, motionPaths8.mPathRotate};
                    int i18 = 0;
                    for (int i19 : iArr2) {
                        if (i19 < 6) {
                            dArr5[i18] = fArr[r13];
                            i18++;
                        }
                    }
                    dArr4[i17] = motionPathsArr[i17].time;
                }
                int i20 = 0;
                while (true) {
                    int[] iArr3 = motionController.mInterpolateVariables;
                    if (i20 < iArr3.length) {
                        if (iArr3[i20] < 6) {
                            String m16m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), MotionPaths.names[motionController.mInterpolateVariables[i20]], " [");
                            for (int i21 = 0; i21 < size; i21++) {
                                StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(m16m);
                                m18m.append(dArr3[i21][i20]);
                                m16m = m18m.toString();
                            }
                        }
                        i20++;
                    } else {
                        motionController.mSpline = new CurveFit[motionController.mAttributeNames.length + 1];
                        int i22 = 0;
                        while (true) {
                            String[] strArr3 = motionController.mAttributeNames;
                            if (i22 >= strArr3.length) {
                                String str102 = str64;
                                motionController.mSpline[0] = CurveFit.get(motionController.mCurveFitType, dArr4, dArr3);
                                if (motionPathsArr[0].mPathMotionArc != -1) {
                                    int[] iArr4 = new int[size];
                                    double[] dArr6 = new double[size];
                                    double[][] dArr7 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, 2);
                                    for (int i23 = 0; i23 < size; i23++) {
                                        iArr4[i23] = motionPathsArr[i23].mPathMotionArc;
                                        dArr6[i23] = r6.time;
                                        double[] dArr8 = dArr7[i23];
                                        dArr8[0] = r6.f27x;
                                        dArr8[1] = r6.f28y;
                                    }
                                    motionController.mArcSpline = new ArcCurveFit(iArr4, dArr6, dArr7);
                                }
                                motionController.mCycleMap = new HashMap();
                                if (arrayList3 != null) {
                                    Iterator it22 = hashSet2.iterator();
                                    float f6 = Float.NaN;
                                    while (it22.hasNext()) {
                                        String str103 = (String) it22.next();
                                        String str104 = str12;
                                        if (!str103.startsWith(str104)) {
                                            switch (str103.hashCode()) {
                                                case -1249320806:
                                                    it3 = it22;
                                                    obj10 = obj4;
                                                    obj11 = obj33;
                                                    str32 = str10;
                                                    str33 = str96;
                                                    str34 = str98;
                                                    str35 = str101;
                                                    str36 = str95;
                                                    str37 = str97;
                                                    str38 = str100;
                                                    str39 = str99;
                                                    str40 = str102;
                                                    obj12 = obj5;
                                                    if (str103.equals(obj10)) {
                                                        c4 = 0;
                                                        break;
                                                    }
                                                    c4 = 65535;
                                                    break;
                                                case -1249320805:
                                                    it3 = it22;
                                                    Object obj34 = obj5;
                                                    obj11 = obj33;
                                                    str32 = str10;
                                                    str33 = str96;
                                                    str34 = str98;
                                                    str35 = str101;
                                                    str36 = str95;
                                                    str37 = str97;
                                                    str38 = str100;
                                                    str39 = str99;
                                                    str40 = str102;
                                                    if (str103.equals(obj34)) {
                                                        obj12 = obj34;
                                                        obj10 = obj4;
                                                        c4 = 1;
                                                        break;
                                                    } else {
                                                        obj12 = obj34;
                                                        obj10 = obj4;
                                                        c4 = 65535;
                                                        break;
                                                    }
                                                case -1225497657:
                                                    obj11 = obj33;
                                                    str32 = str10;
                                                    str33 = str96;
                                                    str34 = str98;
                                                    str35 = str101;
                                                    str36 = str95;
                                                    str37 = str97;
                                                    str38 = str100;
                                                    str39 = str99;
                                                    str40 = str102;
                                                    if (str103.equals(obj11)) {
                                                        it3 = it22;
                                                        obj10 = obj4;
                                                        c4 = 2;
                                                        obj12 = obj5;
                                                        break;
                                                    } else {
                                                        it3 = it22;
                                                        obj10 = obj4;
                                                        obj12 = obj5;
                                                        c4 = 65535;
                                                        break;
                                                    }
                                                case -1225497656:
                                                    str32 = str10;
                                                    str33 = str96;
                                                    str34 = str98;
                                                    str35 = str101;
                                                    str36 = str95;
                                                    str37 = str97;
                                                    str38 = str100;
                                                    str39 = str99;
                                                    str40 = str102;
                                                    it3 = it22;
                                                    obj10 = obj4;
                                                    if (str103.equals(str33)) {
                                                        obj11 = obj33;
                                                        c4 = 3;
                                                        obj12 = obj5;
                                                        break;
                                                    } else {
                                                        obj11 = obj33;
                                                        obj12 = obj5;
                                                        c4 = 65535;
                                                        break;
                                                    }
                                                case -1225497655:
                                                    str32 = str10;
                                                    str34 = str98;
                                                    str35 = str101;
                                                    str36 = str95;
                                                    str37 = str97;
                                                    str38 = str100;
                                                    str39 = str99;
                                                    str40 = str102;
                                                    it3 = it22;
                                                    obj10 = obj4;
                                                    obj11 = obj33;
                                                    if (str103.equals(str35)) {
                                                        str33 = str96;
                                                        c4 = 4;
                                                        obj12 = obj5;
                                                        break;
                                                    } else {
                                                        str33 = str96;
                                                        obj12 = obj5;
                                                        c4 = 65535;
                                                        break;
                                                    }
                                                case -1001078227:
                                                    str32 = str10;
                                                    str34 = str98;
                                                    str36 = str95;
                                                    str37 = str97;
                                                    str38 = str100;
                                                    str39 = str99;
                                                    str40 = str102;
                                                    if (str103.equals(str34)) {
                                                        it3 = it22;
                                                        obj10 = obj4;
                                                        obj11 = obj33;
                                                        str33 = str96;
                                                        c4 = 5;
                                                        str35 = str101;
                                                        obj12 = obj5;
                                                        break;
                                                    }
                                                    it3 = it22;
                                                    obj10 = obj4;
                                                    obj11 = obj33;
                                                    str33 = str96;
                                                    str35 = str101;
                                                    obj12 = obj5;
                                                    c4 = 65535;
                                                    break;
                                                case -908189618:
                                                    str32 = str10;
                                                    str36 = str95;
                                                    str37 = str97;
                                                    str38 = str100;
                                                    str39 = str99;
                                                    str40 = str102;
                                                    if (str103.equals(str36)) {
                                                        it3 = it22;
                                                        obj10 = obj4;
                                                        obj11 = obj33;
                                                        str33 = str96;
                                                        str35 = str101;
                                                        c4 = 6;
                                                        obj12 = obj5;
                                                        str34 = str98;
                                                        break;
                                                    }
                                                    str34 = str98;
                                                    it3 = it22;
                                                    obj10 = obj4;
                                                    obj11 = obj33;
                                                    str33 = str96;
                                                    str35 = str101;
                                                    obj12 = obj5;
                                                    c4 = 65535;
                                                    break;
                                                case -908189617:
                                                    str32 = str10;
                                                    str37 = str97;
                                                    str38 = str100;
                                                    str39 = str99;
                                                    str40 = str102;
                                                    if (str103.equals(str37)) {
                                                        it3 = it22;
                                                        obj10 = obj4;
                                                        obj11 = obj33;
                                                        str33 = str96;
                                                        str34 = str98;
                                                        str35 = str101;
                                                        c4 = 7;
                                                        obj12 = obj5;
                                                        str36 = str95;
                                                        break;
                                                    }
                                                    str36 = str95;
                                                    str34 = str98;
                                                    it3 = it22;
                                                    obj10 = obj4;
                                                    obj11 = obj33;
                                                    str33 = str96;
                                                    str35 = str101;
                                                    obj12 = obj5;
                                                    c4 = 65535;
                                                    break;
                                                case -797520672:
                                                    str32 = str10;
                                                    str38 = str100;
                                                    str39 = str99;
                                                    str40 = str102;
                                                    if (str103.equals("waveVariesBy")) {
                                                        c5 = '\b';
                                                        it3 = it22;
                                                        obj10 = obj4;
                                                        obj11 = obj33;
                                                        str33 = str96;
                                                        str34 = str98;
                                                        str35 = str101;
                                                        str36 = str95;
                                                        c4 = c5;
                                                        obj12 = obj5;
                                                        str37 = str97;
                                                        break;
                                                    }
                                                    str37 = str97;
                                                    str36 = str95;
                                                    str34 = str98;
                                                    it3 = it22;
                                                    obj10 = obj4;
                                                    obj11 = obj33;
                                                    str33 = str96;
                                                    str35 = str101;
                                                    obj12 = obj5;
                                                    c4 = 65535;
                                                    break;
                                                case -40300674:
                                                    str32 = str10;
                                                    str38 = str100;
                                                    str39 = str99;
                                                    str40 = str102;
                                                    if (str103.equals(str38)) {
                                                        c5 = '\t';
                                                        it3 = it22;
                                                        obj10 = obj4;
                                                        obj11 = obj33;
                                                        str33 = str96;
                                                        str34 = str98;
                                                        str35 = str101;
                                                        str36 = str95;
                                                        c4 = c5;
                                                        obj12 = obj5;
                                                        str37 = str97;
                                                        break;
                                                    }
                                                    str37 = str97;
                                                    str36 = str95;
                                                    str34 = str98;
                                                    it3 = it22;
                                                    obj10 = obj4;
                                                    obj11 = obj33;
                                                    str33 = str96;
                                                    str35 = str101;
                                                    obj12 = obj5;
                                                    c4 = 65535;
                                                    break;
                                                case -4379043:
                                                    str32 = str10;
                                                    str39 = str99;
                                                    str40 = str102;
                                                    it3 = it22;
                                                    obj10 = obj4;
                                                    obj11 = obj33;
                                                    str33 = str96;
                                                    str34 = str98;
                                                    str35 = str101;
                                                    str36 = str95;
                                                    str37 = str97;
                                                    if (str103.equals(str39)) {
                                                        str38 = str100;
                                                        c4 = '\n';
                                                        obj12 = obj5;
                                                        break;
                                                    } else {
                                                        str38 = str100;
                                                        obj12 = obj5;
                                                        c4 = 65535;
                                                        break;
                                                    }
                                                case 37232917:
                                                    str32 = str10;
                                                    str40 = str102;
                                                    if (str103.equals("transitionPathRotate")) {
                                                        c6 = 11;
                                                        it3 = it22;
                                                        obj10 = obj4;
                                                        obj11 = obj33;
                                                        str33 = str96;
                                                        str34 = str98;
                                                        str35 = str101;
                                                        str36 = str95;
                                                        str37 = str97;
                                                        str38 = str100;
                                                        c4 = c6;
                                                        obj12 = obj5;
                                                        str39 = str99;
                                                        break;
                                                    }
                                                    it3 = it22;
                                                    obj10 = obj4;
                                                    obj11 = obj33;
                                                    str33 = str96;
                                                    str34 = str98;
                                                    str35 = str101;
                                                    str36 = str95;
                                                    str37 = str97;
                                                    str38 = str100;
                                                    str39 = str99;
                                                    obj12 = obj5;
                                                    c4 = 65535;
                                                    break;
                                                case 92909918:
                                                    str32 = str10;
                                                    str40 = str102;
                                                    if (str103.equals(str40)) {
                                                        c6 = '\f';
                                                        it3 = it22;
                                                        obj10 = obj4;
                                                        obj11 = obj33;
                                                        str33 = str96;
                                                        str34 = str98;
                                                        str35 = str101;
                                                        str36 = str95;
                                                        str37 = str97;
                                                        str38 = str100;
                                                        c4 = c6;
                                                        obj12 = obj5;
                                                        str39 = str99;
                                                        break;
                                                    }
                                                    it3 = it22;
                                                    obj10 = obj4;
                                                    obj11 = obj33;
                                                    str33 = str96;
                                                    str34 = str98;
                                                    str35 = str101;
                                                    str36 = str95;
                                                    str37 = str97;
                                                    str38 = str100;
                                                    str39 = str99;
                                                    obj12 = obj5;
                                                    c4 = 65535;
                                                    break;
                                                case 156108012:
                                                    str32 = str10;
                                                    if (str103.equals(str32)) {
                                                        it3 = it22;
                                                        obj10 = obj4;
                                                        obj11 = obj33;
                                                        str33 = str96;
                                                        str34 = str98;
                                                        str35 = str101;
                                                        str36 = str95;
                                                        str37 = str97;
                                                        str38 = str100;
                                                        str39 = str99;
                                                        c4 = '\r';
                                                        obj12 = obj5;
                                                        str40 = str102;
                                                        break;
                                                    } else {
                                                        it3 = it22;
                                                        obj10 = obj4;
                                                        obj11 = obj33;
                                                        str33 = str96;
                                                        str34 = str98;
                                                        str35 = str101;
                                                        str36 = str95;
                                                        str37 = str97;
                                                        str38 = str100;
                                                        str39 = str99;
                                                        str40 = str102;
                                                        obj12 = obj5;
                                                        c4 = 65535;
                                                        break;
                                                    }
                                                default:
                                                    it3 = it22;
                                                    obj10 = obj4;
                                                    obj11 = obj33;
                                                    str32 = str10;
                                                    str33 = str96;
                                                    str34 = str98;
                                                    str35 = str101;
                                                    str36 = str95;
                                                    str37 = str97;
                                                    str38 = str100;
                                                    str39 = str99;
                                                    str40 = str102;
                                                    obj12 = obj5;
                                                    c4 = 65535;
                                                    break;
                                            }
                                            switch (c4) {
                                                case 0:
                                                    rotationXset = new ViewOscillator.RotationXset();
                                                    KeyCycleOscillator keyCycleOscillator2 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator2;
                                                    break;
                                                case 1:
                                                    rotationXset = new ViewOscillator.RotationYset();
                                                    KeyCycleOscillator keyCycleOscillator22 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator22;
                                                    break;
                                                case 2:
                                                    rotationXset = new ViewOscillator.TranslationXset();
                                                    KeyCycleOscillator keyCycleOscillator222 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator222;
                                                    break;
                                                case 3:
                                                    rotationXset = new ViewOscillator.TranslationYset();
                                                    KeyCycleOscillator keyCycleOscillator2222 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator2222;
                                                    break;
                                                case 4:
                                                    rotationXset = new ViewOscillator.TranslationZset();
                                                    KeyCycleOscillator keyCycleOscillator22222 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator22222;
                                                    break;
                                                case 5:
                                                    rotationXset = new ViewOscillator.ProgressSet();
                                                    KeyCycleOscillator keyCycleOscillator222222 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator222222;
                                                    break;
                                                case 6:
                                                    rotationXset = new ViewOscillator.ScaleXset();
                                                    KeyCycleOscillator keyCycleOscillator2222222 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator2222222;
                                                    break;
                                                case 7:
                                                    rotationXset = new ViewOscillator.ScaleYset();
                                                    KeyCycleOscillator keyCycleOscillator22222222 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator22222222;
                                                    break;
                                                case '\b':
                                                    rotationXset = new ViewOscillator.AlphaSet();
                                                    KeyCycleOscillator keyCycleOscillator222222222 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator222222222;
                                                    break;
                                                case '\t':
                                                    rotationXset = new ViewOscillator.RotationSet();
                                                    KeyCycleOscillator keyCycleOscillator2222222222 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator2222222222;
                                                    break;
                                                case '\n':
                                                    rotationXset = new ViewOscillator.ElevationSet();
                                                    KeyCycleOscillator keyCycleOscillator22222222222 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator22222222222;
                                                    break;
                                                case 11:
                                                    rotationXset = new ViewOscillator.PathRotateSet();
                                                    KeyCycleOscillator keyCycleOscillator222222222222 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator222222222222;
                                                    break;
                                                case '\f':
                                                    rotationXset = new ViewOscillator.AlphaSet();
                                                    KeyCycleOscillator keyCycleOscillator2222222222222 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator2222222222222;
                                                    break;
                                                case '\r':
                                                    rotationXset = new ViewOscillator.AlphaSet();
                                                    KeyCycleOscillator keyCycleOscillator22222222222222 = rotationXset;
                                                    obj4 = obj10;
                                                    keyCycleOscillator = keyCycleOscillator22222222222222;
                                                    break;
                                                default:
                                                    obj4 = obj10;
                                                    keyCycleOscillator = null;
                                                    break;
                                            }
                                        } else {
                                            it3 = it22;
                                            keyCycleOscillator = new ViewOscillator.CustomSet();
                                            obj11 = obj33;
                                            str32 = str10;
                                            str33 = str96;
                                            str34 = str98;
                                            str35 = str101;
                                            str36 = str95;
                                            str37 = str97;
                                            str38 = str100;
                                            str39 = str99;
                                            str40 = str102;
                                            obj12 = obj5;
                                        }
                                        if (keyCycleOscillator == null) {
                                            it22 = it3;
                                            str12 = str104;
                                            str10 = str32;
                                            str102 = str40;
                                            str99 = str39;
                                            str100 = str38;
                                            str97 = str37;
                                            str95 = str36;
                                            str98 = str34;
                                            str101 = str35;
                                            str96 = str33;
                                            obj5 = obj12;
                                            obj33 = obj11;
                                        } else {
                                            Object obj35 = obj11;
                                            String str105 = str33;
                                            if ((keyCycleOscillator.mVariesBy == 1) && Float.isNaN(f6)) {
                                                float[] fArr2 = new float[2];
                                                float f7 = 1.0f / 99;
                                                float f8 = 0.0f;
                                                str43 = str34;
                                                double d2 = 0.0d;
                                                double d3 = 0.0d;
                                                str44 = str35;
                                                int i24 = 0;
                                                while (i24 < 100) {
                                                    float f9 = i24 * f7;
                                                    String str106 = str37;
                                                    String str107 = str36;
                                                    double d4 = f9;
                                                    MotionPaths motionPaths9 = motionPaths3;
                                                    Easing easing = motionPaths9.mKeyFrameEasing;
                                                    Iterator it23 = arrayList2.iterator();
                                                    float f10 = 0.0f;
                                                    float f11 = Float.NaN;
                                                    while (it23.hasNext()) {
                                                        MotionPaths motionPaths10 = motionPaths9;
                                                        MotionPaths motionPaths11 = (MotionPaths) it23.next();
                                                        float f12 = f7;
                                                        Easing easing2 = motionPaths11.mKeyFrameEasing;
                                                        if (easing2 != null) {
                                                            float f13 = motionPaths11.time;
                                                            if (f13 < f9) {
                                                                f10 = f13;
                                                                easing = easing2;
                                                            } else if (Float.isNaN(f11)) {
                                                                f11 = motionPaths11.time;
                                                            }
                                                        }
                                                        motionPaths9 = motionPaths10;
                                                        f7 = f12;
                                                    }
                                                    MotionPaths motionPaths12 = motionPaths9;
                                                    float f14 = f7;
                                                    if (easing != null) {
                                                        if (Float.isNaN(f11)) {
                                                            f11 = 1.0f;
                                                        }
                                                        str46 = str38;
                                                        d = (((float) easing.get((f9 - f10) / r34)) * (f11 - f10)) + f10;
                                                    } else {
                                                        str46 = str38;
                                                        d = d4;
                                                    }
                                                    motionController.mSpline[0].getPos(d, motionController.mInterpolateData);
                                                    String str108 = str46;
                                                    motionController.mStartMotionPath.getCenter(d, motionController.mInterpolateVariables, motionController.mInterpolateData, fArr2, 0);
                                                    if (i24 > 0) {
                                                        f8 = (float) (Math.hypot(d2 - fArr2[1], d3 - fArr2[0]) + f8);
                                                    }
                                                    i24++;
                                                    d3 = fArr2[0];
                                                    d2 = fArr2[1];
                                                    str38 = str108;
                                                    str36 = str107;
                                                    motionPaths3 = motionPaths12;
                                                    f7 = f14;
                                                    str37 = str106;
                                                }
                                                str41 = str37;
                                                str42 = str36;
                                                motionPaths = motionPaths3;
                                                str45 = str38;
                                                f6 = f8;
                                            } else {
                                                str41 = str37;
                                                str42 = str36;
                                                str43 = str34;
                                                str44 = str35;
                                                motionPaths = motionPaths3;
                                                str45 = str38;
                                            }
                                            keyCycleOscillator.mType = str103;
                                            motionController.mCycleMap.put(str103, keyCycleOscillator);
                                            it22 = it3;
                                            str97 = str41;
                                            str102 = str40;
                                            str99 = str39;
                                            str100 = str45;
                                            str101 = str44;
                                            str98 = str43;
                                            str95 = str42;
                                            motionPaths3 = motionPaths;
                                            str12 = str104;
                                            str10 = str32;
                                            str96 = str105;
                                            obj5 = obj12;
                                            obj33 = obj35;
                                        }
                                    }
                                    String str109 = str10;
                                    String str110 = str12;
                                    String str111 = str98;
                                    String str112 = str101;
                                    String str113 = str95;
                                    String str114 = str97;
                                    String str115 = str100;
                                    String str116 = str99;
                                    String str117 = str102;
                                    Object obj36 = obj33;
                                    Object obj37 = obj5;
                                    String str118 = str96;
                                    Object obj38 = obj36;
                                    Iterator it24 = arrayList3.iterator();
                                    while (it24.hasNext()) {
                                        Key key6 = (Key) it24.next();
                                        if (key6 instanceof KeyCycle) {
                                            KeyCycle keyCycle2 = (KeyCycle) key6;
                                            HashMap hashMap13 = motionController.mCycleMap;
                                            keyCycle2.getClass();
                                            Iterator it25 = hashMap13.keySet().iterator();
                                            while (it25.hasNext()) {
                                                String str119 = (String) it25.next();
                                                if (str119.startsWith(str110)) {
                                                    ConstraintAttribute constraintAttribute5 = (ConstraintAttribute) keyCycle2.mCustomConstraints.get(str119.substring(7));
                                                    if (constraintAttribute5 != null) {
                                                        if (constraintAttribute5.mType == ConstraintAttribute.AttributeType.FLOAT_TYPE && (viewOscillator = (ViewOscillator) hashMap13.get(str119)) != null) {
                                                            int i25 = keyCycle2.mFramePosition;
                                                            int i26 = keyCycle2.mWaveShape;
                                                            String str120 = keyCycle2.mCustomWaveShape;
                                                            int i27 = keyCycle2.mWaveVariesBy;
                                                            it = it24;
                                                            it2 = it25;
                                                            hashMap = hashMap13;
                                                            str16 = str110;
                                                            viewOscillator.mWavePoints.add(new KeyCycleOscillator.WavePoint(i25, keyCycle2.mWavePeriod, keyCycle2.mWaveOffset, keyCycle2.mWavePhase, constraintAttribute5.getValueToInterpolate()));
                                                            if (i27 != -1) {
                                                                viewOscillator.mVariesBy = i27;
                                                            }
                                                            viewOscillator.mWaveShape = i26;
                                                            viewOscillator.setCustom(constraintAttribute5);
                                                            viewOscillator.mWaveString = str120;
                                                        } else {
                                                            it = it24;
                                                            hashMap = hashMap13;
                                                            str16 = str110;
                                                            it2 = it25;
                                                        }
                                                        str17 = str117;
                                                        str18 = str116;
                                                        obj7 = obj4;
                                                        str19 = str118;
                                                        obj8 = obj37;
                                                        str20 = str112;
                                                        obj9 = obj38;
                                                        str21 = str16;
                                                        str22 = str113;
                                                        str23 = str114;
                                                        keyCycle = keyCycle2;
                                                        str24 = str109;
                                                        str25 = str115;
                                                        str26 = str111;
                                                        it24 = it;
                                                        keyCycle2 = keyCycle;
                                                        obj37 = obj8;
                                                        str110 = str21;
                                                        str111 = str26;
                                                        str113 = str22;
                                                        it25 = it2;
                                                        str109 = str24;
                                                        hashMap13 = hashMap;
                                                        str114 = str23;
                                                        str115 = str25;
                                                        str116 = str18;
                                                        str117 = str17;
                                                        obj38 = obj9;
                                                        obj4 = obj7;
                                                        str112 = str20;
                                                        str118 = str19;
                                                    }
                                                } else {
                                                    it = it24;
                                                    HashMap hashMap14 = hashMap13;
                                                    String str121 = str110;
                                                    it2 = it25;
                                                    switch (str119.hashCode()) {
                                                        case -1249320806:
                                                            str27 = str114;
                                                            str28 = str115;
                                                            obj7 = obj4;
                                                            str19 = str118;
                                                            obj8 = obj37;
                                                            str29 = str112;
                                                            obj9 = obj38;
                                                            str30 = str111;
                                                            str31 = str113;
                                                            if (str119.equals(obj7)) {
                                                                c = 0;
                                                                break;
                                                            }
                                                            c = 65535;
                                                            break;
                                                        case -1249320805:
                                                            str27 = str114;
                                                            str28 = str115;
                                                            str19 = str118;
                                                            obj8 = obj37;
                                                            str29 = str112;
                                                            obj9 = obj38;
                                                            str30 = str111;
                                                            str31 = str113;
                                                            if (str119.equals(obj8)) {
                                                                c = 1;
                                                                obj7 = obj4;
                                                                break;
                                                            } else {
                                                                obj7 = obj4;
                                                                c = 65535;
                                                                break;
                                                            }
                                                        case -1225497657:
                                                            str27 = str114;
                                                            str28 = str115;
                                                            str19 = str118;
                                                            str29 = str112;
                                                            obj9 = obj38;
                                                            str30 = str111;
                                                            str31 = str113;
                                                            if (str119.equals(obj9)) {
                                                                c = 2;
                                                                obj7 = obj4;
                                                                obj8 = obj37;
                                                                break;
                                                            } else {
                                                                obj7 = obj4;
                                                                obj8 = obj37;
                                                                c = 65535;
                                                                break;
                                                            }
                                                        case -1225497656:
                                                            str27 = str114;
                                                            str28 = str115;
                                                            str19 = str118;
                                                            str29 = str112;
                                                            str30 = str111;
                                                            str31 = str113;
                                                            obj7 = obj4;
                                                            obj8 = obj37;
                                                            if (str119.equals(str19)) {
                                                                obj9 = obj38;
                                                                c = 3;
                                                                break;
                                                            }
                                                            obj9 = obj38;
                                                            c = 65535;
                                                            break;
                                                        case -1225497655:
                                                            str27 = str114;
                                                            str28 = str115;
                                                            str29 = str112;
                                                            str30 = str111;
                                                            str31 = str113;
                                                            if (str119.equals(str29)) {
                                                                obj7 = obj4;
                                                                str19 = str118;
                                                                obj8 = obj37;
                                                                obj9 = obj38;
                                                                c = 4;
                                                                break;
                                                            }
                                                            obj7 = obj4;
                                                            str19 = str118;
                                                            obj8 = obj37;
                                                            obj9 = obj38;
                                                            c = 65535;
                                                            break;
                                                        case -1001078227:
                                                            str27 = str114;
                                                            str28 = str115;
                                                            str30 = str111;
                                                            str31 = str113;
                                                            if (str119.equals(str30)) {
                                                                obj7 = obj4;
                                                                str19 = str118;
                                                                obj8 = obj37;
                                                                str29 = str112;
                                                                obj9 = obj38;
                                                                c = 5;
                                                                break;
                                                            } else {
                                                                str29 = str112;
                                                                obj7 = obj4;
                                                                str19 = str118;
                                                                obj8 = obj37;
                                                                obj9 = obj38;
                                                                c = 65535;
                                                                break;
                                                            }
                                                        case -908189618:
                                                            str27 = str114;
                                                            str28 = str115;
                                                            str31 = str113;
                                                            if (str119.equals(str31)) {
                                                                c = 6;
                                                                obj7 = obj4;
                                                                str19 = str118;
                                                                obj8 = obj37;
                                                                str29 = str112;
                                                                obj9 = obj38;
                                                                str30 = str111;
                                                                break;
                                                            }
                                                            obj7 = obj4;
                                                            str19 = str118;
                                                            obj8 = obj37;
                                                            str29 = str112;
                                                            obj9 = obj38;
                                                            str30 = str111;
                                                            c = 65535;
                                                            break;
                                                        case -908189617:
                                                            str27 = str114;
                                                            str28 = str115;
                                                            if (str119.equals(str27)) {
                                                                c = 7;
                                                                obj7 = obj4;
                                                                str19 = str118;
                                                                obj8 = obj37;
                                                                str29 = str112;
                                                                obj9 = obj38;
                                                                str30 = str111;
                                                                str31 = str113;
                                                                break;
                                                            } else {
                                                                str31 = str113;
                                                                obj7 = obj4;
                                                                str19 = str118;
                                                                obj8 = obj37;
                                                                str29 = str112;
                                                                obj9 = obj38;
                                                                str30 = str111;
                                                                c = 65535;
                                                                break;
                                                            }
                                                        case -40300674:
                                                            str28 = str115;
                                                            if (str119.equals(str28)) {
                                                                c2 = '\b';
                                                                c = c2;
                                                                obj7 = obj4;
                                                                str19 = str118;
                                                                obj8 = obj37;
                                                                str29 = str112;
                                                                obj9 = obj38;
                                                                str30 = str111;
                                                                str31 = str113;
                                                                str27 = str114;
                                                                break;
                                                            } else {
                                                                str27 = str114;
                                                                obj7 = obj4;
                                                                str19 = str118;
                                                                obj8 = obj37;
                                                                str29 = str112;
                                                                obj9 = obj38;
                                                                str30 = str111;
                                                                str31 = str113;
                                                                c = 65535;
                                                                break;
                                                            }
                                                        case -4379043:
                                                            if (str119.equals(str116)) {
                                                                str28 = str115;
                                                                c2 = '\t';
                                                                c = c2;
                                                                obj7 = obj4;
                                                                str19 = str118;
                                                                obj8 = obj37;
                                                                str29 = str112;
                                                                obj9 = obj38;
                                                                str30 = str111;
                                                                str31 = str113;
                                                                str27 = str114;
                                                                break;
                                                            }
                                                            str27 = str114;
                                                            str28 = str115;
                                                            obj7 = obj4;
                                                            str19 = str118;
                                                            obj8 = obj37;
                                                            str29 = str112;
                                                            obj9 = obj38;
                                                            str30 = str111;
                                                            str31 = str113;
                                                            c = 65535;
                                                            break;
                                                        case 37232917:
                                                            if (str119.equals("transitionPathRotate")) {
                                                                str28 = str115;
                                                                c2 = '\n';
                                                                c = c2;
                                                                obj7 = obj4;
                                                                str19 = str118;
                                                                obj8 = obj37;
                                                                str29 = str112;
                                                                obj9 = obj38;
                                                                str30 = str111;
                                                                str31 = str113;
                                                                str27 = str114;
                                                                break;
                                                            }
                                                            str27 = str114;
                                                            str28 = str115;
                                                            obj7 = obj4;
                                                            str19 = str118;
                                                            obj8 = obj37;
                                                            str29 = str112;
                                                            obj9 = obj38;
                                                            str30 = str111;
                                                            str31 = str113;
                                                            c = 65535;
                                                            break;
                                                        case 92909918:
                                                            if (str119.equals(str117)) {
                                                                str28 = str115;
                                                                c2 = 11;
                                                                c = c2;
                                                                obj7 = obj4;
                                                                str19 = str118;
                                                                obj8 = obj37;
                                                                str29 = str112;
                                                                obj9 = obj38;
                                                                str30 = str111;
                                                                str31 = str113;
                                                                str27 = str114;
                                                                break;
                                                            }
                                                            str27 = str114;
                                                            str28 = str115;
                                                            obj7 = obj4;
                                                            str19 = str118;
                                                            obj8 = obj37;
                                                            str29 = str112;
                                                            obj9 = obj38;
                                                            str30 = str111;
                                                            str31 = str113;
                                                            c = 65535;
                                                            break;
                                                        case 156108012:
                                                            if (str119.equals(str109)) {
                                                                c3 = '\f';
                                                                c2 = c3;
                                                                str28 = str115;
                                                                c = c2;
                                                                obj7 = obj4;
                                                                str19 = str118;
                                                                obj8 = obj37;
                                                                str29 = str112;
                                                                obj9 = obj38;
                                                                str30 = str111;
                                                                str31 = str113;
                                                                str27 = str114;
                                                                break;
                                                            }
                                                            str27 = str114;
                                                            str28 = str115;
                                                            obj7 = obj4;
                                                            str19 = str118;
                                                            obj8 = obj37;
                                                            str29 = str112;
                                                            obj9 = obj38;
                                                            str30 = str111;
                                                            str31 = str113;
                                                            c = 65535;
                                                            break;
                                                        case 1530034690:
                                                            if (str119.equals("wavePhase")) {
                                                                c3 = '\r';
                                                                c2 = c3;
                                                                str28 = str115;
                                                                c = c2;
                                                                obj7 = obj4;
                                                                str19 = str118;
                                                                obj8 = obj37;
                                                                str29 = str112;
                                                                obj9 = obj38;
                                                                str30 = str111;
                                                                str31 = str113;
                                                                str27 = str114;
                                                                break;
                                                            }
                                                            str27 = str114;
                                                            str28 = str115;
                                                            obj7 = obj4;
                                                            str19 = str118;
                                                            obj8 = obj37;
                                                            str29 = str112;
                                                            obj9 = obj38;
                                                            str30 = str111;
                                                            str31 = str113;
                                                            c = 65535;
                                                            break;
                                                        default:
                                                            str27 = str114;
                                                            str28 = str115;
                                                            obj7 = obj4;
                                                            str19 = str118;
                                                            obj8 = obj37;
                                                            str29 = str112;
                                                            obj9 = obj38;
                                                            str30 = str111;
                                                            str31 = str113;
                                                            c = 65535;
                                                            break;
                                                    }
                                                    switch (c) {
                                                        case 0:
                                                            f = keyCycle2.mRotationX;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        case 1:
                                                            f = keyCycle2.mRotationY;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        case 2:
                                                            f = keyCycle2.mTranslationX;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        case 3:
                                                            f = keyCycle2.mTranslationY;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        case 4:
                                                            f = keyCycle2.mTranslationZ;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        case 5:
                                                            f = keyCycle2.mProgress;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        case 6:
                                                            f = keyCycle2.mScaleX;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        case 7:
                                                            f = keyCycle2.mScaleY;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        case '\b':
                                                            f = keyCycle2.mRotation;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        case '\t':
                                                            f = keyCycle2.mElevation;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        case '\n':
                                                            f = keyCycle2.mTransitionPathRotate;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        case 11:
                                                            f = keyCycle2.mAlpha;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        case '\f':
                                                            f = keyCycle2.mWaveOffset;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        case '\r':
                                                            f = keyCycle2.mWavePhase;
                                                            f2 = f;
                                                            str21 = str121;
                                                            break;
                                                        default:
                                                            str21 = str121;
                                                            str119.startsWith(str21);
                                                            f2 = Float.NaN;
                                                            break;
                                                    }
                                                    if (Float.isNaN(f2)) {
                                                        str25 = str28;
                                                        hashMap2 = hashMap14;
                                                    } else {
                                                        str25 = str28;
                                                        hashMap2 = hashMap14;
                                                        ViewOscillator viewOscillator2 = (ViewOscillator) hashMap2.get(str119);
                                                        if (viewOscillator2 != null) {
                                                            hashMap = hashMap2;
                                                            int i28 = keyCycle2.mFramePosition;
                                                            str23 = str27;
                                                            int i29 = keyCycle2.mWaveShape;
                                                            str22 = str31;
                                                            String str122 = keyCycle2.mCustomWaveShape;
                                                            str26 = str30;
                                                            int i30 = keyCycle2.mWaveVariesBy;
                                                            str24 = str109;
                                                            str17 = str117;
                                                            str18 = str116;
                                                            keyCycle = keyCycle2;
                                                            str20 = str29;
                                                            viewOscillator2.mWavePoints.add(new KeyCycleOscillator.WavePoint(i28, keyCycle2.mWavePeriod, keyCycle2.mWaveOffset, keyCycle2.mWavePhase, f2));
                                                            if (i30 != -1) {
                                                                viewOscillator2.mVariesBy = i30;
                                                            }
                                                            viewOscillator2.mWaveShape = i29;
                                                            viewOscillator2.mWaveString = str122;
                                                            it24 = it;
                                                            keyCycle2 = keyCycle;
                                                            obj37 = obj8;
                                                            str110 = str21;
                                                            str111 = str26;
                                                            str113 = str22;
                                                            it25 = it2;
                                                            str109 = str24;
                                                            hashMap13 = hashMap;
                                                            str114 = str23;
                                                            str115 = str25;
                                                            str116 = str18;
                                                            str117 = str17;
                                                            obj38 = obj9;
                                                            obj4 = obj7;
                                                            str112 = str20;
                                                            str118 = str19;
                                                        }
                                                    }
                                                    str114 = str27;
                                                    str113 = str31;
                                                    str111 = str30;
                                                    str112 = str29;
                                                    str118 = str19;
                                                    obj38 = obj9;
                                                    obj37 = obj8;
                                                    obj4 = obj7;
                                                    str110 = str21;
                                                    it25 = it2;
                                                    str115 = str25;
                                                    hashMap13 = hashMap2;
                                                    it24 = it;
                                                }
                                            }
                                        }
                                        motionController = this;
                                        it24 = it24;
                                        obj37 = obj37;
                                        str110 = str110;
                                        str111 = str111;
                                        str113 = str113;
                                        str109 = str109;
                                        str114 = str114;
                                        str115 = str115;
                                        str116 = str116;
                                        str117 = str117;
                                        obj38 = obj38;
                                        obj4 = obj4;
                                        str112 = str112;
                                        str118 = str118;
                                    }
                                    Iterator it26 = motionController.mCycleMap.values().iterator();
                                    while (it26.hasNext()) {
                                        ((ViewOscillator) it26.next()).setup();
                                    }
                                    return;
                                }
                                return;
                            }
                            String str123 = strArr3[i22];
                            int i31 = 0;
                            int i32 = 0;
                            double[] dArr9 = null;
                            double[][] dArr10 = null;
                            while (i31 < size) {
                                if (motionPathsArr[i31].attributes.containsKey(str123)) {
                                    if (dArr10 == null) {
                                        dArr9 = new double[size];
                                        ConstraintAttribute constraintAttribute6 = (ConstraintAttribute) motionPathsArr[i31].attributes.get(str123);
                                        dArr10 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, constraintAttribute6 == null ? 0 : constraintAttribute6.numberOfInterpolatedValues());
                                    }
                                    MotionPaths motionPaths13 = motionPathsArr[i31];
                                    dArr9[i32] = motionPaths13.time;
                                    double[] dArr11 = dArr10[i32];
                                    ConstraintAttribute constraintAttribute7 = (ConstraintAttribute) motionPaths13.attributes.get(str123);
                                    if (constraintAttribute7 == null) {
                                        str47 = str123;
                                        str48 = str64;
                                        dArr = dArr9;
                                        dArr2 = dArr10;
                                    } else {
                                        str47 = str123;
                                        if (constraintAttribute7.numberOfInterpolatedValues() == 1) {
                                            dArr = dArr9;
                                            dArr2 = dArr10;
                                            dArr11[0] = constraintAttribute7.getValueToInterpolate();
                                        } else {
                                            dArr = dArr9;
                                            dArr2 = dArr10;
                                            int numberOfInterpolatedValues = constraintAttribute7.numberOfInterpolatedValues();
                                            float[] fArr3 = new float[numberOfInterpolatedValues];
                                            constraintAttribute7.getValuesToInterpolate(fArr3);
                                            int i33 = 0;
                                            int i34 = 0;
                                            while (i33 < numberOfInterpolatedValues) {
                                                dArr11[i34] = fArr3[i33];
                                                i33++;
                                                i34++;
                                                numberOfInterpolatedValues = numberOfInterpolatedValues;
                                                str64 = str64;
                                                fArr3 = fArr3;
                                            }
                                        }
                                        str48 = str64;
                                    }
                                    i32++;
                                    dArr9 = dArr;
                                    dArr10 = dArr2;
                                } else {
                                    str47 = str123;
                                    str48 = str64;
                                }
                                i31++;
                                str123 = str47;
                                str64 = str48;
                            }
                            i22++;
                            motionController.mSpline[i22] = CurveFit.get(motionController.mCurveFitType, Arrays.copyOf(dArr9, i32), (double[][]) Arrays.copyOf(dArr10, i32));
                            str64 = str64;
                        }
                    }
                }
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(" start: x: ");
        MotionPaths motionPaths = this.mStartMotionPath;
        sb.append(motionPaths.f27x);
        sb.append(" y: ");
        sb.append(motionPaths.f28y);
        sb.append(" end: x: ");
        MotionPaths motionPaths2 = this.mEndMotionPath;
        sb.append(motionPaths2.f27x);
        sb.append(" y: ");
        sb.append(motionPaths2.f28y);
        return sb.toString();
    }
}
