package com.android.server.location.provider;

import com.android.server.location.provider.LocationProviderManager;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LocationProviderManager$$ExternalSyntheticLambda12 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((LocationProviderManager.Registration) obj).getRequest().isLocationSettingsIgnored();
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                LocationProviderManager.Registration registration = (LocationProviderManager.Registration) obj;
                synchronized (registration.this$0.mMultiplexerLock) {
                    registration.onHighPowerUsageChanged();
                }
                return false;
            default:
                return ((LocationProviderManager.Registration) obj).onProviderLocationRequestChanged();
        }
    }
}
