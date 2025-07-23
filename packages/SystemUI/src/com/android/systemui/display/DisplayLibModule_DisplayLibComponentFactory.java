package com.android.systemui.display;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import com.android.app.displaylib.DaggerDisplayLibComponent$DisplayLibComponentImpl;
import com.android.app.displaylib.DaggerDisplayLibComponent$Factory;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DisplayLibModule_DisplayLibComponentFactory implements Provider {
    public final Provider backgroundCoroutineDispatcherProvider;
    public final Provider backgroundHandlerProvider;
    public final Provider bgApplicationScopeProvider;
    public final Provider displayManagerProvider;

    public DisplayLibModule_DisplayLibComponentFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.displayManagerProvider = provider;
        this.backgroundHandlerProvider = provider2;
        this.bgApplicationScopeProvider = provider3;
        this.backgroundCoroutineDispatcherProvider = provider4;
    }

    public static DaggerDisplayLibComponent$DisplayLibComponentImpl displayLibComponent(DisplayManager displayManager, Handler handler, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        DisplayLibModule.INSTANCE.getClass();
        new DaggerDisplayLibComponent$Factory(0);
        displayManager.getClass();
        handler.getClass();
        coroutineScope.getClass();
        coroutineDispatcher.getClass();
        return new DaggerDisplayLibComponent$DisplayLibComponentImpl(displayManager, handler, coroutineScope, coroutineDispatcher, 0);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return displayLibComponent((DisplayManager) this.displayManagerProvider.get(), (Handler) this.backgroundHandlerProvider.get(), (CoroutineScope) this.bgApplicationScopeProvider.get(), (CoroutineDispatcher) this.backgroundCoroutineDispatcherProvider.get());
    }
}
