package androidx.lifecycle;

import android.os.Handler;
import androidx.lifecycle.Lifecycle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ProcessLifecycleOwner implements LifecycleOwner {
    public static final ProcessLifecycleOwner sInstance = new ProcessLifecycleOwner();
    public Handler mHandler;
    public int mStartedCounter = 0;
    public int mResumedCounter = 0;
    public boolean mPauseSent = true;
    public boolean mStopSent = true;
    public final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    public final RunnableC03031 mDelayedPauseRunnable = new Runnable() { // from class: androidx.lifecycle.ProcessLifecycleOwner.1
        @Override // java.lang.Runnable
        public final void run() {
            ProcessLifecycleOwner processLifecycleOwner = ProcessLifecycleOwner.this;
            if (processLifecycleOwner.mResumedCounter == 0) {
                processLifecycleOwner.mPauseSent = true;
                processLifecycleOwner.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
            }
            ProcessLifecycleOwner processLifecycleOwner2 = ProcessLifecycleOwner.this;
            if (processLifecycleOwner2.mStartedCounter == 0 && processLifecycleOwner2.mPauseSent) {
                processLifecycleOwner2.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                processLifecycleOwner2.mStopSent = true;
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.lifecycle.ProcessLifecycleOwner$2 */
    public final class C03042 {
        public C03042() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.lifecycle.ProcessLifecycleOwner$1] */
    private ProcessLifecycleOwner() {
        new C03042();
    }

    public final void activityResumed() {
        int i = this.mResumedCounter + 1;
        this.mResumedCounter = i;
        if (i == 1) {
            if (!this.mPauseSent) {
                this.mHandler.removeCallbacks(this.mDelayedPauseRunnable);
            } else {
                this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
                this.mPauseSent = false;
            }
        }
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final LifecycleRegistry getLifecycle() {
        return this.mRegistry;
    }
}
