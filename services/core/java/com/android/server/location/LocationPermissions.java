package com.android.server.location;

import android.content.Context;
import android.os.Binder;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

public abstract class LocationPermissions {
    public static int asAppOp(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        throw new IllegalArgumentException();
    }

    public static void enforceCallingOrSelfBypassPermission(Context context) {
        int callingUid = Binder.getCallingUid();
        if (context.checkPermission(
                        "android.permission.LOCATION_BYPASS", Binder.getCallingPid(), callingUid)
                != 0) {
            throw new SecurityException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            callingUid,
                            "uid",
                            " does not have android.permission.LOCATION_BYPASS."));
        }
    }

    public static void enforceLocationPermission(int i, int i2, int i3) {
        if (i2 >= i3) {
            return;
        }
        if (i3 == 1) {
            throw new SecurityException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i,
                            "uid ",
                            " does not have android.permission.ACCESS_COARSE_LOCATION or"
                                + " android.permission.ACCESS_FINE_LOCATION."));
        }
        if (i3 == 2) {
            throw new SecurityException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "uid ", " does not have android.permission.ACCESS_FINE_LOCATION."));
        }
    }

    public static int getPermissionLevel(Context context, int i, int i2) {
        if (context.checkPermission("android.permission.ACCESS_FINE_LOCATION", i2, i) == 0) {
            return 2;
        }
        return context.checkPermission("android.permission.ACCESS_COARSE_LOCATION", i2, i) == 0
                ? 1
                : 0;
    }
}
