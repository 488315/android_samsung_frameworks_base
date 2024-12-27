package com.android.systemui.util;

import android.telephony.CarrierConfigManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CarrierConfigTracker_Factory implements Provider {
    private final javax.inject.Provider broadcastDispatcherProvider;
    private final javax.inject.Provider carrierConfigManagerProvider;

    public CarrierConfigTracker_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.carrierConfigManagerProvider = provider;
        this.broadcastDispatcherProvider = provider2;
    }

    public static CarrierConfigTracker_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new CarrierConfigTracker_Factory(provider, provider2);
    }

    public static CarrierConfigTracker newInstance(CarrierConfigManager carrierConfigManager, BroadcastDispatcher broadcastDispatcher) {
        return new CarrierConfigTracker(carrierConfigManager, broadcastDispatcher);
    }

    @Override // javax.inject.Provider
    public CarrierConfigTracker get() {
        return newInstance((CarrierConfigManager) this.carrierConfigManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}
