package androidx.dynamicanimation.animation;

import android.animation.ValueAnimator;
import android.os.SystemClock;
import androidx.collection.SimpleArrayMap;
import androidx.dynamicanimation.animation.AnimationHandler;

/* loaded from: classes.dex */
public final /* synthetic */ class AnimationHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ AnimationHandler f$0;

    /* JADX WARN: Removed duplicated region for block: B:16:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0051  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        long j;
        AnimationHandler.AnimationCallbackDispatcher animationCallbackDispatcher = this.f$0.mCallbackDispatcher;
        animationCallbackDispatcher.getClass();
        long jUptimeMillis = SystemClock.uptimeMillis();
        AnimationHandler animationHandler = animationCallbackDispatcher.this$0;
        animationHandler.getClass();
        long jUptimeMillis2 = SystemClock.uptimeMillis();
        for (int i = 0; i < animationHandler.mAnimationCallbacks.size(); i++) {
            AnimationHandler.AnimationFrameCallback animationFrameCallback = (AnimationHandler.AnimationFrameCallback) animationHandler.mAnimationCallbacks.get(i);
            if (animationFrameCallback != null) {
                SimpleArrayMap simpleArrayMap = animationHandler.mDelayedCallbackStartTime;
                Long l = (Long) simpleArrayMap.get(animationFrameCallback);
                if (l == null) {
                    DynamicAnimation dynamicAnimation = (DynamicAnimation) animationFrameCallback;
                    j = dynamicAnimation.mLastFrameTime;
                    if (j != 0) {
                    }
                } else if (l.longValue() < jUptimeMillis2) {
                    simpleArrayMap.remove(animationFrameCallback);
                    DynamicAnimation dynamicAnimation2 = (DynamicAnimation) animationFrameCallback;
                    j = dynamicAnimation2.mLastFrameTime;
                    if (j != 0) {
                        dynamicAnimation2.mLastFrameTime = jUptimeMillis;
                        dynamicAnimation2.setPropertyValue(dynamicAnimation2.mValue);
                    } else {
                        long j2 = jUptimeMillis - j;
                        dynamicAnimation2.mLastFrameTime = jUptimeMillis;
                        float f = dynamicAnimation2.getAnimationHandler().mDurationScale;
                        boolean zUpdateValueAndVelocity = dynamicAnimation2.updateValueAndVelocity(f == 0.0f ? 2147483647L : (long) (j2 / f));
                        float fMin = Math.min(dynamicAnimation2.mValue, dynamicAnimation2.mMaxValue);
                        dynamicAnimation2.mValue = fMin;
                        float fMax = Math.max(fMin, dynamicAnimation2.mMinValue);
                        dynamicAnimation2.mValue = fMax;
                        dynamicAnimation2.setPropertyValue(fMax);
                        if (zUpdateValueAndVelocity) {
                            dynamicAnimation2.endAnimationInternal(false);
                        }
                    }
                }
            }
        }
        if (animationHandler.mListDirty) {
            for (int size = animationHandler.mAnimationCallbacks.size() - 1; size >= 0; size--) {
                if (animationHandler.mAnimationCallbacks.get(size) == null) {
                    animationHandler.mAnimationCallbacks.remove(size);
                }
            }
            if (animationHandler.mAnimationCallbacks.size() == 0) {
                AnimationHandler.DurationScaleChangeListener33 durationScaleChangeListener33 = animationHandler.mDurationScaleChangeListener;
                ValueAnimator.unregisterDurationScaleChangeListener(durationScaleChangeListener33.mListener);
                durationScaleChangeListener33.mListener = null;
            }
            animationHandler.mListDirty = false;
        }
        if (animationHandler.mAnimationCallbacks.size() > 0) {
            animationHandler.mScheduler.postFrameCallback(animationHandler.mRunnable);
        }
    }
}
