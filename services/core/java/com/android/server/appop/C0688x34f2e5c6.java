package com.android.server.appop;

import android.app.SyncNotedAppOp;
import android.content.AttributionSource;
import android.os.IBinder;
import com.android.internal.util.function.UndecFunction;

/* compiled from: R8$$SyntheticClass */
/* renamed from: com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda1 */
/* loaded from: classes.dex */
public final /* synthetic */ class C0688x34f2e5c6 implements UndecFunction {
  public final /* synthetic */ AppOpsService f$0;

  public /* synthetic */ C0688x34f2e5c6(AppOpsService appOpsService) {
    this.f$0 = appOpsService;
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
      Object obj11) {
    SyncNotedAppOp startProxyOperationImpl;
    startProxyOperationImpl =
        this.f$0.startProxyOperationImpl(
            (IBinder) obj,
            ((Integer) obj2).intValue(),
            (AttributionSource) obj3,
            ((Boolean) obj4).booleanValue(),
            ((Boolean) obj5).booleanValue(),
            (String) obj6,
            ((Boolean) obj7).booleanValue(),
            ((Boolean) obj8).booleanValue(),
            ((Integer) obj9).intValue(),
            ((Integer) obj10).intValue(),
            ((Integer) obj11).intValue());
    return startProxyOperationImpl;
  }
}
