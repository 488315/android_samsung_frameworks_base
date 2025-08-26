package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CanvasProperty;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.IntArray;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.RenderNodeAnimator;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.android.internal.R;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public class LockPatternView extends View {
    private static final int ALPHA_MAX_VALUE = 255;
    private static final int ASPECT_LOCK_HEIGHT = 2;
    private static final int ASPECT_LOCK_WIDTH = 1;
    private static final int ASPECT_SQUARE = 0;
    private static final int CELL_ACTIVATE = 0;
    private static final int CELL_DEACTIVATE = 1;
    public static final boolean DEBUG_A11Y = false;
    private static final int DOT_ACTIVATION_DURATION_MILLIS = 50;
    private static final int DOT_RADIUS_DECREASE_DURATION_MILLIS = 192;
    private static final int DOT_RADIUS_INCREASE_DURATION_MILLIS = 96;
    protected static final float DRAG_THRESHHOLD = 0.0f;
    private static final int LINE_END_ANIMATION_DURATION_MILLIS = 50;
    protected static final int MILLIS_PER_CIRCLE_ANIMATING = 700;
    private static final float MIN_DOT_HIT_FACTOR = 0.2f;
    private static final boolean PROFILE_DRAWING = false;
    private static final String TAG = "LockPatternView";
    public static final int VIRTUAL_BASE_VIEW_ID = 1;
    protected long mAnimatingPeriodStart;
    private int mAspect;
    protected final CellState[][] mCellStates;
    protected final Path mCurrentPath;
    private int mDotActivatedColor;
    private int mDotColor;
    private final float mDotHitFactor;
    private float mDotHitMaxRadius;
    private float mDotHitRadius;
    private int mDotSize;
    private int mDotSizeActivated;
    private boolean mDrawingProfilingStarted;
    private boolean mEnlargeVertex;
    private int mErrorColor;
    private final PatternExploreByTouchHelper mExploreByTouchHelper;
    private ExternalHapticsPlayer mExternalHapticsPlayer;
    private int mFadeAnimationAlpha;
    private boolean mFadeClear;
    private LinearGradient mFadeOutGradientShader;
    private boolean mFadePattern;
    private final int mFadePatternAnimationDelayMs;
    private final int mFadePatternAnimationDurationMs;
    private final Interpolator mFastOutSlowInInterpolator;
    protected float mInProgressX;
    protected float mInProgressY;
    protected boolean mInStealthMode;
    private boolean mInputEnabled;
    protected final Rect mInvalidate;
    private boolean mKeepDotActivated;
    private final int mLineFadeOutAnimationDelayMs;
    private final int mLineFadeOutAnimationDurationMs;
    private long[] mLineFadeStart;
    private final Interpolator mLinearOutSlowInInterpolator;
    private Drawable mNotSelectedDrawable;
    private OnPatternListener mOnPatternListener;
    private int mOriginPathColor;
    private int mOriginRegularColor;
    private int mOriginSuccessColor;
    protected final Paint mPaint;
    protected final Paint mPathPaint;
    private int mPathWidth;
    protected final ArrayList<Cell> mPattern;
    protected DisplayMode mPatternDisplayMode;
    protected final boolean[][] mPatternDrawLookup;
    protected boolean mPatternInProgress;
    private final Path mPatternPath;
    private int mRegularColor;
    private Drawable mSelectedDrawable;
    protected float mSquareHeight;
    protected float mSquareWidth;
    private final Interpolator mStandardAccelerateInterpolator;
    private int mSuccessColor;
    protected final Rect mTmpInvalidateRect;
    private boolean mUseLockPatternDrawable;

    public static class CellState {
        float activationAnimationProgress;
        Animator activationAnimator;
        int col;
        Animator deactivationAnimator;
        boolean hwAnimating;
        CanvasProperty<Float> hwCenterX;
        CanvasProperty<Float> hwCenterY;
        CanvasProperty<Paint> hwPaint;
        CanvasProperty<Float> hwRadius;
        float radius;
        int row;
        float translationY;
        float alpha = 0.7f;
        public float lineEndX = Float.MIN_VALUE;
        public float lineEndY = Float.MIN_VALUE;
    }

    public enum DisplayMode {
        Correct,
        Animate,
        Wrong
    }

    public interface ExternalHapticsPlayer {
        void performCellAddedFeedback();
    }

    public interface OnPatternListener {
        void onPatternCellAdded(List<Cell> list);

        void onPatternCleared();

        void onPatternDetected(List<Cell> list);

        void onPatternStart();
    }

    private boolean isVertex(int i, int i2) {
        return (i == 1 || i2 == 1) ? false : true;
    }

    public static final class Cell {
        private static final Cell[][] sCells = createCells();
        final int column;
        final int row;

        private static Cell[][] createCells() {
            Cell[][] cellArr = (Cell[][]) Array.newInstance((Class<?>) Cell.class, 3, 3);
            for (int i = 0; i < 3; i++) {
                for (int i2 = 0; i2 < 3; i2++) {
                    cellArr[i][i2] = new Cell(i, i2);
                }
            }
            return cellArr;
        }

        private Cell(int i, int i2) {
            checkRange(i, i2);
            this.row = i;
            this.column = i2;
        }

        public int getRow() {
            return this.row;
        }

        public int getColumn() {
            return this.column;
        }

        public static Cell of(int i, int i2) {
            checkRange(i, i2);
            return sCells[i][i2];
        }

        private static void checkRange(int i, int i2) {
            if (i < 0 || i > 2) {
                throw new IllegalArgumentException("row must be in range 0-2");
            }
            if (i2 < 0 || i2 > 2) {
                throw new IllegalArgumentException("column must be in range 0-2");
            }
        }

        public String toString() {
            return "(row=" + this.row + ",clmn=" + this.column + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public LockPatternView(Context context) {
        this(context, null);
    }

    public LockPatternView(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        this.mDrawingProfilingStarted = false;
        Paint paint = new Paint();
        this.mPaint = paint;
        Paint paint2 = new Paint();
        this.mPathPaint = paint2;
        this.mPattern = new ArrayList<>(9);
        this.mPatternDrawLookup = (boolean[][]) Array.newInstance((Class<?>) Boolean.TYPE, 3, 3);
        this.mInProgressX = -1.0f;
        this.mInProgressY = -1.0f;
        this.mLineFadeStart = new long[9];
        this.mPatternDisplayMode = DisplayMode.Correct;
        this.mInputEnabled = true;
        this.mInStealthMode = false;
        this.mPatternInProgress = false;
        this.mFadePattern = true;
        this.mFadeClear = false;
        this.mFadeAnimationAlpha = 255;
        this.mPatternPath = new Path();
        this.mCurrentPath = new Path();
        this.mInvalidate = new Rect();
        this.mTmpInvalidateRect = new Rect();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LockPatternView, R.attr.lockPatternStyle, R.style.Widget_LockPatternView);
        String string = typedArrayObtainStyledAttributes.getString(0);
        if ("square".equals(string)) {
            this.mAspect = 0;
        } else if ("lock_width".equals(string)) {
            this.mAspect = 1;
        } else if ("lock_height".equals(string)) {
            this.mAspect = 2;
        } else {
            this.mAspect = 0;
        }
        setClickable(true);
        paint2.setAntiAlias(true);
        paint2.setDither(true);
        this.mRegularColor = typedArrayObtainStyledAttributes.getColor(7, 0);
        this.mErrorColor = typedArrayObtainStyledAttributes.getColor(4, 0);
        this.mSuccessColor = typedArrayObtainStyledAttributes.getColor(8, 0);
        int color = typedArrayObtainStyledAttributes.getColor(2, this.mRegularColor);
        this.mDotColor = color;
        this.mDotActivatedColor = typedArrayObtainStyledAttributes.getColor(1, color);
        this.mKeepDotActivated = typedArrayObtainStyledAttributes.getBoolean(5, false);
        this.mEnlargeVertex = typedArrayObtainStyledAttributes.getBoolean(3, false);
        int color2 = typedArrayObtainStyledAttributes.getColor(6, this.mRegularColor);
        paint2.setColor(color2);
        this.mOriginRegularColor = this.mRegularColor;
        this.mOriginSuccessColor = this.mSuccessColor;
        this.mOriginPathColor = color2;
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.lock_pattern_dot_line_width);
        this.mPathWidth = dimensionPixelSize;
        paint2.setStrokeWidth(dimensionPixelSize);
        this.mLineFadeOutAnimationDurationMs = getResources().getInteger(R.integer.lock_pattern_line_fade_out_duration);
        this.mLineFadeOutAnimationDelayMs = getResources().getInteger(R.integer.lock_pattern_line_fade_out_delay);
        this.mFadePatternAnimationDurationMs = getResources().getInteger(R.integer.lock_pattern_fade_pattern_duration);
        this.mFadePatternAnimationDelayMs = getResources().getInteger(R.integer.lock_pattern_fade_pattern_delay);
        this.mDotSize = getResources().getDimensionPixelSize(R.dimen.lock_pattern_dot_size);
        this.mDotSizeActivated = getResources().getDimensionPixelSize(R.dimen.lock_pattern_dot_size_activated);
        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.lock_pattern_dot_hit_factor, typedValue, true);
        this.mDotHitFactor = Math.max(Math.min(typedValue.getFloat(), 1.0f), 0.2f);
        boolean z = getResources().getBoolean(R.bool.use_lock_pattern_drawable);
        this.mUseLockPatternDrawable = z;
        if (z) {
            this.mSelectedDrawable = getResources().getDrawable(R.drawable.lockscreen_selected);
            this.mNotSelectedDrawable = getResources().getDrawable(R.drawable.lockscreen_notselected);
        }
        paint.setAntiAlias(true);
        paint.setDither(true);
        this.mCellStates = (CellState[][]) Array.newInstance((Class<?>) CellState.class, 3, 3);
        for (int i = 0; i < 3; i++) {
            for (int i2 = 0; i2 < 3; i2++) {
                this.mCellStates[i][i2] = new CellState();
                this.mCellStates[i][i2].radius = this.mDotSize / 2;
                this.mCellStates[i][i2].row = i;
                this.mCellStates[i][i2].col = i2;
            }
        }
        this.mFastOutSlowInInterpolator = AnimationUtils.loadInterpolator(context, 17563661);
        this.mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(context, 17563662);
        this.mStandardAccelerateInterpolator = AnimationUtils.loadInterpolator(context, 17563663);
        PatternExploreByTouchHelper patternExploreByTouchHelper = new PatternExploreByTouchHelper(this);
        this.mExploreByTouchHelper = patternExploreByTouchHelper;
        setAccessibilityDelegate(patternExploreByTouchHelper);
        this.mFadeOutGradientShader = new LinearGradient((-r1) / 2.0f, 0.0f, getResources().getDimensionPixelSize(R.dimen.lock_pattern_fade_away_gradient_width) / 2.0f, 0.0f, 0, color2, Shader.TileMode.CLAMP);
        typedArrayObtainStyledAttributes.recycle();
    }

    public CellState[][] getCellStates() {
        return this.mCellStates;
    }

    public boolean isInStealthMode() {
        return this.mInStealthMode;
    }

    public void setInStealthMode(boolean z) {
        this.mInStealthMode = z;
    }

    public void setFadePattern(boolean z) {
        this.mFadePattern = z;
    }

    public void setOnPatternListener(OnPatternListener onPatternListener) {
        this.mOnPatternListener = onPatternListener;
    }

    public void setExternalHapticsPlayer(ExternalHapticsPlayer externalHapticsPlayer) {
        this.mExternalHapticsPlayer = externalHapticsPlayer;
    }

    public void setPattern(DisplayMode displayMode, List<Cell> list) {
        this.mPattern.clear();
        this.mPattern.addAll(list);
        clearPatternDrawLookup();
        for (Cell cell : list) {
            this.mPatternDrawLookup[cell.getRow()][cell.getColumn()] = true;
        }
        setDisplayMode(displayMode);
    }

    public void setDisplayMode(DisplayMode displayMode) {
        this.mPatternDisplayMode = displayMode;
        if (displayMode == DisplayMode.Animate) {
            if (this.mPattern.size() == 0) {
                throw new IllegalStateException("you must have a pattern to animate if you want to set the display mode to animate");
            }
            this.mAnimatingPeriodStart = SystemClock.elapsedRealtime();
            Cell cell = this.mPattern.get(0);
            this.mInProgressX = getCenterXForColumn(cell.getColumn());
            this.mInProgressY = getCenterYForRow(cell.getRow());
            clearPatternDrawLookup();
        }
        invalidate();
    }

    public void startCellStateAnimation(CellState cellState, float f, float f2, float f3, float f4, float f5, float f6, long j, long j2, Interpolator interpolator, Runnable runnable) {
        if (isHardwareAccelerated()) {
            startCellStateAnimationHw(cellState, f, f2, f3, f4, f5, f6, j, j2, interpolator, runnable);
        } else {
            startCellStateAnimationSw(cellState, f, f2, f3, f4, f5, f6, j, j2, interpolator, runnable);
        }
    }

    private void startCellStateAnimationSw(final CellState cellState, final float f, final float f2, final float f3, final float f4, final float f5, final float f6, long j, long j2, Interpolator interpolator, final Runnable runnable) {
        cellState.alpha = f;
        cellState.translationY = f3;
        cellState.radius = (this.mDotSize / 2) * f5;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(j2);
        valueAnimatorOfFloat.setStartDelay(j);
        valueAnimatorOfFloat.setInterpolator(interpolator);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.LockPatternView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                float f7 = 1.0f - fFloatValue;
                cellState.alpha = (f * f7) + (f2 * fFloatValue);
                cellState.translationY = (f3 * f7) + (f4 * fFloatValue);
                cellState.radius = (LockPatternView.this.mDotSize / 2) * ((f7 * f5) + (fFloatValue * f6));
                LockPatternView.this.invalidate();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.internal.widget.LockPatternView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        valueAnimatorOfFloat.start();
    }

    private void startCellStateAnimationHw(final CellState cellState, float f, float f2, float f3, float f4, float f5, float f6, long j, long j2, Interpolator interpolator, final Runnable runnable) {
        cellState.alpha = f2;
        cellState.translationY = f4;
        cellState.radius = (this.mDotSize / 2) * f6;
        cellState.hwAnimating = true;
        cellState.hwCenterY = CanvasProperty.createFloat(getCenterYForRow(cellState.row) + f3);
        cellState.hwCenterX = CanvasProperty.createFloat(getCenterXForColumn(cellState.col));
        cellState.hwRadius = CanvasProperty.createFloat((this.mDotSize / 2) * f5);
        this.mPaint.setColor(getCurrentColor(false));
        this.mPaint.setAlpha((int) (255.0f * f));
        cellState.hwPaint = CanvasProperty.createPaint(new Paint(this.mPaint));
        startRtFloatAnimation(cellState.hwCenterY, getCenterYForRow(cellState.row) + f4, j, j2, interpolator);
        startRtFloatAnimation(cellState.hwRadius, (this.mDotSize / 2) * f6, j, j2, interpolator);
        startRtAlphaAnimation(cellState, f2, j, j2, interpolator, new AnimatorListenerAdapter(this) { // from class: com.android.internal.widget.LockPatternView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                cellState.hwAnimating = false;
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        invalidate();
    }

    private void startRtAlphaAnimation(CellState cellState, float f, long j, long j2, Interpolator interpolator, Animator.AnimatorListener animatorListener) {
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(cellState.hwPaint, 1, (int) (f * 255.0f));
        renderNodeAnimator.setDuration(j2);
        renderNodeAnimator.setStartDelay(j);
        renderNodeAnimator.setInterpolator(interpolator);
        renderNodeAnimator.setTarget((View) this);
        renderNodeAnimator.addListener(animatorListener);
        renderNodeAnimator.start();
    }

    private void startRtFloatAnimation(CanvasProperty<Float> canvasProperty, float f, long j, long j2, Interpolator interpolator) {
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(canvasProperty, f);
        renderNodeAnimator.setDuration(j2);
        renderNodeAnimator.setStartDelay(j);
        renderNodeAnimator.setInterpolator(interpolator);
        renderNodeAnimator.setTarget((View) this);
        renderNodeAnimator.start();
    }

    private void notifyCellAdded() {
        OnPatternListener onPatternListener = this.mOnPatternListener;
        if (onPatternListener != null) {
            onPatternListener.onPatternCellAdded(this.mPattern);
        }
        this.mExploreByTouchHelper.invalidateRoot();
    }

    protected void notifyPatternStarted() {
        OnPatternListener onPatternListener = this.mOnPatternListener;
        if (onPatternListener != null) {
            onPatternListener.onPatternStart();
        }
    }

    private void notifyPatternDetected() {
        OnPatternListener onPatternListener = this.mOnPatternListener;
        if (onPatternListener != null) {
            onPatternListener.onPatternDetected(this.mPattern);
        }
    }

    private void notifyPatternCleared() {
        OnPatternListener onPatternListener = this.mOnPatternListener;
        if (onPatternListener != null) {
            onPatternListener.onPatternCleared();
        }
    }

    public void clearPattern() {
        resetPattern();
    }

    public void fadeClearPattern() {
        this.mFadeClear = true;
        startFadePatternAnimation();
    }

    @Override // android.view.View
    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.mExploreByTouchHelper.dispatchHoverEvent(motionEvent) | super.dispatchHoverEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetPattern() {
        if (this.mKeepDotActivated && !this.mPattern.isEmpty()) {
            resetPatternCellSize();
        }
        this.mPattern.clear();
        this.mPatternPath.reset();
        clearPatternDrawLookup();
        this.mPatternDisplayMode = DisplayMode.Correct;
        invalidate();
    }

    private void resetPatternCellSize() {
        for (int i = 0; i < this.mCellStates.length; i++) {
            int i2 = 0;
            while (true) {
                CellState[] cellStateArr = this.mCellStates[i];
                if (i2 < cellStateArr.length) {
                    CellState cellState = cellStateArr[i2];
                    if (cellState.activationAnimator != null) {
                        cellState.activationAnimator.cancel();
                    }
                    if (cellState.deactivationAnimator != null) {
                        cellState.deactivationAnimator.cancel();
                    }
                    cellState.activationAnimationProgress = 0.0f;
                    cellState.radius = this.mDotSize / 2.0f;
                    i2++;
                }
            }
        }
    }

    public boolean isEmpty() {
        return this.mPattern.isEmpty();
    }

    protected void clearPatternDrawLookup() {
        for (int i = 0; i < 3; i++) {
            for (int i2 = 0; i2 < 3; i2++) {
                this.mPatternDrawLookup[i][i2] = false;
                this.mLineFadeStart[(i2 * 3) + i] = 0;
                this.mCellStates[i][i2].alpha = 0.7f;
            }
        }
    }

    public void disableInput() {
        this.mInputEnabled = false;
    }

    public void enableInput() {
        this.mInputEnabled = true;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        int i5 = (i - this.mPaddingLeft) - this.mPaddingRight;
        this.mSquareWidth = i5 / 3.0f;
        int i6 = (i2 - this.mPaddingTop) - this.mPaddingBottom;
        this.mSquareHeight = i6 / 3.0f;
        this.mExploreByTouchHelper.invalidateRoot();
        float fMin = Math.min(this.mSquareHeight / 2.0f, this.mSquareWidth / 2.0f);
        this.mDotHitMaxRadius = fMin;
        this.mDotHitRadius = fMin * this.mDotHitFactor;
        if (this.mUseLockPatternDrawable) {
            this.mNotSelectedDrawable.setBounds(this.mPaddingLeft, this.mPaddingTop, i5, i6);
            this.mSelectedDrawable.setBounds(this.mPaddingLeft, this.mPaddingTop, i5, i6);
        }
    }

    private int resolveMeasured(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        if (mode != Integer.MIN_VALUE) {
            return mode != 0 ? size : i2;
        }
        return Math.max(size, i2);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int suggestedMinimumWidth = getSuggestedMinimumWidth();
        int suggestedMinimumHeight = getSuggestedMinimumHeight();
        int iResolveMeasured = resolveMeasured(i, suggestedMinimumWidth);
        int iResolveMeasured2 = resolveMeasured(i2, suggestedMinimumHeight);
        int i3 = this.mAspect;
        if (i3 == 0) {
            iResolveMeasured = Math.min(iResolveMeasured, iResolveMeasured2);
            iResolveMeasured2 = iResolveMeasured;
        } else if (i3 == 1) {
            iResolveMeasured2 = Math.min(iResolveMeasured, iResolveMeasured2);
        } else if (i3 == 2) {
            iResolveMeasured = Math.min(iResolveMeasured, iResolveMeasured2);
        }
        setMeasuredDimension(iResolveMeasured, iResolveMeasured2);
    }

    protected Cell detectAndAddHit(float f, float f2) {
        Cell cell;
        Cell cellCheckForNewHit = checkForNewHit(f, f2);
        Cell cellOf = null;
        if (cellCheckForNewHit == null) {
            return null;
        }
        ArrayList<Cell> arrayList = this.mPattern;
        if (arrayList.isEmpty()) {
            cell = null;
        } else {
            Cell cell2 = arrayList.get(arrayList.size() - 1);
            int i = cellCheckForNewHit.row - cell2.row;
            int i2 = cellCheckForNewHit.column - cell2.column;
            int i3 = cell2.row;
            int i4 = cell2.column;
            if (Math.abs(i) == 2 && Math.abs(i2) != 1) {
                i3 = cell2.row + (i > 0 ? 1 : -1);
            }
            if (Math.abs(i2) == 2 && Math.abs(i) != 1) {
                i4 = cell2.column + (i2 > 0 ? 1 : -1);
            }
            cell = cell2;
            cellOf = Cell.of(i3, i4);
        }
        if (cellOf != null && !this.mPatternDrawLookup[cellOf.row][cellOf.column]) {
            addCellToPattern(cellOf);
            if (this.mKeepDotActivated) {
                if (this.mFadePattern) {
                    startCellDeactivatedAnimation(cellOf, true);
                } else {
                    startCellActivatedAnimation(cellOf);
                }
            }
        }
        if (this.mKeepDotActivated && cell != null) {
            startCellDeactivatedAnimation(cell, false);
        }
        addCellToPattern(cellCheckForNewHit);
        performHapticFeedback(0, 1);
        return cellCheckForNewHit;
    }

    @Override // android.view.View
    public boolean performHapticFeedback(int i, int i2) {
        ExternalHapticsPlayer externalHapticsPlayer = this.mExternalHapticsPlayer;
        if (externalHapticsPlayer != null) {
            externalHapticsPlayer.performCellAddedFeedback();
            return true;
        }
        return super.performHapticFeedback(i, i2);
    }

    protected void addCellToPattern(Cell cell) {
        this.mPatternDrawLookup[cell.getRow()][cell.getColumn()] = true;
        this.mPattern.add(cell);
        if (!this.mInStealthMode) {
            startCellActivatedAnimation(cell);
        }
        notifyCellAdded();
    }

    private void startFadePatternAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(createFadePatternAnimation());
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.internal.widget.LockPatternView.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                LockPatternView.this.mFadeAnimationAlpha = 255;
                LockPatternView.this.mFadeClear = false;
                LockPatternView.this.resetPattern();
            }
        });
        animatorSet.start();
    }

    private Animator createFadePatternAnimation() {
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(255, 0);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$createFadePatternAnimation$0(valueAnimator);
            }
        });
        valueAnimatorOfInt.setInterpolator(this.mStandardAccelerateInterpolator);
        valueAnimatorOfInt.setStartDelay(this.mFadePatternAnimationDelayMs);
        valueAnimatorOfInt.setDuration(this.mFadePatternAnimationDurationMs);
        return valueAnimatorOfInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createFadePatternAnimation$0(ValueAnimator valueAnimator) {
        this.mFadeAnimationAlpha = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        invalidate();
    }

    private void startCellActivatedAnimation(Cell cell) {
        startCellActivationAnimation(cell, 0, false);
    }

    private void startCellDeactivatedAnimation(Cell cell, boolean z) {
        startCellActivationAnimation(cell, 1, z);
    }

    private void startCellActivationAnimation(Cell cell, int i, boolean z) {
        final CellState cellState = this.mCellStates[cell.row][cell.column];
        if (cellState.activationAnimator != null && !this.mKeepDotActivated) {
            cellState.activationAnimator.cancel();
        }
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSet.Builder builderWith = animatorSet.play(createLineDisappearingAnimation()).with(createLineEndAnimation(cellState, i == 0 ? this.mInProgressX : cellState.lineEndX, i == 0 ? this.mInProgressY : cellState.lineEndY, getCenterXForColumn(cell.column), getCenterYForRow(cell.row)));
        if (this.mDotSize != this.mDotSizeActivated) {
            builderWith.with(createDotRadiusAnimation(cellState, i, z));
        }
        if (this.mDotColor != this.mDotActivatedColor) {
            builderWith.with(createDotActivationColorAnimation(cellState, i, z));
        }
        if (i == 0) {
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.internal.widget.LockPatternView.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    cellState.activationAnimator = null;
                    LockPatternView.this.invalidate();
                }
            });
            cellState.activationAnimator = animatorSet;
        } else {
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.internal.widget.LockPatternView.6
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    cellState.deactivationAnimator = null;
                    LockPatternView.this.invalidate();
                }
            });
            cellState.deactivationAnimator = animatorSet;
        }
        animatorSet.start();
    }

    private Animator createDotActivationColorAnimation(final CellState cellState, int i, boolean z) {
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$createDotActivationColorAnimation$1(cellState, valueAnimator);
            }
        };
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat.addUpdateListener(animatorUpdateListener);
        valueAnimatorOfFloat2.addUpdateListener(animatorUpdateListener);
        valueAnimatorOfFloat.setInterpolator(this.mFastOutSlowInInterpolator);
        valueAnimatorOfFloat2.setInterpolator(this.mLinearOutSlowInInterpolator);
        valueAnimatorOfFloat.setDuration(50L);
        valueAnimatorOfFloat2.setDuration(50L);
        AnimatorSet animatorSet = new AnimatorSet();
        if (this.mKeepDotActivated && !z) {
            if (i != 0) {
                valueAnimatorOfFloat = valueAnimatorOfFloat2;
            }
            animatorSet.play(valueAnimatorOfFloat);
            return animatorSet;
        }
        animatorSet.play(valueAnimatorOfFloat2).after((this.mLineFadeOutAnimationDelayMs + this.mLineFadeOutAnimationDurationMs) - 100).after(valueAnimatorOfFloat);
        return animatorSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createDotActivationColorAnimation$1(CellState cellState, ValueAnimator valueAnimator) {
        cellState.activationAnimationProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    private Animator createLineEndAnimation(final CellState cellState, final float f, final float f2, final float f3, final float f4) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$createLineEndAnimation$2(cellState, f, f3, f2, f4, valueAnimator);
            }
        });
        valueAnimatorOfFloat.setInterpolator(this.mFastOutSlowInInterpolator);
        valueAnimatorOfFloat.setDuration(50L);
        return valueAnimatorOfFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createLineEndAnimation$2(CellState cellState, float f, float f2, float f3, float f4, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        float f5 = 1.0f - fFloatValue;
        cellState.lineEndX = (f * f5) + (f2 * fFloatValue);
        cellState.lineEndY = (f5 * f3) + (fFloatValue * f4);
        invalidate();
    }

    private Animator createLineDisappearingAnimation() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$createLineDisappearingAnimation$3(valueAnimator);
            }
        });
        valueAnimatorOfFloat.setStartDelay(this.mLineFadeOutAnimationDelayMs);
        valueAnimatorOfFloat.setDuration(this.mLineFadeOutAnimationDurationMs);
        return valueAnimatorOfFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createLineDisappearingAnimation$3(ValueAnimator valueAnimator) {
        invalidate();
    }

    private Animator createDotRadiusAnimation(final CellState cellState, int i, boolean z) {
        float f = this.mDotSize / 2.0f;
        float f2 = this.mDotSizeActivated / 2.0f;
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$createDotRadiusAnimation$4(cellState, valueAnimator);
            }
        };
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
        valueAnimatorOfFloat.addUpdateListener(animatorUpdateListener);
        valueAnimatorOfFloat.setInterpolator(this.mLinearOutSlowInInterpolator);
        valueAnimatorOfFloat.setDuration(96L);
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(f2, f);
        valueAnimatorOfFloat2.addUpdateListener(animatorUpdateListener);
        valueAnimatorOfFloat2.setInterpolator(this.mFastOutSlowInInterpolator);
        valueAnimatorOfFloat2.setDuration(192L);
        AnimatorSet animatorSet = new AnimatorSet();
        if (this.mKeepDotActivated) {
            if (!this.mFadePattern) {
                if (i == 0) {
                    animatorSet.play(valueAnimatorOfFloat);
                }
                return animatorSet;
            }
            if (z) {
                animatorSet.playSequentially(valueAnimatorOfFloat, valueAnimatorOfFloat2);
                return animatorSet;
            }
            if (i != 0) {
                valueAnimatorOfFloat = valueAnimatorOfFloat2;
            }
            animatorSet.play(valueAnimatorOfFloat);
            return animatorSet;
        }
        animatorSet.playSequentially(valueAnimatorOfFloat, valueAnimatorOfFloat2);
        return animatorSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createDotRadiusAnimation$4(CellState cellState, ValueAnimator valueAnimator) {
        cellState.radius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        cellState.alpha = 1.0f;
        invalidate();
    }

    private Cell checkForNewHit(float f, float f2) {
        Cell cellDetectCellHit = detectCellHit(f, f2);
        if (cellDetectCellHit == null || this.mPatternDrawLookup[cellDetectCellHit.row][cellDetectCellHit.column]) {
            return null;
        }
        return cellDetectCellHit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Cell detectCellHit(float f, float f2) {
        float f3;
        for (int i = 0; i < 3; i++) {
            for (int i2 = 0; i2 < 3; i2++) {
                float centerYForRow = getCenterYForRow(i);
                float centerXForColumn = getCenterXForColumn(i2);
                if (this.mEnlargeVertex && isVertex(i, i2)) {
                    f3 = this.mDotHitMaxRadius;
                } else {
                    f3 = this.mDotHitRadius;
                }
                float f4 = f - centerXForColumn;
                float f5 = f2 - centerYForRow;
                if ((f4 * f4) + (f5 * f5) < f3 * f3) {
                    return Cell.of(i, i2);
                }
            }
        }
        return null;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (AccessibilityManager.getInstance(this.mContext).isTouchExplorationEnabled()) {
            int action = motionEvent.getAction();
            if (action == 7) {
                motionEvent.setAction(2);
            } else if (action == 9) {
                motionEvent.setAction(0);
            } else if (action == 10) {
                motionEvent.setAction(1);
            }
            onTouchEvent(motionEvent);
            motionEvent.setAction(action);
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mInputEnabled || !isEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            handleActionDown(motionEvent);
            return true;
        }
        if (action == 1) {
            handleActionUp();
            return true;
        }
        if (action == 2) {
            handleActionMove(motionEvent);
            return true;
        }
        if (action != 3) {
            return false;
        }
        if (this.mPatternInProgress) {
            setPatternInProgress(false);
            resetPattern();
            notifyPatternCleared();
        }
        return true;
    }

    private void setPatternInProgress(boolean z) {
        this.mPatternInProgress = z;
        this.mExploreByTouchHelper.invalidateRoot();
    }

    protected void handleActionMove(MotionEvent motionEvent) {
        float f = this.mPathWidth;
        int historySize = motionEvent.getHistorySize();
        this.mTmpInvalidateRect.setEmpty();
        int i = 0;
        boolean z = false;
        while (i < historySize + 1) {
            float historicalX = i < historySize ? motionEvent.getHistoricalX(i) : motionEvent.getX();
            float historicalY = i < historySize ? motionEvent.getHistoricalY(i) : motionEvent.getY();
            Cell cellDetectAndAddHit = detectAndAddHit(historicalX, historicalY);
            int size = this.mPattern.size();
            if (cellDetectAndAddHit != null && size == 1) {
                setPatternInProgress(true);
                notifyPatternStarted();
            }
            float fAbs = Math.abs(historicalX - this.mInProgressX);
            float fAbs2 = Math.abs(historicalY - this.mInProgressY);
            if (fAbs > 0.0f || fAbs2 > 0.0f) {
                z = true;
            }
            if (this.mPatternInProgress && size > 0) {
                Cell cell = this.mPattern.get(size - 1);
                float centerXForColumn = getCenterXForColumn(cell.column);
                float centerYForRow = getCenterYForRow(cell.row);
                float fMin = Math.min(centerXForColumn, historicalX) - f;
                float fMax = Math.max(centerXForColumn, historicalX) + f;
                float fMin2 = Math.min(centerYForRow, historicalY) - f;
                float fMax2 = Math.max(centerYForRow, historicalY) + f;
                if (cellDetectAndAddHit != null) {
                    float f2 = this.mSquareWidth * 0.5f;
                    float f3 = this.mSquareHeight * 0.5f;
                    float centerXForColumn2 = getCenterXForColumn(cellDetectAndAddHit.column);
                    float centerYForRow2 = getCenterYForRow(cellDetectAndAddHit.row);
                    fMin = Math.min(centerXForColumn2 - f2, fMin);
                    fMax = Math.max(centerXForColumn2 + f2, fMax);
                    fMin2 = Math.min(centerYForRow2 - f3, fMin2);
                    fMax2 = Math.max(centerYForRow2 + f3, fMax2);
                }
                this.mTmpInvalidateRect.union(Math.round(fMin), Math.round(fMin2), Math.round(fMax), Math.round(fMax2));
            }
            i++;
        }
        this.mInProgressX = motionEvent.getX();
        this.mInProgressY = motionEvent.getY();
        if (z) {
            this.mInvalidate.union(this.mTmpInvalidateRect);
            invalidate(this.mInvalidate);
            this.mInvalidate.set(this.mTmpInvalidateRect);
        }
    }

    private void handleActionUp() {
        if (this.mPattern.isEmpty()) {
            return;
        }
        setPatternInProgress(false);
        if (this.mKeepDotActivated) {
            deactivateLastCell();
        } else {
            cancelLineAnimations();
        }
        notifyPatternDetected();
        if (this.mFadePattern) {
            clearPatternDrawLookup();
            this.mPatternDisplayMode = DisplayMode.Correct;
        }
        invalidate();
    }

    private void deactivateLastCell() {
        startCellDeactivatedAnimation(this.mPattern.get(r0.size() - 1), false);
    }

    private void cancelLineAnimations() {
        for (int i = 0; i < 3; i++) {
            for (int i2 = 0; i2 < 3; i2++) {
                CellState cellState = this.mCellStates[i][i2];
                if (cellState.activationAnimator != null) {
                    cellState.activationAnimator.cancel();
                    cellState.activationAnimator = null;
                    cellState.radius = this.mDotSize / 2.0f;
                    cellState.lineEndX = Float.MIN_VALUE;
                    cellState.lineEndY = Float.MIN_VALUE;
                    cellState.activationAnimationProgress = 0.0f;
                }
            }
        }
    }

    private void handleActionDown(MotionEvent motionEvent) {
        resetPattern();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        Cell cellDetectAndAddHit = detectAndAddHit(x, y);
        if (cellDetectAndAddHit != null) {
            setPatternInProgress(true);
            this.mPatternDisplayMode = DisplayMode.Correct;
            notifyPatternStarted();
        } else if (this.mPatternInProgress) {
            setPatternInProgress(false);
            notifyPatternCleared();
        }
        if (cellDetectAndAddHit != null) {
            float centerXForColumn = getCenterXForColumn(cellDetectAndAddHit.column);
            float centerYForRow = getCenterYForRow(cellDetectAndAddHit.row);
            float f = this.mSquareWidth / 2.0f;
            float f2 = this.mSquareHeight / 2.0f;
            invalidate((int) (centerXForColumn - f), (int) (centerYForRow - f2), (int) (centerXForColumn + f), (int) (centerYForRow + f2));
        }
        this.mInProgressX = x;
        this.mInProgressY = y;
    }

    public void setColors(int i, int i2, int i3) throws Resources.NotFoundException {
        this.mDotActivatedColor = i;
        this.mDotColor = i;
        this.mRegularColor = i;
        this.mErrorColor = i3;
        this.mSuccessColor = i2;
        this.mPathPaint.setColor(i);
        updateGradientPathColor(i);
        invalidate();
    }

    public void setDotColors(int i, int i2) {
        this.mDotColor = i;
        this.mDotActivatedColor = i2;
        invalidate();
    }

    public void setKeepDotActivated(boolean z) {
        this.mKeepDotActivated = z;
    }

    public void setDotSizes(int i, int i2) {
        this.mDotSize = i;
        this.mDotSizeActivated = i2;
    }

    public void setPathWidth(int i) {
        this.mPathWidth = i;
        this.mPathPaint.setStrokeWidth(i);
    }

    protected float getCenterXForColumn(int i) {
        float f = this.mPaddingLeft;
        float f2 = this.mSquareWidth;
        return f + (i * f2) + (f2 / 2.0f);
    }

    protected float getCenterYForRow(int i) {
        float f = this.mPaddingTop;
        float f2 = this.mSquareHeight;
        return f + (i * f2) + (f2 / 2.0f);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Canvas canvas2;
        int i;
        int i2;
        int i3;
        float f;
        ArrayList<Cell> arrayList;
        float f2;
        float f3;
        float f4;
        LockPatternView lockPatternView = this;
        ArrayList<Cell> arrayList2 = lockPatternView.mPattern;
        int size = arrayList2.size();
        boolean[][] zArr = lockPatternView.mPatternDrawLookup;
        if (lockPatternView.mPatternDisplayMode == DisplayMode.Animate) {
            int iElapsedRealtime = (((int) (SystemClock.elapsedRealtime() - lockPatternView.mAnimatingPeriodStart)) % ((size + 1) * 700)) / 700;
            lockPatternView.clearPatternDrawLookup();
            for (int i4 = 0; i4 < iElapsedRealtime; i4++) {
                Cell cell = arrayList2.get(i4);
                zArr[cell.getRow()][cell.getColumn()] = true;
            }
            if (iElapsedRealtime > 0 && iElapsedRealtime < size) {
                float f5 = (r2 % 700) / 700.0f;
                Cell cell2 = arrayList2.get(iElapsedRealtime - 1);
                float centerXForColumn = lockPatternView.getCenterXForColumn(cell2.column);
                float centerYForRow = lockPatternView.getCenterYForRow(cell2.row);
                Cell cell3 = arrayList2.get(iElapsedRealtime);
                float centerXForColumn2 = (lockPatternView.getCenterXForColumn(cell3.column) - centerXForColumn) * f5;
                float centerYForRow2 = f5 * (lockPatternView.getCenterYForRow(cell3.row) - centerYForRow);
                lockPatternView.mInProgressX = centerXForColumn + centerXForColumn2;
                lockPatternView.mInProgressY = centerYForRow + centerYForRow2;
            }
            lockPatternView.invalidate();
        }
        Path path = lockPatternView.mCurrentPath;
        path.rewind();
        if (lockPatternView.mInStealthMode || lockPatternView.mFadeClear) {
            canvas2 = canvas;
        } else {
            lockPatternView.mPathPaint.setColor(lockPatternView.getCurrentColor(true));
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            float f6 = 0.0f;
            float f7 = 0.0f;
            int i5 = 0;
            boolean z = false;
            while (i5 < size) {
                Cell cell4 = arrayList2.get(i5);
                if (!zArr[cell4.row][cell4.column]) {
                    break;
                }
                long[] jArr = lockPatternView.mLineFadeStart;
                if (jArr[i5] == 0) {
                    jArr[i5] = SystemClock.elapsedRealtime();
                }
                float centerXForColumn3 = lockPatternView.getCenterXForColumn(cell4.column);
                float centerYForRow3 = lockPatternView.getCenterYForRow(cell4.row);
                if (i5 != 0) {
                    CellState cellState = lockPatternView.mCellStates[cell4.row][cell4.column];
                    path.rewind();
                    if (cellState.lineEndX == Float.MIN_VALUE || cellState.lineEndY == Float.MIN_VALUE) {
                        f3 = centerXForColumn3;
                        f4 = centerYForRow3;
                    } else {
                        f3 = cellState.lineEndX;
                        f4 = cellState.lineEndY;
                    }
                    i3 = i5;
                    f = centerXForColumn3;
                    ArrayList<Cell> arrayList3 = arrayList2;
                    f2 = centerYForRow3;
                    arrayList = arrayList3;
                    lockPatternView.drawLineSegment(canvas, f6, f7, f3, f4, lockPatternView.mLineFadeStart[i5], jElapsedRealtime);
                    Path path2 = new Path();
                    path2.moveTo(f6, f7);
                    path2.lineTo(f, f2);
                    lockPatternView.mPatternPath.addPath(path2);
                } else {
                    i3 = i5;
                    f = centerXForColumn3;
                    arrayList = arrayList2;
                    f2 = centerYForRow3;
                }
                i5 = i3 + 1;
                f7 = f2;
                f6 = f;
                arrayList2 = arrayList;
                z = true;
            }
            canvas2 = canvas;
            if ((lockPatternView.mPatternInProgress || lockPatternView.mPatternDisplayMode == DisplayMode.Animate) && z) {
                path.rewind();
                path.moveTo(f6, f7);
                path.lineTo(lockPatternView.mInProgressX, lockPatternView.mInProgressY);
                lockPatternView.mPathPaint.setAlpha((int) (lockPatternView.calculateLastSegmentAlpha(lockPatternView.mInProgressX, lockPatternView.mInProgressY, f6, f7) * 255.0f));
                canvas2.drawPath(path, lockPatternView.mPathPaint);
            }
        }
        if (lockPatternView.mFadeClear) {
            lockPatternView.mPathPaint.setAlpha(lockPatternView.mFadeAnimationAlpha);
            canvas2.drawPath(lockPatternView.mPatternPath, lockPatternView.mPathPaint);
        }
        int i6 = 0;
        while (i6 < 3) {
            float centerYForRow4 = lockPatternView.getCenterYForRow(i6);
            int i7 = 0;
            while (i7 < 3) {
                CellState cellState2 = lockPatternView.mCellStates[i6][i7];
                float centerXForColumn4 = lockPatternView.getCenterXForColumn(i7);
                float f8 = cellState2.translationY;
                if (lockPatternView.mUseLockPatternDrawable) {
                    lockPatternView.drawCellDrawable(canvas2, i6, i7, cellState2.radius, zArr[i6][i7]);
                    i = i6;
                    i2 = i7;
                } else {
                    i = i6;
                    i2 = i7;
                    if (isHardwareAccelerated() && cellState2.hwAnimating) {
                        ((RecordingCanvas) canvas).drawCircle(cellState2.hwCenterX, cellState2.hwCenterY, cellState2.hwRadius, cellState2.hwPaint);
                    } else {
                        drawCircle(canvas, (int) centerXForColumn4, ((int) centerYForRow4) + f8, cellState2.radius, zArr[i][i2], cellState2.alpha, cellState2.activationAnimationProgress);
                    }
                }
                i7 = i2 + 1;
                lockPatternView = this;
                canvas2 = canvas;
                i6 = i;
            }
            i6++;
            lockPatternView = this;
            canvas2 = canvas;
        }
    }

    private void drawLineSegment(Canvas canvas, float f, float f2, float f3, float f4, long j, long j2) {
        if (this.mFadePattern) {
            long j3 = j2 - j;
            int i = this.mLineFadeOutAnimationDelayMs;
            int i2 = this.mLineFadeOutAnimationDurationMs;
            if (j3 >= i + i2) {
                return;
            }
            drawFadingAwayLineSegment(canvas, f, f2, f3, f4, Math.max((j3 - i) / i2, 0.0f));
            return;
        }
        this.mPathPaint.setAlpha(255);
        canvas.drawLine(f, f2, f3, f4, this.mPathPaint);
    }

    private void drawFadingAwayLineSegment(Canvas canvas, float f, float f2, float f3, float f4, float f5) {
        float f6 = 1.0f - f5;
        this.mPathPaint.setAlpha((int) (255.0f * f6));
        this.mPathPaint.setShader(this.mFadeOutGradientShader);
        canvas.save();
        canvas.translate((f3 * f5) + (f * f6), (f4 * f5) + (f2 * f6));
        float f7 = f4 - f2;
        float f8 = f3 - f;
        float degrees = (float) Math.toDegrees(Math.atan(f7 / f8));
        if (f8 < 0.0f) {
            degrees += 180.0f;
        }
        canvas.rotate(degrees);
        float fHypot = (float) Math.hypot(f8, f7);
        canvas.drawLine((-fHypot) * f5, 0.0f, fHypot * f6, 0.0f, this.mPathPaint);
        canvas.restore();
        this.mPathPaint.setShader(null);
    }

    private float calculateLastSegmentAlpha(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        return Math.min(1.0f, Math.max(0.0f, ((((float) Math.sqrt((f5 * f5) + (f6 * f6))) / this.mSquareWidth) - 0.3f) * 4.0f));
    }

    private int getDotColor() {
        if (this.mInStealthMode) {
            return this.mDotColor;
        }
        if (this.mPatternDisplayMode == DisplayMode.Wrong) {
            return this.mErrorColor;
        }
        return this.mDotColor;
    }

    private int getCurrentColor(boolean z) {
        if (!z || this.mInStealthMode || this.mPatternInProgress) {
            return this.mRegularColor;
        }
        if (this.mPatternDisplayMode == DisplayMode.Wrong) {
            return this.mErrorColor;
        }
        if (this.mPatternDisplayMode == DisplayMode.Correct || this.mPatternDisplayMode == DisplayMode.Animate) {
            return this.mSuccessColor;
        }
        throw new IllegalStateException("unknown display mode " + this.mPatternDisplayMode);
    }

    private void drawCircle(Canvas canvas, float f, float f2, float f3, boolean z, float f4, float f5) {
        this.mPaint.setColor(getCurrentColor(z));
        this.mPaint.setAlpha((int) (f4 * 255.0f));
        canvas.drawCircle(f, f2, f3, this.mPaint);
    }

    private void drawCellDrawable(Canvas canvas, int i, int i2, float f, boolean z) {
        Rect rect = new Rect((int) (this.mPaddingLeft + (i2 * this.mSquareWidth)), (int) (this.mPaddingTop + (i * this.mSquareHeight)), (int) (this.mPaddingLeft + ((i2 + 1) * this.mSquareWidth)), (int) (this.mPaddingTop + ((i + 1) * this.mSquareHeight)));
        float f2 = f / (this.mDotSize / 2);
        canvas.save();
        canvas.clipRect(rect);
        canvas.scale(f2, f2, rect.centerX(), rect.centerY());
        if (!z || f2 > 1.0f) {
            this.mNotSelectedDrawable.draw(canvas);
        } else {
            this.mSelectedDrawable.draw(canvas);
        }
        canvas.restore();
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState();
        byte[] bArrPatternToByteArray = LockPatternUtils.patternToByteArray(this.mPattern);
        String str = bArrPatternToByteArray != null ? new String(bArrPatternToByteArray) : null;
        if (bArrPatternToByteArray != null) {
            Arrays.fill(bArrPatternToByteArray, (byte) 0);
        }
        return new SavedState(parcelableOnSaveInstanceState, str, this.mPatternDisplayMode.ordinal(), this.mInputEnabled, this.mInStealthMode);
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setPattern(DisplayMode.Correct, LockPatternUtils.byteArrayToPattern(savedState.getSerializedPattern().getBytes()));
        this.mPatternDisplayMode = DisplayMode.values()[savedState.getDisplayMode()];
        this.mInputEnabled = savedState.isInputEnabled();
        this.mInStealthMode = savedState.isInStealthMode();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    private static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.android.internal.widget.LockPatternView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        private final int mDisplayMode;
        private final boolean mInStealthMode;
        private final boolean mInputEnabled;
        private final String mSerializedPattern;

        private SavedState(Parcelable parcelable, String str, int i, boolean z, boolean z2) {
            super(parcelable);
            this.mSerializedPattern = str;
            this.mDisplayMode = i;
            this.mInputEnabled = z;
            this.mInStealthMode = z2;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mSerializedPattern = parcel.readString();
            this.mDisplayMode = parcel.readInt();
            this.mInputEnabled = ((Boolean) parcel.readValue(null)).booleanValue();
            this.mInStealthMode = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        public String getSerializedPattern() {
            return this.mSerializedPattern;
        }

        public int getDisplayMode() {
            return this.mDisplayMode;
        }

        public boolean isInputEnabled() {
            return this.mInputEnabled;
        }

        public boolean isInStealthMode() {
            return this.mInStealthMode;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) throws IOException {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.mSerializedPattern);
            parcel.writeInt(this.mDisplayMode);
            parcel.writeValue(Boolean.valueOf(this.mInputEnabled));
            parcel.writeValue(Boolean.valueOf(this.mInStealthMode));
        }
    }

    protected void updateViewStyle(boolean z) throws Resources.NotFoundException {
        int i = this.mOriginPathColor;
        if (z) {
            i = -14342875;
            this.mSuccessColor = -14342875;
            this.mDotColor = -14342875;
            this.mRegularColor = -14342875;
        } else {
            int i2 = this.mOriginRegularColor;
            this.mDotColor = i2;
            this.mRegularColor = i2;
            this.mSuccessColor = this.mOriginSuccessColor;
        }
        this.mPathPaint.setColor(i);
        this.mPathPaint.setStyle(Paint.Style.STROKE);
        this.mPathPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        updateGradientPathColor(i);
        invalidate();
    }

    private void updateGradientPathColor(int i) throws Resources.NotFoundException {
        this.mFadeOutGradientShader = new LinearGradient((-r0) / 2.0f, 0.0f, getResources().getDimensionPixelSize(R.dimen.lock_pattern_fade_away_gradient_width) / 2.0f, 0.0f, 0, i, Shader.TileMode.CLAMP);
    }

    private final class PatternExploreByTouchHelper extends ExploreByTouchHelper {
        private final SparseArray<VirtualViewContainer> mItems;
        private Rect mTempRect;

        class VirtualViewContainer {
            CharSequence description;

            public VirtualViewContainer(PatternExploreByTouchHelper patternExploreByTouchHelper, CharSequence charSequence) {
                this.description = charSequence;
            }
        }

        public PatternExploreByTouchHelper(View view) {
            super(view);
            this.mTempRect = new Rect();
            this.mItems = new SparseArray<>();
            for (int i = 1; i < 10; i++) {
                this.mItems.put(i, new VirtualViewContainer(this, getTextForVirtualView(i)));
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float f, float f2) {
            return getVirtualViewIdForHit(f, f2);
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(IntArray intArray) {
            if (LockPatternView.this.mPatternInProgress) {
                for (int i = 1; i < 10; i++) {
                    intArray.add(i);
                }
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            VirtualViewContainer virtualViewContainer = this.mItems.get(i);
            if (virtualViewContainer != null) {
                accessibilityEvent.getText().add(virtualViewContainer.description);
            }
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            if (LockPatternView.this.mPatternInProgress) {
                return;
            }
            accessibilityEvent.setContentDescription(LockPatternView.this.getContext().getText(R.string.lockscreen_access_pattern_area));
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfo accessibilityNodeInfo) {
            accessibilityNodeInfo.setText(getTextForVirtualView(i));
            accessibilityNodeInfo.setContentDescription(getTextForVirtualView(i));
            if (LockPatternView.this.mPatternInProgress) {
                accessibilityNodeInfo.setFocusable(true);
                if (isClickable(i)) {
                    accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                    accessibilityNodeInfo.setClickable(isClickable(i));
                }
            }
            accessibilityNodeInfo.setBoundsInParent(getBoundsForVirtualView(i));
        }

        private boolean isClickable(int i) {
            if (i == Integer.MIN_VALUE) {
                return false;
            }
            int i2 = i - 1;
            int i3 = i2 / 3;
            int i4 = i2 % 3;
            if (i3 < 3) {
                return !LockPatternView.this.mPatternDrawLookup[i3][i4];
            }
            return false;
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 != 16) {
                return false;
            }
            return onItemClicked(i);
        }

        boolean onItemClicked(int i) {
            invalidateVirtualView(i);
            sendEventForVirtualView(i, 1);
            return true;
        }

        private Rect getBoundsForVirtualView(int i) {
            int i2 = i - 1;
            Rect rect = this.mTempRect;
            int i3 = i2 / 3;
            float centerXForColumn = LockPatternView.this.getCenterXForColumn(i2 % 3);
            float centerYForRow = LockPatternView.this.getCenterYForRow(i3);
            float f = LockPatternView.this.mDotHitRadius;
            rect.left = (int) (centerXForColumn - f);
            rect.right = (int) (centerXForColumn + f);
            rect.top = (int) (centerYForRow - f);
            rect.bottom = (int) (centerYForRow + f);
            return rect;
        }

        private CharSequence getTextForVirtualView(int i) {
            return LockPatternView.this.getResources().getString(R.string.lockscreen_access_pattern_cell_added_verbose, Integer.valueOf(i));
        }

        private int getVirtualViewIdForHit(float f, float f2) {
            Cell cellDetectCellHit = LockPatternView.this.detectCellHit(f, f2);
            if (cellDetectCellHit == null) {
                return Integer.MIN_VALUE;
            }
            boolean z = LockPatternView.this.mPatternDrawLookup[cellDetectCellHit.row][cellDetectCellHit.column];
            int i = (cellDetectCellHit.row * 3) + cellDetectCellHit.column + 1;
            if (z) {
                return i;
            }
            return Integer.MIN_VALUE;
        }
    }
}
