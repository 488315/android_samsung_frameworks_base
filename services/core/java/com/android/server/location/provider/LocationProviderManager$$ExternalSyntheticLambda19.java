package com.android.server.location.provider;

import com.android.server.location.provider.LocationProviderManager;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LocationProviderManager$$ExternalSyntheticLambda19 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ LocationProviderManager$$ExternalSyntheticLambda19(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        LocationProviderManager.Registration registration = (LocationProviderManager.Registration) obj;
        switch (i) {
            case 0:
                return registration.mIdentity.getUserId() == i2;
            case 1:
                LocationProviderManager.this.mProvider.mController.flush(new LocationProviderManager$Registration$$ExternalSyntheticLambda0(registration, i2));
                return false;
            case 2:
                LocationProviderManager.this.mProvider.mController.flush(new LocationProviderManager$Registration$$ExternalSyntheticLambda0(registration, i2));
                return false;
            case 3:
                return registration.mIdentity.getUserId() == i2;
            case 4:
                synchronized (LocationProviderManager.this.mMultiplexerLock) {
                    try {
                        if (registration.mIdentity.getUid() == i2) {
                            return registration.onLocationPermissionsChanged$2$1();
                        }
                        return false;
                    } finally {
                    }
                }
            case 5:
                return registration.mIdentity.getUserId() == i2;
            default:
                synchronized (LocationProviderManager.this.mMultiplexerLock) {
                    try {
                        if (registration.mIdentity.getUserId() == i2) {
                            return registration.onProviderLocationRequestChanged();
                        }
                        return false;
                    } finally {
                    }
                }
        }
    }
}
