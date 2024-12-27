package com.android.server.appop;

import android.os.IBinder;
import com.android.internal.util.function.HexConsumer;
import com.android.server.appop.AppOpsService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda10 implements HexConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda10(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        int i = this.$r8$classId;
        Object obj7 = this.f$0;
        switch (i) {
            case 0:
                AppOpsService.CheckOpsDelegateDispatcher checkOpsDelegateDispatcher = (AppOpsService.CheckOpsDelegateDispatcher) obj7;
                int intValue = ((Integer) obj6).intValue();
                checkOpsDelegateDispatcher.mCheckOpsDelegate.finishOperation((IBinder) obj, ((Integer) obj2).intValue(), ((Integer) obj3).intValue(), (String) obj4, (String) obj5, intValue, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda10(1, AppOpsService.this));
                break;
            default:
                AppOpsService.m232$$Nest$mfinishOperationImpl((AppOpsService) obj7, (IBinder) obj, ((Integer) obj2).intValue(), ((Integer) obj3).intValue(), (String) obj4, (String) obj5, ((Integer) obj6).intValue());
                break;
        }
    }
}
