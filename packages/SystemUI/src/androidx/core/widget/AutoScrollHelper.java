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
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AutoScrollHelper implements View.OnTouchListener {
    public static final int DEFAULT_ACTIVATION_DELAY = ViewConfiguration.getTapTimeout();
    public final int mActivationDelay;
    public boolean mAlreadyDelayed;
    public boolean mAnimating;
    public final Interpolator mEdgeInterpolator;
    public final int mEdgeType;
    public boolean mEnabled;
    public final float[] mMaximumEdges;
    public final float[] mMaximumVelocity;
    public final float[] mMinimumVelocity;
    public boolean mNeedsCancel;
    public boolean mNeedsReset;
    public final float[] mRelativeEdges;
    public final float[] mRelativeVelocity;
    public ScrollAnimationRunnable mRunnable;
    public final ClampedScroller mScroller;
    public final View mTarget;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ClampedScroller {
        public int mEffectiveRampDown;
        public int mRampDownDuration;
        public int mRampUpDuration;
        public float mStopValue;
        public float mTargetVelocityX;
        public float mTargetVelocityY;
        public long mStartTime = Long.MIN_VALUE;
        public long mStopTime = -1;
        public long mDeltaTime = 0;

        public final float getValueAt(long j) {
            if (j < this.mStartTime) {
                return 0.0f;
            }
            long j2 = this.mStopTime;
            if (j2 < 0 || j < j2) {
                return AutoScrollHelper.constrain((j - r0) / this.mRampUpDuration, 0.0f, 1.0f) * 0.5f;
            }
            float f = this.mStopValue;
            return (AutoScrollHelper.constrain((j - j2) / this.mEffectiveRampDown, 0.0f, 1.0f) * f) + (1.0f - f);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ScrollAnimationRunnable implements Runnable {
        public ScrollAnimationRunnable() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            AutoScrollHelper autoScrollHelper = AutoScrollHelper.this;
            if (autoScrollHelper.mAnimating) {
                if (autoScrollHelper.mNeedsReset) {
                    autoScrollHelper.mNeedsReset = false;
                    ClampedScroller clampedScroller = autoScrollHelper.mScroller;
                    clampedScroller.getClass();
                    long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
                    clampedScroller.mStartTime = currentAnimationTimeMillis;
                    clampedScroller.mStopTime = -1L;
                    clampedScroller.mDeltaTime = currentAnimationTimeMillis;
                    clampedScroller.mStopValue = 0.5f;
                }
                ClampedScroller clampedScroller2 = AutoScrollHelper.this.mScroller;
                if ((clampedScroller2.mStopTime > 0 && AnimationUtils.currentAnimationTimeMillis() > clampedScroller2.mStopTime + clampedScroller2.mEffectiveRampDown) || !AutoScrollHelper.this.shouldAnimate()) {
                    AutoScrollHelper.this.mAnimating = false;
                    return;
                }
                AutoScrollHelper autoScrollHelper2 = AutoScrollHelper.this;
                if (autoScrollHelper2.mNeedsCancel) {
                    autoScrollHelper2.mNeedsCancel = false;
                    long uptimeMillis = SystemClock.uptimeMillis();
                    MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                    autoScrollHelper2.mTarget.onTouchEvent(obtain);
                    obtain.recycle();
                }
                if (clampedScroller2.mDeltaTime == 0) {
                    throw new RuntimeException("Cannot compute scroll delta before calling start()");
                }
                long currentAnimationTimeMillis2 = AnimationUtils.currentAnimationTimeMillis();
                float valueAt = clampedScroller2.getValueAt(currentAnimationTimeMillis2);
                long j = currentAnimationTimeMillis2 - clampedScroller2.mDeltaTime;
                clampedScroller2.mDeltaTime = currentAnimationTimeMillis2;
                AutoScrollHelper.this.scrollTargetBy((int) (j * ((valueAt * 4.0f) + ((-4.0f) * valueAt * valueAt)) * clampedScroller2.mTargetVelocityY));
                View view = AutoScrollHelper.this.mTarget;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view.postOnAnimation(this);
            }
        }
    }

    public AutoScrollHelper(View view) {
        ClampedScroller clampedScroller = new ClampedScroller();
        this.mScroller = clampedScroller;
        this.mEdgeInterpolator = new AccelerateInterpolator();
        float[] fArr = {0.0f, 0.0f};
        this.mRelativeEdges = fArr;
        float[] fArr2 = {Float.MAX_VALUE, Float.MAX_VALUE};
        this.mMaximumEdges = fArr2;
        float[] fArr3 = {0.0f, 0.0f};
        this.mRelativeVelocity = fArr3;
        float[] fArr4 = {0.0f, 0.0f};
        this.mMinimumVelocity = fArr4;
        float[] fArr5 = {Float.MAX_VALUE, Float.MAX_VALUE};
        this.mMaximumVelocity = fArr5;
        this.mTarget = view;
        float f = Resources.getSystem().getDisplayMetrics().density;
        float f2 = ((int) ((1575.0f * f) + 0.5f)) / 1000.0f;
        fArr5[0] = f2;
        fArr5[1] = f2;
        float f3 = ((int) ((f * 315.0f) + 0.5f)) / 1000.0f;
        fArr4[0] = f3;
        fArr4[1] = f3;
        this.mEdgeType = 1;
        fArr2[0] = Float.MAX_VALUE;
        fArr2[1] = Float.MAX_VALUE;
        fArr[0] = 0.2f;
        fArr[1] = 0.2f;
        fArr3[0] = 0.001f;
        fArr3[1] = 0.001f;
        this.mActivationDelay = DEFAULT_ACTIVATION_DELAY;
        clampedScroller.mRampUpDuration = 500;
        clampedScroller.mRampDownDuration = 500;
    }

    public static float constrain(float f, float f2, float f3) {
        return f > f3 ? f3 : f < f2 ? f2 : f;
    }

    public abstract boolean canTargetScrollVertically(int i);

    /* JADX WARN: Removed duplicated region for block: B:7:0x0041 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float computeTargetVelocity(float r4, float r5, float r6, int r7) {
        /*
            r3 = this;
            float[] r0 = r3.mRelativeEdges
            r0 = r0[r7]
            float[] r1 = r3.mMaximumEdges
            r1 = r1[r7]
            float r0 = r0 * r5
            r2 = 0
            float r0 = constrain(r0, r2, r1)
            float r1 = r3.constrainEdgeValue(r4, r0)
            float r5 = r5 - r4
            float r4 = r3.constrainEdgeValue(r5, r0)
            float r4 = r4 - r1
            int r5 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r5 >= 0) goto L27
            android.view.animation.Interpolator r5 = r3.mEdgeInterpolator
            float r4 = -r4
            android.view.animation.AccelerateInterpolator r5 = (android.view.animation.AccelerateInterpolator) r5
            float r4 = r5.getInterpolation(r4)
            float r4 = -r4
            goto L33
        L27:
            int r5 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r5 <= 0) goto L3c
            android.view.animation.Interpolator r5 = r3.mEdgeInterpolator
            android.view.animation.AccelerateInterpolator r5 = (android.view.animation.AccelerateInterpolator) r5
            float r4 = r5.getInterpolation(r4)
        L33:
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            r0 = 1065353216(0x3f800000, float:1.0)
            float r4 = constrain(r4, r5, r0)
            goto L3d
        L3c:
            r4 = r2
        L3d:
            int r5 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r5 != 0) goto L42
            return r2
        L42:
            float[] r0 = r3.mRelativeVelocity
            r0 = r0[r7]
            float[] r1 = r3.mMinimumVelocity
            r1 = r1[r7]
            float[] r3 = r3.mMaximumVelocity
            r3 = r3[r7]
            float r0 = r0 * r6
            if (r5 <= 0) goto L57
            float r4 = r4 * r0
            float r3 = constrain(r4, r1, r3)
            return r3
        L57:
            float r4 = -r4
            float r4 = r4 * r0
            float r3 = constrain(r4, r1, r3)
            float r3 = -r3
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.AutoScrollHelper.computeTargetVelocity(float, float, float, int):float");
    }

    public final float constrainEdgeValue(float f, float f2) {
        if (f2 != 0.0f) {
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
        }
        return 0.0f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0014, code lost:
    
        if (r0 != 3) goto L30;
     */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouch(android.view.View r6, android.view.MotionEvent r7) {
        /*
            r5 = this;
            boolean r0 = r5.mEnabled
            r1 = 0
            if (r0 != 0) goto L7
            goto L80
        L7:
            int r0 = r7.getActionMasked()
            r2 = 1
            if (r0 == 0) goto L1b
            if (r0 == r2) goto L17
            r3 = 2
            if (r0 == r3) goto L1f
            r6 = 3
            if (r0 == r6) goto L17
            goto L80
        L17:
            r5.requestStop()
            return r1
        L1b:
            r5.mNeedsCancel = r2
            r5.mAlreadyDelayed = r1
        L1f:
            float r0 = r7.getX()
            int r3 = r6.getWidth()
            float r3 = (float) r3
            android.view.View r4 = r5.mTarget
            int r4 = r4.getWidth()
            float r4 = (float) r4
            float r0 = r5.computeTargetVelocity(r0, r3, r4, r1)
            float r7 = r7.getY()
            int r6 = r6.getHeight()
            float r6 = (float) r6
            android.view.View r3 = r5.mTarget
            int r3 = r3.getHeight()
            float r3 = (float) r3
            float r6 = r5.computeTargetVelocity(r7, r6, r3, r2)
            androidx.core.widget.AutoScrollHelper$ClampedScroller r7 = r5.mScroller
            r7.mTargetVelocityX = r0
            r7.mTargetVelocityY = r6
            boolean r6 = r5.mAnimating
            if (r6 != 0) goto L80
            boolean r6 = r5.shouldAnimate()
            if (r6 == 0) goto L80
            androidx.core.widget.AutoScrollHelper$ScrollAnimationRunnable r6 = r5.mRunnable
            if (r6 != 0) goto L62
            androidx.core.widget.AutoScrollHelper$ScrollAnimationRunnable r6 = new androidx.core.widget.AutoScrollHelper$ScrollAnimationRunnable
            r6.<init>()
            r5.mRunnable = r6
        L62:
            r5.mAnimating = r2
            r5.mNeedsReset = r2
            boolean r6 = r5.mAlreadyDelayed
            if (r6 != 0) goto L79
            int r6 = r5.mActivationDelay
            if (r6 <= 0) goto L79
            android.view.View r7 = r5.mTarget
            androidx.core.widget.AutoScrollHelper$ScrollAnimationRunnable r0 = r5.mRunnable
            long r3 = (long) r6
            java.util.WeakHashMap r6 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            r7.postOnAnimationDelayed(r0, r3)
            goto L7e
        L79:
            androidx.core.widget.AutoScrollHelper$ScrollAnimationRunnable r6 = r5.mRunnable
            r6.run()
        L7e:
            r5.mAlreadyDelayed = r2
        L80:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.AutoScrollHelper.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    public final void requestStop() {
        int i = 0;
        if (this.mNeedsReset) {
            this.mAnimating = false;
            return;
        }
        ClampedScroller clampedScroller = this.mScroller;
        clampedScroller.getClass();
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        int i2 = (int) (currentAnimationTimeMillis - clampedScroller.mStartTime);
        int i3 = clampedScroller.mRampDownDuration;
        if (i2 > i3) {
            i = i3;
        } else if (i2 >= 0) {
            i = i2;
        }
        clampedScroller.mEffectiveRampDown = i;
        clampedScroller.mStopValue = clampedScroller.getValueAt(currentAnimationTimeMillis);
        clampedScroller.mStopTime = currentAnimationTimeMillis;
    }

    public abstract void scrollTargetBy(int i);

    public final boolean shouldAnimate() {
        ClampedScroller clampedScroller = this.mScroller;
        float f = clampedScroller.mTargetVelocityY;
        int abs = (int) (f / Math.abs(f));
        Math.abs(clampedScroller.mTargetVelocityX);
        return abs != 0 && canTargetScrollVertically(abs);
    }
}
