package com.android.server.appop;

import android.util.ArraySet;

import com.android.internal.util.function.HexConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
