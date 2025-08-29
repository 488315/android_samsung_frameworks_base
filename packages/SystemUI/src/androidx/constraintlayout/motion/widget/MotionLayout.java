package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
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
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
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
import androidx.constraintlayout.motion.widget.ViewTransition;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayoutStates;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.StateSet;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.widget.NestedScrollView;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
            int width;
            int height;
            boolean z;
            float f;
            float f2;
            int[] iArr = this.mPathMode;
            boolean z2 = false;
            if (i == 4) {
                int i3 = 0;
                boolean z3 = false;
                boolean z4 = false;
                while (i3 < this.mKeyFrameCount) {
                    int i4 = iArr[i3];
                    boolean z5 = z3;
                    if (i4 == 1) {
                        z5 = true;
                    }
                    if (i4 == 0) {
                        z4 = true;
                    }
                    i3++;
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
                width = view.getWidth();
                height = motionController.mView.getHeight();
            } else {
                width = 0;
                height = 0;
            }
            int i5 = 1;
            while (i5 < i2 - 1) {
                if (i == 4 && iArr[i5 - 1] == 0) {
                    z = z2;
                } else {
                    int i6 = i5 * 2;
                    float[] fArr3 = this.mKeyFramePoints;
                    float f3 = fArr3[i6];
                    float f4 = fArr3[i6 + 1];
                    this.mPath.reset();
                    z = z2;
                    this.mPath.moveTo(f3, f4 + 10.0f);
                    this.mPath.lineTo(f3 + 10.0f, f4);
                    this.mPath.lineTo(f3, f4 - 10.0f);
                    this.mPath.lineTo(f3 - 10.0f, f4);
                    this.mPath.close();
                    int i7 = i5 - 1;
                    if (i == 4) {
                        int i8 = iArr[i7];
                        if (i8 == 1) {
                            f = 0.0f;
                            drawPathRelativeTicks(canvas2, f3 - 0.0f, f4 - 0.0f);
                        } else {
                            f = 0.0f;
                            if (i8 == 0) {
                                drawPathCartesianTicks(canvas2, f3 - 0.0f, f4 - 0.0f);
                            } else {
                                if (i8 == 2) {
                                    f2 = f4;
                                    drawPathScreenTicks(canvas2, f3 - 0.0f, f2 - 0.0f, width, height);
                                }
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
                        drawPathScreenTicks(canvas2, f3 - f, f2 - f, width, height);
                    }
                    canvas2.drawPath(this.mPath, this.mFillPaint);
                }
                i5++;
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
            float fMin = Math.min(f3, f5);
            float fMax = Math.max(f4, f6);
            float fMin2 = f - Math.min(f3, f5);
            float fMax2 = Math.max(f4, f6) - f2;
            String str = "" + (((int) (((fMin2 * 100.0f) / Math.abs(f5 - f3)) + 0.5d)) / 100.0f);
            this.mTextPaint.getTextBounds(str, 0, str.length(), this.mBounds);
            canvas.drawText(str, ((fMin2 / 2.0f) - (this.mBounds.width() / 2)) + fMin, f2 - 20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, Math.min(f3, f5), f2, this.mPaintGraph);
            String str2 = "" + (((int) (((fMax2 * 100.0f) / Math.abs(f6 - f4)) + 0.5d)) / 100.0f);
            this.mTextPaint.getTextBounds(str2, 0, str2.length(), this.mBounds);
            canvas.drawText(str2, f + 5.0f, fMax - ((fMax2 / 2.0f) - (this.mBounds.height() / 2)), this.mTextPaint);
            canvas.drawLine(f, f2, f, Math.max(f4, f6), this.mPaintGraph);
        }

        public final void drawPathRelativeTicks(Canvas canvas, float f, float f2) {
            float[] fArr = this.mPoints;
            float f3 = fArr[0];
            float f4 = fArr[1];
            float f5 = fArr[fArr.length - 2];
            float f6 = fArr[fArr.length - 1];
            float fHypot = (float) Math.hypot(f3 - f5, f4 - f6);
            float f7 = f5 - f3;
            float f8 = f6 - f4;
            float f9 = (((f2 - f4) * f8) + ((f - f3) * f7)) / (fHypot * fHypot);
            float f10 = (f7 * f9) + f3;
            float f11 = (f9 * f8) + f4;
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f10, f11);
            float fHypot2 = (float) Math.hypot(f10 - f, f11 - f2);
            String str = "" + (((int) ((fHypot2 * 100.0f) / fHypot)) / 100.0f);
            this.mTextPaint.getTextBounds(str, 0, str.length(), this.mBounds);
            canvas.drawTextOnPath(str, path, (fHypot2 / 2.0f) - (this.mBounds.width() / 2), -20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, f10, f11, this.mPaintGraph);
        }

        public final void drawPathScreenTicks(Canvas canvas, float f, float f2, int i, int i2) {
            StringBuilder sb = new StringBuilder("");
            MotionLayout motionLayout = MotionLayout.this;
            sb.append(((int) ((((f - (i / 2)) * 100.0f) / (motionLayout.getWidth() - i)) + 0.5d)) / 100.0f);
            String string = sb.toString();
            this.mTextPaint.getTextBounds(string, 0, string.length(), this.mBounds);
            canvas.drawText(string, ((f / 2.0f) - (this.mBounds.width() / 2)) + 0.0f, f2 - 20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, Math.min(0.0f, 1.0f), f2, this.mPaintGraph);
            String str = "" + (((int) ((((f2 - (i2 / 2)) * 100.0f) / (motionLayout.getHeight() - i2)) + 0.5d)) / 100.0f);
            this.mTextPaint.getTextBounds(str, 0, str.length(), this.mBounds);
            canvas.drawText(str, f + 5.0f, 0.0f - ((f2 / 2.0f) - (this.mBounds.height() / 2)), this.mTextPaint);
            canvas.drawLine(f, f2, f, Math.max(0.0f, 1.0f), this.mPaintGraph);
        }
    }

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
            HashMap map = new HashMap();
            map.put(constraintWidgetContainer, constraintWidgetContainer2);
            constraintWidgetContainer2.mChildren.clear();
            constraintWidgetContainer2.copy(constraintWidgetContainer, map);
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
                map.put(constraintWidget, barrier);
            }
            int size2 = arrayList.size();
            while (i < size2) {
                Object obj2 = arrayList.get(i);
                i++;
                ConstraintWidget constraintWidget3 = (ConstraintWidget) obj2;
                ((ConstraintWidget) map.get(constraintWidget3)).copy(constraintWidget3, map);
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

        public final void build() throws Resources.NotFoundException {
            int i;
            SparseArray sparseArray;
            int[] iArr;
            int i2;
            Interpolator interpolatorLoadInterpolator;
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
                            Rect rectAccess$2000 = MotionLayout.access$2000(motionLayout, widget);
                            ConstraintSet constraintSet2 = model.mStart;
                            iArr = iArr2;
                            int width = motionLayout.getWidth();
                            i2 = i4;
                            int height = motionLayout.getHeight();
                            sparseArray = sparseArray2;
                            int i5 = constraintSet2.mRotate;
                            i = childCount;
                            if (i5 != 0) {
                                MotionController.rotate(rectAccess$2000, motionController2.mTempRect, i5, width, height);
                            }
                            motionPaths.mTime = 0.0f;
                            motionPaths.mPosition = 0.0f;
                            motionController2.readView(motionPaths);
                            motionPaths.setBounds(rectAccess$2000.left, rectAccess$2000.top, rectAccess$2000.width(), rectAccess$2000.height());
                            ConstraintSet.Constraint parameters = constraintSet2.getParameters(motionController2.mId);
                            motionPaths.applyParameters(parameters);
                            motionController2.mMotionStagger = parameters.motion.mMotionStagger;
                            motionConstrainedPoint.setState(rectAccess$2000, constraintSet2, i5, motionController2.mId);
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
                                interpolatorLoadInterpolator = AnimationUtils.loadInterpolator(context, i7);
                            } else if (i6 != -1) {
                                interpolatorLoadInterpolator = i6 != 0 ? i6 != 1 ? i6 != 2 ? i6 != 4 ? i6 != 5 ? null : new OvershootInterpolator() : new BounceInterpolator() : new android.view.animation.DecelerateInterpolator() : new AccelerateInterpolator() : new AccelerateDecelerateInterpolator();
                            } else {
                                final Easing interpolator = Easing.getInterpolator(str);
                                interpolatorLoadInterpolator = 
                                /*  JADX ERROR: Method code generation error
                                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x010d: CONSTRUCTOR (r0v23 'interpolatorLoadInterpolator' android.view.animation.Interpolator) = (r0v22 'interpolator' androidx.constraintlayout.core.motion.utils.Easing A[DONT_INLINE]) A[MD:(androidx.constraintlayout.core.motion.utils.Easing):void (m)] (LINE:267) call: androidx.constraintlayout.motion.widget.MotionController.1.<init>(androidx.constraintlayout.core.motion.utils.Easing):void type: CONSTRUCTOR in method: androidx.constraintlayout.motion.widget.MotionLayout.Model.build():void, file: classes.dex
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
                                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: androidx.constraintlayout.motion.widget.MotionController, state: PROCESS_STARTED
                                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                                    	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                    	... 47 more
                                    */
                                /*
                                    Method dump skipped, instructions count: 737
                                    To view this dump add '--comments-level debug' option
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

                            public final void initFrom(ConstraintSet constraintSet, ConstraintSet constraintSet2) throws NumberFormatException {
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

                            public final void reEvaluateState() throws Resources.NotFoundException {
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
                            public final void setupConstraintWidget(ConstraintWidgetContainer constraintWidgetContainer, ConstraintSet constraintSet) throws NumberFormatException {
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

                        public interface MotionTracker {
                        }

                        public class MyTracker implements MotionTracker {
                            public static final MyTracker sMe = new MyTracker();
                            public VelocityTracker mTracker;

                            private MyTracker() {
                            }
                        }

                        public class StateCache {
                            public float mProgress = Float.NaN;
                            public float mVelocity = Float.NaN;
                            public int mStartState = -1;
                            public int mEndState = -1;

                            public StateCache() {
                            }

                            public final void apply() throws Resources.NotFoundException, NumberFormatException {
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

                        public interface TransitionListener {
                            void onTransitionChange(float f);
                        }

                        enum TransitionState {
                            UNDEFINED,
                            SETUP,
                            MOVING,
                            FINISHED
                        }

                        public MotionLayout(Context context) throws Resources.NotFoundException {
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

                        public static void access$1400(MotionLayout motionLayout) throws Resources.NotFoundException {
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
                                float fAbs = Math.abs(f);
                                float fMax = -3.4028235E38f;
                                float fMin = Float.MAX_VALUE;
                                float fMax2 = -3.4028235E38f;
                                float fMin2 = Float.MAX_VALUE;
                                for (int i13 = 0; i13 < childCount; i13++) {
                                    MotionController motionController7 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i13));
                                    if (!Float.isNaN(motionController7.mMotionStagger)) {
                                        for (int i14 = 0; i14 < childCount; i14++) {
                                            MotionController motionController8 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i14));
                                            if (!Float.isNaN(motionController8.mMotionStagger)) {
                                                fMin = Math.min(fMin, motionController8.mMotionStagger);
                                                fMax = Math.max(fMax, motionController8.mMotionStagger);
                                            }
                                        }
                                        while (i < childCount) {
                                            MotionController motionController9 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i));
                                            if (!Float.isNaN(motionController9.mMotionStagger)) {
                                                motionController9.mStaggerScale = 1.0f / (1.0f - fAbs);
                                                if (z) {
                                                    motionController9.mStaggerOffset = fAbs - (((fMax - motionController9.mMotionStagger) / (fMax - fMin)) * fAbs);
                                                } else {
                                                    motionController9.mStaggerOffset = fAbs - (((motionController9.mMotionStagger - fMin) * fAbs) / (fMax - fMin));
                                                }
                                            }
                                            i++;
                                        }
                                        return;
                                    }
                                    MotionPaths motionPaths = motionController7.mEndMotionPath;
                                    float f2 = motionPaths.mX;
                                    float f3 = motionPaths.mY;
                                    float f4 = z ? f3 - f2 : f3 + f2;
                                    fMin2 = Math.min(fMin2, f4);
                                    fMax2 = Math.max(fMax2, f4);
                                }
                                while (i < childCount) {
                                    MotionController motionController10 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i));
                                    MotionPaths motionPaths2 = motionController10.mEndMotionPath;
                                    float f5 = motionPaths2.mX;
                                    float f6 = motionPaths2.mY;
                                    float f7 = z ? f6 - f5 : f6 + f5;
                                    motionController10.mStaggerScale = 1.0f / (1.0f - fAbs);
                                    motionController10.mStaggerOffset = fAbs - (((f7 - fMin2) * fAbs) / (fMax2 - fMin2));
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

                        /* JADX WARN: Removed duplicated region for block: B:127:0x0312 A[PHI: r10
                          0x0312: PHI (r10v14 float) = (r10v13 float), (r10v22 float) binds: [B:118:0x02ee, B:123:0x02fb] A[DONT_GENERATE, DONT_INLINE]] */
                        /* JADX WARN: Removed duplicated region for block: B:194:0x0550  */
                        @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public void dispatchDraw(Canvas canvas) throws IllegalAccessException, Resources.NotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                            int i;
                            int i2;
                            int i3;
                            Iterator<MotionController> it;
                            int i4;
                            int i5;
                            char c;
                            MotionPaths motionPaths;
                            MotionController motionController;
                            int i6;
                            float[] fArr;
                            float f;
                            ViewOscillator viewOscillator;
                            double d;
                            String str;
                            ViewTransitionController viewTransitionController;
                            ArrayList arrayList;
                            ArrayList<MotionHelper> arrayList2 = this.mDecoratorsHelpers;
                            int i7 = 0;
                            if (arrayList2 != null) {
                                int size = arrayList2.size();
                                int i8 = 0;
                                while (i8 < size) {
                                    MotionHelper motionHelper = arrayList2.get(i8);
                                    i8++;
                                    motionHelper.getClass();
                                }
                            }
                            evaluate(false);
                            MotionScene motionScene = this.mScene;
                            if (motionScene != null && (viewTransitionController = motionScene.mViewTransitionController) != null && (arrayList = viewTransitionController.mAnimations) != null) {
                                int size2 = arrayList.size();
                                int i9 = 0;
                                while (i9 < size2) {
                                    Object obj = arrayList.get(i9);
                                    i9++;
                                    ((ViewTransition.Animate) obj).mutate();
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
                            int i10 = 1;
                            if ((this.mDebugPath & 1) == 1 && !isInEditMode()) {
                                this.mFrames++;
                                long nanoTime = getNanoTime();
                                long j = this.mLastDrawTime;
                                if (j != -1) {
                                    if (nanoTime - j > 200000000) {
                                        this.mLastFps = ((int) ((this.mFrames / (r10 * 1.0E-9f)) * 100.0f)) / 100.0f;
                                        this.mFrames = 0;
                                        this.mLastDrawTime = nanoTime;
                                    }
                                } else {
                                    this.mLastDrawTime = nanoTime;
                                }
                                Paint paint = new Paint();
                                paint.setTextSize(42.0f);
                                float progress = ((int) (getProgress() * 1000.0f)) / 10.0f;
                                StringBuilder sb = new StringBuilder();
                                sb.append(this.mLastFps);
                                sb.append(" fps ");
                                int i11 = this.mBeginState;
                                String resourceEntryName = PeripheralBarcodeConstants.Symbology.UNDEFINED;
                                StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(TransitionKt$$ExternalSyntheticOutline0.m(sb, i11 == -1 ? PeripheralBarcodeConstants.Symbology.UNDEFINED : getContext().getResources().getResourceEntryName(i11), " -> "));
                                int i12 = this.mEndState;
                                sbM.append(i12 == -1 ? PeripheralBarcodeConstants.Symbology.UNDEFINED : getContext().getResources().getResourceEntryName(i12));
                                sbM.append(" (progress: ");
                                sbM.append(progress);
                                sbM.append(" ) state=");
                                int i13 = this.mCurrentState;
                                if (i13 == -1) {
                                    str = "undefined";
                                } else {
                                    if (i13 != -1) {
                                        resourceEntryName = getContext().getResources().getResourceEntryName(i13);
                                    }
                                    str = resourceEntryName;
                                }
                                sbM.append(str);
                                String string = sbM.toString();
                                paint.setColor(-16777216);
                                canvas.drawText(string, 11.0f, getHeight() - 29, paint);
                                paint.setColor(-7864184);
                                canvas.drawText(string, 10.0f, getHeight() - 30, paint);
                            }
                            if (this.mDebugPath <= 1) {
                                i = 0;
                            } else {
                                if (this.mDevModeDraw == null) {
                                    this.mDevModeDraw = new DevModeDraw();
                                }
                                DevModeDraw devModeDraw = this.mDevModeDraw;
                                HashMap<View, MotionController> map = this.mFrameArrayList;
                                int duration = this.mScene.getDuration();
                                int i14 = this.mDebugPath;
                                devModeDraw.getClass();
                                if (map != null && map.size() != 0) {
                                    canvas.save();
                                    MotionLayout motionLayout = MotionLayout.this;
                                    if (!motionLayout.isInEditMode() && (i14 & 1) == 2) {
                                        String str2 = motionLayout.getContext().getResources().getResourceName(motionLayout.mEndState) + ":" + motionLayout.getProgress();
                                        canvas.drawText(str2, 10.0f, motionLayout.getHeight() - 30, devModeDraw.mTextPaint);
                                        canvas.drawText(str2, 11.0f, motionLayout.getHeight() - 29, devModeDraw.mPaint);
                                    }
                                    Iterator<MotionController> it2 = map.values().iterator();
                                    while (it2.hasNext()) {
                                        MotionController next = it2.next();
                                        int iMax = next.mStartMotionPath.mDrawPath;
                                        ArrayList arrayList3 = next.mMotionPaths;
                                        int size3 = arrayList3.size();
                                        int i15 = i7;
                                        while (i15 < size3) {
                                            Object obj2 = arrayList3.get(i15);
                                            i15++;
                                            iMax = Math.max(iMax, ((MotionPaths) obj2).mDrawPath);
                                        }
                                        int iMax2 = Math.max(iMax, next.mEndMotionPath.mDrawPath);
                                        if (i14 > 0 && iMax2 == 0) {
                                            iMax2 = i10;
                                        }
                                        if (iMax2 != 0) {
                                            float[] fArr2 = devModeDraw.mKeyFramePoints;
                                            if (fArr2 != null) {
                                                double[] timePoints = next.mSpline[i7].getTimePoints();
                                                int[] iArr = devModeDraw.mPathMode;
                                                if (iArr != null) {
                                                    ArrayList arrayList4 = next.mMotionPaths;
                                                    i2 = i7;
                                                    int size4 = arrayList4.size();
                                                    int i16 = i2;
                                                    int i17 = i16;
                                                    while (i16 < size4) {
                                                        Object obj3 = arrayList4.get(i16);
                                                        i16++;
                                                        iArr[i17] = ((MotionPaths) obj3).mMode;
                                                        i17++;
                                                    }
                                                } else {
                                                    i2 = i7;
                                                }
                                                int i18 = i2;
                                                int i19 = i18;
                                                while (i18 < timePoints.length) {
                                                    next.mSpline[i2].getPos(timePoints[i18], next.mInterpolateData);
                                                    next.mStartMotionPath.getCenter(timePoints[i18], next.mInterpolateVariables, next.mInterpolateData, fArr2, i19);
                                                    i19 += 2;
                                                    i18++;
                                                }
                                                i7 = i19 / 2;
                                            } else {
                                                i2 = i7;
                                            }
                                            devModeDraw.mKeyFrameCount = i7;
                                            if (iMax2 >= i10) {
                                                int i20 = duration / 16;
                                                float[] fArr3 = devModeDraw.mPoints;
                                                if (fArr3 == null || fArr3.length != i20 * 2) {
                                                    devModeDraw.mPoints = new float[i20 * 2];
                                                    devModeDraw.mPath = new Path();
                                                }
                                                int i21 = devModeDraw.mShadowTranslate;
                                                float f2 = i21;
                                                canvas.translate(f2, f2);
                                                devModeDraw.mPaint.setColor(1996488704);
                                                devModeDraw.mFillPaint.setColor(1996488704);
                                                devModeDraw.mPaintKeyframes.setColor(1996488704);
                                                devModeDraw.mPaintGraph.setColor(1996488704);
                                                float[] fArr4 = devModeDraw.mPoints;
                                                float f3 = 1.0f / (i20 - 1);
                                                HashMap map2 = next.mAttributesMap;
                                                SplineSet splineSet = map2 == null ? null : (SplineSet) map2.get("translationX");
                                                HashMap map3 = next.mAttributesMap;
                                                float f4 = 1.0f;
                                                SplineSet splineSet2 = map3 == null ? null : (SplineSet) map3.get("translationY");
                                                it = it2;
                                                HashMap map4 = next.mCycleMap;
                                                ViewOscillator viewOscillator2 = map4 == null ? null : (ViewOscillator) map4.get("translationX");
                                                HashMap map5 = next.mCycleMap;
                                                ViewOscillator viewOscillator3 = map5 == null ? null : (ViewOscillator) map5.get("translationY");
                                                i4 = duration;
                                                int i22 = i2;
                                                while (true) {
                                                    motionPaths = next.mStartMotionPath;
                                                    float f5 = Float.NaN;
                                                    i5 = i14;
                                                    if (i22 >= i20) {
                                                        break;
                                                    }
                                                    float fMin = i22 * f3;
                                                    int i23 = i20;
                                                    float f6 = next.mStaggerScale;
                                                    if (f6 != f4) {
                                                        float f7 = next.mStaggerOffset;
                                                        if (fMin < f7) {
                                                            fMin = 0.0f;
                                                        }
                                                        if (fMin > f7) {
                                                            fArr = fArr4;
                                                            f = f3;
                                                            if (fMin < 1.0d) {
                                                                fMin = Math.min((fMin - f7) * f6, f4);
                                                            }
                                                        } else {
                                                            fArr = fArr4;
                                                            f = f3;
                                                        }
                                                    }
                                                    double d2 = fMin;
                                                    Easing easing = motionPaths.mKeyFrameEasing;
                                                    ArrayList arrayList5 = next.mMotionPaths;
                                                    int size5 = arrayList5.size();
                                                    Easing easing2 = easing;
                                                    int i24 = i2;
                                                    float f8 = 0.0f;
                                                    while (i24 < size5) {
                                                        Object obj4 = arrayList5.get(i24);
                                                        i24++;
                                                        ArrayList arrayList6 = arrayList5;
                                                        MotionPaths motionPaths2 = (MotionPaths) obj4;
                                                        int i25 = size5;
                                                        Easing easing3 = motionPaths2.mKeyFrameEasing;
                                                        if (easing3 != null) {
                                                            float f9 = motionPaths2.mTime;
                                                            if (f9 < fMin) {
                                                                f8 = f9;
                                                                easing2 = easing3;
                                                            } else if (Float.isNaN(f5)) {
                                                                f5 = motionPaths2.mTime;
                                                            }
                                                        }
                                                        size5 = i25;
                                                        arrayList5 = arrayList6;
                                                    }
                                                    if (easing2 != null) {
                                                        if (Float.isNaN(f5)) {
                                                            f5 = 1.0f;
                                                        }
                                                        viewOscillator = viewOscillator3;
                                                        d = (((float) easing2.get((fMin - f8) / r17)) * (f5 - f8)) + f8;
                                                    } else {
                                                        viewOscillator = viewOscillator3;
                                                        d = d2;
                                                    }
                                                    next.mSpline[i2].getPos(d, next.mInterpolateData);
                                                    ArcCurveFit arcCurveFit = next.mArcSpline;
                                                    if (arcCurveFit != null) {
                                                        double[] dArr = next.mInterpolateData;
                                                        if (dArr.length > 0) {
                                                            arcCurveFit.getPos(d, dArr);
                                                        }
                                                    }
                                                    ViewOscillator viewOscillator4 = viewOscillator;
                                                    int i26 = i22 * 2;
                                                    int i27 = i22;
                                                    double d3 = d;
                                                    SplineSet splineSet3 = splineSet;
                                                    next.mStartMotionPath.getCenter(d3, next.mInterpolateVariables, next.mInterpolateData, fArr, i26);
                                                    if (viewOscillator2 != null) {
                                                        fArr[i26] = viewOscillator2.get(fMin) + fArr[i26];
                                                    } else if (splineSet3 != null) {
                                                        fArr[i26] = splineSet3.get(fMin) + fArr[i26];
                                                    }
                                                    if (viewOscillator4 != null) {
                                                        int i28 = i26 + 1;
                                                        fArr[i28] = viewOscillator4.get(fMin) + fArr[i28];
                                                    } else if (splineSet2 != null) {
                                                        int i29 = i26 + 1;
                                                        fArr[i29] = splineSet2.get(fMin) + fArr[i29];
                                                    }
                                                    i22 = i27 + 1;
                                                    f4 = 1.0f;
                                                    splineSet = splineSet3;
                                                    viewOscillator3 = viewOscillator4;
                                                    i14 = i5;
                                                    i20 = i23;
                                                    fArr4 = fArr;
                                                    f3 = f;
                                                }
                                                devModeDraw.drawAll(canvas, iMax2, devModeDraw.mKeyFrameCount, next);
                                                devModeDraw.mPaint.setColor(-21965);
                                                devModeDraw.mPaintKeyframes.setColor(-2067046);
                                                devModeDraw.mFillPaint.setColor(-2067046);
                                                devModeDraw.mPaintGraph.setColor(-13391360);
                                                float f10 = -i21;
                                                canvas.translate(f10, f10);
                                                devModeDraw.drawAll(canvas, iMax2, devModeDraw.mKeyFrameCount, next);
                                                char c2 = 5;
                                                if (iMax2 == 5) {
                                                    devModeDraw.mPath.reset();
                                                    int i30 = i2;
                                                    while (i30 <= 50) {
                                                        next.mSpline[i2].getPos(next.getAdjustedPosition(i30 / 50, null), next.mInterpolateData);
                                                        int[] iArr2 = next.mInterpolateVariables;
                                                        double[] dArr2 = next.mInterpolateData;
                                                        float f11 = motionPaths.mX;
                                                        float fCos = motionPaths.mY;
                                                        float f12 = motionPaths.mWidth;
                                                        float f13 = motionPaths.mHeight;
                                                        char c3 = c2;
                                                        int i31 = i2;
                                                        while (i31 < iArr2.length) {
                                                            MotionController motionController2 = next;
                                                            float f14 = (float) dArr2[i31];
                                                            int i32 = iArr2[i31];
                                                            if (i32 == 1) {
                                                                f11 = f14;
                                                            } else if (i32 == 2) {
                                                                fCos = f14;
                                                            } else if (i32 == 3) {
                                                                f12 = f14;
                                                            } else if (i32 == 4) {
                                                                f13 = f14;
                                                            }
                                                            i31++;
                                                            next = motionController2;
                                                        }
                                                        MotionController motionController3 = next;
                                                        if (motionPaths.mRelativeToController != null) {
                                                            double d4 = 0.0f;
                                                            double d5 = f11;
                                                            motionController = motionController3;
                                                            i6 = i30;
                                                            double d6 = fCos;
                                                            float fSin = (float) (((Math.sin(d6) * d5) + d4) - (f12 / 2.0f));
                                                            fCos = (float) ((d4 - (Math.cos(d6) * d5)) - (f13 / 2.0f));
                                                            f11 = fSin;
                                                        } else {
                                                            motionController = motionController3;
                                                            i6 = i30;
                                                        }
                                                        float f15 = f12 + f11;
                                                        float f16 = f13 + fCos;
                                                        Float.isNaN(Float.NaN);
                                                        Float.isNaN(Float.NaN);
                                                        float f17 = f11 + 0.0f;
                                                        float f18 = fCos + 0.0f;
                                                        float f19 = f15 + 0.0f;
                                                        float f20 = f16 + 0.0f;
                                                        float[] fArr5 = devModeDraw.mRectangle;
                                                        fArr5[i2] = f17;
                                                        fArr5[1] = f18;
                                                        fArr5[2] = f19;
                                                        fArr5[3] = f18;
                                                        fArr5[4] = f19;
                                                        fArr5[c3] = f20;
                                                        fArr5[6] = f17;
                                                        fArr5[7] = f20;
                                                        devModeDraw.mPath.moveTo(f17, f18);
                                                        devModeDraw.mPath.lineTo(fArr5[2], fArr5[3]);
                                                        devModeDraw.mPath.lineTo(fArr5[4], fArr5[c3]);
                                                        devModeDraw.mPath.lineTo(fArr5[6], fArr5[7]);
                                                        devModeDraw.mPath.close();
                                                        i30 = i6 + 1;
                                                        next = motionController;
                                                        c2 = c3;
                                                    }
                                                    i3 = 1;
                                                    c = 2;
                                                    devModeDraw.mPaint.setColor(1140850688);
                                                    canvas.translate(2.0f, 2.0f);
                                                    canvas.drawPath(devModeDraw.mPath, devModeDraw.mPaint);
                                                    canvas.translate(-2.0f, -2.0f);
                                                    devModeDraw.mPaint.setColor(-65536);
                                                    canvas.drawPath(devModeDraw.mPath, devModeDraw.mPaint);
                                                    i10 = i3;
                                                    i7 = i2;
                                                    it2 = it;
                                                    duration = i4;
                                                    i14 = i5;
                                                } else {
                                                    i3 = 1;
                                                }
                                            } else {
                                                i3 = i10;
                                                it = it2;
                                                i4 = duration;
                                                i5 = i14;
                                            }
                                            c = 2;
                                            i10 = i3;
                                            i7 = i2;
                                            it2 = it;
                                            duration = i4;
                                            i14 = i5;
                                        }
                                    }
                                    i = i7;
                                    canvas.restore();
                                }
                            }
                            ArrayList<MotionHelper> arrayList7 = this.mDecoratorsHelpers;
                            if (arrayList7 != null) {
                                int size6 = arrayList7.size();
                                int i33 = i;
                                while (i33 < size6) {
                                    MotionHelper motionHelper2 = arrayList7.get(i33);
                                    i33++;
                                    motionHelper2.getClass();
                                }
                            }
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

                        /* JADX WARN: Removed duplicated region for block: B:111:0x01ab  */
                        /* JADX WARN: Removed duplicated region for block: B:117:0x01ba  */
                        /* JADX WARN: Removed duplicated region for block: B:120:0x01c7  */
                        /* JADX WARN: Removed duplicated region for block: B:127:0x01e7  */
                        /* JADX WARN: Removed duplicated region for block: B:132:0x0202  */
                        /* JADX WARN: Removed duplicated region for block: B:143:0x021b  */
                        /* JADX WARN: Removed duplicated region for block: B:62:0x00e4 A[PHI: r3
                          0x00e4: PHI (r3v50 float) = (r3v49 float), (r3v51 float), (r3v51 float) binds: [B:47:0x00b0, B:58:0x00d8, B:60:0x00dc] A[DONT_GENERATE, DONT_INLINE]] */
                        /* JADX WARN: Removed duplicated region for block: B:72:0x0113  */
                        /* JADX WARN: Removed duplicated region for block: B:74:0x011a  */
                        /* JADX WARN: Removed duplicated region for block: B:89:0x014f  */
                        /* JADX WARN: Removed duplicated region for block: B:90:0x0151  */
                        /* JADX WARN: Removed duplicated region for block: B:93:0x015a  */
                        /* JADX WARN: Removed duplicated region for block: B:96:0x0171  */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public void evaluate(boolean z) throws IllegalAccessException, Resources.NotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                            boolean z2;
                            char c;
                            int childCount;
                            Interpolator interpolator;
                            int i;
                            int i2;
                            boolean z3;
                            if (this.mTransitionLastTime == -1) {
                                this.mTransitionLastTime = getNanoTime();
                            }
                            float f = this.mTransitionLastPosition;
                            if (f > 0.0f && f < 1.0f) {
                                this.mCurrentState = -1;
                            }
                            boolean z4 = false;
                            if (this.mKeepAnimating || (this.mInTransition && (z || this.mTransitionGoalPosition != f))) {
                                float fSignum = Math.signum(this.mTransitionGoalPosition - f);
                                long nanoTime = getNanoTime();
                                Interpolator interpolator2 = this.mInterpolator;
                                float f2 = !(interpolator2 instanceof MotionInterpolator) ? (((nanoTime - this.mTransitionLastTime) * fSignum) * 1.0E-9f) / this.mTransitionDuration : 0.0f;
                                float f3 = this.mTransitionLastPosition + f2;
                                if (this.mTransitionInstantly) {
                                    f3 = this.mTransitionGoalPosition;
                                }
                                if ((fSignum <= 0.0f || f3 < this.mTransitionGoalPosition) && (fSignum > 0.0f || f3 > this.mTransitionGoalPosition)) {
                                    z2 = false;
                                } else {
                                    f3 = this.mTransitionGoalPosition;
                                    this.mInTransition = false;
                                    z2 = true;
                                }
                                this.mTransitionLastPosition = f3;
                                this.mTransitionPosition = f3;
                                this.mTransitionLastTime = nanoTime;
                                if (interpolator2 == null || z2) {
                                    this.mLastVelocity = f2;
                                } else if (this.mTemporalInterpolator) {
                                    float interpolation = interpolator2.getInterpolation((nanoTime - this.mAnimationStartTime) * 1.0E-9f);
                                    Interpolator interpolator3 = this.mInterpolator;
                                    StopLogic stopLogic = this.mStopLogic;
                                    c = interpolator3 == stopLogic ? stopLogic.mEngine.isStopped() ? (char) 2 : (char) 1 : (char) 0;
                                    this.mTransitionLastPosition = interpolation;
                                    this.mTransitionLastTime = nanoTime;
                                    Interpolator interpolator4 = this.mInterpolator;
                                    if (interpolator4 instanceof MotionInterpolator) {
                                        float velocity = ((MotionInterpolator) interpolator4).getVelocity();
                                        this.mLastVelocity = velocity;
                                        if (Math.abs(velocity) * this.mTransitionDuration <= EPSILON && c == 2) {
                                            this.mInTransition = false;
                                        }
                                        if (velocity > 0.0f && interpolation >= 1.0f) {
                                            this.mTransitionLastPosition = 1.0f;
                                            this.mInTransition = false;
                                            interpolation = 1.0f;
                                        }
                                        if (velocity >= 0.0f || interpolation > 0.0f) {
                                            f3 = interpolation;
                                        } else {
                                            this.mTransitionLastPosition = 0.0f;
                                            this.mInTransition = false;
                                            f3 = 0.0f;
                                        }
                                        if (Math.abs(this.mLastVelocity) > EPSILON) {
                                            setState(TransitionState.MOVING);
                                        }
                                        if (c != 1) {
                                            if ((fSignum > 0.0f && f3 >= this.mTransitionGoalPosition) || (fSignum <= 0.0f && f3 <= this.mTransitionGoalPosition)) {
                                                f3 = this.mTransitionGoalPosition;
                                                this.mInTransition = false;
                                            }
                                            if (f3 >= 1.0f || f3 <= 0.0f) {
                                                this.mInTransition = false;
                                                setState(TransitionState.FINISHED);
                                            }
                                        }
                                        childCount = getChildCount();
                                        this.mKeepAnimating = false;
                                        long nanoTime2 = getNanoTime();
                                        this.mPostInterpolationPosition = f3;
                                        Interpolator interpolator5 = this.mProgressInterpolator;
                                        float interpolation2 = interpolator5 != null ? f3 : interpolator5.getInterpolation(f3);
                                        interpolator = this.mProgressInterpolator;
                                        if (interpolator != null) {
                                            float interpolation3 = interpolator.getInterpolation((fSignum / this.mTransitionDuration) + f3);
                                            this.mLastVelocity = interpolation3;
                                            this.mLastVelocity = interpolation3 - this.mProgressInterpolator.getInterpolation(f3);
                                        }
                                        for (i = 0; i < childCount; i++) {
                                            View childAt = getChildAt(i);
                                            MotionController motionController = this.mFrameArrayList.get(childAt);
                                            if (motionController != null) {
                                                this.mKeepAnimating = motionController.interpolate(interpolation2, nanoTime2, childAt, this.mKeyCache) | this.mKeepAnimating;
                                            }
                                        }
                                        boolean z5 = (fSignum <= 0.0f && f3 >= this.mTransitionGoalPosition) || (fSignum <= 0.0f && f3 <= this.mTransitionGoalPosition);
                                        if (!this.mKeepAnimating && !this.mInTransition && z5) {
                                            setState(TransitionState.FINISHED);
                                        }
                                        if (this.mMeasureDuringTransition) {
                                            requestLayout();
                                        }
                                        this.mKeepAnimating = (!z5) | this.mKeepAnimating;
                                        if (f3 <= 0.0f && (i2 = this.mBeginState) != -1 && this.mCurrentState != i2) {
                                            this.mCurrentState = i2;
                                            this.mScene.getConstraintSet(i2).applyCustomAttributes(this);
                                            setState(TransitionState.FINISHED);
                                            z4 = true;
                                        }
                                        if (f3 >= 1.0d) {
                                            int i3 = this.mCurrentState;
                                            int i4 = this.mEndState;
                                            if (i3 != i4) {
                                                this.mCurrentState = i4;
                                                this.mScene.getConstraintSet(i4).applyCustomAttributes(this);
                                                setState(TransitionState.FINISHED);
                                                z4 = true;
                                            }
                                        }
                                        if (this.mKeepAnimating || this.mInTransition) {
                                            invalidate();
                                        } else if ((fSignum > 0.0f && f3 == 1.0f) || (fSignum < 0.0f && f3 == 0.0f)) {
                                            setState(TransitionState.FINISHED);
                                        }
                                        if (!this.mKeepAnimating && !this.mInTransition && ((fSignum > 0.0f && f3 == 1.0f) || (fSignum < 0.0f && f3 == 0.0f))) {
                                            onNewStateAttachHandlers();
                                        }
                                    }
                                } else {
                                    float interpolation4 = interpolator2.getInterpolation(f3);
                                    Interpolator interpolator6 = this.mInterpolator;
                                    if (interpolator6 instanceof MotionInterpolator) {
                                        this.mLastVelocity = ((MotionInterpolator) interpolator6).getVelocity();
                                    } else {
                                        this.mLastVelocity = ((interpolator6.getInterpolation(f3 + f2) - interpolation4) * fSignum) / f2;
                                    }
                                    f3 = interpolation4;
                                }
                                c = 0;
                                if (Math.abs(this.mLastVelocity) > EPSILON) {
                                }
                                if (c != 1) {
                                }
                                childCount = getChildCount();
                                this.mKeepAnimating = false;
                                long nanoTime22 = getNanoTime();
                                this.mPostInterpolationPosition = f3;
                                Interpolator interpolator52 = this.mProgressInterpolator;
                                if (interpolator52 != null) {
                                }
                                interpolator = this.mProgressInterpolator;
                                if (interpolator != null) {
                                }
                                while (i < childCount) {
                                }
                                if (fSignum <= 0.0f) {
                                    if (!this.mKeepAnimating) {
                                        setState(TransitionState.FINISHED);
                                    }
                                    if (this.mMeasureDuringTransition) {
                                    }
                                    this.mKeepAnimating = (!z5) | this.mKeepAnimating;
                                    if (f3 <= 0.0f) {
                                        this.mCurrentState = i2;
                                        this.mScene.getConstraintSet(i2).applyCustomAttributes(this);
                                        setState(TransitionState.FINISHED);
                                        z4 = true;
                                    }
                                    if (f3 >= 1.0d) {
                                    }
                                    if (this.mKeepAnimating) {
                                        invalidate();
                                        if (!this.mKeepAnimating) {
                                            onNewStateAttachHandlers();
                                        }
                                    }
                                } else {
                                    if (!this.mKeepAnimating) {
                                    }
                                    if (this.mMeasureDuringTransition) {
                                    }
                                    this.mKeepAnimating = (!z5) | this.mKeepAnimating;
                                    if (f3 <= 0.0f) {
                                    }
                                    if (f3 >= 1.0d) {
                                    }
                                    if (this.mKeepAnimating) {
                                    }
                                }
                            }
                            float f4 = this.mTransitionLastPosition;
                            if (f4 < 1.0f) {
                                if (f4 <= 0.0f) {
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

                        public void fireTransitionCompleted() throws Resources.NotFoundException, NumberFormatException {
                            CopyOnWriteArrayList<TransitionListener> copyOnWriteArrayList;
                            if ((this.mTransitionListener != null || ((copyOnWriteArrayList = this.mTransitionListeners) != null && !copyOnWriteArrayList.isEmpty())) && this.mListenerState == -1) {
                                this.mListenerState = this.mCurrentState;
                                int iIntValue = !this.mTransitionCompleted.isEmpty() ? ((Integer) AlertController$$ExternalSyntheticOutline0.m(1, this.mTransitionCompleted)).intValue() : -1;
                                int i = this.mCurrentState;
                                if (iIntValue != i && i != -1) {
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
                            HashMap<View, MotionController> map = this.mFrameArrayList;
                            View viewById = getViewById(i);
                            MotionController motionController = map.get(viewById);
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
                                int iKeyAt = motionScene.mConstraintSetMap.keyAt(i2);
                                if (constraintSet.matchesLabels(strArr)) {
                                    constraintSet.getStateLabels();
                                    iArr[i] = iKeyAt;
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
                            float velocity = this.mLastVelocity;
                            float f4 = this.mTransitionLastPosition;
                            if (this.mInterpolator != null) {
                                float fSignum = Math.signum(this.mTransitionGoalPosition - f4);
                                float interpolation = this.mInterpolator.getInterpolation(this.mTransitionLastPosition + EPSILON);
                                float interpolation2 = this.mInterpolator.getInterpolation(this.mTransitionLastPosition);
                                velocity = (((interpolation - interpolation2) / EPSILON) * fSignum) / this.mTransitionDuration;
                                f4 = interpolation2;
                            }
                            Interpolator interpolator = this.mInterpolator;
                            if (interpolator instanceof MotionInterpolator) {
                                velocity = ((MotionInterpolator) interpolator).getVelocity();
                            }
                            float f5 = velocity;
                            MotionController motionController2 = this.mFrameArrayList.get(view);
                            if ((i & 1) == 0) {
                                int width = view.getWidth();
                                int height = view.getHeight();
                                float[] fArr4 = motionController2.mVelocity;
                                float adjustedPosition = motionController2.getAdjustedPosition(f4, fArr4);
                                HashMap map = motionController2.mAttributesMap;
                                SplineSet splineSet = map == null ? null : (SplineSet) map.get("translationX");
                                HashMap map2 = motionController2.mAttributesMap;
                                SplineSet splineSet2 = map2 == null ? null : (SplineSet) map2.get("translationY");
                                HashMap map3 = motionController2.mAttributesMap;
                                SplineSet splineSet3 = map3 == null ? null : (SplineSet) map3.get("rotation");
                                HashMap map4 = motionController2.mAttributesMap;
                                c = 1;
                                SplineSet splineSet4 = map4 == null ? null : (SplineSet) map4.get("scaleX");
                                c2 = 0;
                                HashMap map5 = motionController2.mAttributesMap;
                                f3 = f5;
                                SplineSet splineSet5 = map5 == null ? null : (SplineSet) map5.get("scaleY");
                                HashMap map6 = motionController2.mCycleMap;
                                ViewOscillator viewOscillator3 = map6 == null ? null : (ViewOscillator) map6.get("translationX");
                                HashMap map7 = motionController2.mCycleMap;
                                ViewOscillator viewOscillator4 = map7 == null ? null : (ViewOscillator) map7.get("translationY");
                                HashMap map8 = motionController2.mCycleMap;
                                ViewOscillator viewOscillator5 = map8 == null ? null : (ViewOscillator) map8.get("rotation");
                                HashMap map9 = motionController2.mCycleMap;
                                ViewOscillator viewOscillator6 = map9 == null ? null : (ViewOscillator) map9.get("scaleX");
                                HashMap map10 = motionController2.mCycleMap;
                                ViewOscillator viewOscillator7 = map10 == null ? null : (ViewOscillator) map10.get("scaleY");
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
                                    float f6 = fArr5[0];
                                    int i2 = 0;
                                    while (true) {
                                        dArr = motionController3.mInterpolateVelocity;
                                        if (i2 >= dArr.length) {
                                            break;
                                        }
                                        dArr[i2] = dArr[i2] * f6;
                                        i2++;
                                    }
                                    int[] iArr2 = motionController3.mInterpolateVariables;
                                    double[] dArr5 = motionController3.mInterpolateData;
                                    motionController3.mStartMotionPath.getClass();
                                    MotionPaths.setDpDt(f, f2, fArr, iArr2, dArr, dArr5);
                                    velocityMatrix.applyTransform(f, f2, width, height, fArr);
                                } else {
                                    MotionPaths motionPaths = motionController3.mEndMotionPath;
                                    float f7 = motionPaths.mX;
                                    MotionPaths motionPaths2 = motionController3.mStartMotionPath;
                                    float f8 = f7 - motionPaths2.mX;
                                    float f9 = motionPaths.mY - motionPaths2.mY;
                                    float f10 = motionPaths.mWidth - motionPaths2.mWidth;
                                    float f11 = f9 + (motionPaths.mHeight - motionPaths2.mHeight);
                                    fArr[0] = ((f10 + f8) * f) + ((1.0f - f) * f8);
                                    fArr[1] = (f11 * f2) + ((1.0f - f2) * f9);
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
                                f3 = f5;
                                c = 1;
                                c2 = 0;
                                motionController2.getDpDt(f4, f, f2, fArr2);
                            }
                            if (i < 2) {
                                fArr2[c2] = fArr2[c2] * f3;
                                fArr2[c] = fArr2[c] * f3;
                            }
                        }

                        public final boolean handlesTouchEvent(float f, float f2, View view, MotionEvent motionEvent) {
                            boolean z;
                            boolean zOnTouchEvent;
                            if (view instanceof ViewGroup) {
                                ViewGroup viewGroup = (ViewGroup) view;
                                for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                                    if (handlesTouchEvent((r3.getLeft() + f) - view.getScrollX(), (r3.getTop() + f2) - view.getScrollY(), viewGroup.getChildAt(childCount), motionEvent)) {
                                        z = true;
                                        break;
                                    }
                                }
                                z = false;
                            } else {
                                z = false;
                            }
                            if (!z) {
                                this.mBoundsCheck.set(f, f2, (view.getRight() + f) - view.getLeft(), (view.getBottom() + f2) - view.getTop());
                                if (motionEvent.getAction() != 0 || this.mBoundsCheck.contains(motionEvent.getX(), motionEvent.getY())) {
                                    float f3 = -f;
                                    float f4 = -f2;
                                    Matrix matrix = view.getMatrix();
                                    if (matrix.isIdentity()) {
                                        motionEvent.offsetLocation(f3, f4);
                                        zOnTouchEvent = view.onTouchEvent(motionEvent);
                                        motionEvent.offsetLocation(-f3, -f4);
                                    } else {
                                        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                                        motionEventObtain.offsetLocation(f3, f4);
                                        if (this.mInverseMatrix == null) {
                                            this.mInverseMatrix = new Matrix();
                                        }
                                        matrix.invert(this.mInverseMatrix);
                                        motionEventObtain.transform(this.mInverseMatrix);
                                        zOnTouchEvent = view.onTouchEvent(motionEventObtain);
                                        motionEventObtain.recycle();
                                    }
                                    if (zOnTouchEvent) {
                                        return true;
                                    }
                                }
                            }
                            return z;
                        }

                        public final void init(AttributeSet attributeSet) throws Resources.NotFoundException {
                            MotionScene motionScene;
                            IS_IN_EDIT_MODE = isInEditMode();
                            int i = 0;
                            if (attributeSet != null) {
                                TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.MotionLayout);
                                int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
                                boolean z = true;
                                for (int i2 = 0; i2 < indexCount; i2++) {
                                    int index = typedArrayObtainStyledAttributes.getIndex(i2);
                                    if (index == R.styleable.MotionLayout_layoutDescription) {
                                        this.mScene = new MotionScene(getContext(), this, typedArrayObtainStyledAttributes.getResourceId(index, -1));
                                    } else if (index == R.styleable.MotionLayout_currentState) {
                                        this.mCurrentState = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                                    } else if (index == R.styleable.MotionLayout_motionProgress) {
                                        this.mTransitionGoalPosition = typedArrayObtainStyledAttributes.getFloat(index, 0.0f);
                                        this.mInTransition = true;
                                    } else if (index == R.styleable.MotionLayout_applyMotionScene) {
                                        z = typedArrayObtainStyledAttributes.getBoolean(index, z);
                                    } else if (index == R.styleable.MotionLayout_showPaths) {
                                        if (this.mDebugPath == 0) {
                                            this.mDebugPath = typedArrayObtainStyledAttributes.getBoolean(index, false) ? 2 : 0;
                                        }
                                    } else if (index == R.styleable.MotionLayout_motionDebug) {
                                        this.mDebugPath = typedArrayObtainStyledAttributes.getInt(index, 0);
                                    }
                                }
                                typedArrayObtainStyledAttributes.recycle();
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
                                            StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("CHECK: ", name, " ALL VIEWS SHOULD HAVE ID's ");
                                            sbM.append(childAt.getClass().getName());
                                            sbM.append(" does not!");
                                            Log.w(TAG, sbM.toString());
                                        }
                                        if (constraintSet.getConstraint(id) == null) {
                                            StringBuilder sbM2 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("CHECK: ", name, " NO CONSTRAINTS for ");
                                            sbM2.append(Debug.getName(childAt));
                                            Log.w(TAG, sbM2.toString());
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

                        public void jumpToState(int i) throws Resources.NotFoundException, NumberFormatException {
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
                                                public final void run() throws Resources.NotFoundException, NumberFormatException {
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
                        public void onAttachedToWindow() throws Resources.NotFoundException, NumberFormatException {
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
                                        public final void run() throws Resources.NotFoundException, NumberFormatException {
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

                        /* JADX WARN: Removed duplicated region for block: B:61:0x00f2  */
                        @Override // android.view.ViewGroup
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                            boolean z;
                            TouchResponse touchResponse;
                            int i;
                            RectF touchRegion;
                            MotionLayout motionLayout;
                            int currentState;
                            Iterator it;
                            MotionScene motionScene = this.mScene;
                            if (motionScene == null || !this.mInteractionEnabled) {
                                return false;
                            }
                            ViewTransitionController viewTransitionController = motionScene.mViewTransitionController;
                            if (viewTransitionController == null || (currentState = (motionLayout = viewTransitionController.mMotionLayout).getCurrentState()) == -1) {
                                z = false;
                            } else {
                                if (viewTransitionController.mRelatedViews == null) {
                                    viewTransitionController.mRelatedViews = new HashSet();
                                    ArrayList arrayList = viewTransitionController.mViewTransitions;
                                    int size = arrayList.size();
                                    int i2 = 0;
                                    while (i2 < size) {
                                        Object obj = arrayList.get(i2);
                                        i2++;
                                        ViewTransition viewTransition = (ViewTransition) obj;
                                        int childCount = motionLayout.getChildCount();
                                        for (int i3 = 0; i3 < childCount; i3++) {
                                            View childAt = motionLayout.getChildAt(i3);
                                            if (viewTransition.matchesView(childAt)) {
                                                childAt.getId();
                                                viewTransitionController.mRelatedViews.add(childAt);
                                            }
                                        }
                                    }
                                }
                                float x = motionEvent.getX();
                                float y = motionEvent.getY();
                                Rect rect = new Rect();
                                int action = motionEvent.getAction();
                                ArrayList arrayList2 = viewTransitionController.mAnimations;
                                int i4 = 2;
                                if (arrayList2 != null && !arrayList2.isEmpty()) {
                                    ArrayList arrayList3 = viewTransitionController.mAnimations;
                                    int size2 = arrayList3.size();
                                    int i5 = 0;
                                    while (i5 < size2) {
                                        Object obj2 = arrayList3.get(i5);
                                        i5++;
                                        ViewTransition.Animate animate = (ViewTransition.Animate) obj2;
                                        if (action != 1) {
                                            if (action != i4) {
                                                animate.getClass();
                                            } else {
                                                animate.mMC.mView.getHitRect(animate.mTempRec);
                                                if (!animate.mTempRec.contains((int) x, (int) y) && !animate.mReverse) {
                                                    animate.reverse();
                                                }
                                            }
                                        } else if (!animate.mReverse) {
                                            animate.reverse();
                                        }
                                        i4 = 2;
                                    }
                                }
                                z = false;
                                if (action == 0 || action == 1) {
                                    ConstraintSet constraintSet = motionLayout.getConstraintSet(currentState);
                                    ArrayList arrayList4 = viewTransitionController.mViewTransitions;
                                    int size3 = arrayList4.size();
                                    int i6 = 0;
                                    while (i6 < size3) {
                                        Object obj3 = arrayList4.get(i6);
                                        i6++;
                                        ViewTransition viewTransition2 = (ViewTransition) obj3;
                                        int i7 = viewTransition2.mOnStateTransition;
                                        if (i7 == 1) {
                                            if (action == 0) {
                                                it = viewTransitionController.mRelatedViews.iterator();
                                                while (it.hasNext()) {
                                                    View view = (View) it.next();
                                                    if (viewTransition2.matchesView(view)) {
                                                        view.getHitRect(rect);
                                                        if (rect.contains((int) x, (int) y)) {
                                                            viewTransition2.applyTransition(viewTransitionController, viewTransitionController.mMotionLayout, currentState, constraintSet, view);
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (i7 == 2) {
                                            if (action == 1) {
                                                it = viewTransitionController.mRelatedViews.iterator();
                                                while (it.hasNext()) {
                                                }
                                            }
                                        } else if (i7 == 3 && action == 0) {
                                            it = viewTransitionController.mRelatedViews.iterator();
                                            while (it.hasNext()) {
                                            }
                                        }
                                    }
                                }
                            }
                            MotionScene.Transition transition = this.mScene.mCurrentTransition;
                            if (transition == null || transition.mDisable || (touchResponse = transition.mTouchResponse) == null) {
                                return z;
                            }
                            if ((motionEvent.getAction() == 0 && (touchRegion = touchResponse.getTouchRegion(this, new RectF())) != null && !touchRegion.contains(motionEvent.getX(), motionEvent.getY())) || (i = touchResponse.mTouchRegionId) == -1) {
                                return z;
                            }
                            View view2 = this.mRegionView;
                            if (view2 == null || view2.getId() != i) {
                                this.mRegionView = findViewById(i);
                            }
                            if (this.mRegionView == null) {
                                return z;
                            }
                            this.mBoundsCheck.set(r1.getLeft(), this.mRegionView.getTop(), this.mRegionView.getRight(), this.mRegionView.getBottom());
                            return (!this.mBoundsCheck.contains(motionEvent.getX(), motionEvent.getY()) || handlesTouchEvent((float) this.mRegionView.getLeft(), (float) this.mRegionView.getTop(), this.mRegionView, motionEvent)) ? z : onTouchEvent(motionEvent);
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

                        /* JADX WARN: Removed duplicated region for block: B:28:0x004f  */
                        /* JADX WARN: Removed duplicated region for block: B:31:0x0074  */
                        @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public void onMeasure(int i, int i2) throws Resources.NotFoundException, NumberFormatException {
                            boolean z;
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
                            int i3 = transition == null ? -1 : transition.mConstraintSetEnd;
                            if (!z3) {
                                Model model = this.mModel;
                                if (startId != model.mStartId || i3 != model.mEndId) {
                                    if (this.mBeginState != -1) {
                                        super.onMeasure(i, i2);
                                        this.mModel.initFrom(this.mScene.getConstraintSet(startId), this.mScene.getConstraintSet(i3));
                                        this.mModel.reEvaluateState();
                                        Model model2 = this.mModel;
                                        model2.mStartId = startId;
                                        model2.mEndId = i3;
                                        z = false;
                                    } else {
                                        if (z3) {
                                            super.onMeasure(i, i2);
                                        }
                                        z = true;
                                    }
                                }
                            }
                            if (this.mMeasureDuringTransition || z) {
                                int paddingBottom = getPaddingBottom() + getPaddingTop();
                                int width = this.mLayoutWidget.getWidth() + getPaddingRight() + getPaddingLeft();
                                int height = this.mLayoutWidget.getHeight() + paddingBottom;
                                int i4 = this.mWidthMeasureMode;
                                if (i4 == Integer.MIN_VALUE || i4 == 0) {
                                    width = (int) ((this.mPostInterpolationPosition * (this.mEndWrapWidth - r1)) + this.mStartWrapWidth);
                                    requestLayout();
                                }
                                int i5 = this.mHeightMeasureMode;
                                if (i5 == Integer.MIN_VALUE || i5 == 0) {
                                    height = (int) ((this.mPostInterpolationPosition * (this.mEndWrapHeight - r2)) + this.mStartWrapHeight);
                                    requestLayout();
                                }
                                setMeasuredDimension(width, height);
                            }
                            float fSignum = Math.signum(this.mTransitionGoalPosition - this.mTransitionLastPosition);
                            long nanoTime = getNanoTime();
                            Interpolator interpolator = this.mInterpolator;
                            float interpolation = this.mTransitionLastPosition + (!(interpolator instanceof StopLogic) ? (((nanoTime - this.mTransitionLastTime) * fSignum) * 1.0E-9f) / this.mTransitionDuration : 0.0f);
                            if (this.mTransitionInstantly) {
                                interpolation = this.mTransitionGoalPosition;
                            }
                            if ((fSignum <= 0.0f || interpolation < this.mTransitionGoalPosition) && (fSignum > 0.0f || interpolation > this.mTransitionGoalPosition)) {
                                z2 = false;
                            } else {
                                interpolation = this.mTransitionGoalPosition;
                            }
                            if (interpolator != null && !z2) {
                                interpolation = this.mTemporalInterpolator ? interpolator.getInterpolation((nanoTime - this.mAnimationStartTime) * 1.0E-9f) : interpolator.getInterpolation(interpolation);
                            }
                            if ((fSignum > 0.0f && interpolation >= this.mTransitionGoalPosition) || (fSignum <= 0.0f && interpolation <= this.mTransitionGoalPosition)) {
                                interpolation = this.mTransitionGoalPosition;
                            }
                            this.mPostInterpolationPosition = interpolation;
                            int childCount = getChildCount();
                            long nanoTime2 = getNanoTime();
                            Interpolator interpolator2 = this.mProgressInterpolator;
                            if (interpolator2 != null) {
                                interpolation = interpolator2.getInterpolation(interpolation);
                            }
                            float f = interpolation;
                            for (int i6 = 0; i6 < childCount; i6++) {
                                View childAt = getChildAt(i6);
                                MotionController motionController = this.mFrameArrayList.get(childAt);
                                if (motionController != null) {
                                    motionController.interpolate(f, nanoTime2, childAt, this.mKeyCache);
                                }
                            }
                            if (this.mMeasureDuringTransition) {
                                requestLayout();
                            }
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
                        public void onNestedPreScroll(final View view, int i, int i2, int[] iArr, int i3) throws IllegalAccessException, Resources.NotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
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
                                    float fMax = Math.max(Math.min(progress + (f12 != f ? (f9 * f12) / fArr2[0] : (f10 * touchResponse.mTouchDirectionY) / fArr2[1]), 1.0f), f);
                                    if (fMax != motionLayout.getProgress()) {
                                        motionLayout.setProgress(fMax);
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
                            View viewFindViewById;
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
                                viewFindViewById = motionLayout.findViewById(i10);
                                if (viewFindViewById == null) {
                                    Log.e("TouchResponse", "cannot find TouchAnchorId @id/" + Debug.getName(touchResponse.mTouchAnchorId, motionLayout.getContext()));
                                }
                            } else {
                                viewFindViewById = null;
                            }
                            if (viewFindViewById instanceof NestedScrollView) {
                                NestedScrollView nestedScrollView = (NestedScrollView) viewFindViewById;
                                nestedScrollView.setOnTouchListener(new View.OnTouchListener(touchResponse) { // from class: androidx.constraintlayout.motion.widget.TouchResponse.1
                                    public AnonymousClass1(final TouchResponse touchResponse2) {
                                    }

                                    @Override // android.view.View.OnTouchListener
                                    public final boolean onTouch(View view, MotionEvent motionEvent) {
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
                                boolean zIsRtl = isRtl();
                                motionScene.mRtl = zIsRtl;
                                MotionScene.Transition transition = motionScene.mCurrentTransition;
                                if (transition == null || (touchResponse = transition.mTouchResponse) == null) {
                                    return;
                                }
                                touchResponse.setRTL(zIsRtl);
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
                        /* JADX WARN: Removed duplicated region for block: B:185:0x0498  */
                        /* JADX WARN: Removed duplicated region for block: B:186:0x04ba  */
                        /* JADX WARN: Removed duplicated region for block: B:189:0x04d5  */
                        /* JADX WARN: Removed duplicated region for block: B:191:0x04e4  */
                        /* JADX WARN: Removed duplicated region for block: B:219:0x0546  */
                        /* JADX WARN: Removed duplicated region for block: B:223:0x0552  */
                        /* JADX WARN: Removed duplicated region for block: B:93:0x01f7  */
                        /* JADX WARN: Removed duplicated region for block: B:96:0x01fd  */
                        /* JADX WARN: Type inference failed for: r21v15 */
                        /* JADX WARN: Type inference failed for: r21v20 */
                        /* JADX WARN: Type inference failed for: r21v21 */
                        @Override // android.view.View
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public boolean onTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException, NumberFormatException {
                            MotionTracker motionTracker;
                            MotionTracker motionTracker2;
                            TouchResponse touchResponse;
                            char c;
                            char c2;
                            int i;
                            float f;
                            char c3;
                            char c4;
                            float right;
                            float f2;
                            int top;
                            int bottom;
                            int i2;
                            float f3;
                            int i3;
                            MotionLayout motionLayout;
                            boolean z;
                            float f4;
                            float f5;
                            ?? r21;
                            MotionEvent motionEvent2;
                            MotionScene.Transition transition;
                            int i4;
                            RectF rectF;
                            int i5;
                            float f6;
                            float f7;
                            MotionScene motionScene = this.mScene;
                            if (motionScene == null || !this.mInteractionEnabled || !motionScene.supportTouch()) {
                                return super.onTouchEvent(motionEvent);
                            }
                            MotionScene motionScene2 = this.mScene;
                            MotionScene.Transition transition2 = motionScene2.mCurrentTransition;
                            if (transition2 != null && transition2.mDisable) {
                                return super.onTouchEvent(motionEvent);
                            }
                            int currentState = getCurrentState();
                            RectF rectF2 = new RectF();
                            MotionTracker motionTracker3 = motionScene2.mVelocityTracker;
                            MotionLayout motionLayout2 = motionScene2.mMotionLayout;
                            if (motionTracker3 == null) {
                                motionScene2.mVelocityTracker = motionLayout2.obtainVelocityTracker();
                            }
                            VelocityTracker velocityTracker = ((MyTracker) motionScene2.mVelocityTracker).mTracker;
                            if (velocityTracker != null) {
                                velocityTracker.addMovement(motionEvent);
                            }
                            if (currentState != -1) {
                                int action = motionEvent.getAction();
                                if (action == 0) {
                                    motionScene2.mLastTouchX = motionEvent.getRawX();
                                    motionScene2.mLastTouchY = motionEvent.getRawY();
                                    motionScene2.mLastTouchDown = motionEvent;
                                    motionScene2.mIgnoreTouch = false;
                                    TouchResponse touchResponse2 = motionScene2.mCurrentTransition.mTouchResponse;
                                    if (touchResponse2 != null) {
                                        RectF limitBoundsTo = touchResponse2.getLimitBoundsTo(motionLayout2, rectF2);
                                        if (limitBoundsTo == null || limitBoundsTo.contains(motionScene2.mLastTouchDown.getX(), motionScene2.mLastTouchDown.getY())) {
                                            RectF touchRegion = motionScene2.mCurrentTransition.mTouchResponse.getTouchRegion(motionLayout2, rectF2);
                                            if (touchRegion == null || touchRegion.contains(motionScene2.mLastTouchDown.getX(), motionScene2.mLastTouchDown.getY())) {
                                                motionScene2.mMotionOutsideRegion = false;
                                            } else {
                                                motionScene2.mMotionOutsideRegion = true;
                                            }
                                            TouchResponse touchResponse3 = motionScene2.mCurrentTransition.mTouchResponse;
                                            float f8 = motionScene2.mLastTouchX;
                                            float f9 = motionScene2.mLastTouchY;
                                            touchResponse3.mLastTouchX = f8;
                                            touchResponse3.mLastTouchY = f9;
                                        } else {
                                            motionScene2.mLastTouchDown = null;
                                            motionScene2.mIgnoreTouch = true;
                                        }
                                    }
                                } else if (action == 2 && !motionScene2.mIgnoreTouch) {
                                    float rawY = motionEvent.getRawY() - motionScene2.mLastTouchY;
                                    float rawX = motionEvent.getRawX() - motionScene2.mLastTouchX;
                                    if ((rawX != 0.0d || rawY != 0.0d) && (motionEvent2 = motionScene2.mLastTouchDown) != null) {
                                        if (currentState != -1) {
                                            List transitionsWithState = motionScene2.getTransitionsWithState(currentState);
                                            RectF rectF3 = new RectF();
                                            ArrayList arrayList = (ArrayList) transitionsWithState;
                                            int size = arrayList.size();
                                            int i6 = 0;
                                            float f10 = 0.0f;
                                            MotionScene.Transition transition3 = null;
                                            while (i6 < size) {
                                                Object obj = arrayList.get(i6);
                                                int i7 = i6 + 1;
                                                MotionScene.Transition transition4 = (MotionScene.Transition) obj;
                                                if (transition4.mDisable) {
                                                    i4 = i7;
                                                } else {
                                                    TouchResponse touchResponse4 = transition4.mTouchResponse;
                                                    if (touchResponse4 != null) {
                                                        touchResponse4.setRTL(motionScene2.mRtl);
                                                        RectF touchRegion2 = transition4.mTouchResponse.getTouchRegion(motionLayout2, rectF3);
                                                        if (touchRegion2 != null) {
                                                            i4 = i7;
                                                            if (!touchRegion2.contains(motionEvent2.getX(), motionEvent2.getY())) {
                                                            }
                                                        } else {
                                                            i4 = i7;
                                                        }
                                                        RectF limitBoundsTo2 = transition4.mTouchResponse.getLimitBoundsTo(motionLayout2, rectF3);
                                                        if (limitBoundsTo2 == null || limitBoundsTo2.contains(motionEvent2.getX(), motionEvent2.getY())) {
                                                            TouchResponse touchResponse5 = transition4.mTouchResponse;
                                                            float fAtan2 = (touchResponse5.mTouchDirectionY * rawY) + (touchResponse5.mTouchDirectionX * rawX);
                                                            if (touchResponse5.mIsRotateMode) {
                                                                float x = motionEvent2.getX();
                                                                transition4.mTouchResponse.getClass();
                                                                float y = motionEvent2.getY();
                                                                transition4.mTouchResponse.getClass();
                                                                rectF = rectF3;
                                                                f6 = rawY;
                                                                f7 = rawX;
                                                                i5 = size;
                                                                fAtan2 = ((float) (Math.atan2(rawY + r11, rawX + r8) - Math.atan2(x - 0.5f, y - 0.5f))) * 10.0f;
                                                            } else {
                                                                rectF = rectF3;
                                                                i5 = size;
                                                                f6 = rawY;
                                                                f7 = rawX;
                                                            }
                                                            float f11 = fAtan2 * (transition4.mConstraintSetEnd == currentState ? -1.0f : 1.1f);
                                                            if (f11 > f10) {
                                                                transition3 = transition4;
                                                                f10 = f11;
                                                            }
                                                        }
                                                    } else {
                                                        rectF = rectF3;
                                                        i4 = i7;
                                                        i5 = size;
                                                        f6 = rawY;
                                                        f7 = rawX;
                                                    }
                                                    i6 = i4;
                                                    rectF3 = rectF;
                                                    rawY = f6;
                                                    rawX = f7;
                                                    size = i5;
                                                }
                                                i6 = i4;
                                            }
                                            transition = transition3;
                                        } else {
                                            transition = motionScene2.mCurrentTransition;
                                        }
                                        if (transition != null) {
                                            setTransition(transition);
                                            RectF touchRegion3 = motionScene2.mCurrentTransition.mTouchResponse.getTouchRegion(motionLayout2, rectF2);
                                            motionScene2.mMotionOutsideRegion = (touchRegion3 == null || touchRegion3.contains(motionScene2.mLastTouchDown.getX(), motionScene2.mLastTouchDown.getY())) ? false : true;
                                            TouchResponse touchResponse6 = motionScene2.mCurrentTransition.mTouchResponse;
                                            float f12 = motionScene2.mLastTouchX;
                                            float f13 = motionScene2.mLastTouchY;
                                            touchResponse6.mLastTouchX = f12;
                                            touchResponse6.mLastTouchY = f13;
                                            touchResponse6.mDragStarted = false;
                                        }
                                        if (!motionScene2.mIgnoreTouch) {
                                        }
                                    }
                                }
                            } else if (!motionScene2.mIgnoreTouch) {
                                MotionScene.Transition transition5 = motionScene2.mCurrentTransition;
                                if (transition5 != null && (touchResponse = transition5.mTouchResponse) != null && !motionScene2.mMotionOutsideRegion) {
                                    MotionTracker motionTracker4 = motionScene2.mVelocityTracker;
                                    boolean z2 = touchResponse.mIsRotateMode;
                                    float[] fArr = touchResponse.mAnchorDpDt;
                                    MotionLayout motionLayout3 = touchResponse.mMotionLayout;
                                    if (z2) {
                                        MyTracker myTracker = (MyTracker) motionTracker4;
                                        VelocityTracker velocityTracker2 = myTracker.mTracker;
                                        if (velocityTracker2 != null) {
                                            velocityTracker2.addMovement(motionEvent);
                                        }
                                        int action2 = motionEvent.getAction();
                                        if (action2 != 0) {
                                            int[] iArr = touchResponse.mTempLoc;
                                            if (action2 == 1) {
                                                touchResponse.mDragStarted = false;
                                                VelocityTracker velocityTracker3 = myTracker.mTracker;
                                                if (velocityTracker3 != null) {
                                                    velocityTracker3.computeCurrentVelocity(16);
                                                }
                                                VelocityTracker velocityTracker4 = myTracker.mTracker;
                                                float xVelocity = velocityTracker4 != null ? velocityTracker4.getXVelocity() : 0.0f;
                                                VelocityTracker velocityTracker5 = myTracker.mTracker;
                                                float yVelocity = velocityTracker5 != null ? velocityTracker5.getYVelocity() : 0.0f;
                                                float progress = motionLayout3.getProgress();
                                                float width = motionLayout3.getWidth() / 2.0f;
                                                float height = motionLayout3.getHeight() / 2.0f;
                                                int i8 = touchResponse.mRotationCenterId;
                                                if (i8 != -1) {
                                                    View viewFindViewById = motionLayout3.findViewById(i8);
                                                    motionLayout3.getLocationOnScreen(iArr);
                                                    right = iArr[0] + ((viewFindViewById.getRight() + viewFindViewById.getLeft()) / 2.0f);
                                                    f2 = iArr[1];
                                                    top = viewFindViewById.getTop();
                                                    bottom = viewFindViewById.getBottom();
                                                } else {
                                                    int i9 = touchResponse.mTouchAnchorId;
                                                    if (i9 != -1) {
                                                        View viewFindViewById2 = motionLayout3.findViewById(motionLayout3.getMotionController(i9).mStartMotionPath.mAnimateRelativeTo);
                                                        motionLayout3.getLocationOnScreen(iArr);
                                                        right = iArr[0] + ((viewFindViewById2.getRight() + viewFindViewById2.getLeft()) / 2.0f);
                                                        f2 = iArr[1];
                                                        top = viewFindViewById2.getTop();
                                                        bottom = viewFindViewById2.getBottom();
                                                    }
                                                    float rawX2 = motionEvent.getRawX() - width;
                                                    double degrees = Math.toDegrees(Math.atan2(motionEvent.getRawY() - height, rawX2));
                                                    i2 = touchResponse.mTouchAnchorId;
                                                    if (i2 == -1) {
                                                        touchResponse.mMotionLayout.getAnchorDpDt(i2, progress, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                                                        fArr[1] = (float) Math.toDegrees(fArr[1]);
                                                    } else {
                                                        fArr[1] = 360.0f;
                                                    }
                                                    float degrees2 = ((float) (Math.toDegrees(Math.atan2(yVelocity + r6, xVelocity + rawX2)) - degrees)) * 62.5f;
                                                    f3 = Float.isNaN(degrees2) ? (((degrees2 * 3.0f) * touchResponse.mDragScale) / fArr[1]) + progress : progress;
                                                    if (f3 == 0.0f && f3 != 1.0f && (i3 = touchResponse.mOnTouchUp) != 3) {
                                                        float fAbs = (degrees2 * touchResponse.mDragScale) / fArr[1];
                                                        float f14 = ((double) f3) < 0.5d ? 0.0f : 1.0f;
                                                        if (i3 == 6) {
                                                            if (progress + fAbs < 0.0f) {
                                                                fAbs = Math.abs(fAbs);
                                                            }
                                                            f14 = 1.0f;
                                                        }
                                                        if (touchResponse.mOnTouchUp == 7) {
                                                            if (progress + fAbs > 1.0f) {
                                                                fAbs = -Math.abs(fAbs);
                                                            }
                                                            f14 = 0.0f;
                                                        }
                                                        motionLayout3.touchAnimateTo(touchResponse.mOnTouchUp, f14, fAbs * 3.0f);
                                                        if (0.0f >= progress || 1.0f <= progress) {
                                                            motionLayout3.setState(TransitionState.FINISHED);
                                                        }
                                                    } else if (0.0f < f3 || 1.0f <= f3) {
                                                        motionLayout3.setState(TransitionState.FINISHED);
                                                    }
                                                }
                                                float f15 = right;
                                                height = ((bottom + top) / 2.0f) + f2;
                                                width = f15;
                                                float rawX22 = motionEvent.getRawX() - width;
                                                double degrees3 = Math.toDegrees(Math.atan2(motionEvent.getRawY() - height, rawX22));
                                                i2 = touchResponse.mTouchAnchorId;
                                                if (i2 == -1) {
                                                }
                                                float degrees22 = ((float) (Math.toDegrees(Math.atan2(yVelocity + r6, xVelocity + rawX22)) - degrees3)) * 62.5f;
                                                if (Float.isNaN(degrees22)) {
                                                }
                                                if (f3 == 0.0f) {
                                                    if (0.0f < f3) {
                                                        motionLayout3.setState(TransitionState.FINISHED);
                                                    }
                                                }
                                            } else if (action2 == 2) {
                                                motionEvent.getRawY();
                                                motionEvent.getRawX();
                                                float width2 = motionLayout3.getWidth() / 2.0f;
                                                float height2 = motionLayout3.getHeight() / 2.0f;
                                                int i10 = touchResponse.mRotationCenterId;
                                                if (i10 != -1) {
                                                    View viewFindViewById3 = motionLayout3.findViewById(i10);
                                                    motionLayout3.getLocationOnScreen(iArr);
                                                    height2 = ((viewFindViewById3.getBottom() + viewFindViewById3.getTop()) / 2.0f) + iArr[1];
                                                    width2 = iArr[0] + ((viewFindViewById3.getRight() + viewFindViewById3.getLeft()) / 2.0f);
                                                } else {
                                                    int i11 = touchResponse.mTouchAnchorId;
                                                    if (i11 != -1) {
                                                        if (motionLayout3.findViewById(motionLayout3.getMotionController(i11).mStartMotionPath.mAnimateRelativeTo) == null) {
                                                            Log.e("TouchResponse", "could not find view to animate to");
                                                        } else {
                                                            motionLayout3.getLocationOnScreen(iArr);
                                                            width2 = iArr[0] + ((r11.getRight() + r11.getLeft()) / 2.0f);
                                                            height2 = iArr[1] + ((r11.getBottom() + r11.getTop()) / 2.0f);
                                                        }
                                                    }
                                                }
                                                float rawX3 = motionEvent.getRawX() - width2;
                                                float rawY2 = motionEvent.getRawY() - height2;
                                                float fAtan22 = (float) (((Math.atan2(motionEvent.getRawY() - height2, motionEvent.getRawX() - width2) - Math.atan2(touchResponse.mLastTouchY - height2, touchResponse.mLastTouchX - width2)) * 180.0d) / 3.141592653589793d);
                                                if (fAtan22 > 330.0f) {
                                                    fAtan22 -= 360.0f;
                                                } else if (fAtan22 < -330.0f) {
                                                    fAtan22 += 360.0f;
                                                }
                                                if (Math.abs(fAtan22) > 0.01d || touchResponse.mDragStarted) {
                                                    float progress2 = motionLayout3.getProgress();
                                                    if (touchResponse.mDragStarted) {
                                                        motionLayout = motionLayout3;
                                                        z = true;
                                                    } else {
                                                        z = true;
                                                        touchResponse.mDragStarted = true;
                                                        motionLayout = motionLayout3;
                                                        motionLayout.setProgress(progress2);
                                                    }
                                                    int i12 = touchResponse.mTouchAnchorId;
                                                    if (i12 != -1) {
                                                        boolean z3 = z;
                                                        f4 = fAtan22;
                                                        f5 = progress2;
                                                        touchResponse.mMotionLayout.getAnchorDpDt(i12, f5, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                                                        fArr[z3 ? 1 : 0] = (float) Math.toDegrees(fArr[z3 ? 1 : 0]);
                                                        r21 = z3;
                                                    } else {
                                                        f4 = fAtan22;
                                                        f5 = progress2;
                                                        boolean z4 = z;
                                                        fArr[z4 ? 1 : 0] = 360.0f;
                                                        r21 = z4;
                                                    }
                                                    float fMax = Math.max(Math.min(((touchResponse.mDragScale * f4) / fArr[r21]) + f5, 1.0f), 0.0f);
                                                    float progress3 = motionLayout.getProgress();
                                                    if (fMax != progress3) {
                                                        if (progress3 == 0.0f || progress3 == 1.0f) {
                                                            motionLayout.endTrigger(progress3 == 0.0f);
                                                        }
                                                        motionLayout.setProgress(fMax);
                                                        VelocityTracker velocityTracker6 = myTracker.mTracker;
                                                        if (velocityTracker6 != null) {
                                                            velocityTracker6.computeCurrentVelocity(1000);
                                                        }
                                                        VelocityTracker velocityTracker7 = myTracker.mTracker;
                                                        float xVelocity2 = velocityTracker7 != null ? velocityTracker7.getXVelocity() : 0.0f;
                                                        VelocityTracker velocityTracker8 = myTracker.mTracker;
                                                        double yVelocity2 = velocityTracker8 != null ? velocityTracker8.getYVelocity() : 0.0f;
                                                        double d = xVelocity2;
                                                        motionLayout.mLastVelocity = (float) Math.toDegrees((float) ((Math.sin(Math.atan2(yVelocity2, d) - r7) * Math.hypot(yVelocity2, d)) / Math.hypot(rawX3, rawY2)));
                                                    } else {
                                                        motionLayout.mLastVelocity = 0.0f;
                                                    }
                                                    touchResponse.mLastTouchX = motionEvent.getRawX();
                                                    touchResponse.mLastTouchY = motionEvent.getRawY();
                                                }
                                            }
                                        } else {
                                            touchResponse.mLastTouchX = motionEvent.getRawX();
                                            touchResponse.mLastTouchY = motionEvent.getRawY();
                                            touchResponse.mDragStarted = false;
                                        }
                                    } else {
                                        MyTracker myTracker2 = (MyTracker) motionTracker4;
                                        VelocityTracker velocityTracker9 = myTracker2.mTracker;
                                        if (velocityTracker9 != null) {
                                            velocityTracker9.addMovement(motionEvent);
                                        }
                                        int action3 = motionEvent.getAction();
                                        if (action3 == 0) {
                                            touchResponse.mLastTouchX = motionEvent.getRawX();
                                            touchResponse.mLastTouchY = motionEvent.getRawY();
                                            touchResponse.mDragStarted = false;
                                        } else if (action3 == 1) {
                                            touchResponse.mDragStarted = false;
                                            VelocityTracker velocityTracker10 = myTracker2.mTracker;
                                            if (velocityTracker10 != null) {
                                                velocityTracker10.computeCurrentVelocity(1000);
                                            }
                                            VelocityTracker velocityTracker11 = myTracker2.mTracker;
                                            float xVelocity3 = velocityTracker11 != null ? velocityTracker11.getXVelocity() : 0.0f;
                                            VelocityTracker velocityTracker12 = myTracker2.mTracker;
                                            float yVelocity3 = velocityTracker12 != null ? velocityTracker12.getYVelocity() : 0.0f;
                                            float progress4 = motionLayout3.getProgress();
                                            int i13 = touchResponse.mTouchAnchorId;
                                            if (i13 != -1) {
                                                touchResponse.mMotionLayout.getAnchorDpDt(i13, progress4, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                                                c = 1;
                                                c2 = 0;
                                            } else {
                                                float fMin = Math.min(motionLayout3.getWidth(), motionLayout3.getHeight());
                                                c = 1;
                                                fArr[1] = touchResponse.mTouchDirectionY * fMin;
                                                c2 = 0;
                                                fArr[0] = fMin * touchResponse.mTouchDirectionX;
                                            }
                                            float fAbs2 = touchResponse.mTouchDirectionX != 0.0f ? xVelocity3 / fArr[c2] : yVelocity3 / fArr[c];
                                            float f16 = !Float.isNaN(fAbs2) ? (fAbs2 / 3.0f) + progress4 : progress4;
                                            if (f16 != 0.0f && f16 != 1.0f && (i = touchResponse.mOnTouchUp) != 3) {
                                                float f17 = ((double) f16) < 0.5d ? 0.0f : 1.0f;
                                                if (i == 6) {
                                                    if (progress4 + fAbs2 < 0.0f) {
                                                        fAbs2 = Math.abs(fAbs2);
                                                    }
                                                    f17 = 1.0f;
                                                }
                                                if (touchResponse.mOnTouchUp == 7) {
                                                    if (progress4 + fAbs2 > 1.0f) {
                                                        fAbs2 = -Math.abs(fAbs2);
                                                    }
                                                    f17 = 0.0f;
                                                }
                                                motionLayout3.touchAnimateTo(touchResponse.mOnTouchUp, f17, fAbs2);
                                                if (0.0f >= progress4 || 1.0f <= progress4) {
                                                    motionLayout3.setState(TransitionState.FINISHED);
                                                }
                                            } else if (0.0f >= f16 || 1.0f <= f16) {
                                                motionLayout3.setState(TransitionState.FINISHED);
                                            }
                                        } else if (action3 == 2) {
                                            float rawY3 = motionEvent.getRawY() - touchResponse.mLastTouchY;
                                            float rawX4 = motionEvent.getRawX() - touchResponse.mLastTouchX;
                                            if (Math.abs((touchResponse.mTouchDirectionY * rawY3) + (touchResponse.mTouchDirectionX * rawX4)) > touchResponse.mDragThreshold || touchResponse.mDragStarted) {
                                                float progress5 = motionLayout3.getProgress();
                                                if (!touchResponse.mDragStarted) {
                                                    touchResponse.mDragStarted = true;
                                                    motionLayout3.setProgress(progress5);
                                                }
                                                int i14 = touchResponse.mTouchAnchorId;
                                                if (i14 != -1) {
                                                    f = progress5;
                                                    touchResponse.mMotionLayout.getAnchorDpDt(i14, f, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                                                    c3 = 1;
                                                    c4 = 0;
                                                } else {
                                                    f = progress5;
                                                    float fMin2 = Math.min(motionLayout3.getWidth(), motionLayout3.getHeight());
                                                    c3 = 1;
                                                    fArr[1] = touchResponse.mTouchDirectionY * fMin2;
                                                    c4 = 0;
                                                    fArr[0] = fMin2 * touchResponse.mTouchDirectionX;
                                                }
                                                if (Math.abs(((touchResponse.mTouchDirectionY * fArr[c3]) + (touchResponse.mTouchDirectionX * fArr[c4])) * touchResponse.mDragScale) < 0.01d) {
                                                    fArr[0] = 0.01f;
                                                    fArr[c3] = 0.01f;
                                                }
                                                float fMax2 = Math.max(Math.min(f + (touchResponse.mTouchDirectionX != 0.0f ? rawX4 / fArr[0] : rawY3 / fArr[c3]), 1.0f), 0.0f);
                                                if (touchResponse.mOnTouchUp == 6) {
                                                    fMax2 = Math.max(fMax2, 0.01f);
                                                }
                                                if (touchResponse.mOnTouchUp == 7) {
                                                    fMax2 = Math.min(fMax2, 0.99f);
                                                }
                                                float progress6 = motionLayout3.getProgress();
                                                if (fMax2 != progress6) {
                                                    if (progress6 == 0.0f || progress6 == 1.0f) {
                                                        motionLayout3.endTrigger(progress6 == 0.0f);
                                                    }
                                                    motionLayout3.setProgress(fMax2);
                                                    VelocityTracker velocityTracker13 = myTracker2.mTracker;
                                                    if (velocityTracker13 != null) {
                                                        velocityTracker13.computeCurrentVelocity(1000);
                                                    }
                                                    VelocityTracker velocityTracker14 = myTracker2.mTracker;
                                                    float xVelocity4 = velocityTracker14 != null ? velocityTracker14.getXVelocity() : 0.0f;
                                                    VelocityTracker velocityTracker15 = myTracker2.mTracker;
                                                    motionLayout3.mLastVelocity = touchResponse.mTouchDirectionX != 0.0f ? xVelocity4 / fArr[0] : (velocityTracker15 != null ? velocityTracker15.getYVelocity() : 0.0f) / fArr[1];
                                                } else {
                                                    motionLayout3.mLastVelocity = 0.0f;
                                                }
                                                touchResponse.mLastTouchX = motionEvent.getRawX();
                                                touchResponse.mLastTouchY = motionEvent.getRawY();
                                            }
                                        }
                                    }
                                }
                                motionScene2.mLastTouchX = motionEvent.getRawX();
                                motionScene2.mLastTouchY = motionEvent.getRawY();
                                if (motionEvent.getAction() == 1 && (motionTracker = motionScene2.mVelocityTracker) != null) {
                                    MyTracker myTracker3 = (MyTracker) motionTracker;
                                    VelocityTracker velocityTracker16 = myTracker3.mTracker;
                                    if (velocityTracker16 != null) {
                                        velocityTracker16.recycle();
                                        motionTracker2 = null;
                                        myTracker3.mTracker = null;
                                    } else {
                                        motionTracker2 = null;
                                    }
                                    motionScene2.mVelocityTracker = motionTracker2;
                                    int i15 = this.mCurrentState;
                                    if (i15 != -1) {
                                        motionScene2.autoTransition(i15, this);
                                    }
                                }
                            }
                            MotionScene.Transition transition6 = this.mScene.mCurrentTransition;
                            if ((transition6.mTransitionFlags & 4) != 0) {
                                return transition6.mTouchResponse.mDragStarted;
                            }
                            return true;
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
                        public void rebuildMotion() throws Resources.NotFoundException {
                            Log.e(TAG, "This method is deprecated. Please call rebuildScene() instead.");
                            rebuildScene();
                        }

                        public void rebuildScene() throws Resources.NotFoundException {
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

                        public void rotateTo(int i, int i2) throws NumberFormatException {
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

                        public void scheduleTransitionTo(int i) throws Resources.NotFoundException, NumberFormatException {
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

                        public void setInterpolatedProgress(float f) throws Resources.NotFoundException, NumberFormatException {
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

                        public void setProgress(float f, float f2) throws Resources.NotFoundException, NumberFormatException {
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

                        public void setScene(MotionScene motionScene) throws Resources.NotFoundException {
                            TouchResponse touchResponse;
                            this.mScene = motionScene;
                            boolean zIsRtl = isRtl();
                            motionScene.mRtl = zIsRtl;
                            MotionScene.Transition transition = motionScene.mCurrentTransition;
                            if (transition != null && (touchResponse = transition.mTouchResponse) != null) {
                                touchResponse.setRTL(zIsRtl);
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

                        public void setState(TransitionState transitionState) throws Resources.NotFoundException, NumberFormatException {
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
                            int iOrdinal = transitionState3.ordinal();
                            if (iOrdinal != 0 && iOrdinal != 1) {
                                if (iOrdinal == 2 && transitionState == transitionState2) {
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

                        public void setTransition(int i, int i2) throws Resources.NotFoundException, NumberFormatException {
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

                        public void setTransitionState(Bundle bundle) throws Resources.NotFoundException, NumberFormatException {
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

                        /* JADX WARN: Removed duplicated region for block: B:26:0x0068  */
                        /* JADX WARN: Removed duplicated region for block: B:27:0x007e  */
                        /* JADX WARN: Removed duplicated region for block: B:36:0x00c4  */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public void touchAnimateTo(int i, float f, float f2) {
                            TouchResponse touchResponse;
                            TouchResponse touchResponse2;
                            TouchResponse touchResponse3;
                            TouchResponse touchResponse4;
                            TouchResponse touchResponse5;
                            TouchResponse touchResponse6;
                            TouchResponse touchResponse7;
                            TouchResponse touchResponse8;
                            if (this.mScene == null || this.mTransitionLastPosition == f) {
                                return;
                            }
                            this.mTemporalInterpolator = true;
                            this.mAnimationStartTime = getNanoTime();
                            float duration = this.mScene.getDuration() / 1000.0f;
                            this.mTransitionDuration = duration;
                            this.mTransitionGoalPosition = f;
                            this.mInTransition = true;
                            float f3 = 0.0f;
                            if (i == 0 || i == 1 || i == 2) {
                                float f4 = (i == 1 || i == 7) ? 0.0f : (i == 2 || i == 6) ? 1.0f : f;
                                MotionScene motionScene = this.mScene;
                                MotionScene.Transition transition = motionScene.mCurrentTransition;
                                if (((transition == null || (touchResponse7 = transition.mTouchResponse) == null) ? 0 : touchResponse7.mAutoCompleteMode) == 0) {
                                    StopLogic stopLogic = this.mStopLogic;
                                    float f5 = this.mTransitionLastPosition;
                                    float maxAcceleration = motionScene.getMaxAcceleration();
                                    MotionScene.Transition transition2 = this.mScene.mCurrentTransition;
                                    if (transition2 != null && (touchResponse6 = transition2.mTouchResponse) != null) {
                                        f3 = touchResponse6.mMaxVelocity;
                                    }
                                    stopLogic.config(f5, f4, f2, duration, maxAcceleration, f3);
                                } else {
                                    float f6 = 0.0f;
                                    StopLogic stopLogic2 = this.mStopLogic;
                                    float f7 = this.mTransitionLastPosition;
                                    float f8 = (transition == null || (touchResponse5 = transition.mTouchResponse) == null) ? 0.0f : touchResponse5.mSpringMass;
                                    float f9 = (transition == null || (touchResponse4 = transition.mTouchResponse) == null) ? 0.0f : touchResponse4.mSpringStiffness;
                                    float f10 = (transition == null || (touchResponse3 = transition.mTouchResponse) == null) ? 0.0f : touchResponse3.mSpringDamping;
                                    if (transition != null && (touchResponse2 = transition.mTouchResponse) != null) {
                                        f6 = touchResponse2.mSpringStopThreshold;
                                    }
                                    stopLogic2.springConfig((transition == null || (touchResponse = transition.mTouchResponse) == null) ? 0 : touchResponse.mSpringBoundary, f7, f4, f8, f9, f10, f6);
                                }
                                int i2 = this.mCurrentState;
                                this.mTransitionGoalPosition = f4;
                                this.mCurrentState = i2;
                                this.mInterpolator = this.mStopLogic;
                            } else if (i == 4) {
                                DecelerateInterpolator decelerateInterpolator = this.mDecelerateLogic;
                                float f11 = this.mTransitionLastPosition;
                                float maxAcceleration2 = this.mScene.getMaxAcceleration();
                                decelerateInterpolator.mInitialV = f2;
                                decelerateInterpolator.mCurrentP = f11;
                                decelerateInterpolator.mMaxA = maxAcceleration2;
                                this.mInterpolator = this.mDecelerateLogic;
                            } else if (i == 5) {
                                float f12 = this.mTransitionLastPosition;
                                float maxAcceleration3 = this.mScene.getMaxAcceleration();
                                if (f2 > 0.0f) {
                                    float f13 = f2 / maxAcceleration3;
                                    if (((f2 * f13) - (((maxAcceleration3 * f13) * f13) / 2.0f)) + f12 > 1.0f) {
                                        DecelerateInterpolator decelerateInterpolator2 = this.mDecelerateLogic;
                                        float f14 = this.mTransitionLastPosition;
                                        float maxAcceleration4 = this.mScene.getMaxAcceleration();
                                        decelerateInterpolator2.mInitialV = f2;
                                        decelerateInterpolator2.mCurrentP = f14;
                                        decelerateInterpolator2.mMaxA = maxAcceleration4;
                                        this.mInterpolator = this.mDecelerateLogic;
                                    } else {
                                        StopLogic stopLogic3 = this.mStopLogic;
                                        float f15 = this.mTransitionLastPosition;
                                        float f16 = this.mTransitionDuration;
                                        float maxAcceleration5 = this.mScene.getMaxAcceleration();
                                        MotionScene.Transition transition3 = this.mScene.mCurrentTransition;
                                        stopLogic3.config(f15, f, f2, f16, maxAcceleration5, (transition3 == null || (touchResponse8 = transition3.mTouchResponse) == null) ? 0.0f : touchResponse8.mMaxVelocity);
                                        this.mLastVelocity = 0.0f;
                                        int i3 = this.mCurrentState;
                                        this.mTransitionGoalPosition = f;
                                        this.mCurrentState = i3;
                                        this.mInterpolator = this.mStopLogic;
                                    }
                                } else {
                                    float f17 = (-f2) / maxAcceleration3;
                                    if ((((maxAcceleration3 * f17) * f17) / 2.0f) + (f2 * f17) + f12 < 0.0f) {
                                    }
                                }
                            } else if (i == 6 || i == 7) {
                            }
                            this.mTransitionInstantly = false;
                            this.mAnimationStartTime = getNanoTime();
                            invalidate();
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

                        public void transitionToState(int i) throws Resources.NotFoundException, NumberFormatException {
                            if (isAttachedToWindow()) {
                                transitionToState(i, -1, -1);
                                return;
                            }
                            if (this.mStateCache == null) {
                                this.mStateCache = new StateCache();
                            }
                            this.mStateCache.mEndState = i;
                        }

                        public void updateState(int i, ConstraintSet constraintSet) throws Resources.NotFoundException, NumberFormatException {
                            MotionScene motionScene = this.mScene;
                            if (motionScene != null) {
                                motionScene.mConstraintSetMap.put(i, constraintSet);
                            }
                            updateState();
                            if (this.mCurrentState == i) {
                                constraintSet.applyTo(this);
                            }
                        }

                        public void updateStateAnimate(int i, ConstraintSet constraintSet, int i2) throws Resources.NotFoundException, NumberFormatException {
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

                        public void transitionToState(int i, int i2) throws Resources.NotFoundException, NumberFormatException {
                            if (!isAttachedToWindow()) {
                                if (this.mStateCache == null) {
                                    this.mStateCache = new StateCache();
                                }
                                this.mStateCache.mEndState = i;
                                return;
                            }
                            transitionToState(i, -1, -1, i2);
                        }

                        public void updateState() throws Resources.NotFoundException, NumberFormatException {
                            this.mModel.initFrom(this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
                            rebuildScene();
                        }

                        @Override // androidx.constraintlayout.widget.ConstraintLayout
                        public void setState(int i, int i2, int i3) throws Resources.NotFoundException, NumberFormatException {
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

                        public void setProgress(float f) throws Resources.NotFoundException, NumberFormatException {
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

                        public void transitionToState(int i, int i2, int i3) throws Resources.NotFoundException, NumberFormatException {
                            transitionToState(i, i2, i3, -1);
                        }

                        public void transitionToState(int i, int i2, int i3, int i4) throws Resources.NotFoundException, NumberFormatException {
                            StateSet stateSet;
                            int iConvertToConstraintSet;
                            MotionScene motionScene = this.mScene;
                            if (motionScene != null && (stateSet = motionScene.mStateSet) != null && (iConvertToConstraintSet = stateSet.convertToConstraintSet(this.mCurrentState, i, i2, i3)) != -1) {
                                i = iConvertToConstraintSet;
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
                                float fMin = Float.MAX_VALUE;
                                float fMax = -3.4028235E38f;
                                for (int i12 = 0; i12 < childCount; i12++) {
                                    MotionPaths motionPaths2 = this.mFrameArrayList.get(getChildAt(i12)).mEndMotionPath;
                                    float f2 = motionPaths2.mY + motionPaths2.mX;
                                    fMin = Math.min(fMin, f2);
                                    fMax = Math.max(fMax, f2);
                                }
                                for (int i13 = 0; i13 < childCount; i13++) {
                                    MotionController motionController5 = this.mFrameArrayList.get(getChildAt(i13));
                                    MotionPaths motionPaths3 = motionController5.mEndMotionPath;
                                    float f3 = motionPaths3.mX;
                                    float f4 = motionPaths3.mY;
                                    motionController5.mStaggerScale = 1.0f / (1.0f - f);
                                    motionController5.mStaggerOffset = f - ((((f3 + f4) - fMin) * f) / (fMax - fMin));
                                }
                            }
                            this.mTransitionPosition = 0.0f;
                            this.mTransitionLastPosition = 0.0f;
                            this.mInTransition = true;
                            invalidate();
                        }

                        public void setTransition(int i) throws Resources.NotFoundException, NumberFormatException {
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

                        public void setTransition(MotionScene.Transition transition) throws Resources.NotFoundException, NumberFormatException {
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

                        public MotionLayout(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
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

                        public MotionLayout(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
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
