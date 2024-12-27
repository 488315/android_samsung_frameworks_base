package com.android.systemui.util;

import android.media.AudioManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

public final class RingerModeTrackerImpl_Factory implements Provider {
    private final javax.inject.Provider audioManagerProvider;
    private final javax.inject.Provider broadcastDispatcherProvider;
    private final javax.inject.Provider executorProvider;

    public RingerModeTrackerImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.audioManagerProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.executorProvider = provider3;
    }

    public static RingerModeTrackerImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new RingerModeTrackerImpl_Factory(provider, provider2, provider3);
    }

    public static RingerModeTrackerImpl newInstance(AudioManager audioManager, BroadcastDispatcher broadcastDispatcher, Executor executor) {
        return new RingerModeTrackerImpl(audioManager, broadcastDispatcher, executor);
    }

    @Override // javax.inject.Provider
    public RingerModeTrackerImpl get() {
        return newInstance((AudioManager) this.audioManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (Executor) this.executorProvider.get());
    }
}
