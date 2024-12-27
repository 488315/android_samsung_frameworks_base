package com.android.server.knox.zt.devicetrust;

import android.os.UserHandle;

import com.android.server.knox.zt.devicetrust.data.EndpointData;

import java.util.function.Predicate;

public final /* synthetic */ class EndpointMonitorImpl$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean isApp;
        isApp = UserHandle.isApp(((EndpointData) obj).getUid());
        return isApp;
    }
}
