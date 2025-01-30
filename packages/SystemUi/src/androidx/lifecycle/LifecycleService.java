package androidx.lifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.lifecycle.Lifecycle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LifecycleService extends Service implements LifecycleOwner {
    public final ServiceLifecycleDispatcher mDispatcher = new ServiceLifecycleDispatcher(this);

    @Override // androidx.lifecycle.LifecycleOwner
    public final LifecycleRegistry getLifecycle() {
        return this.mDispatcher.mRegistry;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.mDispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_START);
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.mDispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_CREATE);
        super.onCreate();
    }

    @Override // android.app.Service
    public void onDestroy() {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.mDispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_STOP);
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_DESTROY);
        super.onDestroy();
    }

    @Override // android.app.Service
    public final void onStart(Intent intent, int i) {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.mDispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_START);
        super.onStart(intent, i);
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        return super.onStartCommand(intent, i, i2);
    }
}
