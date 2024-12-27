package com.android.server.location.gnss;

import android.location.util.identity.CallerIdentity;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GnssListenerMultiplexer$$ExternalSyntheticLambda5
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ GnssListenerMultiplexer$$ExternalSyntheticLambda5(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((GnssListenerMultiplexer.GnssListenerRegistration) obj).mIdentity.getUid()
                        == ((CallerIdentity) obj2).getUid();
            case 1:
                String str = (String) obj2;
                GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration =
                        (GnssListenerMultiplexer.GnssListenerRegistration) obj;
                if (str == null
                        || gnssListenerRegistration.mIdentity.getPackageName().equals(str)) {
                    return gnssListenerRegistration.onLocationPermissionsChanged$2();
                }
                return false;
            case 2:
                return ((GnssListenerMultiplexer.GnssListenerRegistration) obj)
                        .mIdentity
                        .getPackageName()
                        .equals((String) obj2);
            default:
                GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration2 =
                        (GnssListenerMultiplexer.GnssListenerRegistration) obj;
                if (!gnssListenerRegistration2.mIdentity.getPackageName().equals((String) obj2)) {
                    return false;
                }
                gnssListenerRegistration2.remove();
                return false;
        }
    }
}
