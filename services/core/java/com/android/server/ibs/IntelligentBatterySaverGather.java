package com.android.server.ibs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;

/* loaded from: classes2.dex */
public class IntelligentBatterySaverGather {
  public Context mContext;
  public DisplayManager mDisplayManager;
  public IntentFilter mFilter;
  public IntelligentBatterySaverService mIBSService;
  public IntelligentBatterySaverLogger mIntelligentBatterySaverLogger;
  public IntelligentBatterySaverGatherReceiver mReceiver;
  public final String TAG = "IntelligentBatterySaverGather";
  public final DisplayManager.DisplayListener mDisplayListener =
      new DisplayManager
          .DisplayListener() { // from class: com.android.server.ibs.IntelligentBatterySaverGather.1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {}

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {}

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
          if (i == 0) {
            synchronized (IntelligentBatterySaverGather.this) {
              IntelligentBatterySaverGather.this.mIBSService.mIBSFastDrainPolicy
                  .updateDisplayLocked();
            }
          }
        }
      };
  public boolean mScreenOn = true;
  public boolean mCharging = true;

  public IntelligentBatterySaverGather(
      Context context,
      IntelligentBatterySaverService intelligentBatterySaverService,
      IntelligentBatterySaverLogger intelligentBatterySaverLogger) {
    this.mContext = context;
    this.mIntelligentBatterySaverLogger = intelligentBatterySaverLogger;
    this.mIBSService = intelligentBatterySaverService;
  }

  public void init() {
    this.mReceiver = new IntelligentBatterySaverGatherReceiver();
    IntentFilter intentFilter = new IntentFilter();
    this.mFilter = intentFilter;
    intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
    this.mContext.registerReceiver(this.mReceiver, this.mFilter);
    DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
    this.mDisplayManager = displayManager;
    displayManager.registerDisplayListener(this.mDisplayListener, null);
  }

  public class IntelligentBatterySaverGatherReceiver extends BroadcastReceiver {
    public IntelligentBatterySaverGatherReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
        synchronized (IntelligentBatterySaverGather.this) {
          IntelligentBatterySaverGather.this.mIBSService.mIBSFastDrainPolicy.updateChargingLocked(
              intent.getIntExtra("plugged", 0) != 0);
          int intExtra = intent.getIntExtra("level", -1);
          int intExtra2 = intent.getIntExtra("scale", 0);
          if (intExtra != -1 && intExtra2 != 0) {
            IntelligentBatterySaverGather.this.mIBSService.mIBSFastDrainPolicy
                .updateBatteryLevelChanged(intExtra, intExtra2);
          }
        }
      }
    }
  }
}
