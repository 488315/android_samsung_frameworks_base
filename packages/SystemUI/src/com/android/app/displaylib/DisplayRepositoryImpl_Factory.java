package com.android.app.displaylib;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DisplayRepositoryImpl_Factory implements Provider {
    public final Provider backgroundCoroutineDispatcherProvider;
    public final Provider backgroundHandlerProvider;
    public final Provider bgApplicationScopeProvider;
    public final Provider displayManagerProvider;

    public DisplayRepositoryImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.displayManagerProvider = provider;
        this.backgroundHandlerProvider = provider2;
        this.bgApplicationScopeProvider = provider3;
        this.backgroundCoroutineDispatcherProvider = provider4;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DisplayRepositoryImpl((DisplayManager) this.displayManagerProvider.get(), (Handler) this.backgroundHandlerProvider.get(), (CoroutineScope) this.bgApplicationScopeProvider.get(), (CoroutineDispatcher) this.backgroundCoroutineDispatcherProvider.get());
    }
}
