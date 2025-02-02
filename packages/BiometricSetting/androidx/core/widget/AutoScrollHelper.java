package androidx.core.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public abstract class AutoScrollHelper implements View.OnTouchListener {
    private static final int DEFAULT_ACTIVATION_DELAY = ViewConfiguration.getTapTimeout();
    private int mActivationDelay;
    private boolean mAlreadyDelayed;
    boolean mAnimating;
    private final Interpolator mEdgeInterpolator;
    private int mEdgeType;
    private boolean mEnabled;
    private float[] mMaximumEdges;
    private float[] mMaximumVelocity;
    private float[] mMinimumVelocity;
    boolean mNeedsCancel;
    boolean mNeedsReset;
    private float[] mRelativeEdges;
    private float[] mRelativeVelocity;
    private Runnable mRunnable;
    final ClampedScroller mScroller;
    final View mTarget;

    private static class ClampedScroller {
        private int mEffectiveRampDown;
        private int mRampDownDuration;
        private int mRampUpDuration;
        private float mStopValue;
        private float mTargetVelocityX;
        private float mTargetVelocityY;
        private long mStartTime = Long.MIN_VALUE;
        private long mStopTime = -1;
        private long mDeltaTime = 0;
        private int mDeltaX = 0;
        private int mDeltaY = 0;

        ClampedScroller() {
        }

        private float getValueAt(long j) {
            if (j < this.mStartTime) {
                return 0.0f;
            }
            long j2 = this.mStopTime;
            if (j2 < 0 || j < j2) {
                return AutoScrollHelper.constrain((j - r0) / this.mRampUpDuration, 0.0f, 1.0f) * 0.5f;
            }
            float f = this.mStopValue;
            return (f * AutoScrollHelper.constrain((j - j2) / this.mEffectiveRampDown, 0.0f, 1.0f)) + (1.0f - f);
        }

        public final void computeScrollDelta() {
            if (this.mDeltaTime == 0) {
                throw new RuntimeException("Cannot compute scroll delta before calling start()");
            }
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            float valueAt = getValueAt(currentAnimationTimeMillis);
            long j = currentAnimationTimeMillis - this.mDeltaTime;
            this.mDeltaTime = currentAnimationTimeMillis;
            float f = j * ((valueAt * 4.0f) + ((-4.0f) * valueAt * valueAt));
            this.mDeltaX = (int) (this.mTargetVelocityX * f);
            this.mDeltaY = (int) (f * this.mTargetVelocityY);
        }

        public final int getDeltaY() {
            return this.mDeltaY;
        }

        public final int getHorizontalDirection() {
            float f = this.mTargetVelocityX;
            return (int) (f / Math.abs(f));
        }

        public final int getVerticalDirection() {
            float f = this.mTargetVelocityY;
            return (int) (f / Math.abs(f));
        }

        public final boolean isFinished() {
            return this.mStopTime > 0 && AnimationUtils.currentAnimationTimeMillis() > this.mStopTime + ((long) this.mEffectiveRampDown);
        }

        public final void requestStop() {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            int i = (int) (currentAnimationTimeMillis - this.mStartTime);
            int i2 = this.mRampDownDuration;
            if (i > i2) {
                i = i2;
            } else if (i < 0) {
                i = 0;
            }
            this.mEffectiveRampDown = i;
            this.mStopValue = getValueAt(currentAnimationTimeMillis);
            this.mStopTime = currentAnimationTimeMillis;
        }

        public final void setRampDownDuration() {
            this.mRampDownDuration = 500;
        }

        public final void setRampUpDuration() {
            this.mRampUpDuration = 500;
        }

        public final void setTargetVelocity(float f, float f2) {
            this.mTargetVelocityX = f;
            this.mTargetVelocityY = f2;
        }

        public final void start() {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.mStartTime = currentAnimationTimeMillis;
            this.mStopTime = -1L;
            this.mDeltaTime = currentAnimationTimeMillis;
            this.mStopValue = 0.5f;
            this.mDeltaX = 0;
            this.mDeltaY = 0;
        }
    }

    private class ScrollAnimationRunnable implements Runnable {
        ScrollAnimationRunnable() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            AutoScrollHelper autoScrollHelper = AutoScrollHelper.this;
            if (autoScrollHelper.mAnimating) {
                if (autoScrollHelper.mNeedsReset) {
                    autoScrollHelper.mNeedsReset = false;
                    autoScrollHelper.mScroller.start();
                }
                ClampedScroller clampedScroller = AutoScrollHelper.this.mScroller;
                if (!clampedScroller.isFinished()) {
                    AutoScrollHelper autoScrollHelper2 = AutoScrollHelper.this;
                    ClampedScroller clampedScroller2 = autoScrollHelper2.mScroller;
                    int verticalDirection = clampedScroller2.getVerticalDirection();
                    clampedScroller2.getHorizontalDirection();
                    if (verticalDirection != 0 && autoScrollHelper2.canTargetScrollVertically(verticalDirection)) {
                        AutoScrollHelper autoScrollHelper3 = AutoScrollHelper.this;
                        if (autoScrollHelper3.mNeedsCancel) {
                            autoScrollHelper3.mNeedsCancel = false;
                            long uptimeMillis = SystemClock.uptimeMillis();
                            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                            autoScrollHelper3.mTarget.onTouchEvent(obtain);
                            obtain.recycle();
                        }
                        clampedScroller.computeScrollDelta();
                        AutoScrollHelper.this.scrollTargetBy(clampedScroller.getDeltaY());
                        ViewCompat.postOnAnimation(AutoScrollHelper.this.mTarget, this);
                        return;
                    }
                }
                AutoScrollHelper.this.mAnimating = false;
            }
        }
    }

    public AutoScrollHelper(View view) {
        ClampedScroller clampedScroller = new ClampedScroller();
        this.mScroller = clampedScroller;
        this.mEdgeInterpolator = new AccelerateInterpolator();
        this.mRelativeEdges = new float[]{0.0f, 0.0f};
        this.mMaximumEdges = new float[]{Float.MAX_VALUE, Float.MAX_VALUE};
        this.mRelativeVelocity = new float[]{0.0f, 0.0f};
        this.mMinimumVelocity = new float[]{0.0f, 0.0f};
        this.mMaximumVelocity = new float[]{Float.MAX_VALUE, Float.MAX_VALUE};
        this.mTarget = view;
        float f = Resources.getSystem().getDisplayMetrics().density;
        float[] fArr = this.mMaximumVelocity;
        float f2 = ((int) ((1575.0f * f) + 0.5f)) / 1000.0f;
        fArr[0] = f2;
        fArr[1] = f2;
        float[] fArr2 = this.mMinimumVelocity;
        float f3 = ((int) ((f * 315.0f) + 0.5f)) / 1000.0f;
        fArr2[0] = f3;
        fArr2[1] = f3;
        this.mEdgeType = 1;
        float[] fArr3 = this.mMaximumEdges;
        fArr3[0] = Float.MAX_VALUE;
        fArr3[1] = Float.MAX_VALUE;
        float[] fArr4 = this.mRelativeEdges;
        fArr4[0] = 0.2f;
        fArr4[1] = 0.2f;
        float[] fArr5 = this.mRelativeVelocity;
        fArr5[0] = 0.001f;
        fArr5[1] = 0.001f;
        this.mActivationDelay = DEFAULT_ACTIVATION_DELAY;
        clampedScroller.setRampUpDuration();
        clampedScroller.setRampDownDuration();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0041 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private float computeTargetVelocity(int i, float f, float f2, float f3) {
        float f4;
        float interpolation;
        float constrain = constrain(this.mRelativeEdges[i] * f2, 0.0f, this.mMaximumEdges[i]);
        float constrainEdgeValue = constrainEdgeValue(f2 - f, constrain) - constrainEdgeValue(f, constrain);
        if (constrainEdgeValue < 0.0f) {
            interpolation = -((AccelerateInterpolator) this.mEdgeInterpolator).getInterpolation(-constrainEdgeValue);
        } else {
            if (constrainEdgeValue <= 0.0f) {
                f4 = 0.0f;
                if (f4 != 0.0f) {
                    return 0.0f;
                }
                float f5 = this.mRelativeVelocity[i];
                float f6 = this.mMinimumVelocity[i];
                float f7 = this.mMaximumVelocity[i];
                float f8 = f5 * f3;
                return f4 > 0.0f ? constrain(f4 * f8, f6, f7) : -constrain((-f4) * f8, f6, f7);
            }
            interpolation = ((AccelerateInterpolator) this.mEdgeInterpolator).getInterpolation(constrainEdgeValue);
        }
        f4 = constrain(interpolation, -1.0f, 1.0f);
        if (f4 != 0.0f) {
        }
    }

    static float constrain(float f, float f2, float f3) {
        return f > f3 ? f3 : f < f2 ? f2 : f;
    }

    private float constrainEdgeValue(float f, float f2) {
        if (f2 == 0.0f) {
            return 0.0f;
        }
        int i = this.mEdgeType;
        if (i == 0 || i == 1) {
            if (f < f2) {
                if (f >= 0.0f) {
                    return 1.0f - (f / f2);
                }
                if (this.mAnimating && i == 1) {
                    return 1.0f;
                }
            }
        } else if (i == 2 && f < 0.0f) {
            return f / (-f2);
        }
        return 0.0f;
    }

    public abstract boolean canTargetScrollVertically(int i);

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0013, code lost:
    
        if (r0 != 3) goto L38;
     */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int i;
        if (!this.mEnabled) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                }
            }
            if (this.mNeedsReset) {
                this.mAnimating = false;
            } else {
                this.mScroller.requestStop();
            }
            return false;
        }
        this.mNeedsCancel = true;
        this.mAlreadyDelayed = false;
        this.mScroller.setTargetVelocity(computeTargetVelocity(0, motionEvent.getX(), view.getWidth(), this.mTarget.getWidth()), computeTargetVelocity(1, motionEvent.getY(), view.getHeight(), this.mTarget.getHeight()));
        if (!this.mAnimating) {
            ClampedScroller clampedScroller = this.mScroller;
            int verticalDirection = clampedScroller.getVerticalDirection();
            clampedScroller.getHorizontalDirection();
            if (verticalDirection != 0 && canTargetScrollVertically(verticalDirection)) {
                if (this.mRunnable == null) {
                    this.mRunnable = new ScrollAnimationRunnable();
                }
                this.mAnimating = true;
                this.mNeedsReset = true;
                if (this.mAlreadyDelayed || (i = this.mActivationDelay) <= 0) {
                    ((ScrollAnimationRunnable) this.mRunnable).run();
                } else {
                    ViewCompat.postOnAnimationDelayed(this.mTarget, this.mRunnable, i);
                }
                this.mAlreadyDelayed = true;
            }
        }
        return false;
    }

    public abstract void scrollTargetBy(int i);

    public final void setEnabled(boolean z) {
        if (this.mEnabled && !z) {
            if (this.mNeedsReset) {
                this.mAnimating = false;
            } else {
                this.mScroller.requestStop();
            }
        }
        this.mEnabled = z;
    }
}
