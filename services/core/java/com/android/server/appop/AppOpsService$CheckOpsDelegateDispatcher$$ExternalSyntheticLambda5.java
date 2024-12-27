package com.android.server.appop;

import android.content.AttributionSource;
import android.os.IBinder;

import com.android.internal.util.function.QuadFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda5 implements QuadFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppOpsService f$0;

    public /* synthetic */ AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda5(
            AppOpsService appOpsService, int i) {
        this.$r8$classId = i;
        this.f$0 = appOpsService;
    }

    public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
        int i = this.$r8$classId;
        AppOpsService appOpsService = this.f$0;
        switch (i) {
            case 0:
                return Integer.valueOf(
                        AppOpsService.m230$$Nest$mcheckAudioOperationImpl(
                                appOpsService,
                                ((Integer) obj).intValue(),
                                ((Integer) obj2).intValue(),
                                ((Integer) obj3).intValue(),
                                (String) obj4));
            default:
                AppOpsService.m233$$Nest$mfinishProxyOperationImpl(
                        appOpsService,
                        (IBinder) obj,
                        ((Integer) obj2).intValue(),
                        (AttributionSource) obj3,
                        ((Boolean) obj4).booleanValue());
                return null;
        }
    }
}
