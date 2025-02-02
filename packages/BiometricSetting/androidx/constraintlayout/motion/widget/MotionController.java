package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.motion.utils.ArcCurveFit;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.VelocityMatrix;
import androidx.constraintlayout.core.widgets.analyzer.RunGroup$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.utils.CustomSupport;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.motion.utils.ViewTimeCycle;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class MotionController {
    private ArcCurveFit mArcSpline;
    private int[] mAttributeInterpolatorCount;
    private String[] mAttributeNames;
    private HashMap<String, ViewSpline> mAttributesMap;
    private HashMap<String, ViewOscillator> mCycleMap;
    int mId;
    private double[] mInterpolateData;
    private int[] mInterpolateVariables;
    private double[] mInterpolateVelocity;
    private KeyTrigger[] mKeyTriggers;
    private CurveFit[] mSpline;
    private HashMap<String, ViewTimeCycle> mTimeCycleAttributesMap;
    View mView;
    Rect mTempRect = new Rect();
    boolean mForceMeasure = false;
    private int mCurveFitType = -1;
    private MotionPaths mStartMotionPath = new MotionPaths();
    private MotionPaths mEndMotionPath = new MotionPaths();
    private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
    private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
    float mMotionStagger = Float.NaN;
    float mStaggerOffset = 0.0f;
    float mStaggerScale = 1.0f;
    private float[] mValuesBuff = new float[4];
    private ArrayList<MotionPaths> mMotionPaths = new ArrayList<>();
    private float[] mVelocity = new float[1];
    private ArrayList<Key> mKeyList = new ArrayList<>();
    private int mPathMotionArc = -1;
    private int mTransformPivotTarget = -1;
    private View mTransformPivotView = null;
    private int mQuantizeMotionSteps = -1;
    private float mQuantizeMotionPhase = Float.NaN;
    private Interpolator mQuantizeMotionInterpolator = null;
    private boolean mNoMovement = false;

    MotionController(View view) {
        this.mView = view;
        this.mId = view.getId();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            ((ConstraintLayout.LayoutParams) layoutParams).getClass();
        }
    }

    private float getAdjustedPosition(float f, float[] fArr) {
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
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        float f5 = Float.NaN;
        while (it.hasNext()) {
            MotionPaths next = it.next();
            Easing easing2 = next.mKeyFrameEasing;
            if (easing2 != null) {
                float f6 = next.mTime;
                if (f6 < f) {
                    easing = easing2;
                    f2 = f6;
                } else if (Float.isNaN(f5)) {
                    f5 = next.mTime;
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

    private void readView(MotionPaths motionPaths) {
        motionPaths.setBounds((int) this.mView.getX(), (int) this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
    }

    static void rotate(Rect rect, Rect rect2, int i, int i2, int i3) {
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

    final void addKeys(ArrayList<Key> arrayList) {
        this.mKeyList.addAll(arrayList);
    }

    final int buildKeyFrames(float[] fArr, int[] iArr) {
        if (fArr == null) {
            return 0;
        }
        double[] timePoints = this.mSpline[0].getTimePoints();
        if (iArr != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            int i = 0;
            while (it.hasNext()) {
                iArr[i] = it.next().mMode;
                i++;
            }
        }
        int i2 = 0;
        for (int i3 = 0; i3 < timePoints.length; i3++) {
            this.mSpline[0].getPos(timePoints[i3], this.mInterpolateData);
            this.mStartMotionPath.getCenter(timePoints[i3], this.mInterpolateVariables, this.mInterpolateData, fArr, i2);
            i2 += 2;
        }
        return i2 / 2;
    }

    final void buildPath(int i, float[] fArr) {
        double d;
        int i2 = i;
        float f = 1.0f;
        float f2 = 1.0f / (i2 - 1);
        HashMap<String, ViewSpline> hashMap = this.mAttributesMap;
        ViewSpline viewSpline = hashMap == null ? null : hashMap.get("translationX");
        HashMap<String, ViewSpline> hashMap2 = this.mAttributesMap;
        ViewSpline viewSpline2 = hashMap2 == null ? null : hashMap2.get("translationY");
        HashMap<String, ViewOscillator> hashMap3 = this.mCycleMap;
        ViewOscillator viewOscillator = hashMap3 == null ? null : hashMap3.get("translationX");
        HashMap<String, ViewOscillator> hashMap4 = this.mCycleMap;
        ViewOscillator viewOscillator2 = hashMap4 != null ? hashMap4.get("translationY") : null;
        int i3 = 0;
        while (i3 < i2) {
            float f3 = i3 * f2;
            float f4 = this.mStaggerScale;
            float f5 = 0.0f;
            if (f4 != f) {
                float f6 = this.mStaggerOffset;
                if (f3 < f6) {
                    f3 = 0.0f;
                }
                if (f3 > f6 && f3 < 1.0d) {
                    f3 = Math.min((f3 - f6) * f4, f);
                }
            }
            float f7 = f3;
            double d2 = f7;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            float f8 = Float.NaN;
            while (it.hasNext()) {
                MotionPaths next = it.next();
                Easing easing2 = next.mKeyFrameEasing;
                if (easing2 != null) {
                    float f9 = next.mTime;
                    if (f9 < f7) {
                        f5 = f9;
                        easing = easing2;
                    } else if (Float.isNaN(f8)) {
                        f8 = next.mTime;
                    }
                }
            }
            if (easing != null) {
                if (Float.isNaN(f8)) {
                    f8 = 1.0f;
                }
                d = (((float) easing.get((f7 - f5) / r16)) * (f8 - f5)) + f5;
            } else {
                d = d2;
            }
            this.mSpline[0].getPos(d, this.mInterpolateData);
            ArcCurveFit arcCurveFit = this.mArcSpline;
            if (arcCurveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    arcCurveFit.getPos(d, dArr);
                }
            }
            int i4 = i3 * 2;
            int i5 = i3;
            this.mStartMotionPath.getCenter(d, this.mInterpolateVariables, this.mInterpolateData, fArr, i4);
            if (viewOscillator != null) {
                fArr[i4] = viewOscillator.get(f7) + fArr[i4];
            } else if (viewSpline != null) {
                fArr[i4] = viewSpline.get(f7) + fArr[i4];
            }
            if (viewOscillator2 != null) {
                int i6 = i4 + 1;
                fArr[i6] = viewOscillator2.get(f7) + fArr[i6];
            } else if (viewSpline2 != null) {
                int i7 = i4 + 1;
                fArr[i7] = viewSpline2.get(f7) + fArr[i7];
            }
            i3 = i5 + 1;
            i2 = i;
            f = 1.0f;
        }
    }

    final void buildRect(float f, float[] fArr) {
        this.mSpline[0].getPos(getAdjustedPosition(f, null), this.mInterpolateData);
        MotionPaths motionPaths = this.mStartMotionPath;
        int[] iArr = this.mInterpolateVariables;
        double[] dArr = this.mInterpolateData;
        float f2 = motionPaths.f11mX;
        float f3 = motionPaths.f12mY;
        float f4 = motionPaths.mWidth;
        float f5 = motionPaths.mHeight;
        for (int i = 0; i < iArr.length; i++) {
            float f6 = (float) dArr[i];
            int i2 = iArr[i];
            if (i2 == 1) {
                f2 = f6;
            } else if (i2 == 2) {
                f3 = f6;
            } else if (i2 == 3) {
                f4 = f6;
            } else if (i2 == 4) {
                f5 = f6;
            }
        }
        if (motionPaths.mRelativeToController != null) {
            double d = 0.0f;
            double d2 = f2;
            double d3 = f3;
            float sin = (float) (((Math.sin(d3) * d2) + d) - (f4 / 2.0f));
            f3 = (float) ((d - (Math.cos(d3) * d2)) - (f5 / 2.0f));
            f2 = sin;
        }
        float f7 = f4 + f2;
        float f8 = f5 + f3;
        Float.isNaN(Float.NaN);
        Float.isNaN(Float.NaN);
        float f9 = f2 + 0.0f;
        float f10 = f3 + 0.0f;
        float f11 = f7 + 0.0f;
        float f12 = f8 + 0.0f;
        fArr[0] = f9;
        fArr[1] = f10;
        fArr[2] = f11;
        fArr[3] = f10;
        fArr[4] = f11;
        fArr[5] = f12;
        fArr[6] = f9;
        fArr[7] = f12;
    }

    final void endTrigger(boolean z) {
        if (!"button".equals(Debug.getName(this.mView)) || this.mKeyTriggers == null) {
            return;
        }
        int i = 0;
        while (true) {
            KeyTrigger[] keyTriggerArr = this.mKeyTriggers;
            if (i >= keyTriggerArr.length) {
                return;
            }
            keyTriggerArr[i].conditionallyFire(this.mView, z ? -100.0f : 100.0f);
            i++;
        }
    }

    public final int getAnimateRelativeTo() {
        return this.mStartMotionPath.mAnimateRelativeTo;
    }

    public final void getCenter(double d, float[] fArr, float[] fArr2) {
        double[] dArr = new double[4];
        double[] dArr2 = new double[4];
        this.mSpline[0].getPos(d, dArr);
        this.mSpline[0].getSlope(d, dArr2);
        float f = 0.0f;
        Arrays.fill(fArr2, 0.0f);
        MotionPaths motionPaths = this.mStartMotionPath;
        int[] iArr = this.mInterpolateVariables;
        float f2 = motionPaths.f11mX;
        float f3 = motionPaths.f12mY;
        float f4 = motionPaths.mWidth;
        float f5 = motionPaths.mHeight;
        float f6 = 0.0f;
        float f7 = 0.0f;
        int i = 0;
        float f8 = 0.0f;
        while (i < iArr.length) {
            float f9 = (float) dArr[i];
            float f10 = (float) dArr2[i];
            int i2 = iArr[i];
            int[] iArr2 = iArr;
            if (i2 == 1) {
                f = f10;
                f2 = f9;
            } else if (i2 == 2) {
                f8 = f10;
                f3 = f9;
            } else if (i2 == 3) {
                f6 = f10;
                f4 = f9;
            } else if (i2 == 4) {
                f7 = f10;
                f5 = f9;
            }
            i++;
            iArr = iArr2;
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
            double d4 = f;
            double d5 = f8;
            float cos = (float) ((Math.cos(d3) * d5) + (Math.sin(d3) * d4) + f15);
            f12 = (float) ((Math.sin(d3) * d5) + (f16 - (Math.cos(d3) * d4)));
            f2 = sin;
            f11 = cos;
        }
        fArr[0] = (f4 / 2.0f) + f2 + 0.0f;
        fArr[1] = (f5 / 2.0f) + f3 + 0.0f;
        fArr2[0] = f11;
        fArr2[1] = f12;
    }

    final void getDpDt(float f, float f2, float f3, float[] fArr) {
        double[] dArr;
        float adjustedPosition = getAdjustedPosition(f, this.mVelocity);
        CurveFit[] curveFitArr = this.mSpline;
        int i = 0;
        if (curveFitArr == null) {
            MotionPaths motionPaths = this.mEndMotionPath;
            float f4 = motionPaths.f11mX;
            MotionPaths motionPaths2 = this.mStartMotionPath;
            float f5 = f4 - motionPaths2.f11mX;
            float f6 = motionPaths.f12mY - motionPaths2.f12mY;
            float f7 = motionPaths.mWidth - motionPaths2.mWidth;
            float f8 = (motionPaths.mHeight - motionPaths2.mHeight) + f6;
            fArr[0] = ((f7 + f5) * f2) + ((1.0f - f2) * f5);
            fArr[1] = (f8 * f3) + ((1.0f - f3) * f6);
            return;
        }
        double d = adjustedPosition;
        curveFitArr[0].getSlope(d, this.mInterpolateVelocity);
        this.mSpline[0].getPos(d, this.mInterpolateData);
        float f9 = this.mVelocity[0];
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
            MotionPaths motionPaths3 = this.mStartMotionPath;
            int[] iArr = this.mInterpolateVariables;
            double[] dArr2 = this.mInterpolateData;
            motionPaths3.getClass();
            MotionPaths.setDpDt(f2, f3, fArr, iArr, dArr, dArr2);
            return;
        }
        double[] dArr3 = this.mInterpolateData;
        if (dArr3.length > 0) {
            arcCurveFit.getPos(d, dArr3);
            this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
            MotionPaths motionPaths4 = this.mStartMotionPath;
            int[] iArr2 = this.mInterpolateVariables;
            double[] dArr4 = this.mInterpolateVelocity;
            double[] dArr5 = this.mInterpolateData;
            motionPaths4.getClass();
            MotionPaths.setDpDt(f2, f3, fArr, iArr2, dArr4, dArr5);
        }
    }

    public final int getDrawPath() {
        int i = this.mStartMotionPath.mDrawPath;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            i = Math.max(i, it.next().mDrawPath);
        }
        return Math.max(i, this.mEndMotionPath.mDrawPath);
    }

    public final float getFinalX() {
        return this.mEndMotionPath.f11mX;
    }

    public final float getFinalY() {
        return this.mEndMotionPath.f12mY;
    }

    final void getKeyFrame(int i) {
        this.mMotionPaths.get(i);
    }

    final void getPostLayoutDvDp(float f, int i, int i2, float f2, float f3, float[] fArr) {
        float adjustedPosition = getAdjustedPosition(f, this.mVelocity);
        HashMap<String, ViewSpline> hashMap = this.mAttributesMap;
        ViewSpline viewSpline = hashMap == null ? null : hashMap.get("translationX");
        HashMap<String, ViewSpline> hashMap2 = this.mAttributesMap;
        ViewSpline viewSpline2 = hashMap2 == null ? null : hashMap2.get("translationY");
        HashMap<String, ViewSpline> hashMap3 = this.mAttributesMap;
        ViewSpline viewSpline3 = hashMap3 == null ? null : hashMap3.get("rotation");
        HashMap<String, ViewSpline> hashMap4 = this.mAttributesMap;
        ViewSpline viewSpline4 = hashMap4 == null ? null : hashMap4.get("scaleX");
        HashMap<String, ViewSpline> hashMap5 = this.mAttributesMap;
        ViewSpline viewSpline5 = hashMap5 == null ? null : hashMap5.get("scaleY");
        HashMap<String, ViewOscillator> hashMap6 = this.mCycleMap;
        ViewOscillator viewOscillator = hashMap6 == null ? null : hashMap6.get("translationX");
        HashMap<String, ViewOscillator> hashMap7 = this.mCycleMap;
        ViewOscillator viewOscillator2 = hashMap7 == null ? null : hashMap7.get("translationY");
        HashMap<String, ViewOscillator> hashMap8 = this.mCycleMap;
        ViewOscillator viewOscillator3 = hashMap8 == null ? null : hashMap8.get("rotation");
        HashMap<String, ViewOscillator> hashMap9 = this.mCycleMap;
        ViewOscillator viewOscillator4 = hashMap9 == null ? null : hashMap9.get("scaleX");
        HashMap<String, ViewOscillator> hashMap10 = this.mCycleMap;
        ViewOscillator viewOscillator5 = hashMap10 != null ? hashMap10.get("scaleY") : null;
        VelocityMatrix velocityMatrix = new VelocityMatrix();
        velocityMatrix.clear();
        velocityMatrix.setRotationVelocity(viewSpline3, adjustedPosition);
        velocityMatrix.setTranslationVelocity(viewSpline, viewSpline2, adjustedPosition);
        velocityMatrix.setScaleVelocity(viewSpline4, viewSpline5, adjustedPosition);
        velocityMatrix.setRotationVelocity(viewOscillator3, adjustedPosition);
        velocityMatrix.setTranslationVelocity(viewOscillator, viewOscillator2, adjustedPosition);
        velocityMatrix.setScaleVelocity(viewOscillator4, viewOscillator5, adjustedPosition);
        ArcCurveFit arcCurveFit = this.mArcSpline;
        if (arcCurveFit != null) {
            double[] dArr = this.mInterpolateData;
            if (dArr.length > 0) {
                double d = adjustedPosition;
                arcCurveFit.getPos(d, dArr);
                this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
                MotionPaths motionPaths = this.mStartMotionPath;
                int[] iArr = this.mInterpolateVariables;
                double[] dArr2 = this.mInterpolateVelocity;
                double[] dArr3 = this.mInterpolateData;
                motionPaths.getClass();
                MotionPaths.setDpDt(f2, f3, fArr, iArr, dArr2, dArr3);
            }
            velocityMatrix.applyTransform(f2, f3, i, i2, fArr);
            return;
        }
        int i3 = 0;
        if (this.mSpline == null) {
            MotionPaths motionPaths2 = this.mEndMotionPath;
            float f4 = motionPaths2.f11mX;
            MotionPaths motionPaths3 = this.mStartMotionPath;
            float f5 = f4 - motionPaths3.f11mX;
            ViewOscillator viewOscillator6 = viewOscillator5;
            float f6 = motionPaths2.f12mY - motionPaths3.f12mY;
            ViewOscillator viewOscillator7 = viewOscillator4;
            float f7 = motionPaths2.mWidth - motionPaths3.mWidth;
            float f8 = (motionPaths2.mHeight - motionPaths3.mHeight) + f6;
            fArr[0] = ((f7 + f5) * f2) + ((1.0f - f2) * f5);
            fArr[1] = (f8 * f3) + ((1.0f - f3) * f6);
            velocityMatrix.clear();
            velocityMatrix.setRotationVelocity(viewSpline3, adjustedPosition);
            velocityMatrix.setTranslationVelocity(viewSpline, viewSpline2, adjustedPosition);
            velocityMatrix.setScaleVelocity(viewSpline4, viewSpline5, adjustedPosition);
            velocityMatrix.setRotationVelocity(viewOscillator3, adjustedPosition);
            velocityMatrix.setTranslationVelocity(viewOscillator, viewOscillator2, adjustedPosition);
            velocityMatrix.setScaleVelocity(viewOscillator7, viewOscillator6, adjustedPosition);
            velocityMatrix.applyTransform(f2, f3, i, i2, fArr);
            return;
        }
        double adjustedPosition2 = getAdjustedPosition(adjustedPosition, this.mVelocity);
        this.mSpline[0].getSlope(adjustedPosition2, this.mInterpolateVelocity);
        this.mSpline[0].getPos(adjustedPosition2, this.mInterpolateData);
        float f9 = this.mVelocity[0];
        while (true) {
            double[] dArr4 = this.mInterpolateVelocity;
            if (i3 >= dArr4.length) {
                MotionPaths motionPaths4 = this.mStartMotionPath;
                int[] iArr2 = this.mInterpolateVariables;
                double[] dArr5 = this.mInterpolateData;
                motionPaths4.getClass();
                MotionPaths.setDpDt(f2, f3, fArr, iArr2, dArr4, dArr5);
                velocityMatrix.applyTransform(f2, f3, i, i2, fArr);
                return;
            }
            dArr4[i3] = dArr4[i3] * f9;
            i3++;
        }
    }

    public final float getStartX() {
        return this.mStartMotionPath.f11mX;
    }

    public final float getStartY() {
        return this.mStartMotionPath.f12mY;
    }

    /* JADX WARN: Multi-variable type inference failed */
    final boolean interpolate(float f, long j, View view, KeyCache keyCache) {
        boolean z;
        int i;
        boolean z2;
        ViewTimeCycle.PathRotate pathRotate;
        float f2;
        boolean z3;
        double d;
        int i2;
        boolean z4;
        float f3;
        float f4;
        boolean z5;
        float f5;
        ViewTimeCycle.PathRotate pathRotate2 = null;
        float adjustedPosition = getAdjustedPosition(f, null);
        int i3 = this.mQuantizeMotionSteps;
        float f6 = 1.0f;
        if (i3 != -1) {
            float f7 = 1.0f / i3;
            float floor = ((float) Math.floor(adjustedPosition / f7)) * f7;
            float f8 = (adjustedPosition % f7) / f7;
            if (!Float.isNaN(this.mQuantizeMotionPhase)) {
                f8 = (f8 + this.mQuantizeMotionPhase) % 1.0f;
            }
            Interpolator interpolator = this.mQuantizeMotionInterpolator;
            if (interpolator != null) {
                f6 = interpolator.getInterpolation(f8);
            } else if (f8 <= 0.5d) {
                f6 = 0.0f;
            }
            adjustedPosition = (f6 * f7) + floor;
        }
        float f9 = adjustedPosition;
        HashMap<String, ViewSpline> hashMap = this.mAttributesMap;
        if (hashMap != null) {
            Iterator<ViewSpline> it = hashMap.values().iterator();
            while (it.hasNext()) {
                it.next().setProperty(view, f9);
            }
        }
        HashMap<String, ViewTimeCycle> hashMap2 = this.mTimeCycleAttributesMap;
        if (hashMap2 != null) {
            ViewTimeCycle.PathRotate pathRotate3 = null;
            boolean z6 = false;
            for (ViewTimeCycle viewTimeCycle : hashMap2.values()) {
                if (viewTimeCycle instanceof ViewTimeCycle.PathRotate) {
                    pathRotate3 = (ViewTimeCycle.PathRotate) viewTimeCycle;
                } else {
                    z6 |= viewTimeCycle.setProperty(f9, j, view, keyCache);
                }
            }
            pathRotate2 = pathRotate3;
            z = z6;
        } else {
            z = false;
        }
        CurveFit[] curveFitArr = this.mSpline;
        if (curveFitArr != null) {
            double d2 = f9;
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
                pathRotate = pathRotate2;
                f2 = f9;
                z3 = z;
                d = d2;
            } else {
                MotionPaths motionPaths = this.mStartMotionPath;
                int[] iArr = this.mInterpolateVariables;
                double[] dArr2 = this.mInterpolateData;
                double[] dArr3 = this.mInterpolateVelocity;
                boolean z7 = this.mForceMeasure;
                float f10 = motionPaths.f11mX;
                float f11 = motionPaths.f12mY;
                float f12 = motionPaths.mWidth;
                float f13 = motionPaths.mHeight;
                if (iArr.length != 0) {
                    f4 = f11;
                    if (motionPaths.mTempValue.length <= iArr[iArr.length - 1]) {
                        int i4 = iArr[iArr.length - 1] + 1;
                        motionPaths.mTempValue = new double[i4];
                        motionPaths.mTempDelta = new double[i4];
                    }
                } else {
                    f4 = f11;
                }
                float f14 = f12;
                Arrays.fill(motionPaths.mTempValue, Double.NaN);
                for (int i5 = 0; i5 < iArr.length; i5++) {
                    double[] dArr4 = motionPaths.mTempValue;
                    int i6 = iArr[i5];
                    dArr4[i6] = dArr2[i5];
                    motionPaths.mTempDelta[i6] = dArr3[i5];
                }
                float f15 = Float.NaN;
                int i7 = 0;
                float f16 = 0.0f;
                float f17 = f13;
                float f18 = 0.0f;
                float f19 = 0.0f;
                float f20 = f10;
                z3 = z;
                float f21 = f4;
                float f22 = 0.0f;
                float f23 = f21;
                while (true) {
                    double[] dArr5 = motionPaths.mTempValue;
                    pathRotate = pathRotate2;
                    if (i7 >= dArr5.length) {
                        break;
                    }
                    if (Double.isNaN(dArr5[i7])) {
                        f5 = f9;
                    } else {
                        f5 = f9;
                        float f24 = (float) (Double.isNaN(motionPaths.mTempValue[i7]) ? 0.0d : motionPaths.mTempValue[i7] + 0.0d);
                        float f25 = (float) motionPaths.mTempDelta[i7];
                        if (i7 == 1) {
                            f20 = f24;
                            f19 = f25;
                        } else if (i7 == 2) {
                            f23 = f24;
                            f18 = f25;
                        } else if (i7 == 3) {
                            f14 = f24;
                            f22 = f25;
                        } else if (i7 == 4) {
                            f17 = f24;
                            f16 = f25;
                        } else if (i7 == 5) {
                            f15 = f24;
                        }
                    }
                    i7++;
                    pathRotate2 = pathRotate;
                    f9 = f5;
                }
                f2 = f9;
                MotionController motionController = motionPaths.mRelativeToController;
                if (motionController != null) {
                    float[] fArr = new float[2];
                    float[] fArr2 = new float[2];
                    motionController.getCenter(d2, fArr, fArr2);
                    float f26 = fArr[0];
                    float f27 = fArr[1];
                    float f28 = fArr2[0];
                    float f29 = fArr2[1];
                    d = d2;
                    double d3 = f20;
                    z5 = z7;
                    double d4 = f23;
                    float sin = (float) (((Math.sin(d4) * d3) + f26) - (f14 / 2.0f));
                    float f30 = f15;
                    float cos = (float) ((f27 - (Math.cos(d4) * d3)) - (f17 / 2.0f));
                    double d5 = f19;
                    double d6 = f18;
                    float cos2 = (float) ((Math.cos(d4) * d3 * d6) + (Math.sin(d4) * d5) + f28);
                    float sin2 = (float) ((Math.sin(d4) * d3 * d6) + (f29 - (Math.cos(d4) * d5)));
                    if (dArr3.length >= 2) {
                        dArr3[0] = cos2;
                        dArr3[1] = sin2;
                    }
                    if (!Float.isNaN(f30)) {
                        view.setRotation((float) (Math.toDegrees(Math.atan2(sin2, cos2)) + f30));
                    }
                    f23 = cos;
                    f20 = sin;
                } else {
                    z5 = z7;
                    d = d2;
                    if (!Float.isNaN(f15)) {
                        view.setRotation(((float) Math.toDegrees(Math.atan2((f16 / 2.0f) + f18, (f22 / 2.0f) + f19))) + f15 + 0.0f);
                    }
                }
                if (view instanceof FloatLayout) {
                    ((FloatLayout) view).layout(f20, f23, f14 + f20, f23 + f17);
                } else {
                    float f31 = f20 + 0.5f;
                    int i8 = (int) f31;
                    float f32 = f23 + 0.5f;
                    int i9 = (int) f32;
                    int i10 = (int) (f31 + f14);
                    int i11 = (int) (f32 + f17);
                    int i12 = i10 - i8;
                    int i13 = i11 - i9;
                    if (((i12 == view.getMeasuredWidth() && i13 == view.getMeasuredHeight()) ? false : true) || z5) {
                        view.measure(View.MeasureSpec.makeMeasureSpec(i12, 1073741824), View.MeasureSpec.makeMeasureSpec(i13, 1073741824));
                    }
                    view.layout(i8, i9, i10, i11);
                }
                this.mForceMeasure = false;
            }
            if (this.mTransformPivotTarget != -1) {
                if (this.mTransformPivotView == null) {
                    this.mTransformPivotView = ((View) view.getParent()).findViewById(this.mTransformPivotTarget);
                }
                if (this.mTransformPivotView != null) {
                    float bottom = (this.mTransformPivotView.getBottom() + r1.getTop()) / 2.0f;
                    float right = (this.mTransformPivotView.getRight() + this.mTransformPivotView.getLeft()) / 2.0f;
                    if (view.getRight() - view.getLeft() > 0 && view.getBottom() - view.getTop() > 0) {
                        view.setPivotX(right - view.getLeft());
                        view.setPivotY(bottom - view.getTop());
                    }
                }
            }
            HashMap<String, ViewSpline> hashMap3 = this.mAttributesMap;
            if (hashMap3 != null) {
                for (ViewSpline viewSpline : hashMap3.values()) {
                    if (viewSpline instanceof ViewSpline.PathRotate) {
                        double[] dArr6 = this.mInterpolateVelocity;
                        if (dArr6.length > 1) {
                            f3 = f2;
                            view.setRotation(((ViewSpline.PathRotate) viewSpline).get(f3) + ((float) Math.toDegrees(Math.atan2(dArr6[1], dArr6[0]))));
                            f2 = f3;
                        }
                    }
                    f3 = f2;
                    f2 = f3;
                }
            }
            f9 = f2;
            if (pathRotate != null) {
                double[] dArr7 = this.mInterpolateVelocity;
                i2 = 1;
                z4 = z3 | pathRotate.setPathRotate(view, keyCache, f9, j, dArr7[0], dArr7[1]);
            } else {
                i2 = 1;
                z4 = z3;
            }
            int i14 = i2;
            while (true) {
                CurveFit[] curveFitArr2 = this.mSpline;
                if (i14 >= curveFitArr2.length) {
                    break;
                }
                curveFitArr2[i14].getPos(d, this.mValuesBuff);
                CustomSupport.setInterpolatedValue(this.mStartMotionPath.mAttributes.get(this.mAttributeNames[i14 - 1]), view, this.mValuesBuff);
                i14++;
            }
            MotionConstrainedPoint motionConstrainedPoint = this.mStartPoint;
            if (motionConstrainedPoint.mVisibilityMode == 0) {
                if (f9 <= 0.0f) {
                    view.setVisibility(motionConstrainedPoint.mVisibility);
                } else if (f9 >= 1.0f) {
                    view.setVisibility(this.mEndPoint.mVisibility);
                } else if (this.mEndPoint.mVisibility != motionConstrainedPoint.mVisibility) {
                    view.setVisibility(0);
                }
            }
            if (this.mKeyTriggers != null) {
                int i15 = 0;
                while (true) {
                    KeyTrigger[] keyTriggerArr = this.mKeyTriggers;
                    if (i15 >= keyTriggerArr.length) {
                        break;
                    }
                    keyTriggerArr[i15].conditionallyFire(view, f9);
                    i15++;
                }
            }
            int i16 = i2;
            z2 = z4;
            i = i16;
        } else {
            boolean z8 = z;
            MotionPaths motionPaths2 = this.mStartMotionPath;
            float f33 = motionPaths2.f11mX;
            MotionPaths motionPaths3 = this.mEndMotionPath;
            float m3m = RunGroup$$ExternalSyntheticOutline0.m3m(motionPaths3.f11mX, f33, f9, f33);
            float f34 = motionPaths2.f12mY;
            float m3m2 = RunGroup$$ExternalSyntheticOutline0.m3m(motionPaths3.f12mY, f34, f9, f34);
            float f35 = motionPaths2.mWidth;
            float f36 = motionPaths3.mWidth;
            float m3m3 = RunGroup$$ExternalSyntheticOutline0.m3m(f36, f35, f9, f35);
            float f37 = motionPaths2.mHeight;
            float f38 = motionPaths3.mHeight;
            float f39 = m3m + 0.5f;
            int i17 = (int) f39;
            float f40 = m3m2 + 0.5f;
            int i18 = (int) f40;
            int i19 = (int) (f39 + m3m3);
            int m3m4 = (int) (f40 + RunGroup$$ExternalSyntheticOutline0.m3m(f38, f37, f9, f37));
            int i20 = i19 - i17;
            int i21 = m3m4 - i18;
            if (f36 != f35 || f38 != f37 || this.mForceMeasure) {
                view.measure(View.MeasureSpec.makeMeasureSpec(i20, 1073741824), View.MeasureSpec.makeMeasureSpec(i21, 1073741824));
                this.mForceMeasure = false;
            }
            view.layout(i17, i18, i19, m3m4);
            i = 1;
            z2 = z8;
        }
        HashMap<String, ViewOscillator> hashMap4 = this.mCycleMap;
        if (hashMap4 != null) {
            for (ViewOscillator viewOscillator : hashMap4.values()) {
                if (viewOscillator instanceof ViewOscillator.PathRotateSet) {
                    double[] dArr8 = this.mInterpolateVelocity;
                    view.setRotation(((ViewOscillator.PathRotateSet) viewOscillator).get(f9) + ((float) Math.toDegrees(Math.atan2(dArr8[i], dArr8[0]))));
                } else {
                    viewOscillator.setProperty(view, f9);
                }
            }
        }
        return z2;
    }

    final void setBothStates(View view) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.mTime = 0.0f;
        motionPaths.mPosition = 0.0f;
        this.mNoMovement = true;
        motionPaths.setBounds(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        this.mEndMotionPath.setBounds(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        MotionConstrainedPoint motionConstrainedPoint = this.mStartPoint;
        motionConstrainedPoint.getClass();
        view.getX();
        view.getY();
        view.getWidth();
        view.getHeight();
        motionConstrainedPoint.applyParameters(view);
        MotionConstrainedPoint motionConstrainedPoint2 = this.mEndPoint;
        motionConstrainedPoint2.getClass();
        view.getX();
        view.getY();
        view.getWidth();
        view.getHeight();
        motionConstrainedPoint2.applyParameters(view);
    }

    final void setEndState(Rect rect, ConstraintSet constraintSet, int i, int i2) {
        int i3 = constraintSet.mRotate;
        if (i3 != 0) {
            rotate(rect, this.mTempRect, i3, i, i2);
            rect = this.mTempRect;
        }
        MotionPaths motionPaths = this.mEndMotionPath;
        motionPaths.mTime = 1.0f;
        motionPaths.mPosition = 1.0f;
        readView(motionPaths);
        this.mEndMotionPath.setBounds(rect.left, rect.top, rect.width(), rect.height());
        this.mEndMotionPath.applyParameters(constraintSet.getParameters(this.mId));
        this.mEndPoint.setState(rect, constraintSet, i3, this.mId);
    }

    public final void setPathMotionArc(int i) {
        this.mPathMotionArc = i;
    }

    final void setStartCurrentState(View view) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.mTime = 0.0f;
        motionPaths.mPosition = 0.0f;
        motionPaths.setBounds(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        MotionConstrainedPoint motionConstrainedPoint = this.mStartPoint;
        motionConstrainedPoint.getClass();
        view.getX();
        view.getY();
        view.getWidth();
        view.getHeight();
        motionConstrainedPoint.applyParameters(view);
    }

    final void setStartState(Rect rect, ConstraintSet constraintSet, int i, int i2) {
        Interpolator loadInterpolator;
        int i3 = constraintSet.mRotate;
        if (i3 != 0) {
            rotate(rect, this.mTempRect, i3, i, i2);
        }
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.mTime = 0.0f;
        motionPaths.mPosition = 0.0f;
        readView(motionPaths);
        this.mStartMotionPath.setBounds(rect.left, rect.top, rect.width(), rect.height());
        ConstraintSet.Constraint parameters = constraintSet.getParameters(this.mId);
        this.mStartMotionPath.applyParameters(parameters);
        ConstraintSet.Motion motion = parameters.motion;
        this.mMotionStagger = motion.mMotionStagger;
        this.mStartPoint.setState(rect, constraintSet, i3, this.mId);
        this.mTransformPivotTarget = parameters.transform.transformPivotTarget;
        this.mQuantizeMotionSteps = motion.mQuantizeMotionSteps;
        this.mQuantizeMotionPhase = motion.mQuantizeMotionPhase;
        Context context = this.mView.getContext();
        int i4 = motion.mQuantizeInterpolatorType;
        String str = motion.mQuantizeInterpolatorString;
        int i5 = motion.mQuantizeInterpolatorID;
        if (i4 == -2) {
            loadInterpolator = AnimationUtils.loadInterpolator(context, i5);
        } else if (i4 != -1) {
            loadInterpolator = i4 != 0 ? i4 != 1 ? i4 != 2 ? i4 != 4 ? i4 != 5 ? null : new OvershootInterpolator() : new BounceInterpolator() : new DecelerateInterpolator() : new AccelerateInterpolator() : new AccelerateDecelerateInterpolator();
        } else {
            final Easing interpolator = Easing.getInterpolator(str);
            loadInterpolator = new Interpolator() { // from class: androidx.constraintlayout.motion.widget.MotionController.1
                @Override // android.animation.TimeInterpolator
                public final float getInterpolation(float f) {
                    return (float) Easing.this.get(f);
                }
            };
        }
        this.mQuantizeMotionInterpolator = loadInterpolator;
    }

    public final void setup(int i, int i2, long j) {
        ArrayList arrayList;
        String[] strArr;
        Iterator<String> it;
        char c;
        String str;
        ConstraintAttribute constraintAttribute;
        ViewTimeCycle makeSpline;
        ConstraintAttribute constraintAttribute2;
        Integer num;
        ViewSpline makeSpline2;
        ConstraintAttribute constraintAttribute3;
        new HashSet();
        HashSet<String> hashSet = new HashSet<>();
        HashSet<String> hashSet2 = new HashSet<>();
        HashSet<String> hashSet3 = new HashSet<>();
        HashMap<String, Integer> hashMap = new HashMap<>();
        int i3 = this.mPathMotionArc;
        if (i3 != -1) {
            this.mStartMotionPath.mPathMotionArc = i3;
        }
        this.mStartPoint.different(this.mEndPoint, hashSet2);
        ArrayList<Key> arrayList2 = this.mKeyList;
        if (arrayList2 != null) {
            Iterator<Key> it2 = arrayList2.iterator();
            arrayList = null;
            while (it2.hasNext()) {
                Key next = it2.next();
                if (next instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) next;
                    MotionPaths motionPaths = new MotionPaths(i, i2, keyPosition, this.mStartMotionPath, this.mEndMotionPath);
                    if (Collections.binarySearch(this.mMotionPaths, motionPaths) == 0) {
                        Log.e("MotionController", " KeyPath position \"" + motionPaths.mPosition + "\" outside of range");
                    }
                    this.mMotionPaths.add((-r10) - 1, motionPaths);
                    int i4 = keyPosition.mCurveFit;
                    if (i4 != -1) {
                        this.mCurveFitType = i4;
                    }
                } else if (next instanceof KeyCycle) {
                    next.getAttributeNames(hashSet3);
                } else if (next instanceof KeyTimeCycle) {
                    next.getAttributeNames(hashSet);
                } else if (next instanceof KeyTrigger) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add((KeyTrigger) next);
                } else {
                    next.setInterpolation(hashMap);
                    next.getAttributeNames(hashSet2);
                }
            }
        } else {
            arrayList = null;
        }
        int i5 = 0;
        if (arrayList != null) {
            this.mKeyTriggers = (KeyTrigger[]) arrayList.toArray(new KeyTrigger[0]);
        }
        char c2 = 1;
        if (!hashSet2.isEmpty()) {
            this.mAttributesMap = new HashMap<>();
            Iterator<String> it3 = hashSet2.iterator();
            while (it3.hasNext()) {
                String next2 = it3.next();
                if (next2.startsWith("CUSTOM,")) {
                    SparseArray sparseArray = new SparseArray();
                    String str2 = next2.split(",")[c2];
                    Iterator<Key> it4 = this.mKeyList.iterator();
                    while (it4.hasNext()) {
                        Key next3 = it4.next();
                        HashMap<String, ConstraintAttribute> hashMap2 = next3.mCustomConstraints;
                        if (hashMap2 != null && (constraintAttribute3 = hashMap2.get(str2)) != null) {
                            sparseArray.append(next3.mFramePosition, constraintAttribute3);
                        }
                    }
                    makeSpline2 = new ViewSpline.CustomSet(next2, sparseArray);
                } else {
                    makeSpline2 = ViewSpline.makeSpline(next2);
                }
                if (makeSpline2 != null) {
                    makeSpline2.setType(next2);
                    this.mAttributesMap.put(next2, makeSpline2);
                }
                c2 = 1;
            }
            ArrayList<Key> arrayList3 = this.mKeyList;
            if (arrayList3 != null) {
                Iterator<Key> it5 = arrayList3.iterator();
                while (it5.hasNext()) {
                    Key next4 = it5.next();
                    if (next4 instanceof KeyAttributes) {
                        next4.addValues(this.mAttributesMap);
                    }
                }
            }
            this.mStartPoint.addValues(this.mAttributesMap, 0);
            this.mEndPoint.addValues(this.mAttributesMap, 100);
            for (String str3 : this.mAttributesMap.keySet()) {
                int intValue = (!hashMap.containsKey(str3) || (num = hashMap.get(str3)) == null) ? 0 : num.intValue();
                ViewSpline viewSpline = this.mAttributesMap.get(str3);
                if (viewSpline != null) {
                    viewSpline.setup(intValue);
                }
            }
        }
        if (!hashSet.isEmpty()) {
            if (this.mTimeCycleAttributesMap == null) {
                this.mTimeCycleAttributesMap = new HashMap<>();
            }
            Iterator<String> it6 = hashSet.iterator();
            while (it6.hasNext()) {
                String next5 = it6.next();
                if (!this.mTimeCycleAttributesMap.containsKey(next5)) {
                    if (next5.startsWith("CUSTOM,")) {
                        SparseArray sparseArray2 = new SparseArray();
                        String str4 = next5.split(",")[1];
                        Iterator<Key> it7 = this.mKeyList.iterator();
                        while (it7.hasNext()) {
                            Key next6 = it7.next();
                            HashMap<String, ConstraintAttribute> hashMap3 = next6.mCustomConstraints;
                            if (hashMap3 != null && (constraintAttribute2 = hashMap3.get(str4)) != null) {
                                sparseArray2.append(next6.mFramePosition, constraintAttribute2);
                            }
                        }
                        makeSpline = new ViewTimeCycle.CustomSet(next5, sparseArray2);
                    } else {
                        makeSpline = ViewTimeCycle.makeSpline(next5, j);
                    }
                    if (makeSpline != null) {
                        makeSpline.setType(next5);
                        this.mTimeCycleAttributesMap.put(next5, makeSpline);
                    }
                }
            }
            ArrayList<Key> arrayList4 = this.mKeyList;
            if (arrayList4 != null) {
                Iterator<Key> it8 = arrayList4.iterator();
                while (it8.hasNext()) {
                    Key next7 = it8.next();
                    if (next7 instanceof KeyTimeCycle) {
                        ((KeyTimeCycle) next7).addTimeValues(this.mTimeCycleAttributesMap);
                    }
                }
            }
            for (String str5 : this.mTimeCycleAttributesMap.keySet()) {
                this.mTimeCycleAttributesMap.get(str5).setup(hashMap.containsKey(str5) ? hashMap.get(str5).intValue() : 0);
            }
        }
        char c3 = 2;
        int size = this.mMotionPaths.size() + 2;
        MotionPaths[] motionPathsArr = new MotionPaths[size];
        motionPathsArr[0] = this.mStartMotionPath;
        motionPathsArr[size - 1] = this.mEndMotionPath;
        if (this.mMotionPaths.size() > 0 && this.mCurveFitType == -1) {
            this.mCurveFitType = 0;
        }
        Iterator<MotionPaths> it9 = this.mMotionPaths.iterator();
        int i6 = 1;
        while (it9.hasNext()) {
            motionPathsArr[i6] = it9.next();
            i6++;
        }
        HashSet hashSet4 = new HashSet();
        for (String str6 : this.mEndMotionPath.mAttributes.keySet()) {
            if (this.mStartMotionPath.mAttributes.containsKey(str6)) {
                if (!hashSet2.contains("CUSTOM," + str6)) {
                    hashSet4.add(str6);
                }
            }
        }
        String[] strArr2 = (String[]) hashSet4.toArray(new String[0]);
        this.mAttributeNames = strArr2;
        this.mAttributeInterpolatorCount = new int[strArr2.length];
        int i7 = 0;
        while (true) {
            strArr = this.mAttributeNames;
            if (i7 >= strArr.length) {
                break;
            }
            String str7 = strArr[i7];
            this.mAttributeInterpolatorCount[i7] = 0;
            int i8 = 0;
            while (true) {
                if (i8 >= size) {
                    break;
                }
                if (motionPathsArr[i8].mAttributes.containsKey(str7) && (constraintAttribute = motionPathsArr[i8].mAttributes.get(str7)) != null) {
                    int[] iArr = this.mAttributeInterpolatorCount;
                    iArr[i7] = constraintAttribute.numberOfInterpolatedValues() + iArr[i7];
                    break;
                }
                i8++;
            }
            i7++;
        }
        boolean z = motionPathsArr[0].mPathMotionArc != -1;
        int length = strArr.length + 18;
        boolean[] zArr = new boolean[length];
        for (int i9 = 1; i9 < size; i9++) {
            motionPathsArr[i9].different(motionPathsArr[i9 - 1], zArr, z);
        }
        int i10 = 0;
        for (int i11 = 1; i11 < length; i11++) {
            if (zArr[i11]) {
                i10++;
            }
        }
        this.mInterpolateVariables = new int[i10];
        int max = Math.max(2, i10);
        this.mInterpolateData = new double[max];
        this.mInterpolateVelocity = new double[max];
        int i12 = 0;
        for (int i13 = 1; i13 < length; i13++) {
            if (zArr[i13]) {
                this.mInterpolateVariables[i12] = i13;
                i12++;
            }
        }
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, this.mInterpolateVariables.length);
        double[] dArr2 = new double[size];
        int i14 = 0;
        while (true) {
            int i15 = 6;
            if (i5 >= size) {
                break;
            }
            MotionPaths motionPaths2 = motionPathsArr[i5];
            double[] dArr3 = dArr[i5];
            int[] iArr2 = this.mInterpolateVariables;
            float[] fArr = new float[6];
            fArr[i14] = motionPaths2.mPosition;
            fArr[1] = motionPaths2.f11mX;
            fArr[c3] = motionPaths2.f12mY;
            fArr[3] = motionPaths2.mWidth;
            fArr[4] = motionPaths2.mHeight;
            fArr[5] = motionPaths2.mPathRotate;
            int i16 = i14;
            while (i14 < iArr2.length) {
                if (iArr2[i14] < i15) {
                    dArr3[i16] = fArr[r10];
                    i16++;
                }
                i14++;
                i15 = 6;
            }
            dArr2[i5] = motionPathsArr[i5].mTime;
            i5++;
            c3 = 2;
            i14 = 0;
        }
        int i17 = 0;
        while (true) {
            int[] iArr3 = this.mInterpolateVariables;
            if (i17 >= iArr3.length) {
                break;
            }
            if (iArr3[i17] < 6) {
                String str8 = MotionPaths.sNames[this.mInterpolateVariables[i17]] + " [";
                for (int i18 = 0; i18 < size; i18++) {
                    StringBuilder m2m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(str8);
                    m2m.append(dArr[i18][i17]);
                    str8 = m2m.toString();
                }
            }
            i17++;
        }
        this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
        int i19 = 0;
        while (true) {
            String[] strArr3 = this.mAttributeNames;
            if (i19 >= strArr3.length) {
                break;
            }
            String str9 = strArr3[i19];
            int i20 = 0;
            int i21 = 0;
            double[] dArr4 = null;
            double[][] dArr5 = null;
            while (i20 < size) {
                if (motionPathsArr[i20].mAttributes.containsKey(str9)) {
                    if (dArr5 == null) {
                        dArr4 = new double[size];
                        ConstraintAttribute constraintAttribute4 = motionPathsArr[i20].mAttributes.get(str9);
                        dArr5 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, constraintAttribute4 == null ? 0 : constraintAttribute4.numberOfInterpolatedValues());
                    }
                    MotionPaths motionPaths3 = motionPathsArr[i20];
                    dArr4[i21] = motionPaths3.mTime;
                    double[] dArr6 = dArr5[i21];
                    ConstraintAttribute constraintAttribute5 = motionPaths3.mAttributes.get(str9);
                    if (constraintAttribute5 != null) {
                        if (constraintAttribute5.numberOfInterpolatedValues() == 1) {
                            dArr6[0] = constraintAttribute5.getValueToInterpolate();
                        } else {
                            int numberOfInterpolatedValues = constraintAttribute5.numberOfInterpolatedValues();
                            constraintAttribute5.getValuesToInterpolate(new float[numberOfInterpolatedValues]);
                            int i22 = 0;
                            int i23 = 0;
                            while (i22 < numberOfInterpolatedValues) {
                                dArr6[i23] = r15[i22];
                                i22++;
                                str9 = str9;
                                dArr4 = dArr4;
                                dArr5 = dArr5;
                                i23++;
                            }
                        }
                    }
                    str = str9;
                    i21++;
                    dArr4 = dArr4;
                    dArr5 = dArr5;
                } else {
                    str = str9;
                }
                i20++;
                str9 = str;
            }
            i19++;
            this.mSpline[i19] = CurveFit.get(this.mCurveFitType, Arrays.copyOf(dArr4, i21), (double[][]) Arrays.copyOf(dArr5, i21));
        }
        this.mSpline[0] = CurveFit.get(this.mCurveFitType, dArr2, dArr);
        if (motionPathsArr[0].mPathMotionArc != -1) {
            int[] iArr4 = new int[size];
            double[] dArr7 = new double[size];
            double[][] dArr8 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, 2);
            for (int i24 = 0; i24 < size; i24++) {
                iArr4[i24] = motionPathsArr[i24].mPathMotionArc;
                dArr7[i24] = r8.mTime;
                double[] dArr9 = dArr8[i24];
                dArr9[0] = r8.f11mX;
                dArr9[1] = r8.f12mY;
            }
            this.mArcSpline = new ArcCurveFit(iArr4, dArr7, dArr8);
        }
        this.mCycleMap = new HashMap<>();
        if (this.mKeyList != null) {
            Iterator<String> it10 = hashSet3.iterator();
            float f = Float.NaN;
            while (it10.hasNext()) {
                String next8 = it10.next();
                ViewOscillator makeSpline3 = ViewOscillator.makeSpline(next8);
                if (makeSpline3 != null) {
                    if ((makeSpline3.mVariesBy == 1) && Float.isNaN(f)) {
                        float[] fArr2 = new float[2];
                        float f2 = 1.0f / 99;
                        double d = 0.0d;
                        double d2 = 0.0d;
                        int i25 = 0;
                        float f3 = 0.0f;
                        for (int i26 = 100; i25 < i26; i26 = 100) {
                            float f4 = i25 * f2;
                            double d3 = f4;
                            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
                            Iterator<MotionPaths> it11 = this.mMotionPaths.iterator();
                            float f5 = Float.NaN;
                            float f6 = 0.0f;
                            while (it11.hasNext()) {
                                MotionPaths next9 = it11.next();
                                Easing easing2 = next9.mKeyFrameEasing;
                                Iterator<String> it12 = it10;
                                if (easing2 != null) {
                                    float f7 = next9.mTime;
                                    if (f7 < f4) {
                                        f6 = f7;
                                        easing = easing2;
                                    } else if (Float.isNaN(f5)) {
                                        f5 = next9.mTime;
                                    }
                                }
                                it10 = it12;
                            }
                            Iterator<String> it13 = it10;
                            if (easing != null) {
                                if (Float.isNaN(f5)) {
                                    f5 = 1.0f;
                                }
                                d3 = (((float) easing.get((f4 - f6) / r10)) * (f5 - f6)) + f6;
                            }
                            this.mSpline[0].getPos(d3, this.mInterpolateData);
                            int i27 = i25;
                            this.mStartMotionPath.getCenter(d3, this.mInterpolateVariables, this.mInterpolateData, fArr2, 0);
                            if (i27 > 0) {
                                c = 0;
                                f3 += (float) Math.hypot(d2 - fArr2[1], d - fArr2[0]);
                            } else {
                                c = 0;
                            }
                            double d4 = fArr2[c];
                            i25 = i27 + 1;
                            it10 = it13;
                            d = d4;
                            d2 = fArr2[1];
                        }
                        it = it10;
                        f = f3;
                    } else {
                        it = it10;
                    }
                    makeSpline3.setType(next8);
                    this.mCycleMap.put(next8, makeSpline3);
                    it10 = it;
                }
            }
            Iterator<Key> it14 = this.mKeyList.iterator();
            while (it14.hasNext()) {
                Key next10 = it14.next();
                if (next10 instanceof KeyCycle) {
                    ((KeyCycle) next10).addCycleValues(this.mCycleMap);
                }
            }
            Iterator<ViewOscillator> it15 = this.mCycleMap.values().iterator();
            while (it15.hasNext()) {
                it15.next().setup();
            }
        }
    }

    public final void setupRelative(MotionController motionController) {
        this.mStartMotionPath.setupRelative(motionController, motionController.mStartMotionPath);
        this.mEndMotionPath.setupRelative(motionController, motionController.mEndMotionPath);
    }

    public final String toString() {
        return " start: x: " + this.mStartMotionPath.f11mX + " y: " + this.mStartMotionPath.f12mY + " end: x: " + this.mEndMotionPath.f11mX + " y: " + this.mEndMotionPath.f12mY;
    }
}
