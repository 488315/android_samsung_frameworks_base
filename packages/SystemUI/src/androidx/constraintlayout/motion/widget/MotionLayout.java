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
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.motion.utils.ArcCurveFit;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.VelocityMatrix;
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
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.motion.utils.StopLogic;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.utils.ViewState;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayoutStates;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.StateSet;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.widget.NestedScrollView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MotionLayout extends ConstraintLayout implements NestedScrollingParent3 {
    private static final boolean DEBUG = false;
    public static final int DEBUG_SHOW_NONE = 0;
    public static final int DEBUG_SHOW_PATH = 2;
    public static final int DEBUG_SHOW_PROGRESS = 1;
    private static final float EPSILON = 1.0E-5f;
    public static boolean IS_IN_EDIT_MODE = false;
    static final int MAX_KEY_FRAMES = 50;
    static final String TAG = "MotionLayout";
    public static final int TOUCH_UP_COMPLETE = 0;
    public static final int TOUCH_UP_COMPLETE_TO_END = 2;
    public static final int TOUCH_UP_COMPLETE_TO_START = 1;
    public static final int TOUCH_UP_DECELERATE = 4;
    public static final int TOUCH_UP_DECELERATE_AND_COMPLETE = 5;
    public static final int TOUCH_UP_NEVER_TO_END = 7;
    public static final int TOUCH_UP_NEVER_TO_START = 6;
    public static final int TOUCH_UP_STOP = 3;
    public static final int VELOCITY_LAYOUT = 1;
    public static final int VELOCITY_POST_LAYOUT = 0;
    public static final int VELOCITY_STATIC_LAYOUT = 3;
    public static final int VELOCITY_STATIC_POST_LAYOUT = 2;
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
    boolean mFirstDown;
    HashMap<View, MotionController> mFrameArrayList;
    private int mFrames;
    int mHeightMeasureMode;
    private boolean mInLayout;
    private boolean mInRotation;
    boolean mInTransition;
    boolean mIndirectTransition;
    private boolean mInteractionEnabled;
    Interpolator mInterpolator;
    private Matrix mInverseMatrix;
    boolean mIsAnimating;
    private boolean mKeepAnimating;
    private KeyCache mKeyCache;
    private long mLastDrawTime;
    private float mLastFps;
    private int mLastHeightMeasureSpec;
    int mLastLayoutHeight;
    int mLastLayoutWidth;
    private float mLastPos;
    float mLastVelocity;
    private int mLastWidthMeasureSpec;
    private float mLastY;
    private float mListenerPosition;
    private int mListenerState;
    protected boolean mMeasureDuringTransition;
    Model mModel;
    private boolean mNeedsFireTransitionCompleted;
    int mOldHeight;
    int mOldWidth;
    private Runnable mOnComplete;
    private ArrayList<MotionHelper> mOnHideHelpers;
    private ArrayList<MotionHelper> mOnShowHelpers;
    float mPostInterpolationPosition;
    HashMap<View, ViewState> mPreRotate;
    private int mPreRotateHeight;
    private int mPreRotateWidth;
    private int mPreviouseRotation;
    Interpolator mProgressInterpolator;
    private View mRegionView;
    int mRotatMode;
    MotionScene mScene;
    private int[] mScheduledTransitionTo;
    int mScheduledTransitions;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DecelerateInterpolator extends MotionInterpolator {
        public float mMaxA;
        public float mInitialV = 0.0f;
        public float mCurrentP = 0.0f;

        public DecelerateInterpolator() {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DevModeDraw {
        public final Paint mFillPaint;
        public int mKeyFrameCount;
        public final float[] mKeyFramePoints;
        public final Paint mPaint;
        public final Paint mPaintGraph;
        public final Paint mPaintKeyframes;
        public Path mPath;
        public final int[] mPathMode;
        public float[] mPoints;
        public final float[] mRectangle;
        public final Paint mTextPaint;
        public final Rect mBounds = new Rect();
        public final int mShadowTranslate = 1;

        public DevModeDraw() {
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setAntiAlias(true);
            paint.setColor(-21965);
            paint.setStrokeWidth(2.0f);
            Paint.Style style = Paint.Style.STROKE;
            paint.setStyle(style);
            Paint paint2 = new Paint();
            this.mPaintKeyframes = paint2;
            paint2.setAntiAlias(true);
            paint2.setColor(-2067046);
            paint2.setStrokeWidth(2.0f);
            paint2.setStyle(style);
            Paint paint3 = new Paint();
            this.mPaintGraph = paint3;
            paint3.setAntiAlias(true);
            paint3.setColor(-13391360);
            paint3.setStrokeWidth(2.0f);
            paint3.setStyle(style);
            Paint paint4 = new Paint();
            this.mTextPaint = paint4;
            paint4.setAntiAlias(true);
            paint4.setColor(-13391360);
            paint4.setTextSize(MotionLayout.this.getContext().getResources().getDisplayMetrics().density * 12.0f);
            this.mRectangle = new float[8];
            Paint paint5 = new Paint();
            this.mFillPaint = paint5;
            paint5.setAntiAlias(true);
            paint3.setPathEffect(new DashPathEffect(new float[]{4.0f, 8.0f}, 0.0f));
            this.mKeyFramePoints = new float[100];
            this.mPathMode = new int[50];
        }

        public final void drawAll(Canvas canvas, int i, int i2, MotionController motionController) {
            Canvas canvas2;
            int i3;
            int i4;
            boolean z;
            float f;
            float f2;
            int[] iArr = this.mPathMode;
            boolean z2 = false;
            if (i == 4) {
                int i5 = 0;
                boolean z3 = false;
                boolean z4 = false;
                while (i5 < this.mKeyFrameCount) {
                    int i6 = iArr[i5];
                    boolean z5 = z3;
                    if (i6 == 1) {
                        z5 = true;
                    }
                    if (i6 == 0) {
                        z4 = true;
                    }
                    i5++;
                    z3 = z5;
                    z4 = z4;
                }
                if (z3) {
                    float[] fArr = this.mPoints;
                    canvas.drawLine(fArr[0], fArr[1], fArr[fArr.length - 2], fArr[fArr.length - 1], this.mPaintGraph);
                }
                if (z4) {
                    drawPathCartesian(canvas);
                }
            }
            if (i == 2) {
                float[] fArr2 = this.mPoints;
                canvas2 = canvas;
                canvas2.drawLine(fArr2[0], fArr2[1], fArr2[fArr2.length - 2], fArr2[fArr2.length - 1], this.mPaintGraph);
            } else {
                canvas2 = canvas;
            }
            if (i == 3) {
                drawPathCartesian(canvas);
            }
            canvas2.drawLines(this.mPoints, this.mPaint);
            View view = motionController.mView;
            if (view != null) {
                i3 = view.getWidth();
                i4 = motionController.mView.getHeight();
            } else {
                i3 = 0;
                i4 = 0;
            }
            int i7 = 1;
            while (i7 < i2 - 1) {
                if (i == 4 && iArr[i7 - 1] == 0) {
                    z = z2;
                } else {
                    int i8 = i7 * 2;
                    float[] fArr3 = this.mKeyFramePoints;
                    float f3 = fArr3[i8];
                    float f4 = fArr3[i8 + 1];
                    this.mPath.reset();
                    z = z2;
                    this.mPath.moveTo(f3, f4 + 10.0f);
                    this.mPath.lineTo(f3 + 10.0f, f4);
                    this.mPath.lineTo(f3, f4 - 10.0f);
                    this.mPath.lineTo(f3 - 10.0f, f4);
                    this.mPath.close();
                    int i9 = i7 - 1;
                    if (i == 4) {
                        int i10 = iArr[i9];
                        if (i10 == 1) {
                            f = 0.0f;
                            drawPathRelativeTicks(canvas2, f3 - 0.0f, f4 - 0.0f);
                        } else {
                            f = 0.0f;
                            if (i10 == 0) {
                                drawPathCartesianTicks(canvas2, f3 - 0.0f, f4 - 0.0f);
                            } else if (i10 == 2) {
                                f2 = f4;
                                drawPathScreenTicks(canvas2, f3 - 0.0f, f2 - 0.0f, i3, i4);
                                canvas2.drawPath(this.mPath, this.mFillPaint);
                            }
                        }
                        f2 = f4;
                        canvas2.drawPath(this.mPath, this.mFillPaint);
                    } else {
                        f = 0.0f;
                        f2 = f4;
                    }
                    if (i == 2) {
                        drawPathRelativeTicks(canvas2, f3 - f, f2 - f);
                    }
                    if (i == 3) {
                        drawPathCartesianTicks(canvas2, f3 - f, f2 - f);
                    }
                    if (i == 6) {
                        drawPathScreenTicks(canvas2, f3 - f, f2 - f, i3, i4);
                    }
                    canvas2.drawPath(this.mPath, this.mFillPaint);
                }
                i7++;
                z2 = z;
            }
            boolean z6 = z2;
            float[] fArr4 = this.mPoints;
            if (fArr4.length > 1) {
                canvas2.drawCircle(fArr4[z6 ? 1 : 0], fArr4[1], 8.0f, this.mPaintKeyframes);
                float[] fArr5 = this.mPoints;
                canvas2.drawCircle(fArr5[fArr5.length - 2], fArr5[fArr5.length - 1], 8.0f, this.mPaintKeyframes);
            }
        }

        public final void drawPathCartesian(Canvas canvas) {
            float[] fArr = this.mPoints;
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[fArr.length - 2];
            float f4 = fArr[fArr.length - 1];
            canvas.drawLine(Math.min(f, f3), Math.max(f2, f4), Math.max(f, f3), Math.max(f2, f4), this.mPaintGraph);
            canvas.drawLine(Math.min(f, f3), Math.min(f2, f4), Math.min(f, f3), Math.max(f2, f4), this.mPaintGraph);
        }

        public final void drawPathCartesianTicks(Canvas canvas, float f, float f2) {
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
            this.mTextPaint.getTextBounds(str, 0, str.length(), this.mBounds);
            canvas.drawText(str, ((min2 / 2.0f) - (this.mBounds.width() / 2)) + min, f2 - 20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, Math.min(f3, f5), f2, this.mPaintGraph);
            String str2 = "" + (((int) (((max2 * 100.0f) / Math.abs(f6 - f4)) + 0.5d)) / 100.0f);
            this.mTextPaint.getTextBounds(str2, 0, str2.length(), this.mBounds);
            canvas.drawText(str2, f + 5.0f, max - ((max2 / 2.0f) - (this.mBounds.height() / 2)), this.mTextPaint);
            canvas.drawLine(f, f2, f, Math.max(f4, f6), this.mPaintGraph);
        }

        public final void drawPathRelativeTicks(Canvas canvas, float f, float f2) {
            float[] fArr = this.mPoints;
            float f3 = fArr[0];
            float f4 = fArr[1];
            float f5 = fArr[fArr.length - 2];
            float f6 = fArr[fArr.length - 1];
            float hypot = (float) Math.hypot(f3 - f5, f4 - f6);
            float f7 = f5 - f3;
            float f8 = f6 - f4;
            float f9 = (((f2 - f4) * f8) + ((f - f3) * f7)) / (hypot * hypot);
            float f10 = (f7 * f9) + f3;
            float f11 = (f9 * f8) + f4;
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f10, f11);
            float hypot2 = (float) Math.hypot(f10 - f, f11 - f2);
            String str = "" + (((int) ((hypot2 * 100.0f) / hypot)) / 100.0f);
            this.mTextPaint.getTextBounds(str, 0, str.length(), this.mBounds);
            canvas.drawTextOnPath(str, path, (hypot2 / 2.0f) - (this.mBounds.width() / 2), -20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, f10, f11, this.mPaintGraph);
        }

        public final void drawPathScreenTicks(Canvas canvas, float f, float f2, int i, int i2) {
            StringBuilder sb = new StringBuilder("");
            MotionLayout motionLayout = MotionLayout.this;
            sb.append(((int) ((((f - (i / 2)) * 100.0f) / (motionLayout.getWidth() - i)) + 0.5d)) / 100.0f);
            String sb2 = sb.toString();
            this.mTextPaint.getTextBounds(sb2, 0, sb2.length(), this.mBounds);
            canvas.drawText(sb2, ((f / 2.0f) - (this.mBounds.width() / 2)) + 0.0f, f2 - 20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, Math.min(0.0f, 1.0f), f2, this.mPaintGraph);
            String str = "" + (((int) ((((f2 - (i2 / 2)) * 100.0f) / (motionLayout.getHeight() - i2)) + 0.5d)) / 100.0f);
            this.mTextPaint.getTextBounds(str, 0, str.length(), this.mBounds);
            canvas.drawText(str, f + 5.0f, 0.0f - ((f2 / 2.0f) - (this.mBounds.height() / 2)), this.mTextPaint);
            canvas.drawLine(f, f2, f, Math.max(0.0f, 1.0f), this.mPaintGraph);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Model {
        public int mEndId;
        public int mStartId;
        public ConstraintWidgetContainer mLayoutStart = new ConstraintWidgetContainer();
        public ConstraintWidgetContainer mLayoutEnd = new ConstraintWidgetContainer();
        public ConstraintSet mStart = null;
        public ConstraintSet mEnd = null;

        public Model() {
        }

        public static void copy(ConstraintWidgetContainer constraintWidgetContainer, ConstraintWidgetContainer constraintWidgetContainer2) {
            ArrayList arrayList = constraintWidgetContainer.mChildren;
            HashMap hashMap = new HashMap();
            hashMap.put(constraintWidgetContainer, constraintWidgetContainer2);
            constraintWidgetContainer2.mChildren.clear();
            constraintWidgetContainer2.copy(constraintWidgetContainer, hashMap);
            int size = arrayList.size();
            int i = 0;
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ConstraintWidget constraintWidget = (ConstraintWidget) obj;
                ConstraintWidget barrier = constraintWidget instanceof Barrier ? new Barrier() : constraintWidget instanceof Guideline ? new Guideline() : constraintWidget instanceof Flow ? new Flow() : constraintWidget instanceof Placeholder ? new Placeholder() : constraintWidget instanceof Helper ? new HelperWidget() : new ConstraintWidget();
                constraintWidgetContainer2.mChildren.add(barrier);
                ConstraintWidget constraintWidget2 = barrier.mParent;
                if (constraintWidget2 != null) {
                    ((WidgetContainer) constraintWidget2).mChildren.remove(barrier);
                    barrier.reset();
                }
                barrier.mParent = constraintWidgetContainer2;
                hashMap.put(constraintWidget, barrier);
            }
            int size2 = arrayList.size();
            while (i < size2) {
                Object obj2 = arrayList.get(i);
                i++;
                ConstraintWidget constraintWidget3 = (ConstraintWidget) obj2;
                ((ConstraintWidget) hashMap.get(constraintWidget3)).copy(constraintWidget3, hashMap);
            }
        }

        public static ConstraintWidget getWidget(ConstraintWidgetContainer constraintWidgetContainer, View view) {
            if (constraintWidgetContainer.mCompanionWidget == view) {
                return constraintWidgetContainer;
            }
            ArrayList arrayList = constraintWidgetContainer.mChildren;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ConstraintWidget constraintWidget = (ConstraintWidget) arrayList.get(i);
                if (constraintWidget.mCompanionWidget == view) {
                    return constraintWidget;
                }
            }
            return null;
        }

        public final void build() {
            int i;
            SparseArray sparseArray;
            int[] iArr;
            int i2;
            Interpolator loadInterpolator;
            Model model = this;
            MotionLayout motionLayout = MotionLayout.this;
            int childCount = motionLayout.getChildCount();
            motionLayout.mFrameArrayList.clear();
            SparseArray sparseArray2 = new SparseArray();
            int[] iArr2 = new int[childCount];
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = motionLayout.getChildAt(i3);
                MotionController motionController = new MotionController(childAt);
                int id = childAt.getId();
                iArr2[i3] = id;
                sparseArray2.put(id, motionController);
                motionLayout.mFrameArrayList.put(childAt, motionController);
            }
            int i4 = 0;
            while (i4 < childCount) {
                View childAt2 = motionLayout.getChildAt(i4);
                MotionController motionController2 = motionLayout.mFrameArrayList.get(childAt2);
                if (motionController2 == null) {
                    i = childCount;
                    sparseArray = sparseArray2;
                    iArr = iArr2;
                    i2 = i4;
                } else {
                    ConstraintSet constraintSet = model.mStart;
                    MotionConstrainedPoint motionConstrainedPoint = motionController2.mStartPoint;
                    MotionPaths motionPaths = motionController2.mStartMotionPath;
                    if (constraintSet != null) {
                        ConstraintWidget widget = getWidget(model.mLayoutStart, childAt2);
                        if (widget != null) {
                            Rect access$2000 = MotionLayout.access$2000(motionLayout, widget);
                            ConstraintSet constraintSet2 = model.mStart;
                            iArr = iArr2;
                            int width = motionLayout.getWidth();
                            i2 = i4;
                            int height = motionLayout.getHeight();
                            sparseArray = sparseArray2;
                            int i5 = constraintSet2.mRotate;
                            i = childCount;
                            if (i5 != 0) {
                                MotionController.rotate(access$2000, motionController2.mTempRect, i5, width, height);
                            }
                            motionPaths.mTime = 0.0f;
                            motionPaths.mPosition = 0.0f;
                            motionController2.readView(motionPaths);
                            motionPaths.setBounds(access$2000.left, access$2000.top, access$2000.width(), access$2000.height());
                            ConstraintSet.Constraint parameters = constraintSet2.getParameters(motionController2.mId);
                            motionPaths.applyParameters(parameters);
                            motionController2.mMotionStagger = parameters.motion.mMotionStagger;
                            motionConstrainedPoint.setState(access$2000, constraintSet2, i5, motionController2.mId);
                            motionController2.mTransformPivotTarget = parameters.transform.transformPivotTarget;
                            ConstraintSet.Motion motion = parameters.motion;
                            motionController2.mQuantizeMotionSteps = motion.mQuantizeMotionSteps;
                            motionController2.mQuantizeMotionPhase = motion.mQuantizeMotionPhase;
                            Context context = motionController2.mView.getContext();
                            ConstraintSet.Motion motion2 = parameters.motion;
                            int i6 = motion2.mQuantizeInterpolatorType;
                            String str = motion2.mQuantizeInterpolatorString;
                            int i7 = motion2.mQuantizeInterpolatorID;
                            if (i6 == -2) {
                                loadInterpolator = AnimationUtils.loadInterpolator(context, i7);
                            } else if (i6 != -1) {
                                loadInterpolator = i6 != 0 ? i6 != 1 ? i6 != 2 ? i6 != 4 ? i6 != 5 ? null : new OvershootInterpolator() : new BounceInterpolator() : new android.view.animation.DecelerateInterpolator() : new AccelerateInterpolator() : new AccelerateDecelerateInterpolator();
                            } else {
                                final Easing interpolator = Easing.getInterpolator(str);
                                loadInterpolator = 
                                /*  JADX ERROR: Method code generation error
                                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x010d: CONSTRUCTOR (r0v23 'loadInterpolator' android.view.animation.Interpolator) = (r0v22 'interpolator' androidx.constraintlayout.core.motion.utils.Easing A[DONT_INLINE]) A[MD:(androidx.constraintlayout.core.motion.utils.Easing):void (m)] (LINE:267) call: androidx.constraintlayout.motion.widget.MotionController.1.<init>(androidx.constraintlayout.core.motion.utils.Easing):void type: CONSTRUCTOR in method: androidx.constraintlayout.motion.widget.MotionLayout.Model.build():void, file: classes.dex
                                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:140)
                                    	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:157)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:136)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:140)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:226)
                                    	at jadx.core.dex.regions.loops.LoopRegion.generate(LoopRegion.java:171)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:186)
                                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
                                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                                    	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(ReferencePipeline.java:285)
                                    	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:571)
                                    	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
                                    	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:153)
                                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:176)
                                    	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:265)
                                    	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:636)
                                    	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:297)
                                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:286)
                                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:270)
                                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:161)
                                    	at jadx.core.codegen.ClassGen.addInnerClass(ClassGen.java:310)
                                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:299)
                                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:186)
                                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
                                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                                    	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(ReferencePipeline.java:285)
                                    	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:571)
                                    	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
                                    	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:153)
                                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:176)
                                    	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:265)
                                    	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:636)
                                    	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:297)
                                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:286)
                                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:270)
                                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:161)
                                    	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:103)
                                    	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:45)
                                    	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:34)
                                    	at jadx.core.codegen.CodeGen.generate(CodeGen.java:22)
                                    	at jadx.core.ProcessClass.process(ProcessClass.java:79)
                                    	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
                                    	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:402)
                                    	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:390)
                                    	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:340)
                                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: androidx.constraintlayout.motion.widget.MotionController, state: PROCESS_STARTED
                                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:305)
                                    	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                    	... 84 more
                                    */
                                /*
                                    Method dump skipped, instructions count: 737
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.Model.build():void");
                            }

                            public final void computeStartEndSize(int i, int i2) {
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

                            public final void initFrom(ConstraintSet constraintSet, ConstraintSet constraintSet2) {
                                this.mStart = constraintSet;
                                this.mEnd = constraintSet2;
                                this.mLayoutStart = new ConstraintWidgetContainer();
                                this.mLayoutEnd = new ConstraintWidgetContainer();
                                ConstraintWidgetContainer constraintWidgetContainer = this.mLayoutStart;
                                MotionLayout motionLayout = MotionLayout.this;
                                BasicMeasure.Measurer measurer = ((ConstraintLayout) motionLayout).mLayoutWidget.mMeasurer;
                                constraintWidgetContainer.mMeasurer = measurer;
                                constraintWidgetContainer.mDependencyGraph.mMeasurer = measurer;
                                ConstraintWidgetContainer constraintWidgetContainer2 = this.mLayoutEnd;
                                BasicMeasure.Measurer measurer2 = ((ConstraintLayout) motionLayout).mLayoutWidget.mMeasurer;
                                constraintWidgetContainer2.mMeasurer = measurer2;
                                constraintWidgetContainer2.mDependencyGraph.mMeasurer = measurer2;
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
                                this.mLayoutStart.mIsRtl = motionLayout.isRtl();
                                ConstraintWidgetContainer constraintWidgetContainer3 = this.mLayoutStart;
                                constraintWidgetContainer3.mBasicMeasureSolver.updateHierarchy(constraintWidgetContainer3);
                                this.mLayoutEnd.mIsRtl = motionLayout.isRtl();
                                ConstraintWidgetContainer constraintWidgetContainer4 = this.mLayoutEnd;
                                constraintWidgetContainer4.mBasicMeasureSolver.updateHierarchy(constraintWidgetContainer4);
                                ViewGroup.LayoutParams layoutParams = motionLayout.getLayoutParams();
                                if (layoutParams != null) {
                                    if (layoutParams.width == -2) {
                                        ConstraintWidgetContainer constraintWidgetContainer5 = this.mLayoutStart;
                                        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                                        constraintWidgetContainer5.setHorizontalDimensionBehaviour(dimensionBehaviour);
                                        this.mLayoutEnd.setHorizontalDimensionBehaviour(dimensionBehaviour);
                                    }
                                    if (layoutParams.height == -2) {
                                        ConstraintWidgetContainer constraintWidgetContainer6 = this.mLayoutStart;
                                        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                                        constraintWidgetContainer6.setVerticalDimensionBehaviour(dimensionBehaviour2);
                                        this.mLayoutEnd.setVerticalDimensionBehaviour(dimensionBehaviour2);
                                    }
                                }
                            }

                            public final void reEvaluateState() {
                                ConstraintWidgetContainer constraintWidgetContainer;
                                boolean z;
                                MotionLayout motionLayout = MotionLayout.this;
                                int i = motionLayout.mLastWidthMeasureSpec;
                                int i2 = motionLayout.mLastHeightMeasureSpec;
                                int mode = View.MeasureSpec.getMode(i);
                                int mode2 = View.MeasureSpec.getMode(i2);
                                motionLayout.mWidthMeasureMode = mode;
                                motionLayout.mHeightMeasureMode = mode2;
                                computeStartEndSize(i, i2);
                                boolean z2 = true;
                                if (!(motionLayout.getParent() instanceof MotionLayout) || mode != 1073741824 || mode2 != 1073741824) {
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
                                int i6 = motionLayout.mHeightMeasureMode;
                                if (i6 == Integer.MIN_VALUE || i6 == 0) {
                                    i4 = (int) ((motionLayout.mPostInterpolationPosition * (motionLayout.mEndWrapHeight - i4)) + i4);
                                }
                                ConstraintWidgetContainer constraintWidgetContainer2 = this.mLayoutStart;
                                if (constraintWidgetContainer2.mWidthMeasuredTooSmall || this.mLayoutEnd.mWidthMeasuredTooSmall) {
                                    constraintWidgetContainer = constraintWidgetContainer2;
                                    z = true;
                                } else {
                                    constraintWidgetContainer = constraintWidgetContainer2;
                                    z = false;
                                }
                                if (!constraintWidgetContainer.mHeightMeasuredTooSmall && !this.mLayoutEnd.mHeightMeasuredTooSmall) {
                                    z2 = false;
                                }
                                motionLayout.resolveMeasuredDimension(i, i2, i3, i4, z, z2);
                                MotionLayout.access$1400(motionLayout);
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            public final void setupConstraintWidget(ConstraintWidgetContainer constraintWidgetContainer, ConstraintSet constraintSet) {
                                SparseArray<ConstraintWidget> sparseArray = new SparseArray<>();
                                Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, -2);
                                sparseArray.clear();
                                sparseArray.put(0, constraintWidgetContainer);
                                MotionLayout motionLayout = MotionLayout.this;
                                sparseArray.put(motionLayout.getId(), constraintWidgetContainer);
                                if (constraintSet != null && constraintSet.mRotate != 0) {
                                    motionLayout.resolveSystem(this.mLayoutEnd, motionLayout.getOptimizationLevel(), View.MeasureSpec.makeMeasureSpec(motionLayout.getHeight(), 1073741824), View.MeasureSpec.makeMeasureSpec(motionLayout.getWidth(), 1073741824));
                                }
                                ArrayList arrayList = constraintWidgetContainer.mChildren;
                                int size = arrayList.size();
                                int i = 0;
                                while (i < size) {
                                    Object obj = arrayList.get(i);
                                    i++;
                                    ConstraintWidget constraintWidget = (ConstraintWidget) obj;
                                    constraintWidget.mAnimated = true;
                                    sparseArray.put(((View) constraintWidget.mCompanionWidget).getId(), constraintWidget);
                                }
                                ArrayList arrayList2 = constraintWidgetContainer.mChildren;
                                int size2 = arrayList2.size();
                                int i2 = 0;
                                while (i2 < size2) {
                                    int i3 = i2 + 1;
                                    ConstraintWidget constraintWidget2 = (ConstraintWidget) arrayList2.get(i2);
                                    View view = (View) constraintWidget2.mCompanionWidget;
                                    constraintSet.applyToLayoutParams(view.getId(), layoutParams);
                                    constraintWidget2.setWidth(constraintSet.getWidth(view.getId()));
                                    constraintWidget2.setHeight(constraintSet.getHeight(view.getId()));
                                    if (view instanceof ConstraintHelper) {
                                        constraintSet.applyToHelper((ConstraintHelper) view, constraintWidget2, layoutParams, sparseArray);
                                        if (view instanceof androidx.constraintlayout.widget.Barrier) {
                                            ((androidx.constraintlayout.widget.Barrier) view).validateParams();
                                        }
                                    }
                                    layoutParams.resolveLayoutDirection(motionLayout.getLayoutDirection());
                                    MotionLayout.this.applyConstraintsFromLayoutParams(false, view, constraintWidget2, layoutParams, sparseArray);
                                    if (constraintSet.getVisibilityMode(view.getId()) == 1) {
                                        constraintWidget2.mVisibility = view.getVisibility();
                                    } else {
                                        constraintWidget2.mVisibility = constraintSet.getVisibility(view.getId());
                                    }
                                    i2 = i3;
                                }
                                ArrayList arrayList3 = constraintWidgetContainer.mChildren;
                                int size3 = arrayList3.size();
                                int i4 = 0;
                                while (i4 < size3) {
                                    Object obj2 = arrayList3.get(i4);
                                    i4++;
                                    ConstraintWidget constraintWidget3 = (ConstraintWidget) obj2;
                                    if (constraintWidget3 instanceof VirtualLayout) {
                                        ConstraintHelper constraintHelper = (ConstraintHelper) constraintWidget3.mCompanionWidget;
                                        Helper helper = (Helper) constraintWidget3;
                                        constraintHelper.updatePreLayout(constraintWidgetContainer, helper, sparseArray);
                                        VirtualLayout virtualLayout = (VirtualLayout) helper;
                                        for (int i5 = 0; i5 < virtualLayout.mWidgetsCount; i5++) {
                                            ConstraintWidget constraintWidget4 = virtualLayout.mWidgets[i5];
                                            if (constraintWidget4 != null) {
                                                constraintWidget4.mInVirtualLayout = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        public interface MotionTracker {
                        }

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        public class MyTracker implements MotionTracker {
                            public static final MyTracker sMe = new MyTracker();
                            public VelocityTracker mTracker;

                            private MyTracker() {
                            }
                        }

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        public class StateCache {
                            public float mProgress = Float.NaN;
                            public float mVelocity = Float.NaN;
                            public int mStartState = -1;
                            public int mEndState = -1;

                            public StateCache() {
                            }

                            public final void apply() {
                                int i = this.mStartState;
                                MotionLayout motionLayout = MotionLayout.this;
                                if (i != -1 || this.mEndState != -1) {
                                    if (i == -1) {
                                        motionLayout.transitionToState(this.mEndState);
                                    } else {
                                        int i2 = this.mEndState;
                                        if (i2 == -1) {
                                            motionLayout.setState(i, -1, -1);
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

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        public interface TransitionListener {
                            void onTransitionChange(float f);
                        }

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        enum TransitionState {
                            UNDEFINED,
                            SETUP,
                            MOVING,
                            FINISHED
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
                            this.mIndirectTransition = false;
                            this.mDebugPath = 0;
                            this.mTemporalInterpolator = false;
                            this.mStopLogic = new StopLogic();
                            this.mDecelerateLogic = new DecelerateInterpolator();
                            this.mFirstDown = true;
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
                            this.mIsAnimating = false;
                            this.mMeasureDuringTransition = false;
                            this.mKeyCache = new KeyCache();
                            this.mInLayout = false;
                            this.mOnComplete = null;
                            this.mScheduledTransitionTo = null;
                            this.mScheduledTransitions = 0;
                            this.mInRotation = false;
                            this.mRotatMode = 0;
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

                        public static void access$1400(MotionLayout motionLayout) {
                            int childCount = motionLayout.getChildCount();
                            motionLayout.mModel.build();
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
                                        motionController.mPathMotionArc = i3;
                                    }
                                }
                            }
                            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
                            int[] iArr = new int[motionLayout.mFrameArrayList.size()];
                            int i5 = 0;
                            for (int i6 = 0; i6 < childCount; i6++) {
                                MotionController motionController2 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i6));
                                int i7 = motionController2.mStartMotionPath.mAnimateRelativeTo;
                                if (i7 != -1) {
                                    sparseBooleanArray.put(i7, true);
                                    iArr[i5] = motionController2.mStartMotionPath.mAnimateRelativeTo;
                                    i5++;
                                }
                            }
                            if (motionLayout.mDecoratorsHelpers != null) {
                                for (int i8 = 0; i8 < i5; i8++) {
                                    MotionController motionController3 = motionLayout.mFrameArrayList.get(motionLayout.findViewById(iArr[i8]));
                                    if (motionController3 != null) {
                                        motionLayout.mScene.getKeyFrames(motionController3);
                                    }
                                }
                                ArrayList<MotionHelper> arrayList = motionLayout.mDecoratorsHelpers;
                                int size = arrayList.size();
                                int i9 = 0;
                                while (i9 < size) {
                                    MotionHelper motionHelper = arrayList.get(i9);
                                    i9++;
                                    motionHelper.getClass();
                                }
                                for (int i10 = 0; i10 < i5; i10++) {
                                    MotionController motionController4 = motionLayout.mFrameArrayList.get(motionLayout.findViewById(iArr[i10]));
                                    if (motionController4 != null) {
                                        motionController4.setup(width, height, motionLayout.getNanoTime());
                                    }
                                }
                            } else {
                                for (int i11 = 0; i11 < i5; i11++) {
                                    MotionController motionController5 = motionLayout.mFrameArrayList.get(motionLayout.findViewById(iArr[i11]));
                                    if (motionController5 != null) {
                                        motionLayout.mScene.getKeyFrames(motionController5);
                                        motionController5.setup(width, height, motionLayout.getNanoTime());
                                    }
                                }
                            }
                            for (int i12 = 0; i12 < childCount; i12++) {
                                View childAt2 = motionLayout.getChildAt(i12);
                                MotionController motionController6 = motionLayout.mFrameArrayList.get(childAt2);
                                if (!sparseBooleanArray.get(childAt2.getId()) && motionController6 != null) {
                                    motionLayout.mScene.getKeyFrames(motionController6);
                                    motionController6.setup(width, height, motionLayout.getNanoTime());
                                }
                            }
                            MotionScene.Transition transition2 = motionLayout.mScene.mCurrentTransition;
                            float f = transition2 != null ? transition2.mStagger : 0.0f;
                            if (f != 0.0f) {
                                boolean z = ((double) f) < 0.0d;
                                float abs = Math.abs(f);
                                float f2 = -3.4028235E38f;
                                float f3 = Float.MAX_VALUE;
                                float f4 = -3.4028235E38f;
                                float f5 = Float.MAX_VALUE;
                                for (int i13 = 0; i13 < childCount; i13++) {
                                    MotionController motionController7 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i13));
                                    if (!Float.isNaN(motionController7.mMotionStagger)) {
                                        for (int i14 = 0; i14 < childCount; i14++) {
                                            MotionController motionController8 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i14));
                                            if (!Float.isNaN(motionController8.mMotionStagger)) {
                                                f3 = Math.min(f3, motionController8.mMotionStagger);
                                                f2 = Math.max(f2, motionController8.mMotionStagger);
                                            }
                                        }
                                        while (i < childCount) {
                                            MotionController motionController9 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i));
                                            if (!Float.isNaN(motionController9.mMotionStagger)) {
                                                motionController9.mStaggerScale = 1.0f / (1.0f - abs);
                                                if (z) {
                                                    motionController9.mStaggerOffset = abs - (((f2 - motionController9.mMotionStagger) / (f2 - f3)) * abs);
                                                } else {
                                                    motionController9.mStaggerOffset = abs - (((motionController9.mMotionStagger - f3) * abs) / (f2 - f3));
                                                }
                                            }
                                            i++;
                                        }
                                        return;
                                    }
                                    MotionPaths motionPaths = motionController7.mEndMotionPath;
                                    float f6 = motionPaths.mX;
                                    float f7 = motionPaths.mY;
                                    float f8 = z ? f7 - f6 : f7 + f6;
                                    f5 = Math.min(f5, f8);
                                    f4 = Math.max(f4, f8);
                                }
                                while (i < childCount) {
                                    MotionController motionController10 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i));
                                    MotionPaths motionPaths2 = motionController10.mEndMotionPath;
                                    float f9 = motionPaths2.mX;
                                    float f10 = motionPaths2.mY;
                                    float f11 = z ? f10 - f9 : f10 + f9;
                                    motionController10.mStaggerScale = 1.0f / (1.0f - abs);
                                    motionController10.mStaggerOffset = abs - (((f11 - f5) * abs) / (f4 - f5));
                                    i++;
                                }
                            }
                        }

                        public static Rect access$2000(MotionLayout motionLayout, ConstraintWidget constraintWidget) {
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

                        public void addTransitionListener(TransitionListener transitionListener) {
                            if (this.mTransitionListeners == null) {
                                this.mTransitionListeners = new CopyOnWriteArrayList<>();
                            }
                            this.mTransitionListeners.add(transitionListener);
                        }

                        public void animateTo(float f) {
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

                        public boolean applyViewTransition(int i, MotionController motionController) {
                            MotionScene motionScene = this.mScene;
                            if (motionScene != null) {
                                ArrayList arrayList = motionScene.mViewTransitionController.mViewTransitions;
                                int size = arrayList.size();
                                int i2 = 0;
                                while (i2 < size) {
                                    Object obj = arrayList.get(i2);
                                    i2++;
                                    ViewTransition viewTransition = (ViewTransition) obj;
                                    if (viewTransition.mId == i) {
                                        ArrayList arrayList2 = (ArrayList) viewTransition.mKeyFrames.mFramesMap.get(-1);
                                        if (arrayList2 == null) {
                                            return true;
                                        }
                                        motionController.mKeyList.addAll(arrayList2);
                                        return true;
                                    }
                                }
                            }
                            return false;
                        }

                        public ConstraintSet cloneConstraintSet(int i) {
                            MotionScene motionScene = this.mScene;
                            if (motionScene == null) {
                                return null;
                            }
                            ConstraintSet constraintSet = motionScene.getConstraintSet(i);
                            ConstraintSet constraintSet2 = new ConstraintSet();
                            constraintSet2.clone(constraintSet);
                            return constraintSet2;
                        }

                        public void disableAutoTransition(boolean z) {
                            MotionScene motionScene = this.mScene;
                            if (motionScene == null) {
                                return;
                            }
                            motionScene.mDisableAutoTransition = z;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:122:0x032d  */
                        /* JADX WARN: Removed duplicated region for block: B:137:0x035d  */
                        /* JADX WARN: Removed duplicated region for block: B:143:0x038a  */
                        /* JADX WARN: Removed duplicated region for block: B:148:0x03b5  */
                        /* JADX WARN: Removed duplicated region for block: B:150:0x03cc  */
                        /* JADX WARN: Removed duplicated region for block: B:153:0x03d8  */
                        /* JADX WARN: Removed duplicated region for block: B:157:0x03bf  */
                        /* JADX WARN: Removed duplicated region for block: B:159:0x0379  */
                        /* JADX WARN: Removed duplicated region for block: B:217:0x0556  */
                        /* JADX WARN: Removed duplicated region for block: B:223:0x056a A[ORIG_RETURN, RETURN] */
                        @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public void dispatchDraw(android.graphics.Canvas r38) {
                            /*
                                Method dump skipped, instructions count: 1387
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.dispatchDraw(android.graphics.Canvas):void");
                        }

                        public void enableTransition(int i, boolean z) {
                            MotionScene.Transition transition = getTransition(i);
                            int i2 = 0;
                            if (z) {
                                transition.mDisable = false;
                                return;
                            }
                            MotionScene motionScene = this.mScene;
                            if (transition == motionScene.mCurrentTransition) {
                                ArrayList arrayList = (ArrayList) motionScene.getTransitionsWithState(this.mCurrentState);
                                int size = arrayList.size();
                                while (true) {
                                    if (i2 >= size) {
                                        break;
                                    }
                                    Object obj = arrayList.get(i2);
                                    i2++;
                                    MotionScene.Transition transition2 = (MotionScene.Transition) obj;
                                    if (!transition2.mDisable) {
                                        this.mScene.mCurrentTransition = transition2;
                                        break;
                                    }
                                }
                            }
                            transition.mDisable = true;
                        }

                        public void enableViewTransition(int i, boolean z) {
                            MotionScene motionScene = this.mScene;
                            if (motionScene != null) {
                                ArrayList arrayList = motionScene.mViewTransitionController.mViewTransitions;
                                int size = arrayList.size();
                                int i2 = 0;
                                while (i2 < size) {
                                    Object obj = arrayList.get(i2);
                                    i2++;
                                    ViewTransition viewTransition = (ViewTransition) obj;
                                    if (viewTransition.mId == i) {
                                        viewTransition.mDisabled = !z;
                                        return;
                                    }
                                }
                            }
                        }

                        public void endTrigger(boolean z) {
                            int childCount = getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                MotionController motionController = this.mFrameArrayList.get(getChildAt(i));
                                if (motionController != null && "button".equals(Debug.getName(motionController.mView)) && motionController.mKeyTriggers != null) {
                                    int i2 = 0;
                                    while (true) {
                                        KeyTrigger[] keyTriggerArr = motionController.mKeyTriggers;
                                        if (i2 < keyTriggerArr.length) {
                                            keyTriggerArr[i2].conditionallyFire(z ? -100.0f : 100.0f, motionController.mView);
                                            i2++;
                                        }
                                    }
                                }
                            }
                        }

                        /* JADX WARN: Removed duplicated region for block: B:107:0x0194  */
                        /* JADX WARN: Removed duplicated region for block: B:112:0x01ab  */
                        /* JADX WARN: Removed duplicated region for block: B:118:0x01ba  */
                        /* JADX WARN: Removed duplicated region for block: B:121:0x01c7  */
                        /* JADX WARN: Removed duplicated region for block: B:128:0x01e7  */
                        /* JADX WARN: Removed duplicated region for block: B:133:0x0202  */
                        /* JADX WARN: Removed duplicated region for block: B:141:0x0222  */
                        /* JADX WARN: Removed duplicated region for block: B:161:0x0151  */
                        /* JADX WARN: Removed duplicated region for block: B:76:0x0113  */
                        /* JADX WARN: Removed duplicated region for block: B:78:0x011a  */
                        /* JADX WARN: Removed duplicated region for block: B:93:0x014f  */
                        /* JADX WARN: Removed duplicated region for block: B:96:0x015a  */
                        /* JADX WARN: Removed duplicated region for block: B:99:0x0171  */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public void evaluate(boolean r21) {
                            /*
                                Method dump skipped, instructions count: 619
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.evaluate(boolean):void");
                        }

                        public final void fireTransitionChange() {
                            CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList;
                            if ((this.mTransitionListener == null && ((copyOnWriteArrayList = this.mTransitionListeners) == null || copyOnWriteArrayList.isEmpty())) || this.mListenerPosition == this.mTransitionPosition) {
                                return;
                            }
                            if (this.mListenerState != -1) {
                                CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList2 = this.mTransitionListeners;
                                if (copyOnWriteArrayList2 != null) {
                                    Iterator<TransitionListener> it = copyOnWriteArrayList2.iterator();
                                    while (it.hasNext()) {
                                        it.next().getClass();
                                    }
                                }
                                this.mIsAnimating = true;
                            }
                            this.mListenerState = -1;
                            float f = this.mTransitionPosition;
                            this.mListenerPosition = f;
                            TransitionListener transitionListener = this.mTransitionListener;
                            if (transitionListener != null) {
                                transitionListener.onTransitionChange(f);
                            }
                            CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList3 = this.mTransitionListeners;
                            if (copyOnWriteArrayList3 != null) {
                                Iterator<TransitionListener> it2 = copyOnWriteArrayList3.iterator();
                                while (it2.hasNext()) {
                                    it2.next().onTransitionChange(this.mTransitionPosition);
                                }
                            }
                            this.mIsAnimating = true;
                        }

                        public void fireTransitionCompleted() {
                            CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList;
                            if ((this.mTransitionListener != null || ((copyOnWriteArrayList = this.mTransitionListeners) != null && !copyOnWriteArrayList.isEmpty())) && this.mListenerState == -1) {
                                this.mListenerState = this.mCurrentState;
                                int intValue = !this.mTransitionCompleted.isEmpty() ? ((Integer) AlertController$$ExternalSyntheticOutline0.m(this.mTransitionCompleted, 1)).intValue() : -1;
                                int i = this.mCurrentState;
                                if (intValue != i && i != -1) {
                                    this.mTransitionCompleted.add(Integer.valueOf(i));
                                }
                            }
                            processTransitionCompleted();
                            Runnable runnable = this.mOnComplete;
                            if (runnable != null) {
                                runnable.run();
                                this.mOnComplete = null;
                            }
                            int[] iArr = this.mScheduledTransitionTo;
                            if (iArr == null || this.mScheduledTransitions <= 0) {
                                return;
                            }
                            transitionToState(iArr[0]);
                            int[] iArr2 = this.mScheduledTransitionTo;
                            System.arraycopy(iArr2, 1, iArr2, 0, iArr2.length - 1);
                            this.mScheduledTransitions--;
                        }

                        public void fireTrigger(int i, boolean z, float f) {
                            CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList = this.mTransitionListeners;
                            if (copyOnWriteArrayList != null) {
                                Iterator<TransitionListener> it = copyOnWriteArrayList.iterator();
                                while (it.hasNext()) {
                                    it.next().getClass();
                                }
                            }
                        }

                        public void getAnchorDpDt(int i, float f, float f2, float f3, float[] fArr) {
                            HashMap<View, MotionController> hashMap = this.mFrameArrayList;
                            View viewById = getViewById(i);
                            MotionController motionController = hashMap.get(viewById);
                            if (motionController == null) {
                                MotionLayout$$ExternalSyntheticOutline0.m("WARNING could not find view id ", viewById == null ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "") : viewById.getContext().getResources().getResourceName(i), TAG);
                                return;
                            }
                            motionController.getDpDt(f, f2, f3, fArr);
                            float y = viewById.getY();
                            this.mLastPos = f;
                            this.mLastY = y;
                        }

                        public ConstraintSet getConstraintSet(int i) {
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
                            int size = motionScene.mConstraintSetMap.size();
                            int[] iArr = new int[size];
                            for (int i = 0; i < size; i++) {
                                iArr[i] = motionScene.mConstraintSetMap.keyAt(i);
                            }
                            return iArr;
                        }

                        public String getConstraintSetNames(int i) {
                            MotionScene motionScene = this.mScene;
                            if (motionScene == null) {
                                return null;
                            }
                            return motionScene.lookUpConstraintName(i);
                        }

                        public int getCurrentState() {
                            return this.mCurrentState;
                        }

                        public ArrayList<MotionScene.Transition> getDefinedTransitions() {
                            MotionScene motionScene = this.mScene;
                            if (motionScene == null) {
                                return null;
                            }
                            return motionScene.mTransitionList;
                        }

                        public DesignTool getDesignTool() {
                            if (this.mDesignTool == null) {
                                this.mDesignTool = new DesignTool(this);
                            }
                            return this.mDesignTool;
                        }

                        public int getEndState() {
                            return this.mEndState;
                        }

                        public int[] getMatchingConstraintSetIds(String... strArr) {
                            MotionScene motionScene = this.mScene;
                            if (motionScene == null) {
                                return null;
                            }
                            int size = motionScene.mConstraintSetMap.size();
                            int[] iArr = new int[size];
                            int i = 0;
                            for (int i2 = 0; i2 < size; i2++) {
                                ConstraintSet constraintSet = (ConstraintSet) motionScene.mConstraintSetMap.valueAt(i2);
                                int keyAt = motionScene.mConstraintSetMap.keyAt(i2);
                                if (constraintSet.matchesLabels(strArr)) {
                                    constraintSet.getStateLabels();
                                    iArr[i] = keyAt;
                                    i++;
                                }
                            }
                            return Arrays.copyOf(iArr, i);
                        }

                        public MotionController getMotionController(int i) {
                            return this.mFrameArrayList.get(findViewById(i));
                        }

                        public long getNanoTime() {
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

                        public MotionScene.Transition getTransition(int i) {
                            ArrayList arrayList = this.mScene.mTransitionList;
                            int size = arrayList.size();
                            int i2 = 0;
                            while (i2 < size) {
                                Object obj = arrayList.get(i2);
                                i2++;
                                MotionScene.Transition transition = (MotionScene.Transition) obj;
                                if (transition.mId == i) {
                                    return transition;
                                }
                            }
                            return null;
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

                        public void getViewVelocity(View view, float f, float f2, float[] fArr, int i) {
                            float[] fArr2;
                            float f3;
                            char c;
                            char c2;
                            MotionController motionController;
                            float[] fArr3;
                            ViewOscillator viewOscillator;
                            ViewOscillator viewOscillator2;
                            double[] dArr;
                            float f4 = this.mLastVelocity;
                            float f5 = this.mTransitionLastPosition;
                            if (this.mInterpolator != null) {
                                float signum = Math.signum(this.mTransitionGoalPosition - f5);
                                float interpolation = this.mInterpolator.getInterpolation(this.mTransitionLastPosition + EPSILON);
                                float interpolation2 = this.mInterpolator.getInterpolation(this.mTransitionLastPosition);
                                f4 = (((interpolation - interpolation2) / EPSILON) * signum) / this.mTransitionDuration;
                                f5 = interpolation2;
                            }
                            Interpolator interpolator = this.mInterpolator;
                            if (interpolator instanceof MotionInterpolator) {
                                f4 = ((MotionInterpolator) interpolator).getVelocity();
                            }
                            float f6 = f4;
                            MotionController motionController2 = this.mFrameArrayList.get(view);
                            if ((i & 1) == 0) {
                                int width = view.getWidth();
                                int height = view.getHeight();
                                float[] fArr4 = motionController2.mVelocity;
                                float adjustedPosition = motionController2.getAdjustedPosition(f5, fArr4);
                                HashMap hashMap = motionController2.mAttributesMap;
                                SplineSet splineSet = hashMap == null ? null : (SplineSet) hashMap.get("translationX");
                                HashMap hashMap2 = motionController2.mAttributesMap;
                                SplineSet splineSet2 = hashMap2 == null ? null : (SplineSet) hashMap2.get("translationY");
                                HashMap hashMap3 = motionController2.mAttributesMap;
                                SplineSet splineSet3 = hashMap3 == null ? null : (SplineSet) hashMap3.get("rotation");
                                HashMap hashMap4 = motionController2.mAttributesMap;
                                c = 1;
                                SplineSet splineSet4 = hashMap4 == null ? null : (SplineSet) hashMap4.get("scaleX");
                                c2 = 0;
                                HashMap hashMap5 = motionController2.mAttributesMap;
                                f3 = f6;
                                SplineSet splineSet5 = hashMap5 == null ? null : (SplineSet) hashMap5.get("scaleY");
                                HashMap hashMap6 = motionController2.mCycleMap;
                                ViewOscillator viewOscillator3 = hashMap6 == null ? null : (ViewOscillator) hashMap6.get("translationX");
                                HashMap hashMap7 = motionController2.mCycleMap;
                                ViewOscillator viewOscillator4 = hashMap7 == null ? null : (ViewOscillator) hashMap7.get("translationY");
                                HashMap hashMap8 = motionController2.mCycleMap;
                                ViewOscillator viewOscillator5 = hashMap8 == null ? null : (ViewOscillator) hashMap8.get("rotation");
                                HashMap hashMap9 = motionController2.mCycleMap;
                                ViewOscillator viewOscillator6 = hashMap9 == null ? null : (ViewOscillator) hashMap9.get("scaleX");
                                HashMap hashMap10 = motionController2.mCycleMap;
                                ViewOscillator viewOscillator7 = hashMap10 == null ? null : (ViewOscillator) hashMap10.get("scaleY");
                                VelocityMatrix velocityMatrix = new VelocityMatrix();
                                velocityMatrix.mDRotate = 0.0f;
                                velocityMatrix.mDTranslateY = 0.0f;
                                velocityMatrix.mDTranslateX = 0.0f;
                                velocityMatrix.mDScaleY = 0.0f;
                                velocityMatrix.mDScaleX = 0.0f;
                                if (splineSet3 != null) {
                                    motionController = motionController2;
                                    fArr3 = fArr4;
                                    velocityMatrix.mDRotate = (float) splineSet3.mCurveFit.getSlope(adjustedPosition);
                                    velocityMatrix.mRotate = splineSet3.get(adjustedPosition);
                                } else {
                                    motionController = motionController2;
                                    fArr3 = fArr4;
                                }
                                if (splineSet != null) {
                                    viewOscillator = viewOscillator7;
                                    velocityMatrix.mDTranslateX = (float) splineSet.mCurveFit.getSlope(adjustedPosition);
                                } else {
                                    viewOscillator = viewOscillator7;
                                }
                                if (splineSet2 != null) {
                                    velocityMatrix.mDTranslateY = (float) splineSet2.mCurveFit.getSlope(adjustedPosition);
                                }
                                if (splineSet4 != null) {
                                    velocityMatrix.mDScaleX = (float) splineSet4.mCurveFit.getSlope(adjustedPosition);
                                }
                                if (splineSet5 != null) {
                                    velocityMatrix.mDScaleY = (float) splineSet5.mCurveFit.getSlope(adjustedPosition);
                                }
                                if (viewOscillator5 != null) {
                                    velocityMatrix.mDRotate = viewOscillator5.getSlope(adjustedPosition);
                                }
                                if (viewOscillator3 != null) {
                                    velocityMatrix.mDTranslateX = viewOscillator3.getSlope(adjustedPosition);
                                }
                                if (viewOscillator4 != null) {
                                    velocityMatrix.mDTranslateY = viewOscillator4.getSlope(adjustedPosition);
                                }
                                if (viewOscillator6 != null) {
                                    velocityMatrix.mDScaleX = viewOscillator6.getSlope(adjustedPosition);
                                }
                                if (viewOscillator != null) {
                                    velocityMatrix.mDScaleY = viewOscillator.getSlope(adjustedPosition);
                                }
                                MotionController motionController3 = motionController;
                                ArcCurveFit arcCurveFit = motionController3.mArcSpline;
                                if (arcCurveFit != null) {
                                    double[] dArr2 = motionController3.mInterpolateData;
                                    if (dArr2.length > 0) {
                                        double d = adjustedPosition;
                                        arcCurveFit.getPos(d, dArr2);
                                        motionController3.mArcSpline.getSlope(d, motionController3.mInterpolateVelocity);
                                        int[] iArr = motionController3.mInterpolateVariables;
                                        double[] dArr3 = motionController3.mInterpolateVelocity;
                                        double[] dArr4 = motionController3.mInterpolateData;
                                        motionController3.mStartMotionPath.getClass();
                                        MotionPaths.setDpDt(f, f2, fArr, iArr, dArr3, dArr4);
                                    }
                                    velocityMatrix.applyTransform(f, f2, width, height, fArr);
                                } else if (motionController3.mSpline != null) {
                                    float[] fArr5 = fArr3;
                                    double adjustedPosition2 = motionController3.getAdjustedPosition(adjustedPosition, fArr5);
                                    motionController3.mSpline[0].getSlope(adjustedPosition2, motionController3.mInterpolateVelocity);
                                    motionController3.mSpline[0].getPos(adjustedPosition2, motionController3.mInterpolateData);
                                    float f7 = fArr5[0];
                                    int i2 = 0;
                                    while (true) {
                                        dArr = motionController3.mInterpolateVelocity;
                                        if (i2 >= dArr.length) {
                                            break;
                                        }
                                        dArr[i2] = dArr[i2] * f7;
                                        i2++;
                                    }
                                    int[] iArr2 = motionController3.mInterpolateVariables;
                                    double[] dArr5 = motionController3.mInterpolateData;
                                    motionController3.mStartMotionPath.getClass();
                                    MotionPaths.setDpDt(f, f2, fArr, iArr2, dArr, dArr5);
                                    velocityMatrix.applyTransform(f, f2, width, height, fArr);
                                } else {
                                    MotionPaths motionPaths = motionController3.mEndMotionPath;
                                    float f8 = motionPaths.mX;
                                    MotionPaths motionPaths2 = motionController3.mStartMotionPath;
                                    float f9 = f8 - motionPaths2.mX;
                                    float f10 = motionPaths.mY - motionPaths2.mY;
                                    float f11 = motionPaths.mWidth - motionPaths2.mWidth;
                                    float f12 = f10 + (motionPaths.mHeight - motionPaths2.mHeight);
                                    fArr[0] = ((f11 + f9) * f) + ((1.0f - f) * f9);
                                    fArr[1] = (f12 * f2) + ((1.0f - f2) * f10);
                                    velocityMatrix.mDRotate = 0.0f;
                                    velocityMatrix.mDTranslateY = 0.0f;
                                    velocityMatrix.mDTranslateX = 0.0f;
                                    velocityMatrix.mDScaleY = 0.0f;
                                    velocityMatrix.mDScaleX = 0.0f;
                                    if (splineSet3 != null) {
                                        viewOscillator2 = viewOscillator4;
                                        velocityMatrix.mDRotate = (float) splineSet3.mCurveFit.getSlope(adjustedPosition);
                                        velocityMatrix.mRotate = splineSet3.get(adjustedPosition);
                                    } else {
                                        viewOscillator2 = viewOscillator4;
                                    }
                                    if (splineSet != null) {
                                        velocityMatrix.mDTranslateX = (float) splineSet.mCurveFit.getSlope(adjustedPosition);
                                    }
                                    if (splineSet2 != null) {
                                        velocityMatrix.mDTranslateY = (float) splineSet2.mCurveFit.getSlope(adjustedPosition);
                                    }
                                    if (splineSet4 != null) {
                                        velocityMatrix.mDScaleX = (float) splineSet4.mCurveFit.getSlope(adjustedPosition);
                                    }
                                    if (splineSet5 != null) {
                                        velocityMatrix.mDScaleY = (float) splineSet5.mCurveFit.getSlope(adjustedPosition);
                                    }
                                    if (viewOscillator5 != null) {
                                        velocityMatrix.mDRotate = viewOscillator5.getSlope(adjustedPosition);
                                    }
                                    if (viewOscillator3 != null) {
                                        velocityMatrix.mDTranslateX = viewOscillator3.getSlope(adjustedPosition);
                                    }
                                    if (viewOscillator2 != null) {
                                        velocityMatrix.mDTranslateY = viewOscillator2.getSlope(adjustedPosition);
                                    }
                                    if (viewOscillator6 != null) {
                                        velocityMatrix.mDScaleX = viewOscillator6.getSlope(adjustedPosition);
                                    }
                                    if (viewOscillator != null) {
                                        velocityMatrix.mDScaleY = viewOscillator.getSlope(adjustedPosition);
                                    }
                                    fArr2 = fArr;
                                    velocityMatrix.applyTransform(f, f2, width, height, fArr2);
                                }
                                fArr2 = fArr;
                            } else {
                                fArr2 = fArr;
                                f3 = f6;
                                c = 1;
                                c2 = 0;
                                motionController2.getDpDt(f5, f, f2, fArr2);
                            }
                            if (i < 2) {
                                fArr2[c2] = fArr2[c2] * f3;
                                fArr2[c] = fArr2[c] * f3;
                            }
                        }

                        public final boolean handlesTouchEvent(float f, float f2, View view, MotionEvent motionEvent) {
                            boolean z;
                            boolean onTouchEvent;
                            if (view instanceof ViewGroup) {
                                ViewGroup viewGroup = (ViewGroup) view;
                                for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                                    if (handlesTouchEvent((r3.getLeft() + f) - view.getScrollX(), (r3.getTop() + f2) - view.getScrollY(), viewGroup.getChildAt(childCount), motionEvent)) {
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

                        public final void init(AttributeSet attributeSet) {
                            MotionScene motionScene;
                            IS_IN_EDIT_MODE = isInEditMode();
                            int i = 0;
                            if (attributeSet != null) {
                                TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.MotionLayout);
                                int indexCount = obtainStyledAttributes.getIndexCount();
                                boolean z = true;
                                for (int i2 = 0; i2 < indexCount; i2++) {
                                    int index = obtainStyledAttributes.getIndex(i2);
                                    if (index == R.styleable.MotionLayout_layoutDescription) {
                                        this.mScene = new MotionScene(getContext(), this, obtainStyledAttributes.getResourceId(index, -1));
                                    } else if (index == R.styleable.MotionLayout_currentState) {
                                        this.mCurrentState = obtainStyledAttributes.getResourceId(index, -1);
                                    } else if (index == R.styleable.MotionLayout_motionProgress) {
                                        this.mTransitionGoalPosition = obtainStyledAttributes.getFloat(index, 0.0f);
                                        this.mInTransition = true;
                                    } else if (index == R.styleable.MotionLayout_applyMotionScene) {
                                        z = obtainStyledAttributes.getBoolean(index, z);
                                    } else if (index == R.styleable.MotionLayout_showPaths) {
                                        if (this.mDebugPath == 0) {
                                            this.mDebugPath = obtainStyledAttributes.getBoolean(index, false) ? 2 : 0;
                                        }
                                    } else if (index == R.styleable.MotionLayout_motionDebug) {
                                        this.mDebugPath = obtainStyledAttributes.getInt(index, 0);
                                    }
                                }
                                obtainStyledAttributes.recycle();
                                if (this.mScene == null) {
                                    Log.e(TAG, "WARNING NO app:layoutDescription tag");
                                }
                                if (!z) {
                                    this.mScene = null;
                                }
                            }
                            if (this.mDebugPath != 0) {
                                MotionScene motionScene2 = this.mScene;
                                if (motionScene2 == null) {
                                    Log.e(TAG, "CHECK: motion scene not set! set \"app:layoutDescription=\"@xml/file\"");
                                } else {
                                    int startId = motionScene2.getStartId();
                                    MotionScene motionScene3 = this.mScene;
                                    ConstraintSet constraintSet = motionScene3.getConstraintSet(motionScene3.getStartId());
                                    String name = Debug.getName(startId, getContext());
                                    int childCount = getChildCount();
                                    for (int i3 = 0; i3 < childCount; i3++) {
                                        View childAt = getChildAt(i3);
                                        int id = childAt.getId();
                                        if (id == -1) {
                                            StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("CHECK: ", name, " ALL VIEWS SHOULD HAVE ID's ");
                                            m.append(childAt.getClass().getName());
                                            m.append(" does not!");
                                            Log.w(TAG, m.toString());
                                        }
                                        if (constraintSet.getConstraint(id) == null) {
                                            StringBuilder m2 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("CHECK: ", name, " NO CONSTRAINTS for ");
                                            m2.append(Debug.getName(childAt));
                                            Log.w(TAG, m2.toString());
                                        }
                                    }
                                    int[] knownIds = constraintSet.getKnownIds();
                                    for (int i4 = 0; i4 < knownIds.length; i4++) {
                                        int i5 = knownIds[i4];
                                        String name2 = Debug.getName(i5, getContext());
                                        if (findViewById(knownIds[i4]) == null) {
                                            Log.w(TAG, "CHECK: " + name + " NO View matches id " + name2);
                                        }
                                        if (constraintSet.getHeight(i5) == -1) {
                                            Log.w(TAG, MotionLayout$$ExternalSyntheticOutline0.m("CHECK: ", name, "(", name2, ") no LAYOUT_HEIGHT"));
                                        }
                                        if (constraintSet.getWidth(i5) == -1) {
                                            Log.w(TAG, MotionLayout$$ExternalSyntheticOutline0.m("CHECK: ", name, "(", name2, ") no LAYOUT_HEIGHT"));
                                        }
                                    }
                                    SparseIntArray sparseIntArray = new SparseIntArray();
                                    SparseIntArray sparseIntArray2 = new SparseIntArray();
                                    ArrayList arrayList = this.mScene.mTransitionList;
                                    int size = arrayList.size();
                                    while (i < size) {
                                        Object obj = arrayList.get(i);
                                        i++;
                                        MotionScene.Transition transition = (MotionScene.Transition) obj;
                                        MotionScene.Transition transition2 = this.mScene.mCurrentTransition;
                                        if (transition.mConstraintSetStart == transition.mConstraintSetEnd) {
                                            Log.e(TAG, "CHECK: start and end constraint set should not be the same!");
                                        }
                                        int i6 = transition.mConstraintSetStart;
                                        int i7 = transition.mConstraintSetEnd;
                                        String name3 = Debug.getName(i6, getContext());
                                        String name4 = Debug.getName(i7, getContext());
                                        if (sparseIntArray.get(i6) == i7) {
                                            Log.e(TAG, "CHECK: two transitions with the same start and end " + name3 + "->" + name4);
                                        }
                                        if (sparseIntArray2.get(i7) == i6) {
                                            Log.e(TAG, "CHECK: you can't have reverse transitions" + name3 + "->" + name4);
                                        }
                                        sparseIntArray.put(i6, i7);
                                        sparseIntArray2.put(i7, i6);
                                        if (this.mScene.getConstraintSet(i6) == null) {
                                            Log.e(TAG, " no such constraintSetStart " + name3);
                                        }
                                        if (this.mScene.getConstraintSet(i7) == null) {
                                            Log.e(TAG, " no such constraintSetEnd " + name3);
                                        }
                                    }
                                }
                            }
                            if (this.mCurrentState != -1 || (motionScene = this.mScene) == null) {
                                return;
                            }
                            this.mCurrentState = motionScene.getStartId();
                            this.mBeginState = this.mScene.getStartId();
                            MotionScene.Transition transition3 = this.mScene.mCurrentTransition;
                            this.mEndState = transition3 != null ? transition3.mConstraintSetEnd : -1;
                        }

                        public boolean isDelayedApplicationOfInitialState() {
                            return this.mDelayedApply;
                        }

                        public boolean isInRotation() {
                            return this.mInRotation;
                        }

                        public boolean isInteractionEnabled() {
                            return this.mInteractionEnabled;
                        }

                        public boolean isViewTransitionEnabled(int i) {
                            MotionScene motionScene = this.mScene;
                            if (motionScene != null) {
                                ArrayList arrayList = motionScene.mViewTransitionController.mViewTransitions;
                                int size = arrayList.size();
                                int i2 = 0;
                                while (i2 < size) {
                                    Object obj = arrayList.get(i2);
                                    i2++;
                                    if (((ViewTransition) obj).mId == i) {
                                        return !r3.mDisabled;
                                    }
                                }
                            }
                            return false;
                        }

                        public void jumpToState(int i) {
                            if (!isAttachedToWindow()) {
                                this.mCurrentState = i;
                            }
                            if (this.mBeginState == i) {
                                setProgress(0.0f);
                            } else if (this.mEndState == i) {
                                setProgress(1.0f);
                            } else {
                                setTransition(i, i);
                            }
                        }

                        @Override // androidx.constraintlayout.widget.ConstraintLayout
                        public void loadLayoutDescription(int i) {
                            MotionScene.Transition transition;
                            if (i == 0) {
                                this.mScene = null;
                                return;
                            }
                            try {
                                MotionScene motionScene = new MotionScene(getContext(), this, i);
                                this.mScene = motionScene;
                                int i2 = -1;
                                if (this.mCurrentState == -1) {
                                    this.mCurrentState = motionScene.getStartId();
                                    this.mBeginState = this.mScene.getStartId();
                                    MotionScene.Transition transition2 = this.mScene.mCurrentTransition;
                                    if (transition2 != null) {
                                        i2 = transition2.mConstraintSetEnd;
                                    }
                                    this.mEndState = i2;
                                }
                                if (!isAttachedToWindow()) {
                                    this.mScene = null;
                                    return;
                                }
                                try {
                                    Display display = getDisplay();
                                    int i3 = 0;
                                    this.mPreviouseRotation = display == null ? 0 : display.getRotation();
                                    MotionScene motionScene2 = this.mScene;
                                    if (motionScene2 != null) {
                                        ConstraintSet constraintSet = motionScene2.getConstraintSet(this.mCurrentState);
                                        this.mScene.readFallback(this);
                                        ArrayList<MotionHelper> arrayList = this.mDecoratorsHelpers;
                                        if (arrayList != null) {
                                            int size = arrayList.size();
                                            while (i3 < size) {
                                                MotionHelper motionHelper = arrayList.get(i3);
                                                i3++;
                                                motionHelper.getClass();
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
                                            post(new Runnable() { // from class: androidx.constraintlayout.motion.widget.MotionLayout.1
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
                                    MotionScene motionScene3 = this.mScene;
                                    if (motionScene3 == null || (transition = motionScene3.mCurrentTransition) == null || transition.mAutoTransition != 4) {
                                        return;
                                    }
                                    transitionToEnd();
                                    setState(TransitionState.SETUP);
                                    setState(TransitionState.MOVING);
                                } catch (Exception e) {
                                    throw new IllegalArgumentException("unable to parse MotionScene file", e);
                                }
                            } catch (Exception e2) {
                                throw new IllegalArgumentException("unable to parse MotionScene file", e2);
                            }
                        }

                        public int lookUpConstraintId(String str) {
                            Integer num;
                            MotionScene motionScene = this.mScene;
                            if (motionScene == null || (num = (Integer) motionScene.mConstraintSetIdMap.get(str)) == null) {
                                return 0;
                            }
                            return num.intValue();
                        }

                        public MotionTracker obtainVelocityTracker() {
                            MyTracker myTracker = MyTracker.sMe;
                            myTracker.mTracker = VelocityTracker.obtain();
                            return myTracker;
                        }

                        @Override // android.view.ViewGroup, android.view.View
                        public void onAttachedToWindow() {
                            MotionScene.Transition transition;
                            int i;
                            super.onAttachedToWindow();
                            Display display = getDisplay();
                            if (display != null) {
                                this.mPreviouseRotation = display.getRotation();
                            }
                            MotionScene motionScene = this.mScene;
                            if (motionScene != null && (i = this.mCurrentState) != -1) {
                                ConstraintSet constraintSet = motionScene.getConstraintSet(i);
                                this.mScene.readFallback(this);
                                ArrayList<MotionHelper> arrayList = this.mDecoratorsHelpers;
                                if (arrayList != null) {
                                    int size = arrayList.size();
                                    int i2 = 0;
                                    while (i2 < size) {
                                        MotionHelper motionHelper = arrayList.get(i2);
                                        i2++;
                                        motionHelper.getClass();
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
                            if (motionScene2 == null || (transition = motionScene2.mCurrentTransition) == null || transition.mAutoTransition != 4) {
                                return;
                            }
                            transitionToEnd();
                            setState(TransitionState.SETUP);
                            setState(TransitionState.MOVING);
                        }

                        /* JADX WARN: Removed duplicated region for block: B:104:0x00f2  */
                        @Override // android.view.ViewGroup
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public boolean onInterceptTouchEvent(android.view.MotionEvent r19) {
                            /*
                                Method dump skipped, instructions count: 427
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
                        }

                        @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
                        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
                            this.mInLayout = true;
                            try {
                                if (this.mScene == null) {
                                    super.onLayout(z, i, i2, i3, i4);
                                    this.mInLayout = false;
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
                                this.mOldWidth = i5;
                                this.mOldHeight = i6;
                                this.mInLayout = false;
                            } catch (Throwable th) {
                                this.mInLayout = false;
                                throw th;
                            }
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:24:0x004d, code lost:
                        
                            if (r7 == r9.mEndId) goto L31;
                         */
                        /* JADX WARN: Removed duplicated region for block: B:33:0x00e6  */
                        /* JADX WARN: Removed duplicated region for block: B:36:0x00f9  */
                        /* JADX WARN: Removed duplicated region for block: B:46:0x011b  */
                        /* JADX WARN: Removed duplicated region for block: B:47:0x0125  */
                        /* JADX WARN: Removed duplicated region for block: B:56:0x0154  */
                        /* JADX WARN: Removed duplicated region for block: B:65:0x0171  */
                        /* JADX WARN: Removed duplicated region for block: B:67:? A[RETURN, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:68:0x014d  */
                        /* JADX WARN: Removed duplicated region for block: B:78:0x00f1  */
                        @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public void onMeasure(int r18, int r19) {
                            /*
                                Method dump skipped, instructions count: 373
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.onMeasure(int, int):void");
                        }

                        @Override // android.view.ViewGroup, android.view.ViewParent
                        public boolean onNestedFling(View view, float f, float f2, boolean z) {
                            return false;
                        }

                        @Override // android.view.ViewGroup, android.view.ViewParent
                        public boolean onNestedPreFling(View view, float f, float f2) {
                            return false;
                        }

                        @Override // androidx.core.view.NestedScrollingParent2
                        public void onNestedPreScroll(final View view, int i, int i2, int[] iArr, int i3) {
                            MotionScene.Transition transition;
                            boolean z;
                            float f;
                            TouchResponse touchResponse;
                            float f2;
                            TouchResponse touchResponse2;
                            TouchResponse touchResponse3;
                            TouchResponse touchResponse4;
                            int i4;
                            MotionScene motionScene = this.mScene;
                            if (motionScene == null || (transition = motionScene.mCurrentTransition) == null || (z = transition.mDisable)) {
                                return;
                            }
                            int i5 = -1;
                            if (z || (touchResponse4 = transition.mTouchResponse) == null || (i4 = touchResponse4.mTouchRegionId) == -1 || view.getId() == i4) {
                                MotionScene.Transition transition2 = motionScene.mCurrentTransition;
                                if ((transition2 == null || (touchResponse3 = transition2.mTouchResponse) == null) ? false : touchResponse3.mMoveWhenScrollAtTop) {
                                    TouchResponse touchResponse5 = transition.mTouchResponse;
                                    if (touchResponse5 != null && (touchResponse5.mFlags & 4) != 0) {
                                        i5 = i2;
                                    }
                                    float f3 = this.mTransitionPosition;
                                    if ((f3 == 1.0f || f3 == 0.0f) && view.canScrollVertically(i5)) {
                                        return;
                                    }
                                }
                                TouchResponse touchResponse6 = transition.mTouchResponse;
                                if (touchResponse6 == null || (touchResponse6.mFlags & 1) == 0) {
                                    f = 0.0f;
                                } else {
                                    float f4 = i;
                                    float f5 = i2;
                                    MotionScene.Transition transition3 = motionScene.mCurrentTransition;
                                    if (transition3 == null || (touchResponse2 = transition3.mTouchResponse) == null) {
                                        f = 0.0f;
                                        f2 = 0.0f;
                                    } else {
                                        f = 0.0f;
                                        touchResponse2.mMotionLayout.getAnchorDpDt(touchResponse2.mTouchAnchorId, touchResponse2.mMotionLayout.getProgress(), touchResponse2.mTouchAnchorX, touchResponse2.mTouchAnchorY, touchResponse2.mAnchorDpDt);
                                        float f6 = touchResponse2.mTouchDirectionX;
                                        float[] fArr = touchResponse2.mAnchorDpDt;
                                        if (f6 != 0.0f) {
                                            if (fArr[0] == 0.0f) {
                                                fArr[0] = 1.0E-7f;
                                            }
                                            f2 = (f4 * f6) / fArr[0];
                                        } else {
                                            if (fArr[1] == 0.0f) {
                                                fArr[1] = 1.0E-7f;
                                            }
                                            f2 = (f5 * touchResponse2.mTouchDirectionY) / fArr[1];
                                        }
                                    }
                                    float f7 = this.mTransitionLastPosition;
                                    if ((f7 <= f && f2 < f) || (f7 >= 1.0f && f2 > f)) {
                                        view.setNestedScrollingEnabled(false);
                                        view.post(new Runnable(this) { // from class: androidx.constraintlayout.motion.widget.MotionLayout.3
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                view.setNestedScrollingEnabled(true);
                                            }
                                        });
                                        return;
                                    }
                                }
                                float f8 = this.mTransitionPosition;
                                long nanoTime = getNanoTime();
                                float f9 = i;
                                this.mScrollTargetDX = f9;
                                float f10 = i2;
                                this.mScrollTargetDY = f10;
                                this.mScrollTargetDT = (float) ((nanoTime - this.mScrollTargetTime) * 1.0E-9d);
                                this.mScrollTargetTime = nanoTime;
                                MotionScene.Transition transition4 = motionScene.mCurrentTransition;
                                if (transition4 != null && (touchResponse = transition4.mTouchResponse) != null) {
                                    MotionLayout motionLayout = touchResponse.mMotionLayout;
                                    float progress = motionLayout.getProgress();
                                    if (!touchResponse.mDragStarted) {
                                        touchResponse.mDragStarted = true;
                                        motionLayout.setProgress(progress);
                                    }
                                    touchResponse.mMotionLayout.getAnchorDpDt(touchResponse.mTouchAnchorId, progress, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                                    float f11 = touchResponse.mTouchDirectionX;
                                    float[] fArr2 = touchResponse.mAnchorDpDt;
                                    if (Math.abs((touchResponse.mTouchDirectionY * fArr2[1]) + (f11 * fArr2[0])) < 0.01d) {
                                        fArr2[0] = 0.01f;
                                        fArr2[1] = 0.01f;
                                    }
                                    float f12 = touchResponse.mTouchDirectionX;
                                    float max = Math.max(Math.min(progress + (f12 != f ? (f9 * f12) / fArr2[0] : (f10 * touchResponse.mTouchDirectionY) / fArr2[1]), 1.0f), f);
                                    if (max != motionLayout.getProgress()) {
                                        motionLayout.setProgress(max);
                                    }
                                }
                                if (f8 != this.mTransitionPosition) {
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
                        public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
                        }

                        @Override // androidx.core.view.NestedScrollingParent2
                        public void onNestedScrollAccepted(View view, View view2, int i, int i2) {
                            this.mScrollTargetTime = getNanoTime();
                            this.mScrollTargetDT = 0.0f;
                            this.mScrollTargetDX = 0.0f;
                            this.mScrollTargetDY = 0.0f;
                        }

                        /* JADX WARN: Type inference failed for: r1v4, types: [androidx.constraintlayout.motion.widget.TouchResponse$2] */
                        public void onNewStateAttachHandlers() {
                            MotionScene.Transition transition;
                            final TouchResponse touchResponse;
                            View view;
                            MotionScene motionScene = this.mScene;
                            if (motionScene == null) {
                                return;
                            }
                            if (motionScene.autoTransition(this.mCurrentState, this)) {
                                requestLayout();
                                return;
                            }
                            int i = this.mCurrentState;
                            if (i != -1) {
                                MotionScene motionScene2 = this.mScene;
                                ArrayList arrayList = motionScene2.mTransitionList;
                                int size = arrayList.size();
                                int i2 = 0;
                                while (i2 < size) {
                                    Object obj = arrayList.get(i2);
                                    i2++;
                                    MotionScene.Transition transition2 = (MotionScene.Transition) obj;
                                    if (transition2.mOnClicks.size() > 0) {
                                        ArrayList arrayList2 = transition2.mOnClicks;
                                        int size2 = arrayList2.size();
                                        int i3 = 0;
                                        while (i3 < size2) {
                                            Object obj2 = arrayList2.get(i3);
                                            i3++;
                                            ((MotionScene.Transition.TransitionOnClick) obj2).removeOnClickListeners(this);
                                        }
                                    }
                                }
                                ArrayList arrayList3 = motionScene2.mAbstractTransitionList;
                                int size3 = arrayList3.size();
                                int i4 = 0;
                                while (i4 < size3) {
                                    Object obj3 = arrayList3.get(i4);
                                    i4++;
                                    MotionScene.Transition transition3 = (MotionScene.Transition) obj3;
                                    if (transition3.mOnClicks.size() > 0) {
                                        ArrayList arrayList4 = transition3.mOnClicks;
                                        int size4 = arrayList4.size();
                                        int i5 = 0;
                                        while (i5 < size4) {
                                            Object obj4 = arrayList4.get(i5);
                                            i5++;
                                            ((MotionScene.Transition.TransitionOnClick) obj4).removeOnClickListeners(this);
                                        }
                                    }
                                }
                                ArrayList arrayList5 = motionScene2.mTransitionList;
                                int size5 = arrayList5.size();
                                int i6 = 0;
                                while (i6 < size5) {
                                    Object obj5 = arrayList5.get(i6);
                                    i6++;
                                    MotionScene.Transition transition4 = (MotionScene.Transition) obj5;
                                    if (transition4.mOnClicks.size() > 0) {
                                        ArrayList arrayList6 = transition4.mOnClicks;
                                        int size6 = arrayList6.size();
                                        int i7 = 0;
                                        while (i7 < size6) {
                                            Object obj6 = arrayList6.get(i7);
                                            i7++;
                                            ((MotionScene.Transition.TransitionOnClick) obj6).addOnClickListeners(this, i, transition4);
                                        }
                                    }
                                }
                                ArrayList arrayList7 = motionScene2.mAbstractTransitionList;
                                int size7 = arrayList7.size();
                                int i8 = 0;
                                while (i8 < size7) {
                                    Object obj7 = arrayList7.get(i8);
                                    i8++;
                                    MotionScene.Transition transition5 = (MotionScene.Transition) obj7;
                                    if (transition5.mOnClicks.size() > 0) {
                                        ArrayList arrayList8 = transition5.mOnClicks;
                                        int size8 = arrayList8.size();
                                        int i9 = 0;
                                        while (i9 < size8) {
                                            Object obj8 = arrayList8.get(i9);
                                            i9++;
                                            ((MotionScene.Transition.TransitionOnClick) obj8).addOnClickListeners(this, i, transition5);
                                        }
                                    }
                                }
                            }
                            if (!this.mScene.supportTouch() || (transition = this.mScene.mCurrentTransition) == null || (touchResponse = transition.mTouchResponse) == null) {
                                return;
                            }
                            int i10 = touchResponse.mTouchAnchorId;
                            if (i10 != -1) {
                                MotionLayout motionLayout = touchResponse.mMotionLayout;
                                view = motionLayout.findViewById(i10);
                                if (view == null) {
                                    Log.e("TouchResponse", "cannot find TouchAnchorId @id/" + Debug.getName(touchResponse.mTouchAnchorId, motionLayout.getContext()));
                                }
                            } else {
                                view = null;
                            }
                            if (view instanceof NestedScrollView) {
                                NestedScrollView nestedScrollView = (NestedScrollView) view;
                                nestedScrollView.setOnTouchListener(new View.OnTouchListener(touchResponse) { // from class: androidx.constraintlayout.motion.widget.TouchResponse.1
                                    public AnonymousClass1(final TouchResponse touchResponse2) {
                                    }

                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view2, MotionEvent motionEvent) {
                                        return false;
                                    }
                                });
                                nestedScrollView.mOnScrollChangeListener = new Object(touchResponse2) { // from class: androidx.constraintlayout.motion.widget.TouchResponse.2
                                    public AnonymousClass2(final TouchResponse touchResponse2) {
                                    }
                                };
                            }
                        }

                        @Override // android.view.View
                        public void onRtlPropertiesChanged(int i) {
                            TouchResponse touchResponse;
                            MotionScene motionScene = this.mScene;
                            if (motionScene != null) {
                                boolean isRtl = isRtl();
                                motionScene.mRtl = isRtl;
                                MotionScene.Transition transition = motionScene.mCurrentTransition;
                                if (transition == null || (touchResponse = transition.mTouchResponse) == null) {
                                    return;
                                }
                                touchResponse.setRTL(isRtl);
                            }
                        }

                        @Override // androidx.core.view.NestedScrollingParent2
                        public boolean onStartNestedScroll(View view, View view2, int i, int i2) {
                            MotionScene.Transition transition;
                            TouchResponse touchResponse;
                            MotionScene motionScene = this.mScene;
                            return (motionScene == null || (transition = motionScene.mCurrentTransition) == null || (touchResponse = transition.mTouchResponse) == null || (touchResponse.mFlags & 2) != 0) ? false : true;
                        }

                        @Override // androidx.core.view.NestedScrollingParent2
                        public void onStopNestedScroll(View view, int i) {
                            TouchResponse touchResponse;
                            int i2;
                            MotionScene motionScene = this.mScene;
                            if (motionScene != null) {
                                float f = this.mScrollTargetDT;
                                if (f == 0.0f) {
                                    return;
                                }
                                float f2 = this.mScrollTargetDX / f;
                                float f3 = this.mScrollTargetDY / f;
                                MotionScene.Transition transition = motionScene.mCurrentTransition;
                                if (transition == null || (touchResponse = transition.mTouchResponse) == null) {
                                    return;
                                }
                                touchResponse.mDragStarted = false;
                                MotionLayout motionLayout = touchResponse.mMotionLayout;
                                float progress = motionLayout.getProgress();
                                touchResponse.mMotionLayout.getAnchorDpDt(touchResponse.mTouchAnchorId, progress, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                                float f4 = touchResponse.mTouchDirectionX;
                                float[] fArr = touchResponse.mAnchorDpDt;
                                float f5 = f4 != 0.0f ? (f2 * f4) / fArr[0] : (f3 * touchResponse.mTouchDirectionY) / fArr[1];
                                if (!Float.isNaN(f5)) {
                                    progress += f5 / 3.0f;
                                }
                                if (progress == 0.0f || progress == 1.0f || (i2 = touchResponse.mOnTouchUp) == 3) {
                                    return;
                                }
                                motionLayout.touchAnimateTo(i2, ((double) progress) >= 0.5d ? 1.0f : 0.0f, f5);
                            }
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Removed duplicated region for block: B:189:0x0498  */
                        /* JADX WARN: Removed duplicated region for block: B:192:0x04d5  */
                        /* JADX WARN: Removed duplicated region for block: B:226:0x04e4  */
                        /* JADX WARN: Removed duplicated region for block: B:227:0x04ba  */
                        /* JADX WARN: Removed duplicated region for block: B:33:0x07d9  */
                        /* JADX WARN: Removed duplicated region for block: B:35:0x07de A[RETURN] */
                        /* JADX WARN: Type inference failed for: r21v15 */
                        /* JADX WARN: Type inference failed for: r21v20 */
                        /* JADX WARN: Type inference failed for: r21v21 */
                        @Override // android.view.View
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public boolean onTouchEvent(android.view.MotionEvent r39) {
                            /*
                                Method dump skipped, instructions count: 2022
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.onTouchEvent(android.view.MotionEvent):boolean");
                        }

                        @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
                        public void onViewAdded(View view) {
                            super.onViewAdded(view);
                            if (view instanceof MotionHelper) {
                                MotionHelper motionHelper = (MotionHelper) view;
                                if (this.mTransitionListeners == null) {
                                    this.mTransitionListeners = new CopyOnWriteArrayList<>();
                                }
                                this.mTransitionListeners.add(motionHelper);
                                if (motionHelper.mUseOnShow) {
                                    if (this.mOnShowHelpers == null) {
                                        this.mOnShowHelpers = new ArrayList<>();
                                    }
                                    this.mOnShowHelpers.add(motionHelper);
                                }
                                if (motionHelper.mUseOnHide) {
                                    if (this.mOnHideHelpers == null) {
                                        this.mOnHideHelpers = new ArrayList<>();
                                    }
                                    this.mOnHideHelpers.add(motionHelper);
                                }
                            }
                        }

                        @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
                        public void onViewRemoved(View view) {
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
                        public void parseLayoutDescription(int i) {
                            this.mConstraintLayoutSpec = null;
                        }

                        public final void processTransitionCompleted() {
                            CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList;
                            if (this.mTransitionListener == null && ((copyOnWriteArrayList = this.mTransitionListeners) == null || copyOnWriteArrayList.isEmpty())) {
                                return;
                            }
                            int i = 0;
                            this.mIsAnimating = false;
                            ArrayList<Integer> arrayList = this.mTransitionCompleted;
                            int size = arrayList.size();
                            while (i < size) {
                                Integer num = arrayList.get(i);
                                i++;
                                Integer num2 = num;
                                if (this.mTransitionListener != null) {
                                    num2.getClass();
                                }
                                CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList2 = this.mTransitionListeners;
                                if (copyOnWriteArrayList2 != null) {
                                    Iterator<TransitionListener> it = copyOnWriteArrayList2.iterator();
                                    while (it.hasNext()) {
                                        TransitionListener next = it.next();
                                        num2.getClass();
                                        next.getClass();
                                    }
                                }
                            }
                            this.mTransitionCompleted.clear();
                        }

                        @Deprecated
                        public void rebuildMotion() {
                            Log.e(TAG, "This method is deprecated. Please call rebuildScene() instead.");
                            rebuildScene();
                        }

                        public void rebuildScene() {
                            this.mModel.reEvaluateState();
                            invalidate();
                        }

                        public boolean removeTransitionListener(TransitionListener transitionListener) {
                            CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList = this.mTransitionListeners;
                            if (copyOnWriteArrayList == null) {
                                return false;
                            }
                            return copyOnWriteArrayList.remove(transitionListener);
                        }

                        @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View, android.view.ViewParent
                        public void requestLayout() {
                            MotionScene motionScene;
                            MotionScene.Transition transition;
                            if (!this.mMeasureDuringTransition && this.mCurrentState == -1 && (motionScene = this.mScene) != null && (transition = motionScene.mCurrentTransition) != null) {
                                int i = transition.mLayoutDuringTransition;
                                if (i == 0) {
                                    return;
                                }
                                if (i == 2) {
                                    int childCount = getChildCount();
                                    for (int i2 = 0; i2 < childCount; i2++) {
                                        this.mFrameArrayList.get(getChildAt(i2)).mForceMeasure = true;
                                    }
                                    return;
                                }
                            }
                            super.requestLayout();
                        }

                        public void rotateTo(int i, int i2) {
                            this.mInRotation = true;
                            this.mPreRotateWidth = getWidth();
                            this.mPreRotateHeight = getHeight();
                            int rotation = getDisplay().getRotation();
                            this.mRotatMode = (rotation + 1) % 4 <= (this.mPreviouseRotation + 1) % 4 ? 2 : 1;
                            this.mPreviouseRotation = rotation;
                            int childCount = getChildCount();
                            for (int i3 = 0; i3 < childCount; i3++) {
                                View childAt = getChildAt(i3);
                                ViewState viewState = this.mPreRotate.get(childAt);
                                if (viewState == null) {
                                    viewState = new ViewState();
                                    this.mPreRotate.put(childAt, viewState);
                                }
                                viewState.left = childAt.getLeft();
                                viewState.top = childAt.getTop();
                                viewState.right = childAt.getRight();
                                viewState.bottom = childAt.getBottom();
                                viewState.rotation = childAt.getRotation();
                            }
                            this.mBeginState = -1;
                            this.mEndState = i;
                            this.mScene.setTransition(-1, i);
                            this.mModel.initFrom(null, this.mScene.getConstraintSet(this.mEndState));
                            this.mTransitionPosition = 0.0f;
                            this.mTransitionLastPosition = 0.0f;
                            invalidate();
                            transitionToEnd(new Runnable() { // from class: androidx.constraintlayout.motion.widget.MotionLayout.2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    MotionLayout.this.mInRotation = false;
                                }
                            });
                            if (i2 > 0) {
                                this.mTransitionDuration = i2 / 1000.0f;
                            }
                        }

                        public void scheduleTransitionTo(int i) {
                            if (getCurrentState() == -1) {
                                transitionToState(i);
                                return;
                            }
                            int[] iArr = this.mScheduledTransitionTo;
                            if (iArr == null) {
                                this.mScheduledTransitionTo = new int[4];
                            } else if (iArr.length <= this.mScheduledTransitions) {
                                this.mScheduledTransitionTo = Arrays.copyOf(iArr, iArr.length * 2);
                            }
                            int[] iArr2 = this.mScheduledTransitionTo;
                            int i2 = this.mScheduledTransitions;
                            this.mScheduledTransitions = i2 + 1;
                            iArr2[i2] = i;
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
                                    this.mOnHideHelpers.get(i).setProgress();
                                }
                            }
                        }

                        public void setOnShow(float f) {
                            ArrayList<MotionHelper> arrayList = this.mOnShowHelpers;
                            if (arrayList != null) {
                                int size = arrayList.size();
                                for (int i = 0; i < size; i++) {
                                    this.mOnShowHelpers.get(i).setProgress();
                                }
                            }
                        }

                        public void setProgress(float f, float f2) {
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
                            TouchResponse touchResponse;
                            this.mScene = motionScene;
                            boolean isRtl = isRtl();
                            motionScene.mRtl = isRtl;
                            MotionScene.Transition transition = motionScene.mCurrentTransition;
                            if (transition != null && (touchResponse = transition.mTouchResponse) != null) {
                                touchResponse.setRTL(isRtl);
                            }
                            rebuildScene();
                        }

                        public void setStartState(int i) {
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

                        public void setState(TransitionState transitionState) {
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

                        public void setTransition(int i, int i2) {
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
                                transitionToStart();
                            }
                        }

                        public void setTransitionDuration(int i) {
                            MotionScene motionScene = this.mScene;
                            if (motionScene == null) {
                                Log.e(TAG, "MotionScene not defined");
                                return;
                            }
                            MotionScene.Transition transition = motionScene.mCurrentTransition;
                            if (transition != null) {
                                transition.mDuration = Math.max(i, 8);
                            } else {
                                motionScene.mDefaultDuration = i;
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
                        public String toString() {
                            Context context = getContext();
                            return Debug.getName(this.mBeginState, context) + "->" + Debug.getName(this.mEndState, context) + " (pos:" + this.mTransitionLastPosition + " Dpos/Dt:" + this.mLastVelocity;
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:16:0x003b, code lost:
                        
                            if (r11 != 7) goto L86;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:22:0x0058, code lost:
                        
                            if ((((r13 * r4) - (((r0 * r4) * r4) / 2.0f)) + r11) > 1.0f) goto L26;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:23:0x007e, code lost:
                        
                            r3 = r10.mStopLogic;
                            r4 = r10.mTransitionLastPosition;
                            r7 = r10.mTransitionDuration;
                            r8 = r10.mScene.getMaxAcceleration();
                            r11 = r10.mScene.mCurrentTransition;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:24:0x008e, code lost:
                        
                            if (r11 == null) goto L33;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:25:0x0090, code lost:
                        
                            r11 = r11.mTouchResponse;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:26:0x0092, code lost:
                        
                            if (r11 == null) goto L33;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:27:0x0094, code lost:
                        
                            r9 = r11.mMaxVelocity;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:29:0x009c, code lost:
                        
                            r3.config(r4, r12, r13, r7, r8, r9);
                            r10.mLastVelocity = 0.0f;
                            r11 = r10.mCurrentState;
                            r10.mTransitionGoalPosition = r12;
                            r10.mCurrentState = r11;
                            r10.mInterpolator = r10.mStopLogic;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:30:0x009a, code lost:
                        
                            r9 = 0.0f;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:31:0x0068, code lost:
                        
                            r11 = r10.mDecelerateLogic;
                            r12 = r10.mTransitionLastPosition;
                            r0 = r10.mScene.getMaxAcceleration();
                            r11.mInitialV = r13;
                            r11.mCurrentP = r12;
                            r11.mMaxA = r0;
                            r10.mInterpolator = r10.mDecelerateLogic;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:33:0x0066, code lost:
                        
                            if ((((((r0 * r3) * r3) / 2.0f) + (r13 * r3)) + r11) < 0.0f) goto L26;
                         */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public void touchAnimateTo(int r11, float r12, float r13) {
                            /*
                                Method dump skipped, instructions count: 336
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionLayout.touchAnimateTo(int, float, float):void");
                        }

                        public void touchSpringTo(float f, float f2) {
                            TouchResponse touchResponse;
                            TouchResponse touchResponse2;
                            TouchResponse touchResponse3;
                            TouchResponse touchResponse4;
                            TouchResponse touchResponse5;
                            if (this.mScene == null || this.mTransitionLastPosition == f) {
                                return;
                            }
                            this.mTemporalInterpolator = true;
                            this.mAnimationStartTime = getNanoTime();
                            this.mTransitionDuration = this.mScene.getDuration() / 1000.0f;
                            this.mTransitionGoalPosition = f;
                            this.mInTransition = true;
                            StopLogic stopLogic = this.mStopLogic;
                            float f3 = this.mTransitionLastPosition;
                            MotionScene.Transition transition = this.mScene.mCurrentTransition;
                            float f4 = 0.0f;
                            float f5 = (transition == null || (touchResponse5 = transition.mTouchResponse) == null) ? 0.0f : touchResponse5.mSpringMass;
                            float f6 = (transition == null || (touchResponse4 = transition.mTouchResponse) == null) ? 0.0f : touchResponse4.mSpringStiffness;
                            float f7 = (transition == null || (touchResponse3 = transition.mTouchResponse) == null) ? 0.0f : touchResponse3.mSpringDamping;
                            if (transition != null && (touchResponse2 = transition.mTouchResponse) != null) {
                                f4 = touchResponse2.mSpringStopThreshold;
                            }
                            stopLogic.springConfig((transition == null || (touchResponse = transition.mTouchResponse) == null) ? 0 : touchResponse.mSpringBoundary, f3, f, f5, f6, f7, f4);
                            int i = this.mCurrentState;
                            this.mTransitionGoalPosition = f;
                            this.mCurrentState = i;
                            this.mInterpolator = this.mStopLogic;
                            this.mTransitionInstantly = false;
                            this.mAnimationStartTime = getNanoTime();
                            invalidate();
                        }

                        public void transitionToEnd() {
                            animateTo(1.0f);
                            this.mOnComplete = null;
                        }

                        public void transitionToStart() {
                            animateTo(0.0f);
                        }

                        public void transitionToState(int i) {
                            if (isAttachedToWindow()) {
                                transitionToState(i, -1, -1);
                                return;
                            }
                            if (this.mStateCache == null) {
                                this.mStateCache = new StateCache();
                            }
                            this.mStateCache.mEndState = i;
                        }

                        public void updateState(int i, ConstraintSet constraintSet) {
                            MotionScene motionScene = this.mScene;
                            if (motionScene != null) {
                                motionScene.mConstraintSetMap.put(i, constraintSet);
                            }
                            updateState();
                            if (this.mCurrentState == i) {
                                constraintSet.applyTo(this);
                            }
                        }

                        public void updateStateAnimate(int i, ConstraintSet constraintSet, int i2) {
                            if (this.mScene != null && this.mCurrentState == i) {
                                int i3 = R.id.view_transition;
                                updateState(i3, getConstraintSet(i));
                                setState(i3, -1, -1);
                                updateState(i, constraintSet);
                                MotionScene.Transition transition = new MotionScene.Transition(-1, this.mScene, i3, i);
                                transition.mDuration = Math.max(i2, 8);
                                setTransition(transition);
                                transitionToEnd();
                            }
                        }

                        public void viewTransition(int i, View... viewArr) {
                            String str;
                            MotionScene motionScene = this.mScene;
                            if (motionScene == null) {
                                Log.e(TAG, " no motionScene");
                                return;
                            }
                            ViewTransitionController viewTransitionController = motionScene.mViewTransitionController;
                            viewTransitionController.getClass();
                            ArrayList arrayList = new ArrayList();
                            ArrayList arrayList2 = viewTransitionController.mViewTransitions;
                            int size = arrayList2.size();
                            ViewTransition viewTransition = null;
                            int i2 = 0;
                            while (true) {
                                str = viewTransitionController.mTAG;
                                if (i2 >= size) {
                                    break;
                                }
                                int i3 = i2 + 1;
                                ViewTransition viewTransition2 = (ViewTransition) arrayList2.get(i2);
                                if (viewTransition2.mId == i) {
                                    for (View view : viewArr) {
                                        if (viewTransition2.checkTags(view)) {
                                            arrayList.add(view);
                                        }
                                    }
                                    if (arrayList.isEmpty()) {
                                        viewTransition = viewTransition2;
                                    } else {
                                        View[] viewArr2 = (View[]) arrayList.toArray(new View[0]);
                                        MotionLayout motionLayout = viewTransitionController.mMotionLayout;
                                        int currentState = motionLayout.getCurrentState();
                                        if (viewTransition2.mViewTransitionMode != 2) {
                                            if (currentState == -1) {
                                                Log.w(str, "No support for ViewTransition within transition yet. Currently: " + motionLayout.toString());
                                            } else {
                                                ConstraintSet constraintSet = motionLayout.getConstraintSet(currentState);
                                                if (constraintSet != null) {
                                                    viewTransition = viewTransition2;
                                                    viewTransition.applyTransition(viewTransitionController, viewTransitionController.mMotionLayout, currentState, constraintSet, viewArr2);
                                                }
                                            }
                                            viewTransition = viewTransition2;
                                        } else {
                                            viewTransition = viewTransition2;
                                            viewTransition.applyTransition(viewTransitionController, viewTransitionController.mMotionLayout, currentState, null, viewArr2);
                                        }
                                        arrayList.clear();
                                    }
                                }
                                i2 = i3;
                            }
                            if (viewTransition == null) {
                                Log.e(str, " Could not find ViewTransition");
                            }
                        }

                        @Override // androidx.core.view.NestedScrollingParent3
                        public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
                            if (this.mUndergoingMotion || i != 0 || i2 != 0) {
                                iArr[0] = iArr[0] + i3;
                                iArr[1] = iArr[1] + i4;
                            }
                            this.mUndergoingMotion = false;
                        }

                        public void transitionToStart(Runnable runnable) {
                            animateTo(0.0f);
                            this.mOnComplete = runnable;
                        }

                        public void transitionToEnd(Runnable runnable) {
                            animateTo(1.0f);
                            this.mOnComplete = runnable;
                        }

                        public void transitionToState(int i, int i2) {
                            if (!isAttachedToWindow()) {
                                if (this.mStateCache == null) {
                                    this.mStateCache = new StateCache();
                                }
                                this.mStateCache.mEndState = i;
                                return;
                            }
                            transitionToState(i, -1, -1, i2);
                        }

                        public void updateState() {
                            this.mModel.initFrom(this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
                            rebuildScene();
                        }

                        @Override // androidx.constraintlayout.widget.ConstraintLayout
                        public void setState(int i, int i2, int i3) {
                            setState(TransitionState.SETUP);
                            this.mCurrentState = i;
                            this.mBeginState = -1;
                            this.mEndState = -1;
                            ConstraintLayoutStates constraintLayoutStates = this.mConstraintLayoutSpec;
                            if (constraintLayoutStates != null) {
                                constraintLayoutStates.updateConstraints(i, i2, i3);
                                return;
                            }
                            MotionScene motionScene = this.mScene;
                            if (motionScene != null) {
                                motionScene.getConstraintSet(i).applyTo(this);
                            }
                        }

                        public void setProgress(float f) {
                            if (f < 0.0f || f > 1.0f) {
                                Log.w(TAG, "Warning! Progress is defined for values between 0.0 and 1.0 inclusive");
                            }
                            if (!isAttachedToWindow()) {
                                if (this.mStateCache == null) {
                                    this.mStateCache = new StateCache();
                                }
                                this.mStateCache.mProgress = f;
                                return;
                            }
                            if (f <= 0.0f) {
                                if (this.mTransitionLastPosition == 1.0f && this.mCurrentState == this.mEndState) {
                                    setState(TransitionState.MOVING);
                                }
                                this.mCurrentState = this.mBeginState;
                                if (this.mTransitionLastPosition == 0.0f) {
                                    setState(TransitionState.FINISHED);
                                }
                            } else if (f >= 1.0f) {
                                if (this.mTransitionLastPosition == 0.0f && this.mCurrentState == this.mBeginState) {
                                    setState(TransitionState.MOVING);
                                }
                                this.mCurrentState = this.mEndState;
                                if (this.mTransitionLastPosition == 1.0f) {
                                    setState(TransitionState.FINISHED);
                                }
                            } else {
                                this.mCurrentState = -1;
                                setState(TransitionState.MOVING);
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

                        public void transitionToState(int i, int i2, int i3) {
                            transitionToState(i, i2, i3, -1);
                        }

                        public void transitionToState(int i, int i2, int i3, int i4) {
                            StateSet stateSet;
                            int convertToConstraintSet;
                            MotionScene motionScene = this.mScene;
                            if (motionScene != null && (stateSet = motionScene.mStateSet) != null && (convertToConstraintSet = stateSet.convertToConstraintSet(this.mCurrentState, i, i2, i3)) != -1) {
                                i = convertToConstraintSet;
                            }
                            int i5 = this.mCurrentState;
                            if (i5 == i) {
                                return;
                            }
                            if (this.mBeginState == i) {
                                animateTo(0.0f);
                                if (i4 > 0) {
                                    this.mTransitionDuration = i4 / 1000.0f;
                                    return;
                                }
                                return;
                            }
                            if (this.mEndState == i) {
                                animateTo(1.0f);
                                if (i4 > 0) {
                                    this.mTransitionDuration = i4 / 1000.0f;
                                    return;
                                }
                                return;
                            }
                            this.mEndState = i;
                            if (i5 != -1) {
                                setTransition(i5, i);
                                animateTo(1.0f);
                                this.mTransitionLastPosition = 0.0f;
                                transitionToEnd();
                                if (i4 > 0) {
                                    this.mTransitionDuration = i4 / 1000.0f;
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
                            if (i4 == -1) {
                                this.mTransitionDuration = this.mScene.getDuration() / 1000.0f;
                            }
                            this.mBeginState = -1;
                            this.mScene.setTransition(-1, this.mEndState);
                            SparseArray sparseArray = new SparseArray();
                            if (i4 == 0) {
                                this.mTransitionDuration = this.mScene.getDuration() / 1000.0f;
                            } else if (i4 > 0) {
                                this.mTransitionDuration = i4 / 1000.0f;
                            }
                            int childCount = getChildCount();
                            this.mFrameArrayList.clear();
                            for (int i6 = 0; i6 < childCount; i6++) {
                                View childAt = getChildAt(i6);
                                this.mFrameArrayList.put(childAt, new MotionController(childAt));
                                sparseArray.put(childAt.getId(), this.mFrameArrayList.get(childAt));
                            }
                            this.mInTransition = true;
                            this.mModel.initFrom(null, this.mScene.getConstraintSet(i));
                            rebuildScene();
                            this.mModel.build();
                            int childCount2 = getChildCount();
                            for (int i7 = 0; i7 < childCount2; i7++) {
                                View childAt2 = getChildAt(i7);
                                MotionController motionController = this.mFrameArrayList.get(childAt2);
                                if (motionController != null) {
                                    MotionPaths motionPaths = motionController.mStartMotionPath;
                                    motionPaths.mTime = 0.0f;
                                    motionPaths.mPosition = 0.0f;
                                    motionPaths.setBounds(childAt2.getX(), childAt2.getY(), childAt2.getWidth(), childAt2.getHeight());
                                    MotionConstrainedPoint motionConstrainedPoint = motionController.mStartPoint;
                                    motionConstrainedPoint.getClass();
                                    childAt2.getX();
                                    childAt2.getY();
                                    childAt2.getWidth();
                                    childAt2.getHeight();
                                    motionConstrainedPoint.applyParameters(childAt2);
                                }
                            }
                            int width = getWidth();
                            int height = getHeight();
                            if (this.mDecoratorsHelpers != null) {
                                for (int i8 = 0; i8 < childCount; i8++) {
                                    MotionController motionController2 = this.mFrameArrayList.get(getChildAt(i8));
                                    if (motionController2 != null) {
                                        this.mScene.getKeyFrames(motionController2);
                                    }
                                }
                                ArrayList<MotionHelper> arrayList = this.mDecoratorsHelpers;
                                int size = arrayList.size();
                                int i9 = 0;
                                while (i9 < size) {
                                    MotionHelper motionHelper = arrayList.get(i9);
                                    i9++;
                                    motionHelper.getClass();
                                }
                                for (int i10 = 0; i10 < childCount; i10++) {
                                    MotionController motionController3 = this.mFrameArrayList.get(getChildAt(i10));
                                    if (motionController3 != null) {
                                        motionController3.setup(width, height, getNanoTime());
                                    }
                                }
                            } else {
                                for (int i11 = 0; i11 < childCount; i11++) {
                                    MotionController motionController4 = this.mFrameArrayList.get(getChildAt(i11));
                                    if (motionController4 != null) {
                                        this.mScene.getKeyFrames(motionController4);
                                        motionController4.setup(width, height, getNanoTime());
                                    }
                                }
                            }
                            MotionScene.Transition transition = this.mScene.mCurrentTransition;
                            float f = transition != null ? transition.mStagger : 0.0f;
                            if (f != 0.0f) {
                                float f2 = Float.MAX_VALUE;
                                float f3 = -3.4028235E38f;
                                for (int i12 = 0; i12 < childCount; i12++) {
                                    MotionPaths motionPaths2 = this.mFrameArrayList.get(getChildAt(i12)).mEndMotionPath;
                                    float f4 = motionPaths2.mY + motionPaths2.mX;
                                    f2 = Math.min(f2, f4);
                                    f3 = Math.max(f3, f4);
                                }
                                for (int i13 = 0; i13 < childCount; i13++) {
                                    MotionController motionController5 = this.mFrameArrayList.get(getChildAt(i13));
                                    MotionPaths motionPaths3 = motionController5.mEndMotionPath;
                                    float f5 = motionPaths3.mX;
                                    float f6 = motionPaths3.mY;
                                    motionController5.mStaggerScale = 1.0f / (1.0f - f);
                                    motionController5.mStaggerOffset = f - ((((f5 + f6) - f2) * f) / (f3 - f2));
                                }
                            }
                            this.mTransitionPosition = 0.0f;
                            this.mTransitionLastPosition = 0.0f;
                            this.mInTransition = true;
                            invalidate();
                        }

                        public void setTransition(int i) {
                            float f;
                            if (this.mScene != null) {
                                MotionScene.Transition transition = getTransition(i);
                                this.mBeginState = transition.mConstraintSetStart;
                                this.mEndState = transition.mConstraintSetEnd;
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
                                MotionScene motionScene = this.mScene;
                                motionScene.mCurrentTransition = transition;
                                TouchResponse touchResponse = transition.mTouchResponse;
                                if (touchResponse != null) {
                                    touchResponse.setRTL(motionScene.mRtl);
                                }
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
                                    Debug.getLocation();
                                    transitionToStart();
                                } else {
                                    setProgress(f);
                                }
                            }
                        }

                        public void setTransition(MotionScene.Transition transition) {
                            TouchResponse touchResponse;
                            MotionScene motionScene = this.mScene;
                            motionScene.mCurrentTransition = transition;
                            if (transition != null && (touchResponse = transition.mTouchResponse) != null) {
                                touchResponse.setRTL(motionScene.mRtl);
                            }
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
                            this.mTransitionLastTime = (transition.mTransitionFlags & 1) != 0 ? -1L : getNanoTime();
                            int startId = this.mScene.getStartId();
                            MotionScene motionScene2 = this.mScene;
                            MotionScene.Transition transition3 = motionScene2.mCurrentTransition;
                            int i2 = transition3 != null ? transition3.mConstraintSetEnd : -1;
                            if (startId == this.mBeginState && i2 == this.mEndState) {
                                return;
                            }
                            this.mBeginState = startId;
                            this.mEndState = i2;
                            motionScene2.setTransition(startId, i2);
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
                            this.mIndirectTransition = false;
                            this.mDebugPath = 0;
                            this.mTemporalInterpolator = false;
                            this.mStopLogic = new StopLogic();
                            this.mDecelerateLogic = new DecelerateInterpolator();
                            this.mFirstDown = true;
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
                            this.mIsAnimating = false;
                            this.mMeasureDuringTransition = false;
                            this.mKeyCache = new KeyCache();
                            this.mInLayout = false;
                            this.mOnComplete = null;
                            this.mScheduledTransitionTo = null;
                            this.mScheduledTransitions = 0;
                            this.mInRotation = false;
                            this.mRotatMode = 0;
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
                            this.mIndirectTransition = false;
                            this.mDebugPath = 0;
                            this.mTemporalInterpolator = false;
                            this.mStopLogic = new StopLogic();
                            this.mDecelerateLogic = new DecelerateInterpolator();
                            this.mFirstDown = true;
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
                            this.mIsAnimating = false;
                            this.mMeasureDuringTransition = false;
                            this.mKeyCache = new KeyCache();
                            this.mInLayout = false;
                            this.mOnComplete = null;
                            this.mScheduledTransitionTo = null;
                            this.mScheduledTransitions = 0;
                            this.mInRotation = false;
                            this.mRotatMode = 0;
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
