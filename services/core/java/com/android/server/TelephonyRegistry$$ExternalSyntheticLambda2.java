package com.android.server;

import android.telephony.LocationAccessPolicy;

import com.android.internal.util.FunctionalUtils;

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
