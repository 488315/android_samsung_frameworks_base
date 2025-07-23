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

/* loaded from: classes6.dex */
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

    private Paint initPaintWithAlphaAntiAliasing(int i) {
        Paint paint = new Paint();
        paint.setColor(i);
        paint.setAlpha(225);
        paint.setAntiAlias(true);
        return paint;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void setForegroundView(View view) {
        this.mViewForeground = view;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public Rect getBitmapDrawableBound() {
        BitmapDrawable bitmapDrawable = this.mDrawSweepBitmapDrawable;
        if (bitmapDrawable != null) {
            return bitmapDrawable.getBounds();
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
    public void doDrawWaveEffect(View view, float f, int i) {
        float width = f / view.getWidth();
        Canvas drawWaveToBitmapCanvas = drawWaveToBitmapCanvas(view, width);
        SemSweepListAnimator.OnSweepListener onSweepListener = this.mSweepListener;
        if (onSweepListener != null && drawWaveToBitmapCanvas != null && this.mIsActionMove) {
            onSweepListener.onSweep(i, width, drawWaveToBitmapCanvas);
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
    public void doMoveAction(View view, float f, int i) {
        this.mDeltaX = f;
        this.mSweepProgress = f / view.getWidth();
        this.mIsActionMove = true;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public ValueAnimator createActionUpAnimator(View view, float f, int i, float f2, boolean z) {
        float f3;
        int width = view.getWidth();
        float width2 = f2 / view.getWidth();
        float abs = Math.abs(f2);
        float f4 = width;
        if (f2 > f4) {
            f2 = f4;
        }
        long j = 600;
        if (Math.abs(f) > i * 6 && z) {
            f3 = Math.signum(f);
        } else if (abs > f4 / 2.0f) {
            f3 = Math.signum(f2);
        } else {
            j = (int) ((1.0f - (Math.abs(f2) / f4)) * 600.0f);
            f3 = 0.0f;
        }
        this.mEndXOfActionUpAnimator = f3;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(width2, f3);
        ofFloat.setDuration(j);
        ofFloat.setInterpolator(sDecel);
        return ofFloat;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public float getEndXOfActionUpAnimator() {
        return this.mEndXOfActionUpAnimator;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void doUpActionWhenAnimationUpdate(int i, float f) {
        View view = this.mViewForeground;
        Canvas drawWaveToBitmapCanvas = view != null ? drawWaveToBitmapCanvas(view, f) : null;
        SemSweepListAnimator.OnSweepListener onSweepListener = this.mSweepListener;
        if (onSweepListener != null && drawWaveToBitmapCanvas != null) {
            onSweepListener.onSweep(i, f, drawWaveToBitmapCanvas);
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
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mListView.getResources(), this.mSweepBitmap);
        bitmapDrawable.setBounds(this.mSweepRect);
        return bitmapDrawable;
    }

    private Canvas drawWaveToBitmapCanvas(View view, float f) {
        int i;
        int width = view.getWidth();
        int height = view.getHeight();
        int left = view.getLeft();
        View view2 = (View) view.getParent();
        if (view2 == null || !(view2 instanceof ViewGroup)) {
            i = 0;
        } else if (view2 instanceof ListView) {
            i = view.getTop();
        } else {
            i = view.getTop() + view2.getTop();
        }
        this.mSweepRect = new Rect(left, i, width, i + height);
        if (this.mSweepBitmap == null) {
            this.mSweepBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(this.mSweepBitmap);
        drawWave(canvas, new Rect(0, 0, width, height), f);
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
    public void initAnimationFilter(View view, float f, int i, SemSweepListAnimator.OnSweepListener onSweepListener, SemSweepListAnimator.SweepConfiguration sweepConfiguration) {
        this.mViewForeground = view;
        initWaveParams(f, i, onSweepListener);
    }

    private void initWaveParams(float f, final int i, SemSweepListAnimator.OnSweepListener onSweepListener) {
        ListView listView = this.mListView;
        View childAt = listView.getChildAt(i - listView.getFirstVisiblePosition());
        if (childAt == null) {
            return;
        }
        this.mSweepListener = onSweepListener;
        int height = childAt.getHeight();
        Path path = new Path();
        this.mPathDown = path;
        path.reset();
        Path path2 = new Path();
        this.mPathUp = path2;
        path2.reset();
        this.waveHeight = height / 2;
        this.waveWidth = height / 13;
        this.waveControlPointHeight = height / 4;
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
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                float animatedFraction = valueAnimator2.getAnimatedFraction();
                SemSweepWaveFilter semSweepWaveFilter = SemSweepWaveFilter.this;
                semSweepWaveFilter.incrementYdown = semSweepWaveFilter.waveHeight * animatedFraction * 2.0f;
                SemSweepWaveFilter semSweepWaveFilter2 = SemSweepWaveFilter.this;
                semSweepWaveFilter2.incrementYup = (-animatedFraction) * semSweepWaveFilter2.waveHeight * 2.0f;
                SemSweepWaveFilter semSweepWaveFilter3 = SemSweepWaveFilter.this;
                semSweepWaveFilter3.doDrawWaveEffect(semSweepWaveFilter3.mViewForeground, SemSweepWaveFilter.this.mDeltaX, i);
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

    private void drawWave(Canvas canvas, Rect rect, float f) {
        rect.offset(0, -rect.top);
        int width = this.mListView.getWidth();
        canvas.drawRect(rect, this.mBaseWaveColor);
        float f2 = width;
        float f3 = this.mGradientWidth;
        float f4 = (f2 + f3) * f;
        if (f4 > 0.0f) {
            drawWaveInto(canvas, rect, (f4 - f3) + (f3 / 2.0f), false, this.mBgLeftGreen, this.mBgMiddleBlue);
        } else if (f4 < 0.0f) {
            drawWaveInto(canvas, rect, f2 + f4 + (f3 / 2.0f), true, this.mBgMiddleBlue, this.mBgRightYellow);
        } else {
            this.mMiddleBlueRect.set(rect);
            canvas.drawRect(this.mMiddleBlueRect, this.mBgMiddleBlue);
        }
    }

    private void drawWaveInto(Canvas canvas, Rect rect, float f, boolean z, Paint paint, Paint paint2) {
        float f2 = f + (this.waveWidth / 2.0f);
        float f3 = this.incrementYdown - (this.waveHeight * 2.0f);
        float width = this.mListView.getWidth();
        this.mPathDown.reset();
        this.mPathDown.moveTo(0.0f, f3);
        this.mPathDown.lineTo(this.waveWidth + f2, f3);
        Path path = this.mPathDown;
        float f4 = this.waveWidth + f2;
        float f5 = this.waveControlPointHeight;
        float f6 = this.waveHeight;
        path.cubicTo(f4, f3 + f5, f2, (f3 + f6) - f5, f2, f3 + f6);
        Path path2 = this.mPathDown;
        float f7 = this.waveHeight;
        float f8 = this.waveControlPointHeight;
        float f9 = this.waveWidth;
        path2.cubicTo(f2, f3 + f7 + f8, f2 + f9, ((f7 * 2.0f) + f3) - f8, f9 + f2, (f7 * 2.0f) + f3);
        Path path3 = this.mPathDown;
        float f10 = this.waveWidth + f2;
        float f11 = this.waveHeight;
        float f12 = this.waveControlPointHeight;
        path3.cubicTo(f10, (f11 * 2.0f) + f3 + f12, f2, ((f11 * 3.0f) + f3) - f12, f2, (f11 * 3.0f) + f3);
        Path path4 = this.mPathDown;
        float f13 = this.waveHeight;
        float f14 = this.waveControlPointHeight;
        float f15 = this.waveWidth;
        path4.cubicTo(f2, (f13 * 3.0f) + f3 + f14, f2 + f15, (f3 + (f13 * 4.0f)) - f14, f15 + f2, (f13 * 4.0f) + f3);
        this.mPathDown.lineTo(0.0f, (this.waveHeight * 4.0f) + f3);
        this.mPathDown.close();
        this.mPathUp.reset();
        this.mPathUp.moveTo(width, f3);
        this.mPathUp.lineTo(this.waveWidth + f2, f3);
        Path path5 = this.mPathUp;
        float f16 = this.waveWidth + f2;
        float f17 = this.waveControlPointHeight;
        float f18 = this.waveHeight;
        path5.cubicTo(f16, f3 + f17, f2, (f3 + f18) - f17, f2, f3 + f18);
        Path path6 = this.mPathUp;
        float f19 = this.waveHeight;
        float f20 = this.waveControlPointHeight;
        float f21 = this.waveWidth;
        path6.cubicTo(f2, f3 + f19 + f20, f2 + f21, ((f19 * 2.0f) + f3) - f20, f21 + f2, (f19 * 2.0f) + f3);
        Path path7 = this.mPathUp;
        float f22 = this.waveWidth + f2;
        float f23 = this.waveHeight;
        float f24 = this.waveControlPointHeight;
        path7.cubicTo(f22, (2.0f * f23) + f3 + f24, f2, ((f23 * 3.0f) + f3) - f24, f2, f3 + (f23 * 3.0f));
        Path path8 = this.mPathUp;
        float f25 = this.waveHeight;
        float f26 = this.waveControlPointHeight;
        float f27 = this.waveWidth;
        path8.cubicTo(f2, (3.0f * f25) + f3 + f26, f2 + f27, ((f25 * 4.0f) + f3) - f26, f2 + f27, (f25 * 4.0f) + f3);
        this.mPathUp.lineTo(width, f3 + (this.waveHeight * 4.0f));
        this.mPathUp.close();
        int save = canvas.save();
        canvas.clipRect(rect);
        if (z) {
            canvas.drawPath(this.mPathDown, paint);
            canvas.drawPath(this.mPathUp, paint2);
        } else {
            canvas.drawPath(this.mPathUp, paint2);
            canvas.drawPath(this.mPathDown, paint);
        }
        canvas.restoreToCount(save);
    }
}
