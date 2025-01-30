package com.android.server.pm;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda15
    implements ApkChecksums.Injector.Producer {
  public final /* synthetic */ PackageManagerServiceInjector f$0;

  public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda15(
      PackageManagerServiceInjector packageManagerServiceInjector) {
    this.f$0 = packageManagerServiceInjector;
  }

  @Override // com.android.server.pm.ApkChecksums.Injector.Producer
  public final Object produce() {
    return this.f$0.getIncrementalManager();
  }
}
