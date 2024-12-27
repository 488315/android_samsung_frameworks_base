package com.android.server.grammaticalinflection;

import android.content.AttributionSource;
import android.permission.PermissionManager;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
