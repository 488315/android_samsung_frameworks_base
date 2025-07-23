package android.view;

import android.graphics.Insets;
import android.view.animation.Interpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* loaded from: classes4.dex */
public final class WindowInsetsAnimation {
    private float mAlpha;
    private final long mDurationMillis;
    private float mFraction;
    private final Interpolator mInterpolator;
    private final int mTypeMask;

    public WindowInsetsAnimation(int i, Interpolator interpolator, long j) {
        this.mTypeMask = i;
        this.mInterpolator = interpolator;
        this.mDurationMillis = j;
    }

    public int getTypeMask() {
        return this.mTypeMask;
    }

    public float getFraction() {
        return this.mFraction;
    }

    public float getInterpolatedFraction() {
        Interpolator interpolator = this.mInterpolator;
        if (interpolator != null) {
            return interpolator.getInterpolation(this.mFraction);
        }
        return this.mFraction;
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    public long getDurationMillis() {
        return this.mDurationMillis;
    }

    public void setFraction(float f) {
        this.mFraction = f;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public void setAlpha(float f) {
        this.mAlpha = f;
    }

    public static final class Bounds {
        private final Insets mLowerBound;
        private final Insets mUpperBound;

        public Bounds(Insets insets, Insets insets2) {
            this.mLowerBound = insets;
            this.mUpperBound = insets2;
        }

        public Insets getLowerBound() {
            return this.mLowerBound;
        }

        public Insets getUpperBound() {
            return this.mUpperBound;
        }

        public Bounds inset(Insets insets) {
            return new Bounds(WindowInsets.insetInsets(this.mLowerBound, insets.left, insets.top, insets.right, insets.bottom), WindowInsets.insetInsets(this.mUpperBound, insets.left, insets.top, insets.right, insets.bottom));
        }

        public String toString() {
            return "Bounds{lower=" + this.mLowerBound + " upper=" + this.mUpperBound + "}";
        }
    }

    public static abstract class Callback {
        public static final int DISPATCH_MODE_CONTINUE_ON_SUBTREE = 1;
        public static final int DISPATCH_MODE_STOP = 0;
        private final int mDispatchMode;

        @Retention(RetentionPolicy.SOURCE)
        public @interface DispatchMode {
        }

        public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
        }

        public void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
        }

        public abstract WindowInsets onProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list);

        public Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, Bounds bounds) {
            return bounds;
        }

        public Callback(int i) {
            this.mDispatchMode = i;
        }

        public final int getDispatchMode() {
            return this.mDispatchMode;
        }
    }
}
