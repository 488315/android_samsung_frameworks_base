package com.android.server.location.provider;

import android.location.provider.ProviderRequest;
import com.android.internal.util.Preconditions;
import java.util.Collection;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PassiveLocationProviderManager extends LocationProviderManager {
    @Override // com.android.server.location.provider.LocationProviderManager
    public final long calculateRequestDelayMillis(long j, Collection collection) {
        return 0L;
    }

    @Override // com.android.server.location.provider.LocationProviderManager, com.android.server.location.listeners.ListenerMultiplexer
    public final String getServiceState() {
        ProviderRequest providerRequest;
        MockableLocationProvider mockableLocationProvider = this.mProvider;
        synchronized (mockableLocationProvider.mOwnerLock) {
            providerRequest = mockableLocationProvider.mRequest;
        }
        return providerRequest.isActive() ? "registered" : "unregistered";
    }

    @Override // com.android.server.location.provider.LocationProviderManager, com.android.server.location.listeners.ListenerMultiplexer
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
