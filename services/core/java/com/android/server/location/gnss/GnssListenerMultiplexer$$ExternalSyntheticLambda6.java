package com.android.server.location.gnss;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class GnssListenerMultiplexer$$ExternalSyntheticLambda6
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ GnssListenerMultiplexer$$ExternalSyntheticLambda6(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration =
                (GnssListenerMultiplexer.GnssListenerRegistration) obj;
        switch (i) {
            case 0:
                return gnssListenerRegistration.mIdentity.getUserId() == i2;
            case 1:
                return gnssListenerRegistration.mIdentity.getUserId() == i2;
            case 2:
                return gnssListenerRegistration.mIdentity.getUserId() == i2;
            default:
                if (gnssListenerRegistration.mIdentity.getUid() == i2) {
                    return gnssListenerRegistration.onLocationPermissionsChanged$2();
                }
                return false;
        }
    }
}
