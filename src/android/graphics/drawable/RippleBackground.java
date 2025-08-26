package android.graphics.drawable;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.FloatProperty;
import android.view.animation.LinearInterpolator;

/* loaded from: classes.dex */
class RippleBackground extends RippleComponent {
    private static final TimeInterpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final BackgroundProperty OPACITY = new BackgroundProperty("opacity") { // from class: android.graphics.drawable.RippleBackground.1
        @Override // android.util.FloatProperty
        public void setValue(RippleBackground rippleBackground, float f) {
            rippleBackground.mOpacity = f;
            rippleBackground.invalidateSelf();
        }

        @Override // android.util.Property
        public Float get(RippleBackground rippleBackground) {
            return Float.valueOf(rippleBackground.mOpacity);
        }
    };
    private static final int OPACITY_DURATION = 80;
    private ObjectAnimator mAnimator;
    private boolean mFocused;
    private boolean mHovered;
    private boolean mIsBounded;
    private float mOpacity;

    public RippleBackground(RippleDrawable rippleDrawable, Rect rect, boolean z) {
        super(rippleDrawable, rect);
        this.mOpacity = 0.0f;
        this.mFocused = false;
        this.mHovered = false;
        this.mIsBounded = z;
    }

    public boolean isVisible() {
        return this.mOpacity > 0.0f;
    }

    public void draw(Canvas canvas, Paint paint) {
        int alpha = paint.getAlpha();
        int iMin = Math.min((int) ((alpha * this.mOpacity) + 0.5f), 255);
        if (iMin > 0) {
            paint.setAlpha(iMin);
            canvas.drawCircle(0.0f, 0.0f, this.mTargetRadius, paint);
            paint.setAlpha(alpha);
        }
    }

    public void setState(boolean z, boolean z2, boolean z3) {
        boolean z4 = this.mFocused;
        if (!z4) {
            z = z && !z3;
        }
        boolean z5 = this.mHovered;
        if (!z5) {
            z2 = z2 && !z3;
        }
        if (z5 == z2 && z4 == z) {
            return;
        }
        this.mHovered = z2;
        this.mFocused = z;
        onStateChanged();
    }

    private void onStateChanged() {
        float f = this.mFocused ? 0.8f : this.mHovered ? 0.6f : 0.0f;
        ObjectAnimator objectAnimator = this.mAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.mAnimator = null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, OPACITY, f);
        this.mAnimator = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(80L);
        this.mAnimator.setInterpolator(LINEAR_INTERPOLATOR);
        this.mAnimator.start();
    }

    public void jumpToFinal() {
        ObjectAnimator objectAnimator = this.mAnimator;
        if (objectAnimator != null) {
            objectAnimator.end();
            this.mAnimator = null;
        }
    }

    private static abstract class BackgroundProperty extends FloatProperty<RippleBackground> {
        public BackgroundProperty(String str) {
            super(str);
        }
    }
}
