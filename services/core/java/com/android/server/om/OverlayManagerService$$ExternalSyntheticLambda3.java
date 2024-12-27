package com.android.server.om;

import android.content.pm.PackageManagerInternal;
import android.os.Bundle;

import com.android.server.LocalServices;

import java.util.function.BiFunction;

public final /* synthetic */ class OverlayManagerService$$ExternalSyntheticLambda3
        implements BiFunction {
    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        int intValue = ((Integer) obj).intValue();
        Bundle bundle = (Bundle) obj2;
        String string = bundle.getString("android.intent.extra.PACKAGE_NAME");
        if (((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
                .filterAppAccess(
                        intValue, bundle.getInt("android.intent.extra.USER_ID"), string, false)) {
            return null;
        }
        return bundle;
    }
}
