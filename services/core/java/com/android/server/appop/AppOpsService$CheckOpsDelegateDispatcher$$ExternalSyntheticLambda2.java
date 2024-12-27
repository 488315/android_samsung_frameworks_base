package com.android.server.appop;

import android.content.AttributionSource;
import android.os.IBinder;
import com.android.internal.util.function.UndecFunction;
import com.android.server.appop.AppOpsService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda2 implements UndecFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final Object apply(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10, Object obj11) {
        int i = this.$r8$classId;
        Object obj12 = this.f$0;
        switch (i) {
            case 0:
                AppOpsService.CheckOpsDelegateDispatcher checkOpsDelegateDispatcher = (AppOpsService.CheckOpsDelegateDispatcher) obj12;
                boolean booleanValue = ((Boolean) obj7).booleanValue();
                boolean booleanValue2 = ((Boolean) obj8).booleanValue();
                int intValue = ((Integer) obj9).intValue();
                int intValue2 = ((Integer) obj10).intValue();
                int intValue3 = ((Integer) obj11).intValue();
                return checkOpsDelegateDispatcher.mCheckOpsDelegate.startProxyOperation((IBinder) obj, ((Integer) obj2).intValue(), (AttributionSource) obj3, ((Boolean) obj4).booleanValue(), ((Boolean) obj5).booleanValue(), (String) obj6, booleanValue, booleanValue2, intValue, intValue2, intValue3, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda2(1, AppOpsService.this));
            default:
                return AppOpsService.m237$$Nest$mstartProxyOperationImpl((AppOpsService) obj12, (IBinder) obj, ((Integer) obj2).intValue(), (AttributionSource) obj3, ((Boolean) obj4).booleanValue(), ((Boolean) obj5).booleanValue(), (String) obj6, ((Boolean) obj7).booleanValue(), ((Boolean) obj8).booleanValue(), ((Integer) obj9).intValue(), ((Integer) obj10).intValue(), ((Integer) obj11).intValue());
        }
    }
}
