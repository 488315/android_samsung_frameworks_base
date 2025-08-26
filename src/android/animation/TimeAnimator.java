package android.animation;

import android.view.animation.AnimationUtils;

/* loaded from: classes.dex */
public class TimeAnimator extends ValueAnimator {
    private TimeListener mListener;
    private long mPreviousTime = -1;

    public interface TimeListener {
        void onTimeUpdate(TimeAnimator timeAnimator, long j, long j2);
    }

    @Override // android.animation.ValueAnimator
    void animateValue(float f) {
    }

    @Override // android.animation.ValueAnimator
    void initAnimation() {
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    public void start() {
        this.mPreviousTime = -1L;
        super.start();
    }

    @Override // android.animation.ValueAnimator
    boolean animateBasedOnTime(long j) {
        if (this.mListener == null) {
            return false;
        }
        long j2 = j - this.mStartTime;
        long j3 = this.mPreviousTime;
        long j4 = j3 >= 0 ? j - j3 : 0L;
        this.mPreviousTime = j;
        this.mListener.onTimeUpdate(this, j2, j4);
        return false;
    }

    @Override // android.animation.ValueAnimator
    public void setCurrentPlayTime(long j) {
        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        this.mStartTime = Math.max(this.mStartTime, jCurrentAnimationTimeMillis - j);
        this.mStartTimeCommitted = true;
        animateBasedOnTime(jCurrentAnimationTimeMillis);
    }

    public void setTimeListener(TimeListener timeListener) {
        this.mListener = timeListener;
    }
}
