package com.android.server.location.geofence;

import android.app.PendingIntent;
import com.android.server.location.geofence.GeofenceManager;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GeofenceManager$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ GeofenceManager$$ExternalSyntheticLambda4(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((GeofenceManager.GeofenceKey) obj).mPendingIntent.equals((PendingIntent) obj2);
            default:
                String str = (String) obj2;
                GeofenceManager.GeofenceRegistration geofenceRegistration = (GeofenceManager.GeofenceRegistration) obj;
                if (str == null || geofenceRegistration.mIdentity.getPackageName().equals(str)) {
                    return geofenceRegistration.onLocationPermissionsChanged$1$1();
                }
                return false;
        }
    }
}
