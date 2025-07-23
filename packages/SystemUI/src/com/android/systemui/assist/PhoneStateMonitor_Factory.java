package com.android.systemui.assist;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PhoneStateMonitor_Factory implements Provider {
    public final Provider bgHandlerProvider;
    public final Provider bootCompleteCacheProvider;
    public final Provider broadcastDispatcherProvider;
    public final Provider centralSurfacesOptionalLazyProvider;
    public final Provider contextProvider;
    public final Provider statusBarStateControllerProvider;

    public PhoneStateMonitor_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.centralSurfacesOptionalLazyProvider = provider3;
        this.bootCompleteCacheProvider = provider4;
        this.bgHandlerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
    }

    public static PhoneStateMonitor newInstance(Context context, BroadcastDispatcher broadcastDispatcher, Lazy lazy, BootCompleteCache bootCompleteCache, Handler handler, StatusBarStateController statusBarStateController) {
        return new PhoneStateMonitor(context, broadcastDispatcher, lazy, bootCompleteCache, handler, statusBarStateController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PhoneStateMonitor((Context) this.contextProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), DoubleCheck.lazy(this.centralSurfacesOptionalLazyProvider), (BootCompleteCache) this.bootCompleteCacheProvider.get(), (Handler) this.bgHandlerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get());
    }
}
