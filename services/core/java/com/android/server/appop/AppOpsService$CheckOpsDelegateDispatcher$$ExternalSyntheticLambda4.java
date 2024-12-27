package com.android.server.appop;

import android.content.AttributionSource;
import android.os.IBinder;
import com.android.internal.util.function.QuadFunction;
import com.android.server.appop.AppOpsService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda4 implements QuadFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppOpsService.CheckOpsDelegateDispatcher f$0;

    public /* synthetic */ AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda4(AppOpsService.CheckOpsDelegateDispatcher checkOpsDelegateDispatcher, int i) {
        this.$r8$classId = i;
        this.f$0 = checkOpsDelegateDispatcher;
    }

    public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
        int i = this.$r8$classId;
        AppOpsService.CheckOpsDelegateDispatcher checkOpsDelegateDispatcher = this.f$0;
        switch (i) {
            case 0:
                return Integer.valueOf(checkOpsDelegateDispatcher.mCheckOpsDelegate.checkAudioOperation(((Integer) obj).intValue(), ((Integer) obj2).intValue(), ((Integer) obj3).intValue(), (String) obj4, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda5(AppOpsService.this, 0)));
            default:
                boolean booleanValue = ((Boolean) obj4).booleanValue();
                checkOpsDelegateDispatcher.mCheckOpsDelegate.finishProxyOperation((IBinder) obj, ((Integer) obj2).intValue(), (AttributionSource) obj3, booleanValue, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda5(AppOpsService.this, 1));
                return null;
        }
    }
}
