package com.android.server.location.provider;

import android.location.util.identity.CallerIdentity;
import com.android.server.location.provider.LocationProviderManager;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LocationProviderManager$$ExternalSyntheticLambda35 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LocationProviderManager$$ExternalSyntheticLambda35(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean z;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((LocationProviderManager.Registration) obj).mIdentity.getPackageName().equals((String) obj2);
            case 1:
                String str = (String) obj2;
                LocationProviderManager.Registration registration = (LocationProviderManager.Registration) obj;
                synchronized (LocationProviderManager.this.mMultiplexerLock) {
                    if (str != null) {
                        try {
                            if (!registration.mIdentity.getPackageName().equals(str)) {
                                z = false;
                            }
                        } finally {
                        }
                    }
                    z = registration.onLocationPermissionsChanged$2$1();
                }
                return z;
            case 2:
                LocationProviderManager.Registration registration2 = (LocationProviderManager.Registration) obj;
                if (!registration2.mIdentity.getPackageName().equals((String) obj2)) {
                    return false;
                }
                registration2.remove();
                return false;
            default:
                return ((LocationProviderManager.Registration) obj).mIdentity.getUid() == ((CallerIdentity) obj2).getUid();
        }
    }
}
