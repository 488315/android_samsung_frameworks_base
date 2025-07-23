package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import androidx.lifecycle.Lifecycle;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ProcessLifecycleOwner implements LifecycleOwner {
    public static final Companion Companion = new Companion(null);
    public static final ProcessLifecycleOwner newInstance = new ProcessLifecycleOwner();
    public Handler handler;
    public int resumedCounter;
    public int startedCounter;
    public boolean pauseSent = true;
    public boolean stopSent = true;
    public final LifecycleRegistry registry = new LifecycleRegistry(this);
    public final ProcessLifecycleOwner$$ExternalSyntheticLambda0 delayedPauseRunnable = new Runnable() { // from class: androidx.lifecycle.ProcessLifecycleOwner$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            ProcessLifecycleOwner processLifecycleOwner = ProcessLifecycleOwner.this;
            int i = processLifecycleOwner.resumedCounter;
            LifecycleRegistry lifecycleRegistry = processLifecycleOwner.registry;
            if (i == 0) {
                processLifecycleOwner.pauseSent = true;
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
            }
            if (processLifecycleOwner.startedCounter == 0 && processLifecycleOwner.pauseSent) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                processLifecycleOwner.stopSent = true;
            }
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Api29Impl {
        static {
            new Api29Impl();
        }

        private Api29Impl() {
        }

        public static final void registerActivityLifecycleCallbacks(Activity activity, Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
            activity.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.lifecycle.ProcessLifecycleOwner$$ExternalSyntheticLambda0] */
    private ProcessLifecycleOwner() {
        new Object() { // from class: androidx.lifecycle.ProcessLifecycleOwner$initializationListener$1
        };
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.registry;
    }
}
