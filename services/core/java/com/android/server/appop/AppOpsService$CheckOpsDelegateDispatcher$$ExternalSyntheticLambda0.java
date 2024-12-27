package com.android.server.appop;

import com.android.internal.util.function.OctFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */
class AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda0 implements OctFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda0(
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
            Object obj8) {
        int i = this.$r8$classId;
        Object obj9 = this.f$0;
        switch (i) {
            case 0:
                AppOpsService.CheckOpsDelegateDispatcher checkOpsDelegateDispatcher =
                        (AppOpsService.CheckOpsDelegateDispatcher) obj9;
                boolean booleanValue = ((Boolean) obj8).booleanValue();
                return checkOpsDelegateDispatcher.mCheckOpsDelegate.noteOperation(
                        ((Integer) obj).intValue(),
                        ((Integer) obj2).intValue(),
                        (String) obj3,
                        (String) obj4,
                        ((Integer) obj5).intValue(),
                        ((Boolean) obj6).booleanValue(),
                        (String) obj7,
                        booleanValue,
                        new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda0(
                                1, AppOpsService.this));
            default:
                return AppOpsService.m234$$Nest$mnoteOperationImpl(
                        (AppOpsService) obj9,
                        ((Integer) obj).intValue(),
                        ((Integer) obj2).intValue(),
                        (String) obj3,
                        (String) obj4,
                        ((Integer) obj5).intValue(),
                        ((Boolean) obj6).booleanValue(),
                        (String) obj7,
                        ((Boolean) obj8).booleanValue());
        }
    }
}
