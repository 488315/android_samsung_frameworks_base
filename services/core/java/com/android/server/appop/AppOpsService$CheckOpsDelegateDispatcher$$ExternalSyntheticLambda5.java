package com.android.server.appop;

import android.content.AttributionSource;
import android.os.IBinder;

import com.android.internal.util.function.QuadFunction;

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
