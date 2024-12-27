package com.samsung.android.animation;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ListView;

/* loaded from: classes5.dex */
public class SemSweepTranslationFilter extends SemAbsSweepAnimationFilter {
    private static final int BG_ALPHA = 225;
    private static final int COEFFICIENT_FOR_VELOCITY_ADJUSTMENT = 23;
    private static final boolean DEBUGGABLE = false;
    private static final boolean DEBUGGABLE_LOW = true;
    private static final int DIRECTION_LEFT_TO_RIGHT = 0;
    private static final int DIRECTION_RIGHT_TO_LEFT = 1;
    private static final int SWEEP_TEXT_PADDING_DP = 16;
    private static final int SWIPE_DURATION = 600;
    private static final String TAG = "SemSweepTranslationFilter";
    private int SWEEP_TEXT_PADDING_PX;
    private Paint mBgLeftToRight;
    private Paint mBgRightToLeft;
    private Context mContext;
    private ListView mListView;
    private static Interpolator sDecel = new DecelerateInterpolator();
    private static int VELOCITY_UNITS = 1000;
    private final int leftColor = Color.parseColor("#6ebd52");
    private final int rightColor = Color.parseColor("#56c0e5");
    private Rect mSweepRect = null;
    private Bitmap mSweepBitmap = null;
    private SemSweepListAnimator.OnSweepListener mSweepListener = null;
    private BitmapDrawable mDrawSweepBitmapDrawable = null;
    private View mViewForeground = null;
    private float mEndXOfActionUpAnimator = 0.0f;
    private SemSweepListAnimator.SweepConfiguration mSweepConfiguration = null;
    private int mTextPaintSize = 80;
    private int mSweepDirection = -1;
    private boolean mSweepRectFullyDrawn = false;
    private int mViewTop = 0;
    private Paint mTextPaint = initPaintWithAlphaAntiAliasing(Color.parseColor("#ffffff"));

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public /* bridge */ /* synthetic */ boolean isAnimationBack() {
        return super.isAnimationBack();
    }

    SemSweepTranslationFilter(ListView listView, Context context) {
        this.mBgLeftToRight = null;
        this.mBgRightToLeft = null;
        this.mContext = context;
        this.mBgLeftToRight = initPaintWithAlphaAntiAliasing(this.leftColor);
        this.mBgRightToLeft = initPaintWithAlphaAntiAliasing(this.rightColor);
        this.mTextPaint.setTextSize(this.mTextPaintSize);
        this.mListView = listView;
        this.SWEEP_TEXT_PADDING_PX = convertDipToPixels(this.mContext, 16);
    }

    private Paint initPaintWithAlphaAntiAliasing(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        return paint;
    }

    private static int convertDipToPixels(Context context, int dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dip * density);
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

    public BitmapDrawable getSweepBitmapDrawable() {
        Log.d(
                TAG,
                "getSweepBitmapDrawable : mDrawSweepBitmapDrawable = "
                        + this.mDrawSweepBitmapDrawable);
        return this.mDrawSweepBitmapDrawable;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void draw(Canvas canvas) {
        if (this.mDrawSweepBitmapDrawable != null) {
            this.mDrawSweepBitmapDrawable.draw(canvas);
        }
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void initAnimationFilter(
            View viewForeground,
            float deltaX,
            int position,
            SemSweepListAnimator.OnSweepListener listener,
            SemSweepListAnimator.SweepConfiguration sweepConfig) {
        this.mSweepListener = listener;
        this.mViewForeground = viewForeground;
        this.mSweepConfiguration = sweepConfig;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void doRefresh() {
        if (this.mViewForeground != null) {
            this.mViewForeground.setVisibility(0);
            this.mViewForeground.setTranslationX(0.0f);
            this.mViewForeground.setAlpha(1.0f);
        }
        this.mIsAnimationBack = false;
        removeCachedBitmap();
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void doMoveAction(View viewForeground, float deltaX, int position) {
        float sweepProgress = deltaX / viewForeground.getWidth();
        float deltaXAbs = Math.abs(deltaX);
        this.mViewForeground = viewForeground;
        Canvas canvas = drawRectToBitmapCanvas(viewForeground, deltaX, sweepProgress);
        viewForeground.setTranslationX(deltaX);
        float alphaForeground = 1.0f - (deltaXAbs / viewForeground.getWidth());
        viewForeground.setAlpha(alphaForeground);
        if (this.mSweepListener != null && canvas != null) {
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

    private Canvas drawRectToBitmapCanvas(View view, float deltaX, float sweepProgress) {
        int viewTop;
        if (this.mSweepConfiguration == null) {
            return null;
        }
        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();
        int viewLeft = view.getLeft();
        View parentView = (View) view.getParent();
        if (parentView != null && (parentView instanceof ViewGroup)) {
            if (parentView instanceof ListView) {
                int viewTop2 = view.getTop();
                viewTop = viewTop2;
            } else {
                int viewTop3 = view.getTop() + parentView.getTop();
                viewTop = viewTop3;
            }
        } else {
            viewTop = 0;
        }
        this.mViewTop = viewTop;
        this.mSweepRect = new Rect(viewLeft, viewTop, viewWidth, viewTop + viewHeight);
        if (this.mSweepBitmap == null) {
            this.mSweepBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(this.mSweepBitmap);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        float deltaXAbs = Math.abs(deltaX);
        float rectAlpha = (deltaXAbs / view.getWidth()) * 255.0f;
        if (sweepProgress > 0.0f) {
            this.mSweepDirection = 0;
            Drawable d = this.mSweepConfiguration.drawableLeftToRight;
            if (d == null) {
                Log.d(TAG, "mSweepConfiguration.drawableLeftToRight is null");
                return null;
            }
            Rect drawableBound = d.getBounds();
            int width = drawableBound.width();
            int height = drawableBound.height();
            Rect clipLeftRect = new Rect(0, 0, (int) deltaX, viewHeight);
            Rect iconDrawableRect =
                    new Rect(
                            this.mSweepConfiguration.drawablePadding,
                            0,
                            width + this.mSweepConfiguration.drawablePadding,
                            height);
            iconDrawableRect.offset(0, (viewHeight - height) / 2);
            if (this.mSweepConfiguration.backgroundColorLeftToRight != 0) {
                this.mBgLeftToRight.setColor(this.mSweepConfiguration.backgroundColorLeftToRight);
            }
            drawRectInto(
                    canvas,
                    clipLeftRect,
                    this.mBgLeftToRight,
                    255,
                    iconDrawableRect,
                    this.mSweepConfiguration.textLeftToRight,
                    this.mSweepConfiguration.textSize,
                    this.mSweepConfiguration.drawableLeftToRight);
            Rect clipRightRect = new Rect((int) deltaX, 0, viewWidth, viewHeight);
            drawRectInto(
                    canvas,
                    clipRightRect,
                    this.mBgLeftToRight,
                    (int) rectAlpha,
                    iconDrawableRect,
                    this.mSweepConfiguration.textLeftToRight,
                    this.mSweepConfiguration.textSize,
                    null);
            return canvas;
        }
        if (sweepProgress >= 0.0f) {
            return canvas;
        }
        this.mSweepDirection = 1;
        Drawable d2 = this.mSweepConfiguration.drawableRightToLeft;
        if (d2 == null) {
            Log.d(TAG, "mSweepConfiguration.drawableRightToLeft is null");
            return null;
        }
        Rect drawableBound2 = d2.getBounds();
        int width2 = drawableBound2.width();
        int height2 = drawableBound2.height();
        Rect clipRightRect2 = new Rect(viewWidth - ((int) deltaXAbs), 0, viewWidth, viewHeight);
        Rect iconDrawableRect2 =
                new Rect(
                        (viewWidth - width2) - this.mSweepConfiguration.drawablePadding,
                        0,
                        viewWidth - this.mSweepConfiguration.drawablePadding,
                        height2);
        iconDrawableRect2.offset(0, (viewHeight - height2) / 2);
        if (this.mSweepConfiguration != null
                && this.mSweepConfiguration.backgroundColorRightToLeft != 0) {
            this.mBgRightToLeft.setColor(this.mSweepConfiguration.backgroundColorRightToLeft);
        }
        drawRectInto(
                canvas,
                clipRightRect2,
                this.mBgRightToLeft,
                255,
                iconDrawableRect2,
                this.mSweepConfiguration.textRightToLeft,
                this.mSweepConfiguration.textSize,
                this.mSweepConfiguration.drawableRightToLeft);
        Rect clipLeftRect2 = new Rect(0, 0, viewWidth - ((int) deltaXAbs), viewHeight);
        drawRectInto(
                canvas,
                clipLeftRect2,
                this.mBgRightToLeft,
                (int) rectAlpha,
                iconDrawableRect2,
                this.mSweepConfiguration.textRightToLeft,
                this.mSweepConfiguration.textSize,
                null);
        return canvas;
    }

    private void drawRectInto(
            Canvas canvas,
            Rect clipRect,
            Paint rectPaint,
            int rectAlpha,
            Rect iconDrawableRect,
            String text,
            float textSize,
            Drawable iconDrawable) {
        canvas.save();
        rectPaint.setAlpha(rectAlpha);
        this.mTextPaint.setAlpha(rectAlpha);
        if (textSize != 0.0f) {
            this.mTextPaint.setTextSize(textSize);
        } else {
            this.mTextPaint.setTextSize(this.mTextPaintSize);
        }
        canvas.clipRect(clipRect);
        canvas.drawRect(clipRect, rectPaint);
        if (iconDrawable != null) {
            if (iconDrawableRect != null) {
                iconDrawable.setBounds(iconDrawableRect);
            }
            iconDrawable.draw(canvas);
        }
        drawSweepText(canvas, this.mTextPaint, text, iconDrawableRect);
        canvas.restore();
    }

    private void drawSweepText(Canvas canvas, Paint paint, String text, Rect iconDrawableRect) {
        int cHeight = canvas.getHeight();
        canvas.getWidth();
        Rect r = new Rect();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        Paint.FontMetrics fm = paint.getFontMetrics();
        float fontHeight = Math.abs(fm.top - fm.bottom);
        float x = 0.0f;
        if (iconDrawableRect != null) {
            if (this.mSweepDirection == 1) {
                x = (iconDrawableRect.left - this.mSweepConfiguration.drawablePadding) - r.right;
            } else if (this.mSweepDirection == 0) {
                x = iconDrawableRect.right + this.mSweepConfiguration.drawablePadding;
            }
        }
        if (this.mSweepRectFullyDrawn) {
            cHeight = this.mViewForeground.getHeight();
        }
        float y = ((cHeight / 2.0f) + (fontHeight / 2.0f)) - fm.bottom;
        if (this.mSweepRectFullyDrawn) {
            y += this.mViewTop;
            this.mSweepRectFullyDrawn = false;
        }
        canvas.drawText(text, x, y, paint);
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public ValueAnimator createActionUpAnimator(
            View viewForeground,
            float adjustedVelocityX,
            int scaledTouchSlop,
            float deltaX,
            boolean isSweepPattern) {
        long duration;
        float endX;
        int remainingDistance;
        float translationX = viewForeground.getTranslationX();
        int width = viewForeground.getWidth();
        float deltaXAbs = Math.abs(deltaX);
        if (translationX > width) {
            translationX = width;
        }
        Log.d(
                TAG,
                "SemSweepTranslationFilter : createActionUpAnimator() : Math.abs(adjustedVelocityX)"
                    + " = "
                        + Math.abs(adjustedVelocityX));
        Log.d(
                TAG,
                "SemSweepTranslationFilter : createActionUpAnimator() : scaledTouchSlop * 23 = "
                        + (scaledTouchSlop * 23));
        if (Math.abs(adjustedVelocityX) > scaledTouchSlop * 23 && isSweepPattern) {
            Log.d(
                    TAG,
                    "SemSweepTranslationFilter : createActionUpAnimator() : kick in animation with"
                        + " given velocity, point #1");
            int abs = width - ((int) Math.abs(translationX));
            duration = (int) ((1.0f - (Math.abs(translationX) / width)) * 600.0f);
            if (duration > 600) {
                duration = 600;
            }
            endX = Math.signum(adjustedVelocityX) * width;
            remainingDistance = 0;
        } else {
            float endAlpha = width;
            if (deltaXAbs > endAlpha / 2.0f) {
                Log.d(
                        TAG,
                        "SemSweepTranslationFilter : createActionUpAnimator() : Greater than a half"
                            + " of the width, point #2");
                duration = (int) ((1.0f - (Math.abs(translationX) / width)) * 600.0f);
                endX = width * Math.signum(deltaX);
                remainingDistance = 0;
            } else {
                Log.d(
                        TAG,
                        "SemSweepTranslationFilter : createActionUpAnimator() : Not far enough -"
                            + " animate it back, point #3");
                duration = (int) ((Math.abs(translationX) * 600.0f) / width);
                endX = 0.0f;
                remainingDistance = 1065353216;
                this.mIsAnimationBack = true;
            }
        }
        if (duration < 0) {
            duration = 600;
        }
        this.mEndXOfActionUpAnimator = endX;
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat(View.ALPHA, remainingDistance);
        PropertyValuesHolder pvhTranslationX =
                PropertyValuesHolder.ofFloat(View.TRANSLATION_X, endX);
        ObjectAnimator animator =
                ObjectAnimator.ofPropertyValuesHolder(viewForeground, pvhAlpha, pvhTranslationX);
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
            float deltaX = sweepProgress * this.mViewForeground.getWidth();
            canvas = drawRectToBitmapCanvas(this.mViewForeground, deltaX, sweepProgress);
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

    private void drawTextToCenter(Canvas canvas, Paint paint, String text) {
        int cHeight = canvas.getHeight();
        int cWidth = canvas.getWidth();
        Rect r = new Rect();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = ((cWidth / 2.0f) - (r.width() / 2.0f)) - r.left;
        float y = ((cHeight / 2.0f) + (r.height() / 2.0f)) - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

    public void removeCachedBitmap() {
        if (this.mDrawSweepBitmapDrawable != null) {
            this.mDrawSweepBitmapDrawable.getBitmap().recycle();
            this.mDrawSweepBitmapDrawable = null;
            this.mSweepBitmap = null;
        }
    }
}
