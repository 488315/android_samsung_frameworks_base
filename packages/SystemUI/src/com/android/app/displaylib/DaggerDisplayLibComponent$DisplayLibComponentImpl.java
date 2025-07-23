package com.android.app.displaylib;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DaggerDisplayLibComponent$DisplayLibComponentImpl {
    public final InstanceFactory backgroundCoroutineDispatcherProvider;
    public final InstanceFactory bgApplicationScopeProvider;
    public final InstanceFactory bgHandlerProvider;
    public final InstanceFactory displayManagerProvider;
    public final Provider displayRepositoryImplProvider;

    public /* synthetic */ DaggerDisplayLibComponent$DisplayLibComponentImpl(DisplayManager displayManager, Handler handler, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, int i) {
        this(displayManager, handler, coroutineScope, coroutineDispatcher);
    }

    private DaggerDisplayLibComponent$DisplayLibComponentImpl(DisplayManager displayManager, Handler handler, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.displayManagerProvider = InstanceFactory.create(displayManager);
        this.bgHandlerProvider = InstanceFactory.create(handler);
        this.bgApplicationScopeProvider = InstanceFactory.create(coroutineScope);
        InstanceFactory create = InstanceFactory.create(coroutineDispatcher);
        this.backgroundCoroutineDispatcherProvider = create;
        this.displayRepositoryImplProvider = DoubleCheck.provider(new DisplayRepositoryImpl_Factory(this.displayManagerProvider, this.bgHandlerProvider, this.bgApplicationScopeProvider, create));
    }
}
