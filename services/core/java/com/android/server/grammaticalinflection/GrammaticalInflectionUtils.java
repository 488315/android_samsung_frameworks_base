package com.android.server.grammaticalinflection;

import android.content.AttributionSource;
import android.permission.PermissionManager;
import android.util.Log;

public abstract class GrammaticalInflectionUtils {
    public static boolean checkSystemGrammaticalGenderPermission(
            PermissionManager permissionManager, AttributionSource attributionSource) {
        if (permissionManager.checkPermissionForDataDelivery(
                        "android.permission.READ_SYSTEM_GRAMMATICAL_GENDER",
                        attributionSource,
                        (String) null)
                == 0) {
            return true;
        }
        Log.v(
                "GrammaticalInflectionUtils",
                "AttributionSource: "
                        + attributionSource
                        + " does not have READ_SYSTEM_GRAMMATICAL_GENDER permission.");
        return false;
    }
}
