package com.android.server.appop;

import android.content.AttributionSource;

import com.android.internal.util.function.HexFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */
class AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda8 implements HexFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppOpsService.CheckOpsDelegateDispatcher f$0;

    public /* synthetic */ AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda8(
            AppOpsService.CheckOpsDelegateDispatcher checkOpsDelegateDispatcher, int i) {
        this.$r8$classId = i;
        this.f$0 = checkOpsDelegateDispatcher;
    }

    public final Object apply(
            Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        int i = this.$r8$classId;
        AppOpsService.CheckOpsDelegateDispatcher checkOpsDelegateDispatcher = this.f$0;
        int intValue = ((Integer) obj).intValue();
        switch (i) {
            case 0:
                int intValue2 = ((Integer) obj5).intValue();
                boolean booleanValue = ((Boolean) obj6).booleanValue();
                return Integer.valueOf(
                        checkOpsDelegateDispatcher.mCheckOpsDelegate.checkOperation(
                                intValue,
                                ((Integer) obj2).intValue(),
                                (String) obj3,
                                (String) obj4,
                                intValue2,
                                booleanValue,
                                new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda9(
                                        AppOpsService.this, 0)));
            default:
                boolean booleanValue2 = ((Boolean) obj5).booleanValue();
                boolean booleanValue3 = ((Boolean) obj6).booleanValue();
                return checkOpsDelegateDispatcher.mCheckOpsDelegate.noteProxyOperation(
                        intValue,
                        (AttributionSource) obj2,
                        ((Boolean) obj3).booleanValue(),
                        (String) obj4,
                        booleanValue2,
                        booleanValue3,
                        new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda9(
                                AppOpsService.this, 1));
        }
    }
}
