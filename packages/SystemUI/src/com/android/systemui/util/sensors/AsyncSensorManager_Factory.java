package com.android.systemui.util.sensors;

import android.hardware.SensorManager;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.util.concurrency.ThreadFactory;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AsyncSensorManager_Factory implements Provider {
    private final Provider pluginManagerProvider;
    private final Provider sensorManagerProvider;
    private final Provider threadFactoryProvider;

    public AsyncSensorManager_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.sensorManagerProvider = provider;
        this.threadFactoryProvider = provider2;
        this.pluginManagerProvider = provider3;
    }

    public static AsyncSensorManager_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new AsyncSensorManager_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static AsyncSensorManager newInstance(SensorManager sensorManager, ThreadFactory threadFactory, PluginManager pluginManager) {
        return new AsyncSensorManager(sensorManager, threadFactory, pluginManager);
    }

    public static AsyncSensorManager_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new AsyncSensorManager_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public AsyncSensorManager get() {
        return newInstance((SensorManager) this.sensorManagerProvider.get(), (ThreadFactory) this.threadFactoryProvider.get(), (PluginManager) this.pluginManagerProvider.get());
    }
}
