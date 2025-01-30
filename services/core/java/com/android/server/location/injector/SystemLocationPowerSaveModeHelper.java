package com.android.server.location.injector;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import com.android.internal.util.Preconditions;
import com.android.server.LocalServices;
import com.android.server.location.LocationServiceThread;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class SystemLocationPowerSaveModeHelper extends LocationPowerSaveModeHelper
    implements Consumer {
  public final Context mContext;
  public volatile int mLocationPowerSaveMode;
  public boolean mReady;

  public SystemLocationPowerSaveModeHelper(Context context) {
    this.mContext = context;
  }

  public void onSystemReady() {
    if (this.mReady) {
      return;
    }
    ((PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class))
        .registerLowPowerModeObserver(1, this);
    PowerManager powerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class);
    Objects.requireNonNull(powerManager);
    this.mLocationPowerSaveMode = powerManager.getLocationPowerSaveMode();
    this.mReady = true;
  }

  @Override // java.util.function.Consumer
  public void accept(PowerSaveState powerSaveState) {
    final int i = !powerSaveState.batterySaverEnabled ? 0 : powerSaveState.locationMode;
    if (i == this.mLocationPowerSaveMode) {
      return;
    }
    this.mLocationPowerSaveMode = i;
    LocationServiceThread.getHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.location.injector.SystemLocationPowerSaveModeHelper$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                SystemLocationPowerSaveModeHelper.this.lambda$accept$0(i);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$accept$0(int i) {
    notifyLocationPowerSaveModeChanged(i);
  }

  @Override // com.android.server.location.injector.LocationPowerSaveModeHelper
  public int getLocationPowerSaveMode() {
    Preconditions.checkState(this.mReady);
    return this.mLocationPowerSaveMode;
  }
}
