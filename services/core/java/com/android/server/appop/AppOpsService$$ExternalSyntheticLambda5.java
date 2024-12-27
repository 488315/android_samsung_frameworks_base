package com.android.server.appop;

import com.android.internal.util.function.QuadConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AppOpsService$$ExternalSyntheticLambda5 implements QuadConsumer {
    public final /* synthetic */ int $r8$classId;

    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
        AppOpsService appOpsService = (AppOpsService) obj;
        Integer num = (Integer) obj2;
        switch (this.$r8$classId) {
            case 0:
                appOpsService.updateStartedOpModeForUserForDefaultDevice(
                        num.intValue(),
                        ((Integer) obj4).intValue(),
                        ((Boolean) obj3).booleanValue());
                break;
            default:
                int intValue = num.intValue();
                boolean booleanValue = ((Boolean) obj3).booleanValue();
                int intValue2 = ((Integer) obj4).intValue();
                boolean z = AppOpsService.DEBUG;
                appOpsService.updateStartedOpModeForUserForDefaultDevice(
                        intValue, intValue2, booleanValue);
                break;
        }
    }
}
