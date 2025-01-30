package com.android.server.om;

import android.content.pm.UserPackage;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverlayManagerService$2$$ExternalSyntheticLambda0
    implements Consumer {
  public final /* synthetic */ OverlayManagerService f$0;

  public /* synthetic */ OverlayManagerService$2$$ExternalSyntheticLambda0(
      OverlayManagerService overlayManagerService) {
    this.f$0 = overlayManagerService;
  }

  @Override // java.util.function.Consumer
  public final void accept(Object obj) {
    this.f$0.updateTargetPackagesLocked((UserPackage) obj);
  }
}
