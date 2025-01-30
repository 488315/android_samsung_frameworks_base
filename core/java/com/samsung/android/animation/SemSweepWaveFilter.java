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
    private final Interpolator WAVE_INTERPOLATOR;
    private float incrementYdown;
    private float incrementYup;
    private final int leftColor;
    private Paint mBaseWaveColor;
    private Paint mBgLeftGreen;
    private Paint mBgMiddleBlue;
    private Paint mBgRightYellow;
    private float mDeltaX;
    private BitmapDrawable mDrawSweepBitmapDrawable;
    private float mEndXOfActionUpAnimator;
    private float mGradientWidth;
    private boolean mIsActionMove;
    private ListView mListView;
    private RectF mMiddleBlueRect;
    private Path mPathDown;
    private Path mPathUp;
    private Bitmap mSweepBitmap;
    private SemSweepListAnimator.OnSweepListener mSweepListener;
    private float mSweepProgress;
    private Rect mSweepRect;
    private View mViewForeground;
    private final int middleColor;
    private final int rightColor;
    private int waveBaseColor;
    private float waveControlPointHeight;
    private float waveHeight;
    private ValueAnimator waveValueAnimator;
    private float waveWidth;

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public /* bridge */ /* synthetic */ boolean isAnimationBack() {
        return super.isAnimationBack();
    }

    SemSweepWaveFilter(ListView listView) {
        int rgb = Color.rgb(97, 170, 19);
        this.leftColor = rgb;
        int rgb2 = Color.rgb(12, 92, 126);
        this.middleColor = rgb2;
        int rgb3 = Color.rgb(232, 156, 0);
        this.rightColor = rgb3;
        this.waveBaseColor = Color.rgb(255, 255, 255);
        this.mMiddleBlueRect = new RectF();
        this.mGradientWidth = 400.0f;
        this.waveHeight = 0.0f;
        this.waveWidth = 0.0f;
        this.waveControlPointHeight = 0.0f;
        this.WAVE_INTERPOLATOR = new LinearInterpolator();
        this.incrementYdown = 0.0f;
        this.incrementYup = 0.0f;
        this.mSweepRect = null;
        this.mSweepBitmap = null;
        this.mSweepListener = null;
        this.mDrawSweepBitmapDrawable = null;
        this.mViewForeground = null;
        this.mSweepProgress = 0.0f;
        this.mIsActionMove = false;
        this.mDeltaX = 0.0f;
        this.mEndXOfActionUpAnimator = 0.0f;
        this.mBgLeftGreen = initPaintWithAlphaAntiAliasing(rgb);
        this.mBgMiddleBlue = initPaintWithAlphaAntiAliasing(rgb2);
        this.mBgRightYellow = initPaintWithAlphaAntiAliasing(rgb3);
        Paint paint = new Paint();
        this.mBaseWaveColor = paint;
        paint.setColor(this.waveBaseColor);
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
        new Rect();
        BitmapDrawable bitmapDrawable = this.mDrawSweepBitmapDrawable;
        if (bitmapDrawable != null) {
            Rect rect = bitmapDrawable.getBounds();
            return rect;
        }
        return null;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void draw(Canvas canvas) {
        BitmapDrawable bitmapDrawable = this.mDrawSweepBitmapDrawable;
        if (bitmapDrawable != null) {
            bitmapDrawable.draw(canvas);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDrawWaveEffect(View viewForeground, float deltaX, int position) {
        float sweepProgress = deltaX / viewForeground.getWidth();
        Canvas canvas = drawWaveToBitmapCanvas(viewForeground, sweepProgress);
        SemSweepListAnimator.OnSweepListener onSweepListener = this.mSweepListener;
        if (onSweepListener != null && canvas != null && this.mIsActionMove) {
            onSweepListener.onSweep(position, sweepProgress, canvas);
        }
        if (this.mDrawSweepBitmapDrawable == null) {
            this.mDrawSweepBitmapDrawable = new BitmapDrawable();
        }
        BitmapDrawable bitmapDrawableToSweepBitmap = getBitmapDrawableToSweepBitmap();
        this.mDrawSweepBitmapDrawable = bitmapDrawableToSweepBitmap;
        if (bitmapDrawableToSweepBitmap != null) {
            this.mListView.invalidate(bitmapDrawableToSweepBitmap.getBounds());
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
        View view = this.mViewForeground;
        if (view != null) {
            canvas = drawWaveToBitmapCanvas(view, sweepProgress);
        }
        SemSweepListAnimator.OnSweepListener onSweepListener = this.mSweepListener;
        if (onSweepListener != null && canvas != null) {
            onSweepListener.onSweep(position, sweepProgress, canvas);
        }
        BitmapDrawable bitmapDrawableToSweepBitmap = getBitmapDrawableToSweepBitmap();
        this.mDrawSweepBitmapDrawable = bitmapDrawableToSweepBitmap;
        if (bitmapDrawableToSweepBitmap != null) {
            this.mListView.invalidate(bitmapDrawableToSweepBitmap.getBounds());
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
        BitmapDrawable bitmapDrawable = this.mDrawSweepBitmapDrawable;
        if (bitmapDrawable != null) {
            bitmapDrawable.getBitmap().recycle();
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
        ListView listView = this.mListView;
        View child = listView.getChildAt(itemIndex - listView.getFirstVisiblePosition());
        if (child == null) {
            return;
        }
        this.mSweepListener = listener;
        int itemHeight = child.getHeight();
        Path path = new Path();
        this.mPathDown = path;
        path.reset();
        Path path2 = new Path();
        this.mPathUp = path2;
        path2.reset();
        this.waveHeight = itemHeight / 2;
        this.waveWidth = itemHeight / 13;
        this.waveControlPointHeight = itemHeight / 4;
        ValueAnimator valueAnimator = this.waveValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.waveValueAnimator.start();
            return;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.waveValueAnimator = ofFloat;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.animation.SemSweepWaveFilter.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                SemSweepWaveFilter semSweepWaveFilter = SemSweepWaveFilter.this;
                semSweepWaveFilter.incrementYdown = semSweepWaveFilter.waveHeight * fraction * 2.0f;
                SemSweepWaveFilter semSweepWaveFilter2 = SemSweepWaveFilter.this;
                semSweepWaveFilter2.incrementYup = (-fraction) * semSweepWaveFilter2.waveHeight * 2.0f;
                SemSweepWaveFilter semSweepWaveFilter3 = SemSweepWaveFilter.this;
                semSweepWaveFilter3.doDrawWaveEffect(semSweepWaveFilter3.mViewForeground, SemSweepWaveFilter.this.mDeltaX, itemIndex);
            }
        });
        this.waveValueAnimator.setRepeatCount(-1);
        this.waveValueAnimator.setRepeatMode(1);
        this.waveValueAnimator.setDuration(1300L);
        this.waveValueAnimator.setInterpolator(this.WAVE_INTERPOLATOR);
        this.waveValueAnimator.start();
    }

    private void cancelRunningAnimator() {
        ValueAnimator valueAnimator = this.waveValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    private void drawWave(Canvas canvas, Rect rc, float sweepProgress) {
        int rcTopOffset = rc.top;
        rc.offset(0, -rcTopOffset);
        int listWidth = this.mListView.getWidth();
        canvas.drawRect(rc, this.mBaseWaveColor);
        float f = this.mGradientWidth;
        float shift = (listWidth + f) * sweepProgress;
        if (shift > 0.0f) {
            float gradientLeft = shift - f;
            float waveCenterX = (f / 2.0f) + gradientLeft;
            drawWaveInto(canvas, rc, waveCenterX, false, this.mBgLeftGreen, this.mBgMiddleBlue);
        } else if (shift < 0.0f) {
            float gradientLeft2 = listWidth + shift;
            float waveCenterX2 = (f / 2.0f) + gradientLeft2;
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
        Path path = this.mPathDown;
        float f = this.waveWidth + startXdown;
        float f2 = this.waveControlPointHeight;
        float f3 = this.waveHeight;
        path.cubicTo(f, startYdown + f2, startXdown, (startYdown + f3) - f2, startXdown, startYdown + f3);
        Path path2 = this.mPathDown;
        float f4 = this.waveHeight;
        float f5 = this.waveControlPointHeight;
        float f6 = this.waveWidth;
        path2.cubicTo(startXdown, startYdown + f4 + f5, startXdown + f6, ((f4 * 2.0f) + startYdown) - f5, startXdown + f6, startYdown + (f4 * 2.0f));
        Path path3 = this.mPathDown;
        float f7 = this.waveWidth + startXdown;
        float f8 = this.waveHeight;
        float f9 = this.waveControlPointHeight;
        path3.cubicTo(f7, (f8 * 2.0f) + startYdown + f9, startXdown, ((f8 * 3.0f) + startYdown) - f9, startXdown, startYdown + (f8 * 3.0f));
        Path path4 = this.mPathDown;
        float f10 = this.waveHeight;
        float f11 = this.waveControlPointHeight;
        float f12 = this.waveWidth;
        path4.cubicTo(startXdown, (f10 * 3.0f) + startYdown + f11, startXdown + f12, ((f10 * 4.0f) + startYdown) - f11, startXdown + f12, startYdown + (f10 * 4.0f));
        this.mPathDown.lineTo(0.0f, (this.waveHeight * 4.0f) + startYdown);
        this.mPathDown.close();
        this.mPathUp.reset();
        this.mPathUp.moveTo(listWidth, startYdown);
        this.mPathUp.lineTo(this.waveWidth + startXdown, startYdown);
        Path path5 = this.mPathUp;
        float f13 = this.waveWidth + startXdown;
        float f14 = this.waveControlPointHeight;
        float f15 = this.waveHeight;
        path5.cubicTo(f13, startYdown + f14, startXdown, (startYdown + f15) - f14, startXdown, startYdown + f15);
        Path path6 = this.mPathUp;
        float f16 = this.waveHeight;
        float f17 = this.waveControlPointHeight;
        float f18 = this.waveWidth;
        path6.cubicTo(startXdown, startYdown + f16 + f17, startXdown + f18, ((f16 * 2.0f) + startYdown) - f17, startXdown + f18, startYdown + (f16 * 2.0f));
        Path path7 = this.mPathUp;
        float f19 = this.waveWidth + startXdown;
        float f20 = this.waveHeight;
        float f21 = this.waveControlPointHeight;
        path7.cubicTo(f19, (2.0f * f20) + startYdown + f21, startXdown, ((f20 * 3.0f) + startYdown) - f21, startXdown, startYdown + (f20 * 3.0f));
        Path path8 = this.mPathUp;
        float f22 = this.waveHeight;
        float f23 = this.waveControlPointHeight;
        float f24 = this.waveWidth;
        path8.cubicTo(startXdown, startYdown + (3.0f * f22) + f23, startXdown + f24, ((f22 * 4.0f) + startYdown) - f23, startXdown + f24, (f22 * 4.0f) + startYdown);
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
