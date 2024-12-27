package com.samsung.android.animation;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ListView;
import com.samsung.android.animation.SemSweepListAnimator;

/* loaded from: classes5.dex */
public class SemSweepWaveFilter extends SemAbsSweepAnimationFilter {
    private static final boolean DEBUGGABLE = false;
    private static final int SWIPE_DURATION = 600;
    private static final String TAG = "SemSweepWaveFilter";
    private static final int WAVE_ANIMATION_DURATION = 1300;
    private static final int WAVE_BG_ALPHA = 225;
    private static Interpolator sDecel = new DecelerateInterpolator();
    private ListView mListView;
    private Path mPathDown;
    private Path mPathUp;
    private ValueAnimator waveValueAnimator;
    private final int leftColor = Color.rgb(97, 170, 19);
    private final int middleColor = Color.rgb(12, 92, 126);
    private final int rightColor = Color.rgb(232, 156, 0);
    private int waveBaseColor = Color.rgb(255, 255, 255);
    private RectF mMiddleBlueRect = new RectF();
    private float mGradientWidth = 400.0f;
    private float waveHeight = 0.0f;
    private float waveWidth = 0.0f;
    private float waveControlPointHeight = 0.0f;
    private final Interpolator WAVE_INTERPOLATOR = new LinearInterpolator();
    private float incrementYdown = 0.0f;
    private float incrementYup = 0.0f;
    private Rect mSweepRect = null;
    private Bitmap mSweepBitmap = null;
    private SemSweepListAnimator.OnSweepListener mSweepListener = null;
    private BitmapDrawable mDrawSweepBitmapDrawable = null;
    private View mViewForeground = null;
    private float mSweepProgress = 0.0f;
    private boolean mIsActionMove = false;
    private float mDeltaX = 0.0f;
    private float mEndXOfActionUpAnimator = 0.0f;
    private Paint mBgLeftGreen = initPaintWithAlphaAntiAliasing(this.leftColor);
    private Paint mBgMiddleBlue = initPaintWithAlphaAntiAliasing(this.middleColor);
    private Paint mBgRightYellow = initPaintWithAlphaAntiAliasing(this.rightColor);
    private Paint mBaseWaveColor = new Paint();

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public /* bridge */ /* synthetic */ boolean isAnimationBack() {
        return super.isAnimationBack();
    }

    SemSweepWaveFilter(ListView listView) {
        this.mBaseWaveColor.setColor(this.waveBaseColor);
        this.mListView = listView;
    }

    private Paint initPaintWithAlphaAntiAliasing(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAlpha(225);
        paint.setAntiAlias(true);
        return paint;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void setForegroundView(View viewForeground) {
        this.mViewForeground = viewForeground;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public Rect getBitmapDrawableBound() {
        if (this.mDrawSweepBitmapDrawable != null) {
            return this.mDrawSweepBitmapDrawable.getBounds();
        }
        return null;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void draw(Canvas canvas) {
        if (this.mDrawSweepBitmapDrawable != null) {
            this.mDrawSweepBitmapDrawable.draw(canvas);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDrawWaveEffect(View viewForeground, float deltaX, int position) {
        float sweepProgress = deltaX / viewForeground.getWidth();
        Canvas canvas = drawWaveToBitmapCanvas(viewForeground, sweepProgress);
        if (this.mSweepListener != null && canvas != null && this.mIsActionMove) {
            this.mSweepListener.onSweep(position, sweepProgress, canvas);
        }
        if (this.mDrawSweepBitmapDrawable == null) {
            this.mDrawSweepBitmapDrawable = new BitmapDrawable();
        }
        this.mDrawSweepBitmapDrawable = getBitmapDrawableToSweepBitmap();
        if (this.mDrawSweepBitmapDrawable != null) {
            this.mListView.invalidate(this.mDrawSweepBitmapDrawable.getBounds());
        }
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void doMoveAction(View viewForeground, float deltaX, int position) {
        float sweepProgress = deltaX / viewForeground.getWidth();
        this.mDeltaX = deltaX;
        this.mSweepProgress = sweepProgress;
        this.mIsActionMove = true;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public ValueAnimator createActionUpAnimator(View viewForeground, float adjustedVelocityX, int scaledTouchSlop, float deltaX, boolean isSweepPattern) {
        long duration;
        float endX;
        int width = viewForeground.getWidth();
        float sweepProgress = deltaX / viewForeground.getWidth();
        float deltaXAbs = Math.abs(deltaX);
        if (deltaX > width) {
            deltaX = width;
        }
        if (Math.abs(adjustedVelocityX) > scaledTouchSlop * 6 && isSweepPattern) {
            duration = 600;
            endX = Math.signum(adjustedVelocityX);
        } else if (deltaXAbs > width / 2.0f) {
            duration = 600;
            endX = Math.signum(deltaX);
        } else {
            duration = (int) ((1.0f - (Math.abs(deltaX) / width)) * 600.0f);
            endX = 0.0f;
        }
        this.mEndXOfActionUpAnimator = endX;
        ValueAnimator animator = ValueAnimator.ofFloat(sweepProgress, endX);
        animator.setDuration(duration);
        animator.setInterpolator(sDecel);
        return animator;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public float getEndXOfActionUpAnimator() {
        return this.mEndXOfActionUpAnimator;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void doUpActionWhenAnimationUpdate(int position, float sweepProgress) {
        Canvas canvas = null;
        if (this.mViewForeground != null) {
            canvas = drawWaveToBitmapCanvas(this.mViewForeground, sweepProgress);
        }
        if (this.mSweepListener != null && canvas != null) {
            this.mSweepListener.onSweep(position, sweepProgress, canvas);
        }
        this.mDrawSweepBitmapDrawable = getBitmapDrawableToSweepBitmap();
        if (this.mDrawSweepBitmapDrawable != null) {
            this.mListView.invalidate(this.mDrawSweepBitmapDrawable.getBounds());
        }
    }

    private BitmapDrawable getBitmapDrawableToSweepBitmap() {
        if (this.mSweepBitmap == null) {
            return null;
        }
        BitmapDrawable bd = new BitmapDrawable(this.mListView.getResources(), this.mSweepBitmap);
        bd.setBounds(this.mSweepRect);
        return bd;
    }

    private Canvas drawWaveToBitmapCanvas(View view, float sweepProgress) {
        int viewTop = 0;
        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();
        int viewLeft = view.getLeft();
        View parentView = (View) view.getParent();
        if (parentView != null && (parentView instanceof ViewGroup)) {
            viewTop = parentView instanceof ListView ? view.getTop() : view.getTop() + parentView.getTop();
        }
        this.mSweepRect = new Rect(viewLeft, viewTop, viewWidth, viewTop + viewHeight);
        if (this.mSweepBitmap == null) {
            this.mSweepBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(this.mSweepBitmap);
        Rect rect = new Rect(0, 0, viewWidth, viewHeight);
        drawWave(canvas, rect, sweepProgress);
        return canvas;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void doRefresh() {
        this.mIsActionMove = false;
        removeCachedBitmap();
        cancelRunningAnimator();
    }

    private void removeCachedBitmap() {
        if (this.mDrawSweepBitmapDrawable != null) {
            this.mDrawSweepBitmapDrawable.getBitmap().recycle();
            this.mDrawSweepBitmapDrawable = null;
            this.mSweepBitmap = null;
        }
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void initAnimationFilter(View viewForeground, float deltaX, int position, SemSweepListAnimator.OnSweepListener listener, SemSweepListAnimator.SweepConfiguration sweepConfig) {
        this.mViewForeground = viewForeground;
        initWaveParams(deltaX, position, listener);
    }

    private void initWaveParams(float deltaX, final int itemIndex, SemSweepListAnimator.OnSweepListener listener) {
        View child = this.mListView.getChildAt(itemIndex - this.mListView.getFirstVisiblePosition());
        if (child == null) {
            return;
        }
        this.mSweepListener = listener;
        int itemHeight = child.getHeight();
        this.mPathDown = new Path();
        this.mPathDown.reset();
        this.mPathUp = new Path();
        this.mPathUp.reset();
        this.waveHeight = itemHeight / 2;
        this.waveWidth = itemHeight / 13;
        this.waveControlPointHeight = itemHeight / 4;
        if (this.waveValueAnimator != null) {
            this.waveValueAnimator.cancel();
            this.waveValueAnimator.start();
            return;
        }
        this.waveValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.waveValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.animation.SemSweepWaveFilter.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                SemSweepWaveFilter.this.incrementYdown = SemSweepWaveFilter.this.waveHeight * fraction * 2.0f;
                SemSweepWaveFilter.this.incrementYup = (-fraction) * SemSweepWaveFilter.this.waveHeight * 2.0f;
                SemSweepWaveFilter.this.doDrawWaveEffect(SemSweepWaveFilter.this.mViewForeground, SemSweepWaveFilter.this.mDeltaX, itemIndex);
            }
        });
        this.waveValueAnimator.setRepeatCount(-1);
        this.waveValueAnimator.setRepeatMode(1);
        this.waveValueAnimator.setDuration(1300L);
        this.waveValueAnimator.setInterpolator(this.WAVE_INTERPOLATOR);
        this.waveValueAnimator.start();
    }

    private void cancelRunningAnimator() {
        if (this.waveValueAnimator != null) {
            this.waveValueAnimator.cancel();
        }
    }

    private void drawWave(Canvas canvas, Rect rc, float sweepProgress) {
        int rcTopOffset = rc.top;
        rc.offset(0, -rcTopOffset);
        int listWidth = this.mListView.getWidth();
        canvas.drawRect(rc, this.mBaseWaveColor);
        float shift = (listWidth + this.mGradientWidth) * sweepProgress;
        if (shift > 0.0f) {
            float gradientLeft = shift - this.mGradientWidth;
            float waveCenterX = (this.mGradientWidth / 2.0f) + gradientLeft;
            drawWaveInto(canvas, rc, waveCenterX, false, this.mBgLeftGreen, this.mBgMiddleBlue);
        } else if (shift < 0.0f) {
            float gradientLeft2 = listWidth + shift;
            float waveCenterX2 = (this.mGradientWidth / 2.0f) + gradientLeft2;
            drawWaveInto(canvas, rc, waveCenterX2, true, this.mBgMiddleBlue, this.mBgRightYellow);
        } else {
            this.mMiddleBlueRect.set(rc);
            canvas.drawRect(this.mMiddleBlueRect, this.mBgMiddleBlue);
        }
    }

    private void drawWaveInto(Canvas canvas, Rect rect, float waveCenterX, boolean drawLeftFirst, Paint leftPaint, Paint rightPaint) {
        float startXdown = waveCenterX + (this.waveWidth / 2.0f);
        float startYdown = this.incrementYdown - (this.waveHeight * 2.0f);
        float listWidth = this.mListView.getWidth();
        this.mPathDown.reset();
        this.mPathDown.moveTo(0.0f, startYdown);
        this.mPathDown.lineTo(this.waveWidth + startXdown, startYdown);
        this.mPathDown.cubicTo(this.waveWidth + startXdown, this.waveControlPointHeight + startYdown, startXdown, (this.waveHeight + startYdown) - this.waveControlPointHeight, startXdown, startYdown + this.waveHeight);
        this.mPathDown.cubicTo(startXdown, this.waveControlPointHeight + this.waveHeight + startYdown, startXdown + this.waveWidth, ((this.waveHeight * 2.0f) + startYdown) - this.waveControlPointHeight, startXdown + this.waveWidth, startYdown + (this.waveHeight * 2.0f));
        this.mPathDown.cubicTo(this.waveWidth + startXdown, (this.waveHeight * 2.0f) + startYdown + this.waveControlPointHeight, startXdown, ((this.waveHeight * 3.0f) + startYdown) - this.waveControlPointHeight, startXdown, startYdown + (this.waveHeight * 3.0f));
        this.mPathDown.cubicTo(startXdown, this.waveControlPointHeight + (this.waveHeight * 3.0f) + startYdown, startXdown + this.waveWidth, ((this.waveHeight * 4.0f) + startYdown) - this.waveControlPointHeight, startXdown + this.waveWidth, startYdown + (this.waveHeight * 4.0f));
        this.mPathDown.lineTo(0.0f, (this.waveHeight * 4.0f) + startYdown);
        this.mPathDown.close();
        this.mPathUp.reset();
        this.mPathUp.moveTo(listWidth, startYdown);
        this.mPathUp.lineTo(this.waveWidth + startXdown, startYdown);
        this.mPathUp.cubicTo(this.waveWidth + startXdown, this.waveControlPointHeight + startYdown, startXdown, (this.waveHeight + startYdown) - this.waveControlPointHeight, startXdown, startYdown + this.waveHeight);
        this.mPathUp.cubicTo(startXdown, this.waveControlPointHeight + this.waveHeight + startYdown, startXdown + this.waveWidth, ((this.waveHeight * 2.0f) + startYdown) - this.waveControlPointHeight, startXdown + this.waveWidth, startYdown + (this.waveHeight * 2.0f));
        this.mPathUp.cubicTo(this.waveWidth + startXdown, (this.waveHeight * 2.0f) + startYdown + this.waveControlPointHeight, startXdown, ((this.waveHeight * 3.0f) + startYdown) - this.waveControlPointHeight, startXdown, startYdown + (this.waveHeight * 3.0f));
        this.mPathUp.cubicTo(startXdown, (this.waveHeight * 3.0f) + startYdown + this.waveControlPointHeight, startXdown + this.waveWidth, ((this.waveHeight * 4.0f) + startYdown) - this.waveControlPointHeight, startXdown + this.waveWidth, startYdown + (this.waveHeight * 4.0f));
        this.mPathUp.lineTo(listWidth, (this.waveHeight * 4.0f) + startYdown);
        this.mPathUp.close();
        int saveCount = canvas.save();
        canvas.clipRect(rect);
        if (drawLeftFirst) {
            canvas.drawPath(this.mPathDown, leftPaint);
            canvas.drawPath(this.mPathUp, rightPaint);
        } else {
            canvas.drawPath(this.mPathUp, rightPaint);
            canvas.drawPath(this.mPathDown, leftPaint);
        }
        canvas.restoreToCount(saveCount);
    }
}
