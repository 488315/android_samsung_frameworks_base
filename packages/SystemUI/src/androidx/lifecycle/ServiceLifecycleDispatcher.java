package androidx.lifecycle;

import android.os.Handler;
import androidx.lifecycle.Lifecycle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ServiceLifecycleDispatcher {
    public final Handler handler = new Handler();
    public DispatchRunnable lastDispatchRunnable;
    public final LifecycleRegistry registry;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DispatchRunnable implements Runnable {
        public final Lifecycle.Event event;
        public final LifecycleRegistry registry;
        public boolean wasExecuted;

        public DispatchRunnable(LifecycleRegistry lifecycleRegistry, Lifecycle.Event event) {
            this.registry = lifecycleRegistry;
            this.event = event;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (this.wasExecuted) {
                return;
            }
            this.registry.handleLifecycleEvent(this.event);
            this.wasExecuted = true;
        }
    }

    public ServiceLifecycleDispatcher(LifecycleOwner lifecycleOwner) {
        this.registry = new LifecycleRegistry(lifecycleOwner);
    }

    public final void postDispatchRunnable(Lifecycle.Event event) {
        DispatchRunnable dispatchRunnable = this.lastDispatchRunnable;
        if (dispatchRunnable != null) {
            dispatchRunnable.run();
        }
        DispatchRunnable dispatchRunnable2 = new DispatchRunnable(this.registry, event);
        this.lastDispatchRunnable = dispatchRunnable2;
        this.handler.postAtFrontOfQueue(dispatchRunnable2);
    }
}
