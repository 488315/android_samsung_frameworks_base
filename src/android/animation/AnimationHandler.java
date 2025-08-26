package android.animation;

import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.view.Choreographer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class AnimationHandler {
    private static final boolean LOCAL_LOGV = false;
    private static final String TAG = "AnimationHandler";
    private long mEndAnimationFrameVsyncId;
    private long mLastAnimationFrameVsyncId;
    private ArrayList<Runnable> mPendingEndAnimationListeners;
    private AnimationFrameCallbackProvider mProvider;
    private static boolean sAnimatorPausingEnabled = isPauseBgAnimationsEnabledInSystemProperties();
    private static boolean sOverrideAnimatorPausingSystemProperty = false;
    public static final ThreadLocal<AnimationHandler> sAnimatorHandler = new ThreadLocal<>();
    private static AnimationHandler sTestHandler = null;
    private final ArrayMap<AnimationFrameCallback, Long> mDelayedCallbackStartTime = new ArrayMap<>();
    private final ArrayList<AnimationFrameCallback> mAnimationCallbacks = new ArrayList<>();
    private final ArrayList<AnimationFrameCallback> mCommitCallbacks = new ArrayList<>();
    private final ArrayList<Animator> mPausedAnimators = new ArrayList<>();
    private final ArrayList<WeakReference<Object>> mAnimatorRequestors = new ArrayList<>();
    private final Choreographer.FrameCallback mFrameCallback = new Choreographer.FrameCallback() { // from class: android.animation.AnimationHandler.1
        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            AnimationHandler animationHandler = AnimationHandler.this;
            animationHandler.doAnimationFrame(animationHandler.getProvider().getFrameTime());
            if (AnimationHandler.this.mAnimationCallbacks.size() > 0) {
                AnimationHandler.this.getProvider().postFrameCallback(this);
            }
        }
    };
    private boolean mListDirty = false;
    private Choreographer.FrameCallback mPauser = new Choreographer.FrameCallback() { // from class: android.animation.AnimationHandler$$ExternalSyntheticLambda0
        @Override // android.view.Choreographer.FrameCallback
        public final void doFrame(long j) {
            this.f$0.lambda$new$0(j);
        }
    };

    public interface AnimationFrameCallback {
        void commitAnimationFrame(long j);

        boolean doAnimationFrame(long j);
    }

    public interface AnimationFrameCallbackProvider {
        long getFrameDelay();

        long getFrameTime();

        void postCommitCallback(Runnable runnable);

        void postFrameCallback(Choreographer.FrameCallback frameCallback);

        void setFrameDelay(long j);
    }

    public long getMaxAnimationCallbackDuration() {
        long j = -1;
        for (int i = 0; i < this.mAnimationCallbacks.size(); i++) {
            AnimationFrameCallback animationFrameCallback = this.mAnimationCallbacks.get(i);
            if (animationFrameCallback instanceof ValueAnimator) {
                long totalDuration = ((ValueAnimator) animationFrameCallback).getTotalDuration();
                if (totalDuration > j) {
                    j = totalDuration;
                }
            }
        }
        return j;
    }

    public static AnimationHandler getInstance() {
        AnimationHandler animationHandler = sTestHandler;
        if (animationHandler != null) {
            return animationHandler;
        }
        ThreadLocal<AnimationHandler> threadLocal = sAnimatorHandler;
        AnimationHandler animationHandler2 = threadLocal.get();
        if (animationHandler2 != null) {
            return animationHandler2;
        }
        AnimationHandler animationHandler3 = new AnimationHandler();
        threadLocal.set(animationHandler3);
        return animationHandler3;
    }

    public static AnimationHandler setTestHandler(AnimationHandler animationHandler) {
        AnimationHandler animationHandler2 = sTestHandler;
        sTestHandler = animationHandler;
        return animationHandler2;
    }

    private static boolean isPauseBgAnimationsEnabledInSystemProperties() {
        return sOverrideAnimatorPausingSystemProperty ? sAnimatorPausingEnabled : SystemProperties.getBoolean("framework.pause_bg_animations.enabled", true);
    }

    public static void setAnimatorPausingEnabled(boolean z) {
        sAnimatorPausingEnabled = z;
    }

    public static void setOverrideAnimatorPausingSystemProperty(boolean z) {
        sOverrideAnimatorPausingSystemProperty = z;
    }

    public static void removeRequestor(Object obj) {
        getInstance().requestAnimatorsEnabledImpl(false, obj);
    }

    public static void requestAnimatorsEnabled(boolean z, Object obj) {
        getInstance().requestAnimatorsEnabledImpl(z, obj);
    }

    private void requestAnimatorsEnabledImpl(boolean z, Object obj) {
        boolean zIsEmpty = this.mAnimatorRequestors.isEmpty();
        setAnimatorPausingEnabled(isPauseBgAnimationsEnabledInSystemProperties());
        synchronized (this.mAnimatorRequestors) {
            if (z) {
                WeakReference<Object> weakReference = null;
                for (int size = this.mAnimatorRequestors.size() - 1; size >= 0; size--) {
                    WeakReference<Object> weakReference2 = this.mAnimatorRequestors.get(size);
                    Object obj2 = weakReference2.get();
                    if (obj2 == obj) {
                        weakReference = weakReference2;
                    } else if (obj2 == null) {
                        this.mAnimatorRequestors.remove(size);
                    }
                }
                if (weakReference == null) {
                    this.mAnimatorRequestors.add(new WeakReference<>(obj));
                }
            } else {
                for (int size2 = this.mAnimatorRequestors.size() - 1; size2 >= 0; size2--) {
                    Object obj3 = this.mAnimatorRequestors.get(size2).get();
                    if (obj3 == obj || obj3 == null) {
                        this.mAnimatorRequestors.remove(size2);
                    }
                }
            }
        }
        if (!sAnimatorPausingEnabled) {
            resumeAnimators();
            return;
        }
        boolean zIsEmpty2 = this.mAnimatorRequestors.isEmpty();
        if (zIsEmpty != zIsEmpty2) {
            if (!zIsEmpty2) {
                resumeAnimators();
            } else {
                Choreographer.getInstance().postFrameCallbackDelayed(this.mPauser, Animator.getBackgroundPauseDelay());
            }
        }
    }

    private void resumeAnimators() {
        Choreographer.getInstance().removeFrameCallback(this.mPauser);
        for (int size = this.mPausedAnimators.size() - 1; size >= 0; size--) {
            this.mPausedAnimators.get(size).resume();
        }
        this.mPausedAnimators.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(long j) {
        if (this.mAnimatorRequestors.size() > 0) {
            return;
        }
        for (int i = 0; i < this.mAnimationCallbacks.size(); i++) {
            Object obj = (AnimationFrameCallback) this.mAnimationCallbacks.get(i);
            if (obj instanceof Animator) {
                Animator animator = (Animator) obj;
                if (animator.getTotalDuration() == -1 && !animator.isPaused()) {
                    this.mPausedAnimators.add(animator);
                    animator.pause();
                }
            }
        }
    }

    public void setProvider(AnimationFrameCallbackProvider animationFrameCallbackProvider) {
        if (animationFrameCallbackProvider == null) {
            this.mProvider = new MyFrameCallbackProvider();
        } else {
            this.mProvider = animationFrameCallbackProvider;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AnimationFrameCallbackProvider getProvider() {
        if (this.mProvider == null) {
            this.mProvider = new MyFrameCallbackProvider();
        }
        return this.mProvider;
    }

    public void addAnimationFrameCallback(AnimationFrameCallback animationFrameCallback, long j) {
        if (this.mAnimationCallbacks.size() == 0) {
            getProvider().postFrameCallback(this.mFrameCallback);
        }
        if (!this.mAnimationCallbacks.contains(animationFrameCallback)) {
            this.mAnimationCallbacks.add(animationFrameCallback);
        }
        if (j > 0) {
            this.mDelayedCallbackStartTime.put(animationFrameCallback, Long.valueOf(SystemClock.uptimeMillis() + j));
        }
    }

    public void addOneShotCommitCallback(AnimationFrameCallback animationFrameCallback) {
        if (this.mCommitCallbacks.contains(animationFrameCallback)) {
            return;
        }
        this.mCommitCallbacks.add(animationFrameCallback);
    }

    public void removeCallback(AnimationFrameCallback animationFrameCallback) {
        this.mCommitCallbacks.remove(animationFrameCallback);
        this.mDelayedCallbackStartTime.remove(animationFrameCallback);
        int iIndexOf = this.mAnimationCallbacks.indexOf(animationFrameCallback);
        if (iIndexOf >= 0) {
            this.mAnimationCallbacks.set(iIndexOf, null);
            this.mListDirty = true;
        }
    }

    public long getLastAnimationFrameVsyncId(long j) {
        if (j == this.mEndAnimationFrameVsyncId) {
            long j2 = this.mLastAnimationFrameVsyncId;
            if (j2 != 0) {
                return j2;
            }
        }
        return j;
    }

    public void postEndAnimationCallback(Runnable runnable) {
        if (this.mPendingEndAnimationListeners == null) {
            this.mPendingEndAnimationListeners = new ArrayList<>();
        }
        this.mPendingEndAnimationListeners.add(runnable);
        if (this.mPendingEndAnimationListeners.size() > 1) {
            return;
        }
        final Choreographer choreographer = Choreographer.getInstance();
        this.mLastAnimationFrameVsyncId = choreographer.getVsyncId();
        getProvider().postFrameCallback(new Choreographer.FrameCallback() { // from class: android.animation.AnimationHandler$$ExternalSyntheticLambda1
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                this.f$0.lambda$postEndAnimationCallback$1(choreographer, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postEndAnimationCallback$1(Choreographer choreographer, long j) {
        this.mEndAnimationFrameVsyncId = choreographer.getVsyncId();
        while (this.mPendingEndAnimationListeners.size() > 0) {
            this.mPendingEndAnimationListeners.remove(0).run();
        }
        this.mEndAnimationFrameVsyncId = 0L;
        this.mLastAnimationFrameVsyncId = 0L;
    }

    void removePendingEndAnimationCallback(Runnable runnable) {
        ArrayList<Runnable> arrayList = this.mPendingEndAnimationListeners;
        if (arrayList != null) {
            arrayList.remove(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAnimationFrame(long j) {
        long jUptimeMillis = SystemClock.uptimeMillis();
        int size = this.mAnimationCallbacks.size();
        for (int i = 0; i < size; i++) {
            final AnimationFrameCallback animationFrameCallback = this.mAnimationCallbacks.get(i);
            if (animationFrameCallback != null && isCallbackDue(animationFrameCallback, jUptimeMillis)) {
                animationFrameCallback.doAnimationFrame(j);
                if (this.mCommitCallbacks.contains(animationFrameCallback)) {
                    getProvider().postCommitCallback(new Runnable() { // from class: android.animation.AnimationHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AnimationHandler animationHandler = AnimationHandler.this;
                            animationHandler.commitAnimationFrame(animationFrameCallback, animationHandler.getProvider().getFrameTime());
                        }
                    });
                }
            }
        }
        cleanUpList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commitAnimationFrame(AnimationFrameCallback animationFrameCallback, long j) {
        if (this.mDelayedCallbackStartTime.containsKey(animationFrameCallback) || !this.mCommitCallbacks.contains(animationFrameCallback)) {
            return;
        }
        animationFrameCallback.commitAnimationFrame(j);
        this.mCommitCallbacks.remove(animationFrameCallback);
    }

    private boolean isCallbackDue(AnimationFrameCallback animationFrameCallback, long j) {
        Long l = this.mDelayedCallbackStartTime.get(animationFrameCallback);
        if (l == null) {
            return true;
        }
        if (l.longValue() >= j) {
            return false;
        }
        this.mDelayedCallbackStartTime.remove(animationFrameCallback);
        return true;
    }

    public static int getAnimationCount() {
        AnimationHandler animationHandler = sTestHandler;
        if (animationHandler == null) {
            animationHandler = sAnimatorHandler.get();
        }
        if (animationHandler == null) {
            return 0;
        }
        return animationHandler.getCallbackSize();
    }

    public static void setFrameDelay(long j) {
        getInstance().getProvider().setFrameDelay(j);
    }

    public static long getFrameDelay() {
        return getInstance().getProvider().getFrameDelay();
    }

    void autoCancelBasedOn(ObjectAnimator objectAnimator) {
        for (int size = this.mAnimationCallbacks.size() - 1; size >= 0; size--) {
            AnimationFrameCallback animationFrameCallback = this.mAnimationCallbacks.get(size);
            if (animationFrameCallback != null && objectAnimator.shouldAutoCancel(animationFrameCallback)) {
                ((Animator) this.mAnimationCallbacks.get(size)).cancel();
            }
        }
    }

    private void cleanUpList() {
        if (this.mListDirty) {
            for (int size = this.mAnimationCallbacks.size() - 1; size >= 0; size--) {
                if (this.mAnimationCallbacks.get(size) == null) {
                    this.mAnimationCallbacks.remove(size);
                }
            }
            this.mListDirty = false;
        }
    }

    private int getCallbackSize() {
        int i = 0;
        for (int size = this.mAnimationCallbacks.size() - 1; size >= 0; size--) {
            if (this.mAnimationCallbacks.get(size) != null) {
                i++;
            }
        }
        return i;
    }

    private class MyFrameCallbackProvider implements AnimationFrameCallbackProvider {
        final Choreographer mChoreographer;

        private MyFrameCallbackProvider(AnimationHandler animationHandler) {
            this.mChoreographer = Choreographer.getInstance();
        }

        @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
        public void postFrameCallback(Choreographer.FrameCallback frameCallback) {
            this.mChoreographer.postFrameCallback(frameCallback);
        }

        @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
        public void postCommitCallback(Runnable runnable) {
            this.mChoreographer.postCallback(4, runnable, null);
        }

        @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
        public long getFrameTime() {
            return this.mChoreographer.getFrameTime();
        }

        @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
        public long getFrameDelay() {
            return Choreographer.getFrameDelay();
        }

        @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
        public void setFrameDelay(long j) {
            Choreographer.setFrameDelay(j);
        }
    }
}
