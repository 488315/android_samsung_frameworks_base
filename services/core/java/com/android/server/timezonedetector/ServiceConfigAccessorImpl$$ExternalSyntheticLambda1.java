package com.android.server.timezonedetector;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class ServiceConfigAccessorImpl$$ExternalSyntheticLambda1
    implements Runnable {
  public final /* synthetic */ ServiceConfigAccessorImpl f$0;

  public /* synthetic */ ServiceConfigAccessorImpl$$ExternalSyntheticLambda1(
      ServiceConfigAccessorImpl serviceConfigAccessorImpl) {
    this.f$0 = serviceConfigAccessorImpl;
  }

  @Override // java.lang.Runnable
  public final void run() {
    this.f$0.handleConfigurationInternalChangeOnMainThread();
  }
}
