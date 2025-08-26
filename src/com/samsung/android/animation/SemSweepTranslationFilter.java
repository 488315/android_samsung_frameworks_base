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
import com.samsung.android.animation.SemSweepListAnimator;

/* loaded from: classes6.dex */
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
    private final int leftColor;
    private Paint mBgLeftToRight;
    private Paint mBgRightToLeft;
    private Context mContext;
    private BitmapDrawable mDrawSweepBitmapDrawable;
    private float mEndXOfActionUpAnimator;
    private ListView mListView;
    private Bitmap mSweepBitmap;
    private SemSweepListAnimator.SweepConfiguration mSweepConfiguration;
    private int mSweepDirection;
    private SemSweepListAnimator.OnSweepListener mSweepListener;
    private Rect mSweepRect;
    private boolean mSweepRectFullyDrawn;
    private Paint mTextPaint;
    private int mTextPaintSize;
    private View mViewForeground;
    private int mViewTop;
    private final int rightColor;
    private static Interpolator sDecel = new DecelerateInterpolator();
    private static int VELOCITY_UNITS = 1000;

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public /* bridge */ /* synthetic */ boolean isAnimationBack() {
        return super.isAnimationBack();
    }

    SemSweepTranslationFilter(ListView listView, Context context) throws NumberFormatException {
        int color = Color.parseColor("#6ebd52");
        this.leftColor = color;
        int color2 = Color.parseColor("#56c0e5");
        this.rightColor = color2;
        this.mBgLeftToRight = null;
        this.mBgRightToLeft = null;
        this.mSweepRect = null;
        this.mSweepBitmap = null;
        this.mSweepListener = null;
        this.mDrawSweepBitmapDrawable = null;
        this.mViewForeground = null;
        this.mEndXOfActionUpAnimator = 0.0f;
        this.mSweepConfiguration = null;
        this.mTextPaintSize = 80;
        this.mSweepDirection = -1;
        this.mSweepRectFullyDrawn = false;
        this.mViewTop = 0;
        this.mContext = context;
        this.mBgLeftToRight = initPaintWithAlphaAntiAliasing(color);
        this.mBgRightToLeft = initPaintWithAlphaAntiAliasing(color2);
        Paint paintInitPaintWithAlphaAntiAliasing = initPaintWithAlphaAntiAliasing(Color.parseColor("#ffffff"));
        this.mTextPaint = paintInitPaintWithAlphaAntiAliasing;
        paintInitPaintWithAlphaAntiAliasing.setTextSize(this.mTextPaintSize);
        this.mListView = listView;
        this.SWEEP_TEXT_PADDING_PX = convertDipToPixels(this.mContext, 16);
    }

    private Paint initPaintWithAlphaAntiAliasing(int i) {
        Paint paint = new Paint();
        paint.setColor(i);
        paint.setAntiAlias(true);
        return paint;
    }

    private static int convertDipToPixels(Context context, int i) {
        return Math.round(context.getResources().getDisplayMetrics().density * i);
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

    public BitmapDrawable getSweepBitmapDrawable() {
        Log.d(TAG, "getSweepBitmapDrawable : mDrawSweepBitmapDrawable = " + this.mDrawSweepBitmapDrawable);
        return this.mDrawSweepBitmapDrawable;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void draw(Canvas canvas) {
        BitmapDrawable bitmapDrawable = this.mDrawSweepBitmapDrawable;
        if (bitmapDrawable != null) {
            bitmapDrawable.draw(canvas);
        }
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void initAnimationFilter(View view, float f, int i, SemSweepListAnimator.OnSweepListener onSweepListener, SemSweepListAnimator.SweepConfiguration sweepConfiguration) {
        this.mSweepListener = onSweepListener;
        this.mViewForeground = view;
        this.mSweepConfiguration = sweepConfiguration;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void doRefresh() {
        View view = this.mViewForeground;
        if (view != null) {
            view.setVisibility(0);
            this.mViewForeground.setTranslationX(0.0f);
            this.mViewForeground.setAlpha(1.0f);
        }
        this.mIsAnimationBack = false;
        removeCachedBitmap();
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void doMoveAction(View view, float f, int i) {
        float width = f / view.getWidth();
        float fAbs = Math.abs(f);
        this.mViewForeground = view;
        Canvas canvasDrawRectToBitmapCanvas = drawRectToBitmapCanvas(view, f, width);
        view.setTranslationX(f);
        view.setAlpha(1.0f - (fAbs / view.getWidth()));
        SemSweepListAnimator.OnSweepListener onSweepListener = this.mSweepListener;
        if (onSweepListener != null && canvasDrawRectToBitmapCanvas != null) {
            onSweepListener.onSweep(i, width, canvasDrawRectToBitmapCanvas);
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

    private Canvas drawRectToBitmapCanvas(View view, float f, float f2) {
        int top;
        if (this.mSweepConfiguration == null) {
            return null;
        }
        int width = view.getWidth();
        int height = view.getHeight();
        int left = view.getLeft();
        View view2 = (View) view.getParent();
        if (view2 == null || !(view2 instanceof ViewGroup)) {
            top = 0;
        } else if (view2 instanceof ListView) {
            top = view.getTop();
        } else {
            top = view2.getTop() + view.getTop();
        }
        this.mViewTop = top;
        this.mSweepRect = new Rect(left, top, width, top + height);
        if (this.mSweepBitmap == null) {
            this.mSweepBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(this.mSweepBitmap);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        float fAbs = Math.abs(f);
        float width2 = (fAbs / view.getWidth()) * 255.0f;
        if (f2 > 0.0f) {
            this.mSweepDirection = 0;
            Drawable drawable = this.mSweepConfiguration.drawableLeftToRight;
            if (drawable == null) {
                Log.d(TAG, "mSweepConfiguration.drawableLeftToRight is null");
                return null;
            }
            Rect bounds = drawable.getBounds();
            int iWidth = bounds.width();
            int iHeight = bounds.height();
            int i = (int) f;
            Rect rect = new Rect(0, 0, i, height);
            Rect rect2 = new Rect(this.mSweepConfiguration.drawablePadding, 0, iWidth + this.mSweepConfiguration.drawablePadding, iHeight);
            rect2.offset(0, (height - iHeight) / 2);
            if (this.mSweepConfiguration.backgroundColorLeftToRight != 0) {
                this.mBgLeftToRight.setColor(this.mSweepConfiguration.backgroundColorLeftToRight);
            }
            drawRectInto(canvas, rect, this.mBgLeftToRight, 255, rect2, this.mSweepConfiguration.textLeftToRight, this.mSweepConfiguration.textSize, this.mSweepConfiguration.drawableLeftToRight);
            drawRectInto(canvas, new Rect(i, 0, width, height), this.mBgLeftToRight, (int) width2, rect2, this.mSweepConfiguration.textLeftToRight, this.mSweepConfiguration.textSize, null);
            return canvas;
        }
        if (f2 < 0.0f) {
            this.mSweepDirection = 1;
            Drawable drawable2 = this.mSweepConfiguration.drawableRightToLeft;
            if (drawable2 == null) {
                Log.d(TAG, "mSweepConfiguration.drawableRightToLeft is null");
                return null;
            }
            Rect bounds2 = drawable2.getBounds();
            int iWidth2 = bounds2.width();
            int iHeight2 = bounds2.height();
            int i2 = width - ((int) fAbs);
            Rect rect3 = new Rect(i2, 0, width, height);
            Rect rect4 = new Rect((width - iWidth2) - this.mSweepConfiguration.drawablePadding, 0, width - this.mSweepConfiguration.drawablePadding, iHeight2);
            rect4.offset(0, (height - iHeight2) / 2);
            SemSweepListAnimator.SweepConfiguration sweepConfiguration = this.mSweepConfiguration;
            if (sweepConfiguration != null && sweepConfiguration.backgroundColorRightToLeft != 0) {
                this.mBgRightToLeft.setColor(this.mSweepConfiguration.backgroundColorRightToLeft);
            }
            drawRectInto(canvas, rect3, this.mBgRightToLeft, 255, rect4, this.mSweepConfiguration.textRightToLeft, this.mSweepConfiguration.textSize, this.mSweepConfiguration.drawableRightToLeft);
            drawRectInto(canvas, new Rect(0, 0, i2, height), this.mBgRightToLeft, (int) width2, rect4, this.mSweepConfiguration.textRightToLeft, this.mSweepConfiguration.textSize, null);
        }
        return canvas;
    }

    private void drawRectInto(Canvas canvas, Rect rect, Paint paint, int i, Rect rect2, String str, float f, Drawable drawable) {
        canvas.save();
        paint.setAlpha(i);
        this.mTextPaint.setAlpha(i);
        if (f != 0.0f) {
            this.mTextPaint.setTextSize(f);
        } else {
            this.mTextPaint.setTextSize(this.mTextPaintSize);
        }
        canvas.clipRect(rect);
        canvas.drawRect(rect, paint);
        if (drawable != null) {
            if (rect2 != null) {
                drawable.setBounds(rect2);
            }
            drawable.draw(canvas);
        }
        drawSweepText(canvas, this.mTextPaint, str, rect2);
        canvas.restore();
    }

    private void drawSweepText(Canvas canvas, Paint paint, String str, Rect rect) {
        float f;
        int i;
        int height = canvas.getHeight();
        canvas.getWidth();
        Rect rect2 = new Rect();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(str, 0, str.length(), rect2);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float fAbs = Math.abs(fontMetrics.top - fontMetrics.bottom);
        if (rect == null) {
            f = 0.0f;
        } else {
            int i2 = this.mSweepDirection;
            if (i2 == 1) {
                i = (rect.left - this.mSweepConfiguration.drawablePadding) - rect2.right;
            } else {
                if (i2 == 0) {
                    i = rect.right + this.mSweepConfiguration.drawablePadding;
                }
                f = 0.0f;
            }
            f = i;
        }
        if (this.mSweepRectFullyDrawn) {
            height = this.mViewForeground.getHeight();
        }
        float f2 = ((height / 2.0f) + (fAbs / 2.0f)) - fontMetrics.bottom;
        if (this.mSweepRectFullyDrawn) {
            f2 += this.mViewTop;
            this.mSweepRectFullyDrawn = false;
        }
        canvas.drawText(str, f, f2, paint);
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public ValueAnimator createActionUpAnimator(View view, float f, int i, float f2, boolean z) {
        long jAbs;
        float translationX = view.getTranslationX();
        int width = view.getWidth();
        float fAbs = Math.abs(f2);
        float f3 = width;
        if (translationX > f3) {
            translationX = f3;
        }
        Log.d(TAG, "SemSweepTranslationFilter : createActionUpAnimator() : Math.abs(adjustedVelocityX) = " + Math.abs(f));
        StringBuilder sb = new StringBuilder("SemSweepTranslationFilter : createActionUpAnimator() : scaledTouchSlop * 23 = ");
        int i2 = i * 23;
        sb.append(i2);
        Log.d(TAG, sb.toString());
        float f4 = 1.0f;
        float fSignum = 0.0f;
        if (Math.abs(f) > i2 && z) {
            Log.d(TAG, "SemSweepTranslationFilter : createActionUpAnimator() : kick in animation with given velocity, point #1");
            Math.abs(translationX);
            jAbs = (int) ((1.0f - (Math.abs(translationX) / f3)) * 600.0f);
            if (jAbs > 600) {
                jAbs = 600;
            }
            f4 = 0.0f;
            fSignum = Math.signum(f) * f3;
        } else if (fAbs > f3 / 2.0f) {
            Log.d(TAG, "SemSweepTranslationFilter : createActionUpAnimator() : Greater than a half of the width, point #2");
            long jAbs2 = (int) ((1.0f - (Math.abs(translationX) / f3)) * 600.0f);
            f4 = 0.0f;
            fSignum = Math.signum(f2) * f3;
            jAbs = jAbs2;
        } else {
            Log.d(TAG, "SemSweepTranslationFilter : createActionUpAnimator() : Not far enough - animate it back, point #3");
            jAbs = (int) ((Math.abs(translationX) * 600.0f) / f3);
            this.mIsAnimationBack = true;
        }
        long j = jAbs >= 0 ? jAbs : 600L;
        this.mEndXOfActionUpAnimator = fSignum;
        ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat(View.ALPHA, f4), PropertyValuesHolder.ofFloat(View.TRANSLATION_X, fSignum));
        objectAnimatorOfPropertyValuesHolder.setDuration(j);
        objectAnimatorOfPropertyValuesHolder.setInterpolator(sDecel);
        return objectAnimatorOfPropertyValuesHolder;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public float getEndXOfActionUpAnimator() {
        return this.mEndXOfActionUpAnimator;
    }

    @Override // com.samsung.android.animation.SemAbsSweepAnimationFilter
    public void doUpActionWhenAnimationUpdate(int i, float f) {
        Canvas canvasDrawRectToBitmapCanvas;
        if (this.mViewForeground != null) {
            canvasDrawRectToBitmapCanvas = drawRectToBitmapCanvas(this.mViewForeground, r0.getWidth() * f, f);
        } else {
            canvasDrawRectToBitmapCanvas = null;
        }
        SemSweepListAnimator.OnSweepListener onSweepListener = this.mSweepListener;
        if (onSweepListener != null && canvasDrawRectToBitmapCanvas != null) {
            onSweepListener.onSweep(i, f, canvasDrawRectToBitmapCanvas);
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

    private void drawTextToCenter(Canvas canvas, Paint paint, String str) {
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        Rect rect = new Rect();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(str, 0, str.length(), rect);
        canvas.drawText(str, ((width / 2.0f) - (rect.width() / 2.0f)) - rect.left, ((height / 2.0f) + (rect.height() / 2.0f)) - rect.bottom, paint);
    }

    public void removeCachedBitmap() {
        BitmapDrawable bitmapDrawable = this.mDrawSweepBitmapDrawable;
        if (bitmapDrawable != null) {
            bitmapDrawable.getBitmap().recycle();
            this.mDrawSweepBitmapDrawable = null;
            this.mSweepBitmap = null;
        }
    }
}
