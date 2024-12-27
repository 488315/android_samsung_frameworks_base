package com.android.systemui.screenshot.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TransitioningIconDrawable extends Drawable {
    public int alpha = 255;
    public ColorFilter colorFilter;
    public Drawable drawable;
    public Drawable enteringDrawable;
    public ColorStateList tint;
    public final ValueAnimator transitionAnimator;

    public TransitioningIconDrawable() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        Intrinsics.checkNotNull(ofFloat);
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.TransitioningIconDrawable$transitionAnimator$lambda$1$$inlined$doOnEnd$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                TransitioningIconDrawable transitioningIconDrawable = TransitioningIconDrawable.this;
                transitioningIconDrawable.drawable = transitioningIconDrawable.enteringDrawable;
                transitioningIconDrawable.enteringDrawable = null;
                transitioningIconDrawable.invalidateSelf();
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        this.transitionAnimator = ofFloat;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Drawable drawable = this.drawable;
        if (drawable != null) {
            drawScaledDrawable(drawable, canvas, this.transitionAnimator.isRunning() ? 1.0f - this.transitionAnimator.getAnimatedFraction() : 1.0f);
        }
        Drawable drawable2 = this.enteringDrawable;
        if (drawable2 != null) {
            drawScaledDrawable(drawable2, canvas, this.transitionAnimator.getAnimatedFraction());
        }
        if (this.transitionAnimator.isRunning()) {
            invalidateSelf();
        }
    }

    public final void drawScaledDrawable(Drawable drawable, Canvas canvas, float f) {
        drawable.setBounds(getBounds());
        canvas.save();
        canvas.scale(f, f, drawable.getIntrinsicWidth() / 2, drawable.getIntrinsicHeight() / 2);
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return this.alpha;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.alpha = i;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
        Drawable drawable = this.drawable;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        }
        Drawable drawable2 = this.enteringDrawable;
        if (drawable2 == null) {
            return;
        }
        drawable2.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
        Drawable drawable = this.drawable;
        if (drawable != null) {
            drawable.setTintList(colorStateList);
        }
        Drawable drawable2 = this.enteringDrawable;
        if (drawable2 != null) {
            drawable2.setTintList(colorStateList);
        }
        this.tint = colorStateList;
    }
}
