package androidx.dynamicanimation.animation;

import android.animation.ValueAnimator;
import android.os.Looper;
import android.os.SystemClock;
import android.view.Choreographer;
import androidx.collection.SimpleArrayMap;
import androidx.dynamicanimation.animation.AnimationHandler;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AnimationHandler {
    public static final ThreadLocal sAnimatorHandler = new ThreadLocal();
    public DurationScaleChangeListener33 mDurationScaleChangeListener;
    public final FrameCallbackScheduler mScheduler;
    public final SimpleArrayMap mDelayedCallbackStartTime = new SimpleArrayMap();
    public final ArrayList mAnimationCallbacks = new ArrayList();
    public final AnimationCallbackDispatcher mCallbackDispatcher = new AnimationCallbackDispatcher();
    public final AnimationHandler$$ExternalSyntheticLambda0 mRunnable = new Runnable() { // from class: androidx.dynamicanimation.animation.AnimationHandler$$ExternalSyntheticLambda0
        /* JADX WARN: Removed duplicated region for block: B:13:0x0042  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0088 A[SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void run() {
            ArrayList arrayList;
            boolean z;
            AnimationHandler.AnimationCallbackDispatcher animationCallbackDispatcher = AnimationHandler.this.mCallbackDispatcher;
            animationCallbackDispatcher.getClass();
            long uptimeMillis = SystemClock.uptimeMillis();
            AnimationHandler animationHandler = AnimationHandler.this;
            animationHandler.getClass();
            long uptimeMillis2 = SystemClock.uptimeMillis();
            int i = 0;
            while (true) {
                arrayList = animationHandler.mAnimationCallbacks;
                if (i >= arrayList.size()) {
                    break;
                }
                AnimationHandler.AnimationFrameCallback animationFrameCallback = (AnimationHandler.AnimationFrameCallback) arrayList.get(i);
                if (animationFrameCallback != null) {
                    SimpleArrayMap simpleArrayMap = animationHandler.mDelayedCallbackStartTime;
                    Long l = (Long) simpleArrayMap.get(animationFrameCallback);
                    if (l != null) {
                        if (l.longValue() < uptimeMillis2) {
                            simpleArrayMap.remove(animationFrameCallback);
                        } else {
                            z = false;
                            if (!z) {
                                DynamicAnimation dynamicAnimation = (DynamicAnimation) animationFrameCallback;
                                long j = dynamicAnimation.mLastFrameTime;
                                if (j == 0) {
                                    dynamicAnimation.mLastFrameTime = uptimeMillis;
                                    dynamicAnimation.setPropertyValue(dynamicAnimation.mValue);
                                } else {
                                    long j2 = uptimeMillis - j;
                                    dynamicAnimation.mLastFrameTime = uptimeMillis;
                                    float f = DynamicAnimation.getAnimationHandler().mDurationScale;
                                    boolean updateValueAndVelocity = dynamicAnimation.updateValueAndVelocity(f == 0.0f ? 2147483647L : (long) (j2 / f));
                                    float min = Math.min(dynamicAnimation.mValue, dynamicAnimation.mMaxValue);
                                    dynamicAnimation.mValue = min;
                                    float max = Math.max(min, dynamicAnimation.mMinValue);
                                    dynamicAnimation.mValue = max;
                                    dynamicAnimation.setPropertyValue(max);
                                    if (updateValueAndVelocity) {
                                        dynamicAnimation.endAnimationInternal(false);
                                    }
                                }
                            }
                        }
                    }
                    z = true;
                    if (!z) {
                    }
                }
                i++;
            }
            if (animationHandler.mListDirty) {
                int size = arrayList.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    } else if (arrayList.get(size) == null) {
                        arrayList.remove(size);
                    }
                }
                if (arrayList.size() == 0) {
                    AnimationHandler.DurationScaleChangeListener33 durationScaleChangeListener33 = animationHandler.mDurationScaleChangeListener;
                    ValueAnimator.unregisterDurationScaleChangeListener(durationScaleChangeListener33.mListener);
                    durationScaleChangeListener33.mListener = null;
                }
                animationHandler.mListDirty = false;
            }
            if (arrayList.size() > 0) {
                animationHandler.mScheduler.postFrameCallback(animationHandler.mRunnable);
            }
        }
    };
    public boolean mListDirty = false;
    public float mDurationScale = 1.0f;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimationCallbackDispatcher {
        private AnimationCallbackDispatcher() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface AnimationFrameCallback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DurationScaleChangeListener33 {
        public C0181xf577eeeb mListener;

        public DurationScaleChangeListener33() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FrameCallbackScheduler16 implements FrameCallbackScheduler {
        public final Choreographer mChoreographer = Choreographer.getInstance();
        public final Looper mLooper = Looper.myLooper();

        @Override // androidx.dynamicanimation.animation.FrameCallbackScheduler
        public final boolean isCurrentThread() {
            return Thread.currentThread() == this.mLooper.getThread();
        }

        @Override // androidx.dynamicanimation.animation.FrameCallbackScheduler
        public final void postFrameCallback(final AnimationHandler$$ExternalSyntheticLambda0 animationHandler$$ExternalSyntheticLambda0) {
            this.mChoreographer.postFrameCallback(new Choreographer.FrameCallback() { // from class: androidx.dynamicanimation.animation.AnimationHandler$FrameCallbackScheduler16$$ExternalSyntheticLambda0
                @Override // android.view.Choreographer.FrameCallback
                public final void doFrame(long j) {
                    animationHandler$$ExternalSyntheticLambda0.run();
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.dynamicanimation.animation.AnimationHandler$$ExternalSyntheticLambda0] */
    public AnimationHandler(FrameCallbackScheduler frameCallbackScheduler) {
        this.mScheduler = frameCallbackScheduler;
    }
}
