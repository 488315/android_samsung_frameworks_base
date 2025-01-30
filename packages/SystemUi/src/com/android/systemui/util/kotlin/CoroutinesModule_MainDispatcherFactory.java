package com.android.systemui.util.kotlin;

import dagger.internal.Preconditions;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CoroutinesModule_MainDispatcherFactory implements Provider {
    public static CoroutineDispatcher mainDispatcher() {
        CoroutinesModule.INSTANCE.getClass();
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        MainCoroutineDispatcher immediate = MainDispatcherLoader.dispatcher.getImmediate();
        Preconditions.checkNotNullFromProvides(immediate);
        return immediate;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return mainDispatcher();
    }
}
