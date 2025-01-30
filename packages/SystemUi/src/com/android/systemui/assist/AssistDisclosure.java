package com.android.systemui.assist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.BasicRune;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AssistDisclosure {
    public final Context mContext;
    public final Handler mHandler;
    public final RunnableC10371 mShowRunnable = new Runnable() { // from class: com.android.systemui.assist.AssistDisclosure.1
        @Override // java.lang.Runnable
        public final void run() {
            AssistDisclosure assistDisclosure = AssistDisclosure.this;
            if (assistDisclosure.mView == null) {
                assistDisclosure.mView = assistDisclosure.new AssistDisclosureView(assistDisclosure.mContext);
            }
            if (assistDisclosure.mViewAdded) {
                return;
            }
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2015, 525576, -3);
            layoutParams.privateFlags |= 16;
            layoutParams.setTitle("AssistDisclosure");
            layoutParams.setFitInsetsTypes(0);
            layoutParams.layoutInDisplayCutoutMode = 1;
            assistDisclosure.mWm.addView(assistDisclosure.mView, layoutParams);
            assistDisclosure.mViewAdded = true;
        }
    };
    public AssistDisclosureView mView;
    public boolean mViewAdded;
    public final WindowManager mWm;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AssistDisclosureView extends View implements ValueAnimator.AnimatorUpdateListener {
        public int mAlpha;
        public final ValueAnimator mAlphaInAnimator;
        public final ValueAnimator mAlphaOutAnimator;
        public final AnimatorSet mAnimator;
        public final Paint mPaint;
        public final Path mPath;
        public final float mRadius;
        public final Paint mShadowPaint;
        public final float mShadowThickness;
        public final float mThickness;

        public AssistDisclosureView(Context context) {
            super(context);
            this.mPath = new Path();
            this.mAlpha = 0;
            ValueAnimator duration = ValueAnimator.ofInt(0, 222).setDuration(400L);
            this.mAlphaInAnimator = duration;
            duration.addUpdateListener(this);
            Interpolator interpolator = Interpolators.CUSTOM_40_40;
            duration.setInterpolator(interpolator);
            ValueAnimator duration2 = ValueAnimator.ofInt(222, 0).setDuration(300L);
            this.mAlphaOutAnimator = duration2;
            duration2.addUpdateListener(this);
            duration2.setInterpolator(interpolator);
            AnimatorSet animatorSet = new AnimatorSet();
            this.mAnimator = animatorSet;
            animatorSet.play(duration).before(duration2);
            animatorSet.addListener(new AnimatorListenerAdapter(AssistDisclosure.this) { // from class: com.android.systemui.assist.AssistDisclosure.AssistDisclosureView.1
                public boolean mCancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    this.mCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    if (this.mCancelled) {
                        return;
                    }
                    AssistDisclosure assistDisclosure = AssistDisclosure.this;
                    if (assistDisclosure.mViewAdded) {
                        assistDisclosure.mWm.removeView(assistDisclosure.mView);
                        assistDisclosure.mViewAdded = false;
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    this.mCancelled = false;
                }
            });
            boolean z = BasicRune.ASSIST_DISCLOSURE_CORNER_ROUND_ENABLED;
            if (z) {
                this.mPaint = new Paint(1);
                this.mShadowPaint = new Paint(1);
            } else {
                this.mPaint = new Paint();
                this.mShadowPaint = new Paint();
            }
            PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC);
            this.mPaint.setColor(-1);
            this.mPaint.setXfermode(porterDuffXfermode);
            this.mShadowPaint.setColor(-12303292);
            this.mShadowPaint.setXfermode(porterDuffXfermode);
            float dimension = getResources().getDimension(R.dimen.assist_disclosure_thickness);
            this.mThickness = dimension;
            float dimension2 = getResources().getDimension(R.dimen.assist_disclosure_shadow_thickness);
            this.mShadowThickness = dimension2;
            if (z) {
                int dimensionPixelSize = ((View) this).mContext.getResources().getDimensionPixelSize(android.R.dimen.text_size_subhead_material);
                this.mRadius = (dimensionPixelSize <= 0 && (dimensionPixelSize = ((View) this).mContext.getResources().getDimensionPixelSize(R.dimen.assist_disclosure_rounded)) <= 0) ? BasicRune.ASSIST_DISCLOSURE_CORNER_ROUND_SIZE * 6.0f * getResources().getDisplayMetrics().density : dimensionPixelSize;
                this.mPaint.setDither(true);
                this.mPaint.setStrokeWidth(dimension * 2.0f);
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mShadowPaint.setDither(true);
                this.mShadowPaint.setStrokeWidth((dimension2 * 4.0f) + (dimension * 2.0f));
                this.mShadowPaint.setStyle(Paint.Style.STROKE);
            }
        }

        public static void drawBeam(Canvas canvas, float f, float f2, float f3, float f4, Paint paint, float f5) {
            canvas.drawRect(f - f5, f2 - f5, f3 + f5, f4 + f5, paint);
        }

        public final void drawGeometry(Canvas canvas, Paint paint, float f) {
            int width = getWidth();
            int height = getHeight();
            float f2 = this.mThickness;
            float f3 = height;
            float f4 = f3 - f2;
            float f5 = width;
            drawBeam(canvas, 0.0f, f4, f5, f3, paint, f);
            drawBeam(canvas, 0.0f, 0.0f, f2, f4, paint, f);
            float f6 = f5 - f2;
            drawBeam(canvas, f6, 0.0f, f5, f4, paint, f);
            drawBeam(canvas, f2, 0.0f, f6, f2, paint, f);
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            ValueAnimator valueAnimator2 = this.mAlphaOutAnimator;
            if (valueAnimator == valueAnimator2) {
                this.mAlpha = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
            } else {
                ValueAnimator valueAnimator3 = this.mAlphaInAnimator;
                if (valueAnimator == valueAnimator3) {
                    this.mAlpha = ((Integer) valueAnimator3.getAnimatedValue()).intValue();
                }
            }
            invalidate();
        }

        @Override // android.view.View
        public final void onAttachedToWindow() {
            super.onAttachedToWindow();
            this.mAnimator.cancel();
            this.mAnimator.start();
            sendAccessibilityEvent(16777216);
        }

        @Override // android.view.View
        public final void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.mAnimator.cancel();
            this.mAlpha = 0;
        }

        @Override // android.view.View
        public final void onDraw(Canvas canvas) {
            this.mPaint.setAlpha(this.mAlpha);
            this.mShadowPaint.setAlpha(this.mAlpha / 4);
            if (!BasicRune.ASSIST_DISCLOSURE_CORNER_ROUND_ENABLED) {
                drawGeometry(canvas, this.mShadowPaint, this.mShadowThickness);
                drawGeometry(canvas, this.mPaint, 0.0f);
                return;
            }
            int width = getWidth();
            int height = getHeight();
            this.mPath.reset();
            this.mPath.moveTo(0.0f, this.mRadius);
            Path path = this.mPath;
            float f = this.mRadius;
            path.arcTo(0.0f, 0.0f, f * 2.0f, f * 2.0f, 180.0f, 90.0f, true);
            float f2 = width;
            this.mPath.lineTo(f2 - this.mRadius, 0.0f);
            Path path2 = this.mPath;
            float f3 = this.mRadius;
            path2.arcTo(f2 - (f3 * 2.0f), 0.0f, f2, f3 * 2.0f, -90.0f, 90.0f, true);
            float f4 = height;
            this.mPath.lineTo(f2, f4 - this.mRadius);
            Path path3 = this.mPath;
            float f5 = this.mRadius;
            path3.arcTo(f2 - (f5 * 2.0f), f4 - (f5 * 2.0f), f2, f4, 0.0f, 90.0f, true);
            this.mPath.lineTo(this.mRadius, f4);
            Path path4 = this.mPath;
            float f6 = this.mRadius;
            path4.arcTo(0.0f, f4 - (f6 * 2.0f), f6 * 2.0f, f4, 90.0f, 90.0f, true);
            this.mPath.lineTo(0.0f, this.mRadius);
            canvas.drawPath(this.mPath, this.mShadowPaint);
            canvas.drawPath(this.mPath, this.mPaint);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.assist.AssistDisclosure$1] */
    public AssistDisclosure(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
        this.mWm = (WindowManager) context.getSystemService(WindowManager.class);
    }
}
