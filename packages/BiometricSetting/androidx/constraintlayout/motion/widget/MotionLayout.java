package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Flow;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.core.widgets.Placeholder;
import androidx.constraintlayout.core.widgets.VirtualLayout;
import androidx.constraintlayout.core.widgets.WidgetContainer;
import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.constraintlayout.motion.utils.StopLogic;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.motion.widget.ViewTransition;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayoutStates;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.R$styleable;
import androidx.constraintlayout.widget.StateSet;
import androidx.core.view.NestedScrollingParent3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class MotionLayout extends ConstraintLayout implements NestedScrollingParent3 {
    public static boolean IS_IN_EDIT_MODE;
    private long mAnimationStartTime;
    private int mBeginState;
    private RectF mBoundsCheck;
    int mCurrentState;
    int mDebugPath;
    private DecelerateInterpolator mDecelerateLogic;
    private ArrayList<MotionHelper> mDecoratorsHelpers;
    private boolean mDelayedApply;
    private DesignTool mDesignTool;
    DevModeDraw mDevModeDraw;
    private int mEndState;
    int mEndWrapHeight;
    int mEndWrapWidth;
    HashMap<View, MotionController> mFrameArrayList;
    private int mFrames;
    int mHeightMeasureMode;
    private boolean mInLayout;
    boolean mInTransition;
    private boolean mInteractionEnabled;
    MotionInterpolator mInterpolator;
    private Matrix mInverseMatrix;
    private boolean mKeepAnimating;
    private KeyCache mKeyCache;
    private long mLastDrawTime;
    private float mLastFps;
    private int mLastHeightMeasureSpec;
    int mLastLayoutHeight;
    int mLastLayoutWidth;
    float mLastVelocity;
    private int mLastWidthMeasureSpec;
    private float mListenerPosition;
    private int mListenerState;
    protected boolean mMeasureDuringTransition;
    Model mModel;
    private boolean mNeedsFireTransitionCompleted;
    private Runnable mOnComplete;
    private ArrayList<MotionHelper> mOnHideHelpers;
    private ArrayList<MotionHelper> mOnShowHelpers;
    float mPostInterpolationPosition;
    HashMap<View, Object> mPreRotate;
    Interpolator mProgressInterpolator;
    private View mRegionView;
    MotionScene mScene;
    float mScrollTargetDT;
    float mScrollTargetDX;
    float mScrollTargetDY;
    long mScrollTargetTime;
    int mStartWrapHeight;
    int mStartWrapWidth;
    private StateCache mStateCache;
    private StopLogic mStopLogic;
    Rect mTempRect;
    private boolean mTemporalInterpolator;
    ArrayList<Integer> mTransitionCompleted;
    private float mTransitionDuration;
    float mTransitionGoalPosition;
    private boolean mTransitionInstantly;
    float mTransitionLastPosition;
    private long mTransitionLastTime;
    private TransitionListener mTransitionListener;
    private CopyOnWriteArrayList<TransitionListener> mTransitionListeners;
    float mTransitionPosition;
    TransitionState mTransitionState;
    boolean mUndergoingMotion;
    int mWidthMeasureMode;

    class DecelerateInterpolator extends MotionInterpolator {
        float mMaxA;
        float mInitialV = 0.0f;
        float mCurrentP = 0.0f;

        DecelerateInterpolator() {
        }

        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            float f2 = this.mInitialV;
            if (f2 > 0.0f) {
                float f3 = this.mMaxA;
                if (f2 / f3 < f) {
                    f = f2 / f3;
                }
                MotionLayout.this.mLastVelocity = f2 - (f3 * f);
                return ((f2 * f) - (((f3 * f) * f) / 2.0f)) + this.mCurrentP;
            }
            float f4 = this.mMaxA;
            if ((-f2) / f4 < f) {
                f = (-f2) / f4;
            }
            MotionLayout.this.mLastVelocity = (f4 * f) + f2;
            return (((f4 * f) * f) / 2.0f) + (f2 * f) + this.mCurrentP;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionInterpolator
        public final float getVelocity() {
            return MotionLayout.this.mLastVelocity;
        }
    }

    private class DevModeDraw {
        Paint mFillPaint;
        int mKeyFrameCount;
        float[] mKeyFramePoints;
        Paint mPaint;
        Paint mPaintGraph;
        Paint mPaintKeyframes;
        Path mPath;
        int[] mPathMode;
        float[] mPoints;
        private float[] mRectangle;
        Paint mTextPaint;
        Rect mBounds = new Rect();
        int mShadowTranslate = 1;

        DevModeDraw() {
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setAntiAlias(true);
            this.mPaint.setColor(-21965);
            this.mPaint.setStrokeWidth(2.0f);
            this.mPaint.setStyle(Paint.Style.STROKE);
            Paint paint2 = new Paint();
            this.mPaintKeyframes = paint2;
            paint2.setAntiAlias(true);
            this.mPaintKeyframes.setColor(-2067046);
            this.mPaintKeyframes.setStrokeWidth(2.0f);
            this.mPaintKeyframes.setStyle(Paint.Style.STROKE);
            Paint paint3 = new Paint();
            this.mPaintGraph = paint3;
            paint3.setAntiAlias(true);
            this.mPaintGraph.setColor(-13391360);
            this.mPaintGraph.setStrokeWidth(2.0f);
            this.mPaintGraph.setStyle(Paint.Style.STROKE);
            Paint paint4 = new Paint();
            this.mTextPaint = paint4;
            paint4.setAntiAlias(true);
            this.mTextPaint.setColor(-13391360);
            this.mTextPaint.setTextSize(MotionLayout.this.getContext().getResources().getDisplayMetrics().density * 12.0f);
            this.mRectangle = new float[8];
            Paint paint5 = new Paint();
            this.mFillPaint = paint5;
            paint5.setAntiAlias(true);
            this.mPaintGraph.setPathEffect(new DashPathEffect(new float[]{4.0f, 8.0f}, 0.0f));
            this.mKeyFramePoints = new float[100];
            this.mPathMode = new int[50];
        }

        private void drawPathCartesian(Canvas canvas) {
            float[] fArr = this.mPoints;
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[fArr.length - 2];
            float f4 = fArr[fArr.length - 1];
            canvas.drawLine(Math.min(f, f3), Math.max(f2, f4), Math.max(f, f3), Math.max(f2, f4), this.mPaintGraph);
            canvas.drawLine(Math.min(f, f3), Math.min(f2, f4), Math.min(f, f3), Math.max(f2, f4), this.mPaintGraph);
        }

        private void drawPathCartesianTicks(Canvas canvas, float f, float f2) {
            float[] fArr = this.mPoints;
            float f3 = fArr[0];
            float f4 = fArr[1];
            float f5 = fArr[fArr.length - 2];
            float f6 = fArr[fArr.length - 1];
            float min = Math.min(f3, f5);
            float max = Math.max(f4, f6);
            float min2 = f - Math.min(f3, f5);
            float max2 = Math.max(f4, f6) - f2;
            String str = "" + (((int) (((min2 * 100.0f) / Math.abs(f5 - f3)) + 0.5d)) / 100.0f);
            getTextBounds(this.mTextPaint, str);
            canvas.drawText(str, ((min2 / 2.0f) - (this.mBounds.width() / 2)) + min, f2 - 20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, Math.min(f3, f5), f2, this.mPaintGraph);
            String str2 = "" + (((int) (((max2 * 100.0f) / Math.abs(f6 - f4)) + 0.5d)) / 100.0f);
            getTextBounds(this.mTextPaint, str2);
            canvas.drawText(str2, f + 5.0f, max - ((max2 / 2.0f) - (this.mBounds.height() / 2)), this.mTextPaint);
            canvas.drawLine(f, f2, f, Math.max(f4, f6), this.mPaintGraph);
        }

        private void drawPathRelativeTicks(Canvas canvas, float f, float f2) {
            float[] fArr = this.mPoints;
            float f3 = fArr[0];
            float f4 = fArr[1];
            float f5 = fArr[fArr.length - 2];
            float f6 = fArr[fArr.length - 1];
            float hypot = (float) Math.hypot(f3 - f5, f4 - f6);
            float f7 = f5 - f3;
            float f8 = f6 - f4;
            float f9 = (((f2 - f4) * f8) + ((f - f3) * f7)) / (hypot * hypot);
            float f10 = f3 + (f7 * f9);
            float f11 = f4 + (f9 * f8);
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f10, f11);
            float hypot2 = (float) Math.hypot(f10 - f, f11 - f2);
            String str = "" + (((int) ((hypot2 * 100.0f) / hypot)) / 100.0f);
            getTextBounds(this.mTextPaint, str);
            canvas.drawTextOnPath(str, path, (hypot2 / 2.0f) - (this.mBounds.width() / 2), -20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, f10, f11, this.mPaintGraph);
        }

        private void drawPathScreenTicks(Canvas canvas, float f, float f2, int i, int i2) {
            StringBuilder sb = new StringBuilder("");
            MotionLayout motionLayout = MotionLayout.this;
            sb.append(((int) ((((f - (i / 2)) * 100.0f) / (motionLayout.getWidth() - i)) + 0.5d)) / 100.0f);
            String sb2 = sb.toString();
            getTextBounds(this.mTextPaint, sb2);
            canvas.drawText(sb2, ((f / 2.0f) - (this.mBounds.width() / 2)) + 0.0f, f2 - 20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, Math.min(0.0f, 1.0f), f2, this.mPaintGraph);
            String str = "" + (((int) ((((f2 - (i2 / 2)) * 100.0f) / (motionLayout.getHeight() - i2)) + 0.5d)) / 100.0f);
            getTextBounds(this.mTextPaint, str);
            canvas.drawText(str, f + 5.0f, 0.0f - ((f2 / 2.0f) - (this.mBounds.height() / 2)), this.mTextPaint);
            canvas.drawLine(f, f2, f, Math.max(0.0f, 1.0f), this.mPaintGraph);
        }

        public final void draw(Canvas canvas, HashMap<View, MotionController> hashMap, int i, int i2) {
            if (hashMap == null || hashMap.size() == 0) {
                return;
            }
            canvas.save();
            MotionLayout motionLayout = MotionLayout.this;
            if (!motionLayout.isInEditMode() && (i2 & 1) == 2) {
                String str = motionLayout.getContext().getResources().getResourceName(motionLayout.mEndState) + ":" + motionLayout.getProgress();
                canvas.drawText(str, 10.0f, motionLayout.getHeight() - 30, this.mTextPaint);
                canvas.drawText(str, 11.0f, motionLayout.getHeight() - 29, this.mPaint);
            }
            for (MotionController motionController : hashMap.values()) {
                int drawPath = motionController.getDrawPath();
                if (i2 > 0 && drawPath == 0) {
                    drawPath = 1;
                }
                if (drawPath != 0) {
                    this.mKeyFrameCount = motionController.buildKeyFrames(this.mKeyFramePoints, this.mPathMode);
                    if (drawPath >= 1) {
                        int i3 = i / 16;
                        float[] fArr = this.mPoints;
                        if (fArr == null || fArr.length != i3 * 2) {
                            this.mPoints = new float[i3 * 2];
                            this.mPath = new Path();
                        }
                        float f = this.mShadowTranslate;
                        canvas.translate(f, f);
                        this.mPaint.setColor(1996488704);
                        this.mFillPaint.setColor(1996488704);
                        this.mPaintKeyframes.setColor(1996488704);
                        this.mPaintGraph.setColor(1996488704);
                        motionController.buildPath(i3, this.mPoints);
                        drawAll(canvas, drawPath, this.mKeyFrameCount, motionController);
                        this.mPaint.setColor(-21965);
                        this.mPaintKeyframes.setColor(-2067046);
                        this.mFillPaint.setColor(-2067046);
                        this.mPaintGraph.setColor(-13391360);
                        float f2 = -this.mShadowTranslate;
                        canvas.translate(f2, f2);
                        drawAll(canvas, drawPath, this.mKeyFrameCount, motionController);
                        if (drawPath == 5) {
                            this.mPath.reset();
                            for (int i4 = 0; i4 <= 50; i4++) {
                                motionController.buildRect(i4 / 50, this.mRectangle);
                                Path path = this.mPath;
                                float[] fArr2 = this.mRectangle;
                                path.moveTo(fArr2[0], fArr2[1]);
                                Path path2 = this.mPath;
                                float[] fArr3 = this.mRectangle;
                                path2.lineTo(fArr3[2], fArr3[3]);
                                Path path3 = this.mPath;
                                float[] fArr4 = this.mRectangle;
                                path3.lineTo(fArr4[4], fArr4[5]);
                                Path path4 = this.mPath;
                                float[] fArr5 = this.mRectangle;
                                path4.lineTo(fArr5[6], fArr5[7]);
                                this.mPath.close();
                            }
                            this.mPaint.setColor(1140850688);
                            canvas.translate(2.0f, 2.0f);
                            canvas.drawPath(this.mPath, this.mPaint);
                            canvas.translate(-2.0f, -2.0f);
                            this.mPaint.setColor(-65536);
                            canvas.drawPath(this.mPath, this.mPaint);
                        }
                    }
                }
            }
            canvas.restore();
        }

        public final void drawAll(Canvas canvas, int i, int i2, MotionController motionController) {
            int i3;
            int i4;
            float f;
            float f2;
            int i5;
            if (i == 4) {
                boolean z = false;
                boolean z2 = false;
                for (int i6 = 0; i6 < this.mKeyFrameCount; i6++) {
                    int i7 = this.mPathMode[i6];
                    if (i7 == 1) {
                        z = true;
                    }
                    if (i7 == 0) {
                        z2 = true;
                    }
                }
                if (z) {
                    float[] fArr = this.mPoints;
                    canvas.drawLine(fArr[0], fArr[1], fArr[fArr.length - 2], fArr[fArr.length - 1], this.mPaintGraph);
                }
                if (z2) {
                    drawPathCartesian(canvas);
                }
            }
            if (i == 2) {
                float[] fArr2 = this.mPoints;
                canvas.drawLine(fArr2[0], fArr2[1], fArr2[fArr2.length - 2], fArr2[fArr2.length - 1], this.mPaintGraph);
            }
            if (i == 3) {
                drawPathCartesian(canvas);
            }
            canvas.drawLines(this.mPoints, this.mPaint);
            View view = motionController.mView;
            if (view != null) {
                i3 = view.getWidth();
                i4 = motionController.mView.getHeight();
            } else {
                i3 = 0;
                i4 = 0;
            }
            int i8 = 1;
            while (i8 < i2 - 1) {
                if (i == 4 && this.mPathMode[i8 - 1] == 0) {
                    i5 = i8;
                } else {
                    float[] fArr3 = this.mKeyFramePoints;
                    int i9 = i8 * 2;
                    float f3 = fArr3[i9];
                    float f4 = fArr3[i9 + 1];
                    this.mPath.reset();
                    this.mPath.moveTo(f3, f4 + 10.0f);
                    this.mPath.lineTo(f3 + 10.0f, f4);
                    this.mPath.lineTo(f3, f4 - 10.0f);
                    this.mPath.lineTo(f3 - 10.0f, f4);
                    this.mPath.close();
                    int i10 = i8 - 1;
                    motionController.getKeyFrame(i10);
                    if (i == 4) {
                        int i11 = this.mPathMode[i10];
                        if (i11 == 1) {
                            drawPathRelativeTicks(canvas, f3 - 0.0f, f4 - 0.0f);
                        } else if (i11 == 0) {
                            drawPathCartesianTicks(canvas, f3 - 0.0f, f4 - 0.0f);
                        } else if (i11 == 2) {
                            f = f4;
                            f2 = f3;
                            i5 = i8;
                            drawPathScreenTicks(canvas, f3 - 0.0f, f4 - 0.0f, i3, i4);
                            canvas.drawPath(this.mPath, this.mFillPaint);
                        }
                        f = f4;
                        f2 = f3;
                        i5 = i8;
                        canvas.drawPath(this.mPath, this.mFillPaint);
                    } else {
                        f = f4;
                        f2 = f3;
                        i5 = i8;
                    }
                    if (i == 2) {
                        drawPathRelativeTicks(canvas, f2 - 0.0f, f - 0.0f);
                    }
                    if (i == 3) {
                        drawPathCartesianTicks(canvas, f2 - 0.0f, f - 0.0f);
                    }
                    if (i == 6) {
                        drawPathScreenTicks(canvas, f2 - 0.0f, f - 0.0f, i3, i4);
                    }
                    canvas.drawPath(this.mPath, this.mFillPaint);
                }
                i8 = i5 + 1;
            }
            float[] fArr4 = this.mPoints;
            if (fArr4.length > 1) {
                canvas.drawCircle(fArr4[0], fArr4[1], 8.0f, this.mPaintKeyframes);
                float[] fArr5 = this.mPoints;
                canvas.drawCircle(fArr5[fArr5.length - 2], fArr5[fArr5.length - 1], 8.0f, this.mPaintKeyframes);
            }
        }

        final void getTextBounds(Paint paint, String str) {
            paint.getTextBounds(str, 0, str.length(), this.mBounds);
        }
    }

    class Model {
        int mEndId;
        int mStartId;
        ConstraintWidgetContainer mLayoutStart = new ConstraintWidgetContainer();
        ConstraintWidgetContainer mLayoutEnd = new ConstraintWidgetContainer();
        ConstraintSet mStart = null;
        ConstraintSet mEnd = null;

        Model() {
        }

        private void computeStartEndSize(int i, int i2) {
            MotionLayout motionLayout = MotionLayout.this;
            int optimizationLevel = motionLayout.getOptimizationLevel();
            if (motionLayout.mCurrentState == motionLayout.getStartState()) {
                ConstraintWidgetContainer constraintWidgetContainer = this.mLayoutEnd;
                ConstraintSet constraintSet = this.mEnd;
                motionLayout.resolveSystem(constraintWidgetContainer, optimizationLevel, (constraintSet == null || constraintSet.mRotate == 0) ? i : i2, (constraintSet == null || constraintSet.mRotate == 0) ? i2 : i);
                ConstraintSet constraintSet2 = this.mStart;
                if (constraintSet2 != null) {
                    ConstraintWidgetContainer constraintWidgetContainer2 = this.mLayoutStart;
                    int i3 = constraintSet2.mRotate;
                    int i4 = i3 == 0 ? i : i2;
                    if (i3 == 0) {
                        i = i2;
                    }
                    motionLayout.resolveSystem(constraintWidgetContainer2, optimizationLevel, i4, i);
                    return;
                }
                return;
            }
            ConstraintSet constraintSet3 = this.mStart;
            if (constraintSet3 != null) {
                ConstraintWidgetContainer constraintWidgetContainer3 = this.mLayoutStart;
                int i5 = constraintSet3.mRotate;
                motionLayout.resolveSystem(constraintWidgetContainer3, optimizationLevel, i5 == 0 ? i : i2, i5 == 0 ? i2 : i);
            }
            ConstraintWidgetContainer constraintWidgetContainer4 = this.mLayoutEnd;
            ConstraintSet constraintSet4 = this.mEnd;
            int i6 = (constraintSet4 == null || constraintSet4.mRotate == 0) ? i : i2;
            if (constraintSet4 == null || constraintSet4.mRotate == 0) {
                i = i2;
            }
            motionLayout.resolveSystem(constraintWidgetContainer4, optimizationLevel, i6, i);
        }

        static void copy(ConstraintWidgetContainer constraintWidgetContainer, ConstraintWidgetContainer constraintWidgetContainer2) {
            ArrayList<ConstraintWidget> arrayList = constraintWidgetContainer.mChildren;
            HashMap<ConstraintWidget, ConstraintWidget> hashMap = new HashMap<>();
            hashMap.put(constraintWidgetContainer, constraintWidgetContainer2);
            constraintWidgetContainer2.mChildren.clear();
            constraintWidgetContainer2.copy(constraintWidgetContainer, hashMap);
            Iterator<ConstraintWidget> it = arrayList.iterator();
            while (it.hasNext()) {
                ConstraintWidget next = it.next();
                ConstraintWidget barrier = next instanceof Barrier ? new Barrier() : next instanceof Guideline ? new Guideline() : next instanceof Flow ? new Flow() : next instanceof Placeholder ? new Placeholder() : next instanceof Helper ? new HelperWidget() : new ConstraintWidget();
                constraintWidgetContainer2.mChildren.add(barrier);
                ConstraintWidget constraintWidget = barrier.mParent;
                if (constraintWidget != null) {
                    ((WidgetContainer) constraintWidget).mChildren.remove(barrier);
                    barrier.reset();
                }
                barrier.mParent = constraintWidgetContainer2;
                hashMap.put(next, barrier);
            }
            Iterator<ConstraintWidget> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ConstraintWidget next2 = it2.next();
                hashMap.get(next2).copy(next2, hashMap);
            }
        }

        static ConstraintWidget getWidget(ConstraintWidgetContainer constraintWidgetContainer, View view) {
            if (constraintWidgetContainer.getCompanionWidget() == view) {
                return constraintWidgetContainer;
            }
            ArrayList<ConstraintWidget> arrayList = constraintWidgetContainer.mChildren;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ConstraintWidget constraintWidget = arrayList.get(i);
                if (constraintWidget.getCompanionWidget() == view) {
                    return constraintWidget;
                }
            }
            return null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void setupConstraintWidget(ConstraintWidgetContainer constraintWidgetContainer, ConstraintSet constraintSet) {
            SparseArray sparseArray = new SparseArray();
            Constraints.LayoutParams layoutParams = new Constraints.LayoutParams();
            sparseArray.clear();
            sparseArray.put(0, constraintWidgetContainer);
            MotionLayout motionLayout = MotionLayout.this;
            sparseArray.put(motionLayout.getId(), constraintWidgetContainer);
            if (constraintSet != null && constraintSet.mRotate != 0) {
                motionLayout.resolveSystem(this.mLayoutEnd, motionLayout.getOptimizationLevel(), View.MeasureSpec.makeMeasureSpec(motionLayout.getHeight(), 1073741824), View.MeasureSpec.makeMeasureSpec(motionLayout.getWidth(), 1073741824));
            }
            Iterator<ConstraintWidget> it = constraintWidgetContainer.mChildren.iterator();
            while (it.hasNext()) {
                ConstraintWidget next = it.next();
                next.setAnimated();
                sparseArray.put(((View) next.getCompanionWidget()).getId(), next);
            }
            Iterator<ConstraintWidget> it2 = constraintWidgetContainer.mChildren.iterator();
            while (it2.hasNext()) {
                ConstraintWidget next2 = it2.next();
                View view = (View) next2.getCompanionWidget();
                constraintSet.applyToLayoutParams(view.getId(), layoutParams);
                next2.setWidth(constraintSet.getWidth(view.getId()));
                next2.setHeight(constraintSet.getHeight(view.getId()));
                if (view instanceof ConstraintHelper) {
                    constraintSet.applyToHelper((ConstraintHelper) view, next2, layoutParams, sparseArray);
                    if (view instanceof androidx.constraintlayout.widget.Barrier) {
                        ((androidx.constraintlayout.widget.Barrier) view).validateParams();
                    }
                }
                layoutParams.resolveLayoutDirection(motionLayout.getLayoutDirection());
                motionLayout.applyConstraintsFromLayoutParams(false, view, next2, layoutParams, sparseArray);
                if (constraintSet.getVisibilityMode(view.getId()) == 1) {
                    next2.setVisibility(view.getVisibility());
                } else {
                    next2.setVisibility(constraintSet.getVisibility(view.getId()));
                }
            }
            Iterator<ConstraintWidget> it3 = constraintWidgetContainer.mChildren.iterator();
            while (it3.hasNext()) {
                ConstraintWidget next3 = it3.next();
                if (next3 instanceof VirtualLayout) {
                    ConstraintHelper constraintHelper = (ConstraintHelper) next3.getCompanionWidget();
                    Helper helper = (Helper) next3;
                    constraintHelper.updatePreLayout(helper, sparseArray);
                    VirtualLayout virtualLayout = (VirtualLayout) helper;
                    for (int i = 0; i < virtualLayout.mWidgetsCount; i++) {
                        ConstraintWidget constraintWidget = virtualLayout.mWidgets[i];
                        if (constraintWidget != null) {
                            constraintWidget.setInVirtualLayout();
                        }
                    }
                }
            }
        }

        public final void build() {
            MotionLayout motionLayout = MotionLayout.this;
            int childCount = motionLayout.getChildCount();
            motionLayout.mFrameArrayList.clear();
            SparseArray sparseArray = new SparseArray();
            int[] iArr = new int[childCount];
            for (int i = 0; i < childCount; i++) {
                View childAt = motionLayout.getChildAt(i);
                MotionController motionController = new MotionController(childAt);
                int id = childAt.getId();
                iArr[i] = id;
                sparseArray.put(id, motionController);
                motionLayout.mFrameArrayList.put(childAt, motionController);
            }
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt2 = motionLayout.getChildAt(i2);
                MotionController motionController2 = motionLayout.mFrameArrayList.get(childAt2);
                if (motionController2 != null) {
                    if (this.mStart != null) {
                        ConstraintWidget widget = getWidget(this.mLayoutStart, childAt2);
                        if (widget != null) {
                            motionController2.setStartState(MotionLayout.access$2000(motionLayout, widget), this.mStart, motionLayout.getWidth(), motionLayout.getHeight());
                        } else if (motionLayout.mDebugPath != 0) {
                            Log.e("MotionLayout", Debug.getLocation() + "no widget for  " + Debug.getName(childAt2) + " (" + childAt2.getClass().getName() + ")");
                        }
                    }
                    if (this.mEnd != null) {
                        ConstraintWidget widget2 = getWidget(this.mLayoutEnd, childAt2);
                        if (widget2 != null) {
                            motionController2.setEndState(MotionLayout.access$2000(motionLayout, widget2), this.mEnd, motionLayout.getWidth(), motionLayout.getHeight());
                        } else if (motionLayout.mDebugPath != 0) {
                            Log.e("MotionLayout", Debug.getLocation() + "no widget for  " + Debug.getName(childAt2) + " (" + childAt2.getClass().getName() + ")");
                        }
                    }
                }
            }
            for (int i3 = 0; i3 < childCount; i3++) {
                MotionController motionController3 = (MotionController) sparseArray.get(iArr[i3]);
                int animateRelativeTo = motionController3.getAnimateRelativeTo();
                if (animateRelativeTo != -1) {
                    motionController3.setupRelative((MotionController) sparseArray.get(animateRelativeTo));
                }
            }
        }

        final void initFrom(ConstraintSet constraintSet, ConstraintSet constraintSet2) {
            this.mStart = constraintSet;
            this.mEnd = constraintSet2;
            this.mLayoutStart = new ConstraintWidgetContainer();
            this.mLayoutEnd = new ConstraintWidgetContainer();
            ConstraintWidgetContainer constraintWidgetContainer = this.mLayoutStart;
            MotionLayout motionLayout = MotionLayout.this;
            constraintWidgetContainer.setMeasurer(((ConstraintLayout) motionLayout).mLayoutWidget.getMeasurer());
            this.mLayoutEnd.setMeasurer(((ConstraintLayout) motionLayout).mLayoutWidget.getMeasurer());
            this.mLayoutStart.mChildren.clear();
            this.mLayoutEnd.mChildren.clear();
            copy(((ConstraintLayout) motionLayout).mLayoutWidget, this.mLayoutStart);
            copy(((ConstraintLayout) motionLayout).mLayoutWidget, this.mLayoutEnd);
            if (motionLayout.mTransitionLastPosition > 0.5d) {
                if (constraintSet != null) {
                    setupConstraintWidget(this.mLayoutStart, constraintSet);
                }
                setupConstraintWidget(this.mLayoutEnd, constraintSet2);
            } else {
                setupConstraintWidget(this.mLayoutEnd, constraintSet2);
                if (constraintSet != null) {
                    setupConstraintWidget(this.mLayoutStart, constraintSet);
                }
            }
            this.mLayoutStart.setRtl(motionLayout.isRtl());
            this.mLayoutStart.updateHierarchy();
            this.mLayoutEnd.setRtl(motionLayout.isRtl());
            this.mLayoutEnd.updateHierarchy();
            ViewGroup.LayoutParams layoutParams = motionLayout.getLayoutParams();
            if (layoutParams != null) {
                int i = layoutParams.width;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (i == -2) {
                    this.mLayoutStart.setHorizontalDimensionBehaviour(dimensionBehaviour);
                    this.mLayoutEnd.setHorizontalDimensionBehaviour(dimensionBehaviour);
                }
                if (layoutParams.height == -2) {
                    this.mLayoutStart.setVerticalDimensionBehaviour(dimensionBehaviour);
                    this.mLayoutEnd.setVerticalDimensionBehaviour(dimensionBehaviour);
                }
            }
        }

        public final void reEvaluateState() {
            MotionLayout motionLayout = MotionLayout.this;
            int i = motionLayout.mLastWidthMeasureSpec;
            int i2 = motionLayout.mLastHeightMeasureSpec;
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            motionLayout.mWidthMeasureMode = mode;
            motionLayout.mHeightMeasureMode = mode2;
            computeStartEndSize(i, i2);
            boolean z = true;
            if (((motionLayout.getParent() instanceof MotionLayout) && mode == 1073741824 && mode2 == 1073741824) ? false : true) {
                computeStartEndSize(i, i2);
                motionLayout.mStartWrapWidth = this.mLayoutStart.getWidth();
                motionLayout.mStartWrapHeight = this.mLayoutStart.getHeight();
                motionLayout.mEndWrapWidth = this.mLayoutEnd.getWidth();
                int height = this.mLayoutEnd.getHeight();
                motionLayout.mEndWrapHeight = height;
                motionLayout.mMeasureDuringTransition = (motionLayout.mStartWrapWidth == motionLayout.mEndWrapWidth && motionLayout.mStartWrapHeight == height) ? false : true;
            }
            int i3 = motionLayout.mStartWrapWidth;
            int i4 = motionLayout.mStartWrapHeight;
            int i5 = motionLayout.mWidthMeasureMode;
            if (i5 == Integer.MIN_VALUE || i5 == 0) {
                i3 = (int) ((motionLayout.mPostInterpolationPosition * (motionLayout.mEndWrapWidth - i3)) + i3);
            }
            int i6 = i3;
            int i7 = motionLayout.mHeightMeasureMode;
            int i8 = (i7 == Integer.MIN_VALUE || i7 == 0) ? (int) ((motionLayout.mPostInterpolationPosition * (motionLayout.mEndWrapHeight - i4)) + i4) : i4;
            boolean z2 = this.mLayoutStart.isWidthMeasuredTooSmall() || this.mLayoutEnd.isWidthMeasuredTooSmall();
            if (!this.mLayoutStart.isHeightMeasuredTooSmall() && !this.mLayoutEnd.isHeightMeasuredTooSmall()) {
                z = false;
            }
            MotionLayout.this.resolveMeasuredDimension(i, i2, i6, i8, z2, z);
            MotionLayout.access$1400(motionLayout);
        }
    }

    protected interface MotionTracker {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MyTracker implements MotionTracker {
        private static MyTracker sMe = new MyTracker();
        VelocityTracker mTracker;

        public static MyTracker obtain() {
            MyTracker myTracker = sMe;
            myTracker.mTracker = VelocityTracker.obtain();
            return myTracker;
        }
    }

    class StateCache {
        float mProgress = Float.NaN;
        float mVelocity = Float.NaN;
        int mStartState = -1;
        int mEndState = -1;

        StateCache() {
        }

        final void apply() {
            int i = this.mStartState;
            MotionLayout motionLayout = MotionLayout.this;
            if (i != -1 || this.mEndState != -1) {
                if (i == -1) {
                    motionLayout.transitionToState(this.mEndState);
                } else {
                    int i2 = this.mEndState;
                    if (i2 == -1) {
                        motionLayout.setState(i);
                    } else {
                        motionLayout.setTransition(i, i2);
                    }
                }
                motionLayout.setState(TransitionState.SETUP);
            }
            if (Float.isNaN(this.mVelocity)) {
                if (Float.isNaN(this.mProgress)) {
                    return;
                }
                motionLayout.setProgress(this.mProgress);
            } else {
                motionLayout.setProgress(this.mProgress, this.mVelocity);
                this.mProgress = Float.NaN;
                this.mVelocity = Float.NaN;
                this.mStartState = -1;
                this.mEndState = -1;
            }
        }
    }

    public interface TransitionListener {
        void onTransitionChange();

        void onTransitionCompleted(int i);

        void onTransitionStarted();

        void onTransitionTrigger();
    }

    enum TransitionState {
        UNDEFINED,
        SETUP,
        MOVING,
        FINISHED;

        TransitionState() {
        }
    }

    public MotionLayout(Context context) {
        super(context);
        this.mProgressInterpolator = null;
        this.mLastVelocity = 0.0f;
        this.mBeginState = -1;
        this.mCurrentState = -1;
        this.mEndState = -1;
        this.mLastWidthMeasureSpec = 0;
        this.mLastHeightMeasureSpec = 0;
        this.mInteractionEnabled = true;
        this.mFrameArrayList = new HashMap<>();
        this.mAnimationStartTime = 0L;
        this.mTransitionDuration = 1.0f;
        this.mTransitionPosition = 0.0f;
        this.mTransitionLastPosition = 0.0f;
        this.mTransitionGoalPosition = 0.0f;
        this.mInTransition = false;
        this.mDebugPath = 0;
        this.mTemporalInterpolator = false;
        this.mStopLogic = new StopLogic();
        this.mDecelerateLogic = new DecelerateInterpolator();
        this.mUndergoingMotion = false;
        this.mKeepAnimating = false;
        this.mOnShowHelpers = null;
        this.mOnHideHelpers = null;
        this.mDecoratorsHelpers = null;
        this.mTransitionListeners = null;
        this.mFrames = 0;
        this.mLastDrawTime = -1L;
        this.mLastFps = 0.0f;
        this.mListenerState = 0;
        this.mListenerPosition = 0.0f;
        this.mMeasureDuringTransition = false;
        this.mKeyCache = new KeyCache();
        this.mInLayout = false;
        this.mOnComplete = null;
        this.mPreRotate = new HashMap<>();
        this.mTempRect = new Rect();
        this.mDelayedApply = false;
        this.mTransitionState = TransitionState.UNDEFINED;
        this.mModel = new Model();
        this.mNeedsFireTransitionCompleted = false;
        this.mBoundsCheck = new RectF();
        this.mRegionView = null;
        this.mInverseMatrix = null;
        this.mTransitionCompleted = new ArrayList<>();
        init(null);
    }

    static void access$1400(MotionLayout motionLayout) {
        int childCount = motionLayout.getChildCount();
        motionLayout.mModel.build();
        boolean z = true;
        motionLayout.mInTransition = true;
        SparseArray sparseArray = new SparseArray();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = motionLayout.getChildAt(i2);
            sparseArray.put(childAt.getId(), motionLayout.mFrameArrayList.get(childAt));
        }
        int width = motionLayout.getWidth();
        int height = motionLayout.getHeight();
        MotionScene.Transition transition = motionLayout.mScene.mCurrentTransition;
        int i3 = transition != null ? transition.mPathMotionArc : -1;
        if (i3 != -1) {
            for (int i4 = 0; i4 < childCount; i4++) {
                MotionController motionController = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i4));
                if (motionController != null) {
                    motionController.setPathMotionArc(i3);
                }
            }
        }
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        int[] iArr = new int[motionLayout.mFrameArrayList.size()];
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            MotionController motionController2 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i6));
            if (motionController2.getAnimateRelativeTo() != -1) {
                sparseBooleanArray.put(motionController2.getAnimateRelativeTo(), true);
                iArr[i5] = motionController2.getAnimateRelativeTo();
                i5++;
            }
        }
        if (motionLayout.mDecoratorsHelpers != null) {
            for (int i7 = 0; i7 < i5; i7++) {
                MotionController motionController3 = motionLayout.mFrameArrayList.get(motionLayout.findViewById(iArr[i7]));
                if (motionController3 != null) {
                    motionLayout.mScene.getKeyFrames(motionController3);
                }
            }
            Iterator<MotionHelper> it = motionLayout.mDecoratorsHelpers.iterator();
            while (it.hasNext()) {
                it.next().onPreSetup(motionLayout, motionLayout.mFrameArrayList);
            }
            for (int i8 = 0; i8 < i5; i8++) {
                MotionController motionController4 = motionLayout.mFrameArrayList.get(motionLayout.findViewById(iArr[i8]));
                if (motionController4 != null) {
                    motionController4.setup(width, height, motionLayout.getNanoTime());
                }
            }
        } else {
            for (int i9 = 0; i9 < i5; i9++) {
                MotionController motionController5 = motionLayout.mFrameArrayList.get(motionLayout.findViewById(iArr[i9]));
                if (motionController5 != null) {
                    motionLayout.mScene.getKeyFrames(motionController5);
                    motionController5.setup(width, height, motionLayout.getNanoTime());
                }
            }
        }
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt2 = motionLayout.getChildAt(i10);
            MotionController motionController6 = motionLayout.mFrameArrayList.get(childAt2);
            if (!sparseBooleanArray.get(childAt2.getId()) && motionController6 != null) {
                motionLayout.mScene.getKeyFrames(motionController6);
                motionController6.setup(width, height, motionLayout.getNanoTime());
            }
        }
        MotionScene.Transition transition2 = motionLayout.mScene.mCurrentTransition;
        float f = transition2 != null ? transition2.mStagger : 0.0f;
        if (f != 0.0f) {
            boolean z2 = ((double) f) < 0.0d;
            float abs = Math.abs(f);
            float f2 = -3.4028235E38f;
            float f3 = Float.MAX_VALUE;
            int i11 = 0;
            float f4 = -3.4028235E38f;
            float f5 = Float.MAX_VALUE;
            while (true) {
                if (i11 >= childCount) {
                    z = false;
                    break;
                }
                MotionController motionController7 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i11));
                if (!Float.isNaN(motionController7.mMotionStagger)) {
                    break;
                }
                float finalX = motionController7.getFinalX();
                float finalY = motionController7.getFinalY();
                float f6 = z2 ? finalY - finalX : finalY + finalX;
                f5 = Math.min(f5, f6);
                f4 = Math.max(f4, f6);
                i11++;
            }
            if (!z) {
                while (i < childCount) {
                    MotionController motionController8 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i));
                    float finalX2 = motionController8.getFinalX();
                    float finalY2 = motionController8.getFinalY();
                    float f7 = z2 ? finalY2 - finalX2 : finalY2 + finalX2;
                    motionController8.mStaggerScale = 1.0f / (1.0f - abs);
                    motionController8.mStaggerOffset = abs - (((f7 - f5) * abs) / (f4 - f5));
                    i++;
                }
                return;
            }
            for (int i12 = 0; i12 < childCount; i12++) {
                MotionController motionController9 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i12));
                if (!Float.isNaN(motionController9.mMotionStagger)) {
                    f3 = Math.min(f3, motionController9.mMotionStagger);
                    f2 = Math.max(f2, motionController9.mMotionStagger);
                }
            }
            while (i < childCount) {
                MotionController motionController10 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i));
                if (!Float.isNaN(motionController10.mMotionStagger)) {
                    motionController10.mStaggerScale = 1.0f / (1.0f - abs);
                    if (z2) {
                        motionController10.mStaggerOffset = abs - (((f2 - motionController10.mMotionStagger) / (f2 - f3)) * abs);
                    } else {
                        motionController10.mStaggerOffset = abs - (((motionController10.mMotionStagger - f3) * abs) / (f2 - f3));
                    }
                }
                i++;
            }
        }
    }

    static Rect access$2000(MotionLayout motionLayout, ConstraintWidget constraintWidget) {
        motionLayout.mTempRect.top = constraintWidget.getY();
        motionLayout.mTempRect.left = constraintWidget.getX();
        Rect rect = motionLayout.mTempRect;
        int width = constraintWidget.getWidth();
        Rect rect2 = motionLayout.mTempRect;
        rect.right = width + rect2.left;
        int height = constraintWidget.getHeight();
        Rect rect3 = motionLayout.mTempRect;
        rect2.bottom = height + rect3.top;
        return rect3;
    }

    private void fireTransitionChange() {
        CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList;
        if ((this.mTransitionListener == null && ((copyOnWriteArrayList = this.mTransitionListeners) == null || copyOnWriteArrayList.isEmpty())) || this.mListenerPosition == this.mTransitionPosition) {
            return;
        }
        if (this.mListenerState != -1) {
            TransitionListener transitionListener = this.mTransitionListener;
            if (transitionListener != null) {
                transitionListener.onTransitionStarted();
            }
            CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList2 = this.mTransitionListeners;
            if (copyOnWriteArrayList2 != null) {
                Iterator<TransitionListener> it = copyOnWriteArrayList2.iterator();
                while (it.hasNext()) {
                    it.next().onTransitionStarted();
                }
            }
        }
        this.mListenerState = -1;
        this.mListenerPosition = this.mTransitionPosition;
        TransitionListener transitionListener2 = this.mTransitionListener;
        if (transitionListener2 != null) {
            transitionListener2.onTransitionChange();
        }
        CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList3 = this.mTransitionListeners;
        if (copyOnWriteArrayList3 != null) {
            Iterator<TransitionListener> it2 = copyOnWriteArrayList3.iterator();
            while (it2.hasNext()) {
                it2.next().onTransitionChange();
            }
        }
    }

    private boolean handlesTouchEvent(float f, float f2, MotionEvent motionEvent, View view) {
        boolean z;
        boolean onTouchEvent;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                if (handlesTouchEvent((r3.getLeft() + f) - view.getScrollX(), (r3.getTop() + f2) - view.getScrollY(), motionEvent, viewGroup.getChildAt(childCount))) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (!z) {
            this.mBoundsCheck.set(f, f2, (view.getRight() + f) - view.getLeft(), (view.getBottom() + f2) - view.getTop());
            if (motionEvent.getAction() != 0 || this.mBoundsCheck.contains(motionEvent.getX(), motionEvent.getY())) {
                float f3 = -f;
                float f4 = -f2;
                Matrix matrix = view.getMatrix();
                if (matrix.isIdentity()) {
                    motionEvent.offsetLocation(f3, f4);
                    onTouchEvent = view.onTouchEvent(motionEvent);
                    motionEvent.offsetLocation(-f3, -f4);
                } else {
                    MotionEvent obtain = MotionEvent.obtain(motionEvent);
                    obtain.offsetLocation(f3, f4);
                    if (this.mInverseMatrix == null) {
                        this.mInverseMatrix = new Matrix();
                    }
                    matrix.invert(this.mInverseMatrix);
                    obtain.transform(this.mInverseMatrix);
                    onTouchEvent = view.onTouchEvent(obtain);
                    obtain.recycle();
                }
                if (onTouchEvent) {
                    return true;
                }
            }
        }
        return z;
    }

    private void init(AttributeSet attributeSet) {
        MotionScene motionScene;
        IS_IN_EDIT_MODE = isInEditMode();
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.MotionLayout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            boolean z = true;
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 2) {
                    this.mScene = new MotionScene(getContext(), this, obtainStyledAttributes.getResourceId(index, -1));
                } else if (index == 1) {
                    this.mCurrentState = obtainStyledAttributes.getResourceId(index, -1);
                } else if (index == 4) {
                    this.mTransitionGoalPosition = obtainStyledAttributes.getFloat(index, 0.0f);
                    this.mInTransition = true;
                } else if (index == 0) {
                    z = obtainStyledAttributes.getBoolean(index, z);
                } else if (index == 5) {
                    if (this.mDebugPath == 0) {
                        this.mDebugPath = obtainStyledAttributes.getBoolean(index, false) ? 2 : 0;
                    }
                } else if (index == 3) {
                    this.mDebugPath = obtainStyledAttributes.getInt(index, 0);
                }
            }
            obtainStyledAttributes.recycle();
            if (this.mScene == null) {
                Log.e("MotionLayout", "WARNING NO app:layoutDescription tag");
            }
            if (!z) {
                this.mScene = null;
            }
        }
        if (this.mDebugPath != 0) {
            MotionScene motionScene2 = this.mScene;
            if (motionScene2 == null) {
                Log.e("MotionLayout", "CHECK: motion scene not set! set \"app:layoutDescription=\"@xml/file\"");
            } else {
                int startId = motionScene2.getStartId();
                MotionScene motionScene3 = this.mScene;
                ConstraintSet constraintSet = motionScene3.getConstraintSet(motionScene3.getStartId());
                String name = Debug.getName(getContext(), startId);
                int childCount = getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = getChildAt(i2);
                    int id = childAt.getId();
                    if (id == -1) {
                        Log.w("MotionLayout", "CHECK: " + name + " ALL VIEWS SHOULD HAVE ID's " + childAt.getClass().getName() + " does not!");
                    }
                    if (constraintSet.getConstraint(id) == null) {
                        Log.w("MotionLayout", "CHECK: " + name + " NO CONSTRAINTS for " + Debug.getName(childAt));
                    }
                }
                int[] knownIds = constraintSet.getKnownIds();
                for (int i3 = 0; i3 < knownIds.length; i3++) {
                    int i4 = knownIds[i3];
                    String name2 = Debug.getName(getContext(), i4);
                    if (findViewById(knownIds[i3]) == null) {
                        Log.w("MotionLayout", "CHECK: " + name + " NO View matches id " + name2);
                    }
                    if (constraintSet.getHeight(i4) == -1) {
                        Log.w("MotionLayout", "CHECK: " + name + "(" + name2 + ") no LAYOUT_HEIGHT");
                    }
                    if (constraintSet.getWidth(i4) == -1) {
                        Log.w("MotionLayout", "CHECK: " + name + "(" + name2 + ") no LAYOUT_HEIGHT");
                    }
                }
                SparseIntArray sparseIntArray = new SparseIntArray();
                SparseIntArray sparseIntArray2 = new SparseIntArray();
                Iterator<MotionScene.Transition> it = this.mScene.getDefinedTransitions().iterator();
                while (it.hasNext()) {
                    MotionScene.Transition next = it.next();
                    if (next == this.mScene.mCurrentTransition) {
                        Log.v("MotionLayout", "CHECK: CURRENT");
                    }
                    if (next.getStartConstraintSetId() == next.getEndConstraintSetId()) {
                        Log.e("MotionLayout", "CHECK: start and end constraint set should not be the same!");
                    }
                    int startConstraintSetId = next.getStartConstraintSetId();
                    int endConstraintSetId = next.getEndConstraintSetId();
                    String name3 = Debug.getName(getContext(), startConstraintSetId);
                    String name4 = Debug.getName(getContext(), endConstraintSetId);
                    if (sparseIntArray.get(startConstraintSetId) == endConstraintSetId) {
                        Log.e("MotionLayout", "CHECK: two transitions with the same start and end " + name3 + "->" + name4);
                    }
                    if (sparseIntArray2.get(endConstraintSetId) == startConstraintSetId) {
                        Log.e("MotionLayout", "CHECK: you can't have reverse transitions" + name3 + "->" + name4);
                    }
                    sparseIntArray.put(startConstraintSetId, endConstraintSetId);
                    sparseIntArray2.put(endConstraintSetId, startConstraintSetId);
                    if (this.mScene.getConstraintSet(startConstraintSetId) == null) {
                        Log.e("MotionLayout", " no such constraintSetStart " + name3);
                    }
                    if (this.mScene.getConstraintSet(endConstraintSetId) == null) {
                        Log.e("MotionLayout", " no such constraintSetEnd " + name3);
                    }
                }
            }
        }
        if (this.mCurrentState != -1 || (motionScene = this.mScene) == null) {
            return;
        }
        this.mCurrentState = motionScene.getStartId();
        this.mBeginState = this.mScene.getStartId();
        MotionScene.Transition transition = this.mScene.mCurrentTransition;
        this.mEndState = transition != null ? transition.mConstraintSetEnd : -1;
    }

    private void processTransitionCompleted() {
        CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList;
        if (this.mTransitionListener == null && ((copyOnWriteArrayList = this.mTransitionListeners) == null || copyOnWriteArrayList.isEmpty())) {
            return;
        }
        Iterator<Integer> it = this.mTransitionCompleted.iterator();
        while (it.hasNext()) {
            Integer next = it.next();
            TransitionListener transitionListener = this.mTransitionListener;
            if (transitionListener != null) {
                transitionListener.onTransitionCompleted(next.intValue());
            }
            CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList2 = this.mTransitionListeners;
            if (copyOnWriteArrayList2 != null) {
                Iterator<TransitionListener> it2 = copyOnWriteArrayList2.iterator();
                while (it2.hasNext()) {
                    it2.next().onTransitionCompleted(next.intValue());
                }
            }
        }
        this.mTransitionCompleted.clear();
    }

    final void animateTo(float f) {
        if (this.mScene == null) {
            return;
        }
        float f2 = this.mTransitionLastPosition;
        float f3 = this.mTransitionPosition;
        if (f2 != f3 && this.mTransitionInstantly) {
            this.mTransitionLastPosition = f3;
        }
        float f4 = this.mTransitionLastPosition;
        if (f4 == f) {
            return;
        }
        this.mTemporalInterpolator = false;
        this.mTransitionGoalPosition = f;
        this.mTransitionDuration = r0.getDuration() / 1000.0f;
        setProgress(this.mTransitionGoalPosition);
        this.mInterpolator = null;
        this.mProgressInterpolator = this.mScene.getInterpolator();
        this.mTransitionInstantly = false;
        this.mAnimationStartTime = getNanoTime();
        this.mInTransition = true;
        this.mTransitionPosition = f4;
        this.mTransitionLastPosition = f4;
        invalidate();
    }

    public final void applyViewTransition(int i, MotionController motionController) {
        MotionScene motionScene = this.mScene;
        if (motionScene != null) {
            motionScene.mViewTransitionController.applyViewTransition(i, motionController);
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    protected final void dispatchDraw(Canvas canvas) {
        ViewTransitionController viewTransitionController;
        ArrayList<ViewTransition.Animate> arrayList;
        ArrayList<MotionHelper> arrayList2 = this.mDecoratorsHelpers;
        if (arrayList2 != null) {
            Iterator<MotionHelper> it = arrayList2.iterator();
            while (it.hasNext()) {
                it.next().getClass();
            }
        }
        evaluate(false);
        MotionScene motionScene = this.mScene;
        if (motionScene != null && (viewTransitionController = motionScene.mViewTransitionController) != null && (arrayList = viewTransitionController.mAnimations) != null) {
            Iterator<ViewTransition.Animate> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                it2.next().mutate();
            }
            viewTransitionController.mAnimations.removeAll(viewTransitionController.mRemoveList);
            viewTransitionController.mRemoveList.clear();
            if (viewTransitionController.mAnimations.isEmpty()) {
                viewTransitionController.mAnimations = null;
            }
        }
        super.dispatchDraw(canvas);
        if (this.mScene == null) {
            return;
        }
        if ((this.mDebugPath & 1) == 1 && !isInEditMode()) {
            this.mFrames++;
            long nanoTime = getNanoTime();
            long j = this.mLastDrawTime;
            if (j != -1) {
                if (nanoTime - j > 200000000) {
                    this.mLastFps = ((int) ((this.mFrames / (r5 * 1.0E-9f)) * 100.0f)) / 100.0f;
                    this.mFrames = 0;
                    this.mLastDrawTime = nanoTime;
                }
            } else {
                this.mLastDrawTime = nanoTime;
            }
            Paint paint = new Paint();
            paint.setTextSize(42.0f);
            StringBuilder m2m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(this.mLastFps + " fps " + Debug.getState(this.mBeginState, this) + " -> ");
            m2m.append(Debug.getState(this.mEndState, this));
            m2m.append(" (progress: ");
            m2m.append(((int) (getProgress() * 1000.0f)) / 10.0f);
            m2m.append(" ) state=");
            int i = this.mCurrentState;
            m2m.append(i == -1 ? "undefined" : Debug.getState(i, this));
            String sb = m2m.toString();
            paint.setColor(-16777216);
            canvas.drawText(sb, 11.0f, getHeight() - 29, paint);
            paint.setColor(-7864184);
            canvas.drawText(sb, 10.0f, getHeight() - 30, paint);
        }
        if (this.mDebugPath > 1) {
            if (this.mDevModeDraw == null) {
                this.mDevModeDraw = new DevModeDraw();
            }
            this.mDevModeDraw.draw(canvas, this.mFrameArrayList, this.mScene.getDuration(), this.mDebugPath);
        }
        ArrayList<MotionHelper> arrayList3 = this.mDecoratorsHelpers;
        if (arrayList3 != null) {
            Iterator<MotionHelper> it3 = arrayList3.iterator();
            while (it3.hasNext()) {
                it3.next().getClass();
            }
        }
    }

    final void endTrigger(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            MotionController motionController = this.mFrameArrayList.get(getChildAt(i));
            if (motionController != null) {
                motionController.endTrigger(z);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0153  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void evaluate(boolean z) {
        boolean z2;
        char c;
        float f;
        TransitionState transitionState;
        int childCount;
        Interpolator interpolator;
        int i;
        int i2;
        boolean z3;
        if (this.mTransitionLastTime == -1) {
            this.mTransitionLastTime = getNanoTime();
        }
        float f2 = this.mTransitionLastPosition;
        if (f2 > 0.0f && f2 < 1.0f) {
            this.mCurrentState = -1;
        }
        boolean z4 = false;
        if (this.mKeepAnimating || (this.mInTransition && (z || this.mTransitionGoalPosition != f2))) {
            float signum = Math.signum(this.mTransitionGoalPosition - f2);
            long nanoTime = getNanoTime();
            MotionInterpolator motionInterpolator = this.mInterpolator;
            float f3 = !(motionInterpolator instanceof MotionInterpolator) ? (((nanoTime - this.mTransitionLastTime) * signum) * 1.0E-9f) / this.mTransitionDuration : 0.0f;
            float f4 = this.mTransitionLastPosition + f3;
            if (this.mTransitionInstantly) {
                f4 = this.mTransitionGoalPosition;
            }
            if ((signum <= 0.0f || f4 < this.mTransitionGoalPosition) && (signum > 0.0f || f4 > this.mTransitionGoalPosition)) {
                z2 = false;
            } else {
                f4 = this.mTransitionGoalPosition;
                this.mInTransition = false;
                z2 = true;
            }
            this.mTransitionLastPosition = f4;
            this.mTransitionPosition = f4;
            this.mTransitionLastTime = nanoTime;
            if (motionInterpolator == null || z2) {
                this.mLastVelocity = f3;
            } else if (this.mTemporalInterpolator) {
                f = motionInterpolator.getInterpolation((nanoTime - this.mAnimationStartTime) * 1.0E-9f);
                MotionInterpolator motionInterpolator2 = this.mInterpolator;
                StopLogic stopLogic = this.mStopLogic;
                c = motionInterpolator2 == stopLogic ? stopLogic.isStopped() ? (char) 2 : (char) 1 : (char) 0;
                this.mTransitionLastPosition = f;
                this.mTransitionLastTime = nanoTime;
                MotionInterpolator motionInterpolator3 = this.mInterpolator;
                if (motionInterpolator3 instanceof MotionInterpolator) {
                    float velocity = motionInterpolator3.getVelocity();
                    this.mLastVelocity = velocity;
                    if (Math.abs(velocity) * this.mTransitionDuration <= 1.0E-5f && c == 2) {
                        this.mInTransition = false;
                    }
                    if (velocity > 0.0f && f >= 1.0f) {
                        this.mTransitionLastPosition = 1.0f;
                        this.mInTransition = false;
                        f = 1.0f;
                    }
                    if (velocity < 0.0f && f <= 0.0f) {
                        this.mTransitionLastPosition = 0.0f;
                        this.mInTransition = false;
                        f = 0.0f;
                    }
                }
                if (Math.abs(this.mLastVelocity) > 1.0E-5f) {
                    setState(TransitionState.MOVING);
                }
                transitionState = TransitionState.FINISHED;
                if (c != 1) {
                    if ((signum > 0.0f && f >= this.mTransitionGoalPosition) || (signum <= 0.0f && f <= this.mTransitionGoalPosition)) {
                        f = this.mTransitionGoalPosition;
                        this.mInTransition = false;
                    }
                    if (f >= 1.0f || f <= 0.0f) {
                        this.mInTransition = false;
                        setState(transitionState);
                    }
                }
                childCount = getChildCount();
                this.mKeepAnimating = false;
                long nanoTime2 = getNanoTime();
                this.mPostInterpolationPosition = f;
                Interpolator interpolator2 = this.mProgressInterpolator;
                float interpolation = interpolator2 != null ? f : interpolator2.getInterpolation(f);
                interpolator = this.mProgressInterpolator;
                if (interpolator != null) {
                    float interpolation2 = interpolator.getInterpolation((signum / this.mTransitionDuration) + f);
                    this.mLastVelocity = interpolation2;
                    this.mLastVelocity = interpolation2 - this.mProgressInterpolator.getInterpolation(f);
                }
                for (i = 0; i < childCount; i++) {
                    View childAt = getChildAt(i);
                    MotionController motionController = this.mFrameArrayList.get(childAt);
                    if (motionController != null) {
                        this.mKeepAnimating = motionController.interpolate(interpolation, nanoTime2, childAt, this.mKeyCache) | this.mKeepAnimating;
                    }
                }
                boolean z5 = (signum <= 0.0f && f >= this.mTransitionGoalPosition) || (signum <= 0.0f && f <= this.mTransitionGoalPosition);
                if (!this.mKeepAnimating && !this.mInTransition && z5) {
                    setState(transitionState);
                }
                if (this.mMeasureDuringTransition) {
                    requestLayout();
                }
                this.mKeepAnimating = (!z5) | this.mKeepAnimating;
                if (f <= 0.0f || (i2 = this.mBeginState) == -1 || this.mCurrentState == i2) {
                    z4 = false;
                } else {
                    this.mCurrentState = i2;
                    this.mScene.getConstraintSet(i2).applyCustomAttributes(this);
                    setState(transitionState);
                    z4 = true;
                }
                if (f >= 1.0d) {
                    int i3 = this.mCurrentState;
                    int i4 = this.mEndState;
                    if (i3 != i4) {
                        this.mCurrentState = i4;
                        this.mScene.getConstraintSet(i4).applyCustomAttributes(this);
                        setState(transitionState);
                        z4 = true;
                    }
                }
                if (!this.mKeepAnimating || this.mInTransition) {
                    invalidate();
                } else if ((signum > 0.0f && f == 1.0f) || (signum < 0.0f && f == 0.0f)) {
                    setState(transitionState);
                }
                if (!this.mKeepAnimating && !this.mInTransition && ((signum > 0.0f && f == 1.0f) || (signum < 0.0f && f == 0.0f))) {
                    onNewStateAttachHandlers();
                }
            } else {
                float interpolation3 = motionInterpolator.getInterpolation(f4);
                MotionInterpolator motionInterpolator4 = this.mInterpolator;
                if (motionInterpolator4 instanceof MotionInterpolator) {
                    this.mLastVelocity = motionInterpolator4.getVelocity();
                } else {
                    this.mLastVelocity = ((motionInterpolator4.getInterpolation(f4 + f3) - interpolation3) * signum) / f3;
                }
                f4 = interpolation3;
            }
            c = 0;
            f = f4;
            if (Math.abs(this.mLastVelocity) > 1.0E-5f) {
            }
            transitionState = TransitionState.FINISHED;
            if (c != 1) {
            }
            childCount = getChildCount();
            this.mKeepAnimating = false;
            long nanoTime22 = getNanoTime();
            this.mPostInterpolationPosition = f;
            Interpolator interpolator22 = this.mProgressInterpolator;
            if (interpolator22 != null) {
            }
            interpolator = this.mProgressInterpolator;
            if (interpolator != null) {
            }
            while (i < childCount) {
            }
            if (signum <= 0.0f) {
            }
            if (!this.mKeepAnimating) {
                setState(transitionState);
            }
            if (this.mMeasureDuringTransition) {
            }
            this.mKeepAnimating = (!z5) | this.mKeepAnimating;
            if (f <= 0.0f) {
            }
            z4 = false;
            if (f >= 1.0d) {
            }
            if (this.mKeepAnimating) {
            }
            invalidate();
            if (!this.mKeepAnimating) {
                onNewStateAttachHandlers();
            }
        }
        float f5 = this.mTransitionLastPosition;
        if (f5 < 1.0f) {
            if (f5 <= 0.0f) {
                int i5 = this.mCurrentState;
                int i6 = this.mBeginState;
                z3 = i5 == i6 ? z4 : true;
                this.mCurrentState = i6;
            }
            this.mNeedsFireTransitionCompleted |= z4;
            if (z4 && !this.mInLayout) {
                requestLayout();
            }
            this.mTransitionPosition = this.mTransitionLastPosition;
        }
        int i7 = this.mCurrentState;
        int i8 = this.mEndState;
        z3 = i7 == i8 ? z4 : true;
        this.mCurrentState = i8;
        z4 = z3;
        this.mNeedsFireTransitionCompleted |= z4;
        if (z4) {
            requestLayout();
        }
        this.mTransitionPosition = this.mTransitionLastPosition;
    }

    protected final void fireTransitionCompleted() {
        int i;
        CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList;
        if ((this.mTransitionListener != null || ((copyOnWriteArrayList = this.mTransitionListeners) != null && !copyOnWriteArrayList.isEmpty())) && this.mListenerState == -1) {
            this.mListenerState = this.mCurrentState;
            if (this.mTransitionCompleted.isEmpty()) {
                i = -1;
            } else {
                i = this.mTransitionCompleted.get(r0.size() - 1).intValue();
            }
            int i2 = this.mCurrentState;
            if (i != i2 && i2 != -1) {
                this.mTransitionCompleted.add(Integer.valueOf(i2));
            }
        }
        processTransitionCompleted();
        Runnable runnable = this.mOnComplete;
        if (runnable != null) {
            runnable.run();
            this.mOnComplete = null;
        }
    }

    public final void fireTrigger(float f, int i, boolean z) {
        TransitionListener transitionListener = this.mTransitionListener;
        if (transitionListener != null) {
            transitionListener.onTransitionTrigger();
        }
        CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList = this.mTransitionListeners;
        if (copyOnWriteArrayList != null) {
            Iterator<TransitionListener> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                it.next().onTransitionTrigger();
            }
        }
    }

    final void getAnchorDpDt(int i, float f, float f2, float f3, float[] fArr) {
        HashMap<View, MotionController> hashMap = this.mFrameArrayList;
        View viewById = getViewById(i);
        MotionController motionController = hashMap.get(viewById);
        if (motionController != null) {
            motionController.getDpDt(f, f2, f3, fArr);
            viewById.getY();
        } else {
            Log.w("MotionLayout", "WARNING could not find view id " + (viewById == null ? SubMenuBuilder$$ExternalSyntheticOutline0.m0m("", i) : viewById.getContext().getResources().getResourceName(i)));
        }
    }

    public final ConstraintSet getConstraintSet(int i) {
        MotionScene motionScene = this.mScene;
        if (motionScene == null) {
            return null;
        }
        return motionScene.getConstraintSet(i);
    }

    public int[] getConstraintSetIds() {
        MotionScene motionScene = this.mScene;
        if (motionScene == null) {
            return null;
        }
        return motionScene.getConstraintSetIds();
    }

    public int getCurrentState() {
        return this.mCurrentState;
    }

    public ArrayList<MotionScene.Transition> getDefinedTransitions() {
        MotionScene motionScene = this.mScene;
        if (motionScene == null) {
            return null;
        }
        return motionScene.getDefinedTransitions();
    }

    public DesignTool getDesignTool() {
        if (this.mDesignTool == null) {
            this.mDesignTool = new DesignTool();
        }
        return this.mDesignTool;
    }

    public int getEndState() {
        return this.mEndState;
    }

    protected long getNanoTime() {
        return System.nanoTime();
    }

    public float getProgress() {
        return this.mTransitionLastPosition;
    }

    public MotionScene getScene() {
        return this.mScene;
    }

    public int getStartState() {
        return this.mBeginState;
    }

    public float getTargetPosition() {
        return this.mTransitionGoalPosition;
    }

    public final MotionScene.Transition getTransition(int i) {
        return this.mScene.getTransitionById(i);
    }

    public Bundle getTransitionState() {
        if (this.mStateCache == null) {
            this.mStateCache = new StateCache();
        }
        StateCache stateCache = this.mStateCache;
        MotionLayout motionLayout = MotionLayout.this;
        stateCache.mEndState = motionLayout.mEndState;
        stateCache.mStartState = motionLayout.mBeginState;
        stateCache.mVelocity = motionLayout.getVelocity();
        stateCache.mProgress = motionLayout.getProgress();
        StateCache stateCache2 = this.mStateCache;
        stateCache2.getClass();
        Bundle bundle = new Bundle();
        bundle.putFloat("motion.progress", stateCache2.mProgress);
        bundle.putFloat("motion.velocity", stateCache2.mVelocity);
        bundle.putInt("motion.StartState", stateCache2.mStartState);
        bundle.putInt("motion.EndState", stateCache2.mEndState);
        return bundle;
    }

    public long getTransitionTimeMs() {
        if (this.mScene != null) {
            this.mTransitionDuration = r0.getDuration() / 1000.0f;
        }
        return (long) (this.mTransitionDuration * 1000.0f);
    }

    public float getVelocity() {
        return this.mLastVelocity;
    }

    public final void getViewVelocity(View view, float f, float f2, float[] fArr, int i) {
        float f3;
        float f4 = this.mLastVelocity;
        float f5 = this.mTransitionLastPosition;
        if (this.mInterpolator != null) {
            float signum = Math.signum(this.mTransitionGoalPosition - f5);
            float interpolation = this.mInterpolator.getInterpolation(this.mTransitionLastPosition + 1.0E-5f);
            f3 = this.mInterpolator.getInterpolation(this.mTransitionLastPosition);
            f4 = (((interpolation - f3) / 1.0E-5f) * signum) / this.mTransitionDuration;
        } else {
            f3 = f5;
        }
        MotionInterpolator motionInterpolator = this.mInterpolator;
        if (motionInterpolator instanceof MotionInterpolator) {
            f4 = motionInterpolator.getVelocity();
        }
        MotionController motionController = this.mFrameArrayList.get(view);
        if ((i & 1) == 0) {
            motionController.getPostLayoutDvDp(f3, view.getWidth(), view.getHeight(), f, f2, fArr);
        } else {
            motionController.getDpDt(f3, f, f2, fArr);
        }
        if (i < 2) {
            fArr[0] = fArr[0] * f4;
            fArr[1] = fArr[1] * f4;
        }
    }

    @Override // android.view.View
    public final boolean isAttachedToWindow() {
        return super.isAttachedToWindow();
    }

    public final boolean isInteractionEnabled() {
        return this.mInteractionEnabled;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected final void onAttachedToWindow() {
        MotionScene.Transition transition;
        int i;
        super.onAttachedToWindow();
        Display display = getDisplay();
        if (display != null) {
            display.getRotation();
        }
        MotionScene motionScene = this.mScene;
        if (motionScene != null && (i = this.mCurrentState) != -1) {
            ConstraintSet constraintSet = motionScene.getConstraintSet(i);
            this.mScene.readFallback(this);
            ArrayList<MotionHelper> arrayList = this.mDecoratorsHelpers;
            if (arrayList != null) {
                Iterator<MotionHelper> it = arrayList.iterator();
                while (it.hasNext()) {
                    it.next().getClass();
                }
            }
            if (constraintSet != null) {
                constraintSet.applyTo(this);
            }
            this.mBeginState = this.mCurrentState;
        }
        onNewStateAttachHandlers();
        StateCache stateCache = this.mStateCache;
        if (stateCache != null) {
            if (this.mDelayedApply) {
                post(new Runnable() { // from class: androidx.constraintlayout.motion.widget.MotionLayout.4
                    @Override // java.lang.Runnable
                    public final void run() {
                        MotionLayout.this.mStateCache.apply();
                    }
                });
                return;
            } else {
                stateCache.apply();
                return;
            }
        }
        MotionScene motionScene2 = this.mScene;
        if (motionScene2 == null || (transition = motionScene2.mCurrentTransition) == null || transition.getAutoTransition() != 4) {
            return;
        }
        transitionToEnd();
        setState(TransitionState.SETUP);
        setState(TransitionState.MOVING);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        TouchResponse touchResponse;
        int touchRegionId;
        RectF touchRegion;
        MotionScene motionScene = this.mScene;
        if (motionScene != null && this.mInteractionEnabled) {
            ViewTransitionController viewTransitionController = motionScene.mViewTransitionController;
            if (viewTransitionController != null) {
                viewTransitionController.touchEvent(motionEvent);
            }
            MotionScene.Transition transition = this.mScene.mCurrentTransition;
            if (transition != null && transition.isEnabled() && (touchResponse = transition.getTouchResponse()) != null && ((motionEvent.getAction() != 0 || (touchRegion = touchResponse.getTouchRegion(this, new RectF())) == null || touchRegion.contains(motionEvent.getX(), motionEvent.getY())) && (touchRegionId = touchResponse.getTouchRegionId()) != -1)) {
                View view = this.mRegionView;
                if (view == null || view.getId() != touchRegionId) {
                    this.mRegionView = findViewById(touchRegionId);
                }
                if (this.mRegionView != null) {
                    this.mBoundsCheck.set(r0.getLeft(), this.mRegionView.getTop(), this.mRegionView.getRight(), this.mRegionView.getBottom());
                    if (this.mBoundsCheck.contains(motionEvent.getX(), motionEvent.getY()) && !handlesTouchEvent(this.mRegionView.getLeft(), this.mRegionView.getTop(), motionEvent, this.mRegionView)) {
                        return onTouchEvent(motionEvent);
                    }
                }
            }
        }
        return false;
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mInLayout = true;
        try {
            if (this.mScene == null) {
                super.onLayout(z, i, i2, i3, i4);
                return;
            }
            int i5 = i3 - i;
            int i6 = i4 - i2;
            if (this.mLastLayoutWidth != i5 || this.mLastLayoutHeight != i6) {
                rebuildScene();
                evaluate(true);
            }
            this.mLastLayoutWidth = i5;
            this.mLastLayoutHeight = i6;
        } finally {
            this.mInLayout = false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0055, code lost:
    
        if (((r6 == r9.mStartId && r7 == r9.mEndId) ? false : true) != false) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:70:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00f9  */
    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected final void onMeasure(int i, int i2) {
        boolean z;
        int i3;
        int i4;
        float signum;
        long nanoTime;
        MotionInterpolator motionInterpolator;
        float f;
        int childCount;
        Interpolator interpolator;
        if (this.mScene == null) {
            super.onMeasure(i, i2);
            return;
        }
        boolean z2 = true;
        boolean z3 = (this.mLastWidthMeasureSpec == i && this.mLastHeightMeasureSpec == i2) ? false : true;
        if (this.mNeedsFireTransitionCompleted) {
            this.mNeedsFireTransitionCompleted = false;
            onNewStateAttachHandlers();
            processTransitionCompleted();
            z3 = true;
        }
        if (this.mDirtyHierarchy) {
            z3 = true;
        }
        this.mLastWidthMeasureSpec = i;
        this.mLastHeightMeasureSpec = i2;
        int startId = this.mScene.getStartId();
        MotionScene.Transition transition = this.mScene.mCurrentTransition;
        int i5 = transition == null ? -1 : transition.mConstraintSetEnd;
        if (!z3) {
            Model model = this.mModel;
        }
        if (this.mBeginState != -1) {
            super.onMeasure(i, i2);
            this.mModel.initFrom(this.mScene.getConstraintSet(startId), this.mScene.getConstraintSet(i5));
            this.mModel.reEvaluateState();
            Model model2 = this.mModel;
            model2.mStartId = startId;
            model2.mEndId = i5;
            z = false;
            if (!this.mMeasureDuringTransition || z) {
                int paddingBottom = getPaddingBottom() + getPaddingTop();
                int width = this.mLayoutWidget.getWidth() + getPaddingRight() + getPaddingLeft();
                int height = this.mLayoutWidget.getHeight() + paddingBottom;
                i3 = this.mWidthMeasureMode;
                if (i3 != Integer.MIN_VALUE || i3 == 0) {
                    width = (int) ((this.mPostInterpolationPosition * (this.mEndWrapWidth - r1)) + this.mStartWrapWidth);
                    requestLayout();
                }
                i4 = this.mHeightMeasureMode;
                if (i4 != Integer.MIN_VALUE || i4 == 0) {
                    height = (int) ((this.mPostInterpolationPosition * (this.mEndWrapHeight - r2)) + this.mStartWrapHeight);
                    requestLayout();
                }
                setMeasuredDimension(width, height);
            }
            signum = Math.signum(this.mTransitionGoalPosition - this.mTransitionLastPosition);
            nanoTime = getNanoTime();
            motionInterpolator = this.mInterpolator;
            f = this.mTransitionLastPosition + (motionInterpolator instanceof StopLogic ? (((nanoTime - this.mTransitionLastTime) * signum) * 1.0E-9f) / this.mTransitionDuration : 0.0f);
            if (this.mTransitionInstantly) {
                f = this.mTransitionGoalPosition;
            }
            if ((signum > 0.0f || f < this.mTransitionGoalPosition) && (signum > 0.0f || f > this.mTransitionGoalPosition)) {
                z2 = false;
            } else {
                f = this.mTransitionGoalPosition;
            }
            if (motionInterpolator != null && !z2) {
                f = !this.mTemporalInterpolator ? motionInterpolator.getInterpolation((nanoTime - this.mAnimationStartTime) * 1.0E-9f) : motionInterpolator.getInterpolation(f);
            }
            if ((signum > 0.0f && f >= this.mTransitionGoalPosition) || (signum <= 0.0f && f <= this.mTransitionGoalPosition)) {
                f = this.mTransitionGoalPosition;
            }
            this.mPostInterpolationPosition = f;
            childCount = getChildCount();
            long nanoTime2 = getNanoTime();
            interpolator = this.mProgressInterpolator;
            if (interpolator != null) {
                f = interpolator.getInterpolation(f);
            }
            for (int i6 = 0; i6 < childCount; i6++) {
                View childAt = getChildAt(i6);
                MotionController motionController = this.mFrameArrayList.get(childAt);
                if (motionController != null) {
                    motionController.interpolate(f, nanoTime2, childAt, this.mKeyCache);
                }
            }
            if (this.mMeasureDuringTransition) {
                requestLayout();
                return;
            }
            return;
        }
        if (z3) {
            super.onMeasure(i, i2);
        }
        z = true;
        if (!this.mMeasureDuringTransition) {
        }
        int paddingBottom2 = getPaddingBottom() + getPaddingTop();
        int width2 = this.mLayoutWidget.getWidth() + getPaddingRight() + getPaddingLeft();
        int height2 = this.mLayoutWidget.getHeight() + paddingBottom2;
        i3 = this.mWidthMeasureMode;
        if (i3 != Integer.MIN_VALUE) {
        }
        width2 = (int) ((this.mPostInterpolationPosition * (this.mEndWrapWidth - r1)) + this.mStartWrapWidth);
        requestLayout();
        i4 = this.mHeightMeasureMode;
        if (i4 != Integer.MIN_VALUE) {
        }
        height2 = (int) ((this.mPostInterpolationPosition * (this.mEndWrapHeight - r2)) + this.mStartWrapHeight);
        requestLayout();
        setMeasuredDimension(width2, height2);
        signum = Math.signum(this.mTransitionGoalPosition - this.mTransitionLastPosition);
        nanoTime = getNanoTime();
        motionInterpolator = this.mInterpolator;
        f = this.mTransitionLastPosition + (motionInterpolator instanceof StopLogic ? (((nanoTime - this.mTransitionLastTime) * signum) * 1.0E-9f) / this.mTransitionDuration : 0.0f);
        if (this.mTransitionInstantly) {
        }
        if (signum > 0.0f) {
        }
        z2 = false;
        if (motionInterpolator != null) {
            if (!this.mTemporalInterpolator) {
            }
        }
        if (signum > 0.0f) {
            f = this.mTransitionGoalPosition;
            this.mPostInterpolationPosition = f;
            childCount = getChildCount();
            long nanoTime22 = getNanoTime();
            interpolator = this.mProgressInterpolator;
            if (interpolator != null) {
            }
            while (i6 < childCount) {
            }
            if (this.mMeasureDuringTransition) {
            }
        }
        f = this.mTransitionGoalPosition;
        this.mPostInterpolationPosition = f;
        childCount = getChildCount();
        long nanoTime222 = getNanoTime();
        interpolator = this.mProgressInterpolator;
        if (interpolator != null) {
        }
        while (i6 < childCount) {
        }
        if (this.mMeasureDuringTransition) {
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedFling(View view, float f, float f2, boolean z) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedPreScroll(final View view, int i, int i2, int[] iArr, int i3) {
        MotionScene.Transition transition;
        TouchResponse touchResponse;
        int touchRegionId;
        MotionScene motionScene = this.mScene;
        if (motionScene == null || (transition = motionScene.mCurrentTransition) == null || !transition.isEnabled()) {
            return;
        }
        int i4 = -1;
        if (!transition.isEnabled() || (touchResponse = transition.getTouchResponse()) == null || (touchRegionId = touchResponse.getTouchRegionId()) == -1 || view.getId() == touchRegionId) {
            MotionScene.Transition transition2 = motionScene.mCurrentTransition;
            if ((transition2 == null || transition2.mTouchResponse == null) ? false : motionScene.mCurrentTransition.mTouchResponse.getMoveWhenScrollAtTop()) {
                TouchResponse touchResponse2 = transition.getTouchResponse();
                if (touchResponse2 != null && (touchResponse2.getFlags() & 4) != 0) {
                    i4 = i2;
                }
                float f = this.mTransitionPosition;
                if ((f == 1.0f || f == 0.0f) && view.canScrollVertically(i4)) {
                    return;
                }
            }
            if (transition.getTouchResponse() != null && (transition.getTouchResponse().getFlags() & 1) != 0) {
                float f2 = i;
                float f3 = i2;
                MotionScene.Transition transition3 = motionScene.mCurrentTransition;
                float progressDirection = (transition3 == null || transition3.mTouchResponse == null) ? 0.0f : motionScene.mCurrentTransition.mTouchResponse.getProgressDirection(f2, f3);
                float f4 = this.mTransitionLastPosition;
                if ((f4 <= 0.0f && progressDirection < 0.0f) || (f4 >= 1.0f && progressDirection > 0.0f)) {
                    view.setNestedScrollingEnabled(false);
                    view.post(new Runnable() { // from class: androidx.constraintlayout.motion.widget.MotionLayout.3
                        @Override // java.lang.Runnable
                        public final void run() {
                            view.setNestedScrollingEnabled(true);
                        }
                    });
                    return;
                }
            }
            float f5 = this.mTransitionPosition;
            long nanoTime = getNanoTime();
            float f6 = i;
            this.mScrollTargetDX = f6;
            float f7 = i2;
            this.mScrollTargetDY = f7;
            this.mScrollTargetDT = (float) ((nanoTime - this.mScrollTargetTime) * 1.0E-9d);
            this.mScrollTargetTime = nanoTime;
            MotionScene.Transition transition4 = motionScene.mCurrentTransition;
            if (transition4 != null && transition4.mTouchResponse != null) {
                motionScene.mCurrentTransition.mTouchResponse.scrollMove(f6, f7);
            }
            if (f5 != this.mTransitionPosition) {
                iArr[0] = i;
                iArr[1] = i2;
            }
            evaluate(false);
            if (iArr[0] == 0 && iArr[1] == 0) {
                return;
            }
            this.mUndergoingMotion = true;
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        this.mScrollTargetTime = getNanoTime();
        this.mScrollTargetDT = 0.0f;
        this.mScrollTargetDX = 0.0f;
        this.mScrollTargetDY = 0.0f;
    }

    final void onNewStateAttachHandlers() {
        MotionScene motionScene;
        MotionScene.Transition transition;
        MotionScene motionScene2 = this.mScene;
        if (motionScene2 == null) {
            return;
        }
        if (motionScene2.autoTransition(this.mCurrentState, this)) {
            requestLayout();
            return;
        }
        int i = this.mCurrentState;
        if (i != -1) {
            this.mScene.addOnClickListeners(i, this);
        }
        if (!this.mScene.supportTouch() || (transition = (motionScene = this.mScene).mCurrentTransition) == null || transition.mTouchResponse == null) {
            return;
        }
        motionScene.mCurrentTransition.mTouchResponse.setupTouch();
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        MotionScene motionScene = this.mScene;
        if (motionScene != null) {
            motionScene.setRtl(isRtl());
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        MotionScene.Transition transition;
        MotionScene motionScene = this.mScene;
        return (motionScene == null || (transition = motionScene.mCurrentTransition) == null || transition.getTouchResponse() == null || (this.mScene.mCurrentTransition.getTouchResponse().getFlags() & 2) != 0) ? false : true;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onStopNestedScroll(View view, int i) {
        MotionScene motionScene = this.mScene;
        if (motionScene != null) {
            float f = this.mScrollTargetDT;
            if (f == 0.0f) {
                return;
            }
            float f2 = this.mScrollTargetDX / f;
            float f3 = this.mScrollTargetDY / f;
            MotionScene.Transition transition = motionScene.mCurrentTransition;
            if (transition == null || transition.mTouchResponse == null) {
                return;
            }
            motionScene.mCurrentTransition.mTouchResponse.scrollUp(f2, f3);
        }
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        MotionScene motionScene = this.mScene;
        if (motionScene == null || !this.mInteractionEnabled || !motionScene.supportTouch()) {
            return super.onTouchEvent(motionEvent);
        }
        MotionScene.Transition transition = this.mScene.mCurrentTransition;
        if (transition != null && !transition.isEnabled()) {
            return super.onTouchEvent(motionEvent);
        }
        this.mScene.processTouchEvent(motionEvent, getCurrentState(), this);
        if (this.mScene.mCurrentTransition.isTransitionFlag(4)) {
            return this.mScene.mCurrentTransition.getTouchResponse().isDragStarted();
        }
        return true;
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public final void onViewAdded(View view) {
        super.onViewAdded(view);
        if (view instanceof MotionHelper) {
            MotionHelper motionHelper = (MotionHelper) view;
            if (this.mTransitionListeners == null) {
                this.mTransitionListeners = new CopyOnWriteArrayList<>();
            }
            this.mTransitionListeners.add(motionHelper);
            if (motionHelper.isUsedOnShow()) {
                if (this.mOnShowHelpers == null) {
                    this.mOnShowHelpers = new ArrayList<>();
                }
                this.mOnShowHelpers.add(motionHelper);
            }
            if (motionHelper.isUseOnHide()) {
                if (this.mOnHideHelpers == null) {
                    this.mOnHideHelpers = new ArrayList<>();
                }
                this.mOnHideHelpers.add(motionHelper);
            }
            if (motionHelper instanceof MotionEffect) {
                if (this.mDecoratorsHelpers == null) {
                    this.mDecoratorsHelpers = new ArrayList<>();
                }
                this.mDecoratorsHelpers.add(motionHelper);
            }
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        ArrayList<MotionHelper> arrayList = this.mOnShowHelpers;
        if (arrayList != null) {
            arrayList.remove(view);
        }
        ArrayList<MotionHelper> arrayList2 = this.mOnHideHelpers;
        if (arrayList2 != null) {
            arrayList2.remove(view);
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout
    protected final void parseLayoutDescription(int i) {
        this.mConstraintLayoutSpec = null;
    }

    public final void rebuildScene() {
        this.mModel.reEvaluateState();
        invalidate();
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View, android.view.ViewParent
    public final void requestLayout() {
        MotionScene motionScene;
        MotionScene.Transition transition;
        if (!this.mMeasureDuringTransition && this.mCurrentState == -1 && (motionScene = this.mScene) != null && (transition = motionScene.mCurrentTransition) != null) {
            int layoutDuringTransition = transition.getLayoutDuringTransition();
            if (layoutDuringTransition == 0) {
                return;
            }
            if (layoutDuringTransition == 2) {
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    this.mFrameArrayList.get(getChildAt(i)).mForceMeasure = true;
                }
                return;
            }
        }
        super.requestLayout();
    }

    public void setDebugMode(int i) {
        this.mDebugPath = i;
        invalidate();
    }

    public void setDelayedApplicationOfInitialState(boolean z) {
        this.mDelayedApply = z;
    }

    public void setInteractionEnabled(boolean z) {
        this.mInteractionEnabled = z;
    }

    public void setInterpolatedProgress(float f) {
        if (this.mScene != null) {
            setState(TransitionState.MOVING);
            Interpolator interpolator = this.mScene.getInterpolator();
            if (interpolator != null) {
                setProgress(interpolator.getInterpolation(f));
                return;
            }
        }
        setProgress(f);
    }

    public void setOnHide(float f) {
        ArrayList<MotionHelper> arrayList = this.mOnHideHelpers;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mOnHideHelpers.get(i).setProgress(f);
            }
        }
    }

    public void setOnShow(float f) {
        ArrayList<MotionHelper> arrayList = this.mOnShowHelpers;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mOnShowHelpers.get(i).setProgress(f);
            }
        }
    }

    public final void setProgress(float f, float f2) {
        if (!isAttachedToWindow()) {
            if (this.mStateCache == null) {
                this.mStateCache = new StateCache();
            }
            StateCache stateCache = this.mStateCache;
            stateCache.mProgress = f;
            stateCache.mVelocity = f2;
            return;
        }
        setProgress(f);
        setState(TransitionState.MOVING);
        this.mLastVelocity = f2;
        if (f2 != 0.0f) {
            animateTo(f2 > 0.0f ? 1.0f : 0.0f);
        } else {
            if (f == 0.0f || f == 1.0f) {
                return;
            }
            animateTo(f > 0.5f ? 1.0f : 0.0f);
        }
    }

    public void setScene(MotionScene motionScene) {
        this.mScene = motionScene;
        motionScene.setRtl(isRtl());
        rebuildScene();
    }

    void setStartState(int i) {
        if (isAttachedToWindow()) {
            this.mCurrentState = i;
            return;
        }
        if (this.mStateCache == null) {
            this.mStateCache = new StateCache();
        }
        StateCache stateCache = this.mStateCache;
        stateCache.mStartState = i;
        stateCache.mEndState = i;
    }

    void setState(TransitionState transitionState) {
        TransitionState transitionState2 = TransitionState.FINISHED;
        if (transitionState == transitionState2 && this.mCurrentState == -1) {
            return;
        }
        TransitionState transitionState3 = this.mTransitionState;
        this.mTransitionState = transitionState;
        TransitionState transitionState4 = TransitionState.MOVING;
        if (transitionState3 == transitionState4 && transitionState == transitionState4) {
            fireTransitionChange();
        }
        int ordinal = transitionState3.ordinal();
        if (ordinal != 0 && ordinal != 1) {
            if (ordinal == 2 && transitionState == transitionState2) {
                fireTransitionCompleted();
                return;
            }
            return;
        }
        if (transitionState == transitionState4) {
            fireTransitionChange();
        }
        if (transitionState == transitionState2) {
            fireTransitionCompleted();
        }
    }

    public final void setTransition(int i, int i2) {
        if (!isAttachedToWindow()) {
            if (this.mStateCache == null) {
                this.mStateCache = new StateCache();
            }
            StateCache stateCache = this.mStateCache;
            stateCache.mStartState = i;
            stateCache.mEndState = i2;
            return;
        }
        MotionScene motionScene = this.mScene;
        if (motionScene != null) {
            this.mBeginState = i;
            this.mEndState = i2;
            motionScene.setTransition(i, i2);
            this.mModel.initFrom(this.mScene.getConstraintSet(i), this.mScene.getConstraintSet(i2));
            rebuildScene();
            this.mTransitionLastPosition = 0.0f;
            animateTo(0.0f);
        }
    }

    public void setTransitionDuration(int i) {
        MotionScene motionScene = this.mScene;
        if (motionScene == null) {
            Log.e("MotionLayout", "MotionScene not defined");
        } else {
            motionScene.setDuration(i);
        }
    }

    public void setTransitionListener(TransitionListener transitionListener) {
        this.mTransitionListener = transitionListener;
    }

    public void setTransitionState(Bundle bundle) {
        if (this.mStateCache == null) {
            this.mStateCache = new StateCache();
        }
        StateCache stateCache = this.mStateCache;
        stateCache.getClass();
        stateCache.mProgress = bundle.getFloat("motion.progress");
        stateCache.mVelocity = bundle.getFloat("motion.velocity");
        stateCache.mStartState = bundle.getInt("motion.StartState");
        stateCache.mEndState = bundle.getInt("motion.EndState");
        if (isAttachedToWindow()) {
            this.mStateCache.apply();
        }
    }

    @Override // android.view.View
    public final String toString() {
        Context context = getContext();
        return Debug.getName(context, this.mBeginState) + "->" + Debug.getName(context, this.mEndState) + " (pos:" + this.mTransitionLastPosition + " Dpos/Dt:" + this.mLastVelocity;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x003a, code lost:
    
        if (r12 != 7) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0057, code lost:
    
        if ((((r14 * r5) - (((r4 * r5) * r5) / 2.0f)) + r0) > 1.0f) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0068, code lost:
    
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0069, code lost:
    
        if (r1 == false) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x006b, code lost:
    
        r0 = r11.mDecelerateLogic;
        r1 = r11.mTransitionLastPosition;
        r2 = r11.mScene.getMaxAcceleration();
        r0.mInitialV = r14;
        r0.mCurrentP = r1;
        r0.mMaxA = r2;
        r11.mInterpolator = r11.mDecelerateLogic;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0081, code lost:
    
        r0 = r11.mStopLogic;
        r1 = r11.mTransitionLastPosition;
        r4 = r11.mTransitionDuration;
        r5 = r11.mScene.getMaxAcceleration();
        r2 = r11.mScene;
        r6 = r2.mCurrentTransition;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0091, code lost:
    
        if (r6 == null) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0097, code lost:
    
        if (r6.mTouchResponse == null) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0099, code lost:
    
        r6 = r2.mCurrentTransition.mTouchResponse.getMaxVelocity();
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a6, code lost:
    
        r0.config(r1, r13, r14, r4, r5, r6);
        r11.mLastVelocity = 0.0f;
        r0 = r11.mCurrentState;
        r11.mTransitionGoalPosition = r13;
        r11.mCurrentState = r0;
        r11.mInterpolator = r11.mStopLogic;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00a5, code lost:
    
        r6 = 0.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0065, code lost:
    
        if ((((((r4 * r2) * r2) / 2.0f) + (r14 * r2)) + r0) < 0.0f) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void touchAnimateTo(int i, float f, float f2) {
        if (this.mScene == null || this.mTransitionLastPosition == f) {
            return;
        }
        boolean z = true;
        this.mTemporalInterpolator = true;
        this.mAnimationStartTime = getNanoTime();
        this.mTransitionDuration = this.mScene.getDuration() / 1000.0f;
        this.mTransitionGoalPosition = f;
        this.mInTransition = true;
        if (i != 0 && i != 1 && i != 2) {
            if (i == 4) {
                DecelerateInterpolator decelerateInterpolator = this.mDecelerateLogic;
                float f3 = this.mTransitionLastPosition;
                float maxAcceleration = this.mScene.getMaxAcceleration();
                decelerateInterpolator.mInitialV = f2;
                decelerateInterpolator.mCurrentP = f3;
                decelerateInterpolator.mMaxA = maxAcceleration;
                this.mInterpolator = this.mDecelerateLogic;
            } else if (i == 5) {
                float f4 = this.mTransitionLastPosition;
                float maxAcceleration2 = this.mScene.getMaxAcceleration();
                if (f2 > 0.0f) {
                    float f5 = f2 / maxAcceleration2;
                } else {
                    float f6 = (-f2) / maxAcceleration2;
                }
            } else if (i != 6) {
            }
            this.mTransitionInstantly = false;
            this.mAnimationStartTime = getNanoTime();
            invalidate();
        }
        float f7 = (i == 1 || i == 7) ? 0.0f : (i == 2 || i == 6) ? 1.0f : f;
        MotionScene motionScene = this.mScene;
        MotionScene.Transition transition = motionScene.mCurrentTransition;
        if (((transition == null || transition.mTouchResponse == null) ? 0 : motionScene.mCurrentTransition.mTouchResponse.getAutoCompleteMode()) == 0) {
            StopLogic stopLogic = this.mStopLogic;
            float f8 = this.mTransitionLastPosition;
            float f9 = this.mTransitionDuration;
            float maxAcceleration3 = this.mScene.getMaxAcceleration();
            MotionScene motionScene2 = this.mScene;
            MotionScene.Transition transition2 = motionScene2.mCurrentTransition;
            stopLogic.config(f8, f7, f2, f9, maxAcceleration3, (transition2 == null || transition2.mTouchResponse == null) ? 0.0f : motionScene2.mCurrentTransition.mTouchResponse.getMaxVelocity());
        } else {
            StopLogic stopLogic2 = this.mStopLogic;
            float f10 = this.mTransitionLastPosition;
            MotionScene motionScene3 = this.mScene;
            MotionScene.Transition transition3 = motionScene3.mCurrentTransition;
            float springMass = (transition3 == null || transition3.mTouchResponse == null) ? 0.0f : motionScene3.mCurrentTransition.mTouchResponse.getSpringMass();
            MotionScene motionScene4 = this.mScene;
            MotionScene.Transition transition4 = motionScene4.mCurrentTransition;
            float springStiffness = (transition4 == null || transition4.mTouchResponse == null) ? 0.0f : motionScene4.mCurrentTransition.mTouchResponse.getSpringStiffness();
            MotionScene motionScene5 = this.mScene;
            MotionScene.Transition transition5 = motionScene5.mCurrentTransition;
            float springDamping = (transition5 == null || transition5.mTouchResponse == null) ? 0.0f : motionScene5.mCurrentTransition.mTouchResponse.getSpringDamping();
            MotionScene motionScene6 = this.mScene;
            MotionScene.Transition transition6 = motionScene6.mCurrentTransition;
            float springStopThreshold = (transition6 == null || transition6.mTouchResponse == null) ? 0.0f : motionScene6.mCurrentTransition.mTouchResponse.getSpringStopThreshold();
            MotionScene motionScene7 = this.mScene;
            MotionScene.Transition transition7 = motionScene7.mCurrentTransition;
            stopLogic2.springConfig(f10, f7, f2, springMass, springStiffness, springDamping, springStopThreshold, (transition7 == null || transition7.mTouchResponse == null) ? 0 : motionScene7.mCurrentTransition.mTouchResponse.getSpringBoundary());
        }
        int i2 = this.mCurrentState;
        this.mTransitionGoalPosition = f7;
        this.mCurrentState = i2;
        this.mInterpolator = this.mStopLogic;
        this.mTransitionInstantly = false;
        this.mAnimationStartTime = getNanoTime();
        invalidate();
    }

    public final void transitionToEnd() {
        animateTo(1.0f);
        this.mOnComplete = null;
    }

    public final void transitionToState(int i) {
        if (isAttachedToWindow()) {
            transitionToState$1(i, -1);
            return;
        }
        if (this.mStateCache == null) {
            this.mStateCache = new StateCache();
        }
        this.mStateCache.mEndState = i;
    }

    public final void transitionToState$1(int i, int i2) {
        StateSet stateSet;
        float f;
        int convertToConstraintSet;
        MotionScene motionScene = this.mScene;
        if (motionScene != null && (stateSet = motionScene.mStateSet) != null && (convertToConstraintSet = stateSet.convertToConstraintSet(-1, f, this.mCurrentState, i)) != -1) {
            i = convertToConstraintSet;
        }
        int i3 = this.mCurrentState;
        if (i3 == i) {
            return;
        }
        if (this.mBeginState == i) {
            animateTo(0.0f);
            if (i2 > 0) {
                this.mTransitionDuration = i2 / 1000.0f;
                return;
            }
            return;
        }
        if (this.mEndState == i) {
            animateTo(1.0f);
            if (i2 > 0) {
                this.mTransitionDuration = i2 / 1000.0f;
                return;
            }
            return;
        }
        this.mEndState = i;
        if (i3 != -1) {
            setTransition(i3, i);
            animateTo(1.0f);
            this.mTransitionLastPosition = 0.0f;
            transitionToEnd();
            if (i2 > 0) {
                this.mTransitionDuration = i2 / 1000.0f;
                return;
            }
            return;
        }
        this.mTemporalInterpolator = false;
        this.mTransitionGoalPosition = 1.0f;
        this.mTransitionPosition = 0.0f;
        this.mTransitionLastPosition = 0.0f;
        this.mTransitionLastTime = getNanoTime();
        this.mAnimationStartTime = getNanoTime();
        this.mTransitionInstantly = false;
        this.mInterpolator = null;
        if (i2 == -1) {
            this.mTransitionDuration = this.mScene.getDuration() / 1000.0f;
        }
        this.mBeginState = -1;
        this.mScene.setTransition(-1, this.mEndState);
        SparseArray sparseArray = new SparseArray();
        if (i2 == 0) {
            this.mTransitionDuration = this.mScene.getDuration() / 1000.0f;
        } else if (i2 > 0) {
            this.mTransitionDuration = i2 / 1000.0f;
        }
        int childCount = getChildCount();
        this.mFrameArrayList.clear();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            this.mFrameArrayList.put(childAt, new MotionController(childAt));
            sparseArray.put(childAt.getId(), this.mFrameArrayList.get(childAt));
        }
        this.mInTransition = true;
        this.mModel.initFrom(null, this.mScene.getConstraintSet(i));
        rebuildScene();
        this.mModel.build();
        int childCount2 = getChildCount();
        for (int i5 = 0; i5 < childCount2; i5++) {
            View childAt2 = getChildAt(i5);
            MotionController motionController = this.mFrameArrayList.get(childAt2);
            if (motionController != null) {
                motionController.setStartCurrentState(childAt2);
            }
        }
        int width = getWidth();
        int height = getHeight();
        if (this.mDecoratorsHelpers != null) {
            for (int i6 = 0; i6 < childCount; i6++) {
                MotionController motionController2 = this.mFrameArrayList.get(getChildAt(i6));
                if (motionController2 != null) {
                    this.mScene.getKeyFrames(motionController2);
                }
            }
            Iterator<MotionHelper> it = this.mDecoratorsHelpers.iterator();
            while (it.hasNext()) {
                it.next().onPreSetup(this, this.mFrameArrayList);
            }
            for (int i7 = 0; i7 < childCount; i7++) {
                MotionController motionController3 = this.mFrameArrayList.get(getChildAt(i7));
                if (motionController3 != null) {
                    motionController3.setup(width, height, getNanoTime());
                }
            }
        } else {
            for (int i8 = 0; i8 < childCount; i8++) {
                MotionController motionController4 = this.mFrameArrayList.get(getChildAt(i8));
                if (motionController4 != null) {
                    this.mScene.getKeyFrames(motionController4);
                    motionController4.setup(width, height, getNanoTime());
                }
            }
        }
        MotionScene.Transition transition = this.mScene.mCurrentTransition;
        float f2 = transition != null ? transition.mStagger : 0.0f;
        if (f2 != 0.0f) {
            float f3 = Float.MAX_VALUE;
            float f4 = -3.4028235E38f;
            for (int i9 = 0; i9 < childCount; i9++) {
                MotionController motionController5 = this.mFrameArrayList.get(getChildAt(i9));
                float finalY = motionController5.getFinalY() + motionController5.getFinalX();
                f3 = Math.min(f3, finalY);
                f4 = Math.max(f4, finalY);
            }
            for (int i10 = 0; i10 < childCount; i10++) {
                MotionController motionController6 = this.mFrameArrayList.get(getChildAt(i10));
                float finalX = motionController6.getFinalX();
                float finalY2 = motionController6.getFinalY();
                motionController6.mStaggerScale = 1.0f / (1.0f - f2);
                motionController6.mStaggerOffset = f2 - ((((finalX + finalY2) - f3) * f2) / (f4 - f3));
            }
        }
        this.mTransitionPosition = 0.0f;
        this.mTransitionLastPosition = 0.0f;
        this.mInTransition = true;
        invalidate();
    }

    public final void updateState(int i, ConstraintSet constraintSet) {
        MotionScene motionScene = this.mScene;
        if (motionScene != null) {
            motionScene.setConstraintSet(i, constraintSet);
        }
        this.mModel.initFrom(this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
        rebuildScene();
        if (this.mCurrentState == i) {
            constraintSet.applyTo(this);
        }
    }

    public final void viewTransition(int i, View... viewArr) {
        MotionScene motionScene = this.mScene;
        if (motionScene != null) {
            motionScene.mViewTransitionController.viewTransition(i, viewArr);
        } else {
            Log.e("MotionLayout", " no motionScene");
        }
    }

    @Override // androidx.core.view.NestedScrollingParent3
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        if (this.mUndergoingMotion || i != 0 || i2 != 0) {
            iArr[0] = iArr[0] + i3;
            iArr[1] = iArr[1] + i4;
        }
        this.mUndergoingMotion = false;
    }

    public final void transitionToEnd(ViewTransition$$ExternalSyntheticLambda0 viewTransition$$ExternalSyntheticLambda0) {
        animateTo(1.0f);
        this.mOnComplete = viewTransition$$ExternalSyntheticLambda0;
    }

    public final void setState(int i) {
        setState(TransitionState.SETUP);
        this.mCurrentState = i;
        this.mBeginState = -1;
        this.mEndState = -1;
        ConstraintLayoutStates constraintLayoutStates = this.mConstraintLayoutSpec;
        if (constraintLayoutStates != null) {
            float f = -1;
            constraintLayoutStates.updateConstraints(i, f, f);
        } else {
            MotionScene motionScene = this.mScene;
            if (motionScene != null) {
                motionScene.getConstraintSet(i).applyTo(this);
            }
        }
    }

    public void setProgress(float f) {
        if (f < 0.0f || f > 1.0f) {
            Log.w("MotionLayout", "Warning! Progress is defined for values between 0.0 and 1.0 inclusive");
        }
        if (!isAttachedToWindow()) {
            if (this.mStateCache == null) {
                this.mStateCache = new StateCache();
            }
            this.mStateCache.mProgress = f;
            return;
        }
        TransitionState transitionState = TransitionState.FINISHED;
        TransitionState transitionState2 = TransitionState.MOVING;
        if (f <= 0.0f) {
            if (this.mTransitionLastPosition == 1.0f && this.mCurrentState == this.mEndState) {
                setState(transitionState2);
            }
            this.mCurrentState = this.mBeginState;
            if (this.mTransitionLastPosition == 0.0f) {
                setState(transitionState);
            }
        } else if (f >= 1.0f) {
            if (this.mTransitionLastPosition == 0.0f && this.mCurrentState == this.mBeginState) {
                setState(transitionState2);
            }
            this.mCurrentState = this.mEndState;
            if (this.mTransitionLastPosition == 1.0f) {
                setState(transitionState);
            }
        } else {
            this.mCurrentState = -1;
            setState(transitionState2);
        }
        if (this.mScene == null) {
            return;
        }
        this.mTransitionInstantly = true;
        this.mTransitionGoalPosition = f;
        this.mTransitionPosition = f;
        this.mTransitionLastTime = -1L;
        this.mAnimationStartTime = -1L;
        this.mInterpolator = null;
        this.mInTransition = true;
        invalidate();
    }

    public void setTransition(int i) {
        float f;
        if (this.mScene != null) {
            MotionScene.Transition transition = getTransition(i);
            this.mBeginState = transition.getStartConstraintSetId();
            this.mEndState = transition.getEndConstraintSetId();
            if (!isAttachedToWindow()) {
                if (this.mStateCache == null) {
                    this.mStateCache = new StateCache();
                }
                StateCache stateCache = this.mStateCache;
                stateCache.mStartState = this.mBeginState;
                stateCache.mEndState = this.mEndState;
                return;
            }
            int i2 = this.mCurrentState;
            if (i2 == this.mBeginState) {
                f = 0.0f;
            } else {
                f = i2 == this.mEndState ? 1.0f : Float.NaN;
            }
            this.mScene.setTransition(transition);
            this.mModel.initFrom(this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
            rebuildScene();
            if (this.mTransitionLastPosition != f) {
                if (f == 0.0f) {
                    endTrigger(true);
                    this.mScene.getConstraintSet(this.mBeginState).applyTo(this);
                } else if (f == 1.0f) {
                    endTrigger(false);
                    this.mScene.getConstraintSet(this.mEndState).applyTo(this);
                }
            }
            this.mTransitionLastPosition = Float.isNaN(f) ? 0.0f : f;
            if (Float.isNaN(f)) {
                Log.v("MotionLayout", Debug.getLocation() + " transitionToStart ");
                animateTo(0.0f);
                return;
            }
            setProgress(f);
        }
    }

    protected void setTransition(MotionScene.Transition transition) {
        this.mScene.setTransition(transition);
        setState(TransitionState.SETUP);
        int i = this.mCurrentState;
        MotionScene.Transition transition2 = this.mScene.mCurrentTransition;
        if (i == (transition2 == null ? -1 : transition2.mConstraintSetEnd)) {
            this.mTransitionLastPosition = 1.0f;
            this.mTransitionPosition = 1.0f;
            this.mTransitionGoalPosition = 1.0f;
        } else {
            this.mTransitionLastPosition = 0.0f;
            this.mTransitionPosition = 0.0f;
            this.mTransitionGoalPosition = 0.0f;
        }
        this.mTransitionLastTime = transition.isTransitionFlag(1) ? -1L : getNanoTime();
        int startId = this.mScene.getStartId();
        MotionScene.Transition transition3 = this.mScene.mCurrentTransition;
        int i2 = transition3 != null ? transition3.mConstraintSetEnd : -1;
        if (startId == this.mBeginState && i2 == this.mEndState) {
            return;
        }
        this.mBeginState = startId;
        this.mEndState = i2;
        this.mScene.setTransition(startId, i2);
        this.mModel.initFrom(this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
        Model model = this.mModel;
        int i3 = this.mBeginState;
        int i4 = this.mEndState;
        model.mStartId = i3;
        model.mEndId = i4;
        model.reEvaluateState();
        rebuildScene();
    }

    public MotionLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mProgressInterpolator = null;
        this.mLastVelocity = 0.0f;
        this.mBeginState = -1;
        this.mCurrentState = -1;
        this.mEndState = -1;
        this.mLastWidthMeasureSpec = 0;
        this.mLastHeightMeasureSpec = 0;
        this.mInteractionEnabled = true;
        this.mFrameArrayList = new HashMap<>();
        this.mAnimationStartTime = 0L;
        this.mTransitionDuration = 1.0f;
        this.mTransitionPosition = 0.0f;
        this.mTransitionLastPosition = 0.0f;
        this.mTransitionGoalPosition = 0.0f;
        this.mInTransition = false;
        this.mDebugPath = 0;
        this.mTemporalInterpolator = false;
        this.mStopLogic = new StopLogic();
        this.mDecelerateLogic = new DecelerateInterpolator();
        this.mUndergoingMotion = false;
        this.mKeepAnimating = false;
        this.mOnShowHelpers = null;
        this.mOnHideHelpers = null;
        this.mDecoratorsHelpers = null;
        this.mTransitionListeners = null;
        this.mFrames = 0;
        this.mLastDrawTime = -1L;
        this.mLastFps = 0.0f;
        this.mListenerState = 0;
        this.mListenerPosition = 0.0f;
        this.mMeasureDuringTransition = false;
        this.mKeyCache = new KeyCache();
        this.mInLayout = false;
        this.mOnComplete = null;
        this.mPreRotate = new HashMap<>();
        this.mTempRect = new Rect();
        this.mDelayedApply = false;
        this.mTransitionState = TransitionState.UNDEFINED;
        this.mModel = new Model();
        this.mNeedsFireTransitionCompleted = false;
        this.mBoundsCheck = new RectF();
        this.mRegionView = null;
        this.mInverseMatrix = null;
        this.mTransitionCompleted = new ArrayList<>();
        init(attributeSet);
    }

    public MotionLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mProgressInterpolator = null;
        this.mLastVelocity = 0.0f;
        this.mBeginState = -1;
        this.mCurrentState = -1;
        this.mEndState = -1;
        this.mLastWidthMeasureSpec = 0;
        this.mLastHeightMeasureSpec = 0;
        this.mInteractionEnabled = true;
        this.mFrameArrayList = new HashMap<>();
        this.mAnimationStartTime = 0L;
        this.mTransitionDuration = 1.0f;
        this.mTransitionPosition = 0.0f;
        this.mTransitionLastPosition = 0.0f;
        this.mTransitionGoalPosition = 0.0f;
        this.mInTransition = false;
        this.mDebugPath = 0;
        this.mTemporalInterpolator = false;
        this.mStopLogic = new StopLogic();
        this.mDecelerateLogic = new DecelerateInterpolator();
        this.mUndergoingMotion = false;
        this.mKeepAnimating = false;
        this.mOnShowHelpers = null;
        this.mOnHideHelpers = null;
        this.mDecoratorsHelpers = null;
        this.mTransitionListeners = null;
        this.mFrames = 0;
        this.mLastDrawTime = -1L;
        this.mLastFps = 0.0f;
        this.mListenerState = 0;
        this.mListenerPosition = 0.0f;
        this.mMeasureDuringTransition = false;
        this.mKeyCache = new KeyCache();
        this.mInLayout = false;
        this.mOnComplete = null;
        this.mPreRotate = new HashMap<>();
        this.mTempRect = new Rect();
        this.mDelayedApply = false;
        this.mTransitionState = TransitionState.UNDEFINED;
        this.mModel = new Model();
        this.mNeedsFireTransitionCompleted = false;
        this.mBoundsCheck = new RectF();
        this.mRegionView = null;
        this.mInverseMatrix = null;
        this.mTransitionCompleted = new ArrayList<>();
        init(attributeSet);
    }
}
