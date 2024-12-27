package com.android.server.location.provider;

import android.location.provider.ProviderRequest;

import com.android.internal.util.Preconditions;

import java.util.Collection;

public final class PassiveLocationProviderManager extends LocationProviderManager {
    @Override // com.android.server.location.provider.LocationProviderManager
    public final long calculateRequestDelayMillis(long j, Collection collection) {
        return 0L;
    }

    @Override // com.android.server.location.provider.LocationProviderManager,
              // com.android.server.location.listeners.ListenerMultiplexer
    public final String getServiceState() {
        ProviderRequest providerRequest;
        MockableLocationProvider mockableLocationProvider = this.mProvider;
        synchronized (mockableLocationProvider.mOwnerLock) {
            providerRequest = mockableLocationProvider.mRequest;
        }
        return providerRequest.isActive() ? "registered" : "unregistered";
    }

    @Override // com.android.server.location.provider.LocationProviderManager,
              // com.android.server.location.listeners.ListenerMultiplexer
    public final ProviderRequest mergeRegistrations(Collection collection) {
        return new ProviderRequest.Builder().setIntervalMillis(0L).build();
    }

    @Override // com.android.server.location.provider.LocationProviderManager
    public final void setMockProvider(MockLocationProvider mockLocationProvider) {
        if (mockLocationProvider != null) {
            throw new IllegalArgumentException("Cannot mock the passive provider");
        }
    }

    @Override // com.android.server.location.provider.LocationProviderManager
    public final void setRealProvider(AbstractLocationProvider abstractLocationProvider) {
        Preconditions.checkArgument(abstractLocationProvider instanceof PassiveLocationProvider);
        super.setRealProvider(abstractLocationProvider);
    }
}
