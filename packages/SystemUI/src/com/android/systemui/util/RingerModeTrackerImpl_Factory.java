package com.android.systemui.util;

import android.media.AudioManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class RingerModeTrackerImpl_Factory implements Provider {
    private final Provider audioManagerProvider;
    private final Provider broadcastDispatcherProvider;
    private final Provider executorProvider;

    public RingerModeTrackerImpl_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.audioManagerProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.executorProvider = provider3;
    }

    public static RingerModeTrackerImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new RingerModeTrackerImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static RingerModeTrackerImpl newInstance(AudioManager audioManager, BroadcastDispatcher broadcastDispatcher, Executor executor) {
        return new RingerModeTrackerImpl(audioManager, broadcastDispatcher, executor);
    }

    public static RingerModeTrackerImpl_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new RingerModeTrackerImpl_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public RingerModeTrackerImpl get() {
        return newInstance((AudioManager) this.audioManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (Executor) this.executorProvider.get());
    }
}
