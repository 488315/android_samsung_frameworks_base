package android.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.SystemClock;

/* loaded from: classes.dex */
public class TransitionDrawable extends LayerDrawable implements Drawable.Callback {
    private static final int TRANSITION_NONE = 2;
    private static final int TRANSITION_RUNNING = 1;
    private static final int TRANSITION_STARTING = 0;
    private int mAlpha;
    private boolean mCrossFade;
    private int mDuration;
    private int mFrom;
    private int mOriginalDuration;
    private boolean mReverse;
    private long mStartTimeMillis;
    private int mTo;
    private int mTransitionState;

    public TransitionDrawable(Drawable[] drawableArr) {
        this(new TransitionState(null, null, null), drawableArr);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    TransitionDrawable() {
        this(new TransitionState(null, null, null), (Resources) null);
    }

    private TransitionDrawable(TransitionState transitionState, Resources resources) {
        super(transitionState, resources);
        this.mTransitionState = 2;
        this.mAlpha = 0;
    }

    private TransitionDrawable(TransitionState transitionState, Drawable[] drawableArr) {
        super(drawableArr, transitionState);
        this.mTransitionState = 2;
        this.mAlpha = 0;
    }

    @Override // android.graphics.drawable.LayerDrawable
    LayerDrawable.LayerState createConstantState(LayerDrawable.LayerState layerState, Resources resources) {
        return new TransitionState((TransitionState) layerState, this, resources);
    }

    public void startTransition(int i) {
        this.mFrom = 0;
        this.mTo = 255;
        this.mAlpha = 0;
        this.mOriginalDuration = i;
        this.mDuration = i;
        this.mReverse = false;
        this.mTransitionState = 0;
        invalidateSelf();
    }

    public void showSecondLayer() {
        this.mAlpha = 255;
        this.mReverse = false;
        this.mTransitionState = 2;
        invalidateSelf();
    }

    public void resetTransition() {
        this.mAlpha = 0;
        this.mTransitionState = 2;
        invalidateSelf();
    }

    public void reverseTransition(int i) {
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = this.mStartTimeMillis;
        if (uptimeMillis - j > this.mDuration) {
            if (this.mTo == 0) {
                this.mFrom = 0;
                this.mTo = 255;
                this.mAlpha = 0;
                this.mReverse = false;
            } else {
                this.mFrom = 255;
                this.mTo = 0;
                this.mAlpha = 255;
                this.mReverse = true;
            }
            this.mOriginalDuration = i;
            this.mDuration = i;
            this.mTransitionState = 0;
            invalidateSelf();
            return;
        }
        boolean z = this.mReverse;
        this.mReverse = !z;
        this.mFrom = this.mAlpha;
        this.mTo = z ? 255 : 0;
        this.mDuration = (int) (!z ? uptimeMillis - j : this.mOriginalDuration - (uptimeMillis - j));
        this.mTransitionState = 0;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean z;
        int i = this.mTransitionState;
        if (i == 0) {
            this.mStartTimeMillis = SystemClock.uptimeMillis();
            this.mTransitionState = 1;
            z = false;
        } else if (i == 1 && this.mStartTimeMillis >= 0) {
            float uptimeMillis = (SystemClock.uptimeMillis() - this.mStartTimeMillis) / this.mDuration;
            z = uptimeMillis >= 1.0f;
            float min = Math.min(uptimeMillis, 1.0f);
            this.mAlpha = (int) (this.mFrom + ((this.mTo - r3) * min));
        } else {
            z = true;
        }
        int i2 = this.mAlpha;
        boolean z2 = this.mCrossFade;
        LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        if (z) {
            if (!z2 || i2 == 0) {
                childDrawableArr[0].mDrawable.draw(canvas);
            }
            if (i2 == 255) {
                childDrawableArr[1].mDrawable.draw(canvas);
                return;
            }
            return;
        }
        Drawable drawable = childDrawableArr[0].mDrawable;
        if (z2) {
            drawable.setAlpha(255 - i2);
        }
        drawable.draw(canvas);
        if (z2) {
            drawable.setAlpha(255);
        }
        if (i2 > 0) {
            Drawable drawable2 = childDrawableArr[1].mDrawable;
            drawable2.setAlpha(i2);
            drawable2.draw(canvas);
            drawable2.setAlpha(255);
        }
        if (z) {
            return;
        }
        invalidateSelf();
    }

    public void setCrossFadeEnabled(boolean z) {
        this.mCrossFade = z;
    }

    public boolean isCrossFadeEnabled() {
        return this.mCrossFade;
    }

    static class TransitionState extends LayerDrawable.LayerState {
        TransitionState(TransitionState transitionState, TransitionDrawable transitionDrawable, Resources resources) {
            super(transitionState, transitionDrawable, resources);
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new TransitionDrawable(this, null);
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new TransitionDrawable(this, resources);
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }
    }
}
