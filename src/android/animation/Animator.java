package android.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.ConstantState;
import android.os.Trace;
import android.util.LongArray;
import com.android.internal.content.NativeLibraryHelper;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public abstract class Animator implements Cloneable {
    public static final long DURATION_INFINITE = -1;
    private static long sBackgroundPauseDelay = 1000;
    static boolean sPostNotifyEndListenerEnabled;
    private AnimatorConstantState mConstantState;
    private Runnable mPendingEndCallback;
    ArrayList<AnimatorListener> mListeners = null;
    ArrayList<AnimatorPauseListener> mPauseListeners = null;
    boolean mPaused = false;
    int mChangingConfigurations = 0;
    private AtomicReference<Object[]> mCachedList = new AtomicReference<>();
    boolean mStartListenersCalled = false;

    public interface AnimatorPauseListener {
        void onAnimationPause(Animator animator);

        void onAnimationResume(Animator animator);
    }

    void animateSkipToEnds(long j, long j2) {
    }

    void animateValuesInRange(long j, long j2) {
    }

    public boolean canReverse() {
        return false;
    }

    public void cancel() {
    }

    public void end() {
    }

    public abstract long getDuration();

    public TimeInterpolator getInterpolator() {
        return null;
    }

    public abstract long getStartDelay();

    boolean isInitialized() {
        return true;
    }

    public abstract boolean isRunning();

    boolean pulseAnimationFrame(long j) {
        return false;
    }

    public void setAllowRunningAsynchronously(boolean z) {
    }

    public abstract Animator setDuration(long j);

    public abstract void setInterpolator(TimeInterpolator timeInterpolator);

    public abstract void setStartDelay(long j);

    public void setTarget(Object obj) {
    }

    public void setupEndValues() {
    }

    public void setupStartValues() {
    }

    void skipToEndValue(boolean z) {
    }

    public void start() {
    }

    public static void setBackgroundPauseDelay(long j) {
        sBackgroundPauseDelay = j;
    }

    public static long getBackgroundPauseDelay() {
        return sBackgroundPauseDelay;
    }

    public static void setAnimatorPausingEnabled(boolean z) {
        AnimationHandler.setAnimatorPausingEnabled(z);
        AnimationHandler.setOverrideAnimatorPausingSystemProperty(!z);
    }

    public static void setPostNotifyEndListenerEnabled(boolean z) {
        sPostNotifyEndListenerEnabled = z;
    }

    public static boolean isPostNotifyEndListenerEnabled() {
        return sPostNotifyEndListenerEnabled;
    }

    public void pause() {
        if ((isStarted() || this.mStartListenersCalled) && !this.mPaused) {
            this.mPaused = true;
            notifyPauseListeners(AnimatorCaller.ON_PAUSE);
        }
    }

    public void resume() {
        if (this.mPaused) {
            this.mPaused = false;
            notifyPauseListeners(AnimatorCaller.ON_RESUME);
        }
    }

    public boolean isPaused() {
        return this.mPaused;
    }

    public long getTotalDuration() {
        long duration = getDuration();
        if (duration == -1) {
            return -1L;
        }
        return getStartDelay() + duration;
    }

    public boolean isStarted() {
        return isRunning();
    }

    public void addListener(AnimatorListener animatorListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(animatorListener);
    }

    public void removeListener(AnimatorListener animatorListener) {
        ArrayList<AnimatorListener> arrayList = this.mListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(animatorListener);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
    }

    public ArrayList<AnimatorListener> getListeners() {
        return this.mListeners;
    }

    public void addPauseListener(AnimatorPauseListener animatorPauseListener) {
        if (this.mPauseListeners == null) {
            this.mPauseListeners = new ArrayList<>();
        }
        this.mPauseListeners.add(animatorPauseListener);
    }

    public void removePauseListener(AnimatorPauseListener animatorPauseListener) {
        ArrayList<AnimatorPauseListener> arrayList = this.mPauseListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(animatorPauseListener);
        if (this.mPauseListeners.size() == 0) {
            this.mPauseListeners = null;
        }
    }

    public void removeAllListeners() {
        ArrayList<AnimatorListener> arrayList = this.mListeners;
        if (arrayList != null) {
            arrayList.clear();
            this.mListeners = null;
        }
        ArrayList<AnimatorPauseListener> arrayList2 = this.mPauseListeners;
        if (arrayList2 != null) {
            arrayList2.clear();
            this.mPauseListeners = null;
        }
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public void setChangingConfigurations(int i) {
        this.mChangingConfigurations = i;
    }

    public void appendChangingConfigurations(int i) {
        this.mChangingConfigurations = i | this.mChangingConfigurations;
    }

    public ConstantState<Animator> createConstantState() {
        return new AnimatorConstantState(this);
    }

    @Override // 
    /* renamed from: clone */
    public Animator mo76clone() {
        try {
            Animator animator = (Animator) super.clone();
            if (this.mListeners != null) {
                animator.mListeners = new ArrayList<>(this.mListeners);
            }
            if (this.mPauseListeners != null) {
                animator.mPauseListeners = new ArrayList<>(this.mPauseListeners);
            }
            animator.mCachedList.set(null);
            animator.mStartListenersCalled = false;
            return animator;
        } catch (CloneNotSupportedException unused) {
            throw new AssertionError();
        }
    }

    public void reverse() {
        throw new IllegalStateException("Reverse is not supported");
    }

    void startWithoutPulsing(boolean z) {
        if (z) {
            reverse();
        } else {
            start();
        }
    }

    void getStartAndEndTimes(LongArray longArray, long j) {
        long startDelay = getStartDelay() + j;
        if (longArray.indexOf(startDelay) < 0) {
            longArray.add(startDelay);
        }
        long totalDuration = getTotalDuration();
        if (totalDuration != -1) {
            long j2 = totalDuration + j;
            if (longArray.indexOf(j2) < 0) {
                longArray.add(j2);
            }
        }
    }

    void notifyListeners(AnimatorCaller<AnimatorListener, Animator> animatorCaller, boolean z) {
        callOnList(this.mListeners, animatorCaller, this, z);
    }

    void notifyPauseListeners(AnimatorCaller<AnimatorPauseListener, Animator> animatorCaller) {
        callOnList(this.mPauseListeners, animatorCaller, this, false);
    }

    void notifyStartListeners(boolean z) {
        boolean z2 = this.mStartListenersCalled;
        this.mStartListenersCalled = true;
        if (this.mListeners == null || z2) {
            return;
        }
        notifyListeners(AnimatorCaller.ON_START, z);
    }

    void notifyEndListeners(boolean z) {
        boolean z2 = this.mStartListenersCalled;
        this.mStartListenersCalled = false;
        if (this.mListeners == null || !z2) {
            return;
        }
        notifyListeners(AnimatorCaller.ON_END, z);
    }

    boolean consumePendingEndListeners(boolean z) {
        if (this.mPendingEndCallback == null) {
            return false;
        }
        AnimationHandler.getInstance().removePendingEndAnimationCallback(this.mPendingEndCallback);
        this.mPendingEndCallback = null;
        if (!z) {
            return true;
        }
        notifyEndListeners(false);
        return true;
    }

    void notifyEndListenersFromEndAnimation(final boolean z, boolean z2) {
        if (z2) {
            this.mPendingEndCallback = new Runnable() { // from class: android.animation.Animator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Animator.this.lambda$notifyEndListenersFromEndAnimation$0(z);
                }
            };
            AnimationHandler.getInstance().postEndAnimationCallback(this.mPendingEndCallback);
        } else {
            completeEndAnimation(z, "notifyAnimEnd");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyEndListenersFromEndAnimation$0(boolean z) {
        completeEndAnimation(z, "postNotifyAnimEnd");
        this.mPendingEndCallback = null;
    }

    void completeEndAnimation(boolean z, String str) {
        boolean z2 = this.mListeners != null && Trace.isTagEnabled(8L);
        if (z2) {
            Trace.traceBegin(8L, str + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + getClass().getSimpleName());
        }
        notifyEndListeners(z);
        if (z2) {
            Trace.traceEnd(8L);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    <T, A> void callOnList(ArrayList<T> arrayList, AnimatorCaller<T, A> animatorCaller, A a, boolean z) {
        int size = arrayList == null ? 0 : arrayList.size();
        if (size > 0) {
            Object[] andSet = this.mCachedList.getAndSet(null);
            if (andSet == null || andSet.length < size) {
                andSet = new Object[size];
            }
            arrayList.toArray(andSet);
            for (int i = 0; i < size; i++) {
                animatorCaller.call(andSet[i], a, z);
                andSet[i] = null;
            }
            this.mCachedList.compareAndSet(null, andSet);
        }
    }

    public interface AnimatorListener {
        void onAnimationCancel(Animator animator);

        void onAnimationEnd(Animator animator);

        void onAnimationRepeat(Animator animator);

        void onAnimationStart(Animator animator);

        default void onAnimationStart(Animator animator, boolean z) {
            onAnimationStart(animator);
        }

        default void onAnimationEnd(Animator animator, boolean z) {
            onAnimationEnd(animator);
        }
    }

    private static class AnimatorConstantState extends ConstantState<Animator> {
        final Animator mAnimator;
        int mChangingConf;

        public AnimatorConstantState(Animator animator) {
            this.mAnimator = animator;
            animator.mConstantState = this;
            this.mChangingConf = animator.getChangingConfigurations();
        }

        @Override // android.content.res.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConf;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.content.res.ConstantState
        public Animator newInstance() {
            Animator mo76clone = this.mAnimator.mo76clone();
            mo76clone.mConstantState = this;
            return mo76clone;
        }
    }

    interface AnimatorCaller<T, A> {
        public static final AnimatorCaller<AnimatorListener, Animator> ON_START = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda0
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((Animator.AnimatorListener) obj).onAnimationStart((Animator) obj2, z);
            }
        };
        public static final AnimatorCaller<AnimatorListener, Animator> ON_END = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda1
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((Animator.AnimatorListener) obj).onAnimationEnd((Animator) obj2, z);
            }
        };
        public static final AnimatorCaller<AnimatorListener, Animator> ON_CANCEL = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda2
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((Animator.AnimatorListener) obj).onAnimationCancel((Animator) obj2);
            }
        };
        public static final AnimatorCaller<AnimatorListener, Animator> ON_REPEAT = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda3
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((Animator.AnimatorListener) obj).onAnimationRepeat((Animator) obj2);
            }
        };
        public static final AnimatorCaller<AnimatorPauseListener, Animator> ON_PAUSE = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda4
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((Animator.AnimatorPauseListener) obj).onAnimationPause((Animator) obj2);
            }
        };
        public static final AnimatorCaller<AnimatorPauseListener, Animator> ON_RESUME = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda5
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((Animator.AnimatorPauseListener) obj).onAnimationResume((Animator) obj2);
            }
        };
        public static final AnimatorCaller<ValueAnimator.AnimatorUpdateListener, ValueAnimator> ON_UPDATE = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda6
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((ValueAnimator.AnimatorUpdateListener) obj).onAnimationUpdate((ValueAnimator) obj2);
            }
        };

        void call(T t, A a, boolean z);
    }
}
