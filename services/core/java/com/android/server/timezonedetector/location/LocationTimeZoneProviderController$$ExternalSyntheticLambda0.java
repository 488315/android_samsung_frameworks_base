package com.android.server.timezonedetector.location;

import com.android.server.timezonedetector.location.LocationTimeZoneProvider;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class LocationTimeZoneProviderController$$ExternalSyntheticLambda0 {
    public final /* synthetic */ LocationTimeZoneProviderController f$0;

    public /* synthetic */ LocationTimeZoneProviderController$$ExternalSyntheticLambda0(LocationTimeZoneProviderController locationTimeZoneProviderController) {
        this.f$0 = locationTimeZoneProviderController;
    }

    public final void onProviderStateChange(LocationTimeZoneProvider.ProviderState providerState) {
        LocationTimeZoneProviderController locationTimeZoneProviderController = this.f$0;
        locationTimeZoneProviderController.mThreadingDomain.assertCurrentThread();
        LocationTimeZoneProvider locationTimeZoneProvider = providerState.provider;
        if (locationTimeZoneProvider != locationTimeZoneProviderController.mPrimaryProvider && locationTimeZoneProvider != locationTimeZoneProviderController.mSecondaryProvider) {
            throw new IllegalArgumentException("Unknown provider: " + locationTimeZoneProvider);
        }
        synchronized (locationTimeZoneProviderController.mSharedLock) {
            try {
                if (Objects.equals(locationTimeZoneProviderController.mState.get(), "PROVIDERS_INITIALIZING")) {
                    LocationTimeZoneManagerService.warnLog("onProviderStateChange: Ignoring provider state change because both providers have not yet completed initialization. providerState=" + providerState, null);
                    return;
                }
                switch (providerState.stateEnum) {
                    case 1:
                    case 4:
                    case 6:
                        LocationTimeZoneManagerService.warnLog("onProviderStateChange: Unexpected state change for provider, provider=" + locationTimeZoneProvider, null);
                        break;
                    case 2:
                    case 3:
                        LocationTimeZoneManagerService.debugLog("onProviderStateChange: Received notification of a state change while started, provider=" + locationTimeZoneProvider);
                        locationTimeZoneProviderController.handleProviderStartedStateChange(providerState);
                        break;
                    case 5:
                        LocationTimeZoneManagerService.debugLog("Received notification of permanent failure for provider=" + locationTimeZoneProvider);
                        locationTimeZoneProviderController.handleProviderFailedStateChange(providerState);
                        break;
                    default:
                        LocationTimeZoneManagerService.warnLog("onProviderStateChange: Unexpected provider=" + locationTimeZoneProvider, null);
                        break;
                }
            } finally {
            }
        }
    }
}
