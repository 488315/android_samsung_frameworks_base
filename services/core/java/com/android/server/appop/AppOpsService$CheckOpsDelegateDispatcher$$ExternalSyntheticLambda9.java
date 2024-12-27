package com.android.server.appop;

import android.content.AttributionSource;

import com.android.internal.util.function.HexFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */
class AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda9 implements HexFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppOpsService f$0;

    public /* synthetic */ AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda9(
            AppOpsService appOpsService, int i) {
        this.$r8$classId = i;
        this.f$0 = appOpsService;
    }

    public final Object apply(
            Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(
                        AppOpsService.m231$$Nest$mcheckOperationImpl(
                                this.f$0,
                                ((Integer) obj).intValue(),
                                ((Integer) obj2).intValue(),
                                (String) obj3,
                                (String) obj4,
                                ((Integer) obj5).intValue(),
                                ((Boolean) obj6).booleanValue()));
            default:
                return AppOpsService.m235$$Nest$mnoteProxyOperationImpl(
                        this.f$0,
                        ((Integer) obj).intValue(),
                        (AttributionSource) obj2,
                        ((Boolean) obj3).booleanValue(),
                        (String) obj4,
                        ((Boolean) obj5).booleanValue(),
                        ((Boolean) obj6).booleanValue());
        }
    }
}
