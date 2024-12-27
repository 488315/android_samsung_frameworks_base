package com.android.server.appop;

import android.os.IBinder;

import com.android.internal.util.function.DodecFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda6 implements DodecFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda6(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final Object apply(
            Object obj,
            Object obj2,
            Object obj3,
            Object obj4,
            Object obj5,
            Object obj6,
            Object obj7,
            Object obj8,
            Object obj9,
            Object obj10,
            Object obj11,
            Object obj12) {
        int i = this.$r8$classId;
        Object obj13 = this.f$0;
        switch (i) {
            case 0:
                AppOpsService.CheckOpsDelegateDispatcher checkOpsDelegateDispatcher =
                        (AppOpsService.CheckOpsDelegateDispatcher) obj13;
                boolean booleanValue = ((Boolean) obj10).booleanValue();
                int intValue = ((Integer) obj11).intValue();
                int intValue2 = ((Integer) obj12).intValue();
                return checkOpsDelegateDispatcher.mCheckOpsDelegate.startOperation(
                        (IBinder) obj,
                        ((Integer) obj2).intValue(),
                        ((Integer) obj3).intValue(),
                        (String) obj4,
                        (String) obj5,
                        ((Integer) obj6).intValue(),
                        ((Boolean) obj7).booleanValue(),
                        ((Boolean) obj8).booleanValue(),
                        (String) obj9,
                        booleanValue,
                        intValue,
                        intValue2,
                        new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda6(
                                1, AppOpsService.this));
            default:
                return AppOpsService.m236$$Nest$mstartOperationImpl(
                        (AppOpsService) obj13,
                        (IBinder) obj,
                        ((Integer) obj2).intValue(),
                        ((Integer) obj3).intValue(),
                        (String) obj4,
                        (String) obj5,
                        ((Integer) obj6).intValue(),
                        ((Boolean) obj7).booleanValue(),
                        ((Boolean) obj8).booleanValue(),
                        (String) obj9,
                        ((Boolean) obj10).booleanValue(),
                        ((Integer) obj11).intValue(),
                        ((Integer) obj12).intValue());
        }
    }
}
