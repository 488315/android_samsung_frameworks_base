package com.android.server.display;

import android.os.SystemClock;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayPowerController$$ExternalSyntheticLambda8
    implements ScreenOffBrightnessSensorController.Clock {
  @Override // com.android.server.display.ScreenOffBrightnessSensorController.Clock
  public final long uptimeMillis() {
    return SystemClock.uptimeMillis();
  }
}
