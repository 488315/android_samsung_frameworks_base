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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes5.dex */
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
    private final int mDotSize;
    private final int mDotSizeActivated;
    private boolean mDrawingProfilingStarted;
    private boolean mEnlargeVertex;
    private int mErrorColor;
    private final PatternExploreByTouchHelper mExploreByTouchHelper;
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
    private final int mPathWidth;
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

    public interface OnPatternListener {
        void onPatternCellAdded(List<Cell> list);

        void onPatternCleared();

        void onPatternDetected(List<Cell> list);

        void onPatternStart();
    }

    public static final class Cell {
        private static final Cell[][] sCells = createCells();
        final int column;
        final int row;

        private static Cell[][] createCells() {
            Cell[][] res = (Cell[][]) Array.newInstance((Class<?>) Cell.class, 3, 3);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    res[i][j] = new Cell(i, j);
                }
            }
            return res;
        }

        private Cell(int row, int column) {
            checkRange(row, column);
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return this.row;
        }

        public int getColumn() {
            return this.column;
        }

        public static Cell of(int row, int column) {
            checkRange(row, column);
            return sCells[row][column];
        }

        private static void checkRange(int row, int column) {
            if (row < 0 || row > 2) {
                throw new IllegalArgumentException("row must be in range 0-2");
            }
            if (column < 0 || column > 2) {
                throw new IllegalArgumentException("column must be in range 0-2");
            }
        }

        public String toString() {
            return "(row="
                    + this.row
                    + ",clmn="
                    + this.column
                    + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public LockPatternView(Context context) {
        this(context, null);
    }

    public LockPatternView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDrawingProfilingStarted = false;
        this.mPaint = new Paint();
        this.mPathPaint = new Paint();
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
        TypedArray a =
                context.obtainStyledAttributes(
                        attrs,
                        R.styleable.LockPatternView,
                        R.attr.lockPatternStyle,
                        R.style.Widget_LockPatternView);
        String aspect = a.getString(0);
        if ("square".equals(aspect)) {
            this.mAspect = 0;
        } else if ("lock_width".equals(aspect)) {
            this.mAspect = 1;
        } else if ("lock_height".equals(aspect)) {
            this.mAspect = 2;
        } else {
            this.mAspect = 0;
        }
        setClickable(true);
        this.mPathPaint.setAntiAlias(true);
        this.mPathPaint.setDither(true);
        this.mRegularColor = a.getColor(7, 0);
        this.mErrorColor = a.getColor(4, 0);
        this.mSuccessColor = a.getColor(8, 0);
        this.mDotColor = a.getColor(2, this.mRegularColor);
        this.mDotActivatedColor = a.getColor(1, this.mDotColor);
        this.mKeepDotActivated = a.getBoolean(5, false);
        this.mEnlargeVertex = a.getBoolean(3, false);
        int pathColor = a.getColor(6, this.mRegularColor);
        this.mPathPaint.setColor(pathColor);
        this.mOriginRegularColor = this.mRegularColor;
        this.mOriginSuccessColor = this.mSuccessColor;
        this.mOriginPathColor = pathColor;
        this.mPathPaint.setStyle(Paint.Style.STROKE);
        this.mPathPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPathWidth = getResources().getDimensionPixelSize(R.dimen.lock_pattern_dot_line_width);
        this.mPathPaint.setStrokeWidth(this.mPathWidth);
        this.mLineFadeOutAnimationDurationMs =
                getResources().getInteger(R.integer.lock_pattern_line_fade_out_duration);
        this.mLineFadeOutAnimationDelayMs =
                getResources().getInteger(R.integer.lock_pattern_line_fade_out_delay);
        this.mFadePatternAnimationDurationMs =
                getResources().getInteger(R.integer.lock_pattern_fade_pattern_duration);
        this.mFadePatternAnimationDelayMs =
                getResources().getInteger(R.integer.lock_pattern_fade_pattern_delay);
        this.mDotSize = getResources().getDimensionPixelSize(R.dimen.lock_pattern_dot_size);
        this.mDotSizeActivated =
                getResources().getDimensionPixelSize(R.dimen.lock_pattern_dot_size_activated);
        TypedValue outValue = new TypedValue();
        getResources().getValue(R.dimen.lock_pattern_dot_hit_factor, outValue, true);
        this.mDotHitFactor = Math.max(Math.min(outValue.getFloat(), 1.0f), 0.2f);
        this.mUseLockPatternDrawable = getResources().getBoolean(R.bool.use_lock_pattern_drawable);
        if (this.mUseLockPatternDrawable) {
            this.mSelectedDrawable = getResources().getDrawable(R.drawable.lockscreen_selected);
            this.mNotSelectedDrawable =
                    getResources().getDrawable(R.drawable.lockscreen_notselected);
        }
        this.mPaint.setAntiAlias(true);
        this.mPaint.setDither(true);
        this.mCellStates = (CellState[][]) Array.newInstance((Class<?>) CellState.class, 3, 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.mCellStates[i][j] = new CellState();
                this.mCellStates[i][j].radius = this.mDotSize / 2;
                this.mCellStates[i][j].row = i;
                this.mCellStates[i][j].col = j;
            }
        }
        this.mFastOutSlowInInterpolator = AnimationUtils.loadInterpolator(context, 17563661);
        this.mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(context, 17563662);
        this.mStandardAccelerateInterpolator = AnimationUtils.loadInterpolator(context, 17563663);
        this.mExploreByTouchHelper = new PatternExploreByTouchHelper(this);
        setAccessibilityDelegate(this.mExploreByTouchHelper);
        int fadeAwayGradientWidth =
                getResources().getDimensionPixelSize(R.dimen.lock_pattern_fade_away_gradient_width);
        this.mFadeOutGradientShader =
                new LinearGradient(
                        (-fadeAwayGradientWidth) / 2.0f,
                        0.0f,
                        fadeAwayGradientWidth / 2.0f,
                        0.0f,
                        0,
                        pathColor,
                        Shader.TileMode.CLAMP);
        a.recycle();
    }

    public CellState[][] getCellStates() {
        return this.mCellStates;
    }

    public boolean isInStealthMode() {
        return this.mInStealthMode;
    }

    public void setInStealthMode(boolean inStealthMode) {
        this.mInStealthMode = inStealthMode;
    }

    public void setFadePattern(boolean fadePattern) {
        this.mFadePattern = fadePattern;
    }

    public void setOnPatternListener(OnPatternListener onPatternListener) {
        this.mOnPatternListener = onPatternListener;
    }

    public void setPattern(DisplayMode displayMode, List<Cell> pattern) {
        this.mPattern.clear();
        this.mPattern.addAll(pattern);
        clearPatternDrawLookup();
        for (Cell cell : pattern) {
            this.mPatternDrawLookup[cell.getRow()][cell.getColumn()] = true;
        }
        setDisplayMode(displayMode);
    }

    public void setDisplayMode(DisplayMode displayMode) {
        this.mPatternDisplayMode = displayMode;
        if (displayMode == DisplayMode.Animate) {
            if (this.mPattern.size() == 0) {
                throw new IllegalStateException(
                        "you must have a pattern to animate if you want to set the display mode to"
                            + " animate");
            }
            this.mAnimatingPeriodStart = SystemClock.elapsedRealtime();
            Cell first = this.mPattern.get(0);
            this.mInProgressX = getCenterXForColumn(first.getColumn());
            this.mInProgressY = getCenterYForRow(first.getRow());
            clearPatternDrawLookup();
        }
        invalidate();
    }

    public void startCellStateAnimation(
            CellState cellState,
            float startAlpha,
            float endAlpha,
            float startTranslationY,
            float endTranslationY,
            float startScale,
            float endScale,
            long delay,
            long duration,
            Interpolator interpolator,
            Runnable finishRunnable) {
        if (isHardwareAccelerated()) {
            startCellStateAnimationHw(
                    cellState,
                    startAlpha,
                    endAlpha,
                    startTranslationY,
                    endTranslationY,
                    startScale,
                    endScale,
                    delay,
                    duration,
                    interpolator,
                    finishRunnable);
        } else {
            startCellStateAnimationSw(
                    cellState,
                    startAlpha,
                    endAlpha,
                    startTranslationY,
                    endTranslationY,
                    startScale,
                    endScale,
                    delay,
                    duration,
                    interpolator,
                    finishRunnable);
        }
    }

    private void startCellStateAnimationSw(
            final CellState cellState,
            final float startAlpha,
            final float endAlpha,
            final float startTranslationY,
            final float endTranslationY,
            final float startScale,
            final float endScale,
            long delay,
            long duration,
            Interpolator interpolator,
            final Runnable finishRunnable) {
        cellState.alpha = startAlpha;
        cellState.translationY = startTranslationY;
        cellState.radius = (this.mDotSize / 2) * startScale;
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(duration);
        animator.setStartDelay(delay);
        animator.setInterpolator(interpolator);
        animator.addUpdateListener(
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.android.internal.widget.LockPatternView.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float t = ((Float) animation.getAnimatedValue()).floatValue();
                        cellState.alpha = ((1.0f - t) * startAlpha) + (endAlpha * t);
                        cellState.translationY =
                                ((1.0f - t) * startTranslationY) + (endTranslationY * t);
                        cellState.radius =
                                (LockPatternView.this.mDotSize / 2)
                                        * (((1.0f - t) * startScale) + (endScale * t));
                        LockPatternView.this.invalidate();
                    }
                });
        animator.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.android.internal.widget.LockPatternView.2
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        if (finishRunnable != null) {
                            finishRunnable.run();
                        }
                    }
                });
        animator.start();
    }

    private void startCellStateAnimationHw(
            final CellState cellState,
            float startAlpha,
            float endAlpha,
            float startTranslationY,
            float endTranslationY,
            float startScale,
            float endScale,
            long delay,
            long duration,
            Interpolator interpolator,
            final Runnable finishRunnable) {
        cellState.alpha = endAlpha;
        cellState.translationY = endTranslationY;
        cellState.radius = (this.mDotSize / 2) * endScale;
        cellState.hwAnimating = true;
        cellState.hwCenterY =
                CanvasProperty.createFloat(getCenterYForRow(cellState.row) + startTranslationY);
        cellState.hwCenterX = CanvasProperty.createFloat(getCenterXForColumn(cellState.col));
        cellState.hwRadius = CanvasProperty.createFloat((this.mDotSize / 2) * startScale);
        this.mPaint.setColor(getCurrentColor(false));
        this.mPaint.setAlpha((int) (255.0f * startAlpha));
        cellState.hwPaint = CanvasProperty.createPaint(new Paint(this.mPaint));
        startRtFloatAnimation(
                cellState.hwCenterY,
                getCenterYForRow(cellState.row) + endTranslationY,
                delay,
                duration,
                interpolator);
        startRtFloatAnimation(
                cellState.hwRadius, (this.mDotSize / 2) * endScale, delay, duration, interpolator);
        startRtAlphaAnimation(
                cellState,
                endAlpha,
                delay,
                duration,
                interpolator,
                new AnimatorListenerAdapter() { // from class:
                                                // com.android.internal.widget.LockPatternView.3
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        cellState.hwAnimating = false;
                        if (finishRunnable != null) {
                            finishRunnable.run();
                        }
                    }
                });
        invalidate();
    }

    private void startRtAlphaAnimation(
            CellState cellState,
            float endAlpha,
            long delay,
            long duration,
            Interpolator interpolator,
            Animator.AnimatorListener listener) {
        RenderNodeAnimator animator =
                new RenderNodeAnimator(cellState.hwPaint, 1, (int) (255.0f * endAlpha));
        animator.setDuration(duration);
        animator.setStartDelay(delay);
        animator.setInterpolator(interpolator);
        animator.setTarget((View) this);
        animator.addListener(listener);
        animator.start();
    }

    private void startRtFloatAnimation(
            CanvasProperty<Float> property,
            float endValue,
            long delay,
            long duration,
            Interpolator interpolator) {
        RenderNodeAnimator animator = new RenderNodeAnimator(property, endValue);
        animator.setDuration(duration);
        animator.setStartDelay(delay);
        animator.setInterpolator(interpolator);
        animator.setTarget((View) this);
        animator.start();
    }

    private void notifyCellAdded() {
        if (this.mOnPatternListener != null) {
            this.mOnPatternListener.onPatternCellAdded(this.mPattern);
        }
        this.mExploreByTouchHelper.invalidateRoot();
    }

    protected void notifyPatternStarted() {
        sendAccessEvent(R.string.lockscreen_access_pattern_start);
        if (this.mOnPatternListener != null) {
            this.mOnPatternListener.onPatternStart();
        }
    }

    private void notifyPatternDetected() {
        sendAccessEvent(R.string.lockscreen_access_pattern_detected);
        if (this.mOnPatternListener != null) {
            this.mOnPatternListener.onPatternDetected(this.mPattern);
        }
    }

    private void notifyPatternCleared() {
        sendAccessEvent(R.string.lockscreen_access_pattern_cleared);
        if (this.mOnPatternListener != null) {
            this.mOnPatternListener.onPatternCleared();
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
    protected boolean dispatchHoverEvent(MotionEvent event) {
        boolean handled = super.dispatchHoverEvent(event);
        return handled | this.mExploreByTouchHelper.dispatchHoverEvent(event);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetPattern() {
        if (this.mKeepDotActivated && !this.mPattern.isEmpty()) {
            resetLastActivatedCellProgress();
        }
        this.mPattern.clear();
        this.mPatternPath.reset();
        clearPatternDrawLookup();
        this.mPatternDisplayMode = DisplayMode.Correct;
        invalidate();
    }

    private void resetLastActivatedCellProgress() {
        ArrayList<Cell> pattern = this.mPattern;
        Cell lastCell = pattern.get(pattern.size() - 1);
        CellState cellState = this.mCellStates[lastCell.row][lastCell.column];
        if (cellState.activationAnimator != null) {
            cellState.activationAnimator.cancel();
        }
        cellState.activationAnimationProgress = 0.0f;
    }

    public boolean isEmpty() {
        return this.mPattern.isEmpty();
    }

    protected void clearPatternDrawLookup() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.mPatternDrawLookup[i][j] = false;
                this.mLineFadeStart[(j * 3) + i] = 0;
                this.mCellStates[i][j].alpha = 0.7f;
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int width = (w - this.mPaddingLeft) - this.mPaddingRight;
        this.mSquareWidth = width / 3.0f;
        int height = (h - this.mPaddingTop) - this.mPaddingBottom;
        this.mSquareHeight = height / 3.0f;
        this.mExploreByTouchHelper.invalidateRoot();
        this.mDotHitMaxRadius = Math.min(this.mSquareHeight / 2.0f, this.mSquareWidth / 2.0f);
        this.mDotHitRadius = this.mDotHitMaxRadius * this.mDotHitFactor;
        if (this.mUseLockPatternDrawable) {
            this.mNotSelectedDrawable.setBounds(this.mPaddingLeft, this.mPaddingTop, width, height);
            this.mSelectedDrawable.setBounds(this.mPaddingLeft, this.mPaddingTop, width, height);
        }
    }

    private int resolveMeasured(int measureSpec, int desired) {
        int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (View.MeasureSpec.getMode(measureSpec)) {
            case Integer.MIN_VALUE:
                int result = Math.max(specSize, desired);
                return result;
            case 0:
                return desired;
            default:
                return specSize;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minimumWidth = getSuggestedMinimumWidth();
        int minimumHeight = getSuggestedMinimumHeight();
        int viewWidth = resolveMeasured(widthMeasureSpec, minimumWidth);
        int viewHeight = resolveMeasured(heightMeasureSpec, minimumHeight);
        switch (this.mAspect) {
            case 0:
                int min = Math.min(viewWidth, viewHeight);
                viewHeight = min;
                viewWidth = min;
                break;
            case 1:
                viewHeight = Math.min(viewWidth, viewHeight);
                break;
            case 2:
                viewWidth = Math.min(viewWidth, viewHeight);
                break;
        }
        setMeasuredDimension(viewWidth, viewHeight);
    }

    protected Cell detectAndAddHit(float x, float y) {
        Cell cell = checkForNewHit(x, y);
        if (cell != null) {
            Cell fillInGapCell = null;
            ArrayList<Cell> pattern = this.mPattern;
            Cell lastCell = null;
            if (!pattern.isEmpty()) {
                Cell lastCell2 = pattern.get(pattern.size() - 1);
                lastCell = lastCell2;
                int dRow = cell.row - lastCell.row;
                int dColumn = cell.column - lastCell.column;
                int fillInRow = lastCell.row;
                int fillInColumn = lastCell.column;
                if (Math.abs(dRow) == 2 && Math.abs(dColumn) != 1) {
                    fillInRow = lastCell.row + (dRow > 0 ? 1 : -1);
                }
                if (Math.abs(dColumn) == 2 && Math.abs(dRow) != 1) {
                    fillInColumn = lastCell.column + (dColumn > 0 ? 1 : -1);
                }
                fillInGapCell = Cell.of(fillInRow, fillInColumn);
            }
            if (fillInGapCell != null
                    && !this.mPatternDrawLookup[fillInGapCell.row][fillInGapCell.column]) {
                addCellToPattern(fillInGapCell);
                if (this.mKeepDotActivated) {
                    startCellDeactivatedAnimation(fillInGapCell);
                }
            }
            if (this.mKeepDotActivated && lastCell != null) {
                startCellDeactivatedAnimation(lastCell);
            }
            addCellToPattern(cell);
            performHapticFeedback(0, 1);
            return cell;
        }
        return null;
    }

    protected void addCellToPattern(Cell newCell) {
        this.mPatternDrawLookup[newCell.getRow()][newCell.getColumn()] = true;
        this.mPattern.add(newCell);
        if (!this.mInStealthMode) {
            startCellActivatedAnimation(newCell);
        }
        notifyCellAdded();
    }

    private void startFadePatternAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(createFadePatternAnimation());
        animatorSet.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.android.internal.widget.LockPatternView.4
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        LockPatternView.this.mFadeAnimationAlpha = 255;
                        LockPatternView.this.mFadeClear = false;
                        LockPatternView.this.resetPattern();
                    }
                });
        animatorSet.start();
    }

    private Animator createFadePatternAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(255, 0);
        valueAnimator.addUpdateListener(
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda4
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        LockPatternView.this.lambda$createFadePatternAnimation$0(valueAnimator2);
                    }
                });
        valueAnimator.setInterpolator(this.mStandardAccelerateInterpolator);
        valueAnimator.setStartDelay(this.mFadePatternAnimationDelayMs);
        valueAnimator.setDuration(this.mFadePatternAnimationDurationMs);
        return valueAnimator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createFadePatternAnimation$0(ValueAnimator animation) {
        this.mFadeAnimationAlpha = ((Integer) animation.getAnimatedValue()).intValue();
        invalidate();
    }

    private void startCellActivatedAnimation(Cell cell) {
        startCellActivationAnimation(cell, 0);
    }

    private void startCellDeactivatedAnimation(Cell cell) {
        startCellActivationAnimation(cell, 1);
    }

    private void startCellActivationAnimation(Cell cell, int activate) {
        final CellState cellState = this.mCellStates[cell.row][cell.column];
        if (cellState.activationAnimator != null) {
            cellState.activationAnimator.cancel();
        }
        AnimatorSet animatorSet = new AnimatorSet();
        float startX = activate == 0 ? this.mInProgressX : cellState.lineEndX;
        float startY = activate == 0 ? this.mInProgressY : cellState.lineEndY;
        AnimatorSet.Builder animatorSetBuilder =
                animatorSet
                        .play(createLineDisappearingAnimation())
                        .with(
                                createLineEndAnimation(
                                        cellState,
                                        startX,
                                        startY,
                                        getCenterXForColumn(cell.column),
                                        getCenterYForRow(cell.row)));
        if (this.mDotSize != this.mDotSizeActivated) {
            animatorSetBuilder.with(createDotRadiusAnimation(cellState));
        }
        if (this.mDotColor != this.mDotActivatedColor) {
            animatorSetBuilder.with(createDotActivationColorAnimation(cellState, activate));
        }
        animatorSet.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.android.internal.widget.LockPatternView.5
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        cellState.activationAnimator = null;
                        LockPatternView.this.invalidate();
                    }
                });
        cellState.activationAnimator = animatorSet;
        animatorSet.start();
    }

    private Animator createDotActivationColorAnimation(final CellState cellState, int activate) {
        ValueAnimator.AnimatorUpdateListener updateListener =
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        LockPatternView.this.lambda$createDotActivationColorAnimation$1(
                                cellState, valueAnimator);
                    }
                };
        ValueAnimator activateAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        ValueAnimator deactivateAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        activateAnimator.addUpdateListener(updateListener);
        deactivateAnimator.addUpdateListener(updateListener);
        activateAnimator.setInterpolator(this.mFastOutSlowInInterpolator);
        deactivateAnimator.setInterpolator(this.mLinearOutSlowInInterpolator);
        activateAnimator.setDuration(50L);
        deactivateAnimator.setDuration(50L);
        AnimatorSet set = new AnimatorSet();
        if (this.mKeepDotActivated) {
            set.play(activate == 0 ? activateAnimator : deactivateAnimator);
        } else {
            set.play(deactivateAnimator)
                    .after(
                            (this.mLineFadeOutAnimationDelayMs
                                            + this.mLineFadeOutAnimationDurationMs)
                                    - 100)
                    .after(activateAnimator);
        }
        return set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createDotActivationColorAnimation$1(
            CellState cellState, ValueAnimator valueAnimator) {
        cellState.activationAnimationProgress =
                ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    private Animator createLineEndAnimation(
            final CellState state,
            final float startX,
            final float startY,
            final float targetX,
            final float targetY) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.addUpdateListener(
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        LockPatternView.this.lambda$createLineEndAnimation$2(
                                state, startX, targetX, startY, targetY, valueAnimator2);
                    }
                });
        valueAnimator.setInterpolator(this.mFastOutSlowInInterpolator);
        valueAnimator.setDuration(50L);
        return valueAnimator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createLineEndAnimation$2(
            CellState state,
            float startX,
            float targetX,
            float startY,
            float targetY,
            ValueAnimator animation) {
        float t = ((Float) animation.getAnimatedValue()).floatValue();
        state.lineEndX = ((1.0f - t) * startX) + (t * targetX);
        state.lineEndY = ((1.0f - t) * startY) + (t * targetY);
        invalidate();
    }

    private Animator createLineDisappearingAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.addUpdateListener(
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        LockPatternView.this.lambda$createLineDisappearingAnimation$3(
                                valueAnimator2);
                    }
                });
        valueAnimator.setStartDelay(this.mLineFadeOutAnimationDelayMs);
        valueAnimator.setDuration(this.mLineFadeOutAnimationDurationMs);
        return valueAnimator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createLineDisappearingAnimation$3(ValueAnimator animation) {
        invalidate();
    }

    private Animator createDotRadiusAnimation(final CellState state) {
        float defaultRadius = this.mDotSize / 2.0f;
        float activatedRadius = this.mDotSizeActivated / 2.0f;
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener =
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        LockPatternView.this.lambda$createDotRadiusAnimation$4(
                                state, valueAnimator);
                    }
                };
        ValueAnimator activationAnimator = ValueAnimator.ofFloat(defaultRadius, activatedRadius);
        activationAnimator.addUpdateListener(animatorUpdateListener);
        activationAnimator.setInterpolator(this.mLinearOutSlowInInterpolator);
        activationAnimator.setDuration(96L);
        ValueAnimator deactivationAnimator = ValueAnimator.ofFloat(activatedRadius, defaultRadius);
        deactivationAnimator.addUpdateListener(animatorUpdateListener);
        deactivationAnimator.setInterpolator(this.mFastOutSlowInInterpolator);
        deactivationAnimator.setDuration(192L);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(activationAnimator, deactivationAnimator);
        return set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createDotRadiusAnimation$4(
            CellState state, ValueAnimator animation) {
        state.radius = ((Float) animation.getAnimatedValue()).floatValue();
        state.alpha = 1.0f;
        invalidate();
    }

    private Cell checkForNewHit(float x, float y) {
        Cell cellHit = detectCellHit(x, y);
        if (cellHit != null && !this.mPatternDrawLookup[cellHit.row][cellHit.column]) {
            return cellHit;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Cell detectCellHit(float x, float y) {
        float hitRadiusSquared;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                float centerY = getCenterYForRow(row);
                float centerX = getCenterXForColumn(column);
                if (this.mEnlargeVertex) {
                    if (isVertex(row, column)) {
                        hitRadiusSquared = this.mDotHitMaxRadius * this.mDotHitMaxRadius;
                    } else {
                        hitRadiusSquared = this.mDotHitRadius * this.mDotHitRadius;
                    }
                } else {
                    float hitRadiusSquared2 = this.mDotHitRadius;
                    hitRadiusSquared = hitRadiusSquared2 * this.mDotHitRadius;
                }
                if (((x - centerX) * (x - centerX)) + ((y - centerY) * (y - centerY))
                        < hitRadiusSquared) {
                    return Cell.of(row, column);
                }
            }
        }
        return null;
    }

    private boolean isVertex(int row, int column) {
        return (row == 1 || column == 1) ? false : true;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent event) {
        if (AccessibilityManager.getInstance(this.mContext).isTouchExplorationEnabled()) {
            int action = event.getAction();
            switch (action) {
                case 7:
                    event.setAction(2);
                    break;
                case 9:
                    event.setAction(0);
                    break;
                case 10:
                    event.setAction(1);
                    break;
            }
            onTouchEvent(event);
            event.setAction(action);
        }
        return super.onHoverEvent(event);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!this.mInputEnabled || !isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case 0:
                handleActionDown(event);
                return true;
            case 1:
                handleActionUp();
                return true;
            case 2:
                handleActionMove(event);
                return true;
            case 3:
                if (this.mPatternInProgress) {
                    setPatternInProgress(false);
                    resetPattern();
                    notifyPatternCleared();
                }
                return true;
            default:
                return false;
        }
    }

    private void setPatternInProgress(boolean progress) {
        this.mPatternInProgress = progress;
        this.mExploreByTouchHelper.invalidateRoot();
    }

    protected void handleActionMove(MotionEvent event) {
        float radius;
        int historySize;
        boolean invalidateNow;
        MotionEvent motionEvent = event;
        float radius2 = this.mPathWidth;
        int historySize2 = event.getHistorySize();
        this.mTmpInvalidateRect.setEmpty();
        boolean invalidateNow2 = false;
        int i = 0;
        while (i < historySize2 + 1) {
            float x = i < historySize2 ? motionEvent.getHistoricalX(i) : event.getX();
            float y = i < historySize2 ? motionEvent.getHistoricalY(i) : event.getY();
            Cell hitCell = detectAndAddHit(x, y);
            int patternSize = this.mPattern.size();
            if (hitCell != null && patternSize == 1) {
                setPatternInProgress(true);
                notifyPatternStarted();
            }
            float dx = Math.abs(x - this.mInProgressX);
            float dy = Math.abs(y - this.mInProgressY);
            if (dx > 0.0f || dy > 0.0f) {
                invalidateNow2 = true;
            }
            if (!this.mPatternInProgress || patternSize <= 0) {
                radius = radius2;
                historySize = historySize2;
                invalidateNow = invalidateNow2;
            } else {
                ArrayList<Cell> pattern = this.mPattern;
                Cell lastCell = pattern.get(patternSize - 1);
                float lastCellCenterX = getCenterXForColumn(lastCell.column);
                float lastCellCenterY = getCenterYForRow(lastCell.row);
                float left = Math.min(lastCellCenterX, x) - radius2;
                historySize = historySize2;
                float right = Math.max(lastCellCenterX, x) + radius2;
                invalidateNow = invalidateNow2;
                float top = Math.min(lastCellCenterY, y) - radius2;
                float bottom = Math.max(lastCellCenterY, y) + radius2;
                if (hitCell == null) {
                    radius = radius2;
                } else {
                    radius = radius2;
                    float radius3 = this.mSquareWidth;
                    float width = radius3 * 0.5f;
                    float height = this.mSquareHeight * 0.5f;
                    float hitCellCenterX = getCenterXForColumn(hitCell.column);
                    float hitCellCenterY = getCenterYForRow(hitCell.row);
                    left = Math.min(hitCellCenterX - width, left);
                    right = Math.max(hitCellCenterX + width, right);
                    top = Math.min(hitCellCenterY - height, top);
                    bottom = Math.max(hitCellCenterY + height, bottom);
                }
                this.mTmpInvalidateRect.union(
                        Math.round(left), Math.round(top), Math.round(right), Math.round(bottom));
            }
            i++;
            motionEvent = event;
            radius2 = radius;
            historySize2 = historySize;
            invalidateNow2 = invalidateNow;
        }
        this.mInProgressX = event.getX();
        this.mInProgressY = event.getY();
        if (invalidateNow2) {
            this.mInvalidate.union(this.mTmpInvalidateRect);
            invalidate(this.mInvalidate);
            this.mInvalidate.set(this.mTmpInvalidateRect);
        }
    }

    private void sendAccessEvent(int resId) {
        announceForAccessibility(this.mContext.getString(resId));
    }

    private void handleActionUp() {
        if (!this.mPattern.isEmpty()) {
            setPatternInProgress(false);
            cancelLineAnimations();
            if (this.mKeepDotActivated) {
                deactivateLastCell();
            }
            notifyPatternDetected();
            if (this.mFadePattern) {
                clearPatternDrawLookup();
                this.mPatternDisplayMode = DisplayMode.Correct;
            }
            invalidate();
        }
    }

    private void deactivateLastCell() {
        Cell lastCell = this.mPattern.get(this.mPattern.size() - 1);
        startCellDeactivatedAnimation(lastCell);
    }

    private void cancelLineAnimations() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                CellState state = this.mCellStates[i][j];
                if (state.activationAnimator != null) {
                    state.activationAnimator.cancel();
                    state.activationAnimator = null;
                    state.radius = this.mDotSize / 2.0f;
                    state.lineEndX = Float.MIN_VALUE;
                    state.lineEndY = Float.MIN_VALUE;
                    state.activationAnimationProgress = 0.0f;
                }
            }
        }
    }

    private void handleActionDown(MotionEvent event) {
        resetPattern();
        float x = event.getX();
        float y = event.getY();
        Cell hitCell = detectAndAddHit(x, y);
        if (hitCell != null) {
            setPatternInProgress(true);
            this.mPatternDisplayMode = DisplayMode.Correct;
            notifyPatternStarted();
        } else if (this.mPatternInProgress) {
            setPatternInProgress(false);
            notifyPatternCleared();
        }
        if (hitCell != null) {
            float startX = getCenterXForColumn(hitCell.column);
            float startY = getCenterYForRow(hitCell.row);
            float widthOffset = this.mSquareWidth / 2.0f;
            float heightOffset = this.mSquareHeight / 2.0f;
            invalidate(
                    (int) (startX - widthOffset),
                    (int) (startY - heightOffset),
                    (int) (startX + widthOffset),
                    (int) (startY + heightOffset));
        }
        this.mInProgressX = x;
        this.mInProgressY = y;
    }

    public void setColors(int regularColor, int successColor, int errorColor) {
        this.mDotActivatedColor = regularColor;
        this.mDotColor = regularColor;
        this.mRegularColor = regularColor;
        this.mErrorColor = errorColor;
        this.mSuccessColor = successColor;
        this.mPathPaint.setColor(regularColor);
        updateGradientPathColor(regularColor);
        invalidate();
    }

    protected float getCenterXForColumn(int column) {
        return this.mPaddingLeft + (column * this.mSquareWidth) + (this.mSquareWidth / 2.0f);
    }

    protected float getCenterYForRow(int row) {
        return this.mPaddingTop + (row * this.mSquareHeight) + (this.mSquareHeight / 2.0f);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Canvas canvas2;
        boolean[][] drawLookup;
        float lastY;
        float lastX;
        int i;
        ArrayList<Cell> pattern;
        int count;
        boolean[][] drawLookup2;
        float centerY;
        float centerX;
        float endX;
        float endY;
        ArrayList<Cell> pattern2 = this.mPattern;
        int count2 = pattern2.size();
        boolean[][] drawLookup3 = this.mPatternDrawLookup;
        if (this.mPatternDisplayMode == DisplayMode.Animate) {
            int oneCycle = (count2 + 1) * 700;
            int spotInCycle =
                    ((int) (SystemClock.elapsedRealtime() - this.mAnimatingPeriodStart)) % oneCycle;
            int numCircles = spotInCycle / 700;
            clearPatternDrawLookup();
            for (int i2 = 0; i2 < numCircles; i2++) {
                Cell cell = pattern2.get(i2);
                drawLookup3[cell.getRow()][cell.getColumn()] = true;
            }
            boolean needToUpdateInProgressPoint = numCircles > 0 && numCircles < count2;
            if (needToUpdateInProgressPoint) {
                float percentageOfNextCircle = (spotInCycle % 700) / 700.0f;
                Cell currentCell = pattern2.get(numCircles - 1);
                float centerX2 = getCenterXForColumn(currentCell.column);
                float centerY2 = getCenterYForRow(currentCell.row);
                Cell nextCell = pattern2.get(numCircles);
                float dx =
                        (getCenterXForColumn(nextCell.column) - centerX2) * percentageOfNextCircle;
                float dy = (getCenterYForRow(nextCell.row) - centerY2) * percentageOfNextCircle;
                this.mInProgressX = centerX2 + dx;
                this.mInProgressY = centerY2 + dy;
            }
            invalidate();
        }
        Path currentPath = this.mCurrentPath;
        currentPath.rewind();
        boolean drawPath = !this.mInStealthMode;
        if (drawPath && !this.mFadeClear) {
            this.mPathPaint.setColor(getCurrentColor(true));
            boolean anyCircles = false;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            float lastX2 = 0.0f;
            float lastY2 = 0.0f;
            int i3 = 0;
            while (true) {
                if (i3 >= count2) {
                    lastY = lastY2;
                    drawLookup = drawLookup3;
                    lastX = lastX2;
                    break;
                }
                Cell cell2 = pattern2.get(i3);
                if (drawLookup3[cell2.row][cell2.column]) {
                    if (this.mLineFadeStart[i3] == 0) {
                        this.mLineFadeStart[i3] = SystemClock.elapsedRealtime();
                    }
                    float centerX3 = getCenterXForColumn(cell2.column);
                    float centerY3 = getCenterYForRow(cell2.row);
                    if (i3 == 0) {
                        i = i3;
                        pattern = pattern2;
                        count = count2;
                        drawLookup2 = drawLookup3;
                        centerY = centerY3;
                        centerX = centerX3;
                    } else {
                        CellState state = this.mCellStates[cell2.row][cell2.column];
                        currentPath.rewind();
                        if (state.lineEndX != Float.MIN_VALUE
                                && state.lineEndY != Float.MIN_VALUE) {
                            float endX2 = state.lineEndX;
                            endX = endX2;
                            endY = state.lineEndY;
                        } else {
                            endX = centerX3;
                            endY = centerY3;
                        }
                        pattern = pattern2;
                        centerY = centerY3;
                        float centerY4 = endX;
                        count = count2;
                        centerX = centerX3;
                        float centerX4 = endY;
                        i = i3;
                        drawLookup2 = drawLookup3;
                        drawLineSegment(
                                canvas,
                                lastX2,
                                lastY2,
                                centerY4,
                                centerX4,
                                this.mLineFadeStart[i3],
                                elapsedRealtime);
                        Path tempPath = new Path();
                        tempPath.moveTo(lastX2, lastY2);
                        tempPath.lineTo(centerX, centerY);
                        this.mPatternPath.addPath(tempPath);
                    }
                    lastX2 = centerX;
                    lastY2 = centerY;
                    i3 = i + 1;
                    anyCircles = true;
                    drawLookup3 = drawLookup2;
                    pattern2 = pattern;
                    count2 = count;
                } else {
                    lastY = lastY2;
                    drawLookup = drawLookup3;
                    lastX = lastX2;
                    break;
                }
            }
            if (!this.mPatternInProgress && this.mPatternDisplayMode != DisplayMode.Animate) {
                canvas2 = canvas;
            } else if (!anyCircles) {
                canvas2 = canvas;
            } else {
                currentPath.rewind();
                currentPath.moveTo(lastX, lastY);
                currentPath.lineTo(this.mInProgressX, this.mInProgressY);
                this.mPathPaint.setAlpha(
                        (int)
                                (calculateLastSegmentAlpha(
                                                this.mInProgressX, this.mInProgressY, lastX, lastY)
                                        * 255.0f));
                canvas2 = canvas;
                canvas2.drawPath(currentPath, this.mPathPaint);
            }
        } else {
            canvas2 = canvas;
            drawLookup = drawLookup3;
        }
        if (this.mFadeClear) {
            this.mPathPaint.setAlpha(this.mFadeAnimationAlpha);
            canvas2.drawPath(this.mPatternPath, this.mPathPaint);
        }
        for (int i4 = 0; i4 < 3; i4++) {
            float centerY5 = getCenterYForRow(i4);
            for (int j = 0; j < 3; j++) {
                CellState cellState = this.mCellStates[i4][j];
                float centerX5 = getCenterXForColumn(j);
                float translationY = cellState.translationY;
                if (this.mUseLockPatternDrawable) {
                    drawCellDrawable(canvas, i4, j, cellState.radius, drawLookup[i4][j]);
                } else if (isHardwareAccelerated() && cellState.hwAnimating) {
                    RecordingCanvas recordingCanvas = (RecordingCanvas) canvas2;
                    recordingCanvas.drawCircle(
                            cellState.hwCenterX,
                            cellState.hwCenterY,
                            cellState.hwRadius,
                            cellState.hwPaint);
                } else {
                    drawCircle(
                            canvas,
                            (int) centerX5,
                            ((int) centerY5) + translationY,
                            cellState.radius,
                            drawLookup[i4][j],
                            cellState.alpha,
                            cellState.activationAnimationProgress);
                }
            }
        }
    }

    private void drawLineSegment(
            Canvas canvas,
            float startX,
            float startY,
            float endX,
            float endY,
            long lineFadeStart,
            long elapsedRealtime) {
        if (!this.mFadePattern) {
            this.mPathPaint.setAlpha(255);
            canvas.drawLine(startX, startY, endX, endY, this.mPathPaint);
        } else if (elapsedRealtime - lineFadeStart
                < this.mLineFadeOutAnimationDelayMs + this.mLineFadeOutAnimationDurationMs) {
            float fadeAwayProgress =
                    Math.max(
                            ((elapsedRealtime - lineFadeStart) - this.mLineFadeOutAnimationDelayMs)
                                    / this.mLineFadeOutAnimationDurationMs,
                            0.0f);
            drawFadingAwayLineSegment(canvas, startX, startY, endX, endY, fadeAwayProgress);
        }
    }

    private void drawFadingAwayLineSegment(
            Canvas canvas,
            float startX,
            float startY,
            float endX,
            float endY,
            float fadeAwayProgress) {
        float segmentAngleDegrees;
        this.mPathPaint.setAlpha((int) ((1.0f - fadeAwayProgress) * 255.0f));
        this.mPathPaint.setShader(this.mFadeOutGradientShader);
        canvas.save();
        float gradientMidX = (endX * fadeAwayProgress) + ((1.0f - fadeAwayProgress) * startX);
        float gradientMidY = (endY * fadeAwayProgress) + ((1.0f - fadeAwayProgress) * startY);
        canvas.translate(gradientMidX, gradientMidY);
        double segmentAngleRad = Math.atan((endY - startY) / (endX - startX));
        float segmentAngleDegrees2 = (float) Math.toDegrees(segmentAngleRad);
        if (endX - startX >= 0.0f) {
            segmentAngleDegrees = segmentAngleDegrees2;
        } else {
            segmentAngleDegrees = segmentAngleDegrees2 + 180.0f;
        }
        canvas.rotate(segmentAngleDegrees);
        float segmentLength = (float) Math.hypot(endX - startX, endY - startY);
        canvas.drawLine(
                (-segmentLength) * fadeAwayProgress,
                0.0f,
                segmentLength * (1.0f - fadeAwayProgress),
                0.0f,
                this.mPathPaint);
        canvas.restore();
        this.mPathPaint.setShader(null);
    }

    private float calculateLastSegmentAlpha(float x, float y, float lastX, float lastY) {
        float diffX = x - lastX;
        float diffY = y - lastY;
        float dist = (float) Math.sqrt((diffX * diffX) + (diffY * diffY));
        float frac = dist / this.mSquareWidth;
        return Math.min(1.0f, Math.max(0.0f, (frac - 0.3f) * 4.0f));
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

    private int getCurrentColor(boolean partOfPattern) {
        if (!partOfPattern || this.mInStealthMode || this.mPatternInProgress) {
            return this.mRegularColor;
        }
        if (this.mPatternDisplayMode == DisplayMode.Wrong) {
            return this.mErrorColor;
        }
        if (this.mPatternDisplayMode == DisplayMode.Correct
                || this.mPatternDisplayMode == DisplayMode.Animate) {
            return this.mSuccessColor;
        }
        throw new IllegalStateException("unknown display mode " + this.mPatternDisplayMode);
    }

    private void drawCircle(
            Canvas canvas,
            float centerX,
            float centerY,
            float radius,
            boolean partOfPattern,
            float alpha,
            float activationAnimationProgress) {
        this.mPaint.setColor(getCurrentColor(partOfPattern));
        this.mPaint.setAlpha((int) (255.0f * alpha));
        canvas.drawCircle(centerX, centerY, radius, this.mPaint);
    }

    private void drawCellDrawable(
            Canvas canvas, int i, int j, float radius, boolean partOfPattern) {
        Rect dst =
                new Rect(
                        (int) (this.mPaddingLeft + (j * this.mSquareWidth)),
                        (int) (this.mPaddingTop + (i * this.mSquareHeight)),
                        (int) (this.mPaddingLeft + ((j + 1) * this.mSquareWidth)),
                        (int) (this.mPaddingTop + ((i + 1) * this.mSquareHeight)));
        float scale = radius / (this.mDotSize / 2);
        canvas.save();
        canvas.clipRect(dst);
        canvas.scale(scale, scale, dst.centerX(), dst.centerY());
        if (!partOfPattern || scale > 1.0f) {
            this.mNotSelectedDrawable.draw(canvas);
        } else {
            this.mSelectedDrawable.draw(canvas);
        }
        canvas.restore();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        byte[] patternBytes = LockPatternUtils.patternToByteArray(this.mPattern);
        String patternString = patternBytes != null ? new String(patternBytes) : null;
        if (patternBytes != null) {
            Arrays.fill(patternBytes, (byte) 0);
        }
        return new SavedState(
                superState,
                patternString,
                this.mPatternDisplayMode.ordinal(),
                this.mInputEnabled,
                this.mInStealthMode);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setPattern(
                DisplayMode.Correct,
                LockPatternUtils.byteArrayToPattern(ss.getSerializedPattern().getBytes()));
        this.mPatternDisplayMode = DisplayMode.values()[ss.getDisplayMode()];
        this.mInputEnabled = ss.isInputEnabled();
        this.mInStealthMode = ss.isInStealthMode();
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<
                        SavedState>() { // from class:
                                        // com.android.internal.widget.LockPatternView.SavedState.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
        private final int mDisplayMode;
        private final boolean mInStealthMode;
        private final boolean mInputEnabled;
        private final String mSerializedPattern;

        private SavedState(
                Parcelable superState,
                String serializedPattern,
                int displayMode,
                boolean inputEnabled,
                boolean inStealthMode) {
            super(superState);
            this.mSerializedPattern = serializedPattern;
            this.mDisplayMode = displayMode;
            this.mInputEnabled = inputEnabled;
            this.mInStealthMode = inStealthMode;
        }

        private SavedState(Parcel in) {
            super(in);
            this.mSerializedPattern = in.readString();
            this.mDisplayMode = in.readInt();
            this.mInputEnabled = ((Boolean) in.readValue(null)).booleanValue();
            this.mInStealthMode = ((Boolean) in.readValue(null)).booleanValue();
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

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState,
                  // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(this.mSerializedPattern);
            dest.writeInt(this.mDisplayMode);
            dest.writeValue(Boolean.valueOf(this.mInputEnabled));
            dest.writeValue(Boolean.valueOf(this.mInStealthMode));
        }
    }

    protected void updateViewStyle(boolean whiteWp) {
        int pathColor = this.mOriginPathColor;
        if (whiteWp) {
            this.mSuccessColor = -14342875;
            this.mDotColor = -14342875;
            this.mRegularColor = -14342875;
            pathColor = -14342875;
        } else {
            int i = this.mOriginRegularColor;
            this.mDotColor = i;
            this.mRegularColor = i;
            this.mSuccessColor = this.mOriginSuccessColor;
        }
        this.mPathPaint.setColor(pathColor);
        this.mPathPaint.setStyle(Paint.Style.STROKE);
        this.mPathPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        updateGradientPathColor(pathColor);
        invalidate();
    }

    private void updateGradientPathColor(int pathColor) {
        int fadeAwayGradientWidth =
                getResources().getDimensionPixelSize(R.dimen.lock_pattern_fade_away_gradient_width);
        this.mFadeOutGradientShader =
                new LinearGradient(
                        (-fadeAwayGradientWidth) / 2.0f,
                        0.0f,
                        fadeAwayGradientWidth / 2.0f,
                        0.0f,
                        0,
                        pathColor,
                        Shader.TileMode.CLAMP);
    }

    private final class PatternExploreByTouchHelper extends ExploreByTouchHelper {
        private final SparseArray<VirtualViewContainer> mItems;
        private Rect mTempRect;

        class VirtualViewContainer {
            CharSequence description;

            public VirtualViewContainer(CharSequence description) {
                this.description = description;
            }
        }

        public PatternExploreByTouchHelper(View forView) {
            super(forView);
            this.mTempRect = new Rect();
            this.mItems = new SparseArray<>();
            for (int i = 1; i < 10; i++) {
                this.mItems.put(i, new VirtualViewContainer(getTextForVirtualView(i)));
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float x, float y) {
            return getVirtualViewIdForHit(x, y);
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(IntArray virtualViewIds) {
            if (!LockPatternView.this.mPatternInProgress) {
                return;
            }
            for (int i = 1; i < 10; i++) {
                virtualViewIds.add(i);
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event) {
            VirtualViewContainer container = this.mItems.get(virtualViewId);
            if (container != null) {
                event.getText().add(container.description);
            }
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onPopulateAccessibilityEvent(host, event);
            if (!LockPatternView.this.mPatternInProgress) {
                CharSequence contentDescription =
                        LockPatternView.this
                                .getContext()
                                .getText(R.string.lockscreen_access_pattern_area);
                event.setContentDescription(contentDescription);
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfo node) {
            node.setText(getTextForVirtualView(virtualViewId));
            node.setContentDescription(getTextForVirtualView(virtualViewId));
            if (LockPatternView.this.mPatternInProgress) {
                node.setFocusable(true);
                if (isClickable(virtualViewId)) {
                    node.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                    node.setClickable(isClickable(virtualViewId));
                }
            }
            Rect bounds = getBoundsForVirtualView(virtualViewId);
            node.setBoundsInParent(bounds);
        }

        private boolean isClickable(int virtualViewId) {
            if (virtualViewId != Integer.MIN_VALUE) {
                int row = (virtualViewId - 1) / 3;
                int col = (virtualViewId - 1) % 3;
                if (row < 3) {
                    return !LockPatternView.this.mPatternDrawLookup[row][col];
                }
                return false;
            }
            return false;
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(
                int virtualViewId, int action, Bundle arguments) {
            switch (action) {
                case 16:
                    return onItemClicked(virtualViewId);
                default:
                    return false;
            }
        }

        boolean onItemClicked(int index) {
            invalidateVirtualView(index);
            sendEventForVirtualView(index, 1);
            return true;
        }

        private Rect getBoundsForVirtualView(int virtualViewId) {
            int ordinal = virtualViewId - 1;
            Rect bounds = this.mTempRect;
            int row = ordinal / 3;
            int col = ordinal % 3;
            float centerX = LockPatternView.this.getCenterXForColumn(col);
            float centerY = LockPatternView.this.getCenterYForRow(row);
            float cellHitRadius = LockPatternView.this.mDotHitRadius;
            bounds.left = (int) (centerX - cellHitRadius);
            bounds.right = (int) (centerX + cellHitRadius);
            bounds.top = (int) (centerY - cellHitRadius);
            bounds.bottom = (int) (centerY + cellHitRadius);
            return bounds;
        }

        private CharSequence getTextForVirtualView(int virtualViewId) {
            Resources res = LockPatternView.this.getResources();
            return res.getString(
                    R.string.lockscreen_access_pattern_cell_added_verbose,
                    Integer.valueOf(virtualViewId));
        }

        private int getVirtualViewIdForHit(float x, float y) {
            Cell cellHit = LockPatternView.this.detectCellHit(x, y);
            if (cellHit == null) {
                return Integer.MIN_VALUE;
            }
            boolean dotAvailable =
                    LockPatternView.this.mPatternDrawLookup[cellHit.row][cellHit.column];
            int dotId = (cellHit.row * 3) + cellHit.column + 1;
            if (dotAvailable) {
                return dotId;
            }
            return Integer.MIN_VALUE;
        }
    }
}
