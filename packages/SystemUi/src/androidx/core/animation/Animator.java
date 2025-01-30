package androidx.core.animation;

import androidx.core.animation.AnimationHandler;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class Animator implements Cloneable {
    public ArrayList mListeners = null;
    public ArrayList mPauseListeners = null;
    public ArrayList mUpdateListeners = null;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface AnimatorListener {
        void onAnimationCancel();

        default void onAnimationEnd(Animator animator) {
            onAnimationEnd$1(animator);
        }

        void onAnimationEnd$1(Animator animator);

        void onAnimationRepeat();

        void onAnimationStart$1();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface AnimatorUpdateListener {
        void onAnimationUpdate(Animator animator);
    }

    public static void addAnimationCallback(AnimationHandler.AnimationFrameCallback animationFrameCallback) {
        AnimationHandler animationHandler = AnimationHandler.getInstance();
        ArrayList arrayList = animationHandler.mAnimationCallbacks;
        int size = arrayList.size();
        AnimationHandler.AnimationFrameCallbackProvider animationFrameCallbackProvider = animationHandler.mProvider;
        if (size == 0) {
            animationFrameCallbackProvider.postFrameCallback();
        }
        if (!arrayList.contains(animationFrameCallback)) {
            arrayList.add(animationFrameCallback);
        }
        animationFrameCallbackProvider.onNewCallbackAdded();
    }

    public final void addListener(AnimatorListener animatorListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(animatorListener);
    }

    public final void addUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        if (this.mUpdateListeners == null) {
            this.mUpdateListeners = new ArrayList();
        }
        this.mUpdateListeners.add(animatorUpdateListener);
    }

    public abstract long getDuration();

    public abstract long getStartDelay();

    public long getTotalDuration() {
        long duration = getDuration();
        if (duration == -1) {
            return -1L;
        }
        return getStartDelay() + duration;
    }

    public boolean isInitialized() {
        return true;
    }

    public abstract boolean isRunning();

    public boolean isStarted() {
        return isRunning();
    }

    public boolean pulseAnimationFrame(long j) {
        return false;
    }

    public void reverse() {
        throw new IllegalStateException("Reverse is not supported");
    }

    public abstract Animator setDuration(long j);

    public abstract void setInterpolator(Interpolator interpolator);

    public void startWithoutPulsing(boolean z) {
        if (z) {
            reverse();
        } else {
            start();
        }
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Animator mo305clone() {
        try {
            Animator animator = (Animator) super.clone();
            if (this.mListeners != null) {
                animator.mListeners = new ArrayList(this.mListeners);
            }
            if (this.mPauseListeners != null) {
                animator.mPauseListeners = new ArrayList(this.mPauseListeners);
            }
            return animator;
        } catch (CloneNotSupportedException unused) {
            throw new AssertionError();
        }
    }

    public void skipToEndValue(boolean z) {
    }

    public void cancel() {
    }

    public void end() {
    }

    public void start() {
    }
}
