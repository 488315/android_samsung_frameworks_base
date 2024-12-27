package com.android.server.location.geofence;

import com.android.server.location.geofence.GeofenceManager;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GeofenceManager$$ExternalSyntheticLambda5 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ GeofenceManager$$ExternalSyntheticLambda5(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        GeofenceManager.GeofenceRegistration geofenceRegistration = (GeofenceManager.GeofenceRegistration) obj;
        switch (i) {
            case 0:
                return geofenceRegistration.mIdentity.getUserId() == i2;
            case 1:
                if (geofenceRegistration.mIdentity.getUid() == i2) {
                    return geofenceRegistration.onLocationPermissionsChanged$1$1();
                }
                return false;
            case 2:
                return geofenceRegistration.mIdentity.getUserId() == i2;
            default:
                return geofenceRegistration.mIdentity.getUserId() == i2;
        }
    }
}
