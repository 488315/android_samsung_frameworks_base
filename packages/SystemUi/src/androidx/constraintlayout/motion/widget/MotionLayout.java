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
import android.support.v4.media.AbstractC0000x2c234b15;
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
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.motion.utils.ArcCurveFit;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.SpringStopEngine;
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
import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.constraintlayout.motion.utils.StopLogic;
import androidx.constraintlayout.motion.utils.ViewOscillator;
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
import androidx.core.widget.NestedScrollView;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MotionLayout extends ConstraintLayout implements NestedScrollingParent3 {
    public static boolean IS_IN_EDIT_MODE;
    public long mAnimationStartTime;
    public int mBeginState;
    public final RectF mBoundsCheck;
    public int mCurrentState;
    public int mDebugPath;
    public final DecelerateInterpolator mDecelerateLogic;
    public ArrayList mDecoratorsHelpers;
    public DevModeDraw mDevModeDraw;
    public int mEndState;
    public int mEndWrapHeight;
    public int mEndWrapWidth;
    public final HashMap mFrameArrayList;
    public int mFrames;
    public int mHeightMeasureMode;
    public boolean mInLayout;
    public boolean mInTransition;
    public final boolean mInteractionEnabled;
    public MotionInterpolator mInterpolator;
    public Matrix mInverseMatrix;
    public boolean mKeepAnimating;
    public final KeyCache mKeyCache;
    public long mLastDrawTime;
    public float mLastFps;
    public int mLastHeightMeasureSpec;
    public int mLastLayoutHeight;
    public int mLastLayoutWidth;
    public float mLastVelocity;
    public int mLastWidthMeasureSpec;
    public float mListenerPosition;
    public int mListenerState;
    public boolean mMeasureDuringTransition;
    public final Model mModel;
    public boolean mNeedsFireTransitionCompleted;
    public Runnable mOnComplete;
    public ArrayList mOnHideHelpers;
    public ArrayList mOnShowHelpers;
    public float mPostInterpolationPosition;
    public final HashMap mPreRotate;
    public Interpolator mProgressInterpolator;
    public View mRegionView;
    public MotionScene mScene;
    public float mScrollTargetDT;
    public float mScrollTargetDX;
    public float mScrollTargetDY;
    public long mScrollTargetTime;
    public int mStartWrapHeight;
    public int mStartWrapWidth;
    public StateCache mStateCache;
    public final StopLogic mStopLogic;
    public final Rect mTempRect;
    public boolean mTemporalInterpolator;
    public final ArrayList mTransitionCompleted;
    public float mTransitionDuration;
    public float mTransitionGoalPosition;
    public boolean mTransitionInstantly;
    public float mTransitionLastPosition;
    public long mTransitionLastTime;
    public CopyOnWriteArrayList mTransitionListeners;
    public float mTransitionPosition;
    public TransitionState mTransitionState;
    public boolean mUndergoingMotion;
    public int mWidthMeasureMode;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.constraintlayout.motion.widget.MotionLayout$5 */
    public abstract /* synthetic */ class AbstractC01265 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$motion$widget$MotionLayout$TransitionState */
        public static final /* synthetic */ int[] f25xabc7e4ac;

        static {
            int[] iArr = new int[TransitionState.values().length];
            f25xabc7e4ac = iArr;
            try {
                iArr[TransitionState.UNDEFINED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f25xabc7e4ac[TransitionState.SETUP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f25xabc7e4ac[TransitionState.MOVING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f25xabc7e4ac[TransitionState.FINISHED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DecelerateInterpolator extends MotionInterpolator {
        public float maxA;
        public float initalV = 0.0f;
        public float currentP = 0.0f;

        public DecelerateInterpolator() {
        }

        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            float f2 = this.initalV;
            if (f2 > 0.0f) {
                float f3 = this.maxA;
                if (f2 / f3 < f) {
                    f = f2 / f3;
                }
                MotionLayout.this.mLastVelocity = f2 - (f3 * f);
                return ((f2 * f) - (((f3 * f) * f) / 2.0f)) + this.currentP;
            }
            float f4 = this.maxA;
            if ((-f2) / f4 < f) {
                f = (-f2) / f4;
            }
            MotionLayout.this.mLastVelocity = (f4 * f) + f2;
            return (((f4 * f) * f) / 2.0f) + (f2 * f) + this.currentP;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionInterpolator
        public final float getVelocity() {
            return MotionLayout.this.mLastVelocity;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DevModeDraw {
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
            paint.setStyle(Paint.Style.STROKE);
            Paint paint2 = new Paint();
            this.mPaintKeyframes = paint2;
            paint2.setAntiAlias(true);
            paint2.setColor(-2067046);
            paint2.setStrokeWidth(2.0f);
            paint2.setStyle(Paint.Style.STROKE);
            Paint paint3 = new Paint();
            this.mPaintGraph = paint3;
            paint3.setAntiAlias(true);
            paint3.setColor(-13391360);
            paint3.setStrokeWidth(2.0f);
            paint3.setStyle(Paint.Style.STROKE);
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
            int i3;
            int i4;
            Paint paint;
            float f;
            float f2;
            int i5;
            Paint paint2 = this.mPaintGraph;
            int[] iArr = this.mPathMode;
            int i6 = 4;
            if (i == 4) {
                boolean z = false;
                boolean z2 = false;
                for (int i7 = 0; i7 < this.mKeyFrameCount; i7++) {
                    int i8 = iArr[i7];
                    if (i8 == 1) {
                        z = true;
                    }
                    if (i8 == 0) {
                        z2 = true;
                    }
                }
                if (z) {
                    float[] fArr = this.mPoints;
                    canvas.drawLine(fArr[0], fArr[1], fArr[fArr.length - 2], fArr[fArr.length - 1], paint2);
                }
                if (z2) {
                    drawPathCartesian(canvas);
                }
            }
            if (i == 2) {
                float[] fArr2 = this.mPoints;
                canvas.drawLine(fArr2[0], fArr2[1], fArr2[fArr2.length - 2], fArr2[fArr2.length - 1], paint2);
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
            int i9 = 1;
            while (i9 < i2 - 1) {
                if (i == i6 && iArr[i9 - 1] == 0) {
                    i5 = i9;
                } else {
                    int i10 = i9 * 2;
                    float[] fArr3 = this.mKeyFramePoints;
                    float f3 = fArr3[i10];
                    float f4 = fArr3[i10 + 1];
                    this.mPath.reset();
                    this.mPath.moveTo(f3, f4 + 10.0f);
                    this.mPath.lineTo(f3 + 10.0f, f4);
                    this.mPath.lineTo(f3, f4 - 10.0f);
                    this.mPath.lineTo(f3 - 10.0f, f4);
                    this.mPath.close();
                    int i11 = i9 - 1;
                    Paint paint3 = this.mFillPaint;
                    if (i == i6) {
                        int i12 = iArr[i11];
                        if (i12 == 1) {
                            drawPathRelativeTicks(canvas, f3 - 0.0f, f4 - 0.0f);
                        } else if (i12 == 0) {
                            drawPathCartesianTicks(canvas, f3 - 0.0f, f4 - 0.0f);
                        } else if (i12 == 2) {
                            paint = paint3;
                            f = f4;
                            f2 = f3;
                            i5 = i9;
                            drawPathScreenTicks(canvas, f3 - 0.0f, f4 - 0.0f, i3, i4);
                            canvas.drawPath(this.mPath, paint);
                        }
                        paint = paint3;
                        f = f4;
                        f2 = f3;
                        i5 = i9;
                        canvas.drawPath(this.mPath, paint);
                    } else {
                        paint = paint3;
                        f = f4;
                        f2 = f3;
                        i5 = i9;
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
                    canvas.drawPath(this.mPath, paint);
                }
                i9 = i5 + 1;
                i6 = 4;
            }
            float[] fArr4 = this.mPoints;
            if (fArr4.length > 1) {
                float f5 = fArr4[0];
                float f6 = fArr4[1];
                Paint paint4 = this.mPaintKeyframes;
                canvas.drawCircle(f5, f6, 8.0f, paint4);
                float[] fArr5 = this.mPoints;
                canvas.drawCircle(fArr5[fArr5.length - 2], fArr5[fArr5.length - 1], 8.0f, paint4);
            }
        }

        public final void drawPathCartesian(Canvas canvas) {
            float[] fArr = this.mPoints;
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[fArr.length - 2];
            float f4 = fArr[fArr.length - 1];
            float min = Math.min(f, f3);
            float max = Math.max(f2, f4);
            float max2 = Math.max(f, f3);
            float max3 = Math.max(f2, f4);
            Paint paint = this.mPaintGraph;
            canvas.drawLine(min, max, max2, max3, paint);
            canvas.drawLine(Math.min(f, f3), Math.min(f2, f4), Math.min(f, f3), Math.max(f2, f4), paint);
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
            Paint paint = this.mTextPaint;
            getTextBounds(paint, str);
            Rect rect = this.mBounds;
            canvas.drawText(str, ((min2 / 2.0f) - (rect.width() / 2)) + min, f2 - 20.0f, paint);
            float min3 = Math.min(f3, f5);
            Paint paint2 = this.mPaintGraph;
            canvas.drawLine(f, f2, min3, f2, paint2);
            String str2 = "" + (((int) (((max2 * 100.0f) / Math.abs(f6 - f4)) + 0.5d)) / 100.0f);
            getTextBounds(paint, str2);
            canvas.drawText(str2, f + 5.0f, max - ((max2 / 2.0f) - (rect.height() / 2)), paint);
            canvas.drawLine(f, f2, f, Math.max(f4, f6), paint2);
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
            float f10 = f3 + (f7 * f9);
            float f11 = f4 + (f9 * f8);
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f10, f11);
            float hypot2 = (float) Math.hypot(f10 - f, f11 - f2);
            String str = "" + (((int) ((hypot2 * 100.0f) / hypot)) / 100.0f);
            Paint paint = this.mTextPaint;
            getTextBounds(paint, str);
            canvas.drawTextOnPath(str, path, (hypot2 / 2.0f) - (this.mBounds.width() / 2), -20.0f, paint);
            canvas.drawLine(f, f2, f10, f11, this.mPaintGraph);
        }

        public final void drawPathScreenTicks(Canvas canvas, float f, float f2, int i, int i2) {
            StringBuilder sb = new StringBuilder("");
            MotionLayout motionLayout = MotionLayout.this;
            sb.append(((int) ((((f - (i / 2)) * 100.0f) / (motionLayout.getWidth() - i)) + 0.5d)) / 100.0f);
            String sb2 = sb.toString();
            Paint paint = this.mTextPaint;
            getTextBounds(paint, sb2);
            Rect rect = this.mBounds;
            canvas.drawText(sb2, ((f / 2.0f) - (rect.width() / 2)) + 0.0f, f2 - 20.0f, paint);
            float min = Math.min(0.0f, 1.0f);
            Paint paint2 = this.mPaintGraph;
            canvas.drawLine(f, f2, min, f2, paint2);
            String str = "" + (((int) ((((f2 - (i2 / 2)) * 100.0f) / (motionLayout.getHeight() - i2)) + 0.5d)) / 100.0f);
            getTextBounds(paint, str);
            canvas.drawText(str, f + 5.0f, 0.0f - ((f2 / 2.0f) - (rect.height() / 2)), paint);
            canvas.drawLine(f, f2, f, Math.max(0.0f, 1.0f), paint2);
        }

        public final void getTextBounds(Paint paint, String str) {
            paint.getTextBounds(str, 0, str.length(), this.mBounds);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Model {
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
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ConstraintWidget constraintWidget = (ConstraintWidget) it.next();
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
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ConstraintWidget constraintWidget3 = (ConstraintWidget) it2.next();
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
            Rect rect;
            Rect rect2;
            Interpolator loadInterpolator;
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
                MotionController motionController2 = (MotionController) motionLayout.mFrameArrayList.get(childAt2);
                if (motionController2 == null) {
                    i = childCount;
                    sparseArray = sparseArray2;
                    iArr = iArr2;
                    i2 = i4;
                } else {
                    ConstraintSet constraintSet = this.mStart;
                    Rect rect3 = motionController2.mTempRect;
                    if (constraintSet != null) {
                        ConstraintWidget widget = getWidget(this.mLayoutStart, childAt2);
                        if (widget != null) {
                            Rect access$2000 = MotionLayout.access$2000(motionLayout, widget);
                            ConstraintSet constraintSet2 = this.mStart;
                            int width = motionLayout.getWidth();
                            sparseArray = sparseArray2;
                            int height = motionLayout.getHeight();
                            iArr = iArr2;
                            int i5 = constraintSet2.mRotate;
                            if (i5 != 0) {
                                MotionController.rotate(i5, width, height, access$2000, rect3);
                            }
                            MotionPaths motionPaths = motionController2.mStartMotionPath;
                            motionPaths.time = 0.0f;
                            motionPaths.position = 0.0f;
                            motionController2.readView(motionPaths);
                            i = childCount;
                            i2 = i4;
                            rect = rect3;
                            motionPaths.setBounds(access$2000.left, access$2000.top, access$2000.width(), access$2000.height());
                            ConstraintSet.Constraint constraint = constraintSet2.get(motionController2.mId);
                            motionPaths.applyParameters(constraint);
                            ConstraintSet.Motion motion = constraint.motion;
                            motionController2.mMotionStagger = motion.mMotionStagger;
                            motionController2.mStartPoint.setState(access$2000, constraintSet2, i5, motionController2.mId);
                            motionController2.mTransformPivotTarget = constraint.transform.transformPivotTarget;
                            motionController2.mQuantizeMotionSteps = motion.mQuantizeMotionSteps;
                            motionController2.mQuantizeMotionPhase = motion.mQuantizeMotionPhase;
                            Context context = motionController2.mView.getContext();
                            int i6 = motion.mQuantizeInterpolatorType;
                            String str = motion.mQuantizeInterpolatorString;
                            int i7 = motion.mQuantizeInterpolatorID;
                            if (i6 == -2) {
                                loadInterpolator = AnimationUtils.loadInterpolator(context, i7);
                            } else if (i6 != -1) {
                                loadInterpolator = i6 != 0 ? i6 != 1 ? i6 != 2 ? i6 != 4 ? i6 != 5 ? null : new OvershootInterpolator() : new BounceInterpolator() : new android.view.animation.DecelerateInterpolator() : new AccelerateInterpolator() : new AccelerateDecelerateInterpolator();
                            } else {
                                final Easing interpolator = Easing.getInterpolator(str);
                                loadInterpolator = new Interpolator() { // from class: androidx.constraintlayout.motion.widget.MotionController.1
                                    @Override // android.animation.TimeInterpolator
                                    public final float getInterpolation(float f) {
                                        return (float) Easing.this.get(f);
                                    }
                                };
                            }
                            motionController2.mQuantizeMotionInterpolator = loadInterpolator;
                        } else {
                            i = childCount;
                            sparseArray = sparseArray2;
                            iArr = iArr2;
                            i2 = i4;
                            rect = rect3;
                            if (motionLayout.mDebugPath != 0) {
                                Log.e("MotionLayout", Debug.getLocation() + "no widget for  " + Debug.getName(childAt2) + " (" + childAt2.getClass().getName() + ")");
                            }
                        }
                    } else {
                        i = childCount;
                        sparseArray = sparseArray2;
                        iArr = iArr2;
                        i2 = i4;
                        rect = rect3;
                    }
                    if (this.mEnd != null) {
                        ConstraintWidget widget2 = getWidget(this.mLayoutEnd, childAt2);
                        if (widget2 != null) {
                            Rect access$20002 = MotionLayout.access$2000(motionLayout, widget2);
                            ConstraintSet constraintSet3 = this.mEnd;
                            int width2 = motionLayout.getWidth();
                            int height2 = motionLayout.getHeight();
                            int i8 = constraintSet3.mRotate;
                            if (i8 != 0) {
                                Rect rect4 = rect;
                                MotionController.rotate(i8, width2, height2, access$20002, rect4);
                                rect2 = rect4;
                            } else {
                                rect2 = access$20002;
                            }
                            MotionPaths motionPaths2 = motionController2.mEndMotionPath;
                            motionPaths2.time = 1.0f;
                            motionPaths2.position = 1.0f;
                            motionController2.readView(motionPaths2);
                            motionPaths2.setBounds(rect2.left, rect2.top, rect2.width(), rect2.height());
                            motionPaths2.applyParameters(constraintSet3.get(motionController2.mId));
                            motionController2.mEndPoint.setState(rect2, constraintSet3, i8, motionController2.mId);
                        } else if (motionLayout.mDebugPath != 0) {
                            Log.e("MotionLayout", Debug.getLocation() + "no widget for  " + Debug.getName(childAt2) + " (" + childAt2.getClass().getName() + ")");
                        }
                    }
                }
                i4 = i2 + 1;
                sparseArray2 = sparseArray;
                iArr2 = iArr;
                childCount = i;
            }
            SparseArray sparseArray3 = sparseArray2;
            int[] iArr3 = iArr2;
            int i9 = childCount;
            int i10 = 0;
            while (i10 < i9) {
                SparseArray sparseArray4 = sparseArray3;
                MotionController motionController3 = (MotionController) sparseArray4.get(iArr3[i10]);
                int i11 = motionController3.mStartMotionPath.mAnimateRelativeTo;
                if (i11 != -1) {
                    MotionController motionController4 = (MotionController) sparseArray4.get(i11);
                    motionController3.mStartMotionPath.setupRelative(motionController4, motionController4.mStartMotionPath);
                    motionController3.mEndMotionPath.setupRelative(motionController4, motionController4.mEndMotionPath);
                }
                i10++;
                sparseArray3 = sparseArray4;
            }
        }

        public final void computeStartEndSize(int i, int i2) {
            MotionLayout motionLayout = MotionLayout.this;
            int i3 = motionLayout.mLayoutWidget.mOptimizationLevel;
            if (motionLayout.mCurrentState == motionLayout.mBeginState) {
                ConstraintWidgetContainer constraintWidgetContainer = this.mLayoutEnd;
                ConstraintSet constraintSet = this.mEnd;
                motionLayout.resolveSystem(constraintWidgetContainer, i3, (constraintSet == null || constraintSet.mRotate == 0) ? i : i2, (constraintSet == null || constraintSet.mRotate == 0) ? i2 : i);
                ConstraintSet constraintSet2 = this.mStart;
                if (constraintSet2 != null) {
                    ConstraintWidgetContainer constraintWidgetContainer2 = this.mLayoutStart;
                    int i4 = constraintSet2.mRotate;
                    int i5 = i4 == 0 ? i : i2;
                    if (i4 == 0) {
                        i = i2;
                    }
                    motionLayout.resolveSystem(constraintWidgetContainer2, i3, i5, i);
                    return;
                }
                return;
            }
            ConstraintSet constraintSet3 = this.mStart;
            if (constraintSet3 != null) {
                ConstraintWidgetContainer constraintWidgetContainer3 = this.mLayoutStart;
                int i6 = constraintSet3.mRotate;
                motionLayout.resolveSystem(constraintWidgetContainer3, i3, i6 == 0 ? i : i2, i6 == 0 ? i2 : i);
            }
            ConstraintWidgetContainer constraintWidgetContainer4 = this.mLayoutEnd;
            ConstraintSet constraintSet4 = this.mEnd;
            int i7 = (constraintSet4 == null || constraintSet4.mRotate == 0) ? i : i2;
            if (constraintSet4 == null || constraintSet4.mRotate == 0) {
                i = i2;
            }
            motionLayout.resolveSystem(constraintWidgetContainer4, i3, i7, i);
        }

        public final void initFrom(ConstraintSet constraintSet, ConstraintSet constraintSet2) {
            this.mStart = constraintSet;
            this.mEnd = constraintSet2;
            this.mLayoutStart = new ConstraintWidgetContainer();
            ConstraintWidgetContainer constraintWidgetContainer = new ConstraintWidgetContainer();
            this.mLayoutEnd = constraintWidgetContainer;
            ConstraintWidgetContainer constraintWidgetContainer2 = this.mLayoutStart;
            boolean z = MotionLayout.IS_IN_EDIT_MODE;
            MotionLayout motionLayout = MotionLayout.this;
            ConstraintWidgetContainer constraintWidgetContainer3 = motionLayout.mLayoutWidget;
            BasicMeasure.Measurer measurer = constraintWidgetContainer3.mMeasurer;
            constraintWidgetContainer2.mMeasurer = measurer;
            constraintWidgetContainer2.mDependencyGraph.mMeasurer = measurer;
            BasicMeasure.Measurer measurer2 = constraintWidgetContainer3.mMeasurer;
            constraintWidgetContainer.mMeasurer = measurer2;
            constraintWidgetContainer.mDependencyGraph.mMeasurer = measurer2;
            constraintWidgetContainer2.mChildren.clear();
            this.mLayoutEnd.mChildren.clear();
            copy(motionLayout.mLayoutWidget, this.mLayoutStart);
            copy(motionLayout.mLayoutWidget, this.mLayoutEnd);
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
            ConstraintWidgetContainer constraintWidgetContainer4 = this.mLayoutStart;
            constraintWidgetContainer4.mBasicMeasureSolver.updateHierarchy(constraintWidgetContainer4);
            this.mLayoutEnd.mIsRtl = motionLayout.isRtl();
            ConstraintWidgetContainer constraintWidgetContainer5 = this.mLayoutEnd;
            constraintWidgetContainer5.mBasicMeasureSolver.updateHierarchy(constraintWidgetContainer5);
            ViewGroup.LayoutParams layoutParams = motionLayout.getLayoutParams();
            if (layoutParams != null) {
                if (layoutParams.width == -2) {
                    ConstraintWidgetContainer constraintWidgetContainer6 = this.mLayoutStart;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    constraintWidgetContainer6.setHorizontalDimensionBehaviour(dimensionBehaviour);
                    this.mLayoutEnd.setHorizontalDimensionBehaviour(dimensionBehaviour);
                }
                if (layoutParams.height == -2) {
                    ConstraintWidgetContainer constraintWidgetContainer7 = this.mLayoutStart;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    constraintWidgetContainer7.setVerticalDimensionBehaviour(dimensionBehaviour2);
                    this.mLayoutEnd.setVerticalDimensionBehaviour(dimensionBehaviour2);
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
            int i3 = motionLayout.mLayoutWidget.mOptimizationLevel;
            computeStartEndSize(i, i2);
            int i4 = 0;
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
            int i5 = motionLayout.mStartWrapWidth;
            int i6 = motionLayout.mStartWrapHeight;
            int i7 = motionLayout.mWidthMeasureMode;
            if (i7 == Integer.MIN_VALUE || i7 == 0) {
                i5 = (int) ((motionLayout.mPostInterpolationPosition * (motionLayout.mEndWrapWidth - i5)) + i5);
            }
            int i8 = i5;
            int i9 = motionLayout.mHeightMeasureMode;
            int i10 = (i9 == Integer.MIN_VALUE || i9 == 0) ? (int) ((motionLayout.mPostInterpolationPosition * (motionLayout.mEndWrapHeight - i6)) + i6) : i6;
            ConstraintWidgetContainer constraintWidgetContainer = this.mLayoutStart;
            motionLayout.resolveMeasuredDimension(constraintWidgetContainer.mWidthMeasuredTooSmall || this.mLayoutEnd.mWidthMeasuredTooSmall, i, i2, i8, i10, constraintWidgetContainer.mHeightMeasuredTooSmall || this.mLayoutEnd.mHeightMeasuredTooSmall);
            int childCount = motionLayout.getChildCount();
            motionLayout.mModel.build();
            motionLayout.mInTransition = true;
            SparseArray sparseArray = new SparseArray();
            for (int i11 = 0; i11 < childCount; i11++) {
                View childAt = motionLayout.getChildAt(i11);
                sparseArray.put(childAt.getId(), (MotionController) motionLayout.mFrameArrayList.get(childAt));
            }
            int width = motionLayout.getWidth();
            int height2 = motionLayout.getHeight();
            MotionScene.Transition transition = motionLayout.mScene.mCurrentTransition;
            int i12 = transition != null ? transition.mPathMotionArc : -1;
            if (i12 != -1) {
                for (int i13 = 0; i13 < childCount; i13++) {
                    MotionController motionController = (MotionController) motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i13));
                    if (motionController != null) {
                        motionController.mPathMotionArc = i12;
                    }
                }
            }
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
            int[] iArr = new int[motionLayout.mFrameArrayList.size()];
            int i14 = 0;
            for (int i15 = 0; i15 < childCount; i15++) {
                MotionController motionController2 = (MotionController) motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i15));
                int i16 = motionController2.mStartMotionPath.mAnimateRelativeTo;
                if (i16 != -1) {
                    sparseBooleanArray.put(i16, true);
                    iArr[i14] = motionController2.mStartMotionPath.mAnimateRelativeTo;
                    i14++;
                }
            }
            if (motionLayout.mDecoratorsHelpers != null) {
                for (int i17 = 0; i17 < i14; i17++) {
                    MotionController motionController3 = (MotionController) motionLayout.mFrameArrayList.get(motionLayout.findViewById(iArr[i17]));
                    if (motionController3 != null) {
                        motionLayout.mScene.getKeyFrames(motionController3);
                    }
                }
                Iterator it = motionLayout.mDecoratorsHelpers.iterator();
                while (it.hasNext()) {
                    ((MotionHelper) it.next()).onPreSetup(motionLayout, motionLayout.mFrameArrayList);
                }
                for (int i18 = 0; i18 < i14; i18++) {
                    MotionController motionController4 = (MotionController) motionLayout.mFrameArrayList.get(motionLayout.findViewById(iArr[i18]));
                    if (motionController4 != null) {
                        motionController4.setup(width, height2, System.nanoTime());
                    }
                }
            } else {
                for (int i19 = 0; i19 < i14; i19++) {
                    MotionController motionController5 = (MotionController) motionLayout.mFrameArrayList.get(motionLayout.findViewById(iArr[i19]));
                    if (motionController5 != null) {
                        motionLayout.mScene.getKeyFrames(motionController5);
                        motionController5.setup(width, height2, System.nanoTime());
                    }
                }
            }
            for (int i20 = 0; i20 < childCount; i20++) {
                View childAt2 = motionLayout.getChildAt(i20);
                MotionController motionController6 = (MotionController) motionLayout.mFrameArrayList.get(childAt2);
                if (!sparseBooleanArray.get(childAt2.getId()) && motionController6 != null) {
                    motionLayout.mScene.getKeyFrames(motionController6);
                    motionController6.setup(width, height2, System.nanoTime());
                }
            }
            MotionScene.Transition transition2 = motionLayout.mScene.mCurrentTransition;
            float f = transition2 != null ? transition2.mStagger : 0.0f;
            if (f != 0.0f) {
                boolean z2 = ((double) f) < 0.0d;
                float abs = Math.abs(f);
                float f2 = -3.4028235E38f;
                float f3 = Float.MAX_VALUE;
                float f4 = -3.4028235E38f;
                float f5 = Float.MAX_VALUE;
                int i21 = 0;
                while (true) {
                    if (i21 >= childCount) {
                        z = false;
                        break;
                    }
                    MotionController motionController7 = (MotionController) motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i21));
                    if (!Float.isNaN(motionController7.mMotionStagger)) {
                        break;
                    }
                    MotionPaths motionPaths = motionController7.mEndMotionPath;
                    float f6 = motionPaths.f27x;
                    float f7 = motionPaths.f28y;
                    float f8 = z2 ? f7 - f6 : f7 + f6;
                    f5 = Math.min(f5, f8);
                    f4 = Math.max(f4, f8);
                    i21++;
                }
                if (!z) {
                    while (i4 < childCount) {
                        MotionController motionController8 = (MotionController) motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i4));
                        MotionPaths motionPaths2 = motionController8.mEndMotionPath;
                        float f9 = motionPaths2.f27x;
                        float f10 = motionPaths2.f28y;
                        float f11 = z2 ? f10 - f9 : f10 + f9;
                        motionController8.mStaggerScale = 1.0f / (1.0f - abs);
                        motionController8.mStaggerOffset = abs - (((f11 - f5) * abs) / (f4 - f5));
                        i4++;
                    }
                    return;
                }
                for (int i22 = 0; i22 < childCount; i22++) {
                    MotionController motionController9 = (MotionController) motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i22));
                    if (!Float.isNaN(motionController9.mMotionStagger)) {
                        f3 = Math.min(f3, motionController9.mMotionStagger);
                        f2 = Math.max(f2, motionController9.mMotionStagger);
                    }
                }
                while (i4 < childCount) {
                    MotionController motionController10 = (MotionController) motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i4));
                    if (!Float.isNaN(motionController10.mMotionStagger)) {
                        motionController10.mStaggerScale = 1.0f / (1.0f - abs);
                        if (z2) {
                            motionController10.mStaggerOffset = abs - (((f2 - motionController10.mMotionStagger) / (f2 - f3)) * abs);
                        } else {
                            motionController10.mStaggerOffset = abs - (((motionController10.mMotionStagger - f3) * abs) / (f2 - f3));
                        }
                    }
                    i4++;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void setupConstraintWidget(ConstraintWidgetContainer constraintWidgetContainer, ConstraintSet constraintSet) {
            ConstraintSet.Constraint constraint;
            ConstraintSet.Constraint constraint2;
            SparseArray sparseArray = new SparseArray();
            Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, -2);
            sparseArray.clear();
            sparseArray.put(0, constraintWidgetContainer);
            MotionLayout motionLayout = MotionLayout.this;
            sparseArray.put(motionLayout.getId(), constraintWidgetContainer);
            if (constraintSet != null && constraintSet.mRotate != 0) {
                ConstraintWidgetContainer constraintWidgetContainer2 = this.mLayoutEnd;
                int i = motionLayout.mLayoutWidget.mOptimizationLevel;
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(motionLayout.getHeight(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(motionLayout.getWidth(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                boolean z = MotionLayout.IS_IN_EDIT_MODE;
                motionLayout.resolveSystem(constraintWidgetContainer2, i, makeMeasureSpec, makeMeasureSpec2);
            }
            Iterator it = constraintWidgetContainer.mChildren.iterator();
            while (it.hasNext()) {
                ConstraintWidget constraintWidget = (ConstraintWidget) it.next();
                constraintWidget.mAnimated = true;
                sparseArray.put(((View) constraintWidget.mCompanionWidget).getId(), constraintWidget);
            }
            Iterator it2 = constraintWidgetContainer.mChildren.iterator();
            while (it2.hasNext()) {
                ConstraintWidget constraintWidget2 = (ConstraintWidget) it2.next();
                View view = (View) constraintWidget2.mCompanionWidget;
                int id = view.getId();
                HashMap hashMap = constraintSet.mConstraints;
                if (hashMap.containsKey(Integer.valueOf(id)) && (constraint2 = (ConstraintSet.Constraint) hashMap.get(Integer.valueOf(id))) != null) {
                    constraint2.applyTo(layoutParams);
                }
                constraintWidget2.setWidth(constraintSet.get(view.getId()).layout.mWidth);
                constraintWidget2.setHeight(constraintSet.get(view.getId()).layout.mHeight);
                if (view instanceof ConstraintHelper) {
                    ConstraintHelper constraintHelper = (ConstraintHelper) view;
                    int id2 = constraintHelper.getId();
                    HashMap hashMap2 = constraintSet.mConstraints;
                    if (hashMap2.containsKey(Integer.valueOf(id2)) && (constraint = (ConstraintSet.Constraint) hashMap2.get(Integer.valueOf(id2))) != null && (constraintWidget2 instanceof HelperWidget)) {
                        constraintHelper.loadParameters(constraint, (HelperWidget) constraintWidget2, layoutParams, sparseArray);
                    }
                    if (view instanceof androidx.constraintlayout.widget.Barrier) {
                        ((androidx.constraintlayout.widget.Barrier) view).validateParams();
                    }
                }
                layoutParams.resolveLayoutDirection(motionLayout.getLayoutDirection());
                MotionLayout motionLayout2 = MotionLayout.this;
                boolean z2 = MotionLayout.IS_IN_EDIT_MODE;
                motionLayout2.applyConstraintsFromLayoutParams(false, view, constraintWidget2, layoutParams, sparseArray);
                if (constraintSet.get(view.getId()).propertySet.mVisibilityMode == 1) {
                    constraintWidget2.mVisibility = view.getVisibility();
                } else {
                    constraintWidget2.mVisibility = constraintSet.get(view.getId()).propertySet.visibility;
                }
            }
            Iterator it3 = constraintWidgetContainer.mChildren.iterator();
            while (it3.hasNext()) {
                ConstraintWidget constraintWidget3 = (ConstraintWidget) it3.next();
                if (constraintWidget3 instanceof VirtualLayout) {
                    ConstraintHelper constraintHelper2 = (ConstraintHelper) constraintWidget3.mCompanionWidget;
                    Helper helper = (Helper) constraintWidget3;
                    constraintHelper2.updatePreLayout(helper, sparseArray);
                    VirtualLayout virtualLayout = (VirtualLayout) helper;
                    for (int i2 = 0; i2 < virtualLayout.mWidgetsCount; i2++) {
                        ConstraintWidget constraintWidget4 = virtualLayout.mWidgets[i2];
                        if (constraintWidget4 != null) {
                            constraintWidget4.mInVirtualLayout = true;
                        }
                    }
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MyTracker {

        /* renamed from: me */
        public static final MyTracker f26me = new MyTracker();
        public VelocityTracker tracker;

        private MyTracker() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StateCache {
        public float mProgress = Float.NaN;
        public float mVelocity = Float.NaN;
        public int startState = -1;
        public int endState = -1;

        public StateCache() {
        }

        public final void apply() {
            int i = this.startState;
            MotionLayout motionLayout = MotionLayout.this;
            if (i != -1 || this.endState != -1) {
                if (i == -1) {
                    int i2 = this.endState;
                    if (motionLayout.isAttachedToWindow()) {
                        motionLayout.transitionToState$1(i2, -1);
                    } else {
                        if (motionLayout.mStateCache == null) {
                            motionLayout.mStateCache = motionLayout.new StateCache();
                        }
                        motionLayout.mStateCache.endState = i2;
                    }
                } else {
                    int i3 = this.endState;
                    if (i3 == -1) {
                        motionLayout.setState(i);
                    } else {
                        motionLayout.setTransition(i, i3);
                    }
                }
                motionLayout.setState(TransitionState.SETUP);
            }
            if (Float.isNaN(this.mVelocity)) {
                if (Float.isNaN(this.mProgress)) {
                    return;
                }
                motionLayout.setProgress(this.mProgress);
                return;
            }
            float f = this.mProgress;
            float f2 = this.mVelocity;
            if (motionLayout.isAttachedToWindow()) {
                motionLayout.setProgress(f);
                motionLayout.setState(TransitionState.MOVING);
                motionLayout.mLastVelocity = f2;
                if (f2 != 0.0f) {
                    motionLayout.animateTo(f2 > 0.0f ? 1.0f : 0.0f);
                } else if (f != 0.0f && f != 1.0f) {
                    motionLayout.animateTo(f > 0.5f ? 1.0f : 0.0f);
                }
            } else {
                if (motionLayout.mStateCache == null) {
                    motionLayout.mStateCache = motionLayout.new StateCache();
                }
                StateCache stateCache = motionLayout.mStateCache;
                stateCache.mProgress = f;
                stateCache.mVelocity = f2;
            }
            this.mProgress = Float.NaN;
            this.mVelocity = Float.NaN;
            this.startState = -1;
            this.endState = -1;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TransitionListener {
        void onTransitionChange();

        void onTransitionCompleted(int i);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum TransitionState {
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
        this.mFrameArrayList = new HashMap();
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
        this.mPreRotate = new HashMap();
        this.mTempRect = new Rect();
        this.mTransitionState = TransitionState.UNDEFINED;
        this.mModel = new Model();
        this.mNeedsFireTransitionCompleted = false;
        this.mBoundsCheck = new RectF();
        this.mRegionView = null;
        this.mInverseMatrix = null;
        this.mTransitionCompleted = new ArrayList();
        init(null);
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

    public final void animateTo(float f) {
        MotionScene motionScene = this.mScene;
        if (motionScene == null) {
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
        this.mTransitionDuration = (motionScene.mCurrentTransition != null ? r3.mDuration : motionScene.mDefaultDuration) / 1000.0f;
        setProgress(f);
        Interpolator interpolator = null;
        this.mInterpolator = null;
        final MotionScene motionScene2 = this.mScene;
        MotionScene.Transition transition = motionScene2.mCurrentTransition;
        int i = transition.mDefaultInterpolator;
        if (i == -2) {
            interpolator = AnimationUtils.loadInterpolator(motionScene2.mMotionLayout.getContext(), motionScene2.mCurrentTransition.mDefaultInterpolatorID);
        } else if (i == -1) {
            final Easing interpolator2 = Easing.getInterpolator(transition.mDefaultInterpolatorString);
            interpolator = new Interpolator(motionScene2, interpolator2) { // from class: androidx.constraintlayout.motion.widget.MotionScene.1
                public final /* synthetic */ Easing val$easing;

                {
                    this.val$easing = interpolator2;
                }

                @Override // android.animation.TimeInterpolator
                public final float getInterpolation(float f5) {
                    return (float) this.val$easing.get(f5);
                }
            };
        } else if (i == 0) {
            interpolator = new AccelerateDecelerateInterpolator();
        } else if (i == 1) {
            interpolator = new AccelerateInterpolator();
        } else if (i == 2) {
            interpolator = new android.view.animation.DecelerateInterpolator();
        } else if (i == 4) {
            interpolator = new BounceInterpolator();
        } else if (i == 5) {
            interpolator = new OvershootInterpolator();
        } else if (i == 6) {
            interpolator = new AnticipateInterpolator();
        }
        this.mProgressInterpolator = interpolator;
        this.mTransitionInstantly = false;
        this.mAnimationStartTime = System.nanoTime();
        this.mInTransition = true;
        this.mTransitionPosition = f4;
        this.mTransitionLastPosition = f4;
        invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:215:0x051d  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0531 A[ORIG_RETURN, RETURN] */
    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dispatchDraw(Canvas canvas) {
        ArrayList arrayList;
        DevModeDraw devModeDraw;
        Iterator it;
        int i;
        int i2;
        Canvas canvas2;
        int i3;
        DevModeDraw devModeDraw2;
        int i4;
        MotionPaths motionPaths;
        int i5;
        MotionController motionController;
        MotionPaths motionPaths2;
        int i6;
        Paint paint;
        Paint paint2;
        double d;
        ViewTransitionController viewTransitionController;
        ArrayList arrayList2;
        MotionLayout motionLayout = this;
        Canvas canvas3 = canvas;
        ArrayList arrayList3 = motionLayout.mDecoratorsHelpers;
        if (arrayList3 != null) {
            Iterator it2 = arrayList3.iterator();
            while (it2.hasNext()) {
                ((MotionHelper) it2.next()).getClass();
            }
        }
        int i7 = 0;
        motionLayout.evaluate(false);
        MotionScene motionScene = motionLayout.mScene;
        if (motionScene != null && (viewTransitionController = motionScene.mViewTransitionController) != null && (arrayList2 = viewTransitionController.animations) != null) {
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                ((ViewTransition.Animate) it3.next()).mutate();
            }
            ArrayList arrayList4 = viewTransitionController.animations;
            ArrayList arrayList5 = viewTransitionController.removeList;
            arrayList4.removeAll(arrayList5);
            arrayList5.clear();
            if (viewTransitionController.animations.isEmpty()) {
                viewTransitionController.animations = null;
            }
        }
        super.dispatchDraw(canvas);
        if (motionLayout.mScene == null) {
            return;
        }
        int i8 = 1;
        if ((motionLayout.mDebugPath & 1) == 1 && !isInEditMode()) {
            motionLayout.mFrames++;
            long nanoTime = System.nanoTime();
            long j = motionLayout.mLastDrawTime;
            if (j != -1) {
                if (nanoTime - j > 200000000) {
                    motionLayout.mLastFps = ((int) ((motionLayout.mFrames / (r9 * 1.0E-9f)) * 100.0f)) / 100.0f;
                    motionLayout.mFrames = 0;
                    motionLayout.mLastDrawTime = nanoTime;
                }
            } else {
                motionLayout.mLastDrawTime = nanoTime;
            }
            Paint paint3 = new Paint();
            paint3.setTextSize(42.0f);
            StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(motionLayout.mLastFps + " fps " + Debug.getState(motionLayout.mBeginState, motionLayout) + " -> ");
            m18m.append(Debug.getState(motionLayout.mEndState, motionLayout));
            m18m.append(" (progress: ");
            m18m.append(((int) (motionLayout.mTransitionLastPosition * 1000.0f)) / 10.0f);
            m18m.append(" ) state=");
            int i9 = motionLayout.mCurrentState;
            m18m.append(i9 == -1 ? "undefined" : Debug.getState(i9, motionLayout));
            String sb = m18m.toString();
            paint3.setColor(EmergencyPhoneWidget.BG_COLOR);
            canvas3.drawText(sb, 11.0f, getHeight() - 29, paint3);
            paint3.setColor(-7864184);
            canvas3.drawText(sb, 10.0f, getHeight() - 30, paint3);
        }
        if (motionLayout.mDebugPath > 1) {
            if (motionLayout.mDevModeDraw == null) {
                motionLayout.mDevModeDraw = motionLayout.new DevModeDraw();
            }
            DevModeDraw devModeDraw3 = motionLayout.mDevModeDraw;
            HashMap hashMap = motionLayout.mFrameArrayList;
            MotionScene motionScene2 = motionLayout.mScene;
            MotionScene.Transition transition = motionScene2.mCurrentTransition;
            int i10 = transition != null ? transition.mDuration : motionScene2.mDefaultDuration;
            int i11 = motionLayout.mDebugPath;
            devModeDraw3.getClass();
            if (hashMap != null) {
                if (hashMap.size() != 0) {
                    canvas.save();
                    MotionLayout motionLayout2 = MotionLayout.this;
                    boolean isInEditMode = motionLayout2.isInEditMode();
                    Paint paint4 = devModeDraw3.mPaint;
                    if (!isInEditMode && (i11 & 1) == 2) {
                        String str = motionLayout2.getContext().getResources().getResourceName(motionLayout2.mEndState) + ":" + motionLayout2.mTransitionLastPosition;
                        canvas3.drawText(str, 10.0f, motionLayout2.getHeight() - 30, devModeDraw3.mTextPaint);
                        canvas3.drawText(str, 11.0f, motionLayout2.getHeight() - 29, paint4);
                    }
                    Iterator it4 = hashMap.values().iterator();
                    Canvas canvas4 = canvas3;
                    DevModeDraw devModeDraw4 = devModeDraw3;
                    while (it4.hasNext()) {
                        MotionController motionController2 = (MotionController) it4.next();
                        int i12 = motionController2.mStartMotionPath.mDrawPath;
                        ArrayList arrayList6 = motionController2.mMotionPaths;
                        Iterator it5 = arrayList6.iterator();
                        while (it5.hasNext()) {
                            i12 = Math.max(i12, ((MotionPaths) it5.next()).mDrawPath);
                        }
                        int max = Math.max(i12, motionController2.mEndMotionPath.mDrawPath);
                        if (i11 > 0 && max == 0) {
                            max = i8;
                        }
                        if (max != 0) {
                            float[] fArr = devModeDraw4.mKeyFramePoints;
                            if (fArr != null) {
                                int[] iArr = devModeDraw4.mPathMode;
                                if (iArr != null) {
                                    Iterator it6 = arrayList6.iterator();
                                    while (it6.hasNext()) {
                                        iArr[i7] = ((MotionPaths) it6.next()).mMode;
                                        i7++;
                                        it4 = it4;
                                    }
                                }
                                it = it4;
                                int i13 = 0;
                                int i14 = 0;
                                for (double[] timePoints = motionController2.mSpline[i7].getTimePoints(); i13 < timePoints.length; timePoints = timePoints) {
                                    motionController2.mSpline[0].getPos(timePoints[i13], motionController2.mInterpolateData);
                                    motionController2.mStartMotionPath.getCenter(timePoints[i13], motionController2.mInterpolateVariables, motionController2.mInterpolateData, fArr, i14);
                                    i14 += 2;
                                    i13++;
                                    i11 = i11;
                                    devModeDraw3 = devModeDraw3;
                                }
                                devModeDraw = devModeDraw3;
                                i = i11;
                                i2 = i14 / 2;
                            } else {
                                devModeDraw = devModeDraw3;
                                it = it4;
                                i = i11;
                                i2 = 0;
                            }
                            devModeDraw4.mKeyFrameCount = i2;
                            if (max >= 1) {
                                int i15 = i10 / 16;
                                float[] fArr2 = devModeDraw4.mPoints;
                                if (fArr2 == null || fArr2.length != i15 * 2) {
                                    devModeDraw4.mPoints = new float[i15 * 2];
                                    devModeDraw4.mPath = new Path();
                                }
                                int i16 = devModeDraw4.mShadowTranslate;
                                float f = i16;
                                canvas4.translate(f, f);
                                paint4.setColor(1996488704);
                                Paint paint5 = devModeDraw4.mFillPaint;
                                paint5.setColor(1996488704);
                                Paint paint6 = devModeDraw4.mPaintKeyframes;
                                paint6.setColor(1996488704);
                                Paint paint7 = devModeDraw4.mPaintGraph;
                                paint7.setColor(1996488704);
                                float[] fArr3 = devModeDraw4.mPoints;
                                float f2 = 1.0f / (i15 - 1);
                                HashMap hashMap2 = motionController2.mAttributesMap;
                                SplineSet splineSet = hashMap2 == null ? null : (SplineSet) hashMap2.get("translationX");
                                HashMap hashMap3 = motionController2.mAttributesMap;
                                SplineSet splineSet2 = hashMap3 == null ? null : (SplineSet) hashMap3.get("translationY");
                                i3 = i10;
                                HashMap hashMap4 = motionController2.mCycleMap;
                                ViewOscillator viewOscillator = hashMap4 == null ? null : (ViewOscillator) hashMap4.get("translationX");
                                HashMap hashMap5 = motionController2.mCycleMap;
                                ViewOscillator viewOscillator2 = hashMap5 == null ? null : (ViewOscillator) hashMap5.get("translationY");
                                int i17 = 0;
                                while (true) {
                                    float f3 = Float.NaN;
                                    SplineSet splineSet3 = splineSet2;
                                    motionPaths = motionController2.mStartMotionPath;
                                    if (i17 >= i15) {
                                        break;
                                    }
                                    int i18 = i15;
                                    float f4 = i17 * f2;
                                    float f5 = f2;
                                    float f6 = motionController2.mStaggerScale;
                                    if (f6 != 1.0f) {
                                        i6 = i16;
                                        float f7 = motionController2.mStaggerOffset;
                                        if (f4 < f7) {
                                            f4 = 0.0f;
                                        }
                                        paint = paint5;
                                        paint2 = paint6;
                                        if (f4 > f7 && f4 < 1.0d) {
                                            f4 = Math.min((f4 - f7) * f6, 1.0f);
                                        }
                                    } else {
                                        i6 = i16;
                                        paint = paint5;
                                        paint2 = paint6;
                                    }
                                    double d2 = f4;
                                    Easing easing = motionPaths.mKeyFrameEasing;
                                    Iterator it7 = arrayList6.iterator();
                                    float f8 = 0.0f;
                                    while (it7.hasNext()) {
                                        double d3 = d2;
                                        MotionPaths motionPaths3 = (MotionPaths) it7.next();
                                        Easing easing2 = motionPaths3.mKeyFrameEasing;
                                        if (easing2 != null) {
                                            float f9 = motionPaths3.time;
                                            if (f9 < f4) {
                                                f8 = f9;
                                                easing = easing2;
                                            } else if (Float.isNaN(f3)) {
                                                f3 = motionPaths3.time;
                                            }
                                        }
                                        d2 = d3;
                                    }
                                    double d4 = d2;
                                    if (easing != null) {
                                        if (Float.isNaN(f3)) {
                                            f3 = 1.0f;
                                        }
                                        d = (((float) easing.get((f4 - f8) / r16)) * (f3 - f8)) + f8;
                                    } else {
                                        d = d4;
                                    }
                                    motionController2.mSpline[0].getPos(d, motionController2.mInterpolateData);
                                    ArcCurveFit arcCurveFit = motionController2.mArcSpline;
                                    if (arcCurveFit != null) {
                                        double[] dArr = motionController2.mInterpolateData;
                                        if (dArr.length > 0) {
                                            arcCurveFit.getPos(d, dArr);
                                        }
                                    }
                                    int i19 = i17 * 2;
                                    ArrayList arrayList7 = arrayList6;
                                    int i20 = i17;
                                    motionController2.mStartMotionPath.getCenter(d, motionController2.mInterpolateVariables, motionController2.mInterpolateData, fArr3, i19);
                                    if (viewOscillator != null) {
                                        fArr3[i19] = viewOscillator.get(f4) + fArr3[i19];
                                    } else if (splineSet != null) {
                                        fArr3[i19] = splineSet.get(f4) + fArr3[i19];
                                    }
                                    if (viewOscillator2 != null) {
                                        int i21 = i19 + 1;
                                        fArr3[i21] = viewOscillator2.get(f4) + fArr3[i21];
                                    } else if (splineSet3 != null) {
                                        int i22 = i19 + 1;
                                        fArr3[i22] = splineSet3.get(f4) + fArr3[i22];
                                    }
                                    i17 = i20 + 1;
                                    splineSet2 = splineSet3;
                                    i15 = i18;
                                    f2 = f5;
                                    i16 = i6;
                                    paint5 = paint;
                                    paint6 = paint2;
                                    arrayList6 = arrayList7;
                                }
                                int i23 = i16;
                                devModeDraw2 = devModeDraw;
                                canvas2 = canvas;
                                devModeDraw2.drawAll(canvas2, max, devModeDraw2.mKeyFrameCount, motionController2);
                                paint4.setColor(-21965);
                                paint6.setColor(-2067046);
                                paint5.setColor(-2067046);
                                paint7.setColor(-13391360);
                                float f10 = -i23;
                                canvas2.translate(f10, f10);
                                devModeDraw2.drawAll(canvas2, max, devModeDraw2.mKeyFrameCount, motionController2);
                                if (max == 5) {
                                    devModeDraw2.mPath.reset();
                                    int i24 = 0;
                                    while (i24 <= 50) {
                                        motionController2.mSpline[0].getPos(motionController2.getAdjustedPosition(i24 / 50, null), motionController2.mInterpolateData);
                                        int[] iArr2 = motionController2.mInterpolateVariables;
                                        double[] dArr2 = motionController2.mInterpolateData;
                                        float f11 = motionPaths.f27x;
                                        float f12 = motionPaths.f28y;
                                        float f13 = motionPaths.width;
                                        float f14 = motionPaths.height;
                                        int i25 = 0;
                                        while (i25 < iArr2.length) {
                                            MotionController motionController3 = motionController2;
                                            float f15 = (float) dArr2[i25];
                                            int i26 = iArr2[i25];
                                            if (i26 == 1) {
                                                f11 = f15;
                                            } else if (i26 == 2) {
                                                f12 = f15;
                                            } else if (i26 == 3) {
                                                f13 = f15;
                                            } else if (i26 == 4) {
                                                f14 = f15;
                                            }
                                            i25++;
                                            motionController2 = motionController3;
                                        }
                                        MotionController motionController4 = motionController2;
                                        if (motionPaths.mRelativeToController != null) {
                                            double d5 = 0.0f;
                                            double d6 = f11;
                                            double d7 = f12;
                                            motionController = motionController4;
                                            motionPaths2 = motionPaths;
                                            float sin = (float) (((Math.sin(d7) * d6) + d5) - (f13 / 2.0f));
                                            f12 = (float) ((d5 - (Math.cos(d7) * d6)) - (f14 / 2.0f));
                                            f11 = sin;
                                        } else {
                                            motionController = motionController4;
                                            motionPaths2 = motionPaths;
                                        }
                                        float f16 = f13 + f11;
                                        float f17 = f14 + f12;
                                        Float.isNaN(Float.NaN);
                                        Float.isNaN(Float.NaN);
                                        float f18 = f11 + 0.0f;
                                        float f19 = f12 + 0.0f;
                                        float f20 = f16 + 0.0f;
                                        float f21 = f17 + 0.0f;
                                        float[] fArr4 = devModeDraw2.mRectangle;
                                        fArr4[0] = f18;
                                        fArr4[1] = f19;
                                        fArr4[2] = f20;
                                        fArr4[3] = f19;
                                        fArr4[4] = f20;
                                        fArr4[5] = f21;
                                        fArr4[6] = f18;
                                        fArr4[7] = f21;
                                        devModeDraw2.mPath.moveTo(f18, f19);
                                        devModeDraw2.mPath.lineTo(fArr4[2], fArr4[3]);
                                        devModeDraw2.mPath.lineTo(fArr4[4], fArr4[5]);
                                        devModeDraw2.mPath.lineTo(fArr4[6], fArr4[7]);
                                        devModeDraw2.mPath.close();
                                        i24++;
                                        motionPaths = motionPaths2;
                                        motionController2 = motionController;
                                    }
                                    i4 = 0;
                                    i5 = 1;
                                    paint4.setColor(1140850688);
                                    canvas2.translate(2.0f, 2.0f);
                                    canvas2.drawPath(devModeDraw2.mPath, paint4);
                                    canvas2.translate(-2.0f, -2.0f);
                                    paint4.setColor(-65536);
                                    canvas2.drawPath(devModeDraw2.mPath, paint4);
                                } else {
                                    i4 = 0;
                                    i5 = 1;
                                }
                                devModeDraw4 = devModeDraw2;
                                canvas4 = canvas2;
                                i8 = i5;
                            } else {
                                canvas2 = canvas;
                                i3 = i10;
                                devModeDraw2 = devModeDraw;
                                i8 = 1;
                                i4 = 0;
                            }
                            devModeDraw3 = devModeDraw2;
                            canvas3 = canvas2;
                            it4 = it;
                            i11 = i;
                            i10 = i3;
                            i7 = i4;
                        }
                    }
                    canvas.restore();
                }
                arrayList = motionLayout.mDecoratorsHelpers;
                if (arrayList == null) {
                    Iterator it8 = arrayList.iterator();
                    while (it8.hasNext()) {
                        ((MotionHelper) it8.next()).getClass();
                    }
                    return;
                }
                return;
            }
        }
        motionLayout = this;
        arrayList = motionLayout.mDecoratorsHelpers;
        if (arrayList == null) {
        }
    }

    public final void endTrigger(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            MotionController motionController = (MotionController) this.mFrameArrayList.get(getChildAt(i));
            if (motionController != null && "button".equals(Debug.getName(motionController.mView)) && motionController.mKeyTriggers != null) {
                int i2 = 0;
                while (true) {
                    KeyTrigger[] keyTriggerArr = motionController.mKeyTriggers;
                    if (i2 < keyTriggerArr.length) {
                        keyTriggerArr[i2].conditionallyFire(motionController.mView, z ? -100.0f : 100.0f);
                        i2++;
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:118:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x016c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void evaluate(boolean z) {
        boolean z2;
        char c;
        float f;
        int childCount;
        Interpolator interpolator;
        int i;
        int i2;
        boolean z3;
        if (this.mTransitionLastTime == -1) {
            this.mTransitionLastTime = System.nanoTime();
        }
        float f2 = this.mTransitionLastPosition;
        if (f2 > 0.0f && f2 < 1.0f) {
            this.mCurrentState = -1;
        }
        boolean z4 = false;
        if (this.mKeepAnimating || (this.mInTransition && (z || this.mTransitionGoalPosition != f2))) {
            float signum = Math.signum(this.mTransitionGoalPosition - f2);
            long nanoTime = System.nanoTime();
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
                c = motionInterpolator2 == stopLogic ? stopLogic.mEngine.isStopped() ? (char) 2 : (char) 1 : (char) 0;
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
                if (c != 1) {
                    if ((signum > 0.0f && f >= this.mTransitionGoalPosition) || (signum <= 0.0f && f <= this.mTransitionGoalPosition)) {
                        f = this.mTransitionGoalPosition;
                        this.mInTransition = false;
                    }
                    if (f >= 1.0f || f <= 0.0f) {
                        this.mInTransition = false;
                        setState(TransitionState.FINISHED);
                    }
                }
                childCount = getChildCount();
                this.mKeepAnimating = false;
                long nanoTime2 = System.nanoTime();
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
                    MotionController motionController = (MotionController) this.mFrameArrayList.get(childAt);
                    if (motionController != null) {
                        this.mKeepAnimating = motionController.interpolate(interpolation, nanoTime2, childAt, this.mKeyCache) | this.mKeepAnimating;
                    }
                }
                boolean z5 = (signum <= 0.0f && f >= this.mTransitionGoalPosition) || (signum <= 0.0f && f <= this.mTransitionGoalPosition);
                if (!this.mKeepAnimating && !this.mInTransition && z5) {
                    setState(TransitionState.FINISHED);
                }
                if (this.mMeasureDuringTransition) {
                    requestLayout();
                }
                this.mKeepAnimating = (!z5) | this.mKeepAnimating;
                if (f <= 0.0f && (i2 = this.mBeginState) != -1 && this.mCurrentState != i2) {
                    this.mCurrentState = i2;
                    this.mScene.getConstraintSet(i2).applyCustomAttributes(this);
                    setState(TransitionState.FINISHED);
                    z4 = true;
                }
                if (f >= 1.0d) {
                    int i3 = this.mCurrentState;
                    int i4 = this.mEndState;
                    if (i3 != i4) {
                        this.mCurrentState = i4;
                        this.mScene.getConstraintSet(i4).applyCustomAttributes(this);
                        setState(TransitionState.FINISHED);
                        z4 = true;
                    }
                }
                if (!this.mKeepAnimating || this.mInTransition) {
                    invalidate();
                } else if ((signum > 0.0f && f == 1.0f) || (signum < 0.0f && f == 0.0f)) {
                    setState(TransitionState.FINISHED);
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
            if (c != 1) {
            }
            childCount = getChildCount();
            this.mKeepAnimating = false;
            long nanoTime22 = System.nanoTime();
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
                setState(TransitionState.FINISHED);
            }
            if (this.mMeasureDuringTransition) {
            }
            this.mKeepAnimating = (!z5) | this.mKeepAnimating;
            if (f <= 0.0f) {
                this.mCurrentState = i2;
                this.mScene.getConstraintSet(i2).applyCustomAttributes(this);
                setState(TransitionState.FINISHED);
                z4 = true;
            }
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

    public final void fireTransitionChange() {
        CopyOnWriteArrayList copyOnWriteArrayList;
        CopyOnWriteArrayList copyOnWriteArrayList2 = this.mTransitionListeners;
        if (copyOnWriteArrayList2 == null || copyOnWriteArrayList2.isEmpty() || this.mListenerPosition == this.mTransitionPosition) {
            return;
        }
        if (this.mListenerState != -1 && (copyOnWriteArrayList = this.mTransitionListeners) != null) {
            Iterator it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                ((TransitionListener) it.next()).getClass();
            }
        }
        this.mListenerState = -1;
        this.mListenerPosition = this.mTransitionPosition;
        CopyOnWriteArrayList copyOnWriteArrayList3 = this.mTransitionListeners;
        if (copyOnWriteArrayList3 != null) {
            Iterator it2 = copyOnWriteArrayList3.iterator();
            while (it2.hasNext()) {
                ((TransitionListener) it2.next()).onTransitionChange();
            }
        }
    }

    public final void fireTransitionCompleted() {
        int i;
        CopyOnWriteArrayList copyOnWriteArrayList = this.mTransitionListeners;
        if (copyOnWriteArrayList != null && !copyOnWriteArrayList.isEmpty() && this.mListenerState == -1) {
            this.mListenerState = this.mCurrentState;
            if (this.mTransitionCompleted.isEmpty()) {
                i = -1;
            } else {
                i = ((Integer) this.mTransitionCompleted.get(r0.size() - 1)).intValue();
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
        }
    }

    public final void getAnchorDpDt(int i, float f, float f2, float f3, float[] fArr) {
        HashMap hashMap = this.mFrameArrayList;
        View viewById = getViewById(i);
        MotionController motionController = (MotionController) hashMap.get(viewById);
        if (motionController == null) {
            MotionLayout$$ExternalSyntheticOutline0.m23m("WARNING could not find view id ", viewById == null ? AbstractC0000x2c234b15.m0m("", i) : viewById.getContext().getResources().getResourceName(i), "MotionLayout");
        } else {
            motionController.getDpDt(f, f2, f3, fArr);
            viewById.getY();
        }
    }

    public final ConstraintSet getConstraintSet(int i) {
        MotionScene motionScene = this.mScene;
        if (motionScene == null) {
            return null;
        }
        return motionScene.getConstraintSet(i);
    }

    public final MotionScene.Transition getTransition(int i) {
        Iterator it = this.mScene.mTransitionList.iterator();
        while (it.hasNext()) {
            MotionScene.Transition transition = (MotionScene.Transition) it.next();
            if (transition.mId == i) {
                return transition;
            }
        }
        return null;
    }

    public final boolean handlesTouchEvent(float f, float f2, MotionEvent motionEvent, View view) {
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

    public final void init(AttributeSet attributeSet) {
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
                String name = Debug.getName(startId, getContext());
                int childCount = getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = getChildAt(i2);
                    int id = childAt.getId();
                    if (id == -1) {
                        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("CHECK: ", name, " ALL VIEWS SHOULD HAVE ID's ");
                        m4m.append(childAt.getClass().getName());
                        m4m.append(" does not!");
                        Log.w("MotionLayout", m4m.toString());
                    }
                    if (constraintSet.getConstraint(id) == null) {
                        StringBuilder m4m2 = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("CHECK: ", name, " NO CONSTRAINTS for ");
                        m4m2.append(Debug.getName(childAt));
                        Log.w("MotionLayout", m4m2.toString());
                    }
                }
                Integer[] numArr = (Integer[]) constraintSet.mConstraints.keySet().toArray(new Integer[0]);
                int length = numArr.length;
                int[] iArr = new int[length];
                for (int i3 = 0; i3 < length; i3++) {
                    iArr[i3] = numArr[i3].intValue();
                }
                for (int i4 = 0; i4 < length; i4++) {
                    int i5 = iArr[i4];
                    String name2 = Debug.getName(i5, getContext());
                    if (findViewById(iArr[i4]) == null) {
                        Log.w("MotionLayout", "CHECK: " + name + " NO View matches id " + name2);
                    }
                    if (constraintSet.get(i5).layout.mHeight == -1) {
                        Log.w("MotionLayout", MotionLayout$$ExternalSyntheticOutline0.m22m("CHECK: ", name, "(", name2, ") no LAYOUT_HEIGHT"));
                    }
                    if (constraintSet.get(i5).layout.mWidth == -1) {
                        Log.w("MotionLayout", MotionLayout$$ExternalSyntheticOutline0.m22m("CHECK: ", name, "(", name2, ") no LAYOUT_HEIGHT"));
                    }
                }
                SparseIntArray sparseIntArray = new SparseIntArray();
                SparseIntArray sparseIntArray2 = new SparseIntArray();
                Iterator it = this.mScene.mTransitionList.iterator();
                while (it.hasNext()) {
                    MotionScene.Transition transition = (MotionScene.Transition) it.next();
                    MotionScene.Transition transition2 = this.mScene.mCurrentTransition;
                    if (transition.mConstraintSetStart == transition.mConstraintSetEnd) {
                        Log.e("MotionLayout", "CHECK: start and end constraint set should not be the same!");
                    }
                    int i6 = transition.mConstraintSetStart;
                    int i7 = transition.mConstraintSetEnd;
                    String name3 = Debug.getName(i6, getContext());
                    String name4 = Debug.getName(i7, getContext());
                    if (sparseIntArray.get(i6) == i7) {
                        Log.e("MotionLayout", "CHECK: two transitions with the same start and end " + name3 + "->" + name4);
                    }
                    if (sparseIntArray2.get(i7) == i6) {
                        Log.e("MotionLayout", "CHECK: you can't have reverse transitions" + name3 + "->" + name4);
                    }
                    sparseIntArray.put(i6, i7);
                    sparseIntArray2.put(i7, i6);
                    if (this.mScene.getConstraintSet(i6) == null) {
                        Log.e("MotionLayout", " no such constraintSetStart " + name3);
                    }
                    if (this.mScene.getConstraintSet(i7) == null) {
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
        MotionScene.Transition transition3 = this.mScene.mCurrentTransition;
        this.mEndState = transition3 != null ? transition3.mConstraintSetEnd : -1;
    }

    @Override // android.view.View
    public final boolean isAttachedToWindow() {
        return super.isAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        MotionScene.Transition transition;
        int i;
        boolean z;
        super.onAttachedToWindow();
        Display display = getDisplay();
        if (display != null) {
            display.getRotation();
        }
        MotionScene motionScene = this.mScene;
        if (motionScene != null && (i = this.mCurrentState) != -1) {
            ConstraintSet constraintSet = motionScene.getConstraintSet(i);
            MotionScene motionScene2 = this.mScene;
            int i2 = 0;
            while (true) {
                SparseArray sparseArray = motionScene2.mConstraintSetMap;
                if (i2 >= sparseArray.size()) {
                    break;
                }
                int keyAt = sparseArray.keyAt(i2);
                SparseIntArray sparseIntArray = motionScene2.mDeriveMap;
                int i3 = sparseIntArray.get(keyAt);
                int size = sparseIntArray.size();
                while (i3 > 0) {
                    if (i3 != keyAt) {
                        int i4 = size - 1;
                        if (size >= 0) {
                            i3 = sparseIntArray.get(i3);
                            size = i4;
                        }
                    }
                    z = true;
                    break;
                }
                z = false;
                if (z) {
                    Log.e("MotionScene", "Cannot be derived from yourself");
                    break;
                } else {
                    motionScene2.readConstraintChain(keyAt, this);
                    i2++;
                }
            }
            ArrayList arrayList = this.mDecoratorsHelpers;
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((MotionHelper) it.next()).getClass();
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
            stateCache.apply();
            return;
        }
        MotionScene motionScene3 = this.mScene;
        if (motionScene3 == null || (transition = motionScene3.mCurrentTransition) == null || transition.mAutoTransition != 4) {
            return;
        }
        animateTo(1.0f);
        this.mOnComplete = null;
        setState(TransitionState.SETUP);
        setState(TransitionState.MOVING);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        TouchResponse touchResponse;
        int i;
        RectF touchRegion;
        MotionLayout motionLayout;
        int i2;
        ViewTransition viewTransition;
        int i3;
        MotionScene motionScene = this.mScene;
        if (motionScene == null || !this.mInteractionEnabled) {
            return false;
        }
        ViewTransitionController viewTransitionController = motionScene.mViewTransitionController;
        if (viewTransitionController != null && (i2 = (motionLayout = viewTransitionController.mMotionLayout).mCurrentState) != -1) {
            HashSet hashSet = viewTransitionController.mRelatedViews;
            ArrayList arrayList = viewTransitionController.viewTransitions;
            if (hashSet == null) {
                viewTransitionController.mRelatedViews = new HashSet();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ViewTransition viewTransition2 = (ViewTransition) it.next();
                    int childCount = motionLayout.getChildCount();
                    for (int i4 = 0; i4 < childCount; i4++) {
                        View childAt = motionLayout.getChildAt(i4);
                        if (viewTransition2.matchesView(childAt)) {
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
            ArrayList arrayList2 = viewTransitionController.animations;
            int i5 = 2;
            if (arrayList2 != null && !arrayList2.isEmpty()) {
                Iterator it2 = viewTransitionController.animations.iterator();
                while (it2.hasNext()) {
                    ViewTransition.Animate animate = (ViewTransition.Animate) it2.next();
                    if (action != 1) {
                        if (action != 2) {
                            animate.getClass();
                        } else {
                            View view = animate.mMC.mView;
                            Rect rect2 = animate.mTempRec;
                            view.getHitRect(rect2);
                            if (!rect2.contains((int) x, (int) y) && !animate.reverse) {
                                animate.reverse();
                            }
                        }
                    } else if (!animate.reverse) {
                        animate.reverse();
                    }
                }
            }
            if (action == 0 || action == 1) {
                ConstraintSet constraintSet = motionLayout.getConstraintSet(i2);
                Iterator it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    ViewTransition viewTransition3 = (ViewTransition) it3.next();
                    int i6 = viewTransition3.mOnStateTransition;
                    if (i6 != 1 ? !(i6 != i5 ? !(i6 == 3 && action == 0) : action != 1) : action == 0) {
                        Iterator it4 = viewTransitionController.mRelatedViews.iterator();
                        while (it4.hasNext()) {
                            View view2 = (View) it4.next();
                            if (viewTransition3.matchesView(view2)) {
                                view2.getHitRect(rect);
                                if (rect.contains((int) x, (int) y)) {
                                    viewTransition = viewTransition3;
                                    i3 = i5;
                                    viewTransition3.applyTransition(viewTransitionController, viewTransitionController.mMotionLayout, i2, constraintSet, view2);
                                } else {
                                    viewTransition = viewTransition3;
                                    i3 = i5;
                                }
                                viewTransition3 = viewTransition;
                                i5 = i3;
                            }
                        }
                    }
                }
            }
        }
        MotionScene.Transition transition = this.mScene.mCurrentTransition;
        if (transition == null || !(!transition.mDisable) || (touchResponse = transition.mTouchResponse) == null) {
            return false;
        }
        if ((motionEvent.getAction() == 0 && (touchRegion = touchResponse.getTouchRegion(this, new RectF())) != null && !touchRegion.contains(motionEvent.getX(), motionEvent.getY())) || (i = touchResponse.mTouchRegionId) == -1) {
            return false;
        }
        View view3 = this.mRegionView;
        if (view3 == null || view3.getId() != i) {
            this.mRegionView = findViewById(i);
        }
        if (this.mRegionView == null) {
            return false;
        }
        this.mBoundsCheck.set(r1.getLeft(), this.mRegionView.getTop(), this.mRegionView.getRight(), this.mRegionView.getBottom());
        if (!this.mBoundsCheck.contains(motionEvent.getX(), motionEvent.getY()) || handlesTouchEvent(this.mRegionView.getLeft(), this.mRegionView.getTop(), motionEvent, this.mRegionView)) {
            return false;
        }
        return onTouchEvent(motionEvent);
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
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

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0053, code lost:
    
        if (((r6 == r9.mStartId && r7 == r9.mEndId) ? false : true) != false) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:70:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00f7  */
    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onMeasure(int i, int i2) {
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
            nanoTime = System.nanoTime();
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
            long nanoTime2 = System.nanoTime();
            interpolator = this.mProgressInterpolator;
            if (interpolator != null) {
                f = interpolator.getInterpolation(f);
            }
            for (int i6 = 0; i6 < childCount; i6++) {
                View childAt = getChildAt(i6);
                MotionController motionController = (MotionController) this.mFrameArrayList.get(childAt);
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
        nanoTime = System.nanoTime();
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
            long nanoTime22 = System.nanoTime();
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
        long nanoTime222 = System.nanoTime();
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

    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v5 */
    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedPreScroll(final View view, int i, int i2, int[] iArr, int i3) {
        MotionScene.Transition transition;
        boolean z;
        ?? r1;
        TouchResponse touchResponse;
        float f;
        TouchResponse touchResponse2;
        TouchResponse touchResponse3;
        TouchResponse touchResponse4;
        int i4;
        MotionScene motionScene = this.mScene;
        if (motionScene == null || (transition = motionScene.mCurrentTransition) == null || !(!transition.mDisable)) {
            return;
        }
        int i5 = -1;
        if (!z || (touchResponse4 = transition.mTouchResponse) == null || (i4 = touchResponse4.mTouchRegionId) == -1 || view.getId() == i4) {
            MotionScene.Transition transition2 = motionScene.mCurrentTransition;
            if ((transition2 == null || (touchResponse3 = transition2.mTouchResponse) == null) ? false : touchResponse3.mMoveWhenScrollAtTop) {
                TouchResponse touchResponse5 = transition.mTouchResponse;
                if (touchResponse5 != null && (touchResponse5.mFlags & 4) != 0) {
                    i5 = i2;
                }
                float f2 = this.mTransitionPosition;
                if ((f2 == 1.0f || f2 == 0.0f) && view.canScrollVertically(i5)) {
                    return;
                }
            }
            TouchResponse touchResponse6 = transition.mTouchResponse;
            if (touchResponse6 != null && (touchResponse6.mFlags & 1) != 0) {
                float f3 = i;
                float f4 = i2;
                MotionScene.Transition transition3 = motionScene.mCurrentTransition;
                if (transition3 == null || (touchResponse2 = transition3.mTouchResponse) == null) {
                    f = 0.0f;
                } else {
                    MotionLayout motionLayout = touchResponse2.mMotionLayout;
                    motionLayout.getAnchorDpDt(touchResponse2.mTouchAnchorId, motionLayout.mTransitionLastPosition, touchResponse2.mTouchAnchorX, touchResponse2.mTouchAnchorY, touchResponse2.mAnchorDpDt);
                    float f5 = touchResponse2.mTouchDirectionX;
                    float[] fArr = touchResponse2.mAnchorDpDt;
                    if (f5 != 0.0f) {
                        if (fArr[0] == 0.0f) {
                            fArr[0] = 1.0E-7f;
                        }
                        f = (f3 * f5) / fArr[0];
                    } else {
                        if (fArr[1] == 0.0f) {
                            fArr[1] = 1.0E-7f;
                        }
                        f = (f4 * touchResponse2.mTouchDirectionY) / fArr[1];
                    }
                }
                float f6 = this.mTransitionLastPosition;
                if ((f6 <= 0.0f && f < 0.0f) || (f6 >= 1.0f && f > 0.0f)) {
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
            float f7 = this.mTransitionPosition;
            long nanoTime = System.nanoTime();
            float f8 = i;
            this.mScrollTargetDX = f8;
            float f9 = i2;
            this.mScrollTargetDY = f9;
            this.mScrollTargetDT = (float) ((nanoTime - this.mScrollTargetTime) * 1.0E-9d);
            this.mScrollTargetTime = nanoTime;
            MotionScene.Transition transition4 = motionScene.mCurrentTransition;
            if (transition4 != null && (touchResponse = transition4.mTouchResponse) != null) {
                MotionLayout motionLayout2 = touchResponse.mMotionLayout;
                float f10 = motionLayout2.mTransitionLastPosition;
                if (!touchResponse.mDragStarted) {
                    touchResponse.mDragStarted = true;
                    motionLayout2.setProgress(f10);
                }
                touchResponse.mMotionLayout.getAnchorDpDt(touchResponse.mTouchAnchorId, f10, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                float f11 = touchResponse.mTouchDirectionX;
                float[] fArr2 = touchResponse.mAnchorDpDt;
                if (Math.abs((touchResponse.mTouchDirectionY * fArr2[1]) + (f11 * fArr2[0])) < 0.01d) {
                    fArr2[0] = 0.01f;
                    fArr2[1] = 0.01f;
                }
                float f12 = touchResponse.mTouchDirectionX;
                float max = Math.max(Math.min(f10 + (f12 != 0.0f ? (f8 * f12) / fArr2[0] : (f9 * touchResponse.mTouchDirectionY) / fArr2[1]), 1.0f), 0.0f);
                if (max != motionLayout2.mTransitionLastPosition) {
                    motionLayout2.setProgress(max);
                }
            }
            if (f7 != this.mTransitionPosition) {
                iArr[0] = i;
                r1 = 1;
                iArr[1] = i2;
            } else {
                r1 = 1;
            }
            evaluate(false);
            if (iArr[0] == 0 && iArr[r1] == 0) {
                return;
            }
            this.mUndergoingMotion = r1;
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        this.mScrollTargetTime = System.nanoTime();
        this.mScrollTargetDT = 0.0f;
        this.mScrollTargetDX = 0.0f;
        this.mScrollTargetDY = 0.0f;
    }

    public final void onNewStateAttachHandlers() {
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
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                MotionScene.Transition transition2 = (MotionScene.Transition) it.next();
                if (transition2.mOnClicks.size() > 0) {
                    Iterator it2 = transition2.mOnClicks.iterator();
                    while (it2.hasNext()) {
                        ((MotionScene.Transition.TransitionOnClick) it2.next()).removeOnClickListeners(this);
                    }
                }
            }
            ArrayList arrayList2 = motionScene2.mAbstractTransitionList;
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                MotionScene.Transition transition3 = (MotionScene.Transition) it3.next();
                if (transition3.mOnClicks.size() > 0) {
                    Iterator it4 = transition3.mOnClicks.iterator();
                    while (it4.hasNext()) {
                        ((MotionScene.Transition.TransitionOnClick) it4.next()).removeOnClickListeners(this);
                    }
                }
            }
            Iterator it5 = arrayList.iterator();
            while (it5.hasNext()) {
                MotionScene.Transition transition4 = (MotionScene.Transition) it5.next();
                if (transition4.mOnClicks.size() > 0) {
                    Iterator it6 = transition4.mOnClicks.iterator();
                    while (it6.hasNext()) {
                        ((MotionScene.Transition.TransitionOnClick) it6.next()).addOnClickListeners(this, i, transition4);
                    }
                }
            }
            Iterator it7 = arrayList2.iterator();
            while (it7.hasNext()) {
                MotionScene.Transition transition5 = (MotionScene.Transition) it7.next();
                if (transition5.mOnClicks.size() > 0) {
                    Iterator it8 = transition5.mOnClicks.iterator();
                    while (it8.hasNext()) {
                        ((MotionScene.Transition.TransitionOnClick) it8.next()).addOnClickListeners(this, i, transition5);
                    }
                }
            }
        }
        if (!this.mScene.supportTouch() || (transition = this.mScene.mCurrentTransition) == null || (touchResponse = transition.mTouchResponse) == null) {
            return;
        }
        int i2 = touchResponse.mTouchAnchorId;
        if (i2 != -1) {
            MotionLayout motionLayout = touchResponse.mMotionLayout;
            view = motionLayout.findViewById(i2);
            if (view == null) {
                Log.e("TouchResponse", "cannot find TouchAnchorId @id/" + Debug.getName(touchResponse.mTouchAnchorId, motionLayout.getContext()));
            }
        } else {
            view = null;
        }
        if (view instanceof NestedScrollView) {
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            nestedScrollView.setOnTouchListener(new View.OnTouchListener(touchResponse) { // from class: androidx.constraintlayout.motion.widget.TouchResponse.1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    return false;
                }
            });
            nestedScrollView.mOnScrollChangeListener = new NestedScrollView.OnScrollChangeListener(touchResponse) { // from class: androidx.constraintlayout.motion.widget.TouchResponse.2
                @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
                public final void onScrollChange(NestedScrollView nestedScrollView2) {
                }
            };
        }
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
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
    public final boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        MotionScene.Transition transition;
        TouchResponse touchResponse;
        MotionScene motionScene = this.mScene;
        return (motionScene == null || (transition = motionScene.mCurrentTransition) == null || (touchResponse = transition.mTouchResponse) == null || (touchResponse.mFlags & 2) != 0) ? false : true;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onStopNestedScroll(View view, int i) {
        TouchResponse touchResponse;
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
            float f4 = motionLayout.mTransitionLastPosition;
            motionLayout.getAnchorDpDt(touchResponse.mTouchAnchorId, f4, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
            float f5 = touchResponse.mTouchDirectionX;
            float[] fArr = touchResponse.mAnchorDpDt;
            float f6 = f5 != 0.0f ? (f2 * f5) / fArr[0] : (f3 * touchResponse.mTouchDirectionY) / fArr[1];
            if (!Float.isNaN(f6)) {
                f4 += f6 / 3.0f;
            }
            if (f4 != 0.0f) {
                boolean z = f4 != 1.0f;
                int i2 = touchResponse.mOnTouchUp;
                if ((i2 != 3) && z) {
                    motionLayout.touchAnimateTo(((double) f4) >= 0.5d ? 1.0f : 0.0f, f6, i2);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:69:0x00ed, code lost:
    
        if (r8.contains(r14.getX(), r14.getY()) == false) goto L70;
     */
    /* JADX WARN: Removed duplicated region for block: B:174:0x07ad  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x04ba  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x04f7  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0504  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x04dd  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x07d6  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x07db  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x07e0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x07d8  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        MotionScene motionScene;
        boolean z;
        MotionLayout motionLayout;
        MyTracker myTracker;
        MyTracker myTracker2;
        TouchResponse touchResponse;
        char c;
        char c2;
        int i;
        char c3;
        char c4;
        char c5;
        float right;
        float f;
        int top;
        int bottom;
        int i2;
        float f2;
        int i3;
        char c6;
        MotionScene.Transition transition;
        MotionEvent motionEvent2;
        RectF rectF;
        MotionScene.Transition transition2;
        int i4;
        RectF rectF2;
        Iterator it;
        MotionScene.Transition transition3;
        float f3;
        MotionEvent motionEvent3;
        RectF rectF3;
        float f4;
        TouchResponse touchResponse2;
        MotionScene motionScene2 = this.mScene;
        if (motionScene2 == null || !this.mInteractionEnabled || !motionScene2.supportTouch()) {
            return super.onTouchEvent(motionEvent);
        }
        MotionScene motionScene3 = this.mScene;
        if (motionScene3.mCurrentTransition != null && !(!r3.mDisable)) {
            return super.onTouchEvent(motionEvent);
        }
        int i5 = this.mCurrentState;
        RectF rectF4 = new RectF();
        MyTracker myTracker3 = motionScene3.mVelocityTracker;
        MotionLayout motionLayout2 = motionScene3.mMotionLayout;
        if (myTracker3 == null) {
            motionLayout2.getClass();
            MyTracker myTracker4 = MyTracker.f26me;
            VelocityTracker obtain = VelocityTracker.obtain();
            MyTracker myTracker5 = MyTracker.f26me;
            myTracker5.tracker = obtain;
            motionScene3.mVelocityTracker = myTracker5;
        }
        VelocityTracker velocityTracker = motionScene3.mVelocityTracker.tracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(motionEvent);
        }
        if (i5 != -1) {
            int action = motionEvent.getAction();
            if (action == 0) {
                motionScene3.mLastTouchX = motionEvent.getRawX();
                motionScene3.mLastTouchY = motionEvent.getRawY();
                motionScene3.mLastTouchDown = motionEvent;
                motionScene3.mIgnoreTouch = false;
                TouchResponse touchResponse3 = motionScene3.mCurrentTransition.mTouchResponse;
                if (touchResponse3 != null) {
                    RectF limitBoundsTo = touchResponse3.getLimitBoundsTo(motionLayout2, rectF4);
                    if (limitBoundsTo == null || limitBoundsTo.contains(motionScene3.mLastTouchDown.getX(), motionScene3.mLastTouchDown.getY())) {
                        RectF touchRegion = motionScene3.mCurrentTransition.mTouchResponse.getTouchRegion(motionLayout2, rectF4);
                        if (touchRegion == null || touchRegion.contains(motionScene3.mLastTouchDown.getX(), motionScene3.mLastTouchDown.getY())) {
                            motionScene3.mMotionOutsideRegion = false;
                        } else {
                            motionScene3.mMotionOutsideRegion = true;
                        }
                        TouchResponse touchResponse4 = motionScene3.mCurrentTransition.mTouchResponse;
                        float f5 = motionScene3.mLastTouchX;
                        float f6 = motionScene3.mLastTouchY;
                        touchResponse4.mLastTouchX = f5;
                        touchResponse4.mLastTouchY = f6;
                    } else {
                        motionScene3.mLastTouchDown = null;
                        motionScene3.mIgnoreTouch = true;
                    }
                }
            } else if (action == 2 && !motionScene3.mIgnoreTouch) {
                float rawY = motionEvent.getRawY() - motionScene3.mLastTouchY;
                float rawX = motionEvent.getRawX() - motionScene3.mLastTouchX;
                if ((rawX != 0.0d || rawY != 0.0d) && (motionEvent2 = motionScene3.mLastTouchDown) != null) {
                    if (i5 != -1) {
                        StateSet stateSet = motionScene3.mStateSet;
                        if (stateSet == null || (i4 = stateSet.stateGetConstraintID(i5)) == -1) {
                            i4 = i5;
                        }
                        ArrayList arrayList = new ArrayList();
                        Iterator it2 = motionScene3.mTransitionList.iterator();
                        while (it2.hasNext()) {
                            MotionScene.Transition transition4 = (MotionScene.Transition) it2.next();
                            if (transition4.mConstraintSetStart == i4 || transition4.mConstraintSetEnd == i4) {
                                arrayList.add(transition4);
                            }
                        }
                        RectF rectF5 = new RectF();
                        Iterator it3 = arrayList.iterator();
                        float f7 = 0.0f;
                        transition2 = null;
                        while (it3.hasNext()) {
                            MotionScene.Transition transition5 = (MotionScene.Transition) it3.next();
                            if (transition5.mDisable || (touchResponse2 = transition5.mTouchResponse) == null) {
                                rectF2 = rectF5;
                                it = it3;
                                transition3 = transition2;
                                f3 = rawY;
                                motionEvent3 = motionEvent2;
                                rectF3 = rectF4;
                            } else {
                                touchResponse2.setRTL(motionScene3.mRtl);
                                RectF touchRegion2 = transition5.mTouchResponse.getTouchRegion(motionLayout2, rectF5);
                                if (touchRegion2 != null) {
                                    it = it3;
                                } else {
                                    it = it3;
                                }
                                RectF limitBoundsTo2 = transition5.mTouchResponse.getLimitBoundsTo(motionLayout2, rectF5);
                                if (limitBoundsTo2 == null || limitBoundsTo2.contains(motionEvent2.getX(), motionEvent2.getY())) {
                                    TouchResponse touchResponse5 = transition5.mTouchResponse;
                                    float f8 = (touchResponse5.mTouchDirectionY * rawY) + (touchResponse5.mTouchDirectionX * rawX);
                                    if (touchResponse5.mIsRotateMode) {
                                        float x = motionEvent2.getX() - transition5.mTouchResponse.mRotateCenterX;
                                        float y = motionEvent2.getY() - transition5.mTouchResponse.mRotateCenterY;
                                        rectF2 = rectF5;
                                        transition3 = transition2;
                                        f3 = rawY;
                                        motionEvent3 = motionEvent2;
                                        double atan2 = Math.atan2(rawY + y, rawX + x);
                                        double d = x;
                                        f4 = rawX;
                                        rectF3 = rectF4;
                                        f8 = ((float) (atan2 - Math.atan2(d, y))) * 10.0f;
                                    } else {
                                        rectF2 = rectF5;
                                        rectF3 = rectF4;
                                        transition3 = transition2;
                                        f3 = rawY;
                                        motionEvent3 = motionEvent2;
                                        f4 = rawX;
                                    }
                                    float f9 = (transition5.mConstraintSetEnd == i5 ? -1.0f : 1.1f) * f8;
                                    if (f9 > f7) {
                                        f7 = f9;
                                        transition2 = transition5;
                                        rectF4 = rectF3;
                                        rawX = f4;
                                        it3 = it;
                                        rectF5 = rectF2;
                                        rawY = f3;
                                        motionEvent2 = motionEvent3;
                                    }
                                    transition2 = transition3;
                                    rectF4 = rectF3;
                                    rawX = f4;
                                    it3 = it;
                                    rectF5 = rectF2;
                                    rawY = f3;
                                    motionEvent2 = motionEvent3;
                                }
                                rectF2 = rectF5;
                                rectF3 = rectF4;
                                transition3 = transition2;
                                f3 = rawY;
                                motionEvent3 = motionEvent2;
                            }
                            f4 = rawX;
                            transition2 = transition3;
                            rectF4 = rectF3;
                            rawX = f4;
                            it3 = it;
                            rectF5 = rectF2;
                            rawY = f3;
                            motionEvent2 = motionEvent3;
                        }
                        rectF = rectF4;
                    } else {
                        rectF = rectF4;
                        transition2 = motionScene3.mCurrentTransition;
                    }
                    if (transition2 != null) {
                        setTransition(transition2);
                        RectF touchRegion3 = motionScene3.mCurrentTransition.mTouchResponse.getTouchRegion(motionLayout2, rectF);
                        motionScene3.mMotionOutsideRegion = (touchRegion3 == null || touchRegion3.contains(motionScene3.mLastTouchDown.getX(), motionScene3.mLastTouchDown.getY())) ? false : true;
                        TouchResponse touchResponse6 = motionScene3.mCurrentTransition.mTouchResponse;
                        float f10 = motionScene3.mLastTouchX;
                        float f11 = motionScene3.mLastTouchY;
                        touchResponse6.mLastTouchX = f10;
                        touchResponse6.mLastTouchY = f11;
                        touchResponse6.mDragStarted = false;
                    }
                }
            }
            motionLayout = this;
            z = false;
            transition = motionLayout.mScene.mCurrentTransition;
            if ((transition.mTransitionFlags & 4) == 0 ? true : z) {
                return true;
            }
            return transition.mTouchResponse.mDragStarted;
        }
        if (!motionScene3.mIgnoreTouch) {
            MotionScene.Transition transition6 = motionScene3.mCurrentTransition;
            if (transition6 != null && (touchResponse = transition6.mTouchResponse) != null && !motionScene3.mMotionOutsideRegion) {
                MyTracker myTracker6 = motionScene3.mVelocityTracker;
                boolean z2 = touchResponse.mIsRotateMode;
                MotionLayout motionLayout3 = touchResponse.mMotionLayout;
                float[] fArr = touchResponse.mAnchorDpDt;
                if (z2) {
                    VelocityTracker velocityTracker2 = myTracker6.tracker;
                    if (velocityTracker2 != null) {
                        velocityTracker2.addMovement(motionEvent);
                    }
                    int action2 = motionEvent.getAction();
                    if (action2 != 0) {
                        int[] iArr = touchResponse.mTempLoc;
                        if (action2 == 1) {
                            motionScene = motionScene3;
                            touchResponse.mDragStarted = false;
                            VelocityTracker velocityTracker3 = myTracker6.tracker;
                            if (velocityTracker3 != null) {
                                velocityTracker3.computeCurrentVelocity(16);
                            }
                            VelocityTracker velocityTracker4 = myTracker6.tracker;
                            float xVelocity = velocityTracker4 != null ? velocityTracker4.getXVelocity() : 0.0f;
                            VelocityTracker velocityTracker5 = myTracker6.tracker;
                            float yVelocity = velocityTracker5 != null ? velocityTracker5.getYVelocity() : 0.0f;
                            float f12 = motionLayout3.mTransitionLastPosition;
                            float width = motionLayout3.getWidth() / 2.0f;
                            float height = motionLayout3.getHeight() / 2.0f;
                            int i6 = touchResponse.mRotationCenterId;
                            if (i6 != -1) {
                                View findViewById = motionLayout3.findViewById(i6);
                                motionLayout3.getLocationOnScreen(iArr);
                                right = ((findViewById.getRight() + findViewById.getLeft()) / 2.0f) + iArr[0];
                                f = iArr[1];
                                top = findViewById.getTop();
                                bottom = findViewById.getBottom();
                            } else {
                                int i7 = touchResponse.mTouchAnchorId;
                                if (i7 != -1) {
                                    View findViewById2 = motionLayout3.findViewById(((MotionController) motionLayout3.mFrameArrayList.get(motionLayout3.findViewById(i7))).mStartMotionPath.mAnimateRelativeTo);
                                    motionLayout3.getLocationOnScreen(iArr);
                                    right = ((findViewById2.getRight() + findViewById2.getLeft()) / 2.0f) + iArr[0];
                                    f = iArr[1];
                                    top = findViewById2.getTop();
                                    bottom = findViewById2.getBottom();
                                }
                                float rawX2 = motionEvent.getRawX() - width;
                                double degrees = Math.toDegrees(Math.atan2(motionEvent.getRawY() - height, rawX2));
                                i2 = touchResponse.mTouchAnchorId;
                                if (i2 == -1) {
                                    touchResponse.mMotionLayout.getAnchorDpDt(i2, f12, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                                    fArr[1] = (float) Math.toDegrees(fArr[1]);
                                } else {
                                    fArr[1] = 360.0f;
                                }
                                float degrees2 = ((float) (Math.toDegrees(Math.atan2(yVelocity + r5, xVelocity + rawX2)) - degrees)) * 62.5f;
                                f2 = Float.isNaN(degrees2) ? (((degrees2 * 3.0f) * touchResponse.mDragScale) / fArr[1]) + f12 : f12;
                                if (f2 == 0.0f && f2 != 1.0f && (i3 = touchResponse.mOnTouchUp) != 3) {
                                    float f13 = (degrees2 * touchResponse.mDragScale) / fArr[1];
                                    float f14 = ((double) f2) < 0.5d ? 0.0f : 1.0f;
                                    if (i3 == 6) {
                                        if (f12 + f13 < 0.0f) {
                                            f13 = Math.abs(f13);
                                        }
                                        f14 = 1.0f;
                                    }
                                    if (touchResponse.mOnTouchUp == 7) {
                                        if (f12 + f13 > 1.0f) {
                                            f13 = -Math.abs(f13);
                                        }
                                        f14 = 0.0f;
                                    }
                                    motionLayout3.touchAnimateTo(f14, f13 * 3.0f, touchResponse.mOnTouchUp);
                                    if (0.0f >= f12 || 1.0f <= f12) {
                                        motionLayout3.setState(TransitionState.FINISHED);
                                    }
                                } else if (0.0f < f2 || 1.0f <= f2) {
                                    motionLayout3.setState(TransitionState.FINISHED);
                                }
                            }
                            height = ((bottom + top) / 2.0f) + f;
                            width = right;
                            float rawX22 = motionEvent.getRawX() - width;
                            double degrees3 = Math.toDegrees(Math.atan2(motionEvent.getRawY() - height, rawX22));
                            i2 = touchResponse.mTouchAnchorId;
                            if (i2 == -1) {
                            }
                            float degrees22 = ((float) (Math.toDegrees(Math.atan2(yVelocity + r5, xVelocity + rawX22)) - degrees3)) * 62.5f;
                            if (Float.isNaN(degrees22)) {
                            }
                            if (f2 == 0.0f) {
                            }
                            if (0.0f < f2) {
                            }
                            motionLayout3.setState(TransitionState.FINISHED);
                        } else if (action2 == 2) {
                            motionEvent.getRawY();
                            motionEvent.getRawX();
                            float width2 = motionLayout3.getWidth() / 2.0f;
                            float height2 = motionLayout3.getHeight() / 2.0f;
                            int i8 = touchResponse.mRotationCenterId;
                            if (i8 != -1) {
                                View findViewById3 = motionLayout3.findViewById(i8);
                                motionLayout3.getLocationOnScreen(iArr);
                                height2 = ((findViewById3.getBottom() + findViewById3.getTop()) / 2.0f) + iArr[1];
                                width2 = iArr[0] + ((findViewById3.getRight() + findViewById3.getLeft()) / 2.0f);
                            } else {
                                int i9 = touchResponse.mTouchAnchorId;
                                if (i9 != -1) {
                                    if (motionLayout3.findViewById(((MotionController) motionLayout3.mFrameArrayList.get(motionLayout3.findViewById(i9))).mStartMotionPath.mAnimateRelativeTo) == null) {
                                        Log.e("TouchResponse", "could not find view to animate to");
                                    } else {
                                        motionLayout3.getLocationOnScreen(iArr);
                                        width2 = iArr[0] + ((r7.getRight() + r7.getLeft()) / 2.0f);
                                        height2 = iArr[1] + ((r7.getBottom() + r7.getTop()) / 2.0f);
                                    }
                                }
                            }
                            float rawX3 = motionEvent.getRawX() - width2;
                            float rawY2 = motionEvent.getRawY() - height2;
                            float atan22 = (float) (((Math.atan2(motionEvent.getRawY() - height2, motionEvent.getRawX() - width2) - Math.atan2(touchResponse.mLastTouchY - height2, touchResponse.mLastTouchX - width2)) * 180.0d) / 3.141592653589793d);
                            if (atan22 > 330.0f) {
                                atan22 -= 360.0f;
                            } else if (atan22 < -330.0f) {
                                atan22 += 360.0f;
                            }
                            if (Math.abs(atan22) > 0.01d || touchResponse.mDragStarted) {
                                float f15 = motionLayout3.mTransitionLastPosition;
                                if (!touchResponse.mDragStarted) {
                                    touchResponse.mDragStarted = true;
                                    motionLayout3.setProgress(f15);
                                }
                                int i10 = touchResponse.mTouchAnchorId;
                                if (i10 != -1) {
                                    motionScene = motionScene3;
                                    touchResponse.mMotionLayout.getAnchorDpDt(i10, f15, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                                    c6 = 1;
                                    fArr[1] = (float) Math.toDegrees(fArr[1]);
                                } else {
                                    motionScene = motionScene3;
                                    c6 = 1;
                                    fArr[1] = 360.0f;
                                }
                                float max = Math.max(Math.min(((atan22 * touchResponse.mDragScale) / fArr[c6]) + f15, 1.0f), 0.0f);
                                float f16 = motionLayout3.mTransitionLastPosition;
                                if (max != f16) {
                                    if (f16 == 0.0f || f16 == 1.0f) {
                                        motionLayout3.endTrigger(f16 == 0.0f);
                                    }
                                    motionLayout3.setProgress(max);
                                    VelocityTracker velocityTracker6 = myTracker6.tracker;
                                    if (velocityTracker6 != null) {
                                        velocityTracker6.computeCurrentVelocity(1000);
                                    }
                                    VelocityTracker velocityTracker7 = myTracker6.tracker;
                                    float xVelocity2 = velocityTracker7 != null ? velocityTracker7.getXVelocity() : 0.0f;
                                    VelocityTracker velocityTracker8 = myTracker6.tracker;
                                    double yVelocity2 = velocityTracker8 != null ? velocityTracker8.getYVelocity() : 0.0f;
                                    double d2 = xVelocity2;
                                    motionLayout3.mLastVelocity = (float) Math.toDegrees((float) ((Math.sin(Math.atan2(yVelocity2, d2) - r9) * Math.hypot(yVelocity2, d2)) / Math.hypot(rawX3, rawY2)));
                                } else {
                                    motionLayout3.mLastVelocity = 0.0f;
                                }
                                touchResponse.mLastTouchX = motionEvent.getRawX();
                                touchResponse.mLastTouchY = motionEvent.getRawY();
                            }
                        }
                        z = false;
                        MotionScene motionScene4 = motionScene;
                        motionScene4.mLastTouchX = motionEvent.getRawX();
                        motionScene4.mLastTouchY = motionEvent.getRawY();
                        if (motionEvent.getAction() == 1 || (myTracker = motionScene4.mVelocityTracker) == null) {
                            motionLayout = this;
                        } else {
                            VelocityTracker velocityTracker9 = myTracker.tracker;
                            if (velocityTracker9 != null) {
                                velocityTracker9.recycle();
                                myTracker2 = null;
                                myTracker.tracker = null;
                            } else {
                                myTracker2 = null;
                            }
                            motionScene4.mVelocityTracker = myTracker2;
                            motionLayout = this;
                            int i11 = motionLayout.mCurrentState;
                            if (i11 != -1) {
                                motionScene4.autoTransition(i11, motionLayout);
                            }
                        }
                    } else {
                        motionScene = motionScene3;
                        touchResponse.mLastTouchX = motionEvent.getRawX();
                        touchResponse.mLastTouchY = motionEvent.getRawY();
                        z = false;
                        touchResponse.mDragStarted = false;
                        MotionScene motionScene42 = motionScene;
                        motionScene42.mLastTouchX = motionEvent.getRawX();
                        motionScene42.mLastTouchY = motionEvent.getRawY();
                        if (motionEvent.getAction() == 1) {
                        }
                        motionLayout = this;
                    }
                } else {
                    motionScene = motionScene3;
                    VelocityTracker velocityTracker10 = myTracker6.tracker;
                    if (velocityTracker10 != null) {
                        velocityTracker10.addMovement(motionEvent);
                    }
                    int action3 = motionEvent.getAction();
                    if (action3 != 0) {
                        if (action3 == 1) {
                            touchResponse.mDragStarted = false;
                            VelocityTracker velocityTracker11 = myTracker6.tracker;
                            if (velocityTracker11 != null) {
                                velocityTracker11.computeCurrentVelocity(1000);
                            }
                            VelocityTracker velocityTracker12 = myTracker6.tracker;
                            float xVelocity3 = velocityTracker12 != null ? velocityTracker12.getXVelocity() : 0.0f;
                            VelocityTracker velocityTracker13 = myTracker6.tracker;
                            float yVelocity3 = velocityTracker13 != null ? velocityTracker13.getYVelocity() : 0.0f;
                            float f17 = motionLayout3.mTransitionLastPosition;
                            int i12 = touchResponse.mTouchAnchorId;
                            if (i12 != -1) {
                                motionLayout3.getAnchorDpDt(i12, f17, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                                c2 = 0;
                                c = 1;
                            } else {
                                float min = Math.min(motionLayout3.getWidth(), motionLayout3.getHeight());
                                c = 1;
                                fArr[1] = touchResponse.mTouchDirectionY * min;
                                c2 = 0;
                                fArr[0] = min * touchResponse.mTouchDirectionX;
                            }
                            float f18 = touchResponse.mTouchDirectionX != 0.0f ? xVelocity3 / fArr[c2] : yVelocity3 / fArr[c];
                            float f19 = !Float.isNaN(f18) ? (f18 / 3.0f) + f17 : f17;
                            if (f19 != 0.0f && f19 != 1.0f && (i = touchResponse.mOnTouchUp) != 3) {
                                float f20 = ((double) f19) < 0.5d ? 0.0f : 1.0f;
                                if (i == 6) {
                                    if (f17 + f18 < 0.0f) {
                                        f18 = Math.abs(f18);
                                    }
                                    f20 = 1.0f;
                                }
                                if (touchResponse.mOnTouchUp == 7) {
                                    if (f17 + f18 > 1.0f) {
                                        f18 = -Math.abs(f18);
                                    }
                                    f20 = 0.0f;
                                }
                                motionLayout3.touchAnimateTo(f20, f18, touchResponse.mOnTouchUp);
                                if (0.0f >= f17 || 1.0f <= f17) {
                                    motionLayout3.setState(TransitionState.FINISHED);
                                }
                            } else if (0.0f >= f19 || 1.0f <= f19) {
                                motionLayout3.setState(TransitionState.FINISHED);
                            }
                        } else if (action3 == 2) {
                            float rawY3 = motionEvent.getRawY() - touchResponse.mLastTouchY;
                            float rawX4 = motionEvent.getRawX() - touchResponse.mLastTouchX;
                            if (Math.abs((touchResponse.mTouchDirectionY * rawY3) + (touchResponse.mTouchDirectionX * rawX4)) > touchResponse.mDragThreshold || touchResponse.mDragStarted) {
                                float f21 = motionLayout3.mTransitionLastPosition;
                                if (!touchResponse.mDragStarted) {
                                    touchResponse.mDragStarted = true;
                                    motionLayout3.setProgress(f21);
                                }
                                int i13 = touchResponse.mTouchAnchorId;
                                if (i13 != -1) {
                                    touchResponse.mMotionLayout.getAnchorDpDt(i13, f21, touchResponse.mTouchAnchorX, touchResponse.mTouchAnchorY, touchResponse.mAnchorDpDt);
                                    c4 = 0;
                                    c3 = 1;
                                } else {
                                    float min2 = Math.min(motionLayout3.getWidth(), motionLayout3.getHeight());
                                    c3 = 1;
                                    fArr[1] = touchResponse.mTouchDirectionY * min2;
                                    c4 = 0;
                                    fArr[0] = min2 * touchResponse.mTouchDirectionX;
                                }
                                if (Math.abs(((touchResponse.mTouchDirectionY * fArr[c3]) + (touchResponse.mTouchDirectionX * fArr[c4])) * touchResponse.mDragScale) < 0.01d) {
                                    c5 = 0;
                                    fArr[0] = 0.01f;
                                    fArr[c3] = 0.01f;
                                } else {
                                    c5 = 0;
                                }
                                float max2 = Math.max(Math.min(f21 + (touchResponse.mTouchDirectionX != 0.0f ? rawX4 / fArr[c5] : rawY3 / fArr[c3]), 1.0f), 0.0f);
                                if (touchResponse.mOnTouchUp == 6) {
                                    max2 = Math.max(max2, 0.01f);
                                }
                                if (touchResponse.mOnTouchUp == 7) {
                                    max2 = Math.min(max2, 0.99f);
                                }
                                float f22 = motionLayout3.mTransitionLastPosition;
                                if (max2 != f22) {
                                    if (f22 == 0.0f || f22 == 1.0f) {
                                        motionLayout3.endTrigger(f22 == 0.0f);
                                    }
                                    motionLayout3.setProgress(max2);
                                    VelocityTracker velocityTracker14 = myTracker6.tracker;
                                    if (velocityTracker14 != null) {
                                        velocityTracker14.computeCurrentVelocity(1000);
                                    }
                                    VelocityTracker velocityTracker15 = myTracker6.tracker;
                                    float xVelocity4 = velocityTracker15 != null ? velocityTracker15.getXVelocity() : 0.0f;
                                    VelocityTracker velocityTracker16 = myTracker6.tracker;
                                    motionLayout3.mLastVelocity = touchResponse.mTouchDirectionX != 0.0f ? xVelocity4 / fArr[0] : (velocityTracker16 != null ? velocityTracker16.getYVelocity() : 0.0f) / fArr[1];
                                } else {
                                    motionLayout3.mLastVelocity = 0.0f;
                                }
                                touchResponse.mLastTouchX = motionEvent.getRawX();
                                touchResponse.mLastTouchY = motionEvent.getRawY();
                            }
                        }
                        z = false;
                        MotionScene motionScene422 = motionScene;
                        motionScene422.mLastTouchX = motionEvent.getRawX();
                        motionScene422.mLastTouchY = motionEvent.getRawY();
                        if (motionEvent.getAction() == 1) {
                        }
                        motionLayout = this;
                    } else {
                        touchResponse.mLastTouchX = motionEvent.getRawX();
                        touchResponse.mLastTouchY = motionEvent.getRawY();
                        z = false;
                        touchResponse.mDragStarted = false;
                        MotionScene motionScene4222 = motionScene;
                        motionScene4222.mLastTouchX = motionEvent.getRawX();
                        motionScene4222.mLastTouchY = motionEvent.getRawY();
                        if (motionEvent.getAction() == 1) {
                        }
                        motionLayout = this;
                    }
                }
                transition = motionLayout.mScene.mCurrentTransition;
                if ((transition.mTransitionFlags & 4) == 0 ? true : z) {
                }
            }
            motionScene = motionScene3;
            z = false;
            MotionScene motionScene42222 = motionScene;
            motionScene42222.mLastTouchX = motionEvent.getRawX();
            motionScene42222.mLastTouchY = motionEvent.getRawY();
            if (motionEvent.getAction() == 1) {
            }
            motionLayout = this;
            transition = motionLayout.mScene.mCurrentTransition;
            if ((transition.mTransitionFlags & 4) == 0 ? true : z) {
            }
        }
        motionLayout = this;
        z = false;
        transition = motionLayout.mScene.mCurrentTransition;
        if ((transition.mTransitionFlags & 4) == 0 ? true : z) {
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public final void onViewAdded(View view) {
        super.onViewAdded(view);
        if (view instanceof MotionHelper) {
            MotionHelper motionHelper = (MotionHelper) view;
            if (this.mTransitionListeners == null) {
                this.mTransitionListeners = new CopyOnWriteArrayList();
            }
            this.mTransitionListeners.add(motionHelper);
            if (motionHelper.mUseOnShow) {
                if (this.mOnShowHelpers == null) {
                    this.mOnShowHelpers = new ArrayList();
                }
                this.mOnShowHelpers.add(motionHelper);
            }
            if (motionHelper.mUseOnHide) {
                if (this.mOnHideHelpers == null) {
                    this.mOnHideHelpers = new ArrayList();
                }
                this.mOnHideHelpers.add(motionHelper);
            }
            if (motionHelper instanceof MotionEffect) {
                if (this.mDecoratorsHelpers == null) {
                    this.mDecoratorsHelpers = new ArrayList();
                }
                this.mDecoratorsHelpers.add(motionHelper);
            }
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        ArrayList arrayList = this.mOnShowHelpers;
        if (arrayList != null) {
            arrayList.remove(view);
        }
        ArrayList arrayList2 = this.mOnHideHelpers;
        if (arrayList2 != null) {
            arrayList2.remove(view);
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout
    public final void parseLayoutDescription(int i) {
        this.mConstraintLayoutSpec = null;
    }

    public final void processTransitionCompleted() {
        CopyOnWriteArrayList copyOnWriteArrayList = this.mTransitionListeners;
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.isEmpty()) {
            return;
        }
        Iterator it = this.mTransitionCompleted.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            CopyOnWriteArrayList copyOnWriteArrayList2 = this.mTransitionListeners;
            if (copyOnWriteArrayList2 != null) {
                Iterator it2 = copyOnWriteArrayList2.iterator();
                while (it2.hasNext()) {
                    ((TransitionListener) it2.next()).onTransitionCompleted(num.intValue());
                }
            }
        }
        this.mTransitionCompleted.clear();
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
            int i = transition.mLayoutDuringTransition;
            if (i == 0) {
                return;
            }
            if (i == 2) {
                int childCount = getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    ((MotionController) this.mFrameArrayList.get(getChildAt(i2))).mForceMeasure = true;
                }
                return;
            }
        }
        super.requestLayout();
    }

    public final void setProgress(float f) {
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

    public final void setState(TransitionState transitionState) {
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
        int i = AbstractC01265.f25xabc7e4ac[transitionState3.ordinal()];
        if (i != 1 && i != 2) {
            if (i == 3 && transitionState == transitionState2) {
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
            stateCache.startState = i;
            stateCache.endState = i2;
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

    @Override // android.view.View
    public final String toString() {
        Context context = getContext();
        return Debug.getName(this.mBeginState, context) + "->" + Debug.getName(this.mEndState, context) + " (pos:" + this.mTransitionLastPosition + " Dpos/Dt:" + this.mLastVelocity;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0046, code lost:
    
        if (r18 != 7) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0061, code lost:
    
        if ((((r17 * r5) - (((r3 * r5) * r5) / 2.0f)) + r1) > 1.0f) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0072, code lost:
    
        r2 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0073, code lost:
    
        if (r2 == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0075, code lost:
    
        r1 = r15.mDecelerateLogic;
        r2 = r15.mTransitionLastPosition;
        r3 = r15.mScene.getMaxAcceleration();
        r1.initalV = r17;
        r1.currentP = r2;
        r1.maxA = r3;
        r15.mInterpolator = r15.mDecelerateLogic;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x008b, code lost:
    
        r1 = r15.mStopLogic;
        r2 = r15.mTransitionLastPosition;
        r5 = r15.mTransitionDuration;
        r6 = r15.mScene.getMaxAcceleration();
        r3 = r15.mScene.mCurrentTransition;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x009b, code lost:
    
        if (r3 == null) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x009d, code lost:
    
        r3 = r3.mTouchResponse;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x009f, code lost:
    
        if (r3 == null) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00a1, code lost:
    
        r7 = r3.mMaxVelocity;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a6, code lost:
    
        r1.config(r2, r16, r17, r5, r6, r7);
        r15.mLastVelocity = 0.0f;
        r1 = r15.mCurrentState;
        r15.mTransitionGoalPosition = r8;
        r15.mCurrentState = r1;
        r15.mInterpolator = r15.mStopLogic;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00a5, code lost:
    
        r7 = 0.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x006f, code lost:
    
        if ((((((r3 * r5) * r5) / 2.0f) + (r17 * r5)) + r1) < 0.0f) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void touchAnimateTo(float f, float f2, int i) {
        TouchResponse touchResponse;
        TouchResponse touchResponse2;
        TouchResponse touchResponse3;
        TouchResponse touchResponse4;
        TouchResponse touchResponse5;
        TouchResponse touchResponse6;
        TouchResponse touchResponse7;
        float f3 = f;
        if (this.mScene == null || this.mTransitionLastPosition == f3) {
            return;
        }
        boolean z = true;
        this.mTemporalInterpolator = true;
        this.mAnimationStartTime = System.nanoTime();
        MotionScene motionScene = this.mScene;
        MotionScene.Transition transition = motionScene.mCurrentTransition;
        float f4 = (transition != null ? transition.mDuration : motionScene.mDefaultDuration) / 1000.0f;
        this.mTransitionDuration = f4;
        this.mTransitionGoalPosition = f3;
        this.mInTransition = true;
        float f5 = 0.0f;
        if (i != 0 && i != 1 && i != 2) {
            if (i == 4) {
                DecelerateInterpolator decelerateInterpolator = this.mDecelerateLogic;
                float f6 = this.mTransitionLastPosition;
                float maxAcceleration = motionScene.getMaxAcceleration();
                decelerateInterpolator.initalV = f2;
                decelerateInterpolator.currentP = f6;
                decelerateInterpolator.maxA = maxAcceleration;
                this.mInterpolator = this.mDecelerateLogic;
            } else if (i == 5) {
                float f7 = this.mTransitionLastPosition;
                float maxAcceleration2 = motionScene.getMaxAcceleration();
                if (f2 > 0.0f) {
                    float f8 = f2 / maxAcceleration2;
                } else {
                    float f9 = (-f2) / maxAcceleration2;
                }
            } else if (i != 6) {
            }
            this.mTransitionInstantly = false;
            this.mAnimationStartTime = System.nanoTime();
            invalidate();
        }
        if (i == 1 || i == 7) {
            f3 = 0.0f;
        } else if (i == 2 || i == 6) {
            f3 = 1.0f;
        }
        if (((transition == null || (touchResponse7 = transition.mTouchResponse) == null) ? 0 : touchResponse7.mAutoCompleteMode) == 0) {
            StopLogic stopLogic = this.mStopLogic;
            float f10 = this.mTransitionLastPosition;
            float maxAcceleration3 = motionScene.getMaxAcceleration();
            MotionScene.Transition transition2 = this.mScene.mCurrentTransition;
            if (transition2 != null && (touchResponse6 = transition2.mTouchResponse) != null) {
                f5 = touchResponse6.mMaxVelocity;
            }
            stopLogic.config(f10, f3, f2, f4, maxAcceleration3, f5);
        } else {
            StopLogic stopLogic2 = this.mStopLogic;
            float f11 = this.mTransitionLastPosition;
            float f12 = (transition == null || (touchResponse5 = transition.mTouchResponse) == null) ? 0.0f : touchResponse5.mSpringMass;
            float f13 = (transition == null || (touchResponse4 = transition.mTouchResponse) == null) ? 0.0f : touchResponse4.mSpringStiffness;
            float f14 = (transition == null || (touchResponse3 = transition.mTouchResponse) == null) ? 0.0f : touchResponse3.mSpringDamping;
            float f15 = (transition == null || (touchResponse2 = transition.mTouchResponse) == null) ? 0.0f : touchResponse2.mSpringStopThreshold;
            int i2 = (transition == null || (touchResponse = transition.mTouchResponse) == null) ? 0 : touchResponse.mSpringBoundary;
            if (stopLogic2.mSpringStopEngine == null) {
                stopLogic2.mSpringStopEngine = new SpringStopEngine();
            }
            SpringStopEngine springStopEngine = stopLogic2.mSpringStopEngine;
            stopLogic2.mEngine = springStopEngine;
            springStopEngine.mTargetPos = f3;
            springStopEngine.mDamping = f14;
            springStopEngine.mPos = f11;
            springStopEngine.mStiffness = f13;
            springStopEngine.mMass = f12;
            springStopEngine.mStopThreshold = f15;
            springStopEngine.mBoundaryMode = i2;
            springStopEngine.mLastTime = 0.0f;
        }
        int i3 = this.mCurrentState;
        this.mTransitionGoalPosition = f3;
        this.mCurrentState = i3;
        this.mInterpolator = this.mStopLogic;
        this.mTransitionInstantly = false;
        this.mAnimationStartTime = System.nanoTime();
        invalidate();
    }

    public final void transitionToState$1(int i, int i2) {
        StateSet stateSet;
        MotionScene motionScene = this.mScene;
        if (motionScene != null && (stateSet = motionScene.mStateSet) != null) {
            int i3 = this.mCurrentState;
            float f = -1;
            StateSet.State state = (StateSet.State) stateSet.mStateList.get(i);
            if (state == null) {
                i3 = i;
            } else {
                ArrayList arrayList = state.mVariants;
                int i4 = state.mConstraintID;
                if (f != -1.0f && f != -1.0f) {
                    Iterator it = arrayList.iterator();
                    StateSet.Variant variant = null;
                    while (true) {
                        if (it.hasNext()) {
                            StateSet.Variant variant2 = (StateSet.Variant) it.next();
                            if (variant2.match(f, f)) {
                                if (i3 == variant2.mConstraintID) {
                                    break;
                                } else {
                                    variant = variant2;
                                }
                            }
                        } else if (variant != null) {
                            i3 = variant.mConstraintID;
                        }
                    }
                } else if (i4 != i3) {
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        if (i3 == ((StateSet.Variant) it2.next()).mConstraintID) {
                            break;
                        }
                    }
                    i3 = i4;
                }
            }
            if (i3 != -1) {
                i = i3;
            }
        }
        int i5 = this.mCurrentState;
        if (i5 == i) {
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
        if (i5 != -1) {
            setTransition(i5, i);
            animateTo(1.0f);
            this.mTransitionLastPosition = 0.0f;
            animateTo(1.0f);
            this.mOnComplete = null;
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
        this.mTransitionLastTime = System.nanoTime();
        this.mAnimationStartTime = System.nanoTime();
        this.mTransitionInstantly = false;
        this.mInterpolator = null;
        if (i2 == -1) {
            this.mTransitionDuration = (this.mScene.mCurrentTransition != null ? r7.mDuration : r3.mDefaultDuration) / 1000.0f;
        }
        this.mBeginState = -1;
        this.mScene.setTransition(-1, this.mEndState);
        SparseArray sparseArray = new SparseArray();
        if (i2 == 0) {
            this.mTransitionDuration = (this.mScene.mCurrentTransition != null ? r3.mDuration : r14.mDefaultDuration) / 1000.0f;
        } else if (i2 > 0) {
            this.mTransitionDuration = i2 / 1000.0f;
        }
        int childCount = getChildCount();
        this.mFrameArrayList.clear();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            this.mFrameArrayList.put(childAt, new MotionController(childAt));
            sparseArray.put(childAt.getId(), (MotionController) this.mFrameArrayList.get(childAt));
        }
        this.mInTransition = true;
        this.mModel.initFrom(null, this.mScene.getConstraintSet(i));
        rebuildScene();
        this.mModel.build();
        int childCount2 = getChildCount();
        for (int i7 = 0; i7 < childCount2; i7++) {
            View childAt2 = getChildAt(i7);
            MotionController motionController = (MotionController) this.mFrameArrayList.get(childAt2);
            if (motionController != null) {
                MotionPaths motionPaths = motionController.mStartMotionPath;
                motionPaths.time = 0.0f;
                motionPaths.position = 0.0f;
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
                MotionController motionController2 = (MotionController) this.mFrameArrayList.get(getChildAt(i8));
                if (motionController2 != null) {
                    this.mScene.getKeyFrames(motionController2);
                }
            }
            Iterator it3 = this.mDecoratorsHelpers.iterator();
            while (it3.hasNext()) {
                ((MotionHelper) it3.next()).onPreSetup(this, this.mFrameArrayList);
            }
            for (int i9 = 0; i9 < childCount; i9++) {
                MotionController motionController3 = (MotionController) this.mFrameArrayList.get(getChildAt(i9));
                if (motionController3 != null) {
                    motionController3.setup(width, height, System.nanoTime());
                }
            }
        } else {
            for (int i10 = 0; i10 < childCount; i10++) {
                MotionController motionController4 = (MotionController) this.mFrameArrayList.get(getChildAt(i10));
                if (motionController4 != null) {
                    this.mScene.getKeyFrames(motionController4);
                    motionController4.setup(width, height, System.nanoTime());
                }
            }
        }
        MotionScene.Transition transition = this.mScene.mCurrentTransition;
        float f2 = transition != null ? transition.mStagger : 0.0f;
        if (f2 != 0.0f) {
            float f3 = Float.MAX_VALUE;
            float f4 = -3.4028235E38f;
            for (int i11 = 0; i11 < childCount; i11++) {
                MotionPaths motionPaths2 = ((MotionController) this.mFrameArrayList.get(getChildAt(i11))).mEndMotionPath;
                float f5 = motionPaths2.f28y + motionPaths2.f27x;
                f3 = Math.min(f3, f5);
                f4 = Math.max(f4, f5);
            }
            for (int i12 = 0; i12 < childCount; i12++) {
                MotionController motionController5 = (MotionController) this.mFrameArrayList.get(getChildAt(i12));
                MotionPaths motionPaths3 = motionController5.mEndMotionPath;
                float f6 = motionPaths3.f27x;
                float f7 = motionPaths3.f28y;
                motionController5.mStaggerScale = 1.0f / (1.0f - f2);
                motionController5.mStaggerOffset = f2 - ((((f6 + f7) - f3) * f2) / (f4 - f3));
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
            motionScene.mConstraintSetMap.put(i, constraintSet);
        }
        this.mModel.initFrom(this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
        rebuildScene();
        if (this.mCurrentState == i) {
            constraintSet.applyTo(this);
        }
    }

    public final void viewTransition(int i, View... viewArr) {
        String str;
        MotionScene motionScene = this.mScene;
        if (motionScene == null) {
            Log.e("MotionLayout", " no motionScene");
            return;
        }
        ViewTransitionController viewTransitionController = motionScene.mViewTransitionController;
        viewTransitionController.getClass();
        ArrayList arrayList = new ArrayList();
        Iterator it = viewTransitionController.viewTransitions.iterator();
        ViewTransition viewTransition = null;
        while (true) {
            boolean hasNext = it.hasNext();
            str = viewTransitionController.TAG;
            if (!hasNext) {
                break;
            }
            ViewTransition viewTransition2 = (ViewTransition) it.next();
            if (viewTransition2.mId == i) {
                for (View view : viewArr) {
                    if (viewTransition2.checkTags(view)) {
                        arrayList.add(view);
                    }
                }
                if (!arrayList.isEmpty()) {
                    View[] viewArr2 = (View[]) arrayList.toArray(new View[0]);
                    MotionLayout motionLayout = viewTransitionController.mMotionLayout;
                    int i2 = motionLayout.mCurrentState;
                    if (viewTransition2.mViewTransitionMode == 2) {
                        viewTransition2.applyTransition(viewTransitionController, motionLayout, i2, null, viewArr2);
                    } else if (i2 == -1) {
                        Log.w(str, "No support for ViewTransition within transition yet. Currently: " + motionLayout.toString());
                    } else {
                        ConstraintSet constraintSet = motionLayout.getConstraintSet(i2);
                        if (constraintSet != null) {
                            viewTransition2.applyTransition(viewTransitionController, viewTransitionController.mMotionLayout, i2, constraintSet, viewArr2);
                        }
                    }
                    arrayList.clear();
                }
                viewTransition = viewTransition2;
            }
        }
        if (viewTransition == null) {
            Log.e(str, " Could not find ViewTransition");
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

    public final void setState(int i) {
        ConstraintLayoutStates.State state;
        setState(TransitionState.SETUP);
        this.mCurrentState = i;
        this.mBeginState = -1;
        this.mEndState = -1;
        ConstraintLayoutStates constraintLayoutStates = this.mConstraintLayoutSpec;
        if (constraintLayoutStates != null) {
            float f = -1;
            int i2 = constraintLayoutStates.mCurrentStateId;
            SparseArray sparseArray = constraintLayoutStates.mStateList;
            int i3 = 0;
            ConstraintLayout constraintLayout = constraintLayoutStates.mConstraintLayout;
            if (i2 == i) {
                if (i == -1) {
                    state = (ConstraintLayoutStates.State) sparseArray.valueAt(0);
                } else {
                    state = (ConstraintLayoutStates.State) sparseArray.get(i2);
                }
                int i4 = constraintLayoutStates.mCurrentConstraintNumber;
                if (i4 == -1 || !((ConstraintLayoutStates.Variant) state.mVariants.get(i4)).match(f, f)) {
                    while (true) {
                        ArrayList arrayList = state.mVariants;
                        if (i3 >= arrayList.size()) {
                            i3 = -1;
                            break;
                        } else if (((ConstraintLayoutStates.Variant) arrayList.get(i3)).match(f, f)) {
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (constraintLayoutStates.mCurrentConstraintNumber == i3) {
                        return;
                    }
                    ArrayList arrayList2 = state.mVariants;
                    ConstraintSet constraintSet = i3 == -1 ? null : ((ConstraintLayoutStates.Variant) arrayList2.get(i3)).mConstraintSet;
                    if (i3 != -1) {
                        int i5 = ((ConstraintLayoutStates.Variant) arrayList2.get(i3)).mConstraintID;
                    }
                    if (constraintSet == null) {
                        return;
                    }
                    constraintLayoutStates.mCurrentConstraintNumber = i3;
                    constraintSet.applyTo(constraintLayout);
                    return;
                }
                return;
            }
            constraintLayoutStates.mCurrentStateId = i;
            ConstraintLayoutStates.State state2 = (ConstraintLayoutStates.State) sparseArray.get(i);
            while (true) {
                ArrayList arrayList3 = state2.mVariants;
                if (i3 >= arrayList3.size()) {
                    i3 = -1;
                    break;
                } else if (((ConstraintLayoutStates.Variant) arrayList3.get(i3)).match(f, f)) {
                    break;
                } else {
                    i3++;
                }
            }
            ArrayList arrayList4 = state2.mVariants;
            ConstraintSet constraintSet2 = i3 == -1 ? state2.mConstraintSet : ((ConstraintLayoutStates.Variant) arrayList4.get(i3)).mConstraintSet;
            if (i3 != -1) {
                int i6 = ((ConstraintLayoutStates.Variant) arrayList4.get(i3)).mConstraintID;
            }
            if (constraintSet2 == null) {
                return;
            }
            constraintLayoutStates.mCurrentConstraintNumber = i3;
            constraintSet2.applyTo(constraintLayout);
            return;
        }
        MotionScene motionScene = this.mScene;
        if (motionScene != null) {
            motionScene.getConstraintSet(i).applyTo(this);
        }
    }

    public final void setTransition(int i) {
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
                stateCache.startState = this.mBeginState;
                stateCache.endState = this.mEndState;
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
                animateTo(0.0f);
            } else {
                setProgress(f);
            }
        }
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
        this.mFrameArrayList = new HashMap();
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
        this.mPreRotate = new HashMap();
        this.mTempRect = new Rect();
        this.mTransitionState = TransitionState.UNDEFINED;
        this.mModel = new Model();
        this.mNeedsFireTransitionCompleted = false;
        this.mBoundsCheck = new RectF();
        this.mRegionView = null;
        this.mInverseMatrix = null;
        this.mTransitionCompleted = new ArrayList();
        init(attributeSet);
    }

    public final void setTransition(MotionScene.Transition transition) {
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
        this.mTransitionLastTime = (transition.mTransitionFlags & 1) != 0 ? -1L : System.nanoTime();
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
        this.mFrameArrayList = new HashMap();
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
        this.mPreRotate = new HashMap();
        this.mTempRect = new Rect();
        this.mTransitionState = TransitionState.UNDEFINED;
        this.mModel = new Model();
        this.mNeedsFireTransitionCompleted = false;
        this.mBoundsCheck = new RectF();
        this.mRegionView = null;
        this.mInverseMatrix = null;
        this.mTransitionCompleted = new ArrayList();
        init(attributeSet);
    }
}
