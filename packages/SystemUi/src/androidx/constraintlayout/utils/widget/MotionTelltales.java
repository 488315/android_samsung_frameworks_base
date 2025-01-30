package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewParent;
import androidx.constraintlayout.core.motion.utils.ArcCurveFit;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.VelocityMatrix;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.widget.MotionController;
import androidx.constraintlayout.motion.widget.MotionInterpolator;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionPaths;
import androidx.constraintlayout.widget.R$styleable;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MotionTelltales extends MockView {
    public final Matrix mInvertMatrix;
    public MotionLayout mMotionLayout;
    public final Paint mPaintTelltales;
    public int mTailColor;
    public float mTailScale;
    public int mVelocityMode;
    public final float[] velocity;

    public MotionTelltales(Context context) {
        super(context);
        this.mPaintTelltales = new Paint();
        this.velocity = new float[2];
        this.mInvertMatrix = new Matrix();
        this.mVelocityMode = 0;
        this.mTailColor = -65281;
        this.mTailScale = 0.25f;
        init(context, null);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MotionTelltales);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 0) {
                    this.mTailColor = obtainStyledAttributes.getColor(index, this.mTailColor);
                } else if (index == 2) {
                    this.mVelocityMode = obtainStyledAttributes.getInt(index, this.mVelocityMode);
                } else if (index == 1) {
                    this.mTailScale = obtainStyledAttributes.getFloat(index, this.mTailScale);
                }
            }
            obtainStyledAttributes.recycle();
        }
        this.mPaintTelltales.setColor(this.mTailColor);
        this.mPaintTelltales.setStrokeWidth(5.0f);
    }

    @Override // android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // androidx.constraintlayout.utils.widget.MockView, android.view.View
    public final void onDraw(Canvas canvas) {
        int i;
        int i2;
        float f;
        float[] fArr;
        int i3;
        int i4;
        float[] fArr2;
        float f2;
        float f3;
        int i5;
        SplineSet splineSet;
        int i6;
        SplineSet splineSet2;
        SplineSet splineSet3;
        SplineSet splineSet4;
        SplineSet splineSet5;
        int i7;
        float f4;
        float f5;
        double[] dArr;
        float[] fArr3;
        float f6;
        VelocityMatrix velocityMatrix;
        float[] fArr4;
        int i8;
        super.onDraw(canvas);
        getMatrix().invert(this.mInvertMatrix);
        if (this.mMotionLayout == null) {
            ViewParent parent = getParent();
            if (parent instanceof MotionLayout) {
                this.mMotionLayout = (MotionLayout) parent;
                return;
            }
            return;
        }
        int width = getWidth();
        int height = getHeight();
        int i9 = 5;
        float[] fArr5 = {0.1f, 0.25f, 0.5f, 0.75f, 0.9f};
        MotionTelltales motionTelltales = this;
        int i10 = 0;
        while (i10 < i9) {
            float f7 = fArr5[i10];
            int i11 = 0;
            while (i11 < i9) {
                float f8 = fArr5[i11];
                MotionLayout motionLayout = motionTelltales.mMotionLayout;
                float[] fArr6 = motionTelltales.velocity;
                int i12 = motionTelltales.mVelocityMode;
                float f9 = motionLayout.mLastVelocity;
                float f10 = motionLayout.mTransitionLastPosition;
                if (motionLayout.mInterpolator != null) {
                    float signum = Math.signum(motionLayout.mTransitionGoalPosition - f10);
                    float interpolation = motionLayout.mInterpolator.getInterpolation(motionLayout.mTransitionLastPosition + 1.0E-5f);
                    f10 = motionLayout.mInterpolator.getInterpolation(motionLayout.mTransitionLastPosition);
                    f9 = (((interpolation - f10) / 1.0E-5f) * signum) / motionLayout.mTransitionDuration;
                }
                MotionInterpolator motionInterpolator = motionLayout.mInterpolator;
                if (motionInterpolator instanceof MotionInterpolator) {
                    f9 = motionInterpolator.getVelocity();
                }
                float f11 = f9;
                MotionController motionController = (MotionController) motionLayout.mFrameArrayList.get(motionTelltales);
                if ((i12 & 1) == 0) {
                    int width2 = getWidth();
                    int height2 = getHeight();
                    float[] fArr7 = motionController.mVelocity;
                    float adjustedPosition = motionController.getAdjustedPosition(f10, fArr7);
                    HashMap hashMap = motionController.mAttributesMap;
                    fArr = fArr5;
                    if (hashMap == null) {
                        i6 = i12;
                        splineSet = null;
                    } else {
                        splineSet = (SplineSet) hashMap.get("translationX");
                        i6 = i12;
                    }
                    HashMap hashMap2 = motionController.mAttributesMap;
                    i3 = i10;
                    if (hashMap2 == null) {
                        i5 = i11;
                        splineSet2 = null;
                    } else {
                        splineSet2 = (SplineSet) hashMap2.get("translationY");
                        i5 = i11;
                    }
                    HashMap hashMap3 = motionController.mAttributesMap;
                    i2 = height;
                    if (hashMap3 == null) {
                        i = width;
                        splineSet3 = null;
                    } else {
                        splineSet3 = (SplineSet) hashMap3.get("rotation");
                        i = width;
                    }
                    HashMap hashMap4 = motionController.mAttributesMap;
                    if (hashMap4 == null) {
                        f = f11;
                        splineSet4 = null;
                    } else {
                        splineSet4 = (SplineSet) hashMap4.get("scaleX");
                        f = f11;
                    }
                    HashMap hashMap5 = motionController.mAttributesMap;
                    if (hashMap5 == null) {
                        i7 = width2;
                        splineSet5 = null;
                    } else {
                        splineSet5 = (SplineSet) hashMap5.get("scaleY");
                        i7 = width2;
                    }
                    HashMap hashMap6 = motionController.mCycleMap;
                    ViewOscillator viewOscillator = hashMap6 == null ? null : (ViewOscillator) hashMap6.get("translationX");
                    HashMap hashMap7 = motionController.mCycleMap;
                    ViewOscillator viewOscillator2 = hashMap7 == null ? null : (ViewOscillator) hashMap7.get("translationY");
                    HashMap hashMap8 = motionController.mCycleMap;
                    ViewOscillator viewOscillator3 = hashMap8 == null ? null : (ViewOscillator) hashMap8.get("rotation");
                    HashMap hashMap9 = motionController.mCycleMap;
                    ViewOscillator viewOscillator4 = hashMap9 == null ? null : (ViewOscillator) hashMap9.get("scaleX");
                    HashMap hashMap10 = motionController.mCycleMap;
                    ViewOscillator viewOscillator5 = hashMap10 != null ? (ViewOscillator) hashMap10.get("scaleY") : null;
                    VelocityMatrix velocityMatrix2 = new VelocityMatrix();
                    velocityMatrix2.mDRotate = 0.0f;
                    velocityMatrix2.mDTranslateY = 0.0f;
                    velocityMatrix2.mDTranslateX = 0.0f;
                    velocityMatrix2.mDScaleY = 0.0f;
                    velocityMatrix2.mDScaleX = 0.0f;
                    if (splineSet3 != null) {
                        f4 = f8;
                        f5 = f7;
                        velocityMatrix2.mDRotate = (float) splineSet3.mCurveFit.getSlope(adjustedPosition);
                        velocityMatrix2.mRotate = splineSet3.get(adjustedPosition);
                    } else {
                        f4 = f8;
                        f5 = f7;
                    }
                    if (splineSet != null) {
                        velocityMatrix2.mDTranslateX = (float) splineSet.mCurveFit.getSlope(adjustedPosition);
                    }
                    if (splineSet2 != null) {
                        velocityMatrix2.mDTranslateY = (float) splineSet2.mCurveFit.getSlope(adjustedPosition);
                    }
                    if (splineSet4 != null) {
                        velocityMatrix2.mDScaleX = (float) splineSet4.mCurveFit.getSlope(adjustedPosition);
                    }
                    if (splineSet5 != null) {
                        velocityMatrix2.mDScaleY = (float) splineSet5.mCurveFit.getSlope(adjustedPosition);
                    }
                    if (viewOscillator3 != null) {
                        velocityMatrix2.mDRotate = viewOscillator3.getSlope(adjustedPosition);
                    }
                    if (viewOscillator != null) {
                        velocityMatrix2.mDTranslateX = viewOscillator.getSlope(adjustedPosition);
                    }
                    if (viewOscillator2 != null) {
                        velocityMatrix2.mDTranslateY = viewOscillator2.getSlope(adjustedPosition);
                    }
                    if (viewOscillator4 != null) {
                        velocityMatrix2.mDScaleX = viewOscillator4.getSlope(adjustedPosition);
                    }
                    if (viewOscillator5 != null) {
                        velocityMatrix2.mDScaleY = viewOscillator5.getSlope(adjustedPosition);
                    }
                    ArcCurveFit arcCurveFit = motionController.mArcSpline;
                    MotionPaths motionPaths = motionController.mStartMotionPath;
                    if (arcCurveFit != null) {
                        double[] dArr2 = motionController.mInterpolateData;
                        if (dArr2.length > 0) {
                            double d = adjustedPosition;
                            arcCurveFit.getPos(d, dArr2);
                            motionController.mArcSpline.getSlope(d, motionController.mInterpolateVelocity);
                            int[] iArr = motionController.mInterpolateVariables;
                            double[] dArr3 = motionController.mInterpolateVelocity;
                            double[] dArr4 = motionController.mInterpolateData;
                            motionPaths.getClass();
                            velocityMatrix = velocityMatrix2;
                            i8 = i6;
                            fArr4 = fArr6;
                            f6 = f4;
                            MotionPaths.setDpDt(f4, f5, fArr6, iArr, dArr3, dArr4);
                        } else {
                            velocityMatrix = velocityMatrix2;
                            fArr4 = fArr6;
                            i8 = i6;
                            f6 = f4;
                        }
                        velocityMatrix.applyTransform(f6, f5, i7, height2, fArr4);
                        i4 = i8;
                        fArr3 = fArr4;
                    } else {
                        i4 = i6;
                        float f12 = f4;
                        if (motionController.mSpline != null) {
                            double adjustedPosition2 = motionController.getAdjustedPosition(adjustedPosition, fArr7);
                            motionController.mSpline[0].getSlope(adjustedPosition2, motionController.mInterpolateVelocity);
                            motionController.mSpline[0].getPos(adjustedPosition2, motionController.mInterpolateData);
                            float f13 = fArr7[0];
                            int i13 = 0;
                            while (true) {
                                dArr = motionController.mInterpolateVelocity;
                                if (i13 >= dArr.length) {
                                    break;
                                }
                                dArr[i13] = dArr[i13] * f13;
                                i13++;
                            }
                            int[] iArr2 = motionController.mInterpolateVariables;
                            double[] dArr5 = motionController.mInterpolateData;
                            motionPaths.getClass();
                            fArr3 = fArr6;
                            f6 = f12;
                            MotionPaths.setDpDt(f12, f5, fArr3, iArr2, dArr, dArr5);
                            velocityMatrix2.applyTransform(f6, f5, i7, height2, fArr3);
                        } else {
                            MotionPaths motionPaths2 = motionController.mEndMotionPath;
                            ViewOscillator viewOscillator6 = viewOscillator4;
                            float f14 = motionPaths2.f27x - motionPaths.f27x;
                            ViewOscillator viewOscillator7 = viewOscillator2;
                            float f15 = motionPaths2.f28y - motionPaths.f28y;
                            ViewOscillator viewOscillator8 = viewOscillator;
                            float f16 = motionPaths2.width - motionPaths.width;
                            float f17 = (motionPaths2.height - motionPaths.height) + f15;
                            fArr6[0] = ((f16 + f14) * f12) + ((1.0f - f12) * f14);
                            fArr6[1] = (f17 * f5) + ((1.0f - f5) * f15);
                            velocityMatrix2.mDRotate = 0.0f;
                            velocityMatrix2.mDTranslateY = 0.0f;
                            velocityMatrix2.mDTranslateX = 0.0f;
                            velocityMatrix2.mDScaleY = 0.0f;
                            velocityMatrix2.mDScaleX = 0.0f;
                            if (splineSet3 != null) {
                                velocityMatrix2.mDRotate = (float) splineSet3.mCurveFit.getSlope(adjustedPosition);
                                velocityMatrix2.mRotate = splineSet3.get(adjustedPosition);
                            }
                            if (splineSet != null) {
                                velocityMatrix2.mDTranslateX = (float) splineSet.mCurveFit.getSlope(adjustedPosition);
                            }
                            if (splineSet2 != null) {
                                velocityMatrix2.mDTranslateY = (float) splineSet2.mCurveFit.getSlope(adjustedPosition);
                            }
                            if (splineSet4 != null) {
                                velocityMatrix2.mDScaleX = (float) splineSet4.mCurveFit.getSlope(adjustedPosition);
                            }
                            if (splineSet5 != null) {
                                velocityMatrix2.mDScaleY = (float) splineSet5.mCurveFit.getSlope(adjustedPosition);
                            }
                            if (viewOscillator3 != null) {
                                velocityMatrix2.mDRotate = viewOscillator3.getSlope(adjustedPosition);
                            }
                            if (viewOscillator8 != null) {
                                velocityMatrix2.mDTranslateX = viewOscillator8.getSlope(adjustedPosition);
                            }
                            if (viewOscillator7 != null) {
                                velocityMatrix2.mDTranslateY = viewOscillator7.getSlope(adjustedPosition);
                            }
                            if (viewOscillator6 != null) {
                                velocityMatrix2.mDScaleX = viewOscillator6.getSlope(adjustedPosition);
                            }
                            if (viewOscillator5 != null) {
                                velocityMatrix2.mDScaleY = viewOscillator5.getSlope(adjustedPosition);
                            }
                            fArr2 = fArr6;
                            f2 = f12;
                            velocityMatrix2.applyTransform(f12, f5, i7, height2, fArr2);
                            f3 = f5;
                        }
                    }
                    fArr2 = fArr3;
                    f2 = f6;
                    f3 = f5;
                } else {
                    i = width;
                    i2 = height;
                    f = f11;
                    fArr = fArr5;
                    i3 = i10;
                    i4 = i12;
                    fArr2 = fArr6;
                    f2 = f8;
                    f3 = f7;
                    i5 = i11;
                    motionController.getDpDt(f10, f2, f3, fArr2);
                }
                if (i4 < 2) {
                    fArr2[0] = fArr2[0] * f;
                    fArr2[1] = fArr2[1] * f;
                }
                this.mInvertMatrix.mapVectors(this.velocity);
                int i14 = i;
                float f18 = i14 * f2;
                int i15 = i2;
                float f19 = i15 * f3;
                float[] fArr8 = this.velocity;
                float f20 = fArr8[0];
                float f21 = this.mTailScale;
                float f22 = f19 - (fArr8[1] * f21);
                this.mInvertMatrix.mapVectors(fArr8);
                canvas.drawLine(f18, f19, f18 - (f20 * f21), f22, this.mPaintTelltales);
                i11 = i5 + 1;
                motionTelltales = this;
                f7 = f3;
                fArr5 = fArr;
                i10 = i3;
                height = i15;
                width = i14;
                i9 = 5;
            }
            i10++;
            width = width;
            i9 = 5;
        }
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        postInvalidate();
    }

    public MotionTelltales(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaintTelltales = new Paint();
        this.velocity = new float[2];
        this.mInvertMatrix = new Matrix();
        this.mVelocityMode = 0;
        this.mTailColor = -65281;
        this.mTailScale = 0.25f;
        init(context, attributeSet);
    }

    public MotionTelltales(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaintTelltales = new Paint();
        this.velocity = new float[2];
        this.mInvertMatrix = new Matrix();
        this.mVelocityMode = 0;
        this.mTailColor = -65281;
        this.mTailScale = 0.25f;
        init(context, attributeSet);
    }
}
