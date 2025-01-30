package com.android.systemui.wallpaper.tilt;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AnimationListenerAdapterProfiler implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    public long mLastFrameTime;
    public float mLongestFrameFraction;
    public long mLongestFrameNum;
    public long mLongestFrameTime;
    public boolean mIsCanceled = false;
    public boolean mIsProfilingStarted = false;
    public String mProfileTag = "";
    public long mAnimStartDelayElapsed = 0;
    public int mFrameCnt = 0;
    public long mAnimElapsed = System.nanoTime();
    public float mStartFraction = -1.0f;
    public String mAnimInfo = "";

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        this.mIsCanceled = true;
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        if (this.mIsProfilingStarted) {
            long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.mAnimElapsed);
            Log.i("AnimationListenerAdapterProfiler", "[" + this.mProfileTag + "] onAnimationEnd : " + this.mAnimInfo + " onStart-onEnd took " + millis + " ms / duration diff= " + (millis - animator.getDuration()) + " ms / " + this.mFrameCnt + " frames " + ((this.mFrameCnt * 1000.0f) / millis) + " fps / StartFraction = " + this.mStartFraction + "\nLongest Frame [num : " + this.mLongestFrameNum + " time : " + this.mLongestFrameTime + " fracton : " + this.mLongestFrameFraction);
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        if (this.mIsProfilingStarted) {
            long nanoTime = System.nanoTime();
            this.mAnimElapsed = nanoTime;
            this.mStartFraction = 0.0f;
            this.mLastFrameTime = -1L;
            this.mLongestFrameTime = 0L;
            this.mLongestFrameNum = 0L;
            this.mLongestFrameFraction = 0.0f;
            long millis = TimeUnit.NANOSECONDS.toMillis(nanoTime - this.mAnimStartDelayElapsed);
            this.mAnimInfo = "(duration= " + animator.getDuration() + " / delay= " + animator.getStartDelay() + ")";
            Log.i("AnimationListenerAdapterProfiler", "[" + this.mProfileTag + "] onAnimationStart : " + this.mAnimInfo + " StartDelay took " + millis + " ms / delay diff= " + (millis - animator.getStartDelay()) + " ms. ");
            this.mFrameCnt = 0;
        }
        this.mIsCanceled = false;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        if (this.mIsProfilingStarted) {
            long nanoTime = System.nanoTime();
            if (this.mFrameCnt == 0) {
                this.mStartFraction = valueAnimator.getAnimatedFraction();
            }
            long j = this.mLastFrameTime;
            if (j > 0) {
                long j2 = nanoTime - j;
                if (j2 > this.mLongestFrameTime) {
                    this.mLongestFrameTime = j2;
                    this.mLongestFrameFraction = valueAnimator.getAnimatedFraction();
                    this.mLongestFrameNum = this.mFrameCnt;
                }
            }
            this.mLastFrameTime = nanoTime;
            this.mFrameCnt++;
        }
    }

    public final void startAnimationProfile(String str) {
        this.mIsProfilingStarted = true;
        if (str == null) {
            str = "Unknown";
        }
        this.mProfileTag = str;
        this.mAnimStartDelayElapsed = System.nanoTime();
        ExifInterface$$ExternalSyntheticOutline0.m36m(new StringBuilder("["), this.mProfileTag, "] startAnimationProfile", "AnimationListenerAdapterProfiler");
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationRepeat(Animator animator) {
    }
}
