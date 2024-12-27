package com.android.server.appop;

import android.util.ArraySet;

import com.android.internal.util.function.HexConsumer;

public final /* synthetic */ class AppOpsService$$ExternalSyntheticLambda7 implements HexConsumer {
    public final /* synthetic */ int $r8$classId;

    public final void accept(
            Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        switch (this.$r8$classId) {
            case 0:
                ((AppOpsService) obj)
                        .notifyOpChanged(
                                (AppOpsService.ModeCallback) obj2,
                                ((Integer) obj3).intValue(),
                                ((Integer) obj4).intValue(),
                                (String) obj5,
                                (String) obj6);
                break;
            default:
                ((AppOpsService) obj)
                        .notifyOpChanged(
                                (ArraySet) obj2,
                                ((Integer) obj3).intValue(),
                                ((Integer) obj4).intValue(),
                                (String) obj5,
                                (String) obj6);
                break;
        }
    }
}
