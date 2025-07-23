package androidx.lifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.lifecycle.Lifecycle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LifecycleService extends Service implements LifecycleOwner {
    public final ServiceLifecycleDispatcher dispatcher = new ServiceLifecycleDispatcher(this);

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.dispatcher.registry;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.dispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_START);
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.dispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_CREATE);
        super.onCreate();
    }

    @Override // android.app.Service
    public void onDestroy() {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.dispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_STOP);
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_DESTROY);
        super.onDestroy();
    }

    @Override // android.app.Service
    public final void onStart(Intent intent, int i) {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.dispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_START);
        super.onStart(intent, i);
    }
}
