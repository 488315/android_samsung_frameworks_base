package com.android.server.appop;

import android.app.SyncNotedAppOp;
import com.android.internal.util.function.HeptFunction;

/* compiled from: R8$$SyntheticClass */
/* renamed from: com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda3 */
/* loaded from: classes.dex */
public final /* synthetic */ class C0696x34f2e5c8 implements HeptFunction {
  public final /* synthetic */ AppOpsService f$0;

  public /* synthetic */ C0696x34f2e5c8(AppOpsService appOpsService) {
    this.f$0 = appOpsService;
  }

  public final Object apply(
      Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7) {
    SyncNotedAppOp noteOperationImpl;
    noteOperationImpl =
        this.f$0.noteOperationImpl(
            ((Integer) obj).intValue(),
            ((Integer) obj2).intValue(),
            (String) obj3,
            (String) obj4,
            ((Boolean) obj5).booleanValue(),
            (String) obj6,
            ((Boolean) obj7).booleanValue());
    return noteOperationImpl;
  }
}
