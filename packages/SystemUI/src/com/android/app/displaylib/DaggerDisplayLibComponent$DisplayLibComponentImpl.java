package com.android.app.displaylib;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

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
        InstanceFactory instanceFactoryCreate = InstanceFactory.create(coroutineDispatcher);
        this.backgroundCoroutineDispatcherProvider = instanceFactoryCreate;
        this.displayRepositoryImplProvider = DoubleCheck.provider(new DisplayRepositoryImpl_Factory(this.displayManagerProvider, this.bgHandlerProvider, this.bgApplicationScopeProvider, instanceFactoryCreate));
    }
}
