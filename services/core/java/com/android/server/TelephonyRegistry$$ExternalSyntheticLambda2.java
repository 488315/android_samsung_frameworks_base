package com.android.server;

import android.telephony.LocationAccessPolicy;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class TelephonyRegistry$$ExternalSyntheticLambda2
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TelephonyRegistry f$0;
    public final /* synthetic */ LocationAccessPolicy.LocationPermissionQuery f$1;

    public /* synthetic */ TelephonyRegistry$$ExternalSyntheticLambda2(
            TelephonyRegistry telephonyRegistry,
            LocationAccessPolicy.LocationPermissionQuery locationPermissionQuery,
            int i) {
        this.$r8$classId = i;
        this.f$0 = telephonyRegistry;
        this.f$1 = locationPermissionQuery;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(
                        LocationAccessPolicy.checkLocationPermission(this.f$0.mContext, this.f$1)
                                == LocationAccessPolicy.LocationPermissionResult.ALLOWED);
            default:
                return Boolean.valueOf(
                        LocationAccessPolicy.checkLocationPermission(this.f$0.mContext, this.f$1)
                                == LocationAccessPolicy.LocationPermissionResult.ALLOWED);
        }
    }
}
