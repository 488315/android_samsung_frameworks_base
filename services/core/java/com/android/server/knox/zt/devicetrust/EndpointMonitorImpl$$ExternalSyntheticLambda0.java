package com.android.server.knox.zt.devicetrust;

import android.os.UserHandle;
import com.android.server.knox.zt.devicetrust.data.EndpointData;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class EndpointMonitorImpl$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean isApp;
        isApp = UserHandle.isApp(((EndpointData) obj).getUid());
        return isApp;
    }
}
