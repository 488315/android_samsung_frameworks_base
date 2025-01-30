package com.android.server.display;

import android.util.FloatProperty;
import android.view.Choreographer;
import com.android.server.power.Slog;

/* loaded from: classes2.dex */
public class RampAnimator {
  public float mAnimatedValue;
  public boolean mAnimating;
  public float mAnimationDecreaseMaxTimeSecs;
  public float mAnimationIncreaseMaxTimeSecs;
  public float mCurrentValue;
  public boolean mFirstTime = true;
  public long mLastFrameTimeNanos;
  public final Object mObject;
  public final FloatProperty mProperty;
  public float mRate;
  public float mRateAtHbm;
  public float mTarget;
  public float mTargetValue;

  public interface Listener {
    void onAnimationEnd();
  }

  public RampAnimator(Object obj, FloatProperty floatProperty) {
    this.mObject = obj;
    this.mProperty = floatProperty;
  }

  public void setAnimationTimeLimits(long j, long j2) {
    float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    this.mAnimationIncreaseMaxTimeSecs = j > 0 ? j / 1000.0f : 0.0f;
    if (j2 > 0) {
      f = j2 / 1000.0f;
    }
    this.mAnimationDecreaseMaxTimeSecs = f;
  }

  /* JADX WARN: Removed duplicated region for block: B:32:0x0071  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean setAnimationTarget(float f, float f2, float f3, long j) {
    boolean z;
    float f4;
    float f5;
    boolean z2;
    if (this.mTarget != f) {
      this.mTarget = f;
      z = true;
    } else {
      z = false;
    }
    boolean z3 = this.mFirstTime;
    if (z3 || f2 <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      if (!z3 && f == this.mCurrentValue && !z) {
        return false;
      }
      this.mFirstTime = false;
      this.mRate = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      this.mRateAtHbm = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      this.mTargetValue = f;
      this.mCurrentValue = f;
      setPropertyValue(f);
      this.mAnimating = false;
      return true;
    }
    float f6 = this.mCurrentValue;
    if (f > f6) {
      f4 = this.mAnimationIncreaseMaxTimeSecs;
      if (f4 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON && (f - f6) / f2 > f4) {
        f5 = f - f6;
        f2 = f5 / f4;
        z2 = this.mAnimating;
        if (z2
            || f2 != this.mRate
            || f3 != this.mRateAtHbm
            || ((f <= f6 && f6 <= this.mTargetValue) || (this.mTargetValue <= f6 && f6 <= f))) {
          this.mRate = f2;
          this.mRateAtHbm = f3;
        }
        boolean z4 = this.mTargetValue != f;
        this.mTargetValue = f;
        if (!z2 && f != f6) {
          this.mAnimating = true;
          this.mAnimatedValue = f6;
          this.mLastFrameTimeNanos = j;
        }
        return z4;
      }
    }
    if (f < f6) {
      f4 = this.mAnimationDecreaseMaxTimeSecs;
      if (f4 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON && (f6 - f) / f2 > f4) {
        f5 = f6 - f;
        f2 = f5 / f4;
      }
    }
    z2 = this.mAnimating;
    if (z2) {}
    this.mRate = f2;
    this.mRateAtHbm = f3;
    if (this.mTargetValue != f) {}
    this.mTargetValue = f;
    if (!z2) {
      this.mAnimating = true;
      this.mAnimatedValue = f6;
      this.mLastFrameTimeNanos = j;
    }
    return z4;
  }

  public boolean isAnimating() {
    return this.mAnimating;
  }

  public float getTarget() {
    return this.mTarget;
  }

  public float getCurrentValue() {
    return this.mCurrentValue;
  }

  public final void setPropertyValue(float f) {
    this.mProperty.setValue(this.mObject, f);
  }

  public void performNextAnimationStep(long j) {
    float f = (j - this.mLastFrameTimeNanos) * 1.0E-9f;
    if (f >= 1.0f) {
      Slog.m72d("RampAnimator", "Choreographer callback time out: " + f + "s");
    }
    this.mLastFrameTimeNanos = j;
    float f2 = this.mRate;
    if (this.mTargetValue < this.mCurrentValue
        && this.mAnimatedValue > 1.0f
        && !Float.isNaN(this.mRateAtHbm)) {
      f2 = this.mRateAtHbm;
    }
    float f3 = (f * f2) / 1.0f;
    float f4 = this.mTargetValue;
    if (f4 > this.mCurrentValue) {
      this.mAnimatedValue = Math.min(this.mAnimatedValue + f3, f4);
    } else {
      this.mAnimatedValue = Math.max(this.mAnimatedValue - f3, f4);
    }
    float f5 = this.mCurrentValue;
    float f6 = this.mAnimatedValue;
    this.mCurrentValue = f6;
    if (f5 != f6) {
      setPropertyValue(f6);
    }
    if (this.mTargetValue == this.mCurrentValue) {
      this.mAnimating = false;
    }
  }

  public class DualRampAnimator {
    public boolean mAwaitingCallback;
    public final RampAnimator mFirst;
    public Listener mListener;
    public final RampAnimator mSecond;
    public final Runnable mAnimationCallback =
        new Runnable() { // from class: com.android.server.display.RampAnimator.DualRampAnimator.1
          @Override // java.lang.Runnable
          public void run() {
            long frameTimeNanos = DualRampAnimator.this.mChoreographer.getFrameTimeNanos();
            DualRampAnimator.this.mFirst.performNextAnimationStep(frameTimeNanos);
            DualRampAnimator.this.mSecond.performNextAnimationStep(frameTimeNanos);
            if (DualRampAnimator.this.isAnimating()) {
              DualRampAnimator.this.postAnimationCallback();
              return;
            }
            if (DualRampAnimator.this.mListener != null) {
              DualRampAnimator.this.mListener.onAnimationEnd();
            }
            DualRampAnimator.this.mAwaitingCallback = false;
          }
        };
    public final Choreographer mChoreographer = Choreographer.getInstance();

    public DualRampAnimator(Object obj, FloatProperty floatProperty, FloatProperty floatProperty2) {
      this.mFirst = new RampAnimator(obj, floatProperty);
      this.mSecond = new RampAnimator(obj, floatProperty2);
    }

    public void setAnimationTimeLimits(long j, long j2) {
      this.mFirst.setAnimationTimeLimits(j, j2);
      this.mSecond.setAnimationTimeLimits(j, j2);
    }

    public boolean animateTo(float f, float f2, float f3, float f4) {
      long nanoTime = System.nanoTime();
      boolean animationTarget =
          this.mFirst.setAnimationTarget(f, f3, f4, nanoTime)
              | this.mSecond.setAnimationTarget(f2, f3, f4, nanoTime);
      boolean isAnimating = isAnimating();
      boolean z = this.mAwaitingCallback;
      if (isAnimating != z) {
        if (isAnimating) {
          this.mAwaitingCallback = true;
          postAnimationCallback();
        } else if (z) {
          Listener listener = this.mListener;
          if (listener != null) {
            listener.onAnimationEnd();
          }
          this.mChoreographer.removeCallbacks(1, this.mAnimationCallback, null);
          this.mAwaitingCallback = false;
        }
      }
      return animationTarget;
    }

    public void setListener(Listener listener) {
      this.mListener = listener;
    }

    public boolean isAnimating() {
      return this.mFirst.isAnimating() || this.mSecond.isAnimating();
    }

    public final void postAnimationCallback() {
      this.mChoreographer.postCallback(1, this.mAnimationCallback, null);
    }

    public float getTarget() {
      return this.mFirst.getTarget();
    }

    public float getCurrentValue() {
      return this.mFirst.getCurrentValue();
    }
  }
}
