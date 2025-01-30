package com.android.server.appop;

import android.app.SyncNotedAppOp;
import android.os.IBinder;
import com.android.internal.util.function.UndecFunction;

/* compiled from: R8$$SyntheticClass */
/* renamed from: com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda11 */
/* loaded from: classes.dex */
public final /* synthetic */ class C0690x6969d32b implements UndecFunction {
    public final /* synthetic */ AppOpsService f$0;

    public /* synthetic */ C0690x6969d32b(AppOpsService appOpsService) {
        this.f$0 = appOpsService;
    }

    public final Object apply(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10, Object obj11) {
        SyncNotedAppOp startOperationImpl;
        startOperationImpl = this.f$0.startOperationImpl((IBinder) obj, ((Integer) obj2).intValue(), ((Integer) obj3).intValue(), (String) obj4, (String) obj5, ((Boolean) obj6).booleanValue(), ((Boolean) obj7).booleanValue(), (String) obj8, ((Boolean) obj9).booleanValue(), ((Integer) obj10).intValue(), ((Integer) obj11).intValue());
        return startOperationImpl;
    }
}
