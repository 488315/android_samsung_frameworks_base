package androidx.core.animation;

import android.view.Choreographer;
import androidx.core.animation.AnimationHandler;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class Animator implements Cloneable {
    public ArrayList mListeners = null;
    public ArrayList mPauseListeners = null;
    public ArrayList mUpdateListeners = null;

    public interface AnimatorListener {
        void onAnimationCancel(Animator animator);

        void onAnimationEnd(Animator animator);

        default void onAnimationEnd(Animator animator, boolean z) {
            onAnimationEnd(animator);
        }

        void onAnimationRepeat(Animator animator);

        void onAnimationStart(Animator animator);

        default void onAnimationStart(Animator animator, boolean z) {
            onAnimationStart(animator);
        }
    }

    public interface AnimatorUpdateListener {
        void onAnimationUpdate(Animator animator);
    }

    public static void addAnimationCallback(AnimationHandler.AnimationFrameCallback animationFrameCallback) {
        AnimationHandler animationHandler = AnimationHandler.getInstance();
        int size = animationHandler.mAnimationCallbacks.size();
        AnimationHandler.AnimationFrameCallbackProvider animationFrameCallbackProvider = animationHandler.mProvider;
        if (size == 0) {
            AnimationHandler.FrameCallbackProvider16 frameCallbackProvider16 = (AnimationHandler.FrameCallbackProvider16) animationFrameCallbackProvider;
            frameCallbackProvider16.getClass();
            Choreographer.getInstance().postFrameCallback(frameCallbackProvider16);
        }
        if (!animationHandler.mAnimationCallbacks.contains(animationFrameCallback)) {
            animationHandler.mAnimationCallbacks.add(animationFrameCallback);
        }
        animationFrameCallbackProvider.getClass();
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

    public final void removeAllListeners() {
        ArrayList arrayList = this.mListeners;
        if (arrayList != null) {
            arrayList.clear();
            this.mListeners = null;
        }
        ArrayList arrayList2 = this.mPauseListeners;
        if (arrayList2 != null) {
            arrayList2.clear();
            this.mPauseListeners = null;
        }
    }

    public final void removeListener(AnimatorListener animatorListener) {
        ArrayList arrayList = this.mListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(animatorListener);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
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
    public Animator mo892clone() {
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

    public void cancel() {
    }

    public void end() {
    }

    public void start() {
    }

    public void skipToEndValue(boolean z) {
    }
}
